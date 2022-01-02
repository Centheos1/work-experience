import logging as logger
import datetime
import pandas as pd
import json
import requests
from requests.auth import HTTPBasicAuth
from requests.exceptions import HTTPError
import copy
import boto3
import os
import base64
import urllib

from constants import *
from helpers import *
from source_api import *
from mbo_api import *
from enrolment import *
from gymsales_api import *


# ######################################################################
# CHECK DUPLICATE KEY                                                  #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Returns: Boolean                                                    #
# ######################################################################
def check_duplicate_key(ENROLMENT_DATA_ENTITY):

    if ENROLMENT_DATA_ENTITY['accessKeyNumber'] is None:
        return False

    PARAMS = { "ClientIds" : ENROLMENT_DATA_ENTITY['accessKeyNumber'] }

    response = mbo_get_request(Constants.MBO_GET_CLIENTS_URL, PARAMS)

    if response['PaginationResponse']['TotalResults'] == 0:
        print('Not a Duplicate Key')
        logger.info('Not a Duplicate Key')
        is_duplicate_key = False
    else:
        print('Duplicate Access Key')
        logger.info('Duplicate Access Key')
        is_duplicate_key = True

    logger.info(f"is_duplicate_key: {is_duplicate_key}")
    print(f"is_duplicate_key: {is_duplicate_key}")

    return is_duplicate_key


# ######################################################################
# CHECK EXISTING CLIENT                                                #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Returns: Dictionary                                                 #
# ######################################################################
def check_existing_client(ENROLMENT_DATA_ENTITY):

    PARAMS = { "SearchText" : ENROLMENT_DATA_ENTITY['email'] }

    if Constants.IS_TEST:
        logger.info(PARAMS)

    response = mbo_get_request(Constants.MBO_GET_CLIENTS_URL, PARAMS)

    EXISTING_MBO_CLIENT = None
    EXISTING_CREDIT_CARD = None
    EXISTING_BANK_DETIALS = None

    if response['PaginationResponse']['TotalResults'] == 0:
        logger.info('New Client')
        print('New Client')
        ENROLMENT_DATA_ENTITY['existingClient'] = False
    else:
        logger.info(f'Found MBO Profile with Email: {ENROLMENT_DATA_ENTITY["email"] }')
        print(f'Found MBO Profile with Email: {ENROLMENT_DATA_ENTITY["email"] }')
        mbo_clients = response['Clients']

        for c in mbo_clients:
            logger.info(f"firstName: {c['FirstName']} | lastName: {c['LastName']}")
            print(f"firstName: {c['FirstName']} | lastName: {c['LastName']}")
            if c['FirstName'].lower().strip() == ENROLMENT_DATA_ENTITY['firstName'].lower().strip() and c['LastName'].lower().strip() == ENROLMENT_DATA_ENTITY['lastName'].lower().strip():
                logger.info('Existing Client')
                print('Existing Client')
                ENROLMENT_DATA_ENTITY['existingClient'] = True
                EXISTING_MBO_CLIENT = c
                EXISTING_CREDIT_CARD = EXISTING_MBO_CLIENT['ClientCreditCard']
            else:
                logger.info('New Client BUT Duplicate Email')
                print('New Client BUT Duplicate Email')
                ENROLMENT_DATA_ENTITY['existingClient'] = False
                handle_manual_submission(MemberStatus.DUPLICATE_USERNAME, ENROLMENT_DATA_ENTITY, None)
                ENROLMENT_DATA_ENTITY['status'] = MemberStatus.DUPLICATE_USERNAME[0]

    return {
        'EXISTING_MBO_CLIENT' : EXISTING_MBO_CLIENT,
        'EXISTING_CREDIT_CARD' : EXISTING_CREDIT_CARD,
        'EXISTING_BANK_DETIALS' : EXISTING_BANK_DETIALS,
        'ENROLMENT_DATA_ENTITY' : ENROLMENT_DATA_ENTITY
    }


# ######################################################################
# ADD OR UPDATE CLIENT                                                 #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Params: is_duplicate_key                                            #
# @Params: EXISTING_MBO_CLIENT                                         #
# @Returns: Dictionary                                                 #
# ######################################################################
def add_or_update_client(ENROLMENT_DATA_ENTITY, is_duplicate_key, EXISTING_MBO_CLIENT):

    IS_MBO_SUCCESS = True
    response = None

    MBO_CLIENT = build_mbo_client_from_enrolmentData(ENROLMENT_DATA_ENTITY, is_duplicate_key, EXISTING_MBO_CLIENT)
    if ENROLMENT_DATA_ENTITY['existingClient']:
        logger.info(f'Update Client: {MBO_CLIENT}')
        print(f'Update Client: {MBO_CLIENT}')
        response = mbo_post_request(Constants.MBO_UPDATE_CLIENT_URL, MBO_CLIENT)
        if response is None:
            logger.info("Error Updating Existing Client")
            print("Error Updating Existing Client")

            handle_manual_submission(MemberStatus.PENDING, ENROLMENT_DATA_ENTITY, None)
            ENROLMENT_DATA_ENTITY['status'] = MemberStatus.PENDING[0]
            IS_MBO_SUCCESS = False
        else:
            MBO_CLIENT = response['Client']
    else:
        logger.info(f'Add Client: {MBO_CLIENT}')
        print(f'Add Client: {MBO_CLIENT}')

        response = mbo_post_request(Constants.MBO_ADD_CLIENT_URL, MBO_CLIENT)

        if response is None:
            logger.info("Error Adding New Client")
            print("Error Adding New Client")

            handle_manual_submission(MemberStatus.PENDING, ENROLMENT_DATA_ENTITY, None)
            ENROLMENT_DATA_ENTITY['status'] = MemberStatus.PENDING[0]
            IS_MBO_SUCCESS = False
        else:
            MBO_CLIENT = response['Client']

    try:
        ENROLMENT_DATA_ENTITY['mboUniqueId'] = MBO_CLIENT['UniqueId']
        if Constants.IS_TEST:
            logger.info(f"Added mboUniqueId to ENROLMENT_DATA_ENTITY: {ENROLMENT_DATA_ENTITY['mboUniqueId']}")
    except Exception as ex:
        print(f"Failed to add mboUniqueId")
        logger.error(f"Failed to add mboUniqueId")

    return {
        'ENROLMENT_DATA_ENTITY' : ENROLMENT_DATA_ENTITY,
        'MBO_CLIENT' : MBO_CLIENT,
        'IS_MBO_SUCCESS' : IS_MBO_SUCCESS
    }



# ######################################################################
# ADD BANK DETAILS                                                     #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Params: MBO_CLIENT                                                  #
# @Returns: Dictionary                                                 #
# ######################################################################
def add_bank_details(ENROLMENT_DATA_ENTITY, MBO_CLIENT):

    IS_MBO_SUCCESS = True

    ADD_CLIENT_DIRECT_DEBIT_INFO = {
        "Test": Constants.IS_TEST,
        "ClientId": MBO_CLIENT['Id'],
        "AccountNumber": ENROLMENT_DATA_ENTITY['memberBankDetail']['accountNumber'],
        "RoutingNumber": ENROLMENT_DATA_ENTITY['memberBankDetail']['bsb'],
        "AccountType": ENROLMENT_DATA_ENTITY['memberBankDetail']['accountType'].title(),
        "NameOnAccount": ENROLMENT_DATA_ENTITY['memberBankDetail']['accountHolderName']
    }
    
    response = mbo_post_request(Constants.MBO_ADD_CLIENT_DIRECT_DEBIT_INFO_URL, ADD_CLIENT_DIRECT_DEBIT_INFO)

    if response is None:
        
        logger.error("Error Adding Member Bank Details")
        print("Error Adding Member Bank Details")

        handle_manual_submission(MemberStatus.ADD_DIRECT_DEBIT_INFO_ERROR, ENROLMENT_DATA_ENTITY, None)
        ENROLMENT_DATA_ENTITY['status'] = MemberStatus.ADD_DIRECT_DEBIT_INFO_ERROR[0]
        IS_MBO_SUCCESS = False

    return { 
        'IS_MBO_SUCCESS' : IS_MBO_SUCCESS,
        'ENROLMENT_DATA_ENTITY' : ENROLMENT_DATA_ENTITY
    }


