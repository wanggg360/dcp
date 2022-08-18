package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.entity.PageComponent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:35
 * @Version 1.0
 **/
@Mapper
public interface PageComponentDao {

    // 根据元素ID查询（非主键ID）
    PageComponent selectById(String cid);

    // 根据元素ID删除（非主键ID）
    void deleteById(String cid);

    void insert(PageComponent record);

    void update(PageComponent record);
}