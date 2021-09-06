package com.ht.lc.dcp.server.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    private static Logger LOG = LoggerFactory.getLogger("testlogger");

    @RequestMapping(value="/getConfig",method= RequestMethod.GET)
    public void getConfig() {

        LOG.info("wanggang test log");

        LOG.debug("test test");

    }
}
