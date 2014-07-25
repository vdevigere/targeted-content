package com.viddu.content.resource;

import java.util.Collection;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
    public Collection<? extends Map<String, ?>> findByTagName(@PathParam("tagName") String tagName, @DefaultValue("0") @QueryParam("depth") Integer depth) {
        return redisDAO.findByTagName(CONTENT, tagName, depth);
    }
}
