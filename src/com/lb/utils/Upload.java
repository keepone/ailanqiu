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
          
        //�ļ���ŵ�Ŀ¼  
        File tempDirPath =new File(request.getSession().getServletContext().getRealPath("/")+"\\upload\\");  
        if(!tempDirPath.exists()){  
            tempDirPath.mkdirs();  
        }  
          
        //���������ļ�����  
        DiskFileItemFactory fac = new DiskFileItemFactory();      
        //����servlet�ļ��ϴ����  
        ServletFileUpload upload = new ServletFileUpload(fac);      
        //�ļ��б�  
        List fileList = null;      
        //����request�Ӷ��õ�ǰ̨���������ļ�  
        try {      
            fileList = upload.parseRequest(request);      
        } catch (FileUploadException ex) {      
            ex.printStackTrace();      
            return;      
        }   
        //�������ļ���  
        String imageName = null;  
        //������ǰ̨�õ����ļ��б�  
        Iterator<FileItem> it = fileList.iterator();     
        while(it.hasNext()){      
            FileItem item =  it.next();     
            //���������ͨ���򣬵����ļ���������  
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
        //����ط������٣�����ǰ̨�ò����ϴ��Ľ��  
        out.write("1");  
        out.close();   
    }  
      
    /** 
     * Ajax�������� ��ȡ PrintWriter 
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

