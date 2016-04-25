package com.lb.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface LbaCommentService {
	public Integer addComment(String title,String content);
	public JSONArray getAll();
	public JSONObject getCommentDetail(Integer id);
}
