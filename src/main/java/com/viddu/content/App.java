package com.viddu.content;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.content.bo.MenuItem;
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

    @Produces
    @PageModel
    public Map<String, Object> buildPageModel() {
        Map<String, Object> pageModel = new HashMap<String, Object>();
        pageModel.put("project", buildProjectModel());
        pageModel.put("topMenu", buildTopMenu());
        return pageModel;
    }

    private List<MenuItem> buildTopMenu() {
        List<MenuItem> topMenuItems = new LinkedList<MenuItem>();
        topMenuItems.add(new MenuItem("Home", "#home", true));
        topMenuItems.add(new MenuItem("About", "#about"));

        return topMenuItems;
    }

    private Map<String, String> buildProjectModel() {
        Map<String, String> projectModel = new HashMap<String, String>();
        projectModel.put("name", "Targeted Content");
        return projectModel;
    }

}
