# from fp_enrolment.constants import *
from constants import *

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

def get_gym_name(x):
    x = str(x)
    switcher={
        '3' : 'Marrickville',
        '2' :'Newtown',
        '1' : 'Surry Hills',
        '5' : 'The Bunker',
        '6' : 'Virtual'
    }
    return switcher.get(x, x)

def get_location_id(x):
    x = str(x)
    switcher={
        'Marrickville' : 3,
        'Newtown' : 2,
        'Surry Hills' : 1,
        'The Bunker' : 5,
        'Bunker' : 5,
        'Virtual': 6
    }
    return switcher.get(x, x)

def get_gym_sales_company_id_from_mbo_location_id(x):
    x = str(x)
    switcher={
        '3' : Constants.GS_MK_COMPANY_ID,
        '2' : Constants.GS_NT_COMPANY_ID,
        '1' : Constants.GS_SH_COMPANY_ID,
        '5' : Constants.GS_BK_COMPANY_ID,
        '6' : Constants.GS_FP_VIRTUAL_COMPANY_ID
    }
    return switcher.get(x, x)

def get_mbo_staff_from_name(name):
    mbo_staff = None
    for s in Constants.SOURCE_STAFF_LIST:
        if s['name'] == name:
            mbo_staff = s
    return mbo_staff

def get_from_email(x):
    x = str(x)
    switcher={
        'Surry Hills' : Constants.EMAIL_ADDRESS_SH,
        '1' : Constants.EMAIL_ADDRESS_SH,
        'Newtown' : Constants.EMAIL_ADDRESS_NT,
        '2' : Constants.EMAIL_ADDRESS_NT,
        'Marrickville' : Constants.EMAIL_ADDRESS_MK,
        '3' : Constants.EMAIL_ADDRESS_MK,
        'The Bunker' : Constants.EMAIL_ADDRESS_BK,
        'Bunker' : Constants.EMAIL_ADDRESS_BK,
        '5' : Constants.EMAIL_ADDRESS_BK
    }
    return switcher.get(x, 'play@thefitnessplayground.com.au')

def get_payment_frequency_str(mbo_contract):
    c = mbo_contract
    print(f"{c['name']} | {c['reoccuringPaymentAmountTotal']} | {c['autoPayScheduleTimeUnit']} | {c['numberOfAutoPays']}")
    if int(c['numberOfAutoPays']) == 12:
        return '/month'
    elif int(c['numberOfAutoPays']) == 26:
        return '/fortnight'
    else:
        return ''


