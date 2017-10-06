package com.icezhg.h2.config;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class ReturnValueHandlerInterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(new ReturnValueHandlerInterceptor());
        ir.addPathPatterns("/**");

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
        returnValueHandlers.add(new ReturnValueHandler());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    static class ReturnValueHandler implements HandlerMethodReturnValueHandler {

        @Override
        public boolean supportsReturnType(MethodParameter methodParameter) {
            return true;
        }

        @Override
        public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
            System.out.println(o);
            System.out.println(methodParameter);
        }
    }

    static class ReturnValueHandlerInterceptor extends HandlerInterceptorAdapter {
        public ReturnValueHandlerInterceptor() {
            super();
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
                Exception {
            return super.preHandle(request, response, handler);
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
                modelAndView) throws Exception {
            super.postHandle(request, response, handler, modelAndView);
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
                ex) throws Exception {
            super.afterCompletion(request, response, handler, ex);
        }

        @Override
        public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object
                handler) throws Exception {
            super.afterConcurrentHandlingStarted(request, response, handler);
        }
    }
}
