package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * UserLoginRequest
 *
 * @author LaineyC
 */
public class GobangStepRequest extends JopRequest<GobangStepResponse> {

    @Override
    public String serviceMethod() {
        return "gobang.step";
    }

    @Override
    public Class<GobangStepResponse> responseClass() {
        return GobangStepResponse.class;
    }

    private Integer table;

    private Integer row;

    private Integer column;


    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
