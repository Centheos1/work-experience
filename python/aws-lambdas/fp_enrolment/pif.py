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
import smtplib
from os.path import basename
from email.mime.application import MIMEApplication
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email.utils import COMMASPACE, formatdate
from email import encoders
import math

# from fp_enrolment.constants import *
# from fp_enrolment.helpers import *
# from fp_enrolment.source_api import *
from constants import *
from helpers import *
from source_api import *

# # #######################################################
# # PIF                                                   #
# # #######################################################

def build_pif_enrolment_data(e, member_status = None, communications_status = None):
    logger.info("build_PIF_enrolment_data")
    
    _ids = FS_FIELD_IDS.FORM[e["FormID"]]
    
    ENROLMENT_DATA_ENTITY = {}
    
    #     Formstack Ids
    ENROLMENT_DATA_ENTITY['fs_uniqueId'] = e['UniqueID']
    ENROLMENT_DATA_ENTITY['fs_formId'] = e['FormID']
    
    #     User Id
#     ENROLMENT_DATA_ENTITY['UID'] = Constants.SOURCE_UID
    
#     Marketting Ids
    ENROLMENT_DATA_ENTITY['googleClickId'] = None
    ENROLMENT_DATA_ENTITY['facebookCampaignId'] = None
    
    ENROLMENT_DATA_ENTITY['existingClient'] = None
    ENROLMENT_DATA_ENTITY['gymSalesId'] = None
    
    #     MBO Location Id
    try:
        if type(e[_ids["MEMBERSHIP_CONSULTANT_LOCATION"]]) == list:
            locationId = e[_ids["MEMBERSHIP_CONSULTANT_LOCATION"]][0]['value']
            ENROLMENT_DATA_ENTITY['locationId'] = locationId
            ENROLMENT_DATA_ENTITY['gymName'] = e[_ids["MEMBERSHIP_CONSULTANT_LOCATION"]][0]['label']
        else:
            locationId = e[_ids["MEMBERSHIP_CONSULTANT_LOCATION"]]
    except Exception as ex:
        logger.error(f'Error - locationId: {ex}')
    
#     Metadata
    ENROLMENT_DATA_ENTITY['createDate'] = pd.to_datetime(e[_ids['SUBMISSION_DATETIME']]).strftime(Constants.DATETIME_FORMAT)
    ENROLMENT_DATA_ENTITY['updateDate'] = datetime.datetime.now().strftime(Constants.DATETIME_FORMAT)
    
#     Set Status
#     print(member_status)
    if member_status is not None:
        ENROLMENT_DATA_ENTITY['status'] = member_status
    
#     print(communications_status)
    if communications_status is not None:
        ENROLMENT_DATA_ENTITY['communicationsStatus'] = communications_status
    
#     Get MBO Contracts from Source Database
    mbo_contracts = source_get_request(Constants.SOURCE_GET_ALL_CONTRACTS_URL + locationId)
        
# ######################################################
# PROCESS PURCHASES                                    #
# ######################################################
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

#     try:
#         if e[_ids['FREE_TIME']] is not None and len(e[_ids['FREE_TIME']]) > 0:
#             if type(e[_ids['FREE_TIME']]) == list:
#                 free_time = 'First ' + e[_ids["FREE_TIME"]][0]['value'] + ' Days'
#     #                 free_time = e[_ids['FREE_TIME']][0]['label']
#             else:
#                 free_time = e[_ids['FREE_TIME']]

