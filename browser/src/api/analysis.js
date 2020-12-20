/**
 * 查询数据分析相关接口
 */
import request from '@/utils/request'

export default {

    //查询学生当前是否有课
    queryStudentCourse(studentName) {
        return request({
            // url: '/sys_user/query',
            // method: 'post',
            // data
        })
    },

    //查询老师课程和对应学生
    queryCourseAndStudent(teacherName) {
        return request({
            // url: '/sys_user/query',
            // method: 'post',
            // data
        })
    },

    //老师查询单个学生专注度
    queryStudentConcentration(studentName, course, startTime, endTime) {
        return request({
            url: '/teacher/query_concentration',
            method: 'post',
            data: {
                sid: studentName,
                cid: course,
                startTime: startTime,
                endTime: endTime
            }
        })
    },

    //老师查询班级整体专注度
    queryClassConcentration(coucidrse, startTime, endTime) {
        return request({
            // url: '/sys_user/query',
            // method: 'post',
            // data
        })
    },

    //老师实时查询班级情绪
    queryClassEmotion(teacherName) {
        return request({
            // url: '/sys_user/query',
            // method: 'post',
            // data
        })
    },

}
