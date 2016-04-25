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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.lb.admin.service.UserService;
import com.lb.dao.BaseDao;
import com.lb.service.BattleService;
import com.lb.service.ServiceUtil;
import com.lb.service.impl.BattleServiceImpl;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.CreateDefinedPwdUtil;
import com.lb.utils.IoUtil;
import com.lb.utils.JpushUtil;
import com.lb.utils.MapUtil;
import com.lb.utils.PasswordUtil;
import com.lb.utils.TimeUtil;
 
@Component
public class GetUserByHuPu  {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	

//	@Autowired
//	ServiceUtil serviceUtil=null;
	
//	@Autowired
//	BattleService battleService=null; 
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}
	
//	@Autowired
//	BattleService battleService=null;
	
	private static List<JSONObject> list=new ArrayList<JSONObject>();
	
	

	
	public static void main(String[] args) throws InterruptedException {
//		//http://search.sina.com.cn/?q=&range=all&c=news&sort=time&SL2=nbavideo&PID=&col=&source=&from=&country=&size=&time=&a=&page=
//		String startURL = "http://list.letv.com/listn/c4_t30232_vt-1_md_o1_d2_p.html";
//		// get("http://search.sina.com.cn/?q=&range=title&c=news&sort=time&SL2=nbavideo&PID=");
//		//getLetvVideo2(startURL);
//		//getUserInfo("http://bbs.hupu.com/bxj");
////		addTeam("/Users/Allen/team/1.txt"); 
//		
//		//getTeamName("http://www.cu-league.com/team/teamWebList.html");
//		
//	GetUserByHuPu getUserByHuPu=new GetUserByHuPu();
//	getUserByHuPu.getTeamUser("http://www.cu-league.com/teamDate/queryTeamRoster.html?schoolId=61&teamId=669");
	}

	public  void getTeamName(String startURL){
		
		List<String> gradeList=new ArrayList<String>();
		gradeList.add("体育系");
		gradeList.add("经管系");
		gradeList.add("数学系");
		gradeList.add("中文系");
		gradeList.add("历史系");
		gradeList.add("哲学系");
		gradeList.add("计算机系");
		
		JSONArray userList=new JSONArray();
		String areaDetail="";
		Document doc = null;
			try {
				doc = Jsoup.connect(startURL).timeout(30000).get();
			} catch (IOException e) {
				System.out.println("发生异常,再次请求.................");
				 
			}
			Elements dls = doc.select("div.tm-block");
			dls = dls.select("div.tm-campusname");
			
			//遍历学校列表
			for (Element dl : dls) {
				System.out.println(dl);
				 userList=new JSONArray();
				int schoolInitCount=1;
				String schoolName = dl.select("div").last().ownText();
				areaDetail=schoolName;
				
				//初始化球队ID
				Integer schoolId=0;
				//添加球队
				
				if(schoolId==0){
					int tag=0;
					Elements lis = dl.select("li");
					//第一次遍历学校球队列表
					for(Element li:lis){
						if(tag==0){
							String sId=li.select("a").attr("name");
							String tId=li.select("a").attr("id");
							String teamDetailHref="http://www.cu-league.com/teamDate/queryTeamRoster.html?schoolId="+sId+"&teamId="+tId;
							GetUserByHuPu getUserByHuPu=new GetUserByHuPu();
							userList=getUserByHuPu.getTeamUser(teamDetailHref);
							System.out.println(userList.size());
						}
						tag++;
						
						
					 
					}
					 
				}
				if(userList!=null&&userList.size()>0){
					JSONObject user=null;
					if(schoolId==0){
						Elements lis = dl.select("li");
						//遍历学校【球队】列表
						for(Element li:lis){
							int j=0;
							if(schoolInitCount==1){
								user=userList.getJSONObject(0);//取第一个用户
								schoolId=addSchool(3,user.getInt("userId"), schoolName, areaDetail);
								//添加学校成功后添加球队
								if(schoolId>0){
									String teamName =li.text();
									System.out.println(teamName);
									
									double n=Math.random()*7;
									Integer nu=(int) n;
									
									user=userList.getJSONObject(j);
									Integer userId=user.getInt("userId");
									
									
									Integer teamId=addSchoolTeam(gradeList.get(nu), "false", teamName, user.getString("userImg"), schoolId,userId);
									updateHomeCourt(schoolId, userId);
									for(int i=0;i<userList.size();i++){
										JSONObject u=userList.getJSONObject(i);
										Integer uid=u.getInt("userId");
										addUserToTeam(uid, teamId);
									}
									 
									
									j++;
								}
							}else{
								String sId=li.select("a").attr("name");
								String tId=li.select("a").attr("id");
								String teamDetailHref="http://www.cu-league.com/teamDate/queryTeamRoster.html?schoolId="+sId+"&teamId="+tId;
								GetUserByHuPu getUserByHuPu=new GetUserByHuPu();
								userList=getUserByHuPu.getTeamUser(teamDetailHref);
								
								if(userList!=null&&userList.size()>0){
									String teamName =li.text();
									
									
									double n=Math.random()*7;
									Integer nu=(int) n;
									
									user=userList.getJSONObject(0);
									Integer userId=user.getInt("userId");
									
									
									Integer teamId=addSchoolTeam(gradeList.get(nu), "false", teamName, user.getString("userImg"), schoolId,userId);
									for(int i=0;i<userList.size();i++){
										JSONObject u=userList.getJSONObject(i);
										Integer uid=u.getInt("userId");
										if(i+1==userList.size()){
											System.out.println(userList.size());
											System.out.println(teamName);
											addUserToTeam(uid, teamId);
										}else{
											addUserToTeam(uid, teamId);
										}
										
									}
								}else{
									System.out.println("bug");
								}
								 
							}
							schoolInitCount++;
							
							 
								
							 
						}
						 
							

						 
					}
				} else{
					System.out.println("bug");
				}
				
				
				
	 
			}
	 
}
	
	//获取球队用户
	public  JSONArray getTeamUser(String startURL){
		List<Integer> ageList=new ArrayList<Integer>();
		ageList.add(19);
		ageList.add(20);
		ageList.add(21);
		ageList.add(22);
		ageList.add(23);
		ageList.add(24);
		ageList.add(25);
		
		
		List<Integer> heightList=new ArrayList<Integer>();
		heightList.add(170);
		heightList.add(172);
		heightList.add(173);
		heightList.add(174);
		heightList.add(175);
		heightList.add(176);
		heightList.add(177);
		
		
		List<Integer> weightList=new ArrayList<Integer>();
		weightList.add(70);
		weightList.add(71);
		weightList.add(72);
		weightList.add(73);
		weightList.add(74);
		weightList.add(75);
		weightList.add(76);
		
		
		JSONArray userList=new JSONArray();
		
		String areaDetail="";
		Document doc = null;
			try {
				doc = Jsoup.connect(startURL).timeout(30000).get();
			} catch (IOException e) {
 
			}
			Elements dls = doc.select("div.tc-table-block").select("tbody").select("tr");
			
			//判断球队是否有球员
			if(dls!=null&&dls.size()>0){
				String randsql="SELECT * FROM lb_user where province!='' and  user_type>3  ORDER BY id DESC LIMIT 1";
				JSONObject json=baseDao.findJsonObject(randsql, null);
				
				 
				Integer userType=14;    
				int startCount=3068;
				if(json!=null&&json.size()>0){
					startCount=json.getInt("phone")+1;
					userType=json.getInt("user_type")+1;
				}
				
//				System.out.println(dls);
				for (Element dl : dls) {
					startCount=startCount+1;
				   
					String pwd=PasswordUtil.encryp("jiayou");
					
					double n=Math.random()*7;
					Integer nu=(int) n;
					
					String userName=dl.select("a").first().ownText();
					String userImg=dl.select("img").first().attr("src");
					if(userImg.equals("http://images.cu-league.com/image/2/3w/7b/Z5-ori.jpg")){
						userImg="http://img5.imgtn.bdimg.com/it/u=3847116183,767642706&fm=21&gp=0.jpg";
					}
					Integer count=addUser(userName, userImg, userType, pwd, startCount, heightList.get(nu), weightList.get(nu));
					
					JSONObject user=new JSONObject();
					user.put("userId", count);
					user.put("userImg", userImg);
					if(count>0){
						userList.add(user);
					}else{
						
					} 
					}
			}else{
				System.out.println("球队没有录入用户信息");
			}
				
	 
			return userList;
	 
}
	
	
	




















 

	public static Integer getUserInfo(String startURL){
		JSONArray arr=new JSONArray();
		List<Integer> ageList=new ArrayList<Integer>();
		ageList.add(19);
		ageList.add(20);
		ageList.add(21);
		ageList.add(22);
		ageList.add(23);
		ageList.add(24);
		ageList.add(25);
		
		
		List<Integer> heightList=new ArrayList<Integer>();
		heightList.add(170);
		heightList.add(172);
		heightList.add(173);
		heightList.add(174);
		heightList.add(175);
		heightList.add(176);
		heightList.add(177);
		
		 
		AnalyzeVideoUtil getSinaMp4Util=new AnalyzeVideoUtil();
		int initcount=0;
		
		String randsql="SELECT * FROM lb_user where province!='' and  user_type>3  ORDER BY id DESC LIMIT 1";
		JSONObject json=baseDao.findJsonObject(randsql, null);
		
		 
		Integer userType=14;    
		int startCount=3068;
		if(json!=null&&json.size()>0){
			startCount=json.getInt("phone")+1;
			userType=json.getInt("user_type")+1;
		}
		int maxCount=startCount+100;
		initcount=startCount;
		int f = 1;
		String savePath = "E:\\personal\\新浪视频\\" + f+"\\";
		//起始时间
		long begin_time=System.currentTimeMillis();
	 
			Document doc = null;
				try {
					doc = Jsoup.connect(startURL).timeout(30000).get();
				} catch (IOException e) {
					System.out.println("发生异常,再次请求.................");
					 
					
					 
				}
				Elements dls = doc.select("td.p_author");
				
				for (Element dl : dls) {
					String source_href = dl.select("a[href]").first().attr("href");
					String img="";
					try {
						doc=Jsoup.connect(source_href).timeout(30000).get();
						img=doc.select("img#j_head").first().attr("abs:src");
						if(img.indexOf("default_big")!=-1){
							img="";
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String name = dl.select("a").first().text().replace("虎扑", "");
					
					
					Integer sum=0;
					String sql1 = "select count(*) AS count from lb_user where user_name='"
							+ name + "'";
					sum = Integer
							.parseInt(((Map) baseDao.findBy(sql1, null)
									.get(0)).get("count").toString());
					
					if(sum==0){
						 sql1 = "select count(*) AS count from lb_false_user where user_name='"
								+ name + "'";
						sum = Integer
								.parseInt(((Map) baseDao.findBy(sql1, null)
										.get(0)).get("count").toString());
						if(sum==0){
							String pwd=PasswordUtil.encryp("jiayou");
							
							String sql0="SELECT * FROM lb_user where province!='' ORDER BY RAND() LIMIT 1 ";
							JSONObject user=baseDao.findJsonObject(sql0, null);
							String province=user.getString("province");
							String city=user.getString("city");
							String area=user.getString("area");
							Integer weight=user.getInt("weight");
							
							String sql="INSERT INTO lb_user(user_name,user_img,user_type,password,phone,sex,height,weight,province,city,area) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
							Integer sex=1;
							if(startCount%2==0){
								sex=2;
							}
							
							double n=Math.random()*7;
							Integer nu=(int) n;
							
							Object [] params={name,img,userType,pwd,startCount,sex,heightList.get(nu),weight,province,city,area};
							if(startCount<maxCount){
								sum=baseDao.insertBy(sql, params);
								
								
								Integer rsum=(int) (Math.random()*5+1);
								for(int j=1;j<rsum;j++){
									//关注用户
									String sql3="INSERT INTO lb_user_focus(user_id,focus_user_id,add_time) VALUES(?,?,?)";
									
									String sql5="SELECT user_id FROM lb_dynamic  ORDER BY RAND() LIMIT 1 ";
									JSONObject dynamic=baseDao.findJsonObject(sql5, null);
									
									Integer uid=dynamic.getInt("user_id");
									 
									Object [] params3={sum,uid,System.currentTimeMillis()};
									Integer count3= baseDao.insertBy(sql3, params3);
									if(count3>0){
										JpushUtil jpusht=new JpushUtil();
										JpushUtil.pushMessage(CreateDefinedPwdUtil.encryp(uid), 2);
									}
								}
								
								
								
							}
							if(sum>0){
								System.out.println(source_href+name+img);
								startCount++;
							}
						}
					}
					
				}
			 
			 
		 
		//结束时间
		long end_time=System.currentTimeMillis();
		System.out.println("本次从新浪视频库下载"+(startCount-initcount)+"个用户");
		return startCount;

	}
	
	public static void addTeam(String filePath){
		List<String> strs=IoUtil.readTxtFileAsList(filePath);
		String userImg="http://7tsz1e.com1.z0.glb.clouddn.com/1437530677316.jpg";
		
		int teamId=14;
		String teamName="结球帮";
		for(int i=0;i<strs.size();i++){
			String str = strs.get(i);
			 
				int index = str.indexOf("\t");
				String name = str.substring(0, index).replace("\t", "");
				String phone = str.substring(index, str.length()).replace("\t", "");
				System.out.println(name);
			 
				
				Integer sum=0;
				//姓名和电话都不重复
				String sql1 = "select count(*) AS count from lb_user where user_name='"
						+ name + "' OR phone='"+phone+"'";
				sum = Integer
						.parseInt(((Map) baseDao.findBy(sql1, null)
								.get(0)).get("count").toString());
				
				if(sum==0){
					String pwd=PasswordUtil.encryp(phone);
					String sql="INSERT INTO lb_user(user_name,user_img,password,phone,teamId,create_time) VALUES(?,?,?,?,?,?)";
					Object [] params={name,userImg,pwd,phone,teamId,System.currentTimeMillis()};
					baseDao.insertBy(sql, params);
				}else{
					String sql="UPDATE lb_user SET team=?,teamId=? WHERE phone=?";
					Object [] params={teamName,teamId,phone};
					baseDao.updateBy(sql, params);
					System.out.println("出错++"+name+phone);
				}
			
		}
	}
	
	
	public void getLetvVideo2(String startURL){
			Document doc = null;
				try {
					doc = Jsoup.connect(startURL).timeout(30000).get();
				} catch (IOException e) {
					System.out.println("发生异常,再次请求.................");
					 
				}
				Elements dls = doc.select("dt.hd_pic");
				
				for (Element dl : dls) {
					String name = dl.select("img").first().attr("alt");
					Integer sum=0;
					String sql1 = "select count(*) AS count from lb_store where NAME='"
							+ name + "'";
					sum = Integer
							.parseInt(((Map) baseDao.findBy(sql1, null)
									.get(0)).get("count").toString());
					if(sum==0){
						String source_href = dl.select("a[href]").first().attr("href");
						
						String source_img = dl.select("img").first().attr("src");
							System.out.println(name);
						 AnalyzeVideoUtil analyze=new AnalyzeVideoUtil();
							String one_category = "01";
							String two_category = "0102";
							String sql = "INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,one_category,two_category,source,resourceType) values(?,?,?,?,?,?,?,?,?,?)";
							String mp4_href=analyze.analyzeFromLetv(source_href);
							Object[] params = { name, mp4_href, source_img,
									System.currentTimeMillis(), source_img, source_href,
									one_category, two_category, "乐视",2}; 
							baseDao.insertBy(sql, params);
						 
					}
					
		 
				}
		 
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



	
	
	
	
	
	
	
public Integer addSchool(Integer courtType,Integer userId, String schoolName, String areaDetail) {
	Integer schoolId= 0;
		String IF_SQL="SELECT COUNT(*) AS count FROM lb_basketball_court WHERE name=?";
		Object [] IF_PARAMS={schoolName};
		JSONObject json2=baseDao.findJsonObject(IF_SQL,IF_PARAMS);
		Integer ifHave=json2.getInt("count");
		Integer count=0;
		if(ifHave>0){
			
		}else{
			String sql="INSERT INTO lb_basketball_court(remark,status,areaDetail,user_id,name,lng,lat,addTime,court_type) VALUES(?,?,?,?,?,?,?,?,?)";
			JSONObject json=MapUtil.getLngAndLat(areaDetail);
			double lng=0;
		    double lat=0;
		    if(json!=null&&json.size()>0){
		    	lng=json.getDouble("lng");
		    	lat=json.getDouble("lat");
		    }
		    Object [] params={"false",1,areaDetail,userId,schoolName,lng,lat,System.currentTimeMillis(),courtType};
			 schoolId= baseDao.insertBy(sql, params);
			count=updateHomeCourt(schoolId, userId);//更新主场
		}
		return schoolId;
	}


	public Integer updateHomeCourt(Integer schoolId, Integer userId) {
		String sql="UPDATE lb_user SET home_court=? WHERE id=?";
		Object [] params={schoolId,userId};
		return baseDao.updateBy(sql, params);
	}
	
	
	public Integer addUser(String userName,String userImg,Integer userType,String password,Integer phone,Integer height,Integer weight){
		String sql="INSERT INTO lb_user(user_name,user_img,user_type,password,phone,height,weight) VALUES(?,?,?,?,?,?,?)";
		Object [] params={userName,userImg,userType,password,phone,height,weight};
		Integer count=0;
		String sql1 = "select count(*) AS count from lb_user where user_name='"
					+ userName + "'";
		JSONObject json=baseDao.findJsonObject(sql1, null);
		Integer sum=json.getInt("count");
//			Integer sum = Integer
//					.parseInt(((Map) baseDao.findBy(sql1, null)
//							.get(0)).get("count").toString());
			
			if(sum>0){
//				Object [] params2={userName+"111",userImg,userType,password,phone,height,weight};
//				count= baseDao.insertBy(sql, params2);
			}else{
				 count=baseDao.insertBy(sql, params);
			}
			
		return count;
		
	}
	
	
	public Integer addSchoolTeam(String grade,String remark,String teamName, String teamImg,
			Integer school_id,Integer userId) {
		String IF_SQL="SELECT COUNT(*) AS count FROM lb_team WHERE teamName=?";
		Object [] IF_PARAMS={teamName};
		JSONObject json=baseDao.findJsonObject(IF_SQL,IF_PARAMS);
		Integer ifHave=json.getInt("count");
		//Integer ifHave=serviceUtil.getCountBySqlAndParams(IF_SQL, IF_PARAMS);
		Integer count=0;
		if(ifHave>0){
			
		}else{
			String sql="INSERT INTO lb_team(class_name,grade,remark,teamName,teamImg,school_id,add_time,user_id,team_type) VALUES(?,?,?,?,?,?,?,?,?)";
			Object [] params={"",grade,remark,teamName,teamImg,school_id,System.currentTimeMillis(),userId,2};
			count=baseDao.insertBy(sql, params);
		}
	 
		return count;
	}

	
	public Integer addUserToTeam(Integer userId, Integer teamId) {
		String COUNT_SQL="SELECT count(*) AS count FROM lb_user_team WHERE user_id=?";
		Object [] COUNT_PARAMS={userId};
		JSONObject json=baseDao.findJsonObject(COUNT_SQL,COUNT_PARAMS);
		Integer ifHave=json.getInt("count");
		
		String sql="INSERT INTO lb_user_team(user_id,team_id,add_time) VALUE(?,?,?)";
		Object [] params={userId,teamId,System.currentTimeMillis()};
		Integer count=0;
		if(ifHave>0){
			count=-1;
		}else{
			count=baseDao.insertBy(sql, params);
		}
		return count;
	}
}
