package com.lb.utils;

import org.apache.struts2.ServletActionContext;

public class SourceUtil {
	/**
	 * ≈–∂œ«Î«Û¿¥‘¥£¨iphone,android,pc,ipad
	 * @return
	 */
	public static String checkSource() {
		String source = null;
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		if (StrUtil.isNotEmpty(agent)) {
			agent = agent.toLowerCase();
			if (agent.contains("android")) {
				source = "android";
			} else if (agent.contains("itouch") || agent.contains("iphone")) {
				source = "iphone";
			} else if (agent.contains("ipad")) {
				source = "ipad";
			} else {
				source = "web";
			}

		}
		return source;
	}
}
