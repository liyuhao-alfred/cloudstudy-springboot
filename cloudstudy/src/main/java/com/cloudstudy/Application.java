package com.cloudstudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot 应用启动类
 *
 * Created by liyuhao 2018年1月19日08:38:34
 */

@EnableScheduling // 定时任务
@ServletComponentScan // 添加的注解
@SpringBootApplication // Spring Boot 应用的标识
@MapperScan("com.cloudstudy.mapper") // mapper 接口类扫描包配置
@EnableCaching // 启动缓存
public class Application {
	public static void main(String[] args) {

		/** 程序启动入口，启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件 **/
		SpringApplication.run(Application.class, args);
	}
}
