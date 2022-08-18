package com.ht.lc.dcp.server.sys.service;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.req.LoginReq;
import com.ht.lc.dcp.server.sys.req.LoginResult;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:51
 * @Version 1.0
 **/
public interface UserService {

    /**
     * 登陆
     * @param req
     * @return
     */
    ResultObject<LoginResult> login(LoginReq req);
}
