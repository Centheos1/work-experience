import pytest
import logging as logger
import json
import copy
import boto3
import os
import base64
import urllib
import datetime


import sys
sys.path.insert(1, '/Users/clintsellen/Desktop/Fitness Playground/Lambdas/lambdas/fp_enrolment')

CONFIG_FILE_PATH = "CONFIG_FILE_PATH"
with open(f"{CONFIG_FILE_PATH}") as json_file:
    CONFIG = json.load(json_file)

os.environ["SOURCE_UID"] = CONFIG['SOURCE_UID']
os.environ["MBO_API_KEY_FP_VIRTUAL"] = CONFIG['MBO_API_KEY_FP_VIRTUAL']
os.environ["MBO_API_KEY_FP_SOURCE"] = CONFIG['MBO_API_KEY_FP_SOURCE']
os.environ["MBO_CREDENTIALS"] = CONFIG['MBO_CREDENTIALS']


from genericUtil import *
from constants import *
from source_api import *
from mbo_api import *
from pif import *
from enrolment import *
from email import *
from coach import *
from processing import *
from gymsales_api import *
from emailer import *

class TEST_CONSTANTS:
    TEST_EMAIL = 'clint@thefitnessplayground.com.au'
    TEST_ACCESS_KEY_NUMBER = "100060597"
    TEST_MBO_EMAIL = "clint@centheos.com"
    RANDOM_EMAIL = None
    RANDOM_FS_UNIQUE_ID = None
    RANDOM_ACCESS_KEY = None

# generate_random_integer


def execute_test_enrolment(expected_build, expected_save, event, description):

    Constants.IS_TEST = True

    logger.info(f"**************************************\nStarting Test: {description} IS_TEST = {Constants.IS_TEST}\n\n")

# ####################################################################### ACTIVE CODE - handler.py
    body = json.loads(event)
    _ids = FS_FIELD_IDS.FORM[body["FormID"]]
