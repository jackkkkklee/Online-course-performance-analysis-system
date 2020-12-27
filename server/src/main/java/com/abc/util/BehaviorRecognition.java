package com.abc.util;


import  com.abc.util.Base64Util;
import  com.abc.util.FileUtil;
import  com.abc.util.HttpUtil;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BehaviorRecognition {


    /**

     * 人体检测和属性识别
     */


        /**

         * 重要提示代码中所需工具类

         * FileUtil,Base64Util,HttpUtil,GsonUtils请从

         * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72

         * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2

         * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3

         * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3

         * 下载
         */
        public static Boolean isUsingCellPhone=false;
        public static Boolean isSmoking=false;

        public static String body_attr(String filePath,String accessToken) {
            // 请求url
            String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/body_attr";
            try {
                // 本地文件路径
                //String filePath = "[本地文件路径]";
                byte[] imgData = FileUtil.readFileByBytes(filePath);
                String imgStr = Base64Util.encode(imgData);
                String imgParam = URLEncoder.encode(imgStr, "UTF-8");

                String param = "image=" + imgParam;

                // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。


                String result = HttpUtil.post(url, accessToken, param);
                System.out.println(result);
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public static String body_attr(String filePath,String ak, String sk) {
            String token=GetBaiduToken.getAuth(ak,sk);
            return body_attr(filePath,token);
        }

        public static String body_attr(String filePath) {
        String token=GetBaiduToken.getAuth();
        return body_attr(filePath,token);
        }

        public static  void jsonIterate(JSONObject jsonObject){
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                System.out.println("key值="+entry.getKey());
                System.out.println("对应key值的value="+entry.getValue());
            }
        }
        public static  void getInfoFromJson(JSONObject jsonObject){
            String res = jsonObject.getString("person_info");
            JSONArray json = JSONArray.parseArray(res);
            System.out.println("res"+res);
            List list=json.toJavaList(String.class);

            Iterator iterator=json.listIterator();
            while(iterator.hasNext()) {
                JSONObject js=(JSONObject)iterator.next();
                String cell=JSONObject.parseObject(js.getString("attributes")).getString("cellphone");

                if(JSONObject.parseObject(cell).getString("name").equals("看手机")){
                    isUsingCellPhone=true;
                    System.out.println(JSONObject.parseObject(cell).getString("name"));
                }else{
                    isUsingCellPhone=false;
                }

                String smoke=JSONObject.parseObject(js.getString("attributes")).getString("smoke");
                if(JSONObject.parseObject(smoke).getString("name").equals("吸烟")){
                    isSmoking=true;
                }else{
                    isSmoking=false;
                }

            }

        }
        public static void CheckBehaviorInClass(String imagePath){
            //
            String res=BehaviorRecognition.body_attr(imagePath);
            //if(res.contains("cellphone"))
            JSONObject jsonObject = JSON.parseObject(res);
            getInfoFromJson(jsonObject);
            System.out.println("phone: "+isUsingCellPhone);
            System.out.println("smoke: "+isSmoking);
        }


        public static void main(String[] args) {

            //
            String res=BehaviorRecognition.body_attr("E:\\git\\biu-master\\server\\src\\main\\resources\\testImage\\7.jpg");
            //if(res.contains("cellphone"))
            JSONObject jsonObject = JSON.parseObject(res);
            getInfoFromJson(jsonObject);
            System.out.println("phone: "+isUsingCellPhone);
            System.out.println("smoke: "+isSmoking);
        }
    }

