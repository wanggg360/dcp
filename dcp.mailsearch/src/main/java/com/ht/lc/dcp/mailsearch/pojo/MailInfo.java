package com.ht.lc.dcp.mailsearch.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2023-06-06 13:51
 * @Version 1.0
 **/
public class MailInfo implements Serializable {

    private String id;

    private String messageId;

    private String originFileName;

    private String sendtime;

    private MailAddress ffrom;

    private String title;

    private String content;

    private String htmlContent;

    private List<MailAddress> toList;

    private List<MailAddress> ccList;

    private List<MailAttachment> attachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public MailAddress getFfrom() {
        return ffrom;
    }

    public void setFfrom(MailAddress ffrom) {
        this.ffrom = ffrom;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MailAddress> getToList() {
        return toList;
    }

    public void setToList(List<MailAddress> toList) {
        this.toList = toList;
    }

    public List<MailAddress> getCcList() {
        return ccList;
    }

    public void setCcList(List<MailAddress> ccList) {
        this.ccList = ccList;
    }

    public List<MailAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MailAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
