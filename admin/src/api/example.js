import {axios} from '@utils/http'

/**
 * 获取管理员列表
 *
 * @returns {Promise}
 */
export function getExample(params) {
    console.log(params);
    console.log(axios)
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                data: [
                    {id: 1, name: 'example1'},
                    {id: 2, name: 'example2'},
                    {id: 3, name: 'example3'},
                    {id: 4, name: 'example4'},
                ]
            })
        }, 2000)
    })
}