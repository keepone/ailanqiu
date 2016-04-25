package com.lucene.crawl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.jiutong.test.Log;
import com.lb.dao.BaseDao;
import com.lb.utils.IoUtil;
import com.lb.utils.StrUtil;
 
@Component
public class GetPlayerBy {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public static int playerCount=0;
	public static void main(String [] args){
		getTeam("http://g.hupu.com/nba/teams/");
	}
 
	
	/**
	 * �ӻ���¼��NBA��Ա
	 * @param startURL
	 * @return
	 */
	public static JSONArray getTeam(String startURL){
		JSONArray jsonArray=new JSONArray();
		boolean mark=true;
		Document doc = null;
		try {
			 
				 doc = Jsoup.connect(startURL).get();
					 Elements elements=doc.select("div.teamlist_box_r").select("a");
					  
					 for(Element element:elements){
						 Element e=element.select("a[href]").first();
						 String href=e.attr("abs:href");
						 String teamName=e.select("div.font").select("h2").text();
						 getTeamPlayer(href, teamName);
						 }
						 
					  
					 Elements page=doc.select("div.page").select("a.next");
					 String nextPage=page.attr("abs:href");
					 if(nextPage!=null&&!nextPage.trim().equals("")){
						 startURL=nextPage;
					 }else{
						 mark=false;
					 }
			 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}

	
	
	public static JSONArray getTeamPlayer(String startURL,String teamName){
		List list=new ArrayList();
		JSONArray jsonArray=new JSONArray();
		boolean mark=true;
		int count=0;
		 
		Document doc = null;
		try {
					doc = Jsoup.connect(startURL).get();
					 Elements elements=doc.select("div.a").select("div.x_list");
					 for(Element element:elements){
						 Element e=element.select("a[href]").first();
						 String href=e.attr("href");
						 String playerName=e.attr("title");
						 getPlayerDetail(href, playerName,0);
						 System.out.println(teamName);  
					 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	public static String getPlayerDetail(String startURL,String playerName,int team_id){
		String userImg="";
		String href="";
		String position="";
		int height=0;
		int weight=0;
		String birthday="";
		int salary=0;
		String contract="";
		/**
		 * <div class="font">
							 		<p>λ�ã�PG��9�ţ�</p>
							 		<p>��ߣ�1.88��/6��2</p>
							 		<p>���أ�84����/185��</p>
							 		<p>���գ�1982-05-17</p>
							 		<p>��ӣ�<a target="_blank" title="ʥ����������" style="color:red" href="/nba/teams/spurs">ʥ����������</a></p>							 		                                     <p>ѡ�㣺2001���1�ֵ�28˳λ</p>								 		<p>�����أ�����ʱ</p>
                                    <p>������н��1344����Ԫ</p>                                    <p>��ͬ��3��4500����
		 */
		Document doc = null;
		try {
					doc = Jsoup.connect(startURL).timeout(60000).get();
					 
						Element e=doc.select("div.clearfix").first();
						userImg=e.select("img").attr("src");
						Elements ps=e.select("p").select("p");
						int i=1;
						String text="";
						for(Element p:ps){
							if(i==1){
								position=p.text().replace("λ�ã�", "");
								position=StrUtil.getBeforeStrBySubString(position, "��");
							}else if(i==2){
								text=p.text().replace("��ߣ�", "");
								text=StrUtil.getBeforeStrBySubString(position, "��");
								height=Integer.parseInt(text.replace(".", ""));
							}else if(i==3){
								text=p.text().replace("���أ�", "");
								text=StrUtil.getBeforeStrBySubString(position, "��");
								weight=Integer.parseInt(text);
							}else if(i==4){
								text=p.text().replace("���գ�", "");
								birthday=text;
							}else if(i==5){
								//�������
							}else if(i==6){
								text=p.text().replace("������н��", "");
								text=StrUtil.getBeforeStrBySubString(position, "��");
								salary=Integer.parseInt(text);
							}else if(i==7){
								text=p.text().replace("��ͬ��", "");
								text=StrUtil.getBeforeStrBySubString(position, "��");
								contract=text;
							}
						}
						String sql="INSERT bh_player(name,img,position,height,weight,birthday,salary,contract,team_id) VALUES(?,?,?,?,?,?,?,?,?)";
						Object [] prams={playerName,userImg,position,height,weight,birthday,salary,contract,team_id};
					
						System.out.println(playerName);
						playerCount=playerCount++;
						System.out.println(playerCount);
				 
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		return href;
	}
	
	 
	
	/**
	 * ���������NBA�����˶�Ա
	 */
	// @Scheduled(cron="0 47 11,* * * ?")  
	public  static void addAllNBAPlayerFromSina() {
		System.out.println("begin add best player^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		String sql="INSERT  INTO bh_player(name, english_name, height, weight, school, into_nba, nba_age, team, into_reason, position, career_max, season_max, birthday, cloth_num, img, birth_place, age) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String startURL = "http://nba.sports.sina.com.cn/players.php?dpc=1";
		JSONArray jsonArray=null;
	 	jsonArray =GetNBAInfoUtil.getAllPlayer(startURL); 
	 	System.out.println(jsonArray.size());
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json=(JSONObject) jsonArray.get(i);
			long current_time=0;String name="";String english_name="";String height="";String weight="";String school="";String into_nba="";String nba_age="";String team="";String into_reason="";String position="";String career_max="";String season_max="";String birthday="";String cloth_num="";String img="";String birth_place="";String age="";
			for(int j=0;j<json.size();j++){
				if(j==0){
					String content=json.get("td"+j).toString();
					int index=content.indexOf(",");
					name=content.substring(0,index);
					english_name=content.substring(index+1,content.length());
				}else if(j==1){
					cloth_num=json.get("td"+j).toString().trim();
				}else if(j==2){
					position=json.get("td"+j).toString();
				}else if(j==3){
					team=json.get("td"+j).toString();
				}else if(j==4){
					img=json.get("td"+j).toString();
					if(img!=null&&!img.equals("")){
						current_time=System.currentTimeMillis();
						IoUtil.downloadImage(img, "", "D:/tomcat6/Tomcat 6.0/webapps/ROOT/player_img/"+current_time+".jpg");
						img=current_time+".jpg";
					}
					
				}else if(j==5){
					birthday=json.get("td"+j).toString();
				}else if(j==6){
					age=json.get("td"+j).toString();
				}else if(j==7){
					birth_place=json.get("td"+j).toString();
				}else if(j==8){
					school=json.get("td"+j).toString();
				}else if(j==9){
					height=json.get("td"+j).toString();
				}else if(j==10){
					weight=json.get("td"+j).toString();
				}else if(j==11){
					into_nba=json.get("td"+j).toString();
				}else if(j==12){
					nba_age=json.get("td"+j).toString();
				}else if(j==13){
					into_reason=json.get("td"+j).toString();
				}else if(j==14){
					season_max=json.get("td"+j).toString();
				}else if(j==15){
					career_max=json.get("td"+j).toString();
				} 
				
			}
			Object[] params={ name, english_name, height, weight, school, into_nba, nba_age, team, into_reason, position, career_max, season_max, birthday, cloth_num, img, birth_place, age};
			baseDao.insertBy(sql, params);
		}
	}
	
	
	 
}
