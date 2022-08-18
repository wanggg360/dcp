package com.ht.lc.dcp.server.sys.req;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-30 16:35
 * @Version 1.0
 **/
public class LoginResult {

    String role;

    String status;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
