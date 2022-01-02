# FP_Data_Warehouse_Sync.py

import pandas as pd
import pymysql
import json
import requests
from requests.auth import HTTPBasicAuth
from requests.exceptions import HTTPError
import base64
import datetime
import pytz
import time
import holidays
import calendar
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
# pip install firebase-admin

import boto3
# pip install boto3

# #######################################################################################################################
# READ ME                                                                                                               #
# - Default Timezone is UTC                                                                                             #
# - Suspend your operation between 11PM Sunday through 6AM Monday                                                       #
# #######################################################################################################################

# #######################################################################################################################
# CONSTANTS                                                                                                             #
# #######################################################################################################################
class Constants:
    IS_TEST = True
    AUTH_SPLIT_CHARACTER = ":"
    DATETIME_FORMAT = "%Y-%m-%d %H:%M:%S"
    DATE_FORMAT = "%Y-%m-%d"
    CONFIG = None
    CONFIG_FILE_PATH = "CONFIG_FILE_PATH"

    DB_FIRESTORE = None
    
    DWH_SYDNEY_ID = 'DWH_SYDNEY_ID'
    DWH_DARWIN_ID = 'DWH_DARWIN_ID'

    SOURCE_TEST_BASE_URL = 'https://test.fitnessplayground.com.au/v1/'
    SOURCE_BASE_URL = 'https://source.fitnessplayground.com.au/v1/'
    SOURCE_DATA_WAREHOUSE_SYNC_ON_URL = 'debt/dataWarehouseSyncOn'
    SOURCE_CLOUD_SEARCH_ADD_URL = 'cloudsearch/add'
    SOURCE_DEBT_PORTAL_ADD_URL = 'debt/debtportal'
    SOURCE_DEBT_PORTAL_GET_CURRENT_URL = 'debt/getAllCurrent'
    SOURCE_AUTH_HEADER = 'x-fp-authorization'
    CONTENT_TYPE = "application/json"

    MBO_DATETIME_FORMAT = "%Y-%m-%dT%H:%M:%S"
    MBO_BASE_URL = "https://api.mindbodyonline.com/public/v6/"
    MBO_GET_CLIENTS_URL = "client/clients"
    MBO_UPDATE_CLIENTS_URL = "client/updateclient"
    MBO_SITE_ID = "152065"
    MBO_TOKEN = None
    MBO_HEADERS = None
    MBO_USER_NAME = None
    MBO_PASSWORD = None
    MBO_INDEX_NAB_INDEX_ID = 11
    MBO_INDEX_NAB_COMPLETE = {'Active': True, 'Id': 49, 'Name': 'NAB Complete'}
    MBO_INDEX_NAB_PENDING = {'Active': True, 'Id': 50, 'Name': 'NAB Pending'}
    MBO_INDEX_NAB_PARTIAL_PAYMENT = {'Active': True, 'Id': 51, 'Name': 'NAB Partial Payment'}

    NAB_UNPROCESSED = 'NAB_UNPROCESSED'
    NAB_COMPLETE = 'NAB_COMPLETE'
    NAB_DEBT_COLLECTION = 'NAB_DEBT_COLLECTION'
    NAB_PARTIAL_PAYMENT = 'NAB_PARTIAL_PAYMENT'
    NAB_COMMS_SEQUENCE_ACTIVE = 'NAB_COMMS_SEQUENCE_ACTIVE'
    NAB_COMMS_SEQUENCE_COMPLETE = 'NAB_COMMS_SEQUENCE_COMPLETE'
    NAB_COMMS_SEQUENCE_ERROR = 'NAB_COMMS_SEQUENCE_ERROR'
    NAB_ERROR = 'NAB_ERROR'

    COMMS_PENDING = 'COMMS_PENDING'
    COMMS_EMAIL_1 = 'COMMS_EMAIL_1'
    COMMS_EMAIL_2 = 'COMMS_EMAIL_2'
    COMMS_EMAIL_3 = 'COMMS_EMAIL_3'
    COMMS_EMAIL_4 = 'COMMS_EMAIL_4'
    COMMS_EMAIL_5 = 'COMMS_EMAIL_5'

    DEBT_COMMS_DELAYS = [0,3,5,14,21]
    ACTIVE_LOCATIONS = """{"1" : false,"2" : true,"3" : false,"4" : false,"5" : false}"""
    
