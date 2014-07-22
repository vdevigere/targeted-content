package com.viddu.content.tiles;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelView {
    private final String view;
    private final Map<String, ?> model;

    private static final Logger logger = LoggerFactory.getLogger(ModelView.class);

    public ModelView(String view, Map<String, ?> model) {
        super();
        this.view = view;
        this.model = model;
    }

    public ModelView(String view, Collection<?> model) {
        super();
        this.view = view;
        Map<String, Object> modelMap = new LinkedHashMap<String, Object>();
        // TODO: Initialize model here
        for (Object modelObj : model) {
            String elName = modelObj.getClass().getSimpleName();
            String camelCase = elName.substring(0, 1).toLowerCase().concat(elName.substring(1));
            logger.debug("el-name:{}", camelCase);
            modelMap.put(camelCase, modelObj);
        }
        this.model = modelMap;

    }

    public String getView() {
        return view;
    }

    public Map<String, ?> getModel() {
        return model;
    }

}
