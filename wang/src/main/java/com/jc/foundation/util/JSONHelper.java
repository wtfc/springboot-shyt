package com.jc.foundation.util;

import java.util.Map;
import com.alibaba.fastjson.JSONObject;

/**
 * JSON拼串帮助类
 * @title GOA V2.0
 * @description 
 * @version  2014年10月8日
 * 使用方法:
 * String result = JSONHelper.helper().inflate("hello","hello").inflate("world","world").helper().toString();
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JSONHelper {
	
    private final JSONObject jsonObject;
    
    public static Helper helper(){
        return new Helper();
    }
 
    private JSONHelper(final Helper helper){
        this.jsonObject = helper.jsonObject;
    }
 
    public static class Helper{
    	
        private JSONObject jsonObject = new JSONObject();
 
        public JSONHelper help(){
            return new JSONHelper(this);
        }
        
        public Helper inflate(String key,Object value){
            jsonObject.put(key,value);
            return this;
        }
        
		public Helper inflateALL(Map map){
            jsonObject.putAll(map);
            return this;
        }
    }
    
    @Override
    public String toString() {
        return jsonObject.toString();
    }
}