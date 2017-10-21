package com.icezhg.h2.common.vo;

import java.io.Serializable;

/**
 * ResponseVO:
 *
 * @author zhongjibing 2017-10-09
 * @version 1.0
 */
public class ResponseVO implements Serializable {
    private static final long serialVersionUID = -5261492218850783208L;

    private String code = "";
    private String status = "success";
    private String message = "";
    private Object data;

    public ResponseVO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
