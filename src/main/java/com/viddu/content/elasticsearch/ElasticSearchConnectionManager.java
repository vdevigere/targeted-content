package com.viddu.content.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@WebListener
public class ElasticSearchConnectionManager implements ServletContextListener {
    private Config config = ConfigFactory.load();

    private Node node;

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConnectionManager.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Starting ElasticSearch node..");
        node = nodeBuilder().node();
        Client client = node.client();
        ServletContext context = sce.getServletContext();
        context.setAttribute("esClient", client);
        try {
            createIndexIfNoneExists(client);
        } catch (ElasticsearchException | InterruptedException | ExecutionException e) {
            logger.error("Exception: ", e);
        }
    }

    private void createIndexIfNoneExists(Client client) throws ElasticsearchException, InterruptedException,
            ExecutionException {
        String indexName = config.getString("INDEX_NAME");
        String contentType = config.getString("TYPE_NAME");
        FutureTask<IndicesExistsResponse> future = new FutureTask<IndicesExistsResponse>(() -> {
            final IndicesExistsResponse res = client.admin().indices().prepareExists(indexName).execute().actionGet();
            return res;
        });
        Executors.newSingleThreadExecutor().execute(future);

        if (!future.get().isExists()) {
            final CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices()
                    .prepareCreate(indexName);
            String mapping = "{\"content\":{\"properties\":{\"contentDataSet\":{\"properties\":{\"data\":{\"type\":\"string\"},\"weight\":{\"type\":\"long\"}}},\"endDate\":{\"type\":\"date\",\"format\":\"dateOptionalTime\"},\"id\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"startDate\":{\"type\":\"date\",\"format\":\"dateOptionalTime\"},\"target\":{\"properties\":{\"tags\":{\"type\":\"string\",\"index\":\"not_analyzed\"}}}}}}";
            createIndexRequestBuilder.addMapping(contentType, mapping);
            createIndexRequestBuilder.execute().actionGet();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("Stopping ElasticSearch node..");
        node.close();
    }

}
