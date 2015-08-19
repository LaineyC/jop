package com.lite.jop.demo.api.parameter;

import com.lite.jop.platform.model.JopResponse;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class UserLoginResponse extends JopResponse<String> {

    public UserLoginResponse(){

    }

    public UserLoginResponse(String session){
        super(session);
    }

}
