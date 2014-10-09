package com.viddu.api;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.viddu.api.resource.CORSFilter;
import com.viddu.api.resource.ContentResource;
import com.viddu.api.resource.TagCloudResource;

@ApplicationPath("/api")
public class ApiApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new LinkedHashSet<Class<?>>();
        resources.add(ContentResource.class);
        resources.add(TagCloudResource.class);
        resources.add(CORSFilter.class);
        return resources;
    }
}
