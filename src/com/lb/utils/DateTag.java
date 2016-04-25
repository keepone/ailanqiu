/**
 * @author allen
 * @date 2014-8-2
 * @function 自定义分页标签
 */
package com.lb.utils;
import java.io.PrintWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class DateTag extends TagSupport {
	private String DATE;
	@Override
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		String date=TimeUtil.toDate3(DATE);
		sb.append(date);
			try {
				JspWriter out = pageContext.getOut();
				out.println(sb.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		return super.doEndTag();
	}
	public String getDATE() {
		return DATE;
	}
	public void setDATE(String dATE) {
		DATE = dATE;
	}
 
	 
	
}
