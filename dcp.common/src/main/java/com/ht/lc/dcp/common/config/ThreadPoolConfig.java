package com.ht.lc.dcp.common.config;

import com.ht.lc.dcp.common.task.CustomizedThreadPoolTaskExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-25 15:28
 * @Version 1.0
 **/
@Configuration @EnableAsync @PropertySource("classpath:/conf/system.properties") public class ThreadPoolConfig {

    @Value("${custom.threadpool.corepoolsize:5}") private int corePoolSize;

    @Value("${custom.threadpool.maxpoolsize:10}") private int maxPoolSize;

    @Value("${custom.threadpool.queuecapacity:20}") private int queueCapacity;

    @Value("${custom.threadpool.threadnameprefix:dcp-thread-pool-}") private String threadNamePrefix;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    @Bean public Executor taskExecutor() {
        ThreadPoolTaskExecutor pool = new CustomizedThreadPoolTaskExecutor();
        //线程核心数目
        pool.setCorePoolSize(corePoolSize);
        pool.setAllowCoreThreadTimeOut(true);
        //最大线程数
        pool.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        pool.setQueueCapacity(queueCapacity);
        //配置线程池前缀
        pool.setThreadNamePrefix(threadNamePrefix);
        //配置拒绝策略
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //数据初始化
        pool.initialize();
        return pool;
    }
}
