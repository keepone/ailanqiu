package com.jiutong.test;

public class Teacher {

	 
	 public static void main(String[] args) {
	  // TODO Auto-generated method stub
	  //创建a这个人给a学生取名李明
	  Student a=new Student("李明"); 
	  //创建b这个人给b学生取名赵明
	  Student b=new Student("赵明");
	  //创建c这个人，还未起名
	  Student c=new Student();
	  //分别输出a,b两个人的名字
	  System.out.println("a同学名字是"+a.name);
	  System.out.println("b同学名字是"+b.name);
	  //将c人的名字指向b，实质是将b的地址赋给c，这样c,b同指一片内存区域
	  c=b;
	  System.out.println("c同学名字是"+c.name);
	  //果然c的名字变为了赵明
	  b.changName(a);
	  //利用改名函数改变b的名字，将a对象传入将a的名字赋给b
	  System.out.println("b同学名字是"+b.name);
	  //结果是a的名字李明
	  System.out.println(c.name);
	 }

	}