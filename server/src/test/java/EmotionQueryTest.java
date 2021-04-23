import com.abc.ServerApplication;
import com.abc.service.EmotionService;
import com.abc.vo.EmotionVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class EmotionQueryTest {

    @Autowired
    EmotionService emotionService;
    @Test
    public void queryEmotion(){
        String cid="class001";
        Date date=new Date();
        EmotionVo emotionVo=emotionService.queryAllEmotionByCourse(cid,date);
        System.out.println(emotionVo);
    }

}
