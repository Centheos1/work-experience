package com.fitnessplayground.util;

public final class Constants {

    private Constants() {}

    public static final String MBO_SESSION_TYPE_PT_30_MIN = "PT | 30 Minute";
    public static final String MBO_SESSION_TYPE_PT_40_MIN = "PT | 40 Minute";
    public static final String MBO_SESSION_TYPE_PT_60_MIN = "PT | 60 Minute";
    public static final String MBO_SESSION_TYPE_PT_EXTERNAL_40_MIN = "PT External | 40 Minute";
    public static final String MBO_SESSION_TYPE_PT_EXTERNAL_60_MIN = "PT External | 60 Minute";
    public static final String[] MBO_PT_SESSIONS_TYPES = new String[] {
            MBO_SESSION_TYPE_PT_30_MIN,
            MBO_SESSION_TYPE_PT_40_MIN,
            MBO_SESSION_TYPE_PT_60_MIN,
            MBO_SESSION_TYPE_PT_EXTERNAL_40_MIN,
            MBO_SESSION_TYPE_PT_EXTERNAL_60_MIN
    };

    public static final String FEEDBACK_TYPE_PT_EARLY_FEEDBACK = "ptEarlyFeedback";
    public static final String FEEDBACK_TYPE_PT_FEEDBACK = "ptFeedback";

    public static final String PERMISSION_LEVEL_GOD = "God";
    public static final String PERMISSION_LEVEL_MANAGER = "Manager";
    public static final String PERMISSION_LEVEL_ADMINISTRATOR = "Administrator";
    public static final String PERMISSION_LEVEL_MEMBERSHIP_CONSULTANT = "Membership Consultant";
    public static final String PERMISSION_LEVEL_PERSONAL_TRAINER = "Personal Trainer";
    public static final String PERMISSION_LEVEL_LOCATION = "Location";
    public static final String PERMISSION_LEVEL_TESTER = "Tester";
    public static final String PERMISSION_LEVEL_PEASANT = "Peasant";

    public static final String ROLE_PERSONAL_TRAINER = "Personal Trainer";
    public static final String ROLE_MEMBERSHIP_CONSULTANT = "Membership Consultant";

