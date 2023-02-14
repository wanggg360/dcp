package com.ht.lc.dcp.server.sys.service.impl;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.dao.DepartmentDao;
import com.ht.lc.dcp.server.sys.dao.UserDao;
import com.ht.lc.dcp.server.sys.dao.UserDepartmentDao;
import com.ht.lc.dcp.server.sys.daobean.DepartmentDaoBean;
import com.ht.lc.dcp.server.sys.daobean.UserDaoBean;
import com.ht.lc.dcp.server.sys.daobean.UserDepartmentDaoBean;
import com.ht.lc.dcp.server.sys.pojo.Department;
import com.ht.lc.dcp.server.sys.pojo.User;
import com.ht.lc.dcp.server.sys.pojo.req.AddUserReq;
import com.ht.lc.dcp.server.sys.pojo.req.LoginReq;
import com.ht.lc.dcp.server.sys.pojo.req.QueryUserDetailsReq;
import com.ht.lc.dcp.server.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    UserDepartmentDao userDepartmentDao;

    @Autowired
    DepartmentDao departmentDao;

    @Override
    public ResultObject login(LoginReq req) {
        UserDaoBean userDaoBean = userDao.findByUserId(req.getUserId());
        if (Objects.isNull(userDaoBean)) {
            LOG.error("can't find such user, id: {}. ", req.getUserId());
            return ResultObject.error(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getDesc());
        }

        if (!userDaoBean.getPassword().equals(req.getPassword())) {
            LOG.error("username or password wrong. ");
            return ResultObject.error(ResultCode.USER_LOGIN_FAILED.getCode(), ResultCode.USER_LOGIN_FAILED.getDesc());
        }

        User user = new User();
        BeanUtils.copyProperties(userDaoBean, user);

        List<UserDepartmentDaoBean> userDepts = userDepartmentDao.selectByUserId(req.getUserId());

        if (!CollectionUtils.isEmpty(userDepts)) {
            List<String> deptCodes = userDepts
                    .stream()
                    .map(uddb -> uddb.getDeptCode())
                    .collect(Collectors.toList());

            List<DepartmentDaoBean> depts = departmentDao.selectByDeptCodes(deptCodes);
            if (!CollectionUtils.isEmpty(depts)) {
                List<Department> departments = depts.stream()
                        .map(ddb -> {
                            Department d = new Department();
                            BeanUtils.copyProperties(ddb, d);
                            return d; })
                        .collect(Collectors.toList());
                user.setDepartments(departments);
            }
        }
        return ResultObject.success(user);
    }

    @Override
    public ResultObject addUser(@RequestBody AddUserReq req) {
        // 校验请求参数
        if (!req.isValidRequest()) {
            LOG.error("email, mobile or createtype may wrong, please check! ");
            return ResultObject.error(ResultCode.SYS_REQ_PARAM_ERROR);
        }
        // 判断用户是否存在
        UserDaoBean userDaoBean = userDao.findByUserId(req.getUserId());
        if (!Objects.isNull(userDaoBean)) {
            LOG.info("user already exist. ");
            return ResultObject.error(ResultCode.USER_ALREADY_EXIST);
        }
        // 判断是否已经存在用户部门关系
        List<UserDepartmentDaoBean> beans = userDepartmentDao.selectByUserId(req.getUserId());
        if (!CollectionUtils.isEmpty(beans)) {
            LOG.info("user already belongs to department, please check! ");
            return ResultObject.error(ResultCode.SYS_INNER_ERROR);
        }
        // 判断请求中的部门编码是否在库中存在，如果不存在不添加
        List<UserDepartmentDaoBean> lists = new ArrayList<>();
        if (!CollectionUtils.isEmpty(req.getDepartments())) {
            for (String deptCode : req.getDepartments()) {
                DepartmentDaoBean temp = departmentDao.selectByDeptCode(deptCode);
                if (Objects.isNull(temp)) {
                    LOG.error("can not find such department, code: {}, please check!", deptCode);
                    return ResultObject.error(ResultCode.SYS_INNER_ERROR);
                }
                UserDepartmentDaoBean tempDaoBean = new UserDepartmentDaoBean();
                tempDaoBean.setDeptCode(deptCode);
                tempDaoBean.setUserId(req.getUserId());
                tempDaoBean.setCreateTime(LocalDateTime.now());
                tempDaoBean.setUpdateTime(LocalDateTime.now());
                lists.add(tempDaoBean);
            }
        }

        UserDaoBean newUser = new UserDaoBean();
        newUser.setUserId(req.getUserId());
        newUser.setFullName(req.getFullName());
        newUser.setPassword(req.getPassword());
        newUser.setEmail(req.getEmail());
        newUser.setMobile(req.getMobile());
        newUser.setRemark(req.getRemark());
        newUser.setCreateType(req.getCreateType());
        newUser.setUpdateBy("");
        newUser.setUpdateTime(LocalDateTime.now());
        newUser.setCreateBy("");
        newUser.setCreateTime(LocalDateTime.now());

        userDao.insert(newUser);
        if (!CollectionUtils.isEmpty(lists)) {
            userDepartmentDao.insertBatch(lists);
        }
        LOG.info("add user success, userid: {}. ", req.getUserId());
        return ResultObject.success("");
    }

    @Override
    public ResultObject queryUserDetails(QueryUserDetailsReq req) {

        return ResultObject.success("");
    }

}
