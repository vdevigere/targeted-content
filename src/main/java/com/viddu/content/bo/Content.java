package com.viddu.content.bo;

import java.util.Map;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CONTENT")
public class Content {

    private long _id;

    @FormParam("name")
    private String name;

    private Map<String, String> target;

    private ContentData[] contentData;

    public Content(String name) {
        this.name = name;
    }

    public Content() {

    }

    public String getName() {
        return name;
    }

    public Map<String, String> getTarget() {
        return target;
    }

    public void setTarget(Map<String, String> target) {
        this.target = target;
    }
}
