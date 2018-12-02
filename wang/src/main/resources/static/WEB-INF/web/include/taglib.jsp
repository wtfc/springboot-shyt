<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld"%>
<%@ taglib prefix="c" uri="c"%>
<%@ taglib prefix="fn" uri="fn" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="dic" uri="/WEB-INF/tlds/dic-tags.tld" %>
<%@ taglib prefix="workflow" uri="/WEB-INF/tlds/workflow.tld" %>
<%@ taglib prefix="common" uri="/WEB-INF/tlds/common.tld" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.SecurityUtils"%>
<c:set var="systemState" value="${initParam.systemState}"/>
<c:set var="sysPath" value="${pageContext.request.contextPath}"/>
<c:set var="theme" value="${sessionScope.theme}"/>
<c:if test="${sysPath=='/'}">
	<c:set var="sysPath" value="" />
</c:if>