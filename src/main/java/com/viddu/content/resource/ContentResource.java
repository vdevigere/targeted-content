package com.viddu.content.resource;

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

import com.viddu.content.ContentDAO;
import com.viddu.content.bo.Content;

@Path("/content")
@ApplicationScoped
public class ContentResource {

    @Inject
    ContentDAO contentDAO;

    private static final Logger logger = LoggerFactory.getLogger(ContentResource.class);

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Content getById(@PathParam("id") String contentId) {
        return contentDAO.findContentById(contentId);
    }

    @POST
    @GET
    @Path("/post")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String saveContent(@BeanParam Content content) {
        logger.debug("Name={}", content.getName());
        Long id = contentDAO.save(content);
        return id.toString();
    }
}
