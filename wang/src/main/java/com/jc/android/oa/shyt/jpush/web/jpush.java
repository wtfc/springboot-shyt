package com.jc.android.oa.shyt.jpush.web;

//import horizon.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.shyt.jpush.domain.Jiguang;
import com.jc.foundation.web.BaseController;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

@Controller
@RequestMapping(value="/android/jpush")
public class jpush extends BaseController{
	
	@RequestMapping(value = "push.action")
	@ResponseBody   
    public static String push(HttpServletResponse response) throws APIConnectionException, APIRequestException{    
//		PushPayload payload = Jiguang.buildPushObject_all_all_alert();  
//        PushResult result = Jiguang.jPushClient.sendPush(payload);  
//        response.setContentType("text/html");    
//        response.setCharacterEncoding("utf-8");    
//        PrintWriter out=null;    
//        try {    
//            Map<String, String> maps=new HashMap<String, String>();  
//            maps.put("name", "推送成功");  
//              
//            JSONArray array=JSONArray.fromObject(maps);  
//           // JSONObject obj = JSONObject.fromObject(app);  
//            out=response.getWriter();    
//            out.print(array);  
//              
//        } catch (IOException e) {    
//            // TODO Auto-generated catch block    
//            e.printStackTrace();    
//        }finally{  
//            out.close();  
//        }  
        return null;
    }  
}