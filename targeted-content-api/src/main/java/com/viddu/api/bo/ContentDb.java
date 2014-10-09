package com.viddu.api.bo;

import java.util.Collection;
import java.util.List;

public interface ContentDb<T> {

    public Content<T> findContentById(String contentId);

    public String saveUpdate(Content<T> content, String id);

    public boolean deleteContentById(String id);

    public abstract Collection<Content<T>> search(Collection<String> tags, Boolean activeOnly);

    public abstract Collection<Content<T>> search(Collection<String> tags, Boolean activeOnly, Integer size, Integer from);

    public Collection<TagCloudItem> tagCloud(List<String> tags, boolean activeOnly);
}
