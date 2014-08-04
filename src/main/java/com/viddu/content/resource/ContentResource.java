package com.viddu.content.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;
import com.viddu.content.bo.ContentData;

@Path("/content")
public class ContentResource {

    @Inject
    @Named("elasticSearch")
    private ContentDAO contentDAO;

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Content getById(@PathParam("id") String contentId) {
        return contentDAO.findContentById(contentId);
    }

    @POST
    @GET
    @Path("/save")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/html")
    public String saveContent(MultivaluedMap<String, String> formParams) throws ParseException {
        String name = formParams.getFirst("content-name");
        List<String> dataList = formParams.get("content-data");
        List<String> weightList = formParams.get("content-weight");
        Date sDate = sdf.parse(formParams.getFirst("start-date"));
        Date eDate = sdf.parse(formParams.getFirst("end-date"));
        String id = formParams.getFirst("id");
        Content content = new Content(name, sDate, eDate);
        IntStream.range(0, dataList.size()).parallel().forEach(index -> {
            String data = dataList.get(index);
            Integer weight = Integer.parseInt(weightList.get(index));
            ContentData contentData = new ContentData(data, weight);
            content.addContentData(contentData);
        });
        content.addTags(formParams.get("tags"));
        return contentDAO.saveUpdate(content, id);
    }
}
