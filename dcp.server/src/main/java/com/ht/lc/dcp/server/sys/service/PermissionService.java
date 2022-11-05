package com.ht.lc.dcp.server.sys.service;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.pojo.req.AddRolesReq;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-11 19:50
 * @Version 1.0
 **/
public interface PermissionService {

    /**
     * 添加角色
     *
     * @param req
     * @return
     */
    ResultObject addRoles(AddRolesReq req);
}
