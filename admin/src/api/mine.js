import {axios} from '@utils/http'

/**
 * 设置当前账号
 * @param data 提交数据
 * @returns {Promise}
 */
export function updateMine(data) {
    return axios({
        url: '/mine',
        method: 'PATCH',
        data,
    })
}