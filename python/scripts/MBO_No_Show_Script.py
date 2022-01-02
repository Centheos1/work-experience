import pandas as pd
import numpy as np
import base64
import urllib
import json
import requests
from requests.exceptions import HTTPError
import datetime
import pytz
import calendar
import pymysql
import math

# Email Imports
import smtplib
from pandas import ExcelWriter
from pandas import ExcelFile
from os.path import basename
from email.mime.application import MIMEApplication
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email.utils import COMMASPACE, formatdate
from email import encoders

from dateutil.relativedelta import *

# Firebase Imports
#pip3 install firebase-admin
import os
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from google.oauth2 import service_account
import googleapiclient.discovery

# Build Imports
import timeit
import time

# Constants
class Constants:
    IS_TEST = True
    TEST_DATABASE_CREDENTIALS = 'TEST_DATABASE_CREDENTIALS'
    IS_PRE_LAUNCH = True
    LAUNCH_DATE = '2020-08-17'
    DB_CONNECTION = None
    PDF_BOOKING_POLICY_BUNKER = 'https://static-digital-media.s3-ap-southeast-2.amazonaws.com/pdf/Bunker_Class_Booking_Policy_All_Members.pdf'
    PDF_BOOKING_POLICY_FP = 'https://static-digital-media.s3-ap-southeast-2.amazonaws.com/pdf/FP_Class_Booking_Policy_New_Members.pdf'
    PDF_BOOKING_POLICY_FP_WITH_STATS = 'https://static-digital-media.s3-ap-southeast-2.amazonaws.com/pdf/FP_Class_Booking_Policy_Members.pdf'
    MEDIA_HOW_TO_CANCEL_CLASS = "https://static-digital-media.s3-ap-southeast-2.amazonaws.com/media/FP_Positive_Civilian_First.mp4"
    SOURCE_UID = 'SOURCE_UID'
    SOURCE_BASE_URL = "https://source.fitnessplayground.com.au/v1/source/"
    GET_GYM_DETAILS_URL = 'getAllGyms'
    GET_SOURCE_STAFF_URL = "getStaff"
    FP_GYM_DATA = {}
    EXECUTIVE_EMAILS = []
    DISPUTE_CHARGE_FORM_URL = 'DISPUTE_CHARGE_FORM_URL'
    STATUS_ACTIVE = 'ACTIVE'
    STATUS_SUCCESS = 'SUCCESS'
    STATUS_ERROR = 'ERROR'
    STATUS_SUSPENDED = 'SUSPENDED'
    STATUS_TERMINATED = 'TERMINATED'
    STATUS_DECLINE = 'DECLINE'
    FORGIVE_FREQUENCY_MONTHLY = 'monthly'
    FORGIVE_FREQUENCY_WEEKLY = 'weekly'
    CHARGE_STATUS_UNPROCESSED = 'UNPROCESSED'
    CHARGE_STATUS_NO_SHOW_CHARGE_PENDING = 'NO_SHOW_CHARGE_PENDING'
    CHARGE_STATUS_NO_SHOW_COMPLETE = 'NO_SHOW_COMPLETE'
    CHARGE_STATUS_NO_SHOW_ABORT = 'NO_SHOW_ABORT'
    CHARGE_STATUS_NO_SHOW_ERROR = 'NO_SHOW_ERROR'
    CHARGE_STATUS_NO_SHOW_REFUND = 'NO_SHOW_REFUND'
    CHARGE_STATUS_LATE_CANCEL_CHARGE_PENDING = 'LATE_CANCEL_CHARGE_PENDING'
    CHARGE_STATUS_LATE_CANCEL_COMPLETE = 'LATE_CANCEL_COMPLETE'
    CHARGE_STATUS_LATE_CANCEL_ABORT = 'LATE_CANCEL_ABORT'
    CHARGE_STATUS_LATE_CANCEL_ERROR = 'LATE_CANCEL_ERROR'
    CHARGE_STATUS_LATE_CANCEL_REFUND = 'LATE_CANCEL_REFUND'
    CHARGE_STATUS_SYSTEM_ABORT = 'SYSTEM_ABORT'
    CHARGE_STATUS_FORGIVE = 'FORGIVE'
    OFFENSE_TYPE_NO_SHOW = 'NO_SHOW'
    OFFENSE_TYPE_LATE_CXL = 'LATE_CANCELLED'
    AUTH_SPLIT_CHARACTER = 'AUTH_SPLIT_CHARACTER'
    UI_STAFF_SPLIT = '::'
    CONTENT_TYPE = "application/json"
    MBO_BASE_URL = "https://api.mindbodyonline.com/public/v6/"
    USERTOKEN_URL = "usertoken/issue"
    GET_CLASSES_URL = "class/classes"
    GET_CLASS_VISITS_URL = "class/classvisits"
    GET_CLIENTS_URL = "client/clients"
    GET_SERVICES_URL = "sale/services"
    CHECKOUT_SHOPPING_CART_URL = "sale/checkoutshoppingcart"
    MBO_PAYMENT_TYPE_STORED_CARD = "StoredCard"
    MBO_PAYMENT_TYPE_DIRECT_DEBIT = "DirectDebit"
    MBO_SITE_ID = None
    MBO_TOKEN = None
    MBO_NO_SHOW_API_KEY = None
    MBO_USER_NAME = None
    MBO_PASSWORD = None
    MBO_PRODUCT_NO_SHOW_ID = None
    MBO_PRODUCT_NO_SHOW = {}
    MBO_PRODUCT_LATE_CXL_ID = None
    MBO_PRODUCT_LATE_CXL = {}
    NO_SHOW_DATABASE_HOST = None
    NO_SHOW_DATABASE_DATABASE = None
    NO_SHOW_DATABASE_USERNAME = None
    NO_SHOW_DATABASE_PASSWORD = None
    FORGIVE_NO_SHOW_MAX = -9999 # no_show_forgive_max
    FORGIVE_NO_SHOW_MIN = 9999 # no_show_forgive_min
    FORGIVE_NO_SHOW_FREQUENCY = None # no_show_forgive_frequency
    FORGIVE_LATE_CXL_MIN = 9999 # late_cancel_forgive_min
    FORGIVE_LATE_CXL_MAX = -9999 # late_cancel_forgive_max
    FORGIVE_LATE_CXL_FREQUENCY = None # late_cancel_forgive_frequency
    OFFENSE_TO_BILLING_HOLDING_DAYS = 99999999 # offense_to_billing_hold_days
    WILL_RUN_BILLING = {}
    CLASS_BLACK_LIST = {}
    EMAIL_USER_NAME = None
    EMAIL_PASSWORD = None
    COMUNICATIONS_STATUS_UNPROCESSED = "UNPROCESSED"
    COMUNICATIONS_STATUS_WARNING_EMAIL_SENT = "WARNING_EMAIL_SENT"
    COMUNICATIONS_STATUS_CHARGE_EMAIL_SENT = "CHARGE_EMAIL_SENT"
    COMUNICATIONS_STATUS_EMAIL_ABORT = "EMAIL_ABORT"
    COMUNICATIONS_STATUS_EMAIL_ERROR = "EMAIL_ERROR"
    GOOGLE_APPLICATION_CREDENTIALS_FILE = 'GOOGLE_APPLICATION_CREDENTIALS_FILE'


###########################################
# EMAIL COPY                              #
###########################################

"""
Member communications IF:
    NoShowClient.is_pre_launch is True

Builds plain text email copy

@PARAM - dictionary of paramaters to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - plain text email copy
"""
def WARNING_EMAIL_PRE_LAUNCH_TEXT(params_dict):
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
#     print(link)
    text = f"""\
Hi {params_dict['name-first']},

We noticed a {params_dict['charge type']} for {params_dict['class name']} on {make_datetime_pretty(pd.to_datetime(params_dict['class date time']))}, in {params_dict['class location']} with {params_dict['instructor name']}.

We reached out to you regarding our new Class Booking Policy that’s currently being trialled.

If you missed it, here’s what you need to know…

Under the new Policy:

- Anyone who Late Cancels (within 24 hrs prior to the class start time) more than 2 x per month will be charged $8 for each Late Cancel 

- Anyone who doesn’t show up to a booked class will automatically be charged $15

These policies encourage you to early cancel classes you’re not able to attend, in order to allow other members the opportunity to fill the spot. 

For now, we are in a trial period for the new policy, which is why you will not be charged. But please take this as a reminder.

We want to thank those that have shared their feedback along the way and for your support during the trial period. 

See you in class.

{params_dict['group fitness manager']} | Group Fitness Manager
{params_dict['class location']}"""
    return text

"""
Member communications IF:
    NoShowClient.is_pre_launch is True

Builds HTML email copy

@PARAM - dictionary of paramates to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - HTML email copy
"""
def WARNING_EMAIL_PRE_LAUNCH_HTML(params_dict):
   
    class_datetime = pd.to_datetime(params_dict['class date time'])
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
#     print(link)
    html =  f"""\
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>New Policy Coming Soon</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body style="margin: 10; padding: 0; font-family: Arial, sans-serif; font-size: 1em; line-height: 1.2em;">
       
        <p>Hi {params_dict['name-first']},</p>

        <p>We noticed a {params_dict['charge type']} for {params_dict['class name'].title()} on {make_datetime_pretty(pd.to_datetime(params_dict['class date time']))}, in {params_dict['class location']} with {params_dict['instructor name']}.</p>
        
        <p>We reached out to you regarding our new Class Booking Policy that’s currently being trialled.</p>

        <p>If you missed it, here’s what you need to know…</p>

        <p>Under the new Policy:
        <ul>
            <li>Anyone who Late Cancels (within 24 hrs prior to the class start time) more than 2 x per month will be charged $8 for each Late Cancel</li>
            <li>Anyone who doesn’t show up to a booked class will automatically be charged $15</li>
        </ul></p>

        <p>These policies encourage you to early cancel classes you’re not able to attend, in order to allow other members the opportunity to fill the spot.</p>

        <p>For now, we are in a trial period for the new policy, which is why you will not be charged. But please take this as a reminder.</p>

        <p>We want to thank those that have shared their feedback along the way and for your support during the trial period.</p>
        
        <p>See you in class.</p>

        <p>{params_dict['group fitness manager']} | Group Fitness Manager<br/><b style="color: #fcb120;">{params_dict['class location']}</b></p>

    </body>
 </html>
     """
    return html


"""
Member communications IF:
    NoShowClient.is_pre_launch is False
    AND ChargeBillingLedger.charge_status is Constants.CHARGE_STATUS_FORGIVE
    AND ChargeBillingLedger.charge_type is Constants.OFFENSE_TYPE_LATE_CXL

Builds plain text email copy

@PARAM - dictionary of paramates to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - plain test email copy
"""
def WARNING_EMAIL_LATE_CANCEL_TEXT(params_dict):
    
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
    
    text = f"""\
Hi {params_dict['name-first']},

I noticed a {params_dict['charge type']} for {params_dict['class name']} at {params_dict['class location']} on {make_datetime_pretty(params_dict['class date time'])} with {params_dict['instructor name']}?
 
Firstly, I’d like to thank you for taking the time to cancel your class.

In the future, we would really appreciate it if you could cancel at least 24 hours prior to the class starting. This will mean that you will not be charged and you will give the other members the chance to book into your spot.
 
Please remember that if you Late Cancel (less than 24 hours before the class starts) more than 2 x per month, you will be charged ${params_dict['fee price']} each time.
 
It’s really easy to cancel! In the app, just click on “Profile”, then click “Cancel” for the relevant class.
 
See you next time!

{params_dict['group fitness manager']} | Group Fitness Manager
{params_dict['class location']}"""
    
    return text

"""
Member communications IF:
    NoShowClient.is_pre_launch is False
    AND ChargeBillingLedger.charge_status is Constants.CHARGE_STATUS_FORGIVE
    AND ChargeBillingLedger.charge_type is Constants.OFFENSE_TYPE_LATE_CXL

Builds HTML email copy

@PARAM - dictionary of paramates to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - HTML email copy
"""
def WARNING_EMAIL_LATE_CANCEL_HTML(params_dict):
    
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
    
    html = f"""\
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>No Show Forgive</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body style="margin: 10; padding: 0; font-family: Arial, sans-serif; font-size: 1em; line-height: 1.2em;">
        <p>Hi {params_dict['name-first']},</p>
        
        <p>We noticed a {params_dict['charge type']} for {params_dict['class name']} at {params_dict['class location']} on {make_datetime_pretty(params_dict['class date time'])} with {params_dict['instructor name']}?</p>
 
        <p>Firstly, I’d like to thank you for taking the time to cancel your class.</p>

        <p>In the future, we would really appreciate it if you could cancel at least 24 hours prior to the class starting. This will mean that you will not be charged and you will give the other members the chance to book into your spot.</p>

        <p>Please remember that if you Late Cancel (less than 24 hours before the class starts) more than 2 x per month, you will be charged ${params_dict['fee price']} each time.</p>

        <p>It’s really easy to cancel! In the app, just click on “Profile”, then click “Cancel” for the relevant class.</p>

        <p>See you next time!</p>
        
        <p>{params_dict['group fitness manager']} | Group Fitness Manager<br/><b style="color: #fcb120;">{params_dict['class location']}</b></p>

    </body>
 </html>
"""
    return html


"""
Member communications IF:
    NoShowClient.is_pre_launch is False
    AND ChargeBillingLedger.charge_status is Constants.CHARGE_STATUS_FORGIVE
    AND ChargeBillingLedger.charge_type is Constants.OFFENSE_TYPE_NO_SHOW

Builds plain text email copy

@PARAM - dictionary of paramates to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - plain test email copy
"""
def WARNING_EMAIL_NO_SHOW_TEXT(params_dict):
    
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
    
    text = f"""\
Hi {params_dict['name-first']},

We noticed a {params_dict['charge type']} for {params_dict['class name']} at {params_dict['class location']} on {make_datetime_pretty(params_dict['class date time'])} with {params_dict['instructor name']}?
 
Firstly, I’d like to thank you for taking the time to cancel your class.

In the future, we would really appreciate it if you could cancel at least 24 hours prior to the class starting. This will mean that you will not be charged and you will give the other members the chance to book into your spot.
 
Please remember that if you No Show, you will be charged ${params_dict['fee price']} each time.
 
It’s really easy to cancel! In the app, just click on “Profile”, then click “Cancel” for the relevant class.
 
See you next time!

{params_dict['group fitness manager']} | Group Fitness Manager
{params_dict['class location']}"""
    
    return text



"""
Member communications IF:
    NoShowClient.is_pre_launch is False
    AND ChargeBillingLedger.charge_status is Constants.CHARGE_STATUS_FORGIVE
    AND ChargeBillingLedger.charge_type is Constants.OFFENSE_TYPE_NO_SHOW

Builds HTML email copy

@PARAM - dictionary of paramates to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - HTML email copy
"""
def WARNING_EMAIL_NO_SHOW_HTML(params_dict):
    
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
    
    html = f"""\
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Late Cancel Forgive</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body style="margin: 10; padding: 0; font-family: Arial, sans-serif; font-size: 1em; line-height: 1.2em;">
        <p>Hi {params_dict['name-first']},</p>
        
        <p>We noticed a {params_dict['charge type']} for {params_dict['class name'].title()} at {params_dict['class location']} on {make_datetime_pretty(params_dict['class date time'])} with {params_dict['instructor name']}?</p>
 
        <p>Firstly, I’d like to thank you for taking the time to cancel your class.</p>

        <p>In the future, we would really appreciate it if you could cancel at least 24 hours prior to the class starting. This will mean that you will not be charged and you will give the other members the chance to book into your spot.</p>

        <p>Please remember that if you No Show, you will be charged ${params_dict['fee price']} each time.</p>

        <p>It’s really easy to cancel! In the app, just click on “Profile”, then click “Cancel” for the relevant class.</p>

        <p>See you next time!</p>
        
        <p>{params_dict['group fitness manager']} | Group Fitness Manager<br/><b style="color: #fcb120;">{params_dict['class location']}</b></p>

    </body>
 </html>
""" 
    return html


"""
Member communications IF:
    NoShowClient.is_pre_launch is False
    AND ChargeBillingLedger.charge_status is Constants.CHARGE_STATUS_LATE_CANCEL_CHARGE_PENDING
    AND ChargeBillingLedger.charge_type is Constants.OFFENSE_TYPE_LATE_CANCEL

Builds plain text email copy

@PARAM - dictionary of paramates to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - plain test email copy
"""
def CHARGE_PENDING_EMAIL_LATE_CANCEL_TEXT(params_dict):
    
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
    text = f"""\
Hi {params_dict['name-first']},

We noticed another {params_dict['charge type']} for {params_dict['class name']} at {params_dict['class location']} on {make_datetime_pretty(params_dict['class date time'])} with {params_dict['instructor name']}.

Firstly, thank you for taking the time to cancel your class.

Instead of being charged the full No Show fee you will only be charged ${params_dict['fee price']} because you took the time to cancel.

In the future, we would really appreciate it if you could cancel at least 24 hours prior to the class starting. This will mean that you will not be charged and you will give the other members the chance to book into your spot.

What happens now?
 
1. You will be charged ${params_dict['fee price']}
2. This amount will be processed on {make_date_pretty(params_dict['charge scheduled run date'])}.
3. If you have a valid reason to dispute this charge click here:
{link}
 
*Forms must be submitted within 48 hours.
 
Remember, it’s really easy to cancel! In the app, just click “Profile” then “Cancel” the relevant class.
 
See you in club!

{params_dict['group fitness manager']} | Group Fitness Manager
{params_dict['class location']}"""
    
    return text


"""
Member communications IF:
    NoShowClient.is_pre_launch is False
    AND ChargeBillingLedger.charge_status is Constants.CHARGE_STATUS_LATE_CANCEL_CHARGE_PENDING
    AND ChargeBillingLedger.charge_type is Constants.OFFENSE_TYPE_LATE_CANCEL

Builds HTML email copy

@PARAM - dictionary of paramates to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - HTML email copy
"""
def CHARGE_PENDING_EMAIL_LATE_CANCEL_HTML(params_dict):
    
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
    
    html = f"""\
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Late Cancel Charge</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body style="margin: 10; padding: 0; font-family: Arial, sans-serif; font-size: 1em; line-height: 1.2em;">
        <p>Hi {params_dict['name-first']},</p>
        
        <p>We noticed another {params_dict['charge type']} for {params_dict['class name']} at {params_dict['class location']} on {make_datetime_pretty(params_dict['class date time'])} with {params_dict['instructor name']}.</p>

        <p>Firstly, thank you for taking the time to cancel your class.</p>

        <p>Instead of being charged the full No Show fee you will only be charged ${params_dict['fee price']} because you took the time to cancel.</p>

        <p>In the future, we would really appreciate it if you could cancel at least 24 hours prior to the class starting. This will mean that you will not be charged and you will give the other members the chance to book into your spot.</p>

        <p>What happens now?</p>
        
        <ol>
            <li>You will be charged ${params_dict['fee price']}</li>
            
            <li>This amount will be processed on {make_date_pretty(params_dict['charge scheduled run date'])}.</li>
            
            <li>If you have a valid reason to dispute this charge <a href="{link}">click here.</a></li>
        </ol>
 
        <p><i>*Forms must be submitted within 48 hours.</i></p>
 
        <p>Remember, it’s really easy to cancel! In the app, just click “Profile” then “Cancel” the relevant class.</p>
 
        <p>See you in club!</p>

        <p>{params_dict['group fitness manager']} | Group Fitness Manager<br/><b style="color: #fcb120;">{params_dict['class location']}</b></p>

    </body>
 </html>
"""
    return html


