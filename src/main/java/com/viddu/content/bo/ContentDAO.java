package com.viddu.content.bo;


public interface ContentDAO {

    public Content findContentById(String contentId);

    public String save(Content content);

}
