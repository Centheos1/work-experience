import {
  IonApp,
  IonContent,
  IonHeader,
  IonTitle,
  IonToolbar,
  IonButtons,
  IonBackButton,
  IonList,
  IonLabel,
  IonItem,
  IonInput,
  IonTextarea,
  IonButton,
  IonDatetime,
} from '@ionic/react';
import React, { useState } from 'react';
import { firestore } from '../firebase';
import { useAuth } from '../auth';
import { useHistory } from 'react-router';

// import { locationData, reportData, location0, location1, location2, location3, location5 } from '../firebase';

const AddEntryPage: React.FC = () => {
  const { userId } = useAuth();
  const history = useHistory();

  const [date, setDate] = useState('');
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  const handleSave = async () => {
    console.log('[handleSave]', {title, description})
    const entriesRef = firestore.collection('users').doc(userId).collection('entries');
    const entryData = { date, title, description };
    const entryRef = await entriesRef.add(entryData);
    console.log('[handleSave]', entryRef.id)
    history.goBack();

    // console.log('\n\nADDING DATA TO FIREBASE')
    // const entriesRef = firestore.collection('users').doc(userId).collection('LocationPerformance').doc("5");
    // const entryData = {...location5};
    // const entryRef = await entriesRef.set(entryData);
    // console.log('[handleSave]', entryRef.id)
    // history.goBack();
    
  }

  return (
    <IonApp>
      <IonHeader>
        <IonToolbar>
          <IonButtons slot="start">
            <IonBackButton defaultHref="/v1/entries" />
          </IonButtons>
          <IonTitle>Add Entry</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent className="ion-padding">
        <IonList>
        <IonItem>
            <IonLabel position="stacked">Date</IonLabel>
            <IonDatetime
              value={date}
              onIonChange={(event) => setDate(event.detail.value)}
            />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Title</IonLabel>
            <IonInput 
              value={title}
              onIonChange={(event) => setTitle(event.detail.value)}
            />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Description</IonLabel>
            <IonTextarea 
              value={description}
              onIonChange={(event) => setDescription(event.detail.value)}
            />
          </IonItem>
          <IonButton expand="block" onClick={handleSave}>Save</IonButton>
        </IonList>
      </IonContent>
    </IonApp>
  );
};

export default AddEntryPage;
