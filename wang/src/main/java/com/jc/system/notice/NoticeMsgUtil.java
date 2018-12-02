package com.jc.system.notice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.common.util.StringUtil;
import com.jc.system.notice.domain.NoticeMsg;
import com.jc.system.notice.service.INoticeMsgService;
import com.jc.system.notice.service.impl.NoticeMsgServiceImpl;
import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.ISysUserRoleService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.service.impl.SysUserRoleServiceImpl;
import com.jc.system.security.service.impl.UserServiceImpl;

/**
 * @title GOA V2.0
 * @description 消息提醒工具类
 * @version  2014年6月9日
 */
public class NoticeMsgUtil {
	
	private static transient final Logger log = Logger.getLogger(NoticeMsgUtil.class);
	
	private static INoticeMsgService noticeMsgService = SpringContextHolder.getBean(NoticeMsgServiceImpl.class);
	private static IUserService userService = SpringContextHolder.getBean(UserServiceImpl.class);
	private static ISysUserRoleService userRoleService = SpringContextHolder.getBean(SysUserRoleServiceImpl.class);
	
	
	/**
	  * 发起消息提醒
	  * @param noticeMsg 实体类
	  * @return 插入的数据条数
	  * @throws CustomException
	  * @return 1 成功 0 失败
	  * @version 1.0 2014年6月9日 下午1:59:49
	*/
	public static Integer notice(NoticeMsg noticeMsg){
		Integer result = 1;
		noticeMsg.setShowFlag(0);
		noticeMsg.setReadFlag(0);
		try {
			noticeMsgService.save(noticeMsg);
		} catch (CustomException e) {
			log.error(e.getLogMsg());
			result = 0;
		}
		
		return result;
	}
	
	/**
	  * 发起消息提醒
	  * @param noticeMsg 实体类
	  * @return 插入的数据条数
	  * @throws CustomException
	  * @return 1 成功 0 失败
	  * @version 1.0 2014年6月9日 下午1:59:49
	*/
	public static Integer notice(NoticeMsg noticeMsg,String[] userIds){
		Integer result = 1;
		try {
			//对userIds进行去重复
			Set<String> userIdSet = new HashSet<String>();
			for(int i=0;i<userIds.length;i++){
				if(!StringUtil.isEmpty(userIds[i])){
					userIdSet.add(userIds[i]);
				}
			}
			List<NoticeMsg> noticeList = new ArrayList<NoticeMsg>();
			Iterator<String> userIter = userIdSet.iterator();
			while(userIter.hasNext()){
				NoticeMsg noticeMsgItem = noticeMsg.clone();
				noticeMsgItem.setReceiveUser(Long.parseLong(userIter.next()));
				noticeList.add(noticeMsgItem);
			}
			if(noticeList.size()>0){
				noticeMsgService.saveList(noticeList);
			}
		} catch (CustomException e) {
			log.error(e.getLogMsg());
			result = 0;
		}catch (Exception e) {
			log.error(e);
			result = 0;
		}
		return result;
	}
	
	/**
	  * 发起消息提醒(部门下所有人)
	  * @param noticeMsg 实体类
	  * @return 插入的数据条数
	  * @throws CustomException
	  * @return 1 成功 0 失败
	  * @version 1.0 2014年6月9日 下午1:59:49
	*/
	public static Integer noticeByDeptId(NoticeMsg noticeMsg,Long deptId){
		User user = new User();
		user.setDeptId(deptId);
		List<User> userList = userService.queryUserByDeptId(user);
		String usersStr = "";
		for(int i=0;i<userList.size();i++){
			usersStr +=userList.get(i).getId()+",";
		}
		if(usersStr.length()>0){
			usersStr = usersStr.substring(0,usersStr.length()-1);
			return NoticeMsgUtil.notice(noticeMsg, usersStr.split(","));
		}
		return 1;
	}
	
	/**
	  * 发起消息提醒(角色下所有人)
	  * @param noticeMsg 实体类
	  * @return 插入的数据条数
	  * @throws CustomException
	  * @return 1 成功 0 失败
	  * @author 孙圣然
	  * @version 1.0 2014年6月9日 下午1:59:49
	*/
	public static Integer noticeByRoleId(NoticeMsg noticeMsg,Long roleId){
		try {
			SysUserRole userRole = new SysUserRole();
			userRole.setRoleId(roleId);
			List<SysUserRole> userRoleList;
			userRoleList = userRoleService.queryAll(userRole);
			String usersStr = "";
			for(int i=0;i<userRoleList.size();i++){
				usersStr +=userRoleList.get(i).getUserId()+",";
			}
			if(usersStr.length()>0){
				usersStr = usersStr.substring(0,usersStr.length()-1);
				return NoticeMsgUtil.notice(noticeMsg, usersStr.split(","));
			}
		} catch (CustomException e) {
			log.error(e.getLogMsg());
			return 0;
		}
		return 1;
	}
	
	/**
	  * 删除消息提醒
	  * @param businessId 业务id
	  * @param businessFlag 业务标志
	  * @throws CustomException
	  * @version 1.0 2014年6月10日 上午9:12:59
	*/
	public static Integer delete(Long businessId,String businessFlag,Long receiveUser) throws CustomException{
		NoticeMsg noticeMsg = new NoticeMsg();
		noticeMsg.setBusinessId(businessId);
		noticeMsg.setBusinessFlag(businessFlag);
		noticeMsg.setReceiveUser(receiveUser);
		List<NoticeMsg> list = noticeMsgService.queryAll(noticeMsg);
		String ids = "";
		for(NoticeMsg item:list){
			ids += item.getId()+",";
		}
		int result = 0;
		if(ids.length()>0){
			ids = ids.substring(0,ids.length()-1);
			NoticeMsg temp = new NoticeMsg();
			temp.setPrimaryKeys(ids.split(","));
			result = noticeMsgService.delete(noticeMsg);
		}
		return result;
	}
}
