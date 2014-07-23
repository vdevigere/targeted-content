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

@Path("/tag")
@ApplicationScoped
public class TagResource {

    @Inject
    ContentDAO contentDAO;

    @GET
    @Path("/{tagName}")
    @Produces("application/json")
    public Set<Content> getById(@PathParam("tagName") String tagName) {
        return contentDAO.getContentByTagName(tagName, 1);
    }
}
