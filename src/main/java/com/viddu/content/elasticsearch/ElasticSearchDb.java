package com.viddu.content.elasticsearch;

import java.io.IOException;

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

public class ElasticSearchDb implements ContentDAO {

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDb.class);


    private final Client client;

    public ElasticSearchDb(Client client) {
        this.client = client;
    }

    @Override
    public Content findContentById(String contentId) {
        GetResponse response = client.prepareGet(ElasticSearchConstants.INDEX_NAME, ElasticSearchConstants.TYPE_NAME, contentId).execute().actionGet();
        try {
            String contentJson = mapper.writeValueAsString(response.getSource());
            Content content = mapper.readValue(contentJson, Content.class);
            return content;
        } catch (IOException e) {
            logger.debug("JSON or IOException", e);
        }
        return null;
    }

    @Override
    public String saveUpdate(Content content, String id) {
        try {
            String contentJson = mapper.writeValueAsString(content);
            if (id != null && !id.isEmpty()) {
                IndexResponse response = client.prepareIndex(ElasticSearchConstants.INDEX_NAME, ElasticSearchConstants.TYPE_NAME, id).setSource(contentJson)
                        .execute().actionGet();
                return response.getId();
            } else {
                IndexResponse response = client.prepareIndex(ElasticSearchConstants.INDEX_NAME, ElasticSearchConstants.TYPE_NAME).setSource(contentJson).execute()
                        .actionGet();
                return response.getId();
            }
        } catch (JsonProcessingException e) {
            logger.error("Json Processing Exception", e);
        }
        return null;
    }

}