#             for c in mbo_contracts:
#                 if c['name'].strip() == free_time:
# #                     print(c['name'].strip())
#                     contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # 0.00 | "
#                     memberContracts = memberContracts + str(c['mboId']) + ','
#                     couponCode = couponCode + Constants.MBO_COUPON_CODE_N_DAYS_FREE + ',' 
#                     ENROLMENT_DATA_ENTITY['daysFree'] = free_time
#     except Exception as ex:
#         logger.error(f'Error - FREE_TIME: {ex}')
    
          
#     HANDLE ACCESS CARD SITE CODE
    try:
        if e[_ids['ACCESS_KEY_NUMBER']] is not None:
            site_code = source_get_request(Constants.SOURCE_GET_ACCESS_KEY_SITE_CODE + locationId)['siteCode']
            ENROLMENT_DATA_ENTITY['accessKeyNumber'] = site_code + e[_ids['ACCESS_KEY_NUMBER']]
        else:
            ENROLMENT_DATA_ENTITY['accessKeyNumber'] = None
    except Exception as ex:
        logger.error(f'Error - ACCESS_KEY_NUMBER: {ex}')
          
#         Access Key
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
        if (e[_ids['OPTIONS']] is not None and 'Access Key' in e[_ids['OPTIONS']]) or (e[_ids['IS_ACCESS_CARD']] is not None and e[_ids['IS_ACCESS_CARD']].lower() == 'yes'):
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

#     try:
#         if (e[_ids['OPTIONS']] is not None and 'Access Key' in e[_ids['OPTIONS']]) or (e[_ids['IS_ACCESS_CARD']] is not None and e[_ids['IS_ACCESS_CARD']].lower() == 'yes'):

#             if _ids['ACCESS_KEY_DISCOUNT'] is not None and e[_ids['ACCESS_KEY_DISCOUNT']] is not None and len(e[_ids['ACCESS_KEY_DISCOUNT']]) > 0:

#                 if e[_ids['ACCESS_KEY_DISCOUNT']] == list:
#                     access_key_label = e[_ids['ACCESS_KEY_DISCOUNT']][0]['label']
#                     access_key_value = e[_ids['ACCESS_KEY_DISCOUNT']][0]['value']
#                 else:
#                     access_key_label = e[_ids['ACCESS_KEY_DISCOUNT']]

#                 for c in mbo_contracts:
#                     if c['name'] == 'Access Key':
# #                         print(c['reoccuringPaymentAmountTotal'] + int(e[_ids['ACCESS_KEY_DISCOUNT']][0]['value']))
#                         access_key_price = c['reoccuringPaymentAmountTotal'] + int(e[_ids['ACCESS_KEY_DISCOUNT']][0]['value'])
#                         contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {access_key_price} | "    
# #                         print(contractNamesToBeActivated)
#                         memberContracts = memberContracts + str(c['mboId']) + ','
#                         if int(e[_ids['ACCESS_KEY_DISCOUNT']][0]['value']) + int(e[_ids['ACCESS_KEY_RETAIL_PRICE']]) == 0:
#                             couponCode = couponCode + Constants.MBO_COUPON_CODE_KEY_FREE + ','
#                         else:
#                             couponCode = couponCode + 'Key' + str(int(e[_ids['ACCESS_KEY_DISCOUNT']][0]['value']) + int(e[_ids['ACCESS_KEY_RETAIL_PRICE']])) + ','
#                         ENROLMENT_DATA_ENTITY['accessKeyDiscount'] = e[_ids['ACCESS_KEY_DISCOUNT']][0]['label']
#     except Exception as ex:
#         logger.error(f'Error - ACCESS_KEY: {ex}')
          
#     try:
#         if (e[_ids['FREE_PT_PACK']] is not None and len(e[_ids['FREE_PT_PACK']]) > 0):
#             for c in mbo_contracts:
#                 if c['name'].strip() == 'Packs: FP Coach Pack':
#                     memberContracts = memberContracts + str(c['mboId']) + ','
#                     contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # 0.00 | "
#                     ENROLMENT_DATA_ENTITY['freePTPack'] = e[_ids['FREE_PT_PACK']][0]
#                     couponCode = couponCode + Constants.MBO_COUPON_PT_PACK_FREE + ','

#     except Exception as ex:
#         logger.error(f'Error - FREE_PT_PACK: {ex}')

#   Two Free Sessions
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
        logger.error(f'Error - 2_FREE: {ex}')
    
