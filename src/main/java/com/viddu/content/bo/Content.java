package com.viddu.content.bo;

import java.util.Set;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CONTENT")
public class Content implements Taggable {
    @FormParam("type")
    private ContentType type;

    @FormParam("url")
    private String url;

    @FormParam("name")
    private String name;

    @FormParam("tags")
    private Set<String> tags;

    public Content(String name, ContentType type, String url) {
        this.type = type;
        this.url = url;
        this.name = name;
    }

    public Content() {

    }

    public ContentType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    @Override
    public Set<String> getTags() {
        return tags;
    }

    @Override
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
