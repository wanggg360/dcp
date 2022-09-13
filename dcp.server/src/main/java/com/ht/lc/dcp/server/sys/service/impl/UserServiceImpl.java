package com.ht.lc.dcp.server.sys.service.impl;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.dao.UserDao;
import com.ht.lc.dcp.server.sys.daobean.UserDaoBean;
import com.ht.lc.dcp.server.sys.req.LoginReq;
import com.ht.lc.dcp.server.sys.req.LoginResult;
import com.ht.lc.dcp.server.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:53
 * @Version 1.0
 **/

@Service
public class UserServiceImpl implements UserService {

    private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    public ResultObject<LoginResult> login(LoginReq req) {
        LoginResult result = new LoginResult();
        UserDaoBean userDaoBean = userDao.findByUserId(req.getUserId());
        if (Objects.isNull(userDaoBean)) {
            LOG.error("can't find such user, id: {}. ", req.getUserId());
            return ResultObject.error(ResultCode.AUTH_USER_NOT_EXIST.getCode(),
                    ResultCode.AUTH_USER_NOT_EXIST.getDesc());
        }

        if (!userDaoBean.getPassword().equals(req.getPassword())) {
            LOG.error("username or password wrong. ");
            return ResultObject.error(ResultCode.AUTH_WRONG_PASSWD.getCode(),
                    ResultCode.AUTH_WRONG_PASSWD.getDesc());
        }
        result.setRole("admin");
        result.setStatus("ok");
        return ResultObject.success(result);
    }
}
