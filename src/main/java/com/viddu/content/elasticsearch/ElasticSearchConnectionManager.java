package com.viddu.content.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;

@WebListener
public class ElasticSearchConnectionManager implements ServletContextListener {
    private static final String CLIENT_NAME = "CLIENT_NAME";
    private static final String TYPE_NAME = "TYPE_NAME";

    private static final String INDEX_NAME = "INDEX_NAME";
    private Node node;
    private Client client;

    @Inject
    private Config config;

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConnectionManager.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Starting ElasticSearch node..");
        String indexName = config.getString(INDEX_NAME);
        String typeName = config.getString(TYPE_NAME);
        node = nodeBuilder().node();
        client = node.client();
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        boolean indexExists = indicesAdminClient.prepareExists(indexName).execute().actionGet().isExists();
        logger.debug("Index Exists = {}", indexExists);
        if (!indexExists) {
            logger.debug("Creating index {}", indexName);
            XContentBuilder mapString;
            try {
                mapString = XContentFactory.jsonBuilder()
                        .startObject()
                            .startObject("properties")
                                .startObject("target")
                                    .startObject("properties")
                                            .startObject("tags")
                                                .field("type", "string")
                                                .field("index", "not_analyzed")
                                            .endObject()
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject();
                indicesAdminClient.prepareCreate(indexName).addMapping(typeName, mapString).execute().actionGet();
            } catch (IOException e) {
                logger.error("IOException", e);
            }
        }
        ServletContext context = sce.getServletContext();
        context.setAttribute(config.getString(CLIENT_NAME), client);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("Stopping ElasticSearch node..");
        node.close();
    }

}
