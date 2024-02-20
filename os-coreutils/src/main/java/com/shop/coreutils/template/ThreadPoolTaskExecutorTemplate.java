package com.shop.coreutils.template;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;


public class ThreadPoolTaskExecutorTemplate {
    public static ThreadPoolTaskExecutor getNewThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(8);
        //  threadPoolTaskExecutor.setKeepAliveSeconds(60);
        //  threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix("Default Thread - ");
        threadPoolTaskExecutor.setThreadGroupName("Default Thread Group - " + new Random().nextInt(100));
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
