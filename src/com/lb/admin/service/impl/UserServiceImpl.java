package com.lb.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.JsonObject;
import com.lb.admin.service.UserService;
import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
 
import com.lb.utils.CreateDefinedPwdUtil;
import com.lb.utils.JpushUtil;
import com.lb.utils.PageBean;
import com.lb.utils.PasswordUtil;
import com.lb.utils.StrUtil;
import com.lb.utils.TimeUtil;
 
@Component
public class UserServiceImpl  implements UserService{
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Autowired
	ServiceUtil serviceUtil=null;
	 
	
	
	public JSONArray getBattleList(){
		String sql="SELECT m.user_name,m.phone,m.`status`,team.teamName AS hostTeam,m.teamName as guestTeam,m.guest_score,m.host_score,m.`name`,m.add_time,m.update_time from (SELECT t.teamName,b.host_team,b.`status`,b.guest_score,b.host_score,b.school_id,c.`name`,b.add_time,b.update_time,user.phone,user.user_name from lb_battle b INNER JOIN lb_team t inner join lb_basketball_court c INNER join lb_user user on b.guest_team=t.teamId and b.school_id=c.id and c.user_id=user.id     WHERE b.`status`>0 ORDER BY b.add_time desc ) m inner join lb_team team on team.teamId=m.host_team";
		Object [] params={null};
		JSONArray arr=baseDao.find(sql, null);
		return arr;
	}
	
	public Integer deleteUserBy(Integer userId) {
		String sql="DELETE FROM lb_dynamic_leave WHERE user_id=?";  //删除留言
		Object [] params={userId};
		Integer count=baseDao.updateBy(sql, params);
		sql="DELETE FROM lb_dynamic WHERE user_id=?";	//删除动态
		Object [] params2={userId};
		
		if(count>=0){
			count=baseDao.updateBy(sql, params2);
			if(count>=0){
				sql="DELETE FROM lb_dynamic_agree WHERE user_id=? || reply_user_id=?";	//删除点赞
				Object [] params3={userId,userId};
				count=baseDao.updateBy(sql, params3);
				if(count>=0){
					sql="DELETE FROM lb_user_focus WHERE user_id=? || focus_user_id=?";		//删除关注
					Object [] params4={userId,userId};
					count=baseDao.updateBy(sql, params4);
					if(count>=0){
						sql="DELETE FROM lb_user WHERE id=?";	//删除用户
						Object [] params5={userId};
						count=baseDao.updateBy(sql, params5);
					}
				}
			}
		}
		
		
		return count;
	}
	public PageBean getUserListBy(String startTime,String endTime,Integer userType,Integer pageNo, Integer pageSize) {
		PageBean pageBean=new PageBean();
		pageBean.setPageNo(pageNo);
		Integer userCount=getUserListCountBy(startTime,endTime,userType);
		pageBean.setRowCount(userCount);
		
		List params=new ArrayList();
	 
		 
		StringBuffer str=new StringBuffer();
		str=str.append("SELECT * FROM 	 lb_user  where 1=1 ");
		if(StrUtil.isNotEmpty(String.valueOf(userType))){
			str.append(" AND  user_type=?");
			params.add(userType);
		}
		if(StrUtil.isNotEmpty(startTime)&&StrUtil.isNotEmpty(endTime)){
			str.append(" AND  create_time between ? AND ?");
			long time=TimeUtil.dateTo(startTime);
			long time2=TimeUtil.dateTo(endTime);
			params.add(time);
			params.add(time2);
		}else{
			if(StrUtil.isNotEmpty(startTime)){
				str.append(" AND  create_time>?");
				long time=TimeUtil.dateTo(startTime);
				params.add(time);
			}
			if(StrUtil.isNotEmpty(endTime)){
				str.append(" AND  create_time<?");
				long time=TimeUtil.dateTo(endTime);
				params.add(time);
			}
		}
		
		
		str.append(" ORDER BY  create_time DESC LIMIT ?,?");
		params.add((pageNo-1)*pageSize);
		params.add(pageSize);
		JSONArray arr=baseDao.find(str.toString(), params.toArray());
		pageBean.setData(arr);
	
		return pageBean;
	}
	
