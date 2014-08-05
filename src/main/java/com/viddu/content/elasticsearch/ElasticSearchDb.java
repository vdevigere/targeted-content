package com.viddu.content.elasticsearch;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHits;
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
        GetResponse response = client
                .prepareGet(ElasticSearchConstants.INDEX_NAME, ElasticSearchConstants.TYPE_NAME, contentId).execute()
                .actionGet();
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
    public Collection<Content> findContentActiveNow() {
        Date now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
        Set<Content> validContent = new LinkedHashSet<>();
        FilterBuilder dateFilter = FilterBuilders.boolFilter().must(FilterBuilders.rangeFilter("startDate").lte(now),
                FilterBuilders.rangeFilter("endDate").gte(now));
        SearchResponse response = client
                .prepareSearch(ElasticSearchConstants.INDEX_NAME, ElasticSearchConstants.TYPE_NAME)
                .setPostFilter(dateFilter).execute().actionGet();
        SearchHits hits = response.getHits();
        hits.forEach(hit -> {
            String contentJson = hit.getSourceAsString();
            try {
                Content content = mapper.readValue(contentJson, Content.class);
                validContent.add(content);
            } catch (Exception e) {
                logger.error("JsonParse Exception, {}", e);
            }
            logger.debug("Hit:{}", contentJson);
        });
        return validContent;
    }

    @Override
    public String saveUpdate(Content content, String id) {
        try {
            String contentJson = mapper.writeValueAsString(content);
            if (id != null && !id.isEmpty()) {
                IndexResponse response = client
                        .prepareIndex(ElasticSearchConstants.INDEX_NAME, ElasticSearchConstants.TYPE_NAME, id)
                        .setSource(contentJson).execute().actionGet();
                return response.getId();
            } else {
                IndexResponse response = client
                        .prepareIndex(ElasticSearchConstants.INDEX_NAME, ElasticSearchConstants.TYPE_NAME)
                        .setSource(contentJson).execute().actionGet();
                return response.getId();
            }
        } catch (JsonProcessingException e) {
            logger.error("Json Processing Exception", e);
        }
        return null;
    }

}
