/******************************************************************
 *    
 *    Package:     com.cloudstudy.constant
 *
 *    Filename:    RoleTypeConstant.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月22日 上午9:50:23
 *
 *    - first revision 
 *
 *****************************************************************/
package com.cloudstudy.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @ClassName StatusConstant
 * @author alfred
 * @Date 2018年1月22日 上午9:50:23
 * @version 1.0.0
 */
public class FileOriginConstant {
	public static List<String> imgList = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("jpg");
			add("jpeg");
			add("gif");
			add("png");
			add("bmp");
		}
	};
	public static List<String> docList = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("doc");
			add("docx");
			add("docm");
			add("xls");
			add("xlsx");
			add("xlsm");
			add("ppt");
			add("pptx");
			add("pptm");
			add("pdf");
			add("rtf");
		}
	};
	public static List<String> videoList = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("avi");
			add("wmv");
			add("mp4");
			add("mpeg");
			add("mov");
			add("mkv");
			add("flv");
			add("f4v");
			add("m4v");
			add("rmvb");
			add("rm");
			add("3gp");
			add("dat");
			add("ts");
			add("mts");
		}
	};
}
