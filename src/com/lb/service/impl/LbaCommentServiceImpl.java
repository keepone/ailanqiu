package com.lb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.LbaCommentService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("lbaCommentService")
@Component
public class LbaCommentServiceImpl implements LbaCommentService{
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public Integer addComment(String title,String content) {
	    String sql="INSERT INTO lba_comment(name,content,addTime) VALUES(?,?,?)";
	    Object[] params={title,content,System.currentTimeMillis()};
		return baseDao.insertBy(sql, params);
	}

	@Override
	public JSONArray getAll() {
		String sql="SELECT * FROM lba_comment";
		return baseDao.find(sql, null);
	}
	@Override
	public JSONObject getCommentDetail(Integer id) {
		String sql="SELECT*FROM lba_comment where id=?";
		Object[] params={id};
		return baseDao.findJsonObject(sql, params);
	}

}
