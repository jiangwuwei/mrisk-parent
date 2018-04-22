package com.zoom.risk.operating.scard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class JadeModeVo {
    private Integer id;
    private String typeName;
    private List<JadeModeDefinition> listDefinitions;

    public void addJadeModeDefinition(JadeModeDefinition jadeModeDefinition){
        if ( listDefinitions == null ){
            listDefinitions = new ArrayList<>();
        }
        listDefinitions.add(jadeModeDefinition);
    }


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

    public List<JadeModeDefinition> getListDefinitions() {
        return listDefinitions;
    }

    public void setListDefinitions(List<JadeModeDefinition> listDefinitions) {
        this.listDefinitions = listDefinitions;
    }
}
