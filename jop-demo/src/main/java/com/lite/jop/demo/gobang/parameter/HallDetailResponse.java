package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.demo.gobang.domain.Hall;
import com.lite.jop.platform.model.JopResponse;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class HallDetailResponse extends JopResponse<Hall>{

    public HallDetailResponse(){

    }

    public HallDetailResponse(Hall hall){
        super(hall);
    }

}
