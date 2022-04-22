package com.ht.lc.dcp.task.entity;

import java.time.LocalDate;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-28 20:44
 * @Version 1.0
 **/
public class NoticeDetails {

    private String taskId;

    private Integer briefId;

    private String title;

    private String noticeObject;

    private String content;

    private LocalDate noticeDate;

    private LocalDate publishDate;

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

}
