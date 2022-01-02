import {
  IonRouterOutlet, IonTabs, IonTabBar, IonTabButton, IonLabel, IonIcon
} from '@ionic/react';
import { home as homeIcon, settings as settingsIcon, analyticsSharp as reportIcon } from 'ionicons/icons';
import React from 'react';
import { Route, Redirect } from 'react-router';
import { useAuth } from './auth';
import HomePage from './pages/HomePage';
import SettingsPage from './pages/SettingsPage';
import EntryPage from './pages/EntryPage';
import AddEntryPage from './pages/AddEntryPage';
import ReportPage from './pages/ReportPage';
import EmailEditorPage from './pages/EmailEditorPage';


const AppTabs: React.FC = () => {

  const { loggedIn, permission } = useAuth();
  // const { no_show_client } = useNoShowClientInit();
  // console.log(`[AppTabs] loggedIn = ${loggedIn}`)


  if (!loggedIn) {
    return <Redirect to="/login" />
  }

  return (
    // <IonApp>
    <IonTabs>
      {/* <NoShowClientContext.Provider value={no_show_client}> */}
      <IonRouterOutlet>

        {/* Entries */}
        <Route exact path="/v1/entries">
          <HomePage />
        </Route>
        <Route exact path="/v1/entries/view/:id">
          <EntryPage />
        </Route>
        <Route exact path="/v1/entries/add">
          <AddEntryPage />
        </Route>

        {/* Settings */}
        {/* <Route exact path="/v1/settings/comms/:id">
              <EmailEditorPage />
            </Route> */}
        {
          permission !== 'Read Only' ?
            <Route exact path="/v1/settings/comms/:id">
              <EmailEditorPage />
            </Route>
            : null
        }
        {
          permission !== 'Read Only' ?
            <Route exact path="/v1/settings">
              <SettingsPage />
            </Route>
            : null
        }



        {/* Reports */}
        <Route exact path="/v1/reports">
          <ReportPage />
        </Route>
        <Route exact path="/v1/reports/:type/:id">
          <ReportPage />
        </Route>
        <Route exact path="/v1/reports/location/:id?">
          <ReportPage />
        </Route>

        {/* Catch */}
        <Redirect exact path="/" to="/v1/entries" />
      </IonRouterOutlet>

      {/* Tab Bar */}
      <IonTabBar slot="bottom">
        <IonTabButton tab="home" href="/v1/entries">
          <IonIcon icon={homeIcon} />
          <IonLabel>Home</IonLabel>
        </IonTabButton>
        {/* This is not Active for other locations. */}
        <IonTabButton tab="reports" href="/v1/reports/location">
          <IonIcon icon={reportIcon} />
          <IonLabel>Report</IonLabel>
        </IonTabButton>
        {permission !== 'Read Only' ?
          <IonTabButton tab="settings" href="/v1/settings">
            <IonIcon icon={settingsIcon} />
            <IonLabel>Settings</IonLabel>
          </IonTabButton>
          : null}
      </IonTabBar>
      {/* </NoShowClientContext.Provider> */}
    </IonTabs>
  );
};

export default AppTabs;
