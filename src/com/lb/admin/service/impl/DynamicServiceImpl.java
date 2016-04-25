package com.lb.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sun.print.resources.serviceui;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.lb.admin.service.DynamicService;
import com.lb.admin.service.UserService;
import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.CreateDefinedPwdUtil;
import com.lb.utils.JpushUtil;
import com.lb.utils.PageBean;
import com.lb.utils.StrUtil;
import com.lb.utils.TimeUtil;
import com.lb.utils.UploadPhotoToQiNiu;
@Component
public class DynamicServiceImpl implements DynamicService {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Autowired
	ServiceUtil serviceUtil=null;
	
	@Autowired
	UserService userService=null;
	
	@Override
	public PageBean getDynamicListBy(String userName,Integer adId,String searchDynamicContent,Integer pageNo, Integer pageSize) {
		List params=new ArrayList();
		
		
		PageBean pageBean=new PageBean();
		pageBean.setPageNo(pageNo);
		Integer dynamicCount=getDynamicListCountBy(searchDynamicContent);
		pageBean.setRowCount(dynamicCount);
		
		
		 
		StringBuffer str=new StringBuffer();
		str=str.append("SELECT * FROM lb_dynamic  dynamic INNER JOIN lb_user user ON  dynamic.user_id=user.id where 1=1 ");
		if(StrUtil.isNotEmpty(searchDynamicContent)){
			str.append(" AND dynamic.text_content like ?");
			params.add("%"+searchDynamicContent+"%");
		}
		if(StrUtil.isNotEmpty(String.valueOf(adId))){
			str.append(" AND dynamic.ad_id=?");
			params.add(adId);
		}
		if(StrUtil.isNotEmpty(String.valueOf(userName))){
			str.append(" AND user.user_name like ?");
			userName="%"+userName+"%";
			params.add(userName);
		}
		str.append(" ORDER BY dynamic.add_time DESC LIMIT ?,?");
		params.add((pageNo-1)*pageSize);
		params.add(pageSize);
		//Object [] params={(pageNo-1)*pageSize,pageSize};
		
		JSONArray arr=baseDao.find(str.toString(), params.toArray());
		JSONArray end_arr=new JSONArray();
		if(arr!=null&&arr.size()>0){
			for(int i=0;i<arr.size();i++){
				JSONObject json=arr.getJSONObject(i);
				
				String img=json.getString("img");
				String sourceImg=img;
				json.put("sourceImg", sourceImg);
				img=img+"?imageView2/1/w/210/h/210";  //对图片进行压缩处理
				json.put("img", img);
				Integer dynamicId=json.getInt("dynamic_id");
				JSONArray leaveArr=getLeaveOfDynamicBy(dynamicId);
				json.put("leaveContent", leaveArr);
				end_arr.add(json);
			}
		}
		pageBean.setData(end_arr);
		return pageBean;
	}

