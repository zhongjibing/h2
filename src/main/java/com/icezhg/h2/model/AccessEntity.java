package com.icezhg.h2.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * AccessEntity:
 *
 * @author zhongjibing 2017-10-09
 * @version 1.0
 */
public class AccessEntity implements Serializable{
    private static final long serialVersionUID = 1723466298198733112L;



    private static String id;
    private String clientIp;
    private String uri;
    private String type;
    private String method;
    private String paramData;
    private String returnData;
    private String sessionId;
    private Date requestTime;
    private Date returnTime;



    static {
        id = "";

        InputStream is = null;
        try {
            is = new FileInputStream("");
            ///
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }

        }

        try (InputStream is2 = new FileInputStream("")) {
            int read = is2.read(new byte[]{});
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AccessEntity() {
        id = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }
}
