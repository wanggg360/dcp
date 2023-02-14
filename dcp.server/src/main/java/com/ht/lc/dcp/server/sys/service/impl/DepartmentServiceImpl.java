package com.ht.lc.dcp.server.sys.service.impl;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.dao.DepartmentDao;
import com.ht.lc.dcp.server.sys.dao.UserDao;
import com.ht.lc.dcp.server.sys.dao.UserDepartmentDao;
import com.ht.lc.dcp.server.sys.daobean.DepartmentDaoBean;
import com.ht.lc.dcp.server.sys.daobean.UserDaoBean;
import com.ht.lc.dcp.server.sys.daobean.UserDepartmentDaoBean;
import com.ht.lc.dcp.server.sys.pojo.req.AddUserReq;
import com.ht.lc.dcp.server.sys.pojo.req.LoginReq;
import com.ht.lc.dcp.server.sys.pojo.req.QueryUserDetailsReq;
import com.ht.lc.dcp.server.sys.service.DepartmentService;
import com.ht.lc.dcp.server.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:53
 * @Version 1.0
 **/

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static Logger LOG = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    UserDepartmentDao userDepartmentDao;

    @Autowired
    DepartmentDao departmentDao;



}
