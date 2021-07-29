package io.github.jhipster.sample.service;

import io.quarkus.runtime.configuration.ProfileManager;
import io.github.jhipster.sample.config.JHipsterProperties;
import io.github.jhipster.sample.service.dto.ManagementInfoDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
* Provides information for management/info resource
*/
@ApplicationScoped
public class ManagementInfoService {

    private final JHipsterProperties jHipsterProperties;

    @Inject
    public ManagementInfoService(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    public ManagementInfoDTO getManagementInfo(){
        var info = new ManagementInfoDTO();
        if(jHipsterProperties.info().swagger().enable()){
            info.activeProfiles.add("swagger");
        }
        info.activeProfiles.add(ProfileManager.getActiveProfile());
        info.displayRibbonOnProfiles = ProfileManager.getActiveProfile();
        return info;
    }
}

