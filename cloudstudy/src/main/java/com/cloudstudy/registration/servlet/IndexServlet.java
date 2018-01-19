/******************************************************************
 *    
 *    Package:     com.cloudstudy.registration
 *
 *    Filename:    package-info.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月19日 上午8:31:24
 *
 *    - first revision 
 *
 *****************************************************************/
/**
 * 
 * 
 * @ClassName package-info 
 * @author alfred 
 * @Date 2018年1月19日 上午8:31:24 
 * @version 1.0.0  
 */
package com.cloudstudy.registration.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注解实现
 * 
 * @ClassName IndexServlet
 * @author alfred
 * @Date 2018年1月19日 上午8:41:40
 * @version 1.0.0
 */
public class IndexServlet extends HttpServlet {
	/**
	 * @Field @serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		PrintWriter out = resp.getWriter();
		out.print("00000000000000000");
	}
}
