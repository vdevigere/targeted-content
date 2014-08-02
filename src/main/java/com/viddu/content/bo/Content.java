package com.viddu.content.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {

    private long _id;

    private String name;

    private Map<String, Object> target;

    private Set<ContentData> contentDataSet;

    public Content(String name, Date sDate, Date eDate) {
        this.name = name;
        contentDataSet = new LinkedHashSet<>();
        target = new HashMap<String, Object>();
        target.put("startDate", sDate);
        target.put("endDate", eDate);
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

    public void setName(String name) {
        this.name = name;
    }

    public void addContentData(ContentData contentData) {
        contentDataSet.add(contentData);
    }
}
