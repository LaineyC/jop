package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class MessageSendRequest extends JopRequest<MessageSendResponse> {
    @Override
    public String serviceMethod() {
        return "message.send";
    }

    @Override
    public Class<MessageSendResponse> responseClass() {
        return MessageSendResponse.class;
    }

    private Integer from;

    private Integer to;

    private String content;


    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
