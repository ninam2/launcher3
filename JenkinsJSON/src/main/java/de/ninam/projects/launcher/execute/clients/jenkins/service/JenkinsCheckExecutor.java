package de.ninam.projects.launcher.execute.clients.jenkins.service;


import de.ninam.projects.launcher.execute.business.domain.Check;
import de.ninam.projects.launcher.execute.business.domain.CheckResult;
import de.ninam.projects.launcher.execute.clients.jenkins.domain.JenkinsCheck;
import de.ninam.projects.launcher.execute.business.domain.State;
import de.ninam.projects.launcher.execute.clients.jenkins.external.JenkinsBuildInfo;
import de.ninam.projects.launcher.execute.clients.jenkins.external.JenkinsJobInfo;
import de.ninam.projects.launcher.execute.clients.jenkins.utils.JenkinsClient;
import de.ninam.projects.launcher.execute.business.service.CheckExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class JenkinsCheckExecutor implements CheckExecutor {

    @Autowired
    private JenkinsClient jenkinsClient;


    @Override
    public List<CheckResult> executeCheck(Check check) {

        JenkinsCheck jenkinsCheck = (JenkinsCheck) check;

        final String jobName = jenkinsCheck.getName();

        // load results from jenkins
        final JenkinsBuildInfo lastCompletedBuildInfo;
        final JenkinsBuildInfo lastBuildInfo;
        final String url;
        try {
            url = jenkinsCheck.getBaseUrl() + "/job/" + UriUtils.encodePathSegment(jobName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try {
            log.debug("Retrieving job result from jenkins url {}", url);

            final JenkinsJobInfo jobInfo = queryJenkins(jenkinsCheck, url, JenkinsJobInfo.class);

            lastCompletedBuildInfo = queryJenkins(jenkinsCheck, jobInfo.getLastCompletedBuild().getUrl(), JenkinsBuildInfo.class);
            lastBuildInfo = queryJenkins(jenkinsCheck, jobInfo.getLastBuild().getUrl(), JenkinsBuildInfo.class);

        } catch (Exception e) {
            log.error("error fetching jenkins result: {}", jobName, e);
            return Arrays.asList(new CheckResult(State.RED, shortName(jobName), 0, 0, jenkinsCheck.getGroup()).withLink(url));
        }

        int failedTestCount = 0;
        int totalTestCount = 0;

        for (JenkinsBuildInfo.Action action : lastCompletedBuildInfo.getActions()) {
            if (action.getFailCount() != null && action.getTotalCount() != null) {
                failedTestCount += action.getFailCount();
                totalTestCount += action.getTotalCount();
            }
        }


        State state = identifyStatus(lastCompletedBuildInfo, failedTestCount);


        CheckResult checkResult = new CheckResult(state, shortName(jobName), totalTestCount, failedTestCount, jenkinsCheck.getGroup()).withLink(url);
        if (lastBuildInfo.isBuilding()) {
            checkResult = checkResult.markRunning();
        }
        return Arrays.asList(checkResult);
    }


    @Override
    public boolean isApplicable(Check check) {
        return check instanceof JenkinsCheck;
    }

    private State identifyStatus(JenkinsBuildInfo lastSuccessfullBuildInfo, int failedTestCount) {

        if (failedTestCount > 0) {
            return State.YELLOW;
        }

        if (lastSuccessfullBuildInfo.getResult() == null) {
            return State.RED;
        }

        switch (lastSuccessfullBuildInfo.getResult()) {
            case ABORTED:
                return State.GREY;
            case UNSTABLE:
                // if there were only test failures, we never get here. therefore treat unstable as failed
            case FAILURE:
                return State.RED;
            case SUCCESS:
                return State.GREEN;
            default:
                return State.GREY;
        }
    }

    private <T> T queryJenkins(JenkinsCheck jenkinsCheck, String url, Class<T> responseType) throws IOException, AuthenticationException {
        return jenkinsClient.query(url, jenkinsCheck.getUserName(), jenkinsCheck.getApiToken(), responseType);
    }

    String shortName(String jobName) {

        String[] splitJobName = jobName.split("_");
        if (splitJobName.length < 2) {
            log.error("invalid job name ({}).", jobName);
            return jobName;
        }
        return splitJobName[1];
    }
}
