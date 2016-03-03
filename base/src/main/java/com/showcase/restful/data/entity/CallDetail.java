package com.showcase.restful.data.entity;

import java.util.Date;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description: 调用记录信息
 */

public class CallDetail {
    //话单ID
    private int callId;
    //调用方ID
    private String appId;
    //卡号
    private String cardId;
    //错误码。00：成功，其他表示失败
    private String resultCode;
    //调用时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //插入时间
    private Date createTime;
    //消息序列号
    private String seqId;

    public int getCallId() {
        return callId;
    }

    public void setCallId(int callId) {
        this.callId = callId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }
}
