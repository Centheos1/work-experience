import os

class Constants:
    IS_TEST = False
    TEST_ACCESS_KEY_NUMBER = "100060597"
    TEST_EMAIL = 'clint@thefitnessplayground.com.au'
    ERROR_NOTIFICATION_EMAIL = 'clint@thefitnessplayground.com.au'
    DATETIME_FORMAT = "%Y-%m-%d %H:%M:%S"
    DATE_FORMAT = "%Y-%m-%d"
    
    FS_PHONE_ENROLMENT_INTERNAL_FORM_ID = "3929752"
    FS_PHONE_ENROLMENT_EXTERNAL_FP_FORM_ID = "3929753"
    FS_PHONE_ENROLMENT_EXTERNAL_BK_FORM_ID = "3939648"
    FS_IN_CLUB_ENROLMENT_FORM_ID = "3931477"
    FS_FP_COACH_ENROLMENT_FORM_ID = "3844672"
    FS_PIF_ENROLMENT_FORM_ID = "3980725"
    FS_PRE_SALES_ENROLMENT_FORM_ID = "4395680"
    
    MBO_BASE_URL = "https://api.mindbodyonline.com/public/v6/"
    MBO_ADD_CLIENT_URL = "client/addclient"
    MBO_UPDATE_CLIENT_URL = "client/updateclient"
    MBO_ADD_CLIENT_DIRECT_DEBIT_INFO_URL = "client/addclientdirectdebitinfo"
    MBO_PURCHASE_CONTRACT_URL = "sale/purchasecontract"
    MBO_CHECKOUT_SHOPPING_CART_URL = "sale/checkoutshoppingcart"
    MBO_GET_CLIENTS_DIRECT_DEBIT_URL = "client/clientdirectdebitinfo"
    MBO_GET_CLIENTS_URL = "client/clients"
    MBO_SEND_PASSWORD_RESET_URL = "client/sendpasswordresetemail"
    MBO_UPLOAD_CLIENT_IMAGE_URL = "client/uploadclientphoto"
    MBO_ADD_CONTACT_LOG_URL = "client/addcontactlog"
    
    CONTENT_TYPE = "application/json"
    MBO_SITE_ID = "152065"
    MBO_APPOINTMENT_ID = None
    MBO_TOKEN = None
    MBO_HEADERS = None
    MBO_DATETIME_FORMAT = "%Y-%m-%dT%H:%M:%S"
    MBO_API_KEY_FP_VIRTUAL = os.getenv('MBO_API_KEY_FP_VIRTUAL')
    MBO_API_KEY_FP_SOURCE = os.getenv('MBO_API_KEY_FP_SOURCE')
    MBO_CREDENTIALS = os.getenv('MBO_CREDENTIALS')
    MBO_USER_NAME = None
    MBO_PASSWORD = None

    MBO_COUPON_CODE_KEY_30 = 'Key30'
    MBO_COUPON_CODE_KEY_49 = 'Key49'
    MBO_COUPON_CODE_KEY_79 = 'Key79'
    MBO_COUPON_CODE_KEY_89 = 'Key89'
    MBO_COUPON_CODE_KEY_FREE = 'KeyFree'
    MBO_COUPON_CODE_N_DAYS_FREE = 'NDaysFree'
    MBO_COUPON_PT_PACK_DISCOUNT_10 = 'ptPackDiscount10'
    MBO_COUPON_PT_PACK_FREE = 'FreePTPack'
    MBO_COUPON_CODE_MEMBERSHIP_DISCOUNT_2 = 'membershipDiscount_4' # This is $2 off weekly membership price
    MBO_COUPON_CODE_COMFORT_CANCEL = 'ComfortCancel'

    FS_COACHING_MODALITIES_FACE_TO_FACE = 'Face-to-Face Coach'
    FS_COACHING_MODALITIES_LIFESTYLE = 'Lifestyle Personal Training'
    FS_COACHING_MODALITIES_VIRTUAL_COACH = 'Virtual Coach'
    FS_TRAINING_OPTIONS_FITNESS_COACHING = 'Fitness Coaching'
    FS_TRAINING_OPTIONS_WELNESS_COACHING = 'Wellness Coaching'
    FS_TRAINING_OPTIONS_PROGRAMMING_CONSULT = 'Programming + Consult'
    FS_TRAINING_OPTIONS_STARTER_PACK = 'Starter Pack'
    FS_PAYMENT_TYPE_CREDIT_CARD = 'Credit Card'
    FS_PAYMENT_TYPE_DIRECT_DEBIT = 'Direct Debit'
    FS_PAYMENT_TYPE_BANK_ACCOUNT = 'Bank Account'
    FS_PAYMENT_TYPE_STORED_DETAILS = 'Use Existing Details'
    FS_COUPON_X2FREE = '2 Free Sessions'
    FS_ACCESS_KEY_PAYMENT_OPTION_FIRST_PAYMENT = 'Add to First Payment'
    
    SOURCE_TEST_BASE_URL = 'https://test.fitnessplayground.com.au/v1/'
    SOURCE_BASE_URL = 'https://source.fitnessplayground.com.au/v1/'
    SOURCE_UID = os.getenv('SOURCE_UID')
    SOURCE_AUTH_HEADER = 'x-fp-authorization'
    SOURCE_GET_ALL_CONTRACTS_URL = 'source/getAllContracts/'
    SOURCE_GET_ALL_SERVICES_URL = 'source/getAllServices/'
    SOURCE_GET_ALL_PRODUCTS_URL = 'source/getAllProducts/'
    SOURCE_GET_CONTRACTS_URL = 'source/getContracts/' # This is only when contractType is not null
    SOURCE_ENROLEMNT_DATA_URL = 'source/enrolmentData'
    SOURCE_FIND_ENROLMENTS_URL = 'source/findEnrolment'
    SOURCE_GET_PHONE_ENROLMENT_URL = "source/getPhoneEnrolment"
    SOURCE_SEARCH_CLIENTS_URL = "source/searchClients"
    SOURCE_GET_ACCESS_KEY_SITE_CODE = "source/getAccessKeySiteCode/"
    SOURCE_FP_COACH_ENROLMENT_URL = "source/fpCoachEnrolment"
    SOURCE_FP_FIND_COACH_ENROLMENTS_URL = "source/findCoachEnrolment"
    SOURCE_GYM_DETAILS_URL = "source/gym"
    SOURCE_TRIGGER_COMMS_URL = "source/triggerCommunications/"
    SOURCE_HANDLE_MANUAL_SUBMISSION_URL = "source/manualSubmission"
    SOURCE_REFERRAL_URL = "source/referral"
    SOURCE_SEARCH_PRE_EX_URL = "source/searchPreEx"
    SOURCE_SAVE_PT_TRACKER_URL = 'source/savePtTracker'
    
    SOURCE_STAFF_LIST = None
    SOURCE_GYM_LISTS = None
    SOURCE_BUNKER = 'bunker'
    SOURCE_FITNESS_PLAYGROUND = 'fitnessPlayground'
    SOURCE_STARTER_PACK_GURU = 'fitnessGuru'           
    SOURCE_STARTER_PACK_PT_ONGOING = 'ongoingPersonalTraining'
    SOURCE_STARTER_PACK_LIFESTYLE_PT = 'lifestylePersonalTraining' 
    SOURCE_STARTER_PACK_PACK = 'ptPack'                  
    SOURCE_STARTER_PACK_VIRTUAL_COACH = 'virtualCoach'
    SOURCE_STARTER_PACK_WELLNESS_COACH = 'wellnessCoach'
    SOURCE_ACCESS_KEY_PAYMENT_OPTION_PAY_TODAY = 'Pay Today'
    
    AC_API_TOKEN = os.getenv('AC_API_TOKEN')
    AC_BASE_URL = 'https://thefitnessplayground.api-us1.com/api/3/'
    AC_ADD_UPDATE_CONTACT_URL = 'contact/sync'
    AC_ADD_CONTACT_TAG_URL = 'contactTags'
    AC_ADD_CUSTOM_FIELD_URL = 'fieldValues'
    
    FS_CLIENT_PRE_SALE_FORM_URL_BK = 'https://fitnessplayground.formstack.com/forms/presale_enrolment_form_bk'
    FS_CLIENT_PRE_SALE_FORM_URL_FP = 'https://fitnessplayground.formstack.com/forms/presale_enrolment_form_fp'
    
    FS_EXTERNAL_PHONE_ENROLMENT_URL_BK = 'https://fitnessplayground.formstack.com/forms/phone_enrolment_form_external_bk'
    FS_EXTERNAL_PHONE_ENROLMENT_URL_FP = 'https://fitnessplayground.formstack.com/forms/phone_enrolment_form_external_fp'
    
    LOGO_FP = "https://dmz5utdoc4n0a.cloudfront.net/img/logos/FP_Icon.png"
    LOGO_BK = "https://dmz5utdoc4n0a.cloudfront.net/img/logos/TheBunker_Icon.png"
    URL_FP = "https://www.fitnessplayground.com.au"
    URL_BK = "https://www.thebunkergym.com.au"
    URL_STR_FP = "FITNESSPLAYGROUND.COM.AU"
    URL_STR_BK = "THEBUNKERGYM.COM.AU"
    
    LOGOS_FP_HEADER_URL = "https://dmz5utdoc4n0a.cloudfront.net/img/logos/fp_horizontal_black.png"
    LOGOS_BK_HEADER_URL = "https://dmz5utdoc4n0a.cloudfront.net/img/logos/bunker_horizontal_black.png"
    ICONS_TICK = "https://dmz5utdoc4n0a.cloudfront.net/img/icons/tick+icon.png"
    
    EMAIL_ADDRESS_SH = "surryhills@fitnessplayground.com.au"
    EMAIL_ADDRESS_NT = "newtown@fitnessplayground.com.au"
    EMAIL_ADDRESS_MK = "marrickville@fitnessplayground.com.au"
    EMAIL_ADDRESS_BK = "train@thebunkergym.com.au"
    
    AUTH_SPLIT_CHARACTER = ':'
    EMAIL_CREDENTIALS = os.getenv('EMAIL_CREDENTIALS')
    DOCUMENTS_EMAIL_CREDENTIALS = os.getenv('DOCUMENTS_EMAIL_CREDENTIALS')
    MEMBERSHIPS_EMAIL_CREDENTIALS = os.getenv('MEMBERSHIPS_EMAIL_CREDENTIALS')
    SERVER = 'smtp.gmail.com'
    PORT = 465
    SMTP_USERNAME = None
    SMTP_PASSWORD = None
    
    GS_BASE_URL = 'https://login.gymsales.net/api/v1/'
    GS_CREDENTIALS = os.getenv('GS_CREDENTIALS')
    GS_USERNAME = None
    GS_PASSWORD = None
    GS_SEARCH_PEOPLE_URL = 'people/search' # params -> company id and first_name, last_name, email, phone_mobile
    GS_PEOPLE_URL = 'people'
    GS_BK_COMPANY_ID = 2975
    GS_GW_COMPANY_ID = 2509
    GS_MK_COMPANY_ID = 1812
    GS_NT_COMPANY_ID = 1809
    GS_SH_COMPANY_ID = 1808
    GS_ISF_COMPANY_ID = 4484
    GS_FP_VIRTUAL_COMPANY_ID = 5013
    GS_CORPORATE_VIRTUAL_COMPANY_ID = 5014
    GS_LEAD_SOURCE_POS = "Referral - POS"
    
    VIRTUAL_PLAYGROUND_LAMBDA_URL = "https://g0i6ynanq2.execute-api.ap-southeast-2.amazonaws.com/dev/handler.fp_virtual_enrol"
    KAJABI_ACTIVATION_URL = "https://checkout.kajabi.com/webhooks/offers/mdr3JLj5Dzq5X2gA/585176/activate"

    PDF_WRITER_ENROLMENT_LAMBDA_URL = "https://wzerlkt2vi.execute-api.ap-southeast-2.amazonaws.com/dev/enrolment_pdf_writer_v2"
    PDF_WRITER_FP_COACH_ENROLMENT_LAMBDA_URL = "https://wzerlkt2vi.execute-api.ap-southeast-2.amazonaws.com/dev/coach_pdf_writer"
    
    


