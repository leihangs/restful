package com.showcase.restful.data.entity;

import java.util.Date;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description: 产品信息
 */

public class Product {

    //产品编码
    private int productId;
    //产品名称
    private String productName;
    //可用时间
    private Date availableDate;
    //停用时间
    private Date endDate;
    //状态 1－有效，0－无效
    private String status;
    //产品描述
    private String description;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
