package com.lite.jop.demo.api.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class UserLogoutRequest extends JopRequest<UserLogoutResponse> {

    @Override
    public String serviceMethod() {
        return "user.logout";
    }

    @Override
    public Class<UserLogoutResponse> responseClass() {
        return UserLogoutResponse.class;
    }

}
