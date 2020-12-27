package com.abc.service.impl;

import com.abc.dao.CourseDao;
import com.abc.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CourseServiceImpl implements com.abc.service.CourseService {
    @Autowired
    CourseDao courseDao;
    @Override
    public List<Student> selectStudentByCourse(String cid) {
        return courseDao.selectStudentByCourse(cid);

    }
}
