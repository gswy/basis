import {axios} from '@utils/http'

/**
 * 获取用户信息
 *
 * @returns {Promise}
 */
export function getAuth() {
    return axios({
        url: '/mine',
        method: 'GET',
    })
}

/**
 * 用户名密码登录
 *
 * @param {Object} data
 * @returns {Promise}
 */
export function loginWithPassword(data) {
    return axios({
        url: '/login',
        method: 'POST',
        data: data
    })
}

/**
 *
 * @param {Object} data
 * @returns {Promise}
 */
export function registerWithPassword(data) {
    return axios({
        url: '/register',
        method: 'POST',
        data: data
    })
}

/**
 *
 * @param {Object} data
 * @returns {Promise}
 */
export function forgetPassword(data) {
    return axios({
        url: '/forget',
        method: 'POST',
        data: data
    })
}