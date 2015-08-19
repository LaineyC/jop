package com.lite.jop.demo.api.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class UserLoginRequest  extends JopRequest<UserLoginResponse> {


    @Override
    public String serviceMethod() {
        return "user.login";
    }

    @Override
    public Class<UserLoginResponse> responseClass() {
        return UserLoginResponse.class;
    }

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
