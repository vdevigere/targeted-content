package com.viddu.content.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentDAO;
import javax.inject.Named;

@Named
public class ElasticSearchDAO implements ContentDAO {
    private final Client client;

    ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDAO.class);

    public ElasticSearchDAO() {
        Node node = nodeBuilder().node();
        client = node.client();
    }

    @Override
    public Content findContentById(String contentId) {
        return null;
    }

    @Override
    public String save(Content content) {
        try {
            String contentJSON = mapper.writeValueAsString(content);
            IndexResponse response = client.prepareIndex("content", "content").setSource(contentJSON).execute().actionGet();
            return response.getId();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