# #######################################################################################################################
# HELPERS                                                                                                               #
# #######################################################################################################################
def convert_datetime_timezone(dt, tz1 = 'UTC', tz2 = 'Australia/Sydney'):
    print(f"\t\t[convert_datetime_timezone] Start {dt}: From {tz1} -> To {tz2}")
    try:
        dt = pd.to_datetime(dt).strftime(Constants.DATETIME_FORMAT)
        
        tz1 = pytz.timezone(tz1)
        tz2 = pytz.timezone(tz2)

        dt = datetime.datetime.strptime(dt, Constants.DATETIME_FORMAT)
        dt = tz1.localize(dt)
        dt = dt.astimezone(tz2)
        dt = dt.strftime(Constants.DATETIME_FORMAT)

    except Exception as e:
        print(f"\t\t[convert_datetime_timezone] Error {e}")
    finally:
        print(f"\t\t[convert_datetime_timezone] Returning {dt}")
        return dt


def set_comms_schedule(d = None):
    DELAYS = Constants.DEBT_COMMS_DELAYS
    if d is None:
        NOW = datetime.datetime.utcnow().date()
    else:
        NOW = pd.to_datetime(d)
    return {
        Constants.COMMS_EMAIL_1 : (NOW + datetime.timedelta(DELAYS[0])).strftime(Constants.DATE_FORMAT),
        Constants.COMMS_EMAIL_2 : (NOW + datetime.timedelta(DELAYS[1])).strftime(Constants.DATE_FORMAT),
        Constants.COMMS_EMAIL_3 : (NOW + datetime.timedelta(DELAYS[2])).strftime(Constants.DATE_FORMAT),
        Constants.COMMS_EMAIL_4 : (NOW + datetime.timedelta(DELAYS[3])).strftime(Constants.DATE_FORMAT),
        Constants.COMMS_EMAIL_5 : (NOW + datetime.timedelta(DELAYS[4])).strftime(Constants.DATE_FORMAT)
    }

# ##############################################
# CREDENTIALS.                                 #
# ##############################################
def init_credentials():
    print(f"\t[init_credentials] Start")
    # Read JSON File
    with open(f"{Constants.CONFIG_FILE_PATH}") as json_file:
        Constants.CONFIG = json.load(json_file)

    if Constants.CONFIG is not None:
        print(f"\t[init_credentials] CONFIG: Returning Successfully Initiated - Is Debt Sync Required: {Constants.CONFIG['is_debt_sync_required']}")
        return True
    else:
        print(f"\t[init_credentials] CONFIG: Returning Initiation Failed")
        return False

def decode_mbo_credentials():
    decoded_credentials = base64.b64decode(Constants.CONFIG['MBO_CREDENTIALS']).decode("utf-8")
    split = decoded_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
    Constants.MBO_USER_NAME = split[0]
    Constants.MBO_PASSWORD = split[1]
    
    
# ##############################################
# CONNECT TO FIRESTORE DATABASE                #
# ##############################################
def init_firestore():
    print(f"\t[init_firestore] Start")
    if Constants.DB_FIRESTORE is None:
        try:
            cred = credentials.Certificate(Constants.CONFIG['db_firestore_credentials'])
            firebase_admin.initialize_app(cred)
        except Exception as ex:
            print(f"\t[init_firestore] Error Initializing Firebase: {ex}")

        Constants.DB_FIRESTORE = firestore.client()

        if Constants.DB_FIRESTORE is not None:
            print("\t[init_firestore] Firestore is connected")
        else:
            print("\t[init_firestore] Firestore connection failed")

    else:
        print("\t[init_firestore] Returning Firestore is connected")


