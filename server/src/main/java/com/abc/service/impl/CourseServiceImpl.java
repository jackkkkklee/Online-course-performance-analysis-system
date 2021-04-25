package com.abc.service.impl;

import com.abc.dao.CourseDao;
import com.abc.entity.Course;
import com.abc.entity.Student;
import com.abc.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;
    @Override
    public List<Student> selectStudentByCourse(String cid) {
        return courseDao.selectStudentByCourse(cid);

    }

    @Override
    public void deleteCourseByCidService(String cid) {
        courseDao.deleteCourseByCid(cid);
        return;
    }

    @Override
    public void addCourseService(Course course) {
        courseDao.addCourse(course);
    }

    @Override
    public List<Course> selectCourseByTidService(String tid) {
        List<Course> courses = courseDao.selectCourseByTid(tid);
        return courses;
    }

    @Override
    public List<Course> selectAllCoursesService() {
        List<Course> courses = courseDao.selectAll();
        return courses;
    }

    @Override
    public void updateCourse(String cid, Date startTime, Date endTime) {
        courseDao.update(cid,startTime,endTime);
    }

    @Override
    public void clearTeacherCourseService(String tid) {
        courseDao.clearTeacherCourse(tid);
    }

    @Override
    public void setNewTeacherForCourseService(String cid, String tid) {
        courseDao.updateTeacherForCourse(cid,tid);
    }

}
