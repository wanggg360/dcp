package com.ht.lc.dcp.task.service.impl;

import com.ht.lc.dcp.task.constant.BizConst;
import com.ht.lc.dcp.task.dao.SiteInfoDao;
import com.ht.lc.dcp.task.daobean.SiteInfoDaoBean;
import com.ht.lc.dcp.task.entity.SiteInfo;
import com.ht.lc.dcp.task.service.SiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-24 19:52
 * @Version 1.0
 **/
@Service
public class SiteInfoServiceImpl implements SiteInfoService {

    @Autowired
    SiteInfoDao siteInfoDao;

    @Override
    public List<SiteInfo> getAllValidSiteInfos() {
        SiteInfoDaoBean bean = new SiteInfoDaoBean();
        bean.setIsValid(BizConst.Common.DATA_FLAG_VALID);
        List<SiteInfoDaoBean> daoBeans = siteInfoDao.getListByDaoBean(bean);
        List<SiteInfo> result = daoBeans.stream().map(dao -> cvt2SiteInfo(dao)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<SiteInfo> getSiteInfosByDataType(int dataType) {
        SiteInfoDaoBean bean = new SiteInfoDaoBean();
        bean.setDataType(dataType);
        List<SiteInfoDaoBean> daoBeans = siteInfoDao.getListByDaoBean(bean);
        List<SiteInfo> result = daoBeans.stream().map(dao -> cvt2SiteInfo(dao)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<SiteInfo> getSiteInfosByBranchId(String branchId) {
        SiteInfoDaoBean bean = new SiteInfoDaoBean();
        bean.setBranchId(branchId);
        List<SiteInfoDaoBean> daoBeans = siteInfoDao.getListByDaoBean(bean);
        List<SiteInfo> result = daoBeans.stream().map(dao -> cvt2SiteInfo(dao)).collect(Collectors.toList());
        return result;
    }

    private SiteInfo cvt2SiteInfo(SiteInfoDaoBean daoBean) {
        SiteInfo siteInfo = new SiteInfo();
        siteInfo.setDataType(daoBean.getDataType());
        siteInfo.setBranchId(daoBean.getBranchId());
        siteInfo.setBranchName(daoBean.getBranchName());
        siteInfo.setHostname(daoBean.getHostname());
        siteInfo.setUrl(daoBean.getUrl());
        siteInfo.setSiteMenu(daoBean.getSiteMenu());
        siteInfo.setGatherType(daoBean.getGatherType());
        siteInfo.setGatherPeriod(daoBean.getGatherPeriod());
        siteInfo.setRemark(daoBean.getRemark());
        siteInfo.setIsValid(daoBean.getIsValid());
        siteInfo.setBranchCategory(daoBean.getBranchCategory());
        return siteInfo;
    }
}
