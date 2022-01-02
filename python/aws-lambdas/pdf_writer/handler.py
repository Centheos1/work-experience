import logging as logger
import json
import pdfkit
import pandas as pd
import requests
import base64
import re
import smtplib
from os.path import basename
from email.mime.application import MIMEApplication
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email.mime.image import MIMEImage
from email.utils import COMMASPACE, formatdate
from email import encoders
import os
import copy
import datetime
import boto3

from termsAndConditions import *

# Reference 
# https://github.com/Centheos1/Lambda-PDF-Generator
# https://blog.richardkeller.net/building-a-pdf-generator-on-aws-lambda-with-python3-and-wkhtmltopdf/
# https://medium.com/@crespo.wang/how-to-generate-pdf-in-aws-lambda-c92477068cf6
# https://tech.musement.com/create-a-pdf-service-with-aws-lambda/


# ###########################################################################
# ENVIRONMENT                                                               #
# ###########################################################################
# os.environ["PATH"] = os.environ["PATH"] + ":" + os.environ.get("LAMBDA_TASK_ROOT") + "/bin"
os.environ["PATH"] = f"""{os.environ["PATH"]}:{os.environ.get("LAMBDA_TASK_ROOT")}/bin"""

SQS_CLIENT = boto3.client('sqs')


# ###########################################################################
# DEFINITIONS.                                                              #
# ###########################################################################
class Constants:
    IS_TEST = False
    LOGOS_FP_HEADER_URL = "https://dmz5utdoc4n0a.cloudfront.net/img/logos/fp_horizontal_black.png"
    LOGOS_BK_HEADER_URL = "https://dmz5utdoc4n0a.cloudfront.net/img/logos/bunker_horizontal_black.png"
    LOGO_FP = "https://dmz5utdoc4n0a.cloudfront.net/img/logos/FP_Icon.png"
    LOGO_BK = "https://dmz5utdoc4n0a.cloudfront.net/img/logos/TheBunker_Icon.png"

    ASSET_WELCOME_EMAIL_BANNER_FP = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/01-header-banner-fp.png"
    ASSET_WELCOME_EMAIL_BANNER_BK = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/01-header-banner-bunker.png"
    ASSET_WELCOME_EMAIL_GOLDEN_RULES = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/02-three-golden-rules.png"
    ASSET_WELCOME_EMAIL_MEMBERSHIP = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/03-your-membership.png"
    ASSET_WELCOME_EMAIL_PT = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/your-personal-trainer.png"
    ASSET_WELCOME_EMAIL_FRIEND = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/04-train-with-a-friend.png"
    ASSET_WELCOME_EMAIL_PLAY = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/how-to-book-your-first-class.png"
    ASSET_WELCOME_EMAIL_APP_IOS = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/apple.png"
    ASSET_WELCOME_EMAIL_APP_ANDRIOD = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/Google.png"
    ASSET_WELCOME_EMAIL_SOCIAL = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/05-train-and-socialise-with-us.png"
    ASSET_WELCOME_EMAIL_INSTAGRAM = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/07-instagram.png"
    ASSET_WELCOME_EMAIL_FACEBOOK = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/06-facebook.png"
    ASSET_WELCOME_EMAIL_SIGNATURE = "https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/08-justin-and-serra.png"

    URL_FP = "https://www.fitnessplayground.com.au"
    URL_BK = "https://www.thebunkergym.com.au"
    URL_STR_FP = "FITNESSPLAYGROUND.COM.AU"
    URL_STR_BK = "THEBUNKERGYM.COM.AU"
    ICONS_TICK = "https://dmz5utdoc4n0a.cloudfront.net/img/icons/tick+icon.png"
    AUTH_SPLIT_CHARACTER = ':'
    SERVER = 'smtp.gmail.com'
    PORT = 465
    SMTP_USERNAME = None
    SMTP_PASSWORD = None
    ERROR_NOTIFICATION_EMAIL_CREDENTIALS = os.getenv('ERROR_NOTIFICATION_EMAIL_CREDENTIALS')
    CONTENT_TYPE = "application/json"
    SOURCE_TEST_BASE_URL = 'https://test.fitnessplayground.com.au/v1/'
    SOURCE_BASE_URL = 'https://source.fitnessplayground.com.au/v1/'
    SOURCE_HANDLE_MANUAL_SUBMISSION_URL = "source/manualSubmission"
    SOURCE_AUTH_HEADER = os.getenv('SOURCE_AUTH_HEADER')
    SOURCE_UID = os.getenv('UID')

    FP_SH_EMAIL_ADDRESS = 'surryhills@fitnessplayground.com.au'
    FP_NT_EMAIL_ADDRESS = 'newtown@fitnessplayground.com.au'
    FP_MK_EMAIL_ADDRESS = 'marrickville@fitnessplayground.com.au'
    FP_BK_EMAIL_ADDRESS = 'train@thebunkergym.com.au'
    FP_PLAY_EMAIL_ADDRESS = 'play@fitnessplayground.com.au'

    PAR_Q_URL = 'https://fitnessplayground.formstack.com/forms/par_q_v2'

    MBO_BASE_URL = "https://api.mindbodyonline.com/public/v6/"
    MBO_GET_CLIENTS_URL = "client/clients"
    CONTENT_TYPE = "application/json"
    MBO_SITE_ID = "152065"
    MBO_API_KEY_FP_SOURCE = os.getenv('MBO_API_KEY_FP_SOURCE')
    MBO_CREDENTIALS = os.getenv('MBO_CREDENTIALS')
    MBO_USER_NAME = None
    MBO_PASSWORD = None

class CommunicationsStatus:
    PDF_ERROR = ("PDF_ERROR", "Contract PDF Failed",418)
    PDF_EMAIL_ERROR = ("PDF_EMAIL_ERROR", "Contract PDF failed to send to client",418)
    PDF_UPLOAD_ERROR = ("PDF_UPLOAD_ERROR", "Contract PDF failed to upload to Mindbody Online",418)


# ###########################################################################
# HELPERS.                                                                  #
# ###########################################################################
def cleanCamelCase(data):
    s1 = re.sub('(.)([A-Z][a-z]+)', r'\1 \2', data)
    return re.sub('([a-z0-9])([A-Z])', r'\1 \2', s1).title()

def split_hash(x):
    return (x.split(" # "))

# Helper to format date
def make_date_pretty(d):
    if d is None:
        return d
    try:
        d = pd.to_datetime(d)
        day = d.strftime('%A') # full day of week
        month = d.strftime('%B') # full month
        date = d.strftime('%d') # date day
        year = d.strftime('%Y') # date year

        pretty = (f"{day}, {date} {month} {year}")
        return pretty
    except Exception as ex:
        print(f"Error - make_date_pretty {ex}")
        return d

def make_phone_pretty(p):
    
    try:
        if p[:3] == '+61':
            phone = '0' + p[3:]
            if len(phone) == 10:
                phone = phone[:4] + ' ' + phone[4:7] + ' ' + phone[7:]
            return phone
        elif p[:2] == '61':
            phone = '0' + p[2:]
            if len(phone) == 10:
                phone = phone[:4] + ' ' + phone[4:7] + ' ' + phone[7:]
            return phone
        else:
            return p
    except Exception as ex:
        print(f"Error - make_phone_pretty {ex}")
        return p

def decode_email_credentials(EMAIL_CREDENTIALS):
    decoded_credentials = base64.b64decode(EMAIL_CREDENTIALS).decode("utf-8")
    split = decoded_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
    Constants.SMTP_USERNAME = split[0]
    Constants.SMTP_PASSWORD = split[1]


def get_reply_to_address(locationId):
    if locationId is None:
        return Constants.FP_PLAY_EMAIL_ADDRESS
    
    try:
        x = str(locationId)
        switcher={
            '1' : Constants.FP_SH_EMAIL_ADDRESS,
            '2' : Constants.FP_NT_EMAIL_ADDRESS,
            '3' : Constants.FP_MK_EMAIL_ADDRESS,
            '5' : Constants.FP_BK_EMAIL_ADDRESS
        }
        return switcher.get(x, Constants.FP_PLAY_EMAIL_ADDRESS)
        
    except Exception as ex:
        print(f"Error - [get_reply_to_address]: {ex}")
        return Constants.FP_PLAY_EMAIL_ADDRESS


# ###########################################################################
# SOURCE                                                                    #
# ###########################################################################
def source_post_request(URL, BODY):
    
    try:
        if Constants.IS_TEST:
            BASE_URL = Constants.SOURCE_TEST_BASE_URL
            print(f"POSTING AS A TEST: {BASE_URL}")
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
            print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP type: {type(http_err)}\nHTTP message: {http_err.response.reason}')

        except Exception as err:
            print(f'Error {URL}: {err}')

        else:
            print(r)
            if r.status_code != 204:
                event = r.json()
                j = json.dumps(event)
                data = json.loads(j)

        return data
    
    except Exception as e:
        print(f"Error [source_post_request]: {e}")
        return None


# ###########################################################################
# MINDBODY ONLINE                                                           #
# ###########################################################################
def decode_mbo_credentials():
    decoded_credentials = base64.b64decode(Constants.MBO_CREDENTIALS).decode("utf-8")
    split = decoded_credentials.split(":")
    Constants.MBO_USER_NAME = split[0]
    Constants.MBO_PASSWORD = split[1]


def upload_client_document_v2(docData, mbo_client, mbo_credentials, key, filepath):

    isSuccess = False

    try:
        with open(filepath, "rb") as pdf_file:
                encoded_string = base64.b64encode(pdf_file.read())

        UPLOAD_CLIENT_DOCUMENT_URL = "https://api.mindbodyonline.com/public/v6/client/uploadclientdocument"
        CONTENT_TYPE = "application/json"

        s = str(encoded_string)
        split = s.split("'")

        data = {
            'ClientId': mbo_client['Id'],
            "File": {
                'FileName': key,
                'MediaType': 'application/pdf',
                'Buffer': split[1]
            }
        }

        r = requests.post(url = UPLOAD_CLIENT_DOCUMENT_URL, data = json.dumps(data), headers=mbo_credentials )
        print(r.json())
        r.raise_for_status()

    except HTTPError as http_err:
        print(f'HTTP error: {http_err} - {r.json()}')
        isSuccess = False
        # return False

    except Exception as err:
        print(f'Error updating client visit: {err}')
        isSuccess = False
        # return False

    else:
        print(r)
        event = r.json()
        j = json.dumps(event)
        upload_client_document_data = json.loads(j)

        print(f'successfully uploaded document: {upload_client_document_data}')
        isSuccess = True
        # return True

    finally:
        return isSuccess

def get_mbo_token():

    USERTOKEN_URL = "usertoken/issue"

    body = {
        "Username": Constants.MBO_USER_NAME,
        "Password": Constants.MBO_PASSWORD
    }

    headers = {
        "Api-Key": Constants.MBO_API_KEY_FP_SOURCE,
        "Content-Type": Constants.CONTENT_TYPE,
        "SiteId": Constants.MBO_SITE_ID
    }

    API_ENDPOINT = Constants.MBO_BASE_URL + USERTOKEN_URL

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
        print(r)
        j = json.dumps(event)
        # print(j)
        mbo_auth_data = json.loads(j)

        Constants.MBO_TOKEN = mbo_auth_data['AccessToken']
        
        Constants.MBO_HEADERS = {
            "Api-Key": Constants.MBO_API_KEY_FP_SOURCE,
            "Content-Type": Constants.CONTENT_TYPE,
            "SiteId": Constants.MBO_SITE_ID,
            "authorization": Constants.MBO_TOKEN
        }
        
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


def mbo_get_client(data):
    print(f"Get MBO Client")
    
    MBO_CLIENT = None
    
    try:

        PARAMS = { 'SearchText' : data['firstName'] + " " + data['lastName'] }

        response = mbo_get_request(Constants.MBO_GET_CLIENTS_URL, PARAMS)
    
        if response is not None:
            print(f"{response['PaginationResponse']['TotalResults']} results returned from MBO")

            if response['PaginationResponse']['TotalResults'] == 0:
                print("No Clients returned from MBO")
                return None

            elif response['PaginationResponse']['TotalResults'] == 1:
                MBO_CLIENT = response['Clients'][0]
            else:
                for c in response['Clients']:
                    if c['MobilePhone'] is not None and data['phone'] is not None and c['MobilePhone'].replace(' ','')[-9:] == data['phone'].replace(' ','')[-9:]:
                        print(f"{c['FirstName']} {c['LastName']} => {c['MobilePhone'][-9:]} == {data['phone'][-9:]}")
                        MBO_CLIENT = c

        else:
            print("Error returned from MBO GetClients")
            return None
        
        return MBO_CLIENT
    except Exception as ex:
        print(f"Error [mbo_get_client]: {ex}")
    


