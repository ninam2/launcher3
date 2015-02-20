package de.ninam.projects.launcher.web;

import de.ninam.projects.launcher.core.LauncherControl;
import de.ninam.projects.launcher.web.model.CommandWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/nina")
public class LauncherWebController {

    @Autowired
    private LauncherControl launcherControl;


    @RequestMapping(value = "/launcherOLD", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void executeCommands(@RequestBody char[] commands) {

        for (char command : commands) {
            System.out.println(command);
            launcherControl.init();
            launcherControl.executeCommand(command);
        }

    }

    @RequestMapping(value = "/launcher", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void executeCommands(@RequestBody CommandWrapper commandWrapper) {


        launcherControl.init();
        launcherControl.executeCommand(commandWrapper.getCommand());

    }
/*
    @RequestMapping(value = "/hello/{word}/1", method = RequestMethod.GET) //einfache URL
    public String hello(@PathVariable String word) {
        return "hello = " + word;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET) // mehrere Parameter in URL
    public String hello2(@RequestParam(required = false) String word) {
        return "hello = " + word;
    }*/
}

