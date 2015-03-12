package de.ninam.projects.launcher.server.web;

import de.ninam.projects.launcher.server.console.Launcher;
import de.ninam.projects.launcher.server.core.LauncherControl;
import de.ninam.projects.launcher.server.targets.Target;
import de.ninam.projects.launcher.server.targets.Targets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "de.ninam.projects.launcher")
@EnableAutoConfiguration
public class LauncherApplication {

    public static void main(String[] args) {
        SpringApplication.run(LauncherApplication.class, args);
    }

    @Bean
    //tells Spring that the method will return an object that should be registered in the spring application context
    public Launcher launcher() {

        return new Launcher();
    }

    @Bean
    public LauncherControl launchercontrol() {

        return new LauncherControl();
    }

    @Bean
    public Target target() {

        return new Target(0, 0);
    }

    @Bean
    public Targets targets() {
        return new Targets();
    }


}
