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
		String fileName = "����ɭ";
		String directoryPath="E:\\personal\\������Ƶ";
		String oldFile="E:\\personal\\������Ƶ\\������Ƶ\\ �°볡40�֣�ԭ���Ʊ������˱��۳����ⳡ������.txt";
		String newFile="E:\\personal\\�Ƿ���Ƶ\\[������Ļ��]Bballbrea";
		 //downloadImage("http://mmbiz.qpic.cn/mmbiz/Ifu42jCUvzI2BEuaKibAcr5gsEnrd2LyiaS9eMloq88ia8udZT9ArZPGF8CYwpndZ7sChUGOw1uXkaAORlOzKIAqg/0?tp=webp&wxfrom=5", "", "/Users/Allen/LB_Img/�ƶ�.gif");
		//moveFile(oldFile, newFile);
		/*List<String> list=getDirectoryName(directoryPath);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		String content = "Hello\r\nwww.a.com";*/
		//writeFile(savePath, fileName, content);
		readTxtFileAsList(filePath);
		// createDirectory(savePath);
		
		//writeFile("/users/Allen/test.txt", "���\r\n" +"����");
	}

	/**
	 * �����ļ���
	 * 
	 * @param path
	 */
	public static void createDirectory(String path) {
		File file = new File(path);
		if (file.exists()) {
			System.out.println("�ļ��Ѵ���");
		} else {
			file.mkdir();
			System.out.println("�ļ������ɹ�");
		}
	}

	/**
	 * д��txt�ļ�
	 * 
	 * @param savePath
	 *            ����·��
	 * @param fileName
	 *            �ļ�����
	 * @param content
	 *            �ļ�����
	 */
	public static void writeFile(String savePath, String fileName,
			String content) {
		File file = new File(savePath + fileName + ".txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			System.out.println("�ļ��в�����");
			e.printStackTrace();
			return;
		}
		BufferedWriter writer = new BufferedWriter(fileWriter);
		try {
			writer.write(content);
		} catch (IOException e) {
			System.out.println(content + "д��ʧ��");
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
	 * ��ȡ�ļ�
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
	 * ��ȡ�ļ�
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
	 * ��ȡָ��Ŀ¼�������ļ������ƣ�����������Ŀ¼�Լ��༶��
	 * @param path
	 * @return
	 */
	public static List<String> getDirectoryName(String path){
		List<String> list=new ArrayList<String>();
		File file=new File(path);
		for(File f:file.listFiles()){
			//����ļ���ʽ���ļ��У����ȡ�ļ�������
			if(f.isDirectory()){
				String name=f.getName();
				list.add(name);
			}
		}
		return list;
		
	}
	
	
	/**
	 * ����ͼƬ������
	 * @param urlPath
	 * @param savaPath
	 * @param fileName
	 */
	public static void downloadImage(String urlPath,String savaPath,String fileName){
		URL url=null;
		//��΢��ͼƬ���й��˴���
				if(urlPath.indexOf("tp=webp")!=-1){
					urlPath=urlPath.replace("tp=webp", "tp=jpg");
				}
				
		try {
			url = new URL(urlPath);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//����ȡ��·��
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
