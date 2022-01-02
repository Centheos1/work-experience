import { IonButton, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonContent, IonGrid, IonItem, IonItemGroup, IonLabel, IonListHeader, IonLoading, IonPage, IonRadio, IonRadioGroup, IonRow, IonText, IonTextarea, IonToast } from '@ionic/react';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import lambdaProxy from '../lambda-proxy-axios';
import { firestore } from '../firebase';
import { ChargeBillingLedger } from '../models/Entry';
import { formatDateTime } from '../date';
import { camelCaseToTitleCase } from '../helpers';
import * as CONSTANTS from '../constants'

interface RouteParams {
    ledgerId: string;
    type: string;
}

const DisputePage: React.FC = () => {

    const { ledgerId } = useParams<RouteParams>();
    const [chargeBillingLedger, setChargeBillingLedger] = useState<ChargeBillingLedger>()
    const [uid, setUid] = useState("")
    const [status, setStatus] = useState({ loading: false, error: false });
    const [refundReason, setRefundReason] = useState("")
    const [refundReasonDetail, setRefundReasonDetail] = useState("")
    const [isValid, setIsValid] = useState(false);
    const [showToast, setShowToast] = useState(false);
    const [isSubmitted, setIsSubmitted] = useState(false);


    const handleSubmission = async () => {
        // console.log("Handle Submit Dispute")
        // console.log("refundReason: ", refundReason)
        setStatus({ loading: true, error: false })
        setShowToast(true)
        const d = new Date();
        return await firestore.collection('users').doc(uid)
            .collection('Disputes').doc(ledgerId).set({
                verdict: 'PENDING',
                chargeType: chargeBillingLedger.charge_type,
                clientName: `${chargeBillingLedger.client_first_name} ${chargeBillingLedger.client_last_name}`,
                className: chargeBillingLedger.class_name,
                classLocation: chargeBillingLedger.class_location_name,
                classLocationId: chargeBillingLedger.class_location_id,
                classDatetime: chargeBillingLedger.class_start_datetime,
                refundReason: refundReason,
                refundReasonDetail: refundReasonDetail,
                disputeId: ledgerId,
                indexKey: chargeBillingLedger.index_key,
                createDate: d,
                updateDate: d,
                comments: ""
            }).then(() => {
                setStatus({ loading: false, error: false })
                // setShowToast(true)
                setIsSubmitted(true)
                console.log("Saved Dispute, handle UI")
            }).catch((error) => {
                console.error("Error writing document: ", error);
                setStatus({ loading: false, error: true })
            })
    }

    useEffect(() => {
        setStatus({ loading: true, error: false })
        lambdaProxy.post('get_ledger_item', { data: ledgerId })
            .then(function (response) {
                console.log('Dispute response [ledgerId]: ', ledgerId)
                console.log('Dispute response [ledger_document]: ', response.data.ledger_document)
                console.log('Dispute response: [auth]', response.data.auth)
                setStatus({ loading: false, error: false })
                setChargeBillingLedger(response.data.ledger_document)
                setUid(response.data.auth)
            }).catch(function (error) {
                console.error("Error getting Dispute: ", error);
                setStatus({ loading: false, error: true })
            })
        // return (
        //     console.log(" Look up ChargeBillingLedger with id: ", ledgerId)
        // )
    }, [ledgerId])


    useEffect(() => {
        let valid = refundReason !== "";
        if (refundReason === "other") {
            valid = refundReasonDetail !== "";
        }
        setIsValid(valid);
    }, [refundReason, refundReasonDetail])

    return (
        <IonPage>

            <IonToast
                isOpen={showToast}
                position="bottom"
                onDidDismiss={() => setShowToast(false)}
                message="Your request is being submitted."
                duration={3000}
            />

            {status.loading || status.error === false ? null :
                <IonContent className="ion-padding">
                    <IonCard className="ion-padding">
                        <IonCardHeader>
                            <IonCardTitle color="primary">Opps something went wrong.</IonCardTitle>
                            <IonCardSubtitle color="medium">We do apolise.</IonCardSubtitle>
                        </IonCardHeader>
                    </IonCard>
                </IonContent>
            }

            {status.loading || status.error === true ? null :
                <IonContent className="ion-padding">
                    {/* Dispute Form */}
                    <IonCard className={isSubmitted ? "ion-hide" : "ion-padding"}>
                        <IonCardHeader>
                            <IonCardTitle color="primary">Class Charge Review</IonCardTitle>
                            <IonCardSubtitle color="medium">Please dispute within <u>48 hours</u> of the missed or late cancelled class.</IonCardSubtitle>
                        </IonCardHeader>
                        <IonCardContent>
                            <IonGrid>
                                <IonItemGroup>
                                    <IonRow>
                                        <IonCol size="12" size-sm="6">
                                            <IonItem>
                                                <IonLabel position="stacked" color="primary">First Name</IonLabel>
                                                <IonText color="medium">{`${chargeBillingLedger?.client_first_name}`}</IonText>
                                            </IonItem>
                                        </IonCol>
                                        <IonCol size="12" size-sm="6">
                                            <IonItem>
                                                <IonLabel position="stacked" color="primary">Last Name</IonLabel>
                                                <IonText color="medium">{`${chargeBillingLedger?.client_last_name}`}</IonText>
                                            </IonItem>
                                        </IonCol>
                                    </IonRow>
                                    <IonRow>
                                        <IonCol size="12" size-sm="6">
                                            <IonItem>
                                                <IonLabel position="stacked" color="primary">Class Date & Time</IonLabel>
                                                <IonText color="medium">{`${formatDateTime(chargeBillingLedger?.class_start_datetime)}`}</IonText>
                                            </IonItem>
                                        </IonCol>
                                        <IonCol size="12" size-sm="6">
                                            <IonItem>
                                                <IonLabel position="stacked" color="primary">Class Name</IonLabel>
                                                <IonText color="medium">{`${chargeBillingLedger?.class_name}`}</IonText>
                                            </IonItem>
                                        </IonCol>
                                    </IonRow>
                                    <IonRow>
                                        <IonCol size="12" size-sm="6">
                                            <IonItem>
                                                <IonLabel position="stacked" color="primary">Instructor Name</IonLabel>
                                                <IonText color="medium">{`${chargeBillingLedger?.class_instructor}`}</IonText>
                                            </IonItem>
                                        </IonCol>
                                        <IonCol size="12" size-sm="6">
                                            <IonItem>
                                                <IonLabel position="stacked" color="primary">Class Location</IonLabel>
                                                <IonText color="medium">{`${chargeBillingLedger?.class_location_name}`}</IonText>
                                            </IonItem>
                                        </IonCol>
                                    </IonRow>
                                    <IonRow>
                                        <IonCol size="12" size-sm="6">
                                            <IonItem>
                                                <IonLabel position="stacked" color="primary">Charge Type</IonLabel>
                                                <IonText color="medium">{`${camelCaseToTitleCase(chargeBillingLedger?.charge_type)}`}</IonText>
                                            </IonItem>
                                        </IonCol>
                                        <IonCol size="12" size-sm="6">
                                            <IonItem>
                                                <IonLabel position="stacked" color="primary">Charge Status</IonLabel>
                                                <IonText color="medium">{`${camelCaseToTitleCase(chargeBillingLedger?.charge_status)}`}</IonText>
                                            </IonItem>
                                        </IonCol>
                                    </IonRow>
                                </IonItemGroup>
                            </IonGrid>

                            {/* Charge needs to be refunded */}
                            {chargeBillingLedger?.charge_status === CONSTANTS.CHARGE_STATUS_NO_SHOW_REFUND || chargeBillingLedger?.charge_status === CONSTANTS.CHARGE_STATUS_LATE_CANCEL_REFUND ?
                                <IonGrid>
                                    <IonRow>
                                        <IonCol>
                                            <IonItem>
                                                <IonText>This class charge has been reviewed and approved, however, a charge was applied. This will be refunded.</IonText>
                                            </IonItem>
                                        </IonCol>
                                    </IonRow>
                                </IonGrid>
                                : null
                            }

                            {/* Charge has been forgiven */}
                            {chargeBillingLedger?.charge_status === CONSTANTS.CHARGE_STATUS_FORGIVE ?
                                <IonGrid>
                                    <IonRow>
                                        <IonCol>
                                            <IonItem>
                                                <IonText>This class charge has been forgiven. No charge will be applied.</IonText>
                                            </IonItem>
                                        </IonCol>
                                    </IonRow>
                                </IonGrid>
                                : null
                            }

                            {/* Charge has been actioned */}
                            {chargeBillingLedger?.charge_status === CONSTANTS.CHARGE_STATUS_NO_SHOW_COMPLETE || chargeBillingLedger?.charge_status === CONSTANTS.CHARGE_STATUS_LATE_CANCEL_COMPLETE ?
                                <IonGrid>
                                    <IonRow>
                                        <IonCol>
                                            <IonItem>
                                                <IonText>The review period for this class charge has expired.</IonText>
                                            </IonItem>
                                        </IonCol>
                                    </IonRow>
                                </IonGrid>
                                : null
                            }

                            {/* Charge has been reversed */}
                            {chargeBillingLedger?.charge_status === CONSTANTS.CHARGE_STATUS_NO_SHOW_ABORT || chargeBillingLedger?.charge_status === CONSTANTS.CHARGE_STATUS_LATE_CANCEL_ABORT || chargeBillingLedger?.charge_status === CONSTANTS.CHARGE_STATUS_SYSTEM_ABORT ?
                                <IonGrid>
                                    <IonRow>
                                        <IonCol>
                                            <IonItem>
                                                <IonText>This class charge has been reviewed and approved. No charge will be applied.</IonText>
                                            </IonItem>
                                        </IonCol>
                                    </IonRow>
                                </IonGrid>
                                : null
                            }

                            {/* Charge is Pending */}
                            {chargeBillingLedger?.charge_status == 'NO_SHOW_CHARGE_PENDING' || chargeBillingLedger?.charge_status == 'LATE_CANCEL_CHARGE_PENDING' ?
                                // Form Section 
                                <IonGrid>
                                    <IonItemGroup>
                                        <IonRow>
                                            <IonCol size="12">
                                                <IonRadioGroup allowEmptySelection={false} value={refundReason} onIonChange={e => setRefundReason(e.detail.value)}>
                                                    <IonListHeader>
                                                        <IonLabel color="primary">What is the reason for the Refund Request?</IonLabel>
                                                    </IonListHeader>
                                                    <IonItem>
                                                        <IonRadio value="attended" />
                                                        <IonLabel className="ion-margin-start">I Attended the Class</IonLabel>
                                                    </IonItem>
                                                    <IonItem>
                                                        <IonRadio value="emergency" />
                                                        <IonLabel className="ion-margin-start">There was an Emergency</IonLabel>
                                                    </IonItem>
                                                    <IonItem>
                                                        <IonRadio value="forgot" />
                                                        <IonLabel className="ion-margin-start">Oups! Did I have a Class</IonLabel>
                                                    </IonItem>
                                                    <IonItem>
                                                        <IonRadio value="other" />
                                                        <IonLabel className="ion-margin-start">Other</IonLabel>
                                                    </IonItem>
                                                </IonRadioGroup>
                                            </IonCol>
                                        </IonRow>

                                        <IonRow className={refundReason !== 'other' ? 'ion-hide' : 'ion-padding'}>
                                            <IonCol>
                                                <IonItem>
                                                    <IonTextarea placeholder="Enter more information here..." value={refundReasonDetail} onIonChange={e => setRefundReasonDetail(e.detail.value!)}></IonTextarea>
                                                </IonItem>
                                            </IonCol>
                                        </IonRow>
                                    </IonItemGroup>

                                    {/* Submit Button */}
                                    <IonRow>
                                        <IonCol>
                                            <IonButton disabled={!isValid} expand="block" fill="outline" color="primary" onClick={handleSubmission}>Submit</IonButton>
                                        </IonCol>
                                    </IonRow>

                                </IonGrid>
                                : null
                            }
                        </IonCardContent>
                    </IonCard>

                    {/* Submitted Message */}
                    <IonCard className={!isSubmitted ? "ion-hide" : "ion-padding"}>
                        <IonCardContent>
                            <IonText>
                                <p>Thank you {chargeBillingLedger?.client_first_name},</p>

                                <p>We have received your request to review the charge associated with your class on {formatDateTime(chargeBillingLedger?.class_start_datetime)}</p>

                                <p>We will review this request via email and will be in touch within the <b>next 24-48 hours</b> to advise the outcome of your request.</p>

                                <p>I hope you're enjoying our classes and if you ever have any questions please do not hesitate to get in touch.</p>

                                <p>Speak soon!</p></IonText>
                        </IonCardContent>
                    </IonCard>

                </IonContent >
            }

            <IonLoading isOpen={status.loading} />
        </IonPage >
    )

}

export default DisputePage;