package com.jiutong.test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class MyTestServiceImpl implements IMyTestService {
	//@Scheduled(cron="0 0 19 * * ?")   //每天6点执行指定任务
	public void myTest() {
		 //System.out.println(TimeUtil.);
		 System.out.println("进入测试"); 
	}
	public static void main(String[] args){
		System.out.println("i");
	}
	 
     
}
