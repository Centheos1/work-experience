import logging as logger
import datetime
import pandas as pd
import smtplib
from os.path import basename
from email.mime.application import MIMEApplication
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email.utils import COMMASPACE, formatdate
from email import encoders
import urllib.parse
import base64

# from fp_enrolment.constants import *
# from fp_enrolment.helpers import *
from constants import *
from helpers import *


class EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES:
    IS_TEST = "is_test"
    FS_INTERNAL_SUBMISSION_UNIQUE_ID = "fs_internal_submission_uniqueId"
    FS_INTERNAL_SUBMISSION_FORM_ID  = "fs_internal_submission_formId"
    MC_MBO_ID = "membership_consultant_mboId"
    COACH_MBO_ID = "coach_mboId"
    LOCATION_ID = "location_id"
    COUPON_CODE = "coupon_code"
    HAS_VIRTUAL = "has_virtual"
    HAS_CRECHE = "has_creche"
    HAS_FREE_TIME = "has_free_time"
    HAS_ACCESS_KEY_DISCOUNT = "has_access_key_discount"
    HAS_FREE_PT_SESSIONS = "has_free_pt_sessions"
    HAS_COMFORT_CXL = "has_comfort_cxl"
    HAS_EXTERNAL_PT = "has_external_pt"
    ORGANISED_BY_COACH = "organised_by_coach"
    HAS_COACH = "has_coach"
    IS_6_SESSIONS_COMMITMENT = "is_6_sessions_commitment"
    IS_LIFESTYLE_COMMITMENT = "is_lifestyle_commitment"
    IS_ANNUAL_COMMITMENT = "is_annual_commitment"
    F_NAME = "name-first"
    L_NAME = "name-last"
    ADDRESS_ADDRESS = "address-address"
    ADDRESS_CITY = "address-city"
    ADDRESS_STATE = "address-state"
    ADDRESS_POSTCODE = "address-zip"
    PHONE = "Phone"
    EMAIL = "Email"
    DATE_OF_BIRTH = "Date of Birth"
    AGE = "age"
    EMERGENCY_CONTACT_NAME = "Emergency Contact Name"
    EMERGENCY_CONTACT_PHONE = "Emergency Contact Phone"
    HOME_LOCATION = "Home Location"
    MEMBERSHIP_NAME = "Membership Name"
    TRAINING_PACKAGE = "Training Package"
    ACCESS_KEY = "Access Key"
    ACCESS_KEY_DISCOUNT = "Access Key Discount"
    STARTER_PACK = "Starter Pack"
    FREE_PT_PACK = "Free PT Sessions"
    ONE_OFF_TOTAL_PAYMENT = "One Off Payment Total"
    CRECHE_FORTNIGHTLY_DD = "Creche Fortnightly Direct Debit"
    COACHING_FORTNIGHTLY_DD = "Coaching Fortnightly Direct Debit"
    MEMBERSHIP_FORTNIGHTLY_DD = "Membership Fortnightly Direct Debit"
    TOTAL_FORTNIGHTLY_DD = "Ongoing Fortnightly Direct Debit"
    VIRTUAL_PLAYGROUND_MONTHLY_DD = "Virtual Playground Monthly Direct Debit"
    START_DATE = "Membership Start Date"
    FIRST_DEBIT_DATE = "First Debit Date"
    FREE_TIME = "Free Time"
    COACHING_START_DATE = "Coaching Start Date"
    COACHING_FIRST_DEBIT_DATE = "Coaching First Debit Date"
    COACH_NAME = "Coach Name"
    MEMBERSHIP_CONSULTANT_NAME = "Membership Consultant"
    NOTES = "Notes"
    COMFORT_DAYS = "ComfortDays"


