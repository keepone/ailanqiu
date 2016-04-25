package com.lb.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.admin.service.BasketballCourtService;
import com.lb.dao.BaseDao;
import com.lb.utils.CreateDefinedPwdUtil;
import com.lb.utils.PageBean;
import com.lb.utils.StrUtil;
import com.lb.utils.TimeUtil;
@Component
public class BasketballCourtServiceImpl implements BasketballCourtService {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public JSONArray getReviewCourt() {
		String sql="SELECT * FROM lb_basketball_court WHERE status=0";
		return baseDao.find(sql, null);
	}

	
//	public PageBean getSchoolList(String startTime,String endTime,String schoolName,Integer pageNo,Integer pageSize,Integer userType) {
//		List params=new ArrayList();
//
//		String sql="SELECT court.name,phone,court.addTime,court.id,court_type FROM lb_basketball_court court inner join lb_user  user on user.id=court.user_id WHERE court_type>1 and user.user_type=? ";
//		
//		PageBean pageBean=new PageBean();
//		pageBean.setPageNo(pageNo);
//		Integer schoolCount=getSchoolListCount(startTime,endTime,schoolName,userType);
//		pageBean.setRowCount(schoolCount);
//		
//		
//		 
//		StringBuffer str=new StringBuffer();
//		str=str.append("SELECT court.id AS schoolId,user.id AS userId,court.name,phone,court.addTime,court.id,court_type FROM lb_basketball_court court inner join lb_user  user on user.id=court.user_id WHERE court_type>1 ");
//		if(StrUtil.isNotEmpty(String.valueOf(userType))){
//			str.append("  and user.user_type=?");
//			params.add(userType);
//		}
//		if(StrUtil.isNotEmpty(String.valueOf(schoolName))){
//			str.append("  and court.name like ?");
//			params.add("%"+schoolName+"%");
//		}
//		if(StrUtil.isNotEmpty(startTime)&&StrUtil.isNotEmpty(endTime)){
//			str.append(" AND  addTime between ? AND ?");
//			long time=TimeUtil.dateTo(startTime);
//			long time2=TimeUtil.dateTo(endTime);
//			params.add(time);
//			params.add(time2);
//		}else{
//			if(StrUtil.isNotEmpty(startTime)){
//				str.append(" AND  addTime>?");
//				long time=TimeUtil.dateTo(startTime);
//				params.add(time);
//			}
//			if(StrUtil.isNotEmpty(endTime)){
//				str.append(" AND  addTime<?");
//				long time=TimeUtil.dateTo(endTime);
//				params.add(time);
//			}
//		}
//		str.append(" ORDER BY court.addTime DESC LIMIT ?,?");
//		
//		params.add((pageNo-1)*pageSize);
//		params.add(pageSize);
//		//Object [] params={(pageNo-1)*pageSize,pageSize};
//		
//		JSONArray arr=baseDao.find(str.toString(), params.toArray());
//		Integer schoolUserCount=0;
//		for(int i=0;i<arr.size();i++){
//			JSONObject json=arr.getJSONObject(i);
//			 schoolUserCount=getSchoolUserCountBy(json.getInt("id"));
//			 String userId=CreateDefinedPwdUtil.encryp(json.getInt("userId"));
//			json.put("schoolUserCount", schoolUserCount);
//			json.put("userId", userId);
//		}
//		pageBean.setData(arr);
//		
//		return pageBean;
//	}
	
	public PageBean getSchoolList(Integer ifOKSchool,String startTime,String endTime,String schoolName,Integer pageNo,Integer pageSize,Integer userType) {
		List params=new ArrayList();
 
		PageBean pageBean=new PageBean();
		pageBean.setPageNo(pageNo);
		Integer schoolCount=getSchoolListCount(ifOKSchool,startTime,endTime,schoolName,userType);
		pageBean.setRowCount(schoolCount);
		
		
		 
		StringBuffer str=new StringBuffer();
		if(ifOKSchool==0){
			str=str.append("SELECT count(*) AS schoolUserCount, court.id AS schoolId,user.id AS userId,court.name,phone,court.addTime,court.id,court_type FROM lb_basketball_court court inner join lb_user  user on user.id=court.user_id WHERE court_type>1 and court.remark!='false'" );
		}else{
			str=str.append("SELECT count(*) AS schoolUserCount,court.id AS schoolId,user.id AS userId,court.name,user.phone,court.addTime,court.id,court_type from lb_user user INNER join  lb_user_team userTeam  inner JOIN lb_team team  inner JOIN  lb_basketball_court court on user.id=userTeam.user_id and userTeam.team_id=team.teamId and team.school_id=court.id    WHERE court_type>1  and court.remark!='false'  ");
			
		}
		
		if(StrUtil.isNotEmpty(String.valueOf(userType))){
			str.append("  and user.user_type=?");
			params.add(userType);
		}
		if(StrUtil.isNotEmpty(String.valueOf(schoolName))){
			str.append("  and court.name like ?");
			params.add("%"+schoolName+"%");
		}
		if(StrUtil.isNotEmpty(startTime)&&StrUtil.isNotEmpty(endTime)){
			str.append(" AND  court.addTime between ? AND ?");
			long time=TimeUtil.dateTo(startTime);
			long time2=TimeUtil.dateTo(endTime);
			params.add(time);
			params.add(time2);
		}else{
			if(StrUtil.isNotEmpty(startTime)){
				str.append(" AND  court.addTime>?");
				long time=TimeUtil.dateTo(startTime);
				params.add(time);
			}
			if(StrUtil.isNotEmpty(endTime)){
				str.append(" AND  court.addTime<?");
				long time=TimeUtil.dateTo(endTime);
				params.add(time);
			}
		}
//		str.append("  GROUP BY court.id   ORDER BY schoolUserCount DESC   LIMIT ?,?");
		str.append("  GROUP BY court.id   ORDER BY court.addTime DESC   LIMIT ?,?");
		params.add((pageNo-1)*pageSize);
		params.add(pageSize);
		//Object [] params={(pageNo-1)*pageSize,pageSize};
		
		JSONArray arr=baseDao.find(str.toString(), params.toArray());
//		Integer schoolUserCount=0;
//		for(int i=0;i<arr.size();i++){
//			JSONObject json=arr.getJSONObject(i);
//			 schoolUserCount=getSchoolUserCountBy(json.getInt("id"));
//			 String userId=CreateDefinedPwdUtil.encryp(json.getInt("userId"));
//			json.put("schoolUserCount", schoolUserCount);
//			json.put("userId", userId);
//		}
		pageBean.setData(arr);
		
		return pageBean;
	}
	
