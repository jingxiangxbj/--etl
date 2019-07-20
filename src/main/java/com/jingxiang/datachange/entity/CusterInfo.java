package com.jingxiang.datachange.entity;

import lombok.Data;

@Data
public class CusterInfo {
    private String clusterName;
    private Integer nodesCount;
    private Integer indicesCount;
    private String clusterStatus;
    private String osmemUsed;
    private String osmemTotal;
    private String osmemFree;
    private Integer osmemFreePercent;
    private Integer osmemUsedPercent;
    private String esmemUsed;
    private String esmemTotal;
    private Integer cpuPercent;
    private Integer docsCount;

}
