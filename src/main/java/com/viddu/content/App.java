package com.viddu.content;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.content.resource.ContentResource;
import com.viddu.content.resource.DashboardResource;
import com.viddu.content.resource.TagResource;
import com.viddu.content.tiles.TilesMessageBodyWriter;

@ApplicationPath("/target")
public class App extends Application {

    private Set<Class<?>> resources = new LinkedHashSet<>();

    @Override
    public Set<Class<?>> getClasses() {
        resources.add(DashboardResource.class);
        resources.add(TagResource.class);
        resources.add(ContentResource.class);
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

    @Produces
    @Singleton
    public JedisPool getConnection() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
        return pool;
    }

    @Produces
    @Singleton
    public ObjectMapper getMapper() {
        return new ObjectMapper();
    }
}
