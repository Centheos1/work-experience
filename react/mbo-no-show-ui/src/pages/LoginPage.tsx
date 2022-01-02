import {
  IonApp,
  IonContent,
  IonHeader,
  IonTitle,
  IonToolbar,
  IonButton,
  IonList,
  IonItem,
  IonLabel,
  IonInput,
  IonText,
  IonLoading
} from '@ionic/react';
import React, { useState } from 'react';
import { Redirect } from 'react-router';
import { useAuth } from '../auth';
import { auth } from '../firebase';


const LoginPage: React.FC = () => {

  const { loggedIn } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [status, setStatus] = useState({ loading: false, error: false});

  const handleLogin = async () => {
    // console.log('Login with: ', { email, password})
    // const credential = auth.signInWithEmailAndPassword("test@me.co", "123456");
    try {
      setStatus({ loading: true, error: false});
      // const credential = 
      await auth.signInWithEmailAndPassword(email, password);
      // console.log("credential:", credential)
    } catch(error) {
      setStatus({ loading: false, error: true});
      console.log('[LoginPage] signin error: ', error);
    }
    
  }

  if (loggedIn) { return <Redirect to="/v1/entries" /> }

  return (
    <IonApp>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Login</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent className="ion-padding">
        <IonList>
          <IonItem>
            <IonLabel position="stacked">Email</IonLabel>
            <IonInput
              type="email"
              value={email}
              onIonChange={(event) => setEmail(event.detail.value)}
            />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Password</IonLabel>
            <IonInput
              type="password"
              value={password}
              onIonChange={(event) => setPassword(event.detail.value)}
            />
          </IonItem>
        </IonList>
        {status.error && <IonText color="danger">Invalid Credentials</IonText>}
        <IonButton expand="block" onClick={handleLogin}>Login</IonButton>
      </IonContent>
      <IonLoading isOpen={status.loading} />
    </IonApp>
  );
};

export default LoginPage;

