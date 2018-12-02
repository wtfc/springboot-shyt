package com.jc.android.oa.po.worklog.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.po.diary.domain.Diary4M;
import com.jc.android.oa.po.worklog.domain.Worklog4M;
import com.jc.foundation.domain.PageManager;
import com.jc.oa.po.PoException;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.worklog.domain.Worklog;
import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.oa.po.worklog.service.IWorklogService;
import com.jc.system.CustomException;
import com.jc.system.common.util.BeanUtil;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;

/**
 * @title 个人办公 工作日志
 * @description  controller类
 * @version  2014-09-16
 */
@Controller
@RequestMapping(value="/android/po/worklog")
public class Worklog4MController {
	
	@Autowired
	private IWorklogService worklogService;
	
	/**
	 * @description 我的日志查询方法
	 * @return 
	 * @throws Exception
	 * @author 金峰
	 * @version  2014-09-16
	 */
	/*@RequestMapping(value="getMyworklogList4M.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="移动端获取我的日志列表",operateFuncNm="getMyworklogList4M",operateDescribe="移动端获取我的日志列表") 
	public Map<String, Object> getMyworklogList4M(String date,String userid,String curPage, HttpSession httpSession) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(date)&&StringUtils.isNotEmpty(userid)&&StringUtils.isNotEmpty(curPage)){
			PageManager page =new PageManager();
			page.setPage(Integer.parseInt(curPage));
			
			Worklog worklog=new Worklog();
			worklog.setCreateUser(Long.parseLong(userid));
			String year=date.substring(0,4);
			String month=date.substring(5,date.length());
			int day=DateUtils.getMonthDays(Integer.parseInt(year),Integer.parseInt(month));
			String startDate=date+"-01 00:00:00";
			String endDate=date+"-"+day+" 23:59:59";
			worklog.setWorklogDateBegin(DateUtils.parseDate(startDate));
			worklog.setWorklogDateEnd(DateUtils.parseDate(endDate));
			
			List<Worklog> list= worklogService.getWorklogPage4M(worklog,page);
			List<Worklog4M> alist=new ArrayList<Worklog4M>();
			if(list!=null){
				for(Worklog i : list){
					//移动端封装数据的bean
					Worklog4M vo=new Worklog4M();
					//web端bean转化为移动端bean，传输数据量更小
					BeanUtils.copyProperties(i, vo);// (源，目标)
					alist.add(vo);
				}
				if(alist!=null){
					//成功状态位和数据
					resultMap.put("state", "success");
					resultMap.put("data", alist);
				}
			}else{
				//失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
			}
		}else{
			//失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	*/
	
	/**
	 * 方法描述：我的日志查询方法
	 * @param date
	 * @param userId
	 * @param curPage
	 * @param httpSession
	 * @return
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年9月29日上午10:00:44
	 * @see
	 */
	@RequestMapping(value="getMyworklogList4M.action")
	@SuppressWarnings({ "unchecked", "unused" })
	@ResponseBody
	@ActionLog(operateModelNm="移动端获取我的日志列表",operateFuncNm="getMyworklogList4M",operateDescribe="移动端获取我的日志列表") 
	public Map<String, Object> getMyworklogList4M(String date,String userId, HttpSession httpSession) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(date)&&StringUtils.isNotEmpty(userId)){
			Worklog worklog = new Worklog();
			worklog.setCreateUser(Long.parseLong(userId));
			
			String startDate = date+" 00:00:00";
			String endDate = date+" 23:59:59";
			worklog.setWorklogDateBegin(DateUtils.parseDate(startDate));
			worklog.setWorklogDateEnd(DateUtils.parseDate(endDate));
			
			List<Worklog> list= worklogService.getMyworklogCollect(worklog);
			for(Worklog w:list){
				List<WorklogContent> todayLogs = w.getTodayLogs();
				StringBuffer worklogContent = new StringBuffer();
				for(int j=0;j<todayLogs.size();j++){
					worklogContent.append((j+1)+"."+todayLogs.get(j).getContent()+"\n");
				}
				w.setTodayLogStr(worklogContent.toString());
			}
			
		
			List<Worklog4M> alist = new ArrayList<Worklog4M>();
			
			if(list!=null){
				for(Worklog i : list){
					//移动端封装数据的bean
					Worklog4M vo=new Worklog4M();
					//web端bean转化为移动端bean，传输数据量更小
					BeanUtils.copyProperties(i, vo);// (源，目标)
					vo.setWorklogDate(DateUtils.formatDate(i.getWorklogDate(), "yyyy-MM-dd"));
					alist.add(vo);
				}
				//成功状态位和数据
				resultMap.put("state", "success");
				resultMap.put("data", alist);
			} else
				extracted(resultMap);
		} else
			extracted(resultMap);
		return resultMap;
	}

	private void extracted(Map<String, Object> resultMap) {
		{
			//失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
	}
	
	/**
	 * @description 保存方法
	 * @return 
	 * @throws Exception
	 * @author jinf
	 * @version  2014-09-16
	 * @throws CustomException 
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="save",operateDescribe="保存方法") 
	public Map<String,Object> save(String userid,String title,String content,String date) throws CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(userid)&&StringUtils.isNotEmpty(title)
				&&StringUtils.isNotEmpty(content)&&StringUtils.isNotEmpty(date)){
			Worklog worklog=new Worklog();
			worklog.setTitle(title);
			WorklogContent worklogContent = new WorklogContent();
			worklogContent.setContent(content);
			worklogContent.setContentType("0");//0 今日日志 1 明日提醒
			List<WorklogContent> wcl=new ArrayList<WorklogContent>();
			wcl.add(worklogContent);
			worklog.setTodayLogs(wcl);
			Integer result =worklogService.save(worklog);
			if (result== 1) {
				resultMap.put("state", "success");
			}else{
				//失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",MessageUtils.getMessage("JC_SYS_002"));
			}
		} else
			extracted(resultMap);
		return resultMap;
	}
}
