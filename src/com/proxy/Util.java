package com.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Util {
	static OutputStreamWriter osw;
	
	static{
		try {
			File file = new File(Util.class.getResource(".").getPath()+"/ipSuccess.txt");
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//FileOutputStream out = new FileOutputStream(new File(Util.class.getResource(".").getPath())+"/ipSuccess.txt",true);
			FileOutputStream out = new FileOutputStream(Util.class.getResource("ipSuccess.txt").getPath(),true);
			osw = new OutputStreamWriter(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static List<String> readListFile(String filePath){
		List<String> urlList = new ArrayList<String>();
		File file=new File(filePath);
		if(file.isFile() && file.exists()){ //判断文件是否存在
			try {
				//考虑到编码格�?
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					urlList.add(lineTxt);
				}
				read.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return urlList;
	}
	

	
	public static void write(String str){
		try {
			System.out.println("IP写入文件�?======="+str);
			synchronized (osw) {
				osw.write(str);
				osw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
