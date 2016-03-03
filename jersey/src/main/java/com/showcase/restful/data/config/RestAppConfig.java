package com.showcase.restful.data.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * @author: lei hang
 * @date: 2016年03月02日
 * @description:
 */
public class RestAppConfig extends ResourceConfig {
    public RestAppConfig() {
        register(RequestContextFilter.class);
    }

}