# ###########################################################################
# EMAIL.                                                                    #
# ###########################################################################
def send_enrolment_email(docData, gym_details, key, filepath):
    print(f'send_enrolment_email: {filepath} :: {key}')

    try:

        header = 'Fitness Playground ' + docData['gymName']
        logo = Constants.LOGO_FP
        url = Constants.URL_FP
        url_str = Constants.URL_STR_FP
        company_name = 'Fitness Playground'
        
        if int(docData['locationId']) == 5:
            header = 'The Bunker Gym'
            logo = Constants.LOGO_BK
            url = Constants.URL_BK
            url_str = Constants.URL_STR_BK
            company_name = 'Bunker Gym'


        msg = MIMEMultipart('alternative')

        msg['From'] = Constants.SMTP_USERNAME

        msg['To'] = docData['email']
        msg['Subject'] = "Welcome to {}.".format(header)
        msg['Date'] = formatdate(localtime=True)


        text = """\
        Hi {},

        It’s official, welcome to the {} family!
        
        As promised, you will find your Membership Summary attached below with your:

        1. Signed Membership Details 

        2. Signed Payment Authorisation

        3. Membership Terms & Conditions
        
        If you are unsure of anything in the attached documentation or need some help to start training please feel free to reply to this email. 
        
        We are so excited to be by your side on your fitness journey and hope to help you reach your goals!
        
        See you soon,
        {}
        Club Manager | {}
        """.format(company_name, docData['firstName'], gym_details['clubManager']['name'], docData['gymName'])

        html = f"""\
        <html>
            <head></head>
            <body>
                
                <p>Hi {docData['firstName']},</p>
                
                <p>It’s official, welcome to the {company_name} family!</p>

                <p>As promised, you will find your Membership Summary attached below with your:</p>

                <ol>
                    <li>Signed Membership Details</li>

                    <li>Signed Payment Authorisation</li>
                    
                    <li>Membership Terms & Conditions</li>
                </ol>
                
                <p>If you are unsure of anything in the attached documentation or need some help to start training please feel free to reply to this email.</p>
                
                <p>We are so excited to be by your side on your fitness journey and hope to help you reach your goals!</p>

                <p>See you soon,</p>

                <table style="margin: 10px;">
                  <tr>
                    <td rowSpan="3" style="padding: 5px 0;width: 60px;"><img src="{logo}" alt="Fitness Playground"></td>
                    <td style="color: #fcb120; padding-left: 10px;font-size: 1.2em; vertical-align: centre;"><b>{gym_details['clubManager']['name']}</b></td>
                  </tr>
                  <tr>
                      <td style="padding-left: 10px; vertical-align: centre;"><b>{docData['gymName']}</b></td>
                  </tr>
                  <tr>
                      <td style="padding-left: 10px; vertical-align: centre;"><a href="{url}">{url_str}</a></td>
                  </tr>
                </table>

            </body>
        </html>
        """


        part1 = MIMEText(text, 'plain')
        part2 = MIMEText(html, 'html')

        msg.attach(part1)
        msg.attach(part2)


        part = MIMEBase('application', 'octet-stream')
        part.set_payload(open(filepath, "rb").read())
        encoders.encode_base64(part)
        part.add_header('Content-Disposition', "attachment; filename= %s" % key)

        msg.attach(part)

        server_ssl = smtplib.SMTP_SSL(Constants.SERVER, Constants.PORT)
        server_ssl.login(Constants.SMTP_USERNAME, Constants.SMTP_PASSWORD)
        text = msg.as_string()
        server_ssl.sendmail(Constants.SMTP_USERNAME, docData['email'], text)
        server_ssl.quit()
        print('Email sent :-)')
        return True
    except Exception as ex:  
        print(f'Error - [send_enrolment_email]: {ex}')
        return False


def send_enrolment_email_v2(docData, gym_details, PT_TRACKER_ENTITY, key, filepath):
    print(f'send_enrolment_email_v2: {filepath} :: {key}')

    try:

        header = 'Fitness Playground ' + docData['gymName']
        logo = Constants.ASSET_WELCOME_EMAIL_BANNER_FP
        url = Constants.URL_FP
        url_str = Constants.URL_STR_FP
        company_name = 'Fitness Playground'
        
        if int(docData['locationId']) == 5:
            header = 'The Bunker Gym'
            logo = Constants.ASSET_WELCOME_EMAIL_BANNER_BK
            url = Constants.URL_BK
            url_str = Constants.URL_STR_BK
            company_name = 'Bunker Gym'


        msg = MIMEMultipart('alternative')

        msg['From'] = Constants.SMTP_USERNAME

        msg['To'] = docData['email']
        msg['Subject'] = "Welcome to {}.".format(header)
        msg['Date'] = formatdate(localtime=True)

        REPLY_TO_EMAIL_ADDRESS = get_reply_to_address(docData['locationId'])

        msg.add_header('reply-to', REPLY_TO_EMAIL_ADDRESS)


        text = """\
        Hi {},

        It’s official, welcome to the {} family!
        
        As promised, you will find your Membership Summary attached below with your:

        1. Signed Membership Details 

        2. Signed Payment Authorisation

        3. Membership Terms & Conditions
        
        If you are unsure of anything in the attached documentation or need some help to start training please feel free to reply to this email. 
        
        We are so excited to be by your side on your fitness journey and hope to help you reach your goals!
        
        See you soon,
        {}
        Club Manager | {}
        """.format(company_name, docData['firstName'], gym_details['clubManager']['name'], docData['gymName'])

        top = f"""\
        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <title>Welcome Email</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            </head>
            <body style="margin: 10; padding: 0; font-family: Arial, sans-serif; font-size: 1em; line-height: 1.2em;">

                <div style="width: 100%; background-color: #000">
                    <table style="margin-left: auto; margin-right: auto;">
                        <tr>
                            <td><img src={logo} width="auto" height="65rem" style="display: block;" /></td>
                        </tr>
                    </table>
                </div>

                <p>WELCOME {docData['firstName']}!</p>

                <p>I'd like to introduce myself, Justin, and my business partner Serra - we are the founders of Fitness Playground. Welcome to the FP family!</p>

                <p>We try not to be a typical gym and hope you see that in all we do. With that, we'd love for you to meet our 3 Golden Rules:</p>

                <table style="margin-left: auto; margin-right: auto;">
                    <tr>
                        <td><img src="{Constants.ASSET_WELCOME_EMAIL_GOLDEN_RULES}" width="auto" height="250rem" /></td>
                    </tr>
                </table>
                

                <p>We know the gym can be intimidating, so don't be afraid to seek some extra guidance. You're not just joining a gym, you're joining a community! Make sure you're keeping your community, and if you're not sure how to, check out our safety policies <a href="https://dmz5utdoc4n0a.cloudfront.net/img/FP+Welcome/Play+it+Safe.png">here</a>.</p>
            
                <p>Oh, and we love feedback, if there's anything you see/hear/feel, we welcome it with open arms.</p>

                <p>Before you go, there are five simple things we're going to cover in your first email with us. It'll be a quick one:</p>

                <table>
                    <tr>
                        <td><img src="{Constants.ASSET_WELCOME_EMAIL_MEMBERSHIP}" width="auto" height="65rem" /></td>
                    </tr>
                </table>

                <p>A summary of your membership is attached to this email.</p>"""
# ---- PT
        pt = ""
        if docData['trainingStarterPack'] is not None and docData['trainingStarterPack'].replace(' ','').lower() in ['face-to-face', 'coaching', 'starterpack','externalpersonaltraining']:
            pt_tracker_id = ''
            if PT_TRACKER_ENTITY is not None:
                pt_tracker_id = str(PT_TRACKER_ENTITY['id'])

            PAR_Q_LINK = Constants.PAR_Q_URL + '?pt_tracker_id=' + pt_tracker_id + '&name-first=' + docData['firstName'] + '&name-last=' + docData['lastName'] + '&Home+Club=' + docData['gymName'] + '&Your+Coach+is=' + docData['personalTrainerName']
            pt = f"""\
                <table>
                    <tr>
                        <td><img src="{Constants.ASSET_WELCOME_EMAIL_PT}" width="auto" height="65rem" /></td>
                    </tr>
                </table>

                <p><a href='{PAR_Q_LINK}'>Click Here</a> to complete your personal questionnaire for your coach. This will help us to give you the best possible service.</p>"""
# ---- PT

        vip_pass = f"""
            <table>
                <tr>
                    <td><img src="{Constants.ASSET_WELCOME_EMAIL_FRIEND}" width="auto" height="65rem" /></td>
                </tr>
            </table>

            <p>Collect 3 x VIP passes to give to your friends! Just visit us in the club office when you're in next to pick them up. Oh, and if they join, you'll get two-weeks free on your membership!</p>"""


# --- Play
        play = ""
        if docData['gymOrPlay'] is not None and docData['gymOrPlay'].lower() == 'play':
            play = f"""\
                <table>
                    <tr>
                        <td><img src="{Constants.ASSET_WELCOME_EMAIL_PLAY}" width="auto" height="65rem" align="left" /></td>
                    </tr>
                </table>

                <p>1. Download the Fitness Playground app here:</p>

                <table >
                    <tr>
                        <td><a href="https://apps.apple.com/au/app/fitness-playground/id1018929246"><img src="{Constants.ASSET_WELCOME_EMAIL_APP_IOS}" width="auto" height="65rem" ></a></td>
                        <td><a href="https://play.google.com/store/apps/details?id=com.fitnessmobileapps.fitnessplayground&hl=en"><img src="{Constants.ASSET_WELCOME_EMAIL_APP_ANDRIOD}" width="auto" height="65rem" ></a></td>
                    </tr>
                </table>

                <p>2. Sign in using your email and create your password.</p>

                <p>3. Choose your location and book yourself into the class.</p>

                <p>If you can't make a class. please cancel your booking on the app at least 24 hours before the class to ensure you are not charged for missing the class.</p>"""
# --- Play

        bottom = f"""\
                <table>
                    <tr>
                        <td><img src="{Constants.ASSET_WELCOME_EMAIL_SOCIAL}" width="auto" height="70rem" /></td>
                    </tr>
                </table>

                <p>If you've got 10 seconds, you can visit our social pages and give us a follow.</p>

                <table>
                    <tr>
                        <td width="100px"><a href="https://www.instagram.com/fitnessplaygroundaus/"><img src="{Constants.ASSET_WELCOME_EMAIL_INSTAGRAM}" width="auto" height="70rem" /></a></td>
                        <td><a href="https://www.facebook.com/fitnessplaygroundhq/"><img src="{Constants.ASSET_WELCOME_EMAIL_FACEBOOK}" width="auto" height="70rem" /></a></td>
                    </tr>
                </table>

                <p>If there is anything else you need, please reach out to your new club team and they will happily assist you.</p>

                <p>See you around!<br />Justin & Serra</p>

                <table>
                    <tr>
                        <td><img src="{Constants.ASSET_WELCOME_EMAIL_SIGNATURE}" width="auto" height="100rem" /></td>
                    </tr>
                </table>

                <p><b>Founders of Fitness Playground & The Bunker Gyms</b></p>

            </body>
         </html>
        """

        html = top + pt + vip_pass + play + bottom

        part1 = MIMEText(text, 'plain')
        part2 = MIMEText(html, 'html')

        msg.attach(part1)
        msg.attach(part2)


        part = MIMEBase('application', 'octet-stream')
        part.set_payload(open(filepath, "rb").read())
        encoders.encode_base64(part)
        part.add_header('Content-Disposition', "attachment; filename= %s" % key)

        msg.attach(part)

        server_ssl = smtplib.SMTP_SSL(Constants.SERVER, Constants.PORT)
        server_ssl.login(Constants.SMTP_USERNAME, Constants.SMTP_PASSWORD)
        text = msg.as_string()
        server_ssl.sendmail(Constants.SMTP_USERNAME, docData['email'], text)
        server_ssl.quit()
        print('Email sent :-)')
        return True
    except Exception as ex:  
        print(f'Error - [send_enrolment_email]: {ex}')
        return False