# ######################################################################
# ADD BANK DETAILS                                                     #
# @Params: FP_COACH_ENTITY                                             #
# @Params: MBO_CLIENT                                                  #
# @Returns: Dictionary                                                 #
# ######################################################################
def coach_add_bank_details(FP_COACH_ENTITY, MBO_CLIENT):

    IS_MBO_SUCCESS = True

    ADD_CLIENT_DIRECT_DEBIT_INFO = {
        "Test": Constants.IS_TEST,
        "ClientId": MBO_CLIENT['Id'],
        "AccountNumber": FP_COACH_ENTITY['memberBankDetail']['accountNumber'],
        "RoutingNumber": FP_COACH_ENTITY['memberBankDetail']['bsb'],
        "AccountType": FP_COACH_ENTITY['memberBankDetail']['accountType'].title(),
        "NameOnAccount": FP_COACH_ENTITY['memberBankDetail']['accountHolderName']
    }
    
    response = mbo_post_request(Constants.MBO_ADD_CLIENT_DIRECT_DEBIT_INFO_URL, ADD_CLIENT_DIRECT_DEBIT_INFO)

    if response is None:
        
        logger.error("Error Adding Member Bank Details")
        print("Error Adding Member Bank Details")

        handle_manual_submission(MemberStatus.ADD_DIRECT_DEBIT_INFO_ERROR, None, FP_COACH_ENTITY)
        FP_COACH_ENTITY['status'] = MemberStatus.ADD_DIRECT_DEBIT_INFO_ERROR[0]
        IS_MBO_SUCCESS = False

    return { 
        'IS_MBO_SUCCESS' : IS_MBO_SUCCESS,
        'FP_COACH_ENTITY' : FP_COACH_ENTITY
    }



# ######################################################################
# PURCHASE CONTRACTS                                                   #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Params: MBO_CLIENT                                                  #
# @Returns: Dictionary                                                 #
# ######################################################################
def purchase_contracts(ENROLMENT_DATA_ENTITY, MBO_CLIENT):

    IS_MBO_SUCCESS = True
    use_existing_payment_details = False
    dollar_value = 0

    # Get MBO Contracts from Source Database
    mbo_contracts = source_get_request(Constants.SOURCE_GET_ALL_CONTRACTS_URL + ENROLMENT_DATA_ENTITY['locationId'])

    if ENROLMENT_DATA_ENTITY['memberContracts'] is not None:
        for c in ENROLMENT_DATA_ENTITY['memberContracts'].split(','):

            if Constants.IS_TEST:
                logger.info(f"Contract Id: {c}")
                print(f"Contract Id: {c}")

            is_external_pt_contract = False
            is_free_contract = False
            is_access_key_contract = False
            is_coaching_contract = False
            trigger_virtual_playground = False

            SalesRepId = { "Id": int(ENROLMENT_DATA_ENTITY['staffMember'].split('::')[0]), "SalesRepNumber": 1 }

            # Find the Contract
            for mbo_c in mbo_contracts:
                if int(c) == int(mbo_c['mboId']):
                    dollar_value += (float(mbo_c['numberOfAutoPays']) * float(mbo_c['reoccuringPaymentAmountTotal']))
                    logger.info(f"Contract Name: {mbo_c['name']}")
                    print(f"Contract Name: {mbo_c['name']}")
                    PromotionCode = None
                    
                    # Handle Promotions
                    if ENROLMENT_DATA_ENTITY['couponCode'] is not None:
                        if 'First' in mbo_c['name']:
                            for code in ENROLMENT_DATA_ENTITY['couponCode'].split(','):
                                if Constants.MBO_COUPON_CODE_N_DAYS_FREE in code:
                                    logger.info(f"\tFirst N Days Promotion Code: {code}")
                                    print(f"\tFirst N Days Promotion Code: {code}")
                                    PromotionCode = code

                        if 'Access Key' in mbo_c['name']:
                            for code in ENROLMENT_DATA_ENTITY['couponCode'].split(','):
                                if 'Key' in code:
                                    logger.info(f"\tAccess Key Promotion Code: {code}")
                                    print(f"\tAccess Key Promotion Code: {code}")
                                    PromotionCode = code

                        if 'Packs' in mbo_c['name']:
                            for code in ENROLMENT_DATA_ENTITY['couponCode'].split(','):
                                if Constants.MBO_COUPON_PT_PACK_FREE in code or Constants.MBO_COUPON_PT_PACK_DISCOUNT_10 in code:
                                    logger.info(f"\tPTP Promotion Code: {code}")
                                    print(f"\tPTP Promotion Code: {code}")
                                    PromotionCode = code
                                    is_free_contract = True
                        
                        if 'Gym' in mbo_c['name'] or 'Play' in mbo_c['name'] or 'Perform' in mbo_c['name']:
                            for code in ENROLMENT_DATA_ENTITY['couponCode'].split(','):
                                if Constants.MBO_COUPON_CODE_MEMBERSHIP_DISCOUNT_2 in code:
                                    logger.info(f"\tMembership Discount Code: {code}")
                                    print(f"\tMembership Discount Code: {code}")
                                    PromotionCode = code

                    # This is outside of promotions
                    if 'First' in mbo_c['name']:
                        is_free_contract = True

                    if 'Access Key' in mbo_c['name']:
                        is_access_key_contract = True
                    
                    # if 'Gym' in mbo_c['name'] or 'Play' in mbo_c['name'] or 'Perform' in mbo_c['name']:
                    #     pass

                    if 'PT:' in mbo_c['name']:
                        is_coaching_contract = True

                    if 'Virtual Playground' in mbo_c['name']:
                        trigger_virtual_playground = True

                    if 'External' in mbo_c['name'] and 'Access' not in mbo_c['name']:
                        is_external_pt_contract = True

            if is_external_pt_contract:
                logger.info("External PT External Manual Submission")
                print("External PT External Manual Submission")
                handle_manual_submission(MemberStatus.EXTERNAL_PT, ENROLMENT_DATA_ENTITY, None)
                ENROLMENT_DATA_ENTITY['status'] = MemberStatus.EXTERNAL_PT[0]
                IS_MBO_SUCCESS = False

            # EXECUTE PURCHASE CONTRACTS
            else:
                # logger.info(f"\n\nEXECUTE PURCHASE CONTRACTS DATES\n\is_external_pt_contract: {is_external_pt_contract}\n\tis_free_contract: {is_free_contract}\n\tis_coaching_contract: {is_coaching_contract}\n\tis_access_key_contract: {is_access_key_contract}\n\tstartDate: {pd.to_datetime(ENROLMENT_DATA_ENTITY['startDate']).strftime(Constants.MBO_DATETIME_FORMAT)}\n\tfirstBillingDate: {pd.to_datetime(ENROLMENT_DATA_ENTITY['firstBillingDate']).strftime(Constants.MBO_DATETIME_FORMAT)}\n\tNow: {datetime.datetime.now().strftime(Constants.MBO_DATETIME_FORMAT)}\n\tPT StartDate: {pd.to_datetime(ENROLMENT_DATA_ENTITY['personalTrainingStartDate']).strftime(Constants.MBO_DATETIME_FORMAT)}")

                try:
                    # This is been handled separately
                    # if is_access_key_contract == True and ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'] is not None and ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'].lower().replace(' ','') == 'paytoday':
                    #     StartDate = datetime.datetime.now().strftime(Constants.MBO_DATETIME_FORMAT)
                    #     logger.info(f"\n\nStartDate Variable: is_access_key_contract")
                    #     print(f"\n\nStartDate Variable: is_access_key_contract")

                    if is_coaching_contract == True:
                        StartDate = pd.to_datetime(ENROLMENT_DATA_ENTITY['personalTrainingStartDate']).strftime(Constants.MBO_DATETIME_FORMAT)

                    elif trigger_virtual_playground and ENROLMENT_DATA_ENTITY['virtualPlaygroundCommencement'] is not None and ENROLMENT_DATA_ENTITY['virtualPlaygroundCommencement'].lower().replace(' ','') == 'starttoday':
                        StartDate = datetime.datetime.now().strftime(Constants.MBO_DATETIME_FORMAT)
                        logger.info(f"\n\nStartDate Variable: trigger_virtual_playground")
                        print(f"\n\nStartDate Variable: trigger_virtual_playground")

                    elif is_free_contract == True:
                        StartDate = pd.to_datetime(ENROLMENT_DATA_ENTITY['startDate']).strftime(Constants.MBO_DATETIME_FORMAT)
                        logger.info(f"\n\nStartDate Variable: is_free_contract")
                        print(f"\n\nStartDate Variable: is_free_contract")

                    else:
                        StartDate = pd.to_datetime(ENROLMENT_DATA_ENTITY['firstBillingDate']).strftime(Constants.MBO_DATETIME_FORMAT)
                        logger.info(f"\n\nStartDate Variable: 'Else'")
                        print(f"\n\nStartDate Variable: 'Else'")

                except Exception as ex:
                    logger.error(f"Error - StartDate: {ex}")
                    print(f"Error - StartDate: {ex}")
                    StartDate = datetime.datetime.now().strftime(Constants.MBO_DATETIME_FORMAT)

                try:
                    if pd.to_datetime(StartDate).strftime('%y-%m-%d') == datetime.datetime.now().strftime('%y-%m-%d'):
                        FirstPaymentOccurs = "Instant"
                    else:
                        FirstPaymentOccurs = "StartDate"

                except Exception as ex:
                    logger.error(f"Error - FirstPaymentOccurs: {ex}")
                    print(f"Error - FirstPaymentOccurs: {ex}")
                    FirstPaymentOccurs = "StartDate"


                logger.info(f"\n\tStartDate: {StartDate}\tFirstPaymentOccurs: {FirstPaymentOccurs}")
                print(f"\n\tStartDate: {StartDate}\tFirstPaymentOccurs: {FirstPaymentOccurs}")


                PURCHASE_CONTRACT = {
                    "Test": Constants.IS_TEST,
                    "LocationId": ENROLMENT_DATA_ENTITY['locationId'],
                    "ClientId": MBO_CLIENT['Id'],
                    "ContractId": c,
                    "StartDate": StartDate,
                    "FirstPaymentOccurs" : FirstPaymentOccurs,
                    "PromotionCode": PromotionCode,
                    "SalesRepId": SalesRepId,
                    "SendNotifications": False
                }

                # New Bank Details
                if ENROLMENT_DATA_ENTITY['memberBankDetail'] is not None:
                    PURCHASE_CONTRACT["UseDirectDebit"] = True

                # Existing Bank Details
                elif ENROLMENT_DATA_ENTITY['paymentType'] is not None and ('existingbankaccount' in ENROLMENT_DATA_ENTITY['paymentType'].replace(' ','').lower() or 'existingdirectdebit' in ENROLMENT_DATA_ENTITY['paymentType'].replace(' ','').lower()):
                    logger.info("MBO Purchase Contract: Use Existing Bank Account")
                    print("MBO Purchase Contract: Use Existing Bank Account")
                    PURCHASE_CONTRACT["UseDirectDebit"] = True
                    use_existing_payment_details = True
                else:
                    PURCHASE_CONTRACT["UseDirectDebit"] = False

                # New Credit Card
                if ENROLMENT_DATA_ENTITY['memberCreditCard'] is not None and MBO_CLIENT["ClientCreditCard"] is not None:
                    PURCHASE_CONTRACT["StoredCardInfo"] = { "LastFour": MBO_CLIENT['ClientCreditCard']['LastFour'] }
                
                # Existing Credit Card
                elif MBO_CLIENT is not None and ENROLMENT_DATA_ENTITY['paymentType'] is not None and 'existingcreditcard' in ENROLMENT_DATA_ENTITY['paymentType'].replace(' ','').lower():
                    logger.info(f"MBO Purchase Contract: Use Existing Credit Card")
                    print(f"MBO Purchase Contract: Use Existing Credit Card")
                    PURCHASE_CONTRACT["StoredCardInfo"] = { "LastFour": MBO_CLIENT['ClientCreditCard']['LastFour'] }
                    use_existing_payment_details = True

