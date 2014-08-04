package com.viddu.content.resource;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;

@Path("/content")
public class ContentResource {

    @Inject
    @Named("elasticSearch")
    private ContentDAO contentDAO;


    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Content getById(@PathParam("id") String contentId) {
        return contentDAO.findContentById(contentId);
    }
}
