package com.ht.lc.dcp.server.sys.controller;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.pojo.req.AddRolesReq;
import com.ht.lc.dcp.server.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-09-22 19:32
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/api/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @RequestMapping(value="/addRoles",method= RequestMethod.POST)
    public ResultObject addRoles(@RequestBody @Validated AddRolesReq req) {
        return permissionService.addRoles(req);
    }
}
