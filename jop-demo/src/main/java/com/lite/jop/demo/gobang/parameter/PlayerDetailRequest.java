package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class PlayerDetailRequest extends JopRequest<PlayerDetailResponse> {

    @Override
    public String serviceMethod() {
        return "player.detail";
    }

    @Override
    public Class<PlayerDetailResponse> responseClass() {
        return PlayerDetailResponse.class;
    }

}
