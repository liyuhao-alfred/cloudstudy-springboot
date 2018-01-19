package com.cloudstudy.schedule;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 添加并行定时任务处理
 * 
 * @ClassName ScheduleConfig
 * @author alfred
 * @Date 2018年1月19日 上午9:59:11
 * @version 1.0.0
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer, AsyncConfigurer {

	/*
	 * 并行任务
	 */
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		TaskScheduler taskScheduler = threadPoolTaskScheduler();
		taskRegistrar.setTaskScheduler(taskScheduler);
	}

	/**
	 * 并行任务使用策略：开启多线程处理
	 * 
	 * @return ThreadPoolTaskScheduler 线程池
	 */
	@Bean(destroyMethod = "shutdown")
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(20);
		scheduler.setThreadNamePrefix("task-");
		scheduler.setAwaitTerminationSeconds(60);
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		return scheduler;
	}

	/*
	 * 异步任务
	 */
	public Executor getAsyncExecutor() {
		Executor executor = threadPoolTaskScheduler();
		return executor;
	}

	/*
	 * 异步任务 异常处理
	 */
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}