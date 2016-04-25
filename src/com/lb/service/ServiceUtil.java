package com.lb.service;

import com.lb.utils.PageBean;

import net.sf.json.JSONObject;

public interface ServiceUtil {
	/**
	 * 根据sql语句求数量
	 * @param sql
	 * @return
	 */
	 public Integer getCountBySql(String sql);
	 
	 public Integer getCountBy(String sql,Object [] params);
	 
	 /**
	  * 返回一个指定的字符串
	  * @param sql
	  * @param column
	  * @return
	  */
	 public String getStringBySql(String sql,String column);
	
	 
		public Integer getCountBySqlAndParams(String sql,Object [] params);
 
}
