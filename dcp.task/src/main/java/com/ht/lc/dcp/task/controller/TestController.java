package com.ht.lc.dcp.task.controller;

import com.ht.lc.dcp.common.http.HttpClientManager;
import com.ht.lc.dcp.common.http.SyncHttpClientBuilder;
import com.ht.lc.dcp.task.dao.SiteInfoDao;
import com.ht.lc.dcp.task.daobean.SiteInfoDaoBean;
import com.ht.lc.dcp.task.entity.pubinfo.SiteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-08-11 08:53
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    SiteInfoDao siteInfoDao;

    private static Logger LOG = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value="/getConfig",method= RequestMethod.GET)
    public List<SiteInfo> getConfig() {

        SiteInfoDaoBean siteInfoDaoBean = new SiteInfoDaoBean();
        //siteInfoDaoBean.setDataType(1);
        siteInfoDaoBean.setIsValid("1");

        HttpClientManager.getInstance().test();

        List<SiteInfo> aa = siteInfoDao.getSiteInfos(siteInfoDaoBean);

        return aa;
    }
}
