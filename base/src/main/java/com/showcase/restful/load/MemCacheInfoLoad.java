package com.showcase.restful.load;

import com.showcase.restful.common.MemcachedConstants;
import com.showcase.restful.data.dao.ProductDao;
import com.showcase.restful.data.entity.Product;
import com.showcase.restful.memcached.BaseMemcachedCachedProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: lei hang
 * @date: 2016年03月04日
 * @description:
 */

public class MemCacheInfoLoad {
    private final Logger logger = LoggerFactory.getLogger(MemCacheInfoLoad.class);
    @Autowired
    private BaseMemcachedCachedProxy memcachedProxy;
    @Autowired
    private ProductDao productDao;

    public void init() {
        loadProductInfo();
    }

    private void loadProductInfo() {
        List<Product> all = productDao.getProductListAll();
        for (Product product : all) {
            memcachedProxy.set(MemcachedConstants.PRODUCT_KEY + product.getProductId(), product);
        }
    }
}