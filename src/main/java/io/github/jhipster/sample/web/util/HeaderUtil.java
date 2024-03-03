package io.github.jhipster.sample.web.util;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private HeaderUtil() {}

    /**
     * <p>createAlert.</p>
     *
     * @param applicationName a {@link java.lang.String} object.
     * @param message         a {@link java.lang.String} object.
     * @param param           a {@link java.lang.String} object.
     * @return a {@link java.util.Map} object.
     */
    public static Map<String, String> createAlert(String applicationName, String message, String param) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-" + applicationName + "-alert", message);
        headers.put("X-" + applicationName + "-params", param);
        return headers;
    }

    /**
     * <p>createEntityCreationAlert.</p>
     *
     * @param applicationName   a {@link java.lang.String} object.
     * @param enableTranslation a boolean.
     * @param entityName        a {@link java.lang.String} object.
     * @param param             a {@link java.lang.String} object.
     * @return a {@link jakarta.ws.rs.core.MultivaluedMap} object.
     */
    public static Map<String, String> createEntityCreationAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String param
    ) {
        String message = enableTranslation
            ? applicationName + "." + entityName + ".created"
            : "A new " + entityName + " is created with identifier " + param;
        return createAlert(applicationName, message, param);
    }

    /**
     * <p>createEntityUpdateAlert.</p>
     *
     * @param applicationName   a {@link java.lang.String} object.
     * @param enableTranslation a boolean.
     * @param entityName        a {@link java.lang.String} object.
     * @param param             a {@link java.lang.String} object.
     * @return a {@link jakarta.ws.rs.core.MultivaluedMap} object.
     */
    public static Map<String, String> createEntityUpdateAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String param
    ) {
        String message = enableTranslation
            ? applicationName + "." + entityName + ".updated"
            : "A " + entityName + " is updated with identifier " + param;
        return createAlert(applicationName, message, param);
    }

    /**
     * <p>createEntityDeletionAlert.</p>
     *
     * @param applicationName   a {@link java.lang.String} object.
     * @param enableTranslation a boolean.
     * @param entityName        a {@link java.lang.String} object.
     * @param param             a {@link java.lang.String} object.
     * @return a {@link jakarta.ws.rs.core.MultivaluedMap} object.
     */
    public static Map<String, String> createEntityDeletionAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String param
    ) {
        String message = enableTranslation
            ? applicationName + "." + entityName + ".deleted"
            : "A " + entityName + " is deleted with identifier " + param;
        return createAlert(applicationName, message, param);
    }

    /**
     * <p>createFailureAlert.</p>
     *
     * @param applicationName   a {@link java.lang.String} object.
     * @param enableTranslation a boolean.
     * @param entityName        a {@link java.lang.String} object.
     * @param errorKey          a {@link java.lang.String} object.
     * @param defaultMessage    a {@link java.lang.String} object.
     * @return a {@link jakarta.ws.rs.core.MultivaluedMap} object.
     */
    public static Map<String, String> createFailureAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String errorKey,
        String defaultMessage
    ) {
        log.error("Entity processing failed, {}", defaultMessage);

        String message = enableTranslation ? "error." + errorKey : defaultMessage;

        Map<String, String> headers = new HashMap<>();
        headers.put("X-" + applicationName + "-error", message);
        headers.put("X-" + applicationName + "-params", entityName);
        return headers;
    }
}