def send_fp_coach_enrolment_email(docData, gym_details, key, filepath):
    print(f'send_fp_coach_enrolment_email: {filepath} :: {key}')

    try:

        header = 'Fitness Playground ' + docData['gymName']
        logo = Constants.LOGO_FP
        url = Constants.URL_FP
        url_str = Constants.URL_STR_FP
        company_name = 'Fitness Playground'
        
        if int(docData['locationId']) == 5:
            header = 'The Bunker Gym'
            logo = Constants.LOGO_BK
            url = Constants.URL_BK
            url_str = Constants.URL_STR_BK
            company_name = 'Bunker Gym'


        msg = MIMEMultipart('alternative')

        msg['From'] = Constants.SMTP_USERNAME

        msg['To'] = docData['email']
        msg['Subject'] = "Your Signed Contract with {}.".format(header)
        msg['Date'] = formatdate(localtime=True)


        text = f"""\
Hi {docData['firstName']},

It's official! 
{docData['personalTrainerName']} will be joining you on your fitness journey (and I can assure you that you're in very good hands!)

As promised, you will find your Personal Training Summary attached below with your:

1. Signed Personal Training Details

2. Signed Payment Authorisation

3. Membership Terms & Conditions

If you are unsure of anything in the attached documentation please feel free to reply to this email.

I'm excited to see you smash your goals in the coming weeks! 

See you around,
{gym_details['clubManager']['name']}"""


        html = f"""\
        <html>
            <body>
                <p>Hi {docData['firstName']},</p>

                <p>It’s official!</p>

                <p>{docData['personalTrainerName']} will be joining you on your fitness journey (and I can assure you that you're in very good hands!)</p>

                <p>As promised, you will find your Personal Training Summary attached below with your:</p>

                <ol>
                    <li>Signed Personal Training Details</li>

                    <li>Signed Payment Authorisation</li>

                    <li>Membership Terms & Conditions</li>
                </ol>

                <p>If you are unsure of anything in the attached documentation please feel free to reply to this email.</p>

                <p>I'm excited to see you smash your goals in the coming weeks!</p>

                <p>See you around,</p>

                <table style="margin: 5px;">
                  <tr>
                    <td rowSpan="3" style="padding: 5px 0;width: 60px;"><img src="{logo}" alt="Fitness Playground"></td>
                    <td style="color: #fcb120; padding-left: 10px;font-size: 1.2em; vertical-align: centre;"><b>{gym_details['clubManager']['name']}</b></td>
                  </tr>
                  <tr>
                      <td style="padding-left: 10px; vertical-align: centre;"><b>{docData['gymName']}</b></td>
                  </tr>
                  <tr>
                      <td style="padding-left: 10px; vertical-align: centre;"><a href="{url}">{url_str}</a></td>
                  </tr>
                </table>

            </body>
        </html>
        """

        part1 = MIMEText(text, 'plain')
        part2 = MIMEText(html, 'html')

        msg.attach(part1)
        msg.attach(part2)


        part = MIMEBase('application', 'octet-stream')
        part.set_payload(open(filepath, "rb").read())
        encoders.encode_base64(part)
        part.add_header('Content-Disposition', "attachment; filename= %s" % key)

        msg.attach(part)

#         server_ssl = smtplib.SMTP_SSL('smtp.gmail.com', 465)
        server_ssl = smtplib.SMTP_SSL(Constants.SERVER, Constants.PORT)
        server_ssl.login(Constants.SMTP_USERNAME, Constants.SMTP_PASSWORD)
        text = msg.as_string()
        server_ssl.sendmail(Constants.SMTP_USERNAME, docData['email'], text)
        server_ssl.quit()
        print('Email sent :-)')
        return True
    except Exception as ex:  
        print(f'Error - [send_fp_enrolment_email]: {ex}')
        return False


def send_error_notification(message = None):
    
    print("send_error_notification")

    try:
        
        decoded_credentials = base64.b64decode(Constants.ERROR_NOTIFICATION_EMAIL_CREDENTIALS).decode("utf-8")
        split = decoded_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
        USERNAME = split[0]
        PASSWORD = split[1]
        
        msg = MIMEMultipart('alternative')
        msg['From'] = USERNAME
        msg['To'] = USERNAME
        msg['Subject'] = "Error - Enrolment PDF Writer Lambda {}.".format(formatdate(localtime=True))
        msg['Date'] = formatdate(localtime=True)
        
        text = f"""\
    Message: \n{message}
        """
        part = MIMEText(text, 'plain')
        msg.attach(part)

    #     server_ssl = smtplib.SMTP_SSL('smtp.gmail.com', 465)
        server_ssl = smtplib.SMTP_SSL(Constants.SERVER, Constants.PORT)
        server_ssl.login(USERNAME, PASSWORD)
        text = msg.as_string()
        server_ssl.sendmail(USERNAME, USERNAME, text)
        server_ssl.quit()
        print('Error notification sent :-(')

    except Exception as ex:
        print(f'Error [send_error_notification]: {ex}')

    finally:
        return



# ###########################################################################
# PDF BUILDERS                                                              #
# ###########################################################################

# ################################################################## Build PDF Enrolment v2
def generate_enrolment_pdf(docData, key, filepath):

    try:
        if int(docData['locationId']) == 5:
            title = f"""<img src="{Constants.LOGOS_BK_HEADER_URL}" alt="The Bunker Gym">"""
        else:
            title = f"""<img src="{Constants.LOGOS_FP_HEADER_URL}" alt="Fitness Playground">"""

        topLine = """
            <style>
                body {{
                    font-family: 'Open Sans', sans-serif;
                    margin: 20px auto;
                    width: 100%;
                    text-align: center;
                    border: 1px solid #fff;
                    padding: 10px;
                    box-sizing: border-box;
                    overflow-y: auto;
                }}

                h1 {{
                    color: #fcb120;
                    text-transform: uppercase;
                    text-align: center;
                }}

                h2,h3,h4,h5 {{
                    text-transform: capitalize;
                }}

                h2, p {{
                    text-align: left;
                }}

                h3 {{
                    color: #fcb120;
                }}

                tr, th, td {{
                    text-align: left;
                    padding: 10px 10px 5px 10px;
                    border: #000000;
                }}

                td {{
                    width=60%
                    margin: 0 10px
                }}

                table {{
                    width: 80%;
                    margin: 0 auto 10px;
                    box-sizing: border-box;
                    border-collapse: collapse;
                    text-align: left;

                }}

                img {{
                    margin: 10px auto;
                    padding: 5px;
                    max-width: 80%;
                    height: auto;
                }}

                ul {{
                    position: relative;
                    list-style: none;
                    margin-left: 0;
                    padding-left: 1.2em;
                }}
                ul li:before {{
                    content: "\2605";
                    position: absolute;
                    left: 0;
                }}

                .new-page {{
                    page-break-before: always;
                }}
            </style>
            <table>
                <tr>
                    <td colSpan="2"><h1>{}</h1></td>
                </tr>
                <tr>
                    <td><h3>Personal Details</h3></td>
                </tr>
                <tr>
                    <td><h2>{} {}</h2></td>
                </tr>
                <tr>
                    <th>Email</th>
                    <th>Phone</th>
                </tr>
                <tr>
                    <td>{}</td>
                    <td>{}</td>
                </tr>
            </table>
            """.format(title,
                       docData['firstName'],
                       docData['lastName'],
                       docData['email'],
                       make_phone_pretty(docData['phone']))

    #     PAYMENT DETAILS
        if docData['paymentType'].replace(' ','').lower() == 'creditcard' or docData['paymentType'].replace(' ','').lower() == 'bankaccount' or docData['paymentType'].replace(' ','').lower() == 'directdebit':
            paymentDetails = ""
            if docData['paymentType'].replace(' ','').lower() == 'creditcard':
                paymentDetails = f"""
                <table>
                    <tr>
                        <td><h3>Payment Details</h3></td>
                    </tr>
                    <tr>
                        <td><strong>Payment Method</strong></td>
                        <td>{docData['paymentType']}</td>
                    </tr>

                    <tr>
                        <td><strong>Card Holder</strong></td>
                        <td>{docData['memberCreditCard']['holder']}</td>
                    </tr>
                    <tr>
                        <td><strong>Card Number</strong></td>
                        <td>****{docData['memberCreditCard']['number'][-4:]}</td>
                    </tr>
                    <tr>
                        <td><strong>Card Expiry</strong></td>
                        <td>{docData['memberCreditCard']['expMonth']}/{docData['memberCreditCard']['expYear']}</td>
                    </tr>
                </table>
                """

            if docData['paymentType'].replace(' ','').lower() == 'bankaccount' or docData['paymentType'].replace(' ','').lower() == 'directdebit':
                paymentDetails = f"""
                <table>
                    <tr>
                        <td><h3>Payment Details</h3></td>
                    </tr>
                    <tr>
                        <td><strong>Account Name</strong></td>
                        <td>{docData['memberBankDetail']['accountHolderName']}</td>
                    </tr>
                    <tr>
                        <td><strong>BSB</strong></td>
                        <td>{docData['memberBankDetail']['bsb']}</td>
                    </tr>
                    <tr>
                        <td><strong>Account Number</strong></td>
                        <td>****{docData['memberBankDetail']['accountNumber'][-4:]}</td>
                    </tr>
                    <tr>
                        <td><strong>Account Type</strong></td>
                        <td>{docData['memberBankDetail']['accountType']}</td>
                    </tr>
                </table>
                """

            payment = f"""
            {paymentDetails}
            <table>
               <tr>
                   <td width='70'><img style='float: right' src='{Constants.ICONS_TICK}' alt='' height='20' width='20' /></td>
                   <td>{docData['getAuthorisation']}</td>
                </tr>
                <tr>
                    <td><strong>Signature</strong></td>
                </tr>
                <tr><td colSpan="2">
                    <img src={docData['paymentAuthSignatureURL']} alt='' />
                </td></tr>
            </table>
            """
        else:
            payment = ''

    #     MEMBERSHIP DETAILS
        if docData['contractNamesToBeActivated'] is not None:
            memberships_data = docData['contractNamesToBeActivated'].split(' | ')
        else:
            memberships_data = None

        if docData['serviceNamesToBeActivated'] is not None:
            services_data = docData['serviceNamesToBeActivated'].split(' | ')
        else:
            services_data = None

        memberships_td = ""
        if memberships_data is not None:
            for m in memberships_data:
                membership = m.split(" # ")
                membership_billing = membership[1].split('/')
                amount = float(membership_billing[0])
                if len(membership_billing) == 2:
                    frequency = "/" + membership_billing[1]
                else:
                    frequency = ''

                memberships_td = memberships_td + "<tr><td>{}</td><td>${:.2f}{}</td></tr>".format(membership[0], amount, frequency)

        if services_data is not None:
            for s in services_data:
                service = s.split(' # ')
                amount = float(service[2]) * float(service[3])
                memberships_td = memberships_td + "<tr><td>{}</td><td></td></tr>".format(service[1])
                memberships_td = memberships_td + "<tr><td>Quantity: {}, Unit Price: ${:.2f}</td><td>${:.2f}</td></tr>".format(service[3], float(service[2]), amount)

        memberships = f"""
        <table>
            <tr>
                <td><h3>Memberships</h3></td>
            </tr>
            {memberships_td}
            <tr>
                <td colSpan="2"><i><sup>*</sup>Direct Debits incur $1.50 transaction fee.</i></td>
            </tr>
            <tr>
                <td colSpan="2"><i><sup>*</sup>All cancellations require 30 days notice.</i></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td><strong>Notes</strong></td>
            <tr>
            <tr>
                <td colSpan="2">{docData['notes']}</td>
            </tr>
        </table>
        """

        if docData[ 'personalTrainingStartDate'] is not None:
            ptStartDate = """
            <tr>
                <td><strong>Personal Training Start Date</strong></td>
                <td>{}</td>
            </tr>
            """.format(make_date_pretty(docData['personalTrainingStartDate']))
        else:
            ptStartDate = ""

        if docData['startDate'] is not None:
            start_date = f"""<tr>
                <td><strong>Start Date</strong></td>
                <td>{make_date_pretty(docData['startDate'])}</td>
            </tr>
            """
        else:
            start_date = ""


        if docData['firstBillingDate'] is not None:
            first_billing_date = f"""<tr>
                <td><strong>First Billing Date</strong></td>
                <td>{make_date_pretty(docData['firstBillingDate'])}</td>
            </tr>
            """
        else:
            first_billing_date = ""


        compfort_cxl_date = ""
        try:
            if docData['couponCode'] is not None and 'comfortcancel' in docData['couponCode'].lower():

                COUPONS = docData['couponCode'].split(',')
                for COUPON in COUPONS:
                    if 'comfortcancel' in COUPON.lower():
                        TIME_DELTA = int(COUPON.split('Day')[0])

                end_date = pd.to_datetime(docData['startDate']) + datetime.timedelta(TIME_DELTA)
                compfort_cxl_date = f"""<tr>
                <td><strong>Trial Period End Date</strong></td>
                <td>{make_date_pretty(end_date)}</td>
                </tr>
                <tr><td colSpan="2"><i><sup>*</sup>Cooling Off Period <u>expires</u> at 5pm on this date.</i></td></tr>"""
                
        except Exception as ex:
            print(f"Error setting Comfort Cxl Trial date: {ex}") 


        if docData['personalTrainerName'] is not None:
            coach = docData['personalTrainerName']
        else:
            coach = "No Coach Assigned"

        details = f"""
        <table>
            <tr>
                <td><h3>Membership Details</h3></td>
            </tr>
            <tr>
                <td><strong>Membership Consultant</strong></td>
                <td>{docData['staffName']}</td>
            </tr>
            <tr>
                <td><strong>Assigned Coach</strong></td>
                <td>{coach}</td>
            </tr>
            {ptStartDate}
            {first_billing_date}
            {start_date}
            {compfort_cxl_date}
        </table>
        """

        has_agreement = False
        agreement_items = ""
        if docData['agreement'] is not None:
            has_agreement = True
            split = docData['agreement'].split(" | ")
            for i in split:
        #         print(i)
                agreement_items = agreement_items + f"<tr><td width='70'><img style='float: right' src='{Constants.ICONS_TICK}' alt='' height='20' width='20' /></td>" \
                    f"<td>{i}</td></tr>"


        if (docData['under18SignatureURL'] is not None):
            has_agreement = True
            under18Signature = """
            <tr>
                <td><strong>Guardian Signature</strong></td>
            </tr>
            <tr><td colSpan="2">
                <img src={} alt='' />
            </td></tr>
            """.format(docData['under18SignatureURL'])
        else:
            under18Signature = ''

        if ( docData['primarySignatureURL'] is not None):
            has_agreement = True
            signature = """
            <tr>
                <td><strong>Signature</strong></td>
            </tr>
            <tr><td colSpan="2">
                <img src={} alt='' />
            </td></tr>
            """.format(docData['primarySignatureURL'])
        else:
            signature = ""

        if has_agreement == True:
            agreements = f"""
             <table>
                 <tr>
                    <td><h3>Agreement</h3></td>
                </tr>
                 {agreement_items}
                 {under18Signature}
                 {signature}
             </table>"""
        else:
            agreements = ''

        membership_agreement = f"""
        <div class="new-page">
            {memberships}
            {agreements}
        </div>"""
        
        termsAndConditions = """
        <div class="new-page">
            <table>
                <tr><td>{}</td></tr>
            </table>
        </div>
        """.format(terms_and_conditions)


        document = topLine + payment + details + membership_agreement + termsAndConditions #+ payment + membership + termsAndConditions

        options = {
            'margin-top': '0.5in',
            'margin-right': '0in',
            'margin-bottom': '0.5in',
            'margin-left': '0in',
            'encoding': "UTF-8",
            'no-outline': None
        }

        pdfkit.from_string(document, filepath, options=options)
        return True
    except Exception as ex:
        print(f'Error [generate_enrolment_pdf]: {ex}')
        return False



