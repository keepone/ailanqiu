package com.lb.action;

import java.util.ArrayList;
import java.util.List;

import com.lb.utils.TimeUtil;

public class TEST {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	long today=TimeUtil.getTodayDecimal();
		System.out.println(today);
		long yestoday=today-3*24*60*60*1000;
		System.out.println(yestoday);
		System.out.println(TimeUtil.toDate(String.valueOf(today)));
System.out.println(TimeUtil.toDate(String.valueOf(yestoday)));
	}

}
