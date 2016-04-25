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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.jiutong.test.Log;
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
import com.lb.utils.PageBean;
import com.lb.utils.PrintUtil;
import com.lb.utils.SourceUtil;
import com.lucene.crawl.GetCBADataRanking;
import com.lucene.crawl.GetCBANewsBySina;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetEuropeBasketball;
import com.lucene.crawl.GetGameResult;
import com.lucene.crawl.GetHupu;
import com.lucene.crawl.GetNBATeamInfoBy163;
import com.lucene.crawl.GetPlayerBy;
import com.lucene.crawl.GetPlayerVideoBy56;
import com.lucene.crawl.GetPlayerVideoByLetv;
import com.lucene.crawl.GetPlayerVideoByNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;
import com.lucene.crawl.GetPlayerVideoByYouKu;
import com.lucene.crawl.GetTvDirectByBroadcast;
import com.lucene.crawl.GetUserByHuPu;
import com.lucene.crawl.GetWCBANews;
import com.lucene.crawl.GetZhiboBaSpecial;
 
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/news")
@Controller
@Scope("prototype")
public class NewsAction extends PageAction {
	
	
	@Autowired
//	@Qualifier("newsService")
	NewsService newsService = null;
	JSONArray jsonArray = null;
	private String content = null;
	private JSONObject json = null;
	private String specialId = null;
	private String specialName = null;
	private String result = null;
	private PageBean pageBean = null;
	private String newsId=null;
	private String newsTitle=null;
	private String clickType = "非查询";
	private String source_url="";
	private String videoType="";
	private String url=null;
	private Integer ifDownloadImg=0;
	private Integer resourceType=1;
	private Integer original=1;
	private String source="";
	private String img="";
	
	
	
	
	
