package com.abc.vo;

import java.util.HashMap;
import java.util.Map;

public class EmotionVo {
    static HashMap<Integer,String> emotionMap =new HashMap<>();
    static HashMap<String,Integer> reverseMap =new HashMap<>();
    HashMap<String,Integer> emotionVoMap=new HashMap();
    static {
        emotionMap.put(0,"unsure");
        emotionMap.put(1,"listening");
        emotionMap.put(2,"confusing");
        emotionMap.put(3,"understanding");
        emotionMap.put(4,"resist");
        emotionMap.put(5,"disdain");

        reverseMap.put("unsure",0);
        reverseMap.put("listening",1);
        reverseMap.put("confusing",2);
        reverseMap.put("understanding",3);
        reverseMap.put("resist",4);
        reverseMap.put("disdain",5);



    }

    public void add(int emotionNumber){
        String voKey=emotionMap.get(emotionNumber);
        emotionVoMap.put(voKey,emotionVoMap.get(voKey));
    }
    public static int convertToNumber(String emotion){
        System.out.println("emotion"+emotion);
        for(Map.Entry entry: reverseMap.entrySet()){
            System.out.println("key:"+entry.getKey());
            System.out.println("value:"+entry.getValue());
        }
        System.out.println(reverseMap.get("understanding"));
        System.out.println(reverseMap.get("disdain"));
        int res=reverseMap.get(emotion.trim());
        return res;
    }
}
