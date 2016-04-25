package com.lb.action;

import java.io.IOException;
import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lb.service.TeamService;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/team")
@Controller
@Scope("prototype")
public class TeamAction extends ActionSupport {
	@Autowired
	@Qualifier("teamService")
	TeamService teamService=null;

	// 
	@Action(value = "AddTeamAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String add() {
		String startURL = "http://nba.sports.163.com/2014/team/home/";
		String img_savePath="D:/tomcat6/Tomcat 6.0/webapps/ROOT/team_logo/";
		String endURL = ".html";
		int count=1;
		int f = 1;
		 
		for (int i = 1; i <= 30; i++) {
			String url=new String();
			url=startURL+i+endURL;
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(60000).get();
				String teamName=doc.select("span.f-teamc").first().ownText();
				String logo = doc.select("img.img2").attr("abs:src");
				//�������LOGO
				//IoUtil.downloadImage(logo, "", img_savePath+i+".gif"); 
				System.out.println(logo);
				String teamInroduce=doc.select("div.intr-con").last().ownText();
				String area=doc.select("span.f14px").first().ownText();
				if(area.indexOf("��������")!=-1){
					area="����";
				}else{
					area="����";
				}
				String text=doc.select("span.nor-dblue").first().ownText().trim();
				int index_coach=text.indexOf("������");
				int index_boss=text.indexOf("���ϰ�");
				int index_manage=text.indexOf("�ܾ���");
				int index_address=text.indexOf(" ��ӵ�ַ");
				int index_areaDetail=text.indexOf("����");
				String home_court=text.substring(0,index_coach).replace("�� ����", "");
				String coach=text.substring(index_coach,index_boss).replace("��������", "");
				String boss=text.substring(index_boss,index_manage).replace("���ϰ壺", "");;
				String manage=text.substring(index_manage,index_address).replace("�ܾ���", "");
				String address=text.substring(index_address,index_areaDetail).replace("��ӵ�ַ��", "");
				String area_detail=text.substring(index_areaDetail,text.length()).replace("������", "");
				 
				teamService.addTeam(teamName,"",logo, home_court, area, area_detail, teamInroduce, coach, boss, manage, address);
				count=count+1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("dowload:"+count);

		}
 
		return null;
	}

	
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 

}
