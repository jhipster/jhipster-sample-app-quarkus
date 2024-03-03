package io.github.jhipster.sample.web.rest;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class SpaFilter extends HttpFilter {

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile(".*[.][a-zA-Z\\d]+");

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        chain.doFilter(request, response);

        // do not alter an "application" response coming from the API
        if (request.getRequestURI().startsWith("/api") || request.getRequestURI().startsWith("/management")) {
            return;
        }

        // If the server didn't find the resource
        if (response.getStatus() == 404) {
            // Is it a file (eg. image, font, etc.)
            String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
            if (!FILE_NAME_PATTERN.matcher(path).matches()) {
                // reset response status
                // status is set to 404
                response.setStatus(200);
                // contentType is set as application/json
                response.setContentType("text/html");
                // pass the request resolution to the front-end routes
                request.getRequestDispatcher("/").forward(request, response);
            }
        }
    }
}
