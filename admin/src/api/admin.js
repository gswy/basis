import {axios} from '@utils/http'

/**
 * 获取管理员列表
 *
 * @returns {Promise}
 */
export function getAdminUsers(params) {
    return axios({
        url: '/admin_users',
        method: 'GET',
        params
    })
}

/**
 * 创建管理员
 *
 * @returns {Promise}
 */
export function createAdminUser(data) {
    return axios({
        url: '/admin_user',
        method: 'POST',
        data
    })
}

/**
 * 修改管理员
 *
 * @returns {Promise}
 */
export function showAdminUser(id) {
    return axios({
        url: '/admin_user/' + id,
        method: 'GET',
    })
}

/**
 * 修改管理员
 *
 * @returns {Promise}
 */
export function updateAdminUser(id, data) {
    return axios({
        url: '/admin_user/' + id,
        method: 'PUT',
        data
    })
}

/**
 * 删除管理员
 *
 * @returns {Promise}
 */
export function deleteAdminUser(id) {
    return axios({
        url: '/admin_user/' + id,
        method: 'DELETE',
    })
}

/**
 * 获取管理员角色
 * @param id
 * @returns {*}
 */
export function getAdminUserRole(id) {
    return axios({
        url: '/admin_user/' + id + '/role',
        method: 'GET',
    })
}

/**
 * 修改管理员角色
 * @param id
 * @param data
 * @returns {*}
 */
export function updateAdminUserRole(id, data) {
    return axios({
        url: '/admin_user/' + id + '/role',
        method: 'PUT',
        data,
    })
}

/**
 * 获取角色列表
 *
 * @returns {Promise}
 */
export function getRoles(params) {
    return axios({
        url: '/roles',
        method: 'GET',
        params
    })
}

/**
 * 创建角色
 *
 * @returns {Promise}
 */
export function createRole(data) {
    return axios({
        url: '/role',
        method: 'POST',
        data
    })
}

/**
 * 查看角色
 *
 * @returns {Promise}
 */
export function showRole(id) {
    return axios({
        url: '/role/' + id,
        method: 'GET',
    })
}

/**
 * 修改角色
 *
 * @returns {Promise}
 */
export function updateRole(id, data) {
    return axios({
        url: '/role/' + id,
        method: 'PUT',
        data
    })
}

/**
 * 删除角色
 *
 * @returns {Promise}
 */
export function deleteRole(id) {
    return axios({
        url: '/role/' + id,
        method: 'DELETE',
    })
}

/**
 * 角色下拉
 *
 * @returns {*}
 */
export function getRoleList() {
    return axios({
        url: '/role/list',
        method: 'GET',
    })
}

/**
 * 获取角色权限
 * @param id
 * @returns {*}
 */
export function getRolePermission(id) {
    return axios({
        url: '/role/' + id + '/permission',
        method: 'GET',
    })
}

/**
 * 修改角色权限
 * @param id
 * @param data
 * @returns {*}
 */
export function updateRolePermission(id, data) {
    return axios({
        url: '/role/' + id + '/permission',
        method: 'PUT',
        data,
    })
}

/**
 * 权限下拉
 *
 * @returns {*}
 */
export function getPermissionList() {
    return axios({
        url: '/permission/tree',
        method: 'GET',
    })
}