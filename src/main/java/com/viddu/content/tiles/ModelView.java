package com.viddu.content.tiles;

import java.util.Map;

public class ModelView {
    private final String view;
    private final Map<String, ?> model;

    public ModelView(String view, Map<String, ?> model) {
        super();
        this.view = view;
        this.model = model;
    }

    public String getView() {
        return view;
    }

    public Map<String, ?> getModel() {
        return model;
    }

}
