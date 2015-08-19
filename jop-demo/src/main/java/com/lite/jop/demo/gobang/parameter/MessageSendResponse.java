package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Message;
import com.lite.jop.platform.model.JopResponse;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class MessageSendResponse extends JopResponse<Message> {

    public MessageSendResponse(){

    }

    public MessageSendResponse(Message message){
        super(message);
    }

}
