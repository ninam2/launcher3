package de.ninam.projects.launcher.execute.business.domain;

import lombok.Getter;

@Getter
public class CheckResult {

    private State state;

    private String name;

    private Group group;

    private Team team;

    /**
     * Link for Detail info.
     */
    private String link;

    /**
     * Indicates errors of the check execution.
     * It does not mean, that the validation/check against a metric failed or was not successful. In the case of errors, it is not possible to define success/failure!
     */
    private boolean isError = false;

    private int testCount = 0;

    private int failCount = 0;

    private boolean running = false;

    public CheckResult(State state, String name, int testCount, int failCount, Group group) {
        this.state = state;
        this.name = name;
        this.testCount = testCount;
        this.failCount = failCount;
        this.group = group;
    }


    public CheckResult markRunning() {
        this.running = true;
        return this;
    }

    public CheckResult withLink(String link) {
        this.link = link;
        return this;
    }

    public CheckResult withTeam(Team team) {
        this.team = team;
        return this;
    }

}
