package com.ht.lc.dcp;

import com.ht.lc.dcp.event.ApplicationReadyEventListener;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-07-23 20:09
 * @Version 1.0
 **/

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Banner.Mode.OFF);

        ApplicationReadyEventListener appListener = new ApplicationReadyEventListener();
        application.addListeners(appListener);
        application.run(args);
    }
}
