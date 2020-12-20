package com.abc.controller;

import com.abc.annotation.PermInfo;
import com.abc.entity.Performance;
import com.abc.service.TeacherService;
import com.abc.util.MyTimeUtils;
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
import java.util.List;

@PermInfo(value = "老师模块", pval = "a:teacher")
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private static final Logger log = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    TeacherService teacherService;
    //queryStudentCourse()

    @PostMapping("/query_concentration")
    Json queryStudentConcentration(@RequestBody String body){
        String oper = "queryStudentConcentration";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String sid = jsonObj.getString("sid");
        String cid = jsonObj.getString("cid");
        Date startTime= jsonObj.getDate ("startTime");
        Date endTime = jsonObj.getDate("endTime");
        List<Performance> performanceList= teacherService.queryStudentConcentrationByTimeAndCourse(sid,cid,startTime,endTime);
        // 以 前端需要的格式返回json

        String[] time = new String[performanceList.size()];
        int[] attention_value = new int[performanceList.size()];
        for(int i=0;i<performanceList.size();i++){
            time[i] = MyTimeUtils.convertToHHmmFormat(performanceList.get(i).getTimeOffset());
            attention_value[i] = performanceList.get(i).getAttention_value();
        }
        Json json=Json.succ(oper, "time", time).data("attention_value",attention_value);
        System.out.println(json);
        return json;
    }
}
