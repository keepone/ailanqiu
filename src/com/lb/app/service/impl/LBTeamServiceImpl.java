package com.lb.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.app.service.LBTeamService;
import com.lb.dao.BaseDao;
@Service("lbTeamService")
public class LBTeamServiceImpl implements LBTeamService {
	BaseDao baseDao=null;
	JSONArray jsonArray=null;
	JSONObject json=null;
	
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	 

	@Override
	public JSONArray getLBTeamBy(Integer pageNo, Integer pageSize) {
		String sql="SELECT*FROM lb_team LIMIT ?,?";
		Object [] params={(pageNo-1)*pageSize,pageSize};
		jsonArray=baseDao.find(sql, params);
		return jsonArray;
	}

	@Override
	public JSONObject getLBTeamDetailBy(Integer teamId) {
		String sql="SELECT * FROM lb_team WHERE teamId=?";
		Object [] params={teamId};
		json=baseDao.findJsonObject(sql, params);
		return json;
	}



	@Override
	public Integer addLBTeam(String name, String img, String province,
			String city, String area, String phone) {
		String sql="INSERT INTO lb_team(name,img,phone,province,city,area) VALUES(?,?,?,?,?,?)";
		Object [] params={name,img,phone,province,city,area};
		return baseDao.insertBy(sql, params);
		
	}



	@Override
	public JSONArray getPlayersByTeamId(Integer teamId) {
		String sql="SELECT * FROM lb_player WHERE teamId=?";
		Object [] params={teamId};
		jsonArray=baseDao.find(sql, params);
		return jsonArray;
	}

}
