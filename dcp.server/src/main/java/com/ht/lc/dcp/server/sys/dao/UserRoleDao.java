package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:35
 * @Version 1.0
 **/
@Mapper
public interface UserRoleDao {

    void deleteByUserId(String userId);

    void insertBatch(List<UserRole> records);

    UserRole selectByUserId(String userId);
}