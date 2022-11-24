package com.ht.lc.dcp.server.sys.daobean;

import java.time.LocalDateTime;

public class UserRoleDaoBean {

    /**
     * 主键Id
     */
    private Long id;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String rid;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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