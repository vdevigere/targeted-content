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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDb;

@Path("/content")
public class ContentResource {

    @Inject
    private ObjectMapper mapper;

    @Inject
    private ContentDb<String> contentDAO;

    private TypeReference<Content<String>> typeRef = new TypeReference<Content<String>>() {
    };

    /**
     * CREATE
     *
     * @param contentJson
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(String contentJson) throws JsonParseException, JsonMappingException, IOException {
        Content<String> content = mapper.readValue(contentJson, typeRef);
        String savedId = contentDAO.saveUpdate(content, null);
        return savedId;
    }

    /**
     * READ
     *
     * @param contentId
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Content<String> read(@PathParam("id") String contentId) {
        return contentDAO.findContentById(contentId);
    }

    /**
     * UPDATE
     *
     * @param contentJson
     * @param id
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String update(String contentJson, @PathParam("id") String id) throws JsonParseException,
            JsonMappingException, IOException {
        Content<String> content = mapper.readValue(contentJson, typeRef);
        String savedId = contentDAO.saveUpdate(content, id);
        return savedId;
    }

    /**
     * DELETE
     *
     * @param id
     * @return
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String delete(@PathParam("id") String id) {
        boolean deleteStatus = contentDAO.deleteContentById(id);
        return (deleteStatus) ? "Deleted Successfully" : "Could not find record to Delete";
    }

    /**
     * READ ALL
     *
     * @param tags
     * @param activeOnly
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Content<String>> getByTag(@QueryParam("tags[]") List<String> tags,
            @QueryParam("activeOnly") boolean activeOnly) {
        return contentDAO.search(tags, activeOnly);
    }
}
