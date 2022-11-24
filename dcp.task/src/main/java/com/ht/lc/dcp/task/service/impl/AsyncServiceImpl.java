package com.ht.lc.dcp.task.service.impl;

import com.ht.lc.dcp.common.http.HttpClientManager;
import com.ht.lc.dcp.task.dao.NoticeDetailsDao;
import com.ht.lc.dcp.task.daobean.NoticeDetailsDaoBean;
import com.ht.lc.dcp.task.entity.NoticeBrief;
import com.ht.lc.dcp.task.entity.NoticeDetails;
import com.ht.lc.dcp.task.service.AsyncService;
import com.ht.lc.dcp.task.utils.ComUtils;
import com.ht.lc.dcp.task.utils.JsoupUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @program: dcp
 * @description: 异步方法汇总类
 * @author: wanggang
 * @create: 2022-03-28 09:30
 * @Version 1.0
 **/
@Service
public class AsyncServiceImpl implements AsyncService {

    private static Logger LOG = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Autowired
    NoticeDetailsDao noticeDetailsDao;

    @Async
    public CompletableFuture<List<NoticeBrief>> getNoticeBriefByPageUrl(String url, int dataType) {
        List<NoticeBrief> list = new ArrayList<>(2);
        if (!ComUtils.isValidHtmlUrl(url)) {
            LOG.error("notice brief url is wrong, url: {}. ", url);
        } else {
            String response = HttpClientManager.getInstance().doGet(url, null, null);
            Document rspDoc = JsoupUtils.getDocFromStr(response);
            list = JsoupUtils.getNoticeBriefListFromDoc(rspDoc, dataType);
            LOG.info("parse notice brief success, url: {}. ", url);
        }
        return CompletableFuture.completedFuture(list);
    }

    @Async
    public void addNoticeDetails(List<NoticeBrief> noticeBriefs, CountDownLatch latch) {
        List<NoticeDetails> list = new ArrayList<>(2);
        if (CollectionUtils.isEmpty(noticeBriefs)) {
            LOG.error("notice brief is null, can not get notice details. ");
        } else {
            noticeBriefs.stream().forEach(l -> {
                String response = HttpClientManager.getInstance().doGet(l.getContentUrl(), null, null);
                Document rspDoc = JsoupUtils.getDocFromStr(response);
                NoticeDetails nd = JsoupUtils.cycleGenerateNoticeDetailsFromDoc(rspDoc, l);

                list.add(nd);
            });
            LOG.info("parse notice details success, size {}. ", list.size());
            List<NoticeDetailsDaoBean> daoBeans =
                    list.stream().map(nd -> cvt2NoticeDetailsDaoBean(nd)).collect(Collectors.toList());
            noticeDetailsDao.insertBatch(daoBeans);
            LOG.info("insert notice details success, size {}. ", daoBeans.size());
        }
        latch.countDown();
    }

    private NoticeDetailsDaoBean cvt2NoticeDetailsDaoBean(NoticeDetails details) {
        NoticeDetailsDaoBean noticeDetailsDaoBean = new NoticeDetailsDaoBean();
        noticeDetailsDaoBean.setTitle(details.getTitle());
        noticeDetailsDaoBean.setNoticeObject(details.getNoticeObject());
        noticeDetailsDaoBean.setContent(details.getContent());
        noticeDetailsDaoBean.setNoticeDate(details.getNoticeDate());
        noticeDetailsDaoBean.setPublishDate(details.getPublishDate());
        noticeDetailsDaoBean.setCreateTime(LocalDateTime.now());
        noticeDetailsDaoBean.setTaskId(details.getTaskId());
        noticeDetailsDaoBean.setBriefId(details.getBriefId());
        return noticeDetailsDaoBean;
    }
}
