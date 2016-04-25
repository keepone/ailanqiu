package com.lb.service;

public interface BattleService {
	public Integer addSchool(Integer courtType,Integer userId, String schoolName, String areaDetail) ;
	public Integer updateHomeCourt(Integer schoolId, Integer userId) ;
	public Integer addUser(String userName,String userImg,Integer userType,String password,Integer phone,Integer height,Integer weight);
	public Integer addSchoolTeam(String grade,String className,String teamName, String teamImg,
			Integer school_id,Integer userId);	
	public Integer addUserToTeam(Integer userId, Integer teamId);
		
	
}
