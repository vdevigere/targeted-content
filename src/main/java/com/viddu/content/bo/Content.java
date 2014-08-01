package com.viddu.content.bo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {

    private long _id;

    private String name;

    private Map<String, Object> target;

    private ContentData[] contentData;

    public Content(String name) {
        this.name = name;
    }

    public Content() {

    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getTarget() {
        return target;
    }

    public void setTarget(Map<String, Object> target) {
        this.target = target;
    }

    @JsonIgnore
    public long get_id() {
        return _id;
    }

    @JsonProperty
    public void set_id(long _id) {
        this._id = _id;
    }

    public ContentData[] getContentData() {
        return contentData;
    }

    public void setContentData(ContentData[] contentData) {
        this.contentData = contentData;
    }

    public void setName(String name) {
        this.name = name;
    }
}
