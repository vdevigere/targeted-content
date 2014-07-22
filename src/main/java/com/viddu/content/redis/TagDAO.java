package com.viddu.content.redis;

import java.util.Set;

import javax.inject.Inject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TagDAO {

    private final JedisPool pool;

    @Inject
    public TagDAO(JedisPool pool) {
        this.pool = pool;
    }

    public Set<String> getTaggedContent(String tagName) {
        String key = "TAGS::" + tagName + "::CONTENT";
        Jedis jedis = pool.getResource();
        try {
            return jedis.smembers(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<String> getTagsById(String tagName) {
        String key = "TAGS::" + tagName + "::CHILDREN";
        Jedis jedis = pool.getResource();
        try {
            return jedis.smembers(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
