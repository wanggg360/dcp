package com.ht.lc.dcp.server.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-09-07 09:20
 * @Version 1.0
 **/
@Entity
@Table(name = "sys_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(name = "user_name", nullable = false, length = 256)
    private String username;

    @Column(name = "password", nullable = false, length = 512)
    private String password;

    @Column(name = "pwd_update_date")
    private LocalDateTime pwdUpdateTime;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "status")
    private char status;

    @Column(name = "del_flag")
    private char deleteFlag;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "update_by", length = 64)
    private String updateBy;

    @Column(name = "create_by", length = 64)
    private String createBy;

    @Column(name = "create_type")
    private char createType;

    @Column(name = "comp_id")
    private Integer companyId;

    @Column(name = "dept_id")
    private Integer departmentId;

    @Column(name = "avatar", length = 2048)
    private String avatar;

    @Column(name = "remark", length = 512)
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getPwdUpdateTime() {
        return pwdUpdateTime;
    }

    public void setPwdUpdateTime(LocalDateTime pwdUpdateTime) {
        this.pwdUpdateTime = pwdUpdateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public char getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(char deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public char getCreateType() {
        return createType;
    }

    public void setCreateType(char createType) {
        this.createType = createType;
    }
}
