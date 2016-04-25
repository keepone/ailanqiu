package com.lb.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.app.service.LBSForAppService;
import com.lb.app.service.NewsForAppService;
import com.lb.dao.BaseDao;
import com.lb.utils.TimeUtil;
@Service("lbsForAppService")
public class LBSForAppServiceImpl implements LBSForAppService{
	BaseDao baseDao=null;
	JSONArray jsonArray=null;
	 
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public JSONArray getCourt(Integer pageNo,String lng,String lat) {
		// 0.00001=1m  0.01=1km
		//经度：lng  0-180度
		//纬度: lat 0-90度
		double constant=0.05;
		double lg=Double.valueOf(lng);
		double la=Double.valueOf(lat);
		
		double lng_min=lg-constant;
		double lng_max=la+constant;
		double lat_min=la-constant;
		double lat_max=la+constant;
		String sql="select*from lb_basketball_court  where (((lng between ? and ?) and (lat between ? and ?))||((lng between ? and ?) and (lat between ? and ?)))";
	 
		//String sql="select*from lb_basketball_court  where ((lng between ? and ?) and (lat between ? and ?))";
		
		Object [] params={lng_min,lg,lat_min,la,lg,lng_max,la,lat_max};
		//Object [] params={lng_min,lg,lat_min,la};
			
		JSONArray arr=baseDao.find(sql, params);
		return arr;
	}

	@Override
	public JSONObject getCourtDetail(Integer courtId) {
		String sql="SELECT * FROM lb_basketball_court where id=?";
		Object [] params={courtId};
		return baseDao.findJsonObject(sql, params);
	}
	

}
