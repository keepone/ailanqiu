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
import com.lb.service.GameDataService;
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
@Service("gameDataService")
@Component
public class GameDataServiceImpl implements GameDataService {

	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public JSONArray getGameDataByDirectId(Integer directId,String team) {
		String sql="select*from nba_game_data where directId=? and team=?";
		Object[] params={directId,team};
		JSONArray arr=baseDao.find(sql, params);
		return arr;
	}

	 

}
