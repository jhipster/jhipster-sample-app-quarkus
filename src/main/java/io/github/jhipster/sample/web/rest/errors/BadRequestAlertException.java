package io.github.jhipster.sample.web.rest.errors;

import org.eclipse.microprofile.config.ConfigProvider;
import com.tietoevry.quarkus.resteasy.problem.HttpProblem;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.net.URI;

public class BadRequestAlertException extends HttpProblem {

    private static final String APPLICATION_NAME = ConfigProvider.getConfig()
        .getOptionalValue("application.name", String.class)
        .orElse("jhipster");

    private static final long serialVersionUID = 1L;

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(builder()
            .withType(type)
            .withStatus(Response.Status.BAD_REQUEST)
            .withTitle(defaultMessage)
            .withHeader("X-" + APPLICATION_NAME + "-error", "error." + errorKey)
            .withHeader("X-" + APPLICATION_NAME + "-params", entityName)
            .with("entityName", entityName)
            .with("errorKey", errorKey)
            .with("message", "error." + errorKey)
            .with("params", entityName));
    }

}
