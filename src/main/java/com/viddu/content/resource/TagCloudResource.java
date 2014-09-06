package com.viddu.content.resource;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDb;
import com.viddu.content.bo.TagCloudItem;

@Path("/tagCloud")
public class TagCloudResource {

    @Inject
    ContentDb<TagCloudItem> contentDb;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Content<TagCloudItem>> getTagCloud(@QueryParam("tags[]") List<String> tags,
            @QueryParam("activeOnly") boolean activeOnly){
        return contentDb.tagCloud(tags, activeOnly);
    }
}
