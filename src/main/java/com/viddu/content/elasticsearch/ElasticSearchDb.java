package com.viddu.content.elasticsearch;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.TimeZone;

import javax.inject.Inject;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BaseFilterBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDb;

public class ElasticSearchDb implements ContentDb {

    private static final String TYPE_NAME = "TYPE_NAME";

    private static final String INDEX_NAME = "INDEX_NAME";

    @Inject
    private ObjectMapper mapper;

    @Inject
    private Config config;

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDb.class);

    private final Client client;

    @Inject
    public ElasticSearchDb(Client client) {
        this.client = client;
    }

    @Override
    public Content findContentById(String contentId) {
        GetResponse response = client.prepareGet(config.getString(INDEX_NAME), config.getString(TYPE_NAME), contentId)
                .execute().actionGet();
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
            dateFilter = dateFilter.should(FilterBuilders.termsFilter("target.tags", tags).execution("and"));
        }
        return doSearch(dateFilter, 10, 0);
    }

    @Override
    public String saveUpdate(Content content, String id) {
        try {
            String contentJson = mapper.writeValueAsString(content);
            if (id != null && !id.isEmpty()) {
                IndexResponse response = client
                        .prepareIndex(config.getString(INDEX_NAME), config.getString(TYPE_NAME), id)
                        .setSource(contentJson).execute().actionGet();
                return response.getId();
            } else {
                IndexResponse response = client.prepareIndex(config.getString(INDEX_NAME), config.getString(TYPE_NAME))
                        .setSource(contentJson).execute().actionGet();
                return response.getId();
            }
        } catch (JsonProcessingException e) {
            logger.error("Json Processing Exception", e);
        }
        return null;
    }

    @Override
    public boolean deleteContentById(String id) {
        DeleteResponse response = client.prepareDelete(config.getString(INDEX_NAME), config.getString(TYPE_NAME), id)
                .execute().actionGet();
        return response.isFound();
    }

    @Override
    public Collection<Content> findAllContent(Collection<String> tags) {
        if (tags != null && !tags.isEmpty()) {
            return doSearch(FilterBuilders.termsFilter("target.tags", tags).execution("and"), 10, 0);
        }
        return doSearch();
    }

    protected Collection<Content> doSearch() {
        return doSearch(null, 10, 0);
    }

    protected Collection<Content> doSearch(BaseFilterBuilder filter, int size, int from) {
        logger.debug("Filter={}", filter);
        SearchRequestBuilder searchRequest = client.prepareSearch(config.getString(INDEX_NAME))
                .setTypes(config.getString(TYPE_NAME)).setFrom(from).setSize(size);
        if (filter != null) {
            searchRequest.setPostFilter(filter);
        }
        SearchResponse response = searchRequest.execute().actionGet();
        SearchHits hits = response.getHits();
        long totalHits = hits.getTotalHits();
        logger.debug("TotalHits={}, Size={}, From={}", totalHits, size, from);
        final Collection<Content> validContent = new LinkedHashSet<>();
        if (totalHits > from + size) {
            validContent.addAll(doSearch(filter, size, from + size));
        }
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
