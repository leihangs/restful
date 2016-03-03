package com.showcase.restful.data.service;

import com.showcase.restful.data.entity.Page;
import com.showcase.restful.data.entity.Product;

import java.util.List;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description: 产品信息服务类
 */

public interface ProductService {

    public boolean saveProduct(Product product);

    public boolean updateProduct(Product product);

    public boolean deleteProduct(int productId);

    public Product getProduct(int productId);

    public Page<Product> getProducts(Page<Product> page, Product product);
}
