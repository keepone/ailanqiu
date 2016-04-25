package com.lucene.crawl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
 
import com.lb.crawl.utils.GetJsoupDocByProxyUtil;
import com.lb.dao.BaseDao;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.PrintUtil;
import com.lb.utils.UploadPhotoToQiNiu;
 
@Component
public class GetCBANewsBySina {
	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	public final static String phoneUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A5313e Safari/7534.48.3";
	
	
	private static BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public static void main(String[] args) {
		

	}
	
	
	public static void addWeiXin2(String startURL){
		int status=1;
		String str="";
		String cover="";
		String title="";
		int i=1;
		try {
			
//			Document doc=Jsoup.connect(startURL).userAgent(USERAGENT).timeout(10000).get();
			Document doc=null;
			JSONObject json1=GetProxyIpUtil.readOneProxyIP();
			String ip=json1.getString("ip");
			Integer port2=json1.getInt("port");
			GetJsoupDocByProxyUtil getJsoupDocByProxyUtil=new GetJsoupDocByProxyUtil();
				 doc=getJsoupDocByProxyUtil.getDoc(startURL, ip,port2, USERAGENT);
				 
			title=doc.select("h2.rich_media_title").first().text();
			Elements ps=doc.select("div.rich_media_content").select("p");
			for(Element p:ps){
				if(p.outerHtml().indexOf("�Ķ�ԭ��")==-1){
					if(p.outerHtml().indexOf("img")!=-1){
						String img=p.select("img").attr("data-src");
						img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(img)+"?imageView2/1/w/640/h/640"; //��ͼƬ����ѹ������;
						str=str+"<img src=\""+img+"\"/>";
						if(!p.text().trim().equals("")&&p.text().trim()!=null){
							String content=p.text();
//							content=content.replace("��", "").replace("��", "");
							str=str+content;
							 
						}
						i++;
					}else{
						String content=p.outerHtml();
						 
						str=str+content;
						 
					}
				}
			}
			
			String sql="INSERT INTO lb_store(name,img,source_img,content,addTime,source_href,one_category,resourceType,source) values(?,?,?,?,?,?,?,?,?)";
			Object[] params={title,cover,cover,str,System.currentTimeMillis(),"","06","1","������"};
			baseDao.insertBy(sql, params);
			 
			System.out.println(str);
		} catch (Exception e) {
			 status=0;
			e.printStackTrace();
		}
		PrintWriter out=PrintUtil.printWord();
		if(status==1){
			out.print("���سɹ�!�����أ�"+(i-1)+"��ͼƬ");
		}else{
			out.print("����ʧ��!");
		}
	}
	
	//¼�빫�ں����¡������á�
	public static void addWeiXin(String startURL,String fileType){
		int status=1;
		String str="";
		String cover="";
		String title="";
		String img="";
		String onecateGory="";
		String twocateGory="";
		String resourceType="";
		if(fileType.equals("people")){
			onecateGory="06";
			resourceType="1";
		}else{
			onecateGory="01";
			twocateGory="0101";
			resourceType="1";
		}
		int i=1;
		try {
			
//			Document doc=Jsoup.connect(startURL).userAgent(USERAGENT).timeout(10000).get();
			Document doc=null;
			JSONObject json1=GetProxyIpUtil.readOneProxyIP();
			String ip=json1.getString("ip");
			Integer port2=json1.getInt("port");
			GetJsoupDocByProxyUtil getJsoupDocByProxyUtil=new GetJsoupDocByProxyUtil();
				 doc=getJsoupDocByProxyUtil.getDoc(startURL, ip,port2, USERAGENT);
				 
			title=doc.select("h2.rich_media_title").first().text();
			Elements c=doc.select("div.rich_media_content");
			String content=c.text();
			
			Elements ps=c.select("img");
			for(Element p:ps){
				 
					 
						 img=p.attr("data-src");
						img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(img)+"?imageView2/1/w/640/h/640"; //��ͼƬ����ѹ������;
						str=str+"<img src=\""+img+"\"/>";
						i++;
					 
				 
			}
			str=content+str;
			
			String sql="INSERT INTO lb_store(name,img,source_img,content,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";
			Object[] params={title,cover,cover,str,System.currentTimeMillis(),"",onecateGory,twocateGory,resourceType,"������"};
			baseDao.insertBy(sql, params);
			 
			System.out.println(str);
		} catch (Exception e) {
			 status=0;
			e.printStackTrace();
		}
		PrintWriter out=PrintUtil.printWord();
		if(status==1){
			out.print("���سɹ�!�����أ�"+(i-1)+"��ͼƬ");
		}else{
			out.print("����ʧ��!");
		}
	}

