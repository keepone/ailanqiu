package com.lucene.crawl;

import java.io.IOException;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
import org.jsoup.Connection;
import org.jsoup.Jsoup;
 
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.ImageUtil;
import com.lb.utils.StrUtil;
import com.lb.utils.TimeUtil;
import com.lb.utils.UploadPhotoToQiNiu;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
 
@Component
public class GetPlayerVideoBy56 extends ActionSupport {
	private static BaseDao baseDao;
	private static ServiceUtil serviceUtil=null;
	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	
	 
	 
	
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Resource(name = "serviceUtil")
	public  void setServiceUtil(ServiceUtil serviceUtil) {
		GetPlayerVideoBy56.serviceUtil = serviceUtil;
	}


	private static List<JSONObject> list=new ArrayList<JSONObject>();
	public static void main(String[] args) throws InterruptedException {
		//addNewsFromWeiXin("http://bbs.hupu.com/13837992.html",1,1,"虎扑");

	}

   
    public static void getVideoList(){
    	AnalyzeVideoUtil analyzeVideoUtil=new AnalyzeVideoUtil();
		try {
			for(int i=2;i<=9;i++){
				String url="http://i.56.com/u/r475213358/videos/p_"+i;
				Document doc=Jsoup.connect(url).timeout(10000).get();
				Elements lis=doc.select("div.video_list_item").select("ul.vlist_mod").first().select("li");
				for(Element li:lis){
					String sourceHref=li.select("a[href]").first().attr("abs:href");
					String video_href=analyzeVideoUtil.analyzeFrom56(sourceHref);
					String name=li.select("a[href]").first().attr("title");
					String img=li.select("img").first().attr("src");
					long time=System.currentTimeMillis();
					String sql="INSERT INTO lb_store(name,video_href,sourceVideoHref,source_href,img,source_img,source_time,addTime,updateTime,one_category,source,resourceType) values(?,?,?,?,?,?,?,?,?,?,?,?)";
					Object[] params={name,video_href,sourceHref,sourceHref,img,img,time,time,time,"05","56",2};
					baseDao.insertBy(sql, params);
					System.out.println(video_href+name+img);
				}
			}
		 
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public static Integer getVideoDetail(String url,String videoType){
    	AnalyzeVideoUtil analyzeVideoUtil=new AnalyzeVideoUtil();
    	Integer status=1;
    	String source="腾讯";
		try {
			 
				Document doc=null;
				String name="";
				Element div=null;
				 
					String sourceHref=url;
					
					String video_href="";
					if(sourceHref.indexOf("56.com")!=-1){
						doc=Jsoup.connect(url).timeout(10000).get();
						div=doc.select("div.play_info").first();
						name=div.select("h1#video_title_text").first().attr("title");
						video_href=analyzeVideoUtil.analyzeFrom56(sourceHref);
						source="56";
					}else if(sourceHref.indexOf("qq.com")!=-1){
						if(sourceHref.indexOf("vid=")!=-1){
							video_href=analyzeVideoUtil.analyzeFromTencent2(sourceHref);	
						}
						
					}
					
					String one_category="01";
					String two_category="0102";
					if(videoType.equals("teach")){
						one_category="05";
						two_category="";
					}
					long time=System.currentTimeMillis();
					String sql="INSERT INTO lb_store(name,video_href,sourceVideoHref,source_href,source_time,addTime,updateTime,one_category,two_category,source,resourceType) values(?,?,?,?,?,?,?,?,?,?,?)";
					Object[] params={name,video_href,sourceHref,sourceHref,time,time,time,one_category,two_category,source,2};
					baseDao.insertBy(sql, params);
					System.out.println(video_href+name);
				 
			
		 
			 
		} catch (Exception e) {
			status=0;
			e.printStackTrace();
		}
		
		return status;
	}
    
    
    public static Integer addNewsFromWeiXin(Integer original,String url,Integer ifDownloadImg,Integer resourceType,String source,String img){
    	AnalyzeVideoUtil analyzeVideoUtil=new AnalyzeVideoUtil();
    	Integer status=1;
    	String content="";
    	
    	String title="test weixin";
		try {
			 
				Document doc=null;
				
				Element div=null;
				  Integer rule=0;
					String sourceHref=url;
					String imgUrl="";
					String video_href="";
					 doc=Jsoup.connect(url).userAgent(USERAGENT).timeout(100000).get();
						//doc=Jsoup.connect(url).timeout(10000).get();
						//获取标题
						 title = doc.title();
						 
						//获取封面
						String cover="";
						
						 if(source.equals("虎扑")){
							 status=addResourceFromHuPu(doc, ifDownloadImg, resourceType,url);
						 }else{
							 cover=StrUtil.getStrBySubString_2(doc.html(), "msg_cdn_url =", "msg_link",6,8).replace("\"", "").replace(";", "");
							 if(original==0){
								 source="篮球热";
							 }else{
								 source=doc.select("span.rich_media_meta_nickname").text();
							 }
								Elements ps=doc.select("div.rich_media_content").first().select("p");
								String con=ps.text();
								if(con.equals("")){
									//ps=doc.select("div.rich_media_content").first().select("section");
								}
								System.out.println(ps);
								for(Element p:ps){
									String text=p.text();
									//如果包含视频
									if(p.html().indexOf("vid=")!=-1){
										text=p.select("iframe").first().attr("data-src");
										if(text.equals("")){
											text=p.select("iframe").first().attr("src");
										}
										text=analyzeVideoUtil.analyzeFromTencent2(text);
										text="<iframe src=\""+text+"\""+" frameborder=\"0\" style=\"height:200px;\"></iframe>";
										
										content=content+text;
									}else{
										text=p.text();
										System.out.println(text);
										
										//判断是否有文字内容
										if(!text.equals("")&&text!=null&&text.length()>0){
											
											Integer index=-1;
											index=p.html().indexOf("data-src");
										
											//文字里可能也包含图片，所以判断是否包含图片，因为之前已经过滤视频，这里把包含data-src的属性默认当成图片链接
											if(index!=-1){
												 //如果当前条件为下载图片
												if(ifDownloadImg==1&&p.html().indexOf("vid=")==-1){
//													imgUrl=p.select("img").first().attr("data-src");
//													imgUrl=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgUrl);
//													imgUrl="<img src=\""+imgUrl+"\">";
//														content=content+imgUrl;
														Elements imgs=p.select("img");
														for(Element im:imgs){
															imgUrl=im.attr("data-src");
															imgUrl=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgUrl);
															imgUrl=imgUrl+ImageUtil.NEWS_DETAIL_SIZE;
															imgUrl="<img onclick=\"loadURL('gap://imgUrl="+imgUrl+"')\" src=\""+imgUrl+"\">";
																content=content+imgUrl;
														}
														//rule=1;
													}
												text="<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+text.replaceAll("\u3000", "")+"</p>";
												content=content+text;
												}else{
													//u3000代表中文空格
													text=text.replaceAll("\u3000", "").replace("&nbsp;", "");
													
													
													if(rule==1&&text.length()<12){
														text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:#003399;\">("+text+")</span>";
														
														
														content=content+text;
													}else{
														text="<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+text+"</p>";
														content=content+text;
													}
													rule=0;
												}
											
											
											
											
										}else{
											Integer index=-1;
											index=p.html().indexOf("data-src");
											//判断是否包含图片，因为之前已经过滤视频，这里把包含data-src的属性默认当成图片链接
											if(index!=-1){
												//下载文章中的图片（只抓取原链接）
												if(ifDownloadImg==1){
													Elements imgs=p.select("img");
													for(Element im:imgs){
														imgUrl=im.attr("data-src");
														imgUrl=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgUrl);
														imgUrl=imgUrl+ImageUtil.NEWS_DETAIL_SIZE;
														imgUrl="<p><img onclick=\"loadURL('gap://imgUrl="+imgUrl+"')\" src=\""+imgUrl+"\"></p>";
															content=content+imgUrl;
													}
													
//													imgUrl=p.select("img").first().attr("data-src");
//													imgUrl=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgUrl);
//													imgUrl="<img src=\""+imgUrl+"\">";
//														content=content+imgUrl;
													}
												rule=1;
												}
											}
									}
									
										
									
										
									}
								
								System.out.println(content);
								
								
								//封面图片
								if(!img.equals("")&&img!=null&&img.length()>0){
									img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(img);
								}else{
									img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(cover);
								}
								
								long time=System.currentTimeMillis();
								String sql="INSERT INTO lb_resource(name,img,content,addTime,updateTime,one_category,resourceType,source,source_href) values(?,?,?,?,?,?,?,?,?)";
								String oneCategory="06";
								if(resourceType==5){
									oneCategory="05";
								}
								content=content.replaceAll("(微博)", "");
								Object[] params={title,img,content,time,time,oneCategory,resourceType,source,url};
								status=baseDao.insertBy(sql, params);
								 
						 }
						 
						
				
			
		 
			 
		} catch (Exception e) {
			status=0;
			e.printStackTrace();
		}
		
		return status;
	}
    
	 
    public static Integer addResourceFromHuPu(Document doc,Integer ifDownloadImg,Integer resourceType,String url){
    	AnalyzeVideoUtil analyzeVideoUtil=new AnalyzeVideoUtil();
    	Integer status=1;
    	String content="";
    	String source="虎扑";
    	
    	String title="test weixin";
		try {
					
					String video_href="";
				
						//获取标题
						 title = doc.title();
						 
					
							   
//							    Elements select=doc.select("section.detail-wrap");
//							    if(select.size()>0&&select!=null&&!select.equals("")){
//							        String c=doc.select("section.detail-wrap").first().select("article.article-content").html();
//									   
//								    String d=doc.select("section.detail-wrap").first().select("article.article-content").text();
//									
//								     while(c.indexOf("center")!=-1){
//								    	int a=c.indexOf("<center>");
//								    	int b=c.indexOf("</center>");
//								    	String x=c.substring(0, a);
//								    	String y=c.substring(b+16, c.length());
//								    	c=x+y;
//								    	System.out.println(c);
//								    }
//								     content=c.replace("<br />", "<br /><br />");
//							    }else{
//							    	 
//							    	String c=doc.select("table.case").first().select("div").outerHtml();
//							    	
//							    	  while(c.indexOf("div")!=-1){
//									    	int a=c.indexOf("div");
//									    	int b=c.indexOf("</div");
//									    	String x=c.substring(0, a);
//									    	String y=c.substring(b+16, c.length());
//									    	c=x+y;
//									    	System.out.println(c);
//									    }
//									     content=c.replace("<br />", "<br /><br />");
//									     
//							    	System.out.println(c);
//							    }
							
							    
							    
//							    System.out.println(c);
							   // System.out.println(c);
							   
						   Elements ps=doc.select("div.artical-main-content").select("p");
						   String img=doc.select("div.artical-importantPic").select("img").first().attr("src");
						   img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(img);
						   
								for(Element p:ps){
									String text=p.text();
									 
									if(!text.equals("")&&text!=null&&text.length()>0){
										text="<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+text+"</p>";
										content=content+text;
									}
										
										 
										
									}
					
						    
					

					long time=System.currentTimeMillis();
					String sql="INSERT INTO lb_store(name,img,content,addTime,updateTime,one_category,resourceType,source,source_href) values(?,?,?,?,?,?,?,?,?)";
					String oneCategory="06";
					if(resourceType==5){
						oneCategory="05";
					}
					Object[] params={title,img,content,time,time,oneCategory,resourceType,"篮球热",url};
					status=baseDao.insertBy(sql, params);
			
		 
			 
		} catch (Exception e) {
			status=0;
			e.printStackTrace();
		}
		
		return status;
	}
}
