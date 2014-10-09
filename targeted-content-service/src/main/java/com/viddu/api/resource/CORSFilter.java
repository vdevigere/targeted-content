package com.viddu.api.resource;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CORSFilter implements ContainerResponseFilter {

    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        logger.debug("Adding CORS headers on response");
        logger.debug("Headers={}", requestContext.getHeaders());
        responseContext.getHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        responseContext.getHeaders().add(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS");
        responseContext.getHeaders().add(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
    }

}
