import {
  IonContent,
  IonHeader,
  IonTitle,
  IonToolbar,
  IonButton,
  IonItem,
  IonLabel,
  IonText,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent,
  IonCardSubtitle,
  IonItemGroup,
  IonRow,
  IonGrid,
  IonCol,
  IonIcon,
  IonAlert,
  IonList,
  IonModal,
  IonLoading,
  IonToast,
  IonFabButton,
  IonFab,
  IonInput,
  IonSelect,
  IonSelectOption,
  IonNote,
  IonPage,
  IonButtons
} from '@ionic/react';
import { informationCircleOutline as infoIcon, bookmarkSharp as editIcon, createSharp as emailIcon, add as addIcon, remove as removeIcon, close as closeIcon, checkmark as tickIcon, cloudUploadSharp as saveIcon, checkmarkCircleOutline as activeIcon, closeCircleOutline as inactiveIcon, trashBinSharp as deleteIcon, personSharp as primaryUserIcon, personAddSharp as addPersonIcon } from 'ionicons/icons';
import React, { useState, useEffect, useReducer } from 'react';
import { auth as firsebaseAuth, firestore } from '../firebase';
import { useAuth } from '../auth';
import { NoShowClient, timezones, Timezone } from '../models/NoShowClient';
import mboProxyInstance from '../mbo-proxy-axios';
import lambdaProxyInstance from '../lambda-proxy-axios';
import { Credential, toCredential } from '../models/Credential';


