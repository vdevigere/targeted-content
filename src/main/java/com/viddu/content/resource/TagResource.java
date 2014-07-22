package com.viddu.content.resource;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.viddu.content.redis.TagDAO;

@Path("/tag")
@ApplicationScoped
public class TagResource {

    @Inject
    TagDAO tagDAO;

    @GET
    @Path("/{tagName}")
    @Produces("application/json")
    public Set<String> getById(@PathParam("tagName") String tagName) {
        return tagDAO.getTaggedContent(tagName);
    }
}
