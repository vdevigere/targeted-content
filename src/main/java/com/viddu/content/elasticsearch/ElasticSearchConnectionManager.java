package com.viddu.content.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

@WebListener
public class ElasticSearchConnectionManager implements ServletContextListener {
    private Node node;
    private Client client;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        node = nodeBuilder().node();
        client = node.client();
        ServletContext context = sce.getServletContext();
        context.setAttribute(ElasticSearchConstants.ES_CLIENT, client);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        node.close();
    }

}
