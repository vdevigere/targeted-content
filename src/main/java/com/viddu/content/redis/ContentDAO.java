package com.viddu.content.redis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentTag;
import com.viddu.content.bo.ContentType;

public class ContentDAO {
    private final JedisPool pool;

    private static final Logger logger = LoggerFactory.getLogger(ContentDAO.class);

    @Inject
    public ContentDAO(JedisPool pool) {
        this.pool = pool;
    }

    public Content getContentById(Long id, Integer depth) {
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
            Content content = new Content(name, type, url);
            content.setId(id);
            // Fetch Tag details.
            if (depth > 0) {
                logger.debug("Fetching Tags for Content {}, Depth={}", id, depth);
                content.setTags(getTagsByContentId(id, depth));
            }
            return content;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<ContentTag> getTagsByContentId(Long id, Integer depth) {
        depth--;
        String key = "CONTENT::" + id + "::TAGS";
        Set<ContentTag> contentTags = new LinkedHashSet<ContentTag>();
        Jedis jedis = pool.getResource();
        try {
            Set<String> tags = jedis.smembers(key);
            for (String tagName : tags) {
                ContentTag contentTag = new ContentTag(tagName);
                // Fetch Content details.
                if (depth > 0) {
                    logger.debug("Fetching Content by TagName {}, Depth={}", tagName, depth);
                    contentTag.setContent(getContentByTagName(tagName, depth));
                }
                contentTags.add(contentTag);
            }

            return contentTags;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<Content> getContentByTagName(String tagName, Integer depth) {
        depth--;
        String key = "TAGS::" + tagName + "::CONTENT";
        Jedis jedis = pool.getResource();
        Set<Content> taggedContent = new LinkedHashSet<Content>();
        try {
            Set<String> contentIds = jedis.smembers(key);
            for (String contentId : contentIds) {
                Long id = Long.parseLong(contentId);
                taggedContent.add(getContentById(id, depth));
            }
            return taggedContent;
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
