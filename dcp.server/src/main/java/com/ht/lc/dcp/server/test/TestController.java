package com.ht.lc.dcp.server.test;

import com.ht.lc.dcp.common.constants.CipherConstant;
import com.ht.lc.dcp.server.entity.User;
import com.ht.lc.dcp.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
    UserRepository userRepository;

    private static Logger LOG = LoggerFactory.getLogger("testlogger");

    @RequestMapping(value="/getConfig",method= RequestMethod.GET)
    public void getConfig() {

        User user1 = new User();
        user1.setUserId("018208");
        user1.setPassword("test");
        user1.setUsername("wanggang");
        user1.setCreateTime(LocalDateTime.now());
        userRepository.save(user1);

        LOG.info("wanggang test log");

        LOG.debug("test test" + CipherConstant.AES_GCM_256);
    }
}
