package com.cloudstudy.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 生成swagger接口文档
 * 
 * @author liyuhao
 * @since 2017/7/17
 */
//@Configuration
//@EnableSwagger2
@SuppressWarnings("unused")
public class Swagger2 {

	@Bean
	public Docket createRestApi() {

		ApiInfo apiInfo = new ApiInfoBuilder().title("cloudstudy 项目接口文档")
				.description("cloudstudy 项目的接口文档，符合RESTful API。").version("1.0").build();

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select()
				.apis(RequestHandlerSelectors.basePackage("com.cloudstudy.controller")) // 以扫描包的方式
				.paths(PathSelectors.any()).build();
	}

}