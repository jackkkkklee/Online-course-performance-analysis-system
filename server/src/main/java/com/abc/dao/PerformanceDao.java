package com.abc.dao;

import com.abc.entity.Course;
import com.abc.entity.Performance;
import jnr.constants.platform.INAddr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Mapper
@Component
public interface PerformanceDao {

    void add(@Param("cid") String cid, @Param("sid")String sid,@Param("attention_value") int attention_value ,@Param("timeOffset") Date timeOffset);

    void addEmotion(@Param("cid") String cid, @Param("sid")String sid,@Param("expression_value") int expression_value ,@Param("timeOffset") Date timeOffset);
    //查某个学生单个时刻
    public Performance selectOne(@Param("cid") String cid, @Param("sid")String sid,@Param("timeOffset") Date timeOffset);

    public List<Integer> selectOne11(@Param("cid") String cid, @Param("sid")String sid);

    //查单个学生一节课单所有时刻表现  ps:这个应该是一种课，一节课要加时间开始 结束！！
    //查单个学生某种课的所有表现
    public List<Performance> selectAll(@Param("cid")String cid, @Param("sid")String sid);
    Integer selectNewestEmotionForSingleStu(@Param("cid") String cid, @Param("sid")String sid,@Param("startDate") Date startDate,@Param("endDate")Date endDate);
    //查学生的表情
    Integer selectEmotionForSingleStu(@Param("cid") String cid, @Param("sid")String sid,@Param("timeOffset") Date timeOffset);





}
