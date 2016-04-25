package com.proxy;

import java.io.File;
import java.util.List;

import com.proxy.ReptileTaskPool;;

public class ReadIp implements Runnable{

	public static void main(String[] args){
		File file = new File(Util.class.getResource(".").getPath()+"/ipSuccess.txt");
		if(file.exists()){
			file.delete();
		}
		ReptileTaskPool.sharedInstance().doRun(new ReadIp());			// 读取ip线程
		for(int i=0;i<=10;i++){
			System.out.println(i);
			
			ReptileTaskPool.sharedInstance().doRun(new IPTest());
		}
		//ReptileTaskPool.sharedInstance().doRun(new WriteFileIp());

	} 
	
	public void run() {
		List<String> list = Util.readListFile(ReadIp.class.getResource("ipLibrary.txt").getPath()); // 按照行读取到list

		for (int i = 0; i < list.size(); i++) {
			String[] strUrl = list.get(i).split("@");
			try {
				ShareQueue.brandQueue.put(strUrl[0]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
