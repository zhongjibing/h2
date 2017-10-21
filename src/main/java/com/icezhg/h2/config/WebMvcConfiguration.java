package com.icezhg.h2.config;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;

import com.icezhg.h2.common.vo.ResponseVO;
import com.icezhg.h2.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Autowired
    private void removedReturnValueHandlers(RequestMappingHandlerAdapter adapter) {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();
        for (HandlerMethodReturnValueHandler handler : adapter.getReturnValueHandlers()) {
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                RequestResponseBodyMethodProcessor processor = (RequestResponseBodyMethodProcessor) handler;
                returnValueHandlers.add(new WrappedRequestResponseBodyMethodProcessor(processor));
            } else {
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

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.configureHandlerExceptionResolvers(exceptionResolvers);
        exceptionResolvers.add((request, response, handler, ex) -> {
            LOGGER.error("{}", ex.getMessage() != null ? ex.getMessage() : "", ex);

            response.setStatus(200);
            ResponseVO responseVO = new ResponseVO();
            responseVO.setCode("0");
            responseVO.setStatus("error");
            responseVO.setMessage(ex.getMessage());
            responseVO.setData(ex.getStackTrace());
            try (ServletOutputStream stream = response.getOutputStream()) {
                stream.write(JsonUtil.toString(responseVO).getBytes());
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

            return null;
        });
    }

    @Bean
    public InternalResourceViewResolver htmlViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/html/");
        resolver.setSuffix(".html");
        resolver.setOrder(0);
        return resolver;
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/webapp/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(1);
        return resolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.viewResolver(htmlViewResolver());
        registry.viewResolver(jspViewResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/html/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

}
