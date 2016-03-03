package com.showcase.restful.data.controller;

import com.showcase.restful.common.*;
import com.showcase.restful.data.entity.Page;
import com.showcase.restful.data.entity.Product;
import com.showcase.restful.data.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description: JAX-RS jersey方式实现
 * JAX-RS即Java API for RESTful Web Services，是一个Java 编程语言的应用程序接口，支持按照表述性状态转移（REST）架构风格创建Web服务。
 * JAX-RS使用了Java SE5引入的Java标注来简化Web服务的客户端和服务端的开发和部署。
 */

@Path("/product")
@Component
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 查询 获取一个产品信息
     * url:http://localhost:8080/jersey/api/product/v1/getProduct?productId=1
     */
    @Path(ApiConstants.V1 + "/getProduct")
    @GET
    @Produces({MediaType.APPLICATION_JSON, "text/html;charset=utf-8"})
    public String getProduct(
            @QueryParam("productId") String productId) {
        try {
            //1.参数校验
            ParamChecker pc = ParamChecker.newInstance()
                    .notNullNumberCheck("productId", productId);
            if (pc.isNotValid()) {
                return ResponseResultBuilder.getInstance()
                        .genParamInvalid2Json("invalid param ", pc.getCheckResult());
            }
            //2.查询数据
            Product product = productService.getProduct(Integer.parseInt(productId));
            //3.数据查询并返回json串
            return ResponseResultBuilder.getInstance().genSuccess2Json(product);
        } catch (Exception e) {
            logger.error("query product [{}] data exception:{}", productId, e);
            return ResponseResultBuilder.getInstance().genUnknowError2Json(e);
        }
    }

    /**
     * 查询产品信息列表，分页查询
     * url:http://localhost:8080/jersey/api/product/v1/getProducts?productName=1&status=1&start=1&limit=2
     * @param productName
     * @param status
     * @param start       开始页码， 从1开始
     * @param limit       每页限制数量，
     * @return
     */

    @Path(ApiConstants.V1 + "/getProducts")
    @GET
    @Produces({MediaType.APPLICATION_JSON, "text/html;charset=utf-8"})
    public String getProducts(
            @QueryParam("productName") String productName,
            @QueryParam("status") String status,
            @QueryParam("start") String start,
            @QueryParam("limit") String limit) {
        try {
            //1.参数校验
            ParamChecker pc = ParamChecker.newInstance()
                    .notBlankCheck("productName", productName)
                    .notBlankCheck("status", status)
                    .notNumberCheck("start", start)
                    .notNumberCheck("limit", limit);
            if (pc.isNotValid()) {
                return ResponseResultBuilder.getInstance()
                        .genParamInvalid2Json("invalid param ", pc.getCheckResult());
            }

            long startNum = Long.parseLong(start);
            long limitNum = Long.parseLong(limit);
            Page<Product> page = new Page();
            page.setStartIndex((startNum - 1) * limitNum);
            page.setLastIndex(startNum * limitNum);

            Product product = new Product();
            product.setStatus(status);
            product.setProductName(productName);

            return ResponseResultBuilder.getInstance().genSuccess2Json(
                    productService.getProducts(page, product).getCurrentPageResult());
        } catch (Exception e) {
            logger.error("query products data exception:" + e);
            return ResponseResultBuilder.getInstance().genUnknowError2Json(e);
        }
    }

    /**
     * 新增一个产品
     *
     * @param productName
     * @param availableDate
     * @param endDate
     * @param status
     * @param description
     * @return
     */
    @Path(ApiConstants.V1 + "/addProduct")
    @POST
    @Produces({MediaType.APPLICATION_JSON, "text/html;charset=utf-8"})
    public String addProduct(
            @QueryParam("productName") String productName,
            @QueryParam("availableDate") String availableDate,
            @QueryParam("endDate") String endDate,
            @QueryParam("status") String status,
            @QueryParam("description") String description) {
        try {
            //1.参数校验
            ParamChecker pc = ParamChecker.newInstance()
                    .notBlankCheck("productName", productName)
                    .notBlankCheck("availableDate", availableDate)
                    .notBlankCheck("endDate", endDate)
                    .notBlankCheck("status", status)
                    .paramDateCheck("availableDate", availableDate, "yyyy-MM-dd hh:mm:ss")
                    .paramDateCheck("endDate", availableDate, "yyyy-MM-dd hh:mm:ss");
            if (pc.isNotValid()) {
                return ResponseResultBuilder.getInstance()
                        .genParamInvalid2Json("invalid param ", pc.getCheckResult());
            }

            Product product = new Product();
            product.setStatus(status);
            product.setProductName(productName);
            product.setAvailableDate(DateFormatConstant.DATA_FORMAT_YEAR_MONTH_DAY_TIME.parse(availableDate));
            product.setEndDate(DateFormatConstant.DATA_FORMAT_YEAR_MONTH_DAY_TIME.parse(endDate));
            product.setStatus(status);

            boolean result = productService.saveProduct(product);
            if (result) {
                return ResponseResultBuilder.getInstance().genSuccess2Json(null);
            } else {
                return ResponseResultBuilder.getInstance().genUnknowError2Json(null);
            }

        } catch (Exception e) {
            logger.error("add product data exception:" + e);
            return ResponseResultBuilder.getInstance().genUnknowError2Json(e);
        }
    }

    /**
     * 修改一个产品
     *
     * @param productId     必要参数
     * @param productName
     * @param availableDate
     * @param endDate
     * @param status
     * @param description
     * @return
     */
    @Path(ApiConstants.V1 + "/updateProduct")
    @POST
    @Produces({MediaType.APPLICATION_JSON, "text/html;charset=utf-8"})
    public String updateProduct(
            @QueryParam("productId") String productId,
            @QueryParam("productName") String productName,
            @QueryParam("availableDate") String availableDate,
            @QueryParam("endDate") String endDate,
            @QueryParam("status") String status,
            @QueryParam("description") String description) {
        try {
            //1.参数校验
            ParamChecker pc = ParamChecker.newInstance()
                    .notNullNumberCheck("productId", productId)
                    .notBlankCheck("status", status)
                    .paramDateCheck("availableDate", availableDate, "yyyy-MM-dd hh:mm:ss")
                    .paramDateCheck("endDate", availableDate, "yyyy-MM-dd hh:mm:ss");
            if (pc.isNotValid()) {
                return ResponseResultBuilder.getInstance()
                        .genParamInvalid2Json("invalid param ", pc.getCheckResult());
            }

            Product product = new Product();
            product.setProductId(Integer.parseInt(productId));
            product.setStatus(status);
            product.setProductName(productName);
            if (StringUtils.isNotBlank(availableDate)) {
                product.setAvailableDate(DateFormatConstant.DATA_FORMAT_YEAR_MONTH_DAY_TIME.parse(availableDate));
            }
            if (StringUtils.isNotBlank(endDate)) {
                product.setEndDate(DateFormatConstant.DATA_FORMAT_YEAR_MONTH_DAY_TIME.parse(endDate));
            }
            product.setStatus(status);
            boolean result = productService.updateProduct(product);
            if (result) {
                return ResponseResultBuilder.getInstance().genSuccess2Json(null);
            } else {
                return ResponseResultBuilder.getInstance().genUnknowError2Json(null);
            }

        } catch (Exception e) {
            logger.error("modify product data exception:" + e);
            return ResponseResultBuilder.getInstance().genUnknowError2Json(e);
        }
    }

    /**
     * 删除一个产品信息
     *
     * @param productId 产品ID ，必要参数
     * @return
     */
    @Path(ApiConstants.V1 + "/deleteProduct")
    @POST
    @Produces({MediaType.APPLICATION_JSON, "text/html;charset=utf-8"})
    public String deleteProduct(
            @QueryParam("productId") String productId) {
        try {
            //1.参数校验
            ParamChecker pc = ParamChecker.newInstance()
                    .notNullNumberCheck("productId", productId);
            if (pc.isNotValid()) {
                return ResponseResultBuilder.getInstance()
                        .genParamInvalid2Json("invalid param ", pc.getCheckResult());
            }

            boolean result = productService.deleteProduct(Integer.parseInt(productId));
            if (result) {
                return ResponseResultBuilder.getInstance().genSuccess2Json(null);
            } else {
                return ResponseResultBuilder.getInstance().genUnknowError2Json(null);
            }

        } catch (Exception e) {
            logger.error("delete product data exception:" + e);
            return ResponseResultBuilder.getInstance().genUnknowError2Json(e);
        }
    }
}
