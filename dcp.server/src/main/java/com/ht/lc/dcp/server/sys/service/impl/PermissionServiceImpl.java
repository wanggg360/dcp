package com.ht.lc.dcp.server.sys.service.impl;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.dao.RoleDao;
import com.ht.lc.dcp.server.sys.pojo.req.AddRolesReq;
import com.ht.lc.dcp.server.sys.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-11 19:51
 * @Version 1.0
 **/

@Service public class PermissionServiceImpl implements PermissionService {

    private static Logger LOG = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired RoleDao roleDao;

    public ResultObject addRoles(@RequestBody @Validated AddRolesReq req) {
        return null;
    }

}
