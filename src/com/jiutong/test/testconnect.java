package com.jiutong.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testconnect {

	/**
	 * 创建数据库连接
	 */
	Connection con = null;  //创建用于连接数据库的Connection对象  
	 public  Connection getConnection() {  
	       
	        try {  
	            Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动  
	              
	            con = DriverManager.getConnection(  
	                    "jdbc:mysql://61.129.52.141:3306/db_pichouzz", "deve", "?;p09ol.<");// 创建数据连接  
	            System.out.println("--connect ok------");
	        } catch (Exception e) {  
	            System.out.println("数据库连接失败" + e.getMessage());  
	        }  
	        return con; //返回所建立的数据库连接  
	    }
	 
	 /**
	  * 关闭数据库连接
	  */
	 public void closeConnect(){
		 try {
			if(!con.isClosed()){
				 con.close();
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
