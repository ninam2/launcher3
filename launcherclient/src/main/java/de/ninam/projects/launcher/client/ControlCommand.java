package de.ninam.projects.launcher.client;


public enum ControlCommand {
    LEFT('a'),
    RIGHT('d'),
    UP('w'),
    DOWN('s'),
    LAUNCH(' '),;

    private Character command;

    public Character getCommand() {
        return command;
    }

    ControlCommand(char shortCommand) {
        this.command = shortCommand;
    }


}