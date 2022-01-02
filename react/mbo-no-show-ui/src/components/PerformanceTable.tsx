import React, { useState } from 'react';
import { IonList, IonItemGroup, IonItem, IonCard, IonCardContent, IonCardHeader, IonCardTitle, IonIcon, IonButton } from '@ionic/react';
import { medalSharp as medalIcon, chevronDownOutline as openIcon } from 'ionicons/icons'

var showDetails = false;

function renderDetails(data) {
    showDetails = !showDetails

    console.log('[PerformanceTable] showDetails: ', data)

    return (
        <IonCardContent>CONTENT DETAILS</IonCardContent>
    )

    // if (showDetails) {
    //     return (
    //         <IonCardContent>CONTENT DETAILS</IonCardContent>
    //     )
    // } else {
    //     return null;
    // }
}

function PerformanceTable({ rank, data }) {
    console.log('[PerformanceTable]: ', rank)
    console.log('[PerformanceTable]: ', data)
    // var showDetails = true;

    return (
        // <div>[PerformanceData]</div>
        // key={`$performance-modal-${i}`}
        <IonCard>
            {/* <IonCardHeader>{`${i + 1}. ${d.index}`}</IonCardHeader> */}
            <IonItem>
                {rank == 1 ? <IonIcon color="warning" icon={medalIcon} slot="end" />
                    : null}

                {/* <IonLabel></IonLabel> */}
                <IonCardHeader>{`${rank}. ${data.index}`}</IonCardHeader>
                {/* <IonButton fill="outline" slot="start" onClick={() => renderDetails(data)}><IonIcon  slot="icon-only" icon={openIcon} /></IonButton> */}
            </IonItem>
            {/* {renderDetails} */}
            <IonItemGroup>
            <IonItem>
                {rank == 1 ? <IonIcon color="warning" icon={medalIcon} slot="end" />
                    : null}

                {/* <IonLabel></IonLabel> */}
                <IonCardHeader>{`${rank}. ${data.index}`}</IonCardHeader>
                {/* <IonButton fill="outline" slot="start" onClick={() => renderDetails(data)}><IonIcon  slot="icon-only" icon={openIcon} /></IonButton> */}
            </IonItem>
            <IonItem>

            </IonItem>
            </IonItemGroup>
        </IonCard>
    )
}

export default PerformanceTable;