"""
Member communications IF:
    NoShowClient.is_pre_launch is False
    AND ChargeBillingLedger.charge_status is Constants.CHARGE_STATUS_NO_SHOW_CHARGE_PENDING
    AND ChargeBillingLedger.charge_type is Constants.OFFENSE_TYPE_NO_SHOW

Builds plain text email copy

@PARAM - dictionary of paramates to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - plain test email copy
"""
def CHARGE_PENDING_EMAIL_NO_SHOW_TEXT(params_dict):
    
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
    text = f"""\
Hi {params_dict['name-first']},

Oh, no! There was another {params_dict['charge type']} this month.

This time, it was a {params_dict['charge type']} for {params_dict['class name']} at {params_dict['class location']} on {make_datetime_pretty(params_dict['class date time'])} with {params_dict['instructor name']}.

Our classes have limited spots available meaning your spot could have been allocated to one of your fellow classmates wanting to join.
 
In the future, we would really appreciate it if you could cancel more than 24 hours prior to the class starting. This will mean that you will not be charged and you will give another member plenty of opportunity to take your spot.

What happens now?
 
1. You will be charged ${params_dict['fee price']}
2. This amount will be processed on {make_date_pretty(params_dict['charge scheduled run date'])}.
3. If you have a valid reason to dispute this charge click here:
{link}
 
*Forms must be submitted within 48 hours.
 
Remember, it’s really easy to cancel! In the app, just click “Profile” then “Cancel” the relevant class.
 
See you in club!

{params_dict['group fitness manager']} | Group Fitness Manager
{params_dict['class location']}"""
    
    return text


"""
Member communications IF:
    NoShowClient.is_pre_launch is False
    AND ChargeBillingLedger.charge_status is Constants.CHARGE_STATUS_NO_SHOW_CHARGE_PENDING
    AND ChargeBillingLedger.charge_type is Constants.OFFENSE_TYPE_NO_SHOW

Builds HTML email copy

@PARAM - dictionary of paramates to populate the email copy
@DEPENDANTS - db_get_unprocessed_communications() -> send_emails()
@RETURNS: String - HTML email copy
"""
def CHARGE_PENDING_EMAIL_NO_SHOW_HTML(params_dict):
    
    params = urllib.parse.urlencode(params_dict)
    link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
    
    html = f"""\
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>No Show Charge</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body style="margin: 10; padding: 0; font-family: Arial, sans-serif; font-size: 1em; line-height: 1.2em;">
        <p>Hi {params_dict['name-first']},</p>
        
        <p>Oh, no! There was another {params_dict['charge type']} this month.</p>
        
        <p>This time, it was a {params_dict['charge type']} for {params_dict['class name']} at {params_dict['class location']} on {make_datetime_pretty(params_dict['class date time'])} with {params_dict['instructor name']}.</p>
        
        <p>Our classes have limited spots available meaning your spot could have been allocated to one of your fellow classmates wanting to join.</p>

        <p>In the future, we would really appreciate it if you could cancel more than 24 hours prior to the class starting. This will mean that you will not be charged and you will give another member plenty of opportunity to take your spot.</p>

        <p>What happens now?</p>
        
        <ol>
            <li>You will be charged ${params_dict['fee price']}</li>
            
            <li>This amount will be processed on {make_date_pretty(params_dict['charge scheduled run date'])}.</li>
            
            <li>If you have a valid reason to dispute this charge <a href="{link}">click here.</a></li>
        </ol>
 
        <p><i>*Forms must be submitted within 48 hours.</i></p>
 
        <p>Remember, it’s really easy to cancel! In the app, just click “Profile” then “Cancel” the relevant class.</p>
 
        <p>See you in club!</p>

        <p>{params_dict['group fitness manager']} | Group Fitness Manager<br/><b style="color: #fcb120;">{params_dict['class location']}</b></p>

    </body>
 </html>
""" 
    return html


###########################################
# HELPERS                                 #
###########################################

"""
Get Gym Details from Source server

@PARAM - None
@DEPENDANTS
    - details are managed at portal.fitnessplayground.com.au
    - send_emails()
    - get_report_emails()
@RETURNS: Dictionary - Staff, Coaches, Gym Manager, Personal Trainer Manage and Group Fitness Manager details
"""
def get_gym_data():
    PARAMS = {'UID': Constants.SOURCE_UID}

    r = requests.get(url = Constants.SOURCE_BASE_URL + Constants.GET_GYM_DETAILS_URL , params=PARAMS )
    event = r.json()
    j = json.dumps(event)
    data = json.loads(j)

    return data


"""
Get Staff Details from Source server

@PARAM - staff
@DEPENDANTS
    - details are managed at source.fitnessplayground.com.au on a daily scheduled sync
    - get_report_emails()
@RETURNS: Dictionary - MBO Staff
"""
def get_staff_data(s):
    s_split = s.get('mboId').split(Constants.UI_STAFF_SPLIT)

    PARAMS = {'UID': Constants.SOURCE_UID}
    r = requests.get(url = Constants.SOURCE_BASE_URL + "/" + Constants.GET_SOURCE_STAFF_URL + "/" + s_split[1] + "/" + s_split[0], params=PARAMS )
    event = r.json()
    j = json.dumps(event)
    data = json.loads(j)
    return data

"""
Formats date and time to be human readable

@PARAM - datetime
@DEPENDANTS
    - WARNING_EMAIL_PRE_LAUNCH_TEXT()
    - WARNING_EMAIL_PRE_LAUNCH_HTML()
    - WARNING_EMAIL_PROD_TEXT()
    - WARNING_EMAIL_PROD_HTML()
    - CHARGE_PENDING_EMAIL_TEXT()
    - CHARGE_PENDING_EMAIL_HTML()
@RETURNS: String
"""
def make_datetime_pretty(d):
    d = pd.to_datetime(d)
    day = d.strftime('%A') # full day of week
    month = d.strftime('%B') # full month
    date = d.strftime('%d') # date day
    year = d.strftime('%Y') # date year
    hour = d.strftime('%I') # date hour
    minutes = d.strftime('%M') # date minutes
    am_pm = d.strftime('%p').lower() # date hour

    pretty = (f"{day}, {date} {month} {year} at {hour}:{minutes}{am_pm}")
    return pretty

"""
Formats date to be human readable

@PARAM - datetime
@DEPENDANTS
    - WARNING_EMAIL_PRE_LAUNCH_TEXT()
    - WARNING_EMAIL_PRE_LAUNCH_HTML()
    - WARNING_EMAIL_PROD_TEXT()
    - WARNING_EMAIL_PROD_HTML()
    - CHARGE_PENDING_EMAIL_TEXT()
    - CHARGE_PENDING_EMAIL_HTML()
@RETURNS: String
"""
def make_date_pretty(d):
    d = pd.to_datetime(d)
    day = d.strftime('%A') # full day of week
    month = d.strftime('%B') # full month
    date = d.strftime('%d') # date day
    year = d.strftime('%Y') # date year
    
    pretty = (f"{day}, {date} {month} {year}")
    return pretty

"""
Decodes database credentials and sets the Database Constants

@PARAM - trigger event
@DEPENDANTS
    - main()
    - SET_CONSTANTS
@RETURNS: None
"""
def decoded_database_credentials(event):
    
    if Constants.IS_TEST:
        print("TEST DATABASE")
        decoded_database_credentials = base64.b64decode(Constants.TEST_DATABASE_CREDENTIALS).decode("utf-8")
    else:
        decoded_database_credentials = base64.b64decode(event['database_auth_credentials']).decode("utf-8")
        
    split = decoded_database_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
    Constants.NO_SHOW_DATABASE_HOST = split[0]
    Constants.NO_SHOW_DATABASE_DATABASE = split[1]
    Constants.NO_SHOW_DATABASE_USERNAME = split[2]
    Constants.NO_SHOW_DATABASE_PASSWORD = split[3]

"""
Opens Database connection

@PARAM - None
@DEPENDANTS
    - DB_CLOSE_CONNECTION()
    - main()
    - create_transaction_history_record()
    - db_get_no_show_client()
    - db_get_classes()
    - db_get_class_visits()
    - get_processing_list()
    - db_get_unprcessed_billing_list()
    - db_get_unprocessed_communications()
    - db_get_error_communications()
    - db_get_mbo_class_visits()
    - db_get_mbo_classs()
    - db_save_mbo_classes()
    - db_save_mbo_class_visits()
    - db_insert_new_ledger_documents()
    - db_update_ledger_documents()
    - db_update_ledger_document_with_charge()
    - db_save_shopping_cart_response()
@RETURNS: None
"""
def DB_MAKE_CONNECTION():
    if Constants.DB_CONNECTION is None:
        try:
            host = Constants.NO_SHOW_DATABASE_HOST
            database = Constants.NO_SHOW_DATABASE_DATABASE
            user = Constants.NO_SHOW_DATABASE_USERNAME
            passw = Constants.NO_SHOW_DATABASE_PASSWORD
            Constants.DB_CONNECTION = pymysql.connect(host = host, db=database, user=user, password=passw, charset='utf8mb4', cursorclass=pymysql.cursors.DictCursor)
            return True
        except:
            print("Error making connection with database")
            return False

"""
Closes Database connection

@PARAM - None
@DEPENDANTS
    - main()
@RETURNS: None
"""
def DB_CLOSE_CONNECTION():
    try:
        Constants.DB_CONNECTION.close()
    finally:
        Constants.DB_CONNECTION = None
        print("DB Connection is Closed")

"""
Set Constants

@PARAM - NoShowClient
@DEPENDANTS
    - main()
@RETURNS: None
"""
def SET_CONSTANTS(request_body):
    
    if request_body['is_test']:
        Constants.IS_TEST = True
    else:
        Constants.IS_TEST = False
    
    if request_body['is_pre_launch']:
        Constants.IS_PRE_LAUNCH = True
    else:
        Constants.IS_PRE_LAUNCH = False

#     CHECK NULL
    if request_body['mbo_api_credentials'] != None:
        decoded_mbo_credentials = base64.b64decode(request_body['mbo_api_credentials']).decode("utf-8")
        split = decoded_mbo_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
        Constants.MBO_USER_NAME = split[0]
        Constants.MBO_PASSWORD = split[1]
        Constants.MBO_SITE_ID = str(request_body['mbo_site_id'])
        Constants.MBO_NO_SHOW_API_KEY = request_body['mbo_api_key']
        Constants.MBO_TOKEN = mbo_auth_token()
    
    # CHECK NULL
    if request_body['database_auth_credentials'] != None:
        decoded_database_credentials = base64.b64decode(request_body['database_auth_credentials']).decode("utf-8")
        split = decoded_database_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
        Constants.NO_SHOW_DATABASE_HOST = split[0]
        Constants.NO_SHOW_DATABASE_DATABASE = split[1]
        Constants.NO_SHOW_DATABASE_USERNAME = split[2]
        Constants.NO_SHOW_DATABASE_PASSWORD = split[3]

    # CHECK NULL
    if request_body['email_credentials'] != None:
        decoded_email_credentials = base64.b64decode(request_body['email_credentials']).decode("utf-8")
        split = decoded_email_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
        Constants.EMAIL_USER_NAME = split[0]
        Constants.EMAIL_PASSWORD = split[1]
    
    Constants.FORGIVE_NO_SHOW_MAX = request_body['no_show_forgive_max']
    Constants.FORGIVE_NO_SHOW_MIN = request_body['no_show_forgive_min']
    Constants.FORGIVE_NO_SHOW_FREQUENCY = request_body['no_show_forgive_frequency']
    Constants.FORGIVE_LATE_CXL_MAX = request_body['late_cancel_forgive_max']
    Constants.FORGIVE_LATE_CXL_MIN = request_body['late_cancel_forgive_min']
    Constants.FORGIVE_LATE_CXL_FREQUENCY = request_body['late_cancel_forgive_frequency']
    Constants.OFFENSE_TO_BILLING_HOLDING_DAYS = request_body['offense_to_billing_hold_days']

    will_run_billing = {}

#     This splits a JSON block of the individual locations and their billing status
    split = request_body['will_run_billing'].split(",")
    for x in split:
        y = x.split(':')
        if y[1].casefold() == 'true':
            will_run_billing[y[0]] = True
        else:
            will_run_billing[y[0]] = False

    Constants.WILL_RUN_BILLING = will_run_billing
    
    try:
        if request_body['class_black_list'] is not None:
            Constants.CLASS_BLACK_LIST = json.loads(request_body['class_black_list'])
    except:
        print('Error - SETTING CLASS_BLACK_LIST')
    
    Constants.MBO_PRODUCT_NO_SHOW_ID = request_body['mbo_no_show_product_id']
    Constants.MBO_PRODUCT_LATE_CXL_ID = request_body['mbo_late_cancel_product_id']


###########################################
# WORKERS                                 #
###########################################

"""
Builds new ChargeBillingLedger entity

@PARAMS
    - mbo_clients
    - db_no_show_visits OR db_late_cancel_visits
    - charge_type
@DEPENDANTS
    - main()
@RETURNS: ChargeBillingLedger document
"""
def create_charge_billing_ledger_documents(mbo_clients, df, charge_type):

    ledger_items = pd.DataFrame()
    now = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S")

    for index, row in df.iterrows():
        try:

            # CHECK IF ON CLASS BLACK LIST
            if Constants.CLASS_BLACK_LIST.get(row['session_type_name']) is None:

                item = {}
                item['index_key'] = str(Constants.MBO_SITE_ID) + Constants.AUTH_SPLIT_CHARACTER + str(mbo_clients[mbo_clients['Id'] == row['client_id']]['UniqueId'].values[0])
                item['mbo_site_id'] = int(Constants.MBO_SITE_ID)
                item['mbo_shopping_cart_id'] = None
                item['mbo_class_visit_id'] = int(row['visit_id'])
                item['mbo_class_id'] = int(row['class_id'])
                item['mbo_client_unique_id'] = int(mbo_clients[mbo_clients['Id'] == row['client_id']]['UniqueId'].values[0])
                item['mbo_client_id'] = row['client_id']
                item['mbo_instructor_id'] = int(row['staff_id'])
                item['charge_type'] = charge_type
                item['charge_status'] = Constants.CHARGE_STATUS_UNPROCESSED
                item['charge_scheduled_run_date'] = pd.to_datetime(datetime.datetime.combine(row['start_date_time'] + datetime.timedelta(Constants.OFFENSE_TO_BILLING_HOLDING_DAYS), datetime.time.min))
                item['client_first_name'] = mbo_clients[mbo_clients['Id'] == row['client_id']]['FirstName'].values[0]
                item['client_last_name'] = mbo_clients[mbo_clients['Id'] == row['client_id']]['LastName'].values[0]
                item['client_email'] = mbo_clients[mbo_clients['Id'] == row['client_id']]['Email'].values[0]
                item['client_phone'] = mbo_clients[mbo_clients['Id'] == row['client_id']]['MobilePhone'].values[0]

                if mbo_clients[mbo_clients['Id'] == row['client_id']]['ClientCreditCard'].values[0] is None:
                    item['client_payment_type'] = Constants.MBO_PAYMENT_TYPE_DIRECT_DEBIT
                    item['client_credit_card_last_four'] = None
                elif mbo_clients[mbo_clients['Id'] == row['client_id']]['ClientCreditCard'].values[0].get('LastFour') == '1111':
                    item['client_payment_type'] = Constants.MBO_PAYMENT_TYPE_DIRECT_DEBIT
                    item['client_credit_card_last_four'] = None
                else:
                    item['client_payment_type'] = Constants.MBO_PAYMENT_TYPE_STORED_CARD
                    item['client_credit_card_last_four'] = mbo_clients[mbo_clients['Id'] == row['client_id']]['ClientCreditCard'].values[0].get('LastFour')

                item['class_name'] = row['class_name']
                item['class_instructor'] = row['staff_name']
                item['class_location_id'] = int(row['location_id'])
                item['class_location_name'] = row['class_location_name']
                item['class_start_datetime'] = row['start_date_time']
                item['class_week_of_year'] = row['start_date_time'].isocalendar()[1]
                item['class_year_month'] = row['start_date_time'].strftime("%Y-%m")
                item['class_day_of_week'] = row['start_date_time'].strftime("%A")
                item['session_type_name'] = row['session_type_name']

                if np.isnan(row['session_type_id']):
                    item['session_type_id'] = None
                else:
                    item['session_type_id'] = int(row['session_type_id'])

                item['communications_status'] = Constants.COMUNICATIONS_STATUS_UNPROCESSED

                item['createDate'] = datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S")
                item['updateDate'] = datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S")

                ledger_items = ledger_items.append(item, ignore_index=True)
        except:
            print(f"Error create_charge_billing_ledger_documents - mboId: {row['client_id']}")

    if len(ledger_items) > 0:
        ledger_items['class_start_datetime'] = ledger_items['class_start_datetime'].astype(str)
        ledger_items['charge_scheduled_run_date'] = ledger_items['charge_scheduled_run_date'].astype(str)

    return ledger_items

"""
Adds an entry to the transaction record log

@PARAMS
    - createDate
    - charge_billing_ledger_id
    - new_status, old_status
    - message
@DEPENDANTS
    - db_insert_new_ledger_documents()
    - db_update_ledger_documents
    - db_update_ledger_document_with_charge

@RETURNS: ChargeBillingLedger document
"""
def create_transaction_history_record(createDate, charge_billing_ledger_id, new_status, old_status, message):

    with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """INSERT INTO TransactionHistory (
                `createDate`,
                `charge_billing_ledger_id`,
                `new_status`,
                `old_status`,
                `message`)
                VALUES (%s,%s,%s,%s,%s)
                """
                cursor.execute(sql, (createDate,
                                     charge_billing_ledger_id,
                                     new_status,
                                     old_status,
                                     message))
    Constants.DB_CONNECTION.commit()