# ################################################################## Build PDF FP Coach Enrolment
def generate_fp_coach_enrolment_pdf(docData, key, filepath):
    print('generate_fp_coach_enrolment_pdf')
    
    try:
        if int(docData['locationId']) == 5:
            title = f"""<img src="{Constants.LOGOS_BK_HEADER_URL}" alt="The Bunker Gym">"""
        else:
            title = f"""<img src="{Constants.LOGOS_FP_HEADER_URL}" alt="Fitness Playground">"""

        topLine = """
        <style>
            body {{
                font-family: 'Open Sans', sans-serif;
                margin: 20px auto;
                width: 100%;
                text-align: center;
                border: 1px solid #fff;
                padding: 10px;
                box-sizing: border-box;
                overflow-y: auto;
            }}

            h1 {{
                color: #fcb120;
                text-transform: uppercase;
                text-align: center;
            }}

            h2,h3,h4,h5 {{
                text-transform: capitalize;
            }}

            h2, p {{
                text-align: left;
            }}

            h3 {{
                color: #fcb120;
            }}

            tr, th, td {{
                text-align: left;
                padding: 10px 10px 5px 10px;
                border: #000000;
            }}

            td {{
                width=60%
                margin: 0 10px
            }}

            table {{
                width: 80%;
                margin: 0 auto 10px;
                box-sizing: border-box;
                border-collapse: collapse;
                text-align: left;

            }}

            img {{
                margin: 10px auto;
                padding: 5px;
                max-width: 80%;
                height: auto;
            }}

            ul {{
                position: relative;
                list-style: none;
                margin-left: 0;
                padding-left: 1.2em;
            }}
            ul li:before {{
                content: "\2605";
                position: absolute;
                left: 0;
            }}

            .new-page {{
                page-break-before: always;
            }}
        </style>
        <table>
            <tr>
                <td colSpan="2"><h1>{}</h1></td>
            </tr>
            <tr>
                <td><h3>Personal Details</h3></td>
            </tr>
            <tr>
                <td><h2>{} {}</h2></td>
            </tr>
            <tr>
                <th>Email</th>
                <th>Phone</th>
            </tr>
            <tr>
                <td>{}</td>
                <td>{}</td>
            </tr>
        </table>
        """.format(title,
                   docData['firstName'],
                   docData['lastName'],
                   docData['email'],
                   make_phone_pretty(docData['phone']))

        #     MEMBERSHIP DETAILS
        if docData['mboContractNames'] is not None:
            memberships_data = docData['mboContractNames'].split(' | ')
        else:
            memberships_data = None

        if memberships_data is not None:
            memberships_td = "<tr><td><strong>Coaching Option</strong></td><td></td></tr>"
            for m in memberships_data:
                membership = m.split(" # ")
                membership_billing = membership[1].split('/')
                amount = float(membership_billing[0])
                if len(membership_billing) == 2:
                    frequency = "/" + membership_billing[1]
                else:
                    frequency = ''

                memberships_td = memberships_td + "<tr><td>{}</td><td>${:.2f}{}</td></tr>".format(membership[0], amount, frequency)

        if docData['startDate'] is not None:
            start_date = f"""<tr>
                <td><strong>Start Date</strong></td>
                <td>{make_date_pretty(docData['startDate'])}</td>
            </tr>
            """
        else:
            start_date = ""

        if docData['personalTrainerName'] is not None:
            coach = docData['personalTrainerName']
        else:
            coach = "No Coach Assigned"

        details = f"""
        <table>
            <tr>
                <td><h3>Membership Details</h3></td>
            </tr>
            {start_date}
            <tr>
                <td><strong>Membership Consultant</strong></td>
                <td>{docData['staffName']}</td>
            </tr>
            <tr>
                <td><strong>Assigned Coach</strong></td>
                <td>{coach}</td>
            </tr>
            {memberships_td}
            <tr>
                <td colSpan="2"><i><sup>*</sup>Direct Debits incur $1.50 transaction fee.</i></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td><strong>Notes</strong></td>
            <tr>
            <tr>
                <td colSpan="2">{docData['notes']}</td>
            </tr>
        </table>
        """

        #     PAYMENT DETAILS
        if docData['paymentType'].replace(' ','').lower() == 'creditcard' or docData['paymentType'].replace(' ','').lower() == 'bankaccount' or docData['paymentType'].replace(' ','').lower() == 'directdebit':
            paymentDetails = ""
            if docData['paymentType'].replace(' ','').lower() == 'creditcard':
                paymentDetails = f"""
                <table>
                    <tr>
                        <td><h3>Payment Details</h3></td>
                    </tr>
                    <tr>
                        <td><strong>Payment Method</strong></td>
                        <td>{docData['paymentType']}</td>
                    </tr>

                    <tr>
                        <td><strong>Card Holder</strong></td>
                        <td>{docData['memberCreditCard']['holder']}</td>
                    </tr>
                    <tr>
                        <td><strong>Card Number</strong></td>
                        <td>****{docData['memberCreditCard']['number'][-4:]}</td>
                    </tr>
                    <tr>
                        <td><strong>Card Expiry</strong></td>
                        <td>{docData['memberCreditCard']['expMonth']}/{docData['memberCreditCard']['expYear']}</td>
                    </tr>
                </table>
                """

            if docData['paymentType'].replace(' ','').lower() == 'bankaccount' or docData['paymentType'].replace(' ','').lower() == 'directdebit':
                paymentDetails = f"""
                <table>
                    <tr>
                        <td><h3>Payment Details</h3></td>
                    </tr>
                    <tr>
                        <td><strong>Account Name</strong></td>
                        <td>{docData['memberBankDetail']['accountHolderName']}</td>
                    </tr>
                    <tr>
                        <td><strong>BSB</strong></td>
                        <td>{docData['memberBankDetail']['bsb']}</td>
                    </tr>
                    <tr>
                        <td><strong>Account Number</strong></td>
                        <td>****{docData['memberBankDetail']['accountNumber'][-4:]}</td>
                    </tr>
                    <tr>
                        <td><strong>Account Type</strong></td>
                        <td>{docData['memberBankDetail']['accountType']}</td>
                    </tr>
                </table>
                """

            agreements = ''
            if docData['agreement'] is not None:
                split = docData['agreement'].split(' | ')

                for i in split:
                    agreements = agreements + """<tr>""" \
                       f"""<td width='70'><img style='float: right' src='{Constants.ICONS_TICK}' alt='' height='20' width='20' /></td>""" \
                       f"""<td>{i}</td>""" \
                    """</tr>"""

            termsAndConditions = f"""
            <div class="new-page">
                <table>
                    <tr>
                        <td><h3>Agreement</h3></td>
                    </tr>
                    <tr>
                        <td><strong>Payments</strong></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colSpan="2">All sessions are paid for in advance. The exact date of the debit may vary depending on your financial institution and your start date. We reserve the right to rebill missed payments at any time.</td>
                    </tr>
                    <tr>
                        <td><strong>Cancelling Sessions</strong></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colSpan="2">Any cancellations must be made 24 hours prior to the session. Failure to provide notice will result in forfeiting the session. In the case adequate notice is provided a make-up session must be arranged within the same fortnightly cycle or this is forfeited (or otherwise agreed with your trainer). You agree any used sessions are to be completed and Fitness Playground is not obliged to refund or credit session values.</td>
                    </tr>
                    <tr>
                        <td><strong>Suspending Agreement</strong></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colSpan="2">You may place your agreement on hold by providing notice 72 hours prior to the billing date. Suspensions are for set time period only any charges after this period will not be refunded.</td>
                    </tr>
                    <tr>
                        <td><strong>Cancelling Agreement</strong></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colSpan="2">All cancellations must be submitted in writing in home club. Minimum notice period is 2 weeks to allow for administration time.</td>
                    </tr>
                   {agreements}
                    <tr>
                        <td><strong>Signature</strong></td>
                    </tr>
                    <tr><td colSpan="2">
                        <img src={docData['signature']} alt='' />
                    </td></tr>
                </table>
            </div>
            <div class="new-page">
                <table>
                    <tr><td>{terms_and_conditions}</td></tr>
                </table>
            </div>
            """
        else:
            payment = ''

        document = topLine + details + paymentDetails + termsAndConditions #+ details + membership_agreement + termsAndConditions #+ payment + membership + termsAndConditions

        options = {
            'margin-top': '0.5in',
            'margin-right': '0in',
            'margin-bottom': '0.5in',
            'margin-left': '0in',
            'encoding': "UTF-8",
            'no-outline': None
        }

        pdfkit.from_string(document, filepath, options=options)
        return True
    except Exception as ex:
        print(f'Error [generate_fp_coach_enrolment_pdf]: {ex}')
        return False


