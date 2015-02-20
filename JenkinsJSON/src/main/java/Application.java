import de.ninam.projects.launcher.client.ControlCommand;
import de.ninam.projects.launcher.client.LauncherClient;
import org.springframework.web.client.RestTemplate;

public class Application {

    public static void main(String args[]) {
        LauncherClient launcherClient = new LauncherClient();
        RestTemplate restClient = new RestTemplate();
        Page page = restClient.getForObject("http://localhost:8080/job/test-project/de.ninam.projects$launcherServer/api/json", Page.class);

        System.out.println("Name:    " + page.getName());
        System.out.println("Color:   " + page.getColor());
        if (!"blue".equals(page.getColor())) {

            for (int i = 1; i <= 30; i++) {
                launcherClient.control(ControlCommand.RIGHT);
            }
            launcherClient.control(ControlCommand.UP, ControlCommand.LAUNCH, ControlCommand.ZERO);


           /*CommandWrapper requestObject = new CommandWrapper();
            requestObject.setCommand('w');*/


        } else {
            for (int i = 1; i <= 12; i++) {
                launcherClient.control(ControlCommand.RIGHT);
            }
            launcherClient.control(ControlCommand.UP, ControlCommand.LAUNCH, ControlCommand.ZERO);
        }


    }


}