package test;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jiutong.test.Log;

public class Log4j {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyConfigurator.configure( "/Users/Allen/tomcat7/webapps/ROOT/WEB-INF/log4j/log4j.properties" ); 
//		Logger logger = Logger.getLogger(Log4j.class ); 
//		logger.info( "debug " ); 
		//Logger logger = Logger.getLogger(Log.class);
		Logger log = Logger.getLogger("debug");  
    	log.debug(" ß∞‹≤‚ ‘\r\n--------------------------------------------------------------------------------------------\r\n");  
    	
	 
	 
	}
	public  void getContextRealPath()  
	{  
		  File f = new File(this.getClass().getResource("/").getPath());
	         System.out.println(f);
	}  
}
