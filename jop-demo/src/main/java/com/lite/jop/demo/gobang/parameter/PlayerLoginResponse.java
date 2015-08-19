package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopResponse;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class PlayerLoginResponse extends JopResponse<String> {

    public PlayerLoginResponse(){

    }

    public PlayerLoginResponse(String session){
        super(session);
    }

}
