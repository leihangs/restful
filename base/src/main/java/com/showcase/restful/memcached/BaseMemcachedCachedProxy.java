package com.showcase.restful.memcached;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

/**
 * @author: lei hang
 * @date: 2016年03月04日
 * @description:
 */

public class BaseMemcachedCachedProxy extends AbstractMemcachedClientProxy {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Object getValue(String key) {
        Object paramValue = null;
        try {
            paramValue = get(key);
        } catch (DataAccessException e) {
            this.logger.error("paramDAO.getParamValue exception,e={}", e);
        }
        return paramValue;
    }

    public boolean set(String key, int expireTimes, Object obj) {
        return super.set(key, expireTimes, obj);
    }

    public boolean set(String key, Object obj) {
        return super.set(key, obj);
    }


}
