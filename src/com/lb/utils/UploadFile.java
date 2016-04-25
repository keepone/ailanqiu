package com.lb.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.client.fluent.Request;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.util.CommonUtils;
import org.json.JSONException;

import sun.misc.BASE64Decoder;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.rs.PutPolicy;

public class UploadFile {
	public static String uploadPhoto(){
		Long time = System.currentTimeMillis();
		 Config.ACCESS_KEY = "hlVkFIkObKm8x16a_kmU6s49QlEdDYJJjPBniNFq";
			        Config.SECRET_KEY = "bPWGES5FsngTx20CsC5JjItuJRIfIKc_vl_GV8H_";
			        
			        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
			        String bucketName = "productpic";
			        PutPolicy putPolicy = new PutPolicy(bucketName);
			        String uptoken=null;
					try {
						uptoken = putPolicy.token(mac);
						System.out.println(uptoken);
					} catch (AuthException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        PutExtra extra = new PutExtra(); 
					//String targetfile =ServletActionContext.getRequest().getParameter(resource);
	
					try {
							BASE64Decoder base64 = new BASE64Decoder();
							//InputStream in = new ByteArrayInputStream(base64.decodeBuffer(targetfile));
							IoApi.putFile(uptoken, time+".jpg", "C:\\Users\\Administrator\\Pictures\\a.jpg", extra);
					       //IoApi.Put(uptoken, time+".jpg", in, extra);
					        
					} catch (Exception e) {
					}
					String url="http://productpic.qiniudn.com/"+time+".jpg";
					return url;
	}
	
	public static String uploadPhoto2(String filePath){
		Long time = System.currentTimeMillis();
		 Config.ACCESS_KEY = "hlVkFIkObKm8x16a_kmU6s49QlEdDYJJjPBniNFq";
			        Config.SECRET_KEY = "bPWGES5FsngTx20CsC5JjItuJRIfIKc_vl_GV8H_";
			        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
			        String bucketName = "productpic";
			        PutPolicy putPolicy = new PutPolicy(bucketName);
			        String uptoken=null;
					try {
						uptoken = putPolicy.token(mac);
						System.out.println(uptoken);
					} catch (AuthException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        PutExtra extra = new PutExtra(); 
					//String targetfile =ServletActionContext.getRequest().getParameter(resource);
	
					try {
							BASE64Decoder base64 = new BASE64Decoder();
							//InputStream in = new ByteArrayInputStream(base64.decodeBuffer(targetfile));
							IoApi.putFile(uptoken, time+".jpg", filePath, extra);
					       //IoApi.Put(uptoken, time+".jpg", in, extra);
					        
					} catch (Exception e) {
					}
					String url="http://productpic.qiniudn.com/"+time+".jpg";
					return url;
	}
	public static void main(String[] args){
		System.out.println(uploadPhoto());
	}
	
	
}
