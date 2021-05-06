/**
 * 数据导出相关接口
 */
import request from '@/utils/request'

export default {

    // 学生查询自己（老师/家长查询学生）
    queryStudent(studentName, course, startTime, endTime) {
        return request({
            url: '/teacher/query_concentration2',
            method: 'post',
            data: {
                xid: studentName,
                cid: course,
                startTime: startTime,
                endTime: endTime
            }
        })
    },

    // 老师查询班级
    queryClass(course, startTime, endTime) {
        return request({
            url: '/teacher/query_class_concentration2',
            method: 'post',
            data: {
                cid: course,
                startTime: startTime,
                endTime: endTime
            }
        })
    },

}