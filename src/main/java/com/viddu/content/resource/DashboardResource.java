package com.viddu.content.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viddu.content.PageModel;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;
import com.viddu.content.bo.ContentData;
import com.viddu.content.tiles.ModelView;

@Path("/dashboard")
public class DashboardResource {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger logger = LoggerFactory.getLogger(DashboardResource.class);
    @Inject
    @PageModel
    Map<String, Object> pageModel;

    @Inject
    @Named("elasticSearch")
    private ContentDAO contentDAO;

    @GET
    @Path("/new.html")
    public ModelView addNew() {
        ModelView modelView = new ModelView("content.new", pageModel);
        return modelView;
    }

    @GET
    @Path("/search.html")
    public ModelView searchById(@QueryParam("tags") List<String> tags) {
        logger.debug("Tags={}", tags);
        pageModel.put("validContent", contentDAO.filterActiveContent(tags));
        pageModel.put("allContent", contentDAO.findAllContent());
        ModelView modelView = new ModelView("searchForm", pageModel);
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
    public ModelView viewUpdate(@QueryParam("id") String id) {
        Content content = contentDAO.findContentById(id);
        pageModel.put("content", content);
        ModelView modelView = new ModelView("content.new", pageModel);
        return modelView;
    }

    @GET
    @Path("/delete.html")
    public ModelView deleteContent(@QueryParam("id") String id) {
        String status = contentDAO.deleteContentById(id);
        pageModel.put("status", status);
        ModelView modelView = new ModelView("content.delete", pageModel);
        return modelView;
    }

    @POST
    @Path("/save.html")
    @Consumes("application/x-www-form-urlencoded")
    public ModelView saveContent(MultivaluedMap<String, String> formParams) throws ParseException {
        // Extract from FormParams
        String name = formParams.getFirst("content-name");
        List<String> dataList = formParams.get("content-data");
        List<String> weightList = formParams.get("content-weight");
        Date sDate = sdf.parse(formParams.getFirst("start-date"));
        Date eDate = sdf.parse(formParams.getFirst("end-date"));
        Content content = new Content(name, sDate, eDate);
        IntStream.range(0, dataList.size()).parallel().forEach(index -> {
            String data = dataList.get(index);
            Integer weight = Integer.parseInt(weightList.get(index));
            ContentData contentData = new ContentData(data, weight);
            content.addContentData(contentData);
        });
        content.addTags(formParams.get("tags"));
        // If id is null or empty create and get new id.
        String id = contentDAO.saveUpdate(content, formParams.getFirst("id"));

        // Return back to Edit page
        pageModel.put("content", content);
        content.setId(id);
        ModelView modelView = new ModelView("content.new", pageModel);
        return modelView;
    }
}
