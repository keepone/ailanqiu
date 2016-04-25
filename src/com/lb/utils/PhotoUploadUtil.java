/**
 * @function 图片上传工具类
 */
package com.lb.utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class PhotoUploadUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//http://mmbiz.qpic.cn/mmbiz/83rWicvJyreCvBicLa1V4CtxhL7msffdpK3dbeoiagibtw6lmM7W4zEpgR24ibWm8KmMxCEaWeuCTugVwlx2xLqLwRw/0?wx_fmt=gif&tp=webp&wxfrom=5
		//http://mmbiz.qpic.cn/mmbiz/4HZibZhTVhTxHiaVkwEsfhylGdQGrKGgv67wkpajEJ1OkNp6NAUWYXanjCycYIDhCuSlR5DwgicnDaqnhrk44GoLQ/640?wx_fmt=jpeg&tp=webp&wxfrom=5
		String resourceFileName="http://ww4.sinaimg.cn/bmiddle/71540ad7jw1ernwqs0alnj20hs0hjjt1.png";
		System.out.println(getPhotoType(resourceFileName));
	}
	 
	
	/**
	 * 取得上传图片的类型（.jpg,gif）；
	 * @param resourceFileName
	 * @returnhttp://www.asqql.com/upfile/u/gifc/output/20150523/336989.jpg
	 */
	public static String getPhotoType(String resourceFileName){
//		int x=resourceFileName.lastIndexOf(".");
//		int y=resourceFileName.length();
//		String z=resourceFileName.substring(x, y); //取得上传图片的类型（.jpg,gif）；
		String z=".jpg";
		
		//判断是否是微信的图片
		if(resourceFileName.indexOf("wx_fmt=")!=-1){
					if(resourceFileName.indexOf("=jpeg")!=-1){
						z=".jpeg";
					}else if(resourceFileName.indexOf("=gif")!=-1){
						z=".gif";
					}else if(resourceFileName.indexOf("=png")!=-1){
						z=".png";
					}else{
						z=".jpeg";
					}
		}else{
			if(resourceFileName.indexOf(".jpeg")!=-1){
				z=".jpeg";
			}else if(resourceFileName.indexOf(".gif")!=-1){
				z=".gif";
			}else if(resourceFileName.indexOf(".png")!=-1){
				z=".png";
			}else{
				//z="";
			}
		}
		
	 
		return z;
	}
}
