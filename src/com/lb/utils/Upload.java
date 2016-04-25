package com.lb.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.struts2.ServletActionContext;

public class Upload extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        this.doPost(request, response);  
    }  
  
    @SuppressWarnings("unchecked")  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
          
        //文件存放的目录  
        File tempDirPath =new File(request.getSession().getServletContext().getRealPath("/")+"\\upload\\");  
        if(!tempDirPath.exists()){  
            tempDirPath.mkdirs();  
        }  
          
        //创建磁盘文件工厂  
        DiskFileItemFactory fac = new DiskFileItemFactory();      
        //创建servlet文件上传组件  
        ServletFileUpload upload = new ServletFileUpload(fac);      
        //文件列表  
        List fileList = null;      
        //解析request从而得到前台传过来的文件  
        try {      
            fileList = upload.parseRequest(request);      
        } catch (FileUploadException ex) {      
            ex.printStackTrace();      
            return;      
        }   
        //保存后的文件名  
        String imageName = null;  
        //便利从前台得到的文件列表  
        Iterator<FileItem> it = fileList.iterator();     
        while(it.hasNext()){      
            FileItem item =  it.next();     
            //如果不是普通表单域，当做文件域来处理  
            if(!item.isFormField()){  
            imageName = new Date().getTime()+Math.random()*10000+item.getName();  
                BufferedInputStream in = new BufferedInputStream(item.getInputStream());     
                BufferedOutputStream out = new BufferedOutputStream(        
                        new FileOutputStream(new File(tempDirPath+"\\"+imageName)));  
                Streams.copy(in, out, true);  
                  
            }  
        }  
        //  
        PrintWriter out = null;  
        try {  
            out = encodehead(request, response);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //这个地方不能少，否则前台得不到上传的结果  
        out.write("1");  
        out.close();   
    }  
      
    /** 
     * Ajax辅助方法 获取 PrintWriter 
     * @return 
     * @throws IOException  
     * @throws IOException  
     * request.setCharacterEncoding("utf-8"); 
        response.setContentType("text/html; charset=utf-8"); 
     */  
    private PrintWriter encodehead(HttpServletRequest request,HttpServletResponse response) throws IOException{  
        request.setCharacterEncoding("utf-8");  
        response.setContentType("text/html; charset=utf-8");  
        return response.getWriter();  
    }  
} 

