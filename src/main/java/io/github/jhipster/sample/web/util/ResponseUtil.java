package io.github.jhipster.sample.web.util;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public interface ResponseUtil {
    static Response wrapOrNotFound(Optional<?> maybeResponse) {
        return wrapOrNotFound(maybeResponse, Collections.emptyMap());
    }

    static Response wrapOrNotFound(Optional<?> maybeResponse, Map<String, String> headersIfPresent) {
        return maybeResponse
            .map(value -> {
                Response.ResponseBuilder response = Response.ok(value);
                headersIfPresent.forEach(response::header);
                return response.build();
            })
            .orElseThrow(NotFoundException::new);
    }
}
