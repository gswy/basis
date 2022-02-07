import RightLayout from "@layouts/RightLayout";
import {
    DashboardOutlined,
    TeamOutlined,
    SettingOutlined,
} from '@ant-design/icons-vue';

/**
 * 已授权的路由
 * meta元数据说明
 *  - icon component 图标（可选）
 *  - isPermission bool 是否rbac权限控制（必填）
 *  - isSidebar bool 是否为侧栏菜单（必填）
 */
export default [
    {
        name: '仪表盘',
        path: 'dashboard',
        component: () => import('@views/dashboard/Index'),
        meta: {icon: <DashboardOutlined />, isPermission: false, isSidebar: true}
    },
    {
        name: '个人中心',
        path: 'mine',
        component: () => import('@views/mine/Index'),
        meta: {icon: <DashboardOutlined />, isPermission: false, isSidebar: false}
    },
    {
        name: '管理员管理',
        path: 'admin',
        redirect: '/admin/users',
        component: RightLayout,
        meta: {icon: <TeamOutlined />, isPermission: true, isSidebar: true},
        children: [
            {
                name: '管理员列表',
                path: 'users',
                component: () => import('@views/admin/users/Index'),
                meta: {isPermission: true, isSidebar: true}
            },
            {
                name: '角色列表',
                path: 'roles',
                component: () => import('@views/admin/roles/Index'),
                meta: {isPermission: true, isSidebar: true}
            },
        ]
    },
    {
        name: '系统管理',
        path: 'system',
        redirect: '/system/config',
        component: RightLayout,
        meta: {icon: <SettingOutlined />, isPermission: false, isSidebar: true},
        children: [
            {
                name: '系统设置',
                path: 'config',
                component: () => import('@views/system/config/Index'),
                meta: {isPermission: false, isSidebar: true}
            },
            {
                name: '缓存管理',
                path: 'cache',
                component: () => import('@views/system/cache/Index'),
                meta: {isPermission: false, isSidebar: true}
            },
        ]
    }
]