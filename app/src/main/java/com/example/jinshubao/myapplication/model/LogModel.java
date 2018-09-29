package com.example.jinshubao.myapplication.model;

public class LogModel {

    private Long eqTime;

    private String eqNo;

    private String storeUuid;

    private Integer eqStatus;

    private Integer eqType;

    public Long getEqTime() {
        return eqTime;
    }

    public void setEqTime(Long eqTime) {
        this.eqTime = eqTime;
    }

    public String getEqNo() {
        return eqNo;
    }

    public void setEqNo(String eqNo) {
        this.eqNo = eqNo;
    }

    public String getStoreUuid() {
        return storeUuid;
    }

    public void setStoreUuid(String storeUuid) {
        this.storeUuid = storeUuid;
    }

    public Integer getEqStatus() {
        return eqStatus;
    }

    public void setEqStatus(Integer eqStatus) {
        this.eqStatus = eqStatus;
    }

    public Integer getEqType() {
        return eqType;
    }

    public void setEqType(Integer eqType) {
        this.eqType = eqType;
    }
}
