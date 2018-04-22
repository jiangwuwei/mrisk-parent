package com.zoom.risk.operating.web.model;

import com.zoom.risk.operating.web.vo.MenuVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu {
    private Long id;

    private Long parentId;

    private String menuName;

    private String menuCode;

    private String menuUrl;

    private Integer menuOrder;

    private String display;

    private String iconcls;

    private Date createDate;

    private Date modifiedDate;

    private List<Menu> children;

    public List<Menu> getChildren() {
        return children;
    }

    public void addChild(Menu child) {
        if ( children == null ){
            children = new ArrayList<>();
        }
        children.add(child);
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display == null ? null : display.trim();
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls == null ? null : iconcls.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public MenuVo copyOne(){
        MenuVo vo = new MenuVo();
        vo.setId(this.getId());
        vo.setName(this.getMenuName());
        vo.setIconSkin(this.getIconcls());
        vo.setpId(this.getParentId());
        vo.setFile(this.getMenuUrl());
        return vo;
    }
}