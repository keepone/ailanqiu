package com.lb.dao;

import java.sql.ResultSet;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface BaseDao {
	/**
	 * 
	 * @param 根据sql语句查询
	 * @return 返回一个List
	 */
	public List findBy(String sql,Object[] params); 
	
	public JSONArray find(String sql,Object[] params); 
	
	/**
	 * 
	 * @param  sql语句
	 * @param  占位符参数
	 * @return 返回一个整数，判断更新操作是否成功
	 */
	public int updateBy(String sql,Object[] params);
 
	/**
	 * 执行插入操作
	 * @param sql
	 * @param params
	 * @return
	 */
	public int insertBy(String sql,Object[] params);

	/**
	 * 不推荐使用
	 * @param sql
	 * @param params
	 * @return
	 */
	public JSONObject findJsonObject(String sql, final Object[] params);
	
	/**
	 * 推荐使用
	 * @param sql
	 * @param params
	 * @return
	 */
	public JSONObject getJsonBy(String sql, Object[] params);
	
	 
	public List<JSONObject> findListJSONObject(String sql,Object[] params);
}
