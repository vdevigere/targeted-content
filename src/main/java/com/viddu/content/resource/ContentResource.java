package com.viddu.content.resource;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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

    @GET
    @Path("/all")
    @Produces("application/json")
    public Collection<Content> getAll() {
        return contentDAO.findAllContent();
    }

    @GET
    @Path("active")
    @Produces("application/json")
    public Collection<Content> getActive(@QueryParam("tags") List<String> tags) {
        return contentDAO.filterActiveContent(tags);
    }
}
