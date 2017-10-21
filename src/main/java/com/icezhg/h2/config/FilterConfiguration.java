package com.icezhg.h2.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * FilterConfiguration:
 *
 * @author zhongjibing 2017-10-17
 * @version 1.0
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean encodingFilter() {
        return new FilterRegistrationBean(new CharacterEncodingFilter("UTF-8"));
    }
}
