import {axios} from '@utils/http'

/**
 * 获取分类信息
 *
 * @returns {Promise}
 */
export function getCategoryList() {
    return axios({
        url: '/categories/lists',
        method: 'GET',
    })
}