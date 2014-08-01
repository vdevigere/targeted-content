package com.viddu.content;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.viddu.content.bo.ContentDAO;
import com.viddu.content.elasticsearch.ElasticSearchDAO;
import com.viddu.content.resource.ContentResource;

@ApplicationPath("/api")
public class ApiApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new LinkedHashSet<Class<?>>();
        resources.add(ContentResource.class);
        return resources;
    }

    @Produces
    @Singleton
    public ContentDAO getElasticSearchDAO() {
        return new ElasticSearchDAO();
    }
}
