package com.jc.system.notice.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.system.CommonException;
import com.jc.system.CustomException;
import com.jc.system.notice.domain.NoticeMsg;
import com.jc.system.notice.domain.validator.NoticeMsgValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.system.notice.service.INoticeMsgService;


/**
 * @title GOA系统管理
 * @description  controller类
 * @author 
 * @version  2014-06-05
 */
 
@Controller
@RequestMapping(value="/sys/noticeMsg")
public class NoticeMsgController extends BaseController{
	
	@Autowired
	private INoticeMsgService noticeService;
	
	@org.springframework.web.bind.annotation.InitBinder("notice")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new NoticeMsgValidator()); 
    }
	
	public NoticeMsgController() {
	}

	/**
	 * 弹出框查询方法
	 * @param NoticeMsg notice 实体类
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 * @throws CustomException 
	 */
	@RequestMapping(value="queryMsgTip.action")
	@ResponseBody
	public List<NoticeMsg> manageList(HttpServletRequest request) throws CustomException{
		List<NoticeMsg> list = noticeService.queryMsg(SystemSecurityUtils.getUser().getId());
		return list;
	}
	
	/**
	 * 弹出框查询方法
	 * @param NoticeMsg notice 实体类
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 * @throws CustomException 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(NoticeMsg noticeMsg,PageManager page,HttpServletRequest request){
		//默认排序
		noticeMsg.setReceiveUser(SystemSecurityUtils.getUser().getId());
		if(StringUtils.isEmpty(noticeMsg.getOrderBy())) {
			noticeMsg.addOrderByFieldDesc("t.CREATE_DATE");
		}
		PageManager page_ = noticeService.query(noticeMsg, page);
		return page_; 
	}
	
	/**
	 * 消息页面跳转方法
	 */
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="通知消息表",operateFuncNm="gotoPage",operateDescribe="消息页面跳转方法")
	public String gotoPage(HttpServletRequest request) throws CustomException{
		return "sys/noticeMsg/noticeMsg";
	}
	
	/**
	 * 设为只读方法
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="readNoticeByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="通知消息表",operateFuncNm="updateNoticeToReadedByIds",operateDescribe="对消息表进行已读操作")
	public Integer updateNoticeToReadedByIds(NoticeMsg notice,String ids,HttpServletRequest request) throws CommonException{
		notice.setPrimaryKeys(ids.split(","));
		return noticeService.readNotice(notice);
	}
	
	/**
	 * 删除方法
	 * @param NoticeMsg notice 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="通知消息表",operateFuncNm="deleteByIds",operateDescribe="对通知消息表进行删除")
	public Integer deleteByIds(NoticeMsg notice,String ids,HttpServletRequest request) throws Exception{
		notice.setPrimaryKeys(ids.split(","));
		return noticeService.delete(notice);
	}

	/**
	* 修改方法
	* @param NoticeMsg notice 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="通知消息表",operateFuncNm="update",operateDescribe="对通知消息表进行更新操作")
	public Map<String, Object> update(NoticeMsg notice, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = noticeService.update(notice);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param NoticeMsg notice 实体类
	 * @return Notice 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="通知消息表",operateFuncNm="get",operateDescribe="对通知消息表进行单条查询操作")
	public NoticeMsg get(NoticeMsg notice,HttpServletRequest request) throws Exception{
		return noticeService.get(notice);
	}

}