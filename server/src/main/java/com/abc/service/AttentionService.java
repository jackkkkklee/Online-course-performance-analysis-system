package com.abc.service;

import com.abc.entity.Performance;
import com.abc.vo.ClassAttentionVo;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface AttentionService {
    int finalAssessment(String base64,String mode,String sid,Date image_date) throws IOException;
    int AssessmentByImage(String mode,String sid,String imageName);
    String[] basicAssessment(String sid, String imageName);

    //查询某节课所有学生的平均注意力
    List<ClassAttentionVo> selectAllAverageByCourseAndTime(String cid,  Date startTime,Date endTime) throws CloneNotSupportedException, ParseException;
    void add(String cid,String sid,int attention_value,  Date startTime,Date endTime);
}
