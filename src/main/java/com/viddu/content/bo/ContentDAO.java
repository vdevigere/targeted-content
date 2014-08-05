package com.viddu.content.bo;

import java.util.Collection;

public interface ContentDAO {

    public Content findContentById(String contentId);

    public String saveUpdate(Content content, String id);

    public abstract Collection<Content> findContentActiveNow(Collection<String> tags);

}
