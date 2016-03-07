package com.showcase.restful.api.controller;

import com.showcase.restful.api.common.ApiConstants;
import com.showcase.restful.api.common.ResponseResultBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author: lei hang
 * @date: 2016年03月07日
 * @description:
 */
@Controller
@RequestMapping(ApiConstants.API_VERSION)
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    //接口超时时间(毫秒)
    public static final int DATA_OVERTIME_DEFAULT = 30000;

    @RequestMapping(value = "/{apiName}", method = {RequestMethod.POST, RequestMethod.GET})
    public void apiCallApi(@PathVariable("apiName") String apiName,
                           HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        callServer(httpServletRequest, httpServletResponse, ApiConstants.API_VERSION, apiName);
    }

    private void callServer(HttpServletRequest request, HttpServletResponse httpServletResponse,
                            String version, String apiName) {
        String returnInfo = null;
        long startTime = System.currentTimeMillis();
        logger.info("--------start--------[" + apiName + "] : {}", startTime);

        //获取基础鉴权错误信息：
        String resultCode = (String) request.getAttribute(ApiConstants.ERROR_CODE);
        if (StringUtils.isEmpty(resultCode)) {
            resultCode = ApiConstants.SYSTEM_ERROR_STATUS;
            logger.error("resultCode is null !!!");
        }
        BufferedReader reader = null;
        InputStream is = null;
        InputStreamReader inputStreamReader = null;
        try {
            //获取传入参数
            //如果处理结果为：0-正确，则进行API调用；否则返回错误结果
            if (resultCode.equals(ApiConstants.SUCCESS_STATUS)) {
                //获取API调用URL
                String apiWholeUrl = getApiUrl(request, version, apiName);
                logger.info(apiName + ",reqeust URL is {}", apiWholeUrl);

                HttpClient httpClient = new DefaultHttpClient();
                //设置链接和读取超时
                HttpParams params = httpClient.getParams();
                ConnManagerParams.setTimeout(params, DATA_OVERTIME_DEFAULT);
                HttpConnectionParams.setConnectionTimeout(params, DATA_OVERTIME_DEFAULT);
                HttpConnectionParams.setSoTimeout(params, DATA_OVERTIME_DEFAULT);
                //调用API
                HttpGet httpGet = new HttpGet(apiWholeUrl);
                httpGet.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity responseEntity = response.getEntity();
                is = responseEntity.getContent();
                inputStreamReader = new InputStreamReader(is, "UTF-8");
                reader = new BufferedReader(inputStreamReader);

                StringBuilder messageBuffer = new StringBuilder();
                String tempString;
                while (null != (tempString = reader.readLine())) {
                    messageBuffer.append(tempString);
                }
                returnInfo = messageBuffer.toString();
            } else {
                //返回错误信息
                returnInfo = ResponseResultBuilder.getInstance().genRespsonResult2Json(resultCode, apiName + ",调用失败", null);
                logger.info(apiName + ",result errorCode {} ", resultCode);
            }
            logger.info("--------start--------[" + apiName + "] time-consuming : {}", System.currentTimeMillis() - startTime);

        } catch (Exception e) {
            logger.error("call api[" + apiName + "] exception...", e);
            returnInfo = ResponseResultBuilder.getInstance().genUnknowError2Json(e);
        } finally {
            logger.info(apiName + ",reqeust end ");

            sendJson(returnInfo, httpServletResponse);
            logger.info(apiName + ",Json is {}  ", returnInfo);


            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStreamReader);
            IOUtils.closeQuietly(is);
        }


    }

    /**
     * 发送Json串到前台
     *
     * @param json
     * @param response
     */
    private void sendJson(String json, HttpServletResponse response) {
        response.setContentType("text/plain;charset=utf-8");
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(json);
            printWriter.flush();
        } catch (Exception e) {
            logger.error("-----sendJson IOException:", e);
        }
    }

    /**
     * 获取API调用的URL(根据API参数配置信息，动态生成API调用的URL)
     *
     * @return
     */
    private String getApiUrl(HttpServletRequest httpServletRequest, String version, String apiName) {
        String apiUrl = "http://localhost:9090/restful/";
        return apiUrl + version + "/" + apiName;
    }

}
