package com.jiutong.test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class MyTestServiceImpl implements IMyTestService {
	//@Scheduled(cron="0 0 19 * * ?")   //ÿ��6��ִ��ָ������
	public void myTest() {
		 //System.out.println(TimeUtil.);
		 System.out.println("�������"); 
	}
	public static void main(String[] args){
		System.out.println("i");
	}
	 
     
}
