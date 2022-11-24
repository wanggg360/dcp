package com.ht.lc.dcp.demo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-08-04 08:41
 * @Version 1.0
 **/

public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(ApplicationReadyEventListener.class);

    @Override public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("Get App ready event.");
    }
}
