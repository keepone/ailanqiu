package com.lb.crawl.utils;

public class GetFilePath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Users/Allen/Workspaces/MyEclipse%2010/ailanqiu/WebRoot/WEB-INF/classes/com/lb/crawl/utils/
		String path=GetFilePath.class.getResource("ip.txt").getPath();
		System.out.println(path);

	}

}
