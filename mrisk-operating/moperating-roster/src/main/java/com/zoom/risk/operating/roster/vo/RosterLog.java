package com.zoom.risk.operating.roster.vo;

/**
 * Created by jiangyulin on 2015/4/10.
 */
public class RosterLog extends  Roster {
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAIL = 0;
    public static final String STATUS_DESC_SUCCESS = "Successful";
    public static final String STATUS_DESC_FAIL = "Existing";

    public static final int OPER_TYPE_ADD = 1;
    public static final int OPER_TYPE_DEL = 2;

    private Integer rosterBusiType;
    private Integer status;
    private String statusDesc;
    private Integer operType;

    public RosterLog(){

    }

    public RosterLog(Integer rosterBusiType, Integer operType, Integer rosterOrigin, Integer rosterType, String content, String creator, Integer status, String statusDesc){
        super(rosterOrigin, rosterType, content, creator);
        this.operType = operType;
        this.rosterBusiType = rosterBusiType;
        this.setStatus(status);
        this.setStatusDesc(statusDesc);
    }

    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public Integer getRosterBusiType() {
        return rosterBusiType;
    }

    public void setRosterBusiType(Integer rosterBusiType) {
        this.rosterBusiType = rosterBusiType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
