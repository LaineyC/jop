package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class TableReadyRequest extends JopRequest<TableReadyResponse> {

    @Override
    public String serviceMethod() {
        return "table.ready";
    }

    @Override
    public Class<TableReadyResponse> responseClass() {
        return TableReadyResponse.class;
    }

    private Integer table;

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }
}