"""
CHARGE PROCESSING

Gets list of ChargeBillingLedger documents that are
unprocessed for the day passed and
updates the charge_status
based on the NoShowClient configuration

@PARAM - datetime
@DEPENDANTS - main()
@RETURNS: None
"""
def process_class_charges(d):
    
    d = pd.to_datetime(d)

    # Process No Shows
    no_show_process_list = get_processing_list(d, Constants.OFFENSE_TYPE_NO_SHOW)

    if len(no_show_process_list) > 0:

        list_of_offenders = pd.unique(no_show_process_list['index_key'])
        print(f"Processing {len(list_of_offenders)} NoShow offenders")

        for i in range(len(list_of_offenders)):
            x = no_show_process_list[no_show_process_list['index_key'] == list_of_offenders[i]].sort_values('class_start_datetime')
            offense_count = 0
            for index, row in x.iterrows():
                if row['charge_status'] != Constants.CHARGE_STATUS_NO_SHOW_ABORT and row['charge_status'] != Constants.CHARGE_STATUS_NO_SHOW_REFUND:
                    offense_count = offense_count + 1
        
                if row['charge_status'] == Constants.CHARGE_STATUS_UNPROCESSED:
                    if offense_count <= Constants.FORGIVE_NO_SHOW_MIN:
                        no_show_process_list.loc[no_show_process_list['id'] == row['id'], 'charge_status'] = Constants.CHARGE_STATUS_FORGIVE
                        print(f"{row['id']}: FORGIVE NO SHOW: {row['charge_status']} | offence count: {offense_count}")
                        
                    elif offense_count >= Constants.FORGIVE_NO_SHOW_MAX:
                        no_show_process_list.loc[no_show_process_list['id'] == row['id'], 'charge_status'] = Constants.CHARGE_STATUS_FORGIVE
                        print(f"{row['id']}: FORGIVE NO SHOW: {row['charge_status']} | offence count: {offense_count}")

                    else:
                        print(f"{row['id']}: ADD OFFENCE NO SHOW: {row['charge_status']} | offence count: {offense_count}")
                        no_show_process_list.loc[no_show_process_list['id'] == row['id'], 'charge_status'] = Constants.CHARGE_STATUS_NO_SHOW_CHARGE_PENDING

        db_update_ledger_documents(no_show_process_list, 'process_class_charges')

    # Process Late Cancels
    late_cxl_process_list = get_processing_list(d, Constants.OFFENSE_TYPE_LATE_CXL)

    if len(late_cxl_process_list) > 0:

        list_of_offenders = pd.unique(late_cxl_process_list['index_key'])
        print(f"Processing {len(list_of_offenders)} LateCxl offenders")

        for i in range(len(list_of_offenders)):
            x = late_cxl_process_list[late_cxl_process_list['index_key'] == list_of_offenders[i]].sort_values('class_start_datetime')
            offense_count = 0
            for index, row in x.iterrows():
                if row['charge_status'] != Constants.CHARGE_STATUS_LATE_CANCEL_ABORT and row['charge_status'] != Constants.CHARGE_STATUS_LATE_CANCEL_REFUND:
                    offense_count = offense_count + 1
        
                if row['charge_status'] == Constants.CHARGE_STATUS_UNPROCESSED:
                    if offense_count <= Constants.FORGIVE_LATE_CXL_MIN:
                        late_cxl_process_list.loc[late_cxl_process_list['id'] == row['id'], 'charge_status'] = Constants.CHARGE_STATUS_FORGIVE
                        print(f"{row['id']}: FORGIVE LATE CANCEL: {row['charge_status']} | offence count: {offense_count}")

                    elif offense_count >= Constants.FORGIVE_LATE_CXL_MAX:
                        late_cxl_process_list.loc[late_cxl_process_list['id'] == row['id'], 'charge_status'] = Constants.CHARGE_STATUS_FORGIVE
                        print(f"{row['id']}: FORGIVE LATE CANCEL: {row['charge_status']} | offence count: {offense_count}")

                    else:
                        late_cxl_process_list.loc[late_cxl_process_list['id'] == row['id'], 'charge_status'] = Constants.CHARGE_STATUS_LATE_CANCEL_CHARGE_PENDING
                        print(f"{row['id']}: ADD OFFENCE LATE CANCEL: {row['charge_status']} | offence count: {offense_count}")

        db_update_ledger_documents(late_cxl_process_list, 'process_class_charges')


"""
PRCOESS BILLING

Gets list of ChargeBillingLedger documents that have:
    - CHARGE_PENDING_STATUS (NoShow or LateCancel)
    - charge_scheduled_run_date <= datetime parameter := event end_date


@PARAM - datetime
@DEPENDANTS - main()
@RETURNS: None
"""
def process_billing(d):
    unprocessed_billing_list = db_get_unprcessed_billing_list(d)
    
    counter = 0
    print(f"unprocessed_billing_list length: {len(unprocessed_billing_list)}")
    
    mbo_get_services()

# ######################################################## TESTING ONLY
#     for index, row in unprocessed_billing_list.head(1).iterrows():
# ######################################################## TESTING ONLY

# ######################################################## PRODUCTION ONLY
    for index, row in unprocessed_billing_list.iterrows():
# ######################################################## PRODUCTION ONLY
    
        previous_charge_status = row['charge_status']

        # PRE-LAUNCH
        if Constants.IS_PRE_LAUNCH == True:
            row['charge_status'] = Constants.CHARGE_STATUS_SYSTEM_ABORT
            
            db_update_ledger_document_with_charge(row, previous_charge_status, 'Pre-Launch billing is turned off')

        elif Constants.WILL_RUN_BILLING[row['class_location_id']] == True:
            counter += 1
            print(f"{counter} RUN BILLING {row['class_location_id']} | {row['charge_type']} | {row['charge_status']} | id: {row['id']}")

            response = mbo_execute_charge(row)
            
            if Constants.IS_TEST:
                print(f"[process_billing] -> mbo_execute_charge response\n{response}\n")

            try:
                # Error executing charge - Ledger Status needs to be ERROR
                if response is None:
                    print("\t[mbo_execute_charge] Response is None")

                    # UPDATE LEDGER - ERROR message
                    if row['charge_type'] == Constants.OFFENSE_TYPE_NO_SHOW:
                        row['charge_status'] = Constants.CHARGE_STATUS_NO_SHOW_ERROR

                    if row['charge_type'] == Constants.OFFENSE_TYPE_LATE_CXL:
                        row['charge_status'] = Constants.CHARGE_STATUS_LATE_CANCEL_ERROR

                    # SAVE UPDATED LEDGER
                    db_update_ledger_document_with_charge(row, previous_charge_status, "MBO checkout shopping cart respone was null")

                else:
                    try:
                        # Charge executed successfully
                        shopping_cart_response = response['ShoppingCart']
#                        print(f"Success {shopping_cart_response}")
                        row['mbo_shopping_cart_id'] = shopping_cart_response['Id']
    
#                         if Constants.IS_TEST:
#                             print(f" response: {response}")

                        # UPDATE LEDGER - default message
                        if row['charge_type'] == Constants.OFFENSE_TYPE_NO_SHOW:
                            row['charge_status'] = Constants.CHARGE_STATUS_NO_SHOW_COMPLETE

                        if row['charge_type'] == Constants.OFFENSE_TYPE_LATE_CXL:
                            row['charge_status'] = Constants.CHARGE_STATUS_LATE_CANCEL_COMPLETE

                        # SAVE UPDATED LEDGER
                        db_update_ledger_document_with_charge(row, previous_charge_status)

                        # SAVE SHOPPING CART RESPONSE
                        db_save_shopping_cart_response(shopping_cart_response)
                    except:
                
                        shopping_cart_response = response['Error']
                        print(f"[mbo_execute_charge] Error {shopping_cart_response}")

                        # UPDATE LEDGER - ERROR message
                        if row['charge_type'] == Constants.OFFENSE_TYPE_NO_SHOW:
                            row['charge_status'] = Constants.CHARGE_STATUS_NO_SHOW_ERROR

                        if row['charge_type'] == Constants.OFFENSE_TYPE_LATE_CXL:
                            row['charge_status'] = Constants.CHARGE_STATUS_LATE_CANCEL_ERROR
 
                        # SAVE UPDATED LEDGER
                        db_update_ledger_document_with_charge(row, previous_charge_status, shopping_cart_response['Message'])

            except:
                print(f"Error during Billing Ledger Id: {row[id]}")
        else:
            print(f"NO BILLING | {row['id']} {row['class_location_id']} | {row['charge_type']} | {row['charge_status']}")
            row['charge_status'] = Constants.CHARGE_STATUS_SYSTEM_ABORT
            db_update_ledger_document_with_charge(row, previous_charge_status, 'Billing is turned off')


"""
Builds the emails to be sent

@PARAM: Dictionary - ChargeBillingLeger documents with communications_status = UNPROCESSED
@DEPENDANTS - main()
@RETURNS: None
"""
def send_emails(unprocessed_comms):
    my_email= Constants.EMAIL_USER_NAME
    my_email_pass = Constants.EMAIL_PASSWORD
    #print(f"{my_email} {my_email_pass}")
    
    Constants.FP_GYM_DATA = get_gym_data()
    counter = 0
    print(f"Sending {len(unprocessed_comms)} emails")
    
####################################################### TESTING ONLY
#     for index, row in unprocessed_comms.head(2).iterrows():
####################################################### TESTING ONLY

# ######################################################## PRODUCTION ONLY
    for index, row in unprocessed_comms.iterrows():
# ######################################################## PRODUCTION ONLY

        params_dict = {}
        html = None
        text = None
        subject = "Class Charge Notification"
        
        if row['charge_type'] == Constants.OFFENSE_TYPE_NO_SHOW:
            pretty_charge_type = "No Show"
            
        if row['charge_type'] == Constants.OFFENSE_TYPE_LATE_CXL:
            pretty_charge_type = "Late Cancel"
        
        gfm = row['class_instructor']
        for gym in Constants.FP_GYM_DATA:
            if str(gym['locationId']) == row['class_location_id']:
                gfm = gym['groupFitnessManager']['name']
        
        fee_price = 0
        if row['charge_type'] == Constants.OFFENSE_TYPE_NO_SHOW:
            fee_price = '{0:.2f}'.format(Constants.MBO_PRODUCT_NO_SHOW['Price'])
        elif row['charge_type'] == Constants.OFFENSE_TYPE_LATE_CXL:
            fee_price = '{0:.2f}'.format(Constants.MBO_PRODUCT_LATE_CXL['Price'])
        
        pdf = None
        if int(row['class_location_id']) == 5:
            pdf = Constants.PDF_BOOKING_POLICY_BUNKER
        else:
            pdf = Constants.PDF_BOOKING_POLICY_FP
        
        params_dict['_ledger_id'] = row['id']
        params_dict['_index_key'] = row['index_key']
        params_dict['_location_id'] = row['class_location_id']
        params_dict['_class_visit_id'] = row['mbo_class_visit_id']
        params_dict['name-first'] = row['client_first_name']
        params_dict['name-last'] = row['client_last_name']
        params_dict['email'] = row['client_email']
        params_dict['phone'] = row['client_phone']
        params_dict['access key number'] = row['mbo_client_id']
        params_dict['class name'] = row['class_name'].title()
        params_dict['class date time'] = row['class_start_datetime']
        params_dict['class location'] = row['class_location_name']
        params_dict['charge type'] = pretty_charge_type
        params_dict['instructor name'] = row['class_instructor']
        params_dict['charge scheduled run date'] = row['charge_scheduled_run_date']
        params_dict['group fitness manager'] = gfm
        params_dict['fee price'] = fee_price
        params_dict['pdf_attachment'] = pdf
        params_dict['media_attachment'] = Constants.MEDIA_HOW_TO_CANCEL_CLASS
        
        params = urllib.parse.urlencode(params_dict)
        link = Constants.DISPUTE_CHARGE_FORM_URL +"?" + params
        
#         PRE LAUNCH
        if Constants.IS_PRE_LAUNCH:
            subject = "Just a warning."
            html = WARNING_EMAIL_PRE_LAUNCH_HTML(params_dict)
            text = WARNING_EMAIL_PRE_LAUNCH_TEXT(params_dict)
            print(f"WARNING EMAIL - PRE_LAUNCH {row['charge_status']} | {row['charge_type']}")
        else:
    #         LATE CANCEL
            if row['charge_type'] == Constants.OFFENSE_TYPE_LATE_CXL:
    #             FORGIVE STATUS
                if row['charge_status'] == Constants.CHARGE_STATUS_FORGIVE:
                    subject = "Your cancelled class"
                    html = WARNING_EMAIL_LATE_CANCEL_HTML(params_dict)
                    text = WARNING_EMAIL_LATE_CANCEL_TEXT(params_dict)
                    print(f"WARNING EMAIL - FORGIVE {row['charge_status']} | {row['charge_type']}")
    #             LATE CANCEL CHARGE
                if row['charge_status'] == Constants.CHARGE_STATUS_LATE_CANCEL_CHARGE_PENDING:
                    print("CHARGE PENDING EMAIL LATE CANCEL - PROD")
                    subject = "You missed your class"
                    html = CHARGE_PENDING_EMAIL_LATE_CANCEL_HTML(params_dict)
                    text = CHARGE_PENDING_EMAIL_LATE_CANCEL_TEXT(params_dict)

    #         NO SHOW
            if row['charge_type'] == Constants.OFFENSE_TYPE_NO_SHOW:
    #             FORGIVE STATUS
                if row['charge_status'] == Constants.CHARGE_STATUS_FORGIVE:
                    subject = "You missed your class"
                    html = WARNING_EMAIL_NO_SHOW_HTML(params_dict)
                    text = WARNING_EMAIL_NO_SHOW_TEXT(params_dict)
                    print(f"WARNING EMAIL - FORGIVE {row['charge_status']} | {row['charge_type']}")
    #             NO SHOW CHARGE
                if row['charge_status'] == Constants.CHARGE_STATUS_NO_SHOW_CHARGE_PENDING:
                    print("CHARGE PENDING EMAIL NO SHOW - PROD")
                    subject = "You missed your class"
                    html = CHARGE_PENDING_EMAIL_NO_SHOW_HTML(params_dict)
                    text = CHARGE_PENDING_EMAIL_NO_SHOW_TEXT(params_dict)

                
        if html is not None and text is not None:
            to_email = row['client_email']
            is_email_sent = execute_send_email(text, html, to_email, subject, my_email, my_email_pass)
            
            counter += 1
            print(f"{counter} {is_email_sent}")
            if is_email_sent:
                #print("Update ledger communications status")
                if row['charge_status'] == Constants.CHARGE_STATUS_FORGIVE:
                    unprocessed_comms.at[index, 'communications_status'] = Constants.COMUNICATIONS_STATUS_WARNING_EMAIL_SENT
                else:
                    unprocessed_comms.at[index, 'communications_status'] = Constants.COMUNICATIONS_STATUS_CHARGE_EMAIL_SENT

            else:
                unprocessed_comms.at[index, 'communications_status'] = Constants.COMUNICATIONS_STATUS_EMAIL_ERROR
                      
    db_update_ledger_documents(unprocessed_comms, 'communications update')


"""
Send emails

@PARAM:
    - String: plain text email copy
    - String: HTML email copy
    - String: to email address
    - String: email subject
    - String: from email address
    - String: from email password
@DEPENDANTS - send_emails()
@RETURNS: Boolean - email send status
"""
def execute_send_email(text, html, to_email, subject, my_email, my_email_pass):
    try:
        msg = MIMEMultipart('alternative')

        if Constants.IS_TEST:
            to_email = "clint@thefitnessplayground.com.au"
            print(f"{to_email}")
            
        msg['From'] = my_email

        msg['To'] = to_email
        msg['Subject'] = subject
        msg['Date'] = formatdate(localtime=True)

#THIS IS THROWING AND Atrribute error about encoding
    #    part1 = MIMEText(text, 'plain')
        part2 = MIMEText(html, 'html')

    #    msg.attach(part1)
        msg.attach(part2)

        server_ssl = smtplib.SMTP_SSL('smtp.gmail.com', 465)
        server_ssl.login(my_email, my_email_pass)
        text = msg.as_string()
        server_ssl.sendmail(my_email, to_email, text)
        server_ssl.quit()
        print('Email sent: {}'.format(to_email))
        return True
    except:
        print('Something went wrong sending email to: {}'.format(to_email))
        return False


###########################################
# DATABASE FUNCTIONS                      #
###########################################

# GETTERS
def db_get_no_show_client(event):
    
    no_show_client = None
    query = "SELECT * FROM NoShowClient WHERE mbo_site_id = '{}' AND database_auth_credentials = '{}'".format(event['mbo_site_id'], event['database_auth_credentials'])

    try:
        no_show_client = pd.read_sql(query, con=Constants.DB_CONNECTION)
    except:
        print("Error getting No Show Client")

#     TODO - This is throwing a warning
    return no_show_client.to_dict('r')[0]

def db_get_classes(start_date, end_date):

    mbo_classes = None
    start_date = pd.to_datetime(start_date)
    end_date = pd.to_datetime(end_date)

    query = """\
     SELECT * FROM MboClass
     WHERE (start_date_time BETWEEN '{}' AND '{}')
     AND mbo_site_id = '{}'
     AND schedule_type_name = 'Class'
    """.format(start_date, end_date, Constants.MBO_SITE_ID)
    
    try:
        mbo_classes = pd.read_sql(query, con=Constants.DB_CONNECTION)
    except:
        print("Error getting DB MBO Classes")
    return mbo_classes

def db_get_class_visits(start_date, end_date, offense_type):
  
    mbo_class_visits = None
    start_date = pd.to_datetime(start_date)
    end_date = pd.to_datetime(end_date)
    
    try:
        if offense_type == Constants.OFFENSE_TYPE_NO_SHOW:
            query = """\
             SELECT * FROM MboVisit
             WHERE start_date_time BETWEEN '{}' AND '{}'
             AND (NOT late_cancelled AND NOT signed_in)
             AND mbo_site_id = '{}'
            """.format(start_date, end_date, Constants.MBO_SITE_ID)

        elif offense_type == Constants.OFFENSE_TYPE_LATE_CXL:
            query = """\
             SELECT * FROM MboVisit
             WHERE start_date_time BETWEEN '{}' AND '{}'
             AND (late_cancelled)
             AND mbo_site_id = '{}'
            """.format(start_date, end_date, Constants.MBO_SITE_ID)
        else:
            query = None

        if query is not None:
            mbo_class_visits = pd.read_sql(query, con=Constants.DB_CONNECTION)
    
    except:
        print("Error getting MBO Class Visits")

    return mbo_class_visits