def get_client_enrol_email_html(first_name, link, staff_name, gym_name):

    logo = Constants.LOGO_FP
    url = Constants.URL_FP
    url_str = Constants.URL_STR_FP
    
    if get_location_id(gym_name) == 5:
        logo = Constants.LOGO_BK
        url = Constants.URL_BK
        url_str = Constants.URL_STR_BK
    
    html = """\
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Pre Sale Form</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        </head>
        <body style="margin: 10; padding: 0; font-family: Arial, sans-serif; font-size: 1em; line-height: 1.2em;">
            <p>Hi {},</p>

            <p>Thank you for your time over the phone. As promised, here is the form to secure your membership.</p>

            <p style="font-size: 1.2em;""><a href="{}">Click here to complete the process.</a></p>

            <p>Stay healthy and we'll see you very soon!</p>

            <table style="margin: 10px;">
              <tr>
                <td rowSpan="3" style="padding: 5px 0;width: 60px;"><img src="{}" alt="Fitness Playground"></td>
                <td style="color: #fcb120; padding-left: 10px;font-size: 1.2em; vertical-align: centre;"><b>{}</b></td>
              </tr>
              <tr>
                  <td style="padding-left: 10px; vertical-align: centre;"><b>{}</b></td>
              </tr>
              <tr>
                  <td style="padding-left: 10px; vertical-align: centre;"><a href="{}">{}</a></td>
              </tr>
            </table>
        </body>
     </html>
    """.format(first_name, link, logo, staff_name, gym_name, url, url_str)

    return html


def get_client_enrol_email_text(first_name, link, staff_name, gym_name):
    text = """\
    Hello {},

    Thank you for your time over the phone. As promised, here is the form to secure your membership.

    Click this link to complete the process.

    {}

    Stay healthy and we'll see you very soon!

    {}
    {}""".format(first_name, link, staff_name, gym_name)

    return text


def get_club_enrolment_notification_html(first_name, last_name, gym_name):

    try:

        logo = Constants.LOGO_FP
        url = Constants.URL_FP
        url_str = Constants.URL_STR_FP
        
        if get_location_id(gym_name) == 5:
            logo = Constants.LOGO_BK
            url = Constants.URL_BK
            url_str = Constants.URL_STR_BK

        html = f"""\
        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <title>Pre Sale Form</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            </head>
            <body style="margin: 10; padding: 0; font-family: Arial, sans-serif; font-size: 1em; line-height: 1.2em;">
                <p>Hi,</p>

                <p>{first_name} {last_name} has completed there enrolment.</p>

                <table style="margin: 10px;">
                  <tr>
                    <td rowSpan="3" style="padding: 5px 0;width: 60px;"><img src="{logo}" alt="Fitness Playground"></td>
                    <td style="color: #fcb120; padding-left: 10px;font-size: 1.2em; vertical-align: centre;"><b>{gym_name}</b></td>
                  </tr>
                  <tr>
                      <td style="padding-left: 10px; vertical-align: centre;"><b>Memberships</b></td>
                  </tr>
                  <tr>
                      <td style="padding-left: 10px; vertical-align: centre;"><a href="{url}">{url_str}</a></td>
                  </tr>
                </table>
            </body>
         </html>
        """
        return html

    except Exception as ex:
        print(f"Error - get_club_enrolment_notification_html: {ex}")
        return None


def get_club_enrolment_notification_text(first_name, last_name, gym_name):

    try:
        text = f"""Hello,\n{first_name} {last_name} has completed there enrolment.\nThanks,\nMemberships"""
        return text
    except Exception as e:
        print(f"Error - get_club_enrolment_notification_text: {ex}")
        return None



def decode_email_credentials():
    decoded_credentials = base64.b64decode(Constants.EMAIL_CREDENTIALS).decode("utf-8")
    split = decoded_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
    Constants.SMTP_USERNAME = split[0]
    Constants.SMTP_PASSWORD = split[1]


