package com.lb.utils;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;

public class PrintUtil {
	public static PrintWriter printWord(){
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8"); 
		PrintWriter out = null;
		try {
			out = ServletActionContext.getResponse().getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return out;
	}
}