class MemberStatus:
    SAVED = ("SAVED","Data has been saved to the database",102)
    PENDING = ("PENDING","New member, waiting to be added to MindBody",418)
    ADDED = ("ADDED","Added to MindBody but not active yet",200)
    INACTIVE = ("INACTIVE","Added to MindBody but not active yet",418)
    PURCHASE_CONTRACT_ERROR = ("PURCHASE_CONTRACT_ERROR","Error purchasing MBO Contract",418)
    PURCHASE_SERVICE_ERROR = ("PURCHASE_SERVICE_ERROR","Error purchasing MBO Service",418)
    ADD_DIRECT_DEBIT_INFO_ERROR = ("ADD_DIRECT_DEBIT_INFO_ERROR","Error adding client direct debit info",418)
    TEMP_MEMBERSHIP = ("TEMP_MEMBERSHIP","Temporary membership applied",412)
    VIRTUAL_PLAYGROUND_ERROR = ("VIRTUAL_PLAYGROUND_ERROR","Virtual Playground enrolment failed",412)
    GYM_SALES_ERROR = ("GYM_SALES_ERROR","Failed to update GymSales",412)
    ACTIVE = ("ACTIVE","Active in MindBody but incomplete submission",412)
    MANUAL = ("MANUAL","Memberships need to be actioned manually",412)
    DUPLICATE_KEY_DIFF_MEMBER = ("DUPLICATE_KEY_DIFF_MEMBER","Access key already exists on a different member in MindBody",501)
    DUPLICATE_KEY_SAME_MEMBER = ("DUPLICATE_KEY_SAME_MEMBER","Access key and member already exist in MindBody",418)
    ACCESS_KEY_ERROR = ("ACCESS_KEY_ERROR","Failed to upload access key to Mindbody",418)
    DUPLICATE_KEY = ("DUPLICATE_KEY","Access key already exist in MindBody",418)
    DUPLICATE_USERNAME = ("DUPLICATE_USERNAME","Username already exist in MindBody",418)
    ERROR = ("ERROR","Error",501)
    EXTERNAL_PT = ("EXTERNAL_PT","Member has signed up for external personal training",412)
    IN_CLUB_PAYMENT = ("IN_CLUB_PAYMENT","Member has paid cash or efpos",412)
    SUCCESS = ("SUCCESS","Action was successful",200)
    EMAIL_CAMPAIGN_FAILED = ("EMAIL_CAMPAIGN_FAILED","Failed to trigger email auto-responder",418)
    EMAIL_CAMPAIGN_ADDED = ("EMAIL_CAMPAIGN_ADDED","Member added to email auto-responder",200)
    PDF = ("PDF","Contract is waiting to be uploaded to MBO",200)
    COMPLETE = ("COMPLETE","Action is complete",200)
    PROCESSING = ("PROCESSING","Action is currently been processed",102)
    CLIENT_NOT_FOUND = ("CLIENT_NOT_FOUND","Client not found in Mindbody Online",412)
#     PENDING_CLIENT_AUTHORISATION = ('PENDING_CLIENT_AUTHORISATION','Waiting for client authorisation',201)
    ENROLMENT_RECEIVED = ("ENROLMENT_RECEIVED", "Received Membership Change", 100)
    ENROLMENT_AUTHORISATION_PENDING = ("ENROLMENT_AUTHORISATION_PENDING", "Waiting on authorisation from member", 200)
    ENROLMENT_AUTHORISATION_OVERDUE = ("ENROLMENT_AUTHORISATION_OVERDUE", "Authorisation from member is overdue", 200)
    ENROLMENT_AUTHORISED = ("ENROLMENT_AUTHORISED", "Enrolment has been authorised by member", 200)

    PAR_Q_PENDING = ("PAR_Q_PENDING","Waiting to receive par-q",200)
    PAR_Q_RECEIVED = ("PAR_Q_RECEIVED","Par-q has been received",200)
    PAR_Q_OVERDUE = ("PAR_Q_OVERDUE","Par-q is overdue",200)
    PT_FREE_SESSION = ("PT_FREE_SESSION","Member has booked N Free PT Sessions",200)
    PT_PAID_SESSION = ("PT_PAID_SESSION","Memebr has booked N Paid PT Sessions",200)
    PT_CANCELLED = ("PT_CANCELLED","Member has cancelled PT",200)

    ZETLAND_PRE_SALES = ("ZETLAND_PRE_SALES","Zetland pre-sales enrolment",200)
    ZETLAND_PRE_SALES_MANUAL = ("ZETLAND_PRE_SALES_MANUAL","Zetland pre-sales enrolment needs to be actioned manually",412)

   
