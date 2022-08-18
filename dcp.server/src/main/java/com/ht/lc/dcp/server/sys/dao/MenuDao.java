package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:35
 * @Version 1.0
 **/
@Mapper
public interface MenuDao {

    // 根据元素ID查询（非主键ID）
    Menu selectById(String mid);

    // 根据元素ID删除（非主键ID）
    void deleteById(String mid);

    void insert(Menu record);

    void update(Menu record);
}
