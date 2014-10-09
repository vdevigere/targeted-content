package com.viddu.api.bo;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Content<T> {

    private final String name;

    private String id;

    private final Map<String, Collection<String>> target;

    private final Set<ContentData<T>> contentDataSet;

    private final Date startDate, endDate;

    public Content(String name, Date sDate, Date eDate) {
        this.name = name;
        contentDataSet = new LinkedHashSet<>();
        target = new HashMap<String, Collection<String>>();
        this.startDate = sDate;
        this.endDate = eDate;
    }

    public Content() {
        this.contentDataSet = new LinkedHashSet<>();
        this.target = new HashMap<String, Collection<String>>();
        startDate = endDate = Calendar.getInstance().getTime();
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public Map<String, Collection<String>> getTarget() {
        return target;
    }

    public void addContentData(ContentData<T> contentData) {
        contentDataSet.add(contentData);
    }

    public void addTags(Collection<String> tags) {
        target.put("tags", tags);
    }

    @JsonIgnore
    public Collection<String> getTags() {
        return target.get("tags");
    }

    public Set<ContentData<T>> getContentDataSet() {
        return contentDataSet;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