# ####################################################################### TESTING CODE
                if Constants.IS_TEST:
                    if ENROLMENT_DATA_ENTITY['memberCreditCard'] is not None and MBO_CLIENT["ClientCreditCard"] is not None:
                        PURCHASE_CONTRACT["StoredCardInfo"] = { "LastFour": '1111' }
                    elif MBO_CLIENT is not None and ENROLMENT_DATA_ENTITY['paymentType'] is not None and 'existingcreditcard' in ENROLMENT_DATA_ENTITY['paymentType'].replace(' ','').lower():
                        PURCHASE_CONTRACT["StoredCardInfo"] = { "LastFour": '1111' }

                    logger.info(f"\n\nPURCHASE_CONTRACT: {PURCHASE_CONTRACT}")
                    print(f"\n\nPURCHASE_CONTRACT: {PURCHASE_CONTRACT}")
# ####################################################################### TESTING CODE

                if ENROLMENT_DATA_ENTITY['memberBankDetail'] is None and ENROLMENT_DATA_ENTITY['memberCreditCard'] is None and use_existing_payment_details == False:
                    logger.info("Handle Cash or EFPOS Manual Submission")
                    print("Handle Cash or EFPOS Manual Submission")

                    handle_manual_submission(MemberStatus.IN_CLUB_PAYMENT, ENROLMENT_DATA_ENTITY, None)
                    ENROLMENT_DATA_ENTITY['status'] = MemberStatus.IN_CLUB_PAYMENT[0]
                    IS_MBO_SUCCESS = False
                    
                else:
                    response = mbo_post_request(Constants.MBO_PURCHASE_CONTRACT_URL, PURCHASE_CONTRACT)
                    
                    if Constants.IS_TEST:
                        logger.info(f"MBO Purchase Contract Response: {response}\n")
                        print(f"MBO Purchase Contract Response: {response}\n")

                    if response is None:
                        logger.error(f'Error purchasing contact {c}\n{json.dumps(PURCHASE_CONTRACT)}')
                        print(f'Error purchasing contact {c}\n{json.dumps(PURCHASE_CONTRACT)}')

                        handle_manual_submission(MemberStatus.PURCHASE_CONTRACT_ERROR, ENROLMENT_DATA_ENTITY, None)
                        ENROLMENT_DATA_ENTITY['status'] = MemberStatus.PURCHASE_CONTRACT_ERROR[0]
                        IS_MBO_SUCCESS = False

    if ENROLMENT_DATA_ENTITY['dollarValue'] is None:
        ENROLMENT_DATA_ENTITY['dollarValue'] = 0

    ENROLMENT_DATA_ENTITY['dollarValue'] += round(dollar_value,2)

    return {
        'IS_MBO_SUCCESS' : IS_MBO_SUCCESS,
        'ENROLMENT_DATA_ENTITY' : ENROLMENT_DATA_ENTITY
    }




# ######################################################################
# PURCHASE CONTRACTS                                                   #
# @Params: FP_COACH_ENTITY                                             #
# @Params: EXISTING_CREDIT_CARD                                        #
# @Returns: Dictionary                                                 #
# ######################################################################
def coach_purchase_contracts(FP_COACH_ENTITY, MBO_CLIENT):

    IS_MBO_SUCCESS = True
    # Get MBO Contracts from Source Database
    mbo_contracts = source_get_request(Constants.SOURCE_GET_ALL_CONTRACTS_URL + str(FP_COACH_ENTITY['locationId']))
    dollar_value = 0

    # FOR TESTING ONLY
    if Constants.IS_TEST:
        FP_COACH_ENTITY['startDate'] = datetime.datetime.now().strftime(Constants.DATETIME_FORMAT)

    logger.info(FP_COACH_ENTITY['mboContractIds'])
    print(FP_COACH_ENTITY['mboContractIds'])

    for c in FP_COACH_ENTITY['mboContractIds'].split(','):
        print(c)
        logger.info(c)

        is_manual_submission = False
        is_free_contract = False
        PromotionCode = None
        # Get Contract
        for mbo_c in mbo_contracts:
            if int(c) == int(mbo_c['mboId']):
                dollar_value += float(mbo_c['numberOfAutoPays']) + float(mbo_c['reoccuringPaymentAmountTotal'])
                print(f"\t{mbo_c['name']}")
                logger.info(f"\t{mbo_c['name']}")

                # Promo Code
                if FP_COACH_ENTITY['couponCode'] is not None:
                    if 'Packs' in mbo_c['name']:
                        for code in FP_COACH_ENTITY['couponCode'].split(','):
                            if Constants.MBO_COUPON_PT_PACK_FREE in code or Constants.MBO_COUPON_PT_PACK_DISCOUNT_10 in code:
                                print(f"\tPTP Promotion Code: {code}")
                                logger.info(f"\tPTP Promotion Code: {code}")
                                PromotionCode = code
                                is_free_contract = True
        # Set Sales Rep
        SalesRepId = None
        try:
            if FP_COACH_ENTITY['organisedByCoach']:
                SalesRepId = { "Id": int(FP_COACH_ENTITY['personalTrainer'].split('::')[0]), "SalesRepNumber": 2 }
            else:
                SalesRepId = { "Id": int(FP_COACH_ENTITY['staffMember'].split('::')[0]), "SalesRepNumber": 1 }
        except Exception as ex:
            print(f"Error - SalesRepId {ex}")
            logger.error(f"Error - SalesRepId {ex}")

        print(f"\tSalesRepId: {SalesRepId}")
        logger.info(f"SalesRepId: {SalesRepId}")

        # Handle External PT
        if FP_COACH_ENTITY['externalClient']:
            print("\n\nExternal PT External Manual Submission")
            logger.info("External PT External Manual Submission")
            handle_manual_submission(MemberStatus.EXTERNAL_PT, None, FP_COACH_ENTITY)
            FP_COACH_ENTITY['status'] = MemberStatus.EXTERNAL_PT[0]

        # ######################################################################
        # EXECUTE PURCHASE CONTRACTS                                           #
        # ######################################################################
        else:
            try:
                if pd.to_datetime(FP_COACH_ENTITY['startDate']).strftime('%Y-%m-%d') == datetime.datetime.now().strftime('%Y-%m-%d'):
                    FirstPaymentOccurs = "Instant"
                else:
                    FirstPaymentOccurs = "StartDate"
            except Exception as ex:
                print(f"Error - FirstPaymentOccurs: {ex}")
                logger.error(f"Error - FirstPaymentOccurs: {ex}")
                FirstPaymentOccurs = "StartDate"

            try:
                StartDate = pd.to_datetime(FP_COACH_ENTITY['startDate']).strftime(Constants.MBO_DATETIME_FORMAT)
            except Exception as ex:
                print(f"Error - StartDate: {ex}")
                logger.error(f"Error - StartDate: {ex}")
                StartDate = datetime.datetime.now().strftime(Constants.MBO_DATETIME_FORMAT)

            PURCHASE_CONTRACT = {
                "Test": Constants.IS_TEST,
                "LocationId": FP_COACH_ENTITY['locationId'],
                "ClientId": MBO_CLIENT['Id'],
                "ContractId": c,
                "StartDate": StartDate,
                "FirstPaymentOccurs" : FirstPaymentOccurs,
                "PromotionCode": PromotionCode,
                "SalesRepId": SalesRepId,
                "SendNotifications": False
            }

            # ######################################################################
            # USE BANK DETAILS                                                     #
            # ######################################################################
            if FP_COACH_ENTITY['paymentType'] == Constants.FS_PAYMENT_TYPE_BANK_ACCOUNT or FP_COACH_ENTITY['paymentType'] == Constants.FS_PAYMENT_TYPE_DIRECT_DEBIT:
                PURCHASE_CONTRACT["UseDirectDebit"] = True
            # Existing Bank Details
            elif ('existingbankaccount' in FP_COACH_ENTITY['paymentType'].replace(' ','').lower() or 'existingdirectdebit' in FP_COACH_ENTITY['paymentType'].replace(' ','').lower()):
                print("\n\tUse Existing Bank Account")
                logger.info("Use Existing Bank Account")

                PURCHASE_CONTRACT["UseDirectDebit"] = True
                use_existing_payment_details = True
            else:
                PURCHASE_CONTRACT["UseDirectDebit"] = False

            # New Credit Card
            if FP_COACH_ENTITY['paymentType'] == Constants.FS_PAYMENT_TYPE_CREDIT_CARD and FP_COACH_ENTITY['memberCreditCard'] is not None:
                CreditCardInfo = {
                    "BillingName": FP_COACH_ENTITY["memberCreditCard"]["holder"],
                    "CreditCardNumber": FP_COACH_ENTITY["memberCreditCard"]["number"],
                    "ExpMonth": FP_COACH_ENTITY["memberCreditCard"]["expMonth"],
                    "ExpYear": FP_COACH_ENTITY["memberCreditCard"]["expYear"],
                    "SaveInfo": False
                }
                PURCHASE_CONTRACT["CreditCardInfo"] = CreditCardInfo

            # Existing Credit Card
            if MBO_CLIENT is not None and FP_COACH_ENTITY['paymentType'] is not None and 'existingcreditcard' in FP_COACH_ENTITY['paymentType'].replace(' ','').lower():
                print(f"\n\tUse Existing Credit Card")
                logger.info(f"Use Existing Credit Card")

                PURCHASE_CONTRACT["StoredCardInfo"] = { "LastFour": MBO_CLIENT['ClientCreditCard']['LastFour'] }
                use_existing_payment_details = True

            if Constants.IS_TEST:
                logger.info(PURCHASE_CONTRACT)

            response = mbo_post_request(Constants.MBO_PURCHASE_CONTRACT_URL, PURCHASE_CONTRACT)
            logger.info(f"{response}\n")
            print(f"{response}\n")

            if response is None:
                print(f'Error purchasing contact {c}\n{json.dumps(PURCHASE_CONTRACT)}')
                loger.error(f'Error purchasing contact {c}\n{json.dumps(PURCHASE_CONTRACT)}')
                handle_manual_submission(MemberStatus.PURCHASE_CONTRACT_ERROR, None, FP_COACH_ENTITY)
                FP_COACH_ENTITY['status'] = MemberStatus.PURCHASE_CONTRACT_ERROR[0]
                IS_MBO_SUCCESS = False

    return {
        'FP_COACH_ENTITY' : FP_COACH_ENTITY,
        'IS_MBO_SUCCESS' : IS_MBO_SUCCESS
    }



