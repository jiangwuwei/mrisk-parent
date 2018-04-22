package com.zoom.risk.operating.roster.vo;

import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class QueryResult<T> {
    private List<T> list;
    private Integer totalCount;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
