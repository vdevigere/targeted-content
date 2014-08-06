package com.viddu.content.bo;

import java.util.Collection;

public interface ContentDAO {

    public Content findContentById(String contentId);

    public String saveUpdate(Content content, String id);

    public abstract Collection<Content> filterActiveContent(Collection<String> tags);

    public abstract Collection<Content> findAllContent();

    public Status deleteContentById(String id);

}
