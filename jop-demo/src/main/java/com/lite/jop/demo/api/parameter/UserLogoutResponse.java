package com.lite.jop.demo.api.parameter;

import com.lite.jop.platform.model.JopResponse;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class UserLogoutResponse extends JopResponse<String> {

    public UserLogoutResponse(){

    }

    public UserLogoutResponse(String message){
        super(message);
    }

}