def get_phone_enrolment_query_params(e, ENROLMENT_DATA_ENTITY):
    
    _ids = FS_FIELD_IDS.FORM[e["FormID"]]
    QUERY_PARAMS = {}
    
    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.IS_TEST] = e[_ids['IS_TEST']]
    except Exception as ex:
        logger.error(f'Error - IS_TEST {ex}')
    
    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.FS_INTERNAL_SUBMISSION_UNIQUE_ID] = e['UniqueID']
    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.FS_INTERNAL_SUBMISSION_FORM_ID] = e['FormID']
    
    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HOME_LOCATION] = ENROLMENT_DATA_ENTITY['gymName']
    except:
        logger.error('Error - HOME_LOCATION')

    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.LOCATION_ID] = ENROLMENT_DATA_ENTITY['locationId']
    except Exception as ex:
        logger.error(f'Error - LOCATION_ID {ex}')
    
    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.MC_MBO_ID] = ENROLMENT_DATA_ENTITY['staffMember']
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.MEMBERSHIP_CONSULTANT_NAME] = ENROLMENT_DATA_ENTITY['staffName']
    except Exception as ex:
        logger.error(f'Error - MEMBERSHIP_CONSULTANT_NAME {ex}')
    
    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.COACH_MBO_ID] = ENROLMENT_DATA_ENTITY['personalTrainer']
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.COACH_NAME] = ENROLMENT_DATA_ENTITY['personalTrainerName']
    except Exception as ex:
        logger.error(f'Error - COACH_NAME {ex}')

    try:
        if e[_ids['ADD_VIRTUAL_PLAGROUND']] is not None and len(e[_ids['ADD_VIRTUAL_PLAGROUND']]) > 0:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_VIRTUAL] = 'true'
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_VIRTUAL] = 'false'
    except Exception as ex:
        logger.error(f'Error - HAS_VIRTUAL {ex}')

    try:
        if e[_ids['ADD_CRECHE']] is not None and len(e[_ids['ADD_CRECHE']]) > 0:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_CRECHE] = 'true'
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_CRECHE] = 'false'
    except Exception as ex:
        logger.error(f'Error - HAS_CRECHE {ex}')

    try:
        if ENROLMENT_DATA_ENTITY['daysFree'] is None:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_FREE_TIME] = 'false'
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_FREE_TIME] = 'true'
    except Exception as ex:
        logger.error(f'Error - HAS_FREE_TIME {ex}')

    try:
        if ENROLMENT_DATA_ENTITY['accessKeyDiscount'] is None:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_ACCESS_KEY_DISCOUNT] = 'false'
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_ACCESS_KEY_DISCOUNT] = 'true'
    except Exception as ex:
        logger.error(f'Error - HAS_ACCESS_KEY_DISCOUNT {ex}')

    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_FREE_PT_SESSIONS] = 'false'
    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.FREE_PT_PACK] = ''
    try:
        if (e[_ids['FREE_PT_PACK']] is not None and len(e[_ids['FREE_PT_PACK']]) > 0):
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_FREE_PT_SESSIONS] = 'true'
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.FREE_PT_PACK] = e[_ids['FREE_PT_PACK']][0]
#             coupon_code = coupon_code + ',' + e[_ids['FREE_PT_PACK']][0]
    except Exception as ex:
        logger.error(f'Error - HAS_FREE_PT_SESSIONS | FREE_PT_PACK {ex}')


