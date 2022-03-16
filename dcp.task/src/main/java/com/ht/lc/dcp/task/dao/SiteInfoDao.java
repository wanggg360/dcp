package com.ht.lc.dcp.task.dao;

import com.ht.lc.dcp.task.daobean.SiteInfoDaoBean;
import com.ht.lc.dcp.task.entity.pubinfo.SiteInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-10 19:52
 * @Version 1.0
 **/

@Mapper
public interface SiteInfoDao {

    List<SiteInfo> getSiteInfos(SiteInfoDaoBean siteInfoDaoBean);

}
