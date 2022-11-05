package com.ht.lc.dcp.server.sys.service;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.pojo.req.AddUserReq;
import com.ht.lc.dcp.server.sys.pojo.req.LoginReq;
import com.ht.lc.dcp.server.sys.pojo.req.QueryUserDetailReq;
import com.ht.lc.dcp.server.sys.pojo.req.QueryUsersReq;
import com.ht.lc.dcp.server.sys.pojo.User;

import java.util.List;

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
    ResultObject login(LoginReq req);

    /**
     * 添加用户
     * @param req
     * @return
     */
    ResultObject addUser(AddUserReq req);

    /**
     * 查询用户详情
     * @param req
     * @return
     */
    ResultObject queryUserDetails(QueryUserDetailReq req);
}
