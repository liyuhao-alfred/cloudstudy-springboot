
package com.cloudstudy.registration.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class IndexListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("=================listener destroy===================");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("=================listener init===================");
	}

}
