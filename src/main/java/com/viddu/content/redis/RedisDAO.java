package com.viddu.content.redis;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDAO {

    private static final String TAGS = "TAGS";
    private static final String DELIMITER = "::";

    @Inject
    private JedisPool pool;

    private static final Logger logger = LoggerFactory.getLogger(RedisDAO.class);

    public Collection<Map<String, ?>> findByTagName(String resource, String tagName, Integer depth) {
        Collection<Map<String, ?>> result = new LinkedList<Map<String, ?>>();

        Collection<String> contentIds = findIdsByTagName(resource, tagName);
        for (String contentId : contentIds) {
            result.add(findContentById(resource, contentId, depth));
        }
        return result;
    }

    public Map<String, ?> findContentById(String resource, String contentId, Integer depth) {
        Jedis jedis = pool.getResource();
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        try {
            String key = resource + DELIMITER + contentId;
            logger.debug("HGETALL {}", key);
            Map<String, String> fields = jedis.hgetAll(key);
            fields.put("id", contentId);
            result.put("fields", fields);
            Collection<String> tags = findTagsById(resource, contentId);
            if (depth > 0) {
                depth--;
                for (String tagName : tags) {
                    result.put(tagName, findByTagName(resource, tagName, depth));
                }
            }
            return result;
        } finally {
            jedis.close();
        }
    }

    private Collection<String> findIdsByTagName(String resource, String tagName) {
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
}