def get_processing_list(d, offense_type):
    
    try:
        return_df = None

        # No Show Processing List
        if offense_type == Constants.OFFENSE_TYPE_NO_SHOW:

            if Constants.FORGIVE_NO_SHOW_FREQUENCY == Constants.FORGIVE_FREQUENCY_MONTHLY:

                time_param = d.strftime("%Y-%m")

                query = """SELECT * FROM ChargeBillingLedger
                WHERE class_year_month = '{}'
                AND charge_type = '{}'
                AND mbo_site_id = {}
                """.format(time_param, Constants.OFFENSE_TYPE_NO_SHOW, Constants.MBO_SITE_ID)

                return_df = pd.read_sql(query, con=Constants.DB_CONNECTION)

            if Constants.FORGIVE_NO_SHOW_FREQUENCY == Constants.FORGIVE_FREQUENCY_WEEKLY:
                query = """SELECT * FROM ChargeBillingLedger
                WHERE class_week_of_year = {}
                AND charge_type = '{}'
                AND mbo_site_id = {}
                """.format(d.isocalendar()[1], Constants.OFFENSE_TYPE_NO_SHOW, Constants.MBO_SITE_ID)

                return_df = pd.read_sql(query, con=Constants.DB_CONNECTION)

        # Late Cancel Processing List
        if offense_type == Constants.OFFENSE_TYPE_LATE_CXL:

            if Constants.FORGIVE_LATE_CXL_FREQUENCY == Constants.FORGIVE_FREQUENCY_MONTHLY:

                time_param = d.strftime("%Y-%m")

                query = """SELECT * FROM ChargeBillingLedger
                WHERE class_year_month = '{}'
                AND charge_type = '{}'
                AND mbo_site_id = {}
                """.format(time_param, Constants.OFFENSE_TYPE_LATE_CXL, Constants.MBO_SITE_ID)

                return_df = pd.read_sql(query, con=Constants.DB_CONNECTION)

            if Constants.FORGIVE_LATE_CXL_FREQUENCY == Constants.FORGIVE_FREQUENCY_WEEKLY:
                query = """SELECT * FROM ChargeBillingLedger
                WHERE class_week_of_year = {}
                AND charge_type = '{}'
                AND mbo_site_id = {}
                """.format(d.isocalendar()[1], Constants.OFFENSE_TYPE_LATE_CXL, Constants.MBO_SITE_ID)

                return_df = pd.read_sql(query, con=Constants.DB_CONNECTION)
    except:
        print("Error getting charge processing list")
        
    return return_df

def db_get_unprcessed_billing_list(d):
    
    d = pd.to_datetime(d).strftime('%Y-%m-%d')
    unprocessed_billing_list = None

    try:
        query = """\
         SELECT * FROM ChargeBillingLedger
        WHERE charge_status IN ('{}','{}') AND charge_scheduled_run_date <= '{}';
        """.format(Constants.CHARGE_STATUS_LATE_CANCEL_CHARGE_PENDING, Constants.CHARGE_STATUS_NO_SHOW_CHARGE_PENDING, d)

        unprocessed_billing_list = pd.read_sql(query, con=Constants.DB_CONNECTION)

    except:
        print("Error getting unprocessed billing list")

    return unprocessed_billing_list

def db_get_unprocessed_communications():
  
    unprocessed_comms = None
    
    try:
        
        query = """SELECT * FROM ChargeBillingLedger
        WHERE communications_status = '{}';""".format(Constants.COMUNICATIONS_STATUS_UNPROCESSED)
        unprocessed_comms = pd.read_sql(query, con=Constants.DB_CONNECTION)

    except:
        print("Error getting unprocessed communications")

    return unprocessed_comms
    

def db_get_error_communications():
  
    unprocessed_comms = None
    try:
        
        query = """SELECT * FROM ChargeBillingLedger
        WHERE communications_status = '{}';""".format(Constants.COMUNICATIONS_STATUS_EMAIL_ERROR)
        unprocessed_comms = pd.read_sql(query, con=Constants.DB_CONNECTION)
    
    except:
        print("Error getting error communications")

    return unprocessed_comms

def db_get_mbo_class_visits(start_date_time, end_date_time):
  
    data = None
    
    try:
        query = """\
         SELECT * FROM MboVisit
         WHERE start_date_time >= '{}'
         AND start_date_time <= '{}'
        """.format(start_date_time, end_date_time)

        data = pd.read_sql(query, con=Constants.DB_CONNECTION)

    except:
        print("Error getting DB MBO Class Visits")

    return data


def db_get_mbo_classs(start_date_time, end_date_time):
  
    data = None
    
    try:
        query = """\
         SELECT * FROM MboClass
         WHERE start_date_time >= '{}'
         AND start_date_time <= '{}'
        """.format(start_date_time, end_date_time)

        data = pd.read_sql(query, con=Constants.DB_CONNECTION)

    except:
        print("Error getting DB MBO Classs")

    return data

# SETTERS
def db_save_mbo_classes(mbo_classes):

    for index, row in mbo_classes.iterrows():
        query = "SELECT * FROM MboClass WHERE class_id = {} AND mbo_site_id = '{}'".format(row['Id'], Constants.MBO_SITE_ID)
        mbo_class = pd.read_sql(query, con=Constants.DB_CONNECTION)

        if mbo_class.empty:
            with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """INSERT INTO MboClass (
                `createDate`,
                `class_schedule_id`,
                `location_id`,
                `class_location_name`,
                `max_capacity`,
                `total_booked`,
                `total_booked_waitlist`,
                `web_booked`,
                `is_canceled`,
                `substitute`,
                `active`,
                `is_waitlist_available`,
                `class_id`,
                `is_available`,
                `start_date_time`,
                `end_date_time`,
                `class_name`,
                `session_type_name`,
                `session_type_id`,
                `staff_name`,
                `staff_id`,
                `mbo_site_id`,
                `schedule_type_name`,
                `program_name`)
                VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
                """
                # 23 columns
                cursor.execute(sql, (datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"),
                                     row['ClassScheduleId'],
                                     row['LocationId'],
                                     row['LocationName'],
                                     row['MaxCapacity'],
                                     row['TotalBooked'],
                                     row['TotalBookedWaitlist'],
                                     row['WebBooked'],
                                     row['IsCanceled'],
                                     row['Substitute'],
                                     row['Active'],
                                     row['IsWaitlistAvailable'],
                                     row['Id'],
                                     row['IsAvailable'],
                                     row['StartDateTime'],
                                     row['EndDateTime'],
                                     row['Class Name'],
                                     row['Session Type Name'],
                                     row['Session Type Id'],
                                     row['Instructor Name'],
                                     row['Instructor Id'],
                                     Constants.MBO_SITE_ID,
                                     row['Schedule Type'],
                                     row['Program Name']
                                    ))
            Constants.DB_CONNECTION.commit()
        else:
            with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """UPDATE MboClass SET
                `createDate` = %s,
                `class_schedule_id` = %s,
                `location_id` = %s,
                `class_location_name` = %s,
                `max_capacity` = %s,
                `total_booked` = %s,
                `total_booked_waitlist` = %s,
                `web_booked` = %s,
                `is_canceled` = %s,
                `substitute` = %s,
                `active` = %s,
                `is_waitlist_available` = %s,
                `class_id` = %s,
                `is_available` = %s,
                `start_date_time` = %s,
                `end_date_time` = %s,
                `class_name` = %s,
                `session_type_name` = %s,
                `session_type_id` = %s,
                `staff_name` = %s,
                `staff_id` = %s,
                `mbo_site_id` = %s,
                `schedule_type_name` = %s,
                `program_name` = %s
                WHERE class_id = %s
                """
                # 23 columns
                cursor.execute(sql, (datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"),
                                     row['ClassScheduleId'],
                                     row['LocationId'],
                                     row['LocationName'],
                                     row['MaxCapacity'],
                                     row['TotalBooked'],
                                     row['TotalBookedWaitlist'],
                                     row['WebBooked'],
                                     row['IsCanceled'],
                                     row['Substitute'],
                                     row['Active'],
                                     row['IsWaitlistAvailable'],
                                     row['Id'],
                                     row['IsAvailable'],
                                     row['StartDateTime'],
                                     row['EndDateTime'],
                                     row['Class Name'],
                                     row['Session Type Name'],
                                     row['Session Type Id'],
                                     row['Instructor Name'],
                                     row['Instructor Id'],
                                     Constants.MBO_SITE_ID,
                                     row['Schedule Type'],
                                     row['Program Name'],
                                     row['Id'])) # WHERE id == id
            Constants.DB_CONNECTION.commit()

def db_save_mbo_class_visits(mbo_class_visits):

    for index, row in mbo_class_visits.iterrows():

        query = "SELECT * FROM MboVisit WHERE visit_id = {} AND mbo_site_id = '{}'".format(row['Id'], Constants.MBO_SITE_ID)
        mbo_class = pd.read_sql(query, con=Constants.DB_CONNECTION)

        if mbo_class.empty:
            with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """INSERT INTO MboVisit (
                `createDate`,
                `appointment_id`,
                `appointment_status`,
                `class_id`,
                `client_id`,
                `start_date_time`,
                `end_date_time`,
                `visit_id`,
                `last_modified_datetime`,
                `late_cancelled`,
                `location_id`,
                `class_location_name`,
                `session_type_name`,
                `session_type_id`,
                `class_name`,
                `signed_in`,
                `staff_id`,
                `staff_name`,
                `mbo_site_id`)
                VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
                """
                #  18 Columns
                cursor.execute(sql, (datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"),
                                     row['AppointmentId'],
                                     row['AppointmentStatus'],
                                     row['ClassId'],
                                     row['ClientId'],
                                     row['StartDateTime'],
                                     row['EndDateTime'],
                                     row['Id'],
                                     pd.to_datetime(row['LastModifiedDateTime']).strftime("%Y-%m-%d %H:%M:%S"),
                                     row['LateCancelled'],
                                     row['LocationId'],
                                     row['LocationName'],
                                     row['Name'],
                                     row['Session Type Id'],
                                     row['Class Name'],
                                     row['SignedIn'],
                                     row['StaffId'],
                                     row['Staff Name'],
                                     Constants.MBO_SITE_ID))
            Constants.DB_CONNECTION.commit()
        else:
            with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """UPDATE MboVisit SET
                `createDate` = %s,
                `appointment_id` = %s,
                `appointment_status` = %s,
                `class_id` = %s,
                `client_id` = %s,
                `start_date_time` = %s,
                `end_date_time` = %s,
                `visit_id` = %s,
                `last_modified_datetime` = %s,
                `late_cancelled` = %s,
                `location_id` = %s,
                `class_location_name` = %s,
                `session_type_name` = %s,
                `session_type_id` = %s,
                `class_name` = %s,
                `signed_in` = %s,
                `staff_id` = %s,
                `staff_name` = %s,
                `mbo_site_id` = %s
                WHERE visit_id = %s
                """
                cursor.execute(sql, (datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"),
                                     row['AppointmentId'],
                                     row['AppointmentStatus'],
                                     row['ClassId'],
                                     row['ClientId'],
                                     row['StartDateTime'],
                                     row['EndDateTime'],
                                     row['Id'],
                                     pd.to_datetime(row['LastModifiedDateTime']).strftime("%Y-%m-%d %H:%M:%S"),
                                     row['LateCancelled'],
                                     row['LocationId'],
                                     row['LocationName'],
                                     row['Name'],
                                     row['Session Type Id'],
                                     row['Class Name'],
                                     row['SignedIn'],
                                     row['StaffId'],
                                     row['Staff Name'],
                                     Constants.MBO_SITE_ID,
                                     row['Id'])) # WHERE id == id
            Constants.DB_CONNECTION.commit()


