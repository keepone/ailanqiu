package com.lb.crawl.nba;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.utils.StrUtil;


@Component
public class GetNBANewsFromSina {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String startURL = "http://sports.163.com/nba/";
		getSinaNewsTxtContent("http://sports.sina.com.cn/nba/2015-01-16/14497485461.shtml");
	}

	/**
	 * �����˻�ȡnba����
	 * 
	 * @param startURL
	 * @return
	 */
	public static void getNewsBySina(String startURL) {
		int count = 1;
		int f = 1;
		 
		Document doc = null;

		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
		} catch (IOException e1) {
			System.out.println("�����쳣............");
			getNewsBySina(startURL);
			e1.printStackTrace();
		}

		JSONObject json = null;
		String name = null;
		Elements toutiao = doc.select("h4").select("a[href]");
		for (Element e : toutiao) {
			json = new JSONObject();
			name = e.ownText();
			System.out.println(name);
			if (name.indexOf("�ֲ�") == -1 && name.indexOf("ֱ��") == -1
					&& name.indexOf("��Ƶ��") == -1) {

				String sql1 = "select count(*) AS count from lb_store where NAME='"
						+ name + "'";
				Integer c = Integer.parseInt(((Map) baseDao.findBy(sql1, null)
						.get(0)).get("count").toString());
				if (c==0) {
					String href = e.attr("href");
					JSONObject video = getSinaNewsVideoContent(href);
					if (video != null && video.size() > 0) {
						//System.out.println(count + "-------------" + name);
//						String video_href = video.get("video_href").toString();
//						String video_name = video.get("video_name").toString();
//						String video_intro = video.get("video_intro")
//								.toString();

						// ��¼����Ƶ

					 
					} else {

						JSONObject txt = getSinaNewsTxtContent(href);

						if (txt.size() > 0) {
							// System.out.println(count+"-------------"+name);
							String txtContent = txt.get("content").toString();
						 
									
							String img_href = txt.get("img").toString();

							String sql = "INSERT into lb_store(NAME,content,img,source_img,addTime,one_category,two_category,resourceType,source,source_href) VALUES(?,?,?,?,?,?,?,?,?,?)";
						
							long current_time = System.currentTimeMillis();
							Object[] params = { name, txtContent, img_href,
									img_href, current_time, "01", "0101", 1,
									"����",href };

							baseDao.insertBy(sql, params);

							System.out.println(name);
						}
					}

				}

			} else {
				System.out
						.println("---------------------------------------------------"
								+ count + "---������Ҫ��" + name);
			}
		}

		Elements elements = doc.select("div.tou_c1").select("[href]");
		for (Element element : elements) {
			json = new JSONObject();
			name = element.ownText();
			if (name.indexOf("�ֲ�") == -1 && name.indexOf("ֱ��") == -1&& name.indexOf("��Ƶ��") == -1) {
				
						String sql1 = "select count(*) AS count from lb_store where NAME='"
								+ name + "'";
						Integer c = Integer.parseInt(((Map) baseDao.findBy(
								sql1, null).get(0)).get("count").toString());
						if (c == 0) {
							String href = element.attr("href");
							JSONObject video = getSinaNewsVideoContent(href);
							if (video != null && video.size() > 0) {
//								System.out.println(count + "-------------" + name);
//								String video_href = video.get("video_href").toString();
//								String video_name = video.get("video_name").toString();
//								String video_intro = video.get("video_intro").toString();
							 
							} else {
								 
								JSONObject txt = getSinaNewsTxtContent(href);

								if (txt.size() > 0) {
									String txtContent = txt.get("content").toString();
									txtContent=txtContent.replace("��������Ѷ", "").replace("<p>���ղ�!</p>", "")
											.replace("����ͨ��������ҳ���� ������ �鿴�����ղع������¡�", "")
											.replace("֪����", "").replace("<p></p>", "").replace("(΢��)", "");
									String img_href = txt.get("img").toString();
									 
									String sql = "INSERT into lb_store(NAME,content,img,source_img,addTime,one_category,two_category,resourceType,source,source_href) VALUES(?,?,?,?,?,?,?,?,?,?)";
									long current_time = System.currentTimeMillis();
									Object[] params = { name, txtContent, img_href,
											img_href, current_time, "01", "0101", 1, "����",href };

									 
										baseDao.insertBy(sql, params);
										System.out.println(name);
									}
									 
								}
							}
							 
 
						 
			} else {
				System.out
						.println("---------------------------------------------------"
								+ count + "---������Ҫ��" + name);
			}

			count = count + 1;
		}

		System.out.println("have scan" + (count - 1));
		 
	}

	public static JSONObject getSinaNewsTxtContent(String url) {
		String result = "";
		JSONObject jsonObject = new JSONObject();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(60000).get();
			Element e = doc.select("div.blkContainerPblk").first();
			if (e != null) {
				Elements ee = e.select("div.img_wrapper");
				String img = null;
				if ((ee.size() > 0)) {
					img = ee.select("img").first().attr("src");
				} else {

					img = e.select("div.blk_hd_pic").select("img").first()

							.attr("src");
				}
				
				Elements elements = e.select("p");
				String content = "";
				for (Element element : elements) {
					String text=element.ownText();
					if(text.indexOf("��������ʱ��")!=-1){
						System.out.println("f");
					}
					text=StrUtil.replaceStrForCraw(text);
					if(text.length()>2){
						
						text=StrUtil.filterStr(text);
						content = content + text;
					}
				}
				jsonObject.put("content", content);
				jsonObject.put("img", img);
			}
			// System.out.println(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static JSONObject getSinaNewsVideoContent(String url) {
		String result = "";
		JSONObject jsonObject = new JSONObject();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(60000).get();
			Element e = doc.select("meta[name=description]").first();
			if (e != null) {
				String description = e.attr("content");
				if (description.indexOf("��Ƶ") != -1) {
					result = description;
					if (doc.html().indexOf("    <!--�����ƵLs-->") != -1) {
						Element ee = doc.select("dd.vedio_des").first();
						String result_video_href = ee.select("a[href]").attr(
								"node-url");
						String video_intro = doc.select("em[task$=oldinfor]")
								.select("p").first().ownText();
						jsonObject.put("video_name", description);
						jsonObject.put("video_href", result_video_href);
						jsonObject.put("video_intro", video_intro.trim());
					} else if (doc.html().indexOf("�Ƽ���Ƶ") != -1) {
						// ʮ������ʱֻѡĬ�ϵ�ʮ����������ʮ����ȡ��
						description = doc.select("div.slide01_items").first()
								.attr("data-title");
						String result_video_href = doc
								.select("div.slide01_items").first()
								.attr("data-url");
						String video_intro = doc.select("div.slide01_items")
								.select("p").first().ownText();
						// System.out.println(video_intro);
						jsonObject.put("video_name", description);
						jsonObject.put("video_href", result_video_href);
						jsonObject.put("video_intro", video_intro.trim());
					}

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
