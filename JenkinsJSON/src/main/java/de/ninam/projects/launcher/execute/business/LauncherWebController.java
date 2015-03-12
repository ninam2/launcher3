package de.ninam.projects.launcher.execute.business;

import de.ninam.projects.launcher.execute.clients.jenkins.utils.JenkinsCheckProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/nina")
public class LauncherWebController {

    @Autowired
    private JenkinsCall jenkinsCall;


    @Autowired
    private JenkinsCheckProvider jenkinsCheckProvider;

    public LauncherWebController() {
    }

    @RequestMapping(value = "/instructions", method = RequestMethod.GET)
    public void executeCommands() {

        jenkinsCall.instructions();

    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public int test() {

        int count = jenkinsCall.testConnectionToJenkins();
        return count;

    }
}


