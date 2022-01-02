import json
import os
from twilio.rest import Client
from urllib.parse import urlencode, quote_plus

class Constants:
    FROM_NUMBER = os.environ["TWILIO_FROM_NUMBER"]
    TWILIO_AUTH_TOKEN = os.environ['TWILIO_AUTH_TOKEN']
    TWILIO_ACCOUNT_SID = os.environ['TWILIO_ACCOUNT_SID']


def format_phone(n):
    try:
        n = str(n)
        n = n.replace(' ','')

        if len(n) == 10 and n[:1] == '0':
            n = n[1:]

        if len(n) == 9 and n[:1] == '4':
    #         print(n)
            n = '+61' + n
    #         print(n)
    except:
        print('Error format_phone')
    #     print(n)
    return n

def hello(event, context):

    print(event)
    

    # send_sms('0413506306','Sent from sls')

    body = {
        "message": "Go Serverless v1.0! Your function executed successfully!",
    }

    response = {
        "statusCode": 200,
        "body": json.dumps(body)
    }

    return response


def execute_send_sms(TO_NUMBER, MESSAGE_BODY):
    try:
        CLIENT = Client(Constants.TWILIO_ACCOUNT_SID, Constants.TWILIO_AUTH_TOKEN)

        message = CLIENT.messages.create(
                             body=MESSAGE_BODY,
                             from_=Constants.FROM_NUMBER,
                             to=TO_NUMBER
                         )
        print(f"Message:\nTime:\t\t{message.date_created.strftime('%Y-%m-%d %H:%M %Z')}\nTO_NUMBER:\t{TO_NUMBER}\nSID:\t\t{message.sid}\nStatus:\t\t{message.status}")

    except Exception as ex:
        print(f"Error - execute_send_sms({TO_NUMBER}, {MESSAGE_BODY})")
    finally:
        return
    

def send_sms(event, context):

    print(event)
    
    try:

        EVENT_BODY = json.loads(event['body'])
        print(EVENT_BODY)

        TO_NUMBER = EVENT_BODY['to_number']
        MESSAGE_BODY = EVENT_BODY['message']
        execute_send_sms(TO_NUMBER, MESSAGE_BODY)
        
    except Exception as ex:
        print(f"Error sending SMS: {ex}")

    finally:
        body = {
        "message": "send_sms() function executed successfully!",
        }

        response = {
            "statusCode": 200,
            "body": json.dumps(body)
        }

        return response

def receive_sms(event, context):

    body = {
        "message": "receive_sms() function executed successfully!",
    }

    response = {
        "statusCode": 200,
        "body": json.dumps(body)
    }

    try:
        print(event)
         EVENT_BODY = urllib.parse.parse_qs(event['body'])
         print(EVENT_BODY)

         MESSAGE_BODY = "Thanks for replying! Your enquiry has been sent to our office crew who will be in touch with you shortly. Hang tight!"
         return f'<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><Message><Body>{MESSAGE_BODY}</Body></Message></Response>'
         TO_NUMBER = EVENT_BODY['From'][0]
         execute_send_sms(TO_NUMBER, MESSAGE_BODY)

    except Exception as ex:
        print(f"Error - receiving sms: {ex}")
        body = {
            "message": f"Error - receiving sms: {ex}",
        }

        response = {
            "statusCode": 418,
            "body": json.dumps(body)
        }

    finally:
        return response
         



def receive_voice_call(event, context):

    body = {
        "message": "receive_voice_call() function executed successfully!",
    }

    response = {
        "statusCode": 200,
        "body": json.dumps(body)
    }

    try:
        print(event)
        # EVENT_BODY = urllib.parse.parse_qs(event['body'])
        # print(EVENT_BODY)

        # MESSAGE_BODY = "Thanks for calling! Your enquiry has been sent to our office crew who will be in touch with you shortly. Hang tight!"
        # TO_NUMBER = EVENT_BODY['From'][0]
        # execute_send_sms(TO_NUMBER, MESSAGE_BODY)

    except Exception as ex:
        print(f"Error - receiving voice call: {ex}")
        body = {
            "message": f"Error - receiving voice call: {ex}",
        }

        response = {
            "statusCode": 418,
            "body": json.dumps(body)
        }

    finally:
        return response


