package com.lb.app.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.app.service.CommonForAppService;
import com.lb.dao.BaseDao;
import com.lb.utils.PageBean;
@Service("commonForAppService")
@Component
public class CommonForAppServiceImpl implements CommonForAppService {
	BaseDao baseDao=null;
	@Override
	public List<JSONObject> getAllBestPlayer() {
		String sql="select*from nba_best_player";
		return baseDao.findListJSONObject(sql, null);
	}
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public List<JSONObject> getSalary() {
		String sql="select*from nba_best_player";
		return baseDao.findListJSONObject(sql, null);
		 
	}
	@Override
	public List<JSONObject> getChampion() {
		String sql="select*from nba_champion order by id desc";
		return baseDao.findListJSONObject(sql, null);
	}
}
