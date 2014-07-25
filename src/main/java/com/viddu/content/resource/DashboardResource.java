package com.viddu.content.resource;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viddu.content.bo.ContentType;
import com.viddu.content.tiles.ModelView;

@Path("/dashboard")
@ApplicationScoped
public class DashboardResource {

    private static final Logger logger = LoggerFactory.getLogger(DashboardResource.class);
    @Inject
    @Named("Formal")
    private String salutation;

    @GET
    @Path("/addNew.html")
    public ModelView sayTiles() {
        Map<String, Object> model = new LinkedHashMap<String, Object>();
        model.put("contentTypeValues", ContentType.values());
		ModelView modelView = new ModelView("content.addNew", model  );
        return modelView;
    }

    @GET
    @Path("/{defName: .*[.json]}")
    public String sayHello(@PathParam("defName") String defName) {
        return new StringBuilder(salutation).append(" Viddu, Devigere").toString();
    }

    @GET
    @Path("/{defName: .*[.html]}")
    public ModelView sayTiles(@PathParam("defName") String defName) {
        logger.debug("Definition Name={}", defName);
        ModelView modelView = new ModelView("content.addNew");
        return modelView;
    }
}
