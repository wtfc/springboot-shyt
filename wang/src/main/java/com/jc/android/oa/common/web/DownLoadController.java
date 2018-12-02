package com.jc.android.oa.common.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.system.security.util.SettingUtils;
@Controller
@RequestMapping(value="/android/common/download")
public class DownLoadController {
	@RequestMapping(value="getAttachFilePath.action")
	@ResponseBody
	public Map getAttachFilePath(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("data", SettingUtils.getSetting(SettingUtils.FILE_PATH));
		map.put("state", "success");
		return map;
	}
}
