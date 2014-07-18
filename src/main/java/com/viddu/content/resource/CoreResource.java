package com.viddu.content.resource;

import java.util.LinkedHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.viddu.content.tiles.ModelView;

@Path("/")
@ApplicationScoped
public class CoreResource {

    @Inject
    @Named("Formal")
    private String salutation;

    @GET
    public String sayHello() {
        return new StringBuilder(salutation).append(" Viddu, Devigere").toString();
    }
    
    @GET
    @Path("/tiles")
    public ModelView sayTiles(){
        return new ModelView("myapp.homepage", new LinkedHashMap<String, String>());
    }
}
