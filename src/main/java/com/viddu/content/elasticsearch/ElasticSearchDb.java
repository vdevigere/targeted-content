package com.viddu.content.elasticsearch;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;

public class ElasticSearchDb implements ContentDAO {

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
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
            String contentJson = response.getSourceAsString();
            Content content = mapper.readValue(contentJson, Content.class);
            content.setId(response.getId());
            return content;
        } catch (IOException e) {
            logger.debug("JSON or IOException", e);
        }
        return null;
    }

    @Override
    public Collection<Content> filterActiveContent(Collection<String> tags) {
        Date now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
        BoolFilterBuilder dateFilter = FilterBuilders.boolFilter().must(
                FilterBuilders.rangeFilter("startDate").lte(now), FilterBuilders.rangeFilter("endDate").gte(now));
        if (tags != null && !tags.isEmpty()) {
            dateFilter = dateFilter.should(FilterBuilders.termsFilter("target.tags", tags));
        }
        logger.debug("Filters={}", dateFilter);
        SearchResponse response = client.prepareSearch(ElasticSearchConstants.INDEX_NAME)
                .setTypes(ElasticSearchConstants.TYPE_NAME).setPostFilter(dateFilter).addSort("startDate", SortOrder.DESC).execute().actionGet();
        SearchHits hits = response.getHits();

        Set<Content> validContent = new LinkedHashSet<>();
        hits.forEach(hit -> {
            String contentJson = hit.getSourceAsString();
            try {
                Content content = mapper.readValue(contentJson, Content.class);
                content.setId(hit.getId());
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

    @Override
    public String deleteContentById(String id) {
        DeleteResponse response = client
                .prepareDelete(ElasticSearchConstants.INDEX_NAME, ElasticSearchConstants.TYPE_NAME, id).execute()
                .actionGet();
        return (response.isFound()) ? "Success" : "Failed to Find Record";
    }

    @Override
    public Collection<Content> findAllContent() {
        SearchResponse response = client.prepareSearch(ElasticSearchConstants.INDEX_NAME)
                .setTypes(ElasticSearchConstants.TYPE_NAME).addSort("startDate", SortOrder.DESC).execute().actionGet();
        SearchHits hits = response.getHits();
        Set<Content> validContent = new LinkedHashSet<>();
        hits.forEach(hit -> {
            String contentJson = hit.getSourceAsString();
            try {
                Content content = mapper.readValue(contentJson, Content.class);
                content.setId(hit.getId());
                validContent.add(content);
            } catch (Exception e) {
                logger.error("JsonParse Exception, {}", contentJson, e);
            }
            logger.debug("Hit:{}", contentJson);
        });
        return validContent;

    }

}
