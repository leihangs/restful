package com.showcase.restful.data.config;

/**
 * @author: lei hang
 * @date: 2016年03月03日
 * @description:
 */

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

public class SystemInitListener extends ContextLoaderListener {

    private ApplicationContext ctx;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //加载Spring context配置文件
        super.contextInitialized(event);
        //获取上下文操作的句柄
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
    }


}