def db_insert_new_ledger_documents(documents):

    for index, row in documents.iterrows():
        query = "SELECT * FROM ChargeBillingLedger WHERE mbo_class_visit_id = {} AND mbo_site_id = '{}'".format(row['mbo_class_visit_id'], Constants.MBO_SITE_ID)
        existing_ledger_document = pd.read_sql(query, con=Constants.DB_CONNECTION)

        if existing_ledger_document.empty:

            with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """INSERT INTO ChargeBillingLedger (
                `index_key`,
                `mbo_site_id`,
                `mbo_shopping_cart_id`,
                `mbo_class_visit_id`,
                `mbo_class_id`,
                `mbo_client_unique_id`,
                `mbo_client_id`,
                `mbo_instructor_id`,
                `charge_type`,
                `charge_status`,
                `charge_scheduled_run_date`,
                `client_first_name`,
                `client_last_name`,
                `client_email`,
                `client_phone`,
                `client_payment_type`,
                `client_credit_card_last_four`,
                `class_name`,
                `class_instructor`,
                `class_location_id`,
                `class_location_name`,
                `class_start_datetime`,
                `class_week_of_year`,
                `class_year_month`,
                `class_day_of_week`,
                `session_type_name`,
                `session_type_id`,
                `communications_status`,
                `updateDate`,
                `createDate`)
                VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
                """
                # 30 columns
                cursor.execute(sql, (row['index_key'],
                                     row['mbo_site_id'],
                                     row['mbo_shopping_cart_id'],
                                     row['mbo_class_visit_id'],
                                     row['mbo_class_id'],
                                     row['mbo_client_unique_id'],
                                     row['mbo_client_id'],
                                     row['mbo_instructor_id'],
                                     row['charge_type'],
                                     row['charge_status'],
                                     row['charge_scheduled_run_date'],
                                     row['client_first_name'],
                                     row['client_last_name'],
                                     row['client_email'],
                                     row['client_phone'],
                                     row['client_payment_type'],
                                     row['client_credit_card_last_four'],
                                     row['class_name'],
                                     row['class_instructor'],
                                     row['class_location_id'],
                                     row['class_location_name'],
                                     row['class_start_datetime'],
                                     row['class_week_of_year'],
                                     row['class_year_month'],
                                     row['class_day_of_week'],
                                     row['session_type_name'],
                                     row['session_type_id'],
                                     row['communications_status'],
                                     row['updateDate'],
                                     datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S")))
            Constants.DB_CONNECTION.commit()

            query = "SELECT * FROM ChargeBillingLedger WHERE mbo_class_visit_id = {} AND mbo_site_id = '{}'".format(row['mbo_class_visit_id'], Constants.MBO_SITE_ID)
            existing_ledger_document = pd.read_sql(query, con=Constants.DB_CONNECTION)
            create_transaction_history_record(datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"), int(existing_ledger_document['id'].iloc[0]), existing_ledger_document['charge_status'].iloc[0], None, "database_insert_new_ledger_document")

        else:
            
#             Handle creareDate IS NULL - why is createDate null?
#             tmp_createDate = existing_ledger_document['createDate'].iloc[0]
#             if tmp_createDate is None:
#                 tmp_createDate = datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S")
#             else:
#                 tmp_createDate = pd.to_datetime(tmp_createDate).strftime("%Y-%m-%d %H:%M:%S")

#             ,
#                 `createDate` = %s

            if np.isnan(row['session_type_id']):
                row['session_type_id'] = None

# UPDATES TO BE TESTED - This doe NOT update the Charge Status.
# This is intentional so the status isn't altered when updating an existing entity
# Check it isn't creating an error
            with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """UPDATE ChargeBillingLedger SET
                `index_key` = %s,
                `mbo_site_id` = %s,
                `mbo_shopping_cart_id` = %s,
                `mbo_class_visit_id` = %s,
                `mbo_class_id` = %s,
                `mbo_client_unique_id` = %s,
                `mbo_client_id` = %s,
                `mbo_instructor_id` = %s,
                `charge_type` = %s,
                `charge_scheduled_run_date` = %s,
                `client_first_name` = %s,
                `client_last_name` = %s,
                `client_email` = %s,
                `client_phone` = %s,
                `client_payment_type` = %s,
                `client_credit_card_last_four` = %s,
                `class_name` = %s,
                `class_instructor` = %s,
                `class_location_id` = %s,
                `class_location_name` = %s,
                `class_start_datetime` = %s,
                `class_week_of_year` = %s,
                `class_year_month` = %s,
                `class_day_of_week` = %s,
                `session_type_name` = %s,
                `session_type_id` = %s,
                `updateDate` = %s
                WHERE `id` = %s
                """
                # 30 columns
                cursor.execute(sql, (row['index_key'],
                                     row['mbo_site_id'],
                                     row['mbo_shopping_cart_id'],
                                     row['mbo_class_visit_id'],
                                     row['mbo_class_id'],
                                     row['mbo_client_unique_id'],
                                     row['mbo_client_id'],
                                     row['mbo_instructor_id'],
                                     row['charge_type'],
                                     pd.to_datetime(row['charge_scheduled_run_date']).strftime("%Y-%m-%d %H:%M:%S"),
                                     row['client_first_name'],
                                     row['client_last_name'],
                                     row['client_email'],
                                     row['client_phone'],
                                     row['client_payment_type'],
                                     row['client_credit_card_last_four'],
                                     row['class_name'],
                                     row['class_instructor'],
                                     row['class_location_id'],
                                     row['class_location_name'],
                                     pd.to_datetime(row['class_start_datetime']).strftime("%Y-%m-%d %H:%M:%S"),
                                     row['class_week_of_year'],
                                     row['class_year_month'],
                                     row['class_day_of_week'],
                                     row['session_type_name'],
                                     row['session_type_id'],
                                     pd.to_datetime(existing_ledger_document['updateDate'].iloc[0]).strftime("%Y-%m-%d %H:%M:%S"),
                                     int(existing_ledger_document['id'].iloc[0]))) # WHERE id == id
            Constants.DB_CONNECTION.commit()


def db_update_ledger_documents(documents, message = "database_update_ledger_documents"):

    for index, row in documents.iterrows():
        
        query = "SELECT * FROM ChargeBillingLedger WHERE mbo_class_visit_id = {} AND mbo_site_id = '{}'".format(row['mbo_class_visit_id'], Constants.MBO_SITE_ID)
        existing_ledger_document = pd.read_sql(query, con=Constants.DB_CONNECTION)
        
# #             Handle creareDate IS NULL - why is createDate null?
#         tmp_createDate = existing_ledger_document['createDate'].iloc[0]
#         if tmp_createDate is None:
#             tmp_createDate = datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S")
#         else:
#             tmp_createDate = pd.to_datetime(tmp_createDate).strftime("%Y-%m-%d %H:%M:%S")
            
        if np.isnan(row['session_type_id']):
            row['session_type_id'] = None

        if ~existing_ledger_document.empty:
            
            with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """UPDATE ChargeBillingLedger SET
                `index_key` = %s,
                `mbo_site_id` = %s,
                `mbo_shopping_cart_id` = %s,
                `mbo_class_visit_id` = %s,
                `mbo_class_id` = %s,
                `mbo_client_unique_id` = %s,
                `mbo_client_id` = %s,
                `mbo_instructor_id` = %s,
                `charge_type` = %s,
                `charge_status` = %s,
                `charge_scheduled_run_date` = %s,
                `client_first_name` = %s,
                `client_last_name` = %s,
                `client_email` = %s,
                `client_phone` = %s,
                `client_payment_type` = %s,
                `client_credit_card_last_four` = %s,
                `class_name` = %s,
                `class_instructor` = %s,
                `class_location_id` = %s,
                `class_location_name` = %s,
                `class_start_datetime` = %s,
                `class_week_of_year` = %s,
                `class_year_month` = %s,
                `class_day_of_week` = %s,
                `session_type_name` = %s,
                `session_type_id` = %s,
                `communications_status` = %s,
                `updateDate` = %s
                WHERE `id` = %s
                """
                # 31 columns
                
                cursor.execute(sql, (row['index_key'],
                             row['mbo_site_id'],
                             row['mbo_shopping_cart_id'],
                             row['mbo_class_visit_id'],
                             row['mbo_class_id'],
                             row['mbo_client_unique_id'],
                             row['mbo_client_id'],
                             row['mbo_instructor_id'],
                             row['charge_type'],
                             row['charge_status'],
                             pd.to_datetime(row['charge_scheduled_run_date']).strftime("%Y-%m-%d %H:%M:%S"),
                             row['client_first_name'],
                             row['client_last_name'],
                             row['client_email'],
                             row['client_phone'],
                             row['client_payment_type'],
                             row['client_credit_card_last_four'],
                             row['class_name'],
                             row['class_instructor'],
                             row['class_location_id'],
                             row['class_location_name'],
                             pd.to_datetime(row['class_start_datetime']).strftime("%Y-%m-%d %H:%M:%S"),
                             row['class_week_of_year'],
                             row['class_year_month'],
                             row['class_day_of_week'],
                             row['session_type_name'],
                             row['session_type_id'],
                             row['communications_status'],
                             datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"),
                             int(existing_ledger_document['id'].iloc[0]))) # WHERE id == id

            Constants.DB_CONNECTION.commit()
            create_transaction_history_record(datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"), int(existing_ledger_document['id'].iloc[0]), row['charge_status'], existing_ledger_document['charge_status'].iloc[0], message)


def db_update_ledger_document_with_charge(document, previous_charge_status, message = "db_update_ledger_document_with_charge"):
        
    if np.isnan(document['session_type_id']):
        row['session_type_id'] = None
    
    with Constants.DB_CONNECTION.cursor() as cursor:
        sql = """UPDATE ChargeBillingLedger SET
        `index_key` = %s,
        `mbo_site_id` = %s,
        `mbo_shopping_cart_id` = %s,
        `mbo_class_visit_id` = %s,
        `mbo_class_id` = %s,
        `mbo_client_unique_id` = %s,
        `mbo_client_id` = %s,
        `mbo_instructor_id` = %s,
        `charge_type` = %s,
        `charge_status` = %s,
        `charge_scheduled_run_date` = %s,
        `client_first_name` = %s,
        `client_last_name` = %s,
        `client_email` = %s,
        `client_phone` = %s,
        `client_payment_type` = %s,
        `client_credit_card_last_four` = %s,
        `class_name` = %s,
        `class_instructor` = %s,
        `class_location_id` = %s,
        `class_location_name` = %s,
        `class_start_datetime` = %s,
        `class_week_of_year` = %s,
        `class_year_month` = %s,
        `class_day_of_week` = %s,
        `session_type_name` = %s,
        `session_type_id` = %s,
        `communications_status` = %s,
        `updateDate` = %s,
        `createDate` = %s
        WHERE `id` = %s
        """
        # 32 columns
        cursor.execute(sql, (document['index_key'],
                             document['mbo_site_id'],
                             document['mbo_shopping_cart_id'],
                             document['mbo_class_visit_id'],
                             document['mbo_class_id'],
                             document['mbo_client_unique_id'],
                             document['mbo_client_id'],
                             document['mbo_instructor_id'],
                             document['charge_type'],
                             document['charge_status'],
                             pd.to_datetime(document['charge_scheduled_run_date']).strftime("%Y-%m-%d %H:%M:%S"),
                             document['client_first_name'],
                             document['client_last_name'],
                             document['client_email'],
                             document['client_phone'],
                             document['client_payment_type'],
                             document['client_credit_card_last_four'],
                             document['class_name'],
                             document['class_instructor'],
                             document['class_location_id'],
                             document['class_location_name'],
                             pd.to_datetime(document['class_start_datetime']).strftime("%Y-%m-%d %H:%M:%S"),
                             document['class_week_of_year'],
                             document['class_year_month'],
                             document['class_day_of_week'],
                             document['session_type_name'],
                             document['session_type_id'],
                             document['communications_status'],
                             datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"),
                             pd.to_datetime(document['createDate']).strftime("%Y-%m-%d %H:%M:%S"),
                             int(document['id']))) # WHERE id == id

    Constants.DB_CONNECTION.commit()

    create_transaction_history_record(datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"), int(document['id']), previous_charge_status, document['charge_status'], message)

    
"""
@Request Entity
{'ShoppingCart':
    {
        'Id': 'd19ab689-733f-4f24-ad40-c3223ac461df',
        'CartItems': [
            {'Item':
                {'Id': None,
                'Name': 'Fees: Class No Show',
                'Count': 1,
                'OnlinePrice': 0.0,
                'Price': 10.0,
                'TaxRate': 0.0,
                'ProductId': '10341',
                'ProgramId': 40,
                'TaxIncluded': 0.0},
                'DiscountAmount': 0.0,
                'VisitIds': [],
                'AppointmentIds': [],
                'Appointments': [],
                'Id': 1, 'Quantity': 1
            }
        ],
        'SubTotal': 10.0,
        'DiscountTotal': 0.0,
        'TaxTotal': 0.0,
        'GrandTotal': 10.0
    },
    'Classes': [],
    'Appointments': [],
    'Enrollments': []
}
"""
def db_save_shopping_cart_response(response):
    
    if Constants.IS_TEST:
        print(f"[db_save_shopping_cart_response] : {response}")

    with Constants.DB_CONNECTION.cursor() as cursor:
        sql = """INSERT INTO MboShoppingCart (
        `createDate`,
        `mbo_shopping_cart_id`,
        `cart_item_product_id`,
        `cart_item_product_name`,
        `cart_item_price`,
        `quantity`,
        `sub_total`,
        `discount_total`,
        `tax_total`,
        `grand_total`,
        `mbo_site_id`)
        VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
        """
        cursor.execute(sql, (datetime.datetime.today().strftime("%Y-%m-%d %H:%M:%S"),
                             response['Id'],
                             response['CartItems'][0]['Item']['ProductId'],
                             response['CartItems'][0]['Item']['Name'],
                             response['CartItems'][0]['Item']['Price'],
                             response['CartItems'][0]['Quantity'],
                             response['SubTotal'],
                             response['DiscountTotal'],
                             response['TaxTotal'],
                             response['GrandTotal'],
                             Constants.MBO_SITE_ID))

    Constants.DB_CONNECTION.commit()




###########################################
# MINDBODY ONLINE API FUNCTIONS           #
###########################################

# AUTHENTICATE
def mbo_auth_token():

    body = {
        "Username": Constants.MBO_USER_NAME,
        "Password": Constants.MBO_PASSWORD
    }

    headers = {
        "Api-Key": Constants.MBO_NO_SHOW_API_KEY,
        "Content-Type": Constants.CONTENT_TYPE,
        "SiteId": Constants.MBO_SITE_ID
    }

    API_ENDPOINT = Constants.MBO_BASE_URL + Constants.USERTOKEN_URL

    # sending post request and saving response as response object
    r = requests.post(url = API_ENDPOINT, data = json.dumps(body), headers=headers)
    event = r.json()
    j = json.dumps(event)
    mbo_auth_data = json.loads(j)

    return mbo_auth_data['AccessToken']

# GETTERS
def mbo_get_classes(start_date, end_date):

    start_date = pd.to_datetime(start_date)
    start_date = start_date.strftime("%Y-%m-%dT%H:%M:%S")
    end_date = pd.to_datetime(end_date)
    end_date = end_date.strftime("%Y-%m-%dT%H:%M:%S")

    # start = time.time()
    GET_CLASSES_API_COUNT = 0

    print(f'Get Classes: Start: {start_date} -> End: {end_date}')

    OFFSET = 0
    COUNT = 0
    LIMIT = 100
    FAILED_ATTEMPT = 0
    RETRY_BREAK = 2
    FIRST_ATTEMPT = True
    isValid = True
    mbo_classes = pd.DataFrame()

    HEADERS = {
        'Content-Type': Constants.CONTENT_TYPE,
        'Api-Key': Constants.MBO_NO_SHOW_API_KEY,
        'SiteId': Constants.MBO_SITE_ID,
        'Authorization': Constants.MBO_TOKEN
    }

    while isValid:
        GET_CLASSES_API_COUNT = GET_CLASSES_API_COUNT + 1
        PARAMS = {
            'StartDateTime' : start_date,
            'EndDateTime' : end_date,
            'HideCanceledClasses' : True,
            'limit': LIMIT,
            'offset': OFFSET
        }

        try:
            API_ENDPOINT = Constants.MBO_BASE_URL + Constants.GET_CLASSES_URL
            r = requests.get(url=API_ENDPOINT, params=PARAMS, headers=HEADERS)
            r.raise_for_status()

        except HTTPError as http_err:
            print(f'HTTP error code: {http_err.response.status_code}')
            print(f'HTTP error: {http_err}')
            print(f'HTTP type: {type(http_err)}')
            print('FAILED_ATTEMPT')
            FAILED_ATTEMPT = FAILED_ATTEMPT + 1
            if FAILED_ATTEMPT >= RETRY_BREAK:
                isValid = False

        except Exception as err:
            print(f'Error getting client visits: {err}')
            print('FAILED_ATTEMPT')
            FAILED_ATTEMPT = FAILED_ATTEMPT + 1
            if FAILED_ATTEMPT >= RETRY_BREAK:
                isValid = False

        else:
            event = r.json()
            j = json.dumps(event)
            data = json.loads(j)

            frame = pd.DataFrame.from_dict(data['Classes'])
            if FIRST_ATTEMPT == True and len(mbo_classes) == 0:
                mbo_classes = frame
                OFFSET = OFFSET + LIMIT
                FIRST_ATTEMPT = False

            elif FIRST_ATTEMPT == False and len(mbo_classes) == 0:
                isValid = False

            else:
                mbo_classes = mbo_classes.append(frame)
                COUNT = COUNT + data['PaginationResponse']['PageSize']
                OFFSET = OFFSET + LIMIT

            if (OFFSET >= data['PaginationResponse']['TotalResults']):
                isValid = False

    print(f'GET_CLASSES_API_COUNT = {GET_CLASSES_API_COUNT}')

    mbo_classes.reset_index(drop=True, inplace=True)
    for index, row in mbo_classes.iterrows():
        mbo_classes.at[index, 'Instructor Name'] = row['Staff']['FirstName']+' '+row['Staff']['LastName']
        mbo_classes.at[index, 'Instructor Id'] = row['Staff']['Id']
        mbo_classes.at[index, 'Session Type Id'] = row['ClassDescription']['SessionType']['Id']
        mbo_classes.at[index, 'Session Type Name'] = row['ClassDescription']['SessionType']['Name']
        mbo_classes.at[index, 'Program Name'] = row['ClassDescription']['Program']['Name']
        mbo_classes.at[index, 'Schedule Type'] = row['ClassDescription']['Program']['ScheduleType']
        mbo_classes.at[index, 'Class Name'] = row['ClassDescription']['Name']
        mbo_classes.at[index, 'LocationId'] = int(row['Location'].get('Id'))
        mbo_classes.at[index, 'Day of Week'] = calendar.day_name[pd.to_datetime(row['StartDateTime']).weekday()]
        mbo_classes.at[index, 'Start Time'] = pd.to_datetime(row['StartDateTime']).time()
        mbo_classes.at[index, 'LocationName'] = row['Location'].get('Name')

    db_save_mbo_classes(mbo_classes)
    return mbo_classes


def mbo_get_class_visits(mbo_class):
    
    GET_CLASS_VISITS_API_COUNT = 0
    
    mbo_class_visits = pd.DataFrame()

    COUNT = 1
    
    HEADERS = {
        'Content-Type': Constants.CONTENT_TYPE,
        'Api-Key': Constants.MBO_NO_SHOW_API_KEY,
        'SiteId': Constants.MBO_SITE_ID,
        'Authorization': Constants.MBO_TOKEN
    }

    GET_CLASS_VISITS_API_COUNT = GET_CLASS_VISITS_API_COUNT + 1

    PARAMS = {
        'ClassID' : mbo_class['class_id']
    }

    try:
        API_ENDPOINT = Constants.MBO_BASE_URL + Constants.GET_CLASS_VISITS_URL
#         print("About to hit API")
        r = requests.get(url=API_ENDPOINT, params=PARAMS, headers=HEADERS)
        r.raise_for_status()

    except HTTPError as http_err:
        print(f'HTTP error code: {http_err.response.status_code}')
        print(f'HTTP error: {http_err}')
        print(f'HTTP type: {type(http_err)}')
        print('FAILED_ATTEMPT')
        FAILED_ATTEMPT = FAILED_ATTEMPT + 1
        if FAILED_ATTEMPT >= RETRY_BREAK:
            isValid = False

    except Exception as err:
        print(f'Error getting client visits: {err}')
        print('FAILED_ATTEMPT')
        FAILED_ATTEMPT = FAILED_ATTEMPT + 1
        if FAILED_ATTEMPT >= RETRY_BREAK:
            isValid = False

    else:
        event = r.json()
        j = json.dumps(event)
        data = json.loads(j)
        frame = pd.DataFrame.from_dict(data['Class']['Visits'])
        mbo_class_visits = mbo_class_visits.append(frame)
        COUNT = COUNT + 1

    mbo_class_visits.reset_index(drop=True, inplace=True)
    
    mbo_class_visits.reset_index(drop=True, inplace=True)
    for index, row in mbo_class_visits.iterrows():
        mbo_class_visits.at[index, 'Staff Name'] = mbo_class['staff_name']
        mbo_class_visits.at[index, 'Class Name'] = mbo_class['class_name']
        mbo_class_visits.at[index, 'LocationName'] = mbo_class['class_location_name']
        mbo_class_visits.at[index, 'Session Type Id'] = mbo_class['session_type_id']

#   Save to Database
    db_save_mbo_class_visits(mbo_class_visits)
    
    return mbo_class_visits


# Get Clients from MBO for all No Show & Late Cancel Members
def mbo_get_clients(member_list):

    OFFSET = 0
    COUNT = 0
    LIMIT = 100
    FAILED_ATTEMPT = 0
    RETRY_BREAK = 2
    FIRST_ATTEMPT = True
    isValid = True
    mbo_clients = pd.DataFrame()

    HEADERS = {
        'Content-Type': Constants.CONTENT_TYPE,
        'Api-Key': Constants.MBO_NO_SHOW_API_KEY,
        'SiteId': Constants.MBO_SITE_ID,
        'Authorization': Constants.MBO_TOKEN
    }

    while isValid:

        PARAMS = {
            'ClientIds' : member_list[OFFSET:(OFFSET + LIMIT)],
            'limit' : LIMIT,
            'offset' : OFFSET
        }

        try:
            API_ENDPOINT = Constants.MBO_BASE_URL + Constants.GET_CLIENTS_URL
            print("About to hit API")
            r = requests.get(url=API_ENDPOINT, params=PARAMS, headers=HEADERS)
            r.raise_for_status()

        except HTTPError as http_err:
            print(f'HTTP error code: {http_err.response.status_code}')
            print(f'HTTP error: {http_err}')
            print(f'HTTP type: {type(http_err)}')
            print('FAILED_ATTEMPT')
            FAILED_ATTEMPT = FAILED_ATTEMPT + 1
            if FAILED_ATTEMPT >= RETRY_BREAK:
                isValid = False

        except Exception as err:
            print(f'Error getting client visits: {err}')
            print('FAILED_ATTEMPT')
            FAILED_ATTEMPT = FAILED_ATTEMPT + 1
            if FAILED_ATTEMPT >= RETRY_BREAK:
                isValid = False

        else:
            event = r.json()
            j = json.dumps(event)
            data = json.loads(j)
            print(f"Clients Returned: {len(data['Clients'])}")

            frame = pd.DataFrame.from_dict(data['Clients'])
            if FIRST_ATTEMPT == True and len(mbo_clients) == 0:
                mbo_clients = frame
                FIRST_ATTEMPT = False
                OFFSET = OFFSET + LIMIT

            elif FIRST_ATTEMPT == False and len(mbo_clients) == 0:
                isValid = False

            else:
                mbo_clients = mbo_clients.append(frame)

                COUNT = COUNT + data['PaginationResponse']['PageSize']
                OFFSET =  OFFSET + LIMIT

            if OFFSET > len(member_list):
                isValid = False
                print(f"Complete - Count: {COUNT} | Total Results: {data['PaginationResponse']['TotalResults']}")

#     I  could filter Non-Members nere
    mbo_clients.reset_index(drop=True, inplace=True)
    return mbo_clients

# Get Services from MBO
def mbo_get_services():
    try:
        HEADERS = {
            "Api-Key": Constants.MBO_NO_SHOW_API_KEY,
            "Content-Type": Constants.CONTENT_TYPE,
            "SiteId": Constants.MBO_SITE_ID,
            "Authorization": Constants.MBO_TOKEN
        }

        PARAMS = {
                'ServiceIds' : [Constants.MBO_PRODUCT_LATE_CXL_ID, Constants.MBO_PRODUCT_NO_SHOW_ID]
            }

        API_ENDPOINT = Constants.MBO_BASE_URL + Constants.GET_SERVICES_URL
        print("About to hit API")
        r = requests.get(url=API_ENDPOINT, params=PARAMS, headers=HEADERS)
        r.raise_for_status()

    except HTTPError as http_err:
        print(f'HTTP error code: {http_err.response.status_code}')
        print(f'HTTP error: {http_err}')
        print(f'HTTP type: {type(http_err)}')
        print('FAILED_ATTEMPT')
        FAILED_ATTEMPT = FAILED_ATTEMPT + 1
        if FAILED_ATTEMPT >= RETRY_BREAK:
            isValid = False

    except Exception as err:
        print(f'Error getting client visits: {err}')
        print('FAILED_ATTEMPT')
    
    else:
        event = r.json()
        j = json.dumps(event)
        data = json.loads(j)
        print(f"Products Returned: {len(data['Services'])}")
        for product in data['Services']:
            if product['Id'] == Constants.MBO_PRODUCT_NO_SHOW_ID:
                Constants.MBO_PRODUCT_NO_SHOW = product

            if product['Id'] == Constants.MBO_PRODUCT_LATE_CXL_ID:
                Constants.MBO_PRODUCT_LATE_CXL = product
                      
# SETTERS
# EXECUTE CLASS CHARGE BILLING
def mbo_execute_charge(data):

    try:
        CLIENT_ID = data['mbo_client_id']
        CC_LAST_FOUR = data['client_credit_card_last_four']

        mbo_item_id = None
        if data['charge_status'] == Constants.CHARGE_STATUS_NO_SHOW_CHARGE_PENDING:
            mbo_item_id = Constants.MBO_PRODUCT_NO_SHOW_ID
            amount = Constants.MBO_PRODUCT_NO_SHOW['Price']

        elif data['charge_status'] == Constants.CHARGE_STATUS_LATE_CANCEL_CHARGE_PENDING:
            mbo_item_id = Constants.MBO_PRODUCT_LATE_CXL_ID
            amount = Constants.MBO_PRODUCT_LATE_CXL['Price']

        else:
            return None

        item = {
            "Type": "Service",
            "Metadata": {
                "Id": mbo_item_id
            }
        }

        items = [{
            "Item": item,
            "Quantity": 1
        }]

        if data['client_payment_type'] == Constants.MBO_PAYMENT_TYPE_DIRECT_DEBIT:

            payment = {
                "Type": data['client_payment_type'],
                "Metadata": {
                    "Amount": amount
                }
            }

        if data['client_payment_type'] == Constants.MBO_PAYMENT_TYPE_STORED_CARD:

            payment = {
                "Type": data['client_payment_type'],
                "Metadata": {
                    "Amount": amount,
                    "LastFour": CC_LAST_FOUR
                }
            }

        body = {
            "Test": Constants.IS_TEST,
            "ClientId": CLIENT_ID,
            "Items": items,
            "Payments": [payment],
            "SendEmail": False,
            "LocationId": data['class_location_id']
        }

        HEADERS = {
            "Api-Key": Constants.MBO_NO_SHOW_API_KEY,
            "Content-Type": Constants.CONTENT_TYPE,
            "SiteId": Constants.MBO_SITE_ID,
            "Authorization": Constants.MBO_TOKEN
        }

        API_ENDPOINT = Constants.MBO_BASE_URL + Constants.CHECKOUT_SHOPPING_CART_URL

        checkout_shopping_cart_response = None
#         sending post request and saving response as response object
        r = requests.post(url = API_ENDPOINT, data = json.dumps(body), headers=HEADERS)

        if Constants.IS_TEST:
            print(f"[mbo_execute_charge] response: {r}")

        event = r.json()
        j = json.dumps(event)
        checkout_shopping_cart_response = json.loads(j)

        if Constants.IS_TEST:
            print(f"checkout_shopping_cart_response:\n{checkout_shopping_cart_response}")

        return checkout_shopping_cart_response
    
    except HTTPError as http_err:
        print(f'HTTP error code: {http_err.response.status_code}')
        print(f'HTTP error: {http_err}')
        print(f'HTTP type: {type(http_err)}')
        print('FAILED_ATTEMPT')
        return None

    except Exception as err:
        print(f'Error getting client visits: {err}')
        print('FAILED_ATTEMPT')
        return None


###########################################
# REPORTS                                 #
###########################################

# MTD Club Performance
def build_mtd_club_performance(event,location_performance):
    mtd_location_performance = location_performance.copy(deep=True)

    mtd_location_performance = mtd_location_performance[mtd_location_performance['month_year'] == pd.to_datetime(event['end_date']).strftime('%B-%Y')]

    mtd_location_performance_columns = ['class_location_name',
           'total_classes', 'capacity', 'booked', 'signed_in', 'no_show',
           'late_cancel', 'total_forgive', 'no_show_charges',
           'late_cancel_charges','avg_charge_per_member', 'no_show_charge_pending',
           'late_cancel_charge_pending', 'no_show_dispute_approved',
           'late_cancel_dispute_approved', 'percent_no_show',
           'percent_late_cancel', 'percent_capacity_attendance']

    mtd_location_performance = mtd_location_performance[mtd_location_performance_columns]

    mtd_location_performance_columns = [pd.to_datetime(event['end_date']).strftime('%B-%Y'), 'Total Classes', 'Capacity', 'Booked', 'Signed In', 'No Show',
           'Late Cancel', 'Total Forgive', 'No Show Charges',
           'Late Cancel Charges','Average Charges Per Member', 'No Show Charge Pending',
           'Late Cancel Charge Pending', 'No Show Dispute Approved',
           'Late Cancel Dispute Approved', '% No Show', '% Late Cancel',
           '% Capacity Attendance']

    mtd_location_performance.columns = mtd_location_performance_columns
    mtd_location_performance.set_index(pd.to_datetime(event['end_date']).strftime('%B-%Y'), inplace=True)
    mtd_location_performance = mtd_location_performance.transpose()
    
    return mtd_location_performance


def db_insert_location_performance(club_performance):

    for index, row in club_performance.iterrows():

        query = """SELECT * FROM LocationPerformance WHERE month_year = "{}" AND mbo_site_id = {} AND class_location_name = "{}" """.format(row['month_year'], Constants.MBO_SITE_ID, index)
        existing_report = pd.read_sql(query, con=Constants.DB_CONNECTION)

        if existing_report.empty:

            with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """INSERT INTO LocationPerformance (
                `mbo_site_id`,
                `month_year`,
                `class_location_name`,
                `total_classes`,
                `capacity`,
                `booked`,
                `signed_in`,
                `no_show`,
                `late_cancel`,
                `total_forgive`,
                `no_show_charges`,
                `late_cancel_charges`,
                `no_show_charge_pending`,
                `late_cancel_charge_pending`,
                `no_show_dispute_approved`,
                `late_cancel_dispute_approved`,
                `avg_charge_per_member`,
                `percent_no_show`,
                `percent_late_cancel`,
                `percent_capacity_attendance`)
                VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
                """
                cursor.execute(sql, (row['mbo_site_id'],
                                     row['month_year'],
                                     index,
                                     row['total_classes'],
                                     row['capacity'],
                                     row['booked'],
                                     row['signed_in'],
                                     row['no_show'],
                                     row['late_cancel'],
                                     row['total_forgive'],
                                     row['no_show_charges'],
                                     row['late_cancel_charges'],
                                     row['no_show_charge_pending'],
                                     row['late_cancel_charge_pending'],
                                     row['no_show_dispute_approved'],
                                     row['late_cancel_dispute_approved'],
                                     row['avg_charge_per_member'],
                                     row['percent_no_show'],
                                     row['percent_late_cancel'],
                                     row['percent_capacity_attendance']))

            Constants.DB_CONNECTION.commit()

        else:
            with Constants.DB_CONNECTION.cursor() as cursor:
                sql = """UPDATE LocationPerformance SET
                `mbo_site_id` = %s,
                `month_year` = %s,
                `class_location_name` = %s,
                `total_classes` = %s,
                `capacity` = %s,
                `booked` = %s,
                `signed_in` = %s,
                `no_show` = %s,
                `late_cancel` = %s,
                `total_forgive` = %s,
                `no_show_charges` = %s,
                `late_cancel_charges` = %s,
                `no_show_charge_pending` = %s,
                `late_cancel_charge_pending` = %s,
                `no_show_dispute_approved` = %s,
                `late_cancel_dispute_approved` = %s,
                `avg_charge_per_member` = %s,
                `percent_no_show` = %s,
                `percent_late_cancel` = %s,
                `percent_capacity_attendance` = %s
                WHERE `id` = %s
                """
                cursor.execute(sql, (row['mbo_site_id'],
                                     row['month_year'],
                                     index,
                                     row['total_classes'],
                                     row['capacity'],
                                     row['booked'],
                                     row['signed_in'],
                                     row['no_show'],
                                     row['late_cancel'],
                                     row['total_forgive'],
                                     row['no_show_charges'],
                                     row['late_cancel_charges'],
                                     row['no_show_charge_pending'],
                                     row['late_cancel_charge_pending'],
                                     row['no_show_dispute_approved'],
                                     row['late_cancel_dispute_approved'],
                                     row['avg_charge_per_member'],
                                     row['percent_no_show'],
                                     row['percent_late_cancel'],
                                     row['percent_capacity_attendance'],
                                     int(existing_report['id'].iloc[0]))) # WHERE id == id

            Constants.DB_CONNECTION.commit()


# Club Performance
def build_location_performance_report(mbo_classes, mbo_visits, ledger):
    creche_visit_data = mbo_visits[mbo_visits['session_type_name'] == 'Creche']
    class_visit_data = mbo_visits[mbo_visits['session_type_name'] != 'Creche']

    creache_data = mbo_classes[mbo_classes['session_type_name'] == 'Creche']
    class_data = mbo_classes[mbo_classes['session_type_name'] != 'Creche']
    
    index = pd.unique(class_data['class_location_name'])
    # index = np.append(index,['Total'])
    club_performance = pd.DataFrame(index=pd.unique(class_data['class_location_name'])).sort_index()

    club_performance['total_classes'] = class_data.groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['capacity'] = class_data.groupby('class_location_name').agg({'max_capacity' : 'sum'})
    club_performance['booked'] = class_visit_data.groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['signed_in'] = class_visit_data[class_visit_data['signed_in'] == True].groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['no_show'] = class_visit_data[(class_visit_data['signed_in'] == False) & (class_visit_data['late_cancelled'] == False)].groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['late_cancel'] = class_visit_data[(class_visit_data['signed_in'] == False) & (class_visit_data['late_cancelled'] == True)].groupby('class_location_name').agg({'class_location_name' : 'count'})

    club_performance['total_forgive'] = ledger[ledger['charge_status'] == Constants.CHARGE_STATUS_FORGIVE].groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['no_show_charges'] = ledger[ledger['charge_status'] == Constants.CHARGE_STATUS_NO_SHOW_COMPLETE].groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['late_cancel_charges'] = ledger[ledger['charge_status'] == Constants.CHARGE_STATUS_LATE_CANCEL_COMPLETE].groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['no_show_charge_pending'] = ledger[ledger['charge_status'] == Constants.CHARGE_STATUS_NO_SHOW_CHARGE_PENDING].groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['late_cancel_charge_pending'] = ledger[ledger['charge_status'] == Constants.CHARGE_STATUS_LATE_CANCEL_CHARGE_PENDING].groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['no_show_dispute_approved'] = ledger[ledger['charge_status'].isin([Constants.CHARGE_STATUS_NO_SHOW_ABORT, Constants.CHARGE_STATUS_NO_SHOW_REFUND])].groupby('class_location_name').agg({'class_location_name' : 'count'})
    club_performance['late_cancel_dispute_approved'] = ledger[ledger['charge_status'].isin([Constants.CHARGE_STATUS_LATE_CANCEL_ABORT, Constants.CHARGE_STATUS_LATE_CANCEL_REFUND])].groupby('class_location_name').agg({'class_location_name' : 'count'})

    club_performance.fillna(0, inplace=True)
    club_performance.reset_index(inplace=True)

    club_performance = club_performance.append(club_performance.sum(numeric_only=True), ignore_index=True)
    club_performance.fillna('Total', inplace=True)

    club_performance['avg_charge_per_member'] = round((club_performance['no_show_charges'] + club_performance['late_cancel_charges']) / len(class_visit_data['client_id'].unique()), 2)
    club_performance['percent_no_show'] = round(100 * club_performance['no_show'] / club_performance['booked'], 2)
    club_performance['percent_late_cancel'] = round(100 * club_performance['late_cancel'] / club_performance['booked'], 2)
    club_performance['percent_capacity_attendance'] = round(100 * club_performance['signed_in'] / club_performance['capacity'], 2)

    club_performance.rename(columns = {"index": pd.to_datetime(class_visit_data['start_date_time'].iloc[0]).strftime('%B-%Y')}, inplace = True)
    club_performance.set_index(pd.to_datetime(class_visit_data['start_date_time'].iloc[0]).strftime('%B-%Y'), inplace=True)

    club_performance['mbo_site_id'] = Constants.MBO_SITE_ID
    club_performance['month_year'] = pd.to_datetime(class_visit_data['start_date_time'].iloc[0]).strftime('%B-%Y')
    
    db_insert_location_performance(club_performance)

    club_performace_columns = ['Total Classes', 'Capacity', 'Booked', 'Signed In', 'No Show',
           'Late Cancel', 'Total Forgive', 'No Show Charges',
           'Late Cancel Charges', 'No Show Charge Pending',
           'Late Cancel Charge Pending', 'No Show Dispute Approved',
           'Late Cancel Dispute Approved', 'Average Charges Per Member', '% No Show', '% Late Cancel',
           '% Capacity Attendance','MBO Site Id', 'Month']

    club_performance.columns = club_performace_columns
    return club_performance.transpose()


def db_insert_class_performance(class_performance):

    for index, row in class_performance.iterrows():
        try:
            query = """SELECT * FROM ClassPerformance WHERE month_year = "{}" AND mbo_site_id = {} AND class_name = "{}" """.format(row['month_year'], Constants.MBO_SITE_ID, index)
            existing_report = pd.read_sql(query, con=Constants.DB_CONNECTION)

            if existing_report.empty:
                with Constants.DB_CONNECTION.cursor() as cursor:
                    sql = """INSERT INTO ClassPerformance (
                    `mbo_site_id`,
                    `month_year`,
                    `class_name`,
                    `total_classes`,
                    `capacity`,
                    `booked`,
                    `signed_in`,
                    `no_show`,
                    `late_cancel`,
                    `percent_no_show`,
                    `percent_late_cancel`,
                    `percent_capacity_attendance`)
                    VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
                    """
                    cursor.execute(sql, (row['mbo_site_id'],
                                         row['month_year'],
                                         index,
                                         row['total_classes'],
                                         row['capacity'],
                                         row['booked'],
                                         row['signed_in'],
                                         row['no_show'],
                                         row['late_cancel'],
                                         row['percent_no_show'],
                                         row['percent_late_cancel'],
                                         row['percent_capacity_attendance']))

                Constants.DB_CONNECTION.commit()

            else:
                with Constants.DB_CONNECTION.cursor() as cursor:
                    sql = """UPDATE ClassPerformance SET
                    `mbo_site_id` = %s,
                    `month_year` = %s,
                    `class_name` = %s,
                    `total_classes` = %s,
                    `capacity` = %s,
                    `booked` = %s,
                    `signed_in` = %s,
                    `no_show` = %s,
                    `late_cancel` = %s,
                    `percent_no_show` = %s,
                    `percent_late_cancel` = %s,
                    `percent_capacity_attendance` = %s
                    WHERE `id` = %s
                    """
                    cursor.execute(sql, (row['mbo_site_id'],
                                         row['month_year'],
                                         index,
                                         row['total_classes'],
                                         row['capacity'],
                                         row['booked'],
                                         row['signed_in'],
                                         row['no_show'],
                                         row['late_cancel'],
                                         row['percent_no_show'],
                                         row['percent_late_cancel'],
                                         row['percent_capacity_attendance'],
                                         int(existing_report['id'].iloc[0]))) # WHERE id == id

                Constants.DB_CONNECTION.commit()
        except:
            print("Error {}".format(index))


# Club Performance
def build_class_performance_report(mbo_classes, mbo_visits, ledger):
    creche_visit_data = mbo_visits[mbo_visits['session_type_name'] == 'Creche']
    class_visit_data = mbo_visits[mbo_visits['session_type_name'] != 'Creche']

    creache_data = mbo_classes[mbo_classes['session_type_name'] == 'Creche']
    class_data = mbo_classes[mbo_classes['session_type_name'] != 'Creche']

    index = pd.unique(class_data['class_name'])
    class_performance = pd.DataFrame(index=pd.unique(class_data['class_name'])).sort_index()

    class_performance['total_classes'] = class_data.groupby('class_name').agg({'class_name' : 'count'})
    class_performance['capacity'] = class_data.groupby('class_name').agg({'max_capacity' : 'sum'})
    class_performance['booked'] = class_visit_data.groupby('class_name').agg({'staff_name' : 'count'})
    class_performance['signed_in'] = class_visit_data[class_visit_data['signed_in'] == True].groupby('class_name').agg({'class_name' : 'count'})
    class_performance['no_show'] = class_visit_data[(class_visit_data['signed_in'] == False) & (class_visit_data['late_cancelled'] == False)].groupby('class_name').agg({'class_name' : 'count'})
    class_performance['late_cancel'] = class_visit_data[(class_visit_data['signed_in'] == False) & (class_visit_data['late_cancelled'] == True)].groupby('class_name').agg({'class_name' : 'count'})

    class_performance.fillna(0, inplace=True)
    class_performance.reset_index(inplace=True)

    class_performance = class_performance.append(class_performance.sum(numeric_only=True), ignore_index=True)
    class_performance.fillna('Total', inplace=True)

    class_performance['percent_no_show'] = round(100 * class_performance['no_show'] / class_performance['booked'], 2)
    class_performance['percent_late_cancel'] = round(100 * class_performance['late_cancel'] / class_performance['booked'], 2)
    class_performance['percent_capacity_attendance'] = round(100 * class_performance['signed_in'] / class_performance['capacity'], 2)

    class_performance.rename(columns = {"index": pd.to_datetime(class_visit_data['start_date_time'].iloc[0]).strftime('%B-%Y')}, inplace = True)
    class_performance.set_index(pd.to_datetime(class_visit_data['start_date_time'].iloc[0]).strftime('%B-%Y'), inplace=True)

    class_performance['mbo_site_id'] = Constants.MBO_SITE_ID
    class_performance['month_year'] = pd.to_datetime(class_visit_data['start_date_time'].iloc[0]).strftime('%B-%Y')
    
    db_insert_class_performance(class_performance)


def db_insert_instructor_performance(instructor_performance):

    for index, row in instructor_performance.iterrows():
        query = """SELECT * FROM InstructorPerformance WHERE month_year = "{}" AND mbo_site_id = {} AND instructor_name = "{}" """.format(row['month_year'], Constants.MBO_SITE_ID, index)
        existing_report = pd.read_sql(query, con=Constants.DB_CONNECTION)

        try:
            if existing_report.empty:
                with Constants.DB_CONNECTION.cursor() as cursor:
                    sql = """INSERT INTO InstructorPerformance (
                    `mbo_site_id`,
                    `month_year`,
                    `instructor_name`,
                    `total_classes`,
                    `capacity`,
                    `booked`,
                    `signed_in`,
                    `no_show`,
                    `late_cancel`,
                    `percent_no_show`,
                    `percent_late_cancel`,
                    `percent_capacity_attendance`)
                    VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
                    """
                    cursor.execute(sql, (row['mbo_site_id'],
                                         row['month_year'],
                                         index,
                                         row['total_classes'],
                                         row['capacity'],
                                         row['booked'],
                                         row['signed_in'],
                                         row['no_show'],
                                         row['late_cancel'],
                                         row['percent_no_show'],
                                         row['percent_late_cancel'],
                                         row['percent_capacity_attendance']))

                Constants.DB_CONNECTION.commit()

            else:
                with Constants.DB_CONNECTION.cursor() as cursor:
                    sql = """UPDATE InstructorPerformance SET
                    `mbo_site_id` = %s,
                    `month_year` = %s,
                    `instructor_name` = %s,
                    `total_classes` = %s,
                    `capacity` = %s,
                    `booked` = %s,
                    `signed_in` = %s,
                    `no_show` = %s,
                    `late_cancel` = %s,
                    `percent_no_show` = %s,
                    `percent_late_cancel` = %s,
                    `percent_capacity_attendance` = %s
                    WHERE `id` = %s
                    """
                    cursor.execute(sql, (row['mbo_site_id'],
                                         row['month_year'],
                                         index,
                                         row['total_classes'],
                                         row['capacity'],
                                         row['booked'],
                                         row['signed_in'],
                                         row['no_show'],
                                         row['late_cancel'],
                                         row['percent_no_show'],
                                         row['percent_late_cancel'],
                                         row['percent_capacity_attendance'],
                                         int(existing_report['id'].iloc[0]))) # WHERE id == id

                Constants.DB_CONNECTION.commit()
        except:
            print("Error {}".format(index))


# Instructor Performance
def build_instructor_performance(mbo_classes, mbo_visits, ledger):
    creche_visit_data = mbo_visits[mbo_visits['session_type_name'] == 'Creche']
    class_visit_data = mbo_visits[mbo_visits['session_type_name'] != 'Creche']

    creache_data = mbo_classes[mbo_classes['session_type_name'] == 'Creche']
    class_data = mbo_classes[mbo_classes['session_type_name'] != 'Creche']

    # Intstructor Performance
    index = pd.unique(class_data['staff_name'])
    instructor_performance = pd.DataFrame(index=pd.unique(class_data['staff_name'])).sort_index()

    instructor_performance['total_classes'] = class_data.groupby('staff_name').agg({'staff_name' : 'count'})
    instructor_performance['capacity'] = class_data.groupby('staff_name').agg({'max_capacity' : 'sum'})
    instructor_performance['booked'] = class_visit_data.groupby('staff_name').agg({'staff_name' : 'count'})
    instructor_performance['signed_in'] = class_visit_data[class_visit_data['signed_in'] == True].groupby('staff_name').agg({'staff_name' : 'count'})
    instructor_performance['no_show'] = class_visit_data[(class_visit_data['signed_in'] == False) & (class_visit_data['late_cancelled'] == False)].groupby('staff_name').agg({'staff_name' : 'count'})
    instructor_performance['late_cancel'] = class_visit_data[(class_visit_data['signed_in'] == False) & (class_visit_data['late_cancelled'] == True)].groupby('staff_name').agg({'staff_name' : 'count'})

    instructor_performance.fillna(0, inplace=True)
    instructor_performance.reset_index(inplace=True)

    instructor_performance = instructor_performance.append(instructor_performance.sum(numeric_only=True), ignore_index=True)
    instructor_performance.fillna('Total', inplace=True)

    instructor_performance['percent_no_show'] = round(100 * instructor_performance['no_show'] / instructor_performance['booked'], 2)
    instructor_performance['percent_late_cancel'] = round(100 * instructor_performance['late_cancel'] / instructor_performance['booked'], 2)
    instructor_performance['percent_capacity_attendance'] = round(100 * instructor_performance['signed_in'] / instructor_performance['capacity'], 2)

    instructor_performance.rename(columns = {"index": pd.to_datetime(class_visit_data['start_date_time'].iloc[0]).strftime('%B-%Y')}, inplace = True)
    instructor_performance.set_index(pd.to_datetime(class_visit_data['start_date_time'].iloc[0]).strftime('%B-%Y'), inplace=True)

    instructor_performance['mbo_site_id'] = Constants.MBO_SITE_ID
    instructor_performance['month_year'] = pd.to_datetime(class_visit_data['start_date_time'].iloc[0]).strftime('%B-%Y')
    
    db_insert_instructor_performance(instructor_performance)


# Build Report JSON and Emails
def fire_reports(event):
    
#     today = datetime.datetime.today()
    today = pd.to_datetime('2020-12-31')

    start_date_time = datetime.datetime.combine(today.replace(day=1), datetime.time.min)
    end_date_time = datetime.datetime.combine(datetime.datetime.today(), datetime.time.max)

    try:
        mbo_visits_query = """\
        SELECT * FROM MboVisit
        WHERE start_date_time >= '{}'
        AND start_date_time <= '{}'
        """.format(start_date_time, end_date_time)

        mbo_classes_query = """\
        SELECT * FROM MboClass
        WHERE start_date_time >= '{}'
        AND start_date_time <= '{}'
        """.format(start_date_time, end_date_time)

        ledger_query = """\
        SELECT * FROM ChargeBillingLedger
        WHERE createDate >= '{}'
        AND createDate <= '{}'
        """.format(start_date_time, end_date_time)

        mbo_visits = pd.read_sql(mbo_visits_query, con=Constants.DB_CONNECTION)
        mbo_classes = pd.read_sql(mbo_classes_query, con=Constants.DB_CONNECTION)
        ledger = pd.read_sql(ledger_query, con=Constants.DB_CONNECTION)

        build_location_performance_report(mbo_classes, mbo_visits, ledger)
        build_class_performance_report(mbo_classes, mbo_visits, ledger)
        build_instructor_performance(mbo_classes, mbo_visits, ledger)

    except:
        print("Error building reports")
    
    now = time.localtime()
    date_minus_3 = pd.to_datetime(event['end_date']) + relativedelta(months=-3)
    date_minus_3 = pd.to_datetime(date_minus_3).strftime('%B-%Y')

    date_minus_2 = pd.to_datetime(event['end_date']) + relativedelta(months=-2)
    date_minus_2 = pd.to_datetime(date_minus_2).strftime('%B-%Y')

    date_minus_1 = pd.to_datetime(event['end_date']) + relativedelta(months=-1)
    date_minus_1 = pd.to_datetime(date_minus_1).strftime('%B-%Y')

    date_minus_0 = pd.to_datetime(event['end_date']).strftime('%B-%Y')

    try:
        location_performance_query = f"SELECT * FROM LocationPerformance WHERE month_year IN ('{date_minus_3}', '{date_minus_2}', '{date_minus_1}', '{date_minus_0}')"
        instructor_performance_query = f"SELECT * FROM InstructorPerformance WHERE month_year IN ('{date_minus_3}', '{date_minus_2}', '{date_minus_1}', '{date_minus_0}')"
        class_performance_query = f"SELECT * FROM ClassPerformance WHERE month_year IN ('{date_minus_3}', '{date_minus_2}', '{date_minus_1}', '{date_minus_0}')"

        location_performance = pd.read_sql(location_performance_query, con=Constants.DB_CONNECTION)
        instructor_performance = pd.read_sql(instructor_performance_query, con=Constants.DB_CONNECTION)
        class_performance = pd.read_sql(class_performance_query, con=Constants.DB_CONNECTION)

        # Sort by month_year
        for index, row in location_performance.iterrows():
            location_performance.at[index, 'sort_date'] = pd.to_datetime(row['month_year'])
        location_performance = location_performance.sort_values(by=['sort_date'])

        for index, row in instructor_performance.iterrows():
            instructor_performance.at[index, 'sort_date'] = pd.to_datetime(row['month_year'])
        instructor_performance = instructor_performance.sort_values(by=['sort_date'])

        for index, row in class_performance.iterrows():
            class_performance.at[index, 'sort_date'] = pd.to_datetime(row['month_year'])
        class_performance = class_performance.sort_values(by=['sort_date'])

    except:
        print("Error Firing Reports")
    
    write_excel_sheets(event, location_performance, instructor_performance, class_performance)
    send_reports_email()


def clean_name_for_excel(c):
    #     ']:*?/\'
    c = c.replace('[','')
    c = c.replace(']','')
    c = c.replace(':','')
    c = c.replace('?','')
    c = c.replace('/','')
    c = c.replace('\\','')
    c = c.replace('*','')
    if len(c) > 31:
        c = c[:31]
    return c


def write_excel_sheets(event, location_performance, instructor_performance, class_performance):

    writer = ExcelWriter('Group Location Performance.xlsx')

    mtd_club_performance = build_mtd_club_performance(event, location_performance)
    mtd_club_performance.to_excel(writer,pd.to_datetime(event['end_date']).strftime('%B-%Y'),index=True)
    worksheet_object  = writer.sheets[pd.to_datetime(event['end_date']).strftime('%B-%Y')]
    worksheet_object.set_column('A:Z', 24)

    data = location_performance.copy(deep=True)

    club_performance_data = {}
    locations_array = data['class_location_name'].unique()
    for location in locations_array:
        print(location)
        club_performance = data[data['class_location_name'] == location]
        club_performance = club_performance.drop(['id','mbo_site_id','sort_date','class_location_name'], axis=1)

        club_performance_columns = [location,'Total Classes', 'Capacity', 'Booked', 'Signed In', 'No Show',
               'Late Cancel', 'Total Forgive', 'No Show Charges',
               'Late Cancel Charges', 'No Show Charge Pending',
               'Late Cancel Charge Pending', 'No Show Dispute Approved',
               'Late Cancel Dispute Approved', '% No Show', '% Late Cancel',
               '% Capacity Attendance', 'Average Charges Per Member']

        club_performance.columns = club_performance_columns
        club_performance = club_performance.transpose()
        club_performance.columns = club_performance.iloc[0]
        location = clean_name_for_excel(location)
        club_performance.drop(club_performance.index[0], inplace=True)
        try:
            club_performance['Difference'] = pd.to_numeric(club_performance.iloc[:,-1], errors='ignore') - pd.to_numeric(club_performance.iloc[:,-2], errors='ignore')
        except:
            print(f"Error write_excel_sheets() - Difference")
        club_performance.to_excel(writer,location,index=True)
        worksheet_object  = writer.sheets[location]
        worksheet_object.set_column('A:Z', 14)
        club_performance_data.update({location : club_performance.to_dict()})
    writer.save()

    # this is for email spreadsheet & JSON
    # club_performance_data

    writer = ExcelWriter('Group Instructor Performance.xlsx')
    data = instructor_performance.copy(deep=True)
    gfi_performance_data = {}
    instructors_array = data['instructor_name'].unique()
    for instructor in instructors_array:
    #     print(instructor)
        gfi_performance = data[data['instructor_name'] == instructor]
        gfi_performance = gfi_performance.drop(['id','mbo_site_id','sort_date','instructor_name'], axis=1)

        gfi_performance_columns = [instructor, 'Total Classes', 'Capacity', 'Booked', 'Signed In', 'No Show',
           'Late Cancel', '% No Show', '% Late Cancel', '% Capacity Attendance']

        gfi_performance.columns = gfi_performance_columns
        gfi_performance = gfi_performance.transpose()
        gfi_performance.columns = gfi_performance.iloc[0]
        instructor = clean_name_for_excel(instructor)
        gfi_performance.drop(gfi_performance.index[0], inplace=True)
        try:
            gfi_performance['Difference'] = pd.to_numeric(gfi_performance.iloc[:,-1], errors='ignore') - pd.to_numeric(gfi_performance.iloc[:,-2], errors='ignore')
        except:
            print(f"Error {instructor}")
        gfi_performance.to_excel(writer,instructor,index=True)
        worksheet_object  = writer.sheets[instructor]
        worksheet_object.set_column('A:Z', 14)
        gfi_performance_data.update({instructor : gfi_performance.to_dict()})

    writer.save()

    writer = ExcelWriter('Group Class Performance.xlsx')
    data = class_performance.copy(deep=True)
    c_performance_data = {}
    class_array = data['class_name'].unique()
    for c in class_array:
    #     print(instructor)
        c_performance = data[data['class_name'] == c]
        c_performance = c_performance.drop(['id','mbo_site_id','sort_date','class_name'], axis=1)

        class_performance_columns = [c, 'Total Classes', 'Capacity', 'Booked', 'Signed In', 'No Show',
           'Late Cancel', '% No Show', '% Late Cancel', '% Capacity Attendance']

        c_performance.columns = gfi_performance_columns
        c_performance = c_performance.transpose()
        c_performance.columns = c_performance.iloc[0]
        c = clean_name_for_excel(c)
        c_performance.drop(c_performance.index[0], inplace=True)
        try:
            c_performance['Difference'] = pd.to_numeric(c_performance.iloc[:,-1], errors='ignore') - pd.to_numeric(c_performance.iloc[:,-2], errors='ignore')
        except:
            print(f"Error {c}")
        c_performance.to_excel(writer,c,index=True)
        worksheet_object  = writer.sheets[c]
        worksheet_object.set_column('A:Z', 14)
        c_performance_data.update({c : c_performance.to_dict()})

    writer.save()


def get_report_emails():
    Constants.FP_GYM_DATA = get_gym_data()
    gfm_emails = Constants.EXECUTIVE_EMAILS
    for g in Constants.FP_GYM_DATA:
        if g['locationId'] != 4:
            gfm = get_staff_data(g['groupFitnessManager'])
            gfm_emails.append(gfm['email'])

    return gfm_emails
#     return ["clint@thefitnessplayground.com.au",]


def send_reports_email():
    try:
        fromaddr = Constants.EMAIL_USER_NAME
#         toaddr = ["clint@thefitnessplayground.com.au",]
        toaddr = get_report_emails()

        msg = MIMEMultipart()
        msg['From'] = fromaddr
        msg['To'] = COMMASPACE.join(toaddr)
        msg['Subject'] = "NoShow | Group Fitness Report {}".format(datetime.datetime.now().strftime('%d %b %Y'))
        msg['Date'] = formatdate(localtime=True)

        body = """\
        Report run time: {}

        Hey Team,

        Here is your NoShow | Group Fitness Report

        From your friendly No Show software.

        You grill 'em and I'll bill 'em
        """.format(formatdate(localtime=True))

        msg.attach(MIMEText(body, 'plain'))

        filename = "Group Location Performance.xlsx"

        part = MIMEBase('application', 'octet-stream')
        part.set_payload(open(filename, "rb").read())
        encoders.encode_base64(part)
        part.add_header('Content-Disposition', "attachment; filename= %s" % filename)

        msg.attach(part)

        filename = "Group Instructor Performance.xlsx"

        part = MIMEBase('application', 'octet-stream')
        part.set_payload(open(filename, "rb").read())
        encoders.encode_base64(part)
        part.add_header('Content-Disposition', "attachment; filename= %s" % filename)

        msg.attach(part)

        filename = "Group Class Performance.xlsx"

        part = MIMEBase('application', 'octet-stream')
        part.set_payload(open(filename, "rb").read())
        encoders.encode_base64(part)
        part.add_header('Content-Disposition', "attachment; filename= %s" % filename)

        msg.attach(part)

        my_email= Constants.EMAIL_USER_NAME
        my_email_pass = Constants.EMAIL_PASSWORD
        server_ssl = smtplib.SMTP_SSL('smtp.gmail.com', 465)
        server_ssl.login(my_email, my_email_pass)
        text = msg.as_string()
        server_ssl.sendmail(fromaddr, toaddr, text)
        server_ssl.quit()
        print('Report sent :-)')
    except:
        print('Report Failed to send :-(')


def build_ui_report_entity(_class, _visit, ledger, siteId, location = None):
    
    _class = _class.copy(deep=True)
    _visit = _visit.copy(deep=True)
    ledger_data = ledger.copy(deep=True)

    if location is not None:
        _class = _class[_class['class_location_name'] == location]
        _visit = _visit[_visit['class_location_name'] == location]
        ledger_data = ledger_data[ledger_data['class_location_name'] == location]
    else:
        location = 'Total'
    
    instructor__visit = _visit[pd.to_datetime(_visit['start_date_time']) >=  datetime.datetime.today() - pd.Timedelta(days=30)]
    instructor__class = _class[pd.to_datetime(_class['start_date_time']) >=  datetime.datetime.today() - pd.Timedelta(days=30)]
    
    total_capacity = _class['max_capacity'].sum()
    
    _class['start_date_time'] = _class.apply(lambda row: row['start_date_time'].to_period("M") , axis=1)
    
    _visit['start_date_time'] = _visit.apply(lambda row: row['start_date_time'].to_period("M") , axis=1)
    
    ledger_data['class_start_datetime'] = ledger_data.apply(lambda row: row['class_start_datetime'].to_period("M") , axis=1)

    index = pd.unique(_class['start_date_time'])
    club_performance = pd.DataFrame(index=index).sort_index()

    club_performance['total_classes'] = _class.groupby('start_date_time').agg({'start_date_time' : 'count'})
    club_performance['capacity'] = _class.groupby('start_date_time').agg({'max_capacity' : 'sum'})

    club_performance['total_booked'] = _class.groupby('start_date_time').agg({'total_booked' : 'sum'})
    club_performance['total_visited'] = _visit.groupby('start_date_time').agg({'start_date_time' : 'count'})
    club_performance['signed_in'] = _visit[_visit['signed_in'] == True].groupby('start_date_time').agg({'start_date_time' : 'count'})
    club_performance['no_show'] = _visit[(_visit['signed_in'] == False) & (_visit['late_cancelled'] == False)].groupby('start_date_time').agg({'start_date_time' : 'count'})
    club_performance['late_cancel'] = _visit[(_visit['signed_in'] == False) & (_visit['late_cancelled'] == True)].groupby('start_date_time').agg({'start_date_time' : 'count'})

    club_performance['total_forgive'] = ledger_data[ledger_data['charge_status'] == Constants.CHARGE_STATUS_FORGIVE].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})

    club_performance['no_show_charges'] = ledger_data[ledger_data['charge_status'] == Constants.CHARGE_STATUS_NO_SHOW_COMPLETE].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})
    club_performance['no_show_charge_pending'] = ledger_data[ledger_data['charge_status'] == Constants.CHARGE_STATUS_NO_SHOW_CHARGE_PENDING].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})
    club_performance['no_show_dispute_approved'] = ledger_data[ledger_data['charge_status'].isin([Constants.CHARGE_STATUS_NO_SHOW_ABORT, Constants.CHARGE_STATUS_NO_SHOW_REFUND])].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})
    club_performance['no_show_refund'] = ledger_data[ledger_data['charge_status'] == Constants.CHARGE_STATUS_NO_SHOW_REFUND].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})
    club_performance['no_show_error'] = ledger_data[ledger_data['charge_status'] == Constants.CHARGE_STATUS_NO_SHOW_ERROR].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})

    club_performance['late_cancel_charges'] = ledger_data[ledger_data['charge_status'] == Constants.CHARGE_STATUS_LATE_CANCEL_COMPLETE].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})
    club_performance['late_cancel_charge_pending'] = ledger_data[ledger_data['charge_status'] == Constants.CHARGE_STATUS_LATE_CANCEL_CHARGE_PENDING].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})
    club_performance['late_cancel_dispute_approved'] = ledger_data[ledger_data['charge_status'].isin([Constants.CHARGE_STATUS_LATE_CANCEL_ABORT, Constants.CHARGE_STATUS_LATE_CANCEL_REFUND])].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})
    club_performance['late_cancel_refund'] = ledger_data[ledger_data['charge_status'].isin([Constants.CHARGE_STATUS_LATE_CANCEL_REFUND])].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})
    club_performance['late_cancel_error'] = ledger_data[ledger_data['charge_status'].isin([Constants.CHARGE_STATUS_LATE_CANCEL_ERROR])].groupby('class_start_datetime').agg({'class_start_datetime' : 'count'})

    club_performance.fillna(0, inplace=True)

    club_performance['total_charges'] = round((club_performance['no_show_charges'] + club_performance['late_cancel_charges']), 2)
    club_performance['avg_charge_per_member'] = round((club_performance['no_show_charges'] + club_performance['late_cancel_charges']) / len(_visit['client_id'].unique()), 2)

    club_performance['no_show_revenue'] = club_performance['no_show_charges'] * Constants.MBO_PRODUCT_NO_SHOW['Price']
    club_performance['late_cancel_revenue'] = club_performance['late_cancel_charges'] * Constants.MBO_PRODUCT_LATE_CXL['Price']
    club_performance.fillna(0, inplace=True)
    club_performance['total_revenue'] = club_performance['no_show_revenue'] + club_performance['late_cancel_revenue']

    club_performance['percent_no_show'] = round(club_performance['no_show'] / club_performance['total_booked'], 4)
    club_performance['percent_late_cancel'] = round(club_performance['late_cancel'] / club_performance['total_booked'], 4)
    club_performance['percent_capacity_attendance'] = round(club_performance['signed_in'] / club_performance['capacity'], 4)

    club_performance = club_performance.reset_index() #.rename(columns={'index': 'date'})
    club_performance['index'] = club_performance['index'].dt.strftime("%Y-%m")

    instructor_performance = pd.DataFrame(index=pd.unique(instructor__class['staff_name'])).sort_index()

    instructor_performance['total_classes'] = instructor__class.groupby('staff_name').agg({'staff_name' : 'count'})
    instructor_performance['capacity'] = instructor__class.groupby('staff_name').agg({'max_capacity' : 'sum'})
    instructor_performance['unique_visits'] = instructor__visit.groupby('staff_name').agg({'client_id' :'nunique'})
    instructor_performance['total_visited'] = instructor__visit.groupby('staff_name').agg({'staff_name' : 'count'})
    instructor_performance['total_booked'] = instructor__class.groupby('staff_name').agg({'total_booked' : 'sum'})
    instructor_performance['signed_in'] = instructor__visit[instructor__visit['signed_in'] == True].groupby('staff_name').agg({'signed_in' : 'count'})
    instructor_performance['no_show'] = instructor__visit[(instructor__visit['signed_in'] == False) & (instructor__visit['late_cancelled'] == False)].groupby('staff_name').agg({'staff_name' : 'count'})
    instructor_performance['late_cancel'] = instructor__visit[(instructor__visit['signed_in'] == False) & (instructor__visit['late_cancelled'] == True)].groupby('staff_name').agg({'staff_name' : 'count'})
    instructor_performance['percent_no_show'] = round(instructor_performance['no_show'] / instructor_performance['total_booked'], 4)
    instructor_performance['percent_late_cancel'] = round(instructor_performance['late_cancel'] / instructor_performance['total_booked'], 4)
    instructor_performance['percent_capacity_attendance'] = round(instructor_performance['signed_in'] / instructor_performance['capacity'], 4)

    instructor_performance.fillna(0, inplace=True)

