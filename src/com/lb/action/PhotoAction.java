package com.lb.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
 
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Decoder;

import com.lb.utils.PhotoUpload;
import com.lb.utils.Upload;
import com.opensymphony.xwork2.ActionSupport;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.rs.PutPolicy;
@Namespace("/photo")
@Controller
@Scope("prototype")
public class PhotoAction extends ActionSupport implements ServletRequestAware {
	/**
	 * 图片处理
	 * 裁剪正中部分，等比缩小生成200x200缩略图：
	   http://qiniuphotos.qiniudn.com/gogopher.jpg?imageView2/1/w/200/h/200
	   
	   宽度固定为200px，高度等比缩小，生成200x133缩略图：
	   http://qiniuphotos.qiniudn.com/gogopher.jpg?imageView2/2/w/200

		高度固定为200px，宽度等比缩小，生成300x200缩略图：
		http://qiniuphotos.qiniudn.com/gogopher.jpg?imageView2/2/h/200
	 */
	private String resourceFileName; // 保存文件名称
	private File resource;
	private String resourceContextType; // 保存文件类型
	private String default_img; // 用户默认头像名称
	private String savePath = "/photo";
	private String detailName;
	private String uptoken=null;
	private long time=0;
	 private static final long serialVersionUID = -1896915260152387341L;  
	    private HttpServletRequest request; 
	    private HttpServletResponse response=ServletActionContext.getResponse();
	    
	    public void setServletRequest(HttpServletRequest arg0) {
			this.request=arg0;
		}

	    @Action(value = "PhotoUploadAction", results = { @Result(name = "success", location = "/jsp/index.jsp") })
		public String admin() {
		 ServletContext servletContext = ServletActionContext
			.getServletContext();
		 String str=null;
		 String realPath = servletContext.getRealPath(savePath);
		 
		 
		 String qiniu_path="http://7tsz1e.com1.z0.glb.clouddn.com/";
		String imagename= PhotoUpload.uploadImg2(resource , resourceFileName);
//			String imagepath="http://192.168.254.50:18080/images/"+imagename;
			//Config.ACCESS_KEY = "hlVkFIkObKm8x16a_kmU6s49QlEdDYJJjPBniNFq";
	    // Config.SECRET_KEY = "bPWGES5FsngTx20CsC5JjItuJRIfIKc_vl_GV8H_";
			Config.ACCESS_KEY = "scz23LP0R3h56PZJ5KswGT-8XNjHfr913OxhEKkl";
			Config.SECRET_KEY = "RKWejVwX9VU1woKR3hgH7oHf8i8DjoaF0tTo5Zje";
			
	     Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
	     String bucketName = "lanqiu";
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
					//IoApi.putFile(uptoken, "scz23LP0R3h56PZJ5KswGT-8XNjHfr913OxhEKkl", "http://p.v.iask.com/517/773/136824735_1.jpg", extra);
					
					//将FILE  转换成inputstream
					InputStream in = new FileInputStream(resource);
					IoApi.Put(uptoken, time+".jpg", in, extra);
			        
			} catch (Exception e) {
			}
		return "";	
	    }
	
/**
 * 上传至七牛服务器
 * @return
 */
 @Action(value = "UploadToQiNiuAction", results = { @Result(name = "success", location = "/jsp/qiniu.jsp") })
	public String uploadToQiNiu() {
	  time = System.currentTimeMillis();
	  Config.ACCESS_KEY = "scz23LP0R3h56PZJ5KswGT-8XNjHfr913OxhEKkl";
		Config.SECRET_KEY = "RKWejVwX9VU1woKR3hgH7oHf8i8DjoaF0tTo5Zje";
	
		        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		        String bucketName = "lanqiu";
		        JSONObject json=new JSONObject();
		        JSONObject j=new JSONObject();
		        j.put("name", "$(fname)");
		      
		      json.put("scope", bucketName);
		     // json.put("callbackBody", j);
		      json.put("callbackUrl", "http://121.41.25.90:8080/lb/");
		      System.out.println(json.toString());
		       // PutPolicy putPolicy = new PutPolicy(json.toString());
		      PutPolicy putPolicy = new PutPolicy(bucketName);
		        uptoken=null;
				try {
					uptoken = putPolicy.token(mac);
					System.out.println("----"+uptoken);
				} catch (AuthException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		         
				 
				 
	 
		return SUCCESS;
	}
 
 
	 

public File getResource() {
	return resource;
}

public void setResource(File resource) {
	this.resource = resource;
}

public HttpServletRequest getRequest() {
	return request;
}

public void setRequest(HttpServletRequest request) {
	this.request = request;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

	public String getResourceFileName() {
		return resourceFileName;
	}
	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}

	 



	public String getResourceContextType() {
		return resourceContextType;
	}
	public void setResourceContextType(String resourceContextType) {
		this.resourceContextType = resourceContextType;
	}
	public String getDefault_img() {
		return default_img;
	}
	public void setDefault_img(String defaultImg) {
		default_img = defaultImg;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getUptoken() {
		return uptoken;
	}

	public void setUptoken(String uptoken) {
		this.uptoken = uptoken;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}



	



 
	 
	
}
