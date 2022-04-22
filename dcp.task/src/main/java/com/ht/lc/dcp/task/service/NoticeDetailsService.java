package com.ht.lc.dcp.task.service;

import com.ht.lc.dcp.task.entity.NoticeBrief;
import com.ht.lc.dcp.task.entity.SiteInfo;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-04-02 17:20
 * @Version 1.0
 **/
public interface NoticeDetailsService {

    /**
     * 根据公告简介增加公告详情
     * @param briefs
     */
    void addNoticeDetailsByBriefs(List<NoticeBrief> briefs);
}
