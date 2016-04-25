package com.lb.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.admin.service.ParkService;
import com.lb.admin.service.UserService;
import com.lb.dao.BaseDao;
import com.lb.utils.CreateDefinedPwdUtil;
import com.lb.utils.MapUtil;
@Component
public class ParkServiceImpl implements ParkService{
	private  BaseDao baseDao=null;
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Autowired
	UserService userService=null;
	
	@Override
	public Integer addPark(Integer userId, String parkName, String areaDetail,
			String parkImg) {
		//String sql="INSERT INTO lqr_park(user_id,park_name,lng,lat,park_img,add_time) VALUES(?,?,?,?,?,?)";
		JSONObject json=MapUtil.getLngAndLat(areaDetail);
		double lng=0;
	    double lat=0;
	    if(json!=null&&json.size()>0){
	    	lng=json.getDouble("lng");
	    	lat=json.getDouble("lat");
	    }
	    Object [] params={userId,parkName,lng,lat,parkImg,System.currentTimeMillis()};
		String sql="INSERT INTO lb_basketball_court(name,img,addTime,status) VALUES(?,?,?,?)";
	    Object [] params={parkName,parkImg,System.currentTimeMillis(),0};
		return baseDao.insertBy(sql, params);
	}
	@Override
	public JSONArray getTeamBy(Integer parkId, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONArray getParkBy(Integer pageNo,Integer pageSize,String lng,String lat,String city,String area) {
		// 0.00001=1m  0.01=1km
		city=city.replace("市", "");
		double constant=0.05;
		double lg=Double.valueOf(lng);
		double la=Double.valueOf(lat);
		
		double lng_min=lg-constant;
		double lng_max=la+constant;
		double lat_min=la-constant;
		double lat_max=la+constant;
		StringBuffer buffer=new StringBuffer();
		buffer.append("select*from lqr_park  where ( city like ?  AND ");
		if(area!=null&&!area.equals("")){
			buffer.append("area=?) AND ( ");
		}

		String sql="status=1)";
		buffer.append(sql);
		city="%"+city+"%";
		Object [] params={city};
		Object [] params2={city,area};
		 
		JSONArray end_arr=new JSONArray();
		JSONArray arr=new JSONArray();
		if(area!=null&&!area.equals("")){
			arr=baseDao.find(buffer.toString(), params2);
		}else{
			arr=baseDao.find(buffer.toString(), params);
		}
		
		JSONArray jsonArray=new JSONArray();
		List<Double> list_distance=new ArrayList<Double>();
		JSONObject object=new JSONObject();  
		for(int i=0;i<arr.size();i++){
			JSONObject json=(JSONObject) arr.get(i);
			double lng1=json.getDouble("lng");
			double lat1=json.getDouble("lat");
			double distance=MapUtil.getDistance(lng1, lat1, lg, la);  //距离，单位为米
			distance=distance/1000;  //转换成KM
			json.put("distance", distance);  //将距离封装到json对象中
			Integer userId=json.getInt("user_id");
			JSONObject user=userService.getUserNameAndImgBy(userId);
			json.put("userId",CreateDefinedPwdUtil.encryp(userId));
			json.put("userName", user.getString("userName"));
			jsonArray.add(json);
		}
		

		end_arr=bubbleSort(jsonArray, "distance");
		 
		if(pageSize==0){
			pageSize=20;
		}
		int count=end_arr.size();
		JSONArray returnArr=new JSONArray();
		if(pageNo==1&&pageSize>count){
			returnArr=end_arr;
		}else if(pageNo>1&&pageSize>count){
			  
		}else if(pageNo>1&&pageSize*pageNo>count){
			int past_count=(pageNo-1)*pageSize;
			int c=count-past_count;
			for(int i=(pageNo-1)*20;i<past_count+c;i++){
				JSONObject json=end_arr.getJSONObject(i);
				returnArr.add(json);
			}
		}else{
			for(int i=(pageNo-1)*20;i<(pageNo-1)*pageSize+pageSize;i++){
				JSONObject json=end_arr.getJSONObject(i);
				returnArr.add(json);
			}
		}
		
		
//		String sql1="INSERT INTO lb_court_log(add_time) VALUES(?)";
//		Object [] params1={System.currentTimeMillis()};
//		baseDao.insertBy(sql1, params1);
		return returnArr;
	}
	
	  /**
		  * JSONArray排序
		  * @param arr
		  * @param param
		  * @return
		  */
		    public static JSONArray bubbleSort(JSONArray arr,String param){
		    	JSONArray jsonArray=arr;
		        int score[] = {67, 69, 75, 87, 89, 90, 99, 100};
		        for (int i = 0; i < arr.size() -1; i++){    //最多做n-1趟排序
		            for(int j = 0 ;j < jsonArray.size()-i-1; j++){    //对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
		              JSONObject json1=arr.getJSONObject(j);
		              JSONObject json2=arr.getJSONObject(j+1);
		              double param1=json1.getDouble(param);
		              double param2=json2.getDouble(param);
		            	if(param1>param2){    //把小的值交换到后面
		                    
		                    jsonArray.set(j, json2);
		                    jsonArray.set(j+1, json1);
		                    
		                }
		            }            
//		            System.out.print("第" + (i + 1) + "次排序结果：");
//		            for(int a = 0; a <arr.size(); a++){
//		                System.out.print(arr.getJSONObject(a).getString(param) + "\t");
//		       }
//		            System.out.println("");
		        }
//		            System.out.print("最终排序结果：");
//		            for(int a = 0; a <arr.size(); a++){
//		                System.out.print(arr.getJSONObject(a).getString(param) + "\t");
//		       }
		    	return arr;
		    }
}
