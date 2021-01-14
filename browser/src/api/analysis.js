/**
 * 查询数据分析相关接口
 */
import request from '@/utils/request'

export default {

    //查询学生当前是否有课和课程名
    queryStudentHasCourse(studentName) {
        return request({
            url: '/student/has_course',
            method: 'post',
            data: {
                sid: studentName
            }
        })
    },

    //查询学生所有课程
    queryStudentCourse(studentName) {
        return request({
            url: '/student/query_all_course',
            method: 'post',
            data: {
                sid: studentName
            }
        })
    },

    //老师查询课程
    queryTeacherCourse(teacherName) {
        return request({
            url: '/teacher/query_courses',
            method: 'post',
            data: {
                tid: teacherName
            }
        })
    },

    //老师查询对应学生
    queryTeacherStudent(teacherName) {
        return request({
            url: '/teacher/query_student',
            method: 'post',
            data: {
                tid: teacherName
            }
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
    queryClassConcentration(course, startTime, endTime) {
        return request({
            url: '/teacher/query_class_concentration',
            method: 'post',
            data: {
                cid: course,
                startTime: startTime,
                endTime: endTime
            }
        })
    },

    //老师实时查询班级情绪
    queryClassEmotion(teacherName) {
        return request({
            url: '/emotion/query_class_emotion',
            method: 'post',
            data: {
                
            }
        })
    },

}
