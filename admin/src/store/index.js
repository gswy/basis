import { createStore } from 'vuex'

import token from '@utils/token'
import {getAuth} from '../api/auth'

const store = createStore({
    state() {
        return {
            auth: null,
            token: null,
        }
    },
    mutations: {
        _preLogin(state, {token, auth}) {
            state.token = token
            state.auth = auth
        },
        _logout(state) {
            state.token = null
            state.auth = null
            token.delToken()
        }
    },
    actions: {
        /**
         * token预登陆操作。
         * 此方法设置store中的token，然后进行获取用户信息。
         * @param context
         */
        preLogin: ({commit}) => {
            return new Promise((resolve, reject) => {
                const tk = token.getToken()
                // const reTk = token.getRefreshToken()     // 获取刷新token

                // 判断cookie中的token存在，开始调用获取用户信息；
                // 如果正常获取用户数据信息，则登录，反之退出登录。
                if (tk) {
                    getAuth().then((data) => {
                        commit('_preLogin', {token: tk, auth: data})
                        resolve('登录用户')
                    }).catch(() => {
                        commit('_logout')
                        reject()
                    })
                    return
                }

                // 判断cookie中的reToken存在，开始换取新的token，再进行获取用户。
                // if (reTk) {
                //     reject('reToken存在 ')
                //     return
                // }


                commit('_logout')
                reject()
            })
        },

        /**
         * 退出登录
         * @param commit
         */
        logout: ({commit}) => {
            commit('_logout')
        }
    }
})

export default store