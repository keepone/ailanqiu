package com.lb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.UserItemService;
@Service("userItemService")
public class UserItemServiceImpl implements UserItemService {

	@Override
	public Integer addUserItem(Integer userId, Integer itemId) {
		String sql="insert into user_item(userId,itemId) values(?,?)";
		Object[] params={userId,itemId};
		return baseDao.insertBy(sql, params);
	}
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}
