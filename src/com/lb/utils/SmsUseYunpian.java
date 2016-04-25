package com.lb.utils;
import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 
 * @author : Ares.yi
 * @createTime : 2014-3-26 下午03:20:26
 * @version : 1.0
 * @description :
 *
 */
public class SmsUseYunpian {
	
	
	private static String API_KEY = "f8a9d7a404edba44bc528e62b650fcdf";
	/**
	 * 服务http地址
	 */
	private static String BASE_URI = "http://yunpian.com";
	/**
	 * 服务版本号
	 */
	private static String VERSION = "v1";
	/**
	 * 编码格式
	 */
	private static String ENCODING = "UTF-8";

	/**
	 * 通用发送接口的http地址
	 */
	private static String URI_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/send.json";
	
	/**
	 * 模板发送接口的http地址
	 */
	private static String URI_TPL_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/tpl_send.json";
	
	
	/**
	 * 发短信
	 * @param text　短信内容　
	 * @param mobile　接受的手机号
	 * @return json格式字符串
	 * @throws IOException 
	 */
	public static String sendSms(String text, String mobile) throws IOException{
		HttpClient client = new HttpClient();
		
		NameValuePair[] nameValuePairs = new NameValuePair[3];
		nameValuePairs[0] = new NameValuePair("apikey", API_KEY);
		nameValuePairs[1] = new NameValuePair("text", text);
		nameValuePairs[2] = new NameValuePair("mobile", mobile);
		
		PostMethod method = new PostMethod(URI_SEND_SMS);
		method.setRequestBody(nameValuePairs);
		
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		
		client.executeMethod(method);
		
		return method.getResponseBodyAsString();
	}
	
	/**
	 * 通过模板发送短信
	 * @param tpl_id　模板id
	 * @param tpl_value　模板变量值　
	 * @param mobile　接受的手机号
	 * @return json格式字符串
	 * @throws IOException 
	 */
	public static String tplSendSms(long tpl_id, String tpl_value, String mobile) throws IOException{
		HttpClient client = new HttpClient();
		
		NameValuePair[] nameValuePairs = new NameValuePair[4];
		nameValuePairs[0] = new NameValuePair("apikey", API_KEY);
		nameValuePairs[1] = new NameValuePair("tpl_id", String.valueOf(tpl_id));
		nameValuePairs[2] = new NameValuePair("tpl_value", tpl_value);
		nameValuePairs[3] = new NameValuePair("mobile", mobile);
		
		PostMethod method = new PostMethod(URI_TPL_SEND_SMS);
		method.setRequestBody(nameValuePairs);
		
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		
		client.executeMethod(method);
		
		return method.getResponseBodyAsString();
	}
	
	/**
	 * 激活码发送短信
	 * @param vrifyCode　激活码
	 * @param mobile　接受的手机号
	 * @throws IOException 
	 */
	public static String vrifySendSms(String vrifyCode, String mobile) throws IOException{
		
		System.out.println("\tSMS YP vrifyCode:" + vrifyCode + ",mobile:"+mobile);
		
		int tpl_id = 321205;
		
		String tpl_value="#app#=人脉通&#code#="+vrifyCode+"&#company#=人脉通";
		
		String res = tplSendSms(tpl_id, tpl_value, mobile);
		
		System.out.println(res);
		
		return res;
	}
	
	
	/**
	 * TEST
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//修改为您的手机号
		String mobile = "15674975284";
		
		
		/**************** 使用通用接口发短信 *****************/
 		//String text = "您的验证码是:1234【久通网】";
 		//System.out.println(SmsUseYunpian.sendSms(text, mobile));
		
		
		
		/**************** 使用模板接口发短信 *****************/
//		String res = SmsUseYunpian.vrifySendSms("3234", mobile);
//		System.out.println(res);
//		System.out.println(JSONObject.fromObject(res).getInt("code")); 
		
	String count=SmsUseYunpian.tplSendSms(1, "#code#="+12345+"&#company#=梦想家", mobile);
		System.out.println(count);
	}
}  