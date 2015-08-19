package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Step;

/**
 * DeferredGobangStepResponse
 *
 * @author LaineyC
 */
public class DeferredGobangStepResponse extends DeferredResponse<Step>{

    {
        channelMethod = "gobang.step";
    }

    public DeferredGobangStepResponse(){

    }

    public DeferredGobangStepResponse(Step step){
        super(step);
    }


}
