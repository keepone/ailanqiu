package com.lb.utils;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
 
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JpushUtil {
	protected static final Logger LOG = LoggerFactory.getLogger(PushExample.class);

	
 
	public static final String TITLE = "Test from API example";
    public static final String ALERT_LEAVE = "你有新的回复了";
    public static final String ALERT_FANS = "你有新的粉丝";
    public static final String ALERT_AGREE = "有人给你点赞了";
    public static final String MSG_CONTENT = "MSG_CONTENT";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		//testSendPush();
//pushMessage("1935", 3);
		// pushDynamicMessage(2873, 5,"今天是习大大生日，一起帮他上头条。");
		
		//活动推送
		pushDynamicMessage(7, 6, "【球衣秀】球衣本身是篮球的一部分，关于偶像，态度，故事。快来秀出你的球衣。",1,"http://121.41.25.90/lbResource/getLbResourceDetail.do?resourceId=20443&report=report&from=singlemessage&isappinstalled=0");
		System.out.println(System.currentTimeMillis());
	}
	
	/**
	 * 
	 * @param userId
	 * @param reportType  1表示点赞  2表示粉丝 3表示回复
	 */
	public static void pushMessage(String userId,Integer reportType) {
		  String appKey = "99f4658503e47184aa0dbf81";
		  String masterSecret = "2a560b2bc369fffaa21216b0";
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = reportToUser(userId, reportType);
        
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
	}
	
	
	public static Integer pushDynamicMessage(Integer dynamicId,Integer reportType,String dynamicContent,Integer fileType,String fileHref) {
		Integer count=1;
		  String appKey = "99f4658503e47184aa0dbf81";
		  String masterSecret = "2a560b2bc369fffaa21216b0";
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = pushDynamicToUser(dynamicId, dynamicContent, reportType,fileType,fileHref);
        
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            
        } catch (APIConnectionException e) {
        	 count=0;
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
        	 count=0;
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
        return count;
	}
	 
	 public static PushPayload reportToUser(String userId,Integer reportType) {
		 
		 String ALERT=ALERT_LEAVE;
		 if(reportType==1){
			 ALERT=ALERT_AGREE;
		 }else if(reportType==2){
			 ALERT=ALERT_FANS;
		 }
		    return PushPayload.newBuilder().setPlatform(Platform.all())
	                .setAudience(Audience.alias(userId))
	                .setNotification(Notification.newBuilder()
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                .setAlert(ALERT)
	                                .setBadge(-1)     
	                                .setSound("happy")
	                                .addExtra("reportType",reportType)
	                                .build())
//	                                 .addPlatformNotification(AndroidNotification.newBuilder()
//		                                .setAlert(ALERT)
//		                               .addExtra("reportType",reportType)
//		                                .build())
	                        .build())
	                 .setMessage(Message.content(MSG_CONTENT))
	                 .setOptions(Options.newBuilder()
	                         .setApnsProduction(true)
	                         .build())
	                 .build()
	                 ;
		}
	 
 public static PushPayload pushDynamicToUser(Integer dynamicId,String dynamicContent,Integer reportType,Integer fileType,String fileHref) {
		    return PushPayload.newBuilder().setPlatform(Platform.all())
	                .setAudience(Audience.all())
	                .setNotification(Notification.newBuilder()
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                .setAlert(dynamicContent)
	                                .setBadge(-1)     
	                                .setSound("happy")
	                                .addExtra("reportType",reportType)
	                                 .addExtra("dynamicId",dynamicId)
	                                 .addExtra("fileType",1)
	                                  .addExtra("fileHref",fileHref)
	                                .build())
	                                 .addPlatformNotification(AndroidNotification.newBuilder()
		                                .setAlert(dynamicContent)
		                               .addExtra("reportType",reportType)
		                               .addExtra("dynamicId",dynamicId)
		                                .addExtra("fileType",fileType)
		                                .addExtra("fileHref",fileHref)
		                                .build())
	                        .build())
	                 .setMessage(Message.content(MSG_CONTENT))
	                 .setOptions(Options.newBuilder()
	                          .setApnsProduction(true)
	                         .build())
	                 .build()
	                 ;
		}
 
 public static PushPayload pushAdDynamicToUser(Integer adId,String dynamicContent,Integer reportType) {
	    return PushPayload.newBuilder().setPlatform(Platform.all())
             .setAudience(Audience.alias("14100"))
             .setNotification(Notification.newBuilder()
                     .addPlatformNotification(IosNotification.newBuilder()
                             .setAlert(dynamicContent)
                             .setBadge(-1)     
                             .setSound("happy")
                             .addExtra("reportType",reportType)
                              .addExtra("dynamicId",adId)
                             .build())
//                              .addPlatformNotification(AndroidNotification.newBuilder()
//	                                .setAlert(dynamicContent)
//	                               .addExtra("reportType",reportType)
//	                                .build())
                     .build())
              .setMessage(Message.content(MSG_CONTENT))
              .setOptions(Options.newBuilder()
                      //.setApnsProduction(true)
                      .build())
              .build()
              ;
	}

}
