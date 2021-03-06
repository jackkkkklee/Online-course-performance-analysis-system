package com.abc.controller;

import com.abc.annotation.PermInfo;
import com.abc.service.EmotionService;
import com.abc.service.TeacherService;
import com.abc.util.MyTimeUtils;
import com.abc.vo.EmotionVo;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@PermInfo(value = "表情识别模块", pval = "a:emotion")
@RestController
@RequestMapping("/emotion")
public class EmotionDetectionController {
    @Autowired
    EmotionService emotionService;
    @Autowired
    TeacherService teacherService;

    private static final Logger log = LoggerFactory.getLogger(EmotionDetectionController.class);
    @PostMapping("/query_class_emotion")
    public Json queryRealTimeEmotionOfAllStu(@RequestBody String body){
        String oper = "queryRealTimeEmotionOfAllStu";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String tid = jsonObj.getString("tid");
        Date startDate= MyTimeUtils.convertToHHmmFormatInDate(new Date());
        //更改为查询最新的
        String cid = teacherService.queryRealTimeCourseByTeacherId(tid);
        if(cid ==null){
            return Json.fail("select ongoing course","no ongoing course");
        }
        EmotionVo emotionVo=emotionService.queryAllEmotionByCourse(cid,startDate);

        return Json.succ(oper).data("emotion",emotionVo.getEmotionVoMap()).data("test","hello");
    }
}
