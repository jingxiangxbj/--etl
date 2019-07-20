package com.jingxiang.datachange.entity;

public class ETLDetailInfo {
    private Integer id;
    private Long callTime;
    private String status;
    private String stage;
    private String detail;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCallTime() {
        return callTime;
    }

    public void setCallTime(Long callTime) {
        this.callTime = callTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "etlInfo{" +
                "id=" + id +
                ", callTime=" + callTime +
                ", status='" + status + '\'' +
                ", stage='" + stage + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
