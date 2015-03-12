package de.ninam.projects.launcher.execute.business;

import de.ninam.projects.launcher.client.ControlCommand;
import de.ninam.projects.launcher.client.LauncherClient;
import de.ninam.projects.launcher.execute.business.domain.Check;
import de.ninam.projects.launcher.execute.business.domain.Page;
import de.ninam.projects.launcher.execute.clients.jenkins.utils.JenkinsCheckProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JenkinsCall {

    @Autowired
    private LauncherClient launcherClient;


    @Autowired
    private JenkinsCheckProvider jenkinsCheckProvider;

    private int lastBuildNumber = 0; //später -> neues Objekt für current/lastpage
    private String lastColor = "green";

    public int testConnectionToJenkins() {
        try {
            List<Check> jenkinsChecks = jenkinsCheckProvider.provideChecks();
            return jenkinsChecks.size();

        } catch (Exception e) {
            // does this need further processing? fix it if it fails :D
            System.out.println("could not load job list from jenkins");
            throw new RuntimeException(e);
        }

    }


    public void instructions() {
        Page currentPage = jenkinsCheckProvider.jobstatus();

        System.out.println("Number" + jenkinsCheckProvider.jobstatus().getBuilds());
        System.out.println("Name:    " + currentPage.getName());
        System.out.println("Color:   " + currentPage.getColor());

        if (!currentPage.getColor().equals("blue")) {
            if (lastColor.equals(currentPage.getColor())) {
                System.out.println("it fails again...");
            } else {
                System.out.println("launch");
                launcherClient.control(ControlCommand.LAUNCH);

               /* for (int i = 1; i <= 24; i++) {
                    launcherClient.control(ControlCommand.RIGHT);

                    launcherClient.control(ControlCommand.UP, ControlCommand.LAUNCH, ControlCommand.ZERO);
                }*/
            }
        } else {
            System.out.println("not launch");
        }

        lastColor = currentPage.getColor();

/*       if (!currentPage.get().equals(lastBuildNumber)){
            System.out.println("new build");

        }
        else {
            System.out.println("last build");}
        //???? currentPage == / != lastPage



        if (!"blue".equals(currentPage.getColor())) {

        } else {
            for (int i = 1; i <= 12; i++) {
                launcherClient.control(ControlCommand.RIGHT);
            }
            launcherClient.control(ControlCommand.UP, ControlCommand.LAUNCH, ControlCommand.ZERO);
        }*/
        //lastBuildNumber = currentPage.getNumber();
    }
}





