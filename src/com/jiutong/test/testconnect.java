package com.jiutong.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testconnect {

	/**
	 * �������ݿ�����
	 */
	Connection con = null;  //���������������ݿ��Connection����  
	 public  Connection getConnection() {  
	       
	        try {  
	            Class.forName("com.mysql.jdbc.Driver");// ����Mysql��������  
	              
	            con = DriverManager.getConnection(  
	                    "jdbc:mysql://61.129.52.141:3306/db_pichouzz", "deve", "?;p09ol.<");// ������������  
	            System.out.println("--connect ok------");
	        } catch (Exception e) {  
	            System.out.println("���ݿ�����ʧ��" + e.getMessage());  
	        }  
	        return con; //���������������ݿ�����  
	    }
	 
	 /**
	  * �ر����ݿ�����
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
