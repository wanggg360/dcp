package com.ht.lc.dcp.server.sys.controller;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.req.LoginReq;
import com.ht.lc.dcp.server.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-08-11 08:53
 * @Version 1.0
 **/

@CrossOrigin
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public ResultObject login(@RequestBody LoginReq req) {

        //Cookie cookie = new Cookie("login","018208");
        //response.addCookie(cookie);
        return userService.login(req);

    }
}
