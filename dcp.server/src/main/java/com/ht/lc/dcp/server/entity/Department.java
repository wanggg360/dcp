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
@Table(name = "sys_department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "dept_code", nullable = false, length = 32)
    private String departmentCode;

    @Column(name = "dept_name", nullable = false, length = 256)
    private String departmentName;

    @Column(name = "dept_level", nullable = false)
    private char departmentLevel;

    @Column(name = "dept_type", nullable = false)
    private char departmentType;

    @Column(name = "dept_manager_id", length = 64)
    private String deptManagerId;

    @Column(name = "dept_manager_name", length = 256)
    private String deptManagerName;

    @Column(name = "parent_dept_code", length = 32)
    private String parentDeptCode;

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

    @Column(name = "remark", length = 512)
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public char getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(char departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    public char getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(char departmentType) {
        this.departmentType = departmentType;
    }

    public String getDeptManagerId() {
        return deptManagerId;
    }

    public void setDeptManagerId(String deptManagerId) {
        this.deptManagerId = deptManagerId;
    }

    public String getDeptManagerName() {
        return deptManagerName;
    }

    public void setDeptManagerName(String deptManagerName) {
        this.deptManagerName = deptManagerName;
    }

    public String getParentDeptCode() {
        return parentDeptCode;
    }

    public void setParentDeptCode(String parentDeptCode) {
        this.parentDeptCode = parentDeptCode;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
