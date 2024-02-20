package com.shop.coreutils.configuration;

import com.shop.coreutils.template.ThreadPoolTaskExecutorTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

@Configuration
public class ApplicationEventMulticasterConfig {

    @Bean(name="applicationEventMulticaster")
    ApplicationEventMulticaster simpleApplicationEventMulticaster(){
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster =
                new SimpleApplicationEventMulticaster();
        simpleApplicationEventMulticaster.setTaskExecutor(ThreadPoolTaskExecutorTemplate.getNewThreadPoolTaskExecutor());
        return simpleApplicationEventMulticaster;

    }

}
