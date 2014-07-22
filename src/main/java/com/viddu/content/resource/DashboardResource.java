package com.viddu.content.resource;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viddu.content.bo.ExerciseRoutine;
import com.viddu.content.tiles.ModelView;

@Path("/dashboard")
@ApplicationScoped
public class DashboardResource {

    private static final Logger logger = LoggerFactory.getLogger(DashboardResource.class);
    @Inject
    @Named("Formal")
    private String salutation;

    @GET
    @Path("/{defName: .*[.json]}")
    public String sayHello(@PathParam("defName") String defName) {
        return new StringBuilder(salutation).append(" Viddu, Devigere").toString();
    }

    @GET
    @Path("/{defName: .*[.html]}")
    public ModelView sayTiles(@PathParam("defName") String defName) {
        logger.debug("Definition Name={}", defName);
        Collection<ExerciseRoutine> model = new LinkedList<>();
        ExerciseRoutine exerciseRoutine1 = new ExerciseRoutine("Chest & Back", 1);
        ExerciseRoutine exerciseRoutine2 = new ExerciseRoutine("Plyometrics", 2);
        model.add(exerciseRoutine1);
        model.add(exerciseRoutine2);
        Map<String, Collection<ExerciseRoutine>> modelMap = new LinkedHashMap<String, Collection<ExerciseRoutine>>();
        modelMap.put("routines", model);
        ModelView modelView = new ModelView("myapp.homepage", modelMap);
        return modelView;
    }
}
