package com.jiutong.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
/**
 * Ä£Äâ²âÊÔ
 * @author Allen
 *
 */
public class MyTest extends Thread {
    public static void main(String[] args) {
       // new MyTest().start();
    	//String d={"code":0,"msg":"OK","result":{"count":1,"fee":3,"sid":890560615}}
    	Logger logger = Logger.getLogger(Log.class);
		Logger log = Logger.getLogger("myTest1");  
    	InetAddress addr=null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	String ip="ÌÆ¶¨"; 
    	String  address="b"; 
    	log.info("   IP:----------------"+ip);  
        log.info("   PC_NAME:-----------"+address); 
    }
}