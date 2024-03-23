package io.github.jhipster.sample.web.util;

import io.github.jhipster.sample.service.Paged;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.ArrayList;

public final class PaginationUtil {

    public static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";
    public static final String HEADER_X_PAGE_COUNT = "X-Page-Count";
    public static final String HEADER_LINK = "Link";
    public static final String REL_FIRST = "first";
    public static final String REL_LAST = "last";
    public static final String REL_PREV = "prev";
    public static final String REL_NEXT = "next";

    private PaginationUtil() {}

    public static <T> Response.ResponseBuilder withPaginationInfo(Response.ResponseBuilder builder, UriInfo uriInfo, Paged<T> paged) {
        builder.header(HEADER_X_TOTAL_COUNT, paged.totalCount);
        builder.header(HEADER_X_PAGE_COUNT, paged.pageCount);

        var pageNumber = paged.index;
        var pageSize = paged.size;

        var links = new ArrayList<String>();

        links.add(prepareLink(uriInfo, 0, pageSize, REL_FIRST));
        links.add(prepareLink(uriInfo, paged.pageCount - 1, pageSize, REL_LAST));

        if (pageNumber > 0) {
            links.add(prepareLink(uriInfo, pageNumber - 1, pageSize, REL_PREV));
        }

        if (pageNumber < paged.pageCount - 1) {
            links.add(prepareLink(uriInfo, pageNumber + 1, pageSize, REL_NEXT));
        }

        builder.header(HEADER_LINK, String.join(",", links));

        return builder;
    }

    private static String prepareLink(UriInfo uriInfo, long pageNumber, long pageSize, String relType) {
        return Link.fromUri(
            uriInfo
                .getRequestUriBuilder()
                .replaceQueryParam("page", pageNumber)
                .replaceQueryParam("size", pageSize)
                .buildFromEncoded()
                .toString()
                .replace(",", "%2C")
                .replace(";", "%3B")
        )
            .rel(relType)
            .build()
            .toString();
    }
}
