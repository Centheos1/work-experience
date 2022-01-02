import {
  IonContent,
  IonHeader,
  IonTitle,
  IonToolbar,
  IonFab,
  IonFabButton,
  IonIcon,
  IonLabel, IonGrid, IonRow, IonCol, IonCardTitle, IonCard, IonCardHeader, IonCardSubtitle, IonPage, IonCardContent, IonText, IonItemGroup, IonItem, IonButton, IonLoading, IonToast, IonFabList, IonNote, IonTextarea
} from '@ionic/react';
import { locationSharp as fabIcon, informationCircleOutline as infoIcon } from 'ionicons/icons'
import React, { useState, useEffect } from 'react';
import { firestore } from '../firebase';
import { Dispute, toDispute } from '../models/Entry';
import { useAuth } from '../auth';
import { formatDate } from '../date';
import { camelCaseToTitleCase, toTitleCase } from '../helpers';
import { NoShowClient } from '../models/NoShowClient';

interface Comment {
  disputeId: string,
  comment: string
}

const HomePage: React.FC = () => {
  const { userId, noShowClient, permission } = useAuth();
  const [no_show_client, setNoShowClient] = useState<NoShowClient>();
  // const [entries, setEntries] = useState<Entries[]>([]);
  // const [billing, setBilling] = useState<Billing[]>([]);
  const [disputes, setDisputes] = useState<Dispute[]>([]);
  const [disputeLocationFilter, setDisputeLocationFilter] = useState("0");
  const [show_disputes_info, setShowDisputesInfo] = useState(false);
  const [status, setStatus] = useState({ loading: false, error: false });
  const [showToast, setShowToast] = useState(false);
  const [comments, setComments] = useState({});

  const handleApproved = async (ledgerId: string) => {
    console.log("Handle Submit Approved", ledgerId)

    console.log("TO BE IMPLEMETED - Check if REFUND will be required")


    setStatus({ loading: true, error: false })
    const d = new Date();
    return await firestore.collection('users').doc(userId)
      .collection('Disputes').doc(ledgerId).update({
        verdict: 'APPROVED',
        comments: comments[ledgerId],
        updateDate: d
      }).then(() => {
        setStatus({ loading: false, error: false })
        setShowToast(true)
        console.log("Updated Dispute, handle Database")

        const abortRequest = {
          ledgerId: ledgerId,
          verdict: 'APPROVED',
          comments: comments[ledgerId],
          updateDate: d
        }

        console.log("TO BE IMPLEMETED - Trigger Lambda to cancel Charge")


      }).catch((error) => {
        console.error("Error updating document: ", error);
        setStatus({ loading: false, error: true })
      })
  }

  const handleDenied = async (ledgerId: string) => {
    console.log("Handle Submit Denied", ledgerId)
    setStatus({ loading: true, error: false })
    const d = new Date();
    return await firestore.collection('users').doc(userId)
      .collection('Disputes').doc(ledgerId).update({
        verdict: 'DENIED',
        comments: comments[ledgerId],
        updateDate: d
      }).then(() => {
        setStatus({ loading: false, error: false })
        setShowToast(true)
        console.log("Updated Dispute, handle Database")
      }).catch((error) => {
        console.error("Error updating document: ", error);
        setStatus({ loading: false, error: true })
      })

  }

  // useEffect(() => {
  //   console.log('\n\n[ReportsPage].useEffect() Get No Show Client: userId', userId)
  //   const entryRef = firestore.collection('users').doc(userId).collection('NoShowClient')
  //   entryRef.get().then((snapshot) => {
  //     snapshot.docs.forEach((doc) => {
  //       console.log(`NoShowClient ${doc.data()}`)
  //       setNoShowClient(toNoShowClient(doc))
  //     })
  //   })
  // }, [userId]);

  useEffect(() => {
    setNoShowClient(noShowClient)
  }, [noShowClient])

  // console.log('Disputes:', disputes)
  // console.log('Comments:', comments)


  useEffect(() => {
    // console.log(`\n\n[HomePage].useEffect() ${firstName} is getting Disputes: userId: ${userId}`)
    // Original version, upadted to handle clean up
    // const disputesRef = firestore.collection('users').doc(userId).collection('Disputes');
    // return disputesRef.where("verdict", "==", "PENDING").onSnapshot(({ docs }) => setDisputes(docs.map(toDispute)));

    // TEST ME
    // var unsubscribe = 
    firestore.collection('users').doc(userId).collection('Disputes').where("verdict", "==", "PENDING")
      .onSnapshot(({ docs }) => setDisputes(docs.map(toDispute)));

    // return unsubscribe();
    // TEST ME

  }, [userId]);

  useEffect(() => {
    let c = { ...comments }
    disputes.forEach(d => {
      if (typeof (c[d.disputeId]) === 'undefined') {
        c[d.disputeId] = ""
      }
    })
    return setComments({ ...c })
    // console.log('Set Comments useEffect()', comments)
  }, [disputes])// eslint-disable-line react-hooks/exhaustive-deps

  const handleFilterDisputes = (locationId: number) => {
    // console.log("TO BE IMPLMENTED handleFilterDisputes: ", locationId)
    setDisputeLocationFilter(locationId.toString())
  }

  const handleResetDisputes = () => {
    // console.log("TO BE IMPLMENTED handleResetDisputes: ")
    setDisputeLocationFilter("0")
  }

  const handleComment = (disputeId: string, comment: string) => {
    // console.log(`disputeId: ${disputeId}, comment: ${comment}`)
    // console.log('comments[disputeId]', comments[disputeId])
    const c = { ...comments };
    c[disputeId] = comment
    // console.log('comments', comments)
    // setComments({ ...comments, disputeId: comment })
    setComments(c)
  }

  // useEffect(() => {
  //   entries.map((e) => {
  //     setDisputes(e.disputes)
  //     setBilling(e.billing)
  //     return e
  //   })
  // }, [entries])

  // console.log("Rendered HomePage: entries: ", entries);
  return (
    <IonPage>

      <IonToast
        isOpen={showToast}
        position="bottom"
        onDidDismiss={() => setShowToast(false)}
        message="Verdict has been submitted."
        duration={1000}
      />

      {/* FAB */}
      <IonFab vertical="bottom" horizontal="end" >
        <IonFabButton><IonIcon icon={fabIcon} /></IonFabButton>
        <IonFabList side="top" >
          <IonFabButton onClick={handleResetDisputes}><IonLabel color="secondary">ALL</IonLabel></IonFabButton>
          {
            no_show_client?.locations.map((location) =>
              <IonFabButton key={`repost-fab-${location.location_name}`} onClick={() => handleFilterDisputes(location.location_id)}>
                <IonLabel color="secondary">{`${location.location_name_abbreviated}`}</IonLabel>
              </IonFabButton>
            )
          }
        </IonFabList>
      </IonFab>

      <IonHeader>
        <IonToolbar>
          <IonTitle>No Show</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent className="ion-padding">

        {/* No Show: Product Id */}
        <IonItem onClick={() => setShowDisputesInfo(!show_disputes_info)}>
          <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
          <IonCardSubtitle>Disputes</IonCardSubtitle>
        </IonItem>

        {/* Information Element: show_show_info */}
        {show_disputes_info ?
          <IonItem onClick={() => setShowDisputesInfo(!show_disputes_info)}>
            {/* <IonItem> */}
            <IonNote class="ion-padding-start">
              <h5>A member has disputed a charge.</h5>
              <hr />
              <p></p>
              <hr />
              <p>Each card contains the information regarding the members dispute.</p>
              <hr />
              <p><u>Note</u>: the <IonText color="secondary"><b>Charge Type</b></IonText> is subtitled.</p>
              <hr />
              <p>To <u>approve</u> click <IonText color="success"><b>Approve</b></IonText>. The member will <u>not</u> be charged.</p>
              <hr />
              <p>To <u>deny</u> click <IonText color="danger"><b>Deny</b></IonText>. The member will be charged.</p>
              <hr />
              <p><u>Note</u>: <i>IF</i> the number of <i>Days from Offence to Charge</i> has elapsed a <u>Refrund</u> will be required.</p>
              <hr />
              <p><u>Note</u>: <i>Days from Offence to Charge</i> can be configured in the Settings.</p>
            </IonNote>
          </IonItem>
          : null}

        <IonGrid fixed>
          <IonRow>
            <IonCol >
              {/* <IonList> */}
              {/* <IonLabel>Disputes</IonLabel> */}
              {disputes.filter((d) => {
                return d.classLocationId === disputeLocationFilter || disputeLocationFilter === "0"
              }).map((d) =>
                <IonCard key={d.disputeId}>
                  <IonCardHeader>
                    <IonCardTitle>{`${d.clientName}`}</IonCardTitle>
                    <IonCardSubtitle color="secondary">{camelCaseToTitleCase(d.chargeType)}</IonCardSubtitle>
                    {/* <IonCardSubtitle slot="end">{formatDate(d.createDate)}</IonCardSubtitle> */}
                  </IonCardHeader>
                  <IonCardContent>
                    <IonGrid>
                      <IonItemGroup>
                        <IonRow>
                          <IonCol size="12" size-sm="6">
                            <IonItem>
                              <IonLabel position="stacked" color="primary">Request Date</IonLabel>
                              <IonText>{formatDate(d.createDate.toDate())}</IonText>
                            </IonItem>
                          </IonCol>
                          <IonCol size="12" size-sm="6">
                            <IonItem>
                              <IonLabel position="stacked" color="primary">Class Name</IonLabel>
                              <IonText>{d.className}</IonText>
                            </IonItem>
                          </IonCol>
                        </IonRow>
                        <IonRow>
                          <IonCol size="12" size-sm="6">
                            <IonItem>
                              <IonLabel position="stacked" color="primary">Class Location</IonLabel>
                              <IonText>{d.classLocation}</IonText>
                            </IonItem>
                          </IonCol>
                          <IonCol size="12" size-sm="6">
                            <IonItem>
                              <IonLabel position="stacked" color="primary">Reason for Request</IonLabel>
                              <IonText>{toTitleCase(d.refundReason)}</IonText>
                            </IonItem>
                          </IonCol>
                        </IonRow>
                        {d.refundReasonDetail === "" ? null :
                          <IonRow>
                            <IonCol>
                              <IonItem>
                                <IonLabel position="stacked" color="primary">Request Details</IonLabel>
                                <IonText>{d.refundReasonDetail}</IonText>
                              </IonItem>
                            </IonCol>
                          </IonRow>
                        }
                        {permission !== 'Read Only' ?
                          <IonRow>
                            <IonCol>
                              <IonItem>
                                <IonLabel position="stacked" color="primary">Comments</IonLabel>
                                <IonTextarea placeholder="Enter a comment here..." value={comments[d.disputeId]} onIonChange={e => handleComment(d.disputeId, e.detail.value!)}></IonTextarea>
                                {/* <IonTextarea placeholder="Enter a comment here..." value={comments[d.disputeId]} onIonChange={e => setComments({ ...comments, d.disputeId: e.detail.value!})}></IonTextarea> */}
                              </IonItem>
                            </IonCol>
                          </IonRow>
                          : null}
                        {permission !== 'Read Only' ?
                          <IonRow>
                            <IonCol size="12" size-sm="6">
                              <IonButton color="success" expand="block" fill="outline" onClick={() => handleApproved(d.disputeId)}>Approve</IonButton>
                            </IonCol>
                            <IonCol size="12" size-sm="6">
                              <IonButton color="danger" expand="block" fill="outline" onClick={() => handleDenied(d.disputeId)}>Deny</IonButton>
                            </IonCol>
                          </IonRow>
                          : null}
                      </IonItemGroup>
                    </IonGrid>
                  </IonCardContent>
                </IonCard>
              )}
              {/* </IonList> */}
            </IonCol>
          </IonRow>

          {/* <IonRow>
            <IonCol>
              <IonLabel>Billing</IonLabel>
              {billing?.map((b) =>
                <IonCard key={b._ledger_id}>
                  <IonCardHeader>
                    <IonCardTitle>{b.member_name}</IonCardTitle>
                    <IonCardSubtitle color={b.status === 'REFUND' ? "tertiary" : "warning"}>{b.status}</IonCardSubtitle>
                  </IonCardHeader>
                </IonCard>
              )}
            </IonCol>
          </IonRow> */}

          <IonRow className={disputes.filter((d) => {
            return d.classLocationId === disputeLocationFilter || disputeLocationFilter === "0"
          }).length === 0 ? "ion-padding" : "ion-hide"}>
            <IonCol>
              <IonText color="medium" className="ion-text-center"><h3><i>No Pending Disputes</i></h3></IonText>
            </IonCol>

          </IonRow>

        </IonGrid>
      </IonContent>
      <IonLoading isOpen={status.loading} />
    </IonPage>
  );
};

export default HomePage;
