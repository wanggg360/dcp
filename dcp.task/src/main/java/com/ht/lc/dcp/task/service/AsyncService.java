package com.ht.lc.dcp.task.service;

import com.ht.lc.dcp.task.entity.NoticeBrief;
import com.ht.lc.dcp.task.entity.NoticeDetails;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CountedCompleter;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-28 09:29
 * @Version 1.0
 **/
public interface AsyncService {

    /**
     * 异步方法获取公告简介
     * @param url
     * @param dataType
     * @return
     */
    CompletableFuture<List<NoticeBrief>> getNoticeBriefByPageUrl(String url, int dataType);

    /**
     * 获取公告详情列表
     * @param noticeBriefs
     * @return 公告详情列表
     */
    void addNoticeDetails(List<NoticeBrief> noticeBriefs, CountDownLatch latch);
}
