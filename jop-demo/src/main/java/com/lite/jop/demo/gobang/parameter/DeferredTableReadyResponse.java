package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Table;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class DeferredTableReadyResponse extends DeferredResponse<Table>{

    {
        channelMethod = "table.ready";
    }

    public DeferredTableReadyResponse(){

    }

    public DeferredTableReadyResponse(Table table){
        super(table);
    }


}
