package com.viddu.content.resource;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;

@Path("/content")
@ApplicationScoped
public class ContentResource {

    @Inject
    @Named("elasticSearchDAO")
    private ContentDAO contentDAO;

    @Inject
    @Named("mapper")
    private ObjectMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(ContentResource.class);

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Content getById(@PathParam("id") String contentId) {
        return contentDAO.findContentById(contentId);
    }

    @POST
    @GET
    @Path("/save")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/html")
    public String saveContent(MultivaluedMap<String, String> formParams) throws JsonParseException, JsonMappingException, IOException {
        for(String key : formParams.keySet()){
            logger.debug("{}->{}", key, formParams.get(key));
        }
        return null;
    }
}
