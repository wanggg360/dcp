package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.daobean.RoleDaoBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:35
 * @Version 1.0
 **/
@Mapper
public interface RoleDao {

    void deleteById(String rid);

    void insert(RoleDaoBean record);

    // 根据元素ID查询（非主键ID）
    RoleDaoBean selectById(String rid);

    void update(RoleDaoBean record);
}