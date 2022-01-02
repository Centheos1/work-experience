package com.fitnessplayground.config;

import com.fitnessplayground.exception.AsyncException;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private ThreadPoolTaskExecutor executor;

    @Override
    public Executor getAsyncExecutor() {

        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("Background");
        executor.initialize();
        return executor;
    }

    @PreDestroy
    public void shutdownExecutors() {
        executor.shutdown();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncException();
    }
}