# ######################################################################
# PURCHASE SERVICES                                                    #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Params: MBO_CLIENT                                                  #
# @Returns: Dictionary                                                 #
# ######################################################################
def purchase_services(ENROLMENT_DATA_ENTITY, MBO_CLIENT):

    dollar_value = 0
    IS_MBO_SUCCESS = True
    is_valid_checkout = True

    if ENROLMENT_DATA_ENTITY['serviceNamesToBeActivated'] is not None:
        logger.info("Purchase Services")
        print("\n\tPurchase Services")

        # Get MBO Services from Source Database
        mbo_services = source_get_request(Constants.SOURCE_GET_ALL_SERVICES_URL + ENROLMENT_DATA_ENTITY['locationId'])

        MBO_CHECKOUT_SHOPPING_CART = {
          "ClientId": MBO_CLIENT['Id'],
          "Test": Constants.IS_TEST,
          "InStore": True,
          "SendEmail": False,
          "LocationId": ENROLMENT_DATA_ENTITY['locationId']
        }

        Items = []
        Payments = []
        services = ENROLMENT_DATA_ENTITY['serviceNamesToBeActivated'].split(' | ')
        Amount = 0
        
        for service in services:

            service_details = service.split(' # ')

            # mboId, name, price, quantity
            logger.info(service_details)
            print(service_details)

            for mbo_s in mbo_services:
                if int(mbo_s['mboId']) == int(service_details[0]):

                    if Constants.IS_TEST:
                        logger.info(f"Service: {mbo_s}")
                        print(f"Service: {mbo_s}")

                    if float(mbo_s['price']) == float(service_details[2]):
                        Item = {
                            "Item": {
                            "Type": "Service",
                            "Metadata": {
                                "Id" : mbo_s['mboId']
                                }
                            },
                            "Quantity": service_details[3]
                        }
                        Items.append(Item)
                        Amount += float(service_details[2]) * float(service_details[3])
                    else:
                        logger.error('Invalid Checkout Service Manual Submission')
                        print('Error - Invalid Checkout Service Manual Submission')
                        is_valid_checkout = False

                        if ENROLMENT_DATA_ENTITY['externalPt'] == True:
                            handle_manual_submission(MemberStatus.EXTERNAL_PT, ENROLMENT_DATA_ENTITY, None)
                            ENROLMENT_DATA_ENTITY['status'] = MemberStatus.EXTERNAL_PT[0]
                        else:
                            handle_manual_submission(MemberStatus.PURCHASE_SERVICE_ERROR, ENROLMENT_DATA_ENTITY, None)
                            ENROLMENT_DATA_ENTITY['status'] = MemberStatus.PURCHASE_SERVICE_ERROR[0]

                        IS_MBO_SUCCESS = False

        dollar_value += Amount
        if is_valid_checkout:
            payment_type = ENROLMENT_DATA_ENTITY['paymentType'].replace(' ','').lower()

            if 'creditcard' in payment_type:
                Payment = {
                  "Type": "StoredCard",
                  "Metadata": {
                    "Amount": Amount,
                    "LastFour": MBO_CLIENT['ClientCreditCard']['LastFour']
                  }
                }

            if 'bankaccount' in payment_type or 'directdebit' in payment_type:
                Payment = {
                  "Type": "DirectDebit",
                  "Metadata": {
                    "Amount": Amount,
                  }
                }

            if payment_type == 'cash':
                Payment = {
                  "Type": "Cash",
                  "Metadata": {
                    "Amount": Amount,
                  }
                }

            if payment_type == 'efpos':
                Payment = {
                  "Type": "Custom",
                  "Metadata": {
                    "Id": 17,
                    "Amount": Amount
                  }
                }

            Payments.append(Payment)

            MBO_CHECKOUT_SHOPPING_CART['Items'] = Items
            MBO_CHECKOUT_SHOPPING_CART['Payments'] = Payments

            # EXCUTE PURCHASE SERVICES
            response = mbo_post_request(Constants.MBO_CHECKOUT_SHOPPING_CART_URL, MBO_CHECKOUT_SHOPPING_CART)
            
            if Constants.IS_TEST:
                logger.info(f"\n\nMBO_CHECKOUT_SHOPPING_CART: {MBO_CHECKOUT_SHOPPING_CART}\tresponse: {response}\n")
                print(f"\n\nMBO_CHECKOUT_SHOPPING_CART: {MBO_CHECKOUT_SHOPPING_CART}\tresponse: {response}\n")

            if response is None:
                logger.error(f'Error purchasing service \n{json.dumps(MBO_CHECKOUT_SHOPPING_CART)}')
                print(f'Error purchasing service \n{json.dumps(MBO_CHECKOUT_SHOPPING_CART)}')

                handle_manual_submission(MemberStatus.PURCHASE_SERVICE_ERROR, ENROLMENT_DATA_ENTITY, None)
                ENROLMENT_DATA_ENTITY['status'] = MemberStatus.PURCHASE_SERVICE_ERROR[0]
                IS_MBO_SUCCESS = False

    if ENROLMENT_DATA_ENTITY['dollarValue'] is None:
        ENROLMENT_DATA_ENTITY['dollarValue'] = 0

    ENROLMENT_DATA_ENTITY['dollarValue'] += round(dollar_value,2)

    return {
        'IS_MBO_SUCCESS' : IS_MBO_SUCCESS,
        'ENROLMENT_DATA_ENTITY' : ENROLMENT_DATA_ENTITY,
        'is_valid_checkout' : is_valid_checkout
    }



