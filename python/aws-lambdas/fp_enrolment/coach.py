import logging as logger
import pandas as pd
import hashlib
import hmac
import base64
import json
import requests
from requests.auth import HTTPBasicAuth
from requests.exceptions import HTTPError
import datetime
import calendar
import time
import urllib.parse
import pytz
import math

# from fp_enrolment.constants import *
# from fp_enrolment.helpers import *
# from fp_enrolment.source_api import *
from constants import *
from helpers import *
from source_api import *


# FP COACH ENROLMENT
def build_fp_coach_enrolment_data(e):

    logger.info("build_fp_coach_enrolment_data")
    print("build_fp_coach_enrolment_data")
    
    _ids = FS_FIELD_IDS.FORM[e["FormID"]]
    FP_COACH_ENROLMENT_ENTITY = {}
    
#     Formstack Ids
    FP_COACH_ENROLMENT_ENTITY['fs_formId'] = e['FormID']
    FP_COACH_ENROLMENT_ENTITY['fs_uniqueId'] = e['UniqueID']

    try:
        if type(e[_ids["HOME_LOCATION"]]) == list:
            locationId = get_location_id(e[_ids["HOME_LOCATION"]][0]['value'])
        else:
            locationId = get_location_id(e[_ids["HOME_LOCATION"]])
    except Exception as ex:
        print(f'Error - locationId: {ex}')
        logger.error(f'Error - locationId: {ex}')
        locationId = None
    
    print(f"locationId: {locationId}")
    logger.info(f"locationId: {locationId}")

    FP_COACH_ENROLMENT_ENTITY['locationId'] = locationId
    
#     Gym Name
    try:
        if type(e[_ids['HOME_LOCATION']]) == list:
            FP_COACH_ENROLMENT_ENTITY['gymName'] = e[_ids['HOME_LOCATION']][0]['label']
        else:
            FP_COACH_ENROLMENT_ENTITY['gymName'] = e[_ids['HOME_LOCATION']]
    except Exception as ex:
        print(f'Error - HOME_LOCATION: {ex}')
        logger.error(f'Error - HOME_LOCATION: {ex}')
    
#     Set Status
    FP_COACH_ENROLMENT_ENTITY['status'] = MemberStatus.SAVED[0]
    FP_COACH_ENROLMENT_ENTITY['communicationsStatus'] = CommunicationsStatus.EMAIL_CAMPAIGN_PENDING[0]

    #     Metadata
    FP_COACH_ENROLMENT_ENTITY['createDate'] = e[_ids['SUBMISSION_DATETIME']]
    FP_COACH_ENROLMENT_ENTITY['updateDate'] = e[_ids['SUBMISSION_DATETIME']]
    
#     Get MBO Contracts from Source Database
    mbo_contracts = source_get_request(Constants.SOURCE_GET_ALL_CONTRACTS_URL + locationId)
    
