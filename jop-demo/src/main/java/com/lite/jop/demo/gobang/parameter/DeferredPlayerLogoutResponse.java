package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Player;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class DeferredPlayerLogoutResponse extends DeferredResponse<Player>{

    {
        channelMethod = "player.logout";
    }

    public DeferredPlayerLogoutResponse(){

    }

    public DeferredPlayerLogoutResponse(Player player){
        super(player);
    }


}
