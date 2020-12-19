package com.abc.util;

import com.abc.annotation.APICallLimiter;
import com.abc.config.TaskExecutorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * assessment for facial expression,behaviour,non class related items
 * and basic attention(head orientation ,gaze,sleepy,yawn detection)
 */
@Component
public class Assessment {
    /**
     * 1 means basic attention(head orientation ,gaze,sleepy,yawn detection)
     * 2 means behaviour detection
     * 3 means non class related items
      */
    static HashMap<String,Integer> sleepCountMap = new HashMap<>();
    //改到配置文件中
    public final static String IMAGE_PATH="E:\\git\\biu-master\\server\\src\\main\\resources\\testImage\\";
    public final static String BASIC_MODE="1";
    public final static String FULL_MODE="123";
    public final static Integer SLEEP_ARRANGE=90;



    //@Async("asyncPromiseExecutor")
    @APICallLimiter
    public void CheckBehaviorInClass(int basicAttention, String imagePath,String sid){
        //System.out.println("调用前"+BehaviorRecognition.isSmoking);
        BehaviorRecognition.CheckBehaviorInClass(imagePath);
        //System.out.println("调用后"+BehaviorRecognition.isSmoking);
        if(BehaviorRecognition.isSmoking)
            basicAttention-=30;
        if(BehaviorRecognition.isUsingCellPhone)
            basicAttention-=30;
    }
    //@Async("asyncPromiseExecutor")
    @APICallLimiter
    public void nonClassItemChecking(int basicAttention, String imagePath,String sid ){

        HashSet<String> nonClassItemsSet=ItemRecognition.NonClassItems(imagePath);
        basicAttention -= nonClassItemsSet.size()*10;
    }


    public static void storeImage(String base64, String sid){
        Long time=Calendar.getInstance().getTimeInMillis();
        String path="E:/git/biu-master/server/src/main/resources/studentImage";
        //Name the picture with student ID and time
        String imageName = sid+time;
        Base64ToImage.decodeBase64ToImage(base64,path,sid+time);
    }

    public  boolean sleepDetection(String sid, String[] res){
        //"是否睡觉"  暂时存放在内存，每节课后清理
        if(!sleepCountMap.containsKey(sid)){
            sleepCountMap.put(sid,0);
        }
        String direction=res[1];
        String yawn=res[2];
        String sleepChance=res[3];
        if(sleepChance.equals("True")){
            if(direction.equals("Down"))
                sleepCountMap.put(sid,(int)sleepCountMap.get(sid)+3);
            if(yawn.equals("True"))
                sleepCountMap.put(sid,(int)sleepCountMap.get(sid)+3);
        }else{
            sleepCountMap.put(sid,(int)sleepCountMap.get(sid)-2);
        }
        if(sleepCountMap.get(sid)>SLEEP_ARRANGE)
            return true;

        return false;
    }

    /**
     * 清空文件的任务可以是异步的，也可以设置为定时任务
     */
    @Async("asyncPromiseExecutor")
    public void clean(){
        String path="xx";
        DeleteFile.delAllFile(path);
        System.out.println("clean the image file in :"+path);
        System.out.println("所有图片都被消费后，清除上一节课程的图片");
    }
    public static  void  main(String[] arg){
        //System.out.println("分析后的专注度分数为 "+Assessment.AssessmentByImage(BASIC_MODE,"3","2"));
        Assessment assessment=new Assessment();
        // System.out.println("分析后的专注度分数为 "+assessment.AssessmentByImage(FULL_MODE,"1","1"));
    }

}
