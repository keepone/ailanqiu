package com.jiutong.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Encoder;

public class Reg {
	public static void main(String[] args){
		String html="http://zx.caipiao.163.com/article/14/0929/16/A7ASVH4200052DT2.htmlG";
		List  resultList = new ArrayList();
		String reg="http://zx.caipiao.163.com[\\a-zA-Z0-9]+\\.html$";
        Pattern p = Pattern.compile(reg);//ƥ��http://zx.caipiao.163.com��ͷ��.html��β���ĵ�
        Matcher m = p.matcher(html );//��ʼ����
        while (m.find()) {
           System.out.println("OK");
          
        }
        
         
    }
	}

