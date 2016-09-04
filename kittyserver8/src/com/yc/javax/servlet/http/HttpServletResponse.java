package com.yc.javax.servlet.http;

import com.yc.javax.servlet.ServletResponse;

public interface HttpServletResponse extends ServletResponse{
	/**
	 * ����ض��򷽷�
	 */
	public void sendRedirect();
	
	public void addCookie(  Cookie cookie );
	
	public JspWriter getJspWriter();
	
	public Cookie[] getCookies();
}