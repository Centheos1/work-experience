import * as firebase from 'firebase/app';
import 'firebase/auth';
import 'firebase/firestore';
import 'firebase/storage';

// This needs to be Secured
const firebaseConfig = {
    apiKey: "AIzaSyAkjZ0l_CDX4Qn_4Mk_bW2xamUXkEFUuDc",
    authDomain: "mbo-no-show-ui-42d2e.firebaseapp.com",
    databaseURL: "https://mbo-no-show-ui-42d2e.firebaseio.com",
    projectId: "mbo-no-show-ui-42d2e",
    storageBucket: "mbo-no-show-ui-42d2e.appspot.com",
    messagingSenderId: "216044501842",
    appId: "1:216044501842:web:1b97ff05b43fd21275d88b",
    measurementId: "G-TTNZXEGJ85"
  };

  const app = firebase.initializeApp(firebaseConfig)
  export const auth = app.auth();
  export const firestore = app.firestore();
  export const storage = app.storage();
