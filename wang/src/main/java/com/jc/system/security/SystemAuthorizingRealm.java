package com.jc.system.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.Encodes;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.impl.DicManagerImpl;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.Menu;
import com.jc.system.security.domain.User;
import com.jc.system.security.exception.UserDisabledException;
import com.jc.system.security.exception.UserIpException;
import com.jc.system.security.exception.UserLockedException;
import com.jc.system.security.exception.UserPasswordException;
import com.jc.system.security.service.IMenuService;
import com.jc.system.security.service.ISystemService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.SettingUtils;
/**
 * 
 * @title GOA V2.0
 * @description 
 * @version  2014年5月8日
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private ISystemService systemService;
	private IUserService userService;
	private IMenuService menuService;
	
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		try{
			UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
			User user = getSystemService().get(token.getUsername());
			if (user != null) {
				//判断并发人数
//				if(VerificationSession.getVerSession() != null){
//					List licenseList = VerificationSession.getVerSession();
//					if(licenseList.size() > 0){
//						UKBean ukbean = (UKBean) licenseList.get(0);
//						if(SystemSecurityUtils.getOnlineCountForLicense(token.getHost()) >= Integer.parseInt(ukbean.getConcurrent())){
//							throw new OnlineCountException();
//						}
//					}
//				}
				//判断锁定
				if(user.getStatus().equals(GlobalContext.USER_STATUS_1)){
					throw new UserDisabledException();
				}  else if(user.getStatus().equals(GlobalContext.USER_STATUS_2)){
					//管理员锁定
					if(user.getLockType() == null || user.getLockType() == 1){
						throw new UserLockedException();
					} else {
						//密码错误次数锁定
						int minute = Integer.parseInt(SettingUtils.getSetting(SettingUtils.LOCK_TIME).toString());
						if(DateUtils.subtractionMinute(user.getLockStartDate(), DateUtils.getSysDate()) < minute){
							throw new UserLockedException();
						} else {
							User u = new User();
							u.setStatus(GlobalContext.USER_STATUS_0);
							u.setId(user.getId());
							getUserService().update2(u);
						}
					}
				}
				//判断IP绑定
				if(SettingUtils.getSetting(SettingUtils.USER_IP_BANDING).toString().equals("1")){
					if(!SystemSecurityUtils.determineIp(user.getId(), token.getHost())){
						throw new UserIpException();
					}
				}
				//判断登录密码
				if(!SystemSecurityUtils.validatePassword(new String(token.getPassword()), user.getPassword())){
					int count = SystemSecurityUtils.passwordError(user.getId());
					throw new UserPasswordException(String.valueOf(count));
				}
				//踢出用户
				if(SettingUtils.getSetting(SettingUtils.LOGIN_TYPE).equals("0")){
					SystemSecurityUtils.kickOutUser(user.getLoginName());
				}
				//添加机构ID
				Department department = new Department();
				department.setId(user.getDeptId());
				department = systemService.queryOrgIdAndName(department);
				user.setOrgId(department.getOrgId());
				
				try{
					byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
					Principal principal = new Principal(user);
					principal.setIp(token.getHost());
					return new SimpleAuthenticationInfo(principal, user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
				} catch(Exception e){
					e.printStackTrace();
					throw new AuthenticationException(e);
				}
				
			} else {
				return null;
			}
		} catch (Exception e) {
			if(e instanceof AuthenticationException) {
				throw e;
			} else {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		List<Menu> menuList = null;
		if(SystemSecurityUtils.getUser().getIsSystemAdmin()){
			try {
				Menu menu = new Menu();
				menu.setDeleteFlag(0);
				menuList = getMenuService().queryAll(menu);
			} catch (CustomException e) {
				e.printStackTrace();
			}
		} else {
			menuList = SystemSecurityUtils.getMenuList();
		}
		if (menuList != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (Menu menu : menuList) {
				if (menu != null && StringUtils.isNotEmpty(menu.getPermission())) {
					// 添加基于Permission的权限信息
					info.addStringPermission(menu.getPermission());
				}
			}
			return info;
		} else {
			return null;
		}
	}

	/**
	 * 清空用户关联权限认证，待下次使用时重新加载
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清空所有关联认证
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
		matcher.setHashIterations(HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
	private static final String OR_OPERATOR = " or ";
    private static final String AND_OPERATOR = " and ";
    private static final String NOT_OPERATOR = "not ";

    /**
     * 支持or and not 关键词  不支持and or混用
     *
     * @param principals
     * @param permission
     * @return
     */
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (permission.contains(OR_OPERATOR)) {
            String[] permissions = permission.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (isPermittedWithNotOperator(principals, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (permission.contains(AND_OPERATOR)) {
            String[] permissions = permission.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return isPermittedWithNotOperator(principals, permission);
        }
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission.startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }

	/**
	 * 获取系统业务对象
	 */
	public ISystemService getSystemService() {
		if (systemService == null) {
			systemService = SpringContextHolder.getBean(ISystemService.class);
		}
		return systemService;
	}
	
	/**
	 * 获取系统业务对象
	 */
	public IUserService getUserService() {
		if (userService == null) {
			userService = SpringContextHolder.getBean(IUserService.class);
		}
		return userService;
	}
	
	/**
	 * 获取系统业务对象
	 */
	public IMenuService getMenuService() {
		if (menuService == null) {
			menuService = SpringContextHolder.getBean(IMenuService.class);
		}
		return menuService;
	}
	
	
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private Long id; /* 用户ID */
		private String displayName; /* 用户显示名 */
		private String loginName; /* 用户登录名 */
		private String dutyId; /*职务*/
		private Integer loginState = 0;/*记录登录状态*/
		private String photo;/*用户头像*/
		private String level;/*用户级别*/
		private int modifyPwdFlag;/*是否修改过密码*/
		private Long orgId;/*机构ID*/
		private String ip;
		private String theme;
		private Map<String, Object> cacheMap;
		
		public Principal(User user) {
			this.id = user.getId();
			this.loginName = user.getLoginName();
			this.displayName = user.getDisplayName();
			this.dutyId = user.getDutyId();
			this.photo = user.getPhoto();
			this.level = user.getLevel();
			this.modifyPwdFlag = user.getModifyPwdFlag();
			this.orgId = user.getOrgId();
			this.theme = user.getTheme();
		}

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		public String getDutyId() {
			return dutyId;
		}

		public void setDutyId(String dutyId) {
			this.dutyId = dutyId;
		}
		
		public String getDutyIdValue() {
			IDicManager dicManager = new DicManagerImpl();
			Dic dic = dicManager.getDic("dutyId", this.getDutyId());
			if(dic == null){
				return "";
			} else {
				return " - "+dic.getValue();
			}
		}

		public Map<String, Object> getCacheMap() {
			if (cacheMap==null){
				cacheMap = new HashMap<String, Object>();
			}
			return cacheMap;
		}

		public Integer getLoginState() {
			return loginState;
		}

		public void setLoginState(Integer loginState) {
			this.loginState = loginState;
		}

		public String getPhoto() {
			if(StringUtils.isEmpty(photo)){
				photo = "images/demoimg/userPhoto.png";
			}
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public int getModifyPwdFlag() {
			return modifyPwdFlag;
		}

		public void setModifyPwdFlag(int modifyPwdFlag) {
			this.modifyPwdFlag = modifyPwdFlag;
		}

		public Long getOrgId() {
			return orgId;
		}

		public void setOrgId(Long orgId) {
			this.orgId = orgId;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getTheme() {
			return theme;
		}

		public void setTheme(String theme) {
			this.theme = theme;
		}
		
	}
}