class CommunicationsStatus:
    INTERNAL_COMMS_PENDING = ("INTERNAL_COMMS_PENDING", "Top of funnel for internal communications", 100)
    MC_NOTES_PENDING = ("MC_NOTES", "Membership Consultant needs to send notes to Personal Trainer", 412)
    NOTIFY_PT_LEAD = ("NOTIFY_PT_LEAD", "Lead has been assigned to a Personal Trainer", 200)
    UNASSIGNED_PT_LEAD = ("UNASSIGNED_PT_LEAD", "Lead has been assigned to Personal Training Manager", 412)
    REASSIGNED_PT_LEAD = ("REASSIGNED_PT_LEAD", "Lead has been re-assigned by the Personal Trainer",200)
    EMAIL_CAMPAIGN_PENDING = ("EMAIL_CAMPAIGN_PENDING", "Member needs to be added to member facing email auto-responder", 501)
    EMAIL_CAMPAIGN_ADDED = ("EMAIL_CAMPAIGN_ADDED", "Member added to email auto-responder", 201)
    EMAIL_CAMPAIGN_ERROR = ("EMAIL_CAMPAIGN_ERROR", "Error adding email campaign", 418)
    INTERNAL_COMMS_COMPLETE = ("INTERNAL_COMMS_COMPLETE", "Internal communications are completed", 200)
    CLIENT_AUTHORISATION_SENT = ("CLIENT_AUTHORISATION_SENT", "Authorisation email has been sent to client", 200)
    CLIENT_AUTHORISATION_ERROR = ("CLIENT_AUTHORISATION_ERROR", "Authorisation email failed to send", 401)
    CLIENT_AUTHORISATION_RECEIVED = ("CLIENT_AUTHORISATION_RECEIVED", "Authorisation has been received by the client",200)
    PDF_EMAIL_SENT = ("PDF_EMAIL_SENT", "Contract PDF has been sent to client",200)
    PDF_EMAIL_ERROR = ("PDF_EMAIL_ERROR", "Contract PDF failed to send to client",418)
    