	// 
	@Action(value = "AddAllNBAPlayer", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String AddAllNBAPlayer() {
		GetPlayerBy.addAllNBAPlayerFromSina();
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	
	// nba新闻
	@Action(value = "AddNBANewsAction", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addNBANews() {
		GetNBANewsFromTencent.getNBANews("http://sports.qq.com/l/basket/nba/newsmore.htm");
		//GetNewsFromNBA98.getNewsList("http://www.nba98.com/news/");
		//GetNewsFromTBBA.getNewsList("http://www.tbba.com.cn/");
		GetNBANewsFromSina.getNewsBySina("http://sports.sina.com.cn/nba/");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	// nba新闻
	@Action(value = "AddZhiBoBaNews", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addNBANews2() {
		GetNBANewsFromZhiBoBa.getNewsList("http://news.zhibo8.cc/nba");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
//	//nba搜狐新闻
//	@Action(value = "AddNBASoHuAction", results = {
//			@Result(name = "success", location = "/editor.jsp"),
//			@Result(name = "input", location = "/jsp/add_company.jsp") })
//	public String addNbaSoHu() {
//		GetNBANewsFromSoHu.getNewsList("http://sports.sohu.com/1/1102/39/subject204253982.shtml");//NBA动态新闻
//		GetNBANewsFromSoHu.getNewsList("http://sports.sohu.com/1/1102/34/subject204253472.shtml");//火箭新闻
//		GetNBANewsFromSoHu.getNewsList("http://sports.sohu.com/s2014/lebron-james/index.shtml");//骑士新闻
//		GetNBANewsFromSoHu.getNewsList_2("http://sports.sohu.com/nba_a_932.shtml");//NBA新闻
//		
//		PrintWriter out=PrintUtil.printWord();
//		out.print("录入完毕");
//		out.flush();
//		out.close();
//		return null;
//	}
	
 
	//新浪视频
	@Action(value = "AddNBAVideoAction", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addNBAVideo() {
		GetPlayerVideoBySina.getEveryDayMatchVideo("http://roll.sports.sina.com.cn/s_nbavideo_video_big/index_");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
		
	}
	
	
	//CBA新闻
	@Action(value = "AddCBANewsAction", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addCBANews() {
		//GetCBANewsByXinHua.getNewsList("http://www.news.cn/sports/cba.htm");
		
		GetCBANewsBySina.getNewsList("http://roll.sports.sina.com.cn/s_campusbasket_all/index.shtml");	  //CUBA
		GetCBANewsBySina.getNewsList("http://roll.sports.sina.com.cn/s_cba_all/index.shtml");  //CBA
		//GetCBANewsFromSoHu.getNewsList_2("http://cbachina.sports.sohu.com/s2011/2671/s309755607/");//CBA媒体评论
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	//CBA新闻[tencent]
	@Action(value = "AddTencentCBANews", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addTencent() {
		GetCBANewsFromTencent.getCBANews("http://sports.qq.com/l/cba/CBAlist.htm");//CBA
	GetCBANewsFromTencent.getCBANews("http://sports.qq.com/l/cba/nationteam/list.htm");//国家队
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}

//	//欧洲篮球新闻
//	@Action(value = "AddEurope", results = {
//			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
//			@Result(name = "input", location = "/jsp/add_company.jsp") })
//	public String addEurope() throws IOException {
//		GetEuropeBasketball.getNewsList("http://sports.sohu.com/s2014/1565/s395217589/");
//		PrintWriter out=PrintUtil.printWord();
//		out.print("录入完毕");
//		out.flush();
//		out.close();
//		return null;
//	}
//	
//	//wcba新闻
//	@Action(value = "AddWCBA", results = {
//			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
//			@Result(name = "input", location = "/jsp/add_company.jsp") })
//	public String addWCBA() throws IOException {
//		GetWCBANews.getNewsList("http://sports.qq.com/l/cba/wcba/list20110816174202.htm");
//		PrintWriter out=PrintUtil.printWord();
//		out.print("录入完毕");
//		out.flush();
//		out.close();
//		return null;
//	}
//	//规则
//	@Action(value = "AddRule", results = {
//			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
//			@Result(name = "input", location = "/jsp/add_company.jsp") })
//	public String addRule() throws IOException {
//		GetBasketballRule.getRuleName("http://www.ilanqiu.com/rules/list_3_",1);
//		PrintWriter out=PrintUtil.printWord();
//		out.print("录入完毕");
//		out.flush();
//		out.close();
//		return null;
//	}
	
//		
//		//战术
//		@Action(value = "AddTactics", results = {
//				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
//				@Result(name = "input", location = "/jsp/add_company.jsp") })
//		public String addTactics() throws IOException {
//			GetTactics.getAllTactics("http://www.172lanqiu.com/tuwenjiaoxue/list_8_");
//			PrintWriter out=PrintUtil.printWord();
//			out.print("录入完毕");
//			out.flush();
//			out.close();
//			return null;
//		}
		
		 //获取专栏
		@Action(value = "AddSpecialFromNBA", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addSpecialFromNBA() throws IOException {
			GetNBACommentFromNBAChina.getSpecial("http://china.nba.com/");
			PrintWriter out=PrintUtil.printWord();
			out.print("录入完毕");
			out.flush();
			out.close();
			return null;
		}
		
		
				
		
	//录像
	@Action(value = "AddLuXiangAction", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addLuXiang() {
		GetLuXiangFromZhiBoBa.getNBALuXiang("http://www.zhibo8.cc/nba/luxiang.htm");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	//乐视视频
	@Action(value = "AddLETVVideoAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addLETVVideo() {
		GetPlayerVideoByLetv getPlayerVideoByLetv=new GetPlayerVideoByLetv();
		getPlayerVideoByLetv.getLetvVideo2("http://list.letv.com/listn/c4_t30232_vt-1_md_o1_d2_p.html");//最新
		getPlayerVideoByLetv.getLetvVideo2("http://list.letv.com/listn/c4_t30232_vt-1_md_o9_d2_p.html");//最新
		getPlayerVideoByLetv.getLetvVideo2("http://list.letv.com/listn/c4_t30232_vt-1_md_o3_d2_p.html");//最新
		
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	//乐视视频
	@Action(value = "AddLETVZhunJi", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String AddLETVZhunJi() {
		GetPlayerVideoByLetv getPlayerVideoByLetv=new GetPlayerVideoByLetv();
		getPlayerVideoByLetv.getLetvZhuanJi(source_url);
	 
		
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	//虎扑视频
	@Action(value = "AddHuPuVideoAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addHuPuVideo() {
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/game");//比赛
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/Cavaliers"); //骑士
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/lakers"); //湖人
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/heat"); //热火
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/rockets"); //火箭
		GetNBAVideoFromHuPu.addTouTiao("http://v.opahnet.com/nba"); //首页推荐
		GetNBAVideoFromHuPu.addTouTiao("http://v.opahnet.com/nba/new/cba"); //CBA
		GetNBAVideoFromHuPu.addTouTiao("http://v.opahnet.com/nba/hot"); // 热门
		
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	//直播
	@Action(value = "AddDirectAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addDirect() throws IOException {
		GetTvDirectByBroadcast.getZhibo("http://www.zhibo8.cc/");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	//NBA比赛结果
	@Action(value = "AddNBAMatchResultAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addNBAMatchResult() throws IOException {
		 GetGameResult.getGamResult("http://nba.sports.sina.com.cn/match_result.php");
		 
//		 GetGameResult.getGamResult("http://nba.sports.sina.com.cn/match_result.php?day=0&years=2015&months=10&teams=");
//		 GetGameResult.getGamResult(" http://nba.sports.sina.com.cn/match_result.php?day=0&years=2015&months=11&teams=");
//		 
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	//CBA比赛结果
	@Action(value = "AddCBAMatchResultAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addCBAMatchResult() throws IOException {
		GetGameResult.getCBAGameResult("http://cba.sports.sina.com.cn/cba/schedule/all/");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	//nba
	@Action(value = "AddNBATeamRank", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addNBATeamRank() throws IOException {
		GetDataRankingBySina.getDataRanking("http://nba.sports.sina.com.cn/league_order1.php?dpc=1");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	@Action(value = "AddNBAPlayerRank", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addNBAPlayerRank() throws IOException {
		GetDataRankingBySina.getPlayerScoreRanking("http://nba.sports.sina.com.cn/playerstats.php?s=0&e=49&key=1&t=1");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}

	
	//nba
	@Action(value = "AddCBATeamRank", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addCBATeamRank() throws IOException {
		GetCBADataRanking.getTeamRanking("http://cba.sports.sina.com.cn/cba/stats/teamrank/?dpc=1");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	
	
	
	
	@Action(value = "AddCBAPlayerRank", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addCBAPlayerRank() throws IOException {
		GetCBADataRanking.getPlayerRanking("http://cbadata.sports.qq.com/playerrank.php?id=");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	@Action(value = "AddWCBAPlayerRank", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addWCBAPlayerRank() throws IOException {
		GetCBADataRanking.getWCBAScoreRanking("http://cba.sports.sina.com.cn/wcba/stats/playerstats/?qorder=pts&qleagueid=160&qround=");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	
	//历史纪录
	@Action(value = "AddHistoryRecords", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addHistoryRecords() throws IOException {
		GetHistoryRecords.getLianSheng("http://sports.sohu.com/20070303/n248486360.shtml");
		GetHistoryRecords.getPersonalScore("http://sports.sohu.com/20070326/n248986361.shtml");
		GetHistoryRecords.getEveryMaxScore("http://sports.sohu.com/20070327/n249014288.shtml");
		GetHistoryRecords.getPersonalSumScore("http://sports.sohu.com/20070327/n249011128.shtml");
		GetHistoryRecords.getAllChampion("http://sports.sina.com.cn/nba/finals.shtml");
		GetHistoryRecords.getBestPlayer("http://sports.163.com/13/0626/22/92B1N94S00051CA1.html");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	//录入考古系，人物志专题
	@Action(value = "AddHistorySpecial", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addSpecial() throws IOException {
		GetSpecialFrom163.getNewsBy163_history("http://sports.163.com/special/archaeology/");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	//录入直播吧专题
	@Action(value = "AddZhiBoBaSpecial", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addSpecial22() {
		int index=url.lastIndexOf("/");
		url=url.substring(0,index+1);
		GetZhiboBaSpecial.addSpeicialFromZhiBoBa(url);
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	//录入考古系，人物志专题
	@Action(value = "AddPersonlSpecial", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addSpecial2() throws IOException {
		GetSpecialFrom163.getPersonal("http://sports.163.com/special/nbalegendhz");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	//教学系列
		@Action(value = "AddStarVideo", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addStarVideo() throws IOException {
			String startURL = "http://search.sina.com.cn/?q=%B0%AC%B8%A5%C9%AD&range=all&c=news&sort=time&SL2=nbavideo&PID=";
			GetPlayerVideoBySina.getSinaVideo(startURL);
			PrintWriter out=PrintUtil.printWord();
			out.print("录入完毕");
			out.flush();
			out.close();
			return null;
		}
		
		@Action(value = "AddJiaNongTeach", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addJiaNongTeach() throws IOException {
			String startURL = "http://www.soku.com/search_video/q_%E5%8A%A0%E5%86%9C%E8%B4%9D%E5%85%8B_orderby_1_page_";
			GetPlayerVideoByYouKu.getYouKuVideo(startURL);
			PrintWriter out=PrintUtil.printWord();
			out.print("录入完毕");
			out.flush();
			out.close();
			return null;
		}
		
		
		@Action(value = "AddWuHu", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addWuHu() throws IOException {
			GetPlayerVideoByYouKu.getWuHu("http://www.soku.com/search_video/q_%E4%BA%94%E8%99%8E%E7%AF%AE%E7%90%83_orderby_1_page_");
			PrintWriter out=PrintUtil.printWord();
			out.print("录入完毕");
			out.flush();
			out.close();
			return null;
		}
		
		
		@Action(value = "AddNike", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addNike() throws IOException {
			String startURL ="http://www.soku.com/search_video/q_NIKE%E7%B2%BE%E5%93%81%E7%AF%AE%E7%90%83%E6%95%99%E5%AD%A6_orderby_1_page_";
			GetPlayerVideoByYouKu.getYouKuVideoFromNike(startURL);
			PrintWriter out=PrintUtil.printWord();
			out.print("录入完毕");
			out.flush();
			out.close();
			return null;
		}
		
		
		
		/**
		 * 优酷专辑列表
		 * @return
		 * @throws IOException
		 */
		@Action(value = "AddZhuanJi", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addZhuanji() throws IOException {
			 
			GetPlayerVideoByYouKu.addZhuanJi(url);
			PrintWriter out=PrintUtil.printWord();
			out.print("录入完毕");
			out.flush();
			out.close();
			return null;
		}
		
		/**
		 * 腾讯个人属性专辑列表
		 * @return
		 * @throws IOException
		 */
		@Action(value = "AddZhuanJiByTencent", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addZhuanJiByTencent() throws IOException {
			 
			GetPlayerVideoByYouKu.addZhuanJiByTencent(source_url);
			PrintWriter out=PrintUtil.printWord();
			out.print("录入完毕");
			out.flush();
			out.close();
			return null;
		}
		
		@Action(value = "AddShuQi", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addShuQi() throws IOException {
			String startURL ="http://www.soku.com/search_video/q_%E6%9A%91%E6%9C%9F%E7%AF%AE%E7%90%83%E6%95%99%E5%AD%A6_orderby_1_page_";
			GetPlayerVideoByYouKu.getYouKuVideoFromShuQi(startURL);
			PrintWriter out=PrintUtil.printWord();
			out.print("录入完毕");
			out.flush();
			out.close();
			return null;
		}
	
		@Action(value = "Add56", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String add56() throws IOException {
			GetPlayerVideoBy56.getVideoList();
			PrintWriter out=PrintUtil.printWord();
			out.print("录入完毕");
			out.flush();
			out.close();
			return null;
		}
	
		@Action(value = "AddVideoDetail", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String add56Detail() throws IOException {
			//videoType=GetParameterUtil.getString("videoType");
			
			Integer status=GetPlayerVideoBy56.getVideoDetail(source_url,videoType);
			PrintWriter out=PrintUtil.printWord();
			if(status==1){
				out.print("录入完毕^^");
			}else{
				out.print("录入失败！！！");
			}
			
			out.flush();
			out.close();
			return null;
		}
	
		@Action(value = "AddWeiXinText", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String AddWeiXinText() throws IOException {
			//videoType=GetParameterUtil.getString("videoType");
			
			Integer status=GetPlayerVideoBy56.addNewsFromWeiXin(original,source_url,ifDownloadImg,resourceType,source,img);
			PrintWriter out=PrintUtil.printWord();
			if(status>1){
				out.print("录入完毕^^");
			}else{
				out.print("录入失败！！！");
			}
			
			out.flush();
			out.close();
			return null;
		}
		
		
		//从网易录入专题。http://sports.163.com/15/0914/15/B3G0J7V500052UUC.html#p=ANGIT4HB0ACR0005
		@Action(value = "GetSpecialFrom163", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String getSpecialBy163() throws IOException {
			//videoType=GetParameterUtil.getString("videoType");
			
			Integer status=GetSpecialFrom163.getSpecialBy163_PLUS(source_url);
			PrintWriter out=PrintUtil.printWord();
			if(status>1){
				out.print("录入完毕^^");
			}else{
				out.print("录入失败！！！");
			}
			
			out.flush();
			out.close();
			return null;
		}
		
		//从新浪录入专题。http://sports.163.com/15/0914/15/B3G0J7V500052UUC.html#p=ANGIT4HB0ACR0005
		@Action(value = "GetSpecialBySina", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String getSpecialBySina() throws IOException {
			//videoType=GetParameterUtil.getString("videoType");
			
			Integer status=GetSpecialFrom163.getSpecialBySina(source_url);
			PrintWriter out=PrintUtil.printWord();
			if(status>1){
				out.print("录入完毕^^");
			}else{
				out.print("录入失败！！！");
			}
			
			out.flush();
			out.close();
			return null;
		}
		
		//从腾讯录入专题(最后转换的是天天快报的接口)。http://sports.163.com/15/0914/15/B3G0J7V500052UUC.html#p=ANGIT4HB0ACR0005
		@Action(value = "GetSpecialByTencent", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String GetSpecialByTencent() throws IOException {
			//videoType=GetParameterUtil.getString("videoType");
			
			Integer status=GetSpecialFrom163.getSpecialByTencent(source_url);
			PrintWriter out=PrintUtil.printWord();
			if(status>1){
				out.print("录入完毕^^");
			}else{
				out.print("录入失败！！！");
			}
			
			out.flush();
			out.close();
			return null;
		}
		
		
		/**
		 * 录入假用户
		 * @return
		 * @throws IOException
		 */
		@Action(value = "AddFalseUser", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addFalseUser() throws IOException {
			//videoType=GetParameterUtil.getString("videoType");
			
			Integer status=GetUserByHuPu.getUserInfo("http://bbs.hupu.com/bxj");
			PrintWriter out=PrintUtil.printWord();
			if(status==1){
				out.print("录入完毕^^");
			}else{
				out.print("录入失败！！！");
			}
			
			out.flush();
			out.close();
			return null;
		}
		

		/**
		 * 录入假学校
		 * @return
		 * @throws IOException
		 */
		@Action(value = "AddFalseSchool", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addFalseSchool() throws IOException {
			//videoType=GetParameterUtil.getString("videoType");
			GetUserByHuPu getUserByHuPu=new GetUserByHuPu();
			getUserByHuPu.getTeamName("http://www.cu-league.com/team/teamWebList.html");
			PrintWriter out=PrintUtil.printWord();
			
			
			out.flush();
			out.close();
			return null;
		}
		
		/**
		 * 录入球队
		 * @return
		 * @throws IOException
		 */
		@Action(value = "AddRegisterTeam", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String AddRegisterTeam() throws IOException {
			//videoType=GetParameterUtil.getString("videoType");
			GetUserByHuPu.addTeam(content);
			PrintWriter out=PrintUtil.printWord();
			 
			
			out.flush();
			out.close();
			return null;
		}
		
		
		
		/**
		 * 录入假用户
		 * @return
		 * @throws IOException
		 */
		@Action(value = "AddNBA98Video", results = {
				@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String AddNBA98Video() throws IOException {
			//videoType=GetParameterUtil.getString("videoType");
			
			GetPlayerVideoByYouKu.addNBA98("http://www.nba98.com/lanqiu/");
//			Integer status=GetUserByHuPu.getUserInfo("http://bbs.hupu.com/bxj");
			PrintWriter out=PrintUtil.printWord();
//			if(status==1){
//				out.print("录入完毕^^");
//			}else{
//				out.print("录入失败！！！");
//			}
//			
			out.flush();
			out.close();
			return null;
		}
		
	@Action(value = "AddVideoAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String add() {
		//newsService.addNews();
		return null;
	}

	@Action(value = "GetSpecialAction", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String getSpecial() {
		Integer specialId = GetParameterUtil.getInteger("specialId");
		json = newsService.getSpecialById(specialId);
		return SUCCESS;
	}

	@Action(value = "UpdateSpecialAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String updateSpecial() {
		newsService.updateSpecialById(Integer.parseInt(specialId), specialName,
				content);
		try {
			ServletActionContext.getResponse().sendRedirect(
					"/news/GetSpecialAction?specialId=" + specialId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	/**
	 * 获取新闻列表
	 * @return
	 */
	@Action(value = "NewsListAction", results = {
			@Result(name = "success", location = "/jsp/news_list.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String getAllNews() {
		String source = SourceUtil.checkSource();
		if (source.equals("web")) {
			pageBean = newsService.getAllNewsByConditions(
					getPage().getPageNo(), getPage().getPageSize());
			ActionContext actionContext = ActionContext.getContext();
			actionContext.getSession().put("page", pageBean);
			result = SUCCESS;
		} else {
			Integer pageNo = GetParameterUtil.getInteger("pageNo");
			Integer pageSize = GetParameterUtil.getInteger("pageSize");
		}

		return result;
	}
	/**
	 * 新闻详情
	 * @return
	 */
	@Action(value = "NewsDetailAction", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String getNewsDetail() {
		Integer newsId=GetParameterUtil.getInteger("newsId");
		json=newsService.getNewsDetail(newsId);
		result=SUCCESS;
		return result;
	}
	
	
	/**
	 * 更新新闻
	 * @return
	 */
	@Action(value = "UpdateNewsDetailAction", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String updateNewsDetail() {
		Integer count=newsService.updateNews(Integer.parseInt(newsId), newsTitle, content);
		result=SUCCESS;
		PrintWriter out=PrintUtil.printWord();
		out.print("<script type='text/javascript'>alert('ok');</script> ");
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 更新新闻
	 * @return
	 */
	@Action(value = "AddBasketballCourt", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addCourt() {
		 GetBasketballPlace getBasketballPlace=new GetBasketballPlace();
		 //getBasketballPlace.get();
		return null;
	}
	
	
	@Action(value = "SpecialListAction", results = {
			@Result(name = "success", location = "/jsp/special_list.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String getAllSpecial() {
		String source = SourceUtil.checkSource();
		if (source.equals("web")) {
			pageBean = newsService.getAllSpecialByConditions(
					getPage().getPageNo(), getPage().getPageSize());
			ActionContext actionContext = ActionContext.getContext();
			actionContext.getSession().put("page", pageBean);
			result = SUCCESS;
		} else {
			Integer pageNo = GetParameterUtil.getInteger("pageNo");
			Integer pageSize = GetParameterUtil.getInteger("pageSize");
		}

		return result;
	}
	
	
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public String getClickType() {
		return clickType;
	}

	public void setClickType(String clickType) {
		this.clickType = clickType;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}


	public String getSource_url() {
		return source_url;
	}


	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}


	public String getVideoType() {
		return videoType;
	}


	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}



	public Integer getResourceType() {
		return resourceType;
	}


	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}


	public Integer getIfDownloadImg() {
		return ifDownloadImg;
	}


	public void setIfDownloadImg(Integer ifDownloadImg) {
		this.ifDownloadImg = ifDownloadImg;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public Integer getOriginal() {
		return original;
	}


	public void setOriginal(Integer original) {
		this.original = original;
	}

}
