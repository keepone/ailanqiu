package com.lb.crawl.nba;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jiutong.test.Log;
import com.lb.dao.BaseDao;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.IoUtil;
 
@Component
public class GetNBAVideoFromHuPu {

	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private static int sum = 0;

	public static void main(String[] args) {
		// go();
	 
	}

	/**
	 *  ��ȡ������Ƶ
	 * 
	 * @param startURL
	 * @return
	 */
	public static void addVideo(
			String startURL) {

		boolean mark = true;
		Document doc = null;
		int count = 0;
		AnalyzeVideoUtil analyze = new AnalyzeVideoUtil();
		long begin = System.currentTimeMillis();
		try {
			 
				doc = Jsoup.connect(startURL).get();
				Elements elements = doc.select("div.p_video");
				Elements elements2 = elements.select("div.img_outer");

				for (Element element : elements2) {
					Element e = element.select("a[href]").first();
					Element img = element.select("img[src]").first();
					String title = e.attr("title");
					String href = e.attr("href");
					String image = img.attr("src");
					if (title != null && !title.equals("")&&title.indexOf("¼��")==-1&& !title.equals("")&&title.indexOf("¼��")==-1) {//���� ��¼����Ƶ
						if (title.indexOf("?") != -1) {
							title = title.replace("?", "");
							System.out.println(title);
						}
						if (title.indexOf("\"") != -1) {
							title = title.replace("\"", "");
							System.out.println(title);
						}
						if (title.indexOf("\\:") != -1) {
							title = title.replace("\\:", "");
							System.out.println(title);
						}
						if (title.indexOf("/") != -1) {
							title = title.replace("/", "");
							System.out.println(title);
						}
						if (title.indexOf("<") != -1) {
							title = title.replace("<", "");
							System.out.println(title);
						}
						if (title.indexOf(">") != -1) {
							title = title.replace(">", "");
							System.out.println(title);
						}
						if (title.indexOf("*") != -1) {
							title = title.replace("*", "");
							System.out.println(title);
						}
						if (title.indexOf("|") != -1) {
							title = title.replace("|", "");
							System.out.println(title);
						}
						if (title.indexOf("'") != -1) {
							title = title.replace("'", "");
							System.out.println(title);
						}

						String detailHref = getEndVideoURL(href);
						String source = "δȷ��";
						if (detailHref.indexOf("youku") != -1) {
							source = "�ſ�";
						} else if (detailHref.indexOf("56.com") != -1) {
							source = "56";
						} else if (detailHref.indexOf("letv.com") != -1) {
							source = "LETV";
						} else if (detailHref.indexOf("sina") != -1) {
							source = "����";
						} else if (detailHref.indexOf("ku6") != -1) {
							source = "ku6";
						} else if (detailHref.indexOf("tudou") != -1) {
							source = "����";
						} else if (detailHref.indexOf("souhu") != -1) {
							source = "�Ѻ�";
						} else if (detailHref.indexOf("cntv") != -1) {
							source = "CNTV";
						} else if (detailHref.indexOf("pptv") != -1) {
							source = "PPTV";
						}  else if (detailHref.indexOf("qq") != -1) {
							source = "��Ѷ";
						}else {

						}

						if (source.equals("��Ѷ")||source.equals("56")||source.equals("����")) {
							String sql1 = "select count(*) AS count from lb_store where NAME='"
									+ title + "'";
							Integer c = Integer
									.parseInt(((Map) baseDao.findBy(sql1, null)
											.get(0)).get("count").toString());
							if (c == 0) {

								String sql = "INSERT INTO lb_store(name,video_href,img,source_img,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";
								String video_href = "";
								try {
									if(source.equals("��Ѷ")){
										if(detailHref.indexOf("vid=")!=-1){
											video_href = analyze
													.analyzeFromTencent2(detailHref);
										}else{
											video_href = analyze
													.analyzeFromTencent(detailHref);
										}
									}else if(source.equals("56")){
										if(detailHref.indexOf("swf")!=-1){
											video_href = analyze
													.analyzeFrom56_2(detailHref);
										}else{
											video_href = analyze
													.analyzeFrom56(detailHref);
										}
									}else if(source.equals("����")){
										video_href=analyze.analyzeFromTuDou(detailHref);
									}
									if(image.indexOf("?")!=-1){
										int in=image.indexOf("?");
										image=image.substring(0,in);
									}
									Object[] params = { title, video_href, image,
											image, System.currentTimeMillis(),
											detailHref, "01", "0102", 2, source };
									baseDao.insertBy(sql, params);
									System.out.println(title + "---" + "---"
											+ image + "---" + source + "---"
											+ detailHref);
								} catch (Exception e1) {
									System.out.println("������Ƶ�������Ի�����Ƶ����ģ��"+detailHref);
									e1.printStackTrace();
								}

							} else {
								 System.out.println("�Ѿ�¼�뻢����Ƶ");
							}
						} else {

						}

					}

				}

			 

		} catch (IOException e) {
			System.out.println("�������Ի�����Ƶ����ģ��");
			e.printStackTrace();
		}

	}

	
	
