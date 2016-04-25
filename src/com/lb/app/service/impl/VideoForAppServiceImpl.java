package com.lb.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.app.service.NewsForAppService;
import com.lb.app.service.VideoForAppService;
import com.lb.dao.BaseDao;
import com.lb.utils.TimeUtil;
@Service("videoForAppService")
@Component
public class VideoForAppServiceImpl implements VideoForAppService{
	BaseDao baseDao=null;
	JSONArray jsonArray=null;
	 
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public JSONArray getAllVideo(Integer pageNo, Integer pageSize) {
		long today=TimeUtil.getTodayDecimal();
		//String sql="select*from nba_video  where addTime>? ORDER BY  addTime desc LIMIT ?,?";
		String sql="select*from nba_video   ORDER BY  addTime desc LIMIT ?,?";
		Object[] params={(pageNo-1)*pageSize,pageSize};
		return baseDao.find(sql, params);
	}

	@Override
	public JSONObject getVideoById(Integer videoId) {
		String sql="select*from nba_video where id=?";
		Object [] params={videoId};
		return baseDao.findJsonObject(sql, params);
	}
	 

}