    public static final String MBO_ID_AND_SITE_IS_SPLIT_CHARACTER = "::";

//    Automatic Kick off Trigger
    public static final String ACTIVE_CAMPAIGN_TAG_NEW_ENROLMENT = "NewEnrolment";

//    AC Tags
    public static final String ACTIVE_CAMPAIGN_TAG_MALE = "Male";
    public static final String ACTIVE_CAMPAIGN_TAG_FEMALE = "Female";
    public static final String ACTIVE_CAMPAIGN_TAG_BUNKER = "Bunker";
    public static final String ACTIVE_CAMPAIGN_TAG_GATEWAY = "Gateway;";
    public static final String ACTIVE_CAMPAIGN_TAG_MARRICKVILLE = "Marrickville";
    public static final String ACTIVE_CAMPAIGN_TAG_NEWTOWN = "Newtown";
    public static final String ACTIVE_CAMPAIGN_TAG_SURRY_HILLS = "SurryHills";
    public static final String ACTIVE_CAMPAIGN_TAG_PT_ONGOING = "PTOngoing";
    public static final String ACTIVE_CAMPAIGN_TAG_PT_PACK = "PTPack";
    public static final String ACTIVE_CAMPAIGN_TAG_PT_COMP_SESSION = "PTCompSession";
    public static final String ACTIVE_CAMPAIGN_TAG_PT_NO_COMP_SESSION = "PTNoCompSession";
    public static final String ACTIVE_CAMPAIGN_TAG_MEMBERSHIP_GYM = "Gym";
    public static final String ACTIVE_CAMPAIGN_TAG_MEMBERSHIP_FIT = "Fit";
    public static final String ACTIVE_CAMPAIGN_TAG_MEMBERSHIP_PLAY = "Play";
    public static final String ACTIVE_CAMPAIGN_TAG_COUPON_PTP = "CouponPTP";
    public static final String ACTIVE_CAMPAIGN_TAG_CORPORATE = "Corporate";
    public static final String ACTIVE_CAMPAIGN_TAG_PTM_ASSIGNED = "PTM Assigned";
    public static final String ACTIVE_CAMPAIGN_TAG_REASSIGNED_PT = "Re-assignedPT";
    public static final String ACTIVE_CAMPAIGN_TAG_EP_REFERRAL = "EP_Referral";
    public static final String ACTIVE_CAMPAIGN_TAG_FP_COACH_ENROLMENT = "FpCoachEnrolment";
    public static final String ACTIVE_CAMPAIGN_TAG_EXTERNAL_PT = "fpCoachExternal";
    public static final String ACTIVE_CAMPAIGN_TAG_REFERRAL_JOINED = "referral_joined";
    public static final String ACTIVE_CAMPAIGN_TAG_OFFER_REFER_A_FRIEND = "offer_refer_a_friend";

//    General Details
    public static final String ACTIVE_CAMPAIGN_ACID = "ACID";
    public static final String ACTIVE_CAMPAIGN_NPS = "NPS";
    public static final String ACTIVE_CAMPAIGN_PTM_FIRST_NAME = "PTM_FIRST_NAME";
    public static final String ACTIVE_CAMPAIGN_PTM_LAST_NAME = "PTM_LAST_NAME";
    public static final String ACTIVE_CAMPAIGN_PTM_NAME = "PTMFULLNAME";
    public static final String ACTIVE_CAMPAIGN_PTM_EMAIL = "PTMEMAIL";
    public static final String ACTIVE_CAMPAIGN_GFM_FIRST_NAME = "GFM_FIRST_NAME";
    public static final String ACTIVE_CAMPAIGN_GFM_LAST_NAME = "GFM_LAST_NAME";
    public static final String ACTIVE_CAMPAIGN_GFM_NAME = "GFM_FULL_NAME";
    public static final String ACTIVE_CAMPAIGN_GFM_EMAIL = "GFM_EMAIL";
    public static final String ACTIVE_CAMPAIGN_FIELD_GENDER = "GENDER";
    public static final String ACTIVE_CAMPAIGN_CM_FULL_NAME = "CM_FULL_NAME";
    public static final String ACTIVE_CAMPAIGN_CM_FIRST_NAME = "CM_FIRST_NAME";
    public static final String ACTIVE_CAMPAIGN_CM_LAST_NAME = "CM_LAST_NAME";
    public static final String ACTIVE_CAMPAIGN_CM_EMAIL = "CM_EMAIL";
    public static final String ACTIVE_CAMPAIGN_FIELD_QUERY_PARAMS = "QUERYPARAMS";

//    Pre-Ex
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_CURRENTLY_EXERCISING = "CURRENTLY_EXERCISING";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_WHAT_DO_YOU_ENJOY = "WHAT_DO_YOU_ENJOY";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_AREAS_TO_IMPROVE_FRONT = "AREAS_TO_IMPROVE_FRONT";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_AREAS_TO_IMPROVE_BACK = "AREAS_TO_IMPROVE_BACK";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_GOALS_BODY = "GOALS_BODY";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_GOALS_HEALTH = "GOALS_HEALTH";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_GOALS_FITNESS = "GOALS_FITNESS";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_SPECIFIC_GOAL_IN_12_WEEKS = "SPECIFIC_GOAL_IN_12_WEEKS";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_REACHED_THESE_GOALS_BEFORE = "REACHED_THESE_GOALS_BEFORE";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_WHAT_PREVENTED_YOU = "WHAT_PREVENTED_YOU";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_SCALE_1_10_IMPORTANCE_TO_ACHIEVE_GOAL = "SCALE_1_10_IMPORTANCE_TO_ACHIEVE_GOAL";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_FREQUENCY_TO_TRAIN_PER_W = "FREQUENCY_TO_TRAIN_PER_W";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_FOLLOW_TRAINING_PLAN = "FOLLOW_TRAINING_PLAN";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_HAVE_AN_EATING_PLAN = "HAVE_AN_EATING_PLAN";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_HOW_EFFECTIVE_WAS_PREVIOUS_PROGRAM = "HOW_EFFECTIVE_WAS_PREVIOUS_PROGRAM";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_WHAT_ELSE_CAN_WE_DO_TO_HELP = "WHAT_ELSE_CAN_WE_DO_TO_HELP";
    public static final String ACTIVE_CAMPAIGN_FIELD_PRE_EX_TAKING_ANY_MEDICATION = "TAKING_ANY_MEDICATION";

//    Enrolment
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_DATE_OF_BIRTH = "DATE_OF_BIRTH";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_TYPE = "MEMBERSHIP_TYPE";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_FIRST_NAME = "MC_FIRST_NAME";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_LAST_NAME = "MC_LAST_NAME";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_NAME = "MCFULLNAME";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_EMAIL = "MCEMAIL";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_AGREEMENT_START_DATE = "AGREEMENT_START_DATE";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_FIRST_DEBIT_DATE = "FIRST_DEBIT_DATE";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_COMMITMENT_TERM = "COMMITMENT_TERM";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_PT_FORTNIGHTLY_DIRECT_DEBIT = "PT_FORTNIGHTLY_DIRECT_DEBIT";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_TOTAL_FORTNIGHTLY_DIRECT_DEBIT = "TOTAL_FORTNIGHTLY_DIRECT_DEBIT";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_ONE_OFF_PAYMENT_SUMMARY = "ONE_OFF_PAYMENT_SUMMARY";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_ACCESS_KEY_AMOUNT = "ACCESS_KEY";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_TODAYS_ONE_OFF_TOTAL = "TODAYS_ONE_OFF_TOTAL";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_ONE_OFF_PAYMENT_OPTION = "ONE_OFF_PAYMENT_OPTION";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_YOUR_HEALTH_ANY_MEDICAL_ISSUES = "YOUR_HEALTH_ANY_MEDICAL_ISSUES";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_GYM_LOCATION = "GYM_LOCATION";
    public static final String ACTIVE_CAMPAIGN_FIELD_ENROLMENT_NOTES = "NOTES";

//    PT
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_FIRST_NAME = "PERSONAL_TRAINER_FIRST_NAME";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_LAST_NAME = "PERSONAL_TRAINER_LAST_NAME";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_NAME = "PERSONALTRAINERNAME";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_EMAIL = "PERSONALTRAINEREMAIL";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_PHONE = "PERSONAL_TRAINER_PHONE";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_TRAINING_STARTER_PACK = "TRAINING_STARTER_PACK";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINING_START_DATE = "PT_START_DATE";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINING_FIRST_DEBIT_DATE = "PT_FIRST_DEBIT_DATE";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_SESSIONS_PER_WEEK = "PT_SESSIONS_PER_WEEK";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_AVAILABILITY_DAY_OF_WEEK = "AVAILABILITY_DAY_OF_WEEK";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_AVAILABILITY_TIME_OF_DAY = "AVAILABILITY_TIME_OF_DAY";
    public static final String ACTIVE_CAMPAIGN_FIELD_COACH_START_DATE = "COACH_START_DATE";
    public static final String ACTIVE_CAMPAIGN_FIELD_COACH_FIRST_BILLING_DATE = "COACH_FIRST_BILLING_DATE";


//    PT ParQ
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_WHAT_ARE_YOUR_EATING_HABITS = "WHAT_ARE_YOUR_EATING_HABITS";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_PHYSICALLY_ACTIVE_AT_WORK = "PHYSICALLY_ACTIVE_AT_WORK";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_HOW_DO_YOU_RATE_YOUR_ENERGY = "HOW_DO_YOU_RATE_YOUR_ENERGY";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_SLEEP_AT_NIGHT = "SLEEP_AT_NIGHT";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_STRESS_LEVELS = "STRESS_LEVELS";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_DESTRESS = "DESTRESS";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_STORE_MAJORITY_BF = "STORE_MAJORITY_BF";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_MAJOR_INJURIES_AND_PAINS = "MAJOR_INJURIES_AND_PAINS";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_OTHER_INJURIES = "OTHER_INJURIES";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_WORK_WITH_PT_BEFORE = "WORKED_WITH_PT_BEFORE";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PARQ_FURTHER_DETAIL_ABOUT_TRAINING = "FURTHER_DETAIL_ABOUT_TRAINING";
    public static final String ACTIVE_CAMPAIGN_FIELD_PT_PAR_Q_URL = "PARQURL";

//    Referrals
    public static final String ACTIVE_CAMPAIGN_FIELD_REFERRALS_REFERRED_BY_NAME = "REFERRED_BY_NAME";
    public static final String ACTIVE_CAMPAIGN_FIELD_REFERRALS_REFERRAL_NAME = "REFERRAL_NAME";


