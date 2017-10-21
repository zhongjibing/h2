package com.icezhg.h2.config;

import com.icezhg.h2.common.vo.ResponseVO;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * WrappedRequestResponseBodyMethodProcessor:
 *
 * @author zhongjibing 2017-10-22
 * @version 1.0
 */
public class WrappedRequestResponseBodyMethodProcessor implements HandlerMethodReturnValueHandler {

    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

    public WrappedRequestResponseBodyMethodProcessor(RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor) {
        this.requestResponseBodyMethodProcessor = requestResponseBodyMethodProcessor;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return requestResponseBodyMethodProcessor.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        ResponseVO data = new ResponseVO();
        data.setData(returnValue);
        requestResponseBodyMethodProcessor.handleReturnValue(data, returnType, mavContainer, webRequest);
    }
}
