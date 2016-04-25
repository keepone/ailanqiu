package com.lb.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lb.admin.service.CommonService;
import com.lb.admin.service.UserService;
import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
@Component
public class CommonServiceImpl implements CommonService{
	@Autowired
	ServiceUtil serviceUtil=null;
	
	@Autowired
	UserService userService=null;
	
	@Autowired
	BaseDao baseDao=null;

	@Override
	public Integer deleteMatchModelData() {
		
		String sql="DELETE FROM lb_user_team";
//		baseDao.updateBy(sql, null);
//		
//		sql="DELETE FROM lb_battle";
//		baseDao.updateBy(sql, null);
//		
//		sql="DELETE FROM lb_battle_deal";
//		baseDao.updateBy(sql, null);
//		
//		sql="DELETE FROM lb_basketball_court where court_type>1";
//		baseDao.updateBy(sql, null);
//		
//		
//		sql="UPDATE lb_user set home_court=0  where home_court>1";
//		baseDao.updateBy(sql, null);
//		
//		sql="DELETE FROM lb_team";
		 
		
		
		
		return baseDao.updateBy(sql, null);
	}

	@Override
	public Integer addSysMessage(Integer messageType, String messageContent,
			Integer thirdId) {
		String sql="INSERT INTO lqr_sys_message(file_type,message_content,third_id,add_time) VALUES(?,?,?,?)";
		Object [] params={messageType,messageContent,thirdId,System.currentTimeMillis()};
		
		return baseDao.insertBy(sql, params);
	}
	
	
}
