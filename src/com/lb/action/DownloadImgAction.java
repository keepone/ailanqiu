package com.lb.action;

import java.io.IOException;
import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.jiutong.test.Log;
import com.lb.app.service.LBSForAppService;
import com.lb.craw.cba.GetCBANewsByXinHua;
import com.lb.craw.cba.GetCBANewsFromSoHu;
import com.lb.craw.cba.GetCBANewsFromTencent;
import com.lb.craw.common.GetBasketballRule;
import com.lb.craw.common.GetTactics;
import com.lb.crawl.nba.GetHistoryRecords;
import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBACommentFromNBAChina;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromSoHu;
import com.lb.crawl.nba.GetNBANewsFromTencent;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNBAVideoFromHuPu;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lb.crawl.nba.GetNewsFromTBBA;
import com.lb.crawl.nba.GetSpecialFrom163;
import com.lb.crawl.utils.GetBasketballPlace;
import com.lb.service.NewsService;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.service.impl.DirectServiceImpl;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.IoUtil;
import com.lb.utils.PageBean;
import com.lb.utils.PrintUtil;
import com.lb.utils.SourceUtil;
import com.lucene.crawl.GetCBADataRanking;
import com.lucene.crawl.GetCBANewsBySina;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetEuropeBasketball;
import com.lucene.crawl.GetGameResult;
import com.lucene.crawl.GetNBATeamInfoBy163;
import com.lucene.crawl.GetPlayerVideoByNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;
import com.lucene.crawl.GetPlayerVideoByYouKu;
import com.lucene.crawl.GetTvDirectByBroadcast;
import com.lucene.crawl.GetWCBANews;
 
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/download")
@Controller
@Scope("prototype")
public class DownloadImgAction extends PageAction {
	private static String fileName="";
	private static  String sourceUrl;
	private static String fileType;
	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	public final static String phoneUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A5313e Safari/7534.48.3";
	
	public static void main(String[] args) {
		String url="http://mp.weixin.qq.com/s?__biz=MzA3NDI3MTg3NA==&mid=203717479&idx=1&sn=e1b93e858f7177ca249889fa16255377#rd";
	
		//get(url);
		//useUsereAgent();
	}
	
	@Action(value = "DownloadImgAction", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public static void download() {
		int status=1;
		int i=1;
		try {
			Document doc=Jsoup.connect(sourceUrl).userAgent(USERAGENT).timeout(10000).get();
			Elements imgs=doc.select("img[data-src]");
			
			for(Element img:imgs){
				//Users/Allen/tomcat7/webapps/review/images
				String savePath="/Users/Allen/LB_Img/";
				String urlPath=img.attr("data-src");
				savePath=savePath+fileName+"_"+i+fileType;
				IoUtil.downloadImage(urlPath, "", savePath);
				i++;
			}
			System.out.println(doc);
		} catch (Exception e) {
			 status=0;
			e.printStackTrace();
		}
		PrintWriter out=PrintUtil.printWord();
		if(status==1){
			out.print("下载成功!共下载："+(i-1)+"张图片");
		}else{
			out.print("下载失败!");
		}
	}
	
	
	@Action(value = "AddWeiXinAction", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public static String addWeiXin() {
		int status=1;
		GetCBANewsBySina.addWeiXin(sourceUrl,fileType);
		 
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	 

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	

}
