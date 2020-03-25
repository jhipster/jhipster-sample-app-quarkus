package com.mycompany.myapp.web.util;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;


public interface ResponseUtil {

    static Response wrapOrNotFound(Optional maybeResponse) {
        return wrapOrNotFound(maybeResponse, Collections.emptyMap());
    }

    static Response wrapOrNotFound(Optional maybeResponse, Map<String, String> header) {
        Response.ResponseBuilder response = (Response.ResponseBuilder) maybeResponse
            .map(value -> Response.ok(value))
            .orElse(Response.status(Response.Status.NOT_FOUND));
        header.forEach(response::header);
        return response.build();
    }
}
