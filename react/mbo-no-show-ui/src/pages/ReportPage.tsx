import {
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonLabel,
    IonGrid,
    IonRow,
    IonCol,
    IonText,
    IonFab,
    IonFabButton,
    IonFabList,
    IonIcon,
    IonLoading,
    IonItem,
    IonList,
    IonItemGroup,
    IonModal,
    IonButton,
    IonCard,
    IonCardHeader,
    IonCardTitle,
    IonCardSubtitle,
    IonNote, IonImg, IonPage
} from '@ionic/react';
import { informationCircleOutline as infoIcon, helpCircleOutline as helpIcon, medalSharp as medalIcon, locationSharp as fabIcon, close as closeIcon, barbellSharp as classIcon, personSharp as instructorIcon } from 'ionicons/icons'
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { firestore } from '../firebase';
import { useAuth } from '../auth';
import dayjs from 'dayjs';
import StackedAreaChart from '../components/StackedAreaChart';
import { NoShowClient } from '../models/NoShowClient';
import { Report, toReport, PerformanceData } from '../models/Reports';
import { proportionalDifference, getMonthCompleted } from '../reportCalculations';


interface RouteParams {
    id: string;
    type: string;
}

const ReportPage: React.FC = () => {
    const { userId, noShowClient } = useAuth();
    const { id } = useParams<RouteParams>();
    const [status, setStatus] = useState({ loading: true, error: false });
    const [showPerformanceModal, setShowPerformanceModal] = useState(false);
    const [showPerformanceDetailsModal, setShowPerformanceDetailsModal] = useState(false);
    const [show_performance_metric_info, setShowPerformanceMeticInfo] = useState(false);
    const [show_table_info, setShowTableInfo] = useState(false);
    const [performanceData, setPerformanceData] = useState<PerformanceData[]>();
    const [performanceDetailsData, setPerformanceDetailsData] = useState<PerformanceData>();
    const [no_show_client, setNoShowClient] = useState<NoShowClient>();


    // console.log(`\t[ReportPage] ${type} ${id} `)
    const [reportData, setReportData] = useState<Report>();
    const month_completed = getMonthCompleted();

    const formatDateToMonth = (d: string) => {
        const date = dayjs(d).toISOString()
        return dayjs(date).format('MMM');
    }

    const toPercent = (decimal: number, fixed = 0) => {
        return `${(decimal * 100).toFixed(fixed)}%`;
    };

    useEffect(() => {
        setNoShowClient(noShowClient)
      }, [noShowClient])


    useEffect(() => {
        var _id = typeof id === 'undefined' ? '0' : id;
        return firestore.collection('users').doc(userId)
            .collection('Report').doc(_id).onSnapshot((doc) => {
                setReportData(toReport(doc))
            })
    }, [id, userId])

    // This is why NoShowClient should be a Context
    // useEffect(() => {
    //     // console.log('\n\n[ReportsPage].useEffect() Get No Show Client')
    //     const entryRef = firestore.collection('users').doc(userId).collection('NoShowClient')
    //     entryRef.get().then((snapshot) => {
    //         snapshot.docs.forEach((doc) => {
    //             setNoShowClient(toNoShowClient(doc))
    //         })
    //     })
    // }, [userId]);

    useEffect(() => {
        if (typeof reportData === 'undefined') {
            setStatus({ loading: true, error: false })
        } else {
            setStatus({ loading: false, error: false })
            // setDataLength(reportData.table_data.length)
        }
    }, [reportData])


    const renderTableRow = (title: string, key: string, color = 'default', prefix = '') => {
        if (status.loading) { return null; }
        // const d = new Date();
        // d.setDate(d.getDate() - 1)
        // console.log(`\n\tmonth_completed = ${d.getDate()}/${dayjs(d).daysInMonth()} ${month_completed}`)
        return (
            <IonRow class="ion-margin-vertical">
                <IonCol><IonText class="ion-text-wrapped"><strong>{title}</strong></IonText></IonCol>
                {reportData?.table_data.map((data, index) => (
                    <IonCol key={`table-${key}-${index}`} class={index < reportData.table_data.length - 3 ? "ion-text-end ion-hide-md-down" : "ion-text-end"}>
                        {index === reportData.table_data.length - 1
                            ? <IonText color={proportionalDifference(reportData?.table_data[index - 1][key], reportData?.table_data[index][key], month_completed) ? "success" : "danger"}>{`${prefix}${data[key]}`}</IonText>
                            : <IonText color={color}>{`${prefix}${data[key]}`}</IonText>}
                    </IonCol>
                ))}
            </IonRow>
        )
    }

    const renderTableRowPercentage = (title: string, key: string, color = 'default') => {
        if (status.loading) { return null; }
        return (
            <IonRow class="ion-margin-vertical">
                <IonCol><IonText class="ion-text-wrapped"><strong>{title}</strong></IonText></IonCol>
                {reportData?.table_data.map((data, index) => (
                    <IonCol key={`table-${key}-${index}`} class={index < reportData.table_data.length - 3 ? "ion-text-end ion-hide-md-down" : "ion-text-end"}>
                        {index === reportData.table_data.length - 1
                            ? <IonText color={proportionalDifference(reportData.table_data[index - 1][key], reportData.table_data[index][key], 1) ? "success" : "danger"}>{`${toPercent(data[key])}`}</IonText>
                            : <IonText color={color}>{`${toPercent(data[key])}`}</IonText>}
                    </IonCol>
                ))}
            </IonRow>
        )
    }

    const openPerformanceModal = (performanceType) => {
        setShowPerformanceModal(true);
        setPerformanceData(reportData[performanceType]);
    }

    const closePerformanceModal = () => {
        setShowPerformanceModal(false);
        setPerformanceData(null);
    }

    const openPerformanceDetailsModal = (data) => {
        setShowPerformanceDetailsModal(true);
        setPerformanceDetailsData(data);
    }

    const closePerformanceDetailsModal = () => {
        setShowPerformanceDetailsModal(false);
        setPerformanceDetailsData(null);
    }

    // This is descending order
    function compare(a, b) {
        if (a.performance_comparitor > b.performance_comparitor) {
            return -1;
        }
        if (a.performance_comparitor < b.performance_comparitor) {
            return 1;
        }
        return 0;
    }

    return (
        <IonPage>
            {/* FAB */}
            <IonFab vertical="bottom" horizontal="end" >
                <IonFabButton><IonIcon icon={fabIcon} /></IonFabButton>
                <IonFabList side="top" >
                    <IonFabButton routerLink="/v1/reports/location/0"><IonLabel color="secondary">ALL</IonLabel></IonFabButton>
                    {
                        no_show_client?.locations.map((location) =>
                            <IonFabButton key={`repost-fab-${location.location_name}`} routerLink={`/v1/reports/location/${location.location_id}`}>
                                <IonLabel color="secondary">{`${location.location_name_abbreviated}`}</IonLabel>
                            </IonFabButton>
                        )
                    }
                </IonFabList>

                <IonFabList side="start" >
                    <IonFabButton onClick={() => openPerformanceModal('class_performance_data')}><IonIcon color="secondary" icon={classIcon} /></IonFabButton>
                    <IonFabButton onClick={() => openPerformanceModal('instructor_performance_data')}><IonIcon color="secondary" icon={instructorIcon} /></IonFabButton>
                </IonFabList>
            </IonFab>

            {/* Performance Modal */}
            <IonModal
                isOpen={showPerformanceModal}
                onDidDismiss={() => closePerformanceModal()}
            >
                <IonToolbar>
                    <IonButton
                        slot="end"
                        color="primary"
                        fill="clear"
                        onClick={() => closePerformanceModal()}>
                        <IonIcon slot="icon-only" icon={closeIcon} /></IonButton>
                    <IonTitle>{reportData?.name}</IonTitle>
                </IonToolbar>

                <IonContent class="ion-padding">
                    {performanceData?.sort(compare).map((d, i) => (
                        <IonCard key={`$performance-modal-${i}`}>
                            <IonItem>
                                {i === 0
                                    ? <IonIcon color="warning" icon={medalIcon} slot="start" />
                                    : <IonIcon slot="start" />}
                                <IonCardHeader>{`${d.index}`}</IonCardHeader>
                                <IonButton
                                    fill="outline"
                                    slot="end"
                                    onClick={() => openPerformanceDetailsModal(d)}
                                >View</IonButton>
                            </IonItem>
                            {/* {renderDetails} */}
                        </IonCard>
                    ))}
                    <IonButton
                        expand="block"
                        fill="outline"
                        color="primary"
                        onClick={closePerformanceModal}>Close</IonButton>
                </IonContent>

            </IonModal>

            {/* Performance Details Modal */}
            <IonModal
                isOpen={showPerformanceDetailsModal}
                onDidDismiss={() => closePerformanceDetailsModal()}>
                <IonToolbar>
                    <IonTitle>{reportData?.name}</IonTitle>
                    <IonButton
                        slot="end"
                        color="primary"
                        fill="clear"
                        onClick={() => closePerformanceDetailsModal()}>
                        <IonIcon slot="icon-only" icon={closeIcon} /></IonButton>
                </IonToolbar>

                <IonContent class="ion-padding">
                    <IonCard class="ion-padding">
                        <IonCardSubtitle>Last 30 Days</IonCardSubtitle>
                        <IonCardTitle>
                            {`${performanceDetailsData?.index}`}
                        </IonCardTitle>
                        <IonList>
                            <IonItemGroup>
                                <IonItem>
                                    {/* <IonLabel color="primary"><h1>Stats</h1></IonLabel> */}
                                    <IonCardSubtitle>Stats</IonCardSubtitle>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">Classes:</IonLabel>
                                    <IonText slot="end">{performanceDetailsData?.total_classes}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">Avg Class Size:</IonLabel>
                                    <IonText slot="end">{performanceDetailsData?.average_class_size.toFixed(2)}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">Capactiy:</IonLabel>
                                    <IonText slot="end">{toPercent(performanceDetailsData?.percent_capacity_attendance)}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">Late Cancel:</IonLabel>
                                    <IonText slot="end">{toPercent(performanceDetailsData?.percent_late_cancel)}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">No Show:</IonLabel>
                                    <IonText slot="end">{toPercent(performanceDetailsData?.percent_no_show)}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonCardSubtitle>Details</IonCardSubtitle>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">Total Visits:</IonLabel>
                                    <IonText slot="end">{performanceDetailsData?.total_visited}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">Total Booked:</IonLabel>
                                    <IonText slot="end">{performanceDetailsData?.total_booked}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">Signed In:</IonLabel>
                                    <IonText slot="end">{performanceDetailsData?.signed_in}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">Unique Visits:</IonLabel>
                                    <IonText slot="end">{performanceDetailsData?.unique_visits}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">Late Cancel:</IonLabel>
                                    <IonText slot="end">{performanceDetailsData?.late_cancel}</IonText>
                                </IonItem>
                                <IonItem>
                                    <IonLabel class="ion-text-wrap" slot="start">No Show:</IonLabel>
                                    <IonText slot="end">{performanceDetailsData?.no_show}</IonText>
                                </IonItem>
                                <IonItem onClick={() => setShowPerformanceMeticInfo(!show_performance_metric_info)}>
                                    <IonIcon slot="start" icon={helpIcon} color="warning"></IonIcon>
                                    <IonLabel color="warning" class="ion-text-wrap" slot="start">Performance Metric:</IonLabel>
                                    <IonText slot="end">{toPercent(performanceDetailsData?.performance_comparitor, 2)}</IonText>
                                </IonItem>
                                {/* Information Element: show_mbo_no_show_product_id */}
                                {show_performance_metric_info ?
                                    <IonItem onClick={() => setShowPerformanceMeticInfo(!show_performance_metric_info)}>
                                        <IonGrid>
                                            <IonRow class="ion-padding-top">
                                                <IonCol size="2"></IonCol>
                                                <IonCol size="8">
                                                    <IonImg src="/assets/performance_metric.svg" alt="" />
                                                </IonCol>
                                                <IonCol size="2"></IonCol>
                                            </IonRow>
                                            <IonRow>
                                                <IonCol class="ion-padding-start">
                                                    <IonNote>
                                                        <p><b>Signed In</b> is the <b>sum</b> of <b><u>all</u> clients who attended</b>.</p>
                                                        <p><b>Total Capacity</b> is the <b>sum</b> of the <b>class size of <u>all</u> classes</b>.</p>
                                                        <hr />
                                                        <p>This is the metric used to rank performance.</p>
                                                        <hr />
                                                        <p>It is the <b>proportion of attendance</b> the Class or Intructor has <b>contributed to attendence</b>.</p>
                                                        <p>An indication of the amount of influence a Class or Instructor has to motivate Clients to attend.</p>
                                                        <p>A high scrore is a good thing.</p>
                                                        <hr />
                                                        <p>For example, say Jonny Bicep is one of your instructors and <b>100</b> people <b>Signed in</b> to Jonny's classes.</p>
                                                        <p>In the same 30 days <b>Total Capacity</b> is <b>10,000</b>.</p>
                                                        <p>Jonny's Performance Metric is <b>1%</b></p>
                                                        <p>That is to say, for <b><em>1 in every 100</em></b> Clients who <b>attended</b> a Class, it was for one of Jonny's classes.</p>
                                                    </IonNote>
                                                </IonCol>
                                            </IonRow>
                                        </IonGrid>
                                    </IonItem>
                                    : null}
                            </IonItemGroup>
                        </IonList>
                    </IonCard>
                    <IonButton
                        expand="block"
                        fill="outline"
                        color="primary"
                        onClick={closePerformanceDetailsModal}>
                        Close</IonButton>
                </IonContent>
            </IonModal>

            <IonHeader>
                <IonToolbar>
                    <IonTitle>{reportData?.name}</IonTitle>
                </IonToolbar>
            </IonHeader>
            {status.loading ? null :
                <IonContent className="ion-padding">
                    <IonGrid fixed>
                        <IonRow>
                            <IonCol>
                                <IonText class="ion-text-capitalize ion-text-center"><h3>class attendance</h3></IonText>
                            </IonCol>
                        </IonRow>
                        <IonRow>
                            <IonCol class="ion-padding-bottom">
                                {/* Visualisation */}
                                <StackedAreaChart
                                    data={reportData?.visualisation_data}
                                    dataKey1='percent_capacity_attendance'
                                    stroke1='#28ba62'
                                    fill1='#2dd36f'
                                    dataKey2='percent_late_cancel'
                                    stroke2='#e0ac08'
                                    fill2='#ffc409'
                                    dataKey3='percent_no_show'
                                    stroke3='#cf3c4f'
                                    fill3='#eb445a'
                                />
                            </IonCol>
                        </IonRow>

                        <IonRow>
                            <IonCol>
                                <IonItem onClick={() => setShowTableInfo(!show_table_info)}>
                                    <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                                    <IonLabel color="primary" class="ion-text-wrap">Report Information</IonLabel>
                                </IonItem>
                            </IonCol>
                        </IonRow>
                        <IonRow>
                            <IonCol>
                                {/* Information Element: show_table_info */}
                                {show_table_info ?
                                    <IonItem onClick={() => setShowTableInfo(!show_table_info)}>
                                        <IonGrid>
                                            <IonRow class="ion-padding-top">
                                                <IonCol>
                                                    <IonImg src="/assets/table_proportional_diff.svg" alt="" />
                                                </IonCol>
                                            </IonRow>
                                            <IonRow>
                                                <IonCol class="ion-padding-start">
                                                    <IonNote>
                                                        <p>Each column of table data is reflective of data for that month.</p>
                                                        <p>The last column is reflective of the data for the month-to-date.</p>
                                                        <p>The colour of the last column is a visual aid to indicate if the metric is proportionately <IonText color="success"><b>higher</b></IonText> or <IonText color="danger"><b>lower</b></IonText> than last month.</p>
                                                        <hr />
                                                        <p>For whole numbers the proportion is calculated as the <b>day of the month</b> divided by the <b>number of days in the month</b>.</p>
                                                        <p><u>Note</u>: percentages are by definition a proportion, hence the proportional scaling is not applied.</p>
                                                        <hr />
                                                        <p>For example, say today is the <b>15<sup>th</sup></b> of April and <b>52</b> classes have been offered.</p>
                                                        <p>In March <b>100</b> classes were offered.</p>
                                                        <p>Because it is the 15<sup>th</sup> and there are 30 days in April, <b>15</b> is divided by <b>30</b>, which equals <b>0.5</b>.</p>
                                                        <p>The <b>proportion</b> of this month completed is <b>0.5</b>.</p>
                                                        <p>That is to say, <em>we are half way through April</em>.</p>
                                                        <hr />
                                                        <p>This proportion of <b>0.5</b> is multiplied by the number of classes offered in March such that the scale of the comparision between March and April is equal.</p>
                                                        <p>That is to say, <em>compared to half of last month have more classes been offered so far this month?</em></p>
                                                        <hr />
                                                        <p>Multipling the <b>100</b> classes offered in March by the proportial scaler of <b>0.5</b> yeilds <b>50</b>.</p>
                                                        <p>So far this month <b>52</b> classes have been offered which is proportionately <b>greater than</b> the <b>50</b> classes offered last month.</p>
                                                        <p>Hence, this month is on track to offer more classes than last month.</p>
                                                        <p>In this example the April column for the Classes index will be <IonText color="success"><b>52</b></IonText>.</p>
                                                    </IonNote>
                                                </IonCol>
                                            </IonRow>
                                        </IonGrid>
                                    </IonItem>
                                    : null}
                            </IonCol>
                        </IonRow>

                        {/* Table Header */}
                        <IonRow key={`table-date`}>
                            <IonCol></IonCol>
                            {
                                reportData?.table_data.map((data, index) => (
                                    <IonCol key={`table-date-${index}`} class={index < reportData.table_data.length - 3 ? "ion-text-end ion-hide-md-down" : "ion-text-end"}>
                                        <IonLabel color="default"><h2><strong>{`${formatDateToMonth(data.index)}`}</strong></h2></IonLabel>
                                    </IonCol>
                                ))
                            }</IonRow>

                        {renderTableRowPercentage('Capactiy', 'percent_capacity_attendance')}
                        {renderTableRowPercentage('No Show', 'percent_no_show')}
                        {renderTableRowPercentage('Late Cancel', 'percent_late_cancel')}
                        {renderTableRowPercentage('Charged', 'avg_charge_per_member')}

                        <IonLabel color="primary"><h2>Late Cancel</h2></IonLabel>
                        {renderTableRow('Total', 'late_cancel')}
                        {renderTableRow('Charged', 'late_cancel_charges')}
                        {renderTableRow('Charge Pending', 'late_cancel_charge_pending')}
                        {renderTableRow('Charge Reversed', 'late_cancel_dispute_approved')}
                        {renderTableRow('Charge Refunded', 'late_cancel_refund')}
                        {renderTableRow('Charge Failed', 'late_cancel_error')}

                        <IonLabel color="primary"><h2>No Show</h2></IonLabel>
                        {renderTableRow('Total', 'no_show')}
                        {renderTableRow('Charged', 'no_show_charges')}
                        {renderTableRow('Charge Pending', 'no_show_charge_pending')}
                        {renderTableRow('Charge Reversed', 'no_show_dispute_approved')}
                        {renderTableRow('Charge Refunded', 'no_show_refund')}
                        {renderTableRow('Charge Failed', 'no_show_error')}

                        <IonLabel color="primary"><h2>Details</h2></IonLabel>
                        {renderTableRow('Classes', 'total_classes')}
                        {renderTableRow('Capactiy', 'capacity')}
                        {renderTableRow('Booked', 'total_booked')}
                        {renderTableRow('Signed In', 'signed_in')}
                        {renderTableRow('Late Cancel', 'late_cancel')}
                        {renderTableRow('No Show', 'no_show')}
                        {renderTableRow('Forgiven', 'total_forgive')}

                        <IonLabel color="primary"><h2>Charge Revenue</h2></IonLabel>
                        {renderTableRow('No Show', 'no_show_revenue', 'default', '$')}
                        {renderTableRow('Late Cancel', 'late_cancel_revenue', 'default', '$')}
                        {renderTableRow('Total', 'total_revenue', 'default', '$')}


                        <IonRow>
                            <IonCol>
                                <IonList>
                                    <IonItemGroup>
                                        {/* Class Performance */}
                                        <IonItem
                                            button onClick={() => openPerformanceModal('class_performance_data')}>
                                            <IonLabel color="primary">Class Performance</IonLabel>
                                        </IonItem>
                                        {/* Instructor Performance */}
                                        <IonItem
                                            button onClick={() => openPerformanceModal('instructor_performance_data')}>
                                            <IonLabel color="primary">Instructor Performance</IonLabel>
                                        </IonItem>
                                    </IonItemGroup>
                                </IonList>
                            </IonCol>
                        </IonRow>

                        <IonRow class="ion-padding">
                            <IonText color="medium">{`Updated: ${dayjs(reportData?.updateDate).format('DD MMM YYYY')}`}</IonText>
                        </IonRow>
                    </IonGrid>
                </IonContent>
            }
            <IonLoading isOpen={status.loading} />
        </IonPage >
    )
};

export default ReportPage;