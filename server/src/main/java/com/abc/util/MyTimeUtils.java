package com.abc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyTimeUtils {
    public static  String convertToHHmmFormat(Date target)  {
        SimpleDateFormat df=null;
     try {
         df = new SimpleDateFormat("HH:mm");//设置日期格式,这里只取出小时和分钟
     }catch (Exception e){
         e.printStackTrace();
     }

        return df.format(target);
    }
    public static List<Date> getDateList(Date startTime,Date endTime,int interval){
        List<Date> dateList=new ArrayList<>();
        //中间时间的分钟表示
        int min = (int)(endTime.getTime()-startTime.getTime())/1000/60;
        //有多少时刻需要表示(包含首尾)
        int num = min/interval+1;
        int intervalToMin = interval*60*1000;
        for(int i=0;i<num;i++){
            dateList.add(new Date(startTime.getTime()+intervalToMin));
        }
        return dateList;
    }
    public static  List<String> getDateListInHHmm(Date startTime,Date endTime,int interval){
        List<String> dateList=new ArrayList<>();
        //中间时间的分钟表示
        int min = (int)(endTime.getTime()-startTime.getTime())/1000/60;
        //有多少时刻需要表示(包含首尾)
        int num = min/interval+1;
        int intervalToMin = interval*60*1000;
        for(int i=0;i<num;i++){
            dateList.add(convertToHHmmFormat(new Date(startTime.getTime()+intervalToMin)));
        }
        return dateList;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式,这里只取出小时和分钟

        //当前时间只取小时和分钟
        Date nowTime = df.parse(df.format(new Date()));


        System.out.println(df.format(new Date()));

    }
}
