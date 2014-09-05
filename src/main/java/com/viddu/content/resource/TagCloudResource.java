package com.viddu.content.resource;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.viddu.content.bo.ContentDb;
import com.viddu.content.bo.TagCloudItem;

@Path("/tagCloud")
public class TagCloudResource {

    @Inject
    ContentDb contentDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<TagCloudItem> getTagCloud(){
        return contentDAO.getTagCloud();
    }
}
