package de.ninam.projects.launcher.client;

public enum ControlCommand {
    LEFT('a'),
    RIGHT('d'),
    UP('w'),
    DOWN('s'),
    LAUNCH(' '),
    ZERO('0'),
    blabla('d');

    private Character command;

    public Character getCommand() {
        return command;
    }

    ControlCommand(char shortCommand) {
        this.command = shortCommand;
    }


}