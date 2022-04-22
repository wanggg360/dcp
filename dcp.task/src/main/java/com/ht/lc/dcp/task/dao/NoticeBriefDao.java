package com.ht.lc.dcp.task.dao;

import com.ht.lc.dcp.task.daobean.NoticeBriefDaoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-24 17:07
 * @Version 1.0
 **/
@Mapper
public interface NoticeBriefDao {

    /**
     * 获取noticebriefdaobean列表
     * @param daobean 查询条件
     * @return list
     */
    List<NoticeBriefDaoBean> getListByDaoBean(NoticeBriefDaoBean daobean);

    /**
     * 批量插入列表
     * @param list
     */
    void insertBatch(List<NoticeBriefDaoBean> list);
}
