package com.jiutong.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Other {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	/*	List<Integer> list=new ArrayList<Integer>();
		Map<String, List > map=new HashMap<String, List >();
		list.add(1);
		list.add(32);
		list.add(null);
		map.put("one", list);
		List list2=map.get("one");
		System.out.println(list2.get(2));*/
		String name="jiu.jpg";
		int x=name.indexOf(".");
		int y=name.length();
		String a=name.substring(x, y);
		System.out.println(a);

	}

}
