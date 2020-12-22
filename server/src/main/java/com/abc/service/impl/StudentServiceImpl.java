package com.abc.service.impl;

import com.abc.dao.StudentDao;
import com.abc.entity.Course;
import com.abc.service.StudentService;
import com.abc.vo.StudentClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;


    @Override
    public StudentClassVo queryOngoingStudentCourse(String sid) {
        Date now = new Date();
        Course course =studentDao.selectStudentCourseByDate(sid,now);
        if(course==null){
            return  new StudentClassVo(false,null);
        }
        return new StudentClassVo(true,course.getCourse());
    }
}
