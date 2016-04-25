package com.lb.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.app.service.NewsForAppService;
import com.lb.app.service.TacticsForAppService;
import com.lb.app.service.VideoForAppService;
import com.lb.dao.BaseDao;
import com.lb.utils.TimeUtil;
@Service("tacticsForAppService")
@Component
public class TacticsForAppServiceImpl implements TacticsForAppService{
	BaseDao baseDao=null;
	JSONArray jsonArray=null;
	 
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public JSONArray getAllTactics(Integer pageNo, Integer pageSize) {
		String sql="select id,content,name from lb_tactics ORDER BY  addTime desc LIMIT ?,?";
		Object[] params={(pageNo-1)*pageSize,pageSize};
		return baseDao.find(sql, params);
	}

	@Override
	public JSONObject getTacticsById(Integer tacticsId) {
		String sql="select*from lb_tactics where id=?";
		Object[] params={tacticsId};
		return baseDao.findJsonObject(sql, params);
	}

 
	 

}
