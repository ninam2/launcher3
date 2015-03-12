package de.ninam.projects.launcher.execute.business.domain;


public abstract class AbstractCheck implements Check {

    private final String name;

    private final Group group;

    private final Team team;


    protected AbstractCheck(String name, Group group, Team team) {
        this.name = name;
        this.group = group;
        this.team = team;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
