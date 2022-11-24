package com.ht.lc.dcp.server.sys.controller;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.pojo.req.AddUserReq;
import com.ht.lc.dcp.server.sys.pojo.req.LoginReq;
import com.ht.lc.dcp.server.sys.pojo.req.QueryUserDetailReq;
import com.ht.lc.dcp.server.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-08-11 08:53
 * @Version 1.0
 **/

@CrossOrigin @RestController @RequestMapping(value = "/api/user") public class UserController {

    @Autowired UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultObject login(@RequestBody @Validated LoginReq req) {
        return userService.login(req);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST) public ResultObject logout() {
        return ResultObject.success("");
    }

    @RequestMapping(value = "/addUser") public ResultObject addUser(@RequestBody @Validated AddUserReq req) {
        return userService.addUser(req);
    }

    @RequestMapping(value = "/queryUserDetails")
    public ResultObject queryUserDetails(@RequestBody @Validated QueryUserDetailReq req) {
        return userService.queryUserDetails(req);
    }
}