# This is throwng an error
    try:
        if (e[_ids['COMFORT_CANCEL']] is not None and len(e[_ids['COMFORT_CANCEL']]) > 0):
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_COMFORT_CXL] = 'true'
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_COMFORT_CXL] = 'false'
    except Exception as ex:
        logger.error(f'Error - COMFORT_CANCEL  {ex}')
          
    try:
        if (e[_ids['2_FREE']] is not None and len(e[_ids['2_FREE']]) > 0):
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_FREE_PT_SESSIONS] = 'true'
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.IS_6_SESSIONS_COMMITMENT] = 'true'
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.FREE_PT_PACK] = e[_ids['2_FREE']][0]
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.IS_6_SESSIONS_COMMITMENT] = 'false'
    except:
        logger.error('Error - IS_6_SESSIONS_COMMITMENT')

    is_external_pt = False
    try:
        if ENROLMENT_DATA_ENTITY['externalPt']:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_EXTERNAL_PT] = 'true'
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_EXTERNAL_PT] = 'false'
    except Exception as ex:
        logger.error(f'Error - HAS_EXTERNAL_PT {ex}')
        
    try:
        if ENROLMENT_DATA_ENTITY['trainingPackageSoldBy'] == 'Coach':
             QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ORGANISED_BY_COACH] = 'Yes'
        else:
             QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ORGANISED_BY_COACH] = 'No'
    except Exception as ex:
        logger.error(f'Error - ORGANISED_BY_COACH {ex}')
    
    try:
        if ENROLMENT_DATA_ENTITY['hasCoach']:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_COACH] = 'true'
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.HAS_COACH] = 'false'
    except Exception as ex:
        logger.error(f'Error - HAS_COACH {ex}')

    try:
        if ENROLMENT_DATA_ENTITY['trainingStarterPack'] == 'Lifestyle Personal Training':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.IS_LIFESTYLE_COMMITMENT] = 'true'
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.IS_LIFESTYLE_COMMITMENT] = 'false'
            
    except Exception as ex:
        logger.error(f'Error - IS_LIFESTYLE_COMMITMENT {ex}')

    try:    
        if ENROLMENT_DATA_ENTITY['noCommitment'] == '12Month':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.IS_ANNUAL_COMMITMENT] = 'true'
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.IS_ANNUAL_COMMITMENT] = 'false'
    except Exception as ex:
        logger.error(f'Error - IS_ANNUAL_COMMITMENT {ex}')

    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.F_NAME] = ENROLMENT_DATA_ENTITY['firstName']
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.L_NAME] = ENROLMENT_DATA_ENTITY['lastName']
    except Exception as ex:
        logger.error(f'Error - NAME {ex}')

    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ADDRESS_ADDRESS] = ENROLMENT_DATA_ENTITY['address1']
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ADDRESS_CITY] = ENROLMENT_DATA_ENTITY['city']
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ADDRESS_STATE] = ENROLMENT_DATA_ENTITY['state']
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ADDRESS_POSTCODE] = ENROLMENT_DATA_ENTITY['postcode']
    except Exception as ex:
        logger.error(f'Error - ADDRESS {ex}')
        
    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.PHONE] = ENROLMENT_DATA_ENTITY['phone']
    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.EMAIL] = ENROLMENT_DATA_ENTITY['email']

    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.DATE_OF_BIRTH] = pd.to_datetime(ENROLMENT_DATA_ENTITY['dob']).strftime('%Y-%m-%d')
    except Exception as ex:
        logger.error(f'Error - DATE_OF_BIRTH {ex}')

    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.EMERGENCY_CONTACT_NAME] = ENROLMENT_DATA_ENTITY['emergencyContactName']
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.EMERGENCY_CONTACT_PHONE] = ENROLMENT_DATA_ENTITY['emergencyContactPhone']
    except Exception as ex:
        logger.error(f'Error - EMERGENCY_CONTACT {ex}')
    
    try:
        if e[_ids['MEMBERSHIP_NAME_NT']] is not None and e[_ids['MEMBERSHIP_NAME_NT']] != '':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.MEMBERSHIP_NAME] = e[_ids['MEMBERSHIP_NAME_NT']][0]['label']
        elif e[_ids['MEMBERSHIP_NAME_MK']] is not None and e[_ids['MEMBERSHIP_NAME_MK']] != '':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.MEMBERSHIP_NAME] = e[_ids['MEMBERSHIP_NAME_MK']][0]['label']
        elif e[_ids['MEMBERSHIP_NAME_SH']] is not None and e[_ids['MEMBERSHIP_NAME_SH']] != '':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.MEMBERSHIP_NAME] = e[_ids['MEMBERSHIP_NAME_SH']][0]['label']
        elif e[_ids['MEMBERSHIP_NAME_BK']] is not None and e[_ids['MEMBERSHIP_NAME_BK']] != '':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.MEMBERSHIP_NAME] = e[_ids['MEMBERSHIP_NAME_BK']][0]['label']
    except Exception as ex:
        logger.error(f'Error - MEMBERSHIP_NAME {ex}')

