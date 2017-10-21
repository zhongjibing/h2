package com.icezhg.h2.config;

import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * FileTypeInterceptor:
 *
 * @author zhongjibing 2017-10-16
 * @version 1.0
 */
public class FileTypeInterceptor implements HandlerInterceptor {

    private Properties properties;

    private HandlerInterceptor  handlerInterceptor;

    public FileTypeInterceptor(Properties properties) {
        this.properties = properties;
        this.handlerInterceptor = new UploadFileInterceptor(properties);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public static class UploadFileInterceptor implements HandlerInterceptor {

        private Properties properties;

        public UploadFileInterceptor(Properties properties) {
            this.properties = properties;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            return false;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        }
    }
}
