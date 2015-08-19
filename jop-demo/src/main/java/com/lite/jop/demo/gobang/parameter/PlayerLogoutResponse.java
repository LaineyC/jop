package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopResponse;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class PlayerLogoutResponse extends JopResponse<String> {

    public PlayerLogoutResponse(){

    }

    public PlayerLogoutResponse(String message){
        super(message);
    }

}
