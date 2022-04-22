package com.ht.lc.dcp.task.service;

import com.ht.lc.dcp.task.entity.SiteInfo;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-24 19:48
 * @Version 1.0
 **/
public interface SiteInfoService {

    /**
     * 获取有效的站点信息
     * @return 站点list
     */
    List<SiteInfo> getAllValidSiteInfos();

    /**
     * 根据dataType获取站点信息
     * @param dataType 数据类型
     * @return 站点list
     */
    List<SiteInfo> getSiteInfosByDataType(int dataType);

    /**
     * 根据机构ID获取站点信息
     * @param branchId 机构ID
     * @return 站点list
     */
    List<SiteInfo> getSiteInfosByBranchId(String branchId);
}
