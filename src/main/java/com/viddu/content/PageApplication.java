package com.viddu.content;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import org.elasticsearch.client.Client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.viddu.content.bo.MenuItem;
import com.viddu.content.resource.DashboardResource;
import com.viddu.content.tiles.TilesMessageBodyWriter;

@ApplicationPath("/page")
public class PageApplication extends Application {

    private Set<Class<?>> resources = new LinkedHashSet<>();

    @Context
    private ServletContext context;

    @Override
    public Set<Class<?>> getClasses() {
        resources.add(DashboardResource.class);
        resources.add(TilesMessageBodyWriter.class);
        return resources;
    }

//====================================================
//                The CDI dependencies
// ===================================================

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
        topMenuItems.add(new MenuItem("Add New", "new.html"));
        topMenuItems.add(new MenuItem("Search", "search.html"));
        return topMenuItems;
    }

    private Map<String, String> buildProjectModel() {
        Map<String, String> projectModel = new HashMap<String, String>();
        projectModel.put("name", "Targeted Content");
        return projectModel;
    }

    @Produces
    @Singleton
    private Client getEsClient() {
        Client client = (Client) context.getAttribute("esClient");
        return client;
    }

    @Produces
    @Singleton
    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return mapper;
    }

    @Produces
    @Singleton
    private Config getConfig(){
        return ConfigFactory.load();
    }
}
