package com.viddu.content;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.viddu.content.bo.MenuItem;

// ====================================================
// The CDI dependencies
// ===================================================
public class CdiApplicationConfig {
    private static final String SERVICE_URL = "SERVICE_URL";
    private final Config config = ConfigFactory.load();;

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
        topMenuItems.add(new MenuItem("Contact", "contact.html"));
        return topMenuItems;
    }

    private Map<String, String> buildProjectModel() {
        Map<String, String> projectModel = new HashMap<String, String>();
        projectModel.put("name", "Targeted Content");
        return projectModel;
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
    private Config getConfig() {
        return config;
    }

    @Produces
    public WebTarget getContentClient() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(config.getString(SERVICE_URL));
        return webTarget.path("content");
    }
}
