package com.ht.lc.dcp.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-25 15:11
 * @Version 1.0
 **/
public class CustomizedThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(CustomizedThreadPoolTaskExecutor.class);

    public void threadPoolInfo() {
        ThreadPoolExecutor te = getThreadPoolExecutor();
        if (Objects.isNull(te)) {
            LOG.error("Thread pool executor is null. ");
            return;
        }
        LOG.info("CorePoolSize: {}, MaxPoolSize: {}, TaskCount: {}, ActiveCount: {}.",
                te.getCorePoolSize(),
                te.getMaximumPoolSize(),
                te.getTaskCount(),
                te.getActiveCount());
    }

    @Override
    public void execute(Runnable task) {
        threadPoolInfo();
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        threadPoolInfo();
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        threadPoolInfo();
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        threadPoolInfo();
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        threadPoolInfo();
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        threadPoolInfo();
        return super.submitListenable(task);
    }
}
