package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.daobean.MenuDaoBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:35
 * @Version 1.0
 **/
@Mapper public interface MenuDao {

    // 根据元素ID查询（非主键ID）
    MenuDaoBean selectById(String mid);

    // 根据元素ID删除（非主键ID）
    void deleteById(String mid);

    void insert(MenuDaoBean record);

    void update(MenuDaoBean record);
}
