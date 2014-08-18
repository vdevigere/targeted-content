package com.viddu.content.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;

@WebListener
public class ElasticSearchConnectionManager implements ServletContextListener {
    private static final String CLIENT_NAME = "CLIENT_NAME";
    private Node node;
    private Client client;

    @Inject
    private Config config;

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConnectionManager.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Starting ElasticSearch node..");
        node = nodeBuilder().node();
        client = node.client();
        ServletContext context = sce.getServletContext();
        context.setAttribute(config.getString(CLIENT_NAME), client);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("Stopping ElasticSearch node..");
        node.close();
    }

}