# ##############################################
# MINDBODY ONLINE.                             #
# ##############################################
def get_mbo_token():

    decode_mbo_credentials()

    print(f"\t\t[get_mbo_token]: Start {Constants.MBO_USER_NAME}")
    
    USERTOKEN_URL = "usertoken/issue"

    body = {
        "Username": Constants.MBO_USER_NAME,
        "Password": Constants.MBO_PASSWORD
    }

    headers = {
        "Api-Key": Constants.CONFIG['MBO_API_KEY_FP_SOURCE'],
        "Content-Type": Constants.CONTENT_TYPE,
        "SiteId": Constants.MBO_SITE_ID
    }

    API_ENDPOINT = Constants.MBO_BASE_URL + USERTOKEN_URL

    try:
        r = requests.post(url = API_ENDPOINT, data = json.dumps(body), headers=headers)

    except HTTPError as http_err:
        logging.error(f'\t\t[get_mbo_token]: HTTP error: {http_err}')
        return http_err

    except Exception as err:
        logging.error(f'\t\t[get_mbo_token]: Error getting client visits: {err}')
        return err

    else:
        event = r.json()
        print(r)
        j = json.dumps(event)
        print(j)
        mbo_auth_data = json.loads(j)

        Constants.MBO_TOKEN = mbo_auth_data['AccessToken']
        
        Constants.MBO_HEADERS = {
            "Api-Key": Constants.CONFIG['MBO_API_KEY_FP_SOURCE'],
            "Content-Type": Constants.CONTENT_TYPE,
            "SiteId": Constants.MBO_SITE_ID,
            "authorization": Constants.MBO_TOKEN
        }
        
        print(f"\t\t[get_mbo_token]: Returning {Constants.MBO_HEADERS is not None}")
        return Constants.MBO_TOKEN


def mbo_get_request(URL, params=None):

    try:
        r = requests.get(url = Constants.MBO_BASE_URL + URL, params=params, headers=Constants.MBO_HEADERS )
        r.raise_for_status()

    except HTTPError as http_err:
        print(f'HTTP error: {http_err}')
        return None

    except Exception as err:
        print(f'Error {URL}: {err}')
        return None

    else:
        event = r.json()
        j = json.dumps(event)
        data = json.loads(j)
            
    return data


def mbo_post_request(URL, BODY):
    
    try:
        r = requests.post(url = Constants.MBO_BASE_URL + URL, data=json.dumps(BODY), headers=Constants.MBO_HEADERS )
        r.raise_for_status()

    except HTTPError as http_err:
        print(f'\t\t[mbo_post_request] HTTP error: {http_err}')
        return None

    except Exception as err:
        print(f'\t\t[mbo_post_request] Error {URL}: {err}')
        return None

    else:
        try:
            event = r.json()
            j = json.dumps(event)
            data = json.loads(j)
        except Exception as ex:
            print(f'\t\t[mbo_post_request] Error: {ex}')
            data = None
            
    return data

def get_all_mbo_clients():
    print(f"\t[get_all_mbo_clients]: Start Get all Clients from MBO")
    REQUEST_LIMIT = 100
    REQUEST_OFFSET = 0
    TOTAL_RESULTS = 1
    ERRORS = 0
    EXIT = 10
    MBO_CLIENTS = []
    URL = Constants.MBO_GET_CLIENTS_URL

    while REQUEST_OFFSET < TOTAL_RESULTS:
        PARAMS = {
            'Limit' : REQUEST_LIMIT,
            'Offset' : REQUEST_OFFSET
        }

        res = mbo_get_request(URL, PARAMS)
        
        if res is None:
            ERRORS += 1
            print(f"Error!: {ERRORS} of {EXIT}")
            if ERRORS >= EXIT:
                return None
        else:
            REQUEST_OFFSET = REQUEST_OFFSET + REQUEST_LIMIT
            TOTAL_RESULTS = res['PaginationResponse']['TotalResults']

            for c in res['Clients']:
                MBO_CLIENTS.append(c)

            if REQUEST_OFFSET % 1000 == 0:
                print(f"\t[[get_all_mbo_clients]] REQUEST_LIMIT: {REQUEST_LIMIT} | REQUEST_OFFSET: {REQUEST_OFFSET} | TOTAL_RESULTS: {TOTAL_RESULTS} | MBO_CLIENTS: {len(MBO_CLIENTS)} | {round(REQUEST_OFFSET/TOTAL_RESULTS*100,2)}%")

    print(f"\t[[get_all_mbo_clients]] ERRORS: {ERRORS}")
    print(f"\t[[get_all_mbo_clients]] Returning REQUEST_LIMIT: {REQUEST_LIMIT} | REQUEST_OFFSET: {REQUEST_OFFSET} | TOTAL_RESULTS: {TOTAL_RESULTS} | MBO_CLIENTS: {len(MBO_CLIENTS)} | {round(REQUEST_OFFSET/TOTAL_RESULTS*100,2)}%")
    return MBO_CLIENTS


