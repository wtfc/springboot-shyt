package com.jc.system.remind.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.web.BaseController;
import com.jc.system.remind.domain.Remind;
import com.jc.system.remind.domain.RemindNum;
import com.jc.system.remind.service.IRemindService;
import com.jc.system.remind.util.FileXmlUtil;
import com.jc.system.security.util.ActionLog;

/**
 * @title GOA系统管理
 * @description  controller类
 * @version  2014-07-24
 */
 
@Controller
@RequestMapping(value="/sys/reminders")
public class RemindersController extends BaseController{
	
	public RemindersController() {
	}
	
	/**
	 * 获得待办数量
	 * @param String ids 删除的多个主键
	 * @return Integer 数量
	 * @throws Exception
	 * @author
	 * @version  2014-07-23
	 */
	@RequestMapping(value="getRemindCount.action")
	@ResponseBody
	public List<RemindNum> getRemindCount(HttpServletRequest request) throws Exception{
		List<RemindNum> listRetur = new ArrayList();
		List<Remind> list = FileXmlUtil.getFieldList(request);
		for(Remind remind:list){
			if(remind!=null&&remind.getDivid()!=null&&remind.getDivid()!=null&&remind.getTegartClass()!=null){
				RemindNum remindBean = new RemindNum();
				IRemindService remindService = (IRemindService)Class.forName(remind.getTegartClass()).newInstance();
			String remindCount = remindService.getRemindCount();
			if(Integer.parseInt(remindCount)>99){
				remindCount="99";
			}
			remindBean.setNum(remindCount);
			remindBean.setDivid(remind.getDivid());
			listRetur.add(remindBean);
			}
		}
		
		return listRetur;
	}

	
}