package com.lb.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.lb.utils.PhotoUpload;

public class IoUtil {
	 
	public static void main(String[] args) {
		String savePath = "D:/lucenetest/";
		String filePath = "/Users/Allen/team/1.txt";
		String fileName = "艾弗森";
		String directoryPath="E:\\personal\\虎扑视频";
		String oldFile="E:\\personal\\虎扑视频\\新鲜视频\\ 下半场40分！原来科比最经典过人爆扣出自这场比赛啊.txt";
		String newFile="E:\\personal\\非法视频\\[虎扑字幕组]Bballbrea";
		 //downloadImage("http://mmbiz.qpic.cn/mmbiz/Ifu42jCUvzI2BEuaKibAcr5gsEnrd2LyiaS9eMloq88ia8udZT9ArZPGF8CYwpndZ7sChUGOw1uXkaAORlOzKIAqg/0?tp=webp&wxfrom=5", "", "/Users/Allen/LB_Img/唐定.gif");
		//moveFile(oldFile, newFile);
		/*List<String> list=getDirectoryName(directoryPath);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		String content = "Hello\r\nwww.a.com";*/
		//writeFile(savePath, fileName, content);
		readTxtFileAsList(filePath);
		// createDirectory(savePath);
		
		//writeFile("/users/Allen/test.txt", "你好\r\n" +"我是");
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 */
	public static void createDirectory(String path) {
		File file = new File(path);
		if (file.exists()) {
			System.out.println("文件已存在");
		} else {
			file.mkdir();
			System.out.println("文件创建成功");
		}
	}

	/**
	 * 写入txt文件
	 * 
	 * @param savePath
	 *            保存路径
	 * @param fileName
	 *            文件名称
	 * @param content
	 *            文件内容
	 */
	public static void writeFile(String savePath, String fileName,
			String content) {
		File file = new File(savePath + fileName + ".txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			System.out.println("文件夹不存在");
			e.printStackTrace();
			return;
		}
		BufferedWriter writer = new BufferedWriter(fileWriter);
		try {
			writer.write(content);
		} catch (IOException e) {
			System.out.println(content + "写入失败");
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 读取文件
	 * @param filePath
	 */
	public static void readFile(String filePath) {
		File file = new File(filePath);
		BufferedReader reader = null;
		for (File f : file.listFiles()) {
			try {
				reader = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			while (line.trim().length() > 0) {
				System.out.println(f.getName() + "--" + line);
				try {
					line = reader.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	
	
	/**
	 * 读取文件
	 * @param filePath
	 */
	public static List<String> readTxtFileAsList(String filePath) {
		File file = new File(filePath);
		List<String> ips=new ArrayList<String>();
		BufferedReader reader = null;
		
			try {
//				reader = new BufferedReader(new FileReader(file));
				try {
					reader=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line = null;
			try {
				while((line = reader.readLine())!=null){
					if(!line.equals("")){
						ips.add(line);
						System.out.println(line);
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return ips;

	}
	/**
	 * 获取指定目录的所有文件夹名称（不包含二级目录以及多级）
	 * @param path
	 * @return
	 */
	public static List<String> getDirectoryName(String path){
		List<String> list=new ArrayList<String>();
		File file=new File(path);
		for(File f:file.listFiles()){
			//如果文件格式是文件夹，则获取文件夹名称
			if(f.isDirectory()){
				String name=f.getName();
				list.add(name);
			}
		}
		return list;
		
	}
	
	
	/**
	 * 下载图片到电脑
	 * @param urlPath
	 * @param savaPath
	 * @param fileName
	 */
	public static void downloadImage(String urlPath,String savaPath,String fileName){
		URL url=null;
		//对微信图片进行过滤处理
				if(urlPath.indexOf("tp=webp")!=-1){
					urlPath=urlPath.replace("tp=webp", "tp=jpg");
				}
				
		try {
			url = new URL(urlPath);
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
				        FileOutputStream outputStream2 = new FileOutputStream(fileName);
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
	}
	
	
	 public static void moveFile(String oldFile,String newFile)
	    {   
	        try{
	  
	           File afile =new File(oldFile);
	  
	           if(afile.renameTo(new File(newFile))){
	            System.out.println("File is moved successful!");
	           }else{
	            System.out.println("File is failed to move!");
	           }
	  
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }
}
