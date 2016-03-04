package com.showcase.restful.memcached;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: lei hang
 * @date: 2016年03月04日
 * @description:
 */

public abstract class AbstractMemcachedClientProxy {

    private MemcachedClient memcachedClient;

    public AbstractMemcachedClientProxy() {
    }

    public Object get(String key) {
        Object value = this.memcachedClient.get(key);
        if (null == value) {
            value = this.getValue(key);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new AsyncMemcachedHandleThread(this.memcachedClient, key, value));
            executorService.shutdown();
        }

        return value;
    }

    protected abstract Object getValue(String var1);

    public boolean set(String key, int expireTimes, Object obj) {
        return this.memcachedClient.set(key, expireTimes, obj);
    }

    public boolean set(String key, Object obj) {
        return this.memcachedClient.set(key, obj);
    }


    @Autowired
    public void setMemcachedClient(@Qualifier("memcachedClient") MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}