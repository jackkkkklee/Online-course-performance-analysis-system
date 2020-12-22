package com.abc.dao;

import com.abc.entity.Course;
import com.abc.entity.Performance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Mapper
@Component
public interface PerformanceDao {

    //查单个时刻
    public Performance selectOne(String cid, String sid, Date timeOffset);
    //查单个学生一节课单所有时刻表现  ps:这个应该是一种课，一节课要加时间开始 结束！！
    //查单个学生某种课的所有表现
    public List<Performance> selectAll(String cid, String sid);
    //查询某节课所有学生的平均注意力
    List<Performance> selectAllAverageByCourseAndTime(String cid,Date startTime,Date endTime);

}
