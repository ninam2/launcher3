package de.ninam.projects.launcher.execute.clients.jenkins.external;

import java.util.List;

public class JenkinsJobListWrapper {

    private List<JenkinsJob> jobs;

    public List<JenkinsJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<JenkinsJob> jobs) {
        this.jobs = jobs;
    }
}
