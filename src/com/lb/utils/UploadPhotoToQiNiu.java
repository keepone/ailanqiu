package com.lb.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.opensymphony.xwork2.ActionContext;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.rs.PutPolicy;

public class UploadPhotoToQiNiu {

	/**
	 * @param args
	 * @throws IOException
	 * 
	 *             裁剪正中部分，等比缩小生成200x200缩略图：
	 *             http://developer.qiniu.com/resource/gogopher
	 *             .jpg?imageView2/1/w/200/h/200
	 * 
	 *             宽度固定为200px，高度等比缩小，生成200x133缩略图：
	 *             http://developer.qiniu.com/resource
	 *             /gogopher.jpg?imageView2/2/w/200
	 * 
	 * 
	 *             生成480x320缩略图 等比缩小75%：
	 *             http://developer.qiniu.com/resource/gogopher
	 *             .jpg?imageMogr2/thumbnail/!75p
	 */
	public static void main(String[] args) throws IOException {

		String result = uploadToQiNiuByImgURL("http://mmbiz.qpic.cn/mmbiz/4HZibZhTVhTxHiaVkwEsfhylGdQGrKGgv67wkpajEJ1OkNp6NAUWYXanjCycYIDhCuSlR5DwgicnDaqnhrk44GoLQ/640?wx_fmt=jpeg&tp=webp&wxfrom=5");
		System.out.println(result);
	}

	public static JSONObject getQiNiuInfo() {
		JSONObject json = new JSONObject();


		// 生产环境
//		String qiniu_path = "http://7tsz1e.com1.z0.glb.clouddn.com/";
		String qiniu_path = "http://img.lanqiure.com/";
		String ACCESS_KEY = "scz23LP0R3h56PZJ5KswGT-8XNjHfr913OxhEKkl";
		String SECRET_KEY = "RKWejVwX9VU1woKR3hgH7oHf8i8DjoaF0tTo5Zje";
		String bucketName = "lanqiu";

		// 开发环境
//		 String qiniu_path="http://7xigsr.com1.z0.glb.clouddn.com/";
//		 String ACCESS_KEY = "5EcXRB01IAiLlfdKnGXH1GsyZujIbVIA5XtrviH5";
//		 String SECRET_KEY = "ntH0m3PaBovozCCy2AUUfVrQGQPW2HBqb0-hqOPR";
//		 String bucketName = "lbtest";
		//
		json.put("qiniu_path", qiniu_path);
		json.put("ACCESS_KEY", ACCESS_KEY);
		json.put("SECRET_KEY", SECRET_KEY);
		json.put("bucketName", bucketName);

		return json;
	}

	public static String GetImageStr(InputStream in) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "d:\\111.jpg";// 待处理的图片

