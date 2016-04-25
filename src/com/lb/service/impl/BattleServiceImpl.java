package com.lb.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.BattleService;
import com.lb.service.ServiceUtil;
import com.lb.utils.MapUtil;

@Service("battleService")
@Component
public class BattleServiceImpl implements BattleService{
	
	private BaseDao baseDao=null;
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Autowired
	ServiceUtil serviceUtil=null;
	
public Integer addSchool(Integer courtType,Integer userId, String schoolName, String areaDetail) {
		
		String IF_SQL="SELECT COUNT(*) AS count FROM lb_basketball_court WHERE name=?";
		Object [] IF_PARAMS={schoolName};
		Integer ifHave=serviceUtil.getCountBySqlAndParams(IF_SQL, IF_PARAMS);
		Integer count=0;
		if(ifHave>0){
			
		}else{
			String sql="INSERT INTO lb_basketball_court(remark,status,areaDetail,user_id,name,lng,lat,addTime,court_type) VALUES(?,?,?,?,?,?,?,?,?)";
			JSONObject json=MapUtil.getLngAndLat(areaDetail);
			double lng=0;
		    double lat=0;
		    if(json!=null&&json.size()>0){
		    	lng=json.getDouble("lng");
		    	lat=json.getDouble("lat");
		    }
		    Object [] params={"false",1,areaDetail,userId,schoolName,lng,lat,System.currentTimeMillis(),courtType};
			Integer schoolId= baseDao.insertBy(sql, params);
			count=updateHomeCourt(schoolId, userId);//更新主场
		}
		return count;
	}


	public Integer updateHomeCourt(Integer schoolId, Integer userId) {
		String sql="UPDATE lb_user SET home_court=? WHERE id=?";
		Object [] params={schoolId,userId};
		return baseDao.updateBy(sql, params);
	}
	
	
	public Integer addUser(String userName,String userImg,Integer userType,String password,Integer phone,Integer height,Integer weight){
		String sql="INSERT INTO lb_user(user_name,user_img,user_type,password,phone,height,weight) VALUES(?,?,?,?,?,?,?)";
		Object [] params={userName,userImg,userType,password,phone,height,weight};
		Integer count=0;
		String sql1 = "select count(*) AS count from lb_user where user_name='"
					+ userName + "'";
		JSONObject json=baseDao.findJsonObject(sql1, null);
		Integer sum=json.getInt("count");
//			Integer sum = Integer
//					.parseInt(((Map) baseDao.findBy(sql1, null)
//							.get(0)).get("count").toString());
			
			if(sum>0){
				Object [] params2={userName+"111",userImg,userType,password,phone,height,weight};
				count= baseDao.insertBy(sql, params2);
			}else{
				 baseDao.insertBy(sql, params);
			}
			
		return count;
		
	}
	
	
	public Integer addSchoolTeam(String grade,String remark,String teamName, String teamImg,
			Integer school_id,Integer userId) {
		String IF_SQL="SELECT COUNT(*) AS count FROM lb_team WHERE teamName=?";
		Object [] IF_PARAMS={teamName};
		Integer ifHave=serviceUtil.getCountBySqlAndParams(IF_SQL, IF_PARAMS);
		Integer count=0;
		if(ifHave>0){
			
		}else{
			String sql="INSERT INTO lb_team(grade,remark,teamName,teamImg,school_id,add_time,user_id,team_type) VALUES(?,?,?,?,?,?,?,?)";
			Object [] params={grade,remark,teamName,teamImg,school_id,System.currentTimeMillis(),userId,2};
			count=baseDao.insertBy(sql, params);
		}
	 
		return count;
	}

	
	public Integer addUserToTeam(Integer userId, Integer teamId) {
		String COUNT_SQL="SELECT count(*) AS count FROM lb_user_team WHERE user_id=?";
		Object [] COUNT_PARAMS={userId};
		Integer ifHave=serviceUtil.getCountBySqlAndParams(COUNT_SQL, COUNT_PARAMS);
		
		String sql="INSERT INTO lb_user_team(user_id,team_id,add_time) VALUE(?,?,?)";
		Object [] params={userId,teamId,System.currentTimeMillis()};
		Integer count=0;
		if(ifHave>0){
			count=-1;
		}else{
			count=baseDao.insertBy(sql, params);
		}
		return count;
	}
}
