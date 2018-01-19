/******************************************************************
 *    
 *    Package:     com.cloudstudy.registration
 *
 *    Filename:    Register.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月19日 上午8:34:53
 *
 *    - first revision 
 *
 *****************************************************************/
package com.cloudstudy.registration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudstudy.registration.filter.IndexFilter;
import com.cloudstudy.registration.listener.IndexListener;
import com.cloudstudy.registration.servlet.IndexServlet;

/**
 * web.xml的servlet、filter、listener注册中心
 * 
 * @ClassName Register
 * @author alfred
 * @Date 2018年1月19日 上午8:34:53
 * @version 1.0.0
 */
@Configuration
public class Register {
	@Bean
	public ServletRegistrationBean weChatValid() {
		// 第一个参数是第1步中创建的WeChatServlet实例，第二个参数是其对应的路径，相当于web.xml中配置时的url-pattern。
		ServletRegistrationBean registration = new ServletRegistrationBean(new IndexServlet());
		registration.addUrlMappings("/weChatValid");
		return registration;

	}

	@Bean
	public FilterRegistrationBean testFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new IndexFilter());
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	public ServletListenerRegistrationBean<IndexListener> testListenerRegistration() {
		ServletListenerRegistrationBean<IndexListener> registration = new ServletListenerRegistrationBean<IndexListener>(
				new IndexListener());
		return registration;
	}
}
