package com.zoom.risk.platform.scard.mode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class SCardPolicies {
    private String sceneNo;
    private String name;
    private Set<SCardRouter> routerSet;
    private List<SCard> scardList;


    public SCardPolicies(){
        routerSet = new HashSet<>();
        scardList = new ArrayList<>();
    }
    public List<SCard> getScardList() {
        return scardList;
    }

    public void addScard(SCard scard){
        scardList.add(scard);
    }

    public Set<SCardRouter> getRouterSet() {
        return routerSet;
    }

    public void addScardRouter(SCardRouter scardRouter) {
        routerSet.add(scardRouter);
    }

    public String getSceneNo() {
        return sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo == null ? null : sceneNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "SCardPolicies{" +
                "sceneNo='" + sceneNo + '\'' +
                ", name='" + name + '\'' +
                ", routerSet=" + routerSet +
                ", scardList=" + scardList +
                '}';
    }
}