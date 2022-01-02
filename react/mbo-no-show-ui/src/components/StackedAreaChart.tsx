import React from 'react';

import {
    IonLabel,
    IonList,
    IonGrid,
    IonRow,
    IonCol
} from '@ionic/react';

import {
    ResponsiveContainer,
    XAxis,
    AreaChart,
    Area,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
} from 'recharts';
import dayjs from 'dayjs';

const formatDateToMonthYear = (isoString) => {
    return dayjs(isoString).format('MMMM YYYY');
}

const formatDateToMonth = (isoString) => {
    return dayjs(isoString).format("MMM YY");
}

const formatLegendText = (s) => {
    return s.split('_').join(' ').replace(
        /\w\S*/g,
        function (txt) {
            if (txt === 'percent') {
                txt = '';
            } else if (txt === 'attendance') {
                txt = '';
            } else {
                txt = txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase()
            }
            return txt;
            // return txt !== 'percent' ? txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase() : '% ';
        }
    );
}

const toPercent = (decimal, fixed = 0) => {
    return `${(decimal * 100).toFixed(fixed)}%`;
};

const renderTooltipContent = (o) => {
    const { payload, label } = o;
    return (
        <div><IonGrid>
            <IonRow><IonCol>
                <IonLabel>{`${formatDateToMonthYear(label)}`}</IonLabel>
            </IonCol></IonRow>
            <IonList>{
                payload.map((entry, index) => (
                    <IonRow key={`item-${index}`} class="ion-align-items-center">
                        <IonCol class="ion-text-start">
                            {`${formatLegendText(entry.name)}`}
                        </IonCol>
                        <IonCol class="ion-text-end">
                            {`${toPercent(entry.value)}`}
                            {/* {`${entry.value}`} */}
                        </IonCol>
                    </IonRow>
                ))}</IonList>
        </IonGrid>
        </div>
    );
};

function StackedAreaChart({ data, dataKey1, fill1, stroke1, dataKey2, fill2, stroke2, dataKey3, fill3, stroke3  }) {
    // console.log(`\n\n[StackedAreaChart]: ${JSON.stringify(data)}`)
    let tickArray = []
    for (var i=0; i<data.length; i++) {
        if (i %2 === 1) {
            tickArray.push(data[i].index)
        }
    }

    return (
        <ResponsiveContainer height={350} width="100%">
            <AreaChart data={data}
                margin={{ top: 10, right: 10, left: 0, bottom: 10 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="index" 
                    tickFormatter={formatDateToMonth} 
                    ticks={tickArray}/>
                <YAxis tickFormatter={toPercent} />
                <Tooltip content={renderTooltipContent} />
                <Legend formatter={(value) => formatLegendText(value)} />
                <Area type='monotone' dataKey={dataKey1} stackId="1" stroke={stroke1} fill={fill1} />
                <Area type='monotone' dataKey={dataKey2} stackId="1" stroke={stroke2} fill={fill2} />
                <Area type='monotone' dataKey={dataKey3} stackId="1" stroke={stroke3} fill={fill3} />
            </AreaChart>
        </ResponsiveContainer>
    )
}

export default StackedAreaChart;