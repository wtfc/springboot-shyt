package com.jc.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import com.google.common.collect.Maps;
import com.jc.system.CustomException;
import com.jc.system.common.util.CacheUtils;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.Digests;
import com.jc.system.common.util.Encodes;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.common.util.Utils;
import com.jc.system.security.SystemAuthorizingRealm.Principal;
import com.jc.system.security.beans.UserBean;
import com.jc.system.security.dao.ISystemDao;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.Menu;
import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.domain.User;
import com.jc.system.security.domain.UserIp;
import com.jc.system.security.service.ISysUserRoleService;
import com.jc.system.security.service.IUserIpService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.SettingUtils;

/**
 * 
 * @title GOA V2.0
 * @version 2014年5月9日
 */
public class SystemSecurityUtils {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	private static final String CACHE_USER = "user";

	private static ISystemDao systemDao = SpringContextHolder.getBean(ISystemDao.class);
	private static IUserService userService = SpringContextHolder.getBean(IUserService.class);
	private static IUserIpService userIpService = SpringContextHolder.getBean(IUserIpService.class);
	private static ISysUserRoleService userRoleService = SpringContextHolder.getBean(ISysUserRoleService.class);
	//shiro bean
	private static SessionDAO sessionDAO = SpringContextHolder.getBean(SessionDAO.class);
	//private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	
	public static final String CACHE_USER_INFO = "user_info_";
	public static final String CACHE_DEPT_INFO = "dept_info_";
	
