package com.viddu.content.redis;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.content.bo.Taggable;

public class RedisDAO {

    private static final String NEXT_OBJ_ID = "NEXT_OBJ_ID";
    private static final String TAGS = "TAGS";
    private static final String DELIMITER = "::";

    private JedisPool pool;

    private ObjectMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(RedisDAO.class);

    @Inject
    public RedisDAO(JedisPool pool, ObjectMapper mapper) {
        this.pool = pool;
        this.mapper = mapper;
    }

    public <T extends Taggable> Collection<T> findContentByTagName(String resource, String tagName, Class<T> clazz) {
        Collection<T> result = new LinkedList<T>();

        Collection<String> contentIds = findContentIdsByTagName(resource, tagName);
        for (String contentId : contentIds) {
            result.add(findContentById(resource, contentId, clazz));
        }
        return result;
    }

    public <T extends Taggable> T findContentById(String resource, String contentId, Class<T> clazz) {
        Jedis jedis = pool.getResource();
        try {
            String key = resource + DELIMITER + contentId;
            logger.debug("GET {}", key);
            String jsonVal = jedis.get(key);
            T result = mapper.readValue(jsonVal.getBytes(), clazz);
            return result;
        } catch (IOException e) {
            logger.error("Error in parsing JSON", e);
            return null;
        } finally {
            jedis.close();
        }
    }

    private Collection<String> findContentIdsByTagName(String resource, String tagName) {
        Jedis jedis = pool.getResource();
        String key = TAGS + DELIMITER + tagName + DELIMITER + resource;
        try {
            logger.debug("SMEMBERS {}", key);
            return jedis.smembers(key);
        } finally {
            jedis.close();
        }
    }

    public Collection<String> findTagsById(String resource, String contentId) {
        Jedis jedis = pool.getResource();
        try {
            String key = resource + DELIMITER + contentId + DELIMITER + TAGS;
            return jedis.smembers(key);
        } finally {
            jedis.close();
        }
    }

    public Long save(String resource, Taggable obj) {
        Jedis jedis = pool.getResource();
        try {
            Long contentId = jedis.incr(NEXT_OBJ_ID);
            String key = resource + DELIMITER + contentId;
            String jsonVal = mapper.writeValueAsString(obj);
            logger.debug("SET {} {}", key, jsonVal);
            jedis.set(key, jsonVal);
            // Save tags
            for (String tagName : obj.getTags()) {
                String tagKey = TAGS + DELIMITER + tagName + DELIMITER + resource;
                logger.debug("SADD {} {}", tagKey, contentId);
                jedis.sadd(tagKey, contentId.toString());
            }
            return contentId;
        } catch (JsonProcessingException e) {
            logger.error("JSON Processing Exception", e);
            return null;
        } finally {
            jedis.close();
        }
    }
}