# ######################################################################
# PURCHASE ACCESS KEY PAY TODAY                                        #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Params: MBO_CLIENT                                                  #
# @Returns: Dictionary                                                 #
# ######################################################################
def purchase_access_key_pay_today(ENROLMENT_DATA_ENTITY, MBO_CLIENT):

    IS_MBO_SUCCESS = True
    Items = []
    Payments = []
    Payment = {}
    Amount = 0
    PromotionCode = None

    print("Access Key Pay Today")
    logger.info("Access Key Pay Today")
    mbo_products = source_get_request(Constants.SOURCE_GET_ALL_PRODUCTS_URL + '1')

    for mbo_p in mbo_products:
        if mbo_p['name'] == 'Access Key':
            if Constants.IS_TEST:
        #             logger.info(f"Product: {mbo_p}")
                print(f"Product: {mbo_p}")

    #         Items
            Item = {
                "Item": {
                "Type": "Product",
                "Metadata": {
                    "Id" : mbo_p['mboId']
                    }
                },
                "Quantity": 1
            }
            Items.append(Item)
            
            if ENROLMENT_DATA_ENTITY['couponCode'] is not None:
                for code in ENROLMENT_DATA_ENTITY['couponCode'].split(','):
                    if 'Key' in code:
                        logger.info(f"\tAccess Key Promotion Code: {code}")
                        print(f"\tAccess Key Promotion Code: {code}")
                        PromotionCode = code
                        if PromotionCode == 'KeyFree':
                            Amount = 0.0
                            print("Free Access Key Pay Today - aborting checkout")
                            logger.info("Free Access Key Pay Today - aborting checkout")
                            return {
                                'IS_MBO_SUCCESS' : IS_MBO_SUCCESS,
                                'ENROLMENT_DATA_ENTITY' : ENROLMENT_DATA_ENTITY
                            }
                        else:
                            Amount = float(PromotionCode[3:])
            else:
                Amount = float(mbo_p['price'])

    #         Payments
            if ENROLMENT_DATA_ENTITY['accessKeyPaymentMethod'] is not None:
                if ENROLMENT_DATA_ENTITY['accessKeyPaymentMethod'].lower() == 'cash':
                    Payment = {
                      "Type": "Cash",
                      "Metadata": {
                        "Amount": Amount,
                      }
                    }
                elif ENROLMENT_DATA_ENTITY['accessKeyPaymentMethod'].lower() == 'efpos':
                    Payment = {
                      "Type": "Custom",
                      "Metadata": {
                        "Id": 17,
                        "Amount": Amount
                      }
                    }
                else:
                    payment_type = ENROLMENT_DATA_ENTITY['paymentType'].replace(' ','').lower()
                    if 'creditcard' in payment_type:
                        Payment = {
                          "Type": "StoredCard",
                          "Metadata": {
                            "Amount": Amount,
                            "LastFour": MBO_CLIENT['ClientCreditCard']['LastFour']
                          }
                        }
                    if 'bankaccount' in payment_type or 'directdebit' in payment_type:
                        Payment = {
                          "Type": "DirectDebit",
                          "Metadata": {
                            "Amount": Amount,
                          }
                        }
    #         Catch All
            else:
                Payment = {
                  "Type": "Cash",
                  "Metadata": {
                    "Amount": Amount,
                  }
                }
            
            Payments.append(Payment)

            MBO_CHECKOUT_SHOPPING_CART = {
                    "ClientId": MBO_CLIENT['Id'],
                    "Test": Constants.IS_TEST,
                    "InStore": True,
                    "SendEmail": False,
                    "LocationId": ENROLMENT_DATA_ENTITY['locationId'],
                    "PromotionCode": PromotionCode,
                    "ConsumerPresent": True,
                    "Payments": Payments,
                    "Items": Items
                }

            # EXCUTE PURCHASE SERVICES
            response = mbo_post_request(Constants.MBO_CHECKOUT_SHOPPING_CART_URL, MBO_CHECKOUT_SHOPPING_CART)
            
            if Constants.IS_TEST:
                logger.info(f"\n\nMBO_CHECKOUT_SHOPPING_CART: {MBO_CHECKOUT_SHOPPING_CART}\tresponse: {response}\n")
                print(f"\n\nMBO_CHECKOUT_SHOPPING_CART: {MBO_CHECKOUT_SHOPPING_CART}\tresponse: {response}\n")
            
            if response is None:
                logger.error(f'Error purchasing service \n{json.dumps(MBO_CHECKOUT_SHOPPING_CART)}')
                print(f'Error purchasing service \n{json.dumps(MBO_CHECKOUT_SHOPPING_CART)}')

                handle_manual_submission(MemberStatus.PURCHASE_SERVICE_ERROR, ENROLMENT_DATA_ENTITY, None)
                ENROLMENT_DATA_ENTITY['status'] = MemberStatus.PURCHASE_SERVICE_ERROR[0]

            return {
                'IS_MBO_SUCCESS' : IS_MBO_SUCCESS,
                'ENROLMENT_DATA_ENTITY' : ENROLMENT_DATA_ENTITY
            }



# ######################################################################
# SAVE REFERRALS                                                       #
# @Params: FORMSTACK SUBMISSION                                        #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# ######################################################################
def save_referrals(e, ENROLMENT_DATA_ENTITY):

    logger.info('Inside save_referrals')
    
#     Set Ids
    _ids = FS_FIELD_IDS.FORM[e["FormID"]]
    REFERRAL_DATA_ENTITY = {}

    try:
        REFERRAL_DATA_ENTITY['name'] = e[_ids['REFERRAL_NAME']]
        REFERRAL_DATA_ENTITY['email'] = e[_ids['REFERRAL_EMAIL']]
        REFERRAL_DATA_ENTITY['phone'] = e[_ids['REFERRAL_PHONE']]
        REFERRAL_DATA_ENTITY['status'] = MemberStatus.PENDING[0]
        REFERRAL_DATA_ENTITY['enrolmentDataId'] = ENROLMENT_DATA_ENTITY['id']

        source_post_request(Constants.SOURCE_REFERRAL_URL, REFERRAL_DATA_ENTITY)
        logger.info(f"Referral One: {REFERRAL_DATA_ENTITY}")
        print(f"Referral One: {REFERRAL_DATA_ENTITY}")

    except Exception as ex:
        logger.error(f"Error - Saving Referral 1: {ex}")
        print(f"Error - Saving Referral 1: {ex}")
    
    try:
        if e[_ids["HAS_REFERRAL_2"]] is not None and e[_ids["HAS_REFERRAL_2"]][0].lower() == 'yes':
            REFERRAL_DATA_ENTITY['name'] = e[_ids['REFERRAL_NAME_2']]
            REFERRAL_DATA_ENTITY['email'] = e[_ids['REFERRAL_EMAIL_2']]
            REFERRAL_DATA_ENTITY['phone'] = e[_ids['REFERRAL_PHONE_2']]
            REFERRAL_DATA_ENTITY['status'] = MemberStatus.PENDING[0]
            REFERRAL_DATA_ENTITY['enrolmentDataId'] = ENROLMENT_DATA_ENTITY['id']

            source_post_request(Constants.SOURCE_REFERRAL_URL, REFERRAL_DATA_ENTITY)
            logger.info(f"Referral Two: {REFERRAL_DATA_ENTITY}")
            print(f"Referral Two: {REFERRAL_DATA_ENTITY}")

    except Exception as ex:
        logger.error(f"Error - Saving Referral 2: {ex}")
        print(f"Error - Saving Referral 2: {ex}")
    
    
    try:
        if e[_ids["HAS_REFERRAL_3"]] is not None and e[_ids["HAS_REFERRAL_3"]][0].lower() == 'yes':
            REFERRAL_DATA_ENTITY['name'] = e[_ids['REFERRAL_NAME_3']]
            REFERRAL_DATA_ENTITY['email'] = e[_ids['REFERRAL_EMAIL_3']]
            REFERRAL_DATA_ENTITY['phone'] = e[_ids['REFERRAL_PHONE_3']]
            REFERRAL_DATA_ENTITY['status'] = MemberStatus.PENDING[0]
            REFERRAL_DATA_ENTITY['enrolmentDataId'] = ENROLMENT_DATA_ENTITY['id']

            source_post_request(Constants.SOURCE_REFERRAL_URL, REFERRAL_DATA_ENTITY)
            logger.info(f"Referral Three: {REFERRAL_DATA_ENTITY}")
            print(f"Referral Three: {REFERRAL_DATA_ENTITY}")

    except Exception as ex:
        logger.error(f"Error - Saving Referral 3: {ex}")
        print(f"Error - Saving Referral 3: {ex}")
    
    return    



