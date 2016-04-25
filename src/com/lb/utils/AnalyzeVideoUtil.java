package com.lb.utils;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jiutong.test.Log;

public class AnalyzeVideoUtil {
	public static void main(String[] args) {
		AnalyzeVideoUtil g = new AnalyzeVideoUtil();
		try {
			String str = "http://v.youku.com/v_show/id_XODQ1MTA2NDAw.html?ev=1&from=y1.1-2.10001-0.1-1";
			String tudou = "http://www.tudou.com/programs/view/VuWOebHf2GA/";
			System.out.println(g.analyzeFromTuDou("http://www.tudou.com/programs/view/0DonAH9J1Fs/"));
			// System.out.println(g.analyze("http://video.sina.com.cn/p/sports/k/v/2014-12-10/184964374609.html"));
			// System.out.println(g.analyzeFromYouKu("http://v.youku.com/v_show/id_XODM4OTk3Njg0.html?f=23140326&ev=1&from=y1.3-idx-grid-1519-9909.86808-86807.1-1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 解析新浪视频
	 */
	public String analyze(String url) throws Exception {
		int id = 0;
		try {
			id = Integer.valueOf(url = url.substring(url.lastIndexOf("#")
					+ "#".length()));
		} catch (Exception e) {
			try {
				Document document = Jsoup.connect(url)
						.userAgent("Mozilla").timeout(20000).get();
				String html = document.toString();
				html = html.substring(html.indexOf("videoData"));
				html = html.substring(html.indexOf("ipad_vid:"));
				html = html.substring(html.indexOf("'") + "'".length());
				html = html.substring(0, html.indexOf("'"));
				id = Integer.valueOf(html);
			} catch (Exception err1) {
				try {
					Document document = Jsoup.connect(url)
							.userAgent("Mozilla").timeout(20000).get();
					String html = document.toString();
					html = html.substring(html.indexOf("play-data=\"")
							+ "play-data=\"".length());
					html = html.substring(0, html.indexOf("\""));
					html = html.split("-")[0];
					id = Integer.valueOf(html);
					System.out.println(id);
				} catch (Exception error2) {
					System.out.println(error2.toString());
				}
			}
		}

		if (0 == id) {

			int f = 0;

			int hello = 9;

			throw new Exception("error====id is 0");
		}
		return "http://v.iask.com/v_play_ipad.php?vid=" + id;
	}

	/**
	 * 解析【优酷】视频
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	 
	public String analyzeFromYouKu(String url) {
		int index_1 = url.indexOf("id_");
		int index_2 = url.indexOf(".html");
		return "http://player.youku.com/embed/"
				+ url.substring(index_1 + 3, index_2);
	}
	
	
	public String analyzeFromYouKu_2(String url) {
		int index=url.lastIndexOf("VideoIDS");
		String result=url.substring(index+9,url.length());
		int i=result.indexOf("&");
		result=result.substring(0,i);
		return "http://player.youku.com/embed/"+result;
	}
	
	

	/**
	 * 解析【土豆】视频,只针对体育视频，其他未验证
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */

	public String analyzeFromTuDou(String url) {
		String a = "http://www.tudou.com/programs/view/DwljcNQtfGI/?fr=rec2";

		int index_1 = url.indexOf("/view/");
		String one = url.substring(index_1 + 6, url.length());
		String result ="";
		if(one.indexOf("#")!=-1){
			result=one.replace("#player", "");
		}else{
			int index_2 = one.indexOf("/");
			result= one.substring(0, index_2);
		}
		
		// System.out.println(url.substring(index_1+3,index_2));
		return "http://www.tudou.com/programs/view/html5embed.action?type=0&code="
				+ result + "&lcode=&resourceId=96673538_06_05_99";
	}
	
	
	public String analyzeFromTuDou2(String url) {
		String a = "http://www.tudou.com/programs/view/DwljcNQtfGI/?fr=rec2";

		
		String result="";
		//http://www.tudou.com/albumplay/E9DvkUlHesE/hVu8eCeM5pI.html
		if(url.indexOf(".html")!=-1){
			int index=url.lastIndexOf("/");
			result=url.substring(index+1,url.length());
			int i=result.indexOf(".html");
			result=result.substring(0,i);
		}else{
		//http://www.tudou.com/programs/view/cWuZEw6C3Tg/
			int index_1 = url.indexOf("/view/");
			String one = url.substring(index_1 + 6, url.length());
			int index_2 = one.indexOf("/");
			result = one.substring(0, index_2);
			
		}
		// System.out.println(url.substring(index_1+3,index_2));
		result="http://www.tudou.com/programs/view/html5embed.action?type=2&code="
				+ result + "&resourceId=96673538_06_05_99";
		return result;
	}
	/**
	 * 解析腾讯网视频[第一种]
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String analyzeFromTencent(String url) throws Exception {
		int index=url.lastIndexOf("/");
		String result=url.substring(index+1,url.length());
		int i=result.indexOf(".html");
		result=result.substring(0,i);
		result="http://v.qq.com/iframe/player.html?vid="+result;
		//System.out.println(result);
		return result;
	}
	//http://static.video.qq.com/TPout.swf?vid=j0413enxrhx&auto=1
	/**
	 * 解析腾讯网视频［第二种,包含vid关键字］
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String analyzeFromTencent2(String url) throws Exception {
		int index=url.lastIndexOf("vid=");
		String result=url.substring(index+4,url.length());
		int i=result.indexOf("&");
		if(i!=-1){
			result=result.substring(0,i);
		}
		
		result="http://v.qq.com/iframe/player.html?vid="+result;
		//System.out.println(result);
		return result;
	}
	
	/**
	 * 解析56网视频[第一种]
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String analyzeFrom56(String url) throws Exception {
		//http://www.56.com/u45/v_MTMzOTI5OTYy.html#player  http://www.56.com/u32/v_MTM0MjM4ODI5.html/1030_guisongrocker.html#player
		int index=url.indexOf(".html");
		url=url.substring(0,index);
		
		 index=url.lastIndexOf("/");
		String result=url.substring(index+3,url.length());
		int i=result.indexOf(".html");
	
		result="http://www.56.com/iframe/"+result;
		//System.out.println(result);
		return result;
	}
		/**
		 * 解析56网视频[第一种]
		 * @param url
		 * @return
		 * @throws Exception
		 */
		public String analyzeFrom56_2(String url) throws Exception {
			//http://player.56.com/cpm_MTI3MDg4NjE0.swf#player
			int index=url.lastIndexOf(".swf");
			String result=url.substring(0,index);
			index=result.indexOf("_");
			 result=url.substring(index+1,result.length());
			
			result="http://www.56.com/iframe/"+result;
			//System.out.println(result);
			return result;
		}
		
		public String analyzeFromLetv(String url) {
			//http://www.letv.com/ptv/vplay/21638356.html
			//http://minisite.letv.com/tuiguang/index.shtml?vid=21638356&autoPlay=none&typeFrom=letv_gjzq_dqd&cid=4&ark=100&isAutoPlay=0
			int index=url.lastIndexOf("/");
			String result=url.substring(index+1,url.length());
			int i=result.indexOf(".");
			result=result.substring(0,i);
			result="http://minisite.letv.com/tuiguang/index.shtml?vid="+result+"&autoPlay=none&typeFrom=letv_gjzq_dqd&cid=4&ark=100&isAutoPlay=0";
			//System.out.println(result);
			return result;
		}
		
		
		
		
		
		
		public JSONObject getVideoSourceAndPlayURL(String detailHref){
			JSONObject json=new JSONObject();
			String video_href = "false";
			String source = "未确认";
			if (detailHref.indexOf("youku") != -1) {
				source = "优酷";
			} else if (detailHref.indexOf("56.com") != -1) {
				source = "56";
			} else if (detailHref.indexOf("letv.com") != -1) {
				source = "LETV";
			} else if (detailHref.indexOf("sina") != -1) {
				source = "新浪";
			} else if (detailHref.indexOf("ku6") != -1) {
				source = "ku6";
			} else if (detailHref.indexOf("tudou") != -1) {
				source = "土豆";
			} else if (detailHref.indexOf("souhu") != -1) {
				source = "搜狐";
			} else if (detailHref.indexOf("cntv") != -1) {
				source = "CNTV";
			} else if (detailHref.indexOf("pptv") != -1) {
				source = "PPTV";
			}  else if (detailHref.indexOf("qq") != -1) {
				source = "腾讯";
			}else {

			}

			 
					
					try {
						if(source.equals("腾讯")){
							if(detailHref.indexOf("vid=")!=-1){
								video_href = analyzeFromTencent2(detailHref);
							}else{
								video_href = analyzeFromTencent(detailHref);
							}
						}else if(source.equals("56")){
							if(detailHref.indexOf("swf")!=-1){
								video_href =  analyzeFrom56_2(detailHref);
							}else{
								video_href =  analyzeFrom56(detailHref);
							}
						}else if(source.equals("土豆")){
							video_href=analyzeFromTuDou(detailHref);
						}  else if (detailHref.indexOf("letv.com") != -1) {
							video_href = analyzeFromLetv(detailHref);
						} else if (detailHref.indexOf("sina") != -1) {
							try {
								// 瑙ｆ����版氮瑙�棰�
								video_href = analyze(detailHref);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								 
							}
							// 瑙ｆ��浼���疯��棰�
						} else if (detailHref.indexOf("youku.com") != -1) {
							if(detailHref.indexOf("swf")!=-1){
								video_href = analyzeFromYouKu_2(detailHref);
							}else{
								video_href = analyzeFromYouKu(detailHref).replace("\"\"", "'");
							}
							
						}  
						json.put("source", source);
						json.put("videoHref", video_href);
					} catch (Exception e1) {
						System.out.println("解析视频出错，来自虎扑视频比赛模块"+detailHref);
						e1.printStackTrace();
						json.put("source", source);
						json.put("videoHref", video_href);
					}

				 
		 
			
			return json;
		}
}