#     Membership and PT Contracts
    try:
        if e[_ids["MEMBERSHIP_TYPE"]] is not None:
            ENROLMENT_DATA_ENTITY['gymOrPlay'] = e[_ids["MEMBERSHIP_TYPE"]].lower()
        else:
            ENROLMENT_DATA_ENTITY['gymOrPlay'] = None
    except Exception as ex:
        logger.error(f'Error - MEMBERSHIP_TYPE: {ex}')
                          
    ENROLMENT_DATA_ENTITY['ddOrPif'] = None
    ENROLMENT_DATA_ENTITY['noCommitment'] = None
    
    try:
        membership = None

# PIF GYM NEWTOWN
        if _ids['MEMBERSHIP_NAME_GYM_NT'] is not None and e[_ids['MEMBERSHIP_NAME_GYM_NT']] is not None and len(e[_ids['MEMBERSHIP_NAME_GYM_NT']]) > 0:
    #             print(f"MEMBERSHIP_NAME_GYM_NT: {e[_ids['MEMBERSHIP_NAME_GYM_NT']]}")
            if type(e[_ids['MEMBERSHIP_NAME_GYM_NT']]) == list:
                  membership = e[_ids['MEMBERSHIP_NAME_GYM_NT']][0]['label']
            else:
                  membership = e[_ids['MEMBERSHIP_NAME_GYM_NT']]

# PIF PLAY NEWTOWN
        if _ids['MEMBERSHIP_NAME_PLAY_NT'] is not None and e[_ids['MEMBERSHIP_NAME_PLAY_NT']] is not None and len(e[_ids['MEMBERSHIP_NAME_PLAY_NT']]) > 0:
    #             print(f"MEMBERSHIP_NAME_PLAY_NT: {e[_ids['MEMBERSHIP_NAME_PLAY_NT']]}")
            if type(e[_ids['MEMBERSHIP_NAME_PLAY_NT']]) == list:
                  membership = e[_ids['MEMBERSHIP_NAME_PLAY_NT']][0]['label']
            else:
                  membership = e[_ids['MEMBERSHIP_NAME_PLAY_NT']]

# PIF GYM MARRICKVILLE
        if _ids['MEMBERSHIP_NAME_GYM_MK'] is not None and e[_ids['MEMBERSHIP_NAME_GYM_MK']] is not None and len(e[_ids['MEMBERSHIP_NAME_GYM_MK']]) > 0:
    #             print(f"MEMBERSHIP_NAME_GYM_MK: {e[_ids['MEMBERSHIP_NAME_GYM_FP']]}")
            if type(e[_ids['MEMBERSHIP_NAME_GYM_MK']]) == list:
                  membership = e[_ids['MEMBERSHIP_NAME_GYM_MK']][0]['label']
            else:
                  membership = e[_ids['MEMBERSHIP_NAME_GYM_MK']]

# PIF PLAY MARRICKVILLE
        if _ids['MEMBERSHIP_NAME_PLAY_MK'] is not None and e[_ids['MEMBERSHIP_NAME_PLAY_MK']] is not None and len(e[_ids['MEMBERSHIP_NAME_PLAY_MK']]) > 0:
    #             print(f"MEMBERSHIP_NAME_PLAY_MK: {e[_ids['MEMBERSHIP_NAME_PLAY_MK']]}")
            if type(e[_ids['MEMBERSHIP_NAME_PLAY_MK']]) == list:
                  membership = e[_ids['MEMBERSHIP_NAME_PLAY_MK']][0]['label']
            else:
                  membership = e[_ids['MEMBERSHIP_NAME_PLAY_MK']]

        if _ids['MEMBERSHIP_NAME_PERFORM_MK'] is not None and e[_ids['MEMBERSHIP_NAME_PERFORM_MK']] is not None and len(e[_ids['MEMBERSHIP_NAME_PERFORM_MK']]) > 0:
    #             print(f"MEMBERSHIP_NAME_PERFORM_MK: {e[_ids['MEMBERSHIP_NAME_PERFORM_MK']]}")
            if type(e[_ids['MEMBERSHIP_NAME_PERFORM_MK']]) == list:
                  membership = e[_ids['MEMBERSHIP_NAME_PERFORM_MK']][0]['label']
            else:
                  membership = e[_ids['MEMBERSHIP_NAME_PERFORM_MK']]

