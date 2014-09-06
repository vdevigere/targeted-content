package com.viddu.content.elasticsearch;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDb;
import com.viddu.content.bo.TagCloudItem;

public class ElasticSearchDb<T> implements ContentDb<T> {

    private static final String PAGE_SIZE = "PAGE_SIZE";

    private static final String TYPE_NAME = "TYPE_NAME";

    private static final String INDEX_NAME = "INDEX_NAME";

    @Inject
    private ObjectMapper mapper;

    @Inject
    private Config config;

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDb.class);

    private final Client client;

    private TypeReference<Content<T>> typeRef = new TypeReference<Content<T>>() {
    };

    @Inject
    public ElasticSearchDb(Client client) {
        this.client = client;
    }

    @Override
    public Content<T> findContentById(String contentId) {
        GetResponse response = client.prepareGet(config.getString(INDEX_NAME), config.getString(TYPE_NAME), contentId)
                .execute().actionGet();
        try {
            String contentJson = response.getSourceAsString();
            Content<T> content = mapper.readValue(contentJson, typeRef);
            content.setId(response.getId());
            return content;
        } catch (IOException e) {
            logger.debug("JSON or IOException", e);
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
    public String saveUpdate(Content<T> content, String id) {
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

    protected Collection<Content<T>> doSearch(BoolFilterBuilder filter, int size, int from) {
        logger.debug("Filter={}", filter);
        logger.debug("Page Size={}, From={}", size, from);
        SearchRequestBuilder searchRequest = client.prepareSearch(config.getString(INDEX_NAME))
                .setTypes(config.getString(TYPE_NAME)).setFrom(from).setSize(size);
        if (filter.hasClauses()) {
            searchRequest.setPostFilter(filter);
        }

        // Execute Search
        SearchResponse response = searchRequest.execute().actionGet();

        // If there is more data, recursively fetch it.
        SearchHits hits = response.getHits();
        long totalHits = hits.getTotalHits();
        logger.debug("TotalHits={}, Size={}, From={}", totalHits, size, from);
        final Collection<Content<T>> validContent = new LinkedHashSet<>();

        if (totalHits > from + size) {
            validContent.addAll(doSearch(filter, size, from + size));
        }

        hits.forEach(hit -> {
            String contentJson = hit.getSourceAsString();
            logger.debug("Hit:{}", contentJson);
            try {
                Content<T> content = mapper.readValue(contentJson, typeRef);
                content.setId(hit.getId());
                validContent.add(content);
            } catch (Exception e) {
                logger.error("JsonParse Exception, {}", contentJson, e);
            }
        });
        return validContent;
    }

    @Override
    public Collection<Content<T>> search(Collection<String> tags, Boolean activeOnly) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (activeOnly) {
            Date now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
            boolFilter = boolFilter.must(FilterBuilders.rangeFilter("startDate").lte(now),
                    FilterBuilders.rangeFilter("endDate").gte(now));
        }

        if (tags != null && !tags.isEmpty()) {
            boolFilter = boolFilter.should(FilterBuilders.termsFilter("target.tags", tags).execution("and"));
        }
        return doSearch(boolFilter, config.getInt(PAGE_SIZE), 0);
    }

    @Override
    public Collection<TagCloudItem> tagCloud(List<String> tags, boolean activeOnly) {
        List<TagCloudItem> tagCloud = new LinkedList<TagCloudItem>();
        SearchRequestBuilder searchRequest = client.prepareSearch(config.getString(INDEX_NAME)).setTypes(
                config.getString(TYPE_NAME));

        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (activeOnly) {
            Date now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
            boolFilter = boolFilter.must(FilterBuilders.rangeFilter("startDate").lte(now),
                    FilterBuilders.rangeFilter("endDate").gte(now));
        }

        if (tags != null && !tags.isEmpty()) {
            boolFilter = boolFilter.should(FilterBuilders.termsFilter("target.tags", tags).execution("and"));
        }

        FilterAggregationBuilder filterAggregation = new FilterAggregationBuilder("FILTERED_AGG");
        if (boolFilter.hasClauses()) {
            filterAggregation.filter(boolFilter);
        } else {
            filterAggregation.filter(FilterBuilders.matchAllFilter());
        }
        filterAggregation.subAggregation(new TermsBuilder("TAG_COUNT").field("target.tags"));
        searchRequest.addAggregation(filterAggregation);

        logger.debug("Search Request={}", searchRequest);
        // Execute Search
        SearchResponse response = searchRequest.execute().actionGet();

        Filter filteredAgg = response.getAggregations().get("FILTERED_AGG");
        Terms termsAgg = filteredAgg.getAggregations().get("TAG_COUNT");

        termsAgg.getBuckets().forEach(bucket -> {
            tagCloud.add(new TagCloudItem(bucket.getKey(), bucket.getDocCount()));
        });
        return tagCloud;
    }
}
