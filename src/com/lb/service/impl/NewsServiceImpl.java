package com.lb.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
 
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
import com.lb.dao.BaseDao;
import com.lb.service.NewsService;
 
import com.lb.utils.PageBean;
 
import com.lb.utils.TimeUtil;
import com.lucene.crawl.GetBasketballTactics;
import com.lucene.crawl.GetNBANewsBy;
 
 
/**
 * 
 * @author Allen
 * @date 2014-11-9
 * @fuction  
 *
 */
@Service("newsService")
@Component
public class NewsServiceImpl implements NewsService {

	 

	BaseDao baseDao = null;

	

	 
	//@Scheduled(cron="0 0/1 23,0 * * ?")  
	public void addNews() {
		System.out.println("begin add sina news........................");
	JSONArray jsonArray=GetNBANewsBy.getNewsBySina("http://sports.sina.com.cn/nba/");
	 for(int i=0;i<jsonArray.size();i++){
			JSONObject json=(JSONObject) jsonArray.get(i);
			String name=json.get("news_name").toString();
			String img=json.get("img_href").toString();
			String content=json.get("news_content").toString().replace("新浪体育讯", " ").trim();
			String video=json.get("video_href").toString();
			String newsId=json.get("newsId").toString();
			String sql="INSERT into lb_store(NAME,content,img,source_img,addTime,one_category,two_category,resourceType,source) VALUES(?,?,?,?,?,?,?,?,?)";
			long current_time=System.currentTimeMillis();
			Object []params={name,content,img,img,current_time,"01","0101",1,"新浪"};
			
			String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
			Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
			if(c==0){
				//System.out.println(name);
				if(!img.equals("")&&img.length()>0){
					Integer count=baseDao.insertBy(sql, params);
				}
			}
			else{
//				Logger logger = Logger.getLogger(Log.class);
//				Logger log = Logger.getLogger("myTest2");  
//				log.info("--------------------------------------------------------------------------------------------\r\n");  
//		    	log.info(""+name+"\r\n");  
			}
			//System.out.println(name+img+content+video+newsId);
		 }
	}
 
 
	
	
	
	@Override
	//@Scheduled(cron="0 58 20 * * ?")  
	public void addTactics() {
	System.out.println("开始录入【篮球战术】........................");
	GetBasketballTactics.getAllTactics("http://www.172lanqiu.com/tuwenjiaoxue/list_8_");
	}
	
	
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}



	@Override
	public PageBean getAllNewsByConditions(Integer pageNo, Integer pageSize) {
		PageBean pageBean=new PageBean();
		pageBean.setPageNo(pageNo);
		Integer count=getAllNewsCountByConditions();
		pageBean.setRowCount(count);
		String sql="select * from nba_news  ORDER BY addTime desc limit ?,?";
		Object []params={(pageNo-1)*10,pageSize};
		//JSONArray jsonArray=baseDao.find(sql, params);
		List<JSONObject> list_json=baseDao.findListJSONObject(sql, params);
		pageBean.setData(list_json);
		return pageBean;
	}
	@Override
	public JSONObject getSpecialById(Integer specialId) {
		JSONObject json=null;
		String sql="SELECT * from nba_special where id=?";
	 	Object[] params={specialId};
	 	JSONArray jsonArray=baseDao.find(sql, params);
	 	if(jsonArray!=null&&jsonArray.size()>0){
	 		json=(JSONObject) jsonArray.get(0);
	 	}
		return json;
	}



	@Override
	public Integer updateSpecialById(Integer specialId,String specialName,String content) {
		String sql="update nba_special set name=?,content=?,updateTime=? where id=?";
		long time=TimeUtil.getCurrentTime();
	 	Object[] params={specialName,content,time,specialId};
		return baseDao.updateBy(sql, params);
	}


 



	@Override
	public Integer getAllNewsCountByConditions() {
		Integer count=0;
		String sql="select count(*) as count from nba_news";
		JSONArray arr=baseDao.find(sql, null);
		if(arr.size()>0){
			JSONObject json=(JSONObject)arr.get(0);
			count=Integer.parseInt(json.get("count").toString());
		}
		return count;
	}



	@Override
	public PageBean getAllSpecialByConditions(Integer pageNo, Integer pageSize) {
		PageBean pageBean=new PageBean();
		pageBean.setPageNo(pageNo);
		String sql_count="select count(*) as count from nba_special";
		Integer count=getCountBySql(sql_count);
		pageBean.setRowCount(count);
		String sql="select * from nba_special  ORDER BY addTime desc limit ?,?";
		Object []params={(pageNo-1)*10,pageSize};
		JSONArray jsonArray=baseDao.find(sql, params);
		pageBean.setData(jsonArray);
		return pageBean;
		 
	}



	@Override
	public Integer getCountBySql(String sql) {
		Integer count=0;
		JSONArray arr=baseDao.find(sql, null);
		if(arr.size()>0){
			JSONObject json=(JSONObject)arr.get(0);
			count=Integer.parseInt(json.get("count").toString());
		}
		return count;
		 
	}

 

	@Override
	public Integer updateNews(Integer newsId, String newsTitle,
			String newsContent) {
		String sql="UPDATE nba_news set name=?,content=? where id=?";
		Object[] params={newsTitle,newsContent,newsId};
		return baseDao.updateBy(sql, params);
	}

	@Override
	public List<JSONObject> getAllHistorySpecial() {
		String sql="select id,href from nba_history_special";
		return baseDao.findListJSONObject(sql, null);
	}

	@Override
	public JSONObject getNewsDetail(Integer newsId) {
		String sql="SELECT id,name,content from nba_news where id=?";
		Object[] params={newsId};
		return baseDao.findJsonObject(sql, params);
	}
 
 
}