		byte[] data = null;
		// 读取图片字节数组
		try {

			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	public static String uploadByImgUrl(String imgURL) {
		String result = "";
		try {

			// 构造URL
			URL url = new URL(imgURL);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 10000);
			// 输入流
			InputStream is = con.getInputStream();
			String targetfile = GetImageStr(is);
			result = uploadByIO(targetfile);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(result);
		return result;
	}

	/**
	 * 客户端上传图片到七牛服务器，客户端需要对图片进行base64加密，然后通过targetfile参数传递，备注：targetfile可自定义
	 * 
	 * @param targetfile
	 * @return
	 */
	public static String uploadByIO(String targetfile) {
		JSONObject json = getQiNiuInfo();
		String photoName = "";

		String qiniu_path = json.getString("qiniu_path");
		Config.ACCESS_KEY = json.getString("ACCESS_KEY");
		Config.SECRET_KEY = json.getString("SECRET_KEY");
		String bucketName = json.getString("bucketName");
		
//		String qiniu_path = "http://7tsz1e.com1.z0.glb.clouddn.com/";
//		Config.ACCESS_KEY = "scz23LP0R3h56PZJ5KswGT-8XNjHfr913OxhEKkl";
//		Config.SECRET_KEY = "RKWejVwX9VU1woKR3hgH7oHf8i8DjoaF0tTo5Zje";
		long time = 0;
		String fileName = "";
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		//String bucketName = "lanqiu";
		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = null;
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
		// String targetfile
		// =ServletActionContext.getRequest().getParameter(resource);

		try {
			BASE64Decoder base64 = new BASE64Decoder();
			InputStream in = new ByteArrayInputStream(
					base64.decodeBuffer(targetfile));
			// IoApi.putFile(uptoken,
			// "scz23LP0R3h56PZJ5KswGT-8XNjHfr913OxhEKkl",
			// "http://p.v.iask.com/517/773/136824735_1.jpg", extra);
			time = System.currentTimeMillis();
			fileName = time + ".jpg";
			IoApi.Put(uptoken, fileName, in, extra);

		} catch (Exception e) {
		}
		return qiniu_path + fileName;
	}

	/**
	 * 通过WEB上传文件到七牛服务器
	 * 
	 * @param resource
	 * @param resourceFileName
	 * @return
	 */
	public static String uploadToQiNiuByWEB(File resource,
			String resourceFileName) {
		ServletContext servletContext = ServletActionContext
				.getServletContext();
		String photoName = "";
		JSONObject json = getQiNiuInfo();
		 

		String qiniu_path = json.getString("qiniu_path");
		Config.ACCESS_KEY = json.getString("ACCESS_KEY");
		Config.SECRET_KEY = json.getString("SECRET_KEY");
		String bucketName = json.getString("bucketName");

		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);

		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);

		} catch (AuthException e1) {

			e1.printStackTrace();
		} catch (JSONException e1) {

			e1.printStackTrace();
		}
		PutExtra extra = new PutExtra();

		try {
			BASE64Decoder base64 = new BASE64Decoder();
			String photoType = PhotoUploadUtil.getPhotoType(resourceFileName);

			long time = System.currentTimeMillis();
			// 将FILE  转换成inputstream

			InputStream in = new FileInputStream(resource);
			photoName = time + photoType;
			System.out.println(photoName);
			IoApi.Put(uptoken, photoName, in, extra);
			qiniu_path = qiniu_path + photoName;
		} catch (Exception e) {
			qiniu_path = "";
		}
		return qiniu_path;
	}

	/**
	 * 通过图片路径上传文件到七牛服务器
	 * 
	 * @param resource
	 * @param resourceFileName
	 * @return
	 */
	public static String uploadToQiNiuByImgURL(String imgURL) {
		//对微信图片进行过滤处理
		if(imgURL.indexOf("tp=webp")!=-1){
			imgURL=imgURL.replace("tp=webp", "tp=jpg");
		}
		
		JSONObject json = getQiNiuInfo();
		String photoName = "";

		String qiniu_path = json.getString("qiniu_path");
		Config.ACCESS_KEY = json.getString("ACCESS_KEY");
		Config.SECRET_KEY = json.getString("SECRET_KEY");
		String bucketName = json.getString("bucketName");

		InputStream in = null;
		String result = "";
		try {

			// 构造URL
			URL url = new URL(imgURL);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 10000);
			// 输入流
			in = con.getInputStream();

			Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);

			PutPolicy putPolicy = new PutPolicy(bucketName);
			String uptoken = null;

			uptoken = putPolicy.token(mac);

			PutExtra extra = new PutExtra();

			BASE64Decoder base64 = new BASE64Decoder();
			String photoType = PhotoUploadUtil.getPhotoType(imgURL);
			// String photoType=".jpeg";
			long time = System.currentTimeMillis();
			// 将FILE  转换成inputstream

			photoName = time + photoType;
			System.out.println(photoName);
			IoApi.Put(uptoken, photoName, in, extra);
			qiniu_path = qiniu_path + photoName;
		} catch (Exception e) {
			qiniu_path = "";
		}
		return qiniu_path;
	}

 
	/**
	 * 下载图片到电脑,通过图片链接
	 * @param urlPath
	 * @param savaPath
	 * @param fileName
	 */
	public static String uploadToPCByImgURL(String imgUrl){
		String savaPath="http://172.26.145.4:8080/review/img/"+System.currentTimeMillis()+PhotoUploadUtil.getPhotoType(imgUrl);
		 
		URL url=null;
		//对微信图片进行过滤处理
				if(imgUrl.indexOf("tp=webp")!=-1){
					imgUrl=imgUrl.replace("tp=webp", "tp=jpg");
				}
				
		try {
			url = new URL(imgUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//：获取的路径
		 HttpURLConnection conn=null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        try {
				conn.setRequestMethod("GET");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        conn.setReadTimeout(6 * 10000);
	        try {
				if (conn.getResponseCode() <10000){
					 
				    InputStream inputStream = conn.getInputStream();
				     
				    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
				    int len=-1;
				    byte[] buffer=new byte[1024];
				    while((len=inputStream.read(buffer))!=-1){
				    	outputStream.write(buffer, 0, len);
				    }
				    outputStream.close();
				    inputStream.close();
				    byte[] data=outputStream.toByteArray();
				    
				    /*if(data.length>(1024*10))*/
				    if(1==1){
				        FileOutputStream outputStream2 = new FileOutputStream(savaPath);
				        outputStream2.write(data);
				        outputStream2.close();
				    }
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return savaPath;
	}

}
