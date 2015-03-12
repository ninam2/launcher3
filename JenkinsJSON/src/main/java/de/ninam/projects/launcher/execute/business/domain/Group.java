package de.ninam.projects.launcher.execute.business.domain;

import lombok.Getter;

@Getter
public enum Group {
    PROD("prod", "secure.mypass.de", "ws-secure.mypass.de"),
    UAT("test", "test.mypass.de", "ws-test.mypass.de"),
    CONS("cons", "cons.mypass.de", "ws-cons.mypass.de"),
    DEV("dev", "dev.mypass.de", "ws-dev.mypass.de"),
    Nina("nina", null, null);

    private String stageId;
    private String loadbalancerHost;
    private String loadbalancerHostWs;

    private Group(String stageId, String loadbalancerHost, String loadbalancerHostWs) {
        this.stageId = stageId;
        this.loadbalancerHost = loadbalancerHost;
        this.loadbalancerHostWs = loadbalancerHostWs;
    }

}