	/**
	 *  ��ȡ����ͷ����Ƶ
	 * 
	 * @param startURL
	 * @return
	 */
	public static void addTouTiao(
			String startURL) {

		boolean mark = true;
		Document doc = null;
		int count = 1;
		AnalyzeVideoUtil analyze = new AnalyzeVideoUtil();
		long begin = System.currentTimeMillis();
		try {
			 
				doc = Jsoup.connect(startURL).get();
				Elements elements = doc.select("div.p_video");
				Elements elements2 = elements.select("div.img_outer");

				for (Element element : elements2) {
					
					Element e = element.select("a[href]").first();
					Element img = element.select("img[src]").first();
					String title = e.attr("title");
					String href = e.attr("href");
					String image = img.attr("src");
					if (title != null && !title.equals("")&&title.indexOf("¼��")==-1&& !title.equals("")&&title.indexOf("¼��")==-1) {//���� ��¼����Ƶ
						if (title.indexOf("?") != -1) {
							title = title.replace("?", "");
							System.out.println(title);
						}
						if (title.indexOf("\"") != -1) {
							title = title.replace("\"", "");
							System.out.println(title);
						}
						if (title.indexOf("\\:") != -1) {
							title = title.replace("\\:", "");
							System.out.println(title);
						}
						if (title.indexOf("/") != -1) {
							title = title.replace("/", "");
							System.out.println(title);
						}
						if (title.indexOf("<") != -1) {
							title = title.replace("<", "");
							System.out.println(title);
						}
						if (title.indexOf(">") != -1) {
							title = title.replace(">", "");
							System.out.println(title);
						}
						if (title.indexOf("*") != -1) {
							title = title.replace("*", "");
							System.out.println(title);
						}
						if (title.indexOf("|") != -1) {
							title = title.replace("|", "");
							System.out.println(title);
						}
						if (title.indexOf("'") != -1) {
							title = title.replace("'", "");
							System.out.println(title);
						}
						String detailHref = getEndVideoURL(href);
						String source = "δȷ��";
						if (detailHref.indexOf("youku") != -1) {
							source = "�ſ�";
						} else if (detailHref.indexOf("56.com") != -1) {
							source = "56";
						} else if (detailHref.indexOf("letv.com") != -1) {
							source = "LETV";
						} else if (detailHref.indexOf("sina") != -1) {
							source = "����";
						} else if (detailHref.indexOf("ku6") != -1) {
							source = "ku6";
						} else if (detailHref.indexOf("tudou") != -1) {
							source = "����";
						} else if (detailHref.indexOf("souhu") != -1) {
							source = "�Ѻ�";
						} else if (detailHref.indexOf("cntv") != -1) {
							source = "CNTV";
						} else if (detailHref.indexOf("pptv") != -1) {
							source = "PPTV";
						}  else if (detailHref.indexOf("qq") != -1) {
							source = "��Ѷ";
						}else {

						}

						if (source.equals("��Ѷ")||source.equals("����")) {
							String sql1 = "select count(*) AS count from lb_store where NAME='"
									+ title + "'";
							Integer c = Integer
									.parseInt(((Map) baseDao.findBy(sql1, null)
											.get(0)).get("count").toString());
							if (c == 0) {

								String sql = "INSERT INTO lb_store(name,video_href,img,source_img,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";
								String video_href = "";
								try {
									if(source.equals("��Ѷ")){
										if(detailHref.indexOf("vid=")!=-1){
											video_href = analyze
													.analyzeFromTencent2(detailHref);
										}else{
											video_href = analyze
													.analyzeFromTencent(detailHref);
										}
									}else if(source.equals("����")){
										video_href=analyze.analyzeFromTuDou(detailHref);
									}
									if(image.indexOf("?")!=-1){
										int in=image.indexOf("?");
										image=image.substring(0,in);
									}
									Object[] params = { title, video_href, image,
											image, System.currentTimeMillis(),
											detailHref, "01", "0102", 2, source };
									if(count<=6){
										baseDao.insertBy(sql, params);
										count=count+1;
									}else{
										return;
									}
									
									System.out.println(title + "---" + "---"
											+ image + "---" + source + "---"
											+ detailHref);
								} catch (Exception e1) {
									System.out.println("Ʒ�ƽ�����Ƶ�������Ի�����Ƶ����ģ��"+detailHref);
									e1.printStackTrace();
								}

							} else {
								System.out.println("������Ƶû�и��£��������Ի��˱���ģ��");
								//break;
							}
						} else {

						}

					}

				}

			 

		} catch (IOException e) {
			System.out.println("�������Ի�����Ƶ����ģ��");
			e.printStackTrace();
		}

	}
	public static String getEndVideoURL(String startURL) {
		String href = "";
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();

			Element element = doc.select("div#play_box").select("a[href]")
					.first();
			if (element == null) {
				element = doc.select("div#flashcontent")
						.select("object#videoobj").select("param").first();
				href = element.attr("value");
			} else {
				href = element.attr("abs:href");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return href;
	}

}
