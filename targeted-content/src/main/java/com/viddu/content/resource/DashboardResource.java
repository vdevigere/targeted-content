package com.viddu.content.resource;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.api.bo.Content;
import com.viddu.api.bo.ContentData;
import com.viddu.content.PageModel;
import com.viddu.content.bo.DashboardResponse;
import com.viddu.content.tiles.ModelView;

@Path("/dashboard")
@Produces(MediaType.TEXT_HTML)
public class DashboardResource {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private TypeReference<Content<String>> typeRef = new TypeReference<Content<String>>() {
    };

    @Inject
    @PageModel
    private Map<String, Object> pageModel;

    @Inject
    private WebTarget contentTarget;

    @Inject
    private ObjectMapper mapper;

    @GET
    @Path("/new.html")
    public ModelView addNew() {
        ModelView modelView = new ModelView("content.new", pageModel);
        return modelView;
    }

    @GET
    @Path("/search.html")
    public ModelView search() throws JsonProcessingException {
        String initialContent = contentTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        pageModel.put("initialContent", initialContent);
        ModelView modelView = new ModelView("content.search", pageModel);
        return modelView;
    }

    @GET
    @Path("/home.html")
    public ModelView goHome() {
        ModelView modelView = new ModelView("home", pageModel);
        return modelView;
    }

    @GET
    @Path("/edit.html")
    public ModelView viewUpdate(@QueryParam("id") String id) throws JsonParseException, JsonMappingException,
            IOException {
        String contentJson = contentTarget.path(id).request(MediaType.APPLICATION_JSON).get(String.class);
        Content<String> content = mapper.readValue(contentJson, typeRef);
        pageModel.put("content", content);
        ModelView modelView = new ModelView("content.new", pageModel);
        return modelView;
    }

    @GET
    @Path("/delete.html")
    public ModelView deleteContent(@QueryParam("id") String id) {
        Response response = contentTarget.path(id).request(MediaType.APPLICATION_JSON).delete();
        DashboardResponse<String> deleteStatus = (response.getStatus() == 200) ? new DashboardResponse<String>(
                DashboardResponse.Type.SUCCESS, "Deleted Successfully", id) : new DashboardResponse<String>(
                DashboardResponse.Type.WARNING, "Could not find record", id);
        pageModel.put("status", deleteStatus);
        ModelView modelView = new ModelView("content.delete", pageModel);
        return modelView;
    }

    @POST
    @Path("/save.html")
    @Consumes("application/x-www-form-urlencoded")
    public ModelView saveContent(MultivaluedMap<String, String> formParams) throws ParseException,
            JsonProcessingException {
        // Extract from FormParams
        String name = formParams.getFirst("content-name");
        List<String> dataList = formParams.get("content-data");
        List<String> weightList = formParams.get("content-weight");
        Date sDate = sdf.parse(formParams.getFirst("start-date"));
        Date eDate = sdf.parse(formParams.getFirst("end-date"));
        Collection<String> tags = formParams.get("tags");
        Content<String> content = new Content<String>(name, sDate, eDate);
        if (dataList != null && !dataList.isEmpty()) {
            IntStream.range(0, dataList.size()).parallel().forEach(index -> {
                String data = dataList.get(index);
                Integer weight = Integer.parseInt(weightList.get(index));
                ContentData<String> contentData = new ContentData<>(data, weight);
                content.addContentData(contentData);
            });
        }
        content.addTags(tags);
        String contentJson = mapper.writeValueAsString(content);
        // If id is null or empty create and get new id.
        String id = formParams.getFirst("id");
        Response response;
        if (id == null || id.isEmpty()) {
            response = contentTarget.request(MediaType.APPLICATION_JSON).post(
                    Entity.entity(contentJson, MediaType.APPLICATION_JSON));
        } else {
            response = contentTarget.path(id).request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(contentJson, MediaType.APPLICATION_JSON));
        }
        id = response.readEntity(String.class);

        // Return back to Edit page
        pageModel.put("content", content);
        content.setId(id);
        DashboardResponse<String> saveStatus = new DashboardResponse<String>(DashboardResponse.Type.SUCCESS,
                "Saved Successfully", id);
        pageModel.put("status", saveStatus);
        ModelView modelView = new ModelView("content.new", pageModel);
        return modelView;
    }
}
