package com.ht.lc.dcp.server.sys.service.impl;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.dao.MenuDao;
import com.ht.lc.dcp.server.sys.dao.PageDao;
import com.ht.lc.dcp.server.sys.pojo.req.AddMenusReq;
import com.ht.lc.dcp.server.sys.pojo.req.AddPageComponentsReq;
import com.ht.lc.dcp.server.sys.pojo.req.AddPagesReq;
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

    @Autowired
    MenuDao menuDao;

    public ResultObject addPages(AddPagesReq req) {

        pageDao.insert(null);
        return null;
    }

    public ResultObject addMenus(AddMenusReq req) {
        return null;
    }

    public ResultObject addPageComponents(AddPageComponentsReq req) {
        return  null;
    }

}
