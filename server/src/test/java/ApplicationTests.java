

import com.abc.util.Assessment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = ApplicationTests.class)
public class ApplicationTests {

    @Test
    public void contextLoads() {
        Assessment assessment=new Assessment();
        System.out.println("分析后的专注度分数为 "+assessment.AssessmentByImage(Assessment.FULL_MODE,"1","1"));
    }

}
