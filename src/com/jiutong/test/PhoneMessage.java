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
                "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码  
        NameValuePair[] data = { new NameValuePair("Uid", "trick_td@163.com"), // 注册的用户名  
                new NameValuePair("Key", "914b45a1de9b4556f001"), // 注册成功后,登录网站使用的密钥  
                new NameValuePair("smsMob", "15674975284"), // 手机号码  
                new NameValuePair("smsText", "你好，欢迎你申请，验证码是：165234。用户名：amdin。密码：admin.") };//设置短信内容          
 
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