    public static final String ENROLMENT_DATA_MALE = "male";
    public static final String ENROLMENT_DATA_FEMALE = "female";
    public static final String ENROLMENT_DATA_GYM = "gym";
    public static final String ENROLMENT_DATA_FIT = "fit";
    public static final String ENROLMENT_DATA_PLAY = "play";
    public static final String ENROLMENT_DATA_COUPON_CORPORATE = "corp";
    public static final String ENROLMENT_DATA_COUPON_PTP = "ptp";
    public static final String ENROLMENT_DATA_COUPON_2_FREE_PT = "twofreept";
    public static final String ENROLMENT_DATA_COUPON_X_DAY_FREE_SPECIAL = "xdaysfree";
    public static final String ENROLMENT_DATA_NO_COMP_SESSION = "No Comp Session";
    public static final String ENROLMENT_DATA_ONGOING_PT = "ongoingPersonalTraining";

    public static final String COUPON_CODE_PTP = "FreePTPack";

    public static final String SUBMISSION_PAYMENT_TYPE_CREDIT_CARD = "creditCard";
    public static final String SUBMISSION_PAYMENT_TYPE_CREDIT_CARD_2 = "Credit Card";
    public static final String SUBMISSION_PAYMENT_TYPE_BANK_ACCOUNT = "bankAccount";
    public static final String SUBMISSION_PAYMENT_TYPE_BANK_ACCOUNT_2 = "Bank Account";
    public static final String SUBMISSION_PAYMENT_TYPE_DIRECT_DEBIT = "Direct Debit";
    public static final String SUBMISSION_BACK_ACCOUNT_TYPE_SAVINGS = "savings";
    public static final String SUBMISSION_BANK_ACCOUNT_TYPE_CHECK = "check";
    public static final String MBO_DIRECT_DEBIT_INFO_ACCOUNT_TYPE_SAVINGS = "Savings";
    public static final String MBO_DIRECT_DEBIT_INFO_ACCOUNT_TYPE_CHECK = "Checking";

    public static final String ACCESS_KEY_PAYMENT_OPTION_PAY_TODAY = "payToday";
    public static final String ACCESS_KEY_PAYMENT_OPTION_ADD_TO_DD = "addToFirstDD";

    public static final String ENTITY_TYPE_ENROLMENT_DATA = "enrolmentData";

    public static final int SURRY_HILLS_LOCATION_ID = 1;
    public static final int NEWTOWN_LOCATION_ID = 2;
    public static final int MARRICKVILLE_LOCATION_ID = 3;
    public static final int GATEWAY_LOCATION_ID = 4;
    public static final int BUNKER_LOCATION_ID = 5;
    public static final int ZETLAND_LOCATION_ID = 7;

    public static final int[] LOCATION_IDS = { SURRY_HILLS_LOCATION_ID, NEWTOWN_LOCATION_ID, MARRICKVILLE_LOCATION_ID, GATEWAY_LOCATION_ID, BUNKER_LOCATION_ID };

    public static final String SURRY_HILLS_GYM_NAME = "Surry Hills";
    public static final String NEWTOWN_GYM_NAME = "Newtown";
    public static final String MARRICKVILLE_GYM_NAME = "Marrickville";
    public static final String GATEWAY_GYM_NAME = "Gateway";
    public static final String BUNKER_GYM_NAME = "Bunker";
    public static final String BUNKER_THE_GYM_NAME = "The Bunker";
    public static final String ZETLAND_GYM_NAME = "Zetland";

    public static final long SYDNEY_SITE_ID = 152065;
    public static final long DARWIN_SITE_ID = 936387;

    public static final String MBO_METADATA_TYPE_PRODUCT = "Product";
    public static final String MBO_METADATA_TYPE_CASH = "Cash";

    public static final String MBO_STATUS_ACTIVE = "Active";
    public static final String MBO_STATUS_DECLINED = "Declined";
    public static final String MBO_STATUS_NON_MEMEBER = "Non-Member";
    public static final String MBO_STATUS_TERMINATED = "Terminated";
    public static final String MBO_STATUS_SUSPENDED = "Suspended";
    public static final String MBO_STATUS_EXPIRED = "Expired";

