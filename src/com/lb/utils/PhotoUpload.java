/**
 * @function 图片上传工具类
 */
package com.lb.utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class PhotoUpload {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 上传图片，返回图片路径
	 * @param resource
	 * @param resourceFileName
	 * @return
	 */
	public static String uploadImg(File resource,String resourceFileName ){
		String savePath="/product_img";
		String logo=null;
		String URL="http://localhost:18080/product_img/";
		long a=System.currentTimeMillis();
		int x=resourceFileName.indexOf(".");
		int y=resourceFileName.length();
		String z=resourceFileName.substring(x, y); //取得上传图片的类型（.jpg,gif）；
		String fileNama=a+z;
		logo=URL+fileNama;
		ServletContext servletContext = ServletActionContext
		.getServletContext();
		String realPath = servletContext.getRealPath(savePath);
		System.out.println(logo);
		System.out.println(realPath);
		try {
			FileUtils.copyFile(resource, new File(realPath,
					String.valueOf(fileNama)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return logo;	
	}

	
	/**
	 * 上传图片，返回图片名称
	 * @param resource
	 * @param resourceFileName
	 * @return
	 */
	public static String uploadImg2(File resource,String resourceFileName ){
		String savePath="/product_img";
		String logo=null;
		String URL="http://localhost:8080/product_img/";
		long a=System.currentTimeMillis();
		int x=resourceFileName.indexOf(".");
		int y=resourceFileName.length();
		String z=resourceFileName.substring(x, y); //取得上传图片的类型（.jpg,gif）；
		String fileNama=a+z;
		logo=URL+fileNama;
		ServletContext servletContext = ServletActionContext
		.getServletContext();
		String realPath = servletContext.getRealPath(savePath);
		System.out.println(logo);
		System.out.println(realPath);
		try {
			FileUtils.copyFile(resource, new File(realPath,
					String.valueOf(fileNama)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileNama;	
	}
	
	 
}
