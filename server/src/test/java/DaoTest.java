import com.abc.ServerApplication;
import com.abc.dao.PerformanceDao;
import com.abc.entity.Performance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class DaoTest {
    @Autowired
    PerformanceDao performanceDao;
    @Test
    public void Performance(){
        Integer EmotionResult=performanceDao.selectEmotionForSingleStu(TestDataExample.cid,TestDataExample.sid,TestDataExample.start);
        if(EmotionResult!=null)
        System.out.println(EmotionResult);
    }
}
