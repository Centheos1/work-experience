import datetime
from datetime import timedelta
from datetime import time
from dateutil import tz
from dateutil.relativedelta import relativedelta
import calendar
import holidays
from calendar import monthrange
from pandas import ExcelWriter
from pandas import ExcelFile
from numpy import inf
import pandas as pd
import numpy as np
import pymysql
import smtplib
import os
from os.path import basename
from email.mime.application import MIMEApplication
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email.utils import COMMASPACE, formatdate
from email import encoders
import json
import requests
from requests.auth import HTTPBasicAuth
from google.oauth2 import service_account
import googleapiclient.discovery
import base64
from xml.etree import ElementTree
from statistics import *
import pytz

#python3 /home/clint/Desktop/reports-schedule/FP_Membership_v5.py | cat - /home/clint/Desktop/reports-schedule/log/FP_Membership_v5.log  > tmp && mv tmp  /home/clint/Desktop/reports-schedule/log/FP_Membership_v5.log


# ##############################################
# CONSTANTS.                                   #
# ##############################################
class Constants:
    CREDENTIALS = None
    AUTH_SPLIT_CHARACTER = 'AUTH_SPLIT_CHARACTER'
    CREDENTIALS_PATH = "CREDENTIALS_PATH"
    DATE_TIME_FORMAT = '%Y-%m-%d %H:%M:%S'
    GS_BASE_URL = 'https://login.gymsales.net/api/v1/'
    GS_LEAD_STATUS_MULTI_CLUB_URL = 'reports/lead_status_multi_club'
    GS_REPORT_LEAD_STATUS_URL = 'reports/lead_status'
    GS_REPORT_SALESPERSON_URL = 'reports/salesperson'
    GS_PEOPLE_URL = 'people/'
    GS_BK_COMPANY_ID = 2975
    GS_GW_COMPANY_ID = 2509
    GS_MK_COMPANY_ID = 1812
    GS_NT_COMPANY_ID = 1809
    GS_SH_COMPANY_ID = 1808
    
    SOURCE_BASE_URL = "https://source.fitnessplayground.com.au/v1/source/"
    GET_GYM_DETAILS_URL = 'getAllGyms'
    
    FS_PLAY_30_DAY_FEEDBACK_FP_FORM_ID = '3589859'
    FS_PLAY_30_DAY_FEEDBACK_BK_FORM_ID = '3714338'
    FS_BASE_URL = 'https://www.formstack.com/api/v2/'
    
    LISTEN360_BASE_URL = 'https://app.listen360.com/'


# ##############################################
# GET CREDENTIALS.                             #
# ##############################################
with open(Constants.CREDENTIALS_PATH) as json_file:
    Constants.CREDENTIALS = json.load(json_file)


# ##############################################
# HELPERS.                                     #
# ##############################################
def decode_credentials(CREDENTIALS):
    decoded_credentials = base64.b64decode(CREDENTIALS).decode("utf-8")
    split = decoded_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
    return {
        'USERNAME' : split[0],
        'PASSWORD' : split[1]
    }


def decode_database_credentials(CREDENTIALS):
    decoded_credentials = base64.b64decode(CREDENTIALS).decode("utf-8")
    split = decoded_credentials.split(Constants.AUTH_SPLIT_CHARACTER)
    return {
        'HOST' : split[0],
        'DATABASE' : split[1],
        'USERNAME' : split[2],
        'PASSWORD' : split[3]
    }

def send_error_notification(ex):
    
    EMAIL_CREDENTIALS = decode_email_credentials(os.environ['CLINT_EMAIL_CREDENTIALS'])

    fromaddr = EMAIL_CREDENTIALS['USERNAME']
    toaddr = ["clint@thefitnessplayground.com.au",]
    msg = MIMEMultipart()
    msg['From'] = fromaddr
    msg['To'] = COMMASPACE.join(toaddr)
    msg['Subject'] = "{} {}".format("Membership Dashboard v5 ERROR",formatdate(localtime=True))
    msg['Date'] = formatdate(localtime=True)
    body = """Membership Dashboard.
Report run time: {}

Error: {}

Thanks,
Clint
""".format(formatdate(localtime=True),ex)
    msg.attach(MIMEText(body, 'plain'))
    my_email= EMAIL_CREDENTIALS['USERNAME']
    my_email_pass = EMAIL_CREDENTIALS['PASSWORD']
    server_ssl = smtplib.SMTP_SSL('smtp.gmail.com', 465)
    server_ssl.login(my_email, my_email_pass)
    text = msg.as_string()
    server_ssl.sendmail(fromaddr, toaddr, text)
    server_ssl.quit()
    print(ex)
    

def get_work_days_membership(d = None):
    if d is None:
        d = datetime.datetime.today()
    else:
        d = pd.to_datetime(d).date()
        
    num_days_month = calendar.monthrange(d.year,d.month)
    holiday_days_this_month = 0
    holidays_this_month = []
    
    for i in holidays.CountryHoliday('AU', prov='NSW', state=None, years=d.year).items():
        if i[0].month == d.month:
            print(i)
            holidays_this_month.append(i[0].day)
            holiday_days_this_month += 1
            
    work_days_this_month = 0
    days_worked_this_month = 0
    
    for i in range(1,num_days_month[1]+1):
        day = datetime.datetime(d.year, d.month, i).weekday()
        if (day != 5 and day != 6):
            if i not in holidays_this_month:
                work_days_this_month += 1
            if i <= d.day and i not in holidays_this_month:
                days_worked_this_month += 1
        else:
            if i not in holidays_this_month:
                work_days_this_month += 0.5
                days_worked_this_month += 0.5
                
#         print(f"day: {day} | i: {i} | days_worked_this_month: {days_worked_this_month} | work_days_this_month: {work_days_this_month}")
#     print(f"{days_worked_this_month}/{work_days_this_month}")
    return {
        'number of days worked' : days_worked_this_month,
        'number of work days' : work_days_this_month
    }

def predictor_num_day_month(d = None):
    if d is None: 
        d = datetime.datetime.today()
    else:
        d = pd.to_datetime(d).date()
        
    num_days_month = monthrange(d.year,d.month)
    
#     print(f"[predictor_num_day_month] num_days_month: {num_days_month}")

    predictor_num_day_month = 0
    last_day = num_days_month[1]
    
    for i in range(2, last_day + 1):
        day = datetime.datetime(d.year, d.month, i).weekday()
        if (i == last_day):
            predictor_num_day_month = predictor_num_day_month + 1.5
        elif (day == 0 or day == 6):
            predictor_num_day_month = predictor_num_day_month + 0.5
        else:
            predictor_num_day_month = predictor_num_day_month + 1
    
    return predictor_num_day_month

def num_working_days_worked(d = None):
    if d is None:
        d = datetime.datetime.now()
    else:
        d = pd.to_datetime(d).date()
        
    day_of_month = d.day
    num_days_month = monthrange(d.year,d.month)
    last_day = num_days_month[1]

    num_days_worked = 0
