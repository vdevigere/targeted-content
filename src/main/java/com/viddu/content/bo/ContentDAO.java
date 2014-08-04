package com.viddu.content.bo;

public interface ContentDAO {

    public Content findContentById(String contentId);

    public String saveUpdate(Content content, String id);

}
