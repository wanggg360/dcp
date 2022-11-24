package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.daobean.UserDaoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:35
 * @Version 1.0
 **/
@Mapper public interface UserDao {

    UserDaoBean findByUserId(@Param("userId") String userId);

    List<UserDaoBean> findUser(UserDaoBean cond);

    void insert(UserDaoBean record);

    void insertBatch(List<UserDaoBean> records);
}
