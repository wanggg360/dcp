package com.ht.lc.dcp.task;

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

@SpringBootApplication(scanBasePackages = {"com.ht.lc.dcp.common","com.ht.lc.dcp.task"})
//@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
