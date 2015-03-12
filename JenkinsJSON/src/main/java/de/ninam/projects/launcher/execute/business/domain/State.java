package de.ninam.projects.launcher.execute.business.domain;

public enum State {

    GREY, GREEN, YELLOW, RED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
