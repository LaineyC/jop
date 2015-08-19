package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Player;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class DeferredPlayerLoginResponse extends DeferredResponse<Player>{

    {
        channelMethod = "player.login";
    }

    public DeferredPlayerLoginResponse(){

    }

    public DeferredPlayerLoginResponse(Player player){
        super(player);
    }


}
