

import com.abc.dao.StudentDao;
import com.abc.dao.TeacherDao;
import com.abc.entity.Teacher;
import com.abc.util.Assessment;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)

public class ApplicationTests {

    @Test
    public void contextLoads() {
        Assessment assessment=new Assessment();
        //System.out.println("分析后的专注度分数为 "+assessment.AssessmentByImage(Assessment.FULL_MODE,"1","1"));
    }

}
