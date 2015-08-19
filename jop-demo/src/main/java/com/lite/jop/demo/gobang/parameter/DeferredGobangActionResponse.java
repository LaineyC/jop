package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Gobang;

/**
 * DeferredGobangActionResponse
 *
 * @author LaineyC
 */
public class DeferredGobangActionResponse extends DeferredResponse<Gobang>{

    {
        channelMethod = "gobang.action";
    }

    public DeferredGobangActionResponse(){

    }

    public DeferredGobangActionResponse(Gobang gobang){
        super(gobang);
    }


}
