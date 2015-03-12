import de.ninam.projects.launcher.execute.Application;
import de.ninam.projects.launcher.execute.business.domain.Check;
import de.ninam.projects.launcher.execute.clients.jenkins.utils.JenkinsCheckProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CheckTest.TestConfig.class)
public class CheckTest {

    @Configuration
    @ComponentScan(basePackageClasses = Application.class)
    public static class TestConfig {

    }

    @Autowired
    JenkinsCheckProvider jenkinsCheckProvider;

    @Test
    public void testCheck() {
        List<Check> jenkinsChecks = jenkinsCheckProvider.provideChecks();
        jenkinsChecks.size();

    }
}
