package com.viddu.content.bo;

import java.net.URL;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CONTENT")
public class Content {
    private Long id;

    @FormParam("type")
    private ContentType type;

    @FormParam("url")
    private URL url;

    @FormParam("name")
    private String name;

    private Set<String> tags;

    public Content(String name, ContentType type, URL url) {
        this.type = type;
        this.url = url;
        this.name = name;
    }

    public Content() {

    }

    public Long getId() {
        return id;
    }

    public ContentType getType() {
        return type;
    }

    public URL getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
