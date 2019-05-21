package com.bc.cache.listener;

import com.bc.cache.thread.RequestProcessorThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 系统初始化监听器
 *
 * @author zhou
 */
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(InitListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("初始化工作线程和内存队列...");
        // 初始化工作线程和内存队列
        RequestProcessorThreadPool.init();
    }

}
