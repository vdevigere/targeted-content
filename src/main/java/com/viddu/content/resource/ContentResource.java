package com.viddu.content.resource;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.viddu.content.bo.Content;
import com.viddu.content.redis.ContentDAO;

@Path("/content")
@ApplicationScoped
public class ContentResource {

    @Inject
    ContentDAO contentDAO;

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Content getById(@PathParam("id") Long id) {
        return contentDAO.getContentById(id);
    }
    
    @GET
    @Path("/{id}/tags")
    @Produces("application/json")
    public Set<String> getAllTags(@PathParam("id") Long id){
        return contentDAO.getTagsById(id);
    }
}
