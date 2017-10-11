package com.icezhg.h2.config;

import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icezhg.h2.model.AccessEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * AccessInterceptor:
 *
 * @author zhongjibing 2017-10-09
 * @version 1.0
 */
public class AccessInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessInterceptor.class);

    private static final String ACCESS_ENTITY = "_access_entity";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        AccessEntity entity = new AccessEntity();
        entity.setClientIp(null);
        entity.setUri(request.getRequestURI());
        entity.setType(null);
        entity.setMethod(request.getMethod());
        entity.setParamData(null);
        entity.setRequestTime(new Date());
        entity.setSessionId(request.getSession().getId());
        request.setAttribute(ACCESS_ENTITY, entity);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        AccessEntity entity = (AccessEntity) request.getAttribute(ACCESS_ENTITY);
        entity.setReturnData(null);
        entity.setReturnTime(new Date());

        LOGGER.info(entity.toString());
    }
}
