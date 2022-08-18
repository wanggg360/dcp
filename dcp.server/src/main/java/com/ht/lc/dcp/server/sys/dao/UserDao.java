package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.entity.User;
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
@Mapper
public interface UserDao {

    User findByUserId(@Param("userId") String userId);

    List<User> findUser(User cond);

    void insert(User record);

    void insertBatch(List<User> records);

}
