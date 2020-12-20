package com.abc.service;

import com.abc.vo.StudentClassVo;

public interface StudentService {
    // 检查现在是否有课，并返回课程名字
    StudentClassVo queryStudentCourse(String sid);
}
