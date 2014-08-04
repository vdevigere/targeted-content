package com.viddu.content.bo;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {

    private String _id;

    private final String name;

    private final Map<String, Object> target;

    private final Set<ContentData> contentDataSet;

    public Content(String name, Date sDate, Date eDate) {
        this.name = name;
        contentDataSet = new LinkedHashSet<>();
        target = new HashMap<String, Object>();
        target.put("startDate", sDate);
        target.put("endDate", eDate);
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getTarget() {
        return target;
    }

    @JsonIgnore
    public String get_id() {
        return _id;
    }

    @JsonProperty
    public void set_id(String _id) {
        this._id = _id;
    }

    public void addContentData(ContentData contentData) {
        contentDataSet.add(contentData);
    }

    public void addTags(Collection<String> tags) {
        target.put("tags", tags);
    }

    public Collection<String> getTags(){
        return (Collection<String>) target.get("tags");
    }
    public Set<ContentData> getContentDataSet() {
        return contentDataSet;
    }
}
