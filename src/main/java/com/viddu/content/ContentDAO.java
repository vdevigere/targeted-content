package com.viddu.content;

import com.viddu.content.bo.Content;

public interface ContentDAO {

    public Content findContentById(String contentId);

    public Long save(Content content);

}
