package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.daobean.UserDepartmentDaoBean;
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
public interface UserDepartmentDao {

    /**
     * 根据用户ID查询关系
     * @param userId
     * @return
     */
    List<UserDepartmentDaoBean> selectByUserId(@Param("userId") String userId);

    /**
     * 批量插入
     * @param records
     */
    void insertBatch(List<UserDepartmentDaoBean> records);
}