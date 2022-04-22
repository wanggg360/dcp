package com.ht.lc.dcp.task.daobean;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-28 20:44
 * @Version 1.0
 **/
public class NoticeDetailsDaoBean {

    private Integer id;

    private String taskId;

    private Integer briefId;

    private String title;

    private String noticeObject;

    private String content;

    private LocalDate noticeDate;

    private LocalDate publishDate;

    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getBriefId() {
        return briefId;
    }

    public void setBriefId(Integer briefId) {
        this.briefId = briefId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoticeObject() {
        return noticeObject;
    }

    public void setNoticeObject(String noticeObject) {
        this.noticeObject = noticeObject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(LocalDate noticeDate) {
        this.noticeDate = noticeDate;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
