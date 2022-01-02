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
import os
import boto3
import logging as logger

from helpers import *
from constants import *
from pif import *
from enrolment import *
from coach import *
from processing import *
from emailer import *
from mbo_api import *
from source_api import *


"""
Simple example for Lambda->SQS->Lambda.
RESCOURCE: # https://medium.com/hackernoon/how-to-setup-aws-lambda-with-sqs-everything-you-should-know-12263d8aa91e
"""
SQS_CLIENT = boto3.client('sqs')

class MBOException(Exception):
    pass


# ############################################################
# HELPER                                                     #
# ############################################################
def push_onto_queue(QUEUE_URL, BODY, MESSAGE_GROUP_ID, MESSAGE_DEDUPLICATION_ID):
    FLAG = True
    try:
        print('[push_onto_queue]')
        print(f"\tQUEUE_URL: {QUEUE_URL}\n\tMESSAGE_GROUP_ID: {MESSAGE_GROUP_ID}\n\tMESSAGE_DEDUPLICATION_ID: {MESSAGE_DEDUPLICATION_ID}")
        logger.info(f"QUEUE_URL {QUEUE_URL}")

        if Constants.IS_TEST:
            logger.info(f"Queue Body:\n{json.dumps(BODY)}")

        RESPONSE = SQS_CLIENT.send_message(
            QueueUrl=QUEUE_URL,
            MessageBody=json.dumps(BODY),
            MessageGroupId=MESSAGE_GROUP_ID,
            MessageDeduplicationId=MESSAGE_DEDUPLICATION_ID,
        )
        logger.info(f" Response: {RESPONSE}")
        print(f" Response: {RESPONSE}")

    except Exception as ex:
        logger.error(f"Error - [push_onto_queue]: {ex}")
        print(f"Error - [push_onto_queue]: {ex}")
        FLAG = False
        # raise e
    finally:
        return FLAG

