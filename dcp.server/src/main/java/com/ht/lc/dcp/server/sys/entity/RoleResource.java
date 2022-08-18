package com.ht.lc.dcp.server.sys.entity;


import java.time.LocalDateTime;

public class RoleResource {

    private Long id;
    /**
     * 角色ID
     */
    private String rid;

    /**
     * 资源ID
     */
    private String resId;

    /**
     * 资源类型 1：菜单 2：页面 3：组件
     */
    private String resType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}