const SettingsPage: React.FC = () => {

  const { userId, noShowClient, credentialId, permission } = useAuth();
  // const [client_document_id, setClientDocumentId] = useState('');
  const [no_show_client, setNoShowClient] = useState<NoShowClient>();
  // const no_show_client = useNoShowClient()

  const [status, setStatus] = useState({ loading: true, error: false });
  const [alertState, setAlertState] = useState(false)
  const [showToast, setShowToast] = useState(false)
  const [showMboSessionTypesModal, setShowMboSessionTypesModal] = useState(false);
  const [showMboServiceModal, setShowMboServiceModal] = useState(false);
  const [showLocationPreferences, setShowLocationPreferences] = useState(false)
  const [showEmails, setShowEmails] = useState(false);
  const [updateServiceType, setUpdateServiceType] = useState('') //No Show Product or Late Cancel Product
  const [showManageUsersModal, setShowManageUsersModal] = useState(false)
  const [showUsers, setShowUsers] = useState(false)
  const [tempCredential, setTempCredential] = useState<Credential>();
  const [isNewCredential, setNewCredential] = useState(false);

  const [show_mbo_no_show_product_id_info, setShowMboNoShowProductIdInfo] = useState(false);
  const [show_no_show_forgive_frequency_info, setShowNoShowForgiveFrequencyInfo] = useState(false)
  const [show_no_show_forgive_min_info, setShowNoShowForgiveMinInfo] = useState(false)
  const [show_no_show_forgive_max_info, setShowNoShowForgiveMaxInfo] = useState(false)

  const [show_mbo_late_cancel_product_id_info, setMboLateCancelProductIdInfo] = useState(false)
  const [show_late_cancel_forgive_frequency_info, setShowLateCancelForgiveFrequencyInfo] = useState(false)
  const [show_late_cancel_forgive_min_info, setShowLateCancelForgiveMinInfo] = useState(false)
  const [show_late_cancel_forgive_max_info, setShowLateCancelForgiveMaxInfo] = useState(false)

  const [show_mbo_cancel_product_id_info, setMboCancelProductIdInfo] = useState(false)
  const [show_cancel_forgive_frequency_info, setShowCancelForgiveFrequencyInfo] = useState(false)
  const [show_cancel_forgive_min_info, setShowCancelForgiveMinInfo] = useState(false)
  const [show_cancel_forgive_max_info, setShowCancelForgiveMaxInfo] = useState(false)

  const [show_timezone_info, setShowTimezoneInfo] = useState(false)
  const [show_offense_to_billing_hold_days_info, setShowOffenseToBillingHoldDaysInfo] = useState(false)
  const [show_dispute_auto_forgive_info, setShowDisputeAutoForgiveInfo] = useState(false)
  const [show_is_billing_info, setShowIsBillingInfo] = useState(false);
  const [show_will_send_comms_info, setShowWillSendCommsInfo] = useState(false);
  const [show_is_pre_launch_info, setShowIsPreLaunchInfo] = useState(false)
  const [show_class_black_list_info, setShowClassBlackListInfo] = useState(false)

  const [show_charge_non_members_info, setShowChargeNonMembersInfo] = useState(false);

  const [show_credential_permission_info, setShowCredentialPermissionInfo] = useState(false)
  const [show_credential_status_info, setShowCredentialStatusInfo] = useState(false)



  const [tz, setTz] = useState<Timezone>()

  // useEffect(() => {
  //   // console.log('\n\n[SettingsPage].useEffect() Get No Show Client')
  //   const entryRef = firestore.collection('users').doc(userId).collection('NoShowClient')
  //   return entryRef.limit(1).onSnapshot(({ docs }) => {
  //     // setClientDocumentId(docs[0].id)
  //     setNoShowClient(toNoShowClient(docs[0]))
  //   });
  // }, [userId]);

  useEffect(() => {
    console.log("[SettingsPage] noShowClient:", noShowClient)
    setNoShowClient(noShowClient)
  }, [noShowClient])

  useEffect(() => {
    if (typeof no_show_client === 'undefined') {
      setStatus({ loading: true, error: false })
    } else {
      setStatus({ loading: false, error: false })
      setTz({ ...no_show_client.billing_timezone })
    }

  }, [no_show_client])

  useEffect(() => {
    if (typeof no_show_client != 'undefined') {
      dispatch({ type: 'billing_timezone_change', payload: tz })
    }
  }, [tz, no_show_client])

  const initialState = {
    subscription_status: 'DEFAULT',
    mbo_site_id: 123456789,

    mbo_no_show_product_id: '987654321',
    mbo_no_show_product_name: 'No Show Fee Needed',
    mbo_no_show_product_amount: 10.00,

    no_show_forgive_frequency: 'monthly',
    no_show_forgive_min: 0,
    no_show_forgive_max: 20,

    mbo_late_cancel_product_id: '-987654321',
    mbo_late_cancel_product_name: 'Late Cancel Fee Needed',
    mbo_late_cancel_product_amount: 0.00,

    late_cancel_forgive_frequency: 'weekly',
    late_cancel_forgive_min: 0,
    late_cancel_forgive_max: 20,

    mbo_cancel_product_id: '-98765432',
    mbo_cancel_product_name: 'Cancel Fee Needed',
    mbo_cancel_product_amount: 0.00,

    cancel_forgive_frequency: 'monthly',
    cancel_forgive_min: 0,
    cancel_forgive_max: 20,

    is_pre_launch: true,
    is_test: true,
    offense_to_billing_hold_days: 7,
    locations: [],
    class_black_list: [],
    mbo_services: null,
    mbo_session_types: null,

    userCredentials: null
  };
  const [state, dispatch] = useReducer(reducer, initialState);

  // This is ascending order
  function mboNameComparator(a, b) {
    if (a.Name < b.Name) {
      return -1;
    }
    if (a.Name > b.Name) {
      return 1;
    }
    return 0;
  }

  function reducer(state, action) {
    // console.log("[SettingsPage.reducer()] state: " + JSON.stringify(state) + "\naction: " + JSON.stringify(action))
    var word = '';
    var number = 0;

    switch (action.type) {
      case 'set_user_credentials':
        console.log('[SettingsPage] set_user_credentials', action.payload)
        state.userCredentials = action.payload
        return { ...state }
      case 'set_mbo_services':
        // console.log('[SettingsPage] set_mbo_services', action.payload.data)
        const uniqueService = [];
        action.payload.data.map(x => uniqueService.filter(
          a => a.Id === x.Id).length > 0
          ? null
          : uniqueService.push(x));
        uniqueService.sort(mboNameComparator)
        // console.log('[SettingsPage] filterd', unique)
        state.mbo_services = uniqueService
        return { ...state }
      case 'set_mbo_session_types':
        // console.log('[SettingsPage] set_mbo_session_types', action)
        const uniqueSessionTypes = [];
        action.payload.data.map(x => uniqueSessionTypes.filter(
          a => a.Id === x.Id).length > 0
          ? null
          : uniqueSessionTypes.push(x));
        uniqueSessionTypes.sort(mboNameComparator)
        // console.log('[SettingsPage] filterd', uniqueSessionTypes)
        state.mbo_session_types = uniqueSessionTypes
        return { ...state }
      case 'mbo_no_show_service_change':
        no_show_client.mbo_no_show_product_id = action.payload.Id;
        no_show_client.mbo_no_show_product_name = action.payload.Name;
        no_show_client.mbo_no_show_product_amount = action.payload.Price
        return { ...state }
      case 'mbo_late_cancel_service_change':
        no_show_client.mbo_late_cancel_product_id = action.payload.Id;
        no_show_client.mbo_late_cancel_product_name = action.payload.Name;
        no_show_client.mbo_late_cancel_product_amount = action.payload.Price;
        return { ...state }

      case 'mbo_cancel_service_update':
        no_show_client.mbo_cancel_product_id = action.payload.Id;
        no_show_client.mbo_cancel_product_name = action.payload.Name;
        no_show_client.mbo_cancel_product_amount = action.payload.Price;
        return { ...state }

      case 'no_show_forgive_frequency_change':
        word = no_show_client.no_show_forgive_frequency.toLowerCase() === 'weekly' ? 'Monthly' : 'Weekly';
        no_show_client.no_show_forgive_frequency = word;
        return { ...state }
      case 'no_show_forgive_min_increment':
        number = no_show_client.no_show_forgive_min < 100 ? no_show_client.no_show_forgive_min + 1 : 100;
        no_show_client.no_show_forgive_min = number;
        return { ...state };
      case 'no_show_forgive_min_decrement':
        number = no_show_client.no_show_forgive_min > 0 ? no_show_client.no_show_forgive_min - 1 : 0;
        no_show_client.no_show_forgive_min = number;
        return { ...state };
      case 'no_show_forgive_max_increment':
        number = no_show_client.no_show_forgive_max < 100 ? no_show_client.no_show_forgive_max + 1 : 100;
        no_show_client.no_show_forgive_max = number;
        return { ...state };
      case 'no_show_forgive_max_decrement':
        number = no_show_client.no_show_forgive_max > 0 ? no_show_client.no_show_forgive_max - 1 : 0;
        no_show_client.no_show_forgive_max = number;
        return { ...state };
      case 'late_cancel_forgive_frequency_change':
        word = no_show_client.late_cancel_forgive_frequency.toLowerCase() === 'weekly' ? 'Monthly' : 'Weekly';
        no_show_client.late_cancel_forgive_frequency = word;
        return { ...state }
      case 'late_cancel_forgive_min_increment':
        number = no_show_client.late_cancel_forgive_min < 100 ? no_show_client.late_cancel_forgive_min + 1 : 100;
        no_show_client.late_cancel_forgive_min = number;
        return { ...state };
      case 'late_cancel_forgive_min_decrement':
        number = no_show_client.late_cancel_forgive_min > 0 ? no_show_client.late_cancel_forgive_min - 1 : 0;
        no_show_client.late_cancel_forgive_min = number
        return { ...state };
      case 'late_cancel_forgive_max_increment':
        number = no_show_client.late_cancel_forgive_max < 100 ? no_show_client.late_cancel_forgive_max + 1 : 100;
        no_show_client.late_cancel_forgive_max = number;
        return { ...state };
      case 'late_cancel_forgive_max_decrement':
        number = no_show_client.late_cancel_forgive_max > 0 ? no_show_client.late_cancel_forgive_max - 1 : 0;
        no_show_client.late_cancel_forgive_max = number;
        return { ...state };

      case 'cancel_forgive_frequency_change':
        word = no_show_client.cancel_forgive_frequency.toLowerCase() === 'weekly' ? 'Monthly' : 'Weekly';
        no_show_client.cancel_forgive_frequency = word;
        return { ...state }
      case 'cancel_forgive_min_decrement':
        number = no_show_client.cancel_forgive_min > 0 ? no_show_client.cancel_forgive_min - 1 : 0;
        no_show_client.cancel_forgive_min = number;
        return { ...state };
      case 'cancel_forgive_min_increment':
        number = no_show_client.cancel_forgive_min < 100 ? no_show_client.cancel_forgive_min + 1 : 100;
        no_show_client.cancel_forgive_min = number;
        return { ...state };
      case 'cancel_forgive_max_decrement':
        number = no_show_client.cancel_forgive_max > 0 ? no_show_client.cancel_forgive_max - 1 : 0;
        no_show_client.cancel_forgive_max = number;
        return { ...state };
      case 'cancel_forgive_max_increment':
        number = no_show_client.cancel_forgive_min < 100 ? no_show_client.cancel_forgive_min + 1 : 100;
        no_show_client.cancel_forgive_min = number;
        return { ...state };

      case 'is_pre_launch_change':
        no_show_client.is_pre_launch = !no_show_client.is_pre_launch;
        return { ...state };
      case 'is_test_change':
        no_show_client.is_test = !no_show_client.is_test;
        return { ...state }
      case 'offense_to_billing_hold_days_increment':
        number = no_show_client.offense_to_billing_hold_days < 14 ? no_show_client.offense_to_billing_hold_days + 1 : 14;
        no_show_client.offense_to_billing_hold_days = number;
        return { ...state };
      case 'offense_to_billing_hold_days_decrement':
        number = no_show_client.offense_to_billing_hold_days > 0 ? no_show_client.offense_to_billing_hold_days - 1 : 0;
        no_show_client.offense_to_billing_hold_days = number;
        return { ...state };
      case 'dispute_auto_forgive_increment':
        number = no_show_client.dispute_auto_forgive < 100 ? no_show_client.dispute_auto_forgive + 1 : 100;
        no_show_client.dispute_auto_forgive = number;
        return { ...state };
      case 'dispute_auto_forgive_decrement':
        number = no_show_client.dispute_auto_forgive > 0 ? no_show_client.dispute_auto_forgive - 1 : 0;
        no_show_client.dispute_auto_forgive = number;
        return { ...state };
      case 'will_run_billing_change':
        no_show_client.locations.map(location => {
          if (location.location_id === action.payload.location_id) {
            location.is_billing = !location.is_billing
          }
          return location;
        })
        return { ...state }
      case 'class_black_list_change':
        // console.log('\n\nclass_black_list_change', action.payload)
        var found = no_show_client.class_black_list.find(item => item.session_type_id === action.payload.class_session_id);
        if (typeof found === 'undefined') {
          no_show_client.class_black_list.push({ session_type_name: action.payload.class_session_name, session_type_id: action.payload.class_session_id });
        } else {
          no_show_client.class_black_list = no_show_client.class_black_list.filter(item => item.session_type_id !== action.payload.class_session_id)
        }
        return { ...state }
      case 'location_name_alias_change':
        no_show_client.locations.map(location => {
          if (location.location_id === action.payload.location_id) {
            location.location_name_alias = action.payload.location_name_alias
          }
          return location
        })
        // console.log(word)
        return { ...state }
      case 'location_name_abbreviated_change':
        word = action.payload.location_name_abbreviated.substring(0, 2).toUpperCase()
        no_show_client.locations.map(location => {
          if (location.location_id === action.payload.location_id) {
            location.location_name_abbreviated = word
          }
          return location
        })
        // console.log(no_show_client.locations)
        return { ...state }
      case 'location_manager_name_change':
        no_show_client.locations.map(location => {
          if (location.location_id === action.payload.location_id) {
            location.location_manager_name = action.payload.location_manager_name
          }
          return location
        })
        // console.log(word)
        return { ...state }

      case 'location_will_send_comms_change':
        // console.log('location_will_send_comms_change', action)
        no_show_client.locations.map(location => {
          if (location.location_id === action.payload.location_id) {
            location.will_send_comms = !location.will_send_comms
          }
          return location
        })
        return { ...state }

      case 'billing_timezone_change':
        // console.log('billing_timezone_change', action)
        no_show_client.billing_timezone = { ...action.payload }
        // set
        return { ...state }

      case 'update_will_charge_non_members':
        no_show_client.will_charge_non_members = !no_show_client.will_charge_non_members
        return { ...state }

      default:
        throw new Error();
    }

  }

  const openMboServiceUpdate = (updateType: string) => {
    // console.log('[SettingsPage] openMboServiceUpdate')
    setUpdateServiceType(updateType);
    setShowMboServiceModal(true);
    getMboServices();

  }

  const closeMboServiceUpdate = (service) => {
    if (service !== null && updateServiceType === 'no_show_service_update') {
      // console.log('no_show_service_update: ', service)
      dispatch({ type: "mbo_no_show_service_change", payload: service })
    }
    if (service !== null && updateServiceType === 'late_cancel_service_update') {
      // console.log('mbo_late_cancel_service_change: ', service)
      dispatch({ type: "mbo_late_cancel_service_change", payload: service })
    }
    if (service !== null && updateServiceType === 'cancel_service_update') {
      // console.log('cancel_service_update: ', service)
      dispatch({ type: "mbo_cancel_service_update", payload: service })
    }
    setUpdateServiceType('');
    setShowMboServiceModal(false)
  }

  const handleClassBlackListUpdate = (session_type) => {
    dispatch({
      type: "class_black_list_change", payload: {
        class_session_id: session_type.Id,
        class_session_name: session_type.Name
      }
    })
  }

  const handleUpdate = () => {
    // console.log("[SettingsPage.handleUpdate()] noShowClient: ", no_show_client)
    setAlertState(true)
  }

  const processUpdate = async () => {
    setAlertState(false)
    setStatus({ loading: true, error: false })

    const entryRef = firestore.collection('users').doc(userId).collection('NoShowClient').doc(userId)

    return await entryRef.set({ ...no_show_client })
      .then(function () {
        console.log("Document successfully updated!");
        setStatus({ loading: false, error: false })
        setShowToast(true)
      })
      .catch(function (error) {
        // The document probably doesn't exist.
        console.error("Error updating document: ", error);
        setStatus({ loading: false, error: true })
      });
  }

  const getMboServices = async () => {
    setStatus({ loading: true, error: false })
    if (state.mbo_services === null) {
      return await mboProxyInstance.get(`services/${no_show_client?.mbo_site_id}`, {
      }).then(function (response) {
        dispatch({ type: "set_mbo_services", payload: response })
        setStatus({ loading: false, error: false })
      }).catch(function (error) {
        console.error("Error getting Mbo Services: ", error);
        setStatus({ loading: false, error: true })
      });
    } else {
      setStatus({ loading: false, error: false })
      return;
    }
  }

  const openMboSessionTypesUpdate = () => {
    // console.log('[SettingsPage] openMboSessionTypesUpdate')
    setShowMboSessionTypesModal(true);
    getMboSesionTypes();
  }

  const getMboSesionTypes = async () => {
    setStatus({ loading: true, error: false })
    if (state.mbo_session_types === null) {
      return await mboProxyInstance.get(`sessionTypes/${no_show_client?.mbo_site_id}`, {
      }).then((response) => {
        console.log('[Settings] getMboSesionTypes: ', response)
        dispatch({ type: "set_mbo_session_types", payload: response })
        setStatus({ loading: false, error: false })
      }).catch((error) => {
        console.error("Error getting Mbo Session Types", error)
        setStatus({ loading: false, error: true })
      })
    } else {
      setStatus({ loading: false, error: false })
      return;
    }
  }

  const openManageUsersModal = (credential: Credential) => {
    // getUserCredentials();
    console.log("[SettingsPage] credential:", credential)
    setTempCredential(credential)
    setShowManageUsersModal(true)
  }

  const closeManageUsersModal = () => {
    setTempCredential(null)
    setShowManageUsersModal(false)
    setNewCredential(false)
  }

  const openUsers = () => {
    if (showUsers === false) {
      getUserCredentials();
    }
    setShowUsers(!showUsers)
  }

  const getUserCredentials = async () => {
    setStatus({ loading: true, error: false })
    return await firestore.collection('credentials').where("userId", "==", userId).get().then((snapshot) => {
      var CREDENTIALS = []
      snapshot.forEach((doc) => {
        CREDENTIALS.push(toCredential(doc))
      });
      console.log('[Settings] getUserCredentials: ', CREDENTIALS)
      dispatch({ type: "set_user_credentials", payload: CREDENTIALS })
      setStatus({ loading: false, error: false })
    }).catch((error) => {
      console.error("Error getting user credentials", error)
      setStatus({ loading: false, error: true })
    })
  }

  const handleAddOrUpdateCredential = async () => {
    setStatus({ loading: true, error: false })
    console.log('IMPLEMENT ME SERVER SIDE: Add or Update Credental', tempCredential)

    // https://firebase.google.com/docs/auth/admin/manage-users#python_3

    if (isNewCredential) {
      // Define the string
      // var verificationString = `${tempCredential.firstName}:${tempCredential.lastName}:${tempCredential.email}:${tempCredential.permission}:${tempCredential.userId}`;

      // Encode the String
      // var verification = btoa(verificationString);

      // Trigger create user service
      console.log("IMPLEMENT ME: Trigger New User service: ", tempCredential);
      return await lambdaProxyInstance.post('credential_request', { type: 'create_credential', data: tempCredential })
        .then(function (response) {
          console.log('Create Credental response [create_credential]: ', response.data.message)
          setStatus({ loading: false, error: false })
          closeManageUsersModal()
          openUsers()
          setShowToast(true)
        }).catch(function (error) {
          console.error("Error Creating Credental: ", error);
          setStatus({ loading: false, error: true })
        })

    } else {
      console.log('IMPLEMENT ME: Update Credental', tempCredential)
      return await lambdaProxyInstance.post('update_credential', { type: 'update_credential', data: tempCredential })
        .then(function (response) {
          console.log('Update Credental response [update_credential]: ', response.data.message)
          setStatus({ loading: false, error: false })
          closeManageUsersModal()
          openUsers()
          setShowToast(true)
        }).catch(function (error) {
          console.error("Error Updating Credental: ", error);
          setStatus({ loading: false, error: true })
        })
    }

    // setStatus({ loading: false, error: false })
    // setShowToast(true)
  }

  const handleResetPasswordCredential = async () => {
    console.log('IMPLEMENT ME: Reset Password', tempCredential)
    return await lambdaProxyInstance.post('reset_credential_password', { type: 'reset_credential_password', data: tempCredential })
      .then(function (response) {
        console.log('Reset Credental Password response [reset_credential_password]: ', response.data.message)
        setStatus({ loading: false, error: false })
        closeManageUsersModal()
        openUsers()
        setShowToast(true)
      }).catch(function (error) {
        console.error("Error Creating Credental: ", error);
        setStatus({ loading: false, error: true })
      })
  }


  const addNewCredential = () => {
    setTempCredential({
      firstName: '',
      lastName: '',
      email: '',
      permission: 'Read Only',
      status: 'Active',
      userId: userId
    })
    setNewCredential(true)
    setShowManageUsersModal(true);

    console.log('IMPLEMENT ME!!!')
  }

  return (
    <IonPage>

      <IonFab vertical="bottom" horizontal="end">
        <IonFabButton onClick={handleUpdate}>
          <IonIcon icon={saveIcon} />
        </IonFabButton>
      </IonFab>

      <IonToast
        isOpen={showToast}
        position="top"
        onDidDismiss={() => setShowToast(false)}
        message="Your settings have been saved."
        duration={2000}
      />

      {/* Update NoShowClient Warning */}
      <IonAlert
        isOpen={alertState}
        onDidDismiss={() => setAlertState(false)}
        header={'Are you sure?'}
        // subHeader={'Press confirm to update.'}
        message={'Press confirm to update.'}
        buttons={[{
          text: 'Cancel',
        },
        {
          text: 'Confirm',
          role: 'update',
          handler: () => { processUpdate() }
        }]}
      />

      {/* Update Mindbody Service Modal */}
      <IonModal
        isOpen={showMboServiceModal}
        onDidDismiss={() => closeMboServiceUpdate(null)}
      >
        <IonToolbar>
          <IonButton
            slot="end"
            color="primary"
            fill="clear"
            onClick={() => closeMboServiceUpdate(null)}>
            <IonIcon slot="icon-only" icon={closeIcon} /></IonButton>
          <IonTitle class="ion-text-capitalize">{`${updateServiceType.replace(/_/g, ' ')}`}</IonTitle>
        </IonToolbar>

        {status.loading ? null :
          <IonContent className="ion-padding">
            <IonList>{state.mbo_services?.map((service) =>
              <IonItem key={`${service.Id}-${service.Name}`}
                button onClick={() => closeMboServiceUpdate(service)}>
                <IonLabel className="ion-text-wrap"
                  color={(updateServiceType === 'no_show_service_update' && service.Id === no_show_client?.mbo_no_show_product_id)
                    || (updateServiceType === 'late_cancel_service_update' && service.Id === no_show_client?.mbo_late_cancel_product_id)
                    || (updateServiceType === 'cancel_service_update' && service.Id === no_show_client?.mbo_cancel_product_id)
                    ? "success" : "default"}>
                  <h2>{service.Name}</h2>
                  <h3>${service.Price.toFixed(2)}</h3>
                </IonLabel>{
                  (updateServiceType === 'no_show_service_update' && service.Id === no_show_client?.mbo_no_show_product_id)
                    || (updateServiceType === 'late_cancel_service_update' && service.Id === no_show_client?.mbo_late_cancel_product_id)
                    || (updateServiceType === 'cancel_service_update' && service.Id === no_show_client?.mbo_cancel_product_id)
                    ? <IonIcon slot="end" color="success" icon={tickIcon}
                    /> : null
                }
              </IonItem>
            )}</IonList>
            <IonButton expand="block" color="primary" fill="solid" onClick={() => closeMboServiceUpdate(null)}>Close</IonButton>
          </IonContent>
        }
      </IonModal>

      {/* Update Mindbody SessionTypes Modal */}
      <IonModal
        isOpen={showMboSessionTypesModal}
        onDidDismiss={() => setShowMboSessionTypesModal(false)}>
        <IonToolbar>
          <IonButton
            slot="end"
            color="primary"
            fill="clear"
            onClick={() => setShowMboSessionTypesModal(false)}>
            <IonIcon slot="icon-only" icon={closeIcon} /></IonButton>
          <IonTitle className="ion-text-wrap">Mindbody Session Types</IonTitle>
        </IonToolbar>
        {status.loading ? null :
          <IonContent className="ion-padding">
            <IonList>{state.mbo_session_types?.map((session_type) =>
              <IonItem key={`${session_type.Id}-${session_type.Name}`}
                button onClick={() => handleClassBlackListUpdate(session_type)}>
                <IonLabel className="ion-text-wrap"
                  color={
                    no_show_client?.class_black_list.find(item => item.session_type_id === session_type.Id)
                      ? "success" : "default"
                  }>
                  <h2>{session_type.Name}</h2>
                  <p>{session_type.Id}</p>

                </IonLabel>
                {
                  no_show_client?.class_black_list.find(item => item.session_type_id === session_type.Id)
                    ? <IonIcon slot="end" color="success" icon={tickIcon} />
                    : <IonIcon slot="end" color="primary" icon={addIcon} /> // Add Item to class_black_list
                }
              </IonItem>
            )}</IonList>
            <IonButton expand="block" color="primary" fill="solid" onClick={() => setShowMboSessionTypesModal(false)}>Close</IonButton>
          </IonContent>
        }
      </IonModal>


      {/* Manage Users */}
      <IonModal
        isOpen={showManageUsersModal}
        onDidDismiss={() => closeManageUsersModal()}>
        <IonToolbar>
          <IonButton slot="end" color="primary" fill="clear" onClick={() => closeManageUsersModal()}>
            <IonIcon slot="icon-only" icon={closeIcon} /></IonButton>
          <IonTitle className="ion-text-wrap">Manage Users</IonTitle>
        </IonToolbar>
        <IonContent className="ion-padding">
          <IonCard>

            {isNewCredential ?
              <IonCardHeader>
                <IonCardTitle>Create User</IonCardTitle>
              </IonCardHeader>
              :
              <IonCardHeader>
                <IonCardTitle>{`${tempCredential?.firstName} ${tempCredential?.lastName}`}</IonCardTitle>
                <IonCardSubtitle>{`${tempCredential?.email}`}</IonCardSubtitle>
              </IonCardHeader>
            }

            <IonCardContent>
              <IonItemGroup className="ion-padding-bottom">
                <IonItem>
                  <IonLabel position="stacked">First Name</IonLabel>
                  <IonInput placeholder="First Name" value={tempCredential?.firstName} onIonChange={e => setTempCredential({ ...tempCredential, firstName: e.detail.value })}></IonInput>
                </IonItem>
                <IonItem>
                  <IonLabel position="stacked">Last Name</IonLabel>
                  <IonInput placeholder="Last Name" value={tempCredential?.lastName} onIonChange={e => setTempCredential({ ...tempCredential, lastName: e.detail.value })}></IonInput>
                </IonItem>
                {/* {isNewCredential ? */}
                <IonItem>
                  <IonLabel position="stacked">Email</IonLabel>
                  <IonInput placeholder="Email" type="email" value={tempCredential?.email} onIonChange={e => setTempCredential({ ...tempCredential, email: e.detail.value })}></IonInput>
                </IonItem>
                {/* : null} */}

                {/* Credential: Permission */}
                {/* Prevent Update of Primary Account */}
                {tempCredential?.credentialId === userId ? null :
                  <IonItem>
                    <IonIcon onClick={() => setShowCredentialPermissionInfo(!show_credential_permission_info)} slot="start" icon={infoIcon} color="primary"></IonIcon>
                    <IonLabel>Permission</IonLabel>
                    <IonSelect interface="popover" value={tempCredential?.permission} onIonChange={e => setTempCredential({ ...tempCredential, permission: e.detail.value })}>
                      <IonSelectOption value="Full Access">Full Access</IonSelectOption>
                      <IonSelectOption value="Administrator">Administrator</IonSelectOption>
                      <IonSelectOption value="Read Only">Read Only</IonSelectOption>
                    </IonSelect>
                  </IonItem>
                }

                {/* Information Element: show_credential_permission_info */}
                {show_credential_permission_info ?
                  <IonItem onClick={() => setShowCredentialPermissionInfo(!show_credential_permission_info)}>
                    <IonNote class="ion-padding-start">
                      <hr />
                      <h5>This is the <b>Permission</b> that is to applied to the <b>Users Account</b> for access to the <b>No Show App</b>.</h5>
                      <hr />
                      <p><b>Full Access:</b> Unrestricted access to all features including billing information.</p>
                      <hr />
                      <p><b>Administrator:</b> access to <em>Settings</em>, <em>Reports</em> and <em>Dispute Management</em>.</p>
                      <hr />
                      <p><b>Read Only:</b> Read Only access to <em>Reports</em> and <em>Dispute Management</em>.</p>
                      <hr />
                    </IonNote>
                  </IonItem>
                  : null}

                {/* Credential: Status */}
                {/* Prevent Deletion of Primary Account */}
                {isNewCredential || userId === tempCredential?.credentialId ? null :
                  <IonItem>
                    <IonIcon onClick={() => setShowCredentialStatusInfo(!show_credential_status_info)} slot="start" icon={infoIcon} color="primary"></IonIcon>
                    <IonLabel>Status</IonLabel>
                    <IonSelect interface="popover" value={tempCredential?.status} onIonChange={e => setTempCredential({ ...tempCredential, status: e.detail.value })}>
                      <IonSelectOption value="Active">Active</IonSelectOption>
                      <IonSelectOption value="Disabled">Disabled</IonSelectOption>
                      <IonSelectOption value="Delete">Delete</IonSelectOption>
                    </IonSelect>
                  </IonItem>
                }

                {/* Information Element: show_credential_status_info */}
                {show_credential_status_info ?
                  <IonItem onClick={() => setShowCredentialStatusInfo(!show_credential_status_info)}>
                    <IonNote class="ion-padding-start">
                      <hr />
                      <h5>This is the <b>Status</b> that is to applied to the <b>Users Account</b> for access to the <b>No Show App</b>.</h5>
                      <hr />
                      <p><b>Active: </b>the user will be able to login.</p>
                      <hr />
                      <p><b>Disabled: </b>the user <b><em>not</em></b> be able to login but the account will exist and may be reactivated.</p>
                      <hr />
                      <p><b>Delete: </b>the users account will be <b><em>deleted</em></b>. This can not be undone.</p>
                      <hr />
                    </IonNote>
                  </IonItem>
                  : null}

              </IonItemGroup>
              {isNewCredential ? null :
                <IonButton
                  expand="block"
                  color="warning"
                  fill='outline'
                  onClick={handleResetPasswordCredential}>Reset Password
                </IonButton>
              }
              <IonButton
                expand="block"
                color={tempCredential?.status === 'Delete' ? "danger" : "primary"}
                onClick={handleAddOrUpdateCredential}>{isNewCredential ? 'Create User' : tempCredential?.status === 'Delete' ? 'Delete User' : 'Update User'}
              </IonButton>

            </IonCardContent>
          </IonCard>
        </IonContent>
      </IonModal>


      {/* Main */}
      <IonHeader>
        <IonToolbar>
          <IonTitle>Settings</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent className="ion-padding">
        <IonGrid fixed>
          <IonRow>
            <IonCol>
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle color={no_show_client?.subscription_status === 'ACTIVE' ? 'success' : 'danger'}>{no_show_client?.subscription_status}</IonCardTitle>
                  <IonCardSubtitle>
                    <IonText>{no_show_client?.mbo_site_id}</IonText>
                  </IonCardSubtitle>
                </IonCardHeader>
                <IonCardContent>
                  <IonItemGroup>
                    {/* <IonItem button routerLink={`/v1/email`}> */}
                    <IonItem button onClick={() => setShowEmails(!showEmails)}>
                      <IonLabel color="primary" className="ion-text-wrap">Emails</IonLabel>
                      <IonIcon
                        slot="start"
                        color={showEmails ? "primary" : "primary"}
                        icon={showEmails ? closeIcon : emailIcon} />
                    </IonItem>
                    {!showEmails ? null :
                      <IonList>
                        <IonItemGroup>
                          <IonItem button routerLink={`/v1/settings/comms/pre_launch_warning_email`}><IonLabel class="ion-text-capitalize ion-text-wrap">pre-launch warning</IonLabel></IonItem>
                          <IonItem button routerLink={`/v1/settings/comms/late_cancel_warning_email`}><IonLabel class="ion-text-capitalize ion-text-wrap">late cancel warning</IonLabel></IonItem>
                          <IonItem button routerLink={`/v1/settings/comms/late_cancel_charge_email`}><IonLabel class="ion-text-capitalize ion-text-wrap">late cancel charge</IonLabel></IonItem>
                          <IonItem button routerLink={`/v1/settings/comms/no_show_warning_email`}><IonLabel class="ion-text-capitalize ion-text-wrap">no show warning</IonLabel></IonItem>
                          <IonItem button routerLink={`/v1/settings/comms/no_show_charge_email`}><IonLabel class="ion-text-capitalize ion-text-wrap">no show charge</IonLabel></IonItem>
                          <IonItem button routerLink={`/v1/entries`}><IonLabel class="ion-text-capitalize ion-text-wrap">TEST</IonLabel></IonItem>
                        </IonItemGroup>
                      </IonList>
                    }

                    <IonItem button onClick={() => openUsers()}>
                      <IonLabel color="primary" className="ion-text-wrap">Manage Users</IonLabel>
                      <IonIcon
                        slot="start"
                        color={showUsers ? "primary" : "primary"}
                        icon={showUsers ? closeIcon : emailIcon}
                      />
                    </IonItem>

                    {!showUsers ? null :
                      <IonList>
                        <IonItem detail={false} onClick={() => addNewCredential()}>
                          <IonLabel color='secondary'>Add User</IonLabel>
                          <IonIcon slot="start" icon={addPersonIcon} color='secondary' />
                        </IonItem>
                        {
                          state.userCredentials?.map((credential: Credential) =>
                            <IonItem key={`${credential.userId}::${credential.email}`} detail={true} onClick={() => openManageUsersModal(credential)}>
                              <IonIcon slot="start" icon={credential.status === 'Active' ? activeIcon : inactiveIcon} color={credential.status === 'Active' ? 'success' : credential.status === 'Pending' ? 'warning' : 'danger'} />
                              <IonLabel>{`${credential.firstName} ${credential.lastName}`}</IonLabel>
                              {credential.credentialId === credential.userId ? 
                                <IonIcon slot="end" icon={primaryUserIcon} color="warning" /> :
                                null
                              }

                              {/* <IonButton slot="end" color="danger" fill="clear" onClick={() => console.log("IMPLEMENT DELETE CREDENTIAL")}>
                                <IonIcon slot="icon-only" icon={deleteIcon} />
                              </IonButton> */}
                            </IonItem>
                          )
                        }
                      </IonList>
                    }

                    <IonItem button onClick={() => setShowLocationPreferences(!showLocationPreferences)}>
                      <IonLabel color="primary" className="ion-text-wrap">Location Preferrences</IonLabel>
                      <IonIcon
                        slot="start"
                        color={showLocationPreferences ? "primary" : "primary"}
                        icon={showLocationPreferences ? closeIcon : emailIcon}
                      />
                    </IonItem>

                  </IonItemGroup>

                  {/* Locations */}
                  {!showLocationPreferences ? null :
                    <IonItemGroup>
                      {
                        no_show_client?.locations.map((location, index) =>
                          <IonGrid key={`${location.location_id}-${location.location_name}`}>
                            <IonRow><IonCol><IonLabel>{`${location.location_name}`}</IonLabel></IonCol></IonRow>
                            <IonRow>
                              {/* <IonItem key={`${location.location_id}-${location.location_name}`}> */}
                              <IonCol size="12" size-md="6">
                                <IonItem>
                                  <IonLabel position="stacked">Alias</IonLabel>
                                  <IonInput
                                    type="text"
                                    value={location.location_name_alias}
                                    onIonChange={(event) => dispatch({ type: 'location_name_alias_change', payload: { location_id: location.location_id, location_name_alias: event.detail.value } })}></IonInput>
                                </IonItem>
                              </IonCol>
                              <IonCol size="12" size-md="6">
                                <IonItem>
                                  <IonLabel position="stacked">Abbreviation</IonLabel>
                                  <IonInput
                                    type="text"
                                    value={no_show_client?.locations[index].location_name_abbreviated}
                                    onIonChange={(event) => dispatch({ type: 'location_name_abbreviated_change', payload: { location_id: location.location_id, location_name_abbreviated: event.detail.value } })}></IonInput>
                                </IonItem>
                              </IonCol>
                            </IonRow>
                            <IonRow>
                              <IonCol>
                                <IonItem>
                                  <IonLabel position="stacked">Manager</IonLabel>
                                  <IonInput
                                    type="text"
                                    placeholder="Who is the manager?"
                                    value={no_show_client?.locations[index].location_manager_name}
                                    onIonChange={(event) => dispatch({ type: 'location_manager_name_change', payload: { location_id: location.location_id, location_manager_name: event.detail.value } })}></IonInput>
                                </IonItem>
                              </IonCol>
                            </IonRow>
                          </IonGrid>
                        )
                      }
                      <IonButton expand="block" fill="clear" onClick={() => setShowLocationPreferences(false)}>Close</IonButton>
                    </IonItemGroup>
                  }
                </IonCardContent>
              </IonCard>
            </IonCol>
          </IonRow>


          {/* No Show Configuration Options */}
          {permission === 'Full Access' ?
            <IonRow class="ion-align-items-center">

              <IonCol size="12" size-lg="4">
                <IonCard>
                  <IonCardHeader>
                    <IonCardTitle color="secondary">No Show</IonCardTitle>
                  </IonCardHeader>
                  <IonCardContent>
                    <IonItemGroup>

                      {/* No Show: Product Id */}
                      <IonItem onClick={() => setShowMboNoShowProductIdInfo(!show_mbo_no_show_product_id_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Mindbody Online Service</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: show_mbo_no_show_product_id */}
                      {show_mbo_no_show_product_id_info ?
                        <IonItem onClick={() => setShowMboNoShowProductIdInfo(!show_mbo_no_show_product_id_info)}>
                          {/* <IonItem> */}
                          <IonNote class="ion-padding-start">
                            <h5>This is the <b>Mindbody Online Serivce</b> that is to applied to the <b>Clients Account</b> for a <u><b>No Show Charge</b></u>.</h5>
                            <hr />
                            <p>You can set the price, name and other attributes in your Mindbody Online account.</p>
                            <hr />
                            <p><u>Note</u>: any changes made to this Serivce via Mindbody Online will be automatically applied.</p>
                            <hr />
                            <p>That is to say, if you change the price of the No Show Service in Mindbody Online the new updated price will be applied for a No Show Charge.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: mbo_no_show_product_id */}
                      <IonItem key={no_show_client?.mbo_no_show_product_id}
                        button onClick={() => openMboServiceUpdate('no_show_service_update')}>
                        {/* <IonText><strong>${state.mbo_no_show_product_amount} {state.mbo_no_show_product_name}</strong></IonText> */}
                        <IonLabel color="default" className="ion-text-wrap">
                          <h2>{no_show_client?.mbo_no_show_product_name}</h2>
                          <h3>${no_show_client?.mbo_no_show_product_amount.toFixed(2)}</h3>
                        </IonLabel>
                        <IonIcon slot="start" color="secondary" icon={editIcon} />
                      </IonItem>

                      {/* No Show: Forgive Reset Cycle */}
                      <IonItem onClick={() => setShowNoShowForgiveFrequencyInfo(!show_no_show_forgive_frequency_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Forgive Reset Frequency</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: no_show_forgive_frequency */}
                      {show_no_show_forgive_frequency_info ?
                        <IonItem onClick={() => setShowNoShowForgiveFrequencyInfo(!show_no_show_forgive_frequency_info)}>
                          <IonNote class="ion-padding-start">

                            <h5>This is the <b>Length of Time</b> for each <b>Forgive Cycle Period</b>.</h5>
                            <hr />
                            <p><u>Note</u>: can be <em>Every Week</em> <b>or</b> <em>Every Month</em>.</p>
                            <hr />
                            <p>That is to say, if this value is <em>Every Month</em>, the number of No Shows forgiven resets to 0 on the 1<sup>st</sup> of every month</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: no_show_forgive_frequency */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill={no_show_client?.no_show_forgive_frequency.toLowerCase() === 'weekly' ? 'solid' : "outline"}
                              onClick={() => dispatch({ type: 'no_show_forgive_frequency_change' })}>
                              Every Week
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill={no_show_client?.no_show_forgive_frequency.toLowerCase() === 'monthly' ? 'solid' : "outline"}
                              onClick={() => dispatch({ type: 'no_show_forgive_frequency_change' })}>
                              Every Month
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>

                      {/* No Show: Number Of Forgives */}
                      <IonItem onClick={() => setShowNoShowForgiveMinInfo(!show_no_show_forgive_min_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Number of Forgives</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: no_show_forgive_min */}
                      {show_no_show_forgive_min_info ?
                        <IonItem onClick={() => setShowNoShowForgiveMinInfo(!show_no_show_forgive_min_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>A <b>No Show Charge</b> will <b><u>not</u></b> be applied <b><u>if</u></b> the number of times the Client <b>has</b> No Showed <b>is</b> <b>less than or equal</b> this value.</h5>
                            <hr />
                            <p><u>Note</u>: this resets to 0 every week or month depending on the <b>No Show Forgive Reset Cycle</b>.</p>
                            <hr />
                            <p>That is to say, if this value is 1, the first No Show will be forgiven but every No Show after this will be charged.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: no_show_forgive_min */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'no_show_forgive_min_decrement' })}>
                              <IonIcon icon={removeIcon} />
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonText class="ion-text-center"><h1>{no_show_client?.no_show_forgive_min}</h1></IonText>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'no_show_forgive_min_increment' })}>
                              <IonIcon icon={addIcon} />
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>

                      {/* No Show: Max Number Of Charges */}
                      <IonItem onClick={() => setShowNoShowForgiveMaxInfo(!show_no_show_forgive_max_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Maximum Number of Charges</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: no_show_forgive_max */}
                      {show_no_show_forgive_max_info ?
                        <IonItem onClick={() => setShowNoShowForgiveMaxInfo(!show_no_show_forgive_max_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>A <b>No Show Charge</b> will <b><u>not</u></b> be applied <b><u>if</u></b> the number of times the Client <b>has</b> No Showed <b>is</b> <b>more than or equal</b> this value.</h5>
                            <hr />
                            <p>That is to say, if this value is 10, the 10<sup>th</sup> No Show and every No Show after will be forgiven.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: no_show_forgive_max */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'no_show_forgive_max_decrement' })}>
                              <IonIcon icon={removeIcon} />
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonText class="ion-text-center"><h1>{no_show_client?.no_show_forgive_max}</h1></IonText>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'no_show_forgive_max_increment' })}>
                              <IonIcon icon={addIcon} />
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>
                    </IonItemGroup>
                  </IonCardContent>
                </IonCard>
              </IonCol>

              {/* Late Cancellation Configuration Options */}
              <IonCol size="12" size-lg="4">
                <IonCard>
                  <IonCardHeader>
                    <IonCardTitle color="tertiary">Late Cancellation</IonCardTitle>
                  </IonCardHeader>
                  <IonCardContent>
                    <IonItemGroup>

                      {/* Late Cancel: Product Id */}
                      <IonItem onClick={() => setMboLateCancelProductIdInfo(!show_mbo_late_cancel_product_id_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Mindbody Online Service</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: mbo_late_cancel_product_id */}
                      {show_mbo_late_cancel_product_id_info ?
                        <IonItem onClick={() => setMboLateCancelProductIdInfo(!show_mbo_late_cancel_product_id_info)}>
                          {/* <IonItem> */}
                          <IonNote class="ion-padding-start">
                            <h5>This is the <b>Mindbody Online Serivce</b> that is to applied to the <b>Clients Account</b> for a <u><b>Late Cancel Charge</b></u>.</h5>
                            <hr />
                            <p>You can set the price, name and other attributes in your Mindbody Online account.</p>
                            <hr />
                            <p><u>Note</u>: any changes made to this Serivce via Mindbody Online will be automatically applied.</p>
                            <hr />
                            <p>That is to say, if you change the price of the Late Cancel Service in Mindbody Online the new updated price will be applied for a Late Cancel Charge.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: mbo_late_cancel_product_id */}
                      <IonItem key={no_show_client?.mbo_late_cancel_product_id}
                        button onClick={() => openMboServiceUpdate('late_cancel_service_update')}>
                        <IonLabel className="ion-text-wrap" color="default">
                          <h2>{no_show_client?.mbo_late_cancel_product_name}</h2>
                          <h3>${no_show_client?.mbo_late_cancel_product_amount.toFixed(2)}</h3>
                        </IonLabel>
                        <IonIcon slot="start" color="tertiary" icon={editIcon} />
                      </IonItem>

                      {/* Late Cancel: Forgive Reset Cycle */}
                      <IonItem onClick={() => setShowLateCancelForgiveFrequencyInfo(!show_late_cancel_forgive_frequency_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Forgive Reset Frequency</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: late_cancel_forgive_frequency */}
                      {show_late_cancel_forgive_frequency_info ?
                        <IonItem onClick={() => setShowLateCancelForgiveFrequencyInfo(!show_late_cancel_forgive_frequency_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>This is the <b>Length of Time</b> for each <b>Forgive Cycle Period</b>.</h5>
                            <hr />
                            <p><u>Note</u>: can be <em>Every Week</em> <b>or</b> <em>Every Month</em>.</p>
                            <hr />
                            <p>That is to say, if this value is <em>Every Week</em>, the number of Late Cancels forgiven resets to 0 every Sunday</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: late_cancel_forgive_frequency */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="tertiary"
                              fill={no_show_client?.late_cancel_forgive_frequency.toLowerCase() === 'weekly' ? 'solid' : "outline"}
                              onClick={() => dispatch({ type: 'late_cancel_forgive_frequency_change' })}>
                              Every Week
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="tertiary"
                              fill={no_show_client?.late_cancel_forgive_frequency.toLowerCase() === 'monthly' ? 'solid' : "outline"}
                              onClick={() => dispatch({ type: 'late_cancel_forgive_frequency_change' })}>
                              Every Month
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>

                      {/* Late Cancel: Number Of Forgives */}
                      <IonItem onClick={() => setShowLateCancelForgiveMinInfo(!show_late_cancel_forgive_min_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Number Forgives</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: late_cancel_forgive_min */}
                      {show_late_cancel_forgive_min_info ?
                        <IonItem onClick={() => setShowLateCancelForgiveMinInfo(!show_late_cancel_forgive_min_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>A <b>Late Cancel Charge</b> will <b><u>not</u></b> be applied <b><u>if</u></b> the number of times the Client <b>has</b> Late Cancelled <b>is</b> <b>less than or equal</b> this value.</h5>
                            <hr />
                            <p><u>Note</u>: this resets to 0 every week or month depending on the <b>Late Cancel Forgive Reset Cycle</b>.</p>
                            <hr />
                            <p>That is to say, if this value is 1, the first Late Cancel will be forgiven but every Late Cancel after this will be charged.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: late_cancel_forgive_min */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="tertiary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'late_cancel_forgive_min_decrement' })}>
                              <IonIcon icon={removeIcon} />
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonText class="ion-text-center"><h1>{no_show_client?.late_cancel_forgive_min}</h1></IonText>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="tertiary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'late_cancel_forgive_min_increment' })}>
                              <IonIcon icon={addIcon} />
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>

                      {/* Late Cancel: Max Number Of Charges */}
                      <IonItem onClick={() => setShowLateCancelForgiveMaxInfo(!show_late_cancel_forgive_max_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Maximum Number of Charges</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: late_cancel_forgive_max */}
                      {show_late_cancel_forgive_max_info ?
                        <IonItem onClick={() => setShowLateCancelForgiveMaxInfo(!show_late_cancel_forgive_max_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>A <b>Late Cancel Charge</b> will <b><u>not</u></b> be applied <b><u>if</u></b> the number of times the Client <b>has</b> Late Cancelled <b>is</b> <b>more than or equal</b> this value.</h5>
                            <hr />
                            <p>That is to say, if this value is 10, the 10<sup>th</sup> Late Cancel and every Late Cancel after will be forgiven.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: late_cancel_forgive_max */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="tertiary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'late_cancel_forgive_max_decrement' })}>
                              <IonIcon icon={removeIcon} />
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonText class="ion-text-center"><h1>{no_show_client?.late_cancel_forgive_max}</h1></IonText>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="tertiary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'late_cancel_forgive_max_increment' })}>
                              <IonIcon icon={addIcon} />
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>
                    </IonItemGroup>
                  </IonCardContent>
                </IonCard>
              </IonCol>

              {/* Cancellation Configuration Options */}
              <IonCol size="12" size-lg="4">
                <IonCard>
                  <IonCardHeader>
                    <IonCardTitle color="secondary">Cancellation</IonCardTitle>
                  </IonCardHeader>
                  <IonCardContent>
                    <IonItemGroup>

                      {/* Cancel: Product Id */}
                      <IonItem onClick={() => setMboCancelProductIdInfo(!show_mbo_cancel_product_id_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Mindbody Online Service</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: mbo_cancel_product_id */}
                      {show_mbo_cancel_product_id_info ?
                        <IonItem onClick={() => setMboCancelProductIdInfo(!show_mbo_cancel_product_id_info)}>
                          {/* <IonItem> */}
                          <IonNote class="ion-padding-start">
                            <h5>This is the <b>Mindbody Online Serivce</b> that is to applied to the <b>Clients Account</b> for a <u><b>Cancel Charge</b></u>.</h5>
                            <hr />
                            <p>You can set the price, name and other attributes in your Mindbody Online account.</p>
                            <hr />
                            <p><u>Note</u>: any changes made to this Serivce via Mindbody Online will be automatically applied.</p>
                            <hr />
                            <p>That is to say, if you change the price of the Cancel Service in Mindbody Online the new updated price will be applied for a Cancel Charge.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: mbo_cancel_product_id */}
                      <IonItem key={no_show_client?.mbo_cancel_product_id}
                        button onClick={() => openMboServiceUpdate('cancel_service_update')}>
                        <IonLabel className="ion-text-wrap" color="default">
                          <h2>{no_show_client?.mbo_cancel_product_name}</h2>
                          <h3>${no_show_client?.mbo_cancel_product_amount.toFixed(2)}</h3>
                        </IonLabel>
                        <IonIcon slot="start" color="secondary" icon={editIcon} />
                      </IonItem>

                      {/* Cancel: Forgive Reset Cycle */}
                      <IonItem onClick={() => setShowCancelForgiveFrequencyInfo(!show_cancel_forgive_frequency_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Forgive Reset Frequency</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: cancel_forgive_frequency */}
                      {show_cancel_forgive_frequency_info ?
                        <IonItem onClick={() => setShowCancelForgiveFrequencyInfo(!show_cancel_forgive_frequency_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>This is the <b>Length of Time</b> for each <b>Forgive Cycle Period</b>.</h5>
                            <hr />
                            <p><u>Note</u>: can be <em>Every Week</em> <b>or</b> <em>Every Month</em>.</p>
                            <hr />
                            <p>That is to say, if this value is <em>Every Week</em>, the number of Cancels forgiven resets to 0 every Sunday</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: cancel_forgive_frequency */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill={no_show_client?.cancel_forgive_frequency.toLowerCase() === 'weekly' ? 'solid' : "outline"}
                              onClick={() => dispatch({ type: 'cancel_forgive_frequency_change' })}>
                              Every Week
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill={no_show_client?.cancel_forgive_frequency.toLowerCase() === 'monthly' ? 'solid' : "outline"}
                              onClick={() => dispatch({ type: 'cancel_forgive_frequency_change' })}>
                              Every Month
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>

                      {/* Cancel: Number Of Forgives */}
                      <IonItem onClick={() => setShowLateCancelForgiveMinInfo(!show_cancel_forgive_min_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Number Forgives</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: cancel_forgive_min */}
                      {show_cancel_forgive_min_info ?
                        <IonItem onClick={() => setShowCancelForgiveMinInfo(!show_cancel_forgive_min_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>A <b>Late Cancel Charge</b> will <b><u>not</u></b> be applied <b><u>if</u></b> the number of times the Client <b>has</b> Cancelled <b>is</b> <b>less than or equal</b> this value.</h5>
                            <hr />
                            <p><u>Note</u>: this resets to 0 every week or month depending on the <b>Cancel Forgive Reset Cycle</b>.</p>
                            <hr />
                            <p>That is to say, if this value is 1, the first Cancel will be forgiven but every Cancel after this will be charged.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: cancel_forgive_min */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'cancel_forgive_min_decrement' })}>
                              <IonIcon icon={removeIcon} />
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonText class="ion-text-center"><h1>{no_show_client?.cancel_forgive_min}</h1></IonText>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'cancel_forgive_min_increment' })}>
                              <IonIcon icon={addIcon} />
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>

                      {/* Cancel: Max Number Of Charges */}
                      <IonItem onClick={() => setShowCancelForgiveMaxInfo(!show_cancel_forgive_max_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Maximum Number of Charges</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: cancel_forgive_max */}
                      {show_cancel_forgive_max_info ?
                        <IonItem onClick={() => setShowCancelForgiveMaxInfo(!show_cancel_forgive_max_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>A <b>Cancel Charge</b> will <b><u>not</u></b> be applied <b><u>if</u></b> the number of times the Client <b>has</b> Cancelled <b>is</b> <b>more than or equal</b> this value.</h5>
                            <hr />
                            <p>That is to say, if this value is 10, the 10<sup>th</sup> Cancel and every Cancel after will be forgiven.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: cancel_forgive_max */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'cancel_forgive_max_decrement' })}>
                              <IonIcon icon={removeIcon} />
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonText class="ion-text-center"><h1>{no_show_client?.cancel_forgive_max}</h1></IonText>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="secondary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'cancel_forgive_max_increment' })}>
                              <IonIcon icon={addIcon} />
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>
                    </IonItemGroup>
                  </IonCardContent>
                </IonCard>
              </IonCol>
            </IonRow>

            : null}

          {permission === 'Full Access' ?
            <IonRow>
              <IonCol>
                {/* Charge Configuration */}
                <IonCard>
                  <IonCardHeader>
                    <IonCardTitle color="primary">Charge Configuration</IonCardTitle>
                  </IonCardHeader>
                  <IonCardContent>
                    <IonItemGroup>

                      {/* Timezone */}
                      <IonItem onClick={() => setShowTimezoneInfo(!show_timezone_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Timezone</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: billing_timezone */}
                      {show_timezone_info ?
                        <IonItem onClick={() => setShowTimezoneInfo(!show_timezone_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>The timezone of your location(s)</h5>
                            <hr />
                            <p><u>Note</u>: all charges are processed after 10pm in this timezone.</p>
                            <hr />
                            <p>That is to say, if this timezone is <em>Canberra, Melbournce, Sydney</em> then it is assumed the last class is finished by 10pm local time and billing will be processed after this time.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: billing_timezone */}
                      <IonSelect
                        value={tz}
                        placeholder="Select a Timezone"
                        interface="action-sheet"
                        selectedText={no_show_client?.billing_timezone.text}
                        onIonChange={e => setTz(e.detail.value)}>
                        {
                          timezones.map((t) =>
                            <IonSelectOption key={t.text} value={t}>{t.text}</IonSelectOption>
                          )
                        }
                      </IonSelect>

                      {/* Will Charge Non-Members */}
                      <IonItem onClick={() => setShowChargeNonMembersInfo(!show_charge_non_members_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Charge Non-Members</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: offense_to_billing_hold_days */}
                      {show_charge_non_members_info ?
                        <IonItem onClick={() => setShowChargeNonMembersInfo(!show_charge_non_members_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>Should Clients with a <b>Non-Member status</b> be <b>Charged</b>?</h5>
                            <hr />
                            <p><u>Note</u>: this is the <em>Member Status</em> in Mindbody Online.</p>
                            <hr />
                            <p>This can prevent a charge been applied to Clients who are not members.</p>
                            <p>This is a common problem when using a promotion that allows Non-Members to book a class.</p>
                            <hr />
                            <p>That is to say, if Jill was late to cancel her free class from the summer promotion. Jill will not be charged.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      <IonGrid>
                        <IonRow>
                          <IonCol>
                            <IonButton
                              expand="block"
                              fill="outline"
                              color={no_show_client?.will_charge_non_members ? "success" : "danger"}
                              onClick={() => dispatch({ type: "update_will_charge_non_members" })}>
                              {no_show_client?.will_charge_non_members ? "On" : "Off"}</IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>


                      {/* Offence To Charge Delay */}
                      <IonItem onClick={() => setShowOffenseToBillingHoldDaysInfo(!show_offense_to_billing_hold_days_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Days from Offence to Charge</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: offense_to_billing_hold_days */}
                      {show_offense_to_billing_hold_days_info ?
                        <IonItem onClick={() => setShowOffenseToBillingHoldDaysInfo(!show_offense_to_billing_hold_days_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>The <em>length of time in</em> <b>days between</b> a <em>No Show</em>, <em>Late Cancel</em> or <em>Cancel</em> and when the <em>Client</em> <b>will be Charged</b></h5>
                            <hr />
                            <p><u>Note</u>: Refund Requests that are approved after this time period will need to be refunded manually.</p>
                            <hr />
                            <p><u>Note</u>: The <b>maximum</b> is <b>14 days</b>.</p>
                            <hr />
                            <p>Effectively this is the grace period the Client has to request a pardon for their No Show or Late Cancel.</p>
                            <hr />
                            <p>That is to say, if the <em>Days from Offence to Charge</em> is 7 and a Client No Shows on a Monday. The Client will be chanrged next Monday 7 days later.</p>
                            <hr />
                            <p>Assumsing the Client <b>is not pardoned for the offence</b>.</p>
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: offense_to_billing_hold_days */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="primary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'offense_to_billing_hold_days_decrement' })}>
                              <IonIcon icon={removeIcon} />
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonText class="ion-text-center"><h1>{no_show_client?.offense_to_billing_hold_days}</h1></IonText>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="primary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'offense_to_billing_hold_days_increment' })}>
                              <IonIcon icon={addIcon} />
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>

                      {/* // Working Here */}

                      {/* Dispute Auto Forgive */}
                      <IonItem onClick={() => setShowDisputeAutoForgiveInfo(!show_dispute_auto_forgive_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Disputes Automatically Forgiven</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: dispute_auto_forgive */}
                      {show_dispute_auto_forgive_info ?
                        <IonItem onClick={() => setShowDisputeAutoForgiveInfo(!show_dispute_auto_forgive_info)}>
                          <IonNote class="ion-padding-start">
                            <h5>The <em>number</em> of <b>Disputes Automatically Forgiven</b> for a <em>No Show</em>, <em>Late Cancel</em> or <em>Cancel</em> <em>Offence</em></h5>
                            <hr />
                            <p><u>Note</u>: all Disputes to be Forgiven after this will done manually.</p>
                            <hr />
                            <p>Effectively this reduces the amount of manual work required to pardon a No Show, Late Cancel or Cancel Offence.</p>
                            <hr />
                            <p>That is to say, if the <em>Disputes Automatically Forgiven</em> is 1 the <b>Clients First Offence</b> will automatically be <b>Forgiven</b>.</p>
                            <hr />
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: dispute_auto_forgive */}
                      <IonGrid>
                        <IonRow class="ion-align-items-center">
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="primary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'dispute_auto_forgive_decrement' })}>
                              <IonIcon icon={removeIcon} />
                            </IonButton>
                          </IonCol>
                          <IonCol>
                            <IonText class="ion-text-center"><h1>{no_show_client?.dispute_auto_forgive}</h1></IonText>
                          </IonCol>
                          <IonCol>
                            <IonButton
                              expand="block"
                              color="primary"
                              fill="outline"
                              onClick={() => dispatch({ type: 'dispute_auto_forgive_increment' })}>
                              <IonIcon icon={addIcon} />
                            </IonButton>
                          </IonCol>
                        </IonRow>
                      </IonGrid>

                      {/* // Working Here */}

                      {/* Will Run Billing */}
                      <IonItem onClick={() => setShowIsBillingInfo(!show_is_billing_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Billing Status</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: is_billing */}
                      {show_is_billing_info ?
                        <IonItem onClick={() => setShowIsBillingInfo(!show_is_billing_info)}>
                          <IonNote class="ion-padding-start">
                            <h5><b>If</b> the <em>Billing Status</em> of a <em>Location</em> is <b><u>on</u></b> then Charges <b>will be applied</b> at that <em>Location.</em></h5>
                            <hr />
                            <p><u>Note</u>: locations can be switched on and off independantly of each other.</p>
                            <hr />
                            <p>That is to say, billing can be applied to all, some or none of your Locaions.</p>
                            <hr />
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: is_billing */}
                      <IonItem>
                        <IonGrid>{no_show_client?.locations.map((location) =>
                          <IonRow key={`locations-${location.location_name}-${location.location_id}`} class="ion-align-items-center">
                            <IonCol>
                              <IonText color={location.is_billing ? "success" : "danger"}><h2>{location.location_name_alias}</h2></IonText>
                            </IonCol>
                            <IonCol>
                              <IonButton
                                expand="block"
                                fill={location.is_billing ? "outline" : "outline"}
                                color={location.is_billing ? "success" : "danger"}
                                onClick={() => dispatch({ type: "will_run_billing_change", payload: location })}>
                                {location.is_billing ? "On" : "Off"}</IonButton>
                            </IonCol>
                          </IonRow>
                        )}
                        </IonGrid>
                      </IonItem>

                      {/* Will Send Communicaitons */}
                      <IonItem onClick={() => setShowWillSendCommsInfo(!show_will_send_comms_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Communications Status</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: is_billing */}
                      {show_will_send_comms_info ?
                        <IonItem onClick={() => setShowWillSendCommsInfo(!show_will_send_comms_info)}>
                          <IonNote class="ion-padding-start">
                            <h5><b>If</b> the <em>Communications Status</em> of a <em>Location</em> is <b><u>on</u></b> then Communications <b>will be sent</b> at that <em>Location.</em></h5>
                            <hr />
                            <p><u>Note</u>: locations can be switched on and off independantly of each other.</p>
                            <hr />
                            <p>That is to say, emails will be sent to Clients.</p>
                            <hr />
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: will_send_comms */}
                      <IonItem>
                        <IonGrid>{no_show_client?.locations.map((location) =>
                          <IonRow key={`locations-${location.location_name}-${location.location_id}`} class="ion-align-items-center">
                            <IonCol>
                              <IonText color={location.will_send_comms ? "success" : "danger"}><h2>{location.location_name_alias}</h2></IonText>
                            </IonCol>
                            <IonCol>
                              <IonButton
                                expand="block"
                                fill={location.will_send_comms ? "outline" : "outline"}
                                color={location.will_send_comms ? "success" : "danger"}
                                onClick={() => dispatch({ type: "location_will_send_comms_change", payload: { location_id: location.location_id } })}>
                                {location.will_send_comms ? "On" : "Off"}</IonButton>
                            </IonCol>
                          </IonRow>
                        )}
                        </IonGrid>
                      </IonItem>


                      {/* Class Black List */}
                      <IonItem onClick={() => setShowClassBlackListInfo(!show_class_black_list_info)}>
                        <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                        <IonCardSubtitle>Class Blacklist</IonCardSubtitle>
                      </IonItem>

                      {/* Information Element: class_black_list */}
                      {show_class_black_list_info ?
                        <IonItem onClick={() => setShowClassBlackListInfo(!show_class_black_list_info)}>
                          <IonNote class="ion-padding-start">
                            <h5><b>If</b> the <em>Class</em> has a <em>Session Type</em> that <b>is <u>on</u></b> the <em>Class Black List</em> then Charges <b>will not be applied</b> for that <em>Class.</em></h5>
                            <hr />
                            <p><u>Note</u>: the <b>Class Session Type</b> option is configured in Mindbody Online and <b>is</b> the identifier of the Class.</p>
                            <hr />
                            <p><u>Note</u>: this is <b>optional</b>.</p>
                            <hr />
                            <p>For example: say you have Class called <em>Yoga Flow</em> and a second Class called <em>Beginner Yoga Flow</em> and <b>both</b> had the <em>Session Type: <u>Vinyasa Yoga</u></em>.</p>
                            <hr />
                            <p><b>and</b> you do <b>not</b> want charges to be applied to these classes</p>
                            <p><b>then add</b> <em><u>Vinyasa yoga</u></em> to your <em>Class Clack List</em></p>
                            <hr />
                            <p><b>and</b> charges will not be appied to both <em>Yoga Flow</em> <b>and</b> <em>Beginner Yoga Flow</em>.</p>
                            <hr />
                          </IonNote>
                        </IonItem>
                        : null}

                      {/* Input: class_black_list */}
                      <IonList>{no_show_client?.class_black_list.map((item) =>
                        <IonItem key={item.session_type_name}>
                          <IonLabel className="ion-text-wrap">
                            <h2>{item.session_type_name}</h2>
                          </IonLabel>
                        </IonItem>)}

                        <IonItem button onClick={openMboSessionTypesUpdate}>
                          <IonLabel color="primary" className="ion-text-wrap">Update Class Blacklist</IonLabel>
                          <IonIcon slot="start" color="primary" icon={editIcon} />
                        </IonItem>
                      </IonList>

                    </IonItemGroup>
                  </IonCardContent>
                </IonCard>
              </IonCol>
            </IonRow>
            : null}
          {/* </IonGrid> */}

          {/* Execute Update */}
          <IonRow>
            <IonCol>
              <IonCard>
                <IonCardContent>
                  <IonGrid>
                    <IonRow>
                      <IonCol class="ion-padding-bottom">
                        <IonButton fill="clear" onClick={() => setShowIsPreLaunchInfo(!show_is_pre_launch_info)}>
                          <IonIcon slot="icon-only" icon={infoIcon} color="primary" onClick={() => setShowIsPreLaunchInfo(!show_is_pre_launch_info)}></IonIcon>
                        </IonButton>
                        <IonButton
                          expand="block"
                          color={no_show_client?.is_pre_launch ? "success" : "warning"}
                          fill={no_show_client?.is_pre_launch ? "solid" : "outline"}
                          onClick={() => dispatch({ type: 'is_pre_launch_change' })}>Pre-Launch Mode is {no_show_client?.is_pre_launch ? "On" : "Off"}
                        </IonButton>
                      </IonCol>
                    </IonRow>
                    {
                      show_is_pre_launch_info ?
                        <IonRow>
                          <IonCol>
                            <IonItem onClick={() => setShowIsPreLaunchInfo(!show_is_pre_launch_info)}>
                              <IonNote class="ion-padding-start">
                                <h5><b>If</b> <em>Pre-Launch Mode</em> <b>is <u>on</u> then</b> <em> Pre Launch Communications will be sent</em> <b>and</b> Charges <b>will not be applied</b>.</h5>
                                <hr />
                                <p><u>Note</u>: turning Pre-Launch Mode <b>on</b> will automatically Forgive all Pending Charges for this Forgive Cycle.</p>
                                <hr />
                                <p>This is a useful to implement a roll-out plan to your client base.</p>
                                <hr />
                                <p><u>Note</u>: the <em>Pre-Launch Warning Email</em> can be used as a notification method to warn Clients that there is a policy change coming.</p>
                                <hr />
                                <p>That is to say, if Pre-Launch Mode is <b>on</b> and a Client No Shows or Late Cancels <b>then</b> they will be <b>sent</b> the <em>Pre-Lauch Warning Email</em> and will <b>not</b> be charged.</p>
                                <hr />
                              </IonNote>
                            </IonItem>
                          </IonCol>
                        </IonRow>
                        : null
                    }

                    {/* This is to control Test Mode, I'm not sure that it should be exposed to the user */}
                    {/* <IonRow>
                  <IonCol class="ion-padding-bottom" size="auto">
                    <IonItem>
                      <IonButton slot="start" fill="clear" onClick={() => setShowIsTestInfo(!show_is_test_info)}>
                        <IonIcon slot="icon-only" icon={infoIcon} color="primary"></IonIcon>
                      </IonButton>
                    </IonItem>

                    <IonButton
                      color={no_show_client?.is_test ? "danger" : "medium"}
                      fill={no_show_client?.is_test ? "solid" : "outline"}
                      onClick={() => dispatch({ type: 'is_test_change' })}>Test Mode is {no_show_client?.is_test ? "On" : "Off"}
                    </IonButton>

                  </IonCol>
                {
                  show_is_test_info ?
                    <IonRow>
                      <IonCol>
                        <IonItem onClick={() => setShowIsTestInfo(!show_is_test_info)}>
                          <IonNote class="ion-padding-start">
                            <h5><b>If</b> the <em>Test Mode</em> <b>is <u>on</u></b> then <em> <b>no</b> Communications will be sent</em> <b>and no Charges will be applied</b>.</h5>
                            <hr />
                            <p><u>Note</u>: this is recomended <b>for trouble shooting only</b>.</p>
                            <hr />
                          </IonNote>
                        </IonItem>
                      </IonCol>
                    </IonRow>
                    : null
                }
              </IonRow> */}


                    <IonRow>
                      <IonCol class="ion-padding-bottom">
                        <IonButton
                          expand="block"
                          color="primary"
                          onClick={handleUpdate}>Update
                        </IonButton>
                      </IonCol>
                    </IonRow>
                    <IonRow>
                      <IonCol>
                        <IonButton
                          color="medium"
                          expand="block"
                          fill="clear"
                          onClick={() => firsebaseAuth.signOut()}>Logout
                        </IonButton>

                      </IonCol>
                    </IonRow>
                  </IonGrid>
                </IonCardContent>
              </IonCard>
            </IonCol>
          </IonRow>
        </IonGrid>
      </IonContent>
      <IonLoading isOpen={status.loading} />
    </IonPage>
  );
};

export default SettingsPage;
