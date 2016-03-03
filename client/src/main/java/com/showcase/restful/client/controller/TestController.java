package com.showcase.restful.client.controller;

import com.alibaba.fastjson.JSON;
import com.showcase.restful.client.common.HttpRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lei hang
 * @date: 2016年03月02日
 * @description:
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private HttpRequestService httpRequestService;

    /**
     * 查询 获取一个产品信息
     */
    @RequestMapping(value = {"/getProduct"},
            method = {RequestMethod.GET})
    @ResponseBody
    public Map getProduct(
            @RequestParam("productId") String productId) {
        Map<String, Object> resultMap = new HashMap();
        String url = "http://localhost:9090/restful/v1/getProduct?productId=" + productId;
        try {
            resultMap = JSON.parseObject(httpRequestService.sendHttpGet(url), HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

}
