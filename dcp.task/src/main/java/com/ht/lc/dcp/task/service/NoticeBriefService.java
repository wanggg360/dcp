package com.ht.lc.dcp.task.service;

import com.ht.lc.dcp.task.daobean.NoticeBriefDaoBean;
import com.ht.lc.dcp.task.entity.NoticeBrief;
import com.ht.lc.dcp.task.entity.SiteInfo;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-24 19:17
 * @Version 1.0
 **/
public interface NoticeBriefService {

    /**
     * 增加公告简介
     * @param siteInfo
     * @param taskId
     * @return
     */
    int addNoticeBriefBySiteInfo(SiteInfo siteInfo, String taskId);

    /**
     * 根据任务ID获取公告简介
     * @param taskId
     * @return 公告简介list
     */
    List<NoticeBrief> getNoticeBriefsByTaskId(String taskId);

    /**
     * 根据条件查询noticebrief
     * @param taskId
     * @param startDate
     * @param endDate
     * @return
     */
    List<NoticeBrief> getNoticeBriefsByDateRange(String taskId, String branchId, String startDate, String endDate);

    /**
     * 根据条件查询noticebrief
     * @param daoBean
     * @return
     */
    List<NoticeBrief> getNoticeBriefsByDaoBean(NoticeBriefDaoBean daoBean);
}
