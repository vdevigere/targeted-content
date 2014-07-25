package com.viddu.content.resource;

import java.util.Collection;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viddu.content.bo.Content;
import com.viddu.content.redis.RedisDAO;

@Path("/content")
@ApplicationScoped
public class ContentResource {

    private static final String CONTENT = "CONTENT";

    @Inject
    RedisDAO redisDAO;

    private static final Logger logger = LoggerFactory.getLogger(ContentResource.class);

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Map<String, ?> getById(@PathParam("id") String contentId,
            @DefaultValue("0") @QueryParam("depth") Integer depth) {
        return redisDAO.findContentById(CONTENT, contentId, depth);
    }

    @GET
    @Path("/{id}/tags")
    @Produces("application/json")
    public Collection<String> getAllTags(@PathParam("id") String contentId,
            @DefaultValue("0") @QueryParam("depth") Integer depth) {
        return redisDAO.findTagsById(CONTENT, contentId);
    }

    @POST
    @Path("/post")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String saveContent(@BeanParam Content content, @FormParam("tags") String tags) {
        logger.debug("Name={}", content.getName());
        logger.debug("URL={}", content.getUrl());
        logger.debug("Type={}", content.getType());
        logger.debug("Tags={}", tags);
        Long id = null;
        return "Success";
    }
}
