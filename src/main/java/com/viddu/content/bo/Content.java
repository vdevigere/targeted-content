package com.viddu.content.bo;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {

    private final String name;

    private final Map<String, Object> target;

    private final Set<ContentData> contentDataSet;

    private final Date startDate, endDate;

    public Content(String name, Date sDate, Date eDate) {
        this.name = name;
        contentDataSet = new LinkedHashSet<>();
        target = new HashMap<String, Object>();
        this.startDate = sDate;
        this.endDate = eDate;
    }

    public Content() {
        this.contentDataSet = new LinkedHashSet<>();
        this.target = new HashMap<String, Object>();
        startDate = endDate = Calendar.getInstance().getTime();
        this.name = "";
    }
    public String getName() {
        return name;
    }

    public Map<String, Object> getTarget() {
        return target;
    }

    public void addContentData(ContentData contentData) {
        contentDataSet.add(contentData);
    }

    public void addTags(Collection<String> tags) {
        target.put("tags", tags);
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public Collection<String> getTags() {
        return (Collection<String>) target.get("tags");
    }

    public Set<ContentData> getContentDataSet() {
        return contentDataSet;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
