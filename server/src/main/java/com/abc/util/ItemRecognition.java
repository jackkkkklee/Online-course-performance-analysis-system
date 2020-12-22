package com.abc.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.net.URLEncoder;
import java.util.*;


public class ItemRecognition {
    public static String APIKEY="GZPbOEikY2NScWMvWEnEy1cY";
    public static String SECRETKEY="h3896eFOLKZffVe6mjBOtjmLlzCICNpo";
    public static Set nonClassItemsSet =new HashSet();
    static {
        nonClassItemsSet.add("商品-食品");
        nonClassItemsSet.add("人物活动-餐饮娱乐活动");
    }

    /**
     * 通用物体和场景识别
     */



        public static String advancedGeneral( String filePath, String accessToken) {
            // 请求url
            String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";
            try {
                // 本地文件路径

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
        public static String advancedGeneral( String filePath, String ak,String sk){
            String token=GetBaiduToken.getAuth(ak,sk);
            return advancedGeneral(filePath,token);
        }
        public static HashSet<String> NonClassItems(String filePath, String ak,String sk){
            HashSet<String> resItemsSet=new HashSet();
            try{
                String token=GetBaiduToken.getAuth(ak,sk);
                String res = advancedGeneral(filePath,token);
                res=test(res,"result");
                JSONArray json = JSONArray.parseArray(res);
                Iterator iterator=json.listIterator();
                while(iterator.hasNext()){
                    JSONObject jsonObject=(JSONObject) iterator.next();
                    double score = Double.parseDouble(jsonObject.getString("score"));
                    System.out.println(score);
                    String root= jsonObject.getString("root");
                    System.out.println(root);
                    String name = jsonObject.getString("keyword");

                    //与非学习物品集合比较
                    if (nonClassItemsSet.contains(root)&&score>0.5){
                        resItemsSet.add(name);
                    }

                }
            }catch (Exception e){
                System.out.println("NonClassItems 调用异常");
            }


            return resItemsSet;
        }
    public static HashSet<String> NonClassItems(String imagePath){
        return NonClassItems(imagePath,APIKEY,SECRETKEY);
    }
        public static String test(String result,String target){

            JSONObject jsonObject = JSONObject.parseObject(result);

            // 获取到key为shoppingCartItemList的值

            String r = jsonObject.getString(target);

            System.out.println(r);
            return r;
        }




        public static void main(String[] args) {
            HashSet resItemSet=ItemRecognition.NonClassItems("E:\\git\\biu-master\\server\\src\\main\\resources\\testImage\\4.jpg",APIKEY,SECRETKEY);

        }
    }

