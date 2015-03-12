package de.ninam.projects.launcher.execute.clients.jenkins.utils;

import de.ninam.projects.launcher.execute.business.domain.Page;
import de.ninam.projects.launcher.execute.business.domain.Check;
import de.ninam.projects.launcher.execute.business.domain.Group;
import de.ninam.projects.launcher.execute.clients.jenkins.domain.JenkinsCheck;
import de.ninam.projects.launcher.execute.clients.jenkins.external.JenkinsJob;
import de.ninam.projects.launcher.execute.clients.jenkins.external.JenkinsJobListWrapper;
import de.ninam.projects.launcher.execute.business.utils.CheckProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class JenkinsCheckProvider implements CheckProvider {

    private final static String PCP_JENKINS_BASE_URL = "http://buildbox.axelspringer.de/jenkins/job/Launcher";
    private final static String PCP_JENKINS_USER_NAME = "pcpmonitor";
    // do not misuse this
    private final static String PCP_JENKINS_API_TOKEN = "40cf54d11e3a80c8753eac9651341a32";

    @Autowired
    private JenkinsClient jenkinsClient;

    @Override
    public List<Check> provideChecks() {

        List<Check> jenkinsChecks = new ArrayList<>();

        final JenkinsJobListWrapper jenkinsJobListWrapper;
        try {
            jenkinsJobListWrapper = jenkinsClient.query(PCP_JENKINS_BASE_URL, PCP_JENKINS_USER_NAME, PCP_JENKINS_API_TOKEN, JenkinsJobListWrapper.class);
        } catch (Exception e) {
            // does this need further processing? fix it if it fails :D
            log.error("could not load job list from jenkins");
            throw new RuntimeException(e);
        }

        List<JenkinsJob> jobs = jenkinsJobListWrapper.getJobs();
        for (JenkinsJob jenkinsJob : jobs) {

            JenkinsCheck jenkinsCheck = createCheck(jenkinsJob);
            if (jenkinsCheck != null) {
                jenkinsChecks.add(jenkinsCheck);
            }
        }

        return jenkinsChecks;
    }

    public Page jobstatus() {
        List<Check> jenkinsChecks = new ArrayList<>();

        final Page page;
        try {
            page = jenkinsClient.query("http://buildbox.axelspringer.de/jenkins/job/Launcher/job/Nina_LauncherJenkins", PCP_JENKINS_USER_NAME, PCP_JENKINS_API_TOKEN, Page.class);
        } catch (Exception e) {
            // does this need further processing? fix it if it fails :D
            log.error("could not load job list from jenkins");
            throw new RuntimeException(e);
        }
        return page;
    }

    private JenkinsCheck createCheck(JenkinsJob jenkinsJob) {

        String jobName = jenkinsJob.getName();
        String[] splitJobName = jobName.split("_");
        if (splitJobName.length < 2) {
            log.debug("ignoring job name ({}) as split name has less than two parts.", jobName);
            return null;
        }

        String groupName = splitJobName[0];
        Group group;
        try {
            group = Group.valueOf(groupName);
        } catch (IllegalArgumentException e) {
            log.debug("ignoring job ({}) as part 1 ({}) of split name could not be parsed to group.", jobName, groupName);
            return null;
        }

        return new JenkinsCheck(jenkinsJob.getName(), PCP_JENKINS_BASE_URL, PCP_JENKINS_USER_NAME, PCP_JENKINS_API_TOKEN, group, null);
    }
}
