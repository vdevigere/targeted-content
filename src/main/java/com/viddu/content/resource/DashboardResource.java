package com.viddu.content.resource;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viddu.content.PageModel;
import com.viddu.content.tiles.ModelView;

@Path("/dashboard")
@ApplicationScoped
public class DashboardResource {

    private static final Logger logger = LoggerFactory.getLogger(DashboardResource.class);

    @Inject
    @PageModel
    Map<String, Object> pageModel;

    @GET
    @Path("/new.html")
    public ModelView addNew() {
        ModelView modelView = new ModelView("content.addNew", pageModel);
        return modelView;
    }

    @GET
    @Path("/home.html")
    public ModelView goHome() {
        ModelView modelView = new ModelView("home", pageModel);
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
