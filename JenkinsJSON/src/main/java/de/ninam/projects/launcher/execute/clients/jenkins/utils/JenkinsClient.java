package de.ninam.projects.launcher.execute.clients.jenkins.utils;

import com.google.gson.Gson;
import de.ninam.projects.launcher.execute.business.utils.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
public class JenkinsClient {

    private static final String DEFAULT_PCP_JENKINS_HOST = "buildbox.axelspringer.de/jenkins/job/Launcher";

    /**
     * The hostname is buildbox.axelspringer.de by default, but when running inside a docker container, the jenkins API cannot be accessed
     * because buildbox.axelspringer.de is resolved by the boreus DNS (?) to an IP address that cannot be accessed from within the container.
     * When the application is started inside docker, this property is replaced with the local IP address of the host machine.
     * see the Dockerfile
     */
    private String pcpJenkinsHost = DEFAULT_PCP_JENKINS_HOST;

    @Autowired
    private RestClient restClient;

    @Autowired
    private Gson gson;

    public <T> T query(String url, String username, String password, Class<T> responseType) throws IOException, AuthenticationException {
        String apiUrl = replaceJenkinsHostnameInUrl(url) + "/api/json";
        log.debug("Querying jenkins URL {}", apiUrl);

        String response = restClient.get(apiUrl, username, password, null);
        return gson.fromJson(response, responseType);
    }

    /**
     * This method replaces the hostname of the URL with the configured jenkinsHost.
     * <p>
     * The jenkins url may contain buildbox.axelspringer.de as the hostname,
     * even if the application was started with a different jenkins hostname.
     * <p>
     * The reason is: The first request to jenkins is made with the configured jenkinsHost,
     * but Jenkins itself returns URLs in the response that contain buildbox.axelspringer.de
     * again.
     *
     * @param url The URL to replace the hostname in
     * @return The url where the hostname is replaced
     */
    private String replaceJenkinsHostnameInUrl(String url) {
        if (DEFAULT_PCP_JENKINS_HOST.equals(pcpJenkinsHost)) {
            // if the pcpJenkinsHost was not configured differently from the default value,
            // (that is: the application is not running inside docker container)
            // we do not need to modify the url at all
            return url;
        }

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);
        final UriComponents uriComponents = uriComponentsBuilder.build();

        if (DEFAULT_PCP_JENKINS_HOST.equals(uriComponents.getHost())) {
            // replace the host with the configured pcpJenkinsHost if the hostname is
            // buildbox.axelspringer.de
            uriComponentsBuilder.host(pcpJenkinsHost);
            return uriComponentsBuilder.build().toUriString();
        } else {
            return url;
        }
    }

}
