package com.lb.admin.service;

public interface CommonService {
	public Integer deleteMatchModelData();
	
	public Integer addSysMessage(Integer messageType,String messageContent,Integer thirdId);
}
