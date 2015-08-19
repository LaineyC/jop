package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Table;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class DeferredTableLeaveResponse extends DeferredResponse<Table>{

    {
        channelMethod = "table.leave";
    }

    public DeferredTableLeaveResponse(){

    }

    public DeferredTableLeaveResponse(Table table){
        super(table);
    }


}
