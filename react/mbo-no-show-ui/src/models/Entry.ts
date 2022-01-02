// Entry
// id
// disputes
// billing

import firebase from "firebase";

export interface Entries {
    id: string;
    disputes: Dispute[];
    billing: Billing[];
}

export function toEntries(doc): Entries { return { id: doc.id, ...doc.data() }; }

export interface Dispute {
    disputeId: string;
    chargeType: string;
    clientName: string;
    className: string;
    classLocation: string;
    classLocationId: string;
    verdict: string;
    refundReason: string;
    refundReasonDetail: string;
    comments: string;
    createDate: firebase.firestore.Timestamp;
    updateDate: firebase.firestore.Timestamp;
}

export function toDispute(doc): Dispute {
    return { id: doc.id, ...doc.data() };
}

export interface ChargeBillingLedger {
    charge_scheduled_run_date: Date;
    charge_status: string
    charge_type: string;
    class_day_of_week: string;
    class_instructor: string;
    class_location_id: string;
    class_location_name: string;
    class_name: string;
    class_start_datetime: Date;
    class_week_of_year: number;
    class_year_month: string;
    client_credit_card_last_four: string;
    client_email: string;
    client_first_name: string;
    client_last_name: string;
    client_payment_type: string;
    client_phone: string;
    communications_status: string;
    createDate: Date;
    id: number;
    index_key: string;
    mbo_class_id: number;
    mbo_class_visit_id: number;
    mbo_client_id: string;
    mbo_client_unique_id: number;
    mbo_instructor_id: string;
    mbo_shopping_cart_id: null
    mbo_site_id: number;
    session_type_id: number;
    session_type_name: string;
}



// export interface Dispute {
//     id: string;
//     _ledger_id: number;
//     _index_key: string;
//     _class_visit_id: string;
//     _location_id: number;
//     verdict: string;
//     dispute_reason: string;
//     class_instructor_name: string;
//     class_name: string;
//     class_location: string;
//     class_datetime: string;
//     member_name: string;
//     member_email: string;
//     member_id: string;
//     charge_scheduled_run_date: string;
//     completion_datetime: string;
//     completed_by_name: string;
//     completed_by_id: string;
//     submission_datetime: string;
// }



export interface Billing {
    id: string;
    _ledger_id: number;
    _index_key: string;
    _class_visit_id: string;
    _location_id: number;
    member_name: string;
    member_email: string;
    member_id: string;
    mbo_shopping_cart_id: string;
    status: string;
    completion_datetime: string;
    amount: number;
    completed_by_name: string;
    completed_by_id: string;
    submission_datetime: string;
}

export function toBilling(doc): Billing {
    return { id: doc.id, ...doc.data() };
}
