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


# ENROLMENT 
# ENROLMENT 
def build_enrolment_data_entity(e, member_status = None, communications_status = None):
    
#     Set Ids
    _ids = FS_FIELD_IDS.FORM[e["FormID"]]
    ENROLMENT_DATA_ENTITY = {}
    
#     Formstack Ids
    ENROLMENT_DATA_ENTITY['fs_uniqueId'] = e['UniqueID']
    ENROLMENT_DATA_ENTITY['fs_formId'] = e['FormID']
    ENROLMENT_DATA_ENTITY['mboUniqueId'] = None
    
    try:
        ENROLMENT_DATA_ENTITY['preExId'] = e[_ids['PRE_EX_ID']]
    except Exception as ex:
        print(f'Error - PRE_EX_ID {ex}')
        logger.error(f'Error - PRE_EX_ID {ex}')

#     Marketting Ids
    ENROLMENT_DATA_ENTITY['googleClickId'] = None
    ENROLMENT_DATA_ENTITY['facebookCampaignId'] = None
    
    ENROLMENT_DATA_ENTITY['existingClient'] = None
    ENROLMENT_DATA_ENTITY['gymSalesId'] = None

#     MBO Location Id


    locationId = None
    try:
        if _ids["MEMBERSHIP_CONSULTANT_LOCATION_ID"] is not None and e[_ids["MEMBERSHIP_CONSULTANT_LOCATION_ID"]] is not None:
            if type(e[_ids["MEMBERSHIP_CONSULTANT_LOCATION_ID"]]) == list:
                locationId = e[_ids["MEMBERSHIP_CONSULTANT_LOCATION_ID"]][0]['value']
            else:
                locationId = e[_ids["MEMBERSHIP_CONSULTANT_LOCATION_ID"]]
    except Exception as ex:
        print(f'Error - locationId {ex}')
        logger.error(f'Error - locationId {ex}')
    
#     print(f"locationId: {locationId}")
    if locationId is not None:
        ENROLMENT_DATA_ENTITY['locationId'] = locationId
    
#     Gym Name
    try:
        if type(e[_ids['HOME_LOCATION']]) == list:
            ENROLMENT_DATA_ENTITY['gymName'] = e[_ids['HOME_LOCATION']][0]['label']
        else:
            ENROLMENT_DATA_ENTITY['gymName'] = e[_ids['HOME_LOCATION']]
    except Exception as ex:
        print(f'Error - HOME_LOCATION {ex}')
        logger.error(f'Error - HOME_LOCATION {ex}')
        
#     Metadata
#     if is_new_entity:
    ENROLMENT_DATA_ENTITY['createDate'] = e[_ids['SUBMISSION_DATETIME']]
    ENROLMENT_DATA_ENTITY['updateDate'] = datetime.datetime.now().strftime(Constants.DATETIME_FORMAT)
    
    
#     Set Status
    if e['FormID'] == Constants.FS_IN_CLUB_ENROLMENT_FORM_ID:
        ENROLMENT_DATA_ENTITY['status'] = MemberStatus.SAVED[0]
        ENROLMENT_DATA_ENTITY['communicationsStatus'] = CommunicationsStatus.EMAIL_CAMPAIGN_PENDING[0]
    
    if e['FormID'] == Constants.FS_PHONE_ENROLMENT_INTERNAL_FORM_ID:
        ENROLMENT_DATA_ENTITY['status'] = MemberStatus.ENROLMENT_AUTHORISATION_PENDING[0]
        ENROLMENT_DATA_ENTITY['communicationsStatus'] = CommunicationsStatus.CLIENT_AUTHORISATION_SENT[0]
        
# ######################################################
# PROCESS PURCHASES                                    #
# ######################################################
    mbo_contracts = source_get_request(Constants.SOURCE_GET_ALL_CONTRACTS_URL + locationId)
    
    contractNamesToBeActivated = ''
    memberContracts = ''
    couponCode = ''

#     First N Days
    try:
        HAS_FREE_TIME = False
        free_time = None
        days_free = 0
        
        if e[_ids['FREE_TIME_SH']] is not None:
            days_free = e[_ids["FREE_TIME_SH"]][0]['value']
            free_time = 'First ' + days_free + ' Days'
            HAS_FREE_TIME = True
        elif e[_ids['FREE_TIME_NT']] is not None:
            days_free = e[_ids["FREE_TIME_NT"]][0]['value']
            free_time = 'First ' + days_free + ' Days'
            HAS_FREE_TIME = True
        elif e[_ids['FREE_TIME_MK']] is not None:
            days_free = e[_ids["FREE_TIME_MK"]][0]['value']
            free_time = 'First ' + days_free + ' Days'
            HAS_FREE_TIME = True
        elif e[_ids['FREE_TIME_BK']] is not None:
            days_free = e[_ids["FREE_TIME_BK"]][0]['value']
            free_time = 'First ' + days_free + ' Days'
            HAS_FREE_TIME = True
        
        if HAS_FREE_TIME:
            for c in mbo_contracts:
                if c['name'].strip() == free_time:
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # 0.00 | "
                    memberContracts = memberContracts + str(c['mboId']) + ','
                    couponCode = couponCode + Constants.MBO_COUPON_CODE_N_DAYS_FREE + ','
                    
        ENROLMENT_DATA_ENTITY['daysFree'] = free_time
            
    except Exception as ex:
        print(f'Error - FREE_TIME {ex}')
        logger.error(f'Error - FREE_TIME {ex}')

        
#     End of Year Free Special
    try:
        if _ids['END_OF_YEAR_FREE'] is not None and e[_ids['END_OF_YEAR_FREE']] is not None and e[_ids['END_OF_YEAR_FREE']][0] == "EOY Free":
            contractNamesToBeActivated = contractNamesToBeActivated + "First 30 Days # 0.00 | "
            memberContracts = memberContracts + '306,'
            couponCode = couponCode + Constants.MBO_COUPON_CODE_N_DAYS_FREE + ',EOY Free,'
            diff = datetime.date(datetime.date.today().year,12,31) - datetime.date.today()
            ENROLMENT_DATA_ENTITY['daysFree'] = str(diff.days)
    
    except Exception as ex:
        print(f'Error - END_OF_YEAR_FREE {ex}')
        logger.error(f'Error - END_OF_YEAR_FREE {ex}')

#     Virtual Playground
    try:
        if _ids['ADD_VIRTUAL_PLAGROUND'] is not None and e[_ids['ADD_VIRTUAL_PLAGROUND']] is not None and len(e[_ids['ADD_VIRTUAL_PLAGROUND']]) > 0:
            for c in mbo_contracts:
                if c['name'].strip() == 'Virtual Playground':
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {round(float(c['reoccuringPaymentAmountTotal']),2)}{get_payment_frequency_str(c)} | "
                    memberContracts = memberContracts + str(c['mboId']) + ','
    except Exception as ex:
        print(f'Error - VIRTUAL_PLAYGROUND {ex}')
        logger.error(f'Error - VIRTUAL_PLAYGROUND {ex}')

#     Creche
    try:
        if _ids['CRECHE'] is not None and e[_ids['CRECHE']] is not None and len(e[_ids['CRECHE']]) > 0:

            if type(e[_ids['CRECHE']]) == list:
                creche = e[_ids['CRECHE']][0]['label']
            else:
                creche = e[_ids['CRECHE']]
#             print(creche)
            for c in mbo_contracts:
                if c['name'].strip() == creche:
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {round(float(c['reoccuringPaymentAmountTotal']),2)}{get_payment_frequency_str(c)} | "
                    memberContracts = memberContracts + str(c['mboId']) + ','
                    ENROLMENT_DATA_ENTITY['creche'] = c['name'].strip()
    except Exception as ex:
        print(f'Error - CRECHE {ex}')
        logger.error(f'Error - CRECHE {ex}')

#     Access Key
    try:
        # if ENROLMENT_DATA_ENTITY['fs_formId'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_FP_FORM_ID or if ENROLMENT_DATA_ENTITY['fs_formId'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_BK_FORM_ID :
        if _ids['ACCESS_KEY_PAYMENT_OPTION'] is None:
            ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'] = Constants.FS_ACCESS_KEY_PAYMENT_OPTION_FIRST_PAYMENT
        else:
            ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'] = e[_ids['ACCESS_KEY_PAYMENT_OPTION']]
    except Exception as ex:
        print(f'Error - ACCESS_KEY_PAYMENT_OPTION: {ex}')
        logger.error(f'Error - ACCESS_KEY_PAYMENT_OPTION: {ex}')


    try:
        # if ENROLMENT_DATA_ENTITY['fs_formId'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_FP_FORM_ID or if ENROLMENT_DATA_ENTITY['fs_formId'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_BK_FORM_ID :
        if _ids['ACCESS_KEY_PAYMENT_METHOD'] is None:
            ENROLMENT_DATA_ENTITY['accessKeyPaymentMethod'] = None
        else:
            ENROLMENT_DATA_ENTITY['accessKeyPaymentMethod'] = e[_ids['ACCESS_KEY_PAYMENT_METHOD']]
    except Exception as ex:
        print(f'Error - ACCESS_KEY_PAYMENT_METHOD: {ex}')
        logger.error(f'Error - ACCESS_KEY_PAYMENT_METHOD: {ex}')


    try:
        HAS_ACCESS_KEY_DISCOUNT = False
        access_key_discount_label = None
        access_key_discount_value = 0
        
        if e[_ids['ACCESS_KEY_DISCOUNT_SH']] is not None:
            HAS_ACCESS_KEY_DISCOUNT = True
            access_key_discount_label = e[_ids['ACCESS_KEY_DISCOUNT_SH']][0]['label']
            access_key_discount_value = e[_ids['ACCESS_KEY_DISCOUNT_SH']][0]['value']
        elif e[_ids['ACCESS_KEY_DISCOUNT_NT']] is not None:
            HAS_ACCESS_KEY_DISCOUNT = True
            access_key_discount_label = e[_ids['ACCESS_KEY_DISCOUNT_NT']][0]['label']
            access_key_discount_value = e[_ids['ACCESS_KEY_DISCOUNT_NT']][0]['value']
        elif e[_ids['ACCESS_KEY_DISCOUNT_MK']] is not None:
            HAS_ACCESS_KEY_DISCOUNT = True
            access_key_discount_label = e[_ids['ACCESS_KEY_DISCOUNT_MK']][0]['label']
            access_key_discount_value = e[_ids['ACCESS_KEY_DISCOUNT_MK']][0]['value']
        elif e[_ids['ACCESS_KEY_DISCOUNT_BK']] is not None:
            HAS_ACCESS_KEY_DISCOUNT = True
            access_key_discount_label = e[_ids['ACCESS_KEY_DISCOUNT_BK']][0]['label']
            access_key_discount_value = e[_ids['ACCESS_KEY_DISCOUNT_BK']][0]['value']
        
        if HAS_ACCESS_KEY_DISCOUNT:
            for c in mbo_contracts:
                if c['name'] == 'Access Key':
