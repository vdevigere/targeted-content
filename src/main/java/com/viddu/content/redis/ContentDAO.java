package com.viddu.content.redis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentType;

public class ContentDAO {
    private final JedisPool pool;

    private static final Logger logger = LoggerFactory.getLogger(ContentDAO.class);

    @Inject
    public ContentDAO(JedisPool pool) {
        this.pool = pool;
    }

    public Content getContentById(Long id) {
        String key = "CONTENT::" + id;
        Jedis jedis = pool.getResource();
        try {
            Map<String, String> contentMap = jedis.hgetAll(key);
            ContentType type = ContentType.valueOf(contentMap.get("TYPE"));
            String name = contentMap.get("NAME");
            URL url = null;
            try {
                url = new URL(contentMap.get("URL"));
            } catch (MalformedURLException e) {
                logger.error("URL is malformed", e);
            }
            return new Content(id, name, type, url);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<String> getTagsById(Long id) {
        String key = "CONTENT::" + id + "::TAGS";
        Jedis jedis = pool.getResource();
        try {
            Set<String> tags = jedis.smembers(key);
            return tags;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }
}