	@Override
	public Integer getDynamicListCountBy(String searchDynamicContent) {
		List params=new ArrayList();
		StringBuffer str=new StringBuffer();
		str=str.append("SELECT COUNT(*) AS count FROM lb_dynamic  dynamic INNER JOIN lb_user user ON  dynamic.user_id=user.id  WHERE 1=1 ");
		if(StrUtil.isNotEmpty(searchDynamicContent)){
			str.append(" AND dynamic.text_content like ? ");
			params.add("%"+searchDynamicContent+"%");
		}
//		if(StrUtil.isNotEmpty(searchDynamicContent)){
//			str.append(" AND dynamic.text_content=? ");
//			params.add(searchDynamicContent);
//		}
		JSONObject json=baseDao.findJsonObject(str.toString(), params.toArray());
		Integer count=json.getInt("count");
		return count;
	}
	
	
	@Override
	public Integer deteteDynamicBy(Integer dynamicId) {
		Integer count=0;
// try{
//			 
		 String sql="DELETE FROM lb_dynamic WHERE dynamic_id=?";
		Object [] params={dynamicId};
		count=baseDao.updateBy(sql, params);
		sql="DELETE FROM lb_dynamic_agree WHERE dynamic_id=?";
		baseDao.updateBy(sql, params);
		sql="DELETE FROM lb_dynamic_leave WHERE dynamic_id=?";
		 baseDao.updateBy(sql, params);
	 
		
// }			catch (Exception e) {
//			count=0;
//		}
		 return count;
				
	}
	
	
	public Integer addDynamic(Integer circleId,String cardName,Integer dynamicType,Integer adId,int userId, String textContent, JSONArray imgArr, String tag_name,double x_position,double y_position, String tag_name2,double x_position2,double y_position2, String tag_name3,double x_position3,double y_position3) {
		String sql="INSERT INTO lb_dynamic(circle_id,card_name,card_num,dynamic_type,ad_id,user_id,text_content,add_time) VALUES(?,?,?,?,?,?,?,?)";
		
		Integer cardNum=0;
		//如果当前为打卡，获取当前打卡名称的总共打卡次数
		if(dynamicType==3){
			cardNum=getUserCardNumBy(userId, cardName);
			Integer ifAddCardToday=ifAddCardToday(userId, cardName);
			//如果今天没有打卡，则打卡数量+1，否则不变（1天可以打卡很多次，但打卡数量只有在当天第一次打卡时才会变化，后面则不更新数量）
			if(ifAddCardToday==0){
				cardNum++;
			}
			
		}
		
		//long time=TimeUtil.dateTo("2015-08-25");
		 long time=System.currentTimeMillis();
		Object [] params={circleId,cardName,cardNum,dynamicType,adId,userId,textContent,time};
		Integer dynamicId=baseDao.insertBy(sql, params);
		Integer result=0;
		if(tag_name!=null&&!tag_name.equals("")){
			sql="INSERT INTO lb_img_tag(dynamic_id,tag_name,x_position,y_position) VALUES(?,?,?,?)";
			Object [] p={dynamicId,tag_name,x_position,y_position};
			result=baseDao.insertBy(sql, p);
		}
		if(tag_name2!=null&&!tag_name2.equals("")){
			sql="INSERT INTO lb_img_tag(dynamic_id,tag_name,x_position,y_position) VALUES(?,?,?,?)";
			Object [] p={dynamicId,tag_name2,x_position2,y_position2};
			result=baseDao.insertBy(sql, p);
		}
		if(tag_name3!=null&&!tag_name3.equals("")){
			sql="INSERT INTO lb_img_tag(dynamic_id,tag_name,x_position,y_position) VALUES(?,?,?,?)";
			Object [] p={dynamicId,tag_name3,x_position3,y_position3};
			result=baseDao.insertBy(sql, p);
		}
		Integer count=0;
		for(int i=0;i<imgArr.size();i++){
			String dImg=imgArr.getString(i);
			 
			if(i==0){
				count=updateDynamicImg(dynamicId, dImg);
				count=addDynamicImg(dynamicId, dImg);
			}else{
				dImg=imgArr.getString(i);
				
			
				count=addDynamicImg(dynamicId, dImg);
			}
			 
		}
		
		return dynamicId;
	}
	
	public  Integer addDynamicImg(Integer dynamicId, String dynamicImg) {
		String sql="INSERT INTO bh_dynamic_img(dynamic_id,dynamic_img) VALUES (?,?)";
		Object [] params={dynamicId,dynamicImg};
		Integer count=baseDao.insertBy(sql, params);
		return count;
	}

	public  Integer updateDynamicImg(Integer dynamicId, String dynamicImg) {
		String sql="UPDATE lb_dynamic set img=? WHERE dynamic_id=?";
		Object [] params={dynamicImg,dynamicId};
		Integer count=baseDao.updateBy(sql, params);
		return count;
	}
	
	@Override
	public Integer updateDynamic(Integer dynamicId, String content,String dynamicImg) {
		String sql="UPDATE lb_dynamic SET text_content=?,img=? WHERE dynamic_id=?";
		Object [] params={content,dynamicImg,dynamicId};
		return baseDao.updateBy(sql, params);
	}
	@Override
	public Integer addDynamicAgree(int userId, int replyUserId, int dynamicId) {
		String sql="INSERT INTO lb_dynamic_agree(dynamic_id,user_id,reply_user_id,add_time) VALUES(?,?,?,?)";
		Object [] params={dynamicId,userId,replyUserId,System.currentTimeMillis()};		
		Integer count= 0;
		String ifsql="SELECT COUNT(*) AS count from lb_dynamic_agree WHERE user_id=? AND dynamic_id=?";
		Object [] ifParams={userId,dynamicId};
		Integer sum=serviceUtil.getCountBy(ifsql, ifParams);  //判断是否重复点赞
		if(sum==0){
			count=baseDao.insertBy(sql, params);
		}
		if(count>0){
			JpushUtil jpusht=new JpushUtil();
			JpushUtil.pushMessage(CreateDefinedPwdUtil.encryp(replyUserId), 1);
		}
		return count;
	}
	
	
	