# ################################################################## Build PDF Membership Change
def generate_membershp_change_pdf(docData, key, filepath):
    try:
        if docData['status'] in ['MEMBERSHIP_CHANGE_AUTHORISED', 'MEMBERSHIP_CHANGE_RECEIVED']:

            if int(docData['locationId']) == 5:
                title = f"""<img src="{Constants.LOGOS_BK_HEADER_URL}" alt="The Bunker Gym">"""
            else:
                title = f"""<img src="{Constants.LOGOS_FP_HEADER_URL}" alt="Fitness Playground">"""

            topLine = f"""
                <style>
                body {{
                    font-family: 'Open Sans', sans-serif;
                    margin: 20px auto;
                    width: 100%;
                    text-align: center;
                    border: 1px solid #fff;
                    padding: 10px;
                    box-sizing: border-box;
                    overflow-y: auto;
                }}

                h1 {{
                    color: #fcb120;
                    text-transform: uppercase;
                    text-align: center;
                }}

                h2,h3,h4,h5 {{
                    text-transform: capitalize;
                }}

                h2, p {{
                    text-align: left;
                }}

                h3 {{
                    color: #fcb120;
                }}

                tr, th, td {{
                    text-align: left;
                    padding: 10px 10px 5px 10px;
                    border: #000000;
                }}

                td {{
                    width: 60%;
                    margin: 0 10px
                }}

                table {{
                    width: 80%;
                    margin: 0 auto 10px;
                    box-sizing: border-box;
                    border-collapse: collapse;
                    text-align: left;
                }}

                img {{
                    margin: 10px auto;
                    padding: 5px;
                    max-width: 80%;
                    height: auto;
                }}

                .new-page {{
                    page-break-before: always;
                }}
                </style>
                <table>
                    <tr>
                        <td colSpan="2"><h1>{title}</h1></td>
                    </tr>
                    <tr>
                        <td><h2>{docData['firstName']} {docData['lastName']}</h2></td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <th>Phone</th>
                    </tr>
                    <tr>
                        <td>{docData['email']}</td>
                        <td>{make_phone_pretty(docData['phone'])}</td>
                    </tr>

                </table>
                """

            updateMembership = ""
            updateCoaching = ""
            updateCreche = ""
            bottomLine = ""

            change_type =  docData['changeTypeMembership'].split(',')
            print('Change Types:')

            for t in change_type:
                print(f"\t{t}")

                if t == 'Update My Membership':
                    print(f"\t\t{docData['changeMembership']}")
    #                 ['Upgrade My Membership', None, 'Downgrade My Membership','Suspend My Membership']

                    if docData['changeMembership'] is not None:

                        if docData['changeMembership'] == 'Suspend My Membership':

                            updateMembership = f"""
                                <table>
                                    <tr>
                                        <td><h3>{docData['changeMembership']}</h3></td>
                                    </tr>
                                    <tr>
                                        <td><strong>Suspension Start Date</strong></td>
                                        <td>{make_date_pretty(docData['membershipSuspensionFromDate'])}</td>
                                    </tr>
                                    <tr>
                                        <td><strong>Suspension End Date</strong></td>
                                        <td>{make_date_pretty(docData['membershipReturnDate'])}</td>
                                    </tr>

                                    <tr>
                                        <td><strong>Suspension Length</strong></td>
                                        <td>{docData['membershipSuspensionDuration']}</td>
                                    </tr>
                                </table>"""

                        if docData['changeMembership'] in ['Downgrade My Membership','Upgrade My Membership']:
                            updateMembership = f"""
                                <table>
                                    <tr>
                                        <td><h3>{docData['changeMembership']}</h3></td>
                                    </tr>
                                    <tr>
                                        <td><strong>Current Membership</strong></td>
                                        <td>{docData['fromMembership']}</td>
                                    </tr>
                                    <tr>
                                        <td><strong>Current Membership Debit Amount</strong></td>
                                        <td>${docData['membershipCurrentDebitAmount']} per fortnight</td>
                                    </tr>
                                    <tr>
                                        <td><strong>New Membership</strong></td>
                                        <td>{docData['toMembership']}</td>
                                    </tr>
                                    <tr>
                                        <td><strong>New Membership Debit Amount</strong></td>
                                        <td>${docData['membershipDebitAmount']} per fortnight</td>
                                    </tr>
                                    <tr>
                                        <td><strong>Next Debit Date</strong></td>
                                        <td>{make_date_pretty(docData['membershipNextDebitDate'])}</td>
                                    </tr>
                                </table>"""


                if t == 'Update My Coaching':
                    print(f"\t\t{docData['changeCoaching']}")
    #                 [None, 'Suspend My Coaching', 'Upgrade My Coaching','Downgrade My Coaching']

                    if docData['changeCoaching'] is not None:
                        if docData['changeCoaching'] == 'Suspend My Coaching':
                            updateCoaching = f"""
                                    <table>
                                        <tr>
                                            <td><h3>{docData['changeCoaching']}</h3></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Coach</strong></td>
                                            <td>{docData['personalTrainerName']}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Suspension Start Date</strong></td>
                                            <td>{make_date_pretty(docData['coachingSuspensionFromDate'])}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Suspension End Date</strong></td>
                                            <td>{make_date_pretty(docData['coachingReturnDate'])}</td>
                                        </tr>

                                        <tr>
                                            <td><strong>Suspension Length</strong></td>
                                            <td>{docData['coachingSuspensionDuration']}</td>
                                        </tr>
                                    </table>"""

                        if docData['changeCoaching'] in ['Upgrade My Coaching','Downgrade My Coaching']:
                            updateCoaching = f"""
                                    <table>
                                        <tr>
                                            <td><h3>{docData['changeCoaching']}</h3></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Coach</strong></td>
                                            <td>{docData['personalTrainerName']}</td>
                                        </tr>"""
                            if docData['coachingModality'] == 'External/Level 3 Personal Training':
                                updateCoaching = updateCoaching + f"""
                                        <tr>
                                            <td><strong>New Training Package</strong></td>
                                            <td>{docData['coachingModality']}</td>
                                        </tr>"""
                            else:
                                updateCoaching = updateCoaching + f"""
                                        <tr>
                                            <td><strong>New Training Package</strong></td>
                                            <td>{docData['trainingPackage']}</td>
                                        </tr>"""

                            updateCoaching = updateCoaching + f"""
                                        <tr>
                                            <td><strong>New Coaching Debit Amount</strong></td>
                                            <td>${docData['coachingDebitAmount']} per fortnight</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Next Debit Date</strong></td>
                                            <td>{make_date_pretty(docData['coachingNextDebitDate'])}</td>
                                        </tr>
                                    </table>"""


                if t == 'Update My Add Ons':
    #             [None, 'Creche','Virtual Playground']
                    if docData['changeTypeAddOns'] == 'Creche':
                        print(f"\t\t{docData['changeCreche']}")
    #                     [None, 'Add Creche', 'Cancel Creche', 'Suspend Creche']
                        if docData['changeCreche'] is not None:
                            if docData['changeCreche'] == 'Suspend Creche':
                                updateCreche = f"""
                                    <table>
                                        <tr>
                                            <td><h3>{docData['changeCreche']}</h3></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Suspension Start Date</strong></td>
                                            <td>{make_date_pretty(docData['crecheSuspensionFromDate'])}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Suspension End Date</strong></td>
                                            <td>{make_date_pretty(docData['crecheReturnDate'])}</td>
                                        </tr>

                                        <tr>
                                            <td><strong>Suspension Length</strong></td>
                                            <td>{docData['crecheSuspensionDuration']}</td>
                                        </tr>
                                    </table>"""

                            if docData['changeCreche'] == 'Add Creche':
                                updateCreche = f"""
                                    <table>
                                        <tr>
                                            <td><h3>{docData['changeCreche']}</h3></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Creche</strong></td>
                                            <td>{docData['crecheMembership']}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Creche Debit Amount</strong></td>
                                            <td>${docData['crecheDebitAmount']} per fortnight</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Next Debit Date</strong></td>
                                            <td>{make_date_pretty(docData['crecheNextDebitDate'])}</td>
                                        </tr>
                                </table>"""

                            if docData['changeCreche'] == 'Cancel Creche':
                                updateCreche = f"""
                                    <table>
                                        <tr>
                                            <td><h3>{docData['changeCreche']}</h3></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Creche</strong></td>
                                            <td>{docData['crecheMembership']}</td>
                                        </tr>
                                </table>"""


                bottomLine = f"""
                    <table>
                        <tr>
                            <td><strong>Signature</strong></td>
                        </tr>
                        <tr><td colSpan="2">
                            <img src={docData['signature']} alt='' />
                        </td></tr>
                        <tr>
                            <td><strong>Location</strong></td>
                            <td>{docData['staffMemberGymName']}</td>
                        </tr>
                        <tr>
                            <td><strong>Staff Name</strong></td>
                            <td>{docData['staffName']}</td>
                        </tr>

                    </table>"""

            document = topLine + updateMembership + updateCoaching + updateCreche + bottomLine

            options = {
                'margin-top': '0.5in',
                'margin-right': '0in',
                'margin-bottom': '0.5in',
                'margin-left': '0in',
                'encoding': "UTF-8",
                'no-outline': None
            }


            pdfkit.from_string(document, filepath, options=options)

            return True
        else:
            print("Membership Change is not Authorised")
            return False
    except Exception as ex:
        print(f'Error [generate_membershp_change_pdf]: {ex}')
        return False



# ################################################################## Build PDF FP PRE EX
def generate_pre_ex_pdf(docData, key, filepath):
    print('generate_pre_ex_pdf')
#     print(docData['gymName'])
    try:
        if 'bunker' in docData['gymName'].lower():
            title = f"""<img src="{Constants.LOGOS_BK_HEADER_URL}" alt="The Bunker Gym">"""
        else:
            title = f"""<img src="{Constants.LOGOS_FP_HEADER_URL}" alt="Fitness Playground">"""

        previousMembershipType = ''
        if docData['previousMembershipType'] is not None:
            previousMembershipType = "<ul>"
            for s in docData['previousMembershipType'].split(','):
                previousMembershipType = previousMembershipType + f"<li>{s}</li>"
            previousMembershipType = previousMembershipType + "</ul>"

        priorities = ''
        if docData['priorities'] is not None:
            priorities = "<ul>"
            for s in docData['priorities'].split(','):
                priorities = priorities + f"<li>{s}</li>"
            priorities = priorities + "</ul>"

        currentExercise = ''
        if docData['isCurrentlyExercising']:
            currentExercise = f"""<tr>
                <th>Current Exercise</th>
                <td>{docData['currentExerciseType']}</td>
            </tr>"""
        else:
            currentExercise = f"""<tr>
                <th>Last Exercised</th>
                <td>{docData['lastExercisePeriod']}</td>
            </tr>"""

        changeMotivation = ''
        if docData['changeMotivation'] is not None:
            currentExercise = f"""<tr>
                <th>Motivation to Change</th>
                </tr>
                <tr>
                <td>{docData['currentExercise']}</td>
            </tr>"""

        roadBlocks = ''
        if docData['roadBlocks'] is not None:
            roadBlocks = "<ul>"
            for s in docData['roadBlocks'].split(','):
                roadBlocks = roadBlocks + f"<li>{s}</li>"
            roadBlocks = roadBlocks + "</ul>"

        exerciseInterests = ''
        if docData['exerciseInterests'] is not None:
            exerciseInterests = "<ul>"
            for s in docData['exerciseInterests'].split(','):
                exerciseInterests = exerciseInterests + f"<li>{s}</li>"
            exerciseInterests = exerciseInterests + "</ul>"

        areasToHelp = ''
        if docData['areasToHelp'] is not None:
            areasToHelp = "<ul>"
            for s in docData['areasToHelp'].split(','):
                areasToHelp = areasToHelp + f"<li>{s}</li>"
            areasToHelp = areasToHelp + "</ul>"

        medicalConditions = ''
        if docData['medicalConditions'] is not None:
            medicalConditions = "<ul>"
            for s in docData['medicalConditions'].split(','):
                medicalConditions = medicalConditions + f"<li>{s}</li>"
            medicalConditions = medicalConditions + "</ul>"

        topLine = f"""
        <style>
            body {{
                font-family: 'Open Sans', sans-serif;
                margin: 20px auto;
                width: 100%;
                text-align: center;
                border: 1px solid #fff;
                padding: 10px;
                box-sizing: border-box;
                overflow-y: auto;
            }}

            h1 {{
                color: #fcb120;
                text-transform: uppercase;
                text-align: center;
            }}

            h2,h3,h4,h5 {{
                text-transform: capitalize;
            }}

            h2, p {{
                text-align: left;
            }}

            h3 {{
                color: #fcb120;
            }}

            tr, th, td {{
                text-align: left;
                padding: 10px 10px 5px 10px;
                border: #000000;
            }}

            td {{
                width=60%
                margin: 0 10px
            }}

            table {{
                width: 80%;
                margin: 0 auto 10px;
                box-sizing: border-box;
                border-collapse: collapse;
                text-align: left;

            }}

            img {{
                margin: 10px auto;
                padding: 5px;
                max-width: 80%;
                height: auto;
            }}

            ul li{{margin-bottom:5px;}}

            .new-page {{
                page-break-before: always;
            }}
        </style>
        <table>
            <tr>
                <td colSpan="2"><h1>{title}</h1></td>
            </tr>
            <tr>
                <td><h3>Personal Details</h3></td>
            </tr>
            <tr>
                <td><h2>{docData['firstName']} {docData['lastName']}</h2></td>
            </tr>
            <tr>
                <th>Email</th>
                <td>{docData['email']}</td>
            </tr>
            <tr>
                <th>Phone</th>
                <td>{make_phone_pretty(docData['phone'])}</td>
            </tr>
            <tr>
                <th>Gender</th>
                <td>{docData['gender'].title()}</td>
            </tr>
            <tr>
                <th>Date of Birth</th>
                <td>{make_date_pretty(docData['dob'])}</td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td><h3>Medical Details</h3></td>
            </tr>
            <tr>
                <th>Has Medical Conditions?</th>
                <td>{(docData['hasMedicalCondition'] == 1)}</td>
            </tr>
            <tr>
                <th>Medical Conditions</th>
                <td>{medicalConditions}</td>
            </tr>
            <tr>
                <th>Has Clearance For Medical Conditions?</th>
                <td>{(docData['hasClearanceMedicalCondition'] == 1)}</td>
            </tr>
            <tr>
                <th>Are You On Medication?</th>
                <td>{(docData['isOnMedication'] == 1)}</td>
            </tr>
            <tr>
                <th>Has Clearance For Medication?</th>
                <td>{(docData['hasClearanceMedication'] == 1)}</td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td><h3>Submission Details</h3></td>
            </tr>
            <tr>
                <th>Club</th>
                <td>{docData['gymName']}</td>
            </tr>
            <tr>
                <th>Submission Date</th>
                <td>{make_date_pretty(docData['submission_datetime'])}</td>
            </tr>
            <tr>
                <th>Enquiry Type</th>
                <td>{docData['enquireType']}</td>
            </tr>
            <tr>
                <th>Assigned Staff</th>
                <td>{docData['assignedStaffName']}</td>
            </tr>
            <tr>
                <th>Staff Name</th>
                <td>{docData['staffName']}</td>
            </tr>
            <tr>
                <th>Formstack Form Id</th>
                <td>{docData['fs_formId']}</td>
            </tr>
            <tr>
                <th>Formstack Unique Id</th>
                <td>{docData['fs_uniqueId']}</td>
            </tr>
            <tr>
                <th>Notes</th>
            </tr>
            <tr>
                <td colSpan="2">{docData['notes']}</td>
            </tr>
        </table>

        <div class="new-page">
            <table>
                <tr>
                    <td><h3>Pre Ex Details</h3></td>
                </tr>
                <tr>
                    <th>How Did You Hear About Us?</th>
                    <td>{docData['howDidYouHearAboutUs']}</td>
                </tr>
                <tr>
                    <th>Had a Fitness Facility Membership</th>
                    <td>{docData['hasPreviousMembership'].title()}</td>
                </tr>
                <tr>
                    <th>Previous Fitness Facility Memberships</th>
                    <td>{previousMembershipType}</td>
                </tr>
                <tr>
                    <th>Is Currently Exercising</th>
                    <td>{docData['isCurrentlyExercising'].title()}</td>
                </tr>
                {currentExercise}
                {changeMotivation}
                <tr>
                    <th>Main Focus</th>
                    <td>{docData['areaOfFocus']}</td>
                </tr>
                <tr>
                    <th>Exercise Interests</th>
                    <td>{exerciseInterests}</td>
                </tr>
                <tr>
                    <th>Areas to Help</th>
                    <td>{areasToHelp}</td>
                </tr>
                <tr>
                    <th>Priorities</th>
                    <td>{priorities}</td>
                </tr>
                <tr>
                    <th>Potential Roadblocks</th>
                    <td>{roadBlocks}</td>
                </tr>
                <tr>
                    <th>How Important Is This?</th>
                    <td>{docData['significantsScale']}</td>
                </tr>
                <tr>
                    <th>Planned Number Of Days To Exercise</th>
                    <td>{docData['numberDaysPerWeekExercise']}</td>
                </tr>
                <tr>
                    <th>Main Focus Details</th>
                </tr>
                <tr>
                    <td colSpan="2">{docData['areaOfFocusDetails']}</td>
                </tr>
                <tr>
                    <th>30 Day Goal</th>
                </tr>
                <tr>
                    <td colSpan="2">{docData['areaOfFocusFirst30Days']}</td>
                </tr>
            </table>
        </div>
        """


        document = topLine # + details + paymentDetails + termsAndConditions #+ details + membership_agreement + termsAndConditions #+ payment + membership + termsAndConditions

        options = {
            'margin-top': '0.5in',
            'margin-right': '0in',
            'margin-bottom': '0.5in',
            'margin-left': '0in',
            'encoding': "UTF-8",
            'no-outline': None
        }

        pdfkit.from_string(document, filepath, options=options)
        return True
    except Exception as ex:
        print(f'Error [generate_fp_pre_ex_pdf]: {ex}')
        return False


