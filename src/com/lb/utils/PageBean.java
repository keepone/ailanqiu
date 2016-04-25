package com.lb.utils;

import java.util.List;

import net.sf.json.JSONArray;

public class PageBean {
	private Integer pageNo=1;
	private Integer pageSize=20;
	private Integer rowCount=0;
	private Integer pageCount=0;
	private Integer groupNo=1;
	private Integer groupSize=2;
	private Integer groupCount;
	/*private JSONArray data;
	public JSONArray getData() {
		return data;
	}
	public void setData(JSONArray data) {
		this.data = data;
	}*/
	private Object data;
	 

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
		if(rowCount<=pageSize&&rowCount!=0){
			this.pageCount=1;
		}
		if(rowCount==0){
			this.pageCount=0;
		}
		if(rowCount>pageSize){
			this.pageCount=(rowCount%pageSize==0)?rowCount/pageSize:rowCount/pageSize+1;
		}
		
		if(pageCount<=groupSize&&pageCount!=0){
			this.groupCount=1;
		}
		if(pageCount==0){
			this.groupCount=0;
		}
		if(pageCount>groupSize){
			this.groupCount=(pageCount%groupSize==0)?pageCount/groupSize:pageCount/groupSize+1;
		}
		
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public Integer getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}
	public Integer getGroupSize() {
		return groupSize;
	}
	public void setGroupSize(Integer groupSize) {
		this.groupSize = groupSize;
	}
	public Integer getGroupCount() {
		return groupCount;
	}
	
}
