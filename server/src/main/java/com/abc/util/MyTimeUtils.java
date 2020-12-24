package com.abc.util;

import javax.xml.crypto.Data;
import java.text.DateFormat;
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
    public static  Date convertToHHmmFormatInDate(Date target)  {
        SimpleDateFormat df=null;
        Date date = null;
        try {
            df = new SimpleDateFormat("HH:mm");//设置日期格式,这里只取出小时和分钟
            date = df.parse(df.format(target));
        }catch (Exception e){
            e.printStackTrace();
        }

        return date ;
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
    public static  List<Date> getDateListInHHmmAndDate(Date startTime,Date endTime,int interval){
        List<Date> dateList=new ArrayList<>();
        //中间时间的分钟表示
        int min = (int)(endTime.getTime()-startTime.getTime())/1000/60;
        //有多少时刻需要表示(包含首尾)
        int num = min/interval+1;
        int intervalToMin = interval*60*1000;
        for(int i=0;i<num;i++){
            dateList.add(convertToHHmmFormatInDate(new Date(startTime.getTime()+intervalToMin)));
        }
        return dateList;
    }
    public static Date createTime(Date target){
        SimpleDateFormat df=null;
        try {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式,这里只取出小时和分钟
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Date(df.format(target));
    }
    public static  Date StringToDate(String target){
        SimpleDateFormat df=null;
        Date date = null;
        try {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式,这里只取出小时和分钟
            date = df.parse(target);

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(date);
        return date;
    }

    public static Date getSystemTime(Date target) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return simpleDateFormat.parse(simpleDateFormat.format(target));

    }

    public static void main(String[] args) throws ParseException {


//        Date date=createTime(new Date());
//        System.out.println(date);
       StringToDate("2020-12-24 12:04");
       StringToDate("2020-12-23 22:20");

    }
}
