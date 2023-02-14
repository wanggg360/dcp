package com.ht.lc.dcp.server.sys.pojo.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-10-24 19:11
 * @Version 1.0
 **/
public class QueryUserDetailsReq extends BaseReq {

    /**
     * 用户ID
     */
    @NotEmpty
    @Size(min = 5, max = 10, message = "size must larger than 5, smaller than 10")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
