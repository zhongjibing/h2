package com.icezhg.h2.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new AccessInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        //        super.addReturnValueHandlers(returnValueHandlers);
        returnValueHandlers.add(new JsonReturnValueHandler());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new JsonArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    //    @Bean
    //    public InternalResourceViewResolver resourceViewResolver()
    //    {
    //        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
    //        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
    //        internalResourceViewResolver.setSuffix(".jsp");
    //        return internalResourceViewResolver;
    //    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        //        registry.viewResolver(resourceViewResolver());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }
}
