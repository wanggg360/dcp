package com.ht.lc.dcp.task.dao;

import com.ht.lc.dcp.task.daobean.SiteInfoDaoBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-10 19:52
 * @Version 1.0
 **/

@Mapper public interface SiteInfoDao {

    /**
     * 获取siteinfodaobean列表
     *
     * @param daobean 查询条件bean
     * @return daobean列表
     */
    List<SiteInfoDaoBean> getListByDaoBean(SiteInfoDaoBean daobean);

}