# #     TRAINING_PACKAGE
    try:
        if ENROLMENT_DATA_ENTITY['trainingStarterPack'].lower() == 'coaching' or 'lifestyle' in ENROLMENT_DATA_ENTITY['trainingStarterPack'].lower():
            if ENROLMENT_DATA_ENTITY['externalPt'] == False:
                if e[_ids['TRAINING_PACKAGE_30_MIN']] is not None and e[_ids['TRAINING_PACKAGE_30_MIN']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_30_MIN']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_40_MIN']] is not None and e[_ids['TRAINING_PACKAGE_40_MIN']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_40_MIN']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_60_MIN']] is not None and e[_ids['TRAINING_PACKAGE_60_MIN']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_60_MIN']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_30_MIN_BK']] is not None and e[_ids['TRAINING_PACKAGE_30_MIN_BK']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_30_MIN_BK']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_40_MIN_BK']] is not None and e[_ids['TRAINING_PACKAGE_40_MIN_BK']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_40_MIN_BK']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_60_MIN_BK']] is not None and e[_ids['TRAINING_PACKAGE_60_MIN_BK']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_60_MIN_BK']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_LIFESTYLE']] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_LIFESTYLE']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']] is not None and e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_LIFESTYLE_BK']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_VIRTUAL_FITNESS']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']] is not None and e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_VIRTUAL_WELLNESS']][0]['label']
                elif e[_ids['TRAINING_PACKAGE_WELLNESS']] is not None and e[_ids['TRAINING_PACKAGE_WELLNESS']] != '':
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = e[_ids['TRAINING_PACKAGE_WELLNESS']][0]['label']
            else:
                contracts = ENROLMENT_DATA_ENTITY['contractNamesToBeActivated'].split(' | ')
                for c in contracts:
                    if 'external' in c.lower():
                        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = c.split(' # ')[0]

        elif ENROLMENT_DATA_ENTITY['trainingStarterPack'].lower() == 'starter pack':
            contracts = ENROLMENT_DATA_ENTITY['contractNamesToBeActivated'].split(' | ')
            for c in contracts:
                if 'pack' in c.lower():
                    QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = c.split(' # ')[0]
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TRAINING_PACKAGE] = ENROLMENT_DATA_ENTITY['trainingStarterPack']

    except Exception as ex:
        logger.error(f'Error - TRAINING_PACKAGE {ex}')
        
    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ACCESS_KEY] = e[_ids['ACCESS_KEY_FEE']]
    except Exception as ex:
        logger.error(f'Error - ACCESS_KEY {ex}')
    
    try:
        if ENROLMENT_DATA_ENTITY['accessKeyDiscount'] is not None:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ACCESS_KEY_DISCOUNT] = ENROLMENT_DATA_ENTITY['accessKeyDiscount']
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ACCESS_KEY_DISCOUNT] = ''

    except Exception as ex:
        logger.error(f'Error - ACCESS_KEY_DISCOUNT {ex}')

    try:
        if e[_ids['STARTER_PACK_OPTIONS']] is not None and e[_ids['STARTER_PACK_OPTIONS']] != '' and QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.FREE_PT_PACK] == '':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.STARTER_PACK] = e[_ids['STARTER_PACK_OPTIONS']][0]['value']
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.STARTER_PACK] = '0'
    except Exception as ex:
        logger.error(f'Error - STARTER_PACK {ex}')

    try:
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.ONE_OFF_TOTAL_PAYMENT] = e[_ids['ONE_OFF_TOTAL_PAYMENT']]
    except Exception as ex:
        logger.error(f'Error - ONE_OFF_TOTAL_PAYMENT {ex}')
    
    try:
        if e[_ids['CRECHE_FORTNIGHTLY_DD']] is not None and e[_ids['CRECHE_FORTNIGHTLY_DD']] != '':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.CRECHE_FORTNIGHTLY_DD] = e[_ids['CRECHE_FORTNIGHTLY_DD']]
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.CRECHE_FORTNIGHTLY_DD] = 0
    except Exception as ex:
        logger.error(f'Error - CRECHE_FORTNIGHTLY_DD {ex}')
          
    try:
        if e[_ids['COACHING_FORTNIGHTLY_DD']] is not None and e[_ids['COACHING_FORTNIGHTLY_DD']] != '':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.COACHING_FORTNIGHTLY_DD] = e[_ids['COACHING_FORTNIGHTLY_DD']]
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.COACHING_FORTNIGHTLY_DD] = 0
    except Exception as ex:
        logger.error(f'Error - COACHING_FORTNIGHTLY_DD {ex}')
        
    try:
        if e[_ids['MEMBERSHIP_FORTNIGHTLY_DD']] is not None and e[_ids['MEMBERSHIP_FORTNIGHTLY_DD']] != '':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.MEMBERSHIP_FORTNIGHTLY_DD] = e[_ids['MEMBERSHIP_FORTNIGHTLY_DD']]
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.MEMBERSHIP_FORTNIGHTLY_DD] = 0
    except Exception as ex:
        logger.error(f'Error - MEMBERSHIP_FORTNIGHTLY_DD {ex}')

    try:
        if e[_ids['TOTAL_FORTNIGHTLY_DD']] is not None and e[_ids['TOTAL_FORTNIGHTLY_DD']] != '':
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TOTAL_FORTNIGHTLY_DD] = e[_ids['TOTAL_FORTNIGHTLY_DD']]
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.TOTAL_FORTNIGHTLY_DD] = 0
    except Exception as ex:
        logger.error(f'Error - TOTAL_FORTNIGHTLY_DD {ex}')
          
    try:
        if e[_ids['ADD_VIRTUAL_PLAGROUND']] is not None and len(e[_ids['ADD_VIRTUAL_PLAGROUND']]) > 0:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.VIRTUAL_PLAYGROUND_MONTHLY_DD] = e[_ids['ADD_VIRTUAL_PLAGROUND']][0]['value']
        else:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.VIRTUAL_PLAYGROUND_MONTHLY_DD] = 0
    except Exception as ex:
        logger.error(f'Error - VIRTUAL_PLAYGROUND_MONTHLY_DD {ex}')

    try:
        try:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.START_DATE] = pd.to_datetime(ENROLMENT_DATA_ENTITY['startDate']).strftime('%Y-%m-%d')
        except:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.START_DATE] = datetime.datetime.now().strftime('%Y-%m-%d')
    except Exception as ex:
        logger.error(f'Error - START_DATE {ex}')
        
    try:
        if ENROLMENT_DATA_ENTITY['firstBillingDate'] is not None:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.FIRST_DEBIT_DATE] = pd.to_datetime(ENROLMENT_DATA_ENTITY['firstBillingDate']).strftime('%Y-%m-%d')
    except Exception as ex:
        logger.error(f'Error - FIRST_DEBIT_DATE {ex}')
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.FIRST_DEBIT_DATE] = datetime.datetime.now().strftime('%Y-%m-%d')

    try:
        if ENROLMENT_DATA_ENTITY['daysFree'] is not None:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.FREE_TIME] = ENROLMENT_DATA_ENTITY['daysFree']
    except Exception as ex:
        logger.error(f'Error - FREE_TIME {ex}')
          
    if e[_ids['COACHING_START_DATE']] is not None and e[_ids['COACHING_START_DATE']] != '':
        try:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.COACHING_START_DATE] = pd.to_datetime(e[_ids['COACHING_START_DATE']]).strftime('%Y-%m-%d')
        except Exception as ex:
            logger.error(f'Error - COACHING_START_DATE {ex}')

    if ENROLMENT_DATA_ENTITY['personalTrainingStartDate'] is not None:
        try:
            QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.COACHING_FIRST_DEBIT_DATE] = pd.to_datetime(ENROLMENT_DATA_ENTITY['personalTrainingStartDate']).strftime('%Y-%m-%d')
        except Exception as ex:
            logger.error(f'Error - COACHING_FIRST_DEBIT_DATE {ex}')

    try:      
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.NOTES] = ENROLMENT_DATA_ENTITY['notes']
    except Exception as ex:
        logger.error(f'Error - NOTES {ex}')
    
    try:
        comfort_days = 0
        if ENROLMENT_DATA_ENTITY['couponCode'] is not None and 'ComfortCancel' in ENROLMENT_DATA_ENTITY['couponCode']:
            comfort_days = ENROLMENT_DATA_ENTITY['couponCode'].split('Days')[0]
        QUERY_PARAMS[EXTERNAL_PHONE_ENROLMENT_FIELD_NAMES.COMFORT_DAYS] = comfort_days
    except:
        logger.error('Error - COUPON_CODE')
    
    return urllib.parse.urlencode(QUERY_PARAMS)


