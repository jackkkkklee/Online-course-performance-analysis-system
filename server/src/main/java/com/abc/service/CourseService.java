package com.abc.service;

import com.abc.entity.Student;

import java.util.List;

public interface CourseService {
    List<Student>  selectStudentByCourse(String cid);
}
