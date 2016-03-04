package com.showcase.restful.memcached;

import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author: lei hang
 * @date: 2016年03月04日
 * @description:
 */

public class MemcachedClient implements InitializingBean {

    //心跳检测间隔
    private static final long SESSION_IDLE_TIMEOUT = 20000L;

    //操作超时时间
    private static final long OP_TIMEOUT = 5000L;

    //连接超时时间
    private static final long CONNECT_TIMEOUT = 10000L;

    private final Logger logger = LoggerFactory.getLogger(MemcachedClient.class);

    //memcach servers localhost:11211
    private String memcachedNodes;

    //nio连接池大小 默认为1
    private int connectionPoolSize;

    //memcachedClient 客户端
    private net.rubyeye.xmemcached.MemcachedClient memcachedClient;

    public void afterPropertiesSet() {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddressMap(memcachedNodes));
        builder.setConnectionPoolSize(connectionPoolSize);
        //设置failure 模式为true,支持主备模式
        //        builder.setFailureMode(true);
        builder.getConfiguration().setSessionIdleTimeout(SESSION_IDLE_TIMEOUT);
        try {
            memcachedClient = builder.build();
            memcachedClient.setOpTimeout(OP_TIMEOUT);
            memcachedClient.setConnectTimeout(CONNECT_TIMEOUT);
        } catch (IOException e) {
            logger.error("Connect to memcached server fail {}", e);
        }
    }

    /**
     * 更新缓存中存在的值
     *
     * @param key   存储的key名称
     * @param value 实际存储的数据 ,可以是任意的java可序列化类型
     * @return
     */
    public boolean replace(String key, Object value) {
        try {
            return memcachedClient.replace(key, 0, value);
        } catch (TimeoutException e) {

            logger.error("MemcachedClient replace timeout {}", e);
        } catch (InterruptedException e) {

            logger.error("MemcachedClient replace interrupted error: {}", e);
        } catch (MemcachedException e) {

            logger.error("MemcachedClient replace fail {}", e);
        }
        return false;
    }

    /**
     * 更新缓存中存在的值 有效期内有效
     *
     * @param key         存储的key名称
     * @param expireTimes expire时间（单位秒）  超过这个时间,memcached将这个数据替换出去,0表示永久存储（默认是一个月）
     * @param value       实际存储的数据 ,可以是任意的java可序列化类型
     * @return
     */
    public boolean replace(String key, int expireTimes, Object value) {
        try {
            return memcachedClient.replace(key, expireTimes, value);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient replace timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient replace interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient replace fail {}", e);
        }
        return false;
    }

    /**
     * 设置key对应的缓存，缓存中是否存在都可以使用。
     *
     * @param key   存储的key名称
     * @param value 实际存储的数据 ,可以是任意的java可序列化类型
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            return memcachedClient.set(key, 0, value);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient add timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient add interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient add fail {}", e);
        }
        return false;
    }

    /**
     * 设置key对应的缓存，缓存中是否存在都可以使用。
     *
     * @param key         存储的key名称
     * @param expireTimes expire时间（单位秒）  超过这个时间,memcached将这个数据替换出去,0表示永久存储（默认是一个月）
     * @param value       实际存储的数据 ,可以是任意的java可序列化类型
     * @return
     */
    public boolean set(String key, int expireTimes, Object value) {
        try {
            return memcachedClient.set(key, expireTimes, value);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient set timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient set interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient set fail {}", e);
        }
        return false;
    }

    /**
     * 删除缓存中的一个对象
     *
     * @param key 存储的key名称
     * @return
     */
    public boolean delete(String key) {
        try {
            return memcachedClient.delete(key);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient delete timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient delete interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient delete fail {}", e);
        }
        return false;
    }

    /**
     * 在现有缓存前添加数据，生成新的缓存
     *
     * @param key   存储的key名称
     * @param value 实际存储的数据 ,可以是任意的java可序列化类型
     * @return
     */
    public boolean append(String key, Object value) {
        try {
            return memcachedClient.append(key, value);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient append timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient append interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient append fail {}", e);
        }
        return false;
    }

    /**
     * 计数器减量操作，默认减1
     *
     * @param key 存储的key名称
     * @return the new value of the item's data, after the decrement operation was carried out.
     */
    public long decr(String key) {
        try {
            return memcachedClient.decr(key, 1);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient decr timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient decr interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient decr fail {}", e);
        }
        return 0L;
    }

    /**
     * 计数器减量操作，减des
     *
     * @param key   存储的key名称
     * @param delta
     * @return the new value of the item's data, after the decrement operation was carried out.
     */
    public long decr(String key, long delta) {
        try {
            return memcachedClient.decr(key, delta);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient decr timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient decr interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient decr fail {}", e);
        }
        return 0L;
    }

    /**
     * 对计数器增量操作，默认加1。
     *
     * @param key 存储的key名称
     * @return
     */
    public long incr(String key) {
        try {
            return memcachedClient.incr(key, 1);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient incr timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient incr interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient incr fail {}", e);
        }
        return 0L;
    }

    /**
     * 对计数器增量操作，加delta。
     *
     * @param key   存储的key名称
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        try {
            return memcachedClient.incr(key, delta);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient incr timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient incr interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient incr fail {}", e);
        }
        return 0L;
    }

    /**
     * key值在缓存中是否存在
     *
     * @param key 存储的key名称
     * @return
     */
    public boolean keyExists(String key) {
        return get(key) != null;
    }

    /**
     * 根据指定的关键字获取对象.
     *
     * @param key 存储的key名称
     * @return
     */
    public Object get(String key) {
        try {
            return memcachedClient.get(key);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient get timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient get interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient get fail {}", e);
        }
        return null;
    }

    /**
     * 从cache服务器获取一组数据
     *
     * @param keys
     * @return
     */
    public Map<String, Object> getMulti(List keys) {
        try {
            return (Map) memcachedClient.gets(keys);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient getMulti timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient getMulti interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient getMulti fail {}", e);
        }
        return new HashMap<String, Object>();
    }

    /**
     * 添加一个key到cache，如果已经存在则返回false
     *
     * @param key
     * @param val
     * @return
     */
    public boolean add(String key, int expireTime, String val) {
        try {
            return memcachedClient.add(key, expireTime, val);
        } catch (TimeoutException e) {
            logger.error("MemcachedClient add timeout {}", e);
        } catch (InterruptedException e) {
            logger.error("MemcachedClient add interrupted error: {}", e);
        } catch (MemcachedException e) {
            logger.error("MemcachedClient add fail {}", e);
        }
        return false;
    }

    public void shutdown() throws IOException {
        if (memcachedClient != null) {
            memcachedClient.shutdown();
        }
    }

    public void setMemcachedNodes(String memcachedNodes) {
        this.memcachedNodes = memcachedNodes;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }
}