# PIF GYM SURRY HILLS
        if _ids['MEMBERSHIP_NAME_GYM_SH'] is not None and e[_ids['MEMBERSHIP_NAME_GYM_SH']] is not None and len(e[_ids['MEMBERSHIP_NAME_GYM_SH']]) > 0:
    #             print(f"MEMBERSHIP_NAME_GYM_SH: {e[_ids['MEMBERSHIP_NAME_GYM_SH']]}")
            if type(e[_ids['MEMBERSHIP_NAME_GYM_SH']]) == list:
                  membership = e[_ids['MEMBERSHIP_NAME_GYM_SH']][0]['label']
            else:
                  membership = e[_ids['MEMBERSHIP_NAME_GYM_SH']]

# PIF PLAY SURRY HILLS
        if _ids['MEMBERSHIP_NAME_PLAY_SH'] is not None and e[_ids['MEMBERSHIP_NAME_PLAY_SH']] is not None and len(e[_ids['MEMBERSHIP_NAME_PLAY_SH']]) > 0:
    #             print(f"MEMBERSHIP_NAME_PLAY_SH: {e[_ids['MEMBERSHIP_NAME_PLAY_SH']]}")
            if type(e[_ids['MEMBERSHIP_NAME_PLAY_SH']]) == list:
                membership = e[_ids['MEMBERSHIP_NAME_PLAY_SH']][0]['label']
            else:
                membership = e[_ids['MEMBERSHIP_NAME_PLAY_SH']]

# PIF GYM BUNKER
        if _ids['MEMBERSHIP_NAME_GYM_BK'] is not None and e[_ids['MEMBERSHIP_NAME_GYM_BK']] is not None and len(e[_ids['MEMBERSHIP_NAME_GYM_BK']]) > 0:
    #             print(f"MEMBERSHIP_NAME_GYM_BK: {e[_ids['MEMBERSHIP_NAME_GYM_BK']]}")
            if type(e[_ids['MEMBERSHIP_NAME_GYM_BK']]) == list:
                  membership = e[_ids['MEMBERSHIP_NAME_GYM_BK']][0]['label']
            else:
                  membership = e[_ids['MEMBERSHIP_NAME_GYM_BK']]

# PIF PLAY BUNKER
        if _ids['MEMBERSHIP_NAME_PLAY_BK'] is not None and e[_ids['MEMBERSHIP_NAME_PLAY_BK']] is not None and len(e[_ids['MEMBERSHIP_NAME_PLAY_BK']]) > 0:
            # print(f"MEMBERSHIP_NAME_PLAY_BK: {e[_ids['MEMBERSHIP_NAME_PLAY_BK']]}")
            if type(e[_ids['MEMBERSHIP_NAME_PLAY_BK']]) == list:
                membership = e[_ids['MEMBERSHIP_NAME_PLAY_BK']][0]['label']
            else:
                membership = e[_ids['MEMBERSHIP_NAME_PLAY_BK']]

