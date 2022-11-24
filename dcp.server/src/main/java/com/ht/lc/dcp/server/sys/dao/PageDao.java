package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.server.sys.daobean.PageDaoBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-29 22:35
 * @Version 1.0
 **/
@Mapper public interface PageDao {

    // 根据元素ID查询（非主键ID）
    PageDaoBean selectById(String pid);

    // 根据元素ID查询（非主键ID）
    void deleteById(String pid);

    void insert(PageDaoBean record);

    void update(PageDaoBean record);

    // 批量插入
    void insertBatch(List<PageDaoBean> records);
}