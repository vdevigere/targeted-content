package com.viddu.content.elasticsearch;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Named;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;
import com.viddu.content.bo.ContentData;

@Named("elasticSearch")
public class ElasticSearchDb implements ContentDAO {

    private final Client client = ElasticSearch.INSTANCE.getClient();

    ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDb.class);

    public ElasticSearchDb() {
    }

    @Override
    public Content findContentById(String contentId) {
        Date sDate, eDate;
        sDate = eDate = Calendar.getInstance().getTime();
        Content content = new Content("Mock", sDate, eDate);
        content.addContentData(new ContentData("Blah", 23));
        content.addContentData(new ContentData("Foo", 43));
        content.addTags(Arrays.asList("mock", "dummy", "foo", "blah"));
        return content;
    }

    @Override
    public String save(Content content) {
        try {
            String contentJSON = mapper.writeValueAsString(content);
            IndexResponse response = client.prepareIndex("content", "content").setSource(contentJSON).execute()
                    .actionGet();
            return response.getId();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
