package com.jc.oa.common.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.common.domain.Remind;
import com.jc.oa.common.domain.validator.RemindValidator;
import com.jc.oa.common.service.IRemindService;
import com.jc.system.CustomException;
import com.jc.system.common.util.JsonUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title  GOA2.0源代码
 * @description  controller类
 * @author 
 * @version  2014-04-17
 */
 
@Controller
@RequestMapping(value="/sys/remind")
public class RemindController extends BaseController{
	
	@Autowired
	private IRemindService remindService;
	
	@org.springframework.web.bind.annotation.InitBinder("remind")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new RemindValidator()); 
    }
	
	public RemindController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Remind remind 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PageManager manageList(Remind remind,PageManager page){
		PageManager page_ = remindService.query(remind, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "sys/remind/remind1"; 
	}

/**
	 * @description 删除方法
	 * @param Remind remind 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(Remind remind,String ids) throws Exception{
		remind.setPrimaryKeys(ids.split(","));
		return remindService.delete(remind);
	}
/*
 * 
 * @RequestMapping(value = "test5", method = RequestMethod.POST)
	@ResponseBody
	public User test5(@RequestBody List<LinkedHashMap> usera, Model model) {
		Map a =  model.asMap();
		User user = SystemUtils.getUser();
		for(LinkedHashMap u:usera) {
			System.out.println(u.get("id"));
			/*Set set = u.entrySet();
			Iterator it = set.iterator();
			while(it.hasNext()) {
				Map<String, String> pu = (Map)it.next(); 
				System.out.println(pu.get("id"));
			}*/
		/*}
		User ao = new User();
		//	String loginName = getRequest().getAttribute(GlobalContext.LOGIN_NAME).toString();
		return ao;
	}
	@RequestMapping(value = "test6", method = RequestMethod.POST)
	@ResponseBody
	public User test6(@RequestBody User usera, Model model) {
		Map a =  model.asMap();
		User user = SystemUtils.getUser();
		User ao = new User();
		for(User u : usera.getUsers()) {
			
			System.out.println(u.getCode());
		}
		ao.setCode("6 code");
		//	String loginName = getRequest().getAttribute(GlobalContext.LOGIN_NAME).toString();
		return ao;
	}*/
	/**
	 * @description 保存方法
	 * @param Remind remind 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(String remind,BindingResult result) throws Exception{
		
		
		/*net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(remind.getRemind()); 
		  Remind bean = (Remind) net.sf.json.JSONObject.toBean( jsonObject, Remind.class );       
		  Remind a = (Remind)JsonUtil.json2Java(remind.getRemind(), Remind.class);
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			//int a = remindService.save(remind);
			resultMap.put("success", "true");
		}*/
		return null;
	}
	
	@RequestMapping(value = "remindByMode.action")
	@ResponseBody
	public void remindByMode() {
		List<Remind> l = null;
		//System.out.println(new Date(1398401100000L).toLocaleString());
		try {
			l = remindService.getNextReminds();
			System.out.println(l.size());
			if(l != null && l.size() > 0) {
				remindService.remindByMode(l);
			}
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getStackTrace());
		}
	}

	
	

	/**
	* @description 修改方法
	* @param Remind remind 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody//@RequestBody
	public Integer update(Remind remind) throws Exception{
		/*Integer flag = remindService.update(remind);
		return flag;*/
		
		 
		return 0;
	}

	/**
	 * @description 获取提醒的json格式数据
	 * @param Remind remind 实体类
	 * @return String 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public String get(Remind remind) throws Exception{
		Remind result = null;
		try {
			result = remindService.get(remind);
			if(result != null) {
				return JsonUtil.getJSON(result);
			} else {
				return "";
			}
		} catch (Exception e) {
			log.error(remind.getTableName() + ":获取提醒信息错误" + e.getStackTrace());
			return "";
		}
	}

	/**
	 * 方法描述：校验公共短信提醒
	 * @param remind
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年9月16日下午1:35:21
	 * @see
	 */
	@RequestMapping(value="validRemind.action")
	@ResponseBody
	@ActionLog(operateModelNm="公用提醒",operateFuncNm="validRemind",operateDescribe="校验将日志共享短信提醒时被提醒人是否存在电话号")
	public Map<String,Object> validRemind(Remind remind,HttpServletRequest request) throws Exception{
		String userIds = remind.getReceiveId();
		String createUser = SystemSecurityUtils.getUser().getId().toString();
		return remindService.validRemind(userIds,createUser);
	} 
	
	/**
	 * 
	 */
	
	
	/*
	 * 
	 * 
	 * */
	
}