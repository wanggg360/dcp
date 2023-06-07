package com.ht.lc.dcp.mailsearch.pojo;

import java.io.Serializable;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2023-06-06 13:52
 * @Version 1.0
 **/
public class MailAddress implements Serializable {

    private String name;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
