package com.abc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TODO:线程池的配置
 */
@Configuration
@EnableAsync
public class TaskExecutorConfig {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorConfig.class);
    public static Integer smsAsynSize = Runtime.getRuntime().availableProcessors();

    /**
     * TODO: 此方法名称为asyncPromiseExecutor，即在spring中注入了一个名字为asyncPromiseExecutor的bean
     * 方法名只要在项目中唯一性，可以适当任意取（最好遵循一定的规则）
     * 使用方法：在需要加入线程池的方法上增加注解@Async("asyncPromiseExecutor")就可以加入此线程池异步执行
     */

    @Bean
    public Executor asyncPromiseExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(smsAsynSize);
        //配置队列大小
        executor.setQueueCapacity(smsAsynSize * 5);
        //配置最大线程数
        executor.setMaxPoolSize(smsAsynSize * 2);
        // executor.setKeepAliveSeconds(2);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("A-Thread");
        /**
         * rejection-policy：当pool已经达到max size的时候，如何处理新任务
         * CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化执行器
        executor.initialize();
        logger.info("线程池A-Thread配置成功");
        return executor;
    }
}
