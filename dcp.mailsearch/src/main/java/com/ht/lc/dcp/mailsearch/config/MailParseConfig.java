package com.ht.lc.dcp.mailsearch.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2023-06-06 13:18
 * @Version 1.0
 **/

@ConfigurationProperties(prefix = "mail")
@Configuration
public class MailParseConfig {

    private String msgParentDir;

    private String msgAttachDir;

    private String msgResultJsonFile;

    private String emlParentDir;

    private String emlResultJsonFile;

    private String emlAttachDir;

    public String getMsgParentDir() {
        return msgParentDir;
    }

    public void setMsgParentDir(String msgParentDir) {
        this.msgParentDir = msgParentDir;
    }

    public String getMsgAttachDir() {
        return msgAttachDir;
    }

    public void setMsgAttachDir(String msgAttachDir) {
        this.msgAttachDir = msgAttachDir;
    }

    public String getMsgResultJsonFile() {
        return msgResultJsonFile;
    }

    public void setMsgResultJsonFile(String msgResultJsonFile) {
        this.msgResultJsonFile = msgResultJsonFile;
    }

    public String getEmlParentDir() {
        return emlParentDir;
    }

    public void setEmlParentDir(String emlParentDir) {
        this.emlParentDir = emlParentDir;
    }

    public String getEmlResultJsonFile() {
        return emlResultJsonFile;
    }

    public void setEmlResultJsonFile(String emlResultJsonFile) {
        this.emlResultJsonFile = emlResultJsonFile;
    }

    public String getEmlAttachDir() {
        return emlAttachDir;
    }

    public void setEmlAttachDir(String emlAttachDir) {
        this.emlAttachDir = emlAttachDir;
    }
}
