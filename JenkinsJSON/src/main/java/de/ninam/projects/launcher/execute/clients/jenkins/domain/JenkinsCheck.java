package de.ninam.projects.launcher.execute.clients.jenkins.domain;


import de.ninam.projects.launcher.execute.business.domain.AbstractCheck;
import de.ninam.projects.launcher.execute.business.domain.Group;
import de.ninam.projects.launcher.execute.business.domain.Team;

public class JenkinsCheck extends AbstractCheck {

    private final String baseUrl;

    private final String userName;

    private final String apiToken;

    public JenkinsCheck(String name, String baseUrl, String userName, String apiToken, Group group, Team team) {
        super(name, group, team);
        this.baseUrl = baseUrl;
        this.userName = userName;
        this.apiToken = apiToken;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getApiToken() {
        return apiToken;
    }
}
