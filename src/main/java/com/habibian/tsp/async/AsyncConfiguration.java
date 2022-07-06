package com.habibian.tsp.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * This configuration class will be used to enable and configure the asynchronous method execution.
 *
 * @author Ali
 */
@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfiguration.class);

    /**
     * This bean helps to customize the thread executor such as configuring the number of threads for an application,
     * queue limit size, and so on.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        LOGGER.info("Creating Async Task Executor");

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("FetchDataThread-");
        executor.initialize();

        return executor;
    }
}
