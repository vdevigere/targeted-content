package com.viddu.content.bo;

import java.util.Set;

public class ContentTag {
    private final String name;
    private Set<Content> content;

    public ContentTag(String name) {
        this.name = name;
    }

    public Set<Content> getContent() {
        return content;
    }

    public void setContent(Set<Content> content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }
}
