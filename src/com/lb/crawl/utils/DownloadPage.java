package com.lb.crawl.utils;
import java.io.BufferedReader;  
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.URL;  
public class DownloadPage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(DownloadPage.getUrlDetail("http://www.xici.net.co/",true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//获取链接地址的字符数据，wichSep是否换行标记  
    public static String getUrlDetail(String urlStr,boolean withSep) throws Exception  
    {  
        URL url = new URL(urlStr);  
        HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();  
        httpConn.connect();  
        InputStream cin = httpConn.getInputStream();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(cin,"UTF-8"));  
        StringBuffer sb = new StringBuffer();  
        String rl = null;  
        while((rl = reader.readLine()) != null)  
        {  
            if (withSep)  
            {  
                sb.append(rl).append(System.getProperty("line.separator"));  
            }  
            else  
            {  
                sb.append(rl);  
            }  
        }  
        return sb.toString();  
    }  

}
