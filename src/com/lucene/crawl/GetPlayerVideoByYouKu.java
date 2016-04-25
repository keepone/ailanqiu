package com.lucene.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.TimeUtil;
 
@Component
public class GetPlayerVideoByYouKu {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private static List<JSONObject> list = new ArrayList<JSONObject>();

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		String str="  Nike 德隆 挡拆          后     投篮    （NIKE精品篮球教学）";
		//System.out.println(str.toLowerCase().trim().replace(" ", ""));
		String startURL = "http://www.soku.com/search_video/q_%E5%8A%A0%E5%86%9C%E8%B4%9D%E5%85%8B_orderby_1_page_";
		//getYouKuVideo(startURL);
	 //getYouKuVideoFromNike("http://www.soku.com/search_video/q_NIKE%E7%B2%BE%E5%93%81%E7%AF%AE%E7%90%83%E6%95%99%E5%AD%A6_orderby_1_page_");
		// System.out.println(2%101);
		/*
		 * String ZZ="马云"; try{ String str = "\u53d6"; byte [] A
		 * =str.getBytes("GB2312"); System.out.println(A.toString());
		 * }catch(java.io.UnsupportedEncodingException e){ }
		 */
		//addZhuanJi("http://www.youku.com/playlist_show/id_222753_ascending_1_mode_pic_page_1.html");
		
