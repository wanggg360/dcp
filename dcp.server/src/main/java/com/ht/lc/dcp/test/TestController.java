package com.ht.lc.dcp.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Autowired
    private MyBean myBean;

    @RequestMapping(value="/getConfig",method= RequestMethod.GET)
    public void getConfig() {
        System.out.println(myBean.getName());
    }
}
