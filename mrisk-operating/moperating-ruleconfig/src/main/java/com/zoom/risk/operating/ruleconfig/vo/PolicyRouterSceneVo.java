package com.zoom.risk.operating.ruleconfig.vo;

/**
 * Created by liyi8 on 2017/6/28.
 */
public class PolicyRouterSceneVo {
    private String chineseName;
    private String englishName;
    private int dbType;

    public PolicyRouterSceneVo(String chineseName, String englishName, int dbType) {
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.dbType = dbType;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }
}
