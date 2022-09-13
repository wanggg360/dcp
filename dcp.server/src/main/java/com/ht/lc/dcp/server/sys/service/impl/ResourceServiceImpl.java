package com.ht.lc.dcp.server.sys.service.impl;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.dao.PageDao;
import com.ht.lc.dcp.server.sys.req.PageReq;
import com.ht.lc.dcp.server.sys.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-11 19:50
 * @Version 1.0
 **/
@Service
public class ResourceServiceImpl implements ResourceService {

    private static Logger LOG = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    PageDao pageDao;

    public ResultObject addPages(PageReq req) {




        pageDao.insert(null);
        return null;
    }

}
