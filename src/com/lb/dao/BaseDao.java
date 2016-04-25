package com.lb.dao;

import java.sql.ResultSet;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface BaseDao {
	/**
	 * 
	 * @param ����sql����ѯ
	 * @return ����һ��List
	 */
	public List findBy(String sql,Object[] params); 
	
	public JSONArray find(String sql,Object[] params); 
	
	/**
	 * 
	 * @param  sql���
	 * @param  ռλ������
	 * @return ����һ���������жϸ��²����Ƿ�ɹ�
	 */
	public int updateBy(String sql,Object[] params);
 
	/**
	 * ִ�в������
	 * @param sql
	 * @param params
	 * @return
	 */
	public int insertBy(String sql,Object[] params);

	/**
	 * ���Ƽ�ʹ��
	 * @param sql
	 * @param params
	 * @return
	 */
	public JSONObject findJsonObject(String sql, final Object[] params);
	
	/**
	 * �Ƽ�ʹ��
	 * @param sql
	 * @param params
	 * @return
	 */
	public JSONObject getJsonBy(String sql, Object[] params);
	
	 
	public List<JSONObject> findListJSONObject(String sql,Object[] params);
}
