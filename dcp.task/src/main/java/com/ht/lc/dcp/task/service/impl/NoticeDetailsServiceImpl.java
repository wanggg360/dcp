package com.ht.lc.dcp.task.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ht.lc.dcp.common.constants.ComConstant;
import com.ht.lc.dcp.task.dao.NoticeDetailsDao;
import com.ht.lc.dcp.task.entity.NoticeBrief;
import com.ht.lc.dcp.task.entity.NoticeDetails;
import com.ht.lc.dcp.task.service.AsyncService;
import com.ht.lc.dcp.task.service.NoticeDetailsService;
import com.ht.lc.dcp.task.utils.ComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-04-02 17:21
 * @Version 1.0
 **/
@Service
public class NoticeDetailsServiceImpl implements NoticeDetailsService {

    private static Logger LOG = LoggerFactory.getLogger(NoticeDetailsServiceImpl.class);

    @Autowired
    AsyncService asyncService;

    @Autowired
    NoticeDetailsDao noticeDetailsDao;

    @Override
    public void addNoticeDetailsByBriefs(List<NoticeBrief> briefs) {
        if (CollectionUtils.isEmpty(briefs)) {
            LOG.error("noticebriefs list empty, will return. ");
            return;
        }
        List<NoticeDetails> details = new ArrayList<>(2);
        List<List<NoticeBrief>> resultList = ComUtils.splitList(briefs, ComConstant.Number.NUM_100);
        CountDownLatch latch = new CountDownLatch(resultList.size());
        resultList.stream().forEach(l -> asyncService.addNoticeDetails(l, latch));
        try {
            latch.await();
        } catch (InterruptedException e) {
            LOG.error("await latch error. ");
        }
        LOG.info("finish add all need notice details. ");
    }
}