# ############################################################
# MAIN                                                       #
# ############################################################
def receive_enrolment(event, context):

    try:
        print('receive_enrolment')
        logger.info(f"Enrolment SQS URL {os.getenv('EnrolmentSqsUrl')}")
        print(f"Enrolment SQS URL {os.getenv('EnrolmentSqsUrl')}")

        body = json.loads(event['body'])
        _ids = FS_FIELD_IDS.FORM[body["FormID"]]
        FLAG = True

        if body['FormID'] == Constants.FS_PHONE_ENROLMENT_INTERNAL_FORM_ID or body['FormID'] == Constants.FS_IN_CLUB_ENROLMENT_FORM_ID:
            ENROLMENT_DATA_ENTITY = build_enrolment_data_entity(body)
            ENROLMENT_DATA_ENTITY = source_post_request(Constants.SOURCE_ENROLEMNT_DATA_URL, ENROLMENT_DATA_ENTITY)

            if ENROLMENT_DATA_ENTITY is not None and  body["FormID"] == Constants.FS_PHONE_ENROLMENT_INTERNAL_FORM_ID:
                print("Part 1 Enrolment: Handle Comms")
                flag = handle_enrolment_communications(body, ENROLMENT_DATA_ENTITY)

                return {
                    "statusCode": 200,
                    "body": json.dumps({"message": "Part 1 Enrolment"})
                }

            # ////////////////
            # ADD TO SQS
            # ////////////////
            BODY = {'type' : 'enrolmentData', 'id' : ENROLMENT_DATA_ENTITY['id']}
            FLAG = push_onto_queue(os.getenv('EnrolmentSqsUrl'), BODY, f"{BODY['type']}:{BODY['id']}", f"{BODY['type']}:{BODY['id']}")       

        if body['FormID'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_BK_FORM_ID or body['FormID'] == Constants.FS_PHONE_ENROLMENT_EXTERNAL_FP_FORM_ID:
            print('Enrolment Part 2: Update and Process')

            URL = Constants.SOURCE_GET_PHONE_ENROLMENT_URL + '/' + body[_ids['FS_INTERNAL_SUBMISSION_FORM_ID']] + '/' + body[_ids['FS_INTERNAL_SUBMISSION_UNIQUE_ID']]

            ENROLMENT_DATA_ENTITY = source_get_request(URL)
            if ENROLMENT_DATA_ENTITY is not None:
                ENROLMENT_DATA_ENTITY = update_enrolment_data_entity_from_external_submission(body, ENROLMENT_DATA_ENTITY)
                URL = Constants.SOURCE_ENROLEMNT_DATA_URL +'/false'
                ENROLMENT_DATA_ENTITY = source_put_request(URL, ENROLMENT_DATA_ENTITY)

                # ////////////////
                # ADD TO SQS
                # ////////////////
                BODY = {'type' : 'enrolmentData', 'id' : ENROLMENT_DATA_ENTITY['id']}
                FLAG = push_onto_queue(os.getenv('EnrolmentSqsUrl'), BODY, f"{BODY['type']}:{BODY['id']}", f"{BODY['type']}:{BODY['id']}")

                # Send notification to club
                handle_enrolment_notification(ENROLMENT_DATA_ENTITY) 

            else:
        #         Handle Part 1 Not Found Error
                logger.error("Part 1 Enrolment Not Found")


        if body['FormID'] == Constants.FS_PIF_ENROLMENT_FORM_ID:
            ENROLMENT_DATA_ENTITY = build_pif_enrolment_data(body, MemberStatus.PROCESSING[0], CommunicationsStatus.EMAIL_CAMPAIGN_PENDING[0])
            ENROLMENT_DATA_ENTITY = source_post_request(Constants.SOURCE_ENROLEMNT_DATA_URL, ENROLMENT_DATA_ENTITY)

            # ////////////////
            # ADD TO SQS
            # ////////////////
            BODY = {'type' : 'enrolmentData', 'id' : ENROLMENT_DATA_ENTITY['id']}
            FLAG = push_onto_queue(os.getenv('EnrolmentSqsUrl'), BODY, f"{BODY['type']}:{BODY['id']}", f"{BODY['type']}:{BODY['id']}")       

        if ENROLMENT_DATA_ENTITY['hasReferral']:
            save_referrals(body, ENROLMENT_DATA_ENTITY)

        if FLAG:
            return {
                "statusCode": 200,
                "body": json.dumps({"message": 'Receive Enrolment Complete'})
            }
        else:
            send_error_notification(f"[receive_enrolment] Failed to add ENROLMENT_DATA_ENTITY [{ENROLMENT_DATA_ENTITY['id']}] to the Queue")
            return {
                "statusCode": 418,
                "body": json.dumps({"message": f"Failed to add to SQS: {event['body']}" })
            }

    except Exception as ex:
        print(f'[receive_enrolment] Critical Error: {ex}')
        send_error_notification(f'[receive_enrolment] Critical Error: {ex}')
        return {
            "statusCode": 501,
            "body": json.dumps({"message": f'[receive_enrolment] Critical Error: {ex}'})
        }




def process_enrolment(event, context):

    try:
        print(f'process_enrolment: {event}')
        logger.info(f'process_enrolment: {event}')

        sqs_body = json.loads(event['Records'][0]['body'])

        print(f"SQS Body: {sqs_body}")
        logger.info(f"SQS Body: {sqs_body}")

        IS_MBO_SUCCESS = True
        # dollar_value = 0
        # ######################################################################
        # GET ENROLMENT                                                        #
        # ######################################################################
        print('GET ENROLMENT')
        logger.info('GET ENROLMENT')
        URL = 'source/'+ sqs_body['type'] +'/'+ str(sqs_body['id']) +'/false'
        ENROLMENT_DATA_ENTITY = source_get_request(URL)

        if ENROLMENT_DATA_ENTITY is None:
            logger.error("Error - Enrolment Doesn't Exist in Source")
            print("Error - Enrolment Doesn't Exist in Source")
            handle_manual_submission(MemberStatus.CLIENT_NOT_FOUND, ENROLMENT_DATA_ENTITY, None)
            ENROLMENT_DATA_ENTITY['status'] = MemberStatus.CLIENT_NOT_FOUND[0]
            IS_MBO_SUCCESS = False
    

        # ######################################################################
        # CHECK DUPLICATE KEY                                                  #
        # ######################################################################
        print('CHECK DUPLICATE KEY')
        logger.info('CHECK DUPLICATE KEY')
        is_duplicate_key = check_duplicate_key(ENROLMENT_DATA_ENTITY)

        # ######################################################################
        # CHECK EXISTING CLIENT                                                #
        # ######################################################################
        print('CHECK EXISTING CLIENT')
        logger.info('CHECK EXISTING CLIENT')
        PROCESS_ENTITY = check_existing_client(ENROLMENT_DATA_ENTITY)

        EXISTING_MBO_CLIENT = PROCESS_ENTITY['EXISTING_MBO_CLIENT']
        EXISTING_CREDIT_CARD = PROCESS_ENTITY['EXISTING_CREDIT_CARD']
        EXISTING_BANK_DETIALS = PROCESS_ENTITY['EXISTING_BANK_DETIALS']
        ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']

        if IS_MBO_SUCCESS:

            # ######################################################################
            # ADD OR UPDATE CLIENT                                                 #
            # ######################################################################
            print('ADD OR UPDATE CLIENT')
            logger.info('ADD OR UPDATE CLIENT')
            PROCESS_ENTITY = add_or_update_client(ENROLMENT_DATA_ENTITY, is_duplicate_key, EXISTING_MBO_CLIENT)
            ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']
            MBO_CLIENT = PROCESS_ENTITY['MBO_CLIENT']
            IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']

            if Constants.IS_TEST:
                logger.info(f"Overriding MBO_CLIENT_ID to TEST ID: {Constants.TEST_ACCESS_KEY_NUMBER}")
                print(f"Overriding MBO_CLIENT_ID to TEST ID: {Constants.TEST_ACCESS_KEY_NUMBER}")
                MBO_CLIENT['Id'] = Constants.TEST_ACCESS_KEY_NUMBER

            # ######################################################################
            # ADD BANK DETAILS                                                     #
            # ######################################################################
            if IS_MBO_SUCCESS and ENROLMENT_DATA_ENTITY['memberBankDetail'] is not None and MBO_CLIENT is not None:
                logger.info('\tAdd Bank Details')
                print('\tAdd Bank Details')

                PROCESS_ENTITY = add_bank_details(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

                IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
                ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']

            if IS_MBO_SUCCESS:

                # Get Client from MBO - this is to ensure the entities are in sync
                response = mbo_get_request(Constants.MBO_GET_CLIENTS_URL, {'ClientIds' : MBO_CLIENT['Id']})

                if response['PaginationResponse']['TotalResults'] == 1:
                    MBO_CLIENT = response['Clients'][0]
                else:
                    ENROLMENT_DATA_ENTITY['status'] = MemberStatus.PENDING[0]
                    ENROLMENT_DATA_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", ENROLMENT_DATA_ENTITY)
                    handle_manual_submission(MemberStatus.PENDING, ENROLMENT_DATA_ENTITY, None)
                    raise MBOException("Client not found in Mindbody")

                logger.info(f"MBO_CLIENT: {MBO_CLIENT}")
                print(f"MBO_CLIENT: {MBO_CLIENT}")

                if Constants.IS_TEST:
                    logger.info(f"Overriding MBO_CLIENT_ID to TEST ID: {Constants.TEST_ACCESS_KEY_NUMBER}")
                    print(f"Overriding MBO_CLIENT_ID to TEST ID: {Constants.TEST_ACCESS_KEY_NUMBER}")
                    MBO_CLIENT['Id'] = Constants.TEST_ACCESS_KEY_NUMBER

                # ######################################################################
                # PURCHASE CONTRACTS                                                   #
                # ######################################################################
                print('PURCHASE CONTRACTS')
                logger.info('PURCHASE CONTRACTS')
                PROCESS_ENTITY = purchase_contracts(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

                IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
                ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']

                # ######################################################################
                # PURCHASE SERVICES                                                    #
                # ######################################################################
                print('PURCHASE SERVICES')
                logger.info('PURCHASE SERVICES')
                PROCESS_ENTITY = purchase_services(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

                if IS_MBO_SUCCESS == True:
                    IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']

                ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']
                is_valid_checkout = PROCESS_ENTITY['is_valid_checkout']

                # ######################################################################
                # PURCHASE ACCESS KEY PAY TODAY                                        #
                # ######################################################################
                if ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'] is not None and ENROLMENT_DATA_ENTITY['accessKeyPaymentOptions'].lower().replace(' ','') == 'paytoday':
                    PROCESS_ENTITY = purchase_access_key_pay_today(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

                    if IS_MBO_SUCCESS == True:
                        IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']

                    ENROLMENT_DATA_ENTITY = PROCESS_ENTITY['ENROLMENT_DATA_ENTITY']

                # ######################################################################
                # HANDLE REFERRALS -> Push to GymSales                                 #
                # ######################################################################
                if ENROLMENT_DATA_ENTITY['hasReferral'] is not None and ENROLMENT_DATA_ENTITY['hasReferral'] == True:
                    print('HANDLE REFERRALS')
                    logger.info('HANDLE REFERRALS')
                    handle_referrals(ENROLMENT_DATA_ENTITY)

                # ######################################################################
                # UPDATE GYMSALES.                                                     #
                # ######################################################################
                # print('UPDATE GYMSALES')
                # logger.info('UPDATE GYMSALES')
                # ENROLMENT_DATA_ENTITY = update_gymsales(ENROLMENT_DATA_ENTITY)

                # #######################################################################################################
                # SET STATUS AND UPDATE DATABASE - A SUCCESS status will trigger payment details to be washed.          #
                # #######################################################################################################
                print('SET STATUS AND UPDATE DATABASE')
                logger.info('SET STATUS AND UPDATE DATABASE')
                ENROLMENT_DATA_ENTITY['fs_formUrl'] = None
            
                if IS_MBO_SUCCESS:
                    ENROLMENT_DATA_ENTITY['status'] = MemberStatus.SUCCESS[0]
                    
                ENROLMENT_DATA_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", ENROLMENT_DATA_ENTITY)

                # ######################################################################
                # HANDLE PT TRACKER                                                    #
                # ######################################################################
                print('HANDLE PT TRACKER ')
                PT_TRACKER_ENTITY = None
                if ENROLMENT_DATA_ENTITY['trainingStarterPack'] is not None and ENROLMENT_DATA_ENTITY['trainingStarterPack'].replace(' ','').lower() in ['face-to-face', 'coaching', 'starterpack','externalpersonaltraining']:
                    logger.info('HANDLE PT TRACKER ')
                    PT_TRACKER_ENTITY = handlePtTracker(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

                # ######################################################################
                # TRIGGER PDF WRITER - Push onto SQS Queue                             #
                # ######################################################################
                print('TRIGGER PDF WRITER')
                logger.info('TRIGGER PDF WRITER')
                ENROLMENT_DATA_ENTITY = trigger_pdf_writer(ENROLMENT_DATA_ENTITY, MBO_CLIENT, PT_TRACKER_ENTITY, "enrolmentData")

                # ######################################################################
                # UPLOAD MEMBER PHOTO                                                  #
                # ######################################################################
                print('UPLOAD MEMBER PHOTO')
                logger.info('UPLOAD MEMBER PHOTO')
                upload_member_photo(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

                # ######################################################################
                # CONTACT LOGS FOR COMFORT CANCEL                                      #
                # ######################################################################
                print('CONTACT LOGS FOR COMFORT CANCEL')
                logger.info('CONTACT LOGS FOR COMFORT CANCEL')
                if ENROLMENT_DATA_ENTITY['couponCode'] is not None and Constants.MBO_COUPON_CODE_COMFORT_CANCEL.lower() in ENROLMENT_DATA_ENTITY['couponCode'].lower():
                    
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


                # ######################################################################
                # UPLOAD COVID VERIFICATION                                            #
                # ######################################################################
                print('UPLOAD COVID VERIFICATION')
                logger.info('UPLOAD COVID VERIFICATION')
                try:
                    resposne = upload_covid_verification(ENROLMENT_DATA_ENTITY, MBO_CLIENT)

                    if response is None:
                        print(f"Failed to update COVID Client Index")
                        logger.error(f"Failed to update COVID Client Index")

                except Exception as ex:
                    print(f"Error - Failed to update COVID Client Index: {ex}")
                    logger.error((f"Error - Failed to update COVID Client Index: {ex}"))

                # Set Mbo Unique Id
                try:
                    ENROLMENT_DATA_ENTITY['mboUniqueId'] = str(MBO_CLIENT['UniqueId'])
                except Exception as e:
                    logger.error(f"Error setting MBO Unique Id: {ex}")
                    print(f"Error setting MBO Unique Id: {ex}")


        # ######################################################################
        # TRIGGER COMMUNICATIONS                                               #
        # ######################################################################
        print('TRIGGER COMMUNICATIONS')
        logger.info('TRIGGER COMMUNICATIONS')
        ENROLMENT_DATA_ENTITY = trigger_communications(ENROLMENT_DATA_ENTITY, sqs_body['type'], str(sqs_body['id']) )

        if ENROLMENT_DATA_ENTITY['communicationsStatus'] == CommunicationsStatus.EMAIL_CAMPAIGN_ERROR[0]:
            ENROLMENT_DATA_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", ENROLMENT_DATA_ENTITY)





        print('DONE')

    except Exception as ex:
        print(f'[process_enrolment] Critical Error: {ex}')
        logger.error(f'[process_enrolment] Critical Error: {ex}')
        send_error_notification(f'[process_enrolment] Critical Error: {ex}')
    finally:
        return


def receive_coach_enrolment(event, context):

    try:
        print('receive_coach_enrolment')
        logger.info(f"Coach Enrolment SQS URL {os.getenv('CoachEnrolmentSqsUrl')}")
        print(f"Coach Enrolment SQS URL {os.getenv('CoachEnrolmentSqsUrl')}")

        # TESTING
        # BODY = json.loads(event['body'])
        # FLAG = push_onto_queue(os.getenv('CoachEnrolmentSqsUrl'), BODY, f"Coach:{BODY['type']}:{BODY['id']}", f"Coach:{BODY['type']}:{BODY['id']}")

        body = json.loads(event['body'])
        _ids = FS_FIELD_IDS.FORM[body["FormID"]]

        FP_COACH_ENTITY = build_fp_coach_enrolment_data(body)

        FP_COACH_ENTITY = source_post_request(Constants.SOURCE_FP_COACH_ENROLMENT_URL, FP_COACH_ENTITY)

        # ////////////////
        # ADD TO SQS
        # ////////////////
        BODY = {'type' : 'fpCoachEnrolment', 'id' : FP_COACH_ENTITY['id']}
        FLAG = push_onto_queue(os.getenv('CoachEnrolmentSqsUrl'), BODY, f"{BODY['type']}:{BODY['id']}", f"{BODY['type']}:{BODY['id']}")       

        if FLAG:
            return {
                "statusCode": 200,
                "body": json.dumps({"message": 'Receive Coach Enrolment Complete'})
            }
        else:
            return {
                "statusCode": 418,
                "body": json.dumps({"message": f"Failed to add to SQS: {event['body']}" })
            }

    except Exception as ex:
        print(f'[receive_coach_enrolment] Critical Error: {ex}')
        send_error_notification(f'[receive_coach_enrolment] Critical Error: {ex}')
        return {
            "statusCode": 501,
            "body": json.dumps({"message": f'[receive_enrolment] Critical Error: {ex}'})
        }



def process_coach_enrolment(event, context):

    try:
        print('process_coach_enrolment')
        print(event)

        sqs_body = json.loads(event['Records'][0]['body'])

        print(sqs_body)

        # ######################################################################
        # GET ENROLMENT                                                        #
        # ######################################################################
        print('GET ENROLMENT')
        IS_MBO_SUCCESS = True
        URL = 'source/'+ sqs_body['type'] +'/'+ str(sqs_body['id']) +'/false'
        FP_COACH_ENTITY = source_get_request(URL)

        if FP_COACH_ENTITY is None:
            print("Error - FP Coach Enrolment Doesn't Exist in Source")
            logger.error("Error - FP Coach Enrolment Doesn't Exist in Source")
            handle_manual_submission(MemberStatus.CLIENT_NOT_FOUND, None, FP_COACH_ENTITY)
            IS_MBO_SUCCESS = False


        if IS_MBO_SUCCESS:
            # ######################################################################
            # GET MBO CLIENT                                                       #
            # ######################################################################
            print('GET MBO CLIENT')
            PROCESS_ENTITY = get_mbo_client(FP_COACH_ENTITY)
            
            FP_COACH_ENTITY = PROCESS_ENTITY['FP_COACH_ENTITY']
            MBO_CLIENT = PROCESS_ENTITY['MBO_CLIENT']
            EXISTING_CREDIT_CARD = PROCESS_ENTITY['EXISTING_CREDIT_CARD']
            EXISTING_BANK_DETIALS = PROCESS_ENTITY['EXISTING_BANK_DETIALS']
            IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']


        if IS_MBO_SUCCESS:
            # ######################################################################
            # ADD BANK DETAILS                                                     #
            # ######################################################################
            if FP_COACH_ENTITY['paymentType'] == Constants.FS_PAYMENT_TYPE_BANK_ACCOUNT or FP_COACH_ENTITY['paymentType'] == Constants.FS_PAYMENT_TYPE_DIRECT_DEBIT:
                print('ADD BANK DETAILS')
                if FP_COACH_ENTITY['memberBankDetail'] is not None:

                    PROCESS_ENTITY = coach_add_bank_details(FP_COACH_ENTITY, MBO_CLIENT)

                    IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
                    FP_COACH_ENTITY = PROCESS_ENTITY['FP_COACH_ENTITY']

            # ######################################################################
            # PURCHASE CONTRACTS                                                   #
            # ######################################################################
            print('PURCHASE CONTRACTS')
            PROCESS_ENTITY = coach_purchase_contracts(FP_COACH_ENTITY, MBO_CLIENT)
            IS_MBO_SUCCESS = PROCESS_ENTITY['IS_MBO_SUCCESS']
            FP_COACH_ENTITY = PROCESS_ENTITY['FP_COACH_ENTITY']

            # #######################################################################################################
            # SET STATUS AND UPDATE DATABASE - A SUCCESS status will trigger payment details to be washed.          #
            # #######################################################################################################
            print('SET STATUS AND UPDATE DATABASE')
            if IS_MBO_SUCCESS:
                FP_COACH_ENTITY['status'] = MemberStatus.SUCCESS[0]
                
            FP_COACH_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", FP_COACH_ENTITY)

            # ######################################################################
            # TRIGGER PDF WRITER - Push onto SQS Queue                             #
            # ######################################################################
            print('TRIGGER PDF WRITER')
            FP_COACH_ENTITY = trigger_pdf_writer(FP_COACH_ENTITY, MBO_CLIENT, "fpCoachEnrolment")


        # ######################################################################
        # TRIGGER COMMUNICATIONS                                               #
        # ######################################################################
        print('TRIGGER COMMUNICATIONS')
        FP_COACH_ENTITY = trigger_communications(FP_COACH_ENTITY, sqs_body['type'], str(sqs_body['id']))

        if FP_COACH_ENTITY['communicationsStatus'] == CommunicationsStatus.EMAIL_CAMPAIGN_ERROR[0]:
            FP_COACH_ENTITY = source_put_request(f"source/{sqs_body['type']}/false", FP_COACH_ENTITY)

        print('DONE')

    except Exception as ex:
        print(f'[process_coach_enrolment] Critical Error: {ex}')
        logger.error(f'[process_coach_enrolment] Critical Error: {ex}')
        send_error_notification(f'[process_coach_enrolment] Critical Error: {ex}')
    finally:
        return


# ############################################################
# TESTING                                                    #
# ############################################################
def hello(event, context):
    print('Inside fp-enrolment.hello')
    # start(event, context)
    response = {
        "statusCode": 200,
    }

    return response




def start(event, context):
    """
    First Lambda function. Triggered manually.
    :param event: AWS event data
    :param context: AWS function's context
    :return: ''
    """
    print('Inside fp-enrolment.start')

    print(event)

    # body = json.loads(event['body'])

    print(SQS_CLIENT.send_message(
        QueueUrl=os.getenv('SQS_URL'),
        MessageBody=event['body']
    ))
    # return ''
    response = {
        "statusCode": 200,
    }

    return response


def end(event, context):
    """
    Second Lambda function. Triggered by the SQS.
    :param event: AWS event data (this time will be the SQS's data)
    :param context: AWS function's context
    :return: ''
    """
    print('Inside QUEUE END')

    # time.sleep(3)

    print(event)

    return ''
    


