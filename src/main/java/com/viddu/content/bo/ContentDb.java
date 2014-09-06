package com.viddu.content.bo;

import java.util.Collection;
import java.util.List;

public interface ContentDb<T> {

    public Content<T> findContentById(String contentId);

    public String saveUpdate(Content<T> content, String id);

    public boolean deleteContentById(String id);

    public abstract Collection<Content<T>> search(Collection<String> tags, Boolean activeOnly);

    public Collection<Content<TagCloudItem>> tagCloud(List<String> tags, boolean activeOnly);
}
