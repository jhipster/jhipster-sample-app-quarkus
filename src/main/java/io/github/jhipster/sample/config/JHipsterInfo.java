package io.github.jhipster.sample.config;

import io.quarkus.arc.config.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ConfigProperties(prefix = "jhipster.info")
public interface JHipsterInfo {

    @ConfigProperty(name = "swagger.enable", defaultValue = "true")
    Boolean isEnable();

}
