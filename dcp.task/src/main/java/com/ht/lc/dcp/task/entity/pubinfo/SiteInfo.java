package com.ht.lc.dcp.task.entity.pubinfo;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-07 18:00
 * @Version 1.0
 **/
public class SiteInfo {

    private Integer id;

    private Integer dataType;

    private String branchId;

    private String branchName;

    private String hostname;

    private String url;

    private String siteMenu;

    private String gatherType;

    private String gatherPeriod;

    private String remark;

    private String isValid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public String getSiteMenu() {
        return siteMenu;
    }

    public void setSiteMenu(String siteMenu) {
        this.siteMenu = siteMenu;
    }

    public String getGatherType() {
        return gatherType;
    }

    public void setGatherType(String gatherType) {
        this.gatherType = gatherType;
    }

    public String getGatherPeriod() {
        return gatherPeriod;
    }

    public void setGatherPeriod(String gatherPeriod) {
        this.gatherPeriod = gatherPeriod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