		addNBA98("http://www.nba98.com/lanqiu/");
	}

	//优酷专辑
	public static void addZhuanJi(String startURL) {
		AnalyzeVideoUtil analyzeVideoUtil=new AnalyzeVideoUtil();
		 try {
			Document doc=Jsoup.connect(startURL).timeout(30000).get();
			Elements es=doc.select("div.items").select("ul");
			for(Element e:es){
				String title=e.select("li").first().select("a").attr("title");
				String videoHref=e.select("li").first().select("a").attr("href");
				String img=e.select("li.v_thumb").first().select("img").attr("src");
				String one_category = "05";
				String two_category = "0508";
				String mp4_href=analyzeVideoUtil.analyzeFromYouKu(videoHref);
				String sql = "INSERT  INTO  lb_resource(name,video_href,img,addTime,source_img,source_href,one_category,two_category,source,sourceVideoHref,resourceType,updateTime) values(?,?,?,?,?,?,?,?,?,?,?,?)";
				Object[] params = { title, mp4_href, img,
						System.currentTimeMillis(), img, videoHref,
						one_category, two_category, "优酷",videoHref,2,System.currentTimeMillis()};
				
				Integer sum=0;
				String sql1 = "select count(*) AS count from lb_resource where NAME='"
						+ title + "'";
				sum = Integer
						.parseInt(((Map) baseDao.findBy(sql1, null)
								.get(0)).get("count").toString());
				
				
				if(sum==0&&title.indexOf("高级")==-1){
					baseDao.insertBy(sql, params);
				}
				System.out.println(title+img+videoHref);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//腾讯专辑
		public static void addZhuanJiByTencent(String startURL) {
			Integer count=0;
			AnalyzeVideoUtil analyzeVideoUtil=new AnalyzeVideoUtil();
			 try {
				Document doc=Jsoup.connect(startURL).timeout(30000).get();
				Elements es=doc.select("div.figures_wrap");
				System.out.println(es);
				for(Element e:es){
					String title=e.select("a").first().attr("title");
					String videoHref=e.select("a").first().attr("href");
					String img=e.select("a").first().select("img").attr("src");
					String one_category = "05";
					String two_category = "0508";
					String mp4_href="";
					try {
						mp4_href = analyzeVideoUtil.analyzeFromTencent(videoHref);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					
					Integer sum=0;
					String sql1 = "select count(*) AS count from lb_store where NAME='"
							+ title + "'";
//					sum = Integer
//							.parseInt(((Map) baseDao.findBy(sql1, null)
//									.get(0)).get("count").toString());
					
					
					if(sum==0&&title.indexOf("一天一战术")!=-1){
						count=count+1;
						title=title+"【第"+count+"天】";
						String sql = "INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,one_category,source,sourceVideoHref,resourceType,updateTime) values(?,?,?,?,?,?,?,?,?,?,?)";
						Object[] params = { title, mp4_href, img,
								System.currentTimeMillis(), img, videoHref,
								one_category, "篮球战术解析",videoHref,5,System.currentTimeMillis()};
						//baseDao.insertBy(sql, params);
					}
					System.out.println(title+img+videoHref);
				}
				System.out.println(count);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	public static void addNBA98(String startURL) {
		AnalyzeVideoUtil analyzeVideoUtil=new AnalyzeVideoUtil();
		 try {
			Document doc=Jsoup.connect(startURL).timeout(30000).get();
			Elements es=doc.select("div#mainContent").select("li");
			for(Element e:es){
				String title=e.select("a").first().attr("title");
				String videoHref=e.select("a").first().attr("abs:href");
				String img=e.select("img").first().attr("abs:src");
				String one_category = "01";
				String two_category = "0102";
				
				String detailViedoHref=getNBA98VideoDetail(videoHref);
				if(detailViedoHref.indexOf("sina.com")==-1){  //排除新浪视频
					JSONObject json=analyzeVideoUtil.getVideoSourceAndPlayURL(detailViedoHref);
					
					String mp4_href=json.getString("videoHref");
					String source=json.getString("source");
					
					if(!mp4_href.equals("false")){
						String sql = "INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,one_category,two_category,source,sourceVideoHref,resourceType,updateTime) values(?,?,?,?,?,?,?,?,?,?,?,?)";
						Object[] params = { title, mp4_href, img,
								System.currentTimeMillis(), img, videoHref,
								one_category, two_category, source,videoHref,2,System.currentTimeMillis()};
						
						Integer sum=0;
						String sql1 = "select count(*) AS count from lb_store where NAME='"
								+ title + "'";
						sum = Integer
								.parseInt(((Map) baseDao.findBy(sql1, null)
										.get(0)).get("count").toString());
						
						
						if(sum==0){
							baseDao.insertBy(sql, params);
							
						}
						System.out.println(title+img+"-----"+mp4_href);
					}
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getNBA98VideoDetail(String startURL) {
		 
		String str="";
		 try {
			 
			Document doc=Jsoup.connect(startURL).timeout(30000).get();
			Element e=doc.select("div#logPanel").select("a").first();
			str=e.attr("abs:href");
		 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return str;
	}
	
	public static void getYouKuVideo(String startURL) {
		JSONArray arr = new JSONArray();
		List<String> list = new ArrayList<String>();
		List<String> list_2 = new ArrayList<String>();
		// list.add("学会三种就�耪ㄌ炝耍〖优┍纯私棠�13种NBA球星的过人绝招（慢动作）");
		AnalyzeVideoUtil analyzeVideoUtil = new AnalyzeVideoUtil();
		int count = 1;
		int f = 1;

		// 起始时间
		long begin_time = System.currentTimeMillis();

		Document doc = null;
		for (int j = 1; j <= 32; j++) {
			try {
				doc = Jsoup.connect(startURL + j).timeout(30000).get();
			} catch (IOException e) {
				System.out.println("发生异常,再次请求.................");
				try {
					Thread.sleep(5000);
					getYouKuVideo(startURL);
					System.out.println("重写请求的地址是：" + startURL);
				} catch (InterruptedException e1) {
					System.out.println("线程出现问题");

				}

			}
			Elements divs = doc.select("div.v");
			System.out.println("当前页数：" + f + "-------" + startURL + j);
			for (Element div : divs) {
				boolean mark = true;
				String name = div.select("img").first().attr("alt");
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						String value = list.get(i);
						if (value.equals(name)) {
							mark = false;
							list_2.add(name);
							break;
						}

					}

				}
				if (mark) {
					list.add(name);
					String source_href = div.select("div.v-link").first()
							.select("a[href]").first().attr("href");
					String source_img = div.select("img").first().attr("src");
					System.out.println(count + "---" + name);
					String mp4_href = "";
					try {
						mp4_href = analyzeVideoUtil
								.analyzeFromYouKu(source_href);
						// TODO Auto-generated catch block e.printStackTrace();
						// }
						// 解析后的MP4格式视频链接(可以在IPAD等移动端兼容播放) long
						long current_time = System.currentTimeMillis(); // 下载图片
						String savepath = "D:/tomcat6/Tomcat 6.0/webapps/YouKuTeachImg/";
						String img_name = current_time + ".jpg";
						savepath = savepath + img_name;
						//IoUtil.downloadImage(source_img, "", savepath);
						String one_category = "05";
						String two_category = "0511";
						String sql = "INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,one_category,two_category,source,sourceVideoHref,resourceType) values(?,?,?,?,?,?,?,?,?,?,?)";
//						if (name.indexOf("加农贝克教你打篮球") != -1) {
//							//two_category = two_category + " 0511";
//						}
//						if (name.indexOf("基本") != -1) {
//							//two_category = two_category+ " 0500";
//						}
//						if (name.indexOf("过人") != -1
//								|| name.indexOf("突破") != -1) {
//							two_category = two_category + " 0502";
//						} else {
//							two_category = two_category + " " + "0500";
//						}
						Object[] params = { name, mp4_href, source_img,
								current_time, source_img, source_href,
								one_category, two_category, "优酷",source_href,2};
						baseDao.insertBy(sql, params);

					} catch (Exception e) {

					}

					/*
					 * Element ele=doc.select("div.pagebox").last(); String
					 * next_page=ele.text(); if(next_page.indexOf("下一页")!=-1){
					 * startURL=ele.select("a[href]").last().attr("abs:href");
					 * //startURL=""; System.out.println("下一页地址："+startURL);
					 * }else{ startURL=""; }
					 */
				}

				count += 1;
			}

		 
			f += 1;
		}
		// 结束时间
		long end_time = System.currentTimeMillis();
		System.out.println("页数：" + f);
		System.out.println("重复：" + list_2.size());
		System.out.println("本次从新浪视频库下载" + (count - 1) + "个视频,用时:"
				+ (end_time - begin_time) / 60000 + "分钟");
	}
	
	//获取跟NIKE有关的篮球教学
	public static void getYouKuVideoFromNike(String startURL) {
		JSONArray arr = new JSONArray();
		List<String> list = new ArrayList<String>();
		List<String> list_2 = new ArrayList<String>();
		// list.add("学会三种就�耪ㄌ炝耍〖优┍纯私棠�13种NBA球星的过人绝招（慢动作）");
		AnalyzeVideoUtil analyzeVideoUtil = new AnalyzeVideoUtil();
		int count = 1;
		int f = 1;
		int not_suit=1;
		int total=1;
		// 起始时间
		long begin_time = System.currentTimeMillis();

		Document doc = null;
		for (int j = 1; j <= 35; j++) {
			try {
				doc = Jsoup.connect(startURL + j).timeout(30000).get();
			} catch (IOException e) {
				System.out.println("发生异常,再次请求.................");
				try {
					Thread.sleep(5000);
					getYouKuVideo(startURL);
					System.out.println("重写请求的地址是：" + startURL);
				} catch (InterruptedException e1) {
					System.out.println("线程出现问题");

				}

			}
			Elements divs = doc.select("div.v");
			System.out.println("当前页数：" + f + "-------" + startURL + j);
			for (Element div : divs) {
				boolean mark = true;
				String name = div.select("img").first().attr("alt");
				name=name.toUpperCase().trim().replace(" ", "");  //将英文转换成小写
				if(name.indexOf("NIKE篮球教学")!=-1||name.indexOf("NIKE精品篮球教学")!=-1||name.indexOf("NIKE篮球高手教学")!=-1||name.indexOf("NIKE篮球专业教学")!=-1||name.indexOf("NIKE篮球教程")!=-1||name.indexOf("高手教學")!=-1){
					if (list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							String value = list.get(i);
							if (value.equals(name)) {
								mark = false;
								list_2.add(name);
								break;
							}

						}

					}
					if (mark) {
						list.add(name);
						String source_href = div.select("div.v-link").first()
								.select("a[href]").first().attr("href");
						String source_img = div.select("img").first().attr("src");
						String play_count=div.select("div.v-meta-entry").text();
						int begin_index=play_count.indexOf("播放:");
						int end_index=play_count.indexOf("发布");
						play_count=play_count.substring(begin_index+3,end_index-1);
						
						String mp4_href = "";
						try {
							 mp4_href = analyzeVideoUtil
									.analyzeFromYouKu(source_href);
							// TODO Auto-generated catch block e.printStackTrace();
							// }
							// 解析后的MP4格式视频链接(可以在IPAD等移动端兼容播放) long
							long current_time = System.currentTimeMillis(); // 下载图片
							String savepath = "D:/tomcat6/Tomcat 6.0/webapps/YouKu_Nike/";
							String img_name = current_time + ".jpg";
							savepath = savepath + img_name;
							//IoUtil.downloadImage(source_img, "", savepath);
							String one_category = "05";
							String two_category = "0512";
							String sql = "INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,one_category,two_category,source,resourceType) values(?,?,?,?,?,?,?,?,?,?)";
							 
							Object[] params = { name, mp4_href, source_img,
									current_time, source_img, source_href,
									one_category, two_category, "优酷",2}; 
							 baseDao.insertBy(sql, params);
							 System.out.println(count + "---" + name);
						} catch (Exception e) {
							
						}

						/*
						 * Element ele=doc.select("div.pagebox").last(); String
						 * next_page=ele.text(); if(next_page.indexOf("下一页")!=-1){
						 * startURL=ele.select("a[href]").last().attr("abs:href");
						 * //startURL=""; System.out.println("下一页地址："+startURL);
						 * }else{ startURL=""; }
						 */
						count += 1;
					}
						
					
					
				}else{
					not_suit=not_suit+1;
				}
				
				total=total+1;
			}

			 
			f += 1;
		}
		// 结束时间
		long end_time = System.currentTimeMillis();
		System.out.println("页数：" + f);
		System.out.println("重复：" + list_2.size());
		System.out.println("本次从优酷视频库扫描"+(total-1)+"个视频，下载" + (count - 1) + "个视频,用时:"+ (end_time - begin_time) / 60000 + "分钟，重复视频"+list.size()+"个视频,不符合要求的有"+(not_suit-1)+"个视频");
	}
	
	
	//获取暑期篮球教学系列
		public static void getYouKuVideoFromShuQi(String startURL) {
			JSONArray arr = new JSONArray();
			List<String> list = new ArrayList<String>();
			List<String> list_2 = new ArrayList<String>();
			// list.add("学会三种就�耪ㄌ炝耍〖优┍纯私棠�13种NBA球星的过人绝招（慢动作）");
			AnalyzeVideoUtil analyzeVideoUtil = new AnalyzeVideoUtil();
			int count = 1;
			int f = 1;
			int not_suit=1;
			int total=1;
			// 起始时间
			long begin_time = System.currentTimeMillis();

			Document doc = null;
			for (int j = 1; j <= 2; j++) {
				try {
					doc = Jsoup.connect(startURL + j).timeout(30000).get();
				} catch (IOException e) {
					System.out.println("发生异常,再次请求.................");
					try {
						Thread.sleep(5000);
						getYouKuVideo(startURL);
						System.out.println("重写请求的地址是：" + startURL);
					} catch (InterruptedException e1) {
						System.out.println("线程出现问题");

					}

				}
				Elements divs = doc.select("div.v");
				System.out.println("当前页数：" + f + "-------" + startURL + j);
				for (Element div : divs) {
					boolean mark = true;
					String name = div.select("img").first().attr("alt");
					name=name.toUpperCase().trim().replace(" ", "");  //将英文转换成小写
					if(name.indexOf("暑期篮球")!=-1){
						if (list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								String value = list.get(i);
								if (value.equals(name)) {
									mark = false;
									list_2.add(name);
									break;
								}

							}

						}
						if (mark) {
							list.add(name);
							String source_href = div.select("div.v-link").first()
									.select("a[href]").first().attr("href");
							String source_img = div.select("img").first().attr("src");
							String play_count=div.select("div.v-meta-entry").text();
							int begin_index=play_count.indexOf("播放:");
							int end_index=play_count.indexOf("发布");
							play_count=play_count.substring(begin_index+3,end_index-1);
							
							String mp4_href = "";
							try {
								 mp4_href = analyzeVideoUtil
										.analyzeFromYouKu(source_href);
								// TODO Auto-generated catch block e.printStackTrace();
								// }
								// 解析后的MP4格式视频链接(可以在IPAD等移动端兼容播放) long
								long current_time = System.currentTimeMillis(); // 下载图片
								String savepath = "D:/tomcat6/Tomcat 6.0/webapps/YouKu_Nike/";
								String img_name = current_time + ".jpg";
								savepath = savepath + img_name;
								//IoUtil.downloadImage(source_img, "", savepath);
								String one_category = "05";
								String two_category = "0515";
								String sql = "INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,one_category,two_category,source,resourceType) values(?,?,?,?,?,?,?,?,?,?)";
								 
								Object[] params = { name, mp4_href, source_img,
										current_time, source_img, source_href,
										one_category, two_category, "优酷",2}; 
								 baseDao.insertBy(sql, params);
								 System.out.println(count + "---" + name);
							} catch (Exception e) {
								
							}

							/*
							 * Element ele=doc.select("div.pagebox").last(); String
							 * next_page=ele.text(); if(next_page.indexOf("下一页")!=-1){
							 * startURL=ele.select("a[href]").last().attr("abs:href");
							 * //startURL=""; System.out.println("下一页地址："+startURL);
							 * }else{ startURL=""; }
							 */
							count += 1;
						}
							
						
						
					}else{
						not_suit=not_suit+1;
					}
					
					total=total+1;
				}

				 
				f += 1;
			}
			// 结束时间
			long end_time = System.currentTimeMillis();
			System.out.println("页数：" + f);
			System.out.println("重复：" + list_2.size());
			System.out.println("本次从优酷视频库扫描"+(total-1)+"个视频，下载" + (count - 1) + "个视频,用时:"+ (end_time - begin_time) / 60000 + "分钟，重复视频"+list.size()+"个视频,不符合要求的有"+(not_suit-1)+"个视频");
		}
	
	
	//获取［五虎篮球］教学
	public static void getWuHu(String startURL) {
		JSONArray arr = new JSONArray();
		List<String> list = new ArrayList<String>();
		List<String> list_2 = new ArrayList<String>();
		// list.add("学会三种就�耪ㄌ炝耍〖优┍纯私棠�13种NBA球星的过人绝招（慢动作）");
		AnalyzeVideoUtil analyzeVideoUtil = new AnalyzeVideoUtil();
		int count = 1;
		int f = 1;
		int not_suit=1;
		int total=1;
		// 起始时间
		long begin_time = System.currentTimeMillis();

		Document doc = null;
		for (int j = 1; j <= 10; j++) {
			try {
				doc = Jsoup.connect(startURL + j).timeout(30000).get();
			} catch (IOException e) {
				System.out.println("发生异常,再次请求.................");
				try {
					Thread.sleep(5000);
					getYouKuVideo(startURL);
					System.out.println("重写请求的地址是：" + startURL);
				} catch (InterruptedException e1) {
					System.out.println("线程出现问题");

				}

			}
			Elements divs = doc.select("div.v");
			System.out.println("当前页数：" + f + "-------" + startURL + j);
			for (Element div : divs) {
				boolean mark = true;
				String name = div.select("img").first().attr("alt");
				name=name.toUpperCase().trim().replace(" ", "");  //将英文转换成小写
				if(name.indexOf("五虎篮球")!=-1){
					if (list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							String value = list.get(i);
							if (value.equals(name)) {
								mark = false;
								list_2.add(name);
								break;
							}

						}

					}
					if (mark) {
						list.add(name);
						String source_href = div.select("div.v-link").first()
								.select("a[href]").first().attr("href");
						String source_img = div.select("img").first().attr("src");
						String play_count=div.select("div.v-meta-entry").text();
						int begin_index=play_count.indexOf("播放:");
						int end_index=play_count.indexOf("发布");
						play_count=play_count.substring(begin_index+3,end_index-1);
						System.out.println(count + "---" + name);
						String mp4_href = "";
						try {
						 if(source_href.indexOf("youku")!=-1){
							 mp4_href = analyzeVideoUtil
										.analyzeFromYouKu(source_href);
						 }else{
							 mp4_href = analyzeVideoUtil
										.analyzeFromTuDou(source_href);
						 }
							// TODO Auto-generated catch block e.printStackTrace();
							// }
							// 解析后的MP4格式视频链接(可以在IPAD等移动端兼容播放) long
							long current_time = System.currentTimeMillis(); // 下载图片
							String savepath = "D:/tomcat6/Tomcat 6.0/webapps/YouKu_Nike/";
							String img_name = current_time + ".jpg";
							savepath = savepath + img_name;
							//IoUtil.downloadImage(source_img, "", savepath);
							String one_category = "05";
							String two_category = "0514";
							String sql = "INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,one_category,two_category,source,resourceType) values(?,?,?,?,?,?,?,?,?,?)";
							 
							Object[] params = { name, mp4_href, source_img,
									current_time, source_img, source_href,
									one_category, two_category, "优酷",2}; 
							baseDao.insertBy(sql, params);

						} catch (Exception e) {

						}

						/*
						 * Element ele=doc.select("div.pagebox").last(); String
						 * next_page=ele.text(); if(next_page.indexOf("下一页")!=-1){
						 * startURL=ele.select("a[href]").last().attr("abs:href");
						 * //startURL=""; System.out.println("下一页地址："+startURL);
						 * }else{ startURL=""; }
						 */
						count += 1;
					}
						
					
					
				}else{
					not_suit=not_suit+1;
				}
				
				total=total+1;
			}

		 
			f += 1;
		}
		// 结束时间
		long end_time = System.currentTimeMillis();
		System.out.println("页数：" + f);
		System.out.println("重复：" + list_2.size());
		System.out.println("本次从优酷视频库扫描"+(total-1)+"个视频，下载" + (count - 1) + "个视频,用时:"+ (end_time - begin_time) / 60000 + "分钟，重复视频"+list_2.size()+"个视频,不符合要求的有"+(not_suit-1)+"个视频");
	}
	public static void get(String LOGIN_URL) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("q", "iverson");
		Connection conn = Jsoup.connect(LOGIN_URL);
		conn
				.header("(Request-Line)",
						"POST /cgi-bin/login?lang=zh_CN HTTP/1.1");
		conn.header("Accept", "application/json, text/javascript, */*; q=0.01");
		conn.header("Accept-Encoding", "gzip, deflate");
		conn.header("Accept-Language", "zh-cn");
		conn.header("Cache-Control", "no-cache");
		conn.header("Connection", "Keep-Alive");
		conn.header("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		conn.header("Host", "mp.weixin.qq.com");
		conn.header("Referer", "https://sina.com.cn/");
		conn
				.header("User-Agent",
						"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)");
		try {
			Response response = conn.execute();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
