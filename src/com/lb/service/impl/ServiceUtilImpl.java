package com.lb.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.dao.BaseDao;
import com.lb.service.DirectService;
import com.lb.service.ServiceUtil;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.utils.PageBean;
import com.lb.utils.TimeUtil;
import com.lucene.crawl.GetGameResult;
import com.lucene.crawl.GetNBAInfoUtil;
import com.lucene.crawl.GetTvDirectByBroadcast;
 

/**
 * 
 * @author Allen
 * @date 2014-11-9
 * @fuction
 * 
 */
@Service("serviceUtil")
//@Component
public class ServiceUtilImpl implements ServiceUtil {

	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Integer getCountBySql(String sql) {
		JSONArray arr=baseDao.find(sql, null);
		JSONObject json=(JSONObject) arr.get(0);
		Integer count=json.getInt("count");
		return count;
	}

	
	@Override
	public String getStringBySql(String sql,String column) {
		JSONArray arr=baseDao.find(sql, null);
		String str="";
		if(arr.size()>0){
			JSONObject json=(JSONObject) arr.get(0);
			 str=json.getString(column);
		}
		
		return str;
	}

	@Override
	public Integer getCountBy(String sql,Object [] params) {
		JSONArray arr=baseDao.find(sql, params);
		JSONObject json=(JSONObject) arr.get(0);
		Integer count=json.getInt("count");
		return count;
	}
	 
	public Integer getCountBySqlAndParams(String sql,Object [] params){
		Integer count=0;
		JSONObject json=baseDao.getJsonBy(sql, params);
		 
		if(json.containsValue(null)||json.size()==0){
			 
		}else{
			count=json.getInt("count");
		}
		return count;
	}
	
	
 

}