	public static void getNewsList(String startURL) {
		int count=1;
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements lis = doc.select("div.d_list_txt").select("li");
			for (Element li : lis) {
				String name = li.select("a[href]").first().text();
				String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
				Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
				//ֻ�����ݿⲻ���ڸ�����¼ʱ����ȡ����
				if(c==0){
					//���˰�����ͼ�ġ���ͳ�ơ�����Ϣ
					if (name.indexOf("ͼ��")==-1&&name.indexOf("ͳ��")==-1&&name.indexOf("����")==-1) {
						String href = li.select("a[href]").attr("abs:href");
						
						//���˳����Ӱ�����form�� ��blog��"teamrank"�ĳ�����
						if(href.indexOf("forum")!=-1||href.indexOf("blog")!=-1||href.indexOf("teamrank")!=-1||href.indexOf("#")!=-1){
							System.out.println(name);
						}else{
							JSONObject json=getNewsDetail(href);
							String content=json.getString("content");
							content=content.replace("��������Ѷ", "").replace("<p>���ղ�!</p>", "")
									.replace("����ͨ��������ҳ���� ������ �鿴�����ղع������¡�", "")
									.replace("֪����", "").replace("<p></p>", "");
							
							content=content.replaceAll("\u00A0", " ");
							content=content.replaceAll("\u0020 ", "");
							content=content.replaceAll("\u3000", "").replaceAll("<p></p>", "").replaceAll("<p>", "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							//�ж�����Ƶ�������ţ����������������Ƶ���������ţ�
							String isvideo=json.getString("isvideo");
							
							if(isvideo.equals("no")){
								String two_category="0201"; //CBA����
								String videoHref=json.getString("videoHref");
								if(!videoHref.equals("")){
									two_category=two_category+" 0202";
								}
								String sql="INSERT INTO lb_store(name,content,video_href,img,source_img,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?,?)";
								String sourceImg=json.getString("img");  //������������ţ�����ܰ���ͼƬ����Ƶ��ʱ�������Ƿ����ͼƬ
								Object[] params={name,content,videoHref,sourceImg,sourceImg,System.currentTimeMillis(),href,"02",two_category,1,"����"};
								baseDao.insertBy(sql, params);
								System.out.println(name+"---"+content);
							}else{
								String sql="INSERT INTO lb_store(name,content,video_href,sourceVideoHref,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";
								String videoHref=json.getString("videoHref");
								String sourceVideoHref=json.getString("sourceVideoHref");
								Object[] params={name,content,videoHref,sourceVideoHref,System.currentTimeMillis(),href,"02","0202",2,"����"};
								baseDao.insertBy(sql, params);
								System.out.println(name+"---"+content);
							}
							
							count++;
						}
						
						
					}
				}else{
					System.out.println(name+"---�Ѿ�¼��");
					//break;
				}
				
				
		
			}
			System.out.println(count);

		} catch (IOException e) {
			System.out.println("cba�����б�ҳ����");
			e.printStackTrace();
		}

	}
	
	public static JSONObject getNewsDetail(String url) {
		String result="";
		JSONObject jsonObject=new JSONObject();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(6000).get();
			Element e = doc.select("div.blkContainerPblk").first();
			//�ж����������Ż�����Ƶ����
			if(e!=null){
				Elements ee=e.select("div.img_wrapper");
				String img="";
				String videoHref="";
				//��������������ͼƬ��ֻȡ��һ��
				if((ee.size()>0)){
					 img=ee.select("img").first().attr("src");
				}else{
					
					 //img=e.select("div.blk_hd_pic").select("img").first().attr("src");
				}
				
				//������Ű�����Ƶ
				if(doc.outerHtml().indexOf("url-data")!=-1){
					//System.out.println(doc.outerHtml());
					Element div=doc.select("div.a-p-s-item").first();
					videoHref=div.attr("url-data");
				}
				
				
				Elements elements=e.select("p");
				String content="";
				for(Element element:elements){
					content=content+"<p>"+element.ownText().trim()+"</p>";
				}
				jsonObject.put("videoHref", videoHref);
				jsonObject.put("content", content);
				jsonObject.put("img", img);
				jsonObject.put("isvideo", "no");
			}else{
				//˵��ֻ�ǵ�������Ƶ�������������������Ƶ
				 
				Elements elements=doc.select("em[task=oldinfor]");
				if(elements!=null&&elements.size()>0){
					elements=elements.first().select("p");
					String content="";
					for(Element element:elements){
						content=content+"<p>"+element.ownText().trim()+"</p>";
					}
					jsonObject.put("isvideo", "yes");
					jsonObject.put("content", content);
					AnalyzeVideoUtil analyze=new AnalyzeVideoUtil();
					try {
						String videoHref=analyze.analyze(url);
						jsonObject.put("videoHref", videoHref);
						jsonObject.put("sourceVideoHref", url);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{// ͼ����������
					Elements ps=doc.select("div.mwl-content").select("p");
					if(ps.outerHtml().indexOf("img")!=-1){
						String img=ps.select("img").first().attr("src");
						jsonObject.put("img", img);
					}
					String content="";
					for(Element p:ps){
						if(p.outerHtml().indexOf("img")==-1){
							content=content+p.outerHtml();
							jsonObject.put("content", content);
						}
					}
					jsonObject.put("isvideo", "no");
					jsonObject.put("videoHref", "");
				}
				
			}
			//System.out.println(img);
		} catch (IOException e) {
			System.out.println("cba��������ҳ����"+url);
			e.printStackTrace();
		}
		return jsonObject;
}
	
//	public static String getNewsDetail(String href){
//		try {
//			Document doc = Jsoup.connect(href).timeout(60000).get();
//			//������Ű�����Ƶ
//			if(doc.outerHtml().indexOf("url-data")!=-1){
//				//System.out.println(doc.outerHtml());
//				Element div=doc.select("div.a-p-s-item").first();
//				String videoHref=div.attr("url-data");
//				System.out.println(videoHref);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
//	}
}
