package com.abc.controller;

import com.abc.annotation.APICallLimiter;
import com.abc.annotation.PermInfo;
import com.abc.service.AttentionService;
import com.abc.service.impl.AttentionServiceImpl;
import com.abc.util.MyTimeUtils;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@PermInfo(value = "注意力分析模块", pval = "a:analysis")
@RestController
@RequestMapping("/analysis")
public class AttentionAnalysisController {

    @Autowired
    AttentionService attentionService;

    @PostMapping
    public Json AttentionAnalysis(@RequestBody String body) {
        String oper = "AttentionAnalysis";

        JSONObject jsonObj = JSON.parseObject(body);
        String image = jsonObj.getString("image");
        String sid = jsonObj.getString("sid");
        String cid = jsonObj.getString("cid");
        //时间命名
        String imageName = jsonObj.getString("image_name");
        int attention=0;
        try{
             attention = attentionService.finalAssessment(image, AttentionServiceImpl.FULL_MODE, sid);
        }catch (Exception e){
            e.printStackTrace();
        }
        //用hashmap存储1分钟以内的attention_value


        System.out.println(attention);
        // 存到数据库

        attentionService.add(cid,sid,attention, MyTimeUtils.StringToDate(image),MyTimeUtils.StringToDate(image));
        return Json.succ(oper);
    }
    @PostMapping("/emotion")
    public Json EmotionAnalysis(@RequestBody String body){
        String oper = "EmotionAnalysis";

        JSONObject jsonObj = JSON.parseObject(body);
        String image = jsonObj.getString("image");
        String imageName = jsonObj.getString("image_name");





        return  null;

    }

}
