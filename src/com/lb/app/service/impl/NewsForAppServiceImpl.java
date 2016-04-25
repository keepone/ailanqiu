package com.lb.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.app.service.NewsForAppService;
import com.lb.dao.BaseDao;
import com.lb.utils.TimeUtil;
@Service("newsForAppService")
@Component
public class NewsForAppServiceImpl implements NewsForAppService{
	BaseDao baseDao=null;
	JSONArray jsonArray=null;
	@Override
	public JSONArray getAllNews(Integer pageNo,Integer pageSize){
		String sql="SELECT id AS newsId,NAME AS name,LEFT(content,7) AS content, img FROM nba_news WHERE img!='' ORDER BY addTime desc LIMIT ?,?";
		Object [] params={(pageNo-1)*pageSize,pageSize};
		return baseDao.find(sql, params);
	}
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public JSONObject getNewsByNewsId(Integer newsId) {
		String sql="SELECT id AS newsId,NAME AS name, content, img,addTime FROM nba_news WHERE id=?";
		Object [] params={newsId};
		JSONObject json= baseDao.findJsonObject(sql, params);
		String time=String.valueOf(json.getLong("addTime"));
		String date=TimeUtil.toDate(time);
		json.put("time", date);
		json.remove("addTime");
		return json;
	}

}
