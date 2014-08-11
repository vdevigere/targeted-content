package com.viddu.content.resource;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(String contentJson) throws JsonParseException, JsonMappingException, IOException {
        Content content = mapper.readValue(contentJson, Content.class);
        String savedId = contentDAO.saveUpdate(content, null);
        return savedId;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Content read(@PathParam("id") String contentId) {
        return contentDAO.findContentById(contentId);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String update(String contentJson, @PathParam("id") String id) throws JsonParseException,
            JsonMappingException, IOException {
        Content content = mapper.readValue(contentJson, Content.class);
        String savedId = contentDAO.saveUpdate(content, id);
        return savedId;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String delete(@PathParam("id") String id) {
        boolean deleteStatus = contentDAO.deleteContentById(id);
        return (deleteStatus) ? "Deleted Successfully" : "Could not find record to Delete";
    }

    @GET
    @Path("/active")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Content> getActive(@QueryParam("tags") List<String> tags) {
        return contentDAO.filterActiveContent(tags);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Content> getByTag(@QueryParam("tags") List<String> tags) {
        return contentDAO.findAllContent(tags);
    }
}
