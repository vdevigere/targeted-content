package com.viddu.content;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.RoleBasedSecurityFeature;

import com.viddu.content.resource.DashboardResource;
import com.viddu.content.tiles.TilesMessageBodyWriter;

@ApplicationPath("/page")
public class PageApplication extends Application {

    private Set<Class<?>> resources = new LinkedHashSet<>();

    @Override
    public Set<Class<?>> getClasses() {
        resources.add(DashboardResource.class);
        resources.add(TilesMessageBodyWriter.class);
        resources.add(RoleBasedSecurityFeature.class);
        return resources;
    }
}
