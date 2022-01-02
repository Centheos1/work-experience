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


def decode_gymsales_credentials():
    decoded_credentials = base64.b64decode(Constants.GS_CREDENTIALS).decode("utf-8") 
    split = decoded_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
    Constants.GS_USERNAME = split[0]
    Constants.GS_PASSWORD = split[1]


def gymsales_get_request(URL, PARAMS = None):
    print('gymsales_get_request')
    
    if Constants.GS_USERNAME is None or Constants.GS_PASSWORD is None:
        decode_gymsales_credentials()
    
    try:
        r = requests.get(url = Constants.GS_BASE_URL + URL, params=PARAMS, auth=HTTPBasicAuth(Constants.GS_USERNAME, Constants.GS_PASSWORD) )
        r.raise_for_status()

    except HTTPError as http_err:
        print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP type: {type(http_err)}\nHTTP message: {http_err.response.reason}')
        logger.error(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP type: {type(http_err)}\nHTTP message: {http_err.response.reason}')
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


def gymsales_put_request(URL, PARAMS):
    
    IS_VALID = True
    RETRY_COUNT = 1
    FAILED_ATTEMPT = 0
    
    if Constants.GS_USERNAME is None or Constants.GS_PASSWORD is None:
        decode_gymsales_credentials()

    while IS_VALID:
        try:
            r = requests.put(url = Constants.GS_BASE_URL + URL, params=PARAMS, auth=HTTPBasicAuth(Constants.GS_USERNAME, Constants.GS_PASSWORD) )
            r.raise_for_status()

        except HTTPError as http_err:
            print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP type: {type(http_err)}\nHTTP message: {http_err.response.reason}')
            logger.error(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP type: {type(http_err)}\nHTTP message: {http_err.response.reason}')
            print('FAILED_ATTEMPT')
            loggger.error('FAILED_ATTEMPT')
            FAILED_ATTEMPT = FAILED_ATTEMPT + 1
            if FAILED_ATTEMPT >= RETRY_COUNT:
                IS_VALID = False

        except Exception as err:
            print(f'Error - PUT to Gym Sales: {err}')
            logger.error(f'Error - PUT to Gym Sales: {err}')
            print('FAILED_ATTEMPT')
            logger.error('FAILED_ATTEMPT')
            FAILED_ATTEMPT = FAILED_ATTEMPT + 1
            if FAILED_ATTEMPT >= RETRY_COUNT:
                IS_VALID = False

        else:
            event = r.json()
            j = json.dumps(event)
            response = json.loads(j)
            IS_VALID = False
            # print(response)
            return response


def gymsales_post_request(URL, PARAMS):
    
    IS_VALID = True
    RETRY_COUNT = 1
    FAILED_ATTEMPT = 0

    if Constants.GS_USERNAME is None or Constants.GS_PASSWORD is None:
        decode_gymsales_credentials()
    
    while IS_VALID:
        try:
            r = requests.post(url = Constants.GS_BASE_URL + URL, params=PARAMS, auth=HTTPBasicAuth(Constants.GS_USERNAME, Constants.GS_PASSWORD) )
            r.raise_for_status()

        except HTTPError as http_err:
            print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP type: {type(http_err)}\nHTTP message: {http_err.response.reason}')
            logger.error(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP type: {type(http_err)}\nHTTP message: {http_err.response.reason}')
            print('FAILED_ATTEMPT')
            logger.error('FAILED_ATTEMPT')
            FAILED_ATTEMPT = FAILED_ATTEMPT + 1
            if FAILED_ATTEMPT >= RETRY_COUNT:
                IS_VALID = False

        except Exception as err:
            print(f'Error adding client to Gym Sales: {err}')
            logger.error(f'Error - POST to Gym Sales: {err}')
            print('FAILED_ATTEMPT')
            logger.error('FAILED_ATTEMPT')
            FAILED_ATTEMPT = FAILED_ATTEMPT + 1
            if FAILED_ATTEMPT >= RETRY_COUNT:
                IS_VALID = False

        else:
            event = r.json()
            j = json.dumps(event)
            response = json.loads(j)
            IS_VALID = False
            # print(response)
            return response

