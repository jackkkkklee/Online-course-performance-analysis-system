package com.abc.service;

import com.abc.entity.Performance;

import java.util.Date;
import java.util.List;

public interface StudentService {
    //返回某个学生的某种课程的一段时间内的专注度
    List<Performance> queryStudentConcentrationByTimeAndClass(String sid, String cid, Date startTime, Date endTime );
}
