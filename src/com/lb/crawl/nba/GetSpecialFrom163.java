package com.lb.crawl.nba;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.ImageUtil;
import com.lb.utils.StrUtil;
import com.lb.utils.UploadPhotoToQiNiu;

@Component
public class GetSpecialFrom163 {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	private static ServiceUtil serviceUtil;
	@Resource(name = "serviceUtil")
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//getNewsDetail("http://sports.163.com/15/0914/15/B3G0J7V500052UUC.html#p=ANGIT4HB0ACR0005");
		getSpecialByTencent("http://kuaibao.qq.com/s/2015112001551800");
	}
	/**
	 * 	NBA考古系专题
	 * @param startURL
	 */
	public static JSONArray getNewsBy163_history(String startURL) {
		int count = 1;
		JSONArray arr=new JSONArray();
		for(int j=1;j<=4;j++){
			/**
			 * //第一页链接是http://sports.163.com/special/archaeology/
			 * 第二页以后都是http://sports.163.com/special/archaeology_0"+“页数”，所以这里做判断
			 */
			if(j==1){ 
				int f = 1;
					String url = new String();
					Document doc = null;
					try {
						doc = Jsoup.connect(startURL).timeout(60000).get();
						Elements lis = doc.select("div.col2").select("li");
						int id=0; //专题id
						String first_img=""; //专题对应的第一张图片
						for (Element li : lis) {
							if(li.outerHtml().indexOf("期")!=-1){
								if(f<=4){  //只抓取前面4个专题      下次只需要改动这里
									JSONObject json=new JSONObject();
									String href = li.select("table").first().select("a[href]").first().attr("abs:href");
									String cover = li.select("table").first().select("img[src]").first().attr("src");
									String title = li.select("h4.tit").first().select("a").first().text().replace("\"", "");
									int index_i=title.indexOf("期:");
									title=title.substring(index_i+2,title.length());
									System.out.println(title);
									String description = li.select("p").first().ownText();	
									
									String sql="INSERT INTO lb_store(name,img,source_img,content,source_href,addTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?);";
									Object [] params={title,cover,cover,description,href,System.currentTimeMillis(),"07","0701","网易",3};
									id=baseDao.insertBy(sql, params);
									
									
									doc = Jsoup.connect(href).timeout(90000).get();				
									if(doc.outerHtml().indexOf("gallery-data")!=-1){
										String elements = doc.select("textarea[name=gallery-data]").first().text();
										elements=elements.substring(elements.indexOf("list"),elements.length());
										//通过正则表达式截取信息
										while(elements.indexOf("note")!=-1){					 
											Integer img_index=elements.indexOf("img");
											Integer timg_index=elements.indexOf("timg");
											//获取大图（共有3中图片格式）
											String img=elements.substring(img_index+7, timg_index-4).replace("\",", "");
											Integer note_index=elements.indexOf("note");
											Integer newsurl_index=elements.indexOf("newsurl");
											//获取文字介绍
											String content=elements.substring(note_index+8,newsurl_index-4).replace("<", "");
											elements=elements.substring(newsurl_index+10,elements.length());
										 
											
											String sql_ditail="INSERT into lb_special_detail(special_id,content,img,sourceImg,addTime) VALUES(?,?,?,?,?)";
											long current_time=System.currentTimeMillis();
											//下载图片
											String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory2/"+current_time+".jpg";
											//IoUtil.downloadImage(img, "",savepath);
											Object []params_detail={id,content,img,img,current_time};
											baseDao.insertBy(sql_ditail, params_detail);
											count+=1;
										}
									}else{			 
										String elements = doc.select("textarea[class=hidden]").first().text();
//										elements=elements.substring(elements.indexOf("list"),elements.length());
										//通过正则表达式截取信息
										while(elements.indexOf("h2")!=-1){
											 
											Integer h2_begin_index=elements.indexOf("h2");
											Integer h2_end_index=elements.indexOf("<p>");
											Integer img_begin_index=elements.indexOf("title=\"img\"");
											Integer img_end_index=elements.indexOf("title=\"timg\"");
											//获取大图（共有3中图片格式）
											String img=elements.substring(img_begin_index+12, img_end_index-7).replace("<", "");
							
											//获取文字介绍
											String content=elements.substring(h2_begin_index+3,h2_end_index-5).replace("<", "");
											elements=elements.substring(img_end_index+10,elements.length());
									 
											
											String sql_ditail="INSERT into lb_special_detail(special_id,content,img,sourceImg,addTime) VALUES(?,?,?,?,?)";
											long current_time=System.currentTimeMillis();
											//下载图片
											String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory2/"+current_time+".jpg";
											//IoUtil.downloadImage(img, "",savepath);
											Object []params_detail={id,content,img,img,current_time};
											baseDao.insertBy(sql_ditail, params_detail);
											count+=1;
										}
									}
									
								 
									f+=1;
									count+=1;
								}
							}	
							
//							String getFirstImg=serviceUtil.getStringBySql("SELECT sourceImg FROM lb_special_detail WHERE special_id="+id+" LIMIT 0,1", "sourceImg");
//							String update_sql="UPDATE  lb_store set img=?,source_img=? where id=?";
//							Object [] update_params={getFirstImg,getFirstImg,id};
//							baseDao.updateBy(update_sql, update_params);
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
			}else{
//				startURL="http://sports.163.com/special/archaeology_0"+j+"/";
//				int f = 1;
//				 
//				String url = new String();
//				Document doc = null;
//				try {
//					doc = Jsoup.connect(startURL).timeout(60000).get();
//					Elements lis = doc.select("div.col2").select("li");
//					int id=0;
//					for (Element li : lis) {
//						if(li.outerHtml().indexOf("期")!=-1){
//							JSONObject json=new JSONObject();
//							String href = li.select("table").first().select("a[href]").first().attr("abs:href");
//							String cover = li.select("table").first().select("img[src]").first().attr("src");
//							
//							String title = li.select("h4.tit").first().select("a").first().text().replace("\"", "");
//							int index_i=title.indexOf("期:");
//							title=title.substring(index_i+2,title.length());
//							System.out.println(title);
//							String description = li.select("p").first().ownText();
//							String sql="INSERT INTO lb_store(name,img,source_img,content,source_href,addTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?);";
//							Object [] params={title,cover,cover,description,href,System.currentTimeMillis(),"07","0701","网易",3};
//							 id=baseDao.insertBy(sql, params);
//							doc = Jsoup.connect(href).timeout(90000).get();
//							
//							if(doc.outerHtml().indexOf("gallery-data")!=-1){
//								String elements = doc.select("textarea[name=gallery-data]").first().text();
//								elements=elements.substring(elements.indexOf("list"),elements.length());
//								//通过正则表达式截取信息
//								while(elements.indexOf("note")!=-1){					 
//									Integer img_index=elements.indexOf("img");
//									Integer timg_index=elements.indexOf("timg");
//									//获取大图（共有3中图片格式）
//									String img=elements.substring(img_index+7, timg_index-4).replace("\",", "");
//									Integer note_index=elements.indexOf("note");
//									Integer newsurl_index=elements.indexOf("newsurl");
//									//获取文字介绍
//									String content=elements.substring(note_index+8,newsurl_index-4);
//									elements=elements.substring(newsurl_index+10,elements.length());
//									 
//									
//									String sql_ditail="INSERT into lb_special_detail(special_id,content,img,sourceImg,addTime) VALUES(?,?,?,?,?)";
//									long current_time=System.currentTimeMillis();
//									//下载图片
//									String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory2/"+current_time+".jpg";
//									//IoUtil.downloadImage(img, "",savepath);
//									Object []params_detail={id,content,img,img,current_time};
//									baseDao.insertBy(sql_ditail, params_detail);
//									count+=1;
//								}
//							}else{
//								 
//								String elements = doc.select("textarea[class=hidden]").first().text();
////								elements=elements.substring(elements.indexOf("list"),elements.length());
//								//通过正则表达式截取信息
//								while(elements.indexOf("h2")!=-1){
//									 
//									Integer h2_begin_index=elements.indexOf("h2");
//									Integer h2_end_index=elements.indexOf("<p>");
//									Integer img_begin_index=elements.indexOf("title=\"img\"");
//									Integer img_end_index=elements.indexOf("title=\"timg\"");
//									//获取大图（共有3中图片格式）
//									String img=elements.substring(img_begin_index+12, img_end_index-7).replace("\",", "").replace("<", "");
//					
//									//获取文字介绍
//									String content=elements.substring(h2_begin_index+3,h2_end_index-5);
//									elements=elements.substring(img_end_index+10,elements.length());
//									 
//									
//									String sql_ditail="INSERT into lb_special_detail(special_id,content,img,sourceImg,addTime) VALUES(?,?,?,?,?)";
//									long current_time=System.currentTimeMillis();
//									//下载图片
//									String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory2/"+current_time+".jpg";
//									//IoUtil.downloadImage(img, "",savepath);
//									Object []params_detail={id,content,img,img,current_time};
//									baseDao.insertBy(sql_ditail, params_detail);
//									count+=1;
//								}
//							}
//							
//					 
//							f+=1;
//							count+=1;
//						}	
//						
////						String getFirstImg=serviceUtil.getStringBySql("SELECT sourceImg FROM lb_special_detail WHERE special_id="+id+" LIMIT 0,1", "sourceImg");
////						String update_sql="UPDATE  lb_store set img=?,source_img=? where id=?";
////						Object [] update_params={getFirstImg,getFirstImg,id};
////						baseDao.updateBy(update_sql, update_params);
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}
		
		System.out.println("have download" + count+"数组大小:"+arr.size());
		return arr;
	}
	
	
	
	
	/**
	 * NBA人物志系列
	 * @param startURL
	 */
	public static void getPersonal(String startURL) {
		 
		int count=1;
		int f = 1;
		String name="";
		Integer special_id=0;
		for (int i = 1; i <= 3; i++) {
			String url=new String();
			if(i==1){
				url=startURL;
			}else{
				url=startURL+"_0"+i+"/";
			}
			 
			 
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(60000).get();
				Elements lis = doc.select("div.col2").select("li");
				for (Element li : lis) {
					if(count==1){
						
					}else{	
						if(li.outerHtml().indexOf("期")!=-1){
//							name=e.select("img").first().attr("alt");
//							String sourceCover=e.select("img").first().attr("src");
//							//String description=e.select("div.imgCite").first().select("p.info").text();
//							String href=e.select("a[href]").first().attr("href");
							
							String href = li.select("table").first().select("a[href]").first().attr("href");
							String sourceCover = li.select("table").first().select("img[src]").first().attr("src");
							name = li.select("h4.tit").first().text();
							int index_i=name.indexOf("期:");
							name=name.substring(index_i+2,name.length());
							String description = li.select("p").first().ownText();
							
							doc = Jsoup.connect(href).timeout(60000).get();
							Elements ee=doc.select("a[href^=http://sports.163.com/photoview]");
							String detail_href=ee.select("a[href]").first().attr("href");
							System.out.println("----"+name);
							
							String sql="INSERT into lb_store(name,img,source_img,source_href,content,addTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?)";
							long current_time=System.currentTimeMillis();
							//下载图片
//							String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory/"+current_time+".jpg";;
//							IoUtil.downloadImage(cover, "",savepath);
							Object []params={name,sourceCover,sourceCover,detail_href,description,current_time,"07","0702","网易",3};
							special_id=baseDao.insertBy(sql, params);
							
							doc = Jsoup.connect(detail_href).timeout(60000).get();
							//页面有2种显示规则。
							if(doc.outerHtml().indexOf("gallery-data")==-1){
								String elements = doc.select("textarea[class=hidden]").first().text();
								//通过正则表达式截取信息
								while(elements.indexOf("h2")!=-1){
									JSONObject json=new JSONObject();
									Integer h2_begin_index=elements.indexOf("h2");
									Integer h2_end_index=elements.indexOf("<p>");
									Integer img_begin_index=elements.indexOf("title=\"img\"");
									Integer img_end_index=elements.indexOf("title=\"timg\"");
									//获取大图（共有3中图片格式）
									String sourceImg=elements.substring(img_begin_index+12, img_end_index-7);
					
									//获取文字介绍
									String content=elements.substring(h2_begin_index+3,h2_end_index-5);
									elements=elements.substring(img_end_index+10,elements.length());
									
									String sql2="INSERT into lb_special_detail(img,special_id,content,sourceImg,addTime) VALUES(?,?,?,?,?)";
									current_time=System.currentTimeMillis();
									//下载图片
//									String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory2/"+current_time+".jpg";;
//									IoUtil.downloadImage(img, "",savepath);
									Object [] params2={sourceImg,special_id,content,sourceImg,current_time};
									baseDao.insertBy(sql2, params2);
									 
									System.out.println(content);
									 
								}
							}else{
								String elements = doc.select("textarea[name=gallery-data]").first().text();
								elements=elements.substring(elements.indexOf("list"),elements.length());
								//通过正则表达式截取信息
								while(elements.indexOf("note")!=-1){
									JSONObject json=new JSONObject();
									Integer img_index=elements.indexOf("img");
									Integer timg_index=elements.indexOf("timg");
									//获取大图（共有3中图片格式）
									String sourceImg=elements.substring(img_index+7, timg_index-4);
									Integer note_index=elements.indexOf("note");
									Integer newsurl_index=elements.indexOf("newsurl");
									//获取文字介绍
									String content=elements.substring(note_index+8,newsurl_index-4);
									elements=elements.substring(newsurl_index+10,elements.length());
									
									String sql2="INSERT into lb_special_detail(img,special_id,content,sourceImg,addTime) VALUES(?,?,?,?,?)";
									current_time=System.currentTimeMillis();
									//下载图片
//									String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory2/"+current_time+".jpg";;
//									IoUtil.downloadImage(img, "",savepath);
									Object [] params2={sourceImg,special_id,content,sourceImg,current_time};
									baseDao.insertBy(sql2, params2);
									System.out.println(content);
								}
							}
							
//							String getFirstImg=serviceUtil.getStringBySql("SELECT sourceImg FROM lb_special_detail WHERE special_id="+special_id+" LIMIT 0,1", "sourceImg");
//							String update_sql="UPDATE  lb_store set img=?,source_img=? where id=?";
//							Object [] update_params={getFirstImg,getFirstImg,special_id};
//							baseDao.updateBy(update_sql, update_params);

							
							
						}
					}
					count++;
//					System.out.println("");
//					System.out.println("");
//					System.out.println("");
					//System.out.println(count+"------------"+name);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		//System.out.println("have download"+count);
	 

	}
	
	
	
	/**
	 * 升级前，图片浏览模式
	 * @param startURL
	 * @return
	 */
	public static Integer getSpecialBy163(String startURL) {
		Document doc = null;
		Integer count=0;
		Integer id=0;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements contents=doc.select("textarea.hidden");
			for(Element con:contents){
				String content=con.text();
				int i=1;
				while(content.indexOf("li")!=-1){
					
					String description=StrUtil.getStrBySubString(content, "<p>", "</p");
					String img=StrUtil.getStrBySubString(content, "<i title=\"img\">", "</i");
					if(i==1){
						String title=StrUtil.getStrBySubString(content, "<h2>", "</h").trim();
						if(title.equals("")){
							title=description.substring(0,3);
						}
						String sql="INSERT INTO lb_store(name,img,source_img,content,source_href,addTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?);";
						Object [] params={title,img,img,description,startURL,System.currentTimeMillis(),"07","0701","网易",3};
						id=baseDao.insertBy(sql, params);
					}
					String sql_ditail="INSERT into lb_special_detail(special_id,content,img,sourceImg,addTime) VALUES(?,?,?,?,?)";
					long current_time=System.currentTimeMillis();
					Object []params_detail={id,description,img,img,current_time};
					count=baseDao.insertBy(sql_ditail, params_detail);
					
					i++;
					int index=content.indexOf("</li");
					content=content.substring(index+4,content.length());
					System.out.println(description+"---"+img);
				}
			}
		 
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
		return count;
	}
	
	/**
	 * 升级版，现在是图文混排，之前是图片浏览模式
	 * @param startURL
	 * @return
	 */
	public static Integer getSpecialBy163_PLUS(String startURL) {
		Document doc = null;
		Integer count=0;
		Integer id=0;
		String textContent="";
		String title="";
		String img="";
		String cover="";
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements contents=doc.select("textarea.hidden");
			for(Element con:contents){
				textContent="";
				String content=con.text();
				System.out.println(content);
				int i=1;
				while(content.indexOf("li")!=-1){
					
					String description=StrUtil.getStrBySubString(content, "<p>", "</p");
					if(description.equals("")){
						description=StrUtil.getStrBySubString(content, "<h2>", "</h");
					}
					 img=StrUtil.getStrBySubString(content, "<i title=\"img\">", "</i");
					if(i==1){
						 cover=img;
						 title=StrUtil.getStrBySubString(content, "<h2>", "</h").trim();
						if(title.equals("")){
							title=description.substring(0,3);
						}
//						String sql="INSERT INTO lb_store(name,img,source_img,content,source_href,addTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?);";
//						Object [] params={title,img,img,description,startURL,System.currentTimeMillis(),"07","0701","网易",3};
//						id=baseDao.insertBy(sql, params);
						
						img="<p><img onclick=\"loadURL('gap://imgUrl="+img+"')\" src=\""+img+"\"></p>";
						textContent=textContent+"<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+title+"</p>";
						 
					}else{
						img="<p><img onclick=\"loadURL('gap://imgUrl="+img+"')\" src=\""+img+"\"></p>";
						textContent=textContent+img+"<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+description+"</p>";
					}
//					String sql_ditail="INSERT into lb_special_detail(special_id,content,img,sourceImg,addTime) VALUES(?,?,?,?,?)";
//					long current_time=System.currentTimeMillis();
//					Object []params_detail={id,description,img,img,current_time};
//					count=baseDao.insertBy(sql_ditail, params_detail);
					
					i++;
					int index=content.indexOf("</li");
					content=content.substring(index+4,content.length());
					System.out.println(description+"---"+img);
				}
				
				String sql="INSERT INTO lb_store(name,img,source_img,content,source_href,addTime,updateTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
				Object [] params={title,cover,cover,textContent,startURL,System.currentTimeMillis(),System.currentTimeMillis(),"01","0101","网易",1};
				count=baseDao.insertBy(sql, params);
			}
		 
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
		return count;
	}
	
	
	public static Integer getSpecialBySina(String startURL) {
		Document doc = null;
		Integer count=0;
		Integer id=0;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements contents=doc.select("div#eData").select("dl");
			String content="";
			String img="";
			String title="";
			int i=1;
			for(Element con:contents){
				
				
				if(i==1){
					
					Elements dds=con.select("dl").first().select("dd");
					int z=1;
					for(Element dd:dds){
						
						if(z==5){
							content=content+dd.text();
						}
						z++;
					}
					img=con.select("dd").first().text();
					title=con.select("dt").first().text();
					content=content+"<p><img onclick=\"loadURL('gap://imgUrl="+img+"')\"src=\""+img+"\"></p>";
					i++;
					
				}else{
					content=content+"<p><img onclick=\"loadURL('gap://imgUrl="+con.select("dd").first().text()+"')\" src=\""+con.select("dd").first().text()+"\"></p>";
					i++;
				}
		
			}
			String sql="INSERT INTO lb_store(name,img,source_img,content,source_href,addTime,updateTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
			Object [] params={title,img,img,content,startURL,System.currentTimeMillis(),System.currentTimeMillis(),"01","0101","新浪",1};
			id=baseDao.insertBy(sql, params);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
		return id;
	}
	
	
	public static Integer getSpecialByTencent(String startURL) {
		Document doc = null;
		Integer count=0;
		Integer id=0;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements contents=doc.select("p");
			String content="";
			String img="";
			String title="";
			int i=1;
			for(Element con:contents){
				
				
				if(i==1){
				 
					img="";
					title=con.text().replace("组图：", "");
					//content=content+"<p><img onclick=\"loadURL('gap://imgUrl="+img+"')\"src=\""+img+"\"></p>";
					i++;
					
				}else{
					String text=con.text();
					
					if(text.equals("")){
						if(con.outerHtml().indexOf("img")!=-1){
							text=con.select("img").attr("src");//图片
							text=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(text);
							text=text+ImageUtil.NEWS_DETAIL_SIZE;
							if(i>2){
								content=content+"<p><img onclick=\"loadURL('gap://imgUrl="+text+"')\" src=\""+text+"\"></p>";
							}else{
								img=text;
							}
							i++;
						}
					}else{
						
					 
							text=StrUtil.getStrByRemoveFistStr(text, "日讯");
							text=StrUtil.filterStr(text);
							content=content+text;
							i++;
						 
						
					}

				}
		
			}
			String sql="INSERT INTO lb_store(name,img,source_img,content,source_href,addTime,updateTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
			Object [] params={title,img,img,content,startURL,System.currentTimeMillis(),System.currentTimeMillis(),"01","0101","篮球热",1};
			id=baseDao.insertBy(sql, params);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
		return id;
	}
	
}
