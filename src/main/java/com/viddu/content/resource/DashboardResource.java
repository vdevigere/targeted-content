package com.viddu.content.resource;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viddu.content.PageModel;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;
import com.viddu.content.tiles.ModelView;

@Path("/dashboard")
public class DashboardResource {

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
        ModelView modelView = new ModelView("content.addNew", pageModel);
        return modelView;
    }

    @GET
    @Path("/search.html")
    public ModelView searchById(@QueryParam("id") String _id) {
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
    public ModelView viewUpdate(@QueryParam("_id") String _id) {
        Content content = contentDAO.findContentById(_id);
        pageModel.put("content", content);
        pageModel.put("id", _id);
        ModelView modelView = new ModelView("content.addNew", pageModel);
        return modelView;
    }

    @GET
    @Path("/{defName: .*[.html]}")
    public ModelView sayTiles(@PathParam("defName") String defName) {
        logger.debug("Definition Name={}", defName);
        ModelView modelView = new ModelView("content.addNew", pageModel);
        return modelView;
    }
}
