
package com.cloudstudy.registration.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cloudstudy.bo.UserExample;
import com.cloudstudy.init.Init;
import com.cloudstudy.mapper.UserMapper;

public class IndexListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("=================listener destroy===================");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		// 通过Spring提供的工具类获取ApplicationContext对象,getAutowireCapableBeanFactory方法将AutowireCapableBeanFactory这个接口暴露给外部使用
		// AutowireCapableBeanFactory这个接口一般在applicationContext的内部是较少使用的，它的功能主要是为了装配applicationContext管理之外的Bean。
		// 这样就可以使用@Autowired转载bean
		WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext())
				.getAutowireCapableBeanFactory().autowireBean(this);

		System.out.println("=================listener init===================");

		initUser();// 利用listener初始化函数
	}

	@Autowired
	private UserMapper userMapper;

	private void initUser() {
		long count = userMapper.countByExample(new UserExample());
		Init.init(count + "");
	}

}
