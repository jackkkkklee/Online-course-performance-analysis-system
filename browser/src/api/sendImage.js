/**
 * 发送图片接口
 */

import request from '@/utils/request'

export default {
    //发送图片
    sendImage(imgData, studentName, course, date, showData) {
        return request({
            url: '/analysis',
            method: 'post',
            data: {
                image: imgData,
                sid: studentName,
                cid: course,
                image_name: date,
                show_data: showData
            }
        })
    }
}
