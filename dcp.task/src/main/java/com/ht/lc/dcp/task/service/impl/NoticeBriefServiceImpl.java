package com.ht.lc.dcp.task.service.impl;

import com.ht.lc.dcp.common.exception.ServiceException;
import com.ht.lc.dcp.common.http.HttpClientManager;
import com.ht.lc.dcp.task.constant.BizConst;
import com.ht.lc.dcp.task.dao.NoticeBriefDao;
import com.ht.lc.dcp.task.daobean.NoticeBriefDaoBean;
import com.ht.lc.dcp.task.entity.NoticeBrief;
import com.ht.lc.dcp.task.entity.NoticePageInfo;
import com.ht.lc.dcp.task.entity.SiteInfo;
import com.ht.lc.dcp.task.service.AsyncService;
import com.ht.lc.dcp.task.service.NoticeBriefService;
import com.ht.lc.dcp.task.utils.ComUtils;
import com.ht.lc.dcp.task.utils.JsoupUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-24 19:17
 * @Version 1.0
 **/
@Service
public class NoticeBriefServiceImpl implements NoticeBriefService {

    private static Logger LOG = LoggerFactory.getLogger(NoticeBriefServiceImpl.class);

    @Autowired
    AsyncService asyncService;

    @Autowired
    NoticeBriefDao noticeBriefDao;

    @Override
    public int addNoticeBriefBySiteInfo(SiteInfo siteInfo, String taskId) {
        int result = 0;
        if (Objects.isNull(siteInfo)) {
            LOG.error("siteinfo is null, please check! ");
            return result;
        }
        // 原始url字符串
        String reqUrl = siteInfo.getUrl();
        if (!ComUtils.isValidHtmlUrl(reqUrl)) {
            LOG.error("siteinfo url wrong, url: {}. ", reqUrl);
            return result;
        }
        List<NoticeBrief> briefInfos = new ArrayList<>(4);
        try {
            // 响应字符串
            String response = HttpClientManager.getInstance().doGet(reqUrl, null, null);
            Document rspdoc = JsoupUtils.getDocFromStr(response);
            NoticePageInfo pageInfo = JsoupUtils.getNoticePageInfo(rspdoc);
            if (pageInfo.getTotalCnt() > 0 && pageInfo.getPageCnt() > 0) {
                LOG.info("get pageinfo success, branch: {}, total: {}, page: {}. ", siteInfo.getBranchName(),
                        pageInfo.getTotalCnt(), pageInfo.getPageCnt());
                // 解析出pageinfo后直接处理第一页
                briefInfos.addAll(JsoupUtils.getNoticeBriefListFromDoc(rspdoc, siteInfo.getDataType()));
                List<String> supMBriefUrls = cvtBaseUrl2PageUrlList(reqUrl, pageInfo.getPageCnt());
                // 异步调用各分页的url，完成后汇聚结果
                CompletableFuture[] futures =
                        supMBriefUrls.stream().map(url -> asyncService.getNoticeBriefByPageUrl(url, siteInfo.getDataType()))
                                .toArray(CompletableFuture[]::new);
                CompletableFuture<Void> all = CompletableFuture.allOf(futures);
                all.thenAccept(s -> LOG.info("combine all page result accept. "));
                all.whenComplete((v, h) -> {
                    LOG.info("combine all page result completed. ");
                }).join();
                for (CompletableFuture f : futures) {
                    briefInfos.addAll((List<NoticeBrief>) f.get());
                }
                if (!CollectionUtils.isEmpty(briefInfos)) {
                    noticeBriefDao.insertBatch(cvt2NoticeBriefDaoBeanList(briefInfos, siteInfo, taskId));
                    result = briefInfos.size();
                    LOG.info("finish add notice brief 2 database, size: {}. ", result);
                }
            } else {
                LOG.error("get pageinfo error, will not process notice brief. site url: {}", reqUrl);
            }
        } catch (ServiceException | InterruptedException | ExecutionException e) {
            LOG.error("async parse sup measure exception,{}. ", e.getMessage());
        }
        return result;
    }

    private List<NoticeBriefDaoBean> cvt2NoticeBriefDaoBeanList(List<NoticeBrief> nbs, SiteInfo si, String taskId) {
        List<NoticeBriefDaoBean> daoBeans = new ArrayList<>(2);
        if (CollectionUtils.isEmpty(nbs) || Objects.isNull(si)) {
            LOG.error("notice brief list is empty or siteinfo is null, can not convert to daobean. ");
            return daoBeans;
        }
        return nbs.stream().map(nb -> cvt2NoticeBriefDaoBean(nb, si, taskId)).collect(Collectors.toList());
    }

