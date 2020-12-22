package com.abc.service;

import com.abc.vo.StudentClassVo;

import java.util.Date;

public interface StudentService {
    // 检查现在是否有课，并返回StudentClassVo(是否有课，课程名字）
    StudentClassVo queryOngoingStudentCourse(String sid);
}