#     total_capacity = instructor_performance['capacity'].sum()
    total_signed_in = instructor_performance['signed_in'].sum()
    total_classes = instructor_performance['total_classes'].sum()

    # print(f"total_capacity = {total_capacity} | total_signed_in = {total_signed_in} | total_classes = {total_classes}")

    average_class_size = round(instructor_performance['capacity'] / instructor_performance['total_classes'],2)
    
    print(f"\naverage_class_size = {average_class_size} | total_capacity = {total_capacity} | total_signed_in = {total_signed_in} | total_classes = {total_classes}")
    
    instructor_performance['average_class_size'] = instructor_performance['capacity'] / instructor_performance['total_classes']
    
    instructor_performance['performance_comparitor'] =  instructor_performance['signed_in'] / total_capacity
    instructor_performance.sort_values('performance_comparitor', ascending=False, inplace=True)
    instructor_performance = instructor_performance.reset_index()

    class_performance = pd.DataFrame(index=pd.unique(_class['class_name'])).sort_index()

    class_performance['total_classes'] = instructor__class.groupby('class_name').agg({'class_name' : 'count'})
    class_performance['capacity'] = instructor__class.groupby('class_name').agg({'max_capacity' : 'sum'})
    class_performance['unique_visits'] = instructor__visit.groupby('class_name').agg({'client_id' :'nunique'})
    class_performance['total_visited'] = instructor__visit.groupby('class_name').agg({'class_name' : 'count'})
    class_performance['total_booked'] = instructor__class.groupby('class_name').agg({'total_booked' : 'sum'})
    class_performance['signed_in'] = instructor__visit[instructor__visit['signed_in'] == True].groupby('class_name').agg({'signed_in' : 'count'})
    class_performance['no_show'] = instructor__visit[(instructor__visit['signed_in'] == False) & (instructor__visit['late_cancelled'] == False)].groupby('class_name').agg({'class_name' : 'count'})
    class_performance['late_cancel'] = instructor__visit[(instructor__visit['signed_in'] == False) & (instructor__visit['late_cancelled'] == True)].groupby('class_name').agg({'class_name' : 'count'})
    class_performance['percent_no_show'] = round(class_performance['no_show'] / class_performance['total_booked'], 4)
    class_performance['percent_late_cancel'] = round(class_performance['late_cancel'] / class_performance['total_booked'], 4)
    class_performance['percent_capacity_attendance'] = round(class_performance['signed_in'] / class_performance['capacity'], 4)

    class_performance.fillna(0, inplace=True)