# ######################################################################
# HANDLE REFERRALS -> Push to GymSales                                 #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Returns: Dictionary                                                 #
# ######################################################################
def handle_referrals(ENROLMENT_DATA_ENTITY):
    
    response = None
    try:

        REFERRALS = source_get_request(Constants.SOURCE_REFERRAL_URL + '/' + str(ENROLMENT_DATA_ENTITY['id']))

        for REFERRAL in REFERRALS:

            PARAMS = {
                'company_id' : get_gym_sales_company_id_from_mbo_location_id(ENROLMENT_DATA_ENTITY['locationId']),
                'email' : REFERRAL['email']
            }

            response = gymsales_get_request(Constants.GS_SEARCH_PEOPLE_URL, PARAMS)
            logger.info(f"Search GymSales response: {response}")
            print(f"Search GymSales response: {response}")

            if response is not None:

                PARAMS = {
                    'company_id': response['company_id'],
                    'company_external_id' : response['company_external_id'],
                    'first_name' : response['first_name'],
                    'last_name' : response['last_name'],
                    'address' : response['address'],
                    'city' : response['city'],
                    'state' : response['state'],
                    'zip' : response['zip'],
                    'phone_home' : response['phone_home'],
                    'phone_mobile' : response['phone_mobile'],
                    'phone_work' : response['phone_work'],
                    'phone_home_formatted' : response['phone_home_formatted'],
                    'phone_mobile_formatted' : response['phone_mobile_formatted'],
                    'phone_work_formatted' : response['phone_work_formatted'],
                    'email' : response['email'],
                    'contact_method_name' : response['contact_method_name'],
                    'source_name': Constants.GS_LEAD_SOURCE_POS,
                    'tag_list' : response['tag_list'],
                    'avatar' : response['avatar'],
                    'social_link' : response['social_link'],
                    'trial_end_at' : response['trial_end_at'],
                    'trial_days_quantity' : response['trial_days_quantity'],
                    'gender' : response['gender'],
                    'birthday' : response['birthday'],
                    'sms_opted_out' : response['sms_opted_out'],
                    'email_opted_out' : response['email_opted_out']
                }

                if response['status'] != 'enquiry':
                    PARAMS['status'] = 'enquiry'

                URL = f"""{Constants.GS_PEOPLE_URL}/{response['id']}"""
                
                logger.info(f"Gymsales Update Lead - response: {response}")
                print(f"Gymsales Update Lead - response: {response}")
                logger.info(f"Gymsales Update Lead - URL: {URL}")
                print(f"Gymsales Update Lead - URL: {URL}")

                response = gymsales_put_request(URL, PARAMS)

            else:
                firstName = ''
                lastName = ''

                name = REFERRAL['name'].split(' ')
                firstName = name[0]
                if len(name) > 1:
                    for i in range(1,len(name)):
                        lastName = lastName + name[i] + ' '
                    lastName = lastName[:-1]

                logger.info(f"Referral: {firstName} {lastName} {ENROLMENT_DATA_ENTITY['locationId']}")
                print(f"Referral: {firstName} {lastName} {ENROLMENT_DATA_ENTITY['locationId']}")

                PARAMS = {
                    'company_id' : get_gym_sales_company_id_from_mbo_location_id(ENROLMENT_DATA_ENTITY['locationId']),
                    'first_name': firstName, # REQUIRED
                    'last_name': lastName,
                    'phone_mobile' : REFERRAL['phone'],
                    'email' : REFERRAL['email'],
                    'source_name' : Constants.GS_LEAD_SOURCE_POS, # Lead Origin
                    'contact_method_name' : 'Referral', # Enumerated Values: Referrals, Internet, Walk In, Guest Register, Phone
                    'referred_by_name' : ENROLMENT_DATA_ENTITY['firstName'] + ' ' + ENROLMENT_DATA_ENTITY['lastName'],
                    'referred_by_phone' : ENROLMENT_DATA_ENTITY['phone'],
                    'referred_by_email' : ENROLMENT_DATA_ENTITY['email'],
                    'notes' : f"""Referral sourced by {ENROLMENT_DATA_ENTITY['staffName']}"""
                }
                
                logger.info(f"Gymsales Add Lead - PARAMS: {PARAMS}\n")
                print(f"Gymsales Add Lead - PARAMS: {PARAMS}\n")
                
                response = gymsales_post_request(Constants.GS_PEOPLE_URL, PARAMS)

                logger.info(f"Gymsales Add Lead- response: {response}\n")
                print(f"Gymsales Add Lead- response: {response}\n")

            REFERRAL['enrolmentDataId'] = ENROLMENT_DATA_ENTITY['id']
            REFERRAL['status'] = MemberStatus.COMPLETE[0]
            source_put_request(Constants.SOURCE_REFERRAL_URL, REFERRAL)

    except Exception as ex:
        logger.error(f"Error - Adding referral to GymSales: {ex}")
        print(f"Error - Adding referral to GymSales: {ex}")
        handle_manual_submission(MemberStatus.GYM_SALES_ERROR, ENROLMENT_DATA_ENTITY, None)
        response = None

    finally:
        return response



# ######################################################################
# UPDATE GYMSALES.                                                     #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Returns: Dictionary                                                 #
# ######################################################################
def update_gymsales(ENROLMENT_DATA_ENTITY):

    try:
        PARAMS = {
            'company_id' : get_gym_sales_company_id_from_mbo_location_id(ENROLMENT_DATA_ENTITY['locationId']),
            'email' : ENROLMENT_DATA_ENTITY['email']
        }
        
        logger.info(f"UPDATE GYMSALES - PARAMS: {PARAMS}")
        print(f"UPDATE GYMSALES - PARAMS: {PARAMS}")

        response = gymsales_get_request(Constants.GS_SEARCH_PEOPLE_URL, PARAMS)
        
        logger.info(f"UPDATE GYMSALES - GS_SEARCH_PEOPLE_URL response: {response}")
        print(f"UPDATE GYMSALES - GS_SEARCH_PEOPLE_URL response: {response}")

        if response is not None:
            ENROLMENT_DATA_ENTITY['gymSalesId'] = str(response['id'])

            try:
                response['status'] = 'Sale'

                if ENROLMENT_DATA_ENTITY['ddOrPif'] == 'dd':
                    response['membership_type'] = 'direct_debit'

                if ENROLMENT_DATA_ENTITY['ddOrPif'] == 'pif':
                    response['membership_type'] = 'paid_in_full'

                if ENROLMENT_DATA_ENTITY['noCommitment'] == '12Month':
                    response['membership_term'] = '12_months'

                if ENROLMENT_DATA_ENTITY['noCommitment'] == 'noCommitment':
                    response['membership_term'] = 'no_contract'

                response['membership_amount'] = ENROLMENT_DATA_ENTITY['dollarValue']
                
                response = gymsales_post_request(Constants.GS_PEOPLE_URL, response)

                logger.info(f"UPDATE GYMSALES - GS_PEOPLE_URL response: {response}")
                print(f"UPDATE GYMSALES - GS_PEOPLE_URL response: {response}")

            except Exception as ex:
                logger.error(f"Error - Updating Gym Sales: {ex}")

        else:
            ENROLMENT_DATA_ENTITY['gymSalesId'] = None

    except Exception as ex:
        logger.error(f"Error - Updating GymSales status to Sale: {ex}")

    finally:
        return ENROLMENT_DATA_ENTITY



# ######################################################################
# TRIGGER PDF WRITER - Push EnrolmentData onto SQS Queue               #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Params: MBO_CLIENT                                                  #
# @Returns: Dictionary                                                 #
# ######################################################################
def trigger_pdf_writer(ENROLMENT_DATA_ENTITY, MBO_CLIENT, PT_TRACKER_ENTITY, TYPE):

    try:
        
        FLAG = True
        DATA = copy.deepcopy(ENROLMENT_DATA_ENTITY)           
        if DATA['paymentType'] == Constants.FS_PAYMENT_TYPE_BANK_ACCOUNT:
            try:
                DATA['memberBankDetail']['accountNumber'] = DATA['memberBankDetail']['accountNumber'][-4:]
            except Exception as ex:
                logger.error(f"Error - cleaning bank details for PDF: {ex}")
                print(f"Error - cleaning bank details for PDF: {ex}")
                FLAG = False

        if DATA['paymentType'] == Constants.FS_PAYMENT_TYPE_CREDIT_CARD:
            try: 
                DATA['memberCreditCard']['number'] = DATA['memberCreditCard']['number'][-4:]
                DATA['memberCreditCard']['verificationCode'] = '***'
            except Exception as ex:
                logger.error(f"Error - cleaning credit card details for PDF: {ex}")
                print(f"Error - cleaning credit card details for PDF: {ex}")
                FLAG = False

        if FLAG:
            BODY = {
                "type" : TYPE,
                "data" : DATA,
                "mbo_client" : MBO_CLIENT,
                "pt_tracker" : PT_TRACKER_ENTITY,
                "email_credentials" : Constants.MEMBERSHIPS_EMAIL_CREDENTIALS,
                "mbo_credentials" : Constants.MBO_HEADERS,
                "gym_details" : source_get_request(Constants.SOURCE_GYM_DETAILS_URL + '/' + str(ENROLMENT_DATA_ENTITY['locationId']))
            }  

            if Constants.IS_TEST:
                logger.info(f"TRIGGER PDF BODY\n{json.dumps(BODY)}")
                print(f"TRIGGER PDF BODY\n{json.dumps(BODY)}")

            # response = SQS_CLIENT.send_message(
            #     QueueUrl=os.getenv('PdfSqsUrl'),
            #     MessageBody=json.dumps(BODY),
            #     MessageGroupId=f"Enrolment:{ENROLMENT_DATA_ENTITY['id']}:{MBO_CLIENT['UniqueId']}",
            #     MessageDeduplicationId=f"Enrolment:{ENROLMENT_DATA_ENTITY['id']}:{MBO_CLIENT['UniqueId']}",
            # )

            # logger.info(f"SQS Response: {response}")
            # print(f"SQS Response: {response}")

            MESSAGE_GROUP_ID = f"Enrolment:{ENROLMENT_DATA_ENTITY['id']}:{MBO_CLIENT['UniqueId']}"
            MESSAGE_DEDUPLICATION_ID = f"Enrolment:{ENROLMENT_DATA_ENTITY['id']}:{MBO_CLIENT['UniqueId']}"

            execute_pdf_writer(BODY, MESSAGE_GROUP_ID, MESSAGE_DEDUPLICATION_ID)


    except Exception as ex:
        logger.error(f"Error - Queueing Enrolment PDF: {ex}")
        print(f"Error - Queueing Enrolment PDF: {ex}")
        ENROLMENT_DATA_ENTITY['communicationsStatus'] = CommunicationsStatus.PDF_EMAIL_ERROR[0]

    finally:
        return ENROLMENT_DATA_ENTITY




