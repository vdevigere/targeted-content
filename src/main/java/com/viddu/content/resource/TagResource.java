package com.viddu.content.resource;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.viddu.content.bo.Content;
import com.viddu.content.redis.RedisDAO;

@Path("/tag")
@ApplicationScoped
public class TagResource {

    private static final String CONTENT = "CONTENT";
	@Inject
    RedisDAO redisDAO;

    @GET
    @Path("/{tagName}")
    @Produces("application/json")
    public Collection<Content> findByTagName(@PathParam("tagName") String tagName) {
        Collection<Content> result = redisDAO.findContentByTagName(CONTENT, tagName, Content.class);
        return result;
    }
}