	/**
	 * 获取用户信息
	 */
	public static User getUser() {
		try{
			User user = (User)getCache(CACHE_USER);
			if(user == null){
				Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
				if (principal != null) {
					user = userService.getUser(principal.getId());
					try {
						SysUserRole userRole = new SysUserRole();
						userRole.setUserId(user.getId());
						user.setSysUserRole(userRoleService.queryAll(userRole));
					} catch (CustomException e) {
						e.printStackTrace();
					}
					putCache(CACHE_USER, user);
				}
			}
			return user;
		} catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 修改密码后设置登陆时间
	 */
	public static void setFirstLoginState(){
		Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
		if (principal != null) {
			principal.setModifyPwdFlag(1);
		}
	}
	
	/**
	 * 判断是否是第一次登陆
	 */
	public static boolean isFirstLogin() {
		Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
		if (principal != null) {
			if(principal.getModifyPwdFlag() == 0){
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 获取菜单列表
	 */
	public static List<Menu> getMenuList() {
		List<Menu> menuList = null;
		User user = getUser();
		if (user != null) {
			menuList = systemDao.queryMenu(String.valueOf(user.getId()));
			return menuList;
		}
		return null;
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt,
				HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获取在线人数过滤机构
	 */
	public static Integer getOnlineCount() {
		List<Principal> list = new ArrayList<Principal>();
		
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		if(sessions == null){
			return 0;
		} else {
			User user = getUser();
			int sessionCount = 0;
			for(Session session : sessions){
				PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				if(principalCollection != null){
					Principal principal = (Principal) principalCollection.getPrimaryPrincipal();
					if(user.getOrgId().equals(principal.getOrgId())){
						if(!isExist(principal, list)){
							list.add(principal);
							sessionCount ++ ;
						}
					}
				}
			}
			return sessionCount;
		}
	}
	
	public static boolean isExist(Principal principal, List<Principal> list){
		for(Principal p : list){
			if(p.getLoginName().equals(principal.getLoginName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取在线人数判断license时调用
	 */
	public static Integer getOnlineCountForLicense(String ip) {
		List<String> ipList = new ArrayList<String>();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		if(sessions == null){
			return 0;
		} else {
			int sessionCount = 0;
			for(Session session : sessions){
				PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				if(principalCollection != null){
					Principal principal = (Principal) principalCollection.getPrimaryPrincipal();
					if(!isIpExist(principal.getIp(), ipList)){
						ipList.add(principal.getIp());
						sessionCount ++ ;
					}
				}
			}
			return sessionCount;
		}
	}
	
	public static boolean isIpExist(String ip, List<String> list){
		for(String str : list){
			if(str.equals(ip)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取在线用户登录名
	 */
	public static List<Principal> getOnlineUsers(){
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		List<Principal> principalList = new ArrayList<Principal>();
		User user = getUser();
		for(Session session : sessions){
			PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(principalCollection != null){
				Principal principal = (Principal) principalCollection.getPrimaryPrincipal();
				if(user.getOrgId().equals(principal.getOrgId())){
					principalList.add(principal);
				}
			}
		}
		return principalList;
	}
	
	/**
	 * 获取在线用户--人员控件显示用--返回userBean
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年11月10日 上午10:18:52
	 */
	public static List<UserBean> getOnlineUserBean(){
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		List<UserBean> userBeanList = new ArrayList<UserBean>();
		User user = getUser();
		for(Session session : sessions){
			PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(principalCollection != null){
				Principal principal = (Principal) principalCollection.getPrimaryPrincipal();
				if(user.getOrgId().equals(principal.getOrgId())){
					UserBean uBean = new UserBean();
					uBean.setDisplayName(principal.getDisplayName());
					userBeanList.add(uBean);
				}
			}
		}
		removeDuplicateWithOrder(userBeanList);
		return userBeanList;
	}
	
	/**
	 * 去除集合中的重复数据
	 * @param list
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年10月30日 上午11:39:20
	 */
	private static void removeDuplicateWithOrder(List<UserBean> list) {
		Set<Long> set = new HashSet<Long>();
        List<UserBean> newList = new ArrayList<UserBean>();
        for (Iterator<UserBean> iter = list.iterator(); iter.hasNext();) {
        	UserBean element = iter.next();
            if (set.add(element.getId())){
            	newList.add(element);
            }
        } 
        list.clear();
        list.addAll(newList);
   }
	
	public static List<Session> getSessionByUsername(String username){
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        List<Session> sessionList = new ArrayList<Session>();
        for(Session session : sessions){
			PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(principalCollection != null){
				Principal principal = (Principal) principalCollection.getPrimaryPrincipal();
			    if(username.equals(principal.getLoginName())){
			    	sessionList.add(session);
			    }
			}
		}
        return sessionList;  
    }  
	
	public static void kickOutUser(String username){
		List<Session> sessionList = getSessionByUsername(username);
		for(Session session : sessionList){
			session.setAttribute("kickout", true);
		}
        //session.stop();
        //Cache<Object, Object> cache = cacheManager.getCache("shiro-activeSessionCache");
    	//cache.remove(session.getId());
    }
	
	public static void logout4M(Serializable sessionId){
		Collection<Session> sessions = sessionDAO.getActiveSessions();
        for(Session session : sessions){
        	if(session.getId() == sessionId){
        		session.stop();
        		break;
        	}
        }
	}
	
	
	/**
	 * 判断用户是否是登录状态
	 */
	public static Map<String, Object> loginState(String ids){
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> onLineList = new ArrayList<String>();
		List<String> noOnLineList = new ArrayList<String>();
		
		if(StringUtils.isNotEmpty(ids)){
			String[] id = ids.split(",");
			List<Principal> principalList =  getOnlineUsers();
			for(String id_ : id){
				boolean state = true;
				for(Principal principal : principalList){
					if(principal.getId().toString().equals(id_)){
						state = false;
						onLineList.add(principal.getDisplayName());
						break;
					}
				}
				if(state){
					noOnLineList.add(id_);
				}
			}
		}
		
		StringBuffer onLineSb = new StringBuffer();
		for(int i=0;i<onLineList.size();i++){
			onLineSb.append(onLineList.get(i));
			if(i != onLineList.size()-1)
				onLineSb.append(",");
		}
		result.put("onLine", onLineSb.toString());
		
		StringBuffer noOnlineSb = new StringBuffer();
		for(int i=0;i<noOnLineList.size();i++){
			noOnlineSb.append(noOnLineList.get(i));
			if(i != noOnLineList.size()-1)
				noOnlineSb.append(",");
		}
		result.put("noOnLine", noOnlineSb.toString());
		return result;
	}
	
	/**
	 * 判断登录IP
	 */
	public static boolean determineIp(Long userId, String host){
		try {
			UserIp userIp = new UserIp();
			userIp.setUserId(userId);
			List<UserIp> ipList = userIpService.queryAll(userIp);
			boolean state = false;
			if(ipList != null && ipList.size() > 0){
				for(UserIp ui : ipList){
					if(ui.getBindType() == 1){
						if(host.trim().equals(ui.getUserStartIp().trim())){
							state = true;
							break;
						}
					} else {
						if(Utils.ipExistsInRange(host, ui.getUserStartIp().trim() + "-" + ui.getUserEndIp())){
							state = true;
							break;
						}
					}
				}
			} else {
				state = true;
			}
			return state;
			
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 登录密码错误次数
	 */
	public static int passwordError(Long userId){
		int count = 0;
		try {
			User u = new User();
			u.setId(userId);
			u = userService.get(u);
			if(u != null){
				Date lockDate = u.getLockStartDate() == null ? new Date() : u.getLockStartDate();
				count = u.getWrongCount() == null ? 0 : u.getWrongCount();
				
				u = new User();
				u.setId(userId);
				u.setLockStartDate(DateUtils.getSysDate());
				if(DateUtils.subtractionDays(DateUtils.getSysDate(), lockDate) == 0){
					if(count >= Integer.parseInt(SettingUtils.getSetting(SettingUtils.MAX_ERROR_COUNT).toString()) -1 ){
						u.setLockType(0);
						u.setStatus(GlobalContext.USER_STATUS_2);
						u.setWrongCount(0);
						userService.update2(u);
						return count;
					} else{
						u.setWrongCount(count+1);
						userService.update2(u);
					}
				} else {
					u.setWrongCount(1);
					userService.update2(u);
				}
			}
			
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 记录登录时间
	 */
	public static void logInMes(){
		User user = getUser();
		if(user != null){
			User u = new User();
			u.setId(user.getId());
			u.setStatus(GlobalContext.USER_STATUS_0);
			u.setWrongCount(0);
			u.setLastLonginDate(DateUtils.getSysDate());
			userService.update2(u);
		}
	}
	
	/**
	 * 获取session
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 删除session中menu列表
	 */
	public static void removeSessionAttribute(final String name) {
		SystemSecurityUtils.getSession().removeAttribute(name);
	}

	/**
	 * 登出
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
		Object obj = getCacheMap().get(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
		getCacheMap().put(key, value);
	}

	public static void removeCache(String key) {
		getCacheMap().remove(key);
	}
	
	public static Map<String, Object> getCacheMap(){
		Map<String, Object> map = Maps.newHashMap();
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			return principal!=null?principal.getCacheMap():map;
		}catch (UnavailableSecurityManagerException e) {
			return map;
		}
	}
	
	/**
	 * 根据用户id集合拼装人员控件数据
	 * @param ids
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年11月14日 上午11:01:28
	 */
	public static List<Map<String, String>> getUsersByUserIdsToSelectControl(String ids){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if(ids != null && ids.length() > 0){
			String[] idArray = ids.split("\\,");
			for(String id : idArray){
				User u = (User) CacheUtils.get(CACHE_USER_INFO + id);
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", u.getId().toString());
				map.put("text", u.getDisplayName());
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 根据组织id集合拼装组织控件数据
	 * @param ids
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年11月14日 上午11:21:25
	 */
	public static List<Map<String, String>> getDeptByDeptIdsToSelectControl(String ids){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if(ids != null && ids.length() > 0){
			String[] idArray = ids.split("\\,");
			for(String id : idArray){
				Department d = (Department) CacheUtils.get(CACHE_DEPT_INFO + id);
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", d.getId().toString());
				map.put("text", d.getName());
				list.add(map);
			}
		}
		return list;
	}
}
