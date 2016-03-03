package com.showcase.restful.data.rest.controller;

import com.showcase.restful.common.*;
import com.showcase.restful.data.entity.Page;
import com.showcase.restful.data.entity.Product;
import com.showcase.restful.data.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description: SpringMVC目录式结构
 */

@RestController //springmvc4.0提供，每个方法@ResponseBody
public class ProductV2Controller {

    private static final Logger logger = LoggerFactory.getLogger(ProductV2Controller.class);

    @Autowired
    private ProductService productService;

    /**
     * 查询 获取一个产品信息
     * 返回示例：
     * {
     * "code":"00",
     * "message":"success",
     * "data":{
     * "productId":1,
     * "productName":"10元套餐",
     * "availableDate":null,
     * "endDate":null,
     * "status":null,
     * "description":"无"
     * }
     * }
     */
    @RequestMapping(value = {ApiConstants.V2 + "/getProducts/{productId}"},
            method = {RequestMethod.GET})
    public ResponseResult getProduct(
            @PathVariable String productId) {
        try {
            //1.参数校验
            ParamChecker pc = ParamChecker.newInstance()
                    .notNullNumberCheck("productId", productId);
            if (pc.isNotValid()) {
                return ResponseResultBuilder.getInstance()
                        .genParamInvalid("invalid param ", pc.getCheckResult());
            }
            //2.查询数据
            Product product = productService.getProduct(Integer.parseInt(productId));
            //3.数据查询并返回json串
            return ResponseResultBuilder.getInstance().genSuccess(product);
        } catch (Exception e) {
            logger.error("query product [{}] data exception:{}", productId, e);
            return ResponseResultBuilder.getInstance().genUnknowError(e);
        }
    }

    /**
     * 查询产品信息列表，分页查询
     * url:http://localhost:9090/restful/v2/getProducts?productName=11&status=1&start=1&limit=20
     * @param productName
     * @param status
     * @param start       开始页码， 从1开始
     * @param limit       每页限制数量，
     * @return
     */
    @RequestMapping(value = {ApiConstants.V2 + "/getProducts"},
            method = {RequestMethod.GET})
    public ResponseResult getProducts(
            @RequestParam("productName") String productName,
            @RequestParam("status") String status,
            @RequestParam("start") String start,
            @RequestParam("limit") String limit) {
        try {
            //1.参数校验
            ParamChecker pc = ParamChecker.newInstance()
                    .notBlankCheck("productName", productName)
                    .notBlankCheck("status", status)
                    .notNumberCheck("start", start)
                    .notNumberCheck("limit", limit);
            if (pc.isNotValid()) {
                return ResponseResultBuilder.getInstance()
                        .genParamInvalid("invalid param ", pc.getCheckResult());
            }

            long startNum = Long.parseLong(start);
            long limitNum = Long.parseLong(limit);
            Page<Product> page = new Page();
            page.setStartIndex((startNum - 1) * limitNum);
            page.setLastIndex(startNum * limitNum);

            Product product = new Product();
            product.setStatus(status);
            product.setProductName(productName);

            return ResponseResultBuilder.getInstance().genSuccess(
                    productService.getProducts(page, product).getCurrentPageResult());
        } catch (Exception e) {
            logger.error("query products data exception:" + e);
            return ResponseResultBuilder.getInstance().genUnknowError(e);
        }
    }

    /**
     * 新增一个产品
     *
     * @return
     */
    @RequestMapping(value = {ApiConstants.V2 + "/getProducts"},
            method = {RequestMethod.POST})
    public ResponseResult addProduct(@ModelAttribute Product product) {
        try {
            boolean result = productService.saveProduct(product);
            if (result) {
                return ResponseResultBuilder.getInstance().genSuccess(null);
            } else {
                return ResponseResultBuilder.getInstance().genUnknowError(null);
            }

        } catch (Exception e) {
            logger.error("add product data exception:" + e);
            return ResponseResultBuilder.getInstance().genUnknowError(e);
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
    @RequestMapping(value = {ApiConstants.V2 + "/updateProduct/{productId}"},
            method = {RequestMethod.PUT})
    public ResponseResult updateProduct(
            @PathVariable("productId") String productId,
            @RequestParam("productName") String productName,
            @RequestParam("availableDate") String availableDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("status") String status,
            @RequestParam("description") String description) {
        try {
            //1.参数校验
            ParamChecker pc = ParamChecker.newInstance()
                    .notNullNumberCheck("productId", productId)
                    .notBlankCheck("status", status)
                    .paramDateCheck("availableDate", availableDate, "yyyy-MM-dd hh:mm:ss")
                    .paramDateCheck("endDate", availableDate, "yyyy-MM-dd hh:mm:ss");
            if (pc.isNotValid()) {
                return ResponseResultBuilder.getInstance()
                        .genParamInvalid("invalid param ", pc.getCheckResult());
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
                return ResponseResultBuilder.getInstance().genSuccess(null);
            } else {
                return ResponseResultBuilder.getInstance().genUnknowError(null);
            }

        } catch (Exception e) {
            logger.error("modify product data exception:" + e);
            return ResponseResultBuilder.getInstance().genUnknowError(e);
        }
    }

    /**
     * 删除一个产品信息
     *
     * @param productId 产品ID ，必要参数
     * @return
     */
    @RequestMapping(value = {ApiConstants.V2 + "/deleteProduct/{productId}"},
            method = {RequestMethod.DELETE})
    public ResponseResult deleteProduct(
            @PathVariable("productId") String productId) {
        try {
            //1.参数校验
            ParamChecker pc = ParamChecker.newInstance()
                    .notNullNumberCheck("productId", productId);
            if (pc.isNotValid()) {
                return ResponseResultBuilder.getInstance()
                        .genParamInvalid("invalid param ", pc.getCheckResult());
            }

            boolean result = productService.deleteProduct(Integer.parseInt(productId));
            if (result) {
                return ResponseResultBuilder.getInstance().genSuccess(null);
            } else {
                return ResponseResultBuilder.getInstance().genUnknowError(null);
            }

        } catch (Exception e) {
            logger.error("delete product data exception:" + e);
            return ResponseResultBuilder.getInstance().genUnknowError(e);
        }
    }
}
