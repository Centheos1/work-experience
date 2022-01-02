import logging as logger
import hashlib
import hmac
import base64
import json
import requests
from requests.auth import HTTPBasicAuth
from requests.exceptions import HTTPError

# from fp_enrolment.constants import *
# from fp_enrolment.helpers import *
from constants import *
from helpers import *


def source_get_request(URL, PARAMS = None):
    try:
        if Constants.IS_TEST:
            BASE_URL = Constants.SOURCE_TEST_BASE_URL
            print(f'GET FROM TEST SITE: {BASE_URL}{URL}')
            logger.info(f'GET FROM TEST SITE: {BASE_URL}{URL}')
        else:
            BASE_URL = Constants.SOURCE_BASE_URL

        HEADERS = {
            "Content-Type": Constants.CONTENT_TYPE,
            Constants.SOURCE_AUTH_HEADER : Constants.SOURCE_UID
        }

        try:
            r = requests.get(url = BASE_URL + URL, params=PARAMS, headers=HEADERS )
            r.raise_for_status()

        except HTTPError as http_err:
            print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')
            logger.error(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')
            return http_err

        except Exception as err:
            print(f'Error {URL}: {err}')
            logger.error(f'Error {URL}: {err}')
            return err

        else:
            event = r.json()
            j = json.dumps(event)
            data = json.loads(j)
            return data

    except Exception as e:
        print(f"Error [source_get_request]: {e}")
        return None

def source_put_request(URL, BODY):
    
    try:
        if Constants.IS_TEST:
            BASE_URL = Constants.SOURCE_TEST_BASE_URL
            print(f"PUT AS A TEST: {BASE_URL}{URL}")
            logger.info(f"PUT AS A TEST: {BASE_URL}{URL}")
        else:
            BASE_URL = Constants.SOURCE_BASE_URL

        HEADERS = {
            "Content-Type": Constants.CONTENT_TYPE,
            Constants.SOURCE_AUTH_HEADER : Constants.SOURCE_UID
        }

        data = None
        try:
            r = requests.put(url = BASE_URL + URL, data=json.dumps(BODY), headers=HEADERS )
            r.raise_for_status()

        except HTTPError as http_err:
            print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')
            logger.error(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')

        except Exception as err:
            print(f'Error {URL}: {err}')
            logger.error(f'Error {URL}: {err}')

        else:
            if r.status_code != 204:
                event = r.json()
                j = json.dumps(event)
                data = json.loads(j)

            if Constants.IS_TEST:
                logger.info(r)
                print(r)

        return data

    except Exception as e:
        logger.error(f"Error [source_put_request]: {e}")
        print(f"Error [source_put_request]: {e}")
        return None

def source_post_request(URL, BODY):
    
    try:
        if Constants.IS_TEST:
            BASE_URL = Constants.SOURCE_TEST_BASE_URL
            logger.info(f"POST AS A TEST: {BASE_URL}{URL}")
            print(f"POST AS A TEST: {BASE_URL}{URL}")
        else:
            BASE_URL = Constants.SOURCE_BASE_URL

        HEADERS = {
            "Content-Type": Constants.CONTENT_TYPE,
            Constants.SOURCE_AUTH_HEADER : Constants.SOURCE_UID
        }

        data = None
        try:
            r = requests.post(url = BASE_URL + URL, data=json.dumps(BODY), headers=HEADERS )
            r.raise_for_status()

        except HTTPError as http_err:
            logger.error(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')
            print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')

        except Exception as err:
            logger.error(f'Error {URL}: {err}')
            print(f'Error {URL}: {err}')

        else:
            if r.status_code != 204:
                event = r.json()
                j = json.dumps(event)
                data = json.loads(j)

            if Constants.IS_TEST:
                logger.info(r)

        return data
    
    except Exception as e:
        logger.error(f"Error [source_post_request]: {e}")
        print(f"Error [source_post_request]: {e}")
        return None


def source_patch_request(URL, BODY=None):
    
    try:
        if Constants.IS_TEST:
            BASE_URL = Constants.SOURCE_TEST_BASE_URL
            print(f"PATCH AS A TEST: {BASE_URL}{URL}")
            logger.info(f"PATCH AS A TEST: {BASE_URL}{URL}")
        else:
            BASE_URL = Constants.SOURCE_BASE_URL

        HEADERS = {
            "Content-Type": Constants.CONTENT_TYPE,
            Constants.SOURCE_AUTH_HEADER : Constants.SOURCE_UID
        }

        data = None
        try:
            r = requests.patch(url = BASE_URL + URL, data=json.dumps(BODY), headers=HEADERS )
            r.raise_for_status()

        except HTTPError as http_err:
            print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP type: {type(http_err)}\nHTTP message: {http_err.response.reason}')
            logger.error(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP type: {type(http_err)}\nHTTP message: {http_err.response.reason}')

        except Exception as err:
            logger.error(f'Error {URL}: {err}')

        else:
            logger.info(r)
            if r.status_code != 204:
                event = r.json()
                j = json.dumps(event)
                data = json.loads(j)

        return data
    
    except Exception as e:
        print(f"Error [source_patch_request]: {e}")
        logger.error(f"Error [source_patch_request]: {e}")
        return None


def handle_manual_submission(status, enrolment = None, fp_coach = None):
    print('handle_manual_submission')
    MANUAL_SUBMISSION = {}
    
    if enrolment is not None:
        MANUAL_SUBMISSION['enrolmentDataId'] = enrolment['id']
    else:
        MANUAL_SUBMISSION['enrolmentDataId'] = None
    
    if fp_coach is not None:
        MANUAL_SUBMISSION['fpCoachEnrolmentDataId'] = fp_coach['id']
    else:
        MANUAL_SUBMISSION['fpCoachEnrolmentDataId'] = None

    MANUAL_SUBMISSION['status'] = status[0] # private String 
    MANUAL_SUBMISSION['errorDetails'] = status[1] # private String 
    MANUAL_SUBMISSION['errorCode'] = status[2] # private Integer 
    MANUAL_SUBMISSION['mboSubmissionCount'] = 1 # private Integer
#     MANUAL_SUBMISSION['submissionDate'] # private String 
#     MANUAL_SUBMISSION['staffMember'] # private String 
#     MANUAL_SUBMISSION['staffName'] # private String 
#     MANUAL_SUBMISSION['productId'] = None # private String 
#     MANUAL_SUBMISSION['cancellationDataId'] = None
#     MANUAL_SUBMISSION['contractId'] = None # private Long 
#     MANUAL_SUBMISSION['serviceId'] = None # private Long 

#     print('TODO - send to source')
    if Constants.IS_TEST:
        print(json.dumps(MANUAL_SUBMISSION))
        logger.info(json.dumps(MANUAL_SUBMISSION))
        
    source_post_request(Constants.SOURCE_HANDLE_MANUAL_SUBMISSION_URL, MANUAL_SUBMISSION)
    
    return MANUAL_SUBMISSION


