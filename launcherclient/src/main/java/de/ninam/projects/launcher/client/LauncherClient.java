package de.ninam.projects.launcher.client;

import org.springframework.web.client.RestTemplate;

public class LauncherClient {
    public void control(ControlCommand... commands) {
        if (commands.length > 0) {
            RestTemplate restClient = new RestTemplate();

            char[] commandoArray = new char[commands.length];

            for (int index = 0; index < commands.length; index++) {
                commandoArray[index]=commands[index].getCommand();
            }
            restClient.postForObject("http://localhost:8080/nina/launcher",commandoArray, Void.class);
        }
    }

    public static void main(String[] args) {
        LauncherClient launcherClient = new LauncherClient();
        launcherClient.control(ControlCommand.LEFT, ControlCommand.DOWN, ControlCommand.UP, ControlCommand.DOWN, ControlCommand.UP, ControlCommand.UP);
    }
}
