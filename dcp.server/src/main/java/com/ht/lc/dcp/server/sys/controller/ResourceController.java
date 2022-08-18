package com.ht.lc.dcp.server.sys.controller;

import com.ht.lc.dcp.common.base.ResultObject;
import com.ht.lc.dcp.server.sys.req.PageReq;
import com.ht.lc.dcp.server.sys.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/api/resource")

public class ResourceController {

    private static Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value="/addPages",method= RequestMethod.POST)
    public ResultObject addPages(@RequestBody @Validated PageReq req) {
        return resourceService.addPages(req);
    }

}
