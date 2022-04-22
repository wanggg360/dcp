package com.ht.lc.dcp.task.entity;

import java.time.LocalDate;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-23 21:36
 * @Version 1.0
 **/
public class NoticeBrief {

    private Integer id;

    private String taskId;

    private Integer dataType;

    private String branchCategory;

    private String branchId;

    private String contentUrl;

    private String title;

    private LocalDate publishDate;

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getBranchCategory() {
        return branchCategory;
    }

    public void setBranchCategory(String branchCategory) {
        this.branchCategory = branchCategory;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
