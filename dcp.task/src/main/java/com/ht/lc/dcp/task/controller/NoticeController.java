package com.ht.lc.dcp.task.controller;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.task.entity.NoticeBrief;
import com.ht.lc.dcp.task.entity.SiteInfo;
import com.ht.lc.dcp.task.req.GatherNoticeDetailsReq;
import com.ht.lc.dcp.task.rsp.GatherNoticeBriefsRsp;
import com.ht.lc.dcp.task.rsp.GatherNoticeDetailsRsp;
import com.ht.lc.dcp.task.service.NoticeDetailsService;
import com.ht.lc.dcp.task.service.SiteInfoService;
import com.ht.lc.dcp.task.service.NoticeBriefService;

import com.ht.lc.dcp.task.utils.ComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-08-11 08:53
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/notice")
public class NoticeController {

    private static Logger LOG = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    SiteInfoService siteInfoService;

    @Autowired
    NoticeBriefService noticeBriefService;

    @Autowired
    NoticeDetailsService noticeDetailsService;

    @RequestMapping(value="/gatherNoticeBriefs",method= RequestMethod.POST)
    public GatherNoticeBriefsRsp gatherNoticeBriefs(@RequestBody GatherNoticeDetailsReq req) {
        String taskId = ComUtils.generateUniqueTaskId();
        List<SiteInfo> sites = siteInfoService.getAllValidSiteInfos();
        int cnt = sites.stream().mapToInt(s -> noticeBriefService.addNoticeBriefBySiteInfo(s, taskId)).sum();
        GatherNoticeBriefsRsp rsp = new GatherNoticeBriefsRsp();
        rsp.setResultCode(ResultCode.SUCCESS.getCode());
        rsp.setResultDesc(ResultCode.SUCCESS.getDesc());
        rsp.setTaskId(taskId);
        rsp.setSuccessCnt(cnt);
        return rsp;
    }

    @RequestMapping(value="/gatherNoticeDetails",method= RequestMethod.POST)
    public GatherNoticeDetailsRsp gatherNoticeDetails(@RequestBody @Validated GatherNoticeDetailsReq req) {

        List<NoticeBrief> noticeBriefs = noticeBriefService.getNoticeBriefsByDateRange(req.getTaskId(), req.getBranchId(), req.getStartDate(), req.getEndDate());
        noticeDetailsService.addNoticeDetailsByBriefs(noticeBriefs);

        return new GatherNoticeDetailsRsp(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc());
    }


    @RequestMapping(value="/test",method= RequestMethod.GET)
    public GatherNoticeBriefsRsp test() {

        GatherNoticeBriefsRsp rsp = new GatherNoticeBriefsRsp();
        List<NoticeBrief> lists = noticeBriefService.getNoticeBriefsByDateRange("20220328185400-lOo", "1", "2020-05-30", "2020-12-31");
        rsp.setResultCode(ResultCode.SUCCESS.getCode());
        rsp.setResultDesc(ResultCode.SUCCESS.getDesc());
        return rsp;
    }
}
