package com.ht.lc.dcp.server.sys.controller;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.pojo.req.AddMenusReq;
import com.ht.lc.dcp.server.sys.pojo.req.AddPageComponentsReq;
import com.ht.lc.dcp.server.sys.pojo.req.AddPagesReq;
import com.ht.lc.dcp.server.sys.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-12 19:43
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/sys/resource")

public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/addPages", method = RequestMethod.POST)
    public ResultObject addPages(@RequestBody @Validated AddPagesReq req) {
        return resourceService.addPages(req);
    }

    @RequestMapping(value = "/addMenus", method = RequestMethod.POST)
    public ResultObject addMenus(@RequestBody @Validated AddMenusReq req) {
        return resourceService.addMenus(req);
    }

    @RequestMapping(value = "/addPageComponents", method = RequestMethod.POST)
    public ResultObject addPageComponents(@RequestBody @Validated AddPageComponentsReq req) {
        return resourceService.addPageComponents(req);
    }
}
