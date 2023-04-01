package io.github.jhipster.sample.web.rest.vm;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RegisterForReflection
public class LoggerVM {

    public String name;
    public String effectiveLevel;
    public String configuredLevel;

    public LoggerVM(String name, Logger logger) {
        this.name = name;
        this.configuredLevel = Optional.of(logger).map(Logger::getLevel).map(Level::getName).orElse(null);
        this.effectiveLevel = retrieveEffectiveLogLevel(logger);
    }

    public LoggerVM(String name, String effectiveLevel, String configuredLevel) {
        this.name = name;
        this.effectiveLevel = effectiveLevel;
        this.configuredLevel = configuredLevel;
    }

    public LoggerVM() {}

    private String retrieveEffectiveLogLevel(Logger logger) {
        if (logger == null) {
            return null;
        }
        if (logger.getLevel() != null) {
            return logger.getLevel().getName();
        }

        return retrieveEffectiveLogLevel(logger.getParent());
    }
}
