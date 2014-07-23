package com.viddu.content.bo;

import java.net.URL;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CONTENT")
public class Content {
    private final Long id;
    private final ContentType type;
    private final URL url;
    private final String name;

    private Set<ContentTag> tags;
    
    public Content(Long id, String name, ContentType type, URL url) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.name = name;
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

    public Set<ContentTag> getTags() {
        return tags;
    }

    public void setTags(Set<ContentTag> tags) {
        this.tags = tags;
    }
}
