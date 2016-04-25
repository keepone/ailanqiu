package com.lb.admin.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface QuestionnaireService {
	 public JSONArray getAllQuestionnaire();
	 public Integer addQuestion(Integer questionnaireId,String questionName,String questionA,String questionB,String questionC,String questionD,String questionImg,String rightAnswer);
	 public Integer runSQL(String sql);
}
