package com.viddu.content.bo;

import java.util.Set;

public interface Taggable {

    public Set<String> getTags();

    public abstract void setTags(Set<String> tags);
}
