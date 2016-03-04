package com.showcase.restful.data.dao.impl;

import com.showcase.restful.data.common.DaoException;
import com.showcase.restful.data.common.MyBatisBaseDAO;
import com.showcase.restful.data.dao.ProductDao;
import com.showcase.restful.data.entity.Page;
import com.showcase.restful.data.entity.Product;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description:
 */
@Component
public class ProductDaoImpl extends MyBatisBaseDAO<Product> implements ProductDao {

    private static String mapperNameSpace = "com.showcase.restful.data.mybatis.mappers.ProductMapper";

    public boolean saveProduct(Product product) {
        try {
            save(mapperNameSpace + ".saveProduct", product);
        } catch (DaoException e) {
            return false;
        }
        return true;
    }

    public boolean updateProduct(Product product) {
        try {
            delete(mapperNameSpace + ".updateProduct", product);
        } catch (DaoException e) {
            return false;
        }
        return true;
    }

    public boolean deleteProduct(int productId) {
        try {
            delete(mapperNameSpace + ".deleteProduct", productId);
        } catch (DaoException e) {
            return false;
        }
        return true;
    }

    public Product getProduct(int productId) {
        return get(mapperNameSpace + ".getProduct", productId);
    }

    public List<Product> getProducts(Page<Product> page, Product product) {
        Map conditionMap = new HashMap();
        conditionMap.put("productName", product.getProductName());
        conditionMap.put("status", product.getStatus());
        conditionMap.put("startIndex", page.getStartIndex());
        conditionMap.put("lastIndex", page.getLastIndex());
        conditionMap.put("orderBy", "");
        conditionMap.put("order", page.getOrder());
        return getList(mapperNameSpace + ".getProductListPage", conditionMap);
    }

    public long getProductsCount(Page<Product> page, Product product) {
        Map conditionMap = new HashMap();
        conditionMap.put("productName", product.getProductName());
        conditionMap.put("status", product.getStatus());
        conditionMap.put("startIndex", page.getStartIndex());
        conditionMap.put("lastIndex", page.getLastIndex());
        conditionMap.put("orderBy", "");
        conditionMap.put("order", page.getOrder());
        return getTotalCount(mapperNameSpace + ".getProductListPageCount", conditionMap);
    }

    public List<Product> getProductListAll() {
        return getList(mapperNameSpace + ".getProductListAll");
    }
}
