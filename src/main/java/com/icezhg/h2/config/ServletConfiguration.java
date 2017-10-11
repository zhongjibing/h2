package com.icezhg.h2.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.icezhg.h2.listener.ActiveSessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ServletConfiguration:
 *
 * @author zhongjibing 2017-10-11
 * @version 1.0
 */
@Configuration
public class ServletConfiguration {

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener(HttpSessionListener sessionListener) {
        return new ServletListenerRegistrationBean<>(sessionListener);
    }
}
