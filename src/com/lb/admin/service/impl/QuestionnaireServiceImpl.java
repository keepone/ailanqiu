package com.lb.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.admin.service.QuestionnaireService;
import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.TimeUtil;
@Service("questionnaireService")
@Component
public class QuestionnaireServiceImpl implements QuestionnaireService {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	
	@Resource(name = "serviceUtil")
	ServiceUtil serviceUtil=null;

	@Override
	public JSONArray getAllQuestionnaire() {
		String sql="SELECT * FROM lb_questionnaire";
		return baseDao.find(sql, null);
	}

	@Override
	public Integer runSQL(String sql) {
		
		return baseDao.updateBy(sql, null);
	}

	@Override
	public Integer addQuestion(Integer questionnaireId,
			String questionName, String questionA, String questionB,
			String questionC, String questionD, String questionImg,
			String rightAnswer) {
		String sql="INSERT INTO lb_question(question_name,question_a,question_b,question_c,question_d,answer,questionnaire_id,question_img) VALUES(?,?,?,?,?,?,?,?)";
				Object [] params={questionName,questionA,questionB,questionC,questionD,rightAnswer,questionnaireId,questionImg};
		return baseDao.insertBy(sql, params);
	}
	 

	
}
