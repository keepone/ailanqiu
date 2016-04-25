package com.lb.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.dao.BaseDao;
import com.lb.service.TeamService;
/**
 * 
 * @author Allen
 * @date 2014-11-9
 * @fuction  
 *
 */
@Service("teamService")
public class TeamServiceImpl implements TeamService {

	 

	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Integer addTeam(String teamName,String logo,String sourceLogo, String homeCourt, String area,
			String areaDetail, String teamInroduce, String coach, String boss,
			String manage, String address) {
		String sql="insert into nba_team( name, logo,sourceLogo, home_court,  area,area_detail,  introduce,  coach,  boss,manage,  address) values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params={ teamName,logo,sourceLogo,  homeCourt,  area,areaDetail,  teamInroduce,  coach,  boss,manage,  address};
		return baseDao.insertBy(sql, params);
	}
}
