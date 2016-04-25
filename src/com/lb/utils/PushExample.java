package com.lb.utils;

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
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushExample {
    protected static final Logger LOG = LoggerFactory.getLogger(PushExample.class);

    // demo App defined in resources/jpush-api.conf 
 
    private static final String appKey = "99f4658503e47184aa0dbf81";
	private static final String masterSecret = "2a560b2bc369fffaa21216b0";
	public static final String TITLE = "标题测试";
    public static final String ALERT = "通知测试";
    public static final String MSG_CONTENT = "去吃饭吗";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    

	public static void main(String[] args) {
	    testSendPush();
	   // buildPushObject_all_all_alert();
	}
	
	
	public static void testSendPush() {
	    // HttpProxy proxy = new HttpProxy("localhost", 3128);
	    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
       
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert();
        
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
	
	public static PushPayload buildPushObject_all_all_alert() {
	    return PushPayload.newBuilder().setPlatform(Platform.all())
                .setAudience(Audience.alias("1935"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert("test")
                                .setBadge(-1)
                                
                                .setSound("happy")
                                .addExtra("param", "675")
                                .build())
                                 .addPlatformNotification(AndroidNotification.newBuilder()
	                                .setAlert(ALERT)
	                                .addExtra("id", "")
	                                 
	                                .build())
                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder()
                         //.setApnsProduction(true)
                         .build())
                 .build()
                 ;
	}
	
	/**
	 * 构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
	 * @return
	 */
    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(ALERT))
                
                .build();
    }
    
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("魔术"))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }
    
    public static PushPayload buildPushObject_android_and_ios() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                		.setAlert("alert content")
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle("Android Title").build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(1)
                				.addExtra("extra_key", "extra_value").build())
                		.build())
                .build();
    }
    
    /**
     * 构建推送对象：平台是 iOS，推送目标是 "tag1", "tag_all" 的交集，推送内容同时包括通知与消息 - 通知信息是 ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容是 MSG_CONTENT。通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
	 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
     * @return
     */
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag("魔术","热火"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert("hello")
                                .setBadge(-1)
                                .setSound("happy")
                                .addExtra("param", "675")
                                .build())
                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 
                 .build();
    }
    
    //构建推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的并集）且（"alias1" 与 "alias2" 的并集），推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush。
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(MSG_CONTENT)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }
    
    
}