# ######################################################
# PROCESS PURCHASES                                    #
# ######################################################
    contractNamesToBeActivated = ''
    memberContracts = ''
    couponCode = ''
        
    try:
        FP_COACH_ENROLMENT_ENTITY['coachingModality'] = e[_ids['COACHING_MODALITY']]
    except Exception as ex:
        print(f'Error - COACHING_MODALITY: {ex}')
        logger.error(f'Error - COACHING_MODALITY: {ex}')

    try:
        FP_COACH_ENROLMENT_ENTITY['sessionLength'] = e[_ids['SESSION_LENGTH']]
    except Exception as ex:
        print(f'Error - SESSION_LENGTH: {ex}')
        logger.error(f'Error - SESSION_LENGTH: {ex}')

    FP_COACH_ENROLMENT_ENTITY['trainingOptions'] = None
    
    try:
        FP_COACH_ENROLMENT_ENTITY['fnDD'] = e[_ids['COACHING_FORTNIGHTLY_DD']]
    except Exception as ex:
        print(f'Error - COACHING_FORTNIGHTLY_DD: {ex}')
        logger.error(f'Error - COACHING_FORTNIGHTLY_DD: {ex}')

    try:
        FP_COACH_ENROLMENT_ENTITY['mtDD'] = e[_ids['MONTHLY_DD']]
    except Exception as ex:
        print(f'Error - MONTHLY_DD: {ex}')
        logger.error(f'Error - MONTHLY_DD: {ex}')

    try:    
        if (_ids['2_FREE'] is not None and e[_ids['2_FREE']] is not None and len(e[_ids['2_FREE']]) > 0):
            for c in mbo_contracts:
                if c['name'].strip() == 'Packs: FP Coach Pack':
                    print(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    logger.info(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    memberContracts = memberContracts + str(c['mboId']) + ','
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # 0.0 | "
                    couponCode = couponCode + Constants.MBO_COUPON_PT_PACK_FREE + ','
    except Exception as ex:
        print(f'Error - 2_FREE: {ex}')
        logger.error(f'Error - 2_FREE: {ex}')

#     Training Package
    FP_COACH_ENROLMENT_ENTITY['externalClient'] = False
    try:
        is_extrenal_pt = False
        training_package = None
        if _ids['TRAINING_PACKAGE_30_MIN'] is not None and e[_ids['TRAINING_PACKAGE_30_MIN']] is not None and e[_ids['TRAINING_PACKAGE_30_MIN']] != '':
            print(e[_ids['TRAINING_PACKAGE_30_MIN']])
            logger.info(e[_ids['TRAINING_PACKAGE_30_MIN']])
            training_package = e[_ids['TRAINING_PACKAGE_30_MIN']][0]['label']

        elif _ids['TRAINING_PACKAGE_40_MIN'] is not None and e[_ids['TRAINING_PACKAGE_40_MIN']] is not None and e[_ids['TRAINING_PACKAGE_40_MIN']] != '':
            print(e[_ids['TRAINING_PACKAGE_40_MIN']])
            logger.info(e[_ids['TRAINING_PACKAGE_40_MIN']])
            training_package = e[_ids['TRAINING_PACKAGE_40_MIN']][0]['label']

        elif _ids['TRAINING_PACKAGE_60_MIN'] is not None and e[_ids['TRAINING_PACKAGE_60_MIN']] is not None and e[_ids['TRAINING_PACKAGE_60_MIN']] != '':
            print(e[_ids['TRAINING_PACKAGE_60_MIN']])
            logger.info(e[_ids['TRAINING_PACKAGE_60_MIN']])
            training_package = e[_ids['TRAINING_PACKAGE_60_MIN']][0]['label']

        elif _ids['TRAINING_PACKAGE_30_MIN_BK'] is not None and e[_ids['TRAINING_PACKAGE_30_MIN_BK']] is not None and e[_ids['TRAINING_PACKAGE_30_MIN_BK']] != '':
            print(e[_ids['TRAINING_PACKAGE_30_MIN_BK']])
            logger.info(e[_ids['TRAINING_PACKAGE_30_MIN_BK']])
            training_package = e[_ids['TRAINING_PACKAGE_30_MIN_BK']][0]['label']

        elif _ids['TRAINING_PACKAGE_40_MIN_BK'] is not None and e[_ids['TRAINING_PACKAGE_40_MIN_BK']] is not None and e[_ids['TRAINING_PACKAGE_40_MIN_BK']] != '':
            print(e[_ids['TRAINING_PACKAGE_40_MIN_BK']])
            logger.info(e[_ids['TRAINING_PACKAGE_40_MIN_BK']])
            training_package = e[_ids['TRAINING_PACKAGE_40_MIN_BK']][0]['label']

        elif _ids['TRAINING_PACKAGE_60_MIN_BK'] is not None and e[_ids['TRAINING_PACKAGE_60_MIN_BK']] is not None and e[_ids['TRAINING_PACKAGE_60_MIN_BK']] != '':
            print(e[_ids['TRAINING_PACKAGE_60_MIN_BK']])
            logger.info(e[_ids['TRAINING_PACKAGE_60_MIN_BK']])
            training_package = e[_ids['TRAINING_PACKAGE_60_MIN_BK']][0]['label']

        elif _ids['TRAINING_PACKAGE_LIFESTYLE'] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE']] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE']] != '':
            print(e[_ids['TRAINING_PACKAGE_LIFESTYLE']])
            logger.info(e[_ids['TRAINING_PACKAGE_LIFESTYLE']])
            training_package = e[_ids['TRAINING_PACKAGE_LIFESTYLE']][0]['label']

        elif _ids['TRAINING_PACKAGE_LIFESTYLE_BK'] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']] != '':
            print(e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']])
            logger.info(e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']])
            training_package = e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']][0]['label']

        elif _ids['TRAINING_PACKAGE_VIRTUAL_FITNESS'] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']] != '':
            print(e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']])
            logger.info(e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']])
            training_package = e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']][0]['label']

        elif _ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS'] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']] != '':
            print(e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']])
            logger.info(e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']])
            training_package = e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']][0]['label']

        elif _ids['TRAINING_PACKAGE_WELLNESS'] is not None and e[_ids['TRAINING_PACKAGE_WELLNESS']] is not None and e[_ids['TRAINING_PACKAGE_WELLNESS']] != '':
            print(e[_ids['TRAINING_PACKAGE_WELLNESS']])
            logger.info(e[_ids['TRAINING_PACKAGE_WELLNESS']])
            training_package = e[_ids['TRAINING_PACKAGE_WELLNESS']][0]['label']
        
        elif _ids['TRAINING_PACKAGE_PROGRAMMING'] is not None and e[_ids['TRAINING_PACKAGE_PROGRAMMING']] is not None and e[_ids['TRAINING_PACKAGE_PROGRAMMING']] != '':
            print(e[_ids['TRAINING_PACKAGE_PROGRAMMING']])
            logger.info(e[_ids['TRAINING_PACKAGE_PROGRAMMING']])
            training_package = e[_ids['TRAINING_PACKAGE_PROGRAMMING']][0]['label']

    #         External PT
        elif _ids['COACHING_MODALITY'] is not None and e[_ids['COACHING_MODALITY']] is not None and e[_ids['COACHING_MODALITY']] == 'External Personal Training':
            print("EXTERNAL PT")
            logger.info("EXTERNAL PT")
            is_extrenal_pt = True
            if int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2 < 1:
                external_pt_contract_name = f"PT: External 1x {e[_ids['SESSION_LENGTH']].split(' ')[0]} Min Session Per Fortnight"
                # external_pt_contract_name = "PT: External 1x 40 Min Session Per Fortnight"
            
            else:
                external_pt_contract_name = 'PT: External ' + str(math.floor(int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2)) + 'x ' + e[_ids['SESSION_LENGTH']].split(' ')[0] + ' Min Session Per Week'
                # external_pt_contract_name = 'PT: External ' + str(math.floor(int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2)) + 'x 40 Min Session Per Week'

            print(external_pt_contract_name)
            logger.info(external_pt_contract_name)
            
            for c in mbo_contracts:
                if c['name'].strip() == external_pt_contract_name:
                    print(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    logger.info(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")

                    memberContracts = memberContracts + str(c['mboId']) + ','
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{external_pt_contract_name} # {float(e[_ids['PRICES_PER_SESSION_EXTERNAL']]) * int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value'])}/fortnight | "

            FP_COACH_ENROLMENT_ENTITY['externalPTSessionPrice'] = e[_ids['PRICES_PER_SESSION_EXTERNAL']]
            FP_COACH_ENROLMENT_ENTITY['numberSessioinsPerWeek'] = math.floor(int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2)
            FP_COACH_ENROLMENT_ENTITY['externalClient'] = True

        else:
            print("I'm a Fitness Guru")
            logger.info("I'm a Fitness Guru")

        if is_extrenal_pt == False:
            for c in mbo_contracts:
                if c['name'].strip() == training_package:
                    print(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    logger.info(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    memberContracts = memberContracts + str(c['mboId']) + ','
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {c['reoccuringPaymentAmountTotal']}{get_payment_frequency_str(c)} | "
    except Exception as ex:
        print(f'Error - TRAINING_PACKAGE: {ex}')
        logger.error(f'Error - TRAINING_PACKAGE: {ex}')

    print(contractNamesToBeActivated)
    logger.info(contractNamesToBeActivated)
    print(memberContracts)
    logger.info(memberContracts)
    print(couponCode)
    logger.info(couponCode)
                       

    FP_COACH_ENROLMENT_ENTITY['mboContractNames'] = contractNamesToBeActivated[:-3]
    FP_COACH_ENROLMENT_ENTITY['mboContractIds'] = memberContracts[:-1]
    FP_COACH_ENROLMENT_ENTITY['couponCode'] = couponCode[:-1]

#     Start and Billing Dats
    try:
        FP_COACH_ENROLMENT_ENTITY['startDate'] = pd.to_datetime(e[_ids['COACHING_START_DATE']]).strftime('%Y-%m-%d')
    except Exception as ex:
        print(f'Error - START_DATE: {ex}')
        logger.error(f'Error - START_DATE: {ex}')
        FP_COACH_ENROLMENT_ENTITY['startDate'] = datetime.datetime.now().strftime('%Y-%m-%d')
                          
#     Staff Name
    try:
        membership_consultant = None
        if _ids['MEMBERSHIP_CONSULTANT_MK'] is not None and e[_ids['MEMBERSHIP_CONSULTANT_MK']] is not None and e[_ids['MEMBERSHIP_CONSULTANT_MK']] != '':
            membership_consultant_name = e[_ids['MEMBERSHIP_CONSULTANT_MK']][0]['label']
            membership_consultant_mbo_id = e[_ids['MEMBERSHIP_CONSULTANT_MK']][0]['value']
        elif _ids['MEMBERSHIP_CONSULTANT_NT'] is not None and e[_ids['MEMBERSHIP_CONSULTANT_NT']] is not None and e[_ids['MEMBERSHIP_CONSULTANT_NT']] != '':
            membership_consultant_name = e[_ids['MEMBERSHIP_CONSULTANT_NT']][0]['label']
            membership_consultant_mbo_id = e[_ids['MEMBERSHIP_CONSULTANT_NT']][0]['value']
        elif _ids['MEMBERSHIP_CONSULTANT_SH'] is not None and e[_ids['MEMBERSHIP_CONSULTANT_SH']] is not None and e[_ids['MEMBERSHIP_CONSULTANT_SH']] != '':
            membership_consultant_name = e[_ids['MEMBERSHIP_CONSULTANT_SH']][0]['label']
            membership_consultant_mbo_id = e[_ids['MEMBERSHIP_CONSULTANT_SH']][0]['value']
        elif _ids['MEMBERSHIP_CONSULTANT_BK'] is not None and e[_ids['MEMBERSHIP_CONSULTANT_BK']] is not None and e[_ids['MEMBERSHIP_CONSULTANT_BK']] != '':
            membership_consultant_name = e[_ids['MEMBERSHIP_CONSULTANT_BK']][0]['label']
            membership_consultant_mbo_id = e[_ids['MEMBERSHIP_CONSULTANT_BK']][0]['value']
        elif _ids['MEMBERSHIP_CONSULTANT'] is not None and e[_ids['MEMBERSHIP_CONSULTANT']] is not None and e[_ids['MEMBERSHIP_CONSULTANT']] != '':
            membership_consultant_name = e[_ids['MEMBERSHIP_CONSULTANT']]
            membership_consultant_mbo_id = e[_ids['MEMBERSHIP_CONSULTANT_MBO_ID']]

        print(f"{membership_consultant_name} {membership_consultant_mbo_id}")
        logger.info(f"{membership_consultant_name} {membership_consultant_mbo_id}")

        FP_COACH_ENROLMENT_ENTITY['staffMember'] = membership_consultant_mbo_id
        FP_COACH_ENROLMENT_ENTITY['staffName'] = membership_consultant_name
        
    except Exception as ex:
        print(f'Error - MEMBERSHIP_CONSULTANT_NAME: {ex}')
        logger.error(f'Error - MEMBERSHIP_CONSULTANT_NAME: {ex}')
    
    try:
        FP_COACH_ENROLMENT_ENTITY['organisedByCoach'] = e[_ids['ORGANISED_BY_COACH']].lower() == 'yes'
    except Exception as ex:
        print(f'Error - ORGANISED_BY_COACH: {ex}')
        logger.error(f'Error - ORGANISED_BY_COACH: {ex}')
        FP_COACH_ENROLMENT_ENTITY['organisedByCoach'] = False

#     Coach Name
    try:
        coach = None
        if _ids['COACH_NAME_MK'] is not None and e[_ids['COACH_NAME_MK']] is not None and e[_ids['COACH_NAME_MK']] != '':
            coach_name = e[_ids['COACH_NAME_MK']][0]['label']
            coach_mbo_id = e[_ids['COACH_NAME_MK']][0]['value']
        elif _ids['COACH_NAME_NT'] is not None and e[_ids['COACH_NAME_NT']] is not None and e[_ids['COACH_NAME_NT']] != '':
            coach_name = e[_ids['COACH_NAME_NT']][0]['label']
            coach_mbo_id = e[_ids['COACH_NAME_NT']][0]['value']
        elif _ids['COACH_NAME_SH'] is not None and e[_ids['COACH_NAME_SH']] is not None and e[_ids['COACH_NAME_SH']] != '':
            coach_name = e[_ids['COACH_NAME_SH']][0]['label']
            coach_mbo_id = e[_ids['COACH_NAME_SH']][0]['value']
        elif _ids['COACH_NAME_BK'] is not None and e[_ids['COACH_NAME_BK']] is not None and e[_ids['COACH_NAME_BK']] != '':
            coach_name = e[_ids['COACH_NAME_BK']][0]['label']
            coach_mbo_id = e[_ids['COACH_NAME_BK']][0]['value']
        elif _ids['COACH_NAME'] is not None and e[_ids['COACH_NAME']] is not None and e[_ids['COACH_NAME']] != '':
            coach_name = e[_ids['COACH_NAME']]
            coach_mbo_id = e[_ids['COACH_MBO_ID']]
        print(f"{coach_name} {coach_mbo_id}")
        logger.info(f"{coach_name} {coach_mbo_id}")

        FP_COACH_ENROLMENT_ENTITY['personalTrainer'] = coach_mbo_id
        FP_COACH_ENROLMENT_ENTITY['personalTrainerName'] = coach_name
        
        if FP_COACH_ENROLMENT_ENTITY['organisedByCoach']:
            FP_COACH_ENROLMENT_ENTITY['staffMember'] = coach_mbo_id
            FP_COACH_ENROLMENT_ENTITY['staffName'] = coach_name
        
    except Exception as ex:
        print(f'Error - COACH_NAME: {ex}')
        logger.error(f'Error - COACH_NAME: {ex}')

#    Primary Details
    try:
        FP_COACH_ENROLMENT_ENTITY['phone'] = format_phone(e[_ids['PHONE']])
    except Exception as ex:
        print(f'Error - PHONE: {ex}')
        logger.error(f'Error - PHONE: {ex}')
         
    try:
        FP_COACH_ENROLMENT_ENTITY['firstName'] = e[_ids['NAME']]['first'].strip().title()
    except Exception as ex:
        print(f'Error - NAME FIRST: {ex}')
        logger.error(f'Error - NAME FIRST: {ex}')
                     
    try:
        FP_COACH_ENROLMENT_ENTITY['lastName'] = e[_ids['NAME']]['last'].strip().title()
    except Exception as ex:
        print(f'Error - NAME LAST: {ex}')
        logger.error(f'Error - NAME LAST: {ex}')

    try:
        FP_COACH_ENROLMENT_ENTITY['email'] = e[_ids['EMAIL']].strip()
    except Exception as ex:
        print(f'Error - EMAIL: {ex}')
        logger.error(f'Error - EMAIL: {ex}')

#     Payment Method
    try:
        if e[_ids["PAYMENT_METHOD"]] is not None and 'useexistingdetails' in e[_ids["PAYMENT_METHOD"]].replace(' ','').lower():
            FP_COACH_ENROLMENT_ENTITY['paymentType'] = 'Existing ' + e[_ids["EXISTING_PAYMENT_METHOD"]]
            FP_COACH_ENROLMENT_ENTITY['useExistingDetails'] = 'Yes'
        else:
            FP_COACH_ENROLMENT_ENTITY['paymentType'] = e[_ids["PAYMENT_METHOD"]]
            FP_COACH_ENROLMENT_ENTITY['useExistingDetails'] = 'No'
    except Exception as ex:
        print(f'Error - PAYMENT_METHOD: {ex}')
        logger.error(f'Error - PAYMENT_METHOD: {ex}')

    FP_COACH_ENROLMENT_ENTITY['memberCreditCard'] = None
    FP_COACH_ENROLMENT_ENTITY['memberBankDetail'] = None
    
#     IF Credit Card
    if e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_CREDIT_CARD:
        MemberCreditCard = {}

#         Billing Details
        if e[_ids['ARE_YOU_PAYING_FOR_THE_MEMBERSHIP']] == 'No':
            try:
                MemberCreditCard['holder'] = e[_ids['BILLING_NAME']]['first'] + ' ' + e[_ids['BILLING_NAME']]['last']
                MemberCreditCard['address'] = e[_ids['BILLING_ADDRESS']]['address']
                MemberCreditCard['city'] = e[_ids['BILLING_ADDRESS']]['city']
                MemberCreditCard['state'] = e[_ids['BILLING_ADDRESS']]['state']
                MemberCreditCard['postcode'] = e[_ids['BILLING_ADDRESS']]['zip']
            except Exception as ex:
                print(f'Error - BILLING_NAME & BILLING_ADDRESS: {ex}')
                logger.error(f'Error - BILLING_NAME & BILLING_ADDRESS: {ex}')
        else:
            try:
                MemberCreditCard['holder'] = e[_ids['NAME']]['first'].strip().title() + ' ' + e[_ids['NAME']]['last'].strip().title()
            except Exception as ex:
                print(f'Error - BILLING_NAME & BILLING_ADDRESS: {ex}')
                logger.error(f'Error - BILLING_NAME & BILLING_ADDRESS: {ex}')
            
        try:
            MemberCreditCard['number'] = e[_ids['CREDIT_CARD']]['card']
            MemberCreditCard['expMonth'] = e[_ids['CREDIT_CARD']]['cardexp'].split('/')[0]
            MemberCreditCard['expYear'] = '20' + e[_ids['CREDIT_CARD']]['cardexp'].split('/')[1]
            MemberCreditCard['verificationCode'] = e[_ids['CREDIT_CARD']]['cvv']
        
            FP_COACH_ENROLMENT_ENTITY['memberCreditCard'] = MemberCreditCard
        except Exception as ex:
            print(f'Error - CREDIT_CARD: {ex}')
            logger.error(f'Error - CREDIT_CARD: {ex}')

#     IF Bank Account
    if e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_BANK_ACCOUNT or e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_DIRECT_DEBIT:
        
        MemberBankDetail = {}
        
        if e[_ids['ARE_YOU_PAYING_FOR_THE_MEMBERSHIP']] == 'No':
            try:
                MemberBankDetail['accountHolderName'] = e[_ids['BILLING_NAME']]['first'].strip().title() + ' ' + e[_ids['BILLING_NAME']]['last'].strip().title()
            except Exception as ex:
                print(f'Error - BILLING_NAME: {ex}')
                logger.error(f'Error - BILLING_NAME: {ex}')
        else:
            try:
                MemberBankDetail['accountHolderName'] = e[_ids['NAME']]['first'].strip().title() + ' ' + e[_ids['NAME']]['last'].strip().title()
            except Exception as ex:
                print(f'Error - BILLING_NAME: {ex}')
                logger.error(f'Error - BILLING_NAME: {ex}')

        try:
            bsb = e[_ids['BSB']].strip()
            bsb = bsb.replace(' ','')
            bsb = bsb.replace('-','')
            if len(bsb) == 5:
                bsb = "0" + bsb
            elif len(bsb) == 4:
                bsb = "00" + bsb
                
            MemberBankDetail['bsb'] = bsb
        except Exception as ex:
            print(f'Error - BSB: {ex}')
            logger.error(f'Error - BSB: {ex}')
            
        try:
            accountNumber = e[_ids['ACCOUNT_NUMBER']].strip()
            accountNumber = accountNumber.replace(' ','')
            accountNumber = accountNumber.replace('-','')
            MemberBankDetail['accountNumber'] = accountNumber
        except Exception as ex:
            print(f'Error - ACCOUNT_NUMBER: {ex}')
            logger.error(f'Error - ACCOUNT_NUMBER: {ex}')
            
        try:
            MemberBankDetail['accountType'] = e[_ids['ACCOUNT_TYPE']]
        except Exception as ex:
            print(f'Error - ACCOUNT_TYPE: {ex}')
            logger.error(f'Error - ACCOUNT_TYPE: {ex}')
                
        FP_COACH_ENROLMENT_ENTITY['memberBankDetail'] = MemberBankDetail

    agreement = ''
    try:
#         print(e[_ids['MEMBERSHIP_TERMS_AGREEMENT']])
        if e[_ids['MEMBERSHIP_TERMS_AGREEMENT']] is not None and type(e[_ids['MEMBERSHIP_TERMS_AGREEMENT']]) == list and len(e[_ids['MEMBERSHIP_TERMS_AGREEMENT']]) > 0:
            agreement = agreement + e[_ids['MEMBERSHIP_TERMS_AGREEMENT']][0] + ' | '
    except Exception as ex:
        print(f'Error - MEMBERSHIP_TERMS_AGREEMENT: {ex}')
        logger.error(f'Error - MEMBERSHIP_TERMS_AGREEMENT: {ex}')

    try:
#         print(e[_ids['PT_6_SESSION_COMMITMENT']])
        if e[_ids['PT_6_SESSION_COMMITMENT']] is not None and type(e[_ids['PT_6_SESSION_COMMITMENT']]) == list and len(e[_ids['PT_6_SESSION_COMMITMENT']]) > 0:
            agreement = agreement + e[_ids['PT_6_SESSION_COMMITMENT']][0] + ' | '
    except Exception as ex:
        print(f'Error - PT_6_SESSION_COMMITMENT: {ex}')
        logger.error(f'Error - PT_6_SESSION_COMMITMENT: {ex}')

    try:
#         print(e[_ids['LIFESTYLE_PT_COMMITMENT']])
        if e[_ids['LIFESTYLE_PT_COMMITMENT']] is not None and type(e[_ids['LIFESTYLE_PT_COMMITMENT']]) == list and len(e[_ids['LIFESTYLE_PT_COMMITMENT']]) > 0:
            agreement = agreement + e[_ids['LIFESTYLE_PT_COMMITMENT']][0] + ' | '
    except Exception as ex:
        print(f'Error - LIFESTYLE_PT_COMMITMENT: {ex}')
        logger.error(f'Error - LIFESTYLE_PT_COMMITMENT: {ex}')

    try:
#         print(e[_ids['BILLING_TERMS_AGREEMENT']])
        if e[_ids['BILLING_TERMS_AGREEMENT']] is not None and type(e[_ids['BILLING_TERMS_AGREEMENT']]) == list and len(e[_ids['BILLING_TERMS_AGREEMENT']]) > 0:
            agreement = agreement + e[_ids['BILLING_TERMS_AGREEMENT']][0] + ' | '
    except Exception as ex:
        print(f'Error - BILLING_TERMS_AGREEMENT: {ex}')
        logger.error(f'Error - BILLING_TERMS_AGREEMENT: {ex}')

    try:
        FP_COACH_ENROLMENT_ENTITY['agreement'] = agreement[:-3]
    except Exception as ex:
        print(f'Error - AGREEMENT: {ex}')
        logger.error(f'Error - AGREEMENT: {ex}')
                          
    try:
        FP_COACH_ENROLMENT_ENTITY['signature'] = e[_ids['SIGNATURE']]
    except Exception as ex:
        print(f'Error - SIGNATURE: {ex}')
        logger.error(f'Error - SIGNATURE: {ex}')

    try:
        FP_COACH_ENROLMENT_ENTITY['notes'] = e[_ids['NOTES']]
    except Exception as ex:
        print(f'Error - NOTES: {ex}')
        logger.error(f'Error - NOTES: {ex}')
    
    return FP_COACH_ENROLMENT_ENTITY

