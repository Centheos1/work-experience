import React, { useContext, useState, useEffect } from 'react';
import { NoShowClient, toNoShowClient } from './models/NoShowClient';
import { firestore } from './firebase';
import { useAuth } from './auth';

const { userId } = useAuth();

interface NoShowClientInit {
  document_id: string;
  no_show_client?: NoShowClient;
}

export const NoShowClientContext = React.createContext<NoShowClient>(null);

export function useNoShowClient(): NoShowClient {
  return useContext(NoShowClientContext);
}


export function useNoShowClientInit(): NoShowClientInit {

  const [noShowClientInit, setNoShowClientInit] = useState<NoShowClientInit>();
  useEffect(() => {

    console.log('\n\n[noShowClient].useNoShowClientInit() Get No Show Client')

    // const entryRef = firestore.collection('users').doc(userId).collection('NoShowClient')
    // entryRef.get().then((snapshot) => {
    //   snapshot.docs.forEach((doc) => {
    //     const no_show_client = toNoShowClient(doc)
    //     setNoShowClientInit({ document_id: doc.id, no_show_client: no_show_client })
    //   })
    // })

    const unsubscribe = firestore.collection('users')
      .doc(userId).collection('NoShowClient')
      .doc(userId).onSnapshot((doc) => {
        const no_show_client = toNoShowClient(doc)
        setNoShowClientInit({ document_id: doc.id, no_show_client: no_show_client })
      }, (error) => {
        console.error("Error subscribing to NoShow Client:", error);
      });
    return unsubscribe();

  }, [userId]);
  return noShowClientInit;
}