def update_mbo_client_index(CLIENT, MBO_INDEX):
    
    if CLIENT is None:
        return
    
    print(f"\t[update_mbo_client_index] Start Client UniqueId {CLIENT['UniqueId']}")

    CLIENT_IDX = None
    CNT = 0
    for i in CLIENT['ClientIndexes']:
        if i.get('Id') == Constants.MBO_INDEX_NAB_INDEX_ID:
            print(i)
            CLIENT_IDX = CNT
        CNT += 1

    if CLIENT_IDX == None:
        CLIENT['ClientIndexes'].append({'Id' : Constants.MBO_INDEX_NAB_INDEX_ID, 'ValueId' : MBO_INDEX['Id']})
    else:
        CLIENT['ClientIndexes'][CLIENT_IDX]['ValueId'] = MBO_INDEX['Id']

    BODY = {
        'Client' : CLIENT,
        'CrossRegionalUpdate' : False,
        'Test' : Constants.IS_TEST
    }
    mbo_post_request(Constants.MBO_UPDATE_CLIENTS_URL, BODY)
    print(f"\t[update_mbo_client_index] Returning")


# ##############################################
# SOURCE.                                      #
# ##############################################
def source_get_request(URL, PARAMS = None):
    print(f"\t\t[source_get_request]: {URL}")
    try:
        if Constants.IS_TEST:
            BASE_URL = Constants.SOURCE_TEST_BASE_URL
            print(f'GET FROM TEST SITE: {BASE_URL}{URL}')
        else:
            BASE_URL = Constants.SOURCE_BASE_URL

        HEADERS = {
            "Content-Type": Constants.CONTENT_TYPE,
            Constants.SOURCE_AUTH_HEADER : Constants.CONFIG['source_uid']
        }

        try:
            r = requests.get(url = BASE_URL + URL, params=PARAMS, headers=HEADERS )
            r.raise_for_status()

        except HTTPError as http_err:
            print(f'[[source_get_request]] HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')
            return None

        except Exception as err:
            print(f'[[source_get_request]] Error {URL}: {err}')
            return None

        else:
            event = r.json()
            j = json.dumps(event)
            data = json.loads(j)
            return data

    except Exception as e:
        print(f"[source_get_request] Error: {e}")
        return None


def source_put_request(URL, BODY):

    print(f"\t\t[source_put_request]: {URL}")
    
    try:
        if Constants.IS_TEST:
            BASE_URL = Constants.SOURCE_TEST_BASE_URL
            print(f"\t\t[source_put_request]: PUT AS A TEST: {BASE_URL}{URL}")
        else:
            BASE_URL = Constants.SOURCE_BASE_URL

        HEADERS = {
            "Content-Type": Constants.CONTENT_TYPE,
            Constants.SOURCE_AUTH_HEADER : Constants.CONFIG['source_uid']
        }

        data = None
        try:
            r = requests.put(url = BASE_URL + URL, data=json.dumps(BODY), headers=HEADERS )
            r.raise_for_status()

        except HTTPError as http_err:
            print(f'\t\t[source_put_request]: HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')

        except Exception as err:
            print(f'\t\t[source_put_request]: Error {URL}: {err}')

        else:
            if r.status_code != 204:
                event = r.json()
                j = json.dumps(event)
                data = json.loads(j)

            if Constants.IS_TEST:
                print(f"\t\t[source_put_request]: TEST response {r}")

        return data

    except Exception as e:
        print(f"Error [source_put_request]: {e}")
        return None


# ##############################################
# FP DATA WAREHOUSE DATABASE.                  #
# ##############################################
def get_dwh_data(ALL_DATA = False):

    print(f"\t[get_dwh_data] Start")
    
    dwh_members = None
    
    try:
        decoded_dwh_credentials = base64.b64decode(Constants.CONFIG['db_dwh_credentials']).decode("utf-8")
        split = decoded_dwh_credentials.split(":")

        host = split[0]
        user = split[1]
        database = split[2]
        passw = split[3]
        conn = pymysql.connect(host = host, db=database, user=user, password=passw)
        
        if ALL_DATA == True:
            query = f"""SELECT * FROM members WHERE tplogin_id = '{Constants.DWH_SYDNEY_ID}'"""
        else:
            query = f"""SELECT * FROM members WHERE updated >= '{Constants.CONFIG['last_sync']}' AND tplogin_id = '{Constants.DWH_SYDNEY_ID}'"""
            
        dwh_members = pd.read_sql(query, con=conn)

    except Exception as ex:
        print(f'\t[get_dwh_data] Database Read Error: {ex}')
    finally:
        conn.close()
        print(f"\t[get_dwh_data] Returning {len(dwh_members)} members ")
        return dwh_members


