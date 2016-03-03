package com.showcase.restful.data.dao;

import com.showcase.restful.data.entity.Page;
import com.showcase.restful.data.entity.Product;

import java.util.List;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description:
 */

public interface ProductDao {

    public boolean saveProduct(Product product);

    public boolean updateProduct(Product product);

    public boolean deleteProduct(int productId);

    public Product getProduct(int productId);

    public List<Product> getProducts(Page<Product> page, Product product);

    public long getProductsCount(Page<Product> page, Product product);
}