#     day_of_month
    for i in range(2, day_of_month + 1):
    #print(i)
        day = datetime.datetime(d.year, d.month, i).weekday()
        if (i == last_day):
            num_days_worked = num_days_worked + 1.5
        elif (day == 0 or day == 6):
            num_days_worked = num_days_worked + 0.5
        else:
            num_days_worked = num_days_worked + 1

    return num_days_worked

def get_gym_name(x):
    x = str(x)
    switcher={
        '3' : 'Marrickville',
        '2' : 'Newtown',
        '1' : 'Surry Hills',
        '5' : 'Bunker',
        '6' : 'Virtual'
    }
    return switcher.get(x, x)

def gs_get_company_id(gym_name):
    if gym_name is None:
        return gym_name
    
    default = Constants.GS_SH_COMPANY_ID
    gym_name = gym_name.replace(' ','').lower()
    switcher = {
        'surryhills' : Constants.GS_SH_COMPANY_ID,
        'newtown' : Constants.GS_NT_COMPANY_ID,
        'marrickville': Constants.GS_MK_COMPANY_ID,
        'gateway' : Constants.GS_GW_COMPANY_ID,
        'bunker' : Constants.GS_BK_COMPANY_ID,
        'thebunker' : Constants.GS_BK_COMPANY_ID,
    }
    return switcher.get(gym_name, default)

def gs_get_gym_from_company_id(company_id):
    if company_id is None:
        return company_id
    
    switcher = {
        Constants.GS_SH_COMPANY_ID : 'Surry Hills',
        Constants.GS_NT_COMPANY_ID : 'Newtown',
        Constants.GS_MK_COMPANY_ID: 'Marrickville',
        Constants.GS_GW_COMPANY_ID : 'Gateway',
        Constants.GS_BK_COMPANY_ID : 'Bunker'
    }
    return switcher.get(company_id, None)

def convert_datetime_timezone(dt, tz1 = 'Australia/Sydney', tz2 = 'EST'):
    tz1 = pytz.timezone(tz1)
    tz2 = pytz.timezone(tz2)

    dt = datetime.datetime.strptime(dt,"%Y-%m-%d %H:%M:%S")
    dt = tz1.localize(dt)
    dt = dt.astimezone(tz2)
    dt = dt.strftime("%Y-%m-%d %H:%M:%S")

    return dt



# ##############################################
# SET DATES.                                   #
# ##############################################
d = datetime.datetime.now()

# TESTING ONLY
# d = pd.to_datetime('2021-11-30')
# TESTING ONLY

# TESTING ONLY
# d = d.replace(month=5)
# TESTING ONLY

first_of_month = d.combine(d.replace(day=1), time.min).strftime(Constants.DATE_TIME_FORMAT)

# TESTING ONLY
# first_of_month = pd.to_datetime('2021-10-11').strftime(Constants.DATE_TIME_FORMAT)
# TESTING ONLY

last_30_days = (d - datetime.timedelta(days=30)).strftime(Constants.DATE_TIME_FORMAT)

# TESTING ONLY
# last_30_days = pd.to_datetime('2021-05-01').strftime(Constants.DATE_TIME_FORMAT)
# TESTING ONLY

now = d.combine(d, time.max).strftime(Constants.DATE_TIME_FORMAT)

print(f"d:\t\t{d}\nnow:\t\t{now}\nfirst_of_month:\t{first_of_month}\nlast_30_days:\t{last_30_days}\n")


# ##############################################
# GET DATA.                                    #
# ##############################################

# ##############################################
# SOURCE DATABASE.                             #
# ##############################################
enrolment_query = f"""SELECT * FROM EnrolmentData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
cancellation_query = f"""SELECT * FROM CancellationData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
fp_coach_query = f"""SELECT * FROM FpCoachEnrolmentData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
membership_change_query = f"""SELECT * FROM MembershipChangeData WHERE submission_datetime >= '{first_of_month}' AND createDate <= '{now}'"""
staff_query = "SELECT * FROM Staff"
web_lead_query = f"""SELECT * FROM LeadData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
src_pre_ex_query = f"""SELECT * FROM PreExData WHERE submission_datetime >= '{first_of_month}' AND createDate <= '{now}'"""
gym_query = f"""SELECT * FROM Gym"""

try:
    SOURCE_DB_CREDENTIALS = decode_database_credentials(Constants.CREDENTIALS['SOURCE_DB_CREDENTIALS'])
    
    conn = pymysql.connect(host = SOURCE_DB_CREDENTIALS['HOST'], db=SOURCE_DB_CREDENTIALS['DATABASE'], user=SOURCE_DB_CREDENTIALS['USERNAME'], password=SOURCE_DB_CREDENTIALS['PASSWORD'])

    enrolment = pd.read_sql(enrolment_query, con=conn)
    cancellation = pd.read_sql(cancellation_query, con=conn)
    fp_coach = pd.read_sql(fp_coach_query, con=conn)
    membership_change = pd.read_sql(membership_change_query, con=conn)
    staff = pd.read_sql(staff_query, con=conn)
    web_lead = pd.read_sql(web_lead_query, con=conn)
    src_pre_ex = pd.read_sql(src_pre_ex_query, con=conn)
    gym_data = pd.read_sql(gym_query, con=conn)

except Exception as ex:
    print(f"Error collecting source data: {ex}")
finally:
    conn.close()

# ##############################################
# CLEAN DATA.                                  #
# ##############################################
# Clean Bots
bot_list = []
for index, row in web_lead.iterrows():
    if row['phone'] is not None:
        if row['phone'][:2] == '+1':
            bot_list.append(index)

web_lead = web_lead.drop(bot_list)
web_lead.reset_index(drop=True, inplace=True)

# Clean Duplicates
PHONE_NUMBERS = []
EMAILS = []
cleaned_web_lead_ids = []
for index, row in web_lead.iterrows():
    if row['email'] is not None and row['email'] not in EMAILS:
        EMAILS.append(row['email'])
        if index not in cleaned_web_lead_ids:
            cleaned_web_lead_ids.append(index)
    
    if row['phone'] is not None and row['phone'] not in PHONE_NUMBERS:
        PHONE_NUMBERS.append(row['phone'])
        if index not in cleaned_web_lead_ids:
            cleaned_web_lead_ids.append(index)

print(f"cleaned_web_lead_ids: {len(cleaned_web_lead_ids)} | web_leads: {len(web_lead)}")
web_leads = web_lead.iloc[cleaned_web_lead_ids]
web_leads.reset_index(drop=True, inplace=True)

# Remove contracts under $100
ENROLMENT_DROP_IDS = []
for index, row in enrolment[enrolment['ddOrPif'] == 'pif'].iterrows():
    SUM_CONTRACTS = 0
    if row['contractNamesToBeActivated'] is not None:
        for contract in row['contractNamesToBeActivated'].split(' | '):
            SUM_CONTRACTS += float(contract.split(' # ')[1].split('/')[0])
    
    if SUM_CONTRACTS <= 100:
        ENROLMENT_DROP_IDS.append(index)

enrolment = enrolment.drop(index = ENROLMENT_DROP_IDS)
enrolment.reset_index(drop=True, inplace=True)

