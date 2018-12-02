package com.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jc.system.security.SystemAuthorizingRealm;
import com.jc.system.security.filter.KickoutSessionControlFilter;
import com.jc.system.security.filter.LoginFormAuthenticationFilter;
import com.jc.system.security.filter.SessionValidateFilter;

@Configuration
public class ShiroConfiguration {

    //将自己的验证方式加入容器
    @Bean
    public SystemAuthorizingRealm myShiroRealm() {
    	SystemAuthorizingRealm myShiroRealm = new SystemAuthorizingRealm();
        return myShiroRealm;
    }
    
    
    @Bean
    public MemorySessionDAO getSessionDAO() {
    	MemorySessionDAO sessionDAO = new MemorySessionDAO();
    	return sessionDAO;
    }
    
    @Bean
    public SimpleCookie getSimpleCookie() {
    	SimpleCookie simpleCookie = new SimpleCookie("sid");//cookieName,不能为空
    	simpleCookie.setHttpOnly(true);
    	simpleCookie.setMaxAge(-1);
    	return simpleCookie;
    }
    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager() {
    	DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
    	defaultWebSessionManager.setSessionIdCookieEnabled(true);
    	defaultWebSessionManager.setSessionIdCookie(getSimpleCookie());
    	defaultWebSessionManager.setSessionDAO(getSessionDAO());
    	defaultWebSessionManager.setSessionValidationInterval(3600000);
    	defaultWebSessionManager.setGlobalSessionTimeout(3600000);
    	return defaultWebSessionManager;
    }
    
    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager getSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(getDefaultWebSessionManager());
        securityManager.setCacheManager(getCacheManagerShiro());//需要引用
        return securityManager;
    }
    
    @Bean
    public EhCacheManager getCacheManagerShiro() {
    	EhCacheManager ehCacheManager = new EhCacheManager();
    	ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
    	return ehCacheManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
        LinkedHashMap<String,Filter> filtersMap = new LinkedHashMap<String, Filter>();
        LinkedHashMap<String,String> filterChainMap = new LinkedHashMap<String, String>();
//        Properties properties = new Properties();
//        properties.load(inStream);

        filtersMap.put("sessionValidate",new SessionValidateFilter());
        filtersMap.put("kickout",new KickoutSessionControlFilter());
        filtersMap.put("authc",new LoginFormAuthenticationFilter());
        
        //拦截的
        /*/css*//** = anon <!-- 匿名使用 -->
        /js/** = anon 
        /img/** = anon 
        /images/** = anon
        /fonts/** = anon  
        /plugin/** = anon
        /install/** = anon
        /*.ico = anon
        /system/getRegFile.action = anon
        /sys/user/forgetPwd.action = anon
        /linceseMes.action = anon
        /system/i18n/** = anon
        /anon/** = anon
        <!--资产报备信息  -->
        /android/toaShytAsset/** =anon
        
        /android/system/login4M.action = anon
        /loginForDesktop = anon  <!--桌面精灵使用 -->
        /content/attach/download.action=anon
        /login = authc <!--shiro使用 表示需要认证 -->
        /logout = anon <!--shiro使用 退出登录 -->
   		/** = user <!--shiro使用 表示必须存在用户-->
   		/** = sessionValidate,kickout
   		 */
        filterChainMap.put("/login","authc");//shiro使用 表示需要认证
        filterChainMap.put("/logout","anon");//登出，shiro使用 退出登录
        filterChainMap.put("/**","user,sessionValidate,kickout");//匿名使用
//        filterChainMap.put("/**","sessionValidate,kickout");//匿名使用
        filterChainMap.put("/loginForDesktop","anon");//桌面精灵使用
        filterChainMap.put("/content/attach/download.action","anon");//下载附件
        filterChainMap.put("/android/system/login4M.action","anon");//安卓登录
        
        filterChainMap.put("/css/**","anon");//
        filterChainMap.put("/js/**","anon");//
        filterChainMap.put("/img/**","anon");//
        filterChainMap.put("/images/**","anon");//
        filterChainMap.put("/fonts/**","anon");//
        filterChainMap.put("/plugin/**","anon");//
        filterChainMap.put("/install/**","anon");//
        filterChainMap.put("/*.ico","anon");//
        filterChainMap.put("/system/getRegFile.action","anon");//
        filterChainMap.put("/sys/user/forgetPwd.action","anon");//
        filterChainMap.put("/linceseMes.action","anon");//
        filterChainMap.put("/system/i18n/**","anon");//
        filterChainMap.put("/anon/**","anon");//
        filterChainMap.put("/android/toaShytAsset/**","anon");//资产报备信息
        //对所有用户认证
//        filterChainMap.put("/**","authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setFilters(filtersMap);
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
