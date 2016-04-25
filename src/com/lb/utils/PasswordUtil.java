package com.lb.utils;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PasswordUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * BASE64º”√‹
	 * @param pwd
	 * @return
	 */
	public  static String encryp(String pwd){
		return pwd=(new BASE64Encoder()).encodeBuffer(pwd.getBytes());
	}
	
	
	/**
	 * BASE64Ω‚√‹
	 * @param pwd
	 * @return
	 */
	public static String Decoding(String pwd){
		try {
			 pwd=new String(new BASE64Decoder()
			.decodeBuffer(pwd));
		} catch (IOException e) {
			 pwd="";
			e.printStackTrace();
		}
		
		return pwd;
	}

}