# #######################################################################################################################
# WORKERS                                                                                                               #
# #######################################################################################################################

# Update Firestore
def update_firestore(dwh_members):
    
    print(f"\t[update_firestore] Start with {len(dwh_members)} records")
    
    for i in range(len(dwh_members)):
        m = dwh_members.iloc[i]
        MEMBER = m.to_dict()
        MEMBER['created'] = pd.to_datetime(MEMBER['created']).strftime(Constants.DATETIME_FORMAT)
        MEMBER['updated'] = pd.to_datetime(MEMBER['updated']).strftime(Constants.DATETIME_FORMAT)

        if MEMBER['member'] is not None:
            MEMBER['member'] = json.loads(MEMBER['member'])

        if MEMBER['memberships'] is not None:
            MEMBER['memberships'] = json.loads(MEMBER['memberships'])

        if MEMBER['contracts'] is not None:
            MEMBER['contracts'] = json.loads(MEMBER['contracts'])

        if MEMBER['isoverride'] == b'\x01':
            MEMBER['isoverride'] = True
        else:
            MEMBER['isoverride'] = False

        if MEMBER['isaccesscontrol'] == b'\x01':
            MEMBER['isaccesscontrol'] = True
        else:
            MEMBER['isaccesscontrol'] = False

        if MEMBER['isdeleted'] == b'\x01':
            MEMBER['isdeleted'] = True
        else:
            MEMBER['isdeleted'] = False

        if MEMBER['isseen'] == b'\x01':
            MEMBER['isseen'] = True
        else:
            MEMBER['isseen'] = False
            
        if str(MEMBER['balance']) == 'nan':
            MEMBER['balance'] = 0

        Constants.DB_FIRESTORE.collection(u'members').document(MEMBER['member_id']).set(MEMBER)

    print(f"\t[update_firestore] Returning ")


# Update Cloud Search
def update_cloud_search(dwh_members):
    print(f"\t[update_cloud_search] Start with {len(dwh_members)} records")

    ALL_CLIENTS = []
    PAGINATION_COUNT = 0

    for i in range(len(dwh_members)):
        m = dwh_members.iloc[i]

        if m['tplogin_id'] == Constants.DWH_SYDNEY_ID:

            PAGINATION_COUNT += 1

            CLIENT = json.loads(m['member'])

            if CLIENT['Id'] is None:
                Id = ''
            else:
                Id = CLIENT['Id']

            if CLIENT['FirstName'] is None:
                FirstName = ''
            else:
                FirstName = CLIENT['FirstName']

            if CLIENT['LastName'] is None:
                LastName = ''
            else:
                LastName = CLIENT['LastName']

            if CLIENT['Email'] is None:
                Email = ''
            else:
                Email = CLIENT['Email']

            if CLIENT['MobilePhone'] is None:
                MobilePhone = ''
            else:
                MobilePhone = CLIENT['MobilePhone']

            CLIENT_LOOK_UP = {
                "access_key_number" : Id,
                "first_name" : FirstName,
                "last_name" : LastName,
                "email" : Email,
                "phone" : MobilePhone,
                "mbo_unique_id": CLIENT['UniqueId']
            }

            ALL_CLIENTS.append(CLIENT_LOOK_UP)

            if PAGINATION_COUNT % 1000 == 0:
                print(f"\t[update_cloud_search] Sending page.")
                request = { "members" : ALL_CLIENTS }
                source_put_request(Constants.SOURCE_CLOUD_SEARCH_ADD_URL, request)
                ALL_CLIENTS.clear()

    # PUT remaining recored
    if len(ALL_CLIENTS) != 0:
        request = { "members" : ALL_CLIENTS }
        source_put_request(Constants.SOURCE_CLOUD_SEARCH_ADD_URL, request)

    print(f"\t[update_cloud_search] Returning")


