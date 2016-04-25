package com.lb.action;

import com.lb.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
 

public class PageAction extends ActionSupport {
	private PageBean page=new PageBean();

	public PageBean getPage() {
		return page;
	}

	public void setPage(PageBean pageBean) {
		this.page = pageBean;
	}
	
}