#     total_capacity = class_performance['capacity'].sum()
    total_signed_in = class_performance['signed_in'].sum()
    total_classes = class_performance['total_classes'].sum()
    
    class_performance['average_class_size'] = round(class_performance['capacity'] / class_performance['total_classes'],2)

#     print(f"total_capacity = {total_capacity} | total_signed_in = {total_signed_in} | total_classes = {total_classes}")

    class_performance['performance_comparitor'] = class_performance['signed_in'] / total_capacity
    class_performance.sort_values('performance_comparitor', ascending=False, inplace=True)
    class_performance = class_performance.reset_index()

    report_data = {
        "updateDate": datetime.datetime.now(datetime.timezone.utc).isoformat(),
        "name": location,
        "siteId": siteId,
        "table_data": club_performance[-6:].to_dict(orient='records'),
        "visualisation_data": club_performance[['index','percent_capacity_attendance','percent_late_cancel','percent_no_show']].to_dict(orient='records'),
        "instructor_performance_data": instructor_performance[:10].to_dict(orient='records'),
        "class_performance_data": class_performance[:10].to_dict(orient='records')
    }
    
    return report_data


def get_location_name_by_id_from_ledger(location_id, ledger):
    for index, row in ledger.iterrows():
        if row['class_location_id'] == location_id:
            return row['class_location_name']
    return None


