package com.viddu.content.elasticsearch;

import java.io.IOException;

import javax.inject.Named;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;

@Named("elasticSearch")
public class ElasticSearchDb implements ContentDAO {

    private static final String INDEX_NAME = "local-index";
    private static final String TYPE_NAME = "content";

    private final Client client = ElasticSearch.INSTANCE.getClient();

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDb.class);

    public ElasticSearchDb() {
    }

    @Override
    public Content findContentById(String contentId) {
        GetResponse response = client.prepareGet(INDEX_NAME, TYPE_NAME, contentId).execute().actionGet();
        try {
            String contentJson = mapper.writeValueAsString(response.getSource());
            Content content = mapper.readValue(contentJson, Content.class);
            return content;
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String save(Content content) {
        try {
            String contentJSON = mapper.writeValueAsString(content);
            IndexResponse response = client.prepareIndex(INDEX_NAME, TYPE_NAME).setSource(contentJSON).execute()
                    .actionGet();
            return response.getId();
        } catch (JsonProcessingException e) {
            logger.error("Json Processing Exception", e);
        }
        return null;
    }

    @Override
    public String update(Content content, String id) {
        try {
            String contentJson = mapper.writeValueAsString(content);
            IndexResponse response = client.prepareIndex(INDEX_NAME, TYPE_NAME, id).setSource(contentJson).execute()
                    .actionGet();
            return new Long(response.getVersion()).toString();
        } catch (JsonProcessingException e) {
            logger.error("Json Processing Exception", e);
        }
        return null;
    }

}
