package de.ninam.projects.launcher.execute.business.domain;

/**
 * Defines the model check belonging to a team and group.
 */
public interface Check {

    public String getName();

    public Group getGroup();

    public Team getTeam();

}
