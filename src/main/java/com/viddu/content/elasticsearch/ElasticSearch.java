package com.viddu.content.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

public enum ElasticSearch {
    INSTANCE;
    private final Client client;

    private ElasticSearch() {
        Node node = nodeBuilder().node();
        this.client = node.client();
    }

    public Client getClient() {
        return client;
    }
}
