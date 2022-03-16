package com.ht.lc.dcp.task.daobean;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-11 14:55
 * @Version 1.0
 **/
public class SiteInfoDaoBean {

    private Integer dataType;

    private String branchId;

    private String hostname;

    private String url;

    private String gatherType;

    private String isValid;

    public SiteInfoDaoBean() {
    }

    public SiteInfoDaoBean(Integer dataType, String branchId, String hostname, String url, String gatherType, String isValid) {
        this.dataType = dataType;
        this.branchId = branchId;
        this.hostname = hostname;
        this.url = url;
        this.gatherType = gatherType;
        this.isValid = isValid;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGatherType() {
        return gatherType;
    }

    public void setGatherType(String gatherType) {
        this.gatherType = gatherType;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