def sync_with_mbo_clients():
    
    print("\t[update_mbo_clients] Start")
    
    dwh_members = get_dwh_data(True)
    decode_mbo_credentials()
    get_mbo_token()
    MBO_CLIENTS = get_all_mbo_clients()
    
    ADD_MEMBERS = []
    for c in MBO_CLIENTS:
        UNIQUE_ID = c['UniqueId']
        CLIENT = json.dumps(c)

        DWH_MEMBER = dwh_members[dwh_members['member_id'] == str(UNIQUE_ID)]

        if DWH_MEMBER.empty:

            ADD_MEMBER = {
                'gymsync_id' : None,
                'tplogin_id' : Constants.DWH_SYDNEY_ID,
                'member_id' : UNIQUE_ID,
                'created' : c['CreationDate'],
                'updated' : datetime.datetime.utcnow().strftime(Constants.DATETIME_FORMAT),
                'format' : "mindbody",
                'member' : CLIENT,
                'memberships' : None,
                'contracts' : None,
                'balance' : c['AccountBalance'],
                'access_type' : None,
                'access_status': None,
                'isoverride' : False,
                'isaccesscontrol' : False,
                'isdeleted' : True,
                'isseen' : False
            }
            ADD_MEMBERS.append(ADD_MEMBER)

        else:
            dwh_members.loc[DWH_MEMBER.index.values[0],'member'] = CLIENT

    print(f"\t[update_mbo_clients] DWH Members: {len(dwh_members)}")
    dwh_members = dwh_members.append(ADD_MEMBERS, ignore_index=True)
    dwh_members.reset_index(drop=True, inplace=True)
    print(f"\t[update_mbo_clients] Returning {len(dwh_members)} DWH Members | {len(MBO_CLIENTS)} MBO_CLIENTS")

    # return Dictionary
    # return { 'dwh_members' : dwh_members.to_dict(orient='records'), 'MBO_CLIENTS': MBO_CLIENTS }
    # return DataFrame
    return { 'dwh_members' : dwh_members, 'MBO_CLIENTS': MBO_CLIENTS }



# Possible Cases
# New Debt
# Partial Payment
# Existing Debt no longer NAB
# Existing Debt, no payment made
def handle_debt(MBO_CLIENTS):
    
    print(f"\t[handle_debt] Start with {len(MBO_CLIENTS)}")
    # Get Current Debt Entites -> NAB_PARTIAL_PAYMENT or NAB_COMMS_SEQUENCE_ACTIVE
    CURRENT_DEBT = source_get_request(Constants.SOURCE_DEBT_PORTAL_GET_CURRENT_URL)
    
    print(f"\t[handle_debt]: MBO_CLIENTS: {len(MBO_CLIENTS)} | CURRENT_DEBT: {len(CURRENT_DEBT)}")

    # Handle Partial Payment updates -> If new partial payment extend partial_payment_expiry
    print(f"\t[handle_debt] Set MBO Unique Ids")
    _IDS = []
    for d in CURRENT_DEBT:
        _IDS.append(int(d['mbo_unique_id']))

    print(f"\t[handle_debt] New Debt && Existing Debt no longer NAB")
    for CLIENT in MBO_CLIENTS:
        # New Debt
        if CLIENT['AccountBalance'] < 0 and int(CLIENT['UniqueId']) not in _IDS:
            DEBT_PORTAL_ENTITY = {
                'mbo_unique_id' : CLIENT['UniqueId'],
                'status' : Constants.NAB_UNPROCESSED,
                'communications_status' : Constants.COMMS_PENDING,
                'createDate' : datetime.datetime.utcnow().strftime(Constants.DATETIME_FORMAT),
                'updateDate' : datetime.datetime.utcnow().strftime(Constants.DATETIME_FORMAT),
                'client' : json.dumps(CLIENT),
                'comms_schedule' : json.dumps(set_comms_schedule()),
                'partial_payment_expiry' : None,
                'debt_amount' : abs(CLIENT['AccountBalance'])
            }
            source_put_request(Constants.SOURCE_DEBT_PORTAL_ADD_URL, DEBT_PORTAL_ENTITY)
            MBO_INDEX = Constants.MBO_INDEX_NAB_PENDING
            update_mbo_client_index(CLIENT, MBO_INDEX)

        # Existing Debt no longer NAB
        if CLIENT['AccountBalance'] >= 0 and int(CLIENT['UniqueId']) in _IDS:
            DEBT_PORTAL_ENTITY = {
                'mbo_unique_id' : CLIENT['UniqueId'],
                'status' : Constants.NAB_COMPLETE,
                'communications_status' : Constants.NAB_COMMS_SEQUENCE_COMPLETE,
                'updateDate' : datetime.datetime.utcnow().strftime(Constants.DATETIME_FORMAT),
                'client' : json.dumps(CLIENT),
                'comms_schedule' : None,
                'partial_payment_expiry' : None,
                'debt_amount' : abs(CLIENT['AccountBalance'])
            }
            source_put_request(Constants.SOURCE_DEBT_PORTAL_ADD_URL, DEBT_PORTAL_ENTITY)
            MBO_INDEX = Constants.MBO_INDEX_NAB_COMPLETE
            update_mbo_client_index(CLIENT, MBO_INDEX)

    # Partial Payment
    print(f"\t[handle_debt] Partial Payment")
    CURRENT_DEBT = source_get_request(Constants.SOURCE_DEBT_PORTAL_GET_CURRENT_URL)
    for d in CURRENT_DEBT:
        if d['status'] == Constants.NAB_PARTIAL_PAYMENT:

            for CLIENT in MBO_CLIENTS:
                if int(CLIENT['UniqueId']) == int(d['mbo_unique_id']):
