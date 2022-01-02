// import { auth } from 'firebase';
import React, { useContext, useState, useEffect } from 'react';
import { auth as firsebaseAuth, firestore } from './firebase';
import { NoShowClient, toNoShowClient } from './models/NoShowClient';

interface Auth {
    loggedIn: boolean;
    userId: string;
    credentialId: string;
    permission?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    noShowClient?: NoShowClient;
}

interface AuthInit {
    loading: boolean;
    auth?: Auth;
}

export const AuthContext = React.createContext<Auth>({ loggedIn: false, credentialId: '', userId: '' });

export function useAuth(): Auth {
    return useContext(AuthContext);
}

// // ORIGINAL WORKING VERSION
// export function useAuthInit(): AuthInit {
//     const [authInit, setAuthInit] = useState<AuthInit>({ loading: true });
//     useEffect(() => {
//         return firsebaseAuth.onAuthStateChanged((firebaseUser) => {
//             // console.log('[onAuthStateChanged]:', user)
//             const auth = firebaseUser
//                 ? { loggedIn: true, userId: firebaseUser.uid }
//                 : { loggedIn: false, userId: '' }
//             setAuthInit({ loading: false, auth: auth });
//         });
//     }, []);
//     return authInit;
// }

// // DEVELOPMENT VERSION
export function useAuthInit(): AuthInit {
    const [authInit, setAuthInit] = useState<AuthInit>({ loading: true, auth: { loggedIn: false, userId: '', credentialId: '', permission: '', firstName: '', lastName: '', email: '' } });
    // const [nowShowClient, setNoShowClient] = useState()

    useEffect(() => {
        return firsebaseAuth.onAuthStateChanged((firebaseUser) => {
            // console.log('[onAuthStateChanged]:', firebaseUser)
            // const auth = firebaseUser
            //     ? { loggedIn: false, credentialId: firebaseUser.uid }
            //     : { loggedIn: false, userId: '', credentialId: '', permission: '', firstName: '', lastName: '', email: '' }

            if (firebaseUser) {
                setAuthInit({ loading: true, auth: { loggedIn: false, credentialId: firebaseUser.uid, userId: '' } });
                // setGetCredential(true)
            } else {
                setAuthInit({ loading: false, auth: { loggedIn: false, userId: '', credentialId: '', permission: '', firstName: '', lastName: '', email: '' } });
            }
        });
    }, []);

    useEffect(() => {
        // console.log('Get Gredentials')
        if (authInit.auth.credentialId === '') {
            return;
        }

        console.log("IMPLEMENT ME!!: check the client credential for any changes and update the credential")

        firestore.collection('credentials').doc(authInit.auth.credentialId).onSnapshot((doc) => {
            if (doc.exists) {
                const credential = doc.data()
                // console.log('credential', credential)
                const auth = {
                    loggedIn: true,
                    userId: credential.userId,
                    credentialId: authInit.auth.credentialId,
                    permission: credential.permission,
                    firstName: credential.firstName,
                    lastName: credential.lastName,
                    email: credential.email
                }
                setAuthInit({ loading: false, auth: auth });
            } else {
                setAuthInit({ loading: false, auth: { loggedIn: false, userId: '', credentialId: '', permission: '', firstName: '', lastName: '', email: '' } })
            }
        });
        // if (authInit.auth.credentialId === '') {
        // return unsubscribe()
        // }
        // return unsubscribe()

    }, [authInit.auth.credentialId])

    useEffect(() => {

        console.log('Get NoShowClient')
        if (authInit.auth.userId === '') {
            return;
        }

        var auth = authInit.auth

        // // const unsubscribe = 
        firestore.collection('users').doc(authInit.auth.userId)
            .collection('NoShowClient').doc(authInit.auth.userId).onSnapshot((doc) => {
                // console.log('NoShowClient: ', doc.data())
                if (doc.exists) {
                    // const auth = {
                    //     ...authInit.auth,
                    //     noShowClient: toNoShowClient(doc)
                    // }
                    auth = {
                        ...auth,
                        noShowClient: toNoShowClient(doc)
                    }

                    setAuthInit({ loading: false, auth: auth });
                } else {
                    setAuthInit({ loading: false, auth: { loggedIn: false, userId: '', credentialId: '', permission: '', firstName: '', lastName: '', email: '' } })
                }
            })

        // if (authInit.auth.userId === '') {
        //     return unsubscribe();
        // }

// eslint-disable-next-line
    }, [authInit.auth.userId])

    return authInit;
}




// async 
// await firestore.collection('credentials').doc(firebaseUser.uid).onSnapshot((doc) => {
//     const credential = doc.data()
//     console.log('credential', credential)
//     auth = {
//         loggedIn: true,
//         userId: credential.userId,
//         credentialId: firebaseUser.uid,
//         permission: credential.permission,
//         firstName: credential.firstName,
//         lastName: credential.lastName,
//         email: credential.email
//     }
// });

// export function useAuthInit(): AuthInit {
//     const [authInit, setAuthInit] = useState<AuthInit>({ loading: true });
//     useEffect(() => {

//         return firsebaseAuth.onAuthStateChanged((firebaseUser) => {
//             // console.log('[onAuthStateChanged]:', firebaseUser)
//             var auth: Auth = {
//                 loggedIn: false
//             }

