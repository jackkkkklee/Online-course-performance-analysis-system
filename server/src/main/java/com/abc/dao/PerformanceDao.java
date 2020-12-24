package com.abc.dao;

import com.abc.entity.Course;
import com.abc.entity.Performance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Mapper
@Component
public interface PerformanceDao {

    void add(@Param("cid") String cid, @Param("sid")String sid,@Param("attention_value") int attention_value ,@Param("timeOffset") Date timeOffset);
    //查某个学生单个时刻
    public Performance selectOne(@Param("cid") String cid, @Param("sid")String sid,@Param("timeOffset") Date timeOffset);
    //查单个学生一节课单所有时刻表现  ps:这个应该是一种课，一节课要加时间开始 结束！！
    //查单个学生某种课的所有表现
    public List<Performance> selectAll(@Param("cid")String cid, @Param("sid")String sid);


}
