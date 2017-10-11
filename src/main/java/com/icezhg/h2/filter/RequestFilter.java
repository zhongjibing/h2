package com.icezhg.h2.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * RequestFilter:
 *
 * @author zhongjibing 2017-10-11
 * @version 1.0
 */
@Component
@WebFilter(filterName = "requestFilter", urlPatterns = "/*")
public class RequestFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("------ request filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("------ request filter dofilter");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.info("------ request filter destory");
    }
}
