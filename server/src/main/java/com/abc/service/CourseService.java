package com.abc.service;


import com.abc.entity.Course;
import com.abc.entity.Student;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.abc.entity.Student;


import java.util.List;

public interface CourseService {
    List<Student>  selectStudentByCourse(String cid);

    //根据课程cid删除课程服务
    void deleteCourseByCidService(String cid);
    void addCourseService(Course course);
    List<Course> selectCourseByTidService(String tid);
    //查询所有课程
    List<Course> selectAllCoursesService();
    //修改课程
    void updateCourse(String cid,Date startTime,Date endTime);
    //根据tid清空老师课程
    void clearTeacherCourseService(String tid);
    //为课程修改授课老师
    void setNewTeacherForCourseService(String cid,String tid);

}
