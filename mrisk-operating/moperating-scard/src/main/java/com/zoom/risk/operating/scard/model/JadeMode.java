package com.zoom.risk.operating.scard.model;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class JadeMode {
    public static final int MODE_TYPE_SC = 2;
    private Integer id;
    private String typeName;
    private Integer applyType;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
