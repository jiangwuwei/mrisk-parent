/**
 *
 */
package com.zoom.risk.platform.engine.api;

import java.io.Serializable;

/**
 * @author jiangyulin
 *         Nov 22, 2015
 */
public class DecisionQuota implements Serializable {
    private static final long serialVersionUID = 2353200077814689207L;
    private Long id;
    private String name;                  //指标名称
    private String quotaNo;              //策略编号
    private Object quotaValue;
    private Integer sourceType;          // 指标来源 1:数据库sql 2: redis缓存
    private Integer quotaDataType;      // 	NUMBER_1 = 1; STRING_2 = 2; DATA_TYPE_DATATIME_3 = 3; SINGLE_STRINGLIST = 4;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuotaNo() {
        return quotaNo;
    }

    public void setQuotaNo(String quotaNo) {
        this.quotaNo = quotaNo;
    }

    public Object getQuotaValue() {
        return quotaValue;
    }

    public void setQuotaValue(Object quotaValue) {
        this.quotaValue = quotaValue;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getQuotaDataType() {
        return quotaDataType;
    }

    public void setQuotaDataType(Integer quotaDataType) {
        this.quotaDataType = quotaDataType;
    }
}
