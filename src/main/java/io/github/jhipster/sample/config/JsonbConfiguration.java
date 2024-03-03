package io.github.jhipster.sample.config;

import io.quarkus.jsonb.JsonbConfigCustomizer;
import jakarta.inject.Singleton;
import jakarta.json.bind.JsonbConfig;
import java.util.Locale;

/**
 * Jsonb Configuration
 * Further details https://quarkus.io/guides/rest-json#configuring-json-support
 */
@Singleton
public class JsonbConfiguration implements JsonbConfigCustomizer {

    @Override
    public void customize(JsonbConfig config) {
        config.withDateFormat(Constants.DATE_TIME_FORMAT, Locale.getDefault());
    }
}
