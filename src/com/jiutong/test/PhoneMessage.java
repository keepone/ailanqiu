package com.jiutong.test;

import org.apache.commons.httpclient.Header;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.NameValuePair;  
import org.apache.commons.httpclient.methods.PostMethod;  

public class PhoneMessage {
    public static void main(String[] args) throws Exception {  
    	  
        HttpClient client = new HttpClient();  
        PostMethod post = new PostMethod("http://yunpian.com");  
        post.addRequestHeader("Content-Type",  
                "application/x-www-form-urlencoded;charset=gbk");// ��ͷ�ļ�������ת��  
        NameValuePair[] data = { new NameValuePair("Uid", "trick_td@163.com"), // ע����û���  
                new NameValuePair("Key", "914b45a1de9b4556f001"), // ע��ɹ���,��¼��վʹ�õ���Կ  
                new NameValuePair("smsMob", "15674975284"), // �ֻ�����  
                new NameValuePair("smsText", "��ã���ӭ�����룬��֤���ǣ�165234���û�����amdin�����룺admin.") };//���ö�������          
 
    post.setRequestBody(data);  
  
    client.executeMethod(post);  
    Header[] headers = post.getResponseHeaders();  
    int statusCode = post.getStatusCode();  
    System.out.println("statusCode:" + statusCode);  
    for (Header h : headers) {  
        System.out.println(h.toString());  
    }  
    String result = new String(post.getResponseBodyAsString().getBytes(  
            "gbk"));  
    System.out.println(result);  
    post.releaseConnection(); 
    
    
}}
