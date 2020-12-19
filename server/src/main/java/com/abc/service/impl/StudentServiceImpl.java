package com.abc.service.impl;

import com.abc.entity.Performance;
import com.abc.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public List<Performance> queryStudentConcentrationByTimeAndClass(String sid, String cid, Date startTime, Date endTime) {
        //mock 模拟
        List<Performance> performanceList =new ArrayList<>();
        Performance performance=new Performance();
        performance.setTimeOffset(new Date());
        performance.setAttention_value(100);
        performanceList.add(performance);
        return performanceList;
    }
}
