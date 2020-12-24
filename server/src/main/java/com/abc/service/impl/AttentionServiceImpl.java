package com.abc.service.impl;

import com.abc.annotation.APICallLimiter;
import com.abc.dao.CourseDao;
import com.abc.dao.PerformanceDao;
import com.abc.entity.Performance;
import com.abc.entity.Student;
import com.abc.service.AttentionService;
import com.abc.util.*;
import com.abc.vo.ClassAttentionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class AttentionServiceImpl  implements AttentionService {
    @Autowired
    Assessment assessment;
    @Autowired
    PerformanceDao performancedao;
    @Autowired
    CourseDao courseDao;
    /**
     * 1 means basic attention(head orientation ,gaze,sleepy,yawn detection)
     * 2 means behaviour detection
     * 3 means non class related items
     */
    static HashMap<String,Integer> sleepCountMap = new HashMap<>();
    //改到配置文件中
<<<<<<< HEAD
    public final static String IMAGE_PATH="E:/testImageForOnlineClass/";
=======
    @Value("${imageDir.path}")
    public  String IMAGE_PATH;
>>>>>>> d0591055ecacba84f5baab825b0860dfbcb011ed
    public final static String BASIC_MODE="1";
    public final static String FULL_MODE="123";
    public final static Integer SLEEP_ARRANGE=90;


    /**param1 is a image json from student,param 2 is the mode name,param 3 :studentID 4:imageName based on timestamp
     * 此时暂时将图片文件保存下来
     * @return Instantaneous integrated assessment score
     */
    public  int finalAssessment(String base64,String mode,String sid) throws IOException {
        String imageName = assessment.storeImage(base64,sid);
        return AssessmentByImage(mode,sid,imageName);
    }

    public  int AssessmentByImage(String mode,String sid,String imageName){

        //after the image is saved,call python script
        String[] res = basicAssessment(sid,imageName);
        String imagePath = IMAGE_PATH+imageName+".jpg";
        //调用python服务异常
        if(res[0].equals("500")){
            System.out.println("python server 500");
            return 0;
        }else if(res[0].equals("no face")){
            //检测无人脸
            return -1;
        }

        //对分数进行运算  1.打哈欠 -10 2.睡觉为0
        //mode 1
        Integer basicAttention=(int)Double.parseDouble(res[0]);
        String direction=res[1];
        String yawn=res[2];
        Boolean isSleep=assessment.sleepDetection(sid,res);
        if(isSleep)
            return 0;
        else if(yawn.equals("True"))
            basicAttention-=10;

        //mode2 行为识别
        //默认是顺序执行，但是api的调用主要是网络时延；并发调用可以加快时间
        //由于api调用次数有限，要减少调用频率

        //CountDownLatch latch = new CountDownLatch(2);
        //要再另一个注入类中进行方法调用才会触发AOP
        if(mode.contains("2")){
            assessment.CheckBehaviorInClass(basicAttention,imagePath,sid);
        }

        //mode3 非学习物品识别
        if(mode.contains("3")){
            assessment.nonClassItemChecking(basicAttention,imagePath,sid);
        }
        //返回的是瞬时的分数
        return basicAttention>0?basicAttention:0;
    }

    public  String[] basicAssessment(String sid, String imageName){
        //Parametric function: CallPython.callPython(path+imageName);
//        System.out.println(IMAGE_PATH);

        CallPythonByHTTP callPythonByHTTP=new CallPythonByHTTP();
        String res=callPythonByHTTP.CallPythonService(IMAGE_PATH+imageName+".jpg");
        // 0:attention 1: direction 2:yawn status 3:sleep chance
        return res.split("\n");
    }

    @Override
    public List<ClassAttentionVo> selectAllAverageByCourseAndTime(String cid, Date startTime, Date endTime) throws CloneNotSupportedException {
        //查出学这个课的所有学生   （上同一种课的学生不一定是同一个班级的！！！这里有问题，需要创建一个班级表）
        List<Student> studentList = courseDao.selectStudentByCourse(cid);
        List<Date> dateList = MyTimeUtils.getDateListInHHmmAndDate(startTime,endTime,1);
        List<ClassAttentionVo> result =new ArrayList<>();
        //可以使用模板方法
        ClassAttentionVo classAttentionVo=new ClassAttentionVo();
        int tempAttention=0;
        int stuNumber = studentList.size();
        for(Date date:dateList){
            for(Student student:studentList){
                tempAttention+=performancedao.selectOne(cid,student.getSid(),date).getAttention_value();
            }
            //计算平均值
            int averageAttentionValue = tempAttention/stuNumber;
            ClassAttentionVo tempVo= classAttentionVo.clone();
            tempVo.setAttention_value(averageAttentionValue);
            tempVo.setCid(cid);
            tempVo.setTimeOffset(date);
            result.add(tempVo);
            //clean
            tempAttention=0;

        }
        return result;
    }
    @APICallLimiter(limitTimes = 60)
    @Override
    public void add(String cid, String sid, int attention_value, Date startTime, Date endTime) {
        List<Date> dateList = MyTimeUtils.getDateListInHHmmAndDate(startTime,endTime,1);
        for(Date date:dateList)
            performancedao.add(cid,sid,attention_value,date);
    }


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

        HashSet<String> nonClassItemsSet= ItemRecognition.NonClassItems(imagePath);
        basicAttention -= nonClassItemsSet.size()*10;
    }



}
