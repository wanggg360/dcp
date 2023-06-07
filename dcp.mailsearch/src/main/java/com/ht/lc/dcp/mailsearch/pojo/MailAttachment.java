package com.ht.lc.dcp.mailsearch.pojo;

import java.io.Serializable;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2023-06-06 13:54
 * @Version 1.0
 **/
public class MailAttachment implements Serializable {

    private String filename;

    private String uri;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