    public static final String MBO_PERMISSION_ADMINISTRATION = "Administration";
    public static final String MBO_PERMISSION_COOWNER = "Coowner";
    public static final String MBO_PERMISSION_MANAGER = "Manager";
    public static final String MBO_PERMISSION_GROUP_TRAINERS = "Group Trainers";
    public static final String MBO_PERMISSION_HEAD_OFFICE = "Head Office";
    public static final String MBO_PERMISSION_PERSONAL_TRAINERS = "Personal Trainers";
    public static final String MBO_PERMISSION_EXERCISE_PHYSIOLOGIST = "Exercise Pyhsiologist";
    public static final String MBO_PERMISSION_SALES = "Sales";
    public static final String MBO_PERMISSION_LOCATION = "Location";
    public static final String MBO_PERMISSION_TESTER = "Tester";


    public static final String EXTERNAL_PT = "externalPersonalTraining";
    public static final String LIFESTYLE_PT = "lifestylePersonalTraining";

    public static final String STARTER_PACK_PT_ONGO = "ongoingPersonalTraining";
    public static final String STARTER_PACK_PT_PACK = "ptPack";
    public static final String STARTER_PACK_TRANSFORMER = "transformer";
    public static final String STARTER_PACK_GURU = "fitnessGuru";
    public static final String STARTER_PACK_EP_REFERRAL = "epReferral";
    public static final String STARTER_PACK_FACE_TO_FACE = "Face to Face";
    public static final String STARTER_PACK_LIFESTYLE_PT = "Lifestyle Personal Training";
    public static final String STARTER_PACK_EXTERNAL_PT = "External Personal Training";

    public static final String FIRST_N_DAYS_MBO_PROMO_CODE = "NDaysFree";

    public static final String FIRST_7_DAYS_ID = "10236";
    public static final String FIRST_14_DAYS_ID = "10376";
    public static final String FIRST_30_DAYS_ID = "10224";


    public static final String GW_FIRST_7_DAYS_ID = "10236";
    public static final String GW_FIRST_14_DAYS_ID = "10289";
    public static final String GW_FIRST_30_DAYS_ID = "10224";

    public static final String COUPON_CODE_7_DAYS = "7DaysFree";
    public static final String COUPON_CODE_14_DAYS = "14DaysFree";
    public static final String COUPON_CODE_21_DAYS = "21DaysFree";
    public static final String COUPON_CODE_30_DAYS = "30DaysFree";
    public static final String COUPON_CODE_X_DAYS = "XDaysFree";

    public static final String COUPON_CODE_21_DAYS_FIRST_BILLING_DATE = "2021-01-22";

    public static final String GW_ACCESS_KEY_PRODUCT_CODE = "9471375183";
    public static final String ACCESS_KEY_PRODUCT_CODE = "9471375183";

    public static final String COUPON_CODE_KEY_0 = "KeyFree";
    public static final String COUPON_CODE_KEY_21 = "Key21";
    public static final String COUPON_CODE_KEY_30 = "Key30";
    public static final String COUPON_CODE_KEY_49 = "Key49";
    public static final String COUPON_CODE_KEY_79 = "Key79";
    public static final String COUPON_CODE_KEY_89 = "Key89";



    public static final String ACCESS_KEY = "accessKey";
//    public static final String BUNKER_ACCESS_KEY = "bunker_accessKey";

    public static final String CRECHE_1_CHILD = "creche_1_Child";
    public static final String CRECHE_N_CHILD = "creche_N_Child";
    
    public static final String STARTERPACK_PTPACK = "starterPack_ptPack";
    public static final String STARTERPACK_TRANSFORMER = "starterPack_transformer";
    public static final String STARTERPACK_EP_REFERRAL = "starterPack_epReferral";
    public static final String BUNKER_STARTERPACK_PTPACK = "bunker_starterPack_ptPack";
    
    public static final String PGT_5PACK = "5PackPif";
    public static final String PGT_10PACK = "10PackPif";
    public static final String BUNKER_PGT_5PACK = "bunker_5PackPif";
    public static final String BUNKER_PGT_10PACK = "bunker_10PackPif";

    public static final String FP_GYM = "fitnessPlayground_gym";
    public static final String FP_PLAY = "fitnessPlayground_play";

    public static final String FP_GYM_NOCOMMIT = "fitnessPlayground_gym_noCommitment";
    public static final String FP_PLAY_NOCOMMIT = "fitnessPlayground_play_noCommitment";

    public static final String CORPORATE_FP_GYM = "corporate_fitnessPlayground_gym";
    public static final String CORPORATE_FP_PLAY = "corporate_fitnessPlayground_play";

    public static final String CORPORATE_FP_GYM_NOCOMMIT = "corporate_fitnessPlayground_gym_noCommitment";
    public static final String CORPORATE_FP_PLAY_NOCOMMIT = "corporate_fitnessPlayground_play_noCommitment";

    public static final String FP_GYM_1MTH = "fitnessPlayground_gym_1Month";
    public static final String FP_GYM_2MTH = "fitnessPlayground_gym_2Month";
    public static final String FP_GYM_3MTH = "fitnessPlayground_gym_3Month";
    public static final String FP_GYM_6MTH = "fitnessPlayground_gym_6Month";
    public static final String FP_GYM_12MTH = "fitnessPlayground_gym_12Month";

    public static final String FP_PLAY_1MTH = "fitnessPlayground_play_1Month";
    public static final String FP_PLAY_2MTH = "fitnessPlayground_play_2Month";
    public static final String FP_PLAY_3MTH = "fitnessPlayground_play_3Month";
    public static final String FP_PLAY_6MTH = "fitnessPlayground_play_6Month";
    public static final String FP_PLAY_12MTH = "fitnessPlayground_play_12Month";

