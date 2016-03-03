package com.showcase.restful.data.service.impl;

import com.showcase.restful.data.dao.ProductDao;
import com.showcase.restful.data.entity.Page;
import com.showcase.restful.data.entity.Product;
import com.showcase.restful.data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description:
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    public boolean saveProduct(Product product) {
        return productDao.saveProduct(product);
    }

    public boolean updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    public boolean deleteProduct(int productId) {
        return productDao.deleteProduct(productId);
    }

    public Product getProduct(int productId) {
        return productDao.getProduct(productId);
    }

    public Page<Product> getProducts(Page<Product> page, Product product) {
        return new Page<Product>(productDao.getProducts(page, product), productDao.getProductsCount(page, product));
    }
}
