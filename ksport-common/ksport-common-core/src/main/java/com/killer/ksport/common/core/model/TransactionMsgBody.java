package com.killer.ksport.common.core.model;

import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-9-12 下午3:12
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class TransactionMsgBody {

    /**
     * 涉及业务表的主键id
     */
    private long keyId;

    /**
     * 消息的uuid
     */
    private String uuid;

    /**
     * 需要调用的接口url
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestType;

    /**
     * 请求参数
     */
    private Map<String,Object> params;

    public TransactionMsgBody(){}

    public TransactionMsgBody(long keyId, String uuid, String url, String requestType, Map<String, Object> params) {
        this.keyId = keyId;
        this.uuid = uuid;
        this.url = url;
        this.requestType = requestType;
        this.params = params;
    }

    public long getKeyId() {
        return keyId;
    }

    public void setKeyId(long keyId) {
        this.keyId = keyId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
