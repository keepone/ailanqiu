package com.jiutong.test;

public class Teacher {

	 
	 public static void main(String[] args) {
	  // TODO Auto-generated method stub
	  //����a����˸�aѧ��ȡ������
	  Student a=new Student("����"); 
	  //����b����˸�bѧ��ȡ������
	  Student b=new Student("����");
	  //����c����ˣ���δ����
	  Student c=new Student();
	  //�ֱ����a,b�����˵�����
	  System.out.println("aͬѧ������"+a.name);
	  System.out.println("bͬѧ������"+b.name);
	  //��c�˵�����ָ��b��ʵ���ǽ�b�ĵ�ַ����c������c,bָͬһƬ�ڴ�����
	  c=b;
	  System.out.println("cͬѧ������"+c.name);
	  //��Ȼc�����ֱ�Ϊ������
	  b.changName(a);
	  //���ø��������ı�b�����֣���a�����뽫a�����ָ���b
	  System.out.println("bͬѧ������"+b.name);
	  //�����a����������
	  System.out.println(c.name);
	 }

	}