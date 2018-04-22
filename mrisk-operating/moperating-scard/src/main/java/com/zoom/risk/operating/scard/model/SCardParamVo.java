package com.zoom.risk.operating.scard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class SCardParamVo {
    private String typeName;
    private Integer typeId;
    private List<Object> paramList;

    public SCardParamVo(Integer typeId, String typeName){
        this.typeId = typeId;
        this.typeName = typeName;
        paramList = new ArrayList<>();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public List<Object> getParamList() {
        return paramList;
    }

    public void add(Object scardParam) {
        paramList.add(scardParam);
    }
}
