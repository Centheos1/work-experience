import pandas as pd
import json
import pandas as pd
import requests
from requests.exceptions import HTTPError
import datetime
import logging

MBO_BASE_URL = "https://api.mindbodyonline.com/public/v6/"
CONTENT_TYPE = "application/json"
START_TIME_WINDOW_MIN = -90
END_TIME_WINDOW_MIN = 15
IS_TEST = False


# Test function
def hello(event, context):
    body = {
        "message": "Go Serverless v1.0! Your function executed successfully!",
        "input": event
    }

    response = {
        "statusCode": 200,
        "body": json.dumps(body)
    }

    return response


def get_token(data):

    USERTOKEN_URL = "usertoken/issue"

    body = {
        "Username": data["USERNAME"],
        "Password": data["PASSWORD"]
    }

    headers = {
        "Api-Key": data['MBO_API_KEY'],
        "Content-Type": CONTENT_TYPE,
        "SiteId": data['MBO_SITE_ID']
    }

    API_ENDPOINT = MBO_BASE_URL + USERTOKEN_URL

    try:
        # sending post request and saving response as response object 
        r = requests.post(url = API_ENDPOINT, data = json.dumps(body), headers=headers)

    except HTTPError as http_err:
        logging.error(f'HTTP error: {http_err}')
        return http_err

    except Exception as err:
        logging.error(f'Error getting client visits: {err}')
        return err

    else:
        event = r.json()
    #     print(r)
        j = json.dumps(event)
    #     print(j)
        mbo_auth_data = json.loads(j)

        return mbo_auth_data['AccessToken']


def isWithInCheckInWindow(v_startTime, v_endTime, e_timeStamp):

    canCheckIn = False
    class_start_time = pd.to_datetime(v_startTime)
    class_end_time = pd.to_datetime(v_endTime)
    gate_event_time = pd.to_datetime(e_timeStamp)

#     print('Class Start',class_start_time)
#     print('Class End',class_end_time)
#     print('Gate Time',gate_event_time)

    start_diff = gate_event_time - class_start_time
    start_minutes = divmod(start_diff.seconds, 60)

    end_diff = gate_event_time - class_end_time
    end_minutes = divmod(end_diff.seconds, 60)

    if start_diff.days < 0:
        # print('Gate Event is Before Class Start')
        start_diff_min = ((24 * 60) - start_minutes[0])* start_diff.days
    else:
        # print('Gate Event is After Class Start')
        start_diff_min = start_minutes[0]

    if end_diff.days < 0:
        # print('Gate Event is Before Class End')
        end_diff_min = ((24 * 60) - end_minutes[0])* end_diff.days
    else:
        # print('Gate Event is After Class End')
        end_diff_min = end_minutes[0]

    # Before Class
    if start_diff_min <=0 and start_diff_min >= START_TIME_WINDOW_MIN:
        canCheckIn = True

    # After 
    if end_diff_min >= 0 and end_diff_min <= END_TIME_WINDOW_MIN:
        canCheckIn = True

    if start_diff_min >= 0 and end_diff_min <=0:
        canCheckIn = True
        
    print('canCheckIn ',canCheckIn)
        
    return canCheckIn


def get_client_visits(gate_data, headers):

    GET_CLIENT_VISITS_URL = "client/clientvisits"

    # #################################################################################################### TODO
    # THIS NEEDS TO BE m_tpcardid
    GET_CLIENT_VISITS_PARAMS = {
        'ClientId': gate_data['m_tpcardid']
    }
    # #################################################################################################### TODO

    try:
        r = requests.get(url = MBO_BASE_URL + GET_CLIENT_VISITS_URL, params=GET_CLIENT_VISITS_PARAMS, headers=headers )

        r.raise_for_status()

    except HTTPError as http_err:
        logging.error(f'HTTP error: {http_err}')
        return http_err

    except Exception as err:
        logging.error(f'Error getting client visits: {err}')
        return err

    else:
        event = r.json()
        j = json.dumps(event)
        client_visits_data = json.loads(j)

        # print(client_visits_data)
        return client_visits_data


def update_client_visit(v, headers):

    GET_UPDATE_CLIENT_VISIT_URL = "client/updateclientvisit"

    body = {
        "VisitId": v['Id'],
        "Makeup": False,
        "SignedIn": True,
        "SendEmail": False,
        "Test": IS_TEST
    }

    try:
        r = requests.post(url = MBO_BASE_URL + GET_UPDATE_CLIENT_VISIT_URL, data = json.dumps(body), headers=headers )

        r.raise_for_status()

    except HTTPError as http_err:
        logging.error(f'HTTP error: {http_err}')
        return http_err

    except Exception as err:
        logging.error(f'Error updating client visit: {err}')
        return err

    else:
        event = r.json()
        j = json.dumps(event)
        update_client_visit_data = json.loads(j)

        print(f'successfully updated class visit: {update_client_visit_data}')
        return update_client_visit_data


def auto_checkin(event, context):

    print('Auto Checkin')

    try:

        gate_event = json.loads(event['body'])

        TOKEN = get_token(gate_event)

        # print(f"Auto Checking \n\nEvent\n{gate_event}")

        headers = {
            "Api-Key": 'MBO_API_KEY',
            "Content-Type": CONTENT_TYPE,
            "SiteId": 'MBO_SITE_ID',
            "authorization": TOKEN
        }
    
        client_visits_data = get_client_visits(gate_event, headers)

        for v in client_visits_data['Visits']:
            # print(f'{v}\n\n')

            if v['ClassId'] != 0 and int(v['LocationId']) == int(gate_event['l_tpid']) and v['LocationId'] != 4:
                willCheckIn = isWithInCheckInWindow(v['StartDateTime'], v['EndDateTime'], gate_event['a_timestamp'])
                print('willCheckIn: ',willCheckIn)

                if willCheckIn:
                    print('auto_checkin to: ',v['Name'])
                    update_client_visit(v, headers)

    except HTTPError as http_err:

        logging.error(f'HTTP error code: {http_err}')
        response = {
            "statusCode": http_err.response.status_code
        }

    except Exception as err:

        logging.error(f'Error getting client visits: {err}')
        response = {
            "statusCode": 500
        }

    else:

        response = {
            "statusCode": 200
        }

    return response
    




