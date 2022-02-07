import { createRouter, createWebHistory } from 'vue-router'
import store from '@store';
import AppLayout from '@layouts/AppLayout';
import AuthLayout from '@layouts/AuthLayout';
import authorized from "./authorized";

// 路由规则
const routes = [
    // 已登录的路由
    {
        path: '/',
        name: '首页',
        component: AppLayout,
        redirect: '/dashboard',
        children: authorized
    },

    // 未登录的路由
    {
        path: '/auth',
        redirect: '/auth/login',
        component: AuthLayout,
        children: [
            {
                name: 'login',
                path: '/auth/login',
                component: () => import('@views/auth/Login')
            },
            {
                name: 'register',
                path: '/auth/register',
                component: () => import('@views/auth/Register')
            },
            {
                name: 'forget',
                path: '/auth/forget',
                component: () => import('@views/auth/Forget')
            }
        ]
    },
]

// 创建路由实例
const router = createRouter({
    history: createWebHistory(),
    routes,
})

// 路由拦截
router.beforeEach(async (to) => {
    // 判断用户没有登录，且路由不在授权页面，跳转至登录页面；
    // 如果用户登录，路由在授权页面，则自动跳转至首页。
    return new Promise((resolve) => {
        store.dispatch('preLogin')
            .then(() => {
                if (to.name === 'login' || to.name === 'register' || to.name === 'forget') {
                    resolve({name: '仪表盘'})
                } else {
                    resolve(true)
                }
            }).catch(() => {
                if (to.name !== 'login' && to.name !== 'register' && to.name !== 'forget') {
                    resolve({name: 'login'})
                } else {
                    resolve(true)
                }
            })
    })
})

export default router