	public Integer getUserListCountBy(String startTime,String endTime,Integer userType) {
		List params=new ArrayList();
		StringBuffer str=new StringBuffer();
		str=str.append("SELECT COUNT(*) AS count FROM lb_user  WHERE 1=1 ");
		if(StrUtil.isNotEmpty(String.valueOf(userType))){
			str.append(" AND  user_type=?");
			params.add(userType);
		}
		if(StrUtil.isNotEmpty(startTime)&&StrUtil.isNotEmpty(endTime)){
			str.append(" AND  create_time between ? AND ?");
			long time=TimeUtil.dateTo(startTime);
			long time2=TimeUtil.dateTo(endTime);
			params.add(time);
			params.add(time2);
		}else{
			if(StrUtil.isNotEmpty(startTime)){
				str.append(" AND  create_time>?");
				long time=TimeUtil.dateTo(startTime);
				params.add(time);
			}
			if(StrUtil.isNotEmpty(endTime)){
				str.append(" AND  create_time<?");
				long time=TimeUtil.dateTo(endTime);
				params.add(time);
			}
		}
		JSONObject json=baseDao.findJsonObject(str.toString(),params.toArray());
		Integer count=json.getInt("count");
		return count;
	}
	
	
	
	public Integer addFocusBy(Integer userId, Integer focudUserId) {
		String sql="INSERT INTO lb_user_focus(user_id,focus_user_id,add_time) VALUES(?,?,?)";
		Object [] params={userId,focudUserId,System.currentTimeMillis()};
		Integer count=0;
		
		String ifSql="SELECT COUNT(*) AS count FROM lb_user_focus WHERE user_id=? AND focus_user_id=?";
		Object [] ifparams={userId,focudUserId};
		Integer ifJson=baseDao.findJsonObject(ifSql, ifparams).getInt("count");
		if(ifJson==0){
			count=baseDao.insertBy(sql, params);
			if(count>0){
				JpushUtil jpusht=new JpushUtil();
				//客户端的缓存用户ID是经过加密的，所以这里要对实际ID加密
				JpushUtil.pushMessage(CreateDefinedPwdUtil.encryp(focudUserId), 2);
			}
		}else{
			
		}
		return count;
	}
	
	
	public JSONObject getRandomUser(){
		String sql="SELECT * FROM lb_user where province!='' and  user_type>3  ORDER BY RAND() LIMIT 1";
		JSONObject json=baseDao.findJsonObject(sql, null);
		 
		return json;
	}
	public Integer userRegister(String phone, String pwd) {
		Integer result=0;
		pwd=PasswordUtil.encryp(pwd);
		String if_sql="SELECT COUNT(*) AS count FROM lb_user WHERE phone=?";
		Object [] if_params={phone};
		Integer count=serviceUtil.getCountBy(if_sql, if_params);
		if(count==0){
			String sql="INSERT INTO lb_user(phone,password,create_time,user_name) VALUES(?,?,?,?)";
			Object [] params={phone,pwd,System.currentTimeMillis(),phone};
			result=baseDao.insertBy(sql, params);
		}else{
			
		}
		
		return  result;
	}
	@Override
	public JSONArray getUserList() {
		String sql="SELECT * FROM lb_user WHERE  id=1988 OR id=15514 OR id=1990 OR id=1991 OR id=2956 OR id=4369  OR id=5779 OR (user_type between 10 and 13)";
		return baseDao.find(sql, null);
	}

	public JSONArray getIndexAd() {
		String sql="SELECT* FROM lb_index_ad ORDER BY ad_id DESC";
		return baseDao.find(sql, null);
	}
	public JSONArray getCircleList() {
		String sql="SELECT* FROM bh_circle";
		return baseDao.find(sql, null);
	}
	public JSONObject getUserNameAndImgBy(Integer userId) {
		String sql="SELECT user_name AS userName,sex,province,city,area,user_img AS userImg,sign,weight,height,position,star,team,honour FROM lb_user WHERE id=?";
		Object [] params={userId};
		return baseDao.findJsonObject(sql, params);
	}
}
