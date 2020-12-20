/**
 * 发送图片接口
 */

import request from '@/utils/request'

export default {
    //发送图片
    sendImage(imgData, studentName) {
        return request({
            url: '/analysis',
            method: 'post',
            data: {
                image: imgData,
                sid: studentName
            }
        })
    }
}