# ######################################################################
# EXECUTE PDF WRITER - Push onto SQS Queue                             #
# @Params: SQS BODY                                                    #
# @Params: MESSAGE_GROUP_ID                                            #
# @Params: MESSAGE_DEDUPLICATION_ID                                    #
# @Returns: AWS response                                               #
# ######################################################################
def execute_pdf_writer(BODY, MESSAGE_GROUP_ID, MESSAGE_DEDUPLICATION_ID):

    SQS_CLIENT = boto3.client('sqs')

    response = SQS_CLIENT.send_message(
        QueueUrl=os.getenv('PdfSqsUrl'),
        MessageBody=json.dumps(BODY),
        MessageGroupId=MESSAGE_GROUP_ID,
        MessageDeduplicationId=MESSAGE_DEDUPLICATION_ID
    )

    logger.info(f"SQS Response: {response}")
    print(f"SQS Response: {response}")
    return response




# ######################################################################
# UPLOAD MEMBER PHOTO.                                                 #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Params: MBO_CLIENT                                                  #
# @Returns: Dictionary                                                 #
# ######################################################################
def upload_member_photo(ENROLMENT_DATA_ENTITY, MBO_CLIENT):
    
    response = None

    os.environ["PATH"] = f"""{os.environ["PATH"]}:{os.environ.get("LAMBDA_TASK_ROOT")}/bin"""

    try:
        if ENROLMENT_DATA_ENTITY['memberPhotoURL'] is not None and ENROLMENT_DATA_ENTITY['memberPhotoURL'] != '':

            url = ENROLMENT_DATA_ENTITY['memberPhotoURL']
            split = url.split(".")

            FILE_TYPE = split[len(split) - 1]
            if FILE_TYPE == 'jpg':
                FILE_TYPE = 'jpeg'

            filepath = f'/tmp/image.{FILE_TYPE}'

            urllib.request.urlretrieve(ENROLMENT_DATA_ENTITY['memberPhotoURL'], filepath)
            encoded_string = None

            with open(filepath, "rb") as imageFile:
                encoded_string = base64.b64encode(imageFile.read()).decode("utf-8")
            
            if encoded_string is not None:
                BODY = {
                    "ClientId" : MBO_CLIENT['Id'],
                    "Bytes" : encoded_string
                }

                response = mbo_post_request(Constants.MBO_UPLOAD_CLIENT_IMAGE_URL, BODY)

            os.remove(filepath)
    except Exception as ex:
        logger.error(f"Error - Uploading Member Photo: {ex}")
        print(f"Error - Uploading Member Photo: {ex}")

    finally:
        return response

    # filepath = '/tmp/image.png'

    # try:
    #     if ENROLMENT_DATA_ENTITY['memberPhotoURL'] is not None and ENROLMENT_DATA_ENTITY['memberPhotoURL'] != '':
    #         urllib.request.urlretrieve(ENROLMENT_DATA_ENTITY['memberPhotoURL'], filepath)
    #         encoded_string = None

    #         with open(filepath, "rb") as imageFile:
    #             encoded_string = base64.b64encode(imageFile.read()).decode("utf-8")
            
    #         if encoded_string is not None:
    #             BODY = {
    #                 "ClientId" : MBO_CLIENT['Id'],
    #                 "Bytes" : encoded_string
    #             }

    #             response = mbo_post_request(Constants.MBO_UPLOAD_CLIENT_IMAGE_URL, BODY)

    #         os.remove(filepath)
    # except Exception as ex:
    #     logger.error(f"Error - Uploading Member Photo: {ex}")
    #     print(f"Error - Uploading Member Photo: {ex}")

    # finally:
    #     return response


        
# ######################################################################
# UPLOAD COVID VERIFICATION                                            #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Params: MBO_CLIENT                                                  #
# @Returns: Dictionary                                                 #
# ######################################################################
def upload_covid_verification(ENROLMENT_DATA_ENTITY, MBO_CLIENT):

    response = None

    try:
        # Changed this so that all enrolments have the index updated
        # if ENROLMENT_DATA_ENTITY['covidVerificationURL'] is not None and ENROLMENT_DATA_ENTITY['covidVerificationURL'] != '':

        os.environ["PATH"] = f"""{os.environ["PATH"]}:{os.environ.get("LAMBDA_TASK_ROOT")}/bin"""

        url = ENROLMENT_DATA_ENTITY['covidVerificationURL']
        split = url.split(".")
        if split[len(split) - 1] == "pdf":
            MediaType = "application/pdf"
        else:
            FILE_TYPE = split[len(split) - 1]

            if FILE_TYPE == 'jpg':
                FILE_TYPE = 'jpeg'

        MediaType = f"image/{FILE_TYPE}"
        FILEPATH = "/tmp/Covid Passport.{}".format(split[len(split) - 1])
        KEY = f"Covid Passport {datetime.datetime.now().strftime('%Y-%m-%d')}"

        r = requests.get(url, stream=True)

        chunk_size = 2000
        with open(FILEPATH, 'wb') as fd:
            for chunk in r.iter_content(chunk_size):
                fd.write(chunk)

        with open(FILEPATH, "rb") as file:
            encoded_string = base64.b64encode(file.read())

        UPLOAD_CLIENT_DOCUMENT_URL = "https://api.mindbodyonline.com/public/v6/client/uploadclientdocument"

        REQUEST_BODY = {
            'ClientId': MBO_CLIENT['Id'],
            "File": {
                'FileName': KEY,
                'MediaType': MediaType,
                'Buffer': encoded_string.decode("utf-8") 
            }
        }

        try:
    
            r = requests.post(url = UPLOAD_CLIENT_DOCUMENT_URL, data = json.dumps(REQUEST_BODY), headers=Constants.MBO_HEADERS )
            # print(r.json())
            r.raise_for_status()

        except HTTPError as http_err:
            logger.error(f'HTTP error: {http_err} - {r.json()}')
            print(f'HTTP error: {http_err} - {r.json()}')

        except Exception as err:
            logger.error(f'Error updating client visit: {err}')
            print(f'Error updating client visit: {err}')

        else:
            print(r)
            logger.info(r)
            event = r.json()
            j = json.dumps(event)
            upload_client_document_data = json.loads(j)

            print(f'successfully uploaded COVID Verification Document: {upload_client_document_data}')
            logger.info(f'successfully uploaded COVID Verification Document: {upload_client_document_data}')
        
        # Clean Up
        try:
            os.remove(FILEPATH)
        except Exception as ex:
            logger.error(f"Error - Cleaning COVID Verification FILEPATH: {ex}")
            print(f"Error - Cleaning COVID Verification FILEPATH: {ex}")

        try:
            ValueId = 45
            UPDATE_CLIENT = {}
            UPDATE_CLIENT['Id'] = MBO_CLIENT['Id']
            UPDATE_CLIENT['ClientIndexes'] = [{
                'Id' : 10,
                "ValueId" : ValueId
            }]

            UPDATE_CLIENT_BODY = {
                "Client" : UPDATE_CLIENT, 
                "CrossRegionalUpdate": False
            }

            response = mbo_post_request(Constants.MBO_UPDATE_CLIENT_URL, UPDATE_CLIENT_BODY)

        except Exception as ex:
            logger.error(f"Error - Updating COVID Verification Client Index: {ex}")
            print(f"Error - Updating COVID Verification Client Index: {ex}")


    except Exception as ex:
        logger.error(f"Error - Handling COVID Verification: {ex}")
        print(f"Error - Handling COVID Verification: {ex}")
    finally:
        return response



