package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Message;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class DeferredMessageSendResponse extends DeferredResponse<Message>{

    {
        channelMethod = "message.send";
    }

    public DeferredMessageSendResponse(){

    }

    public DeferredMessageSendResponse(Message message){
        super(message);
    }


}
