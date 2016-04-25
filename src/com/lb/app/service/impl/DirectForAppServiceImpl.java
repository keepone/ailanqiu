package com.lb.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.app.service.DirectForAppService;
import com.lb.dao.BaseDao;
import com.lb.utils.TimeUtil;
@Service("directForAppService")
@Component
public class DirectForAppServiceImpl implements DirectForAppService{
	BaseDao baseDao=null;
	@Override
	public Map<String, JSONArray> getDirectSinceToday(Integer pageNo, Integer pageSize) {
		Map<String, JSONArray> map=new HashMap<String, JSONArray>();
		long today_decimal = TimeUtil.getTodayDecimal();
		String sql="SELECT DATE,WEEK FROM nba_direct WHERE DATE>1416844800000 OR DATE=1416844800000 GROUP BY DATE ";
		List<JSONObject> list_json=baseDao.findListJSONObject(sql, null);
		for(int i=0;i<list_json.size();i++){
			JSONObject json=list_json.get(i);
			long date=json.getLong("DATE");
			String week=json.getString("WEEK");
			String key=TimeUtil.toDay(String.valueOf(date))+" "+week;
			JSONArray arr=getDirectByDate(date);
			map.put(key, arr);
		}
		return map;
		  
	}
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public JSONArray getDirectByDate(long date) {
		long today_decimal = TimeUtil.getTodayDecimal();
		String sql = "select*from nba_direct where date=?";
		Object[] params={date};
		return baseDao.find(sql, params);
	}
}