	public Integer addDynamicLeave(Integer userId, Integer replyLeaveId,
			Integer dynamicId,String leaveContent,Integer replyUserId) {
		String sql="INSERT INTO lb_dynamic_leave(user_id,reply_leave_id,dynamic_id,add_time,leave_content,reply_user_id) VALUES(?,?,?,?,?,?)";
		Object [] params={userId,replyLeaveId,dynamicId,System.currentTimeMillis(),leaveContent,replyUserId};
		Integer count= baseDao.insertBy(sql, params);
		if(count>0){
			JpushUtil jpusht=new JpushUtil();
			JpushUtil.pushMessage(CreateDefinedPwdUtil.encryp(replyUserId), 3);
		}
		return count;
	}
	
	@Override
	public Integer recommendDynamic(int dynamicId) {
		String sql="UPDATE lb_dynamic set ifRecommend=1 where dynamic_id=?";
		Object [] params={dynamicId};		
		Integer count=baseDao.updateBy(sql, params);
		return count;
	}
	public JSONObject getLeaveBasicInfoBy(Integer leaveId) {
		String sql="SELECT user_id,leave_content,add_time,leave_id FROM lb_dynamic_leave WHERE leave_id=?";
		Object [] params={leaveId};
		JSONObject json=baseDao.findJsonObject(sql, params);
		Integer user_id=json.getInt("user_id");
		JSONObject user=userService.getUserNameAndImgBy(user_id);
		
		
		json.put("userName", user.getString("userName"));
		json.put("userImg", user.getString("userImg"));
		 
		return json;
	}
	

	@Override
	public JSONArray getLeaveOfDynamicBy(Integer dynamicId) {
		String sql="SELECT leave_id,user_id,dynamic_id,leave_content,reply_leave_id,reply_user_id,add_time FROM lb_dynamic_leave WHERE dynamic_id=? ORDER BY add_time DESC";
		Object [] params={dynamicId};
		JSONArray endArr=new JSONArray();
		JSONArray arr=baseDao.find(sql, params);
		if(arr!=null&&arr.size()>0){
			for(int i=0;i<arr.size();i++){
				JSONObject json=arr.getJSONObject(i);  //获取留言用户
				Integer userId=json.getInt("user_id");
				json.put("userId", CreateDefinedPwdUtil.encryp(userId));
				JSONObject user=userService.getUserNameAndImgBy(userId); //获取用户昵称和头像
				json.put("userName", user.getString("userName"));
				json.put("dynamicId", json.getInt("dynamic_id"));
				json.put("userImg", user.getString("userImg"));  
				json.put("star", user.getString("star"));  
				
				json.put("replyUserId",CreateDefinedPwdUtil.encryp( json.getInt("reply_user_id")));
				Integer reply_leave_id=json.getInt("reply_leave_id");
				json.put("replyLeaveId", reply_leave_id);  
				 
				//判断当前用户的留言是直接回复动态还是回复其他用户
				if(reply_leave_id>0){
					JSONObject reply=getLeaveBasicInfoBy(reply_leave_id);
//					json.put("replyUserId", CreateDefinedPwdUtil.encryp(reply.getInt("user_id")));
					json.put("replyUserName", reply.getString("userName")); 
					json.put("replyLeaveContent", reply.getString("leave_content"));
					//json.put("replyLeave", reply);
					 
				}
				endArr.add(json);
			}
		}
		return endArr;
	}

	public Integer getUserCardNumBy(Integer userId, String cardName) {
		String sql="SELECT COUNT(*) AS count  FROM lb_dynamic WHERE user_id=? AND card_name=?";
		Object [] params={userId,cardName};
		JSONObject json=baseDao.findJsonObject(sql, params);
		Integer count=json.getInt("count");
		return count;
	}
	
	public Integer ifAddCardToday(Integer userId, String cardName) {
		long time=TimeUtil.getTodayDecimal();
		String sql="SELECT COUNT(*) AS count  FROM lb_dynamic WHERE user_id=? AND card_name=? AND add_time>?";
		Object [] params={userId,cardName,time};
		JSONObject json=baseDao.findJsonObject(sql, params);
		Integer count=json.getInt("count");
		return count;
	}

	
}