#                     print(f"{Constants.NAB_PARTIAL_PAYMENT}: {pd.to_datetime(d['partial_payment_expiry'])} {d['debt_amount']}")
                    # Partial payment made
                    if abs(round(CLIENT['AccountBalance'], 2)) < abs(round(d['debt_amount'], 2)):
                        UPATED_EXPIRY_DATE = ( pd.to_datetime(d['partial_payment_expiry']) + datetime.timedelta(days=14) )
                        DEBT_PORTAL_ENTITY = {
                            'mbo_unique_id' : int(CLIENT['UniqueId']),
                            'status' : Constants.NAB_PARTIAL_PAYMENT,
                            'updateDate' : datetime.datetime.utcnow().strftime(Constants.DATETIME_FORMAT),
                            'client' : json.dumps(CLIENT),
                            'comms_schedule' : json.dumps(set_comms_schedule(UPATED_EXPIRY_DATE)),
                            'partial_payment_expiry' : UPATED_EXPIRY_DATE.strftime(Constants.DATETIME_FORMAT),
                            'debt_amount' : abs(CLIENT['AccountBalance'])
                        }
                        source_put_request(Constants.SOURCE_DEBT_PORTAL_ADD_URL, DEBT_PORTAL_ENTITY)
                        MBO_INDEX = Constants.MBO_INDEX_NAB_PENDING
                        update_mbo_client_index(CLIENT, MBO_INDEX)

                    # partial_payment_expiry has elapsed
                    elif pd.to_datetime(d['partial_payment_expiry']) > datetime.datetime.utcnow():
                        DEBT_PORTAL_ENTITY = {
                            'mbo_unique_id' : int(CLIENT['UniqueId']),
                            'status' : Constants.NAB_DEBT_COLLECTION,
                            'communications_status' : Constants.NAB_COMMS_SEQUENCE_COMPLETE,
                            'updateDate' : datetime.datetime.utcnow().strftime(Constants.DATETIME_FORMAT),
                            'client' : json.dumps(CLIENT),
                            'comms_schedule' : None,
                            'partial_payment_expiry' : None,
                            'debt_amount' : abs(CLIENT['AccountBalance'])
                        }
                        source_put_request(Constants.SOURCE_DEBT_PORTAL_ADD_URL, DEBT_PORTAL_ENTITY)
                        MBO_INDEX = Constants.MBO_INDEX_NAB_COMPLETE
                        update_mbo_client_index(CLIENT, MBO_INDEX)

                    # No payment made
                    else:
                        DEBT_PORTAL_ENTITY = {
                            'mbo_unique_id' : int(CLIENT['UniqueId']),
                            'updateDate' : datetime.datetime.utcnow().strftime(Constants.DATETIME_FORMAT),
                            'client' : json.dumps(CLIENT),
                            'debt_amount' : abs(CLIENT['AccountBalance'])
                        }
                        source_put_request(Constants.SOURCE_DEBT_PORTAL_ADD_URL, DEBT_PORTAL_ENTITY)
    print(f"\t[handle_debt] Returning")


