package com.zoom.risk.operating.decision.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by liyi8 on 2017/5/30.
 * vo for echarts2 tree
 */
public class EchartsTreeVo {
    private String name;
    private Long id;
    private Long parentId;
    private Integer nodeType;
    private String symbol;
    private Integer symbolSize;
    private Map<String,Object> itemStyle;
    private List<EchartsTreeVo> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
    }

    public Map<String, Object> getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(Map<String, Object> itemStyle) {
        this.itemStyle = itemStyle;
    }

    public List<EchartsTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<EchartsTreeVo> children) {
        this.children = children;
    }
}