def list_service_accounts(project_id):
    """Lists all service accounts for the current project."""

#     credentials = service_account.Credentials.from_service_account_file(
#         filename='/Users/clintsellen/Desktop/Uni/UTS/Semester 9/Advanced Sortware Development/asd-project/Server/mbo-no-show-ui-42d2e-firebase-adminsdk-nnlbs-14c0924057.json',
#         scopes=['https://www.googleapis.com/auth/cloud-platform'])

    credentials = service_account.Credentials.from_service_account_file(
        filename=Constants.GOOGLE_APPLICATION_CREDENTIALS_FILE,
        scopes=['https://www.googleapis.com/auth/cloud-platform'])

    service = googleapiclient.discovery.build(
        'iam', 'v1', credentials=credentials)

    service_accounts = service.projects().serviceAccounts().list(
        name='projects/' + project_id).execute()

    return service_accounts


def fire_ui_reports(NoShowClient):
    
    today = datetime.datetime.today()

#     From the 1st 12 months ago to now
    start_date_time = (datetime.date.today().replace(day=1) - relativedelta(months=12)).strftime("%Y-%m-%dT%H:%M:%S")
    end_date_time = datetime.datetime.combine(datetime.datetime.today(), datetime.time.max)

    print(f"\nReports:\n\tstart_date_time {start_date_time}\n\tend_date_time {end_date_time}\n")

    try:
        mbo_visits_query = """\
        SELECT * FROM MboVisit
        WHERE start_date_time >= '{}'
        AND start_date_time <= '{}'
        """.format(start_date_time, end_date_time)

        mbo_classes_query = """\
        SELECT * FROM MboClass
        WHERE start_date_time >= '{}'
        AND start_date_time <= '{}'
        """.format(start_date_time, end_date_time)

        ledger_query = """\
        SELECT * FROM ChargeBillingLedger
        WHERE createDate >= '{}'
        AND createDate <= '{}'
        """.format(start_date_time, end_date_time)

        mbo_visits = pd.read_sql(mbo_visits_query, con=Constants.DB_CONNECTION)
        mbo_classes = pd.read_sql(mbo_classes_query, con=Constants.DB_CONNECTION)
        ledger = pd.read_sql(ledger_query, con=Constants.DB_CONNECTION)
        
        list_service_accounts("mbo-no-show-ui-42d2e")
        os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = Constants.GOOGLE_APPLICATION_CREDENTIALS_FILE
        firebase_admin.initialize_app()
        db = firestore.client()

        print(f"\n\tmbo_visits: {len(mbo_visits)}\n\tmbo_classes: {len(mbo_classes)}\n\tledger: {len(ledger)}")

        class_black_list_names = json.loads(NoShowClient['class_black_list']).keys()
        class_black_list_ids = json.loads(NoShowClient['class_black_list']).values()
        # print(f"{class_black_list_names}\n{class_black_list_ids}")

        class_visit_data = mbo_visits[~mbo_visits['session_type_name'].isin(class_black_list_names)]
        class_data = mbo_classes[~mbo_classes['session_type_id'].isin(class_black_list_ids)]

        data = build_ui_report_entity(class_data, class_visit_data, ledger, NoShowClient['mbo_site_id'])
        # print(f"\n{data}")

        reports_ref = db.collection(u'users').document(NoShowClient['firebase_auth_id']).collection(u'Report')

        reports_ref.document(u'0').set(data)

        location_ids = ledger['class_location_id'].unique()

        for l in location_ids:
            location_name = get_location_name_by_id_from_ledger(l, ledger)
            print(f"location_name: {location_name} | location_id {l}")
            data = build_ui_report_entity(class_data, class_visit_data, ledger, NoShowClient['mbo_site_id'], location_name)
        #     print(f"\n{l}:\n{data}")
            reports_ref.document(l).set(data)

    except Exception as ex:
        print(f"Error building reports: {ex}")


# MAIN
def main():

    # Init Clock
    start = time.time()

    # Get datetime
    today = datetime.datetime.today()

    # THIS MOVES THE TIME WINDOW RETROSPECTIVELY BY N DAYS := WARNING
#    N = 1
#    today = today - datetime.timedelta(N)
    # THIS MOVES THE TIME WINDOW RETROSPECTIVELY BY N DAYS

    # Set END time window
    end_today = datetime.datetime.combine(today, datetime.time.max)

    # THIS SEEDS THE DATABASE WITH MTD DATA := WARNING
#     today = today.replace(day=1)
    # THIS SEEDS THE DATABASE WITH MTD DATA
    
    # THIS MOVES THE START TIME WINDOW RETROSPECTIVELY BY N DAYS := WARNING
#     N = 1
#     today = today - datetime.timedelta(N)
    # THIS MOVES THE START TIME WINDOW RETROSPECTIVELY BY N DAYS := WARNING

    # Set START time window
    start_today = datetime.datetime.combine(today, datetime.time.min)


    print(f"\nSTART: {datetime.datetime.now()}\t\t\tToday = Start: {start_today} | End: {end_today}\n")

# ################################################### TESTING ONLY
#     body = {
#         "database_auth_credentials": Constants.TEST_DATABASE_CREDENTIALS,
#         "mbo_site_id": "152065",
#         "start_date": start_today.strftime("%Y-%m-%dT%H:%M:%S"),
#         "end_date": end_today.strftime("%Y-%m-%dT%H:%M:%S")
#     }
# ################################################### TESTING ONLY
    
# ################################################### PRODUCTION ONLY
    Constants.IS_TEST = False
    body = {
        "database_auth_credentials": "c291cmNlLXByb2QuY2x1c3Rlci1jbGdmeXB4M3NtbWguYXAtc291dGhlYXN0LTIucmRzLmFtYXpvbmF3cy5jb206bWJvX25vX3Nob3c6c291cmNlOnNvdXJjZSQxOTg0",
        "mbo_site_id": "152065",
        "start_date": start_today.strftime("%Y-%m-%dT%H:%M:%S"),
        "end_date": end_today.strftime("%Y-%m-%dT%H:%M:%S")
    }
# ################################################### PRODUCTION ONLY

    request = {
        "body": json.dumps(body)
    }

    simulated_trigger_request = json.loads(request['body'])

    try:
        event = simulated_trigger_request

        # Open Database Connection
        decoded_database_credentials(event)
        DB_MAKE_CONNECTION()

        #     Get NoShow Client from RDS
#         TODO: This is throwing this warning:
#         FutureWarning: Using short name for 'orient' is deprecated. Only the options: ('dict', list, 'series', 'split', 'records', 'index') will be used in a future version. Use one of the above to silence this warning.
        NoShowClient = db_get_no_show_client(event)

        if (NoShowClient['subscription_status'] == Constants.STATUS_ACTIVE and str(event['mbo_site_id']) == str(NoShowClient['mbo_site_id'])):

            print('Set Constants')
            SET_CONSTANTS(NoShowClient)
            
            if Constants.IS_TEST:
                print(f"NoShowClient:\n{NoShowClient}\nEvent:\n{event}")

            print(f"\tAcitve: {Constants.MBO_SITE_ID}\n\tisTest: {Constants.IS_TEST}\n\tisPreLaunch: {Constants.IS_PRE_LAUNCH}\n\tBilling: {Constants.WILL_RUN_BILLING}\n\tClass Black List: {Constants.CLASS_BLACK_LIST}\n")

            # Get Classes
            print("Get Classes")
            mbo_classes = mbo_get_classes(event['start_date'], event['end_date'])
            if len(mbo_classes) == 0:
                SET_CONSTANTS(NoShowClient)
                mbo_classes = mbo_get_classes(event['start_date'], event['end_date'])
            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Get todays classes from the database
            print("Get todays classes from the database")
            db_mbo_classes = db_get_classes(event['start_date'], event['end_date'])
            print(len(db_mbo_classes))
            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Get Visits for all classes
            print("Get Visits for all classes")
            for index, row in db_mbo_classes.iterrows():
                mbo_class_visits = mbo_get_class_visits(row)
            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Get todays NoShow from the database
            print("Get todays NoShow from the database")
            db_no_show_visits = db_get_class_visits(event['start_date'], event['end_date'], Constants.OFFENSE_TYPE_NO_SHOW)
            print(f"db_no_show_visits: {len(db_no_show_visits)}")
            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")
            
            # Get todays LateCxl from the database
            print("Get todays LateCxl from the database")
            db_late_cxl_visits = db_get_class_visits(event['start_date'], event['end_date'], Constants.OFFENSE_TYPE_LATE_CXL)
            print(f"db_late_cxl_visits: {len(db_late_cxl_visits)}")

            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # NoShow GetClients from Database
            print("NoShow GetClients from Database")
            no_show_list = pd.unique(db_no_show_visits['client_id'])
            print(f"no_show_list: {len(no_show_list)}")

            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # NoShow GetClients from Database
            print("NoShow GetClients from Database")
            late_cxl_list = pd.unique(db_late_cxl_visits['client_id'])
            print(f"late_cxl_list: {len(late_cxl_list)}")

            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Merge the two lists
            print("Merge the two lists")
            member_list = np.append(late_cxl_list, no_show_list)
            print(f"Member list length: {len(member_list)}")

            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Get Clients from MBO
            print("Get Clients from MBO")
            mbo_clients = mbo_get_clients(member_list)
            print(len(mbo_clients))

            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Add to ledger - No Show
            print("Add to ledger - No Show")
            if len(db_no_show_visits) > 0:
                documents = create_charge_billing_ledger_documents(mbo_clients, db_no_show_visits, Constants.OFFENSE_TYPE_NO_SHOW)
                print(f"Documents: {len(documents)}")
                db_insert_new_ledger_documents(documents)

            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Add to ledger - Late Cancel
            print("Add to ledger - Late Cancel")
            if len(db_late_cxl_visits) > 0:
                documents = create_charge_billing_ledger_documents(mbo_clients, db_late_cxl_visits, Constants.OFFENSE_TYPE_LATE_CXL)
                print(f"Documents: {len(documents)}")
                db_insert_new_ledger_documents(documents)

            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Process Class Charges
            print("Process Class Charges")
            process_class_charges(event['start_date'])

            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Communications
            print("Communications")            
            mbo_get_services()
            unprocessed_comms = db_get_unprocessed_communications()
            send_emails(unprocessed_comms)
            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Billing
            print("Billing")
            process_billing(event['end_date'])
            end = time.time()
            print(f"Time: {round((end - start) / 60, 2)}min")

            # Reports
            print("Reports")
            fire_ui_reports(NoShowClient)
            
#             if today.day == calendar.monthrange(today.year, today.month)[1]:
#                 print('Last day of the month')
#                 if Constants.IS_TEST is False:
#                     fire_reports(event)
#                     end = time.time()
#                     print(f"Time: {round((end - start) / 60, 2)}min")

    finally:
        DB_CLOSE_CONNECTION()

        end = time.time()
        print(f"\nEND: {datetime.datetime.now()}\t\t\tRun Time: {round((end - start) / 60, 2)}min")

if __name__ == "__main__":
    main()
