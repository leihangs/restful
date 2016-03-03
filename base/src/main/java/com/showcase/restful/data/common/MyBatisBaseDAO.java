package com.showcase.restful.data.common;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description:
 */

public class MyBatisBaseDAO<T> extends SqlSessionDaoSupport {

    private final Logger logger = LoggerFactory.getLogger(MyBatisBaseDAO.class);

    //mybatis 1.2.2
    @Override
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public void save(String key, Object object) throws DaoException {
        try {
            Assert.notNull(object, "object saved is not null.");
            getSqlSession().insert(key, object);
            this.logger.debug("save key: {}, object: {}", key, object);
        } catch (DataAccessException e) {
            this.logger.error("save error", e);
            throw new DaoException("save error.", e);
        }
    }

    public void delete(String key, Serializable id) throws DaoException {
        try {
            this.logger.debug("delete key: {}, id: {}", key, id);
            getSqlSession().delete(key, id);
        } catch (DataAccessException e) {
            this.logger.error("delete error", e);
            throw new DaoException("delete by id error.", e);
        }
    }

    public void delete(String key, Object object) throws DaoException {
        try {
            this.logger.debug("delete key: {}, object: {}", key, object);
            getSqlSession().delete(key, object);
        } catch (DataAccessException e) {
            this.logger.error("delete error", e);
            throw new DaoException("delete by object error.", e);
        }
    }

    public void delete(String key, Map<String, Object> conditionMap) throws DaoException {
        this.logger.debug("delete key:{} conditionMap:{}", key, conditionMap);
        try {
            getSqlSession().delete(key, conditionMap);
        } catch (DataAccessException e) {
            this.logger.error("delete error", e);
            throw new DaoException("delete by params error", e);
        }
    }

    public void update(String key, Object object) throws DaoException {
        try {
            this.logger.debug("update key: {}, object: {}", key, object);
            getSqlSession().update(key, object);
        } catch (DataAccessException e) {
            this.logger.error("update error", e);
            throw new DaoException(e);
        }
    }

    public T get(String key, Object param) throws DaoException {
        this.logger.debug("get key:{} param:{}", key, param);
        try {
            return getSqlSession().selectOne(key, param);
        } catch (DataAccessException e) {
            this.logger.error("get error:", e);
            throw new DaoException(e);
        }
    }

    public T get(String key, Map<String, Object> paramMap) throws DaoException {
        this.logger.debug("get key:{} paramMap:{}", key, paramMap);
        try {
            return getSqlSession().selectOne(key, paramMap);
        } catch (DataAccessException e) {
            this.logger.error("get error", e);
            throw new DaoException(e);
        }
    }

    public T get(String key) throws DaoException {
        try {
            this.logger.debug("getList key{}", key);
            return getSqlSession().selectOne(key);
        } catch (DataAccessException e) {
            this.logger.error("getList error", e);
            throw new DaoException(e);
        }
    }

    public boolean checkPropertyValueUnique(String key, String propertyName, Object newValue, Object oldValue) throws DaoException {
        if ((newValue == null) || (newValue.equals(oldValue))) {
            return true;
        }
        Long object = null;
        try {
            Map proprtyMap = new HashMap();
            proprtyMap.put(propertyName, newValue);
            object = (Long) getSqlSession().selectOne(key, proprtyMap);
        } catch (DataAccessException e) {
            this.logger.error("checkPropertyValueUnique error", e);
            throw new DaoException(e);
        }
        return object.longValue() == 0L;
    }

    public List<T> getList(String key) throws DaoException {
        try {
            this.logger.debug("getList key{}", key);
            return getSqlSession().selectList(key);
        } catch (DataAccessException e) {
            this.logger.error("getList error", e);
            throw new DaoException(e);
        }
    }

    public List<T> getList(String key, Serializable serializable) throws DaoException {
        try {
            this.logger.debug("getList key{},serializable{}", key, serializable);
            return getSqlSession().selectList(key, serializable);
        } catch (DataAccessException e) {
            this.logger.error("getList error", e);
            throw new DaoException(e);
        }
    }

    public List<T> getList(String key, Object param) throws DaoException {
        this.logger.debug("getList key{},param{}", key, param);
        try {
            return getSqlSession().selectList(key, param);
        } catch (DataAccessException e) {
            this.logger.error("getList error", e);
            throw new DaoException(e);
        }
    }

    public long getTotalCount(String key, Object param) throws DaoException {
        try {
            this.logger.debug("getList key {},paramMap {}", key, param);
            return ((Long) getSqlSession().selectOne(key, param)).longValue();
        } catch (DataAccessException e) {
            this.logger.error("getTotalCount error", e);
            throw new DaoException(e);
        }
    }

    public List<T> getList(String key, Map<String, Object> paramMap) throws DaoException {
        this.logger.debug("getList key{},paramMap{}", key, paramMap);
        try {
            return getSqlSession().selectList(key, paramMap);
        } catch (DataAccessException e) {
            this.logger.error("getList error {}", e);
            throw new DaoException(e);
        }
    }

    public long getTotalCount(String key, Object[] params) throws DaoException {
        try {
            Map paramMap = fillConditionMap(params);
            this.logger.debug("getList key {},paramMap {}", key, paramMap);
            return ((Long) getSqlSession().selectOne(key, paramMap)).longValue();
        } catch (DataAccessException e) {
            this.logger.error("getTotalCount error", e);
            throw new DaoException(e);
        }
    }


    private Map<String, Object> fillConditionMap(Object[] params) {
        Map paramMap = new HashMap();
        int index = 0;
        for (Object param : params) {
            paramMap.put(index++ + "", param);
        }
        return paramMap;
    }

    protected Map<String, Object> fillConditionMapByObjects(Object[] params) {
        return fillConditionMap(params);
    }

    public String getSeqValue(String key) throws DaoException {
        this.logger.error("getSeqValue key:{}", key);
        try {
            return (String) getSqlSession().selectOne(key);
        } catch (DataAccessException e) {
            this.logger.error("getSeqValue error:{}", e);
            throw new DaoException(e);
        }
    }


    protected void closeDatabaseSource(Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            this.logger.error("resultset close error", e);
        } finally {
            closeStatement(st);
        }
    }

    private void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            this.logger.error("statement close error", e);
        }
    }
}
