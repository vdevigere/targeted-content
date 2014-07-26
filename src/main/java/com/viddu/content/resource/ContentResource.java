package com.viddu.content.resource;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viddu.content.bo.Content;
import com.viddu.content.bo.Taggable;
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
    public Taggable getById(@PathParam("id") String contentId) {
        return redisDAO.findContentById(CONTENT, contentId, Content.class);
    }

    @GET
    @Path("/{id}/tags")
    @Produces("application/json")
    public Collection<String> getAllTags(@PathParam("id") String contentId) {
        return redisDAO.findTagsById(CONTENT, contentId);
    }

    @POST
    @GET
    @Path("/post")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String saveContent(@BeanParam Content content) {
        logger.debug("Name={}", content.getName());
        logger.debug("URL={}", content.getUrl());
        logger.debug("Type={}", content.getType());
         Long id = redisDAO.save(CONTENT, content);
        return id.toString();
    }
}