#                     print(f"\tHAS_ACCESS_KEY_DISCOUNT: {round(float(c['reoccuringPaymentAmountTotal']),2) + float(access_key_discount_value)}")
                    
                    access_key_price = round(float(c['reoccuringPaymentAmountTotal']),2) + float(access_key_discount_value)
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {access_key_price} | "
                    
                    # Handle Access Key Pay Today - this will need to be a product purchase
                    if ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'] is not None and ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'].lower().replace(' ','') == 'paytoday':
                        print("Access Key Pay Today ommitting Contract Id")
                    else:
                        memberContracts = memberContracts + str(c['mboId']) + ','

                    if int(access_key_discount_value) + int(e[_ids['ACCESS_KEY_RETAIL_PRICE']]) == 0:
                        couponCode = couponCode + Constants.MBO_COUPON_CODE_KEY_FREE + ','
                    else:
                        couponCode = couponCode + 'Key' + str(int(access_key_discount_value) + int(e[_ids['ACCESS_KEY_RETAIL_PRICE']])) + ','
                    
        else:
            for c in mbo_contracts:
                if c['name'] == 'Access Key':
                    access_key_price = round(float(c['reoccuringPaymentAmountTotal']),2)
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {access_key_price} | "
                    if ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'] is not None and ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'].lower().replace(' ','') == 'paytoday':
                        print("Access Key Pay Today ommitting Contract Id")
                    else:
                        memberContracts = memberContracts + str(c['mboId']) + ','
        
        ENROLMENT_DATA_ENTITY['accessKeyDiscount'] = access_key_discount_label

    except Exception as ex:
        print(f'Error - ACCESS_KEY {ex}')
        logger.error(f'Error - ACCESS_KEY {ex}')
    
#     Starter Pack
    try:
        if _ids['STARTER_PACK_OPTIONS'] is not None and e[_ids['STARTER_PACK_OPTIONS']] is not None and e[_ids['STARTER_PACK_OPTIONS']] != '':
            for c in mbo_contracts:
                if c['name'] == e[_ids['STARTER_PACK_OPTIONS']][0]['label']:
#                     print(f"STARTER_PACK_OPTIONS: {c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    memberContracts = memberContracts + str(c['mboId']) + ','

                    if _ids['FREE_PT_PACK'] is not None and e[_ids['FREE_PT_PACK']] is not None and len(e[_ids['FREE_PT_PACK']]) > 0:
                        contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # 0.00 | "
                        ENROLMENT_DATA_ENTITY['freePTPack'] = e[_ids['FREE_PT_PACK']][0]
                        couponCode = couponCode + Constants.MBO_COUPON_PT_PACK_FREE + ','
                    else:
                        contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {round(float(c['reoccuringPaymentAmountTotal']),2)} | "
    except Exception as ex:
        print(f'Error - STARTER_PACK {ex}')
        logger.error(f'Error - STARTER_PACK {ex}')

#     IF PT Ongoing Two Free Sessions
    try:    
        if (_ids['2_FREE'] is not None and e[_ids['2_FREE']] is not None and len(e[_ids['2_FREE']]) > 0):
            for c in mbo_contracts:
                if c['name'].strip() == 'Packs: FP Coach Pack':
#                     print(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    memberContracts = memberContracts + str(c['mboId']) + ','
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # 0.00 | "
                    ENROLMENT_DATA_ENTITY['freePTPack'] = e[_ids['2_FREE']][0]
                    couponCode = couponCode + Constants.MBO_COUPON_PT_PACK_FREE + ','
    except Exception as ex:
        print(f'Error - 2_FREE {ex}')
        logger.error(f'Error - 2_FREE {ex}')

