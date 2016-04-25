package com.proxy;

import java.io.File;

import com.proxy.ReptileTaskPool;

public class Start {
	
	public static void main(String[] args){
		System.out.println(ReadIp.class.getResource("ipLibrary.txt").getPath());
		String a=Util.class.getResource(".").getPath()+"/ipSuccess.txt";
		new File(Util.class.getResource(".").getPath()+"/ipSuccess.txt").delete();		//删除目录
		ReptileTaskPool.sharedInstance().doRun(new ReadIp());			//读取ip线程
		for(int i=0;i<=300;i++){
			ReptileTaskPool.sharedInstance().doRun(new IPTest());
		}

	} 
}
 