def handle_enrolment_communications(body, ENROLMENT_DATA_ENTITY):

    try:
        if ENROLMENT_DATA_ENTITY['locationId'] == str(5):
            link = Constants.FS_EXTERNAL_PHONE_ENROLMENT_URL_BK+'?'+get_phone_enrolment_query_params(body, ENROLMENT_DATA_ENTITY)
        else:
            link = Constants.FS_EXTERNAL_PHONE_ENROLMENT_URL_FP+'?'+get_phone_enrolment_query_params(body, ENROLMENT_DATA_ENTITY)

        # logger.info(link)
        ENROLMENT_DATA_ENTITY['fs_formUrl'] = link

        logger.info("Part 1 Enrolment Submission: Sending Comms")
        to_email = ENROLMENT_DATA_ENTITY['email']
        from_email = get_from_email(ENROLMENT_DATA_ENTITY['locationId'])
        text = get_client_enrol_email_text(ENROLMENT_DATA_ENTITY['firstName'], link, ENROLMENT_DATA_ENTITY['staffName'], ENROLMENT_DATA_ENTITY['gymName'])
        html = get_client_enrol_email_html(ENROLMENT_DATA_ENTITY['firstName'], link, ENROLMENT_DATA_ENTITY['staffName'], ENROLMENT_DATA_ENTITY['gymName'])
        subject = "{}: Enrolment Form".format(ENROLMENT_DATA_ENTITY['gymName'])

        logger.info(f"{from_email},{[to_email]}, {subject}")
        return send_email(from_email,[to_email], text, html, subject)
    except Exception as ex:
        logger.error(f"Error - handle_enrolment_communications: {ex}")
        print(f"Error - handle_enrolment_communications: {ex}")
        return False


