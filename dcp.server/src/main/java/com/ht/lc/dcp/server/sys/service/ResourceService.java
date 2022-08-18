package com.ht.lc.dcp.server.sys.service;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.req.PageReq;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-11 19:46
 * @Version 1.0
 **/
public interface ResourceService {

    /**
     * 新增页面
     * @param req
     * @return
     */
    ResultObject addPages(PageReq req);

}
