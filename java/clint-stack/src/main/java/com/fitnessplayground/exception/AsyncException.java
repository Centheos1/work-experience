package com.fitnessplayground.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class AsyncException implements AsyncUncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AsyncException.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        logger.error(" Async Exception {} | Method", ex.getMessage(), method.getName());
        for (Object param : params) {
            logger.error(" Async Exception Parameter {}",param);
        }
    }
}