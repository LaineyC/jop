package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class TableLeaveRequest extends JopRequest<TableLeaveResponse> {

    @Override
    public String serviceMethod() {
        return "table.leave";
    }

    @Override
    public Class<TableLeaveResponse> responseClass() {
        return TableLeaveResponse.class;
    }

    private Integer table;

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }
}
