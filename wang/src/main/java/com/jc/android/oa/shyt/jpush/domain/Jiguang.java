package com.jc.android.oa.shyt.jpush.domain;

import cn.jpush.api.JPushClient;  
import cn.jpush.api.push.model.Platform;  
import cn.jpush.api.push.model.PushPayload;  
import cn.jpush.api.push.model.audience.Audience;  
import cn.jpush.api.push.model.notification.Notification;  
  
public class Jiguang {  
	public static JPushClient jPushClient=null;  
	private final static String appKey = "f166d3793a30b2be1b16bfa5";
	 
    private final static String masterSecret = "fddfa809073d1bc4e5f533c5";  
    static {  
        jPushClient =new JPushClient(masterSecret, appKey);  
        //PushPayload payload = buildPushObject_all_all_alert();  
    }  
      
     
    public static PushPayload buildPushObject_all_all_alert(){  
       return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias("xiabo")).setNotification(Notification.android("飞哥的美好时光", "机房应用APP", null)).build();  
    }   
}  
