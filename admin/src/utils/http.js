import axios from 'axios';
import token from "@utils/token";

// 创建axios实例
const service = axios.create({
    baseURL: process.env.VUE_APP_REQUEST_URL,
    timeout: 10000
});

// 设置请求拦截器
service.interceptors.request.use(config => {
    const tk = token.getToken()
    if (tk) {
        config.headers['Authorization'] = tk
    }
    // console.log('http-request', config)
    return config
})

// 设置响应拦截器
service.interceptors.response.use(response => {
    // console.log('http-response-success', response)
    return response.data
}, error => {
    // console.log('http-response-err', error.response)
    return Promise.reject(error.response)
})

export {
    service as axios
}