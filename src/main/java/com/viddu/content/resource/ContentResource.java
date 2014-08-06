package com.viddu.content.resource;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;

@Path("/content")
public class ContentResource {

    @Inject
    private ObjectMapper mapper;

    @Inject
    private ContentDAO contentDAO;

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Content getById(@PathParam("id") String contentId) {
        return contentDAO.findContentById(contentId);
    }

    @POST
    @Consumes("application/json")
    public String createUpdateContent(String contentJson, @DefaultValue("") @QueryParam("id") String id) throws JsonParseException,
            JsonMappingException, IOException {
        Content content = mapper.readValue(contentJson, Content.class);
        String savedId = contentDAO.saveUpdate(content, id);
        return savedId;
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
