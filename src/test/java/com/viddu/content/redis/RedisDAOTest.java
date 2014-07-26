package com.viddu.content.redis;

import static org.mockito.Mockito.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentType;
import com.viddu.content.bo.MenuItem;
import com.viddu.content.bo.Taggable;

public class RedisDAOTest {

    private JedisPool mockJedisPool = mock(JedisPool.class);

    private Jedis mockJedis = mock(Jedis.class);

    private final RedisDAO dao;

    class NestedClass {
        public NestedClass(String field1) {
            super();
            this.field1 = field1;
        }

        private final String field1;

        public String getField1() {
            return field1;
        }

    }

    class ComposingClass implements Taggable {
        public ComposingClass(String field2, NestedClass nestedClass) {
            super();
            this.field2 = field2;
            this.nestedClass = nestedClass;
        }

        private final String field2;
        private final NestedClass nestedClass;
        private Set<String> tags;

        public String getField2() {
            return field2;
        }

        public NestedClass getNestedClass() {
            return nestedClass;
        }

        @Override
        public Set<String> getTags() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setTags(Set<String> tags) {
            // TODO Auto-generated method stub

        }

    }

    public RedisDAOTest() {
        when(mockJedis.incr("NEXT_OBJ_ID")).thenReturn(1L);
        when(mockJedisPool.getResource()).thenReturn(mockJedis);
        dao = new RedisDAO(mockJedisPool, new ObjectMapper());

    }

    @Test
    public void testSave() throws MalformedURLException {
        Taggable content = new Content("Viddu", ContentType.IMAGE, "http://www.google.com");
        String[] tags = { "TAG1", "TAG2", "TAG3" };
        content.setTags(new LinkedHashSet<String>(Arrays.asList(tags)));
        dao.save("Content", content);
        ComposingClass obj2 = new ComposingClass("Viddu", new NestedClass("Vivaan"));
        dao.save("Dummy", obj2);

    }

}