# CASUAL VISITS
        if _ids['CASUAL_VISITS_BK'] is not None and e[_ids['CASUAL_VISITS_BK']] is not None and len(e[_ids['CASUAL_VISITS_BK']]) > 0:
    #             print(f"CASUAL_VISITS_BK: {e[_ids['CASUAL_VISITS_BK']]}")
            if type(e[_ids['CASUAL_VISITS_BK']]) == list:
                membership = e[_ids['CASUAL_VISITS_BK']][0]['label']
            else:
                membership = e[_ids['CASUAL_VISITS_BK']]

        if _ids['CASUAL_VISITS_FP'] is not None and e[_ids['CASUAL_VISITS_FP']] is not None and len(e[_ids['CASUAL_VISITS_FP']]) > 0:
    #             print(f"CASUAL_VISITS_FP: {e[_ids['CASUAL_VISITS_FP']]}")
            if type(e[_ids['CASUAL_VISITS_FP']]) == list:
                membership = e[_ids['CASUAL_VISITS_FP']][0]['label']
            else:
                membership = e[_ids['CASUAL_VISITS_FP']]

        if Constants.IS_TEST:
              logger.info(f"membership: {membership}")

        if membership is not None:
            for c in mbo_contracts:
                if c['name'].strip() == membership:
                    # logger.info(f"{c['name']} | {round(float(c['reoccuringPaymentAmountTotal']),2)} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    memberContracts = memberContracts + str(c['mboId']) + ','
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {round(float(c['reoccuringPaymentAmountTotal']),2)}{get_payment_frequency_str(c)} | "
    except Exception as ex:
        logger.error(f'Error - membership: {ex}')

    training_pack = None
    try:
        logger.info(f"IS_GYM_ACCESS_REQUIRED: {e[_ids['IS_GYM_ACCESS_REQUIRED']]}")
    #      "IS_GYM_ACCESS_REQUIRED" : "96054641",

        if e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_FP']] is not None and len(e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_FP']]) > 0:
#             print(f"MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_FP: {e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_FP']]}")
            if type(e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_FP']]) == list:
                training_pack = e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_FP']][0]['label']
            else:
                training_pack = e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_FP']]

        if e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_BK']] is not None and len(e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_BK']]) > 0:
