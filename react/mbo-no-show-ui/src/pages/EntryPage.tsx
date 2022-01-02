import {
  IonApp,
  IonContent,
  IonHeader,
  IonToolbar,
  IonButtons,
  IonBackButton,
  IonButton,
  IonIcon,
} from '@ionic/react';
import { trash as deleteIcon } from 'ionicons/icons'
import React from 'react';
import { useParams, useHistory } from 'react-router';
import { firestore } from '../firebase';
// import { Entries, toEntries } from '../models/Entry';
import { useAuth } from '../auth';
// import { formatDate } from '../date';

interface RouteParams {
  id: string
}

const EntryPage: React.FC = () => {
  const { userId } = useAuth();
  const { id } = useParams<RouteParams>();
  // const [entries, setEntries] = useState<Entries>();
  const history = useHistory();

  const handleDelete = async () => {
    const entryRef = firestore.collection('users').doc(userId).collection('Entries').doc(id)
    await entryRef.delete()
    history.goBack();
  }

  // useEffect(() => {
  //   const entryRef = firestore.collection('users').doc(userId).collection('Entries').doc(id)
  //   entryRef.get().then((doc) => { 
  //     console.log('Entries',doc)
  //     setEntries(toEntries(doc)); });
  // }, [userId, id])

  return (
    <IonApp>
      <IonHeader>
        <IonToolbar>
          <IonButtons slot="start">
            <IonBackButton defaultHref="/v1/entries" />
          </IonButtons>
          {/* <IonTitle>{formatDate(entry?.date)}</IonTitle> */}
          {/* <IonTitle>{entry?.type}</IonTitle> */}
          <IonButtons slot="end">
            <IonButton onClick={handleDelete}>
              <IonIcon icon={deleteIcon} slot="icon-only" />
            </IonButton>
          </IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent className="ion-padding">
        {/* <h2>{entry?.name}</h2>
        <p>{entry?.message}</p> */}
      </IonContent>
    </IonApp>
  );
};

export default EntryPage;
