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


def decode_mbo_credentials():
    decoded_credentials = base64.b64decode(Constants.MBO_CREDENTIALS).decode("utf-8")
    split = decoded_credentials.split(":")
    Constants.MBO_USER_NAME = split[0]
    Constants.MBO_PASSWORD = split[1]


def get_mbo_token():

    USERTOKEN_URL = "usertoken/issue"

    body = {
        "Username": Constants.MBO_USER_NAME,
        "Password": Constants.MBO_PASSWORD
    }

    headers = {
        "Api-Key": Constants.MBO_API_KEY_FP_VIRTUAL,
        "Content-Type": Constants.CONTENT_TYPE,
        "SiteId": Constants.MBO_SITE_ID
    }

    API_ENDPOINT = Constants.MBO_BASE_URL + USERTOKEN_URL

    try:
        # sending post request and saving response as response object 
        r = requests.post(url = API_ENDPOINT, data = json.dumps(body), headers=headers)

    except HTTPError as http_err:
        logging.error(f'HTTP error: {http_err}')
        print(f'HTTP error: {http_err}')
        return http_err

    except Exception as err:
        logging.error(f'Error getting client visits: {err}')
        print(f'Error getting client visits: {err}')
        return err

    else:
        event = r.json()
        j = json.dumps(event)

        if Constants.IS_TEST:
            logger.info(r)
            logger.info(j)

        mbo_auth_data = json.loads(j)

        Constants.MBO_TOKEN = mbo_auth_data['AccessToken']
        
        Constants.MBO_HEADERS = {
            "Api-Key": Constants.MBO_API_KEY_FP_VIRTUAL,
            "Content-Type": Constants.CONTENT_TYPE,
            "SiteId": Constants.MBO_SITE_ID,
            "authorization": Constants.MBO_TOKEN
        }
        
        return Constants.MBO_TOKEN


def mbo_get_request(URL, params=None):
    
    IS_VALID = True
    RETRY_COUNT = 2
    ATTEMPT_COUNT = 0
    
    data = None
    while(IS_VALID):

        try:
            r = requests.get(url = Constants.MBO_BASE_URL + URL, params=params, headers=Constants.MBO_HEADERS )
            r.raise_for_status()

        except HTTPError as http_err:
            logger.error(f'HTTP error: {http_err}')
            print(f'HTTP error: {http_err}')
            ATTEMPT_COUNT = ATTEMPT_COUNT + 1
            if ATTEMPT_COUNT >= RETRY_COUNT:
                IS_VALID = False

            if http_err.response.status_code == 401:
                decode_mbo_credentials()
                get_mbo_token()
            else:
                return http_err

        except Exception as err:
            logger.error(f'Error {URL}: {err}')
            print(f'Error {URL}: {err}')
            ATTEMPT_COUNT = ATTEMPT_COUNT + 1
            if ATTEMPT_COUNT >= RETRY_COUNT:
                IS_VALID = False
            else:
                return err

        else:
            try:
                event = r.json()
                j = json.dumps(event)
                data = json.loads(j)
            except Exception as ex:
                logger.error(f'Error - [mbo_get_request]: {ex}')
                print(f'Error - [mbo_get_request]: {ex}')
            
            IS_VALID = False
            
    return data


def mbo_post_request(URL, BODY):
    
    IS_VALID = True
    RETRY_COUNT = 1
    ATTEMPT_COUNT = 0
    
    data = None
    while(IS_VALID):
        try:
            r = requests.post(url = Constants.MBO_BASE_URL + URL, data=json.dumps(BODY), headers=Constants.MBO_HEADERS )
            r.raise_for_status()

            if Constants.IS_TEST:
                logger.info(r)

        except HTTPError as http_err:
            print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')
            logger.error(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')
            
#             Unaurthorised
            if http_err.response.status_code == 401:
                decode_mbo_credentials()
                get_mbo_token()
            
            ATTEMPT_COUNT = ATTEMPT_COUNT + 1
            if ATTEMPT_COUNT >= RETRY_COUNT:
                IS_VALID = False

        except Exception as err:
            print(f'Error {URL}: {err}')
            logger.error(f'Error {URL}: {err}')
            ATTEMPT_COUNT = ATTEMPT_COUNT + 1
            if ATTEMPT_COUNT >= RETRY_COUNT:
                IS_VALID = False

        else:
            try:
                event = r.json()
                j = json.dumps(event)
                data = json.loads(j)
            except Exception as ex:
                print(f'Error - [mbo_post_request]: {ex}')
                logger.error(f'Error - [mbo_post_request]: {ex}')

            IS_VALID = False

    return data
