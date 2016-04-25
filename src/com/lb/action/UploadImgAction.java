package com.lb.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.MultiPartRequest;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Decoder;
 
import com.lb.utils.GetParameterUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.rs.PutPolicy;
@Namespace("/img")
@Controller
@Scope("prototype") 
public class UploadImgAction extends ActionSupport{
	private String content=null;
	
	 @Action(value = "UploadImgAction", results = {
				@Result(name = "success",  location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
    public String img() throws FileUploadException, IOException{ 
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out = response.getWriter();
         
        String savePath = request.getRealPath("/")+"attached/";
        File test = new File(savePath);
        if(!test.exists()){
            test.mkdirs();
        }
        //文件保存目录URL
        String saveUrl  = request.getContextPath() + "/attached/";
 
        //定义允许上传的文件扩展名
       // HashMap<string, string=""> extMap=new HashMap<string, string="">();
        HashMap<String,String> extMap=new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
//      extMap.put("flash", "swf,flv");
//      extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
//      extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
 
        //最大文件大小
        long maxSize = 1000000;
 
        response.setContentType("text/html; charset=UTF-8");
 
        if(!ServletFileUpload.isMultipartContent(request)){
            out.print(getError("请选择文件。"));
            return "err";
        }
        //检查目录
        File uploadDir = new File(savePath);
        if(!uploadDir.isDirectory()){
            out.print(getError("上传目录不存在。"));
            return "err";
        }
        //检查目录写权限
        if(!uploadDir.canWrite()){
            out.print(getError("上传目录没有写权限。"));
            return "err";
        }
 
        String dirName = request.getParameter("dir");
        if (dirName == null) {
            dirName = "image";
        }
        if(!extMap.containsKey(dirName)){
            out.print(getError("目录名不正确。"));
            return "err";
        }
        //创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
         
         
         MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper)request;  
            String fileName = wrapper.getFileNames("imgFile")[0];  
            File file = wrapper.getFiles("imgFile")[0];  
             
            
            
            
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
                out.print(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
                 
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
         
            saveUrl += newFileName;  
       
            FileOutputStream fos = new FileOutputStream(savePath + newFileName);  
            byte[] buffer = new byte[1024];  
            InputStream in = new FileInputStream(file);  
 
       		
       		
       		
            try {  
                int num = 0;  
                while ((num = in.read(buffer)) > 0) {  
                    fos.write(buffer, 0, num);  
                }  
            } catch (Exception e) {  
                e.printStackTrace(System.err);  
            } finally {  
                try{  
                    if(in != null)  
                        in.close();  
                    if(fos != null)  
                        fos.close();  
                }catch(IOException e){}  
            }  
       
            JSONObject obj = new JSONObject();  
            obj.put("error", 0);  
            obj.put("url", saveUrl);  
            
            System.out.println(saveUrl);
           out.print(obj.toJSONString());  
             
         
        return null;
    }
	 
	 /**
	  * 上传至七牛服务器
	  * @return
	  */
	 @Action(value = "UploadToQiNiuAction", results = {
				@Result(name = "success",  location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
 public String uploadToQiNiu() throws FileUploadException, IOException{ 
     HttpServletRequest request = ServletActionContext.getRequest();
     HttpServletResponse response = ServletActionContext.getResponse();
     PrintWriter out = response.getWriter();
     MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper)request;  
     String fileName = wrapper.getFileNames("imgFile")[0];  
     System.out.println(fileName);
     File file = wrapper.getFiles("imgFile")[0];  

     byte[] buffer = new byte[1024];  
     InputStream in = new FileInputStream(file);  
          
 
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
    		long time=System.currentTimeMillis();
         PutExtra extra = new PutExtra(); 
    		try {
    	 
    				IoApi.Put(uptoken, time+fileName,in, extra);
    		      
    		        
    		} catch (Exception e) {
    		}
    		
    		
    		
        
         JSONObject obj = new JSONObject();  
         obj.put("error", 0);  
         obj.put("url", "http://7tsz1e.com1.z0.glb.clouddn.com/"+time+fileName+"?imageView2/1/w/640/h/640");  
       
        out.print(obj.toJSONString());  
          
      
     return null;
 }
     
    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toJSONString();
    }
    
    
    @Action(value = "AddAction", results = {
			@Result(name = "success",  location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String test() {
		String result = SUCCESS;
		 
		String oneCateGoryId =content;
		System.out.println(oneCateGoryId);
		 
	 
		 return result;
	}

    
    @Action(value = "AppUploadImgAction", results = {
			@Result(name = "success",  location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
public String appUpload() throws FileUploadException, IOException{ 
 HttpServletRequest request = ServletActionContext.getRequest();
 HttpServletResponse response = ServletActionContext.getResponse();
 PrintWriter out = response.getWriter();
     
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
		String targetfile =ServletActionContext.getRequest().getParameter("resource");
		//InputStream   in_withcode   =   new   ByteArrayInputStream(targetfile.getBytes()); 
		try {
				BASE64Decoder base64 = new BASE64Decoder();
				InputStream in = new ByteArrayInputStream(base64.decodeBuffer(targetfile));
				IoApi.Put(uptoken, "lx.png",in, extra);
		       //IoApi.Put(uptoken, time+".jpg", in, extra);
		        
		} catch (Exception e) {
		}
     
     
      
  
 return null;
}
    
    
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
 
     
     
}
 