#     Training Package
    try:
        is_extrenal_pt = False
        has_coach = False
        training_package = None
        if _ids['TRAINING_PACKAGE_30_MIN'] is not None and e[_ids['TRAINING_PACKAGE_30_MIN']] is not None and e[_ids['TRAINING_PACKAGE_30_MIN']] != '':
            print(e[_ids['TRAINING_PACKAGE_30_MIN']])
            logger.info(e[_ids['TRAINING_PACKAGE_30_MIN']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_30_MIN']][0]['label']

        elif _ids['TRAINING_PACKAGE_40_MIN'] is not None and e[_ids['TRAINING_PACKAGE_40_MIN']] is not None and e[_ids['TRAINING_PACKAGE_40_MIN']] != '':
            print(e[_ids['TRAINING_PACKAGE_40_MIN']])
            logger.info(e[_ids['TRAINING_PACKAGE_40_MIN']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_40_MIN']][0]['label']

        elif _ids['TRAINING_PACKAGE_60_MIN'] is not None and e[_ids['TRAINING_PACKAGE_60_MIN']] is not None and e[_ids['TRAINING_PACKAGE_60_MIN']] != '':
            print(e[_ids['TRAINING_PACKAGE_60_MIN']])
            logger.info(e[_ids['TRAINING_PACKAGE_60_MIN']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_60_MIN']][0]['label']

        elif _ids['TRAINING_PACKAGE_30_MIN_BK'] is not None and e[_ids['TRAINING_PACKAGE_30_MIN_BK']] is not None and e[_ids['TRAINING_PACKAGE_30_MIN_BK']] != '':
            print(e[_ids['TRAINING_PACKAGE_30_MIN_BK']])
            logger.info(e[_ids['TRAINING_PACKAGE_30_MIN_BK']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_30_MIN_BK']][0]['label']

        elif _ids['TRAINING_PACKAGE_40_MIN_BK'] is not None and e[_ids['TRAINING_PACKAGE_40_MIN_BK']] is not None and e[_ids['TRAINING_PACKAGE_40_MIN_BK']] != '':
            print(e[_ids['TRAINING_PACKAGE_40_MIN_BK']])
            logger.info(e[_ids['TRAINING_PACKAGE_40_MIN_BK']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_40_MIN_BK']][0]['label']

        elif _ids['TRAINING_PACKAGE_60_MIN_BK'] is not None and e[_ids['TRAINING_PACKAGE_60_MIN_BK']] is not None and e[_ids['TRAINING_PACKAGE_60_MIN_BK']] != '':
            print(e[_ids['TRAINING_PACKAGE_60_MIN_BK']])
            logger.info(e[_ids['TRAINING_PACKAGE_60_MIN_BK']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_60_MIN_BK']][0]['label']

        elif _ids['TRAINING_PACKAGE_LIFESTYLE'] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE']] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE']] != '':
            print(e[_ids['TRAINING_PACKAGE_LIFESTYLE']])
            logger.info(e[_ids['TRAINING_PACKAGE_LIFESTYLE']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_LIFESTYLE']][0]['label']

        elif _ids['TRAINING_PACKAGE_LIFESTYLE_BK'] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']] != '':
            print(e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']])
            logger.info(e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']][0]['label']

        elif _ids['TRAINING_PACKAGE_VIRTUAL_FITNESS'] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']] != '':
            print(e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']])
            logger.info(e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']][0]['label']

        elif _ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS'] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']] != '':
            print(e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']])
            logger.info(e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']][0]['label']

        elif _ids['TRAINING_PACKAGE_WELLNESS'] is not None and e[_ids['TRAINING_PACKAGE_WELLNESS']] is not None and e[_ids['TRAINING_PACKAGE_WELLNESS']] != '':
            print(e[_ids['TRAINING_PACKAGE_WELLNESS']])
            logger.info(e[_ids['TRAINING_PACKAGE_WELLNESS']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_WELLNESS']][0]['label']
        
        elif _ids['TRAINING_PACKAGE_WELLNESS'] is not None and e[_ids['TRAINING_PACKAGE_WELLNESS']] is not None and e[_ids['TRAINING_PACKAGE_WELLNESS']] != '':
            print(e[_ids['TRAINING_PACKAGE_WELLNESS']])
            logger.info(e[_ids['TRAINING_PACKAGE_WELLNESS']])
            has_coach = True
            training_package = e[_ids['TRAINING_PACKAGE_WELLNESS']][0]['label']

    #         External PT
        elif _ids['COACHING_MODALITY'] is not None and e[_ids['COACHING_MODALITY']] is not None and 'external' in e[_ids['COACHING_MODALITY']].lower():
            print("EXTERNAL PT")
            logger.info("EXTERNAL PT")
            is_extrenal_pt = True
            has_coach = True

            if _ids['NUMBER_OF_SESSIONS_EXTERNAL'] is not None and type(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']]) == list and len(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']]) > 0:
                if int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2 < 1:
                    external_pt_contract_name = 'PT: External 1x 40 Min Session Per Fortnight'
                else:
                    external_pt_contract_name = 'PT: External ' + str(math.floor(int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2)) + 'x 40 Min Session Per Week'

                ENROLMENT_DATA_ENTITY['numberSessionsPerWeek'] = math.ceil(int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2)
                number_sessions_external = int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value'])
            else:
                if int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2 < 1:
#                     external_pt_contract_name = f"PT: External 1x {e[_ids['SESSION_LENGTH']].split(' ')[0]} Min Session Per Fortnight"
                    external_pt_contract_name = f"PT: External 1x 40 Min Session Per Fortnight"
                else:
#                     external_pt_contract_name = 'PT: External ' + str(math.floor(int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2)) + 'x ' + e[_ids['SESSION_LENGTH']].split(' ')[0] + ' Min Session Per Week'
                    external_pt_contract_name = 'PT: External ' + str(math.floor(int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']][0]['value']) / 2)) + 'x 40 Min Session Per Week'

                ENROLMENT_DATA_ENTITY['numberSessionsPerWeek'] = math.ceil(int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']]) / 2)
                number_sessions_external = int(e[_ids['NUMBER_OF_SESSIONS_EXTERNAL']])

            for c in mbo_contracts:
                if c['name'].strip() == external_pt_contract_name:
    #                     print(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    memberContracts = memberContracts + str(c['mboId']) + ','
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{external_pt_contract_name} # {float(e[_ids['PRICES_PER_SESSION_EXTERNAL']]) * number_sessions_external}/fortnight | "

            try:
                ENROLMENT_DATA_ENTITY['externalPTSessionPrice'] = e[_ids['PRICES_PER_SESSION_EXTERNAL']]
            except Exception as ex:
                print(f"Error - PRICES_PER_SESSION_EXTERNAL: {ex}")
                logger.error(f"Error - PRICES_PER_SESSION_EXTERNAL: {ex}")

        elif _ids['TRAINING_PACKAGE'] is not None and e[_ids['TRAINING_PACKAGE']] is not None and e[_ids['TRAINING_PACKAGE']] != '':
            training_package = e[_ids['TRAINING_PACKAGE']]

        else:
            print("I'm a Fitness Guru OR EP Referral")
            logger.info("I'm a Fitness Guru OR EP Referral")

        if is_extrenal_pt == False:
            for c in mbo_contracts:
                if c['name'].strip() == training_package:
    #                     print(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    memberContracts = memberContracts + str(c['mboId']) + ','
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {round(float(c['reoccuringPaymentAmountTotal']),2)}{get_payment_frequency_str(c)} | "
    
        ENROLMENT_DATA_ENTITY['isExternalPt'] = is_extrenal_pt
        ENROLMENT_DATA_ENTITY['hasCoach'] = has_coach
    
    except Exception as ex:
        print(f'Error - TRAINING_PACKAGE {ex}')
        logger.error(f'Error - TRAINING_PACKAGE {ex}')

    try:
        if _ids['SESSION_LENGTH'] is not None:
            ENROLMENT_DATA_ENTITY['sessionLength'] = e[_ids['SESSION_LENGTH']]
    except Exception as ex:
        print(f"Error - SESSION_LENGTH: {ex}")
        logger.error(f"Error - SESSION_LENGTH: {ex}")
                         
#     Training Starter Pack
    try:
        if _ids['TRAINING_OPTIONS'] is not None:
            ENROLMENT_DATA_ENTITY['trainingStarterPack'] = e[_ids['TRAINING_OPTIONS']]
    except Exception as ex:
        print(f'Error - TRAINING_OPTIONS {ex}')
        logger.error(f'Error - TRAINING_OPTIONS {ex}')
        

#     Membership
    membership = None
    try:
        if _ids['MEMBERSHIP_NAME_NT'] is not None and e[_ids['MEMBERSHIP_NAME_NT']] is not None and e[_ids['MEMBERSHIP_NAME_NT']] != '':
            membership = e[_ids['MEMBERSHIP_NAME_NT']][0]['label']
        elif _ids['MEMBERSHIP_NAME_MK'] is not None and e[_ids['MEMBERSHIP_NAME_MK']] is not None and e[_ids['MEMBERSHIP_NAME_MK']] != '':
            membership = e[_ids['MEMBERSHIP_NAME_MK']][0]['label']
        elif _ids['MEMBERSHIP_NAME_SH'] is not None and e[_ids['MEMBERSHIP_NAME_SH']] is not None and e[_ids['MEMBERSHIP_NAME_SH']] != '':
            membership = e[_ids['MEMBERSHIP_NAME_SH']][0]['label']
        elif _ids['MEMBERSHIP_NAME_BK'] is not None and e[_ids['MEMBERSHIP_NAME_BK']] is not None and e[_ids['MEMBERSHIP_NAME_BK']] != '':
            membership = e[_ids['MEMBERSHIP_NAME_BK']][0]['label']
        elif _ids['MEMBERSHIP_NAME'] is not None and e[_ids['MEMBERSHIP_NAME']] is not None and e[_ids['MEMBERSHIP_NAME']] != '':
            membership = e[_ids['MEMBERSHIP_NAME']]

        print(membership)
        logger.info(membership)

#         Membership Metadata
        try:
            if 'nocommitment' in membership.lower().replace(' ',''):
                ENROLMENT_DATA_ENTITY['noCommitment'] = 'noCommitment'
            else:
                ENROLMENT_DATA_ENTITY['noCommitment'] = '12Month'
        except Exception as ex:
            print(f'Error - NO COMMITMENT {ex}')
            logger.error(f'Error - NO COMMITMENT {ex}')

        try:
            if 'play' in membership.lower():
                ENROLMENT_DATA_ENTITY['gymOrPlay'] = 'play'

            if 'gym' in membership.lower():
                ENROLMENT_DATA_ENTITY['gymOrPlay'] = 'gym'

        except Exception as ex:
            print(f'Error - GYM or PLAY {ex}')
            logger.error(f'Error - GYM or PLAY {ex}')

        ENROLMENT_DATA_ENTITY['ddOrPif'] = 'dd'

        for c in mbo_contracts:
            if c['name'].strip() == membership:
                memberContracts = memberContracts + str(c['mboId']) + ','
                contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {round(float(c['reoccuringPaymentAmountTotal']),2)}{get_payment_frequency_str(c)} | "
    except Exception as ex:
        print(f'Error - MEMBERSHIP_NAME {ex}')
        logger.error(f'Error - MEMBERSHIP_NAME {ex}')

    
#     Discounted Membership
    try:
        if _ids['MEMBERSHIP_DISCOUNT'] is not None and e[_ids['MEMBERSHIP_DISCOUNT']] is not None and e[_ids['MEMBERSHIP_DISCOUNT']][0] == "$2 Discount":
            couponCode = couponCode + Constants.MBO_COUPON_CODE_MEMBERSHIP_DISCOUNT_2 + ','
    except Exception as ex:
        print(f'Error - MEMBERSHIP_DISCOUNT {ex}')
        logger.error(f'Error - MEMBERSHIP_DISCOUNT {ex}')


    ENROLMENT_DATA_ENTITY['contractNamesToBeActivated'] = contractNamesToBeActivated[:-3]
    ENROLMENT_DATA_ENTITY['memberContracts'] = memberContracts[:-1]
    ENROLMENT_DATA_ENTITY['couponCode'] = couponCode[:-1]

#     Start and Billing Dats
    try:
        if _ids['START_DATE'] is not None:
            ENROLMENT_DATA_ENTITY['startDate'] = pd.to_datetime(e[_ids['START_DATE']]).strftime('%Y-%m-%d')
    except Exception as ex:
        print(f'Error - START_DATE {ex}')
        logger.error(f'Error - START_DATE {ex}')
        ENROLMENT_DATA_ENTITY['startDate'] = datetime.datetime.now().strftime('%Y-%m-%d')
    
    try:
        # NOT End of Year special
        if _ids['END_OF_YEAR_FREE'] is not None and e[_ids['END_OF_YEAR_FREE']] is None and _ids['FIRST_DEBIT_DATE'] is not None and e[_ids['FIRST_DEBIT_DATE']] is not None and e[_ids['FIRST_DEBIT_DATE']] != '':
            ENROLMENT_DATA_ENTITY['firstBillingDate'] = pd.to_datetime(e[_ids['FIRST_DEBIT_DATE']]).strftime('%Y-%m-%d')
        # End of Year special
        elif _ids['END_OF_YEAR_FREE'] is not None and e[_ids['END_OF_YEAR_FREE']] is not None and "EOY Free" in e[_ids['END_OF_YEAR_FREE']] and _ids['EOY_FIRST_DEBIT_DATE'] is not None and e[_ids['EOY_FIRST_DEBIT_DATE']] is not None and e[_ids['EOY_FIRST_DEBIT_DATE']] != '':
            ENROLMENT_DATA_ENTITY['firstBillingDate'] = pd.to_datetime(e[_ids['EOY_FIRST_DEBIT_DATE']]).strftime('%Y-%m-%d')
        # Default Catch
        else:
            ENROLMENT_DATA_ENTITY['firstBillingDate'] = datetime.datetime.now().strftime('%Y-%m-%d')
    except Exception as ex:
        print(f'Error - FIRST_DEBIT_DATE {ex}')
        logger.error(f'Error - FIRST_DEBIT_DATE {ex}')
        ENROLMENT_DATA_ENTITY['firstBillingDate'] = datetime.datetime.now().strftime('%Y-%m-%d')
        
    try:
        if _ids['COACHING_FIRST_DEBIT_DATE'] is not None and e[_ids['COACHING_FIRST_DEBIT_DATE']] is not None and e[_ids['COACHING_FIRST_DEBIT_DATE']] != '':
            ENROLMENT_DATA_ENTITY['personalTrainingStartDate'] = pd.to_datetime(e[_ids['COACHING_FIRST_DEBIT_DATE']]).strftime('%Y-%m-%d')
        else:
            ENROLMENT_DATA_ENTITY['personalTrainingStartDate'] = None    
    except Exception as ex:
        print(f'Error - COACHING_FIRST_DEBIT_DATE {ex}')
        logger.error(f'Error - COACHING_FIRST_DEBIT_DATE {ex}')
        
#     Staff Name
    membership_consultant_name = None
    membership_consultant_mbo_id = None
    try:
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

#         print(f"{membership_consultant_name} {membership_consultant_mbo_id}")
        ENROLMENT_DATA_ENTITY['staffMember'] = membership_consultant_mbo_id
        ENROLMENT_DATA_ENTITY['staffName'] = membership_consultant_name
    except Exception as ex:
        print(f'Error - MEMBERSHIP_CONSULTANT_NAME {ex}')
        logger.error(f'Error - MEMBERSHIP_CONSULTANT_NAME {ex}')

#     Coach Name
    coach_mbo_id = None
    coach_name = None
    try:
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
#         print(f"{coach_name} {coach_mbo_id}")
        ENROLMENT_DATA_ENTITY['personalTrainer'] = coach_mbo_id
        ENROLMENT_DATA_ENTITY['personalTrainerName'] = coach_name
    except Exception as ex:
        print(f'Error - COACH_NAME {ex}')
        logger.error(f'Error - COACH_NAME {ex}')
        
    try:
        if _ids["TRAINGING_PACKAGE_SOLD_BY"] is not None:
            ENROLMENT_DATA_ENTITY['trainingPackageSoldBy'] = e[_ids["TRAINGING_PACKAGE_SOLD_BY"]]
    except Exception as ex:
        print(f"Error TRAINGING_PACKAGE_SOLD_BY - {ex}")
        logger.error(f"Error TRAINGING_PACKAGE_SOLD_BY - {ex}")
        
    if ENROLMENT_DATA_ENTITY['trainingPackageSoldBy'] == 'Coach':
        ENROLMENT_DATA_ENTITY['trainingPackageConsultantLocationId'] = e[_ids['HOME_LOCATION']][0]['value']
        ENROLMENT_DATA_ENTITY['trainingPackageConsultantName'] = coach_name
        ENROLMENT_DATA_ENTITY['trainingPackageConsultantMboId'] = coach_mbo_id
    
    elif ENROLMENT_DATA_ENTITY['trainingPackageSoldBy'] == 'Membership Consultant':
        ENROLMENT_DATA_ENTITY['trainingPackageConsultantLocationId'] = ENROLMENT_DATA_ENTITY['locationId']
        ENROLMENT_DATA_ENTITY['trainingPackageConsultantName'] = membership_consultant_name
        ENROLMENT_DATA_ENTITY['trainingPackageConsultantMboId'] = membership_consultant_mbo_id
        
    else:
        try:
            if type(e[_ids["TRAINING_PACKAGE_CONSULTANT_LOCATION"]]) == list:
                ENROLMENT_DATA_ENTITY['trainingPackageConsultantLocationId'] = e[_ids["TRAINING_PACKAGE_CONSULTANT_LOCATION"]][0]['value']
            else:
                ENROLMENT_DATA_ENTITY['trainingPackageConsultantLocationId'] = e[_ids["TRAINING_PACKAGE_CONSULTANT_LOCATION"]]
        except Exception as ex:
            print(f"Error TRAINING_PACKAGE_CONSULTANT_LOCATION - {ex}")
            logger.error(f"Error TRAINING_PACKAGE_CONSULTANT_LOCATION - {ex}")
        
        try:
            trainingPackageConsultantName = None
            trainingPackageConsultantMboId = None
            if e[_ids["TRAINING_PACKAGE_CONSULTANT_SH"]] is not None and e[_ids["TRAINING_PACKAGE_CONSULTANT_SH"]] != '' and type(e[_ids["TRAINING_PACKAGE_CONSULTANT_SH"]] == list):
                trainingPackageConsultantName = e[_ids["TRAINING_PACKAGE_CONSULTANT_SH"]][0]['label']
                trainingPackageConsultantMboId = e[_ids["TRAINING_PACKAGE_CONSULTANT_SH"]][0]['value']
            elif e[_ids["TRAINING_PACKAGE_CONSULTANT_NT"]] is not None and e[_ids["TRAINING_PACKAGE_CONSULTANT_NT"]] != '' and type(e[_ids["TRAINING_PACKAGE_CONSULTANT_NT"]] == list):
                trainingPackageConsultantName = e[_ids["TRAINING_PACKAGE_CONSULTANT_NT"]][0]['label']
                trainingPackageConsultantMboId = e[_ids["TRAINING_PACKAGE_CONSULTANT_NT"]][0]['value']
            elif e[_ids["TRAINING_PACKAGE_CONSULTANT_MK"]] is not None and e[_ids["TRAINING_PACKAGE_CONSULTANT_MK"]] != '' and type(e[_ids["TRAINING_PACKAGE_CONSULTANT_MK"]] == list):
                trainingPackageConsultantName = e[_ids["TRAINING_PACKAGE_CONSULTANT_MK"]][0]['label']
                trainingPackageConsultantMboId = e[_ids["TRAINING_PACKAGE_CONSULTANT_MK"]][0]['value']
            elif e[_ids["TRAINING_PACKAGE_CONSULTANT_BK"]] is not None and e[_ids["TRAINING_PACKAGE_CONSULTANT_BK"]] != '' and type(e[_ids["TRAINING_PACKAGE_CONSULTANT_BK"]] == list):
                trainingPackageConsultantName = e[_ids["TRAINING_PACKAGE_CONSULTANT_BK"]][0]['label']
                trainingPackageConsultantMboId = e[_ids["TRAINING_PACKAGE_CONSULTANT_BK"]][0]['value']

            ENROLMENT_DATA_ENTITY['trainingPackageConsultantName'] = trainingPackageConsultantName
            ENROLMENT_DATA_ENTITY['trainingPackageConsultantMboId'] = trainingPackageConsultantMboId
        except Exception as ex:
            print(f"Error TRAINING_PACKAGE_CONSULTANT - {ex}")
            logger.error(f"Error TRAINING_PACKAGE_CONSULTANT - {ex}")


#    Primary Details
    try:
        ENROLMENT_DATA_ENTITY['phone'] = format_phone(e[_ids['PHONE']])
    except Exception as ex:
        print(f'Error - PHONE {ex}')
        logger.error(f'Error - PHONE {ex}')
         
    try:
        ENROLMENT_DATA_ENTITY['firstName'] = e[_ids['NAME']]['first'].strip().title()
    except Exception as ex:
        print(f'Error - NAME FIRST {ex}')
        logger.error(f'Error - NAME FIRST {ex}')
                     
    try:
        ENROLMENT_DATA_ENTITY['lastName'] = e[_ids['NAME']]['last'].strip().title()
    except Exception as ex:
        print(f'Error - NAME LAST {ex}')
        logger.error(f'Error - NAME LAST {ex}')

    try:
        ENROLMENT_DATA_ENTITY['email'] = e[_ids['EMAIL']].strip()
    except Exception as ex:
        print(f'Error - EMAIL {ex}')
        logger.error(f'Error - EMAIL {ex}')

#    Member Details
    try:
        ENROLMENT_DATA_ENTITY['address1'] = e[_ids['ADDRESS']]['address'].strip().title()
        ENROLMENT_DATA_ENTITY['city'] = e[_ids['ADDRESS']]['city'].strip().title()
        ENROLMENT_DATA_ENTITY['state'] = e[_ids['ADDRESS']]['state'].strip().upper()
        ENROLMENT_DATA_ENTITY['postcode'] = e[_ids['ADDRESS']]['zip'].strip().title()
    except Exception as ex:
        print(f'Error - ADDRESS {ex}')
        logger.error(f'Error - ADDRESS {ex}')

    try:
        ENROLMENT_DATA_ENTITY['dob'] = pd.to_datetime(e[_ids['DATE_OF_BIRTH']]).strftime('%Y-%m-%d')
    except Exception as ex:
        print(f'Error - DATE_OF_BIRTH {ex}')
        logger.error(f'Error - DATE_OF_BIRTH {ex}')

    try:
        ENROLMENT_DATA_ENTITY['emergencyContactName'] = e[_ids['EMERGENCY_CONTACT_NAME']].strip().title()
    except Exception as ex:
        print(f'Error - EMERGENCY_CONTACT_NAME {ex}')
        logger.error(f'Error - EMERGENCY_CONTACT_NAME {ex}')

    try:
        ENROLMENT_DATA_ENTITY['emergencyContactPhone'] = format_phone(e[_ids['EMERGENCY_CONTACT_PHONE']])
    except Exception as ex:
        print(f'Error - EMERGENCY_CONTACT_PHONE {ex}')
        logger.error(f'Error - EMERGENCY_CONTACT_PHONE {ex}')

    ENROLMENT_DATA_ENTITY['notes'] = e[_ids['NOTES']]
    
#     Auxiliry Data
    try:
        ENROLMENT_DATA_ENTITY['howDidYouHearAboutUs'] = e[_ids['HOW_DID_YOU_HEAR_ABOUT_US']]
    except Exception as ex:
        print(f'Error - HOW_DID_YOU_HEAR_ABOUT_US {ex}')
        logger.error(f'Error - HOW_DID_YOU_HEAR_ABOUT_US {ex}')
        
    try:
        if e[_ids["HAS_REFERRAL"]] is not None and type(e[_ids["HAS_REFERRAL"]]) == list and len(e[_ids["HAS_REFERRAL"]]) > 0:
            ENROLMENT_DATA_ENTITY['hasReferral'] = e[_ids["HAS_REFERRAL"]][0].lower() == 'yes'
        else:
            ENROLMENT_DATA_ENTITY['hasReferral'] = False
    except Exception as ex:
        print(f"Error - HAS_REFERRAL {ex}")
        logger.error(f"Error - HAS_REFERRAL {ex}")
        ENROLMENT_DATA_ENTITY['hasReferral'] = False

    try:
        ENROLMENT_DATA_ENTITY['referralName'] = e[_ids["REFERRAL_NAME"]]
    except Exception as ex:
        print(f"Error - REFERRAL_NAME {ex}")
        logger.error(f"Error - REFERRAL_NAME {ex}")

    try:
        ENROLMENT_DATA_ENTITY['referralEmail'] = e[_ids["REFERRAL_EMAIL"]]
    except Exception as ex:
        print(f"Error - REFERRAL_EMAIL {ex}")
        logger.error(f"Error - REFERRAL_EMAIL {ex}")

    try:
        ENROLMENT_DATA_ENTITY['referralPhone'] = e[_ids["REFERRAL_PHONE"]]
    except Exception as ex:
        print(f"Error - REFERRAL_PHONE {ex}")
        logger.error(f"Error - REFERRAL_PHONE {ex}")
    # This can be removed

    try:
        ENROLMENT_DATA_ENTITY['virtualPlaygroundCommencement'] = e[_ids['COMMENCE_VIRTUAL_PLAGROUND_ON']]
    except Exception as ex:
        print(f"Error - COMMENCE_VIRTUAL_PLAGROUND_ON {ex}")
        logger.error(f"Error - COMMENCE_VIRTUAL_PLAGROUND_ON {ex}")
    
    
    # COMFORT_CANCEL
    try:
        comfort_cxl = None
        if e[_ids['COMFORT_CANCEL_SH']] is not None:
            comfort_cxl = e[_ids['COMFORT_CANCEL_SH']][0]['value'] + 'Day' + Constants.MBO_COUPON_CODE_COMFORT_CANCEL
        elif e[_ids['COMFORT_CANCEL_NT']] is not None:
            comfort_cxl = e[_ids['COMFORT_CANCEL_NT']][0]['value'] + 'Day' + Constants.MBO_COUPON_CODE_COMFORT_CANCEL
        elif e[_ids['COMFORT_CANCEL_MK']] is not None:
            comfort_cxl = e[_ids['COMFORT_CANCEL_MK']][0]['value'] + 'Day' + Constants.MBO_COUPON_CODE_COMFORT_CANCEL
        elif e[_ids['COMFORT_CANCEL_BK']] is not None:
            comfort_cxl = e[_ids['COMFORT_CANCEL_BK']][0]['value'] + 'Day' + Constants.MBO_COUPON_CODE_COMFORT_CANCEL

        if comfort_cxl is not None:
            ENROLMENT_DATA_ENTITY['couponCode'] = ENROLMENT_DATA_ENTITY['couponCode'] + ',' + comfort_cxl
    except Exception as ex:
        print(f'Error - COMFORT_CANCEL: {ex}')
        logger.error(f'Error - COMFORT_CANCEL: {ex}')
        
# In-club Submission
    if e["FormID"] == Constants.FS_IN_CLUB_ENROLMENT_FORM_ID:
        
        print("In-club Submission")
        logger.info("In-club Submission")
        
        try:
            ENROLMENT_DATA_ENTITY['gender'] = e[_ids['GENDER']].lower()
        except Exception as ex:
            print(f'Error - GENDER {ex}')
            logger.error(f'Error - GENDER {ex}')
        
        #     Payment Details
        try:
            if type(e[_ids["PAYMENT_AUTHORISATION"]]) == list:
                ENROLMENT_DATA_ENTITY['getAuthorisation'] = e[_ids["PAYMENT_AUTHORISATION"]][0]
            else:
                ENROLMENT_DATA_ENTITY['getAuthorisation'] = e[_ids["PAYMENT_AUTHORISATION"]]
        except Exception as ex:
            print(f'Error - PAYMENT_AUTHORISATION {ex}')
            logger.error(f'Error - PAYMENT_AUTHORISATION {ex}')

        try:
            ENROLMENT_DATA_ENTITY['paymentType'] = e[_ids["PAYMENT_METHOD"]]
        except Exception as ex:
            print(f'Error - PAYMENT_METHOD {ex}')
            logger.error(f'Error - PAYMENT_METHOD {ex}')

        try:
            ENROLMENT_DATA_ENTITY['useExistingDetails'] = e[_ids['ARE_YOU_PAYING_FOR_THE_MEMBERSHIP']]
        except Exception as ex:
            print(f'Error - ARE_YOU_PAYING_FOR_THE_MEMBERSHIP {ex}')
            logger.error(f'Error - ARE_YOU_PAYING_FOR_THE_MEMBERSHIP {ex}')

    #     IF Credit Card
        if e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_CREDIT_CARD:
            ENROLMENT_DATA_ENTITY['memberBankDetail'] = None

            MemberCreditCard = {}
            #     Billing Details
            if e[_ids['ARE_YOU_PAYING_FOR_THE_MEMBERSHIP']] == 'No':
                try:
                    MemberCreditCard['holder'] = e[_ids['BILLING_NAME']]['first'] + ' ' + e[_ids['BILLING_NAME']]['last']
                    MemberCreditCard['address'] = e[_ids['BILLING_ADDRESS']]['address']
                    MemberCreditCard['city'] = e[_ids['BILLING_ADDRESS']]['city']
                    MemberCreditCard['state'] = e[_ids['BILLING_ADDRESS']]['state']
                    MemberCreditCard['postcode'] = e[_ids['BILLING_ADDRESS']]['zip']
                except Exception as ex:
                    print(f'Error - BILLING_NAME & BILLING_ADDRESS {ex}')
                    logger.error(f'Error - BILLING_NAME & BILLING_ADDRESS {ex}')
            else:
                try:
                    MemberCreditCard['holder'] = e[_ids['NAME']]['first'].strip().title() + ' ' + e[_ids['NAME']]['last'].strip().title()
                    MemberCreditCard['address'] = e[_ids['ADDRESS']]['address']
                    MemberCreditCard['city'] = e[_ids['ADDRESS']]['city']
                    MemberCreditCard['state'] = e[_ids['ADDRESS']]['state']
                    MemberCreditCard['postcode'] = e[_ids['ADDRESS']]['zip']
                except Exception as ex:
                    print(f'Error - BILLING_NAME & BILLING_ADDRESS {ex}')
                    logger.error(f'Error - BILLING_NAME & BILLING_ADDRESS {ex}')

            try:
                MemberCreditCard['number'] = e[_ids['CREDIT_CARD']]['card']
                MemberCreditCard['expMonth'] = e[_ids['CREDIT_CARD']]['cardexp'].split('/')[0]
                MemberCreditCard['expYear'] = '20' + e[_ids['CREDIT_CARD']]['cardexp'].split('/')[1]
                MemberCreditCard['verificationCode'] = e[_ids['CREDIT_CARD']]['cvv']

                ENROLMENT_DATA_ENTITY['memberCreditCard'] = MemberCreditCard
            except Exception as ex:
                print(f'Error - CREDIT_CARD {ex}')
                logger.error(f'Error - CREDIT_CARD {ex}')

    #     IF Bank Account
        if e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_BANK_ACCOUNT or e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_DIRECT_DEBIT:
            ENROLMENT_DATA_ENTITY['memberCreditCard'] = None

            MemberBankDetail = {}

            if e[_ids['ARE_YOU_PAYING_FOR_THE_MEMBERSHIP']] == 'No':
                try:
                    MemberBankDetail['accountHolderName'] = e[_ids['BILLING_NAME']]['first'].strip().title() + ' ' + e[_ids['BILLING_NAME']]['last'].strip().title()
                except Exception as ex:
                    print(f'Error - BILLING_NAME {ex}')
                    logger.error(f'Error - BILLING_NAME {ex}')
            else:
                try:
                    MemberBankDetail['accountHolderName'] = e[_ids['NAME']]['first'].strip().title() + ' ' + e[_ids['NAME']]['last'].strip().title()
                except Exception as ex:
                    print(f'Error - BILLING_NAME {ex}')
                    logger.error(f'Error - BILLING_NAME {ex}')

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
                print(f'Error - BSB {ex}')
                logger.error(f'Error - BSB {ex}')

            try:
                accountNumber = e[_ids['ACCOUNT_NUMBER']].strip()
                accountNumber = accountNumber.replace(' ','')
                accountNumber = accountNumber.replace('-','')
                MemberBankDetail['accountNumber'] = accountNumber
            except Exception as ex:
                print(f'Error - ACCOUNT_NUMBER {ex}')
                logger.error(f'Error - ACCOUNT_NUMBER {ex}')

            try:
                if e[_ids['ACCOUNT_TYPE']] == 'Cheque':
                    MemberBankDetail['accountType'] = 'Checking'
                elif e[_ids['ACCOUNT_TYPE']] == 'Savings':
                    MemberBankDetail['accountType'] = 'Savings'
                else:
                    MemberBankDetail['accountType'] = e[_ids['ACCOUNT_TYPE']]
            except Exception as ex:
                print(f'Error - ACCOUNT_TYPE {ex}')
                logger.error(f'Error - ACCOUNT_TYPE {ex}')

            ENROLMENT_DATA_ENTITY['memberBankDetail'] = MemberBankDetail
                          
        try:
            ENROLMENT_DATA_ENTITY['numberOneGoal'] = e[_ids['NUMBER_ONE_GOAL']]
        except Exception as ex:
            print(f'Error - NUMBER_ONE_GOAL {ex}')
            logger.error(f'Error - NUMBER_ONE_GOAL {ex}')

        try:
            if e[_ids['TRAINING_AVAILABILITY']] is not None and type(e[_ids['TRAINING_AVAILABILITY']]) == list:
                temp = ''
                for i in e[_ids['TRAINING_AVAILABILITY']]:
                    temp = temp + i + ','

                if len(temp) > 0:
                    temp = temp[:-1]

                ENROLMENT_DATA_ENTITY['trainingAvailability'] = temp
        except Exception as ex:
            print(f'Error - TRAINING_AVAILABILITY {ex}')
            logger.error(f'Error - TRAINING_AVAILABILITY {ex}')

        try:
            if e[_ids['TIME_AVAILABILITY']] is not None and type(e[_ids['TIME_AVAILABILITY']]) == list:
                temp = ''
                for i in e[_ids['TIME_AVAILABILITY']]:
                    temp = temp + i + ','

                if len(temp) > 0:
                    temp = temp[:-1]

                ENROLMENT_DATA_ENTITY['timeAvailability'] = temp
        except Exception as ex:
            print(f'Error - TIME_AVAILABILITY {ex}')
            logger.error(f'Error - TIME_AVAILABILITY {ex}')

    #     Medical Data
        try:
            if e[_ids['MEDICAL']] is not None and type(e[_ids['MEDICAL']]) == list:
                temp = ''
                for m in e[_ids['MEDICAL']]:
                    temp = temp + m + ','

                if len(temp) > 0:
                    temp = temp[:-1]

                ENROLMENT_DATA_ENTITY['medical'] = temp
        except Exception as ex:
            print(f'Error - MEDICAL {ex}')
            logger.error(f'Error - MEDICAL {ex}')

        try:
            ENROLMENT_DATA_ENTITY['medicalClearance'] = e[_ids['MEDICAL_CLEARANCE']]
        except Exception as ex:
            print(f'Error - MEDICAL_CLEARANCE {ex}')

        try:
            if e[_ids['INJURIES']] is not None and type(e[_ids['INJURIES']]) == list:
                temp = ''
                for i in e[_ids['INJURIES']]:
                    temp = temp + i + ','

                if len(temp) > 0:
                    temp = temp[:-1]

                ENROLMENT_DATA_ENTITY['injuries'] = temp
        except Exception as ex:
            print(f'Error - INJURIES {ex}')
            logger.error(f'Error - INJURIES {ex}')

    #     Signatures
        try:
            ENROLMENT_DATA_ENTITY['primarySignatureURL'] = e[_ids['SIGNATURE']]
        except Exception as ex:
            print(f'Error - SIGNATURE {ex}')
            logger.error(f'Error - SIGNATURE {ex}')

        try:
            ENROLMENT_DATA_ENTITY['paymentAuthSignatureURL'] = e[_ids['PAYMENT_AUTHORISATION_SIGNATURE']]
        except Exception as ex:
            print(f'Error - PAYMENT_AUTHORISATION_SIGNATURE {ex}')
            logger.error(f'Error - PAYMENT_AUTHORISATION_SIGNATURE {ex}')

        try:
            ENROLMENT_DATA_ENTITY['under18SignatureURL'] = e[_ids['PARENT_GUARDIAN_SIGNATURE']]
        except Exception as ex:
            print(f'Error - PARENT_GUARDIAN_SIGNATURE {ex}')
            logger.error(f'Error - PARENT_GUARDIAN_SIGNATURE {ex}')

        # ###################################################
        #     REMEMBER ACCESS KEY NUMBER                    #
        # ###################################################
        try:
            site_code = source_get_request(Constants.SOURCE_GET_ACCESS_KEY_SITE_CODE + locationId)['siteCode']
            ENROLMENT_DATA_ENTITY['accessKeyNumber'] = site_code + e[_ids['ACCESS_KEY_NUMBER']]
        except Exception as ex:
            print(f'Error - ACCESS_KEY_NUMBER: {ex}')
            logger.error(f'Error - ACCESS_KEY_NUMBER: {ex}')

    #     AGREEMENTS
        agreement = ''
        try:
    #         print(e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]])
            if e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]] is not None and type(e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]]) == list and len(e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]]) > 0:
                agreement = agreement + e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]][0] + ' | '
        except Exception as ex:
            print(f'Error - MEMBERSHIP_TERMS_AGREEMENT: {ex}')
            logger.error(f'Error - MEMBERSHIP_TERMS_AGREEMENT: {ex}')

        try:
    #         print(e[_ids["PT_6_SESSION_COMMITMENT"]])
            if e[_ids["PT_6_SESSION_COMMITMENT"]] is not None and type(e[_ids["PT_6_SESSION_COMMITMENT"]]) == list and len(e[_ids["PT_6_SESSION_COMMITMENT"]]) > 0:
                agreement = agreement + e[_ids["PT_6_SESSION_COMMITMENT"]][0] + ' | '
        except Exception as ex:
            print(f'Error - PT_6_SESSION_COMMITMENT: {ex}')
            logger.error(f'Error - PT_6_SESSION_COMMITMENT: {ex}')

        try:
    #         print(e[_ids["LIFESTYLE_PT_COMMITMENT"]])
            if e[_ids["LIFESTYLE_PT_COMMITMENT"]] is not None and type(e[_ids["LIFESTYLE_PT_COMMITMENT"]]) == list and len(e[_ids["LIFESTYLE_PT_COMMITMENT"]]) > 0:
                agreement = agreement + e[_ids["LIFESTYLE_PT_COMMITMENT"]][0] + ' | '
        except Exception as ex:
            print(f'Error - LIFESTYLE_PT_COMMITMENT: {ex}')
            logger.error(f'Error - LIFESTYLE_PT_COMMITMENT: {ex}')

        try:
    #         print(e[_ids["ANNUAL_CONTRACT_COMMITMENT"]])
            if e[_ids["ANNUAL_CONTRACT_COMMITMENT"]] is not None and type(e[_ids["ANNUAL_CONTRACT_COMMITMENT"]]) == list and len(e[_ids["ANNUAL_CONTRACT_COMMITMENT"]]) > 0:
                agreement = agreement + e[_ids["ANNUAL_CONTRACT_COMMITMENT"]][0] + ' | '
        except Exception as ex:
            print(f'Error - ANNUAL_CONTRACT_COMMITMENT: {ex}')
            logger.error(f'Error - ANNUAL_CONTRACT_COMMITMENT: {ex}')

        if len(agreement) > 3:
            agreement = agreement[:-3]

        ENROLMENT_DATA_ENTITY['agreement'] = agreement

        try:
            ENROLMENT_DATA_ENTITY['memberPhotoURL'] = e[_ids["MEMBER_PHOTO"]]
        except Exception as ex:
            print(f'Error - MEMBER_PHOTO: {ex}')
            logger.error(f'Error - MEMBER_PHOTO: {ex}')


        ENROLMENT_DATA_ENTITY['covidVerificationURL'] = None
        try:
            if _ids["COVID_VERIFICATION_UPLOAD"] is not None and e[_ids["COVID_VERIFICATION_UPLOAD"]] is not None and e[_ids["COVID_VERIFICATION_UPLOAD"]] != '':
                ENROLMENT_DATA_ENTITY['covidVerificationURL'] = e[_ids["COVID_VERIFICATION_UPLOAD"]]

        except Exception as e:
            print(f'Error - COVID_VERIFICATION_UPLOAD: {ex}')
            logger.error(f'Error - COVID_VERIFICATION_UPLOAD: {ex}')
                          
    return ENROLMENT_DATA_ENTITY
                     



def update_enrolment_data_entity_from_external_submission(e, ENROLMENT_DATA_ENTITY):
    
#     Set Utility Ids
    print('update_enrolment_data_entity_phone_external')
    logger.info('update_enrolment_data_entity_phone_external')
    _ids = FS_FIELD_IDS.FORM[e["FormID"]]
    
    ENROLMENT_DATA_ENTITY['fs_uniqueId'] = e['UniqueID']
    ENROLMENT_DATA_ENTITY['fs_formId'] = e['FormID']
    
#     Set Status
    ENROLMENT_DATA_ENTITY['status'] = MemberStatus.ENROLMENT_AUTHORISED[0]
    ENROLMENT_DATA_ENTITY['communicationsStatus'] = CommunicationsStatus.CLIENT_AUTHORISATION_RECEIVED[0]
    
    ENROLMENT_DATA_ENTITY['updateDate'] = datetime.datetime.now().strftime(Constants.DATETIME_FORMAT)
    
    try:
        ENROLMENT_DATA_ENTITY['useExistingDetails'] = e[_ids['ARE_YOU_PAYING_FOR_THE_MEMBERSHIP']]
    except Exception as ex:
        print(f'Error - ARE_YOU_PAYING_FOR_THE_MEMBERSHIP: {ex}')
        logger.error(f'Error - ARE_YOU_PAYING_FOR_THE_MEMBERSHIP: {ex}')

#     Billing Authorisation
    try:
        if type(e[_ids["PAYMENT_AUTHORISATION"]]) == list:
            ENROLMENT_DATA_ENTITY['getAuthorisation'] = e[_ids["PAYMENT_AUTHORISATION"]][0]
        else:
            ENROLMENT_DATA_ENTITY['getAuthorisation'] = e[_ids["PAYMENT_AUTHORISATION"]]
    except Exception as ex:
        print(f'Error - PAYMENT_AUTHORISATION: {ex}')
        logger.error(f'Error - PAYMENT_AUTHORISATION: {ex}')
    
#     Payment Method
    try:
        ENROLMENT_DATA_ENTITY['paymentType'] = e[_ids["PAYMENT_METHOD"]]
    except Exception as ex:
        print(f'Error - PAYMENT_METHOD: {ex}')
        logger.error(f'Error - PAYMENT_METHOD: {ex}')
                
#     IF Credit Card
    if e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_CREDIT_CARD:
        ENROLMENT_DATA_ENTITY['memberBankDetail'] = None

        MemberCreditCard = {}
        #     Billing Details
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
                MemberCreditCard['address'] = e[_ids['ADDRESS']]['address']
                MemberCreditCard['city'] = e[_ids['ADDRESS']]['city']
                MemberCreditCard['state'] = e[_ids['ADDRESS']]['state']
                MemberCreditCard['postcode'] = e[_ids['ADDRESS']]['zip']
            except Exception as ex:
                print(f'Error - BILLING_NAME & BILLING_ADDRESS: {ex}')
                logger.error(f'Error - BILLING_NAME & BILLING_ADDRESS: {ex}')
        
        try:
            MemberCreditCard['number'] = e[_ids['CREDIT_CARD']]['card']
            MemberCreditCard['expMonth'] = e[_ids['CREDIT_CARD']]['cardexp'].split('/')[0]
            MemberCreditCard['expYear'] = '20' + e[_ids['CREDIT_CARD']]['cardexp'].split('/')[1]
            MemberCreditCard['verificationCode'] = e[_ids['CREDIT_CARD']]['cvv']
        
            ENROLMENT_DATA_ENTITY['memberCreditCard'] = MemberCreditCard
        except Exception as ex:
            print(f'Error - CREDIT_CARD: {ex}')
            logger.error(f'Error - CREDIT_CARD: {ex}')

#     IF Bank Account
    if e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_BANK_ACCOUNT or e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_DIRECT_DEBIT:
        ENROLMENT_DATA_ENTITY['memberCreditCard'] = None
        
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
                
        ENROLMENT_DATA_ENTITY['memberBankDetail'] = MemberBankDetail

#   Auxiliry Data
    try:
        ENROLMENT_DATA_ENTITY['numberOneGoal'] = e[_ids['NUMBER_ONE_GOAL']]
    except Exception as ex:
        print(f'Error - NUMBER_ONE_GOAL: {ex}')
        logger.error(f'Error - NUMBER_ONE_GOAL: {ex}')
        
    try:
        if e[_ids['MEDICAL']] is not None and type(e[_ids['MEDICAL']]) == list:
            temp = ''
            for m in e[_ids['MEDICAL']]:
                temp = temp + m + ','
            
            if len(temp) > 0:
                temp = temp[:-1]

            ENROLMENT_DATA_ENTITY['medical'] = temp
    except Exception as ex:
        print(f'Error - MEDICAL: {ex}')
        logger.error(f'Error - MEDICAL: {ex}')
    
    try:
        if e[_ids['TRAINING_AVAILABILITY']] is not None and type(e[_ids['TRAINING_AVAILABILITY']]) == list:
            temp = ''
            for i in e[_ids['TRAINING_AVAILABILITY']]:
                temp = temp + i + ','
            
            if len(temp) > 0:
                temp = temp[:-1]

            ENROLMENT_DATA_ENTITY['trainingAvailability'] = temp
    except Exception as ex:
        print(f'Error - TRAINING_AVAILABILITY: {ex}')
        logger.error(f'Error - TRAINING_AVAILABILITY: {ex}')

    try:
        if e[_ids['TIME_AVAILABILITY']] is not None and type(e[_ids['TIME_AVAILABILITY']]) == list:
            temp = ''
            for i in e[_ids['TIME_AVAILABILITY']]:
                temp = temp + i + ','

            if len(temp) > 0:
                temp = temp[:-1]

            ENROLMENT_DATA_ENTITY['timeAvailability'] = temp
    except Exception as ex:
        print(f'Error - TIME_AVAILABILITY: {ex}')
        logger.error(f'Error - TIME_AVAILABILITY: {ex}')

#     Medical Data
    try:
        ENROLMENT_DATA_ENTITY['medicalClearance'] = e[_ids['MEDICAL_CLEARANCE']]
    except Exception as ex:
        print(f'Error - MEDICAL_CLEARANCE: {ex}')
        logger.error(f'Error - MEDICAL_CLEARANCE: {ex}')
    
    try:
        if e[_ids['INJURIES']] is not None and type(e[_ids['INJURIES']]) == list:
            temp = ''
            for i in e[_ids['INJURIES']]:
                temp = temp + i + ','
            
            if len(temp) > 0:
                temp = temp[:-1]

            ENROLMENT_DATA_ENTITY['injuries'] = temp
    except Exception as ex:
        print(f'Error - INJURIES: {ex}')
        logger.error(f'Error - INJURIES: {ex}')

#     Signatures
    try:
        ENROLMENT_DATA_ENTITY['primarySignatureURL'] = e[_ids['SIGNATURE']]
    except Exception as ex:
        print(f'Error - SIGNATURE: {ex}')
        logger.error(f'Error - SIGNATURE: {ex}')
    
    try:
        ENROLMENT_DATA_ENTITY['paymentAuthSignatureURL'] = e[_ids['PAYMENT_AUTHORISATION_SIGNATURE']]
    except Exception as ex:
        print(f'Error - PAYMENT_AUTHORISATION_SIGNATURE: {ex}')
        logger.error(f'Error - PAYMENT_AUTHORISATION_SIGNATURE: {ex}')
    
    try:
        ENROLMENT_DATA_ENTITY['under18SignatureURL'] = e[_ids['PARENT_GUARDIAN_SIGNATURE']]
    except Exception as ex:
        print(f'Error - PARENT_GUARDIAN_SIGNATURE: {ex}')
        logger.error(f'Error - PARENT_GUARDIAN_SIGNATURE: {ex}')
    
    
#     Primary Details - These fields are mutable in the Part 2 External Submission
#                     - This will over-right the Internal Submission
    try:
        ENROLMENT_DATA_ENTITY['phone'] = format_phone(e[_ids['PHONE']])
    except Exception as ex:
        print(f'Error - PHONE: {ex}')
        logger.error(f'Error - PHONE: {ex}')
         
    try:
        ENROLMENT_DATA_ENTITY['firstName'] = e[_ids['NAME']]['first'].strip().title()
    except Exception as ex:
        print(f'Error - NAME FIRST: {ex}')
        logger.error(f'Error - NAME FIRST: {ex}')
                     
    try:
        ENROLMENT_DATA_ENTITY['lastName'] = e[_ids['NAME']]['last'].strip().title()
    except Exception as ex:
        print(f'Error - NAME LAST: {ex}')
        logger.error(f'Error - NAME LAST: {ex}')

    try:
        ENROLMENT_DATA_ENTITY['email'] = e[_ids['EMAIL']].strip()
    except Exception as ex:
        print(f'Error - EMAIL: {ex}')
        logger.error(f'Error - EMAIL: {ex}')

#    Member Details
    try:
        ENROLMENT_DATA_ENTITY['address1'] = e[_ids['ADDRESS']]['address'].strip().title()
        ENROLMENT_DATA_ENTITY['city'] = e[_ids['ADDRESS']]['city'].strip().title()
        ENROLMENT_DATA_ENTITY['state'] = e[_ids['ADDRESS']]['state'].strip().upper()
        ENROLMENT_DATA_ENTITY['postcode'] = e[_ids['ADDRESS']]['zip'].strip().title()
    except Exception as ex:
        print(f'Error - ADDRESS: {ex}')
        logger.error(f'Error - ADDRESS: {ex}')

    try:
        ENROLMENT_DATA_ENTITY['dob'] = pd.to_datetime(e[_ids['DATE_OF_BIRTH']]).strftime('%Y-%m-%d')
    except Exception as ex:
        print(f'Error - DATE_OF_BIRTH: {ex}')
        logger.error(f'Error - DATE_OF_BIRTH: {ex}')

    try:
        ENROLMENT_DATA_ENTITY['gender'] = e[_ids['GENDER']].lower()
    except Exception as ex:
        print(f'Error - GENDER: {ex}')
        logger.error(f'Error - GENDER: {ex}')

    try:
        ENROLMENT_DATA_ENTITY['emergencyContactName'] = e[_ids['EMERGENCY_CONTACT_NAME']].strip().title()
    except Exception as ex:
        print(f'Error - EMERGENCY_CONTACT_NAME: {ex}')
        logger.error(f'Error - EMERGENCY_CONTACT_NAME: {ex}')

    try:
        ENROLMENT_DATA_ENTITY['emergencyContactPhone'] = format_phone(e[_ids['EMERGENCY_CONTACT_PHONE']])
    except Exception as ex:
        print(f'Error - EMERGENCY_CONTACT_PHONE: {ex}')
        logger.error(f'Error - EMERGENCY_CONTACT_PHONE: {ex}')

#     Notes
    ENROLMENT_DATA_ENTITY['notes'] = e[_ids['NOTES']]
    

#     AGREEMENTS
    agreement = ''
    try:
#         print(e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]])
        if e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]] is not None and type(e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]]) == list and len(e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]]) > 0:
            agreement = agreement + e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]][0] + ' | '
    except Exception as ex:
        print(f'Error - MEMBERSHIP_TERMS_AGREEMENT: {ex}')
        logger.error(f'Error - MEMBERSHIP_TERMS_AGREEMENT: {ex}')

    try:
#         print(e[_ids["PT_6_SESSION_COMMITMENT"]])
        if e[_ids["PT_6_SESSION_COMMITMENT"]] is not None and type(e[_ids["PT_6_SESSION_COMMITMENT"]]) == list and len(e[_ids["PT_6_SESSION_COMMITMENT"]]) > 0:
            agreement = agreement + e[_ids["PT_6_SESSION_COMMITMENT"]][0] + ' | '
    except Exception as ex:
        print(f'Error - PT_6_SESSION_COMMITMENT: {ex}')
        logger.error(f'Error - PT_6_SESSION_COMMITMENT: {ex}')

    try:
#         print(e[_ids["LIFESTYLE_PT_COMMITMENT"]])
        if e[_ids["LIFESTYLE_PT_COMMITMENT"]] is not None and type(e[_ids["LIFESTYLE_PT_COMMITMENT"]]) == list and len(e[_ids["LIFESTYLE_PT_COMMITMENT"]]) > 0:
            agreement = agreement + e[_ids["LIFESTYLE_PT_COMMITMENT"]][0] + ' | '
    except Exception as ex:
        print(f'Error - LIFESTYLE_PT_COMMITMENT: {ex}')
        logger.error(f'Error - LIFESTYLE_PT_COMMITMENT: {ex}')

    try:
#         print(e[_ids["ANNUAL_CONTRACT_COMMITMENT"]])
        if e[_ids["ANNUAL_CONTRACT_COMMITMENT"]] is not None and type(e[_ids["ANNUAL_CONTRACT_COMMITMENT"]]) == list and len(e[_ids["ANNUAL_CONTRACT_COMMITMENT"]]) > 0:
            agreement = agreement + e[_ids["ANNUAL_CONTRACT_COMMITMENT"]][0] + ' | '
    except Exception as ex:
        print(f'Error - ANNUAL_CONTRACT_COMMITMENT: {ex}')
        logger.error(f'Error - ANNUAL_CONTRACT_COMMITMENT: {ex}')

    if len(agreement) > 3:
        agreement = agreement[:-3]

    ENROLMENT_DATA_ENTITY['agreement'] = agreement

    try:
        ENROLMENT_DATA_ENTITY['memberPhotoURL'] = e[_ids["MEMBER_PHOTO"]]
    except Exception as ex:
        print(f'Error - MEMBER_PHOTO: {ex}')
        logger.error(f'Error - MEMBER_PHOTO: {ex}')

    try:
        if e[_ids["HAS_REFERRAL"]] is not None and type(e[_ids["HAS_REFERRAL"]]) == list and len(e[_ids["HAS_REFERRAL"]]) > 0:
            ENROLMENT_DATA_ENTITY['hasReferral'] = e[_ids["HAS_REFERRAL"]][0].lower() == 'yes'
        else:
            ENROLMENT_DATA_ENTITY['hasReferral'] = False
    except Exception as ex:
        print(f"Error - HAS_REFERRAL {ex}")
        logger.error(f"Error - HAS_REFERRAL {ex}")
        ENROLMENT_DATA_ENTITY['hasReferral'] = False

    try:
        ENROLMENT_DATA_ENTITY['referralName'] = e[_ids["REFERRAL_NAME"]]
    except Exception as ex:
        print(f"Error - REFERRAL_NAME {ex}")
        logger.error(f"Error - REFERRAL_NAME {ex}")

    try:
        ENROLMENT_DATA_ENTITY['referralEmail'] = e[_ids["REFERRAL_EMAIL"]]
    except Exception as ex:
        print(f"Error - REFERRAL_EMAIL {ex}")
        logger.error(f"Error - REFERRAL_EMAIL {ex}")

    try:
        ENROLMENT_DATA_ENTITY['referralPhone'] = e[_ids["REFERRAL_PHONE"]]
    except Exception as ex:
        print(f"Error - REFERRAL_PHONE {ex}")
        logger.error(f"Error - REFERRAL_PHONE {ex}")

    ENROLMENT_DATA_ENTITY['covidVerificationURL'] = None
    try:
        if _ids["COVID_VERIFICATION_UPLOAD"] is not None and e[_ids["COVID_VERIFICATION_UPLOAD"]] is not None and e[_ids["COVID_VERIFICATION_UPLOAD"]] != '':
            ENROLMENT_DATA_ENTITY['covidVerificationURL'] = e[_ids["COVID_VERIFICATION_UPLOAD"]]

    except Exception as e:
        print(f'Error - COVID_VERIFICATION_UPLOAD: {ex}')
        logger.error(f'Error - COVID_VERIFICATION_UPLOAD: {ex}')
    
    return ENROLMENT_DATA_ENTITY



def build_mbo_client_from_enrolmentData(ENROLMENT_DATA_ENTITY, is_duplicate_key, MBO_CLIENT = None):
#     print('\nbuild_mbo_client_from_enrolmentData')
    is_new_client = False
    SalesReps = []
    Liability = None
    staff = None
    # try:
    #     if ENROLMENT_DATA_ENTITY['staffMember'] is not None:
    #         staff = { "Id": int(ENROLMENT_DATA_ENTITY['staffMember'].split('::')[0]), "SalesRepNumber": 1 }
    #         SalesReps.append(staff)
    # except Exception as ex:
    #     print(f"Error - SalesRep: {ex}")
    #     logger.error(f"Error - SalesRep: {ex}")
    
    # if staff is not None:
    #     Liability = {
    #         "AgreementDate": ENROLMENT_DATA_ENTITY['createDate'].replace(' ','T'),
    #         "IsReleased": True,
    #         "ReleasedBy": staff
    #     }
        
    # try:
    #     if ENROLMENT_DATA_ENTITY['personalTrainer'] is not None:
    #         if int(ENROLMENT_DATA_ENTITY['personalTrainer'].split('::')[0]) > 0:
    #             coach = { "Id": int(ENROLMENT_DATA_ENTITY['personalTrainer'].split('::')[0]), "SalesRepNumber": 2 }
    #             SalesReps.append(coach)
    # except Exception as ex:
    #     print(f"Error - SalesRep: {ex}")
    #     logger.error(f"Error - SalesRep: {ex}")
    
    if len(SalesReps) == 0:
        SalesReps = None

    if Constants.IS_TEST:
        print(f"SalesReps: {SalesReps}")
        logger.info(f"SalesReps: {SalesReps}")

    ClientCreditCard = None
    if ENROLMENT_DATA_ENTITY['memberCreditCard'] is not None:

        try:
            Address = ENROLMENT_DATA_ENTITY['memberCreditCard']['address']
        except Exception as ex:
            print(f"Error - Address: {ex}")
            logger.error(f"Error - Address: {ex}")
            Address = None
            
        try:
            CardHolder = ENROLMENT_DATA_ENTITY['memberCreditCard']['holder']
        except Exception as ex:
            print(f"Error - CardHolder: {ex}")
            logger.error(f"Error - CardHolder: {ex}")
            CardHolder = None
            
        try:
            CardNumber = ENROLMENT_DATA_ENTITY['memberCreditCard']['number']
        except Exception as ex:
            print(f"Error - CardNumber: {ex}")
            logger.error(f"Error - CardNumber: {ex}")
            CardNumber = None
            
        try:
            City = ENROLMENT_DATA_ENTITY['memberCreditCard']['city']
        except Exception as ex:
            print(f"Error - City: {ex}")
            logger.error(f"Error - City: {ex}")
            City = None
            
        try:
            ExpMonth = ENROLMENT_DATA_ENTITY['memberCreditCard']['expMonth']
        except Exception as ex:
            print(f"Error - ExpMonth: {ex}")
            logger.error(f"Error - ExpMonth: {ex}")
            ExpMonth = None
            
        try:
            ExpYear = ENROLMENT_DATA_ENTITY['memberCreditCard']['expYear']
        except Exception as ex:
            print(f"Error - ExpYear: {ex}")
            logger.error(f"Error - ExpYear: {ex}")
            ExpYear = None
            
        try:
            PostalCode = ENROLMENT_DATA_ENTITY['memberCreditCard']['postcode']
        except Exception as ex:
            print(f"Error - PostalCode: {ex}")
            logger.error(f"Error - PostalCode: {ex}")
            PostalCode = None
            
        try:
            State = ENROLMENT_DATA_ENTITY['memberCreditCard']['state']
        except Exception as ex:
            print(f"Error - State: {ex}")
            logger.error(f"Error - State: {ex}")
            State = None

        ClientCreditCard = {
            "Address": Address,
            "CardHolder": CardHolder,
            "CardNumber": CardNumber,
            "City": City,
            "ExpMonth": ExpMonth,
            "ExpYear": ExpYear,
            "PostalCode": PostalCode,
            "State": State
        }

    if MBO_CLIENT is None:
        is_new_client = True
        MBO_CLIENT = {
            "Test": Constants.IS_TEST,
            "SalesReps": SalesReps,
            "SendAccountEmails": True,
            "SendAccountTexts": True,
            "SendPromotionalEmails": True,
            "SendPromotionalTexts": True,
            "SendScheduleEmails": True,
            "SendScheduleTexts": True
        }
        if ~is_duplicate_key:
            MBO_CLIENT["NewId"] = ENROLMENT_DATA_ENTITY['accessKeyNumber']
        
        try:
            if ENROLMENT_DATA_ENTITY['gender'] is not None: 
                if ENROLMENT_DATA_ENTITY['gender'].lower() == 'male' or  ENROLMENT_DATA_ENTITY['gender'].lower() == 'female':
                    MBO_CLIENT["Gender"] = ENROLMENT_DATA_ENTITY['gender']
        except Exception as ex:
            print('Error - Gender')
            logger.error('Error - Gender')

    MBO_CLIENT["BirthDate"] = ENROLMENT_DATA_ENTITY['dob']
    MBO_CLIENT["Country"] = "AU"
    MBO_CLIENT["ClientCreditCard"] = ClientCreditCard
    MBO_CLIENT["FirstName"] = ENROLMENT_DATA_ENTITY['firstName']
    MBO_CLIENT["IsCompany"] = False
    MBO_CLIENT["IsProspect"] = False
    MBO_CLIENT["LastName"] = ENROLMENT_DATA_ENTITY['lastName']
    MBO_CLIENT["Liability"] = Liability
    MBO_CLIENT["LiabilityRelease"] = True
    MBO_CLIENT["Notes"] = ENROLMENT_DATA_ENTITY['notes']
    MBO_CLIENT["State"] = ENROLMENT_DATA_ENTITY['state']
    MBO_CLIENT["Email"] = ENROLMENT_DATA_ENTITY['email']
    MBO_CLIENT["MobilePhone"] = ENROLMENT_DATA_ENTITY['phone']
    MBO_CLIENT["AddressLine1"] = ENROLMENT_DATA_ENTITY['address1']
    MBO_CLIENT["AddressLine2"] = ENROLMENT_DATA_ENTITY['address2']
    MBO_CLIENT["City"] = ENROLMENT_DATA_ENTITY['city']
    MBO_CLIENT["PostalCode"] = ENROLMENT_DATA_ENTITY['postcode']
    MBO_CLIENT["EmergencyContactInfoName"] = ENROLMENT_DATA_ENTITY['emergencyContactName']
    MBO_CLIENT["EmergencyContactInfoPhone"] = ENROLMENT_DATA_ENTITY['emergencyContactPhone']
    
    if is_new_client:
        logger.info(f'returning new MBO_CLIENT:\n\n{MBO_CLIENT}')
        print(f'returning new MBO_CLIENT: {MBO_CLIENT}')
        return MBO_CLIENT
    else: 
        if ~is_duplicate_key:
            NewId = ENROLMENT_DATA_ENTITY['accessKeyNumber']
        else:
            NewId = MBO_CLIENT['Id']
        
        return { 
            "Test": Constants.IS_TEST,
            "CrossRegionalUpdate": False,
            "NewId": NewId,
            "Client": MBO_CLIENT
        }





