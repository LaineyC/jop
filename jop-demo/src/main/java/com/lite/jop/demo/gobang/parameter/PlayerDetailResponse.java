package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Player;
import com.lite.jop.platform.model.JopResponse;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class PlayerDetailResponse extends JopResponse<Player> {

    public PlayerDetailResponse(){

    }

    public PlayerDetailResponse(Player player){
        super(player);
    }

}