# ####################################################################### ACTIVE CODE - handler.py

    if body['FormID'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_BK_FORM_ID or body['FormID'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_FP_FORM_ID:
        body[_ids['FS_INTERNAL_SUBMISSION_UNIQUE_ID']] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID
        expected_build['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID
        expected_save['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID

    TEST_CONSTANTS.RANDOM_EMAIL = generate_random_email()
    TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID = str(generate_random_integer(9))
    TEST_CONSTANTS.RANDOM_ACCESS_KEY = f"ZZ-{generate_random_integer(7)}"

    expected_build['email'] = TEST_CONSTANTS.RANDOM_EMAIL

    if body['FormID'] != Constants.FS_PHONE_ENROLMENT_EXTERNAL_BK_FORM_ID and body['FormID'] != Constants.FS_PHONE_ENROLMENT_EXTERNAL_FP_FORM_ID:
        expected_build['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID
        expected_save['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID

# ######################################################################
# ENROLMENT.                                                           #
# ######################################################################

# ####################################################################### ACTIVE CODE - handler.py
    if body['FormID'] == Constants.FS_PHONE_ENROLMENT_INTERNAL_FORM_ID or body['FormID'] == Constants.FS_IN_CLUB_ENROLMENT_FORM_ID:
# ####################################################################### ACTIVE CODE - handler.py

# Executed at submission
# ######################################################################
# BUILD ENROLMENT                                                      #
# ######################################################################

# ####################################################################### ACTIVE CODE
        ENROLMENT_DATA_ENTITY = build_enrolment_data_entity(body)
# ####################################################################### ACTIVE CODE

        ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL
        ENROLMENT_DATA_ENTITY['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID
        # expected_build['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID
        expected_build['createDate'] = ENROLMENT_DATA_ENTITY['createDate']
        expected_build['updateDate'] = ENROLMENT_DATA_ENTITY['updateDate']
        expected_build['daysFree'] = ENROLMENT_DATA_ENTITY['daysFree']
        ENROLMENT_DATA_ENTITY['id'] = None
        expected_build['id'] = None

        assert ENROLMENT_DATA_ENTITY == expected_build, f"BUILD - {description}"

        logger.info(f"BUILD ENROLMENT: {json.dumps(ENROLMENT_DATA_ENTITY)} - Complete\n\n")

# ######################################################################
# SAVE ENROLMENT                                                       #
# ######################################################################

# ####################################################################### ACTIVE CODE - handler.py
        ENROLMENT_DATA_ENTITY = source_post_request(Constants.SOURCE_ENROLEMNT_DATA_URL, ENROLMENT_DATA_ENTITY)
# ####################################################################### ACTIVE CODE - handler.py

        logger.info(f"\n{ENROLMENT_DATA_ENTITY}\n")

        expected_save['email'] = TEST_CONSTANTS.RANDOM_EMAIL
        expected_save['updateDate'] = ENROLMENT_DATA_ENTITY['updateDate']
        expected_save['id'] = ENROLMENT_DATA_ENTITY['id']
        expected_save['memberCreditCard'] = ENROLMENT_DATA_ENTITY['memberCreditCard']
        expected_save['memberBankDetail'] = ENROLMENT_DATA_ENTITY['memberBankDetail']
        expected_save['daysFree'] = ENROLMENT_DATA_ENTITY['daysFree']

        assert ENROLMENT_DATA_ENTITY == expected_save, f"SAVE - {description}"

        logger.info(f"SAVE ENROLMENT: {expected_save['id']} - Complete\n\n")

        sqs_body = {'type' : 'enrolmentData', 'id' : expected_save['id']}


# ####################################################################### ACTIVE CODE - handler.py
        if ENROLMENT_DATA_ENTITY is not None and  body["FormID"] == Constants.FS_PHONE_ENROLMENT_INTERNAL_FORM_ID:
            logger.info("Part 1 Enrolment: Handle Comms")
# ####################################################################### ACTIVE CODE - handler.py

            ENROLMENT_DATA_ENTITY['email'] = "clint@thefitnessplayground.com.au"

# ####################################################################### ACTIVE CODE - handler.py
            # Comms goes here
            # Query Params
            flag = handle_enrolment_communications(body, ENROLMENT_DATA_ENTITY)
# ####################################################################### ACTIVE CODE - handler.py

            assert flag == True, f"PART 1 ENROLMENT: COMMS - {description}"
            logger.info(f"PART 1 ENROLMENT: COMMS: {expected_save['id']} - Complete\n\n")

# ####################################################################### ACTIVE CODE - handler.py
            return
# ####################################################################### ACTIVE CODE - handler.py
        sqs_body = {'type' : 'enrolmentData', 'id' : expected_save['id']}


# ####################################################################### ACTIVE CODE
        if ENROLMENT_DATA_ENTITY['hasReferral']:
            save_referrals(body, ENROLMENT_DATA_ENTITY)
# ####################################################################### ACTIVE CODE

# This is where the submission needs to be added to the Queue
        execute_test_process_enrolment(expected_save, sqs_body, description)

# ######################################################################
# PART 2 ENROLMENT                                                     #
# ######################################################################

# ####################################################################### ACTIVE CODE - handler.py
    if body['FormID'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_BK_FORM_ID or body['FormID'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_FP_FORM_ID:
        logger.info('Enrolment Part 2: Update and Process')

        URL = Constants.SOURCE_GET_PHONE_ENROLMENT_URL + '/' + body[_ids['FS_INTERNAL_SUBMISSION_FORM_ID']] + '/' + body[_ids['FS_INTERNAL_SUBMISSION_UNIQUE_ID']]
    #     print(URL)
        ENROLMENT_DATA_ENTITY = source_get_request(URL)
        if ENROLMENT_DATA_ENTITY is not None:
            ENROLMENT_DATA_ENTITY = update_enrolment_data_entity_from_external_submission(body, ENROLMENT_DATA_ENTITY)
# ####################################################################### ACTIVE CODE - handler.py

            ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL
            ENROLMENT_DATA_ENTITY['fs_uniqueId'] = expected_build['fs_uniqueId']

            expected_build['createDate'] = ENROLMENT_DATA_ENTITY['createDate']
            expected_build['updateDate'] = ENROLMENT_DATA_ENTITY['updateDate']
            expected_build['daysFree'] = ENROLMENT_DATA_ENTITY['daysFree']
            expected_build['id'] = ENROLMENT_DATA_ENTITY['id']

            assert ENROLMENT_DATA_ENTITY == expected_build, f"UPDATE ENROLMENT - {description}"
            logger.info(f"UPDATE ENROLMENT: {ENROLMENT_DATA_ENTITY['id']} - Complete\n\n")

            logger.info(f"\n\nUPDATED ENROLMENT ENTITY: {json.dumps(ENROLMENT_DATA_ENTITY)}")

# ####################################################################### ACTIVE CODE
            URL = Constants.SOURCE_ENROLEMNT_DATA_URL +'/false'
            ENROLMENT_DATA_ENTITY = source_put_request(URL, ENROLMENT_DATA_ENTITY)
# ####################################################################### ACTIVE CODE

            expected_save['email'] = TEST_CONSTANTS.RANDOM_EMAIL
            # expected_save['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID
            expected_save['updateDate'] = ENROLMENT_DATA_ENTITY['updateDate']
            expected_save['id'] = ENROLMENT_DATA_ENTITY['id']
            expected_save['memberCreditCard'] = ENROLMENT_DATA_ENTITY['memberCreditCard']
            expected_save['memberBankDetail'] = ENROLMENT_DATA_ENTITY['memberBankDetail']
            expected_save['daysFree'] = ENROLMENT_DATA_ENTITY['daysFree']

            assert ENROLMENT_DATA_ENTITY == expected_save, f"SAVE UPDATED ENROLMENT - {description}"

            logger.info(f"SAVE UPDATED ENROLMENT: {expected_save['id']} - Complete\n\n")
        
# ####################################################################### ACTIVE CODE
        else:
    #         Handle Part 1 Not Found Error
            logger.error("Part 1 Enrolment Not Found")
# ####################################################################### ACTIVE CODE

        assert ENROLMENT_DATA_ENTITY != None, f"PART 2 ENROLMENT - {description}"
        logger.info(f"\n\nSAVED PART 2 ENROLMENT: {json.dumps(ENROLMENT_DATA_ENTITY)} - Complete\n\n")

        sqs_body = {'type' : 'enrolmentData', 'id' : expected_save['id']}

# This is where the submission needs to be added to the Queue
        execute_test_process_enrolment(expected_save, sqs_body, description)


# ######################################################################
# PIF ENROLMENT.                                                       #
# ######################################################################

# ####################################################################### ACTIVE CODE
    if body['FormID'] == Constants.FS_PIF_ENROLMENT_FORM_ID:
# ####################################################################### ACTIVE CODE

        logger.info("Testing: PIF Submission")

# ####################################################################### ACTIVE CODE
        ENROLMENT_DATA_ENTITY = build_pif_enrolment_data(body, MemberStatus.PROCESSING[0], CommunicationsStatus.EMAIL_CAMPAIGN_PENDING[0])
# ####################################################################### ACTIVE CODE

        ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL
        ENROLMENT_DATA_ENTITY['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID

        expected_build['createDate'] = ENROLMENT_DATA_ENTITY['createDate']
        expected_build['updateDate'] = ENROLMENT_DATA_ENTITY['updateDate']

        assert ENROLMENT_DATA_ENTITY == expected_build, f"BUILD PIF - {description}"
        logger.info(f"BUILD PIF: {description} - Complete\n\n")
        
# ####################################################################### ACTIVE CODE
        ENROLMENT_DATA_ENTITY = source_post_request(Constants.SOURCE_ENROLEMNT_DATA_URL, ENROLMENT_DATA_ENTITY)
# ####################################################################### ACTIVE CODE

        expected_save['email'] = TEST_CONSTANTS.RANDOM_EMAIL
        expected_save['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID
        expected_save['updateDate'] = ENROLMENT_DATA_ENTITY['updateDate']
        expected_save['id'] = ENROLMENT_DATA_ENTITY['id']
        expected_save['memberCreditCard'] = ENROLMENT_DATA_ENTITY['memberCreditCard']
        expected_save['memberBankDetail'] = ENROLMENT_DATA_ENTITY['memberBankDetail']

        assert ENROLMENT_DATA_ENTITY == expected_save, f"SAVE PIF - {description}"
        logger.info(f"SAVE PIF: {ENROLMENT_DATA_ENTITY['id']} - Complete\n\n")

        sqs_body = {'type' : 'enrolmentData', 'id' : expected_save['id']}

# ####################################################################### ACTIVE CODE
        if ENROLMENT_DATA_ENTITY['hasReferral']:
            save_referrals(body, ENROLMENT_DATA_ENTITY)
# ####################################################################### ACTIVE CODE

# This is where the submission needs to be added to the Queue
        execute_test_process_enrolment(expected_save, sqs_body, description)




# ############################################################################################################################################
# From here is executed from the Queue                                                                                                       #
# ############################################################################################################################################
def execute_test_process_enrolment(expected, sqs_body, description):

    os.environ['PdfSqsUrl'] = "https://sqs.ap-southeast-2.amazonaws.com/346880879164/PdfSQS.fifo"
    logger.info(f"PDF SQS URL: {os.getenv('PdfSqsUrl')}")

    # os.environ['EnrolmentSqsUrl'] = "https://sqs.ap-southeast-2.amazonaws.com/346880879164/EnrolmentSQS"

# ######################################################################
# GET ENROLMENT                                                        #
# ######################################################################

# ####################################################################### ACTIVE CODE - handler.py
    # SQS_CLIENT = boto3.client('sqs')
    IS_MBO_SUCCESS = True
    # dollar_value = 0
    URL = 'source/'+ sqs_body['type'] +'/'+ str(sqs_body['id']) +'/false'
    ENROLMENT_DATA_ENTITY = source_get_request(URL)

    if ENROLMENT_DATA_ENTITY is None:
        logger.error("Error - Enrolment Doesn't Exist in Source")
        handle_manual_submission(MemberStatus.CLIENT_NOT_FOUND, ENROLMENT_DATA_ENTITY, None)
        ENROLMENT_DATA_ENTITY['status'] = MemberStatus.CLIENT_NOT_FOUND[0]
        IS_MBO_SUCCESS = False
# ####################################################################### ACTIVE CODE - handler.py

    logger.info(f"\n\nEnrolment Entity for Processing: {ENROLMENT_DATA_ENTITY}\n\n")

    expected['memberCreditCard'] = ENROLMENT_DATA_ENTITY['memberCreditCard']
    expected['memberBankDetail'] = ENROLMENT_DATA_ENTITY['memberBankDetail']
    expected['updateDate'] = ENROLMENT_DATA_ENTITY['updateDate']

    assert ENROLMENT_DATA_ENTITY == expected, f"GET ENROLMENT: {ENROLMENT_DATA_ENTITY['id']} - {description}"
    logger.info(f"GET ENROLMENT: {ENROLMENT_DATA_ENTITY['id']} - Complete\n\n")


# ######################################################################
# CHECK DUPLICATE KEY                                                  #
# ######################################################################
    if generate_random_integer(2) % 2 == 0:
        ENROLMENT_DATA_ENTITY['accessKeyNumber'] = TEST_CONSTANTS.TEST_ACCESS_KEY_NUMBER
        expected_duplicate = True
    else:
        ENROLMENT_DATA_ENTITY['accessKeyNumber'] = TEST_CONSTANTS.RANDOM_ACCESS_KEY
        expected_duplicate = False

# ####################################################################### ACTIVE CODE - handler.py
    is_duplicate_key = check_duplicate_key(ENROLMENT_DATA_ENTITY)
# ####################################################################### ACTIVE CODE - handler.py  

    assert expected_duplicate == is_duplicate_key, f"CHECK DUPLICATE KEY - {description}"
    logger.info(f"CHECK DUPLICATE KEY - Complete\n\n")


# ######################################################################
# CHECK EXISTING CLIENT                                                #
# ######################################################################
    # if generate_random_integer(2) % 2 == 0:
    #     ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.TEST_MBO_EMAIL
    #     ENROLMENT_DATA_ENTITY['accessKeyNumber'] = TEST_CONSTANTS.TEST_ACCESS_KEY_NUMBER
    #     expected_duplicate = True
    # else:
    ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL
    ENROLMENT_DATA_ENTITY['accessKeyNumber'] = TEST_CONSTANTS.RANDOM_ACCESS_KEY
    expected_duplicate = False


# ####################################################################### ACTIVE CODE - handler.py  
    PROCESS_ENTITY = check_existing_client(ENROLMENT_DATA_ENTITY)

    EXISTING_MBO_CLIENT = PROCESS_ENTITY['EXISTING_MBO_CLIENT']
    EXISTING_CREDIT_CARD = PROCESS_ENTITY['EXISTING_CREDIT_CARD']
    EXISTING_BANK_DETIALS = PROCESS_ENTITY['EXISTING_BANK_DETIALS']
    ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']
# ####################################################################### ACTIVE CODE - handler.py  

    assert expected_duplicate == ENROLMENT_DATA_ENTITY['existingClient'], f"CHECK EXISTING CLIENT - {description}"
    logger.info(f"CHECK EXISTING CLIENT - Complete\n\n")

    # ######################################################################
    # FAILED TO ADD OR UPDATE CLIENT IN MINDBODY                           #
    # ######################################################################
# # ####################################################################### ACTIVE CODE - handler.py  
    if IS_MBO_SUCCESS:

    # ######################################################################
    # ADD OR UPDATE CLIENT                                                 #
    # ######################################################################
        PROCESS_ENTITY = add_or_update_client(ENROLMENT_DATA_ENTITY, is_duplicate_key, EXISTING_MBO_CLIENT)
        ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']
        MBO_CLIENT = PROCESS_ENTITY['MBO_CLIENT']
        IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
# ####################################################################### ACTIVE CODE - handler.py  

        assert IS_MBO_SUCCESS == True, f"ADD OR UPDATE CLIENT - {description}"
        logger.info(f"ADD OR UPDATE CLIENT - Complete\n\n")


    # ######################################################################
    # ADD BANK DETAILS                                                     #
    # ######################################################################

        MBO_CLIENT['Id'] = TEST_CONSTANTS.TEST_ACCESS_KEY_NUMBER

    # ####################################################################### ACTIVE CODE - handler.py  
        if ENROLMENT_DATA_ENTITY['memberBankDetail'] is not None and MBO_CLIENT is not None:
            logger.info('\tAdd Bank Details')
            print('\tAdd Bank Details')

            PROCESS_ENTITY = add_bank_details(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

            IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
            ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']
    # ####################################################################### ACTIVE CODE - handler.py  

            assert IS_MBO_SUCCESS == True, f"ADD BANK DETAILS - {description}"
            logger.info(f"ADD BANK DETAILS - Complete\n\n")


    # ######################################################################
    # PURCHASE CONTRACTS                                                   #
    # ######################################################################

        MBO_CLIENT['Id'] = TEST_CONSTANTS.TEST_ACCESS_KEY_NUMBER
        N = generate_random_integer(1)
        random_start_date = datetime.datetime.now() + datetime.timedelta(N)
        random_start_date = random_start_date.strftime(Constants.DATETIME_FORMAT)

        ENROLMENT_DATA_ENTITY['firstBillingDate'] = random_start_date
        ENROLMENT_DATA_ENTITY['startDate'] = datetime.datetime.now().strftime(Constants.DATETIME_FORMAT)

        logger.info(f"random_start_date: {random_start_date}")        
        logger.info(ENROLMENT_DATA_ENTITY['memberContracts'])

        
    # ####################################################################### ACTIVE CODE - handler.py
        response = mbo_get_request(Constants.MBO_GET_CLIENTS_URL, {'ClientIds' : MBO_CLIENT['Id']})
        MBO_CLIENT = response['Clients'][0]

    # ####################################################################### ACTIVE CODE - handler.py  
        logger.info(f"\n\n MBO_CLIENT: {MBO_CLIENT}\n")


    # ####################################################################### ACTIVE CODE - handler.py  
        PROCESS_ENTITY = purchase_contracts(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

        IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
        ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']
    # ####################################################################### ACTIVE CODE - handler.py  

        logger.info(f"IS_MBO_SUCCESS: {IS_MBO_SUCCESS}\tisExternalPt {ENROLMENT_DATA_ENTITY['externalPt']}")

        assert IS_MBO_SUCCESS == True or (IS_MBO_SUCCESS == False and (ENROLMENT_DATA_ENTITY['externalPt'] == True or ENROLMENT_DATA_ENTITY['status'] == MemberStatus.IN_CLUB_PAYMENT[0])), f"PURCHASE CONTRACTS - {description}"
        logger.info(f"PURCHASE CONTRACTS - Complete\n\n")


    # ######################################################################
    # PURCHASE SERVICES                                                    #
    # ######################################################################

        MBO_CLIENT['Id'] = TEST_CONSTANTS.TEST_ACCESS_KEY_NUMBER

     # ####################################################################### ACTIVE CODE - handler.py  
        PROCESS_ENTITY = purchase_services(ENROLMENT_DATA_ENTITY, MBO_CLIENT)
        if IS_MBO_SUCCESS == True:
            IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
        ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']
        is_valid_checkout = PROCESS_ENTITY['is_valid_checkout']
    # ####################################################################### ACTIVE CODE - handler.py

        if is_valid_checkout:
            assert IS_MBO_SUCCESS == True or (IS_MBO_SUCCESS == False and (ENROLMENT_DATA_ENTITY['externalPt'] == True or ENROLMENT_DATA_ENTITY['status'] == MemberStatus.IN_CLUB_PAYMENT[0])), f"PURCHASE SERVICES - {description}"

        logger.info(f"PURCHASE SERVICES - Complete\n\n")

        
        ENROLMENT_DATA_ENTITY['accessKeyNumber'] = TEST_CONSTANTS.RANDOM_ACCESS_KEY
        ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL

    # ######################################################################
    # PURCHASE ACCESS KEY PAY TODAY                                        #
    # ######################################################################
    # ####################################################################### ACTIVE CODE - handler.py
        if ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'] is not None and ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'].lower().replace(' ','') == 'paytoday':
            PROCESS_ENTITY = purchase_access_key_pay_today(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

            if IS_MBO_SUCCESS == True:
                IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']

            ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']
    # ####################################################################### ACTIVE CODE - handler.py

            assert IS_MBO_SUCCESS == True , f"PURCHASE ACCESS KEY PAY TODAY - {description}"

            logger.info(f"PURCHASE ACCESS KEY PAY TODAY - Complete\n\n")


    # ######################################################################
    # HANDLE REFERRALS -> Push to GymSales                                 #
    # ######################################################################
        ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.TEST_EMAIL
        ENROLMENT_DATA_ENTITY['locationId'] = '6' 
        response = None 

    # ####################################################################### ACTIVE CODE - handler.py
        if ENROLMENT_DATA_ENTITY['hasReferral'] is not None and ENROLMENT_DATA_ENTITY['hasReferral'] == True:

            response = handle_referrals(ENROLMENT_DATA_ENTITY)
    # ####################################################################### ACTIVE CODE - handler.py

            logger.info(f"HANDLE REFERRALS response: {response}")
            assert response is not None, f"HANDLE REFERRALS - {description}"

        logger.info(f"HANDLE REFERRALS - Complete\n\n")


    # ######################################################################
    # UPDATE GYMSALES.                                                     #
    # ######################################################################
        expected_gymsales_id = "EXPECTED_GYMSALES_ID"

    # ####################################################################### ACTIVE CODE - handler.py
        # ENROLMENT_DATA_ENTITY = update_gymsales(ENROLMENT_DATA_ENTITY)
    # ####################################################################### ACTIVE CODE - handler.py

        expected_gymsales_id = ENROLMENT_DATA_ENTITY['gymSalesId']
        assert expected_gymsales_id != "EXPECTED_GYMSALES_ID", f"UPDATE GYMSALES - {description}"

        logger.info(f"UPDATE GYMSALES - Complete\n\n")

        ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL
        ENROLMENT_DATA_ENTITY['locationId'] = expected['locationId']


    # #######################################################################################################
    # SET STATUS AND UPDATE DATABASE - A SUCCESS status will trigger payment details to be washed.          #
    # #######################################################################################################

        logger.info(f"\tSTATUS = {ENROLMENT_DATA_ENTITY['status']}\tIS_MBO_SUCCESS: {IS_MBO_SUCCESS}\n")
    
    ####################################################################### ACTIVE CODE - handler.py
        ENROLMENT_DATA_ENTITY['fs_formUrl'] = None
        
        if IS_MBO_SUCCESS:
            ENROLMENT_DATA_ENTITY['status'] = MemberStatus.SUCCESS[0]
            
        UPDATED_ENROLMENT_DATA_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", ENROLMENT_DATA_ENTITY)
    # ####################################################################### ACTIVE CODE - handler.py

        ENROLMENT_DATA_ENTITY['status'] = UPDATED_ENROLMENT_DATA_ENTITY['status']
        # ENROLMENT_DATA_ENTITY['status'] = "COMPLETE"
        ENROLMENT_DATA_ENTITY['memberCreditCard'] = UPDATED_ENROLMENT_DATA_ENTITY['memberCreditCard']
        ENROLMENT_DATA_ENTITY['memberBankDetail'] = UPDATED_ENROLMENT_DATA_ENTITY['memberBankDetail']
        ENROLMENT_DATA_ENTITY['updateDate'] = UPDATED_ENROLMENT_DATA_ENTITY['updateDate']
        ENROLMENT_DATA_ENTITY['mboUniqueId'] = UPDATED_ENROLMENT_DATA_ENTITY['mboUniqueId']

        assert ENROLMENT_DATA_ENTITY == UPDATED_ENROLMENT_DATA_ENTITY, f"SET STATUS AND UPDATE DATABASE - {description}"
        logger.info(f"SET STATUS AND UPDATE DATABASE: {ENROLMENT_DATA_ENTITY['id']} - Complete\n\n")

        ENROLMENT_DATA_ENTITY = UPDATED_ENROLMENT_DATA_ENTITY

    # ######################################################################
    # PT TRACKER                                                           #
    # ######################################################################
        ENROLMENT_DATA_ENTITY['firstName'] = 'Clint'
        ENROLMENT_DATA_ENTITY['lastName'] = "Test"
        ENROLMENT_DATA_ENTITY['phone'] = '+61413506306'
        ENROLMENT_DATA_ENTITY['email'] = 'clint@thefitnessplayground.com.au'

    # # ####################################################################### ACTIVE CODE - handler.py
        PT_TRACKER_ENTITY = None
        if ENROLMENT_DATA_ENTITY['trainingStarterPack'] is not None and ENROLMENT_DATA_ENTITY['trainingStarterPack'].replace(' ','').lower() in ['face-to-face', 'coaching', 'starterpack','externalpersonaltraining']:
            logger.info('HANDLE PT TRACKER ')
            PT_TRACKER_ENTITY = handlePtTracker(ENROLMENT_DATA_ENTITY, MBO_CLIENT)
    # # ####################################################################### ACTIVE CODE - handler.py

        logger.info(f"\n\n\tPT_TRACKER_ENTITY: {PT_TRACKER_ENTITY}\n")

    # # ######################################################################
    # # TRIGGER PDF WRITER - Push onto SQS Queue                             #
    # # ######################################################################
        ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.TEST_EMAIL
        
        logger.info(f"\n\n TRIGGER PDF WRITER  - Preparation: TEST_EMAIL == {ENROLMENT_DATA_ENTITY['email']}")
        # COMMS = 'FAILED COMMMS'

    # # ####################################################################### ACTIVE CODE - handler.py
        # ENROLMENT_DATA_ENTITY = trigger_communications(ENROLMENT_DATA_ENTITY, sqs_body['type'], str(sqs_body['id']) )
        ENROLMENT_DATA_ENTITY = trigger_pdf_writer(ENROLMENT_DATA_ENTITY, MBO_CLIENT, PT_TRACKER_ENTITY, "enrolmentData")

        if ENROLMENT_DATA_ENTITY['communicationsStatus'] == CommunicationsStatus.EMAIL_CAMPAIGN_ERROR[0]:
                ENROLMENT_DATA_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", ENROLMENT_DATA_ENTITY)

    # # ####################################################################### ACTIVE CODE - handler.py

        response = None

    # # ######################################################################
    # # UPLOAD MEMBER PHOTO                                                  #
    # # ######################################################################
        # ####################################################################### ACTIVE CODE - handler.py
        response = upload_member_photo(ENROLMENT_DATA_ENTITY, MBO_CLIENT)
        # ####################################################################### ACTIVE CODE - handler.py

        logger.info(f"{response}\n")
        if ENROLMENT_DATA_ENTITY['memberPhotoURL'] is not None and ENROLMENT_DATA_ENTITY['memberPhotoURL'] != '':
            assert response != None, f"UPLOAD MEMBER PHOTO - {description}"

        logger.info(f"UPLOAD MEMBER PHOTO - Complete\n\n")

        contact_logs_handled = True

        # ######################################################################
        # CONTACT LOGS FOR COMFORT CANCEL                                      #
        # ######################################################################
        # ####################################################################### ACTIVE CODE - handler.py
        print('CONTACT LOGS FOR COMFORT CANCEL')
        logger.info('CONTACT LOGS FOR COMFORT CANCEL')
        if ENROLMENT_DATA_ENTITY['couponCode'] is not None and Constants.MBO_COUPON_CODE_COMFORT_CANCEL in ENROLMENT_DATA_ENTITY['couponCode'].lower():
            
            try:
                comfort_cxl_date = pd.to_datetime(ENROLMENT_DATA_ENTITY['startDate']) + datetime.timedelta(7)
                comfort_cxl_date = comfort_cxl_date.strftime("%A, %d %B %Y")
                MESSAGE = f"Comfort cancel expires on {comfort_cxl_date}."

                BODY = {
                    "Test" : Constants.IS_TEST,
                    "ClientId" : MBO_CLIENT['Id'],
                    "Text" : MESSAGE,
                    "ContactMethod" : "InPerson",
                    "ContactName" : MBO_CLIENT['FirstName'] + " " + MBO_CLIENT['LastName']
                }

                send_contact_logs(BODY)

            except Exception as ex:
                print(f"Error - CONTACT LOGS FOR COMFORT CANCEL: {ex}")
                logger.info(f"Error - CONTACT LOGS FOR COMFORT CANCEL: {ex}")
        # ####################################################################### ACTIVE CODE - handler.py
        
                contact_logs_handled = False

            assert contact_logs_handled == True, f"CONTACT LOGS FOR COMFORT CANCEL - {description}"


# # ######################################################################
# # TRIGGER COMMUNICATIONS                                               #
# # ######################################################################
#     ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.TEST_EMAIL
    
#     logger.info(f"\n\n TRIGGER COMMUNICATIONS  - Preparation: TEST_EMAIL == {ENROLMENT_DATA_ENTITY['email']}")
#     # COMMS = 'FAILED COMMMS'

# # # ####################################################################### ACTIVE CODE - handler.py
    ENROLMENT_DATA_ENTITY = trigger_communications(ENROLMENT_DATA_ENTITY, sqs_body['type'], str(sqs_body['id']) )

    if ENROLMENT_DATA_ENTITY['communicationsStatus'] == CommunicationsStatus.EMAIL_CAMPAIGN_ERROR[0]:
        ENROLMENT_DATA_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", ENROLMENT_DATA_ENTITY)

# # # ####################################################################### ACTIVE CODE - handler.py

    ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL

    assert ENROLMENT_DATA_ENTITY['communicationsStatus'] != CommunicationsStatus.EMAIL_CAMPAIGN_ERROR[0], f"TRIGGER COMMUNICATIONS - {description}"
    logger.info(f"TRIGGER COMMUNICATIONS - Complete\n\n")



# ######################################################################
# UPLOAD COVID VERIFICATION                                            #
# ######################################################################
    MBO_CLIENT['Id'] = TEST_CONSTANTS.TEST_ACCESS_KEY_NUMBER
    resposne = upload_covid_verification(ENROLMENT_DATA_ENTITY, MBO_CLIENT)
    assert response is not None, f"UPLOAD COVID VERIFICATION - {description}"
    logger.info(f"UPLOAD COVID VERIFICATION - Complete\n\n")


    logger.info(f"Finished Test: {description}")
    logger.info(f"______________________")
    return



@pytest.mark.hello
def test_hello_world():
    logger.info("Hello test world")
    logger.info(f"""\nSOURCE_UID: {os.getenv("SOURCE_UID")}\nMBO_API_KEY_FP_VIRTUAL: {os.getenv("MBO_API_KEY_FP_VIRTUAL")}\nMBO_API_KEY_FP_SOURCE: {os.getenv("MBO_API_KEY_FP_SOURCE")}\nMBO_CREDENTIALS: {os.getenv("MBO_CREDENTIALS")}""")
    

# Instructions

# Change directory
# cd ./Desktop/Fitness\ Playground/Lambdas/lambdas/fp_enrolment/test/

# Use builder test to build expected_build and expected_save entities

# Run Test
# # pytest -m enrolment
# OR with output
# pytest -m enrolment -vv 


@pytest.mark.enrolments
@pytest.mark.enrolment
def test_build_enrolment_data_entity():
    logger.info("test_build_enrolment_data_entity")
    Constants.IS_TEST = True
    logger.info(f"IS_TEST: {Constants.IS_TEST}")

    event = """{"94332581": "true","94332582": "2021-10-10 10:11:22","94332583": "","94332584": {"first": "Clint","last": "Test"},"94332585": {"address": "1 Street Rd","city": "Sydney","state": "NSW","zip": "2000"},"94332586": "0413 506 306","94332587": "clint@centheos.com","94332588": "25 Oct 1967","94332589": "male","94332590": "Em","94332591": "0487 654 321","94332592": "","94332593": null,"94332594": null,"94332641": null,"94332642": null,"94332643": null,"94332644": null,"94332645": null,"94332646": null,"94332647": null,"94332648": null,"94333018": "","94333274": "","94333311": ["I have read, understood and agree to the Terms & Conditions"],"94333357": null,"94333358": ["I acknowledge this is a minimum term of 26 fortnightly payments"],"94333524": null,"94333525": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/signature_94333525.png","94333527": "53","94333803": "99999","94335049": ["Any Day"],"94335090": ["Before Work"],"94335502": "","94335503": "Yes","94335504": null,"94335505": null,"94335506": "Credit Card","94335507": {"card": "4111111111111111","cardexp": "12/22","cvv": "231"},"94335508": null,"94335509": null,"94335510": null,"94351558": "","94351559": [{"value": "1","label": "Surry Hills"}],"94351560": null,"94351561": [{"value": "41.40","label": "Gym: Membership ($19.95)"}],"94351562": null,"94351563": "I'm a Fitness Guru","94351564": null,"94351565": null,"94351566": null,"94351568": null,"94351569": null,"94351570": null,"94351571": null,"94351572": null,"94351573": null,"94351574": ["0.00"],"94351575": ["0.00"],"94351576": ["0.00"],"94351577": ["0.00"],"94351578": ["0.00"],"94351579": null,"94351580": null,"94351581": null,"94351582": null,"94351583": ["Promotions"],"94351584": null,"94351585": null,"94351586": [{"value": "21","label": "21 Days Free"}],"94351587": [{"value": "-50","label": "$49 Access Key"}],"94351588": "21","94351589": "99","94351590": "49.00","94351591": "49.00","94351592": null,"94351593": null,"94351594": "41.40","94351595": "41.40","94351596": null,"94351597": "10 Oct 2021","94351598": "31 Oct 2021","94351599": null,"94351600": null,"94351601": [{"value": "100000111::152065","label": "Clint Sellen"}],"94351602": null,"94351603": null,"94351604": null,"94351605": [{"value": "6::152065","label": "Daniel Bowden"}],"94351606": null,"94351607": "","94351608": "Test Submission","94352342": null,"95430162": ["I authorise payments to be debited from the above payment details"],"95430172": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/signature_95430172.png","95431031": "0","95431213": null,"95431216": null,"95431448": "","95431449": "","95431633": [{"value": "1","label": "Surry Hills"}],"96024203": null,"98479769": null,"98479796": null,"98479838": null,"98479850": null,"98482524": null,"101490498": null,"102848235": null,"102848237": null,"102852720": "","103257339": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/103257339_tickicon.png","103863738": ["Please give the iPad to Team Member"],"103867643": "","103920885": null,"103921451": null,"103921454": null,"103921464": null,"103921520": null,"103921554": null,"103921576": null,"103921583": null,"103921853": "Add to First Payment","103982989": "","103983056": "7","103983103": [{"value": "21","label": "21 Day Satisfaction Period"}],"103988711": null,"104837612": null,"111057961": null,"111057964": null,"111057966": null,"111058145": null,"111058146": null,"111058148": null,"111058422": null,"111058423": null,"111058424": null,"111058477": "","111778860": "21","111778876": "31 Oct 2021","116315320": "Yes","116315321": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/116315321_tickicon.png","FormID": "3931477","UniqueID": "871580834","HandshakeKey": "SHARED SECRET"}"""
    expected_build = {'fs_uniqueId': '871580834', 'fs_formId': '3931477', 'mboUniqueId': None, 'preExId': '', 'googleClickId': None, 'facebookCampaignId': None, 'existingClient': None, 'gymSalesId': None, 'locationId': '1', 'gymName': 'Surry Hills', 'createDate': '2021-10-10 10:11:22', 'updateDate': '2021-10-10 11:53:41', 'status': 'SAVED', 'communicationsStatus': 'EMAIL_CAMPAIGN_PENDING', 'daysFree': 'First 21 Days', 'accessKeyPaymentOptions': 'Add to First Payment', 'accessKeyPaymentMethod': None, 'accessKeyDiscount': '$49 Access Key', 'isExternalPt': False, 'hasCoach': False, 'sessionLength': None, 'trainingStarterPack': "I'm a Fitness Guru", 'noCommitment': '12Month', 'gymOrPlay': 'gym', 'ddOrPif': 'dd', 'contractNamesToBeActivated': 'First 21 Days # 0.00 | Access Key # 49.0 | Gym: Membership ($19.95) # 41.4/fortnight', 'memberContracts': '383,120,307', 'couponCode': 'NDaysFree,Key49,21DayComfortCancel', 'startDate': '2021-10-10', 'firstBillingDate': '2021-10-31', 'personalTrainingStartDate': None, 'staffMember': '6::152065', 'staffName': 'Daniel Bowden', 'personalTrainer': '100000111::152065', 'personalTrainerName': 'Clint Sellen', 'trainingPackageSoldBy': None, 'trainingPackageConsultantLocationId': None, 'trainingPackageConsultantName': None, 'trainingPackageConsultantMboId': None, 'phone': '+61413506306', 'firstName': 'Clint', 'lastName': 'Test', 'email': 'clint@centheos.com', 'address1': '1 Street Rd', 'city': 'Sydney', 'state': 'NSW', 'postcode': '2000', 'dob': '1967-10-25', 'emergencyContactName': 'Em', 'emergencyContactPhone': '+61487654321', 'notes': 'Test Submission', 'howDidYouHearAboutUs': '', 'hasReferral': False, 'referralName': None, 'referralEmail': None, 'referralPhone': None, 'virtualPlaygroundCommencement': None, 'gender': 'male', 'getAuthorisation': 'I authorise payments to be debited from the above payment details', 'paymentType': 'Credit Card', 'useExistingDetails': 'Yes', 'memberBankDetail': None, 'memberCreditCard': {'holder': 'Clint Test', 'address': '1 Street Rd', 'city': 'Sydney', 'state': 'NSW', 'postcode': '2000', 'number': '4111111111111111', 'expMonth': '12', 'expYear': '2022', 'verificationCode': '231'}, 'numberOneGoal': None, 'trainingAvailability': 'Any Day', 'timeAvailability': 'Before Work', 'medicalClearance': None, 'primarySignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/signature_94333525.png', 'paymentAuthSignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/signature_95430172.png', 'under18SignatureURL': None, 'accessKeyNumber': 'BA-99999', 'agreement': 'I have read, understood and agree to the Terms & Conditions | I acknowledge this is a minimum term of 26 fortnightly payments', 'memberPhotoURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/103257339_tickicon.png', 'covidVerificationURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/116315321_tickicon.png'}
    expected_save = {'id': 926, 'updateDate': '2021-10-10 11:53:41', 'status': 'SAVED', 'gymName': 'Surry Hills', 'contractNamesToBeActivated': 'First 21 Days # 0.00 | Access Key # 49.0 | Gym: Membership ($19.95) # 41.4/fortnight', 'fs_formId': '3931477', 'fs_uniqueId': '871580834', 'gymSalesId': None, 'googleClickId': None, 'facebookCampaignId': None, 'fs_formUrl': None, 'hasCoach': False, 'primarySignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/signature_94333525.png', 'paymentAuthSignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/signature_95430172.png', 'under18SignatureURL': None, 'memberContracts': '383,120,307', 'existingClient': False, 'firstBillingDate': '2021-10-31', 'createDate': '2021-10-10 10:11:22', 'locationId': '1', 'phone': '+61413506306', 'firstName': 'Clint', 'lastName': 'Test', 'email': 'clint@centheos.com', 'address1': '1 Street Rd', 'address2': None, 'city': 'Sydney', 'state': 'NSW', 'postcode': '2000', 'dob': '1967-10-25', 'gender': 'male', 'emergencyContactName': 'Em', 'emergencyContactPhone': '+61487654321', 'occupation': None, 'employer': None, 'howDidYouHearAboutUs': '', 'numberOneGoal': None, 'useExistingDetails': 'Yes', 'paymentType': 'Credit Card', 'getAuthorisation': 'I authorise payments to be debited from the above payment details', 'fpOrBunker': None, 'gymOrPlay': 'gym', 'ddOrPif': 'dd', 'noCommitment': '12Month', 'pifOptions': None, 'startDate': '2021-10-10', 'membershipTermsAcknowledgment': None, 'trainingStarterPack': "I'm a Fitness Guru", 'personalTrainer': '100000111::152065', 'lifestylePersonalTraining': None, 'numberSessionsPerWeek': None, 'sessionLength': None, 'externalPTSessionPrice': None, 'personalTrainingStartDate': None, 'personalTrainingTermsAcknowledgment': None, 'couponCode': 'NDaysFree,Key49,21DayComfortCancel', 'daysFree': 'First 21 Days', 'accessKeyDiscount': '$49 Access Key', 'freePTPack': None, 'accessKeyPaymentOptions': 'Add to First Payment', 'accessKeyPaymentMethod': None, 'staffMember': '6::152065', 'creche': None, 'staffName': 'Daniel Bowden', 'personalTrainerName': 'Clint Sellen', 'agreement': 'I have read, understood and agree to the Terms & Conditions | I acknowledge this is a minimum term of 26 fortnightly payments', 'contractCommitment': None, 'pt6SessionCommitment': None, 'accessKeyNumber': 'BA-99999', 'notes': 'Test Submission', 'injuries': None, 'medical': None, 'medicalClearance': None, 'trainingAvailability': 'Any Day', 'timeAvailability': 'Before Work', 'activeCampaignId': None, 'trainingPackageSoldBy': None, 'trainingPackageConsultantLocationId': None, 'trainingPackageConsultantName': None, 'trainingPackageConsultantMboId': None, 'communicationsStatus': 'EMAIL_CAMPAIGN_PENDING', 'serviceNamesToBeActivated': None, 'renewalStatus': None, 'hasReferral': False, 'referralName': None, 'referralPhone': None, 'referralEmail': None, 'dollarValue': None, 'memberPhotoURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/103257339_tickicon.png', 'virtualPlaygroundCommencement': None, 'mboUniqueId': None, 'preExId': '', 'covidVerificationURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/116315321_tickicon.png', 'memberCreditCard': {'id': 666, 'number': 'J2OYw8LuwVgsYEm06rmaADprXEjvrMK+mHIFgF6jEbcpQ+Pf5MI/5zAWcGvmxAUO5w74kfqQmtJVMBzxIL4XWYD4e6OPXBgL1i9sllQo73Rrnish2og/hMTnEAp44pgOMJ3/WPxwT/ZMy0aKtFncRO2q0YlflOeiDM19x32i6lslwAkDacwJrZhNOP/OBF9TfuYI8Hd00YB2vRUpBfkZNP9OcCJNpysYpStf2c7VIgAma2F+HLx9a6B7R8NZ7p7oKlS5Zxe5iOfgOC3qW6Qg8JUlL38nbvh8PzV6GY0EdYDdYd/sIfK/mc/6Gh9dum5/cIi48RNhUG9W8g1uxXu4wp08nDtBatGerrsU2lIA88fuduG3JapUo8KIsWiqqPEWQ+avcSMixoiuOMn00YwPCu7WImZFWgtPaXoB95ueWhpukDfKgkx/b7y2uwQTb1EUKqwrIcwrmKpXtY76kursxv0935BPqHTnKVQC4xf7bTDza9eZTTgJ0AlCoxkKr9vCMCbEyRhMCTAmPVANQcMqDQM1to84RSYrd7iNZT0rMSazEN+Di12fT19SWgkMtj7uhZ3Rfip3JsdEEg4W9t9xug4U2sL6HsviPDF3B0FTSpfELD1gePjkh15yDxwS0/gyjVt4oX7uDG4VwlMIfbAH5zyZtiXoG7eEqGUp19Bh9ts=', 'holder': 'Clint Test', 'city': 'Sydney', 'address': '1 Street Rd', 'state': 'NSW', 'postcode': '2000', 'expMonth': '12', 'expYear': '2022', 'type': None, 'verificationCode': 'i4vjL/F3o0IDFMdcWPbUWQKkeqdmFWyM1WGmWgRnIeKHuZ0UVWr9WL6xdVzvoTe/Lwtr8c+6DEkBwEFACA/pPKAO5lO09gApZiedCCOUeDg4Iqhh091KEbfI3fkHYq3mT/Uef8hTQ/OfgCcFOq3swcFHYLLx+VGNGQipdgBjmZjlLUTQJ6dtLbgtCfp+Q/oS8qACE7R0S5wyQPDEg4OubsYLDA3YkkuY68T4CXVFK2L0zFEAuFs4Go96/bVEZswVSqY+8Cmpm/jsQm+Pkuzn86oNhduTiFoeT29XfWMZG+Q/ZlEHBtmkOfnJyvoMKt9a3ecSBg/rDC/ZewYRkMhxdWfEFjee97KMwloV3EO6RCgOjJPKiBz8q1diIFeu3yVtKnTQ3e8IENucFF7OgCWQEaCwActNfOoGUIFVZpouVh/Kx83Q/UvixssQCJ6J3CXNdEpmHJ3bfESbQfOVcHsC3Cyi7QOxtKcY0agawDTj3JhrhmuUWAgOkOGc3becehy87lMFksMLovzhLbGPIXbfed3lsJI0vEW5ARMQaZsUm3L0tTqeVZDpGz63S02DrVWlQAWk6yCRW188ifyy3V8uSOmTS3elNbc9BXmm6BL6O9v19NhwHPFzvdshTapkb9S9TQf2WBCsbIr/ExjbnSF3fgBaSpV3r0zZKv0VblErcTs='}, 'memberBankDetail': None, 'uid': None, 'externalPt': False}
    execute_test_enrolment(expected_build, expected_save, event, 'COVID VERIFICATION')

    


@pytest.mark.enrolments
@pytest.mark.pif
def test_build_pif_enrolment_data():
    logger.info("test_build_pif_enrolment_data")
    Constants.IS_TEST = True
    logger.info(f"IS_TEST: {Constants.IS_TEST}")


    event = """{"96047912": "","96047915": {"first": "Clint","last": "Test"},"96047916": "clint@centheos.com","96047917": "0413 506 306","96047940": "99999","96047950": "","96047951": null,"96047952": null,"96047953": null,"96047954": "Use Existing Details","96047955": null,"96047956": null,"96047957": null,"96047958": null,"96048514": [{"value": "3","label": "Marrickville"}],"96048522": null,"96048728": ["Membership"],"96048980": null,"96048995": null,"96049023": null,"96049074": "99.00","96049084": null,"96049086": "99.00","96049156": null,"96049161": null,"96049163": null,"96049335": "Gym","96049550": null,"96049988": null,"96050145": null,"96050518": "10 Oct 2021","96050523": null,"96050529": null,"96051198": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871618984/signature_96051198.png","96051207": ["I authorise payments to be debited from the above payment details"],"96051256": null,"96051258": null,"96051259": null,"96051260": null,"96051261": null,"96051442": "99","96051500": null,"96051501": null,"96051883": null,"96051963": null,"96051971": null,"96051972": null,"96051983": "10 Oct 2021","96052002": null,"96052105": "0","96052158": "Test Submission","96052159": [{"value": "3","label": "Marrickville"}],"96052163": null,"96052164": null,"96052165": [{"value": "100000957::152065","label": "Dilawar Khan"}],"96052166": null,"96052758": "2021-10-10 14:45:44","96052760": "true","96053701": null,"96054641": null,"96054688": null,"96054716": null,"96055320": null,"99099982": "","99099985": "","99100086": "","99100956": "99.00","99100989": null,"99101008": null,"99101020": null,"99101254": null,"99101872": null,"99102124": null,"99102125": null,"99102128": null,"99102151": null,"99433277": null,"99436986": "Credit Card","101576172": [{"value": "99.00","label": "Gym: 1-Month Upfront ($99.00)"}],"101576175": null,"103260146": "","103929325": "","103929681": ["Please give the iPad to Team Member"],"103929805": "","103980866": null,"103981347": null,"103981769": "","103981875": null,"103985398": null,"103985421": null,"103985422": null,"103985423": null,"103985518": null,"103985524": null,"103985527": null,"103985530": null,"103985531": null,"103985548": null,"103985556": null,"103985559": null,"104212385": ["I have read, understood and agree to the Terms & Conditions"],"104212395": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871618984/signature_104212395.png","104212604": null,"104212635": null,"104212743": null,"104216341": null,"104407256": ["0.00"],"105333764": "Add to First Payment","105333768": null,"105333783": null,"105333795": null,"111842887": null,"111842888": null,"111842889": null,"111843405": null,"111843406": null,"111843407": null,"111843440": null,"111959440": "","116316745": "No","116316748": null,"116316751": ["I acknowledge access to the gym will be restricted until proof of  COVID Vaccination or Medical Contraindication is provided"],"FormID": "3980725","UniqueID": "871618984","HandshakeKey": "SHARED SECRET"}"""
    expected_build = {'fs_uniqueId': '871618984', 'fs_formId': '3980725', 'googleClickId': None, 'facebookCampaignId': None, 'existingClient': None, 'gymSalesId': None, 'locationId': '3', 'gymName': 'Marrickville', 'createDate': '2021-10-10 14:45:44', 'updateDate': '2021-10-10 14:53:52', 'status': 'PROCESSING', 'communicationsStatus': 'EMAIL_CAMPAIGN_PENDING', 'daysFree': None, 'accessKeyNumber': 'BA-99999', 'accessKeyPaymentOptions': 'Add to First Payment', 'accessKeyPaymentMethod': None, 'gymOrPlay': 'gym', 'ddOrPif': 'pif', 'noCommitment': 'noCommitment', 'contractNamesToBeActivated': 'Gym: 1-Month Upfront ($99.00) # 99.0', 'memberContracts': '312', 'hasCoach': False, 'serviceNamesToBeActivated': None, 'pifOptions': 'Membership', 'trainingStarterPack': None, 'personalTrainer': None, 'personalTrainerName': None, 'staffMember': '100000957::152065', 'staffName': 'Dilawar Khan', 'trainingPackageSoldBy': None, 'trainingPackageConsultantLocationId': None, 'trainingPackageConsultantName': None, 'trainingPackageConsultantMboId': None, 'phone': '+61413506306', 'firstName': 'Clint', 'lastName': 'Test', 'email': 'clint@centheos.com', 'startDate': '2021-10-10 00:00:00', 'firstBillingDate': '2021-10-10 00:00:00', 'notes': 'Test Submission', 'renewalStatus': None, 'getAuthorisation': 'I authorise payments to be debited from the above payment details', 'paymentAuthSignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871618984/signature_96051198.png', 'paymentType': 'Existing Credit Card', 'memberPhotoURL': '', 'hasReferral': False, 'medicalClearance': None, 'agreement': 'I have read, understood and agree to the Terms & Conditions | ', 'primarySignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871618984/signature_104212395.png', 'covidVerificationURL': None}
    expected_save = {'id': 932, 'updateDate': '2021-10-10 14:53:52', 'status': 'SAVED', 'gymName': 'Marrickville', 'contractNamesToBeActivated': 'Gym: 1-Month Upfront ($99.00) # 99.0', 'fs_formId': '3980725', 'fs_uniqueId': '871618984', 'gymSalesId': None, 'googleClickId': None, 'facebookCampaignId': None, 'fs_formUrl': None, 'hasCoach': False, 'primarySignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871618984/signature_104212395.png', 'paymentAuthSignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871618984/signature_96051198.png', 'under18SignatureURL': None, 'memberContracts': '312', 'existingClient': False, 'firstBillingDate': '2021-10-10 00:00:00', 'createDate': '2021-10-10 14:45:44', 'locationId': '3', 'phone': '+61413506306', 'firstName': 'Clint', 'lastName': 'Test', 'email': 'clint@centheos.com', 'address1': None, 'address2': None, 'city': None, 'state': None, 'postcode': None, 'dob': None, 'gender': None, 'emergencyContactName': None, 'emergencyContactPhone': None, 'occupation': None, 'employer': None, 'howDidYouHearAboutUs': None, 'numberOneGoal': None, 'useExistingDetails': None, 'paymentType': 'Existing Credit Card', 'getAuthorisation': 'I authorise payments to be debited from the above payment details', 'fpOrBunker': None, 'gymOrPlay': 'gym', 'ddOrPif': 'pif', 'noCommitment': 'noCommitment', 'pifOptions': 'Membership', 'startDate': '2021-10-10 00:00:00', 'membershipTermsAcknowledgment': None, 'trainingStarterPack': None, 'personalTrainer': None, 'lifestylePersonalTraining': None, 'numberSessionsPerWeek': None, 'sessionLength': None, 'externalPTSessionPrice': None, 'personalTrainingStartDate': None, 'personalTrainingTermsAcknowledgment': None, 'couponCode': None, 'daysFree': None, 'accessKeyDiscount': None, 'freePTPack': None, 'accessKeyPaymentOptions': 'Add to First Payment', 'accessKeyPaymentMethod': None, 'staffMember': '100000957::152065', 'creche': None, 'staffName': 'Dilawar Khan', 'personalTrainerName': None, 'agreement': 'I have read, understood and agree to the Terms & Conditions | ', 'contractCommitment': None, 'pt6SessionCommitment': None, 'accessKeyNumber': 'BA-99999', 'notes': 'Test Submission', 'injuries': None, 'medical': None, 'medicalClearance': None, 'trainingAvailability': None, 'timeAvailability': None, 'activeCampaignId': None, 'trainingPackageSoldBy': None, 'trainingPackageConsultantLocationId': None, 'trainingPackageConsultantName': None, 'trainingPackageConsultantMboId': None, 'communicationsStatus': 'EMAIL_CAMPAIGN_PENDING', 'serviceNamesToBeActivated': None, 'renewalStatus': None, 'hasReferral': False, 'referralName': None, 'referralPhone': None, 'referralEmail': None, 'dollarValue': None, 'memberPhotoURL': '', 'virtualPlaygroundCommencement': None, 'mboUniqueId': None, 'preExId': None, 'covidVerificationURL': None, 'memberCreditCard': None, 'memberBankDetail': None, 'uid': None, 'externalPt': False}
    execute_test_enrolment(expected_build, expected_save, event, 'COVID VERIFICATION')




@pytest.mark.enrolments
@pytest.mark.enrolment_2_part
def test_build_enrolment_data_entity_2_part():
    logger.info("test_build_enrolment_2_part_data_entity")
    Constants.IS_TEST = True
    logger.info(f"IS_TEST: {Constants.IS_TEST}")

    # # Part 1 FP
    event = """{"FormID":"3929752","UniqueID":"828987623","HandshakeKey":"SHARED SECRET","94276624":"true","94276625":"2021-06-28 15:20:05","111958479":"","94276626":"","94403487":[{"value":"3","label":"Marrickville"}],"94276679":[{"value":"6::152065","label":"Daniel Bowden"}],"94276680":null,"94276681":null,"94276682":null,"94276627":{"first":"Clint","last":"Sellen"},"94276628":{"address":"120A Devonshire Street","city":"Surry Hills","state":"NSW","zip":"2010"},"94276629":"0413 506 306","94276630":"clint@thefitnessplayground.com.au","94276631":"28 Jun 2004","94469371":"17","94276633":"Clint Sellen","94276634":"0413 506 306","94276635":"","94276636":"","94276637":"","94276638":"","94276639":[{"value":"3","label":"Marrickville"}],"94276640":null,"101491364":[{"value":"59.40","label":"Play: Membership ($28.95)"}],"94276641":null,"94276642":null,"94276643":"Coaching","94276644":"Face to Face","94327899":null,"94276645":"40 Minutes","94276649":null,"94276650":[{"value":"354.00","label":"PT: 3x 40 Min Sessions Per Week"}],"94276651":null,"94276652":null,"94276653":null,"94276654":null,"94276655":["0.00"],"94276656":["0.00"],"94276657":["0.00"],"94276658":["0.00"],"94276659":["0.00"],"94276660":null,"94403802":null,"94403885":null,"94350160":["Add Creche"],"94349722":[{"value":"10.00","label":"Creche: Unlimited x 1 Child"}],"94280674":[{"value":"19.95","label":"Add Virtual Playground for $19.95 a month"}],"105330030":"Start on my First Billing Day","94276665":["Promotions"],"94276667":["Two Free PT"],"94276668":null,"105330104":null,"111954875":null,"111954881":[{"value":"21","label":"21 Day Satisfaction Period"}],"111954879":null,"102849320":null,"102849318":null,"95000322":"0","94276669":null,"111954811":null,"111954821":[{"value":"21","label":"21 Days Free"}],"111954822":null,"94276670":null,"111954856":null,"111954857":[{"value":"-69","label":"$30 Access Key"}],"111954861":null,"94330525":"0","94276672":"99","105330044":"Add to First Payment","105330050":null,"94276673":"30.00","94276674":"30.00","94350283":"10.00","94349002":"354.00","94350367":"59.40","94276675":"423.40","94331302":"19.95","94276677":"28 Jun 2021","94276678":"28 Jun 2021","102853638":"","105332216":"7","111955650":"21","105332191":"","111955654":"","94468404":"28 Jun 2021","94468412":"12 Jul 2021","94276661":[{"value":"100000128::152065","label":"Tom Merriman"}],"94276662":null,"94276663":null,"94276664":null,"96024208":"Other","98537268":[{"value":"5","label":"The Bunker"}],"98537284":null,"98537279":null,"98537276":null,"98537286":[{"value":"100000580::152065","label":"Ben Shepherd"}],"94276683":"","94276693":"Test Submission","94276689":null,"94276690":null,"94276692":null,"94276691":null,"104075740":null,"104075750":null,"104075752":null,"104075753":null,"104075755":null,"104075759":null,"104075761":null,"104075762":null}"""
    expected_build = {'fs_uniqueId': '828987623', 'fs_formId': '3929752', 'mboUniqueId': None, 'preExId': '', 'googleClickId': None, 'facebookCampaignId': None, 'existingClient': None, 'gymSalesId': None, 'locationId': '3', 'gymName': 'Marrickville', 'createDate': '2021-06-28 15:20:05', 'updateDate': '2021-07-02 16:15:01', 'status': 'ENROLMENT_AUTHORISATION_PENDING', 'communicationsStatus': 'CLIENT_AUTHORISATION_SENT', 'daysFree': 'First 21 Days', 'creche': 'Creche: Unlimited x 1 Child', 'accessKeyPaymentOptions': 'Add to First Payment', 'accessKeyPaymentMethod': None, 'accessKeyDiscount': '$30 Access Key', 'freePTPack': 'Two Free PT', 'isExternalPt': False, 'hasCoach': True, 'sessionLength': '40 Minutes', 'trainingStarterPack': 'Coaching', 'noCommitment': '12Month', 'gymOrPlay': 'play', 'ddOrPif': 'dd', 'contractNamesToBeActivated': 'First 21 Days # 0.00 | Virtual Playground # 19.95/month | Creche: Unlimited x 1 Child # 10.0/fortnight | Access Key # 30.0 | Packs: FP Coach Pack # 0.00 | PT: 3x 40 Min Sessions Per Week # 354.0/fortnight | Play: Membership ($28.95) # 59.4/fortnight', 'memberContracts': '383,335,141,120,127,303,170', 'couponCode': 'NDaysFree,Key30,FreePTPack,21DayComfortCancel', 'startDate': '2021-06-28', 'firstBillingDate': '2021-06-28', 'personalTrainingStartDate': '2021-07-12', 'staffMember': '6::152065', 'staffName': 'Daniel Bowden', 'personalTrainer': '100000128::152065', 'personalTrainerName': 'Tom Merriman', 'trainingPackageSoldBy': 'Other', 'trainingPackageConsultantLocationId': '5', 'trainingPackageConsultantName': 'Ben Shepherd', 'trainingPackageConsultantMboId': '100000580::152065', 'phone': '+61413506306', 'firstName': 'Clint', 'lastName': 'Sellen', 'email': 'clint@thefitnessplayground.com.au', 'address1': '120A Devonshire Street', 'city': 'Surry Hills', 'state': 'NSW', 'postcode': '2010', 'dob': '2004-06-28', 'emergencyContactName': 'Clint Sellen', 'emergencyContactPhone': '+61413506306', 'notes': 'Test Submission', 'howDidYouHearAboutUs': '', 'hasReferral': False, 'referralName': None, 'referralEmail': None, 'referralPhone': None, 'virtualPlaygroundCommencement': 'Start on my First Billing Day'}
    expected_save = {'id': 922, 'updateDate': '2021-07-02 16:15:01', 'status': 'SAVED', 'gymName': 'Marrickville', 'contractNamesToBeActivated': 'First 21 Days # 0.00 | Virtual Playground # 19.95/month | Creche: Unlimited x 1 Child # 10.0/fortnight | Access Key # 30.0 | Packs: FP Coach Pack # 0.00 | PT: 3x 40 Min Sessions Per Week # 354.0/fortnight | Play: Membership ($28.95) # 59.4/fortnight', 'fs_formId': '3929752', 'fs_uniqueId': '828987623', 'gymSalesId': None, 'googleClickId': None, 'facebookCampaignId': None, 'fs_formUrl': None, 'hasCoach': True, 'primarySignatureURL': None, 'paymentAuthSignatureURL': None, 'under18SignatureURL': None, 'memberContracts': '383,335,141,120,127,303,170', 'existingClient': False, 'firstBillingDate': '2021-06-28', 'createDate': '2021-06-28 15:20:05', 'locationId': '3', 'phone': '+61413506306', 'firstName': 'Clint', 'lastName': 'Sellen', 'email': 'clint@thefitnessplayground.com.au', 'address1': '120a Devonshire Street', 'address2': None, 'city': 'Surry Hills', 'state': 'NSW', 'postcode': '2010', 'dob': '2004-06-28', 'gender': None, 'emergencyContactName': 'Clint Sellen', 'emergencyContactPhone': '+61413506306', 'occupation': None, 'employer': None, 'howDidYouHearAboutUs': '', 'numberOneGoal': None, 'useExistingDetails': None, 'paymentType': None, 'getAuthorisation': None, 'fpOrBunker': None, 'gymOrPlay': 'play', 'ddOrPif': 'dd', 'noCommitment': '12Month', 'pifOptions': None, 'startDate': '2021-06-28', 'membershipTermsAcknowledgment': None, 'trainingStarterPack': 'Coaching', 'personalTrainer': '100000128::152065', 'lifestylePersonalTraining': None, 'numberSessionsPerWeek': None, 'sessionLength': '40 Minutes', 'externalPTSessionPrice': None, 'personalTrainingStartDate': '2021-07-12', 'personalTrainingTermsAcknowledgment': None, 'couponCode': 'NDaysFree,Key30,FreePTPack,21DayComfortCancel', 'daysFree': 'First 21 Days', 'accessKeyDiscount': '$30 Access Key', 'freePTPack': 'Two Free PT', 'accessKeyPaymentOptions': 'Add to First Payment', 'accessKeyPaymentMethod': None, 'staffMember': '6::152065', 'creche': 'Creche: Unlimited x 1 Child', 'staffName': 'Daniel Bowden', 'personalTrainerName': 'Tom Merriman', 'agreement': None, 'contractCommitment': None, 'pt6SessionCommitment': None, 'accessKeyNumber': None, 'notes': 'Test Submission', 'injuries': None, 'medical': None, 'medicalClearance': None, 'trainingAvailability': None, 'timeAvailability': None, 'activeCampaignId': None, 'trainingPackageSoldBy': 'Other', 'trainingPackageConsultantLocationId': '5', 'trainingPackageConsultantName': 'Ben Shepherd', 'trainingPackageConsultantMboId': '100000580::152065', 'communicationsStatus': 'CLIENT_AUTHORISATION_SENT', 'serviceNamesToBeActivated': None, 'renewalStatus': None, 'hasReferral': False, 'referralName': None, 'referralPhone': None, 'referralEmail': None, 'dollarValue': None, 'memberPhotoURL': None, 'virtualPlaygroundCommencement': 'Start on my First Billing Day', 'mboUniqueId': None, 'preExId': '', 'memberCreditCard': None, 'memberBankDetail': None, 'uid': None, 'externalPt': False}
    execute_test_enrolment(expected_build, expected_save, event, 'Everything - Membership Consultant: Marrickville, Home Club Marrickville')

    # Part 2 FP
    event = """{"FormID":"3929753","UniqueID":"829691687","HandshakeKey":"SHARED_SECRET","94276695":"true","94276694":"828987623","94468037":"3929752","94468156":"6::152065","94277165":"3","94468157":"100000128::152065","94276713":"","94276696":"true","94468050":"true","94468932":"true","94468931":"true","94468933":"true","105330479":"false","94468133":"false","96024211":"No","94545782":"true","94469054":"true","94469066":"false","94469067":"true","94276697":"2021-06-29 15:25:58","94276698":"","94276699":{"first":"Clint","last":"Sellen"},"94276700":{"address":"120a Devonshire Street","city":"Surry Hills","state":"NSW","zip":"2010"},"94276701":"0413 506 306","94276702":"clint@thefitnessplayground.com.au","94276703":"28 Jun 2004","94469312":"17","94276704":"male","94276705":"Clint Sellen","94276706":"0413 506 306","94276707":"","94276708":"Marrickville","94276709":"Play: Membership ($28.95)","94276710":"PT: 3x 40 Min Sessions Per Week","94469431":"0.00","94469001":"Two Free PT","94276711":"30.00","94468995":"$30 Access Key","94276714":"30.00","94468209":"10.00","94468213":"354.00","94468219":"59.40","94276715":"423.40","94276712":"19.95","94276717":"28 Jun 2021","94276718":"28 Jun 2021","105330724":"","105330721":"","94468999":"First 21 Days","94468460":"28 Jun 2021","94468462":"12 Jul 2021","94276719":"Tom Merriman","94276720":"Daniel Bowden","94276721":"","94276722":"Test Submission","94276723":"","94277713":"Yes","94277828":null,"94277830":null,"94276724":"Credit Card","94276725":{"card":"4111111111111111","cardexp":"12/21","cvv":"123"},"94276729":null,"94276730":null,"94276731":null,"94469216":["I authorise payments to be debited from the above payment details"],"94469234":"https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94469234.png","94468291":"","94468292":"Build Muscle","94468293":null,"94468294":["Any Day"],"94468295":["Anytime"],"94468296":null,"94468297":null,"94468298":null,"94468299":null,"94468300":null,"94468301":null,"94468260":"","94468261":"","94468262":["I have read, understood and agree to the Terms & Conditions"],"94468263":["I commit to a minimum of six (6) paid Personal Training Sessions"],"94468264":null,"94468265":["I acknowledge this is a minimum term of 26 fortnightly payments"],"94468266":"https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94468266.png","94468267":"https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94468267.png","103259922":"","94546176":""}"""
    expected_build = {'id': 922, 'updateDate': '2021-07-02 16:19:39', 'status': 'ENROLMENT_AUTHORISED', 'gymName': 'Marrickville', 'contractNamesToBeActivated': 'First 21 Days # 0.00 | Virtual Playground # 19.95/month | Creche: Unlimited x 1 Child # 10.0/fortnight | Access Key # 30.0 | Packs: FP Coach Pack # 0.00 | PT: 3x 40 Min Sessions Per Week # 354.0/fortnight | Play: Membership ($28.95) # 59.4/fortnight', 'fs_formId': '3929753', 'fs_uniqueId': '829691687', 'gymSalesId': None, 'googleClickId': None, 'facebookCampaignId': None, 'fs_formUrl': None, 'hasCoach': True, 'primarySignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94468267.png', 'paymentAuthSignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94469234.png', 'under18SignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94468266.png', 'memberContracts': '383,335,141,120,127,303,170', 'existingClient': False, 'firstBillingDate': '2021-06-28', 'createDate': '2021-06-28 15:20:05', 'locationId': '3', 'phone': '+61413506306', 'firstName': 'Clint', 'lastName': 'Sellen', 'email': 'clint@thefitnessplayground.com.au', 'address1': '120A Devonshire Street', 'address2': None, 'city': 'Surry Hills', 'state': 'NSW', 'postcode': '2010', 'dob': '2004-06-28', 'gender': 'male', 'emergencyContactName': 'Clint Sellen', 'emergencyContactPhone': '+61413506306', 'occupation': None, 'employer': None, 'howDidYouHearAboutUs': '', 'numberOneGoal': 'Build Muscle', 'useExistingDetails': 'Yes', 'paymentType': 'Credit Card', 'getAuthorisation': 'I authorise payments to be debited from the above payment details', 'fpOrBunker': None, 'gymOrPlay': 'play', 'ddOrPif': 'dd', 'noCommitment': '12Month', 'pifOptions': None, 'startDate': '2021-06-28', 'membershipTermsAcknowledgment': None, 'trainingStarterPack': 'Coaching', 'personalTrainer': '100000128::152065', 'lifestylePersonalTraining': None, 'numberSessionsPerWeek': None, 'sessionLength': '40 Minutes', 'externalPTSessionPrice': None, 'personalTrainingStartDate': '2021-07-12', 'personalTrainingTermsAcknowledgment': None, 'couponCode': 'NDaysFree,Key30,FreePTPack,21DayComfortCancel', 'daysFree': 'First 21 Days', 'accessKeyDiscount': '$30 Access Key', 'freePTPack': 'Two Free PT', 'accessKeyPaymentOptions': 'Add to First Payment', 'accessKeyPaymentMethod': None, 'staffMember': '6::152065', 'creche': 'Creche: Unlimited x 1 Child', 'staffName': 'Daniel Bowden', 'personalTrainerName': 'Tom Merriman', 'agreement': 'I have read, understood and agree to the Terms & Conditions | I commit to a minimum of six (6) paid Personal Training Sessions | I acknowledge this is a minimum term of 26 fortnightly payments', 'contractCommitment': None, 'pt6SessionCommitment': None, 'accessKeyNumber': None, 'notes': 'Test Submission', 'injuries': None, 'medical': None, 'medicalClearance': None, 'trainingAvailability': 'Any Day', 'timeAvailability': 'Anytime', 'activeCampaignId': None, 'trainingPackageSoldBy': 'Other', 'trainingPackageConsultantLocationId': '5', 'trainingPackageConsultantName': 'Ben Shepherd', 'trainingPackageConsultantMboId': '100000580::152065', 'communicationsStatus': 'CLIENT_AUTHORISATION_RECEIVED', 'serviceNamesToBeActivated': None, 'renewalStatus': None, 'hasReferral': False, 'referralName': None, 'referralPhone': None, 'referralEmail': None, 'dollarValue': None, 'memberPhotoURL': '', 'virtualPlaygroundCommencement': 'Start on my First Billing Day', 'mboUniqueId': None, 'preExId': '', 'memberCreditCard': {'holder': 'Clint Sellen', 'address': '120a Devonshire Street', 'city': 'Surry Hills', 'state': 'NSW', 'postcode': '2010', 'number': '4111111111111111', 'expMonth': '12', 'expYear': '2021', 'verificationCode': '123'}, 'memberBankDetail': None, 'uid': None, 'externalPt': False}
    expected_save = {'id': 922, 'updateDate': '2021-07-02 16:19:39', 'status': 'ENROLMENT_AUTHORISED', 'gymName': 'Marrickville', 'contractNamesToBeActivated': 'First 21 Days # 0.00 | Virtual Playground # 19.95/month | Creche: Unlimited x 1 Child # 10.0/fortnight | Access Key # 30.0 | Packs: FP Coach Pack # 0.00 | PT: 3x 40 Min Sessions Per Week # 354.0/fortnight | Play: Membership ($28.95) # 59.4/fortnight', 'fs_formId': '3929753', 'fs_uniqueId': '829691687', 'gymSalesId': None, 'googleClickId': None, 'facebookCampaignId': None, 'fs_formUrl': None, 'hasCoach': True, 'primarySignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94468267.png', 'paymentAuthSignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94469234.png', 'under18SignatureURL': 'https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94468266.png', 'memberContracts': '383,335,141,120,127,303,170', 'existingClient': False, 'firstBillingDate': '2021-06-28', 'createDate': '2021-06-28 15:20:05', 'locationId': '3', 'phone': '+61413506306', 'firstName': 'Clint', 'lastName': 'Sellen', 'email': 'clint@thefitnessplayground.com.au', 'address1': '120a Devonshire Street', 'address2': None, 'city': 'Surry Hills', 'state': 'NSW', 'postcode': '2010', 'dob': '2004-06-28', 'gender': 'male', 'emergencyContactName': 'Clint Sellen', 'emergencyContactPhone': '+61413506306', 'occupation': None, 'employer': None, 'howDidYouHearAboutUs': '', 'numberOneGoal': 'Build Muscle', 'useExistingDetails': 'Yes', 'paymentType': 'Credit Card', 'getAuthorisation': 'I authorise payments to be debited from the above payment details', 'fpOrBunker': None, 'gymOrPlay': 'play', 'ddOrPif': 'dd', 'noCommitment': '12Month', 'pifOptions': None, 'startDate': '2021-06-28', 'membershipTermsAcknowledgment': None, 'trainingStarterPack': 'Coaching', 'personalTrainer': '100000128::152065', 'lifestylePersonalTraining': None, 'numberSessionsPerWeek': None, 'sessionLength': '40 Minutes', 'externalPTSessionPrice': None, 'personalTrainingStartDate': '2021-07-12', 'personalTrainingTermsAcknowledgment': None, 'couponCode': 'NDaysFree,Key30,FreePTPack,21DayComfortCancel', 'daysFree': 'First 21 Days', 'accessKeyDiscount': '$30 Access Key', 'freePTPack': 'Two Free PT', 'accessKeyPaymentOptions': 'Add to First Payment', 'accessKeyPaymentMethod': None, 'staffMember': '6::152065', 'creche': 'Creche: Unlimited x 1 Child', 'staffName': 'Daniel Bowden', 'personalTrainerName': 'Tom Merriman', 'agreement': 'I have read, understood and agree to the Terms & Conditions | I commit to a minimum of six (6) paid Personal Training Sessions | I acknowledge this is a minimum term of 26 fortnightly payments', 'contractCommitment': None, 'pt6SessionCommitment': None, 'accessKeyNumber': None, 'notes': 'Test Submission', 'injuries': None, 'medical': None, 'medicalClearance': None, 'trainingAvailability': 'Any Day', 'timeAvailability': 'Anytime', 'activeCampaignId': None, 'trainingPackageSoldBy': 'Other', 'trainingPackageConsultantLocationId': '5', 'trainingPackageConsultantName': 'Ben Shepherd', 'trainingPackageConsultantMboId': '100000580::152065', 'communicationsStatus': 'CLIENT_AUTHORISATION_RECEIVED', 'serviceNamesToBeActivated': None, 'renewalStatus': None, 'hasReferral': False, 'referralName': None, 'referralPhone': None, 'referralEmail': None, 'dollarValue': None, 'memberPhotoURL': '', 'virtualPlaygroundCommencement': 'Start on my First Billing Day', 'mboUniqueId': None, 'preExId': '', 'memberCreditCard': {'id': 663, 'number': '4111111111111111', 'holder': 'Clint Sellen', 'city': 'Surry Hills', 'address': '120a Devonshire Street', 'state': 'NSW', 'postcode': '2010', 'expMonth': '12', 'expYear': '2021', 'type': None, 'verificationCode': '123'}, 'memberBankDetail': None, 'uid': None, 'externalPt': False}
    execute_test_enrolment(expected_build, expected_save, event, 'Everything - Membership Consultant: Marrickville, Home Club Marrickville')




def execute_test_fp_coach_enrolment(expected_build, expected_save, event, description):

    Constants.IS_TEST = True

    logger.info(f"********************************** Starting Test: {description} IS_TEST = {Constants.IS_TEST}\n\n")

# ####################################################################### ACTIVE CODE - handler.py
    body = json.loads(event)
    _ids = FS_FIELD_IDS.FORM[body["FormID"]]
# ####################################################################### ACTIVE CODE - handler.py


    TEST_CONSTANTS.RANDOM_EMAIL = generate_random_email()
    TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID = str(generate_random_integer(9))
    TEST_CONSTANTS.RANDOM_ACCESS_KEY = f"ZZ-{generate_random_integer(7)}"

    expected_build['email'] = TEST_CONSTANTS.RANDOM_EMAIL
    expected_build['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID

# ####################################################################### ACTIVE CODE - handler.py
    FP_COACH_ENTITY = build_fp_coach_enrolment_data(body)
# ####################################################################### ACTIVE CODE - handler.py

    FP_COACH_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL
    FP_COACH_ENTITY['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID
    expected_build['createDate'] = FP_COACH_ENTITY['createDate']
    expected_build['updateDate'] = FP_COACH_ENTITY['updateDate']

    assert FP_COACH_ENTITY == expected_build, f"FP COACH BUILD - {description}"
    logger.info(f"FP COACH BUILD - Complete\n\n")

# ####################################################################### ACTIVE CODE - handler.py
    FP_COACH_ENTITY = source_post_request(Constants.SOURCE_FP_COACH_ENROLMENT_URL, FP_COACH_ENTITY)
# ####################################################################### ACTIVE CODE - handler.py

    expected_save['email'] = TEST_CONSTANTS.RANDOM_EMAIL
    expected_save['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID
    expected_save['updateDate'] = FP_COACH_ENTITY['updateDate']
    expected_save['id'] = FP_COACH_ENTITY['id']
    expected_save['memberCreditCard'] = FP_COACH_ENTITY['memberCreditCard']
    expected_save['memberBankDetail'] = FP_COACH_ENTITY['memberBankDetail']\

    assert FP_COACH_ENTITY == expected_save, f"FP COACH SAVE - {description}"
    logger.info(f"FP COACH SAVE - Complete\n\n")


    sqs_body = {'type' : 'fpCoachEnrolment', 'id' : expected_save['id']}

# This is where the submission needs to be added to the Queue
    execute_test_process_fp_coach_enrolment(expected_save, sqs_body, description)

    logger.info(f"Finished Test: {description}")
    return


# ############################################################################################################################################
# From here is executed from the Queue                                                                                                       #
# ############################################################################################################################################
def execute_test_process_fp_coach_enrolment(expected, sqs_body, description):

    os.environ['PdfSqsUrl'] = "https://sqs.ap-southeast-2.amazonaws.com/346880879164/PdfSQS.fifo"
    logger.info(f"PDF SQS URL {os.getenv('PdfSqsUrl')}")

    # ######################################################################
    # GET ENROLMENT                                                        #
    # ######################################################################
# ####################################################################### ACTIVE CODE - handler.py
    IS_MBO_SUCCESS = True
    URL = 'source/'+ sqs_body['type'] +'/'+ str(sqs_body['id']) +'/false'
    FP_COACH_ENTITY = source_get_request(URL)

    if FP_COACH_ENTITY is None:
        print("Error - FP Coach Enrolment Doesn't Exist in Source")
        logger.error("Error - FP Coach Enrolment Doesn't Exist in Source")
        handle_manual_submission(MemberStatus.CLIENT_NOT_FOUND, None, FP_COACH_ENTITY)
        IS_MBO_SUCCESS = False
# ####################################################################### ACTIVE CODE - handler.py

    logger.info(f"\n\nFP Coach Enrolment Entity for Processing: {FP_COACH_ENTITY}\n\n")

    expected['memberCreditCard'] = FP_COACH_ENTITY['memberCreditCard']
    expected['memberBankDetail'] = FP_COACH_ENTITY['memberBankDetail']
    expected['updateDate'] = FP_COACH_ENTITY['updateDate']

    assert FP_COACH_ENTITY == expected, f"GET FP COACH ENROLMENT: {FP_COACH_ENTITY['id']} - {description}"
    logger.info(f"GET FP COACH ENROLMENT: {FP_COACH_ENTITY['id']} - Complete\n\n")


    # if generate_random_integer(2) % 5 != 0:
    FP_COACH_ENTITY['email'] = "clint@centheos.com"

# ####################################################################### ACTIVE CODE - handler.py
    if IS_MBO_SUCCESS:
        # ######################################################################
        # GET MBO CLIENT                                                       #
        # ######################################################################
        PROCESS_ENTITY = get_mbo_client(FP_COACH_ENTITY)
        
        FP_COACH_ENTITY = PROCESS_ENTITY['FP_COACH_ENTITY']
        MBO_CLIENT = PROCESS_ENTITY['MBO_CLIENT']
        EXISTING_CREDIT_CARD = PROCESS_ENTITY['EXISTING_CREDIT_CARD']
        EXISTING_BANK_DETIALS = PROCESS_ENTITY['EXISTING_BANK_DETIALS']
        IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
# ####################################################################### ACTIVE CODE - handler.py
        

        if IS_MBO_SUCCESS:
            assert MBO_CLIENT != None, f"GET MBO CLIENT: {FP_COACH_ENTITY['id']} - {description}"
            logger.info(f"GET MBO CLIENT: {FP_COACH_ENTITY['id']} - Complete\n\n")
        else:
            assert MBO_CLIENT == None, f"MBO CLIENT NOT FOUND: {FP_COACH_ENTITY['id']} - {description}"
            logger.info(f"MBO CLIENT NOT FOUND: {FP_COACH_ENTITY['id']} - Complete\n\n")


# ####################################################################### ACTIVE CODE - handler.py
        if IS_MBO_SUCCESS:
            # ######################################################################
            # ADD BANK DETAILS                                                     #
            # ######################################################################
            if FP_COACH_ENTITY['paymentType'] == Constants.FS_PAYMENT_TYPE_BANK_ACCOUNT or FP_COACH_ENTITY['paymentType'] == Constants.FS_PAYMENT_TYPE_DIRECT_DEBIT:
                if FP_COACH_ENTITY['memberBankDetail'] is not None:

                    PROCESS_ENTITY = coach_add_bank_details(FP_COACH_ENTITY, MBO_CLIENT)

                    IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
                    FP_COACH_ENTITY = PROCESS_ENTITY['FP_COACH_ENTITY']
# ####################################################################### ACTIVE CODE - handler.py
    
                    assert IS_MBO_SUCCESS == True, f"COACH ADD BANK DETAILS - {description}"
                    logger.info(f"COACH ADD BANK DETAILS: {FP_COACH_ENTITY['id']} - Complete\n\n")


# ####################################################################### ACTIVE CODE - handler.py
            # ######################################################################
            # PURCHASE CONTRACTS                                                   #
            # ######################################################################
            PROCESS_ENTITY = coach_purchase_contracts(FP_COACH_ENTITY, MBO_CLIENT)
            IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
            FP_COACH_ENTITY = PROCESS_ENTITY['FP_COACH_ENTITY']
# ####################################################################### ACTIVE CODE - handler.py

            assert IS_MBO_SUCCESS == True, f"EXECUTE PURCHASE CONTRACTS: {c} FAILED - {description}"
            logger.info(f"EXECUTE PURCHASE CONTRACTS: {description} - Complete\n\n")

            FP_COACH_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL
            logger.info(f"IS_MBO_SUCCESS: {IS_MBO_SUCCESS}")

    
# ####################################################################### ACTIVE CODE - handler.py
            # #######################################################################################################
            # SET STATUS AND UPDATE DATABASE - A SUCCESS status will trigger payment details to be washed.          #
            # #######################################################################################################
            if IS_MBO_SUCCESS:
                FP_COACH_ENTITY['status'] = MemberStatus.SUCCESS[0]
                
            UPDATED_FP_COACH_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", FP_COACH_ENTITY)
# ####################################################################### ACTIVE CODE - handler.py


            FP_COACH_ENTITY['memberCreditCard'] = UPDATED_FP_COACH_ENTITY['memberCreditCard']
            FP_COACH_ENTITY['memberBankDetail'] = UPDATED_FP_COACH_ENTITY['memberBankDetail']
            FP_COACH_ENTITY['updateDate'] = UPDATED_FP_COACH_ENTITY['updateDate']
            FP_COACH_ENTITY['startDate'] = UPDATED_FP_COACH_ENTITY['startDate']

            logger.info(f"\n\nFP_COACH_ENTITY: {FP_COACH_ENTITY}\n\nUPDATED_FP_COACH_ENTITY: {UPDATED_FP_COACH_ENTITY}\n\n")

            FP_COACH_ENTITY['status'] = UPDATED_FP_COACH_ENTITY['status']

            assert FP_COACH_ENTITY == UPDATED_FP_COACH_ENTITY, f"SET STATUS AND UPDATE DATABASE - {description}"
            logger.info(f"SET STATUS AND UPDATE DATABASE: {FP_COACH_ENTITY['id']} - Complete\n\n")

            FP_COACH_ENTITY = UPDATED_FP_COACH_ENTITY


            FP_COACH_ENTITY['email'] = "clint@centheos.com"

# ####################################################################### ACTIVE CODE - handler.py
            # ######################################################################
            # TRIGGER PDF WRITER - Push onto SQS Queue                             #
            # ######################################################################
            FP_COACH_ENTITY = trigger_pdf_writer(FP_COACH_ENTITY, MBO_CLIENT, "fpCoachEnrolment")
# ####################################################################### ACTIVE CODE - handler.py


        logger.info(f"\n\t TRIGGER COMMUNICATIONS  - Preparation: TEST_EMAIL == {FP_COACH_ENTITY['email']}")


# ####################################################################### ACTIVE CODE - handler.py
        # ######################################################################
        # TRIGGER COMMUNICATIONS                                               #
        # ######################################################################
        FP_COACH_ENTITY = trigger_communications(FP_COACH_ENTITY, sqs_body['type'], str(sqs_body['id']))

        if FP_COACH_ENTITY['communicationsStatus'] == CommunicationsStatus.EMAIL_CAMPAIGN_ERROR[0]:
            FP_COACH_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", FP_COACH_ENTITY)
# ####################################################################### ACTIVE CODE - handler.py


        assert FP_COACH_ENTITY['communicationsStatus'] != CommunicationsStatus.EMAIL_CAMPAIGN_ERROR[0], f"TRIGGER COMMUNICATIONS - {description}"
        logger.info(f"TRIGGER COMMUNICATIONS: {FP_COACH_ENTITY['id']} - Complete\n\n")

        return



# PROCESS FP COACH
@pytest.mark.enrolments
@pytest.mark.fp_coach
def test_process_fp_coach_enrolment():

    logger.info('test_process_fp_coach_enrolment')
    Constants.IS_TEST = True
    logger.info(f"IS_TEST: {Constants.IS_TEST}")

    # FP Coach 2 per week 40 min Credit Card Bunker
    event = """{"FormID":"3844672","UniqueID":"704975099","91275771":"","92120786":"true","91886402":"2020-11-18 17:34:50","91275772":{"first":"Clint","last":"Test"},"91275773":"0413 506 306","91275774":"clint@thefitnessplayground.com.au","91440528":[{"value":"5","label":"The Bunker"}],"91275775":null,"91561372":null,"91561371":null,"91561370":[{"value":"100000849::152065","label":"Angela Aho"}],"91561700":"Face to Face","91709853":"40 Minutes","91561868":null,"91710754":null,"91384120":null,"91275778":null,"91709892":null,"91709890":null,"91709612":null,"91709898":[{"value":"260","label":"Bunker PT: 2x 40 Min Session Per Week"}],"91709896":null,"91566019":null,"91709615":null,"91566012":null,"91709847":null,"91935101":["Specials"],"91935138":["Two Free Sessions"],"91275785":"260.00","91275786":null,"91275784":"18 Nov 2020","91711054":"","91711055":"Credit Card","99797180":null,"92061924":"Yes","95667335":"","95667336":"","91880571":{"card":"5555555555554444","cardexp":"12/21","cvv":"123"},"91711061":null,"91711062":null,"91711063":null,"91275804":"","95667429":["I understand Coaching is debited fortnightly in advanced"],"91275805":["I have read, understand and agree to the Terms and Conditions"],"95667430":[],"95667431":["I commit to a minimum of six (6) paid Personal Training Sessions"],"91275807":"https://s3.amazonaws.com/files.formstack.com/uploads/3844672/91275807/704975099/signature_91275807.png","91275809":null,"91941002":null,"91941001":null,"91941000":[{"value":"6::152065","label":"Daniel Bowden"}],"96024590":"No","91275810":"Test Submission"}"""
    expected_build = {'fs_formId': '3844672', 'fs_uniqueId': '704975099', 'locationId': '5', 'gymName': 'The Bunker', 'status': 'SAVED', 'communicationsStatus': 'EMAIL_CAMPAIGN_PENDING', 'createDate': '2020-11-18 17:34:50', 'updateDate': '2020-11-18 17:34:50', 'coachingModality': 'Face to Face', 'sessionLength': '40 Minutes', 'trainingOptions': None, 'fnDD': '260.00', 'mtDD': None, 'externalClient': False, 'mboContractNames': 'Packs: FP Coach Pack # 0.0 | Bunker PT: 2x 40 Min Session Per Week # 260.0/fortnight', 'mboContractIds': '127,235', 'couponCode': 'FreePTPack', 'startDate': '2020-11-18', 'staffMember': '6::152065', 'staffName': 'Daniel Bowden', 'organisedByCoach': False, 'personalTrainer': '100000849::152065', 'personalTrainerName': 'Angela Aho', 'phone': '+61413506306', 'firstName': 'Clint', 'lastName': 'Test', 'email': 'clint@thefitnessplayground.com.au', 'paymentType': 'Credit Card', 'memberCreditCard': {'holder': 'Clint Test', 'number': '5555555555554444', 'expMonth': '12', 'expYear': '2021', 'verificationCode': '123'}, 'memberBankDetail': None, 'useExistingDetails': 'No', 'agreement': 'I have read, understand and agree to the Terms and Conditions | I commit to a minimum of six (6) paid Personal Training Sessions | I understand Coaching is debited fortnightly in advanced', 'signature': 'https://s3.amazonaws.com/files.formstack.com/uploads/3844672/91275807/704975099/signature_91275807.png', 'notes': 'Test Submission'}
    expected_save = {'id': 16, 'status': 'SAVED', 'updateDate': '2020-11-18 17:56:09', 'createDate': '2020-11-18 17:34:50', 'fs_formId': '3844672', 'fs_uniqueId': '704975099', 'firstName': 'Clint', 'lastName': 'Test', 'phone': '+61413506306', 'email': 'clint@thefitnessplayground.com.au', 'gymName': 'The Bunker', 'locationId': 5, 'personalTrainer': '100000849::152065', 'personalTrainerName': 'Angela Aho', 'coachingModality': 'Face to Face', 'trainingOptions': None, 'sessionLength': '40 Minutes', 'externalClient': False, 'externalPTSessionPrice': None, 'numberSessioinsPerWeek': None, 'mboContractNames': 'Packs: FP Coach Pack # 0.0 | Bunker PT: 2x 40 Min Session Per Week # 260.0/fortnight', 'mboContractIds': '127,235', 'couponCode': 'FreePTPack', 'fnDD': '260.00', 'mtDD': None, 'oneOffTotal': None, 'startDate': '2020-11-18', 'paymentType': 'Credit Card', 'agreement': 'I have read, understand and agree to the Terms and Conditions | I commit to a minimum of six (6) paid Personal Training Sessions | I understand Coaching is debited fortnightly in advanced', 'signature': 'https://s3.amazonaws.com/files.formstack.com/uploads/3844672/91275807/704975099/signature_91275807.png', 'staffMember': '6::152065', 'staffName': 'Daniel Bowden', 'notes': 'Test Submission', 'activeCampaignId': None, 'communicationsStatus': 'EMAIL_CAMPAIGN_PENDING', 'useExistingDetails': 'No', 'memberCreditCard': {'id': 230, 'number': 'Es9snGC2NedlIfmglLCBjIE/CY++xCbgWxoh923FeSopDgWFfW82HkqRnhPhVATq9hFJ3NmT0AmxFhw9TC8+g9nno6fHnQIh061e9XCHMeeLUGLhq/8/5RS+wsY2hf0jhj6IRnHGOuBFo6GOqeHdl5IsOEqUQnchVackGm7HYj5B1hw/90eCxIShhVTzLMoZBfPs44m6KZC4xNwduKUGe7pr18lMo6qw8cE8XK5oOk6V6ZBglTDfsT/I1jOlxNRAwuBHgpjjaxr3U79JYPW+vgvyQ13IjArKh5Q/I40qX091xbGB1Fwm16r+C3E2ZEhjoAkPrPFXG+EQGumwLeUJ/EiWV/HdI24Obdi55/QUyTxImuMi2jTBl85biyHL6A5RnmrILAutWP9ztljRb6pjdDl+fYDjnq3ZC533WsUqfNSEBUZ//s3f0+2G9yfwo5otnwRjGONMz8Lax5S7IjanAZ5z72h6ma9YrNsCkhaqcxCirkFocU0ikgXaxfEAYv9kWq86AcpyM6TwIbVVWSjUcBd2CmQ3CKdFDhfZAjdOkV3ZMLHvASb6RTmUr2PXNj6fgcNm+uwJFdUQUgCdzXT3vIRktUR3Ndw6RMIQ1wKyTf841y8CGCdg7iOv199Rtyb6yZoVuIJyyXXz6ON3resVmZ/YvdX+LNvwEY+kwltYHXM=', 'holder': 'Clint Test', 'city': None, 'address': None, 'state': None, 'postcode': None, 'expMonth': '12', 'expYear': '2021', 'type': None, 'verificationCode': 'kxt5RjO1b+Yg++zA+nTwLlbYSmKSY8CW79/4XyWK4cuZEStgXm7ZNmvXL0bxO4BitNX4G4wn93fICKowy+EYB+eojKrSnczbT7UTCOVS6zb8oJp/FDIHZ791rQQ4C2Yhc9Vbi2cP1hxzYzYQy7MbGvsCaLmg+3u6FL0AgVKGeZCiIKWBAb18N/1IDnMXoTS6Wvhn8xdB8qI0WSt1iggkmnapfe7OWpJxE7xmdruwkV9THDasm0lmos1YwRH/tHyYXSXcKwSqB1gDyetP+jNJE/e8fv1AncLQBO2i2lWqmaHdpTZHGXqgSHFg5doWhU9x73DLuK0zaUtdSXjyq4vnViTlN2a6JAvABlq5rCR25lZuBQVBSno7xjjGx1uHNQ287VxTZEqmCledUkoFEgBG4VEF6e29asbllbLGiqGnoBxROZzfdcAoyN8sry0oCV9rwPeuR0sus79mzHZVIGHlvpKgXOOywuSaIzNo8bqBCkhzSsXFw4bfxSh1/29ZLCAK0wUtWKG8jEaEBzKqGcut00BBYRpX4Lyk/X7DUQDLz5j/m/MbwaSqsu61qZl5rHCtnKmmSZdRx8N7LkrIi3NbRZOsT9NPjYPZDAUoSUIwqBl/DAcbmGj1ZH8mD2+ywogYFv5UEcQxZxhgtlxGEMLMeDTBrO3Yvfi9C+41p+MDS+8='}, 'memberBankDetail': None, 'uid': None, 'organisedByCoach': False}
    execute_test_fp_coach_enrolment(expected_build, expected_save, event, 'FP Coach 2 per week 40 min Credit Card Bunker')


@pytest.mark.referrals
def test_referrals():

    logger.info("test_referrals")

    event = """{"FormID":"3931477","UniqueID":"736662399","HandshakeKey":"SHARED SECRET","94332581":"true","94332582":"2021-01-07 14:31:58","94332583":"","94332584":{"first":"Clint","last":"Test"},"94332585":{"address":"120A Devonshire Street","city":"Surry Hills","state":"NSW","zip":"2010"},"94332586":"0413 506 306","94332587":"clint@thefitnessplayground.com.au","94332588":"05 Jan 1989","94333527":"32","94332589":"male","94332590":"Em","94332591":"0487 654 321","94332592":"instagram","94332593":null,"94332594":null,"94335502":"","94335503":"Yes","94335504":null,"94335505":null,"94335506":"Credit Card","94335507":{"card":"4111111111111111","cardexp":"12/21","cvv":"123"},"94335508":null,"94335509":null,"94335510":null,"95430162":["I authorise payments to be debited from the above payment details"],"95430172":"https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/736662399/signature_95430172.png","103863738":["Please give the iPad to Team Member"],"95431633":[{"value":"1","label":"Surry Hills"}],"94351605":[{"value":"6::152065","label":"Daniel Bowden"}],"94351604":null,"94351603":null,"94351606":null,"94351558":"","94351559":[{"value":"1","label":"Surry Hills"}],"94351560":null,"101490498":null,"94351561":[{"value":"51.40","label":"Gym: No Commitment (Surry Hills)"}],"94351562":null,"94351563":"I'm a Fitness Guru","94351564":null,"94351565":null,"94351566":null,"94351568":null,"94351569":null,"94351570":null,"94351571":null,"94351572":null,"94351573":null,"94351574":null,"94351575":null,"94351576":null,"94351577":null,"94351578":null,"94351579":null,"95431213":null,"95431216":null,"94351580":null,"94351581":null,"94351582":null,"94351583":["Promotions"],"94351584":null,"94351585":null,"102848235":null,"102848237":null,"95431031":"0","94351586":[{"value":"7","label":"7 Days Free"}],"94351587":[{"value":"-10","label":"$89 Access Key"}],"94351588":"7","94351589":"99","94351599":null,"94351600":null,"94351601":[{"value":"-1000","label":"No Complimentary Session"}],"94351602":null,"96024203":null,"98479769":null,"98479850":null,"98479838":null,"98479796":null,"98482524":null,"103867643":"","94351607":"","94351590":"89.00","103921853":"Add to First Payment","94351591":"89.00","94351592":null,"94351593":null,"94351594":"51.40","94351595":"51.40","94351596":null,"94351597":"07 Jan 2021","94351598":"14 Jan 2021","102852720":"","95431448":"","95431449":"","94332640":"","94332641":"Rehabilitation","94332642":["Family History of Chronic Illness"],"94332643":"Yes","94335049":["Any Day"],"94335090":["Anytime"],"94332644":null,"94333018":"","94333274":"","94333311":["I have read, understood and agree to the Terms & Conditions"],"94333357":null,"94352342":null,"94333358":null,"94333524":null,"94333525":"https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/736662399/signature_94333525.png","94332645":["Yes"],"94332646":"Referral One","94332648":"0487 654 321","94332647":"one@referral.co","103920885":["Yes"],"103921451":"Referral Two","103921464":"0412 345 678","103921454":"two@referral.co","103921520":["Yes"],"103921554":"Referral Three","103921583":"0487 654 567","103921576":"three@referral.co","103257339":"https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/736662399/103257339_tickicon.png","94333803":"98789","94351608":"Test Submission"}"""
    
    body = json.loads(event)
    _ids = FS_FIELD_IDS.FORM[body["FormID"]]

    ENROLMENT_DATA_ENTITY = build_enrolment_data_entity(body)
    logger.info(f"BUILD ENROLMENT:\n{ENROLMENT_DATA_ENTITY}\n")

    TEST_CONSTANTS.RANDOM_EMAIL = generate_random_email()
    TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID = str(generate_random_integer(9))
    TEST_CONSTANTS.RANDOM_ACCESS_KEY = f"ZZ-{generate_random_integer(7)}"
    ENROLMENT_DATA_ENTITY['email'] = TEST_CONSTANTS.RANDOM_EMAIL
    ENROLMENT_DATA_ENTITY['fs_uniqueId'] = TEST_CONSTANTS.RANDOM_FS_UNIQUE_ID

    ENROLMENT_DATA_ENTITY = source_post_request(Constants.SOURCE_ENROLEMNT_DATA_URL, ENROLMENT_DATA_ENTITY)
    logger.info(f"SAVE ENROLMENT:\n{ENROLMENT_DATA_ENTITY}\n")

    save_referrals(body, ENROLMENT_DATA_ENTITY)

    handle_referrals(ENROLMENT_DATA_ENTITY)




@pytest.mark.builder
def test_builder():

    Constants.IS_TEST = True

    logger.info("test_builder")
    
    # In-club
    # event = """{"94332581": "true","94332582": "2021-10-10 10:11:22","94332583": "","94332584": {"first": "Clint","last": "Test"},"94332585": {"address": "1 Street Rd","city": "Sydney","state": "NSW","zip": "2000"},"94332586": "0413 506 306","94332587": "clint@centheos.com","94332588": "25 Oct 1967","94332589": "male","94332590": "Em","94332591": "0487 654 321","94332592": "","94332593": null,"94332594": null,"94332641": null,"94332642": null,"94332643": null,"94332644": null,"94332645": null,"94332646": null,"94332647": null,"94332648": null,"94333018": "","94333274": "","94333311": ["I have read, understood and agree to the Terms & Conditions"],"94333357": null,"94333358": ["I acknowledge this is a minimum term of 26 fortnightly payments"],"94333524": null,"94333525": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/signature_94333525.png","94333527": "53","94333803": "99999","94335049": ["Any Day"],"94335090": ["Before Work"],"94335502": "","94335503": "Yes","94335504": null,"94335505": null,"94335506": "Credit Card","94335507": {"card": "4111111111111111","cardexp": "12/22","cvv": "231"},"94335508": null,"94335509": null,"94335510": null,"94351558": "","94351559": [{"value": "1","label": "Surry Hills"}],"94351560": null,"94351561": [{"value": "41.40","label": "Gym: Membership ($19.95)"}],"94351562": null,"94351563": "I'm a Fitness Guru","94351564": null,"94351565": null,"94351566": null,"94351568": null,"94351569": null,"94351570": null,"94351571": null,"94351572": null,"94351573": null,"94351574": ["0.00"],"94351575": ["0.00"],"94351576": ["0.00"],"94351577": ["0.00"],"94351578": ["0.00"],"94351579": null,"94351580": null,"94351581": null,"94351582": null,"94351583": ["Promotions"],"94351584": null,"94351585": null,"94351586": [{"value": "21","label": "21 Days Free"}],"94351587": [{"value": "-50","label": "$49 Access Key"}],"94351588": "21","94351589": "99","94351590": "49.00","94351591": "49.00","94351592": null,"94351593": null,"94351594": "41.40","94351595": "41.40","94351596": null,"94351597": "10 Oct 2021","94351598": "31 Oct 2021","94351599": null,"94351600": null,"94351601": [{"value": "100000111::152065","label": "Clint Sellen"}],"94351602": null,"94351603": null,"94351604": null,"94351605": [{"value": "6::152065","label": "Daniel Bowden"}],"94351606": null,"94351607": "","94351608": "Test Submission","94352342": null,"95430162": ["I authorise payments to be debited from the above payment details"],"95430172": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/signature_95430172.png","95431031": "0","95431213": null,"95431216": null,"95431448": "","95431449": "","95431633": [{"value": "1","label": "Surry Hills"}],"96024203": null,"98479769": null,"98479796": null,"98479838": null,"98479850": null,"98482524": null,"101490498": null,"102848235": null,"102848237": null,"102852720": "","103257339": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/103257339_tickicon.png","103863738": ["Please give the iPad to Team Member"],"103867643": "","103920885": null,"103921451": null,"103921454": null,"103921464": null,"103921520": null,"103921554": null,"103921576": null,"103921583": null,"103921853": "Add to First Payment","103982989": "","103983056": "7","103983103": [{"value": "21","label": "21 Day Satisfaction Period"}],"103988711": null,"104837612": null,"111057961": null,"111057964": null,"111057966": null,"111058145": null,"111058146": null,"111058148": null,"111058422": null,"111058423": null,"111058424": null,"111058477": "","111778860": "21","111778876": "31 Oct 2021","116315320": "Yes","116315321": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871580834/116315321_tickicon.png","FormID": "3931477","UniqueID": "871580834","HandshakeKey": "SHARED SECRET"}"""

    # PIF
    event = """{"96047912": "","96047915": {"first": "Clint","last": "Test"},"96047916": "clint@centheos.com","96047917": "0413 506 306","96047940": "99999","96047950": "","96047951": null,"96047952": null,"96047953": null,"96047954": "Use Existing Details","96047955": null,"96047956": null,"96047957": null,"96047958": null,"96048514": [{"value": "3","label": "Marrickville"}],"96048522": null,"96048728": ["Membership"],"96048980": null,"96048995": null,"96049023": null,"96049074": "99.00","96049084": null,"96049086": "99.00","96049156": null,"96049161": null,"96049163": null,"96049335": "Gym","96049550": null,"96049988": null,"96050145": null,"96050518": "10 Oct 2021","96050523": null,"96050529": null,"96051198": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871618984/signature_96051198.png","96051207": ["I authorise payments to be debited from the above payment details"],"96051256": null,"96051258": null,"96051259": null,"96051260": null,"96051261": null,"96051442": "99","96051500": null,"96051501": null,"96051883": null,"96051963": null,"96051971": null,"96051972": null,"96051983": "10 Oct 2021","96052002": null,"96052105": "0","96052158": "Test Submission","96052159": [{"value": "3","label": "Marrickville"}],"96052163": null,"96052164": null,"96052165": [{"value": "100000957::152065","label": "Dilawar Khan"}],"96052166": null,"96052758": "2021-10-10 14:45:44","96052760": "true","96053701": null,"96054641": null,"96054688": null,"96054716": null,"96055320": null,"99099982": "","99099985": "","99100086": "","99100956": "99.00","99100989": null,"99101008": null,"99101020": null,"99101254": null,"99101872": null,"99102124": null,"99102125": null,"99102128": null,"99102151": null,"99433277": null,"99436986": "Credit Card","101576172": [{"value": "99.00","label": "Gym: 1-Month Upfront ($99.00)"}],"101576175": null,"103260146": "","103929325": "","103929681": ["Please give the iPad to Team Member"],"103929805": "","103980866": null,"103981347": null,"103981769": "","103981875": null,"103985398": null,"103985421": null,"103985422": null,"103985423": null,"103985518": null,"103985524": null,"103985527": null,"103985530": null,"103985531": null,"103985548": null,"103985556": null,"103985559": null,"104212385": ["I have read, understood and agree to the Terms & Conditions"],"104212395": "https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/871618984/signature_104212395.png","104212604": null,"104212635": null,"104212743": null,"104216341": null,"104407256": ["0.00"],"105333764": "Add to First Payment","105333768": null,"105333783": null,"105333795": null,"111842887": null,"111842888": null,"111842889": null,"111843405": null,"111843406": null,"111843407": null,"111843440": null,"111959440": "","116316745": "No","116316748": null,"116316751": ["I acknowledge access to the gym will be restricted until proof of  COVID Vaccination or Medical Contraindication is provided"],"FormID": "3980725","UniqueID": "871618984","HandshakeKey": "SHARED SECRET"}"""

    # Part 1 FP
    # event = """{"FormID":"3929752","UniqueID":"828987623","HandshakeKey":"SHARED SECRET","94276624":"true","94276625":"2021-06-28 15:20:05","111958479":"","94276626":"","94403487":[{"value":"3","label":"Marrickville"}],"94276679":[{"value":"6::152065","label":"Daniel Bowden"}],"94276680":null,"94276681":null,"94276682":null,"94276627":{"first":"Clint","last":"Sellen"},"94276628":{"address":"120A Devonshire Street","city":"Surry Hills","state":"NSW","zip":"2010"},"94276629":"0413 506 306","94276630":"clint@thefitnessplayground.com.au","94276631":"28 Jun 2004","94469371":"17","94276633":"Clint Sellen","94276634":"0413 506 306","94276635":"","94276636":"","94276637":"","94276638":"","94276639":[{"value":"3","label":"Marrickville"}],"94276640":null,"101491364":[{"value":"59.40","label":"Play: Membership ($28.95)"}],"94276641":null,"94276642":null,"94276643":"Coaching","94276644":"Face to Face","94327899":null,"94276645":"40 Minutes","94276649":null,"94276650":[{"value":"354.00","label":"PT: 3x 40 Min Sessions Per Week"}],"94276651":null,"94276652":null,"94276653":null,"94276654":null,"94276655":["0.00"],"94276656":["0.00"],"94276657":["0.00"],"94276658":["0.00"],"94276659":["0.00"],"94276660":null,"94403802":null,"94403885":null,"94350160":["Add Creche"],"94349722":[{"value":"10.00","label":"Creche: Unlimited x 1 Child"}],"94280674":[{"value":"19.95","label":"Add Virtual Playground for $19.95 a month"}],"105330030":"Start on my First Billing Day","94276665":["Promotions"],"94276667":["Two Free PT"],"94276668":null,"105330104":null,"111954875":null,"111954881":[{"value":"21","label":"21 Day Satisfaction Period"}],"111954879":null,"102849320":null,"102849318":null,"95000322":"0","94276669":null,"111954811":null,"111954821":[{"value":"21","label":"21 Days Free"}],"111954822":null,"94276670":null,"111954856":null,"111954857":[{"value":"-69","label":"$30 Access Key"}],"111954861":null,"94330525":"0","94276672":"99","105330044":"Add to First Payment","105330050":null,"94276673":"30.00","94276674":"30.00","94350283":"10.00","94349002":"354.00","94350367":"59.40","94276675":"423.40","94331302":"19.95","94276677":"28 Jun 2021","94276678":"28 Jun 2021","102853638":"","105332216":"7","111955650":"21","105332191":"","111955654":"","94468404":"28 Jun 2021","94468412":"12 Jul 2021","94276661":[{"value":"100000128::152065","label":"Tom Merriman"}],"94276662":null,"94276663":null,"94276664":null,"96024208":"Other","98537268":[{"value":"5","label":"The Bunker"}],"98537284":null,"98537279":null,"98537276":null,"98537286":[{"value":"100000580::152065","label":"Ben Shepherd"}],"94276683":"","94276693":"Test Submission","94276689":null,"94276690":null,"94276692":null,"94276691":null,"104075740":null,"104075750":null,"104075752":null,"104075753":null,"104075755":null,"104075759":null,"104075761":null,"104075762":null}"""
    # # Part 2 FP
    # event = """{"FormID":"3929753","UniqueID":"829691687","HandshakeKey":"SHARED_SECRET","94276695":"true","94276694":"828987623","94468037":"3929752","94468156":"6::152065","94277165":"3","94468157":"100000128::152065","94276713":"","94276696":"true","94468050":"true","94468932":"true","94468931":"true","94468933":"true","105330479":"false","94468133":"false","96024211":"No","94545782":"true","94469054":"true","94469066":"false","94469067":"true","94276697":"2021-06-29 15:25:58","94276698":"","94276699":{"first":"Clint","last":"Sellen"},"94276700":{"address":"120a Devonshire Street","city":"Surry Hills","state":"NSW","zip":"2010"},"94276701":"0413 506 306","94276702":"clint@thefitnessplayground.com.au","94276703":"28 Jun 2004","94469312":"17","94276704":"male","94276705":"Clint Sellen","94276706":"0413 506 306","94276707":"","94276708":"Marrickville","94276709":"Play: Membership ($28.95)","94276710":"PT: 3x 40 Min Sessions Per Week","94469431":"0.00","94469001":"Two Free PT","94276711":"30.00","94468995":"$30 Access Key","94276714":"30.00","94468209":"10.00","94468213":"354.00","94468219":"59.40","94276715":"423.40","94276712":"19.95","94276717":"28 Jun 2021","94276718":"28 Jun 2021","105330724":"","105330721":"","94468999":"First 21 Days","94468460":"28 Jun 2021","94468462":"12 Jul 2021","94276719":"Tom Merriman","94276720":"Daniel Bowden","94276721":"","94276722":"Test Submission","94276723":"","94277713":"Yes","94277828":null,"94277830":null,"94276724":"Credit Card","94276725":{"card":"4111111111111111","cardexp":"12/21","cvv":"123"},"94276729":null,"94276730":null,"94276731":null,"94469216":["I authorise payments to be debited from the above payment details"],"94469234":"https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94469234.png","94468291":"","94468292":"Build Muscle","94468293":null,"94468294":["Any Day"],"94468295":["Anytime"],"94468296":null,"94468297":null,"94468298":null,"94468299":null,"94468300":null,"94468301":null,"94468260":"","94468261":"","94468262":["I have read, understood and agree to the Terms & Conditions"],"94468263":["I commit to a minimum of six (6) paid Personal Training Sessions"],"94468264":null,"94468265":["I acknowledge this is a minimum term of 26 fortnightly payments"],"94468266":"https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94468266.png","94468267":"https://fp-formstack-signatures.s3.ap-southeast-2.amazonaws.com/829691687/signature_94468267.png","103259922":"","94546176":""}"""

    body = json.loads(event)
    _ids = FS_FIELD_IDS.FORM[body["FormID"]]

# ______________________

    # # In Club Enrolment & Part 1 Enrolment
    # ENROLMENT_DATA_ENTITY = build_enrolment_data_entity(body)

    # PIF Enrolment
    ENROLMENT_DATA_ENTITY = build_pif_enrolment_data(body, MemberStatus.PROCESSING[0], CommunicationsStatus.EMAIL_CAMPAIGN_PENDING[0])

    logger.info(f"\n\nBUILD ENROLMENT:\n\n{ENROLMENT_DATA_ENTITY}")
    ENROLMENT_DATA_ENTITY = source_post_request(Constants.SOURCE_ENROLEMNT_DATA_URL, ENROLMENT_DATA_ENTITY)
    logger.info(f"\n\nSAVE ENROLMENT:\n\n{ENROLMENT_DATA_ENTITY}")

# # ______________________

    # # Part 2 Enrolment
    # URL = Constants.SOURCE_GET_PHONE_ENROLMENT_URL + '/' + body[_ids['FS_INTERNAL_SUBMISSION_FORM_ID']] + '/' + body[_ids['FS_INTERNAL_SUBMISSION_UNIQUE_ID']]
    # ENROLMENT_DATA_ENTITY = source_get_request(URL)
    # ENROLMENT_DATA_ENTITY = update_enrolment_data_entity_from_external_submission(body, ENROLMENT_DATA_ENTITY)
    # logger.info(f"\n\nBUILD ENROLMENT:\n\n{ENROLMENT_DATA_ENTITY}")
    # URL = Constants.SOURCE_ENROLEMNT_DATA_URL +'/false'
    # ENROLMENT_DATA_ENTITY = source_put_request(URL, ENROLMENT_DATA_ENTITY)
    # logger.info(f"\n\nSAVE ENROLMENT:\n\n{ENROLMENT_DATA_ENTITY}")

    




