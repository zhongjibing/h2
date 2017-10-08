package com.icezhg.h2.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private void removedReturnValueHandlers(RequestMappingHandlerAdapter adapter) {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();
        for (HandlerMethodReturnValueHandler handler : adapter.getReturnValueHandlers()) {
            if (!(handler instanceof RequestResponseBodyMethodProcessor)) {
                returnValueHandlers.add(handler);
            }
        }
        adapter.setReturnValueHandlers(returnValueHandlers);
    }

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
