import * as firebase from 'firebase/app';
import 'firebase/auth';
import 'firebase/firestore';
import 'firebase/storage';

// This needs to be Secured
const firebaseConfig = {
    apiKey: "apiKey",
    authDomain: "authDomain",
    databaseURL: "databaseURL",
    projectId: "projectId",
    storageBucket: "storageBucket",
    messagingSenderId: "messagingSenderId",
    appId: "appId",
    measurementId: "measurementId"
  };

  const app = firebase.initializeApp(firebaseConfig)
  export const auth = app.auth();
  export const firestore = app.firestore();
  export const storage = app.storage();
