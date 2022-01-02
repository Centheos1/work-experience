import {
  IonApp, IonLoading
} from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import React from 'react';
import { Route, Redirect, Switch } from 'react-router';
import { AuthContext, useAuthInit } from './auth';
import LoginPage from './pages/LoginPage';
import AppTabs from './AppTabs';
import NotFoundPage from './pages/404Page';
import DisputePage from './pages/DisputePage';

const App: React.FC = () => {
  const { loading, auth } = useAuthInit();
  // const { no_show_client } = useNoShowClientInit()

  if (loading) {
    return <IonLoading isOpen />
  }

  // console.log(`Rendering app with authState=${JSON.stringify(auth)}`)

  return (
    <IonApp>
      <AuthContext.Provider value={ auth }>
        {/* <NoShowClientContext.Provider value={ no_show_client}> */}
        <IonReactRouter>
          <Switch>
            <Route exact path="/login">
              <LoginPage/>
            </Route>
            <Route exact path="/dispute/:ledgerId">
              <DisputePage/>
            </Route>
            <Route path="/v1">
              <AppTabs />
            </Route>
            <Redirect exact path="/" to="/v1/entries" />
            <Route>
              <NotFoundPage />
            </Route>
          </Switch>
        </IonReactRouter>
        {/* </NoShowClientContext.Provider> */}
      </AuthContext.Provider>
    </IonApp>
  );
};

export default App;
