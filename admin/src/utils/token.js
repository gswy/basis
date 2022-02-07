import Cookies from 'js-cookie'

export default {

    /**
     * 设置Token
     *
     * @param {string} token
     * @param {number} expired
     */
    setToken(token, expired = 3600) {
        // expires 以天为单位
        const tokenExpired = 1 / 86400 * expired;
        const refreshToken = tokenExpired * 2;

        Cookies.set('token', token, {expires: tokenExpired})
        Cookies.set('refresh-token', token, {expires: refreshToken})
    },

    /**
     * 获取或者判断Token
     *
     * @returns {string|boolean}
     */
    getToken() {
        const token = Cookies.get('token')
        if (token === undefined || token === null || token === '') {
            return false
        }
        return token
    },

    /**
     * 获取或者判断RefreshToken
     *
     * @returns {string|boolean}
     */
    getRefreshToken() {
        const token = Cookies.get('refresh-token')
        if (token === undefined || token === null || token === '') {
            return false
        }
        return token
    },

    /**
     * 删除token
     */
    delToken() {
        Cookies.remove('token')
        Cookies.remove('refresh-token')
    }
}