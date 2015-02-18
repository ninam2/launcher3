package de.ninam.projects.launcher.Rest;


import de.ninam.projects.launcher.web.model.CommandWrapper;
import org.springframework.web.client.RestTemplate;

public class RestClient {
    public static void main(String[] args) {
      RestTemplate restClient = new RestTemplate();
        CommandWrapper requestObject = new CommandWrapper();
        requestObject.setCommand('w');
        restClient.postForObject("http://localhost:8080/nina/launcher", requestObject, Void.class);
    }

}