package com.lb.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.app.service.LBPlayerService;
import com.lb.dao.BaseDao;
@Service("lbPlayerService")
public class LBPlayerServiceImpl implements LBPlayerService {
	BaseDao baseDao=null;
	JSONArray jsonArray=null;
	JSONObject json=null;
	
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

 

	@Override
	public JSONObject getPlayerDetail(Integer playerId) {
		String sql="SELECT * FROM lb_player where playerId=?";
		Object [] params={playerId};
		return baseDao.findJsonObject(sql, params);
	}



	@Override
	public Integer addPlayer(String name, String img, Integer weight,
			Integer height, String birthday, Integer position, Integer clothNum,
			Integer teamId) {
		String sql="INSERT INTO lb_player(name,img,weight,height,birthday,position,clothNum,teamId) VALUES(?,?,?,?,?,?,?,?)";
		Object [] params={name,img,weight,height,birthday,position,clothNum,teamId};
		return baseDao.insertBy(sql, params);
	}

}
