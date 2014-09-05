package com.viddu.content;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.viddu.content.resource.CORSFilter;
import com.viddu.content.resource.ContentResource;
import com.viddu.content.resource.TagCloudResource;

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
