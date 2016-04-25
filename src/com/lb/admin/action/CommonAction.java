package com.lb.admin.action;

import java.io.PrintWriter;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lb.action.PageAction;
import com.lb.admin.service.CommonService;
import com.lb.utils.PrintUtil;
@Namespace("/admin/common")
@Controller
@Scope("prototype")
public class CommonAction extends PageAction{

	@Autowired
	CommonService commonService=null;
	/**
	 * 删除比赛功能数据
	 * @return
	 */
	@Action(value = "DeleteMatchModelData", results = {
			@Result(name = "success", location = "/jsp/admin/add_question.jsp"),
			@Result(name = "input", location = "/jsp/admin/add_dynamic.jsp") })
	public String getAllQuestionnaire() {
		String res=SUCCESS;
		Integer result=commonService.deleteMatchModelData();
		PrintWriter out=PrintUtil.printWord();
		out.print(result);
		out.flush();
		out.close();
		 return res;
	}
}
