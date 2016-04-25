package com.jiutong.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;
 

public class Log {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Log.class);
		Logger log = Logger.getLogger("myTest1");  
        
         
        	String ip="f"; 
        	String  address="dd"; 
        	log.info("   IP:----------------"+ip);  
            log.info("   PC_NAME:-----------"+address);  
        
        

	}

}
