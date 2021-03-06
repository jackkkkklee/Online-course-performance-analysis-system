package com.abc.service.impl;

import com.abc.annotation.APICallLimiter;
import com.abc.dao.CourseDao;
import com.abc.dao.PerformanceDao;
import com.abc.entity.Student;
import com.abc.service.EmotionService;
import com.abc.util.Assessment;
import com.abc.util.CallPythonByHTTP;
import com.abc.util.MyTimeUtils;
import com.abc.vo.EmotionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmotionServiceImpl implements EmotionService {
    @Autowired
    PerformanceDao performanceDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    Assessment assessment;
    @Value("${imageDir.path}")
    public  String IMAGE_PATH;

    @Override
    public EmotionVo queryAllEmotionByCourse(String cid, Date startTime) {
        EmotionVo emotionVo=new EmotionVo();

        Date forwardTime= new Date(((Long)startTime.getTime()/1000/60-300)*1000*60);
        forwardTime = MyTimeUtils.convertToHHmmFormatInDate(forwardTime);

        List<Date> dateList = MyTimeUtils.getDateListInHHmmAndDate(forwardTime,startTime,1);

        List<Student> studentList = courseDao.selectStudentByCourse(cid);
        if (studentList==null){
            return emotionVo;
            }
        Set<Student> studentSet= new HashSet();
        for (Student s:studentList){
            studentSet.add(s);
        }
        for(Student s:studentSet){
            Integer res=performanceDao.selectNewestEmotionForSingleStu(cid,s.getSid(),forwardTime,startTime);
            if(res!=null)
                emotionVo.add(res);
        }

//        //暂时查找5分钟以内的
//        int timeIndex=0;
//        for(Student student:studentList){
//            String sid=student.getSid();
//            if(performanceDao.selectEmotionForSingleStu(cid,sid,dateList.get(timeIndex))==null){
//                timeIndex++;
//                continue;
//            }
//            if(timeIndex>=dateList.size())
//                return emotionVo;
//            int express_value=performanceDao.selectEmotionForSingleStu(cid,sid,dateList.get(timeIndex));
//            emotionVo.add(express_value);
//        }

        return emotionVo;
    }



    @Override
    public void addEmotion(String cid, String sid, int express_value, Date startTime) {
        performanceDao.addEmotion(cid,sid,express_value,startTime);

    }

    @Override
    public String detectEmotion(String sid,String imageName ){
        CallPythonByHTTP callPythonByHTTP=new CallPythonByHTTP();
        String res=callPythonByHTTP.CallPythonService(IMAGE_PATH+imageName+".jpg",12312);
        return  res;
    }


}
