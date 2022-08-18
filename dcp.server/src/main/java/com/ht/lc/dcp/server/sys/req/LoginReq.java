package com.ht.lc.dcp.server.sys.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-31 18:50
 * @Version 1.0
 **/
public class LoginReq extends BaseReq {

    @NotBlank
    @Size(min = 5, max = 10, message = "size must larger than 5, smaller than 10")
    private String userId;

    @NotBlank
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
