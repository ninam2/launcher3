package de.ninam.projects.launcher.server.Rest;


import de.ninam.projects.launcher.server.web.model.CommandWrapper;
import org.springframework.web.client.RestTemplate;

public class OldRestClient {
    public static void main(String[] args) {
        RestTemplate restClient = new RestTemplate();
        CommandWrapper requestObject = new CommandWrapper();
        requestObject.setCommand('w');
        restClient.postForObject("http://localhost:9090/nina/launcher", requestObject, Void.class);
        //restClient.getForObject("http://localhost:8080/job/test-project/de.ninam.projects$launcher/api/json",  );
    }

}