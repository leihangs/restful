package com.showcase.restful.memcached;

/**
 * @author: lei hang
 * @date: 2016年03月04日
 * @description:
 */

public class AsyncMemcachedHandleThread extends Thread {
    private MemcachedClient client;
    private String cacheKey;
    private Object cacheVlaue;

    public AsyncMemcachedHandleThread(MemcachedClient client, String cacheKey, Object cacheVlaue) {
        this.client = client;
        this.cacheKey = cacheKey;
        this.cacheVlaue = cacheVlaue;
        this.setName("base-AsyncMemcachedHandleThread");
    }

    public void run() {
        boolean timeout = true;
        this.client.set(this.cacheKey, 600, this.cacheVlaue);
    }
}
