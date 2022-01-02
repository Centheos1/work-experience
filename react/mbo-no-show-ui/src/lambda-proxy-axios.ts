import axios from 'axios';

const lambdaProxyInstance = axios.create({
    baseURL: "https://qgi1bn8bf3.execute-api.ap-southeast-2.amazonaws.com/Prod/",
})


// const lambdaProxyInstance = axios.create({
//     baseURL: "https://v48ootik8b.execute-api.ap-southeast-2.amazonaws.com/dev/",
// })

export default lambdaProxyInstance;