# ######################################################################
# TRIGGER COMMUNICATIONS                                               #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# @Params: entity_type                                                 #
# @Params: source_id                                                   #
# @Returns: Dictionary                                                 #
# ######################################################################
def trigger_communications(ENROLMENT_DATA_ENTITY, entity_type, source_id):

    try:
        URL = 'source/triggerCommunications/'+ entity_type +'/'+ source_id
        source_patch_request(URL)
    except Exception as ex:
        logger.error(f"Error - triggering communications: {ex}")
        print(f"Error - triggering communications: {ex}")
        ENROLMENT_DATA_ENTITY['communicationsStatus'] = CommunicationsStatus.EMAIL_CAMPAIGN_ERROR[0]
    finally:
        return ENROLMENT_DATA_ENTITY



# ######################################################################
# GET MBO CLIENT                                                       #
# @Params: FP_COACH_ENTITY                                             #
# @Returns: Dictionary                                                 #
# ######################################################################
def get_mbo_client(FP_COACH_ENTITY):

    PARAMS = { "SearchText" : FP_COACH_ENTITY['email'] }
    print(f"GET MBO CLIENT: {PARAMS}")
    logger.info(f"GET MBO CLIENT: {PARAMS}")

    response = mbo_get_request(Constants.MBO_GET_CLIENTS_URL, PARAMS)

    MBO_CLIENT = None
    EXISTING_CREDIT_CARD = None
    EXISTING_BANK_DETIALS = None
    use_existing_payment_details = False
    if response['PaginationResponse']['TotalResults'] == 0:
        print("\tClient doesn't exist in MBO")
        logger.error("Client doesn't exist in MBO")
        handle_manual_submission(MemberStatus.CLIENT_NOT_FOUND, None, FP_COACH_ENTITY)
        IS_MBO_SUCCESS = False
    
    else:
        mbo_clients = response['Clients']

        for c in mbo_clients:
            print(f"firstName: {c['FirstName']} | lastName: {c['LastName']}")
            logger.info(f"firstName: {c['FirstName']} | lastName: {c['LastName']}")

            if c['FirstName'].lower().strip() == FP_COACH_ENTITY['firstName'].lower().strip() and c['LastName'].lower().strip() == FP_COACH_ENTITY['lastName'].lower().strip() and c['Email'].lower().strip() == FP_COACH_ENTITY['email'].lower().strip():
                
                print('\tFOUND MBO CLIENT')
                logger.info('FOUND MBO CLIENT\n')

                MBO_CLIENT = c
                EXISTING_CREDIT_CARD = MBO_CLIENT['ClientCreditCard']
                IS_MBO_SUCCESS = True

        if MBO_CLIENT is None:
            print("\n\nHandle Submission Name does not match Name in MBO Manual Submission")
            logger.error("Handle Submission Name does not match Name in MBO Manual Submission")
            handle_manual_submission(MemberStatus.CLIENT_NOT_FOUND, None, FP_COACH_ENTITY)
            IS_MBO_SUCCESS = False

    return {
        'FP_COACH_ENTITY' : FP_COACH_ENTITY,
        'MBO_CLIENT' : MBO_CLIENT,
        'EXISTING_CREDIT_CARD' : EXISTING_CREDIT_CARD,
        'EXISTING_BANK_DETIALS' : EXISTING_BANK_DETIALS,
        'IS_MBO_SUCCESS' : IS_MBO_SUCCESS
    }


# ######################################################################
# SEND CONTACT LOGS                                                    #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# ######################################################################
def send_contact_logs(BODY):
    print(f"MBO CONTACT LOGS: {BODY}")
    logger.info(f"MBO CONTACT LOGS: {BODY}")
    
    mbo_post_request(Constants.MBO_ADD_CONTACT_LOG_URL, BODY)


# ######################################################################
# HANDLE PT TRACKER                                                    #
# @Params: ENROLMENT_DATA_ENTITY                                       #
# ######################################################################
def handlePtTracker(ENROLMENT_DATA_ENTITY, MBO_CLIENT):
    print(f"HANDLE PT TRACKER")
    logger.info(f"HANDLE PT TRACKER")

    try:

        if 'PT:' not in ENROLMENT_DATA_ENTITY['contractNamesToBeActivated'] and 'Coach Pack' not in ENROLMENT_DATA_ENTITY['contractNamesToBeActivated']  and 'PT Pack' not in ENROLMENT_DATA_ENTITY['contractNamesToBeActivated']:
            print(f"Not a PT Contract Abort - ENROLMENT_DATA_ENTITY['id']: {ENROLMENT_DATA_ENTITY['id']}")
            logger.info(f"Not a PT Contract Abort - ENROLMENT_DATA_ENTITY['id']: {ENROLMENT_DATA_ENTITY['id']}")
            return None

        SEARCH_ENTITY = {
            "firstName" : ENROLMENT_DATA_ENTITY['firstName'],
            "lastName" : ENROLMENT_DATA_ENTITY['lastName'],
            "phone" : ENROLMENT_DATA_ENTITY['phone'],
            "email" : ENROLMENT_DATA_ENTITY['email']
        }

        PRE_EX_DATA_ENTITY = source_post_request(Constants.SOURCE_SEARCH_PRE_EX_URL, SEARCH_ENTITY)

        PT_TRACKER_ENTITY = {}

        PT_TRACKER_ENTITY['enrolmentDataId'] = ENROLMENT_DATA_ENTITY['id']
        PT_TRACKER_ENTITY['fpCoachId'] = None
        if PRE_EX_DATA_ENTITY is not None:
            PT_TRACKER_ENTITY['preExId'] = PRE_EX_DATA_ENTITY['id']

        PT_TRACKER_ENTITY['parqId'] = None
        PT_TRACKER_ENTITY['cancellationDataId'] = None
        PT_TRACKER_ENTITY['locationId'] = str(ENROLMENT_DATA_ENTITY['locationId'])
        PT_TRACKER_ENTITY['feedbackIds'] = None
        PT_TRACKER_ENTITY['status'] = MemberStatus.PAR_Q_PENDING[0]
        PT_TRACKER_ENTITY['communicationsStatus'] = CommunicationsStatus.EMAIL_CAMPAIGN_PENDING[0]
        if MBO_CLIENT['UniqueId'] is not None:
            PT_TRACKER_ENTITY['mboUniqueId'] = str(MBO_CLIENT['UniqueId'])

        PT_TRACKER_ENTITY['createDate'] = ENROLMENT_DATA_ENTITY['createDate']
        PT_TRACKER_ENTITY['updateDate'] = ENROLMENT_DATA_ENTITY['createDate']
        PT_TRACKER_ENTITY['communicationsUpdateDate'] = ENROLMENT_DATA_ENTITY['createDate']
        PT_TRACKER_ENTITY['staffMember'] = ENROLMENT_DATA_ENTITY['staffMember']
        PT_TRACKER_ENTITY['personalTrainer'] = ENROLMENT_DATA_ENTITY['personalTrainer']
        PT_TRACKER_ENTITY['isReassigned'] = False
        PT_TRACKER_ENTITY['sessionCount'] = 0
        PT_TRACKER_ENTITY['hasFirstSessionBooked'] = False

        PT_TRACKER_ENTITY = source_post_request(Constants.SOURCE_SAVE_PT_TRACKER_URL, PT_TRACKER_ENTITY)

        if Constants.IS_TEST:
            print(f"PtTracker: {PT_TRACKER_ENTITY}")
            logger.info(f"PtTracker: {PT_TRACKER_ENTITY}")


        if PRE_EX_DATA_ENTITY is not None:
            print(f"Uploading Pre-ex Document from handlePtTracker()")
            logger.info(f"Uploading Pre-ex Document from handlePtTracker()")

            BODY = {
                "type" : "preExData",
                "data" : PRE_EX_DATA_ENTITY,
                "mbo_client" : MBO_CLIENT,
                "pt_tracker" : PT_TRACKER_ENTITY,
                "email_credentials" : Constants.MEMBERSHIPS_EMAIL_CREDENTIALS,
                "mbo_credentials" : Constants.MBO_HEADERS,
                "gym_details" : source_get_request(Constants.SOURCE_GYM_DETAILS_URL + '/' + str(ENROLMENT_DATA_ENTITY['locationId']))
            }  

            MESSAGE_GROUP_ID = f"PreEx:{PRE_EX_DATA_ENTITY['id']}:{MBO_CLIENT['UniqueId']}"
            MESSAGE_DEDUPLICATION_ID = f"PreEx:{PRE_EX_DATA_ENTITY['id']}:{MBO_CLIENT['UniqueId']}"

            execute_pdf_writer(BODY, MESSAGE_GROUP_ID, MESSAGE_DEDUPLICATION_ID)

        return PT_TRACKER_ENTITY

    except Exception as ex:
        print(f"Error - HANDLE PT TRACKER {ex}")
        logger.error(f"Error - HANDLE PT TRACKER {ex}")
    