enrolment['gymName'] = enrolment['gymName'].apply(lambda x: 'Bunker' if x == 'The Bunker' else x)
cancellation['gymName'] = cancellation['gymName'].apply(lambda x: 'Bunker' if x == 'The Bunker' else x)
fp_coach['gymName'] = fp_coach['gymName'].apply(lambda x: 'Bunker' if x == 'The Bunker' else x)
membership_change['staffMemberGymName'] = membership_change['staffMemberGymName'].apply(lambda x: 'Bunker' if x == 'The Bunker' else x)
web_lead['gymName'] = web_lead['gymName'].apply(lambda x: 'Bunker' if x == 'The Bunker' else x)
src_pre_ex['gymName'] = src_pre_ex['gymName'].apply(lambda x: 'Bunker' if x == 'The Bunker' else x)


# ##############################################
# REPORTS DATABASE.                            #
# ##############################################
old_enrolment_query = f"""SELECT * FROM EnrolmentSubmissionData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
old_pif_query = f"""SELECT * FROM PifSubmissionData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
pt_ongoing_query = f"""SELECT * FROM PtOngoingMember WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
pt_pif_ongoing_query = f"""SELECT * FROM PifPTSubmissionData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
old_membership_change_query = f"""SELECT * FROM MemberChangeSubmissionData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
old_pt_cancellation_query = f"""SELECT * FROM PtCancellationSubmissionData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
old_cancellation_query = f"""SELECT * FROM CancellationSubmissionData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""
old_play_upgrade_query = f"""SELECT * FROM PlayUpgradeSubmissionData WHERE createDate >= '{first_of_month}' AND createDate <= '{now}'"""

try:
    REPORTS_DB_CREDENTIALS = decode_database_credentials(Constants.CREDENTIALS['REPORTS_DB_CREDENTIALS'])
    
    conn = pymysql.connect(host = REPORTS_DB_CREDENTIALS['HOST'], db=REPORTS_DB_CREDENTIALS['DATABASE'], user=REPORTS_DB_CREDENTIALS['USERNAME'], password=REPORTS_DB_CREDENTIALS['PASSWORD'])

    old_enrolment = pd.read_sql(old_enrolment_query, con=conn)
    old_pif = pd.read_sql(old_pif_query, con=conn)
    pt_ongoing = pd.read_sql(pt_ongoing_query, con=conn)
    pt_pif_ongoing = pd.read_sql(pt_pif_ongoing_query, con=conn)
    old_membership_change = pd.read_sql(old_membership_change_query, con=conn)
    old_pt_cancellation = pd.read_sql(old_pt_cancellation_query, con=conn)
    old_cancellation = pd.read_sql(old_cancellation_query, con=conn)
    old_play_upgrade = pd.read_sql(old_play_upgrade_query, con=conn)

except Exception as ex:
    print(f"Error collecting reports data: {ex}")
finally:
    conn.close()


# ##############################################
# GYM SALES API.                               #
# ##############################################
def gs_get_request(URL, PARAMS = None):
    data = None
    try:
        GS_CREDENTIALS = decode_credentials(Constants.CREDENTIALS['GYMSALES_CREDENTIALS'])

        r = requests.get(url =  URL, params=PARAMS, auth=HTTPBasicAuth(GS_CREDENTIALS['USERNAME'], GS_CREDENTIALS['PASSWORD']) )
        event = r.json()
        j = json.dumps(event)
        data = json.loads(j)

    except Exception as e:
        print(e)
    finally:
        return data

# ##############################################
# LISTEN360 API.                               #
# ##############################################
# https://docs.python.org/3/library/xml.etree.elementtree.html
def get_listen_360_reviews(start_date):
    LISTEN360_CREDENTIALS = decode_credentials(Constants.CREDENTIALS['LISTEN360_CREDENTIALS'])
    LISTEN360_REVIEW_URL = f"organizations/{LISTEN360_CREDENTIALS['PASSWORD']}/reviews.xml"
    URL = Constants.LISTEN360_BASE_URL + LISTEN360_REVIEW_URL

    PARAMS = {
        'per_page' : 5000,
        'start_date' : start_date
    }

    try:
        response = requests.get(URL, params=PARAMS, auth=HTTPBasicAuth(LISTEN360_CREDENTIALS['USERNAME'],'X'))
    except HTTPError as http_err:
        print(f'HTTP error code: {http_err.response.status_code}\nHTTP error: {http_err}\nHTTP message: {http_err.response.reason}')
        return None
    else:
        try:
            tree = ElementTree.fromstring(response.content)
            
            data = []
            for child in tree:
                value = {}
                for survey in child:
                    value[survey.tag] = survey.text
                data.append(value)
            return data
            
        except Exception as ex:
            print(f"Error [get_listen_360_average_nps]: {ex}")
            return None


# ##############################################
# FORMSTACK API.                               #
# ##############################################
def get_play_feedback(start_date):
    
    FROM_DATE = convert_datetime_timezone(start_date)

    FS_CREDENTIALS = decode_credentials(Constants.CREDENTIALS['FORMSTACK_CREDENTIALS'])

    FS_TOKEN = FS_CREDENTIALS['USERNAME']
    FS_ENCRYPTION = FS_CREDENTIALS['PASSWORD']
    submissions = []

    HEADERS = {
        'X-FS-ENCRYPTION-PASSWORD' : FS_ENCRYPTION
    }

    print("Getting Play Feedback")

    FORM_IDS = [Constants.FS_PLAY_30_DAY_FEEDBACK_FP_FORM_ID, Constants.FS_PLAY_30_DAY_FEEDBACK_BK_FORM_ID]

    for i in range(len(FORM_IDS)):
        URL = Constants.FS_BASE_URL + f'form/{FORM_IDS[i]}/submission.json'
        page_count = 1
        page_total = 1

        while page_count <= page_total:
            print(f"{page_count}/{page_total}")
            res = requests.get(f"{URL}?oauth_token={FS_TOKEN}&page={page_count}&per_page=100&data=true&min_time={FROM_DATE}", headers=HEADERS)
            try:
                for sub in res.json()['submissions']:
                    submissions.append(sub)
                page_total = res.json()['pages']
                page_count += 1
            except:
                break

    submissions            

    field_ids = []
    data = []
    for sub in submissions:
        for field_id in sub['data']:
            if field_id not in field_ids:
                field_ids.append(field_id)

    for sub in submissions:
        value = {}
        for field_id in field_ids:
            try:
                value[sub['data'][field_id]['label']] = sub['data'][field_id]['value']
            except:
                pass
        data.append(value)

    return data



# ##############################################
# GENERATE SPREADSHEET.                        #
# ##############################################
try:
    reportName = "Membership Dashboard v5.xlsx"
    emailTitle = "Membership Dashboard v5"

    sheet_1 = 'MTD Sales'

    writer = ExcelWriter(reportName, engine='xlsxwriter')
    workbook  = writer.book
    worksheet = workbook.add_worksheet('MTD Sales')
    worksheet.set_column('A:Q',10)

    title_format = workbook.add_format({
        'bold':True,
        'font_size':12,
        'border':1,
        'align':'center',
        'valign':'vcenter',
        'fg_color':'#FCB120'
    })

    sub_title_format = workbook.add_format({
        'bold':True,
        'font_size':8,
        'border':2,
        'align':'center',
        'valign':'vcenter',
    })

    column_header_format = workbook.add_format({
        'bold':True,
        'font_size':8,
        'border':1,
        'top':2,
        'align':'center',
        'valign':'vcenter',
    })

    column_header_boarder_left_format = workbook.add_format({
        'bold':True,
        'font_size':8,
        'border':1,
        'left' : 2,
        'top':2,
        'align':'center',
        'valign':'vcenter',
    })

    column_header_boarder_right_format = workbook.add_format({
        'bold':True,
        'font_size':8,
        'border':1,
        'right':2,
        'top':2,
        'align':'center',
        'valign':'vcenter',
    })

    column_header_boarder_left_and_right_format = workbook.add_format({
        'bold':True,
        'font_size':8,
        'border':1,
        'left' : 2,
        'right':2,
        'top':2,
        'align':'center',
        'valign':'vcenter',
    })

    column_header_boarder_left_and_right_bottom_format = workbook.add_format({
        'bold':True,
        'font_size':8,
        'border':1,
        'left' : 2,
        'right':2,
        'bottom':2,
        'align':'center',
        'valign':'vcenter',
    })

    cell_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'align':'center',
        'valign':'vcenter',
    })

    cell_percent_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'align':'center',
        'valign':'vcenter',
        'num_format': '0.0%'
    })

    cell_bottom_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'bottom':2,
        'align':'center',
        'valign':'vcenter',
    })

    cell_bottom_percent_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'bottom':2,
        'align':'center',
        'valign':'vcenter',
        'num_format': '0.0%'
    })

    cell_border_left_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'left' : 2,
        'align':'center',
        'valign':'vcenter',
    })

    cell_border_left_percent_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'left' : 2,
        'align':'center',
        'valign':'vcenter',
        'num_format': '0.0%'
    })

    cell_border_left_bottom_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'left' : 2,
        'bottom':2,
        'align':'center',
        'valign':'vcenter',
    })

    cell_border_left_bottom_percent_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'left' : 2,
        'bottom':2,
        'align':'center',
        'valign':'vcenter',
        'num_format': '0.0%'
    })

    cell_border_right_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'right':2,
        'align':'center',
        'valign':'vcenter',
    })

    cell_border_right_percent_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'right':2,
        'align':'center',
        'valign':'vcenter',
        'num_format': '0.0%'
    })

    cell_border_right_bottom_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'right':2,
        'bottom':2,
        'align':'center',
        'valign':'vcenter',
    })

    cell_border_right_bottom_percent_format = workbook.add_format({
        'bold':False,
        'font_size':8,
        'border':1,
        'right':2,
        'bottom':2,
        'align':'center',
        'valign':'vcenter',
        'num_format': '0.0%'
    })


    COLUMN_VALS = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T']
    ROW = 5
    COL = 0
    CNT = 1
    LAST_GYM = False
    MTD_SALES_DATA = {}

    try:
        predictor_multiplier = predictor_num_day_month(d)/ num_working_days_worked(d)
    except Exception as ex:
        print(f"Error Calculating Predictor: {ex}")
        predictor_multiplier = 1
    print(f"Predictors:\n\tpredictor_num_day_month: {predictor_num_day_month()}\n\tnum_working_days_worked: {num_working_days_worked()}\n\tpredictor_multiplier: {predictor_multiplier}")

    PARAMS = {
        'date_from': first_of_month, # (Date from)
        'date_into': now, # (Date into)
        'report_type': 'multi_club_summary' #(single_club, multi_club_summary, multi_club_detail, multi_club_salesperson)
    }
    URL = Constants.GS_BASE_URL + Constants.GS_REPORT_SALESPERSON_URL
    gs_sales_persons_report_data = gs_get_request(URL, PARAMS)
    listen_360_reviews = get_listen_360_reviews(first_of_month)
    start_date = convert_datetime_timezone(first_of_month)
    cxl_end_date = convert_datetime_timezone('2021-12-14 13:00:00')
    play_feedback = get_play_feedback(start_date)


    # #############################################
    #     NEW CANCELLATIONS TEMP SOLUTION GET DATA
    NEW_CXL_PART_1_FORM_ID = "4558129"
    NEW_CXL_PART_1_A_FORM_ID = "4585616"
    NEW_CXL_PART_1_B_FORM_ID = "4585608"
    NEW_CXL_PART_1_LIST = [NEW_CXL_PART_1_FORM_ID, NEW_CXL_PART_1_A_FORM_ID, NEW_CXL_PART_1_B_FORM_ID]
    NEW_CXL_PART_2_FORM_ID = "4558130"
    NEW_CXL_IN_CLUB_FORM_ID = "4592110"
    NEW_CXL_PART_2_LIST = [NEW_CXL_PART_2_FORM_ID, NEW_CXL_IN_CLUB_FORM_ID]

    
    FS_CREDENTIALS = decode_credentials(Constants.CREDENTIALS['FORMSTACK_CREDENTIALS'])
    FS_TOKEN = FS_CREDENTIALS['USERNAME']
    FS_ENCRYPTION = FS_CREDENTIALS['PASSWORD']
    submissions = []

    HEADERS = { 'X-FS-ENCRYPTION-PASSWORD' : FS_ENCRYPTION }
    print("Getting New Part 1 Cancellations")
    for CXL in NEW_CXL_PART_1_LIST:
        URL = Constants.FS_BASE_URL + f'form/{CXL}/submission.json'
        page_count = 1
        page_total = 1
        while page_count <= page_total:
            print(f"{page_count}/{page_total}")
            res = requests.get(f"{URL}?oauth_token={FS_TOKEN}&page={page_count}&per_page=100&data=true&min_time={start_date}&max_time={cxl_end_date}", headers=HEADERS)
            try:
                for sub in res.json()['submissions']:
                    submissions.append(sub)
                page_total = res.json()['pages']
                page_count += 1
            except:
                break
        print(f"CXL: {CXL} == {len(submissions)}")
        
    NEW_PART_1_CANCELLATION_SUBMISSIONS = submissions

    submissions = []
    print("Getting New Part 2 Cancellations")
    for CXL in NEW_CXL_PART_2_LIST:
        URL = Constants.FS_BASE_URL + f'form/{CXL}/submission.json'
        page_count = 1
        page_total = 1
        while page_count <= page_total:
            print(f"{page_count}/{page_total}")
            res = requests.get(f"{URL}?oauth_token={FS_TOKEN}&page={page_count}&per_page=100&data=true&min_time={start_date}", headers=HEADERS)
            try:
                for sub in res.json()['submissions']:
                    submissions.append(sub)
                page_total = res.json()['pages']
                page_count += 1
            except:
                break
            
    NEW_PART_2_CANCELLATION_SUBMISSIONS = submissions
    #     NEW CANCELLATIONS TEMP SOLUTION GET DATA
    # #############################################

    for gym in gym_data['name']:
        print(gym)
        _ROW = ROW + 9
        CNT += 1
        if CNT == len(gym):
            LAST_GYM = True
        
        COL = 0
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : gym, 'format' : cell_border_left_bottom_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : gym, 'format' : cell_border_left_bottom_format}
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : gym, 'format' : cell_border_left_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : gym, 'format' : cell_border_left_format}
        COL += 1
        
        PARAMS = {
            'date_from': first_of_month, # (Date from)
            'date_into': now, # (Date into)
            'company_id' : gs_get_company_id(gym),
            'group_by' : 'source_id'
        }
        URL = Constants.GS_BASE_URL + Constants.GS_REPORT_LEAD_STATUS_URL
        gs_data_mtd = gs_get_request(URL, PARAMS)
        
        PARAMS = {
            'date_from': last_30_days, # (Date from)
            'date_into': now, # (Date into)
            'company_id' : gs_get_company_id(gym),
            'group_by' : 'source_id'
    #         'group_by' : 'date'
        }
        URL = Constants.GS_BASE_URL + Constants.GS_REPORT_LEAD_STATUS_URL
        gs_data_30_day = gs_get_request(URL, PARAMS)
        
        ACTIVE_LEADS = 0
        GS_LEADS = 0
        GS_INTERNET = 0
        POS_REFERALS = 0
        OUTREACH_LEADS = 0
        GS_WALK_INS = 0
        WALK_IN = 0
        APPTS = 0
        SALES = 0
        CLOSE_PERCENTAGE = 0
        GS_CLOSE_PERCENTAGE = 0
        REFERAL = 0
        FRIEND = 0
        FRIEND_PERCENTAGE = 0
        LEAD_SALE_PERCENTAGE = 0
        
        PT_PACK = 0
        PT_PACK_PERCENTAGE = 0
        POS_PT = 0
        POS_PT_PERCENTAGE = 0
        PT_SOLD_BY_TRAINER = 0
        TOTAL_PT = 0
        PLAY = 0
        PLAY_PERCENTAGE = 0
        PLAY_DOWNGRADE = 0
        PLAY_UPGRADE = 0
        NO_COMMIT = 0
        NO_COMMIT_PERCENTAGE = 0
        
        CANCELLATION_REQUESTS = 0
        CANCELLATIONS = 0
        PT_CANCELLATIONS_LESS_THAN_2 = 0
        SAVES = 0
        SAVES_PERCENTAGE = 0
        CANCELLATIONS_7_DAYS = 0
        CANCELLATIONS_7_DAYS_PERCENTAGE = 0
        DETRACTORS = 0
        NPS_30_DAY = 0
        
        FEEDBACK_30_DAY = []
        for f in play_feedback:
            if f['GymLocation'] == gym:
                FEEDBACK_30_DAY.append(float(f['On a scale of 1 – 10 how likely are you to recommend a friend to try classes at Fitness Playground?']))

        if len(FEEDBACK_30_DAY) > 0:
            NPS_30_DAY = round(mean(FEEDBACK_30_DAY),1)
        
        for r in listen_360_reviews:
            if gym in r['organization-name'] and r['net-promoter-label'] == 'detractor':
                DETRACTORS += 1

        for entity in gs_data_30_day['entities']:
            for c in entity['collection']:
                if c['status'] not in ['Sale', 'Not Interested']:
                    ACTIVE_LEADS += 1
                    
        for entity in gs_data_mtd['entities']:
            for c in entity['collection']:
                
                GS_LEADS += 1
                
                if c['contact'] == 'Internet':
                    GS_INTERNET += 1

                if c['contact'] == 'Referral':
                    REFERAL += 1
                
                if c['contact'] == 'Outreach':
                    OUTREACH_LEADS += 1
                
                if c['contact'] == 'Walk In':
                    GS_WALK_INS += 1
                    
        for entity in gs_sales_persons_report_data['entities']:
            if gym in entity['company_name']:
                GS_WALK_INS = entity['walk_in_total']
        
        leads = web_leads[web_leads['gymName'].str.contains(gym)]
        WEB_LEADS = len(leads)
        
        pre_ex = src_pre_ex[src_pre_ex['gymName'].str.contains(gym)]
        pre_ex = pre_ex.drop_duplicates(subset=['email'],keep='first')
        WALK_IN = len(pre_ex[pre_ex['enquireType'] == 'Walked in'])
        APPTS = len(pre_ex[pre_ex['enquireType'] == 'Had an appointment'])
        FRIEND = len(pre_ex[pre_ex['howDidYouHearAboutUs'].isin(['Friend','Former Member'])])
        SALES = len((enrolment[enrolment['gymName'].str.contains(gym) & (enrolment['status'] != 'SAVED')]))
        SALES += len(old_enrolment[old_enrolment['gymName'].str.contains(gym)])
        SALES += len(old_pif[(old_pif['gymName'].str.contains(gym)) & (pd.to_numeric(old_pif['todaysOneOffTotal']) > 100)])
        PLAY = len(enrolment[(enrolment['gymName'].str.contains(gym)) & (enrolment['gymOrPlay'] == 'play')])
        PLAY += len(old_enrolment[(old_enrolment['gymName'].str.contains(gym)) & (old_enrolment['membershipType'] == 'PLAY')])
        PLAY += len(old_pif[(old_pif['gymName'].str.contains(gym)) & (pd.to_numeric(old_pif['todaysOneOffTotal']) > 100) & (old_pif['membershipType'] == 'PLAY')])
        PLAY_DOWNGRADE = len(membership_change[(membership_change['staffMemberGymName'].str.contains(gym)) & (membership_change['changeMembership'] == 'Downgrade My Membership') & (membership_change['fromMembership'].str.contains('Play')) & (membership_change['toMembership'].str.contains('Gym'))])
        PLAY_DOWNGRADE += len(old_membership_change[(old_membership_change['gymName'].str.contains(gym)) & (old_membership_change['whatDoYouNeedToRequest'] == 'Downgrade Membership')])
        PLAY_UPGRADE = len(membership_change[(membership_change['staffMemberGymName'].str.contains(gym)) & (membership_change['changeMembership'] == 'Upgrade My Membership') & (membership_change['fromMembership'].str.contains('Gym')) & (membership_change['toMembership'].str.contains('Play'))])
        PLAY_UPGRADE += len(old_play_upgrade[old_play_upgrade['gymName'].str.contains(gym)])
        NO_COMMIT = len(enrolment[(enrolment['gymName'].str.contains(gym)) & (enrolment['noCommitment'].str.contains('noCommitment'))])
        NO_COMMIT += len(old_enrolment[(old_enrolment['gymName'].str.contains(gym)) & (old_enrolment['noMinimumTerm'].str.contains('Yes'))])
        
        if APPTS + WALK_IN != 0:
            CLOSE_PERCENTAGE = SALES/(APPTS+WALK_IN)
        
        if len(pre_ex) != 0:
            FRIEND_PERCENTAGE = FRIEND/len(pre_ex)
        
        try:
            for e in gs_sales_persons_report_data['entities']:
                if e['company_id'] == gs_get_company_id(gym):
                    GS_WALK_INS = e['walk_in_total']
                    GS_CLOSE_PERCENTAGE = e['total_close_ratio']
    #                 GS_LEADS = e['new_leads']
        except:
            pass
        
        if GS_LEADS != 0:
            LEAD_SALE_PERCENTAGE = SALES/GS_LEADS
        
        PT_PACK = len(enrolment[(enrolment['gymName'].str.contains(gym)) & (enrolment['trainingStarterPack'].isin(['ptPack','Starter Pack']))])
        PT_PACK += len(old_enrolment[(old_enrolment['gymName'].str.contains(gym)) & (old_enrolment['trainingStarterPack'] == 'PT Pack')])
        POS_PT += len(old_enrolment[(old_enrolment['gymName'].str.contains(gym)) & (old_enrolment['trainingStarterPack'] == 'Ongoing Personal Training')])
        POS_PT += len(enrolment[(enrolment['gymName'].str.contains(gym)) & (enrolment['trainingStarterPack'].isin(['ongoingPersonalTraining','Coaching','Face-to-Face','External Personal Training'])) & (enrolment['trainingPackageSoldBy'] != 'Coach')])
        POS_PT += len(pt_ongoing[(pt_ongoing['gymName'].str.contains(gym)) & (~pt_ongoing['staffName'].str.contains('Organised'))])
        PT_SOLD_BY_TRAINER = len(enrolment[(enrolment['gymName'].str.contains(gym)) & (old_enrolment['trainingStarterPack'] == 'Ongoing Personal Training') & (enrolment['trainingPackageSoldBy'] == 'Coach')])
        PT_SOLD_BY_TRAINER += len(pt_ongoing[(pt_ongoing['gymName'].str.contains(gym)) & (pt_ongoing['staffName'].str.contains('Organised'))])
        TOTAL_PT = len(old_enrolment[(old_enrolment['gymName'].str.contains(gym)) & (old_enrolment['trainingStarterPack'] == 'Ongoing Personal Training')])
        TOTAL_PT += len(pt_ongoing[(pt_ongoing['gymName'].str.contains(gym))])
        TOTAL_PT += len(pt_pif_ongoing[(pt_pif_ongoing['gymName'].str.contains(gym))])
        TOTAL_PT += len(enrolment[(enrolment['gymName'].str.contains(gym)) & (enrolment['trainingStarterPack'].isin(['ongoingPersonalTraining','Coaching','Face-to-Face','External Personal Training']))])
        
        PT_CANCELLATIONS_LESS_THAN_2 = len(cancellation[(cancellation['gymName'].str.contains(gym)) & (cancellation['status'] != 'CANCELLATION_SAVED') & ((cancellation['cancellationOptions'] == 'Personal Training') | (cancellation['hasCoach'])) & (pd.to_numeric(cancellation['ptNumberSessionsCompleted']) < 2)])
        PT_CANCELLATIONS_LESS_THAN_2 += len(old_pt_cancellation[(old_pt_cancellation['gymName'].str.contains(gym)) & (pd.to_numeric(old_pt_cancellation['numSessionsCompleted']) < 2)])
        CANCELLATION_REQUESTS = len(cancellation[(cancellation['gymName'].str.contains(gym)) & (cancellation['cancellationOptions'] == 'Membership')])
        CANCELLATIONS = len(cancellation[(cancellation['gymName'].str.contains(gym)) & (cancellation['status'] != 'CANCELLATION_SAVED') & (cancellation['cancellationOptions'] == 'Membership')])
        CANCELLATIONS += len(old_cancellation[old_cancellation['gymName'].str.contains(gym) & ((old_cancellation['cxlReason'] == 'Medical: Unable to Exercise for 6+ months') | (old_cancellation['relocationSaveOption'].str.contains('Proceed')) | (old_cancellation['financeSaveOption'].str.contains('Proceed')) | (old_cancellation['motivationSaveOption'].str.contains('Proceed')) | (old_cancellation['poorExperienceSaveOption'].str.contains('Proceed')))])
        SAVES = len(cancellation[(cancellation['gymName'].str.contains(gym)) & (cancellation['status'] == 'CANCELLATION_SAVED') & (cancellation['cancellationOptions'] == 'Membership')])
        SAVES += len(old_cancellation[(old_cancellation['gymName'].str.contains(gym))]) - len(old_cancellation[old_cancellation['gymName'].str.contains(gym) & ((old_cancellation['cxlReason'] == 'Medical: Unable to Exercise for 6+ months') | (old_cancellation['relocationSaveOption'].str.contains('Proceed')) | (old_cancellation['financeSaveOption'].str.contains('Proceed')) | (old_cancellation['motivationSaveOption'].str.contains('Proceed')) | (old_cancellation['poorExperienceSaveOption'].str.contains('Proceed')))])
        CANCELLATIONS_7_DAYS = len(cancellation[(cancellation['gymName'].str.contains(gym)) & (cancellation['status'] != 'CANCELLATION_SAVED') & (cancellation['cancellationOptions'] == 'Membership') & (cancellation['eligibilityCheck'].str.contains('Cooling'))])
        CANCELLATIONS_7_DAYS += len(old_cancellation[old_cancellation['gymName'].str.contains(gym) & ((old_cancellation['cxlReason'] == 'Medical: Unable to Exercise for 6+ months') | (old_cancellation['relocationSaveOption'].str.contains('Proceed')) | (old_cancellation['financeSaveOption'].str.contains('Proceed')) | (old_cancellation['motivationSaveOption'].str.contains('Proceed')) | (old_cancellation['poorExperienceSaveOption'].str.contains('Proceed'))) & (old_cancellation['eligibilityCheck'].str.contains('Cooling'))])
        
    # #############################################
    #     NEW CANCELLATIONS TEMP SOLUTION
        for sub in NEW_PART_1_CANCELLATION_SUBMISSIONS:
            FIELD_KEYS = sub['data'].keys()
            DATA = sub['data']
            for key in FIELD_KEYS:
                if DATA.get(key)['label'] == 'Home Club' and DATA.get(key)['value'] == gym:
                    CANCELLATION_REQUESTS += 1
        
        for sub in NEW_PART_2_CANCELLATION_SUBMISSIONS:
            FIELD_KEYS = sub['data'].keys()
            DATA = sub['data']
            IS_HOME_CLUB = False
            for key in FIELD_KEYS:
                if (DATA.get(key)['label'] == 'Home Club' or DATA.get(key)['label'] == 'Where did you join?') and DATA.get(key)['value'] == gym:
                    IS_HOME_CLUB = True
                    
                if DATA.get(key)['label'] == 'Cancellation Request Outcome' and IS_HOME_CLUB:
                    if DATA.get(key)['value'] == 'Proceed with Cancellation':
                        CANCELLATIONS += 1

                    if DATA.get(key)['value'] == 'Cancellation Saved':
                        SAVES += 1

                if DATA.get(key)['label'] == 'Current Contract Status' and IS_HOME_CLUB:
                    if DATA.get(key)['value'] == '7 Day Comfort':
                        CANCELLATIONS_7_DAYS += 1
    #     NEW CANCELLATIONS TEMP SOLUTION
    # #############################################
        
        if SALES != 0:
            PT_PACK_PERCENTAGE = PT_PACK/SALES
            POS_PT_PERCENTAGE = POS_PT/SALES
            PLAY_PERCENTAGE = PLAY/SALES
            NO_COMMIT_PERCENTAGE = NO_COMMIT/SALES
            SALES = SALES - CANCELLATIONS_7_DAYS
        
        if CANCELLATIONS != 0:
            SAVES_PERCENTAGE = SAVES/CANCELLATIONS
            CANCELLATIONS_7_DAYS_PERCENTAGE = CANCELLATIONS_7_DAYS/CANCELLATIONS
        
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : ACTIVE_LEADS,'format' : cell_border_left_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PT_PACK_PERCENTAGE,'format' : cell_border_left_bottom_percent_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : ACTIVE_LEADS,'format' : cell_border_left_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PT_PACK_PERCENTAGE,'format' : cell_border_left_percent_format}
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : WEB_LEADS, 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : POS_PT, 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : WEB_LEADS, 'format' : cell_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : POS_PT, 'format' : cell_format }
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : GS_INTERNET, 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : POS_PT_PERCENTAGE, 'format' : cell_bottom_percent_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : GS_INTERNET, 'format' : cell_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : POS_PT_PERCENTAGE, 'format' : cell_percent_format }
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : REFERAL, 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PT_SOLD_BY_TRAINER, 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : REFERAL, 'format' : cell_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PT_SOLD_BY_TRAINER, 'format' : cell_format }
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : OUTREACH_LEADS, 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : TOTAL_PT, 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : OUTREACH_LEADS, 'format' : cell_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : TOTAL_PT, 'format' : cell_format }
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : GS_LEADS, 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : int(TOTAL_PT * predictor_multiplier), 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : GS_LEADS, 'format' : cell_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : int(TOTAL_PT * predictor_multiplier), 'format' : cell_format }
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : int(GS_INTERNET * predictor_multiplier), 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PLAY_PERCENTAGE, 'format' : cell_border_left_bottom_percent_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : int(GS_INTERNET * predictor_multiplier), 'format' : cell_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PLAY_PERCENTAGE, 'format' : cell_border_left_percent_format }
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : WALK_IN, 'format' : cell_border_left_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : int(PLAY * predictor_multiplier), 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : WALK_IN, 'format' : cell_border_left_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : int(PLAY * predictor_multiplier), 'format' : cell_format}
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : GS_WALK_INS, 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PLAY_DOWNGRADE, 'format' : cell_border_left_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : GS_WALK_INS, 'format' : cell_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PLAY_DOWNGRADE, 'format' : cell_border_left_format}
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : APPTS, 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PLAY_UPGRADE, 'format' : cell_border_left_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : APPTS, 'format' : cell_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PLAY_UPGRADE, 'format' : cell_border_left_format}
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : SALES, 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PT_CANCELLATIONS_LESS_THAN_2, 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : SALES, 'format' : cell_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : PT_CANCELLATIONS_LESS_THAN_2, 'format' : cell_format}
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : CLOSE_PERCENTAGE, 'format' : cell_bottom_percent_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : NO_COMMIT_PERCENTAGE, 'format' : cell_bottom_percent_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : CLOSE_PERCENTAGE, 'format' : cell_percent_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : NO_COMMIT_PERCENTAGE, 'format' : cell_percent_format }
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : GS_CLOSE_PERCENTAGE, 'format' : cell_bottom_percent_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATION_REQUESTS, 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : GS_CLOSE_PERCENTAGE, 'format' : cell_percent_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATION_REQUESTS, 'format' : cell_format}
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : int(SALES * predictor_multiplier), 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATIONS, 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : int(SALES * predictor_multiplier), 'format' : cell_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATIONS, 'format' : cell_format}
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : FRIEND_PERCENTAGE, 'format' : cell_border_left_bottom_percent_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : SAVES_PERCENTAGE, 'format' : cell_bottom_percent_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : FRIEND_PERCENTAGE, 'format' : cell_border_left_percent_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : SAVES_PERCENTAGE, 'format' : cell_percent_format}
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : LEAD_SALE_PERCENTAGE, 'format' : cell_bottom_percent_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : DETRACTORS, 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : LEAD_SALE_PERCENTAGE, 'format' : cell_percent_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : DETRACTORS, 'format' : cell_format}
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : int((SALES - CANCELLATIONS) * predictor_multiplier), 'format' : cell_border_right_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : NPS_30_DAY, 'format' : cell_bottom_format }
    #         MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATIONS_7_DAYS_PERCENTAGE, 'format' : cell_border_right_bottom_percent_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : int((SALES - CANCELLATIONS) * predictor_multiplier), 'format' : cell_border_right_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : NPS_30_DAY, 'format' : cell_format}
    #         MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATIONS_7_DAYS_PERCENTAGE, 'format' : cell_border_right_percent_format }
        COL += 1
        if LAST_GYM:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATIONS_7_DAYS, 'format' : cell_bottom_format }
        else:
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATIONS_7_DAYS, 'format' : cell_format }   
        COL += 1
        if LAST_GYM:
    #         MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : int((SALES - CANCELLATIONS) * predictor_multiplier), 'format' : cell_border_right_bottom_format }
    #         MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : NPS_30_DAY, 'format' : cell_bottom_format }
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATIONS_7_DAYS_PERCENTAGE, 'format' : cell_border_right_bottom_percent_format }
        else:
    #         MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{ROW}"] = {'data' : int((SALES - CANCELLATIONS) * predictor_multiplier), 'format' : cell_border_right_format }
    #         MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : NPS_30_DAY, 'format' : cell_format}
            MTD_SALES_DATA[f"{COLUMN_VALS[COL]}{_ROW}"] = {'data' : CANCELLATIONS_7_DAYS_PERCENTAGE, 'format' : cell_border_right_percent_format }
        
        COL += 1
        ROW += 1

    # Title
    worksheet.merge_range('A1:S1','MTD SALES DASHBOARD',title_format)
    # Sub title - SALES VOLUME
    worksheet.merge_range('A2:C2', d.strftime('%A %d %b %Y at %I:%M%p'), sub_title_format)
    worksheet.write('A3','SALES VOLUME',sub_title_format)
    worksheet.merge_range('B3:G3','LEADS',sub_title_format)
    worksheet.merge_range('H3:N3','PRESENTATIONS',sub_title_format)
    worksheet.merge_range('O3:R3','HEALTH',sub_title_format)

    worksheet.write('A4','Location',column_header_boarder_left_format)
    worksheet.write('B4','Active Leads',column_header_boarder_left_format)
    worksheet.write('C4','Web Leads',column_header_format)
    worksheet.write('D4','GS Internet',column_header_format)
    worksheet.write('E4','POS Refs',column_header_format)
    worksheet.write('F4','Outreach',column_header_format)
    worksheet.write('G4','Total',column_header_boarder_right_format)
    worksheet.write('H4','EOM Leads',column_header_format)
    worksheet.write('I4','Walk In',column_header_boarder_left_format)
    worksheet.write('J4','GS Walk In',column_header_format)
    worksheet.write('K4','Pres From Appt',column_header_format)
    worksheet.write('L4','Sales',column_header_format)
    worksheet.write('M4','% Close',column_header_format)
    worksheet.write('N4','GS Close %',column_header_format)
    worksheet.write('O4','EOM Sales',column_header_format)
    worksheet.write('P4','% Friend',column_header_boarder_left_format)
    worksheet.write('Q4','Lead:Sale %',column_header_format)
    worksheet.write('R4','EOM Growth',column_header_boarder_right_format)
        
    # Sub title - SALES QUALITY
    worksheet.write('A12','SALES QUALITY',sub_title_format)
    worksheet.merge_range('B12:G12','PERSONAL TRAINING',sub_title_format)
    worksheet.merge_range('H12:I12','PLAY',sub_title_format)
    worksheet.merge_range('J12:T12','RETENTION & ONBOARDING',sub_title_format)

    worksheet.write('A13','Location',column_header_boarder_left_format)
    worksheet.write('B13','% PT Pack',column_header_boarder_left_format)
    worksheet.write('C13','PT POS',column_header_format)
    worksheet.write('D13','% PT POS',column_header_format)
    worksheet.write('E13','PT SBT',column_header_format)
    worksheet.write('F13','Total Ongoing',column_header_format)
    worksheet.write('G13','EOM PT',column_header_format)
    worksheet.write('H13','Play %',column_header_boarder_left_format)
    worksheet.write('I13','EOM Play',column_header_format)
    worksheet.write('J13','Play DG',column_header_boarder_left_format)
    worksheet.write('K13','Play UG',column_header_boarder_left_format)
    worksheet.write('L13','PT CXL <2',column_header_format)
    worksheet.write('M13','% No Commit',column_header_format)
    worksheet.write('N13','Cxl Requests',column_header_format)
    worksheet.write('O13','Actual Cxl',column_header_format)
    worksheet.write('P13','% Saves',column_header_format)
    worksheet.write('Q13','Detractors',column_header_format)
    worksheet.write('R13','30 Day NPS',column_header_format)
    worksheet.write('S13','7 Day Cxl',column_header_boarder_right_format)
    worksheet.write('T13','7 Day Cxl %',column_header_boarder_right_format)

    # Report Data
    for cell in MTD_SALES_DATA:
    #     print(f"{cell} | {MTD_SALES_DATA[cell]}")
        worksheet.write(cell, MTD_SALES_DATA[cell]['data'], MTD_SALES_DATA[cell]['format'])

    # Definitions
    DATA_POINTS = ['Active Leads', 'Web Leads', 'GS Internet', 'POS Refs', 'Outreach', 'Total', 'Walk In', 'GS Walk In', 'Pres From Appt', 'Sales', '% Close', 'GS Close %', '% Friend', 'Lead:Sale %','% PT Pack', 'PT POS', '% PT POS', 'PT SBT', 'Total Ongoing', 'Play %', 'Play DG', 'Play UG', 'PT CXL <2', '% No Commit', 'Cxl Requests', 'Actual Cxl', '% Saves', 'Detractors', '30 Day NPS', '7 Day Cxl', '7 Day Cxl %']
    DEFINITIONS = ['Gym Sales Lead Status Report for last 30 days where the status is not Sale or Not Interested' ,'Internet Leads month-to-date','Gym Sales Lead Status Report for Leads month-to-date where the Contact Method is Internet','Gym Sales Lead Status Report for Leads month-to-date where the Contact Method is Referral','Gym Sales Lead Status Report for Leads month-to-date where the Contact Method is Outreach','Gym Sales Lead Status Report for Leads month-to-date where sum of all leads','Pre Ex form with an enquiry type of Walked In','Gym Sales Lead Status Report for Leads month-to-date where the Contact Method is Walk-In','Pre Ex form with an enquiry type of Had an appointment','All Enrolments excluding PIF with a sales value of less than $100 and Part 1 Enrolments minus 7 Day Cancellations','Sales divided by the sum of Pres From Appt and Walk In','Gym Sales Salesperson Report for month-to-date Total Close Ratio','Pre Ex submission where How Did You Hear About Us is Friend or Former Member divided by the sum of Pre Ex submissions','Sales divided by Total','All Enrolments with a Training Starter Pack of PT Pack divided by Sales','The sum of All Enrolments with Ongoing PT and PT Ongoing joiners Sold by Sales','PT POS divided by Sales','The sum of All Enrolments with Ongoing PT Sold by Trainer and PT Ongoing joiners Sold by Trainer','The sum of All Enrolments with Ongoing PT and PT Ongoing joiners','All Enrolments with a Play Contract divided by Sales','Membership Change submissions downgrading from a Play Contract','Membership Change submissions upgrading to a Play Contract','PT Cancellation with number of sessions completed less than 2','All Enrolments with a No Commitment Contract divided by Sales','The sum of all Membership Cancellation and Part 1 Cancellation submissions','The sum of all Membership Cancellation that were not saved and Part 2 Cancellation submissions','The sum of all Membership Cancellations that were saved divided by Actual Cxl','The sum of month-to-date Listen 360 Reviews with a Net Promoter Label of Detractor','The average NPS score of month-to-date Play Feedback submissions','The sum of Membership Cancellations that are 7 Day Cancellations','7 Day Cxl divided by Actual Cxl']
    D = {'Data Point': DATA_POINTS, 'Definition': DEFINITIONS}

    # worksheet = workbook.add_worksheet('Definitions')
    definitions = pd.DataFrame(D)
    definitions.to_excel(writer,sheet_name='Definitions',index=False)
    worksheet = writer.sheets['Definitions']
    worksheet.set_column('A:A',15)
    worksheet.set_column('B:B',100)

    writer.save()

except Exception as ex:
	message = f'Error building spreadsheet: {ex}'
	print(message)
	send_error_notification(message)



fromaddr = "clint@thefitnessplayground.com.au"
# toaddr = "clint@thefitnessplayground.com.au"
toaddr = ["james@fitnessplayground.com.au","justin@fitnessplayground.com.au","quintin@fitnessplayground.com.au","kareim@fitnessplayground.com.au", "daniel@fitnessplayground.com.au","tammy@fitnessplayground.com.au","suh@fitnessplayground.com.au","victoria@fitnessplayground.com.au","chad@fitnessplayground.com.au","ibby@thebunkergym.com.au","tristen@fitnessplayground.com.au","anthony@fitnessplayground.com.au","alessia@fitnessplayground.com.au","alice@fitnessplayground.com.au"]

msg = MIMEMultipart()

msg['From'] = fromaddr
msg['To'] = COMMASPACE.join(toaddr)
msg['Subject'] = emailTitle
# msg['CC'] = COMMASPACE.join(cc)
msg['Date'] = formatdate(localtime=True)

body = """This is a new version of the Membership Sales Report.

Report run time: {}

Please review and let me know if you pick up any errors.

Thanks,
Clint
""".format(formatdate(localtime=True))

msg.attach(MIMEText(body, 'plain'))

part = MIMEBase('application', 'octet-stream')
part.set_payload(open(reportName, "rb").read())
encoders.encode_base64(part)
part.add_header('Content-Disposition', "attachment; filename= %s" % reportName)

msg.attach(part)

my_email= "clint@thefitnessplayground.com.au"
my_email_pass = "mango2334"
server_ssl = smtplib.SMTP_SSL('smtp.gmail.com', 465)
server_ssl.login(my_email, my_email_pass)
text = msg.as_string()
server_ssl.sendmail(fromaddr, toaddr, text)
server_ssl.quit()
print('Report sent :-)')

