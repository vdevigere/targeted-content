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
import javax.servlet.ServletContext;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BaseFilterBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDb;
import com.viddu.content.bo.TagCloudItem;

public class ElasticSearchDb<T> implements ContentDb<T> {

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
    public ElasticSearchDb(ServletContext context) {
        this.client = (Client) context.getAttribute("esClient");
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

    private Collection<Content<T>> buildSearchResponse(SearchHits hits) {
        final Collection<Content<T>> validContent = new LinkedHashSet<>();
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
    public Collection<Content<T>> search(Collection<String> tags, Boolean activeOnly, Integer size, Integer from) {
        BaseFilterBuilder filter = buildFilter(tags, activeOnly);
        SearchRequestBuilder searchRequest = getSearchRequestBuilder();
        searchRequest.setPostFilter(filter).setFrom(from).setSize(size);
        logger.debug("Search Request={}", searchRequest);

        // Execute Search
        SearchResponse response = searchRequest.execute().actionGet();
        SearchHits hits = response.getHits();
        return buildSearchResponse(hits);
    }

    @Override
    public Collection<Content<T>> search(Collection<String> tags, Boolean activeOnly) {
        BaseFilterBuilder filter = buildFilter(tags, activeOnly);
        SearchRequestBuilder searchRequest = getSearchRequestBuilder();
        searchRequest.setPostFilter(filter).setScroll(TimeValue.timeValueMinutes(1L)).setSearchType(SearchType.SCAN);
        logger.debug("Search Request={}", searchRequest);
        // Execute Search
        Collection<Content<T>> searchResults = new LinkedList<>();
        SearchResponse response = searchRequest.execute().actionGet();
        while (true) {
            response = client.prepareSearchScroll(response.getScrollId()).setScroll(TimeValue.timeValueMinutes(1L))
                    .execute().actionGet();
            searchResults.addAll(buildSearchResponse(response.getHits()));
            // Break condition: No hits are returned
            if (response.getHits().getHits().length == 0) {
                break;
            }
        }

        return searchResults;
    }

    @Override
    public Collection<TagCloudItem> tagCloud(List<String> tags, boolean activeOnly) {
        // Build Filter
        BaseFilterBuilder filter = buildFilter(tags, activeOnly);

        // Build Aggregation
        FilterAggregationBuilder filterAggregation = new FilterAggregationBuilder("FILTERED_AGG").filter(filter)
                .subAggregation(new TermsBuilder("TAG_COUNT").field("target.tags").size(0));

        List<TagCloudItem> tagCloud = new LinkedList<TagCloudItem>();

        SearchRequestBuilder searchRequest = getSearchRequestBuilder();

        searchRequest.addAggregation(filterAggregation).setSearchType(SearchType.COUNT);
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

    private SearchRequestBuilder getSearchRequestBuilder() {
        SearchRequestBuilder searchRequest = client.prepareSearch(config.getString(INDEX_NAME)).setTypes(
                config.getString(TYPE_NAME));
        return searchRequest;
    }

    private BaseFilterBuilder buildFilter(Collection<String> tags, boolean activeOnly) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (activeOnly) {
            Date now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
            boolFilter = boolFilter.must(FilterBuilders.rangeFilter("startDate").lte(now),
                    FilterBuilders.rangeFilter("endDate").gte(now));
        }

        if (tags != null && !tags.isEmpty()) {
            boolFilter = boolFilter.should(FilterBuilders.termsFilter("target.tags", tags).execution("and"));
        }

        if (!boolFilter.hasClauses()) {
            return FilterBuilders.matchAllFilter();
        } else {
            return boolFilter;
        }
    }
}
