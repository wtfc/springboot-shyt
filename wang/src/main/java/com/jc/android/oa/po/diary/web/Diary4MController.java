package com.jc.android.oa.po.diary.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.po.diary.domain.Diary4M;
import com.jc.android.oa.po.diary.domain.DiaryByLeaderShare4M;
import com.jc.android.oa.po.diary.domain.Leader4M;
import com.jc.foundation.service.IBeanPropertyService;
import com.jc.foundation.web.BaseController;
import com.jc.oa.common.domain.ControlSide;
import com.jc.oa.common.service.IControlSideService;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.diary.service.IDiaryService;
import com.jc.system.common.service.ICommonService;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;

/**
 * @title 个人办公 工作日程
 * @description  controller类
 * @version  2014-09-15
 */
@Controller
@RequestMapping(value="/android/po/diary")
public class Diary4MController extends BaseController{
	@Autowired
	private IDiaryService diaryService;
	
	@Autowired
	protected IBeanPropertyService propertyService;
	 
	@Autowired
	private ICommonService commonService;
	
	@Autowired
	private IControlSideService controlSideService;
	
	/** 方法描述： 保存方法
	 * @param diary 实体类
	 * @param result 校验结果
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:35:13
	 * @see
	 */
	@RequestMapping(value = "save4M.action")
	@ResponseBody
	//@ActionLog(operateModelNm="移动端保存日程",operateFuncNm="save4M",operateDescribe="移动端保存日程")
	public Map<String,Object> save(String userId,String title,String content,
			String starttime,String endtime,
			HttpSession httpSession) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(title)
				&&StringUtils.isNotEmpty(content)&&StringUtils.isNotEmpty(starttime)
				&&StringUtils.isNotEmpty(endtime)){
			Diary diary=new Diary();
			diary.setPossessorId(Long.parseLong(userId));
			diary.setTitle(title);
			diary.setContent(content);
			diary.setStarttime(DateUtils.parseDate(starttime));
			diary.setEndtime(DateUtils.parseDate(endtime));
			propertyService.fillProperties(diary,false);
			diary.setMillisecond(diary.getCreateDate().getTime());
			Integer result =diaryService.save(diary);
			if (result==1) {
				//成功状态位
				resultMap.put("state", "success");
			}else{
				//失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",MessageUtils.getMessage("JC_SYS_002"));
			}
		}else{
			//失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	
	/** 方法描述：查询方法 查询日程数据 
	 * @param diary 实体类
	 * @param request
	 * @return List<Diary> 查询结果
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:53
	 * @see
	 */
	@RequestMapping(value="queryDiaryList4M.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	//@ActionLog(operateModelNm="移动端得到日程列表",operateFuncNm="queryDiaryList4M",operateDescribe="移动端得到日程列表")
	public Map<String, Object> queryDiaryList4M(String date,String userId,String curPage, HttpSession httpSession) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(date)&&StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(curPage)){
			List<Diary> list= diaryService.queryForListView4M(date,userId,curPage);
			List<Diary4M> alist=new ArrayList<Diary4M>();
			if(list!=null){
				for(Diary i : list){
					//移动端封装数据的bean
					Diary4M vo=new Diary4M();
					//web端bean转化为移动端bean，传输数据量更小
					BeanUtils.copyProperties(i, vo);// (源，目标)
					vo.setStarttime(DateUtils.formatDate(i.getStarttime(),"yyyy-MM-dd HH:mm:ss"));
					vo.setEndtime(DateUtils.formatDate(i.getEndtime(),"yyyy-MM-dd HH:mm:ss"));
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
		//以json串返回结果集
		return resultMap; 
	}
	
	/** 方法描述：领导日程 领导列表
	 * @return List<Department>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日下午1:32:15
	 * @see
	 */
	@RequestMapping(value="queryForLeadList4M.action")
	@ResponseBody
	//@ActionLog(operateModelNm="移动端领导日程领导列表获取",operateFuncNm="queryForLeadList4M",operateDescribe="移动端领导日程领导列表获取")
	public Map<String, Object> queryForLeadList4M(String userId, HttpSession httpSession) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(userId)){
			User user=new User();
			user.setIsLeader(Constants.PO_DIARY_IS_LEADER);//是领导
			user.setOpenCale(Constants.PO_DIARY_OPEN_CALE);//日程公开
			user.setOrgId(SystemSecurityUtils.getUser().getOrgId());
			List<User> ul=commonService.getLeaderUserByDeptId(user);
			List<Leader4M> alist=new ArrayList<Leader4M>();
			if(!ul.isEmpty()){
				for(User u:ul){
					//移动端封装数据的bean
					Leader4M vo=new Leader4M();
					//web端bean转化为移动端bean，传输数据量更小
					BeanUtils.copyProperties(u, vo);// (源，目标)
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
	
	/** 方法描述：领导给特定用户数据
	 * @return List<Department>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日下午1:32:15
	 * @see
	 */
	@RequestMapping(value="queryForShareByLeader4M.action")
	@ResponseBody
	//@ActionLog(operateModelNm="移动端获取领导给特定用户的日程",operateFuncNm="queryForShareByLeader4M",operateDescribe="移动端获取领导给特定用户的日程")
	public Map<String, Object> queryForShareByLeader4M(String userId, String leaderId,String date ,String curPage, HttpSession httpSession) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(leaderId)
				&&StringUtils.isNotEmpty(date)&&StringUtils.isNotEmpty(curPage)){
			List<Diary> list= diaryService.queryForListByLeaderId4M(date,leaderId,curPage);
			List<DiaryByLeaderShare4M> alist=new ArrayList<DiaryByLeaderShare4M>();
			if(list!=null){
				for(Diary i : list){
					//移动端封装数据的bean
					DiaryByLeaderShare4M vo=new DiaryByLeaderShare4M();
					//web端bean转化为移动端bean，传输数据量更小
					BeanUtils.copyProperties(i, vo);// (源，目标)
					vo.setDisplayName(i.getPossessorIdValue());
					vo.setStarttime(DateUtils.formatDate(i.getStarttime(),"yyyy-MM-dd HH:mm:ss"));
					vo.setEndtime(DateUtils.formatDate(i.getEndtime(),"yyyy-MM-dd HH:mm:ss"));
					vo.setLeaderId(leaderId);
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
}
