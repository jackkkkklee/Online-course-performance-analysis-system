package com.abc.service;

import com.abc.vo.EmotionVo;

import java.util.Date;

public interface EmotionService {
    EmotionVo queryAllEmotionByCourse(String cid, Date startTime);
    void addEmotion(String cid,String sid,int express_value,  Date startTime);
    String detectEmotion(String sid,String imageName );
}
