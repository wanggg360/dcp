package com.ht.lc.dcp.task.dao;

import com.ht.lc.dcp.task.daobean.NoticeDetailsDaoBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-24 17:07
 * @Version 1.0
 **/
@Mapper
public interface NoticeDetailsDao {

    /**
     * 获取noticedetailsdaobean列表
     * @param daobean 查询条件
     * @return daobean列表
     */
    List<NoticeDetailsDaoBean> getListByDaoBean(NoticeDetailsDaoBean daobean);

    /**
     * 批量插入数据
     * @param list
     */
    void insertBatch(List<NoticeDetailsDaoBean> list);
}