    public static final String FP_NEWTOWN_GYM = "newtown_gym";
    public static final String FP_NEWTOWN_GYM_NOCOMMIT = "newtown_gym_noCommitment";

//    ________________________________________________________________
    public static final String FP_SURRY_HILLS_GYM = "surryHills_gym";
    public static final String FP_SURRY_HILLS_PLAY = "surryHills_play";

    public static final String FP_SURRY_HILLS_GYM_NOCOMMIT = "surryHills_gym_noCommitment";
    public static final String FP_SURRY_HILLS_PLAY_NOCOMMIT = "surryHills_play_noCommitment";

    public static final String CORPORATE_FP_SURRY_HILLS_GYM = "corporate_surryHills_gym";
    public static final String CORPORATE_FP_SURRY_HILLS_PLAY = "corporate_surryHills_play";

    public static final String CORPORATE_FP_SURRY_HILLS_GYM_NOCOMMIT = "corporate_surryHills_gym_noCommitment";
    public static final String CORPORATE_FP_SURRY_HILLS_PLAY_NOCOMMIT = "corporate_surryHills_play_noCommitment";

    public static final String FP_SURRY_HILLS_GYM_1MTH = "surryHills_gym_1Month";
    public static final String FP_SURRY_HILLS_GYM_2MTH = "surryHills_gym_2Month";
    public static final String FP_SURRY_HILLS_GYM_3MTH = "surryHills_gym_3Month";
    public static final String FP_SURRY_HILLS_GYM_6MTH = "surryHills_gym_6Month";
    public static final String FP_SURRY_HILLS_GYM_12MTH = "surryHills_gym_12Month";

    public static final String FP_NEWTOWN_GYM_1MTH = "newtown_gym_1Month";
    public static final String FP_NEWTOWN_GYM_2MTH = "newtown_gym_2Month";
    public static final String FP_NEWTOWN_GYM_3MTH = "newtown_gym_3Month";
    public static final String FP_NEWTOWN_GYM_6MTH = "newtown_gym_6Month";
    public static final String FP_NEWTOWN_GYM_12MTH = "newtown_gym_12Month";

    public static final String FP_SURRY_HILLS_PLAY_1MTH = "surryHills_play_1Month";
    public static final String FP_SURRY_HILLS_PLAY_2MTH = "surryHills_play_2Month";
    public static final String FP_SURRY_HILLS_PLAY_3MTH = "surryHills_play_3Month";
    public static final String FP_SURRY_HILLS_PLAY_6MTH = "surryHills_play_6Month";
    public static final String FP_SURRY_HILLS_PLAY_12MTH = "surryHills_play_12Month";
//    ________________________________________________________________

    public static final String BUNKER_GYM = "bunker_gym";
    public static final String BUNKER_PLAY = "bunker_play";

    public static final String BUNKER_GYM_NOCOMMIT = "bunker_gym_noCommitment";
    public static final String BUNKER_PLAY_NOCOMMIT = "bunker_play_noCommitment";

    public static final String CORPORATE_BUNKER_GYM = "corporate_bunker_gym";
    public static final String CORPORATE_BUNKER_PLAY = "corporate_bunker_play";

    public static final String CORPORATE_BUNKER_GYM_NOCOMMIT = "corporate_bunker_gym_noCommitment";
    public static final String CORPORATE_BUNKER_PLAY_NOCOMMIT = "corporate_bunker_play_noCommitment";

    public static final String BUNKER_GYM_1MTH = "bunker_gym_1Month";
    public static final String BUNKER_GYM_2MTH = "bunker_gym_2Month";
    public static final String BUNKER_GYM_3MTH = "bunker_gym_3Month";
    public static final String BUNKER_GYM_6MTH = "bunker_gym_6Month";
    public static final String BUNKER_GYM_12MTH = "bunker_gym_12Month";

    public static final String BUNKER_PLAY_1MTH = "bunker_play_1Month";
    public static final String BUNKER_PLAY_2MTH = "bunker_play_2Month";
    public static final String BUNKER_PLAY_3MTH = "bunker_play_3Month";
    public static final String BUNKER_PLAY_6MTH = "bunker_play_6Month";
    public static final String BUNKER_PLAY_12MTH = "bunker_play_12Month";


    public static final String FP_PT_30_1FN = "fitnessPlayground_pt_30_1fn";
    public static final String FP_PT_30_1WK = "fitnessPlayground_pt_30_1wk";
    public static final String FP_PT_30_2WK = "fitnessPlayground_pt_30_2wk";
    public static final String FP_PT_30_3WK = "fitnessPlayground_pt_30_3wk";
    public static final String FP_PT_30_4WK = "fitnessPlayground_pt_30_4wk";

    public static final String FP_PT_40_1FN = "fitnessPlayground_pt_40_1fn";
    public static final String FP_PT_40_1WK = "fitnessPlayground_pt_40_1wk";
    public static final String FP_PT_40_2WK = "fitnessPlayground_pt_40_2wk";
    public static final String FP_PT_40_3WK = "fitnessPlayground_pt_40_3wk";
    public static final String FP_PT_40_4WK = "fitnessPlayground_pt_40_4wk";

    public static final String FP_PT_60_1FN = "fitnessPlayground_pt_60_1fn";
    public static final String FP_PT_60_1WK = "fitnessPlayground_pt_60_1wk";
    public static final String FP_PT_60_2WK = "fitnessPlayground_pt_60_2wk";
    public static final String FP_PT_60_3WK = "fitnessPlayground_pt_60_3wk";
    public static final String FP_PT_60_4WK = "fitnessPlayground_pt_60_4wk";


    public static final String BUNKER_PT_30_1FN = "bunker_pt_30_1fn";
    public static final String BUNKER_PT_30_1WK = "bunker_pt_30_1wk";
    public static final String BUNKER_PT_30_2WK = "bunker_pt_30_2wk";
    public static final String BUNKER_PT_30_3WK = "bunker_pt_30_3wk";
    public static final String BUNKER_PT_30_4WK = "bunker_pt_30_4wk";

