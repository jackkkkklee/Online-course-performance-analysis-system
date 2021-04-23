import com.abc.ServerApplication;
import com.abc.entity.Performance;
import com.abc.service.TeacherService;
import com.abc.util.MyTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class QueryDataTest {
    @Autowired
    TeacherService teacherService;


    @Before
    public void setup(){
        System.out.println("执行初始化任务");
    }

    /**
     * bug原因：sql是根据固定事件查找（秒为00），而数据库中 秒是其他值！！！！
     * 解决方法：负面情绪优先捕获
     */
    @Test
    public void queryStudentTest(){
        String sid="stu001";
        String cid="class001";
        //"yyyy-MM-dd HH:mm"
        Date start= MyTimeUtils.StringToDate("2021-4-19 10:00");
        Date end =MyTimeUtils.StringToDate("2021-4-19 11:00");
        List<Performance> performanceList= teacherService.queryStudentConcentrationByTimeAndCourse(sid,cid,start,end);
        myPrint(performanceList);
    }
    @Test
    public void queryAttentionTest(){

    }
    //泛型方法使用
    public <T>void myPrint(List<T> list){
        System.out.println("****************************");
        for(T t:list ){
            System.out.println(t);
        }
        System.out.println("****************************");
    }
    @After
    public void afterOperation(){
        //Method afterOperation() should be public
        System.out.println("结束测试的收尾工作");
    }



    //测试参考链接 https://blog.csdn.net/fyyyr/article/details/83621377?utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control&dist_request_id=&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control
    //other utils such as MOCK, Assert.assertThat(object,condition)
}