# ################################################################## Build PDF Cancellation
def generate_cancellation_pdf(docData, key, filepath):
    
    try:
        if int(docData['locationId']) == 5:
            title = f"""<img src="{Constants.LOGOS_BK_HEADER_URL}" alt="The Bunker Gym">"""
        else:
            title = f"""<img src="{Constants.LOGOS_FP_HEADER_URL}" alt="Fitness Playground">"""

        topLine = f"""
            <style>
                body {{
                    font-family: 'Open Sans', sans-serif;
                    margin: 20px auto;
                    width: 100%;
                    text-align: center;
                    border: 1px solid #fff;
                    padding: 10px;
                    box-sizing: border-box;
                    overflow-y: auto;
                }}

                h1 {{
                    color: #fcb120;
                    text-transform: uppercase;
                    text-align: center;
                }}

                h2,h3,h4,h5 {{
                    text-transform: capitalize;
                }}

                h2, p {{
                    text-align: left;
                }}

                h3 {{
                    color: #fcb120;
                }}
                
                table {{
                    width: 80%;
                    margin: 0 auto 10px;
                    box-sizing: border-box;
                    border-collapse: collapse;
                    text-align: left;
                    line-height: 1.2em
                }}

                tr, th, td {{
                    text-align: left;
                    padding: 10px 10px 5px 10px;
                    border: #000000;
                }}

                td {{
                    min-width=50%
                    margin: 0 10px
                }}

                img {{
                    margin: 10px auto;
                    padding: 5px;
                    max-width: 80%;
                    height: auto;
                }}

                ul {{
                    position: relative;
                    list-style: none;
                    margin-left: 0;
                    padding-left: 1.2em;
                }}
                

                .new-page {{
                    page-break-before: always;
                }}
            </style>
            <table>
                <tr>
                    <td colSpan="2"><h1>{title}</h1></td>
                </tr>
                <tr>
                    <td><h3>Personal Details</h3></td>
                </tr>
                <tr>
                    <td><h2>{docData['firstName']} {docData['lastName']}</h2></td>
                </tr>
                <tr>
                    <th>Email</th>
                    <th>Phone</th>
                </tr>
                <tr>
                    <td>{docData['email']}</td>
                    <td>{make_phone_pretty(docData['phone'])}</td>
                </tr>
            </table>
            """
        
        cancellation_details = f"""
            <table>
                <tr>
                    <td colSpan="2"><h3>Cancellation Details</h3></td>
                </tr>
                <tr>
                    <td><strong>Cancellation Status</strong></td>
                    <td>{docData['status'].replace('_',' ').title()}</td>
                </tr>
                <tr>
                    <td><strong>Location</strong></td>
                    <td>{docData['gymName']}</td>
                </tr>
                <tr>
                    <td><strong>Staff Name</strong></td>
                    <td>{docData['staffName']}</td>
                </tr>
                <tr>
                    <td><strong>Reason for Cancellation</strong></td>
                    <td>{docData['reasonForCancellation']}</td>
                </tr>"""
        
        if docData['cancellationOptions'] == 'Membership':
        
            cancellation_details = cancellation_details + f"""
                <tr>
                    <td><strong>Contract Status</strong></td>
                    <td>{docData['eligibilityCheck']}</td>
                </tr>
                <tr>
                    <td><strong>Membership Outcome</strong></td>
                    <td>{docData['outcome']}</td>
                </tr>"""
            try:
                if docData['accountPaymentsToBeDebited'] is not None and docData['accountPaymentsToBeDebited'] != '':
                    cancellation_details = cancellation_details + f"""<tr><td><strong>Account Payments to be Debited</strong></td><td>"""
                    for s in docData['accountPaymentsToBeDebited'].split(', '):
                        cancellation_details = cancellation_details + f"""{s}<br/>"""
                    cancellation_details = cancellation_details[:-len("<br/>")]
                    cancellation_details = cancellation_details + """</td></tr>"""
            except:
                pass
            
            try:
                if docData['totalDebitAmount'] is not None and docData['totalDebitAmount'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Total Debited Amount</strong></td>
                        <td>${docData['totalDebitAmount']}</td>
                    </tr>"""
            except:
                pass
        
            try:
                if docData['totalAmountWaived'] is not None and docData['totalAmountWaived'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Total Amount Waived</strong></td>
                        <td>${docData['totalAmountWaived']}</td>
                    </tr>"""
            except:
                pass
            
            if docData['cancellationDate'] is not None and docData['cancellationDate'] != '':
                cancellation_details = cancellation_details + f"""<tr>
                    <td><strong>Cancellation Date</strong></td>
                    <td>{make_date_pretty(docData['cancellationDate'])}</td>
                </tr>"""
            
            if docData['cancellationLastDebitDate'] is not None and docData['cancellationLastDebitDate'] != '':
                cancellation_details = cancellation_details + f"""<tr>
                    <td><strong>Last Debit Date</strong></td>
                    <td>{make_date_pretty(docData['cancellationLastDebitDate'])}</td>
                </tr>"""
        
#             Saves
            try:
                if docData['saveOptions'] is not None and docData['saveOptions'] != '':
                    cancellation_details = cancellation_details + f"""<tr><td><strong>Save Options</strong></td><td>"""
                    for s in docData['saveOptions'].split(', '):
                        cancellation_details = cancellation_details + f"""{s}<br/>"""
                    cancellation_details = cancellation_details[:-len("<br/>")]
                    cancellation_details = cancellation_details + """</td></tr>"""
            except:
                pass

            try:
                if docData['saveUpgradeType'] is not None and docData['saveUpgradeType'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Upgrade</strong></td>
                        <td>{docData['saveUpgradeType']}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['saveUpgradeMembership'] is not None and docData[''] != 'saveUpgradeMembership':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Upgrade Membership</strong></td>
                        <td>{docData['saveUpgradeMembership']}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['saveDowngradeType'] is not None and docData['saveDowngradeType'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Downgrade</strong></td>
                        <td>{docData['saveDowngradeType']}</td>
                    </tr>"""
            except:
                pass
                
            try:
                if docData['saveDowngradeMembership'] is not None and docData['saveDowngradeMembership'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Downgrade Membership</strong></td>
                        <td>{docData['saveDowngradeMembership']}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['saveMotivationFromDate'] is not None and docData['saveMotivationFromDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>From Date</strong></td>
                        <td>{make_date_pretty(docData['saveMotivationFromDate'])}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['saveMotivationToDate'] is not None and docData['saveMotivationToDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>To Date</strong></td>
                        <td>{make_date_pretty(docData['saveMotivationToDate'])}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['saveMotivationNextDebitDate'] is not None and docData['saveMotivationNextDebitDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Next Debit Date</strong></td>
                        <td>{make_date_pretty(docData['saveMotivationNextDebitDate'])}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['saveFreezePeriod'] is not None and docData['saveFreezePeriod'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Membership Freeze Period</strong></td>
                        <td>{docData['saveFreezePeriod']}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['saveFreezeFromDate'] is not None and docData['saveFreezeFromDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Freeze From Date</strong></td>
                        <td>{make_date_pretty(docData['saveFreezeFromDate'])}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['saveFreezeToDate'] is not None and docData['saveFreezeToDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Freeze From Date</strong></td>
                        <td>{make_date_pretty(docData['saveFreezeToDate'])}</td>
                    </tr>"""
            except:
                pass
        
        
#         PT
        if docData['cancellationOptions'] == 'Personal Training' or docData['hasCoach']:
            
            try:
                if docData['ptReasonForCancelling'] is not None and docData['ptReasonForCancelling'] != '':
                    cancellation_details = cancellation_details + f"""<tr><td><strong>Coaching Cancellation Reason</strong></td><td>{docData['ptReasonForCancelling']}</td></tr>"""
            except:
                pass
            
            try:
                cancellation_details = cancellation_details + f"""\
                <tr>
                    <td><strong>Coaching Outcome</strong></td>
                    <td>{docData['ptSaveOptions']}</td>
                </tr>
                <tr>
                    <td><strong>Coach Name</strong></td>
                    <td>{docData['personalTrainerName']}</td>
                </tr>"""
            except:
                pass
            
            try:
                if docData['ptNumberSessionsCompleted'] is not None and docData['ptNumberSessionsCompleted'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Sessions Completed</strong></td>
                        <td>{docData['ptNumberSessionsCompleted']}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['ptCancellationDate'] is not None and docData['ptCancellationDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Coaching Cancellation Date</strong></td>
                        <td>{make_date_pretty(docData['ptCancellationDate'])}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['ptLastDebitDate'] is not None and docData['ptLastDebitDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Coaching Last Debit Date</strong></td>
                        <td>{make_date_pretty(docData['ptLastDebitDate'])}</td>
                    </tr>"""
            except:
                pass
            
            try:
                if docData['ptSaveSuspensionFromDate'] is not None and docData['ptSaveSuspensionFromDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Coaching Suspension From Date</strong></td>
                        <td>{make_date_pretty(docData['ptSaveSuspensionFromDate'])}</td>
                    </tr>"""
            except:
                pass

            try:
                if docData['ptSaveSuspensionToDate'] is not None and docData['ptSaveSuspensionToDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Coaching Suspension To Date</strong></td>
                        <td>{make_date_pretty(docData['ptSaveSuspensionToDate'])}</td>
                    </tr>"""
            except:
                pass

            try:
                if docData['ptSaveSuspensionNextDebitDate'] is not None and docData['ptSaveSuspensionNextDebitDate'] != '':
                    cancellation_details = cancellation_details + f"""<tr>
                        <td><strong>Coaching Next Debit Date</strong></td>
                        <td>{make_date_pretty(docData['ptSaveSuspensionNextDebitDate'])}</td>
                    </tr>"""
            except:
                pass
        
        #         Close Cancellation Details
        cancellation_details = cancellation_details + """</table>"""

        authorisation = ''
        if docData['acknowledgement'] is not None and docData['acknowledgement'] != '' and docData['directDebitAcknowledgement'] is not None and docData['directDebitAcknowledgement'] != '': 
            authorisation = f"""<table>"""

            if docData['acknowledgement'] is not None and docData['acknowledgement'] != '':
                authorisation = authorisation + f"""<tr>
                       <td colSpan="2"><img style='float: right' src='{Constants.ICONS_TICK}' alt='' height='20' width='20' /></td>
                       <td>{docData['acknowledgement']}</td>
                    </tr>"""

            if docData['directDebitAcknowledgement'] is not None and docData['directDebitAcknowledgement'] != '': 
                authorisation = authorisation + f"""<tr>
                       <td colSpan="2"><img style='float: right' src='{Constants.ICONS_TICK}' alt='' height='20' width='20' /></td>
                       <td>{docData['directDebitAcknowledgement']}</td>
                    </tr>"""
            authorisation = authorisation + """</table>"""
        
        authorisation = authorisation + f"""<table>"""
        
        if docData['signature'] is not None and docData['signature'] != '':
            authorisation = authorisation + f"""<table><tr>
                    <td><strong>Signature</strong></td>
                </tr>
                <tr><td colSpan="2">
                    <img src={docData['signature']} alt='' />
                </td></tr></table>"""

# # Membership Transfer
# 'friendFirstName': None,
# 'friendLastName': None,
# 'friendPhone': None,
# 'friendEmail': None,
# 'friendTransferDate': None,
# 'friendMembershipName': None,
# 'friendNumberOutstandingDebits': None,
# 'friendOngoingFortnightlyDirectDebit': None,
# 'friendTermsAndConditions': None,
# 'friendSignature': None,
# 'friendPaymentAuthorisation': None,
# 'friendTransferType': None,
# 'friendMembershipNamePif': None,
# 'friendMembershipExpires': None,
# 'friendMembershipNamePt': None,
# 'friendNumberOutstandingDebitsPt': None,
# 'friendPaymentMethod': None,
# 'friendAccessKeyNumber': None,
  
        document = topLine + cancellation_details + authorisation # + feedback

        options = {
            'margin-top': '0.5in',
            'margin-right': '0in',
            'margin-bottom': '0.5in',
            'margin-left': '0in',
            'encoding': "UTF-8",
            'no-outline': None
        }

        pdfkit.from_string(document, filepath, options=options)
        return True
    except Exception as ex:
        print(f'Error [generate_cancellation_pdf]: {ex}')
        return False



# ###########################################################################
# ERROR HANDLING.                                                           #
# ###########################################################################
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

    if Constants.IS_TEST:
        print(json.dumps(MANUAL_SUBMISSION))
        
    source_post_request(Constants.SOURCE_HANDLE_MANUAL_SUBMISSION_URL, MANUAL_SUBMISSION)
    return
    

# ###########################################################################
# MAIN.                                                                     #
# ###########################################################################
def coach_pdf_writer(BODY):
    try:
        message = ""
        flag = False

        enrolment_data = BODY['data']
        mbo_client = BODY['mbo_client']
        mbo_credentials = BODY['mbo_credentials']
        gym_details = BODY['gym_details']
        decode_email_credentials(BODY['email_credentials'])
        # print(enrolment_data)

        key = "Coach {}.pdf".format(enrolment_data['createDate'].split(' ')[0])
        filepath = '/tmp/{key}'.format(key=key)

        flag = generate_fp_coach_enrolment_pdf(enrolment_data, key, filepath)

    #   If PDF Writer fails 
        if flag == False:
            message = message + '\nError [coach_pdf_writer] - writing pdf'
            print(message)
            handle_manual_submission(CommunicationsStatus.PDF_ERROR, None, enrolment_data)
            return

        # Send email
        flag = send_fp_coach_enrolment_email(enrolment_data, gym_details, key, filepath)
        if flag == False:
            message = message + '\nError [coach_pdf_writer] - emailing pdf '
            print(message)
            send_error_notification(message)
            handle_manual_submission(CommunicationsStatus.PDF_EMAIL_ERROR, None, enrolment_data)

        # Upload PDF to Mindbody Online
        flag = upload_client_document_v2(enrolment_data, mbo_client, mbo_credentials, key, filepath)

        if flag == False:
            message = message + '\nError [coach_pdf_writer] - uploading pdf to MBO'
            print(message)
            handle_manual_submission(CommunicationsStatus.PDF_UPLOAD_ERROR, None, enrolment_data)

        # Clean Up
        os.remove(filepath)

    except Exception as ex:
        message = f'Error [coach_pdf_writer]: {ex}'
        print(message)
        
        send_error_notification(message)
        handle_manual_submission(CommunicationsStatus.PDF_EMAIL_ERROR, None, enrolment_data)
    
    finally:
        return



def enrolment_pdf_writer_v2(BODY):
    print("enrolment_pdf_writer_v2")
    
    try:
        message = ""
        flag = False

        enrolment_data = BODY['data']
        mbo_client = BODY['mbo_client']
        mbo_credentials = BODY['mbo_credentials']
        gym_details = BODY['gym_details']
        decode_email_credentials(BODY['email_credentials'])
        PT_TRACKER_ENTITY = BODY['pt_tracker']
        # print(enrolment_data)

        key = "Enrolment {}.pdf".format(enrolment_data['createDate'].split(' ')[0])
        filepath = '/tmp/{key}'.format(key=key)

        flag = generate_enrolment_pdf(enrolment_data, key, filepath)

        #   If PDF Writer fails 
        if flag == False:
            message = 'Error [enrolment_pdf_writer_v2] - writing pdf'
            print(message)
            handle_manual_submission(CommunicationsStatus.PDF_ERROR, enrolment_data, None)
            return

        # Send email
        flag = send_enrolment_email_v2(enrolment_data, gym_details, PT_TRACKER_ENTITY, key, filepath)
        if flag == False:
            message = message + '\nError [enrolment_pdf_writer_v2] - emailing pdf'
            print(message)
            send_error_notification(message)
            handle_manual_submission(CommunicationsStatus.PDF_EMAIL_ERROR, enrolment_data, None)

        # Upload PDF to Mindbody Online
        flag = upload_client_document_v2(enrolment_data, mbo_client, mbo_credentials, key, filepath)

        if flag == False:
            message = message + '\nError [enrolment_pdf_writer_v2] - uploading pdf to MBO'
            print(message)
            handle_manual_submission(CommunicationsStatus.PDF_UPLOAD_ERROR, enrolment_data, None)

        # Clean Up
        os.remove(filepath)

    except Exception as ex:
        message = f'Error [enrolment_pdf_writer_v2]: {ex}'
        print(message)
        send_error_notification(message)
        handle_manual_submission(CommunicationsStatus.PDF_ERROR, enrolment_data, None)

    finally:
        return


def membership_change_pdf_writer(BODY):

    print('membership_change_pdf_writer')

    try:
        message = ""
        flag = False

        decode_mbo_credentials()
        get_mbo_token()

        data = BODY['data']

        MBO_CLIENT = mbo_get_client(data)
        # mbo_client = MBO_CLIENT

        if MBO_CLIENT is None:
            message = "[membership_change_pdf_writer] Unable to match submission to MBO Client"
            send_error_notification(message)
        else:

            mbo_client = MBO_CLIENT
            mbo_credentials = Constants.MBO_HEADERS,
            # gym_details = BODY['gym_details']
            # decode_email_credentials(BODY['email_credentials'])
            # PT_TRACKER_ENTITY = BODY['pt_tracker']

            CREATE_DATE = pd.to_datetime(data['submission_datetime'])

            key = "Membershp Change {}.pdf".format(CREATE_DATE.strftime("%Y-%m-%d"))
            filepath = '/tmp/{key}'.format(key=key)
            flag = generate_membershp_change_pdf(data, key, filepath)
            print(f"generate_membershp_change_pdf => {flag}")

            flag = upload_client_document_v2(data, mbo_client, mbo_credentials, key, filepath)

            # Clean Up
            os.remove(filepath)

    except Exception as ex:
        message = f'Error [membership_change_pdf_writer]: {ex}'
        print(message)
        send_error_notification(message)

    finally:
        return



def pre_ex_pdf_writer(BODY):
    print('pre_ex_pdf_writer')
    try:
        message = ""
        flag = False

        data = BODY['data']
        mbo_client = BODY['mbo_client']
        mbo_credentials = BODY['mbo_credentials']
        # gym_details = BODY['gym_details']
        # decode_email_credentials(BODY['email_credentials'])
        # PT_TRACKER_ENTITY = BODY['pt_tracker']

        key = "Pre-ex {}.pdf".format(data['submission_datetime'].strftime("%Y-%m-%d"))
        filepath = '/tmp/{key}'.format(key=key)
        flag = generate_pre_ex_pdf(data, key, filepath)
        print(f"generate_pre_ex_pdf => {flag}")

        flag = upload_client_document_v2(data, mbo_client, mbo_credentials, key, filepath)

        # Clean Up
        os.remove(filepath)

    except Exception as e:
        message = f'Error [pre_ex_pdf_writer]: {ex}'
        print(message)
        send_error_notification(message)

    finally:
        return


def cancellation_pdf_writer(BODY):

    print('cancellation_pdf_writer')

    try:
        message = ""
        flag = False

        decode_mbo_credentials()
        get_mbo_token()

        data = BODY['data']

        MBO_CLIENT = mbo_get_client(data)
        # mbo_client = MBO_CLIENT

        if MBO_CLIENT is None:
            message = "[cancellation_pdf_writer] Unable to match submission to MBO Client"
            send_error_notification(message)
        else:

            mbo_client = MBO_CLIENT
            mbo_credentials = Constants.MBO_HEADERS,

            CREATE_DATE = pd.to_datetime(data['submission_datetime'])

            key = "Cancellation {}.pdf".format(CREATE_DATE.strftime("%Y-%m-%d"))
            filepath = '/tmp/{key}'.format(key=key)
            flag = generate_cancellation_pdf(data, key, filepath)
            print(f"generate_cancellation_pdf => {flag}")

            flag = upload_client_document_v2(data, mbo_client, mbo_credentials, key, filepath)

            # Clean Up
            os.remove(filepath)

    except Exception as ex:
        message = f'Error [cancellation_pdf_writer]: {ex}'
        print(message)
        send_error_notification(message)

    finally:
        return


    
def controller(event, context):
    # print(f"PDF Controller:\n{event}")
    print("PDF Controller")

    try:
        
        BODY = json.loads(event['Records'][0]['body'])

        if BODY['type'] == 'enrolmentData':
            print('\n\tStarting Enrolment Contract')
            enrolment_pdf_writer_v2(BODY)
            print('Finished Enrolment Contract\n')

        if BODY['type'] == 'fpCoachEnrolment':
            print('\n\tStarting FP Coach Enrolment Contract')
            coach_pdf_writer(BODY)
            print('Finished FP Coach Enrolment Contract\n')

        if BODY['type'] == 'membershipChangeData':
            print('\n\tStarting Membership Change Contract')
            membership_change_pdf_writer(BODY)
            print('Finished Membership Change Contract\n')

        if BODY['type'] == 'preExData':
            print('\n\tStarting Pre-ex Document')
            pre_ex_pdf_writer(BODY)
            print('Finished Pre-ex Document\n')

        if BODY['type'] == 'cancellationData':
            print('\n\tStarting Cancellation Document')
            cancellation_pdf_writer(BODY)
            print('Finished Cancellation Document\n')

    except Exception as ex:
        print(f'Error [controller] - {ex}')

    finally:
        return


# ###########################################################################
# Version 1 - Depreciated                                                   #
# ###########################################################################
def upload_client_document(docData, key, filepath):

    isSuccess = False

    try:
        with open(filepath, "rb") as pdf_file:
                encoded_string = base64.b64encode(pdf_file.read())

        UPLOAD_CLIENT_DOCUMENT_URL = "https://api.mindbodyonline.com/public/v6/client/uploadclientdocument"
        CONTENT_TYPE = "application/json"

        s = str(encoded_string)
        split = s.split("'")
        
        data = {
            'ClientId': docData['accessKeyNumber'],
            "File": {
                'FileName': key,
                'MediaType': 'application/pdf',
                'Buffer': split[1]
            }
        }
        
        headers = {
            "Api-Key": docData['MBO_API_KEY'],
            "Content-Type": CONTENT_TYPE,
            "SiteId": docData['MBO_SITE_ID'],
            "authorization": docData['TOKEN']
        }

        r = requests.post(url = UPLOAD_CLIENT_DOCUMENT_URL, data = json.dumps(data), headers=headers )
        print(r.json())
        r.raise_for_status()

    except HTTPError as http_err:
        print(f'HTTP error: {http_err} - {r.json()}')
        isSuccess = False
        # return False

    except Exception as err:
        print(f'Error updating client visit: {err}')
        isSuccess = False
        # return False

    else:
        print(r)
        event = r.json()
        j = json.dumps(event)
        upload_client_document_data = json.loads(j)

        print(f'successfully uploaded document: {upload_client_document_data}')
        isSuccess = True
        # return True

    finally:
        return isSuccess


# ################################################################## send email
def send_email(docData, key, filepath):
    print(f'send_email: {filepath} :: {key}')

    try:
        if (docData['locationId'] == '5'):
            header = 'The Bunker Gym'
            company = 'Bunker Gym'
        else:
            header = 'Fitness Playground ' + docData['gymName']
            company = 'Fitness Playground '

        msg = MIMEMultipart('alternative')

        msg['From'] = docData['SENDER_EMAIL_ADDRESS']

        msg['To'] = docData['email']
        msg['Subject'] = "Your Signed Contract with {}.".format(header)
        msg['Date'] = formatdate(localtime=True)


        text = """\
        Hi {},

        It’s official, welcome to the {} family!
        
        As promised, you will find your Membership Summary attached below with your:

        1. Signed Membership Details 

        2. Signed Payment Authorisation

        3. Membership Terms & Conditions
        
        If you are unsure of anything in the attached documentation or need some help to start training please feel free to reply to this email. 
        
        We are so excited to be by your side on your fitness journey and hope to help you reach your goals!
        
        See you soon,
        {}
        Club Manager | {}
        """.format(company, docData['firstName'], docData['clubManager'], header)

        

        html = """\
        <html>
            <head></head>
            <body>
                <h1 style="color: #fcb120">{}</h1>
                <p>Hi {},</p>
                
                <p>It’s official, welcome to the {} family!</p>

                <p>As promised, you will find your Membership Summary attached below with your:</p>

                <ol>
                    <li>Signed Membership Details</li>

                    <li>Signed Payment Authorisation</li>
                    
                    <li>Membership Terms & Conditions</li>
                </ol>
                
                <p>If you are unsure of anything in the attached documentation or need some help to start training please feel free to reply to this email.</p>
                
                <p>We are so excited to be by your side on your fitness journey and hope to help you reach your goals!</p>

                <p>See you soon,<br><strong>{}</strong><br>Club Manager | {}</p>

            </body>
        </html>
        """.format(company, docData['firstName'], header, docData['clubManager'], header)

        part1 = MIMEText(text, 'plain')
        part2 = MIMEText(html, 'html')

        msg.attach(part1)
        msg.attach(part2)


        part = MIMEBase('application', 'octet-stream')
        part.set_payload(open(filepath, "rb").read())
        encoders.encode_base64(part)
        part.add_header('Content-Disposition', "attachment; filename= %s" % key)

        msg.attach(part)

        server_ssl = smtplib.SMTP_SSL('smtp.gmail.com', 465)
        server_ssl.login(docData['SENDER_EMAIL_ADDRESS'], docData['SENDER_EMAIL_PASSWORD'])
        text = msg.as_string()
        server_ssl.sendmail(docData['SENDER_EMAIL_ADDRESS'], docData['email'], text)
        server_ssl.quit()
        print('Report sent :-)')
        return True
    except:  
        print('Something went wrong...')
        return False



# ################################################################## Build PDF v1
def generatePDF(docData, key, filepath):

    try:
        if docData['locationId'] == 5:
            title = 'Bunker Gym'
        else:
            title = 'Fitness Playground'

        topLine = """
        <style>
        body {{
            font-family: 'Open Sans', sans-serif;
            margin: 20px auto;
            width: 100%;
            text-align: center;
            border: 1px solid #fff;
            padding: 10px;
            box-sizing: border-box;
            overflow-y: auto;
        }}

        h1 {{
            color: #fcb120;
            text-transform: uppercase;
            text-align: center;
        }}

        h2,h3,h4,h5 {{
            text-transform: capitalize;
        }}

        h2, p {{
            text-align: left;
        }}

        h3 {{
            color: #fcb120;
        }}

        tr, th, td {{
            text-align: left;
            padding: 10px 10px 5px 10px;
            border: #000000;
        }}

        td {{
            width: 60%;
            margin: 0 10px
        }}

        table {{
            width: 80%;
            margin: 0 auto 10px;
            box-sizing: border-box;
            border-collapse: collapse;
            text-align: left;
        }}

        img {{
            margin: 10px auto;
            padding: 5px;
            max-width: 80%;
            height: auto;
        }}

        .new-page {{
            page-break-before: always;
        }}
        </style>
        <table>
            <tr>
                <td colSpan="2"><h1>{}</h1></td>
            </tr>
            <tr>
                <td><h2>{} {}</h2></td>
            </tr>
            <tr>
                <th>Email</th>
                <th>Phone</th>
            </tr>
            <tr>
                <td>{}</td>
                <td>{}</td>
            </tr>
            <tr>
                <th>Date of Birth</th>
            </tr>
            <tr>
                <td>{}</td>
            </tr>
            <tr>
                <th>Address</th>
            </tr>
            <tr>
                <td colSpan="2">{} {}, {}, {} {}</td>
            </tr>
        </table>
        """.format(title,
                   docData['firstName'],
                   docData['lastName'],
                   docData['email'],
                   docData['phone'],
                   docData['dob'],
                   docData['address1'],
                   docData['address2'],
                   docData['city'],
                   docData['state'].upper(),
                   docData['postcode']
                  )

        if (docData['getAuthorisation'] == "false"):
            paymentTandC = "False"
        else:
            paymentTandC = "True"

        if docData['paymentType'] == 'creditCard':
            paymentDetails = """
            <tr>
                <td><strong>Card Holder</strong></td>
                <td>{}</td>
            </tr>
            <tr>
                <td><strong>Card Number</strong></td>
                <td>**** {}</td>
            </tr>
            <tr>
                <td><strong>Card Expiry</strong></td>
                <td>{}</td>
            </tr>
            <tr>
                <td><strong>Card Holder Address</strong></td>
            </tr>
            <tr>
                <td colSpan="2">{}, {}, {} {}</td>
            </tr>
            """.format(docData['ccHolder'],
                       docData['ccNumber'],
                       docData['ccExpiry'],
                       docData['ccAddress'],
                       docData['ccCity'],
                       docData['ccState'].upper(),
                       docData['ccPostcode'])
        else:
            paymentDetails = """
            <tr>
                <td><strong>Account Name</strong></td>
                <td>{}</td>
            </tr>
            <tr>
                <td><strong>Financial Institution</strong></td>
                <td>{}</td>
            </tr>
            <tr>
                <td><strong>BSB</strong></td>
                <td>{}</td>
            </tr>
            <tr>
                <td><strong>Account Number</strong></td>
                <td>**** {}</td>
            </tr>
            """.format(docData['bdAccountHolderName'], 
                       docData['bdFinancialInstitution'],
                       docData['bdBsb'], 
                       docData['bdAccountNumber'][-4:])


        payment_top = """
        <table>
            <tr>
                <td><h3>Payment Details</h3></td>
            </tr>
            <tr>
                <td><strong>Payment Method</strong></td>
                <td>{}</td>
            </tr>
        """.format(cleanCamelCase(docData['paymentType']))

        payment_bottom = """
            <tr>
                <td colSpan='2'><strong>I authorise payments to be debited from the above payment details:</strong></td>
            </tr>
            <tr>
                <td><strong>Signature</strong></td>
            </tr>
            <tr><td colSpan="2">
                <img src={} alt='' />
            </td></tr>
        </table>
        """.format(docData['paymentAuthSignatureURL'])

        payment = payment_top + paymentDetails + payment_bottom

        details = """
        <div class="new-page">
        <table>
            <tr>
                <td><h3>Membership Details</h3></td>
            </tr>
            <tr>
                <td><strong>Start Date</strong></td>
                <td>{}</td>
            </tr>
            <tr>
                <td><strong>First Membership Billing Date</strong></td>
                <td>{}</td>
            </tr>
            <tr>
                <td><strong>Membership Consultant</strong></td>
                <td>{}</td>
            </tr>
        """.format(
                   docData['startDate'],
                   docData['firstBillingDate'],
                   docData['staffMember'])

        if (docData[ 'trainingStarterPack'] == 'ongoingPersonalTraining'):
            ptStartDate = """
            <tr>
                <td><strong>Personal Training Start Date</strong></td>
                <td>{}</td>
            </tr>
            """.format(docData['personalTrainingStartDate'])
        else:
            ptStartDate = ""


        personalTrainer = """
            <tr>
                <td><strong>Assigned Personal Trainer</strong></td>
                <td>{}</td>
            </tr>
        """.format(docData['personalTrainer'])

        memberships_data = docData['contractNamesToBeActivated'].split(' | ')

        memberships_td = ""
        for m in memberships_data:
            membership = split_hash(m)

            if 'First' in membership[0]:
                memberships_td = memberships_td + "<tr><td>"+membership[0]+"</td><td>$0.00</td></tr>"
            else:
                memberships_td = memberships_td + "<tr><td>"+membership[0]+"</td><td>$"+membership[1]+"</td></tr>"

        if (docData['agreement'] == "false") :
            agreeTandC = "False"
        else:
            agreeTandC = "True"

        memberships = """
        <tr>
            <td><strong>Memberships</strong></td>
        </tr>
        {}

        <tr><td><strong>Notes</strong></td><tr>
        <tr><td colSpan="2">{}</td></tr>

        <tr><td colSpan="2"><i><sup>*</sup>Direct Debits incur $1.50 transaction fee.</i></td></tr>
        <tr><td colSpan="2"><i><sup>*</sup>All cancellations require 30 days notice.</i></td></tr>
        <tr>
            <td colSpan="2"><strong>I have read, understood and agree to the Terms & Conditions</strong></td>
        </tr>
        """.format(memberships_td, docData['notes'])


        if (docData['contractCommitment'] != "[]"):
            noCommit = """
            <tr>
                <td colSpan="2"><strong>I acknowledge this is a minimum term of 26 fortnightly payments</strong></td>
            </tr>
            """
        else:
            noCommit = ""

        if (docData['pt6SessionCommitment'] != "[]"):
            ptCommit = """
            <tr>
                <td colSpan="2"><strong>I commit to a minimum of six (6) paid Personal Training Sessions</strong></td>
            </tr>
            """
        else:
            ptCommit = ""

        if (docData['under18SignatureURL'] is not None):
            under18Signature = """
            <tr>
                <td><strong>Guardian Signature</strong></td>
            </tr>
            <tr><td colSpan="2">
                <img src={} alt='' />
            </td></tr>
            """.format(docData['under18SignatureURL'])
        else:
            under18Signature = ""

        if ( docData['primarySignatureURL'] is not None):
            signature = """
            <tr>
                <td><strong>Signature</strong></td>
            </tr>
            <tr><td colSpan="2">
                <img src={} alt='' />
            </td></tr>
            """.format(docData['primarySignatureURL'])
        else:
            signature = ""

        details_close = """
        </table>
        </div>
        """

        membership = details + ptStartDate + personalTrainer + memberships+ noCommit + ptCommit + under18Signature + signature + details_close

        termsAndConditions = """
        <div class="new-page">
            <h3>Terms & Conditions</h3>
            <table>
                <tr><td>{}</td></tr>
            </table>
        </div>
        """.format(docData['termAndConditions'])

        document = topLine + payment + membership + termsAndConditions

        options = {
            'margin-top': '0.5in',
            'margin-right': '0in',
            'margin-bottom': '0.5in',
            'margin-left': '0in',
            'encoding': "UTF-8",
            'no-outline': None
        }

        pdfkit.from_string(document, filepath, options=options)

        return True
    except:
        return False



# https://wzerlkt2vi.execute-api.ap-southeast-2.amazonaws.com/dev/handler.enrolment_pdf_writer
def enrolment_pdf_writer(event, context):
    # print('enrolment_pdf_writer '+event['body'])
    try:
        message = "SUCCESS"
        flag = False
        statusCode = 200

        enrolment_data = json.loads(event['body'])
        # print(enrolment_data)

        key = "Enrolment {}.pdf".format(enrolment_data['createDate'].split(' ')[0])
        filepath = '/tmp/{key}'.format(key=key)

        flag = generatePDF(enrolment_data, key, filepath)

      # If PDF Writer fails 
        if flag == False:

            print(f'Error writing pdf')
            message = 'Error writing pdf'

            body = {
                "message": message
            }

            response = {
                "statusCode": 500,
                "body": json.dumps(body)
            }
            return response

        # Send email
        flag = send_email(enrolment_data, key, filepath)
        if flag == False:
            print(f'Error emailing pdf')
            message = 'Error emailing pdf '
            statusCode = 500

        # Upload PDF to Mindbody Online
        flag = upload_client_document(enrolment_data, key, filepath)
        
        if flag == False:

            print(f'upload_client_document {flag}')
                # flag = upload_client_document(enrolment_data, key, filepath)            

        if flag == False:
            print(f'Error writing pdf')
            message = message + 'Error writing pdf'
            statusCode = 500

        # Clean Up
        os.remove(filepath)

        body = {
            "message": message
        }

    # return 200 because Calling Service is not waiting for a response
        response = {
            "statusCode": statusCode,
            "body": json.dumps(body)
        }

        return response

    except:
        print(f'Error writing pdf')
        message = message + 'Error writing pdf'
        statusCode = 500
        body = {
            "message": message
        }

    # return 200 because Calling Service is not waiting for a response
        response = {
            "statusCode": statusCode,
            "body": json.dumps(body)
        }



def hello(event, context):
    print(f'Hello World')
    message = 'Hello World'
    statusCode = 200
    body = {
        "message": message
    }

    # SQS_CLIENT.send_message(
    #     QueueUrl=os.getenv('SQS_URL'),
    #     MessageBody=json.dumps(body)
    # )

# return 200 because Calling Service is not waiting for a response
    response = {
        "statusCode": statusCode,
        "body": json.dumps(body)
    }

    return response
       