def handle_enrolment_notification(ENROLMENT_DATA_ENTITY):

    try:
        if Constants.SMTP_USERNAME is None:
            decode_email_credentials()

        from_email = Constants.SMTP_USERNAME
        to_email = get_from_email(ENROLMENT_DATA_ENTITY['locationId'])
        text = get_club_enrolment_notification_text(ENROLMENT_DATA_ENTITY['firstName'], ENROLMENT_DATA_ENTITY['lastName'], ENROLMENT_DATA_ENTITY['gymName'])
        html = get_club_enrolment_notification_html(ENROLMENT_DATA_ENTITY['firstName'], ENROLMENT_DATA_ENTITY['lastName'], ENROLMENT_DATA_ENTITY['gymName'])
        subject = f"Enrolment Completed: {ENROLMENT_DATA_ENTITY['firstName']} {ENROLMENT_DATA_ENTITY['lastName']}"

        if html is not None and text is not None:
            return send_email(from_email,[to_email], text, html, subject)
        else:
            return False

    except Exception as ex:
        print(f"Error - handle_enrolment_notification: {ex}")
        return False
    


def send_error_notification(ERROR):

    subject = "Lambda Exception: fp_enrolment"

    text = f"{datetime.datetime.now().strftime('%Y-%m-%d %H:%M')}\n\n{ERROR}"

    html = f"""\
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Pre Sale Form</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        </head>
        <body style="margin: 10; padding: 0; font-family: Arial, sans-serif; font-size: 1em; line-height: 1.2em;">
            <p>{datetime.datetime.now().strftime('%Y-%m-%d %H:%M')}</p>
            <p>{ERROR}</p>
        </body>
     </html>
    """

    send_email(Constants.ERROR_NOTIFICATION_EMAIL, [Constants.ERROR_NOTIFICATION_EMAIL], text, html, subject)



def send_email(fromaddr, toaddr, text, html, subject):
    
    try:
        decode_email_credentials()

        msg = MIMEMultipart('alternative')

        msg['From'] = fromaddr
        msg['To'] = COMMASPACE.join(toaddr)

        if Constants.IS_TEST:
            toaddr = [Constants.TEST_EMAIL]
            toaddr = "clint@thefitnessplayground.com.au"
            logger.info(f"TEST EMAIL: {toaddr}")

        msg['Subject'] = subject

        part1 = MIMEText(text, 'plain')
        part2 = MIMEText(html, 'html')

    #     msg.attach(part1)
        msg.attach(part2)

        if Constants.IS_TEST:
	        logger.info(Constants.SMTP_USERNAME)
	        logger.info(toaddr)
	        logger.info(msg.as_string())

        server_ssl = smtplib.SMTP_SSL(Constants.SERVER, Constants.PORT)
        server_ssl.login(Constants.SMTP_USERNAME, Constants.SMTP_PASSWORD)
        server_ssl.sendmail(Constants.SMTP_USERNAME, toaddr, msg.as_string())
        server_ssl.quit()
        logger.info('Email Success')
        return True

    except Exception as ex:
        logger.error(f'Error sending email: {ex}')
        return False