def send_referral_form(event, context):


    body = {
        "message": "send_referral_form() function executed successfully!",
    }

    response = {
        "statusCode": 200,
        "body": json.dumps(body)
    }

    FORM_IDS = {
         "CAMPAIGN_OFFER" : "105183652",
         "GYM_LOCATION": "105183653",
         "MEMBERSHIP_CONSULTANT_SH" : "105183654",
         "MEMBERSHIP_CONSULTANT_NT" : "105183655",
         "MEMBERSHIP_CONSULTANT_MK" : "105183656",
         "MEMBERSHIP_CONSULTANT_BK" : "105183657",
         "MEMBER_NAME" : "105183658",
         "MEMBER_PHONE" : "105183659",
         "FS_FORM_ID" : "FormID",
         "FS_UNIQUE_ID" : "UniqueID"
    }

    try:
        # print(event)
        EVENT_BODY = json.loads(event['body'])
        print(EVENT_BODY)
        
        try:
            if EVENT_BODY[FORM_IDS['MEMBERSHIP_CONSULTANT_SH']] is not None:
                REFERAL_SOURCE = EVENT_BODY[FORM_IDS['MEMBERSHIP_CONSULTANT_SH']]
            if EVENT_BODY[FORM_IDS['MEMBERSHIP_CONSULTANT_NT']] is not None:
                REFERAL_SOURCE = EVENT_BODY[FORM_IDS['MEMBERSHIP_CONSULTANT_NT']]
            if EVENT_BODY[FORM_IDS['MEMBERSHIP_CONSULTANT_MK']] is not None:
                REFERAL_SOURCE = EVENT_BODY[FORM_IDS['MEMBERSHIP_CONSULTANT_MK']]
            if EVENT_BODY[FORM_IDS['MEMBERSHIP_CONSULTANT_BK']] is not None:
                REFERAL_SOURCE = EVENT_BODY[FORM_IDS['MEMBERSHIP_CONSULTANT_BK']]

            if 'Bunker' in EVENT_BODY[FORM_IDS['GYM_LOCATION']]:
                GYM_LOCATION = 'The Bunker'
            else:
                GYM_LOCATION = EVENT_BODY[FORM_IDS['GYM_LOCATION']]

            QUERY_PARAMS_PAYLOAD = {'referral_source': REFERAL_SOURCE, 'gymName':EVENT_BODY[FORM_IDS['GYM_LOCATION']]}
            QUERY_PARAMS = urlencode(QUERY_PARAMS_PAYLOAD, quote_via=quote_plus)

            URL = f"https://www.fitnessplayground.com.au/refer-a-friend?{QUERY_PARAMS}"

        except Exception as ex:
            print(f"Error getting Query Params: {ex}")

        try:
            if EVENT_BODY[FORM_IDS['MEMBER_NAME']] is not None:
                MEMBER_NAME = f" {EVENT_BODY[FORM_IDS['MEMBER_NAME']]['first']}"
        except:
            MEMBER_NAME = ""

        MESSAGE_BODY = f"""Hey{MEMBER_NAME},\n\nAs promised, here’s the link to invite a friend & get 2-weeks free!\n\nClick here {URL}. Please note: This link will expire in 48 hours."""

        execute_send_sms(EVENT_BODY[FORM_IDS['MEMBER_PHONE']], MESSAGE_BODY)

    except Exception as ex:
        print(f"Error - sending web referral form: {ex}")
        body = {
            "message": f"Error - sending web referral form: {ex}",
        }

        response = {
            "statusCode": 418,
            "body": json.dumps(body)
        }

    finally:
        return response





