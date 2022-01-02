import axios from 'axios';

const mboProxyInstance = axios.create({
    baseURL: "https://test.fitnessplayground.com.au/v1/mbo/",
    headers: { "x-fp-authorization": process.env.REACT_APP_SOURCE_UID }
})

export default mboProxyInstance;