    private NoticeBriefDaoBean cvt2NoticeBriefDaoBean(NoticeBrief nb, SiteInfo si, String taskId) {
        NoticeBriefDaoBean nbdao = new NoticeBriefDaoBean();
        nbdao.setTaskId(taskId);
        nbdao.setDataType(si.getDataType());
        nbdao.setBranchCategory(si.getBranchCategory());
        nbdao.setBranchId(si.getBranchId());
        if (si.getHostname().endsWith("/")) {
            nbdao.setContentUrl(si.getHostname().substring(0, si.getHostname().length() - 1) + nb.getContentUrl());
        } else {
            nbdao.setContentUrl(si.getBranchId() + nb.getContentUrl());
        }
        nbdao.setTitle(nb.getTitle());
        nbdao.setPublishDate(nb.getPublishDate());
        nbdao.setCreateTime(LocalDateTime.now());
        return nbdao;
    }

    private List<String> cvtBaseUrl2PageUrlList(String baseUrl, int pageCnt) {
        List<String> urls = new ArrayList<>(2);
        if (pageCnt == 1) {
            return urls;
        }
        String temp = "";
        for (int i = 2; i <= pageCnt; i++) {
            temp = BizConst.ElementKeyStr.PAGE_URL_TAG + BizConst.ElementKeyStr.PAGE_SPLIT_SYMBOL + i;
            urls.add(baseUrl.replace(BizConst.ElementKeyStr.PAGE_URL_TAG, temp));
        }
        return urls;
    }

    @Override
    public List<NoticeBrief> getNoticeBriefsByTaskId(String taskId) {
        NoticeBriefDaoBean db = new NoticeBriefDaoBean();
        db.setTaskId(taskId);
        List<NoticeBriefDaoBean> briefDbs = noticeBriefDao.getListByDaoBean(db);
        return briefDbs.stream().map(dbs -> cvt2NoticeBrief(dbs)).collect(Collectors.toList());
    }

    @Override
    public List<NoticeBrief> getNoticeBriefsByDateRange(String taskId, String branchId, String startDate,
                                                        String endDate) {
        NoticeBriefDaoBean db = new NoticeBriefDaoBean();
        db.setTaskId(taskId);
        db.setBranchId(branchId);
        if (StringUtils.hasText(startDate)) {
            if (ComUtils.checkStr(BizConst.Common.PARAMS_DATE_FORMAT_PATTERN, startDate)) {
                db.setStartDate(
                        LocalDate.parse(startDate, DateTimeFormatter.ofPattern(BizConst.Common.DATE_FORMAT_NORMAL)));
            }
        }
        if (StringUtils.hasText(endDate)) {
            if (ComUtils.checkStr(BizConst.Common.PARAMS_DATE_FORMAT_PATTERN, endDate)) {
                db.setEndDate(
                        LocalDate.parse(endDate, DateTimeFormatter.ofPattern(BizConst.Common.DATE_FORMAT_NORMAL)));
            }
        }
        List<NoticeBriefDaoBean> briefDbs = noticeBriefDao.getListByDaoBean(db);
        return briefDbs.stream().map(dbs -> cvt2NoticeBrief(dbs)).collect(Collectors.toList());
    }

    @Override
    public List<NoticeBrief> getNoticeBriefsByDaoBean(NoticeBriefDaoBean daoBean) {
        if (Objects.isNull(daoBean)) {
            LOG.error("input dao bean is nu");
            return null;
        }
        List<NoticeBriefDaoBean> briefDbs = noticeBriefDao.getListByDaoBean(daoBean);
        return briefDbs.stream().map(dbs -> cvt2NoticeBrief(dbs)).collect(Collectors.toList());
    }

    private NoticeBrief cvt2NoticeBrief(NoticeBriefDaoBean db) {
        NoticeBrief nb = new NoticeBrief();
        nb.setContentUrl(db.getContentUrl());
        nb.setTitle(db.getTitle());
        nb.setPublishDate(db.getPublishDate());
        nb.setTaskId(db.getTaskId());
        nb.setDataType(db.getDataType());
        nb.setBranchCategory(db.getBranchCategory());
        nb.setBranchId(db.getBranchId());
        nb.setId(db.getId());
        return nb;
    }
}
