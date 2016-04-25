package com.lb.service;

import com.lb.utils.PageBean;

import net.sf.json.JSONObject;

public interface ServiceUtil {
	/**
	 * ����sql���������
	 * @param sql
	 * @return
	 */
	 public Integer getCountBySql(String sql);
	 
	 public Integer getCountBy(String sql,Object [] params);
	 
	 /**
	  * ����һ��ָ�����ַ���
	  * @param sql
	  * @param column
	  * @return
	  */
	 public String getStringBySql(String sql,String column);
	
	 
		public Integer getCountBySqlAndParams(String sql,Object [] params);
 
}