//             if (firebaseUser === null) {
//                 auth = {
//                     loggedIn: false,
//                     userId: '',
//                     credentialId: '',
//                     permission: '',
//                     firstName: '',
//                     lastName: '',
//                     email: ''
//                 }
//             } else {

//                 (async function getCredential() {
//                     await firestore.collection('credentials').doc(firebaseUser.uid).onSnapshot((doc) => {
//                         const credential = doc.data()
//                         console.log('credential',credential)
//                         auth = {
//                             loggedIn: true,
//                             userId: credential.userId,
//                             credentialId: firebaseUser.uid,
//                             permission: credential.permission,
//                             firstName: credential.firstName,
//                             lastName: credential.lastName,
//                             email: credential.email
//                         }
//                     });
//                 })()
//             }

//             // const auth = firebaseUser
//             //     ? { loggedIn: true, userId: firebaseUser.uid }
//             //     : { loggedIn: false, userId: '' }

//             console.log('[onAuthStateChanged] auth:', auth)

//             setAuthInit({ loading: false, auth: auth });
//         });
//     }, []);
//     return authInit;
// }

// const entryRef = firestore.collection('users').doc(userId).collection('NoShowClient')
//     entryRef.get().then((snapshot) => {
//       snapshot.docs.forEach((doc) => {
//         setNoShowClient(toNoShowClient(doc))
//       })
//     })

// return firestore.collection('users').doc(userId)
//             .collection('CommunicationItems').doc(id).onSnapshot((doc) => {
//                 // console.log(doc.data())
//                 setCommunicationItem(toCommunicationItem(doc))
//             })

// async function getCredential(credentialId: string) {
//     const dbAssetRef = firestore.collection('users').doc(userId).collection('Assets').doc(bucketId)
//     return await dbAssetRef.set({ ...assets })
//         .then(function () {
//             console.log("Asset successfully updated!");
//         }).catch(function (error) {
//             // The document probably doesn't exist.
//             console.error("Error updating media: ", error);
//             // setStatus({ loading: false, error: true })
//         });
// }




// export function useAuthInit(): AuthInit {
//     console.log("INSIDE useAuthInit()")

//     const [authInit, setAuthInit] = useState<AuthInit>({ loading: true, auth: null });
//     // let FIREBASE_AUTH null;
//     useEffect(() => {
//         return firsebaseAuth.onAuthStateChanged((firebaseUser) => {
//             // FIREBASE_AUTH = firsebaseAuth.onAuthStateChanged((firebaseUser) => {
//             // console.log('[onAuthStateChanged]:', user)
//             let AUTH = firebaseUser
//                 ? { loggedIn: true, userId: firebaseUser.uid }
//                 : { loggedIn: false, userId: '' }
//             let AUTH_INIT: AuthInit = { loading: false, auth: AUTH, no_show_client: null };

//             // console.log("HAVE AUTHORIASTATION")
//             // AUTH_INIT = getNoShowClient(AUTH_INIT);
//             // console.log("RETUREND FROM getNoShowClient:", AUTH_INIT)
//             setAuthInit(AUTH_INIT);
//         });
//     }, []);

//     // if (authInit.auth && authInit.auth.loggedIn) {
//     console.log("HAVE AUTHORIASTATION", authInit)
//     useEffect(() => {
//         console.log("INSIDE getNoShowClient().useEffect()")
//         if (authInit.auth && authInit.auth.loggedIn) {
//             const unsubscribe = firestore.collection('users')
//                 .doc(authInit.auth.userId).collection('NoShowClient')
//                 .doc(authInit.auth.userId).onSnapshot((doc) => {
//                     const no_show_client = toNoShowClient(doc)
//                     authInit.no_show_client = no_show_client
//                 }, (error) => {
//                     console.error("Error subscribing to NoShow Client:", error);
//                 });

//             // setAuthInit({ loading: false, auth: auth });
//             console.log("RETURNINNG getNoShowClient().useEffect()")
//             return unsubscribe();
//         } else {
//             return;
//         }

//     }, [authInit.auth])

//     // } else {
//     //     console.log("NO AUTHORIASTATION")
//     // }

//     console.log("RETURNING useAuthInit()")
//     return authInit;
// }
// // END DEVELOPMENT VERSION

// export function getNoShowClient(authInit: AuthInit): AuthInit {
//     console.log("INSIDE getNoShowClient()")


//     console.log("RETURNINNG getNoShowClient()")
//     return authInit;
// }

    // }


    //     }, []);

    // if (authInit.auth) (
    //     console.log('Logged in, time to retrieve Client:', authInit)
    // )

    // useEffect(() => {
    //     console.log('\n\n[noShowClient].useNoShowClientInit() Get No Show Client')
    //     const unsubscribe = firestore.collection('users')
    //         .doc(authInit.auth.userId).collection('NoShowClient')
    //         .doc(authInit.auth.userId).onSnapshot((doc) => {
    //             const no_show_client = toNoShowClient(doc)
    //             // setNoShowClientInit({ document_id: doc.id, no_show_client: no_show_client })
    //             authInit.auth.no_show_client = no_show_client
    //         }, (error) => {
    //             console.error("Error subscribing to NoShow Client:", error);
    //         });
    //     return unsubscribe();
//     return FIRBASE_AUTH
// }, [authInit]);

