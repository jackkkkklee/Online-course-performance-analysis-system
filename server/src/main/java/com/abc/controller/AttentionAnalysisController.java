package com.abc.controller;

import com.abc.annotation.APICallLimiter;
import com.abc.annotation.PermInfo;
import com.abc.service.AttentionService;
import com.abc.service.EmotionService;
import com.abc.service.impl.AttentionServiceImpl;
import com.abc.util.MyTimeUtils;
import com.abc.vo.AttentionDetailVo;
import com.abc.vo.EmotionVo;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@PermInfo(value = "注意力分析模块", pval = "a:analysis")
@RestController
@RequestMapping("/analysis")
public class AttentionAnalysisController {

    @Autowired
    AttentionService attentionService;
    @Autowired
    EmotionService emotionService;
    @PostMapping
    public Json AttentionAnalysis(@RequestBody String body) {
        String oper = "AttentionAnalysis";

        JSONObject jsonObj = JSON.parseObject(body);
        String image = jsonObj.getString("image");
        String sid = jsonObj.getString("sid");
        String cid = jsonObj.getString("cid");
        Boolean showData = jsonObj.getBoolean("show_data");
        String mode = jsonObj.getString("mode");
        //时间命名
        Date image_date = jsonObj.getDate("image_name");
        AttentionDetailVo attentionDetailVo=null;
        int attention=0;
        try{
             attentionDetailVo= attentionService.finalAssessment(image, mode, sid,image_date);
             if (attentionDetailVo==null)
                 return Json.fail(oper);
             attention=attentionDetailVo.getAttentionValue();
        }catch (Exception e){
            e.printStackTrace();
        }
        //用hashmap存储1分钟以内的attention_value


        System.out.println("专注度:"+attention);
        System.out.println("cid"+cid);
        // 将专注度存到数据库
        attentionService.add(cid,sid,attention,image_date,image_date);
        //将表情存到数据库
        if(attentionDetailVo.getExpressValue()!=null){
            EmotionVo emotionVo=new EmotionVo();
            System.out.println(attentionDetailVo.getExpressValue());
            emotionService.addEmotion(cid,sid, EmotionVo.convertToNumber(attentionDetailVo.getExpressValue()),image_date);
        }


        if(showData){

            return Json.succ(oper,"attentionDetailVo",attentionDetailVo);
        }

        return Json.succ(oper);
    }
//    @PostMapping("/emotion")
//    public Json EmotionAnalysis(@RequestBody String body){
//        String oper = "EmotionAnalysis";
//
//        JSONObject jsonObj = JSON.parseObject(body);
//        String image = jsonObj.getString("image");
//        String imageName = jsonObj.getString("image_name");
//
//
//
//
//
//        return  null;
//
//    }

}
