package com.abc.service.impl;

import com.abc.entity.Performance;
import com.abc.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class TeacherServiceImpl implements TeacherService {
    @Override
    public List<Performance> queryStudentConcentrationByTimeAndCourse(String sid, String cid, Date startTime, Date endTime) {
        //mock 模拟
        List<Performance> performanceList =new ArrayList<>();
        Performance performance=new Performance();
        performance.setTimeOffset(new Date());
        performance.setAttention_value(100);
        performanceList.add(performance);
        return performanceList;
    }

    @Override
    public List<Performance> queryAverageClassConcentrationByTimeAndCourse(String cid, Date startTime, Date endTime) {
        return null;
    }

    @Override
    public List<Performance> queryClassEmotionInRealTime(String cid) {
        return null;
    }

    @Override
    public List<String> queryCourseByTeacherId(String tid) {
        return null;
    }

    @Override
    public List<String> queryStudentNameByTeacher(String tid) {
        return null;
    }




}
