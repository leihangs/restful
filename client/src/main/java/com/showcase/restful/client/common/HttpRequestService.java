package com.showcase.restful.client.common;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author: lei hang
 * @date: 2016年03月02日
 * @description:
 * 目前实现get，post方式
 */
@Service
public class HttpRequestService {

    private final Logger logger = LoggerFactory.getLogger(HttpRequestService.class);

    /**
     * 发送get请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String sendHttpGet(String url) throws IOException {
        logger.info(url);
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        InputStream is = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        String jsonStr = null;
        HttpResponse response = client.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                is = response.getEntity().getContent();
                inputStreamReader = new InputStreamReader(is, "UTF-8");
                reader = new BufferedReader(inputStreamReader);
                jsonStr = getResultMessage(reader);
            } catch (Exception e) {
                logger.error("sendHttpGet Exception:", e);
            } finally {
                IOUtils.closeQuietly(reader);
                IOUtils.closeQuietly(inputStreamReader);
                IOUtils.closeQuietly(is);
            }
        } else {
            logger.error("请求{}失败，状态码{}" ,url,response.getStatusLine().getStatusCode());
        }
        logger.info(jsonStr);
        return jsonStr;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public String sendHttpPost(String url, List<NameValuePair> params) throws IOException {
        logger.info(url);
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        InputStream is = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        String jsonStr = null;
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                is = response.getEntity().getContent();
                inputStreamReader = new InputStreamReader(is, "UTF-8");
                reader = new BufferedReader(inputStreamReader);
                jsonStr = getResultMessage(reader);
            } catch (Exception e) {
                logger.error("sendHttpPost Exception:", e);
            } finally {
                IOUtils.closeQuietly(reader);
                IOUtils.closeQuietly(inputStreamReader);
                IOUtils.closeQuietly(is);
            }
        } else {
            logger.error("请求{}失败，状态码{}", url, response.getStatusLine().getStatusCode());
        }
        logger.info(jsonStr);
        return jsonStr;
    }

    /**
     * 获取返回结果
     *
     * @param reader reader
     * @return string 请求响应结果
     * @throws IOException
     */
    private String getResultMessage(BufferedReader reader) throws IOException {
        StringBuilder messageBuffer = new StringBuilder();
        String tempString;
        while (null != (tempString = reader.readLine())) {
            messageBuffer.append(tempString);
        }
        return messageBuffer.toString();
    }

}
