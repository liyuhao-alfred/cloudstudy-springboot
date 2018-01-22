/******************************************************************
 *    
 *    Package:     com.cloudstudy.constant
 *
 *    Filename:    package-info.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月16日 下午5:35:56
 *
 *    - first revision 
 *
 *****************************************************************/
/**
 * 
 * 
 * @ClassName package-info 
 * @author alfred 
 * @Date 2018年1月16日 下午5:35:56 
 * @version 1.0.0  
 */
package com.cloudstudy.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
public class HtmlPathConstant {

	@Value("${cloudstudy.project.name}")
	private String projectName;
	@Value("${cloudstudy.project.version}")
	private String projectVersion;

	public static String testStr;

	public String getProjectName() {
		return projectName;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

}