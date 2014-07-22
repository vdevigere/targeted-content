package com.viddu.content;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.viddu.content.resource.DashboardResource;
import com.viddu.content.tiles.TilesMessageBodyWriter;

@ApplicationPath("/content")
public class App extends Application {

    private Set<Class<?>> resources = new LinkedHashSet<>();

    @Override
    public Set<Class<?>> getClasses() {
        resources.add(DashboardResource.class);
        resources.add(TilesMessageBodyWriter.class);
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        // TODO Auto-generated method stub
        return super.getSingletons();
    }

    @Produces
    @Named("Informal")
    public String sayInformal() {
        return "Hiya";
    }

    @Produces
    @Named("Formal")
    public String sayFormal() {
        return "Hello";
    }
}
