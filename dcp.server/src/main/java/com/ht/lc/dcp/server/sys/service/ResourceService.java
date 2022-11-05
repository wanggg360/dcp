package com.ht.lc.dcp.server.sys.service;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.pojo.req.AddMenusReq;
import com.ht.lc.dcp.server.sys.pojo.req.AddPageComponentsReq;
import com.ht.lc.dcp.server.sys.pojo.req.AddPagesReq;

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
     *
     * @param req
     * @return
     */
    ResultObject addPages(AddPagesReq req);

    /**
     * 新增菜单
     *
     * @param req
     * @return
     */
    ResultObject addMenus(AddMenusReq req);


    /**
     * 添加页面组件
     *
     * @param req
     * @return
     */
    ResultObject addPageComponents(AddPageComponentsReq req);
}
