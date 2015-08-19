package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class PlayerLogoutRequest extends JopRequest<PlayerLogoutResponse> {

    @Override
    public String serviceMethod() {
        return "player.logout";
    }

    @Override
    public Class<PlayerLogoutResponse> responseClass() {
        return PlayerLogoutResponse.class;
    }

}
