package de.ninam.projects.launcher.client;

import java.util.Arrays;
import java.util.List;

public class Command {

    private List<ControlCommand> controlCommand;

    public Command() {
        this.controlCommand = controlCommand;
    }

    public Command(ControlCommand controlCommand) {
        this.controlCommand = Arrays.asList(controlCommand);
    }

    public List<ControlCommand> getControlCommand() {
        return controlCommand;
    }
}