	public Integer getSchoolUserCountBy(Integer schoolId){
		String sql="SELECT count(*)  count from lb_user_team userTeam  inner JOIN lb_team team  inner JOIN  lb_basketball_court court on userTeam.team_id=team.teamId and team.school_id=court.id where team.school_id=?";
		Object [] params={schoolId};
		Integer count=baseDao.findJsonObject(sql, params).getInt("count");
		return count;
	}
//	public Integer getSchoolListCount(String startTime,String endTime,String schoolName,Integer userType) {
//		List params=new ArrayList();
//		StringBuffer str=new StringBuffer();
//		str=str.append("SELECT  COUNT(*) as count FROM lb_basketball_court court inner join lb_user  user on user.id=court.user_id WHERE court_type>1 ");
//		if(StrUtil.isNotEmpty(String.valueOf(userType))){
//			str.append("  and user.user_type=?");
//			params.add(userType);
//		}
//		if(StrUtil.isNotEmpty(String.valueOf(schoolName))){
//			str.append("  and court.name like ?");
//			params.add("%"+schoolName+"%");
//		}
//		if(StrUtil.isNotEmpty(startTime)&&StrUtil.isNotEmpty(endTime)){
//			str.append(" AND  addTime between ? AND ?");
//			long time=TimeUtil.dateTo(startTime);
//			long time2=TimeUtil.dateTo(endTime);
//			params.add(time);
//			params.add(time2);
//		}else{
//			if(StrUtil.isNotEmpty(startTime)){
//				str.append(" AND  addTime>?");
//				long time=TimeUtil.dateTo(startTime);
//				params.add(time);
//			}
//			if(StrUtil.isNotEmpty(endTime)){
//				str.append(" AND  addTime<?");
//				long time=TimeUtil.dateTo(endTime);
//				params.add(time);
//			}
//		}
//		return baseDao.findJsonObject(str.toString(), params.toArray()).getInt("count");
//	}
	
	public Integer getSchoolListCount(Integer ifOKSchool,String startTime,String endTime,String schoolName,Integer userType) {
		List params=new ArrayList();
		StringBuffer str=new StringBuffer();
		if(ifOKSchool==0){
			str=str.append("SELECT  COUNT(*) as count FROM lb_basketball_court court inner join lb_user  user on user.id=court.user_id WHERE court_type>1" );
		}else{
			str=str.append("SELECT  COUNT(*) as count FROM  lb_user user inner join lb_user_team userTeam inner join lb_basketball_court court on user.id=userTeam.user_id and user.id=court.user_id WHERE court_type>1 and court.remark!='false' ");
			
		}
		
		if(StrUtil.isNotEmpty(String.valueOf(userType))){
			str.append("  and user.user_type=?");
			params.add(userType);
		}
		if(StrUtil.isNotEmpty(String.valueOf(schoolName))){
			str.append("  and court.name like ?");
			params.add("%"+schoolName+"%");
		}
		if(StrUtil.isNotEmpty(startTime)&&StrUtil.isNotEmpty(endTime)){
			str.append(" AND  court.addTime between ? AND ?");
			long time=TimeUtil.dateTo(startTime);
			long time2=TimeUtil.dateTo(endTime);
			params.add(time);
			params.add(time2);
		}else{
			if(StrUtil.isNotEmpty(startTime)){
				str.append(" AND  court.addTime>?");
				long time=TimeUtil.dateTo(startTime);
				params.add(time);
			}
			if(StrUtil.isNotEmpty(endTime)){
				str.append(" AND  court.addTime<?");
				long time=TimeUtil.dateTo(endTime);
				params.add(time);
			}
		}
	 
		JSONObject json=baseDao.findJsonObject(str.toString(), params.toArray());
		return json.getInt("count");
	}

			@Override
	public Integer updateCourtStatus(Integer courtId,String tableName) {
		String sql="";
		if(tableName.equals("park")){
			sql="UPDATE lqr_park set status=1 WHERE park_id=?";
		}else{
			sql="UPDATE lb_basketball_court set status=1 WHERE id=?";
		}
		
		Object [] params={courtId};
		return baseDao.updateBy(sql, params);
	}

	@Override
	public Integer updateNational(Integer nationalId,String content) {
		String sql="UPDATE lb_national set introduce=? WHERE nationalId=?";
		Object [] params={content,nationalId};
		return baseDao.updateBy(sql, params);
	}

	@Override
	public JSONArray getReview(String tableName) {
		String sql="";
		if(tableName.equals("park")){
			sql="SELECT * FROM lqr_park  WHERE status=0";
		}else{
			sql="SELECT * FROM lb_basketball_court WHERE status=0";
		}
		 
		return baseDao.find(sql, null);
	}
}