# #######################################################################################################################
# MAIN                                                                                                                  #
# #######################################################################################################################
def main(i = 0):
    i += 1
    D = datetime.datetime.utcnow()
    print(f"\n\n# ###################################################\n# [main] Start {i} at UTC {D}  #\n# ###################################################")

    LOCAL_TIME = pd.to_datetime(convert_datetime_timezone(D))
    if (LOCAL_TIME.weekday() == 6 and LOCAL_TIME.hour >= 23) or ((LOCAL_TIME.weekday() == LOCAL_TIME and D.hour <= 6)):
        print(f"\n{datetime.datetime.utcnow()} [main] Do Not Run Sync")
    else:
        print(f"\n{datetime.datetime.utcnow()} [main] Run Sync {i}")
        
        print(f"\n{datetime.datetime.utcnow()} [main] Initialise Credentials")
        init_credentials()
        
        if source_get_request(Constants.SOURCE_DATA_WAREHOUSE_SYNC_ON_URL) and not Constants.CONFIG["sync_in_progress"]:
            print("Data Warehouse Sync is On and No Sync In Progress")
    
            Constants.CONFIG["sync_in_progress"] = True
            with open(f"{Constants.CONFIG_FILE_PATH}", 'w', encoding='utf-8') as f:
                json.dump(Constants.CONFIG, f, ensure_ascii=False, indent=4)

            LAST_SYNC = pd.to_datetime(Constants.CONFIG['last_sync'])
            print(f"\n{datetime.datetime.utcnow()} [main] Previous sync run time: {LAST_SYNC}")
            if LAST_SYNC.day != D.day:
                Constants.CONFIG['is_debt_sync_required'] = True
                print(f"\n{datetime.datetime.utcnow()} [main] First sync of the day is_debt_sync_required: {Constants.CONFIG['is_debt_sync_required']}")

            if Constants.CONFIG['is_debt_sync_required'] and LOCAL_TIME.hour >= 6:
                print(f"\n{datetime.datetime.utcnow()} [main] sync_with_mbo_clients")
                TEMP = sync_with_mbo_clients()
                dwh_members = TEMP['dwh_members'] # DataFrame
                MBO_CLIENTS = TEMP['MBO_CLIENTS'] # dict
                print(f"\n{datetime.datetime.utcnow()} [main] handle_debt")
                handle_debt(MBO_CLIENTS)
                Constants.CONFIG['is_debt_sync_required'] = False

            else:
                print(f"\n{datetime.datetime.utcnow()} [main] is_debt_sync_required: {Constants.CONFIG['is_debt_sync_required']} ->  Pulling data from data warehouse")
                dwh_members = get_dwh_data()

            print(f"\n{datetime.datetime.utcnow()} [main] Syncing Cloud Search {len(dwh_members)} records")
            update_cloud_search(dwh_members)

    #        print(f"\n{datetime.datetime.utcnow()} [main] Syncing Firebase Firestore {len(dwh_members)} records")
    #        init_firestore()
    #        update_firestore(dwh_members)

            print(f"\n{datetime.datetime.utcnow()} [main] Update Config File")
            NOW = D.strftime(Constants.DATETIME_FORMAT)
            Constants.CONFIG['last_sync'] = NOW
            Constants.CONFIG["sync_in_progress"] = False
            with open(f"{Constants.CONFIG_FILE_PATH}", 'w', encoding='utf-8') as f:
                json.dump(Constants.CONFIG, f, ensure_ascii=False, indent=4)
        
        else:
            print("Data Warehouse Sync is Off OR Sync In Progress")

    print(f"\n{datetime.datetime.utcnow()} [main] Sync iteration complete. Time to complete: {datetime.datetime.utcnow() - D}")
    
#     This is not stable, will use cronjob
#     time.sleep(Constants.CONFIG['polling_delay'])
#     main(i)


if __name__ == "__main__":
    main()
