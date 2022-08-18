package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.entity.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:35
 * @Version 1.0
 **/
@Mapper
public interface PageDao {

    // 根据元素ID查询（非主键ID）
    Page selectById(String pid);

    // 根据元素ID查询（非主键ID）
    void deleteById(String pid);

    void insert(Page record);

    void update(Page record);
}