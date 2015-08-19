package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class HallDetailRequest extends JopRequest<HallDetailResponse> {
    @Override
    public String serviceMethod() {
        return "hall.detail";
    }

    @Override
    public Class<HallDetailResponse> responseClass() {
        return HallDetailResponse.class;
    }

}
