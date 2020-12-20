/**
 * 发送图片接口
 */

import request from '@/utils/request'

export default {
    //发送图片
    sendImage(imageData, studentName,) {
        return request({
            url: '/sys_user/query',
            method: 'post',
            data: {
                image: imageData,
                sid: studentName
            }
        })
    }
}
