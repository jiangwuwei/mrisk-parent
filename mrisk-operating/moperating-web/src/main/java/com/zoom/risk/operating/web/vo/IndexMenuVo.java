package com.zoom.risk.operating.web.vo;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by liyi8 on 2017/6/12.
 */
public class IndexMenuVo {

    private Long id;

    private String name;

    private String cssName;

    private String url;

    private List<IndexMenuVo> children;

    public IndexMenuVo(Long id, String name, String cssName, String url) {
        this.id = id;
        this.name = name;
        this.cssName = cssName;
        this.url = url;
    }

    public void addChild(IndexMenuVo vo){
        if(children == null){
            children = Lists.newArrayList();
        }
        children.add(vo);
    }

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

    public String getCssName() {
        return cssName;
    }

    public void setCssName(String cssName) {
        this.cssName = cssName;
    }

    public List<IndexMenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<IndexMenuVo> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
