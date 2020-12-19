package com.abc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式,这里只取出小时和分钟

        //当前时间只取小时和分钟
        Date nowTime = df.parse(df.format(new Date()));


        System.out.println(df.format(new Date()));

    }
}
