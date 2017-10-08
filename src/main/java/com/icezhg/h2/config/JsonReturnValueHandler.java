package com.icezhg.h2.config;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.icezhg.h2.common.vo.ResponseVO;
import com.icezhg.h2.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * JsonReturnValueHandler:
 *
 * @author zhongjibing 2017-10-09
 * @version 1.0
 */
public class JsonReturnValueHandler implements HandlerMethodReturnValueHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonReturnValueHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(returnValue);
        try (ServletOutputStream stream = response.getOutputStream()) {
            stream.write(JsonUtil.toString(responseVO).getBytes());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }
}
