package com.zoom.risk.operating.ruleconfig.vo;

/**
 * Created by liyi8 on 2017/3/13.
 * condition页面的场景配置属性vo
 */
public class ConditionSceneConfigVo {
    private String chineseName;
    private String englishName;
    private String dbTypeName;

    public ConditionSceneConfigVo(String chineseName, String englishName, String dbTypeName){
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.dbTypeName = dbTypeName;
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

    public String getDbTypeName() {
        return dbTypeName;
    }

    public void setDbTypeName(String dbTypeName) {
        this.dbTypeName = dbTypeName;
    }

}
