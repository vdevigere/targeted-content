package com.viddu.content.resource;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CORSFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        logger.debug("Adding CORS headers on response");
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
    }

}