    public static final String BUNKER_PT_40_1FN = "bunker_pt_40_1fn";
    public static final String BUNKER_PT_40_1WK = "bunker_pt_40_1wk";
    public static final String BUNKER_PT_40_2WK = "bunker_pt_40_2wk";
    public static final String BUNKER_PT_40_3WK = "bunker_pt_40_3wk";
    public static final String BUNKER_PT_40_4WK = "bunker_pt_40_4wk";

    public static final String BUNKER_PT_60_1FN = "bunker_pt_60_1fn";
    public static final String BUNKER_PT_60_1WK = "bunker_pt_60_1wk";
    public static final String BUNKER_PT_60_2WK = "bunker_pt_60_2wk";
    public static final String BUNKER_PT_60_3WK = "bunker_pt_60_3wk";
    public static final String BUNKER_PT_60_4WK = "bunker_pt_60_4wk";

    public static final String FP_LIFESTYLE_PT_40_1FN = "fitnessPlayground_lifestyle_pt_40_1fn";
    public static final String FP_LIFESTYLE_PT_40_1WK = "fitnessPlayground_lifestyle_pt_40_1wk";
    public static final String FP_LIFESTYLE_PT_40_2WK = "fitnessPlayground_lifestyle_pt_40_2wk";
    public static final String FP_LIFESTYLE_PT_40_3WK = "fitnessPlayground_lifestyle_pt_40_3wk";
    public static final String FP_LIFESTYLE_PT_40_4WK = "fitnessPlayground_lifestyle_pt_40_4wk";

    public static final String FP_EXTERNAL_PT_40_1FN = "fitnessPlayground_external_pt_40_1fn";
    public static final String FP_EXTERNAL_PT_40_1WK = "fitnessPlayground_external_pt_40_1wk";
    public static final String FP_EXTERNAL_PT_40_2WK = "fitnessPlayground_external_pt_40_2wk";
    public static final String FP_EXTERNAL_PT_40_3WK = "fitnessPlayground_external_pt_40_3wk";
    public static final String FP_EXTERNAL_PT_40_4WK = "fitnessPlayground_external_pt_40_4wk";

    public static final String FP_EXTERNAL_PT_60_1FN = "fitnessPlayground_external_pt_60_1fn";
    public static final String FP_EXTERNAL_PT_60_1WK = "fitnessPlayground_external_pt_60_1wk";
    public static final String FP_EXTERNAL_PT_60_2WK = "fitnessPlayground_external_pt_60_2wk";
    public static final String FP_EXTERNAL_PT_60_3WK = "fitnessPlayground_external_pt_60_3wk";
    public static final String FP_EXTERNAL_PT_60_4WK = "fitnessPlayground_external_pt_60_4wk";

    public static final String BUNKER_LIFESTYLE_PT_40_1FN = "bunker_lifestyle_pt_40_1fn";
    public static final String BUNKER_LIFESTYLE_PT_40_1WK = "bunker_lifestyle_pt_40_1wk";
    public static final String BUNKER_LIFESTYLE_PT_40_2WK = "bunker_lifestyle_pt_40_2wk";
    public static final String BUNKER_LIFESTYLE_PT_40_3WK = "bunker_lifestyle_pt_40_3wk";
    public static final String BUNKER_LIFESTYLE_PT_40_4WK = "bunker_lifestyle_pt_40_4wk";

    public static final String VIRTUAL_PLAYGROUND = "virtual_playground";

    public static final String PT_NUMBER_SESSIONS_PER_WEEK_1FN = "1fn";
    public static final String PT_NUMBER_SESSIONS_PER_WEEK_1WK = "1wk";
    public static final String PT_NUMBER_SESSIONS_PER_WEEK_2WK = "2wk";
    public static final String PT_NUMBER_SESSIONS_PER_WEEK_3WK = "3wk";
    public static final String PT_NUMBER_SESSIONS_PER_WEEK_4WK = "4wk";

    public static final int ACCESS_KEY_ID = 120;
    public static final int GW_ACCESS_KEY_ID = 120;
//    public static final int BUNKER_ACCESS_KEY_ID = 120;

    public static final int GW_STARTERPACK_PTPACK_ID = 127;
    public static final int GW_STARTERPACK_TRANSFORMER_ID = 118;
//
//    public static final int GW_PGT_5PACK_ID = ;
//    public static final int GW_PGT_10PACK_ID = ;

    public static final int COUPON_CODE_7_DAYS_ID = 304;
    public static final int COUPON_CODE_14_DAYS_ID = 305;
    public static final int COUPON_CODE_21_DAYS_ID = 383;
    public static final int COUPON_CODE_30_DAYS_ID = 306;

    public static final int GW_COUPON_CODE_7_DAYS_ID = -304;
    public static final int GW_COUPON_CODE_14_DAYS_ID = -305;
    public static final int GW_COUPON_CODE_30_DAYS_ID = -306;

    public static final int GW_FP_GYM_ID = 187;
    public static final int GW_FP_PLAY_ID = 170;

    public static final int GW_FP_GYM_NOCOMMIT_ID = 188;
    public static final int GW_FP_PLAY_NOCOMMIT_ID = 172;

//    public static final int GW_CORPORATE_FP_GYM_ID = ;
//    public static final int GW_CORPORATE_FP_PLAY_ID = ;

    public static final int GW_CORPORATE_FP_GYM_NOCOMMIT_ID = 139;
//    public static final int GW_CORPORATE_FP_PLAY_NOCOMMIT_ID = ;