class FS_FIELD_IDS:
    FORM = {
#         Phone Enrolment Internal
        Constants.FS_PHONE_ENROLMENT_INTERNAL_FORM_ID : {
            "IS_TEST" : "94276624" , # {$94276624 is_test}
            "SUBMISSION_DATETIME" : "94276625" , # {$94276625 submission_datetime}
            "PRE_EX_ID" : "111958479" , # {$111958479 pre_ex_id}
            "LOCATION_ID" : "94403502" ,
            "NAME" : "94276627", # { "first": "Clint", "last": "Test" } # {$94276627 Name}
            "ADDRESS" : "94276628" , # { "address": "120A Devonshire Street", "city": "Surry Hills", "state": "NSW", "zip": "2010" } # {$94276628 Address}
            "PHONE" : "94276629" , # {$94276629 Phone}
            "EMAIL" : "94276630" , # {$94276630 Email}
            "DATE_OF_BIRTH" : "94276631" , # {$94276631 Date of Birth}
            "EMERGENCY_CONTACT_NAME" : "94276633" , # {$94276633 Emergency Contact Name}
            "EMERGENCY_CONTACT_PHONE" : "94276634" , # {$94276634 Emergency Contact Phone}
            "HOW_DID_YOU_HEAR_ABOUT_US" : "94276635" , # {$94276635 How Did You Hear About Us?}
            "REFERRED_BY_NAME" : "94276636" , # {$94276636 Who Referred You?}
            "REFERRED_BY_NUMBER" : "94276637" , # {$94276637 Friends Number}
            "HOME_LOCATION" : "94276639" , # {$94276639 Home Location}
            "MEMBERSHIP_NAME_NT" : "94276640" , # {$94276640 Membership Name}
            "MEMBERSHIP_NAME_MK" : "101491364" , # {$101491364 Membership Name}
            "MEMBERSHIP_NAME_SH" : "94276641" , # {$94276641 Membership Name}
            "MEMBERSHIP_NAME_BK" : "94276642" , # {$94276642 Membership Name}
            "MEMBERSHIP_NAME" : None,
            "TRAINING_OPTIONS" : "94276643" , # {$94276643 Training Options}
            "COACHING_MODALITY" : "94276644" , # {$94276644 Coaching Modality}
            "VIRTUAL_OPTIONS" : "94327899" , # {$94327899 Virtual Options}
            "SESSION_LENGTH" : "94276645" , # {$94276645 Session Length}
            "TRAINING_PACKAGE_30_MIN" : "94276649" , # {$94276649 Training Package}
            "TRAINING_PACKAGE_40_MIN" : "94276650" , # {$94276650 Training Package}
            "TRAINING_PACKAGE_60_MIN" : "94276651" , # {$94276651 Training Package}
            "TRAINING_PACKAGE_30_MIN_BK" : "94276652" , # {$94276652 Training Package}
            "TRAINING_PACKAGE_40_MIN_BK" : "94276653" , # {$94276653 Training Package}
            "TRAINING_PACKAGE_60_MIN_BK" : "94276654" , # {$94276654 Training Package}
            "TRAINING_PACKAGE_LIFESTYLE" : None , # {$94276655 Training Package}
            "TRAINING_PACKAGE_LIFESTYLE_BK" : None , # {$94276656 Training Package}
            "TRAINING_PACKAGE_VIRTUAL_FITNESS" : None , # {$94276657 Training Package}
            "TRAINING_PACKAGE_VIRTUAL_WELLNESS" : None , # {$94276658 Training Package}
            "TRAINING_PACKAGE_WELLNESS" : None , # {$94276659 Training Package}
            "TRAINING_PACKAGE" : None,
            "STARTER_PACK_OPTIONS" : "94276660" , # {$94276660 Starter Pack Options}
            "NUMBER_OF_SESSIONS_EXTERNAL" : "94403802" , # {$94403802 Number of Sessions}
            "PRICES_PER_SESSION_EXTERNAL" : "94403885" , # {$94403885 Price Per Session}
            "ADD_CRECHE" : "94350160" , # {$94350160 Add Creche}
            "CRECHE" : "94349722" , # {$94349722 Creche}
            "ADD_VIRTUAL_PLAGROUND" : "94280674" , # {$94280674 Add Virtual Playground }
            "SPECIALS" : "94276665" , # {$94276665 Specials}
            "2_FREE" : "94276667" , # {$94276667 2Free}
            "FREE_PT_PACK" : "94276668" , # {$94276668 FreePTPack}
            "FREE_TIME_SH" : "94276669" , # {$94276669 Free Time SH}
            "FREE_TIME_NT" : "111954811", # {$111954811 Free Time NT}
            "FREE_TIME_MK" : "111954821", # {$111954821 Free Time MK}
            "FREE_TIME_BK" : "111954822", # {$111954822 Free Time BK}
            "ACCESS_KEY_DISCOUNT_SH" : "94276670" , # {$94276670 Access Key Discount SH}
            "ACCESS_KEY_DISCOUNT_NT" : "111954856" , # {$111954856 Access Key Discount NT}
            "ACCESS_KEY_DISCOUNT_MK" : "111954857" , # {$111954857 Access Key Discount MK}
            "ACCESS_KEY_DISCOUNT_BK" : "111954861" , # {$111954861 Access Key Discount BK}
            "NUMBER_DAYS_FREE" : "94330525" , # {$94330525 NumberDaysFree}
            "END_OF_YEAR_FREE" : "102849320", # {$102849320 EndOfYearSpecial}
            "MEMBERSHIP_DISCOUNT" : "102849318", # {$102849318 DiscountedMembership}
            "ACCESS_KEY_RETAIL_PRICE" : "94276672" , # {$94276672 AccessKeyPrice}
            "ACCESS_KEY_FEE" : "94276673" , # {$94276673 Access Key}
            "ONE_OFF_TOTAL_PAYMENT" : "94276674" , # {$94276674 One Off Payment Total}
            "CRECHE_FORTNIGHTLY_DD" : "94350283" , # {$94350283 Creche Fortnightly Direct Debit}
            "COACHING_FORTNIGHTLY_DD" : "94349002" , # {$94349002 Coaching Fortnightly Direct Debit}
            "MEMBERSHIP_FORTNIGHTLY_DD" : "94350367" , # {$94350367 Membership Fortnightly Direct Debit}
            "TOTAL_FORTNIGHTLY_DD" : "94276675" , # {$94276675 Total Ongoing Fortnightly Direct Debit}
            "START_DATE" : "94276677" , # {$94276677 Membership Start Date}
            "FIRST_DEBIT_DATE" : "94276678" , # {$94276678 First Debit Date}
            "EOY_FIRST_DEBIT_DATE" : "102853638", # {$102853638 First Debit Date}
            "COACHING_START_DATE" : "94468404", # {$94468404 Coaching Start Date}
            "COACHING_FIRST_DEBIT_DATE" : "94468412", # {$94468412 Coaching First Debit Date}
            "COACH_NAME_MK" : "94276661" , # {$94276661 Coach Name}
            "COACH_NAME_NT" : "94276662" , # {$94276662 Coach Name}
            "COACH_NAME_SH" : "94276663" , # {$94276663 Coach Name}
            "COACH_NAME_BK" : "94276664" , # {$94276664 Coach Name}
            "COACH_NAME" : None,

            "TRAINGING_PACKAGE_SOLD_BY" : "96024208", # {$96024208 Training Package was Organised By:}
            "TRAINING_PACKAGE_CONSULTANT_LOCATION" : "98537268", # {$98537268 Training Package Consultant Location}
            "TRAINING_PACKAGE_CONSULTANT_SH" : "98537284", # {$98537284 Training Package Consultant}
            "TRAINING_PACKAGE_CONSULTANT_NT" : "98537279", # {$98537279 Training Package Consultant}
            "TRAINING_PACKAGE_CONSULTANT_MK" : "98537276", # {$98537276 Training Package Consultant}
            "TRAINING_PACKAGE_CONSULTANT_BK" : "98537286", # {$98537286 Training Package Consultant}

            "MEMBERSHIP_CONSULTANT_LOCATION_ID" : "94403487" , # {$94403487 Membership Consultant Location}
            "MEMBERSHIP_CONSULTANT_MK" : "94276679" , # {$94276679 Membership Consultant}
            "MEMBERSHIP_CONSULTANT_NT" : "94276680" , # {$94276680 Membership Consultant}
            "MEMBERSHIP_CONSULTANT_SH" : "94276681" , # {$94276681 Membership Consultant}
            "MEMBERSHIP_CONSULTANT_BK" : "94276682" , # {$94276682 Membership Consultant}
            "MEMBERSHIP_CONSULTANT" : None,
            "NOTES" : "94276693" , # {$94276693 Notes}
            "HAS_REFERRAL" : "94276689", # {$94276689 Is there anyone you want to refer to The Fitnes...}
            "REFERRAL_NAME" : "94276690" , # {$94276690 Referral Name 1}
            "REFERRAL_EMAIL" : "94276691", # {$94276691 Referral Email 1}
            "REFERRAL_PHONE" : "94276692", # {$94276692 Referral Phone 1}
            "HAS_REFERRAL_2" : "104075740", # {$104075740 Is there anybody else?}
            "REFERRAL_NAME_2" : "104075750", # {$104075750 Referral Name 2}
            "REFERRAL_EMAIL_2" : "104075753", # {$104075753 Referral Email 2}
            "REFERRAL_PHONE_2" : "104075752", # {$104075752 Referral Phone 2}
            "HAS_REFERRAL_3" : "104075755", # {$104075755 Is there anybody else?}
            "REFERRAL_NAME_3" : "104075759", # {$104075759 Referral Name 3}
            "REFERRAL_EMAIL_3" : "104075762", # {$104075762 Referral Email 3}
            "REFERRAL_PHONE_3" : "104075761", # {$104075761 Referral Phone 3}
            "ACCESS_KEY_PAYMENT_OPTION" : "105330044", # {$105330044 Access Key Payment Option}
            "ACCESS_KEY_PAYMENT_METHOD" : "105330050", # {$105330050 Access Key Payment Method}
            "COMFORT_CANCEL_SH" : "105330104", # {$105330104 Satisfaction Period SH}
            "COMFORT_CANCEL_NT" : "111954875", # {$111954875 Satisfaction Period NT}
            "COMFORT_CANCEL_MK" : "111954881", # {$111954881 Satisfaction Period MK}
            "COMFORT_CANCEL_BK" : "111954879", # {$111954879 Satisfaction Period BK}
            "COMMENCE_VIRTUAL_PLAGROUND_ON" : "105330030" # {$105330030 When do you want to commence your Virtual Playg...}
        },
        # FP EXTERNAL PHONE SUBMISSION
        Constants.FS_PHONE_ENROLMENT_EXTERNAL_FP_FORM_ID : {
            "IS_TEST" : "94276695" ,
            "FS_INTERNAL_SUBMISSION_UNIQUE_ID" : "94276694",
            "FS_INTERNAL_SUBMISSION_FORM_ID" : "94468037",
             "SUBMISSION_DATETIME" : "94276697" ,
             "LOCATION_ID" : "94277165" , # This is the membership consultant location id
             "NAME" : "94276699", # { "first": "Clint", "last": "Test" }
             "ADDRESS" : "94276700" , # { "address": "120A Devonshire Street", "city": "Surry Hills", "state": "NSW", "zip": "2010" }
             "PHONE" : "94276701" ,
             "EMAIL" : "94276702" ,
             "DATE_OF_BIRTH" : "94276703" ,
             "EMERGENCY_CONTACT_NAME" : "94276705" ,
             "EMERGENCY_CONTACT_PHONE" : "94276706" ,
            "GENDER" : "94276704",
             "HOW_DID_YOU_HEAR_ABOUT_US" : None ,
             "REFERRED_BY_NAME" : None ,
             "REFERRED_BY_NUMBER" : None ,
             "HOME_LOCATION" : "94276708" ,
             "MEMBERSHIP_NAME_NT" : None ,
            "MEMBERSHIP_NAME_MK" : None ,
             "MEMBERSHIP_NAME_SH" : None ,
             "MEMBERSHIP_NAME_BK" : None ,
            "MEMBERSHIP_NAME" : "94276709",
             "TRAINING_OPTIONS" : None,
             "COACHING_MODALITY" : None,
             "VIRTUAL_OPTIONS" : None,
             "SESSION_LENGTH" : None,
             "TRAINING_PACKAGE_30_MIN" : None ,
             "TRAINING_PACKAGE_40_MIN" : None ,
             "TRAINING_PACKAGE_60_MIN" : None ,
             "TRAINING_PACKAGE_30_MIN_BK" : None ,
             "TRAINING_PACKAGE_40_MIN_BK" : None ,
             "TRAINING_PACKAGE_60_MIN_BK" : None ,
             "TRAINING_PACKAGE_LIFESTYLE" : None ,
             "TRAINING_PACKAGE_LIFESTYLE_BK" : None ,
             "TRAINING_PACKAGE_VIRTUAL_FITNESS" : None ,
             "TRAINING_PACKAGE_VIRTUAL_WELLNESS" : None ,
             "TRAINING_PACKAGE_WELLNESS" : None ,
            "TRAINING_PACKAGE" : "94276710",
             "STARTER_PACK_OPTIONS" : "94469431" ,
             "ADD_CRECHE" : "94468050" ,
             "CRECHE" : None ,
             "FREE_PT_PACK" : "94469001" ,
            "2_FREE" : None,
             "FREE_TIME" : "94468999" ,
             "ACCESS_KEY_DISCOUNT" : None,
             "ACCESS_KEY_FEE" : "94276711" ,
             "ONE_OFF_TOTAL_PAYMENT" : "94276714" ,
             "CRECHE_FORTNIGHTLY_DD" : "94468209" ,
             "COACHING_FORTNIGHTLY_DD" : "94468213" ,
             "MEMBERSHIP_FORTNIGHTLY_DD" : "94468219" ,
             "TOTAL_FORTNIGHTLY_DD" : "94276715" ,
             "VIRTUAL_PLAYGROUND_MONTHLY_DD" : "94276712",
            "ADD_VIRTUAL_PLAGROUND" : "94276696", # has_virtual
             "START_DATE" : "94276717" ,
             "FIRST_DEBIT_DATE" : "94276718" ,
             "COACHING_START_DATE" : "94468460",
             "COACHING_FIRST_DEBIT_DATE" : "94468462",
            "COACH_NAME_MK" : None,
            "COACH_NAME_NT" : None ,
            "COACH_NAME_SH" : None,
            "COACH_NAME_BK" : None,
            "COACH_NAME" : "94276719",
            "COACH_MBO_ID" : "94468157",
            "MEMBERSHIP_CONSULTANT_LOCATION_ID" : "94277165",
            "MEMBERSHIP_CONSULTANT_MK" : None,
            "MEMBERSHIP_CONSULTANT_NT" : None,
            "MEMBERSHIP_CONSULTANT_SH" : None,
            "MEMBERSHIP_CONSULTANT_BK" : None,
            "MEMBERSHIP_CONSULTANT" : "94276720",
            "MEMBERSHIP_CONSULTANT_MBO_ID" : "94468156",
             "NOTES" : "94276722" ,
             "HAS_REFERRAL" : "94468298" ,
             "REFERRAL_NAME" : "94468299" ,
             "REFERRAL_EMAIL" : "94468300" ,
             "REFERRAL_PHONE" : "94468301",
            "NUMBER_ONE_GOAL" : "94468292",
            "ARE_YOU_PAYING_FOR_THE_MEMBERSHIP" : "94277713",
            "BILLING_NAME" : "94277828",
            "BILLING_ADDRESS" : "94277830",
            "PAYMENT_METHOD" : "94276724",
            "CREDIT_CARD" : "94276725", # { "card": "4111111111111111", "cardexp": "12/22", "cvv": "123" }
            "BSB" : "94276729",
            "ACCOUNT_NUMBER" : "94276730",
            "ACCOUNT_TYPE" : "94276731",
            "PAYMENT_AUTHORISATION" : "94469216",
            "PAYMENT_AUTHORISATION_SIGNATURE" : "94469234",
            "MEMBERSHIP_TERMS_AGREEMENT" : "94468262",
            "PT_6_SESSION_COMMITMENT" : "94468263",
            "LIFESTYLE_PT_COMMITMENT" : "94468264",
            "ANNUAL_CONTRACT_COMMITMENT" : "94468265",
            "PARENT_GUARDIAN_SIGNATURE" : "94468266",
            "SIGNATURE" : "94468267",
            "MEDICAL" : "94468293",
            "MEDICAL_CLEARANCE" : "94468296",
            "INJURIES" : "94468297",
            "TRAINING_AVAILABILITY" : "94468294",
            "TIME_AVAILABILITY" : "94468295",
            "MEMBER_PHOTO" : "103259922",
            "WILL_UPLOAD_COVID_VERIFICATION" : "116317173", # {$116317173 Upload Proof of COVID Vaccination or Medical Co...}
            "COVID_VERIFICATION_UPLOAD" : "116317177" # {$116317177 COVID Verification Upload}
        },
        # Bunker External Phone Sale
        Constants.FS_PHONE_ENROLMENT_EXTERNAL_BK_FORM_ID : {
            "IS_TEST" : "94608508" ,
            "FS_INTERNAL_SUBMISSION_UNIQUE_ID" : "94608509",
            "FS_INTERNAL_SUBMISSION_FORM_ID" : "94608510",
             "SUBMISSION_DATETIME" : "94608525" ,
             "LOCATION_ID" : "94608512" , # This is the membership consultant location id
             "NAME" : "94608527", # { "first": "Clint", "last": "Test" }
             "ADDRESS" : "94608528" , # { "address": "120A Devonshire Street", "city": "Surry Hills", "state": "NSW", "zip": "2010" }
             "PHONE" : "94608529" ,
             "EMAIL" : "94608530" ,
             "DATE_OF_BIRTH" : "94608531" ,
             "EMERGENCY_CONTACT_NAME" : "94608534" ,
             "EMERGENCY_CONTACT_PHONE" : "94608535" ,
            "GENDER" : "94608533",
             "HOW_DID_YOU_HEAR_ABOUT_US" : None ,
             "REFERRED_BY_NAME" : None ,
             "REFERRED_BY_NUMBER" : None ,
             "HOME_LOCATION" : "94608537" ,
             "MEMBERSHIP_NAME_NT" : None ,
            "MEMBERSHIP_NAME_MK" : None ,
             "MEMBERSHIP_NAME_SH" : None ,
             "MEMBERSHIP_NAME_BK" : None ,
            "MEMBERSHIP_NAME" : "94608538",
             "TRAINING_OPTIONS" : None,
             "COACHING_MODALITY" : None,
             "VIRTUAL_OPTIONS" : None,
             "SESSION_LENGTH" : None,
             "TRAINING_PACKAGE_30_MIN" : None ,
             "TRAINING_PACKAGE_40_MIN" : None ,
             "TRAINING_PACKAGE_60_MIN" : None ,
             "TRAINING_PACKAGE_30_MIN_BK" : None ,
             "TRAINING_PACKAGE_40_MIN_BK" : None ,
             "TRAINING_PACKAGE_60_MIN_BK" : None ,
             "TRAINING_PACKAGE_LIFESTYLE" : None ,
             "TRAINING_PACKAGE_LIFESTYLE_BK" : None ,
             "TRAINING_PACKAGE_VIRTUAL_FITNESS" : None ,
             "TRAINING_PACKAGE_VIRTUAL_WELLNESS" : None ,
             "TRAINING_PACKAGE_WELLNESS" : None ,
            "TRAINING_PACKAGE" : "94608539",
             "STARTER_PACK_OPTIONS" : "94608542" ,
             "ADD_CRECHE" : "94608516" ,
             "CRECHE" : None ,
             "FREE_PT_PACK" : "94608543" ,
            "2_FREE" : None,
             "FREE_TIME" : "94608552" ,
             "ACCESS_KEY_DISCOUNT" : None ,
             "ACCESS_KEY_FEE" : "94608540" ,
             "ONE_OFF_TOTAL_PAYMENT" : "94608544" ,
             "CRECHE_FORTNIGHTLY_DD" : "94608545" ,
             "COACHING_FORTNIGHTLY_DD" : "94608546" ,
             "MEMBERSHIP_FORTNIGHTLY_DD" : "94608547" ,
             "TOTAL_FORTNIGHTLY_DD" : "94608548" ,
             "VIRTUAL_PLAYGROUND_MONTHLY_DD" : "94608549",
            "ADD_VIRTUAL_PLAGROUND" : "94608515", # has_virtual
             "START_DATE" : "94608550" ,
             "FIRST_DEBIT_DATE" : "94608551" ,
             "COACHING_START_DATE" : "94608553",
             "COACHING_FIRST_DEBIT_DATE" : "94608554",
            "COACH_NAME_MK" : None,
            "COACH_NAME_NT" : None ,
            "COACH_NAME_SH" : None,
            "COACH_NAME_BK" : None,
            "COACH_NAME" : "94608555",
            "COACH_MBO_ID" : "94608513",
            "MEMBERSHIP_CONSULTANT_LOCATION_ID" : "94608512",
            "MEMBERSHIP_CONSULTANT_MK" : None,
            "MEMBERSHIP_CONSULTANT_NT" : None,
            "MEMBERSHIP_CONSULTANT_SH" : None,
            "MEMBERSHIP_CONSULTANT_BK" : None,
            "MEMBERSHIP_CONSULTANT" : "94608556",
            "MEMBERSHIP_CONSULTANT_MBO_ID" : "94608511",
             "NOTES" : "94608558" ,
             "HAS_REFERRAL" : "94608577" ,
             "REFERRAL_NAME" : "94608578" ,
             "REFERRAL_EMAIL" : "94608579" ,
             "REFERRAL_PHONE" : "94608580",
            "NUMBER_ONE_GOAL" : "94608571",
            "ARE_YOU_PAYING_FOR_THE_MEMBERSHIP" : "94608560",
            "BILLING_NAME" : "94608561",
            "BILLING_ADDRESS" : "94608562",
            "PAYMENT_METHOD" : "94608563",
            "CREDIT_CARD" : "94608564", # { "card": "4111111111111111", "cardexp": "12/22", "cvv": "123" }
            "BSB" : "94608565",
            "ACCOUNT_NUMBER" : "94608566",
            "ACCOUNT_TYPE" : "94608567",
            "PAYMENT_AUTHORISATION" : "94608568",
            "PAYMENT_AUTHORISATION_SIGNATURE" : "94608569",
            "MEMBERSHIP_TERMS_AGREEMENT" : "94608583",
            "PT_6_SESSION_COMMITMENT" : "94608584",
            "LIFESTYLE_PT_COMMITMENT" : "94608585",
            "ANNUAL_CONTRACT_COMMITMENT" : "94608586",
            "PARENT_GUARDIAN_SIGNATURE" : "94608587",
            "SIGNATURE" : "94608588",
            "MEDICAL" : "94608572",
            "MEDICAL_CLEARANCE" : "94608575",
            "INJURIES" : "94608576",
            "TRAINING_AVAILABILITY" : "94608573",
            "TIME_AVAILABILITY" : "94608574",
            "MEMBER_PHOTO" : "103259859",
            "WILL_UPLOAD_COVID_VERIFICATION" : "116317139", # {$116317139 Upload Proof of COVID Vaccination or Medical Co...}
            "COVID_VERIFICATION_UPLOAD" : "116317142" # {$116317142 COVID Verification Upload}
        },
        # IN-CLUB ENROLMENT
         Constants.FS_IN_CLUB_ENROLMENT_FORM_ID : {
            "IS_TEST" : "94332581" , # {$94332581 is_test}
            "FS_INTERNAL_SUBMISSION_UNIQUE_ID" : None,
            "FS_INTERNAL_SUBMISSION_FORM_ID" : None,
            "SUBMISSION_DATETIME" : "94332582", # {$94332582 submission_datetime}
            "LOCATION_ID" : None , # This is the membership consultant location id
            "NAME" : "94332584", # { "first": "Clint", "last": "Test" } # {$94332584 Name}
            "ADDRESS" : "94332585", # { "address": "120A Devonshire Street", "city": "Surry Hills", "state": "NSW", "zip": "2010" } # {$94332585 Address}
            "PHONE" : "94332586", # {$94332586 Phone}
            "EMAIL" : "94332587", # {$94332587 Email}
            "DATE_OF_BIRTH" : "94332588", # {$94332588 Date of Birth}
            "EMERGENCY_CONTACT_NAME" : "94332590", # {$94332590 Emergency Contact Name}
            "EMERGENCY_CONTACT_PHONE" : "94332591", # {$94332591 Emergency Contact Phone}
            "GENDER" : "94332589", # {$94332589 Gender}
            "HOW_DID_YOU_HEAR_ABOUT_US" : "94332592", # {$94332592 How Did You Hear About Us?}
            "REFERRED_BY_NAME" : "94332593", # {$94332593 Who Referred You?}
            "REFERRED_BY_NUMBER" : "94332594", # {$94332594 Friends Number}
            "HOME_LOCATION" : "94351559", # {$94351559 Home Location}
            "MEMBERSHIP_NAME_NT" : "94351560", # {$94351560 Membership Name}
            "MEMBERSHIP_NAME_MK" : "101490498" , # {$101490498 Membership Name}
            "MEMBERSHIP_NAME_SH" : "94351561" , # {$94351561 Membership Name}
            "MEMBERSHIP_NAME_BK" : "94351562" , # {$94351562 Membership Name}
            "MEMBERSHIP_NAME" : None,
            "TRAINING_OPTIONS" : "94351563", # {$94351563 Training Options}
            "COACHING_MODALITY" : "94351564", # {$94351564 Coaching Modality}
            "VIRTUAL_OPTIONS" : "94351565", # {$94351565 Virtual Options}
            "SESSION_LENGTH" : "94351566", # {$94351566 Session Length}
            "TRAINING_PACKAGE_30_MIN" : "94351568" , # {$94351568 Training Package}
            "TRAINING_PACKAGE_40_MIN" : "94351569" , # {$94351569 Training Package}
            "TRAINING_PACKAGE_60_MIN" : "94351570" , # {$94351570 Training Package}
            "TRAINING_PACKAGE_30_MIN_BK" : "94351571" , # {$94351571 Training Package}
            "TRAINING_PACKAGE_40_MIN_BK" : "94351572" ,  # {$94351572 Training Package}
            "TRAINING_PACKAGE_60_MIN_BK" : "94351573" , # {$94351573 Training Package}
            "TRAINING_PACKAGE_LIFESTYLE" : None , # {$94351574 Training Package Lifestyle}
            "TRAINING_PACKAGE_LIFESTYLE_BK" : None , # {$94351575 Training Package Lifestyle}
            "TRAINING_PACKAGE_VIRTUAL_FITNESS" : None , # {$94351576 Training Package}
            "TRAINING_PACKAGE_VIRTUAL_WELLNESS" : None , # {$94351577 Training Package}
            "TRAINING_PACKAGE_WELLNESS" : None , # {$94351578 Training Package}
            "TRAINING_PACKAGE" : None,
            "STARTER_PACK_OPTIONS" : "94351579" , # {$94351579 Starter Pack Options}
            "NUMBER_OF_SESSIONS_EXTERNAL" : "95431213" , # {$95431213 Number of Sessions}
            "PRICES_PER_SESSION_EXTERNAL" : "95431216" , # {$95431216 Price Per Session}
            "ADD_CRECHE" : "94351580" , # {$94351580 Add Creche}
            "CRECHE" : "94351581" , # {$94351581 Creche}
            "FREE_PT_PACK" : "94351585" , # {$94351585 FreePTPack}
            "2_FREE" : "94351584", # {$94351584 2Free}
            "FREE_TIME_SH" : "94351586" , # {$94351586 Free Time SH}            
            "FREE_TIME_NT" : "111057966" , # {$111057966 Free Time NT}
            "FREE_TIME_MK" : "111057964" , # {$111057964 Free Time MK}
            "FREE_TIME_BK" : "111057961" , # {$111057961 Free Time BK}
            "END_OF_YEAR_FREE" : "102848235", # {$102848235 EndOfYearSpecial}
            "MEMBERSHIP_DISCOUNT" : "102848237", # {$102848237 DiscountedMembership}
            "ACCESS_KEY_DISCOUNT_SH" : "94351587", # {$94351587 Access Key Discount SH}                        
            "ACCESS_KEY_DISCOUNT_NT" : "111058148", # {$111058148 Access Key Discount NT}            
            "ACCESS_KEY_DISCOUNT_MK" : "111058146", # {$111058146 Access Key Discount MK}            
            "ACCESS_KEY_DISCOUNT_BK" : "111058145", # {$111058145 Access Key Discount BK}
            "ACCESS_KEY_RETAIL_PRICE" : "94351589", # {$94351589 AccessKeyPrice}
            "ACCESS_KEY_FEE" : "94351590" , # {$94351590 Access Key}
            "ACCESS_KEY_PAYMENT_OPTION" : "103921853", # {$103921853 Access Key Payment Option}
            "ACCESS_KEY_PAYMENT_METHOD" : "104837612", # {$104837612 Access Key Payment Method}
            "COMFORT_CANCEL_SH" : "103983103", # {$103983103 Satisfaction Period SH}
            "COMFORT_CANCEL_NT" : "111058424", # {$111058424 Satisfaction Period NT}
            "COMFORT_CANCEL_MK" : "111058423", # {$111058423 Satisfaction Period MK}
            "COMFORT_CANCEL_BK" : "111058422", # {$111058422 Satisfaction Period BK}            
            "ONE_OFF_TOTAL_PAYMENT" : "94351591" , # {$94351591 One Off Payment Total}
            "CRECHE_FORTNIGHTLY_DD" : "94351592" , # {$94351592 Creche Fortnightly Direct Debit}
            "COACHING_FORTNIGHTLY_DD" : "94351593" , # {$94351593 Coaching Fortnightly Direct Debit}
            "MEMBERSHIP_FORTNIGHTLY_DD" : "94351594" , # {$94351594 Membership Fortnightly Direct Debit}
            "TOTAL_FORTNIGHTLY_DD" : "94351595" , # {$94351595 Total Ongoing Fortnightly Direct Debit}
            "VIRTUAL_PLAYGROUND_MONTHLY_DD" : "94351596", # {$94351596 Ongoing Monthly Direct Debit}
            "ADD_VIRTUAL_PLAGROUND" : "94351582", # has_virtual # {$94351582 Add Virtual Playground}
            "COMMENCE_VIRTUAL_PLAGROUND_ON" : "103988711", # {$103988711 When do you want to commence your Virtual Playg...}
            "START_DATE" : "94351597" , # {$94351597 Membership Start Date}
            "FIRST_DEBIT_DATE" : "94351598" , # {$94351598 First Debit Date}
            "EOY_FIRST_DEBIT_DATE" : "102852720", # {$102852720 First Debit Date}
            "COACHING_START_DATE" : "95431448", # {$95431448 Coaching Start Date}
            "COACHING_FIRST_DEBIT_DATE" : "95431449", # {$95431449 Coaching First Debit Date}
            "COACH_NAME_MK" : "94351599", # {$94351599 Coach Name}
            "COACH_NAME_NT" : "94351600" , # {$94351600 Coach Name}
            "COACH_NAME_SH" : "94351601", # {$94351601 Coach Name}
            "COACH_NAME_BK" : "94351602", # {$94351602 Coach Name}
            "COACH_NAME" : None,
            "COACH_MBO_ID" : None,
            "TRAINGING_PACKAGE_SOLD_BY" : "96024203", # {$96024203 Training Package was Organised By:}
            "TRAINING_PACKAGE_CONSULTANT_LOCATION" : "98479769", # {$98479769 Training Package Consultant Location}
            "TRAINING_PACKAGE_CONSULTANT_SH" : "98479850", # {$98479850 Training Package Consultant}
            "TRAINING_PACKAGE_CONSULTANT_NT" : "98479838", # {$98479838 Training Package Consultant}
            "TRAINING_PACKAGE_CONSULTANT_MK" : "98479796", # {$98479796 Training Package Consultant}
            "TRAINING_PACKAGE_CONSULTANT_BK"  : "98482524", # {$98482524 Training Package Consultant}
            "MEMBERSHIP_CONSULTANT_LOCATION_ID" : "95431633", # {$95431633 Membership Consultant Location}
            "MEMBERSHIP_CONSULTANT_MK" : "94351603", # {$94351603 Membership Consultant}
            "MEMBERSHIP_CONSULTANT_NT" : "94351604", # {$94351604 Membership Consultant}
            "MEMBERSHIP_CONSULTANT_SH" : "94351605", # {$94351605 Membership Consultant}
            "MEMBERSHIP_CONSULTANT_BK" : "94351606",  # {$94351606 Membership Consultant}
            "MEMBERSHIP_CONSULTANT" : None,
            "MEMBERSHIP_CONSULTANT_MBO_ID" : None,
            "NOTES" : "94351608" , # {$94351608 Notes}
            "NUMBER_ONE_GOAL" : "94332641", # {$94332641 What's Your #1 Goal?}
            "ARE_YOU_PAYING_FOR_THE_MEMBERSHIP" : "94335503", # {$94335503 Are you paying for the membership?}
            "BILLING_NAME" : "94335504", # {$94335504 Billing Name}
            "BILLING_ADDRESS" : "94335505", # {$94335505 Billing Address}
            "PAYMENT_METHOD" : "94335506", # {$94335506 Payment Method}
            "CREDIT_CARD" : "94335507", # { "card": "4111111111111111", "cardexp": "12/22", "cvv": "123" } # {$94335507 Credit Card}
            "BSB" : "94335508", # {$94335508 BSB}
            "ACCOUNT_NUMBER" : "94335509", # {$94335509 Account Number}
            "ACCOUNT_TYPE" : "94335510", # {$94335510 Account Type}
            "PAYMENT_AUTHORISATION" : "95430162", # {$95430162 Payment Authorisation}
            "PAYMENT_AUTHORISATION_SIGNATURE" : "95430172", # {$95430172 Payment Authorisation Signature}
            "MEMBERSHIP_TERMS_AGREEMENT" : "94333311", # {$94333311 Membership Terms Agreement}
            "PT_6_SESSION_COMMITMENT" : "94333357", # {$94333357 PT 6 Session Commitment}
            "LIFESTYLE_PT_COMMITMENT" : "94352342", # {$94352342 Lifestyle PT Commitment}
            "ANNUAL_CONTRACT_COMMITMENT" : "94333358", # {$94333358 Annual Contract Commitment}
            "PARENT_GUARDIAN_SIGNATURE" : "94333524", # {$94333524 Parent/Guardian Signature}
            "SIGNATURE" : "94333525", # {$94333525 Signature}
            "MEDICAL" : "94332642", # {$94332642 Do any of the following apply to you?}
            "MEDICAL_CLEARANCE" : "94332643", # {$94332643 Do you have clearance from your Doctor to exerc...}
            "INJURIES" : "94332644",
            "TRAINING_AVAILABILITY" : "94335049", # {$94335049 Within the next 7 days, when are you available?}
            "TIME_AVAILABILITY" : "94335090", # {$94335090 At what times are you more likely to train?}
            "HAS_REFERRAL" : "94332645" , # {$94332645 Want an extra 2 weeks for free?}
            "REFERRAL_NAME" : "94332646" , # {$94332646 Referral Name 1}
            "REFERRAL_EMAIL" : "94332647" , # {$94332647 Referral Email 1}
            "REFERRAL_PHONE" : "94332648", # {$94332648 Referral Phone 1}            
            "HAS_REFERRAL_2" : "103920885" , # {$103920885 Is there anybody else?}
            "REFERRAL_NAME_2" : "103921451" , # {$103921451 Referral Name 2}
            "REFERRAL_EMAIL_2" : "103921454" , # {$103921454 Referral Email 2}
            "REFERRAL_PHONE_2" : "103921464", # {$103921464 Referral Phone 2}
            "HAS_REFERRAL_3" : "103921520" , # {$103921520 Is there anybody else?}
            "REFERRAL_NAME_3" : "103921554" , # {$103921554 Referral Name 3}
            "REFERRAL_EMAIL_3" : "103921576" , # {$103921576 Referral Email 3}
            "REFERRAL_PHONE_3" : "103921583", # {$103921583 Referral Phone 3}
            "ACCESS_KEY_NUMBER" : "94333803", # {$94333803 Access Key Number}
            "MEMBER_PHOTO" : "103257339", # {$103257339 Member Photo}
            "PRE_EX_ID" : "111058477", # {$111058477 pre_ex_id}

            "WILL_UPLOAD_COVID_VERIFICATION" : "116315320", # {$116315320 Upload Proof of COVID Vaccination or Medical Co...}
            "COVID_VERIFICATION_UPLOAD" : "116315321" # {$116315321 Verification Upload}
        },
        # FP COACH
        Constants.FS_FP_COACH_ENROLMENT_FORM_ID : {
            "IS_TEST" : "92120786" ,
             "SUBMISSION_DATETIME" : "91886402" ,
             "NAME" : "91275772", # { "first": "Clint", "last": "Test" }
             "PHONE" : "91275773" ,
             "EMAIL" : "91275774" ,
             "HOME_LOCATION" : "91440528" ,
             "COACHING_MODALITY" : "91561700",
             "SESSION_LENGTH" : "91709853",
             "TRAINING_PACKAGE_30_MIN" : "91275778" ,
             "TRAINING_PACKAGE_40_MIN" : "91709892" ,
             "TRAINING_PACKAGE_60_MIN" : "91709890" ,
             "TRAINING_PACKAGE_30_MIN_BK" : "91709612" ,
             "TRAINING_PACKAGE_40_MIN_BK" : "91709898" ,
             "TRAINING_PACKAGE_60_MIN_BK" : "91709896" ,
             "TRAINING_PACKAGE_LIFESTYLE" : "91566019" ,
             "TRAINING_PACKAGE_LIFESTYLE_BK" : "91709615" ,
             "TRAINING_PACKAGE_VIRTUAL_FITNESS" : "91566012" ,
             "TRAINING_PACKAGE_VIRTUAL_WELLNESS" : None ,
             "TRAINING_PACKAGE_WELLNESS" : "91709847" ,
            "TRAINING_PACKAGE_PROGRAMMING" : "91384120" ,
            "NUMBER_OF_SESSIONS_EXTERNAL" : "91710754" ,
             "PRICES_PER_SESSION_EXTERNAL" : "91561868" ,
             # New Fields - Not in use
             "IS_GYM_ACCESS_REQUIRED" : "104406804",
            "MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_BK" : "104406808",
            "MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_FP" : "104406810",
            "IS_ACCESS_CARD" : "104406829",
            # 
            "2_FREE" : "91935138",
             "COACHING_FORTNIGHTLY_DD" : "91275785" ,
             "MONTHLY_DD" : "91275786" ,
             "COACHING_START_DATE" : "91275784",
            "COACH_NAME_MK" : "91275775",
            "COACH_NAME_NT" : "91561372" ,
            "COACH_NAME_SH" : "91561371",
            "COACH_NAME_BK" : "91561370",
            "MEMBERSHIP_CONSULTANT_MK" : "91941001",
            "MEMBERSHIP_CONSULTANT_NT" : "91941002",
            "MEMBERSHIP_CONSULTANT_SH" : "91275809",
            "MEMBERSHIP_CONSULTANT_BK" : "91941000",
             "NOTES" : "91275810" ,
            "ARE_YOU_PAYING_FOR_THE_MEMBERSHIP" : "92061924",
            "BILLING_NAME" : "95667335",
            "BILLING_ADDRESS" : "95667336",
            "PAYMENT_METHOD" : "91711055",
            "EXISTING_PAYMENT_METHOD" : "99797180",
            "CREDIT_CARD" : "91880571", # { "card": "4111111111111111", "cardexp": "12/22", "cvv": "123" }
            "BSB" : "91711061",
            "ACCOUNT_NUMBER" : "91711062",
            "ACCOUNT_TYPE" : "91711063",
            "MEMBERSHIP_TERMS_AGREEMENT" : "91275805",
            "PT_6_SESSION_COMMITMENT" : "95667431",
            "LIFESTYLE_PT_COMMITMENT" : "95667430",
            "BILLING_TERMS_AGREEMENT" : "95667429",
            "SIGNATURE" : "91275807",
            "ORGANISED_BY_COACH" : "96024590"
        },
        # PIF - THIS NEEDS TO BE UPDATED
        Constants.FS_PIF_ENROLMENT_FORM_ID : {
            "IS_TEST" : "96052760", # {$96052760 is_test}
            "SUBMISSION_DATETIME" :  "96052758", # {$96052758 submission_datetime}
            "PRE_EX_ID" : "111959440", # {$111959440 pre_ex_id}
            "NAME" : "96047915", # { "first": "Clint", "last": "Test" } # {$96047915 Name}
            "PHONE" : "96047917", # {$96047917 Phone}
            "EMAIL" : "96047916", # {$96047916 Email}
            "HOME_LOCATION" : "96048514", # {$96048514 Membership Location}
            "OPTIONS" : "96048728", # [Membership, Coaching, Access Key] # {$96048728 Options}
            "MEMBERSHIP_TYPE" : "96049335", # Gym or Play # {$96049335 Membership Type}
            "MEMBERSHIP_NAME_GYM_NT" : "96048980", # {$96048980 Membership Name}
            "MEMBERSHIP_NAME_PLAY_NT" : "96049156", # {$96049156 Membership Name}
            "MEMBERSHIP_NAME_GYM_MK" : "101576172", # {$101576172 Membership Name}
            "MEMBERSHIP_NAME_PLAY_MK" : "101576175", # {$101576175 Membership Name}
            "MEMBERSHIP_NAME_PERFORM_MK" : None, # {$104407256 Membership Name}
            "MEMBERSHIP_NAME_GYM_SH" : "96048995", # {$96048995 Membership Name}
            "MEMBERSHIP_NAME_PLAY_SH" : "96049161", # {$96049161 Membership Name}
            "MEMBERSHIP_NAME_GYM_BK" : "96049023", # {$96049023 Membership Name}
            "MEMBERSHIP_NAME_PLAY_BK" : "96049163", # {$96049163 Membership Name}
            "CASUAL_VISITS_BK" : "96051883", # {$96051883 Casual Visit Options}
            "CASUAL_VISITS_FP" : "96051963", # {$96051963 Casual Visit Options}
            "IS_GYM_ACCESS_REQUIRED" : "96054641", # {$96054641 Is Gym Access required for the Coaching?}
            "MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_BK" : "96054688", # {$96054688 Gym Access for Coaching}
            "MEMBERSHIP_NAME_PT_EXTERNAL_GYM_ACCESS_FP" : "96054716", # {$96054716 Gym Access for Coaching}
            "IS_ACCESS_CARD" : "104216341", # {$104216341 Is an Access Card required?}
            "COACH_LOCATION" : "99101254", # {$99101254 Coach Location}
            "COACH_NAME_MK" : "96051260", # {$96051260 Coach Name MK}
            "COACH_NAME_NT" : "96051259", # {$96051259 Coach Name NT}
            "COACH_NAME_SH" : "96051258", # {$96051258 Coach Name SH}
            "COACH_NAME_BK" : "96051261", # {$96051261 Coach Name BK}
            "COACHING_MODALITY" : "96050523", # {$96050523 Coaching Modality}
            "NUMBER_OF_SESSIONS" : "96050145", # {$96050145 Number of Sessions}
            "SESSION_LENGTH_EXTERNAL" : "96053701", # {$96053701 Session Length}
            "PRICE_PER_SESSION_EXTERNAL" : "96049988", # {$96049988 Price Per Session}
            "SESSION_LENGTH" : "96049550", # {$96049550 Session Length}
            "PRICE_PER_SESSION" : "96050529", # {$96050529 Price Per Session}
            "FREE_PT_PACK" : "96051972", # {$96051972 FreePTPack}
            "2_FREE" : "96051971", # {$96051971 2Free}
            "FREE_TIME_SH" : "96052002", # {$96052002 Free Time SH}
            "FREE_TIME_NT" : "111843407", # {$111843407 Free Time NT}
            "FREE_TIME_MK" : "111843406", # {$111843406 Free Time MK}
            "FREE_TIME_BK" : "111843405", # {$111843405 Free Time BK}
            "ACCESS_KEY_DISCOUNT_SH" : "96051500", # {$96051500 Access Key Discount SH}
            "ACCESS_KEY_DISCOUNT_NT" : "111842889", # {$111842889 Access Key Discount NT}
            "ACCESS_KEY_DISCOUNT_MK" : "111842888", # {$111842888 Access Key Discount MK}
            "ACCESS_KEY_DISCOUNT_BK" : "111842887", # {$111842887 Access Key Discount BK}
            "ACCESS_KEY_RETAIL_PRICE" : "96051442", # {$96051442 AccessKeyPrice}
            "ACCESS_KEY_FEE" : "96048522", # {$96048522 Access Key}
            "COACHING_TOTAL" : "99101020", # {$99101020 Coaching Total}
            "ONE_OFF_TOTAL_PAYMENT" :  "96049086", # {$96049086 Total}
            "START_DATE" : "96050518", # {$96050518 Start Date}
            "FIRST_DEBIT_DATE" :  "96051983", # {$96051983 First Debit Date}
            "ARE_YOU_PAYING_FOR_THE_MEMBERSHIP" : "96047951", # {$96047951 Are you paying for the membership?}
            "BILLING_NAME" : "96047952", # {$96047952 Billing Name}
            "BILLING_ADDRESS" : "96047953", # {$96047953 Billing Address}
            "PAYMENT_METHOD" : "96047954",  # {$96047954 Payment Method}
            "CREDIT_CARD" : "96047955", # { "card": "4111111111111111", "cardexp": "12/22", "cvv": "123" } # {$96047955 Credit Card}
            "BSB" : "96047956", # {$96047956 BSB}
            "ACCOUNT_NUMBER" : "96047957", # {$96047957 Account Number}
            "ACCOUNT_TYPE" : "96047958", # {$96047958 Account Type}
            "PAYMENT_AUTHORISATION" : "96051207", # {$96051207 Payment Authorisation}
            "PAYMENT_AUTHORISATION_SIGNATURE" : "96051198", # {$96051198 Payment Authorisation Signature}
            "ACCESS_KEY_NUMBER" : "96047940", # {$96047940 Access Card Number}
            "MEMBERSHIP_CONSULTANT_LOCATION" : "96052159", # {$96052159 Membership Consultant Location}
            "MEMBERSHIP_CONSULTANT_MK" : "96052165", # {$96052165 Membership Consultant}
            "MEMBERSHIP_CONSULTANT_NT" : "96052164", # {$96052164 Membership Consultant}
            "MEMBERSHIP_CONSULTANT_SH" : "96052163", # {$96052163 Membership Consultant}
            "MEMBERSHIP_CONSULTANT_BK" : "96052166", # {$96052166 Membership Consultant}
            "TRAINGING_PACKAGE_SOLD_BY" : "96051256", # {$96051256 Training Package was Organised By:}
            "TRAINING_PACKAGE_CONSULTANT_LOCATION" : "99102151",
            "TRAINING_PACKAGE_CONSULTANT_SH" : "99101872", # {$99101872 Training Package Consultant}
            "TRAINING_PACKAGE_CONSULTANT_NT" : "99102124", # {$99102124 Training Package Consultant}
            "TRAINING_PACKAGE_CONSULTANT_MK" : "99102125", # {$99102125 Training Package Consultant}
            "TRAINING_PACKAGE_CONSULTANT_BK" : "99102128", # {$99102128 Training Package Consultant}
            "NOTES" : "96052158", # {$96052158 Notes}
            "RENEWAL_STATUS": '99433277', # This is for PT PIF - New Client or Renew Sessions # {$99433277 Client Status}
            "EXISTING_PAYMENT_METHOD" : "99436986", # {$99436986 Existing Payment Method}
            "MEMBER_PHOTO" : "103260146", # {$103260146 Member Photo}
            "MEMBERSHIP_TERMS_AGREEMENT" : "104212385", # {$104212385 Membership Terms Agreement}
            "SIGNATURE" : "104212395", # {$104212395 Signature}
            "MEDICAL" : "104212604", # {$104212604 Do any of the following apply to you?}
            "MEDICAL_CLEARANCE" : "104212635", # {$104212635 Do you have clearance from your Doctor to exerc...}
            "INJURIES" : "104212743", # {$104212743 Do you have or have you ever had any pain or ma...}
            "HAS_REFERRAL" : "103985398", # {$103985398 Do you want to want to train with a friend?}
            "REFERRAL_NAME" : "103985421", # {$103985421 Referral Name 1}
            "REFERRAL_EMAIL" : "103985422", # {$103985422 Referral Email 1}
            "REFERRAL_PHONE" : "103985423", # {$103985423 Referral Phone 1}
            "HAS_REFERRAL_2" : "103985518", # {$103985518 Is there anybody else?}
            "REFERRAL_NAME_2" : "103985524", # {$103985524 Referral Name 2}
            "REFERRAL_EMAIL_2" : "103985530" , # {$103985530 Referral Email 2}
            "REFERRAL_PHONE_2" : "103985527", # {$103985527 Referral Phone 2}
            "HAS_REFERRAL_3" : "103985531", # {$103985531 Is there anybody else?}
            "REFERRAL_NAME_3" : "103985548" , # {$103985548 Referral Name 3}
            "REFERRAL_EMAIL_3" : "103985559", # {$103985559 Referral Email 3}
            "REFERRAL_PHONE_3" : "103985556", # {$103985556 Referral Phone 3}
            "ACCESS_KEY_PAYMENT_OPTION" : "105333764", # {$105333764 Access Key Payment Option}
            "ACCESS_KEY_PAYMENT_METHOD" : "105333768", # {$105333768 Access Key Payment Method}
            "COMFORT_CANCEL" : "105333783", # {$105333783 Comfort Cancel}
            "COMMENCE_VIRTUAL_PLAGROUND_ON" : "105333795",
            "WILL_UPLOAD_COVID_VERIFICATION" : "116316745", # {$116316745 Upload Proof of COVID Vaccination or Medical Co...}
            "COVID_VERIFICATION_UPLOAD" : "116316748" # {$116316748 COVID Verification Upload}
        }
    }