#             print(f"MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_BK: {e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_BK']]}")
            if type(e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_BK']]) == list:
                training_pack = e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_BK']][0]['label']
            else:
                training_pack = e[_ids['MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_BK']]

        logger.info(f"external_pt_gym_access: {training_pack}")
        if training_pack is not None:
            for c in mbo_contracts:
                if c['name'].strip() == training_pack:
#                     print(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
                    memberContracts = memberContracts + str(c['mboId']) + ','
                    contractNamesToBeActivated = contractNamesToBeActivated + f"{c['name']} # {round(float(c['reoccuringPaymentAmountTotal']),2)}{get_payment_frequency_str(c)} | "
    except Exception as ex:
        logger.error(f'Error - IS_GYM_ACCESS_REQUIRED: {ex}')
        
    
    if len(contractNamesToBeActivated) > 3:
        ENROLMENT_DATA_ENTITY['contractNamesToBeActivated'] = contractNamesToBeActivated[:-3]
    if len(memberContracts) > 1:
        ENROLMENT_DATA_ENTITY['memberContracts'] = memberContracts[:-1]
    if len(couponCode) > 1:
        ENROLMENT_DATA_ENTITY['couponCode'] = couponCode[:-1]

    ENROLMENT_DATA_ENTITY['ddOrPif'] = 'pif'
    ENROLMENT_DATA_ENTITY['noCommitment'] = 'noCommitment'
           
# ######################################################
# PROCESS SERVICES                                     #
# ######################################################       
    ENROLMENT_DATA_ENTITY['hasCoach'] = False
    ENROLMENT_DATA_ENTITY['serviceNamesToBeActivated'] = None
    # print(e[_ids["OPTIONS"]])
    try:
        pifOptions = ''
        for p in e[_ids["OPTIONS"]]:
#             print(p)
            pifOptions = pifOptions + p + ','
        
        if len(pifOptions) > 1:
            ENROLMENT_DATA_ENTITY['pifOptions'] = pifOptions[:-1]
    except Exception as ex:
        logger.error(f"Error - OPTIONS {ex}")
        
    if 'Coaching' in e[_ids["OPTIONS"]]:
        try:
            mbo_services = source_get_request(Constants.SOURCE_GET_ALL_SERVICES_URL + locationId)
            
            ENROLMENT_DATA_ENTITY['hasCoach'] = True

#             print(f"COACHING_MODALITY: {e[_ids['COACHING_MODALITY']]}")

            if 'external' in e[_ids['COACHING_MODALITY']].lower():
                session_length = e[_ids['SESSION_LENGTH_EXTERNAL']]
                unit_price = e[_ids['PRICE_PER_SESSION_EXTERNAL']]
                ENROLMENT_DATA_ENTITY['isExternalPt'] = True
                ENROLMENT_DATA_ENTITY['sessionLength'] = session_length
                ENROLMENT_DATA_ENTITY['externalPTSessionPrice'] = unit_price
                ENROLMENT_DATA_ENTITY['personalTrainingStartDate'] = ENROLMENT_DATA_ENTITY['createDate']
            else:
                session_length = e[_ids['SESSION_LENGTH']]
                unit_price = e[_ids['PRICE_PER_SESSION']]
                ENROLMENT_DATA_ENTITY['isExternalPt'] = False

            number_of_units = e[_ids['NUMBER_OF_SESSIONS']]

            if ENROLMENT_DATA_ENTITY['isExternalPt'] == True:
                service_name = f"PT External: {session_length.split(' ')[0].strip()} Min Session"
            else:
                service_name = f"PT: {session_length.split(' ')[0].strip()} Min Session"


            session_mbo_id = None
            for s in mbo_services:
                if s['name'] == service_name:
                    logger.info(f"name: {s['name']}")
                    session_mbo_id = s['mboId']

            service = f"{session_mbo_id} # {service_name} # {unit_price} # {number_of_units}"
    #          -> MBO Id # Name # Price Per Unit # Number of Units | ...
            ENROLMENT_DATA_ENTITY['serviceNamesToBeActivated'] = service

        except Exaception as ex:
            logger.error(f'Error - PT: {ex}')

    #     Training Starter Pack
    try:
        ENROLMENT_DATA_ENTITY['trainingStarterPack'] = e[_ids['COACHING_MODALITY']]
    except:
        logger.error('Error - TRAINING_OPTIONS')
        

# ######################################################
# STAFF DETAILS.                                       #
# ######################################################     
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

    #         print(f"{coach_name} {coach_mbo_id}")
        ENROLMENT_DATA_ENTITY['personalTrainer'] = coach_mbo_id
        ENROLMENT_DATA_ENTITY['personalTrainerName'] = coach_name
    except Exception as ex:
        logger.error(f'Error - COACH_NAME {ex}')
    
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

#         print(f"{membership_consultant_name} {membership_consultant_mbo_id}")
        ENROLMENT_DATA_ENTITY['staffMember'] = membership_consultant_mbo_id
        ENROLMENT_DATA_ENTITY['staffName'] = membership_consultant_name
    except Exception as ex:
        logger.error(f'Error - MEMBERSHIP_CONSULTANT_NAME {ex}')

    try:
        ENROLMENT_DATA_ENTITY['trainingPackageSoldBy'] = e[_ids["TRAINGING_PACKAGE_SOLD_BY"]]
    except Exception as ex:
        logger.error(f"Error TRAINGING_PACKAGE_SOLD_BY - {ex}")
        
    if ENROLMENT_DATA_ENTITY['trainingPackageSoldBy'] == 'Coach':
        ENROLMENT_DATA_ENTITY['trainingPackageConsultantLocationId'] = e[_ids['COACH_LOCATION']][0]['value']
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
            logger.error(f"Error TRAINING_PACKAGE_CONSULTANT - {ex}")

# ######################################################
# Primary Details.                                     #
# ###################################################### 
    try:
        ENROLMENT_DATA_ENTITY['phone'] = format_phone(e[_ids['PHONE']])
    except Exception as ex:
        logger.error(f'Error - PHONE: {ex}')
         
    try:
        ENROLMENT_DATA_ENTITY['firstName'] = e[_ids['NAME']]['first'].strip().title()
    except Exception as ex:
        logger.error(f'Error - NAME FIRST: {ex}')
                     
    try:
        ENROLMENT_DATA_ENTITY['lastName'] = e[_ids['NAME']]['last'].strip().title()
    except Exception as ex:
        logger.error(f'Error - NAME LAST: {ex}')

    try:
        ENROLMENT_DATA_ENTITY['email'] = e[_ids['EMAIL']].strip()
    except Exception as ex:
        logger.error(f'Error - EMAIL: {ex}')


# ######################################################
# AUX.                                                 #
# ###################################################### 

    try:
        if e[_ids["START_DATE"]] is not None and e[_ids["START_DATE"]] != '':
            ENROLMENT_DATA_ENTITY['startDate'] = pd.to_datetime(e[_ids["START_DATE"]]).strftime(Constants.DATETIME_FORMAT)
    except Exception as ex:
        logger.error(f"Error - START_DATE: {ex}")
        
    try:
        if e[_ids["FIRST_DEBIT_DATE"]] is not None and e[_ids["FIRST_DEBIT_DATE"]] != '':
            ENROLMENT_DATA_ENTITY['firstBillingDate'] = pd.to_datetime(e[_ids["FIRST_DEBIT_DATE"]]).strftime(Constants.DATETIME_FORMAT)
    except Exception as ex:
        logger.error(f"Error - FIRST_DEBIT_DATE: {ex}")
        
    try:
        ENROLMENT_DATA_ENTITY['notes'] = e[_ids['NOTES']]
    except Exception as ex:
        logger.error(f"Error - NOTES: {ex}")
        
    try:
        ENROLMENT_DATA_ENTITY['renewalStatus'] = e[_ids['RENEWAL_STATUS']]
    except Exception as ex:
        logger.error(f"Error - RENEWAL_STATUS: {ex}")
    
# ######################################################
# PAYMENT                                              #
# ######################################################        
    #     Billing Authorisation
    try:
        if type(e[_ids["PAYMENT_AUTHORISATION"]]) == list:
            if len(e[_ids["PAYMENT_AUTHORISATION"]]) > 0:
                ENROLMENT_DATA_ENTITY['getAuthorisation'] = e[_ids["PAYMENT_AUTHORISATION"]][0]
            else:
                ENROLMENT_DATA_ENTITY['getAuthorisation'] = None
        else:
            ENROLMENT_DATA_ENTITY['getAuthorisation'] = e[_ids["PAYMENT_AUTHORISATION"]]
    except Exception as ex:
        logger.error(f'Error - PAYMENT_AUTHORISATION: {ex}')
    
    try:
        ENROLMENT_DATA_ENTITY['paymentAuthSignatureURL'] = e[_ids['PAYMENT_AUTHORISATION_SIGNATURE']]
    except Exception as ex:
        logger.error(f"Error - PAYMENT_AUTHORISATION_SIGNATURE: {ex}")
    
#     Payment Method
    try:
        if e[_ids["PAYMENT_METHOD"]] is not None and 'useexistingdetails' in e[_ids["PAYMENT_METHOD"]].replace(' ','').lower():
            ENROLMENT_DATA_ENTITY['paymentType'] = 'Existing ' + e[_ids["EXISTING_PAYMENT_METHOD"]]
        else:
            ENROLMENT_DATA_ENTITY['paymentType'] = e[_ids["PAYMENT_METHOD"]]
        
    except Exception as ex:
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
                logger.error(f'Error - BILLING_NAME & BILLING_ADDRESS: {ex}')
        else:
            try:
                MemberCreditCard['holder'] = e[_ids['NAME']]['first'].strip().title() + ' ' + e[_ids['NAME']]['last'].strip().title()
#                 MemberCreditCard['address'] = e[_ids['ADDRESS']]['address']
#                 MemberCreditCard['city'] = e[_ids['ADDRESS']]['city']
#                 MemberCreditCard['state'] = e[_ids['ADDRESS']]['state']
#                 MemberCreditCard['postcode'] = e[_ids['ADDRESS']]['zip']
            except Exception as ex:
                logger.error(f'Error - BILLING_NAME & BILLING_ADDRESS: {ex}')
        
        try:
            MemberCreditCard['number'] = e[_ids['CREDIT_CARD']]['card']
            MemberCreditCard['expMonth'] = e[_ids['CREDIT_CARD']]['cardexp'].split('/')[0]
            MemberCreditCard['expYear'] = '20' + e[_ids['CREDIT_CARD']]['cardexp'].split('/')[1]
            MemberCreditCard['verificationCode'] = e[_ids['CREDIT_CARD']]['cvv']
        
            ENROLMENT_DATA_ENTITY['memberCreditCard'] = MemberCreditCard
        except Exception as ex:
            logger.error(f'Error - CREDIT_CARD: {ex}')

#     IF Bank Account
    if e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_BANK_ACCOUNT or e[_ids['PAYMENT_METHOD']] == Constants.FS_PAYMENT_TYPE_DIRECT_DEBIT:
        ENROLMENT_DATA_ENTITY['memberCreditCard'] = None
        
        MemberBankDetail = {}
        
        if e[_ids['ARE_YOU_PAYING_FOR_THE_MEMBERSHIP']] == 'No':
            try:
                MemberBankDetail['accountHolderName'] = e[_ids['BILLING_NAME']]['first'].strip().title() + ' ' + e[_ids['BILLING_NAME']]['last'].strip().title()
            except Exception as ex:
                logger.error(f'Error - BILLING_NAME: {ex}')
        else:
            try:
                MemberBankDetail['accountHolderName'] = e[_ids['NAME']]['first'].strip().title() + ' ' + e[_ids['NAME']]['last'].strip().title()
            except Exception as ex:
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
            logger.error(f'Error - BSB: {ex}')
            
        try:
            accountNumber = e[_ids['ACCOUNT_NUMBER']].strip()
            accountNumber = accountNumber.replace(' ','')
            accountNumber = accountNumber.replace('-','')
            MemberBankDetail['accountNumber'] = accountNumber
        except Exception as ex:
            logger.error(f'Error - ACCOUNT_NUMBER: {ex}')
            
        try:
            MemberBankDetail['accountType'] = e[_ids['ACCOUNT_TYPE']]
        except Exception as ex:
            logger.error(f'Error - ACCOUNT_TYPE: {ex}')
                
        ENROLMENT_DATA_ENTITY['memberBankDetail'] = MemberBankDetail


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

    # Medical Data
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

    # Signatures
    try:
#         print(e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]])
        if e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]] is not None and type(e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]]) == list and len(e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]]) > 0:
            ENROLMENT_DATA_ENTITY['agreement'] = e[_ids["MEMBERSHIP_TERMS_AGREEMENT"]][0] + ' | '
    except Exception as ex:
        print(f'Error - MEMBERSHIP_TERMS_AGREEMENT: {ex}')
        logger.error(f'Error - MEMBERSHIP_TERMS_AGREEMENT: {ex}')

    try:
        ENROLMENT_DATA_ENTITY['primarySignatureURL'] = e[_ids['SIGNATURE']]
    except Exception as ex:
        print(f'Error - SIGNATURE {ex}')
        logger.error(f'Error - SIGNATURE {ex}')

    ENROLMENT_DATA_ENTITY['covidVerificationURL'] = None
    try:
        if _ids["COVID_VERIFICATION_UPLOAD"] is not None and e[_ids["COVID_VERIFICATION_UPLOAD"]] is not None and e[_ids["COVID_VERIFICATION_UPLOAD"]] != '':
            ENROLMENT_DATA_ENTITY['covidVerificationURL'] = e[_ids["COVID_VERIFICATION_UPLOAD"]]

    except Exception as e:
        print(f'Error - COVID_VERIFICATION_UPLOAD: {ex}')
        logger.error(f'Error - COVID_VERIFICATION_UPLOAD: {ex}')

    return ENROLMENT_DATA_ENTITY

