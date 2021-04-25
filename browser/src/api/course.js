/**
 * 课程管理相关接口
 */
import request from '@/utils/request'

export default {

    // 查询所有课程和时间
    queryCourse() {
        return request({
            url: '/select_all_courses',
            method: 'post',
            data: {
            }
        })
    },

    // 删除课程
    deleteCourse(course) {
        return request({
            url: '/delete_course_by_cid',
            method: 'post',
            data: {
                cid: course,
            }
        })
    },

    // 添加课程
    addCourse(course, startTime, endTime) {
        return request({
            url: '/add_course',
            method: 'post',
            data: {
                cid: course,
                startTime: startTime,
                endTime: endTime
            }
        })
    },

    // 修改课程
    updateCourse(course, startTime, endTime) {
        return request({
            url: '/update_course_by_cid',
            method: 'post',
            data: {
                cid: course,
                startTime: startTime,
                endTime: endTime
            }
        })
    },

    // 查询所有老师的课程
    queryTeacherCourse() {
        return request({
            url: '/query_all_teachers',
            method: 'post',
            data: {
            }
        })
    },

    // 查询所有学生的课程
    queryStudentCourse() {
        return request({
            url: '/query_all_student',
            method: 'post',
            data: {
            }
        })
    },

    // 修改老师的课程
    updateTeacherCourse(teacherName, courses) {
        return request({
            url: '/update_teacher_course',
            method: 'post',
            data: {
                tid: teacherName,
                cidArry: courses
            }
        })
    },

    // 修改学生的课程
    updateStudentCourse(studentName, courses) {
        return request({
            url: '/update_student_course',
            method: 'post',
            data: {
                sid: studentName,
                cidArry: courses
            }
        })
    },

}