/**
 * @author allen
 * @date 2014/8/1
 */
package com.lb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import com.lb.dao.BaseDao;
import com.lb.utils.JSONPropertyRowMapper;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

//注解  
@Controller("baseDAO")
public class BaseDaoImpl extends JdbcDaoSupport implements BaseDao {
	public List findBy(String sql, Object[] params) {
		List list= this.getJdbcTemplate().queryForList(sql, params);
		try {
			this.getConnection().close();
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(RecoverableDataAccessException e8 ){
			System.out.println("大错误被我捕捉");
		}catch(CommunicationsException ce){
			System.out.println("大错误被我捕捉");
		}catch (SQLException e) {
			System.out.println("大错误被我捕捉");
		}catch (Exception ee){
			System.out.println("大错误被我捕捉");
		}
		return list;
		 
	}
	@Override
	public JSONArray find(String sql, Object[] params) {
		List list= this.getJdbcTemplate().queryForList(sql, params);
		JSONArray jsonArray=JSONArray.fromObject(list);
		try {
			this.getConnection().close();
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(RecoverableDataAccessException e8 ){
			System.out.println("大错误被我捕捉");
		}catch(CommunicationsException ce){
			System.out.println("大错误被我捕捉");
		}catch (SQLException e) {
			System.out.println("大错误被我捕捉");
		}catch (Exception ee){
			System.out.println("大错误被我捕捉");
		}
		 
		return jsonArray;
	}

	public int updateBy(String sql, Object[] params) {
		return this.getJdbcTemplate().update(sql, params);
		 
	}

	public int insertBy(String sql, final Object[] params) {
		final String contentsql = sql;
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection conn)
		      throws SQLException {
		     PreparedStatement ps = conn.prepareStatement(contentsql,Statement.RETURN_GENERATED_KEYS);
		     for(int i=1;i<=params.length;i++){
		    	 
		    	 ps.setString(i, params[i-1].toString());
		     }
		         return ps;
		    }
		   },holder);
		int contentid = holder.getKey().intValue();
		try {
			this.getConnection().close();
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentid;
	}

	public List<JSONObject> findListJSONObject(String sql,Object[] params){
		
		List<JSONObject> list=this.getJdbcTemplate().query(sql, params,new JSONPropertyRowMapper());
		try {
			this.getConnection().close();
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	/**
	 * 大刘写的
	 */
	public JSONObject findJsonObject(String sql, final Object[] params){
		
		JSONObject json=this.getJdbcTemplate().queryForObject(sql,
				params, new JSONPropertyRowMapper());
		try {
			this.getConnection().close();
			  
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject getJsonBy(String sql, Object[] params) {
		List list= this.getJdbcTemplate().queryForList(sql, params);
		
		JSONObject json=new JSONObject();
		if(list!=null&&list.size()>0){
			JSONArray jsonArray=JSONArray.fromObject(list);
			json=jsonArray.getJSONObject(0);
		}
		try {
			this.getConnection().close();
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
		 
	}

}
