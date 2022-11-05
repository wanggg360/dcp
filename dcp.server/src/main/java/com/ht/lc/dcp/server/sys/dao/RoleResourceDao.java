package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.daobean.RoleResourceDaoBean;
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
public interface RoleResourceDao {

    RoleResourceDaoBean selectByRoleId(String rid);

    void deleteByRoleId(String rid);

    void insertBatch(List<RoleResourceDaoBean> records);
}