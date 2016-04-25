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
 * @createTime : 2014-3-26 ����03:20:26
 * @version : 1.0
 * @description :
 *
 */
public class SmsUseYunpian {
	
	
	private static String API_KEY = "f8a9d7a404edba44bc528e62b650fcdf";
	/**
	 * ����http��ַ
	 */
	private static String BASE_URI = "http://yunpian.com";
	/**
	 * ����汾��
	 */
	private static String VERSION = "v1";
	/**
	 * �����ʽ
	 */
	private static String ENCODING = "UTF-8";

	/**
	 * ͨ�÷��ͽӿڵ�http��ַ
	 */
	private static String URI_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/send.json";
	
	/**
	 * ģ�巢�ͽӿڵ�http��ַ
	 */
	private static String URI_TPL_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/tpl_send.json";
	
	
	/**
	 * ������
	 * @param text���������ݡ�
	 * @param mobile�����ܵ��ֻ���
	 * @return json��ʽ�ַ���
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
	 * ͨ��ģ�巢�Ͷ���
	 * @param tpl_id��ģ��id
	 * @param tpl_value��ģ�����ֵ��
	 * @param mobile�����ܵ��ֻ���
	 * @return json��ʽ�ַ���
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
	 * �����뷢�Ͷ���
	 * @param vrifyCode��������
	 * @param mobile�����ܵ��ֻ���
	 * @throws IOException 
	 */
	public static String vrifySendSms(String vrifyCode, String mobile) throws IOException{
		
		System.out.println("\tSMS YP vrifyCode:" + vrifyCode + ",mobile:"+mobile);
		
		int tpl_id = 321205;
		
		String tpl_value="#app#=����ͨ&#code#="+vrifyCode+"&#company#=����ͨ";
		
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
		//�޸�Ϊ�����ֻ���
		String mobile = "15674975284";
		
		
		/**************** ʹ��ͨ�ýӿڷ����� *****************/
 		//String text = "������֤����:1234����ͨ����";
 		//System.out.println(SmsUseYunpian.sendSms(text, mobile));
		
		
		
		/**************** ʹ��ģ��ӿڷ����� *****************/
//		String res = SmsUseYunpian.vrifySendSms("3234", mobile);
//		System.out.println(res);
//		System.out.println(JSONObject.fromObject(res).getInt("code")); 
		
	String count=SmsUseYunpian.tplSendSms(1, "#code#="+12345+"&#company#=�����", mobile);
		System.out.println(count);
	}
}  