    public static final int GW_FP_GYM_1MTH_ID = 109;
    public static final int GW_FP_GYM_2MTH_ID = 108;
    public static final int GW_FP_GYM_3MTH_ID = 107;
    public static final int GW_FP_GYM_6MTH_ID = 163;
    public static final int GW_FP_GYM_12MTH_ID = 106;

    public static final int GW_FP_PLAY_1MTH_ID = 153;
//    public static final int GW_FP_PLAY_2MTH_ID = ;
    public static final int GW_FP_PLAY_3MTH_ID = 154;
    public static final int GW_FP_PLAY_6MTH_ID = 158;
    public static final int GW_FP_PLAY_12MTH_ID = 155;

    public static final int FP_SURRY_HILLS_GYM_ID = 307;
    public static final int FP_SURRY_HILLS_GYM_NOCOMMIT_ID = 308;

    public static final int FP_SURRY_HILLS_PLAY_ID = 317;
    public static final int FP_SURRY_HILLS_PLAY_NOCOMMIT_ID = 318;

    public static final int FP_SURRY_HILLS_GYM_1MTH_ID = 312;
//    public static final int FP_SURRY_HILLS_GYM_2MTH_ID = ;
    public static final int FP_SURRY_HILLS_GYM_3MTH_ID = 309;
    public static final int FP_SURRY_HILLS_GYM_6MTH_ID = 310;
    public static final int FP_SURRY_HILLS_GYM_12MTH_ID = 311;

    public static final int FP_SURRY_HILLS_PLAY_1MTH_ID = 316;
    //    public static final int FP_PLAY_2MTH_ID = ;
    public static final int FP_SURRY_HILLS_PLAY_3MTH_ID = 315;
    public static final int FP_SURRY_HILLS_PLAY_6MTH_ID = 314;
    public static final int FP_SURRY_HILLS_PLAY_12MTH_ID = 313;

    public static final int CORPORATE_FP_SURRY_HILLS_GYM_ID = 322;
    public static final int CORPORATE_FP_SURRY_HILLS_PLAY_ID = 320;

    public static final int CORPORATE_FP_SURRY_HILLS_GYM_NOCOMMIT_ID = 321;
    public static final int CORPORATE_FP_SURRY_HILLS_PLAY_NOCOMMIT_ID = 319;

    public static final int STARTERPACK_PTPACK_ID = 127;
    public static final int STARTERPACK_TRANSFORMER_ID = 118;
    public static final int BUNKER_STARTERPACK_PTPACK_ID = 301;

    public static final int PGT_5PACK_ID = 248;
    public static final int PGT_10PACK_ID = 247;
    public static final int BUNKER_PGT_5PACK_ID = 377;
    public static final int BUNKER_PGT_10PACK_ID = 376;

    public static final int CRECHE_1_CHILD_ID = 141;
    public static final int CRECHE_N_CHILD_ID = 142;
    public static final int GW_CRECHE_1_CHILD_ID = 141;
    public static final int GW_CRECHE_N_CHILD_ID = 142;


//    _________________________________________________

    public static final int FP_GYM_ID = 307;
    public static final int FP_NEWTOWN_GYM_ID = 187;
    public static final int FP_PLAY_ID = 170;

    public static final int FP_NEWTOWN_GYM_NOCOMMIT_ID = 188;
    public static final int FP_GYM_NOCOMMIT_ID = 308;
    public static final int FP_PLAY_NOCOMMIT_ID = 172;

    public static final int CORPORATE_FP_GYM_ID = 175;
    public static final int CORPORATE_FP_PLAY_ID = 176;

    public static final int CORPORATE_FP_GYM_NOCOMMIT_ID = 139;
    public static final int CORPORATE_FP_PLAY_NOCOMMIT_ID = 265;
//    _________________________________________________

    public static final int FP_GYM_1MTH_ID = 312;
    public static final int FP_GYM_3MTH_ID = 309;
    public static final int FP_GYM_6MTH_ID = 310;
    public static final int FP_GYM_12MTH_ID = 311;

    public static final int FP_NEWTOWN_GYM_1MTH_ID = 109;
    public static final int FP_NEWTOWN_GYM_2MTH_ID = 108;
    public static final int FP_NEWTOWN_GYM_3MTH_ID = 107;
    public static final int FP_NEWTOWN_GYM_6MTH_ID = 163;
    public static final int FP_NEWTOWN_GYM_12MTH_ID = 106;

    public static final int FP_PLAY_1MTH_ID = 153;
//    public static final int FP_PLAY_2MTH_ID = ;
    public static final int FP_PLAY_3MTH_ID = 154;
    public static final int FP_PLAY_6MTH_ID = 158;
    public static final int FP_PLAY_12MTH_ID = 155;


    public static final int BUNKER_GYM_ID = 214;
    public static final int BUNKER_PLAY_ID = 221;

    public static final int BUNKER_GYM_NOCOMMIT_ID = 200;
    public static final int BUNKER_PLAY_NOCOMMIT_ID = 222;

    public static final int CORPORATE_BUNKER_GYM_ID = 227;
    public static final int CORPORATE_BUNKER_PLAY_ID = 257;

    public static final int CORPORATE_BUNKER_GYM_NOCOMMIT_ID = 266;
    public static final int CORPORATE_BUNKER_PLAY_NOCOMMIT_ID = 267;

    public static final int BUNKER_GYM_1MTH_ID = 273;
//    public static final int BUNKER_GYM_2MTH_ID = ;
    public static final int BUNKER_GYM_3MTH_ID = 288;
    public static final int BUNKER_GYM_6MTH_ID = 289;
    public static final int BUNKER_GYM_12MTH_ID = 230;

    public static final int BUNKER_PLAY_1MTH_ID = 224;
//    public static final int BUNKER_PLAY_2MTH_ID = ;
    public static final int BUNKER_PLAY_3MTH_ID = 290;
    public static final int BUNKER_PLAY_6MTH_ID = 291;
    public static final int BUNKER_PLAY_12MTH_ID = 231;

    public static final int FP_PT_30_1FN_ID = 277;
    public static final int FP_PT_30_1WK_ID = 130;
    public static final int FP_PT_30_2WK_ID = 192;
    public static final int FP_PT_30_3WK_ID = 271;
    public static final int FP_PT_30_4WK_ID = 276;
//
    public static final int FP_PT_40_1FN_ID = 261;
    public static final int FP_PT_40_1WK_ID = 260;
    public static final int FP_PT_40_2WK_ID = 262;
    public static final int FP_PT_40_3WK_ID = 303;
    public static final int FP_PT_40_4WK_ID = 292;
//
    public static final int FP_PT_60_1FN_ID = 286;
    public static final int FP_PT_60_1WK_ID = 259;
    public static final int FP_PT_60_2WK_ID = 272;
    public static final int FP_PT_60_3WK_ID = 246;
    public static final int FP_PT_60_4WK_ID = 287;


    public static final int GW_FP_PT_30_1FN_ID = 213;
    public static final int GW_FP_PT_30_1WK_ID = 130;
    public static final int GW_FP_PT_30_2WK_ID = 192;
    public static final int GW_FP_PT_30_3WK_ID = 211;
    public static final int GW_FP_PT_30_4WK_ID = 212;

    public static final int GW_FP_PT_40_1FN_ID = 166;
    public static final int GW_FP_PT_40_1WK_ID = 124;
    public static final int GW_FP_PT_40_2WK_ID = 123;
    public static final int GW_FP_PT_40_3WK_ID = 126;
    public static final int GW_FP_PT_40_4WK_ID = 129;

    public static final int GW_FP_PT_60_1FN_ID = 224;
    public static final int GW_FP_PT_60_1WK_ID = 182;
    public static final int GW_FP_PT_60_2WK_ID = 219;
    public static final int GW_FP_PT_60_3WK_ID = 221;
    public static final int GW_FP_PT_60_4WK_ID = 222;

    public static final int BUNKER_PT_30_1FN_ID = 295;
    public static final int BUNKER_PT_30_1WK_ID = 296;
    public static final int BUNKER_PT_30_2WK_ID = 297;
    public static final int BUNKER_PT_30_3WK_ID = 298;
    public static final int BUNKER_PT_30_4WK_ID = 299;

    public static final int BUNKER_PT_40_1FN_ID = 300;
    public static final int BUNKER_PT_40_1WK_ID = 234;
    public static final int BUNKER_PT_40_2WK_ID = 235;
    public static final int BUNKER_PT_40_3WK_ID = 236;
    public static final int BUNKER_PT_40_4WK_ID = 237;

    public static final int BUNKER_PT_60_1FN_ID = 285;
    public static final int BUNKER_PT_60_1WK_ID = 258;
    public static final int BUNKER_PT_60_2WK_ID = 282;
    public static final int BUNKER_PT_60_3WK_ID = 283;
    public static final int BUNKER_PT_60_4WK_ID = 284;


    public static final int FP_LIFESTYLE_PT_40_1FN_ID = 294;
    public static final int FP_LIFESTYLE_PT_40_1WK_ID = 268;
    public static final int FP_LIFESTYLE_PT_40_2WK_ID = 269;
    public static final int FP_LIFESTYLE_PT_40_3WK_ID = 270;
    public static final int FP_LIFESTYLE_PT_40_4WK_ID = 279;


    public static final int GW_FP_LIFESTYLE_PT_40_1FN_ID = 225;
    public static final int GW_FP_LIFESTYLE_PT_40_1WK_ID = 226;
    public static final int GW_FP_LIFESTYLE_PT_40_2WK_ID = 227;
    public static final int GW_FP_LIFESTYLE_PT_40_3WK_ID = 228;
    public static final int GW_FP_LIFESTYLE_PT_40_4WK_ID = 229;

    public static final int BUNKER_LIFESTYLE_PT_40_1FN_ID = 293;
    public static final int BUNKER_LIFESTYLE_PT_40_1WK_ID = 207;
    public static final int BUNKER_LIFESTYLE_PT_40_2WK_ID = 208;
    public static final int BUNKER_LIFESTYLE_PT_40_3WK_ID = 209;
    public static final int BUNKER_LIFESTYLE_PT_40_4WK_ID = 278;


    public static final int FP_EXTERNAL_PT_40_1FN_ID = 191;
    public static final int FP_EXTERNAL_PT_40_1WK_ID = 178;
    public static final int FP_EXTERNAL_PT_40_2WK_ID = 193;
    public static final int FP_EXTERNAL_PT_40_3WK_ID = 180;
    public static final int FP_EXTERNAL_PT_40_4WK_ID = 181;

    public static final int FP_EXTERNAL_PT_60_1FN_ID = 281;
    public static final int FP_EXTERNAL_PT_60_1WK_ID = 241;
    public static final int FP_EXTERNAL_PT_60_2WK_ID = 242;
    public static final int FP_EXTERNAL_PT_60_3WK_ID = 242;
    public static final int FP_EXTERNAL_PT_60_4WK_ID = 280;

    public static final int VIRTUAL_PLAYGROUND_ID = 335;

    public static final String ASSIGN_TO_PTM_ID = "-10";
    public static final String NO_COMP_SESSION_ID = "-1000";

    public static final String ACTION_TYPE_PROCESS_ENROLMENT = "Process Enrolment";
    public static final String ACTION_TYPE_CREATE_AC_CONTRACT = "Create Active Campaign Contact";

    public static final String FS_FORM_ID_PARQ_TRAINER = "4405744";
}

