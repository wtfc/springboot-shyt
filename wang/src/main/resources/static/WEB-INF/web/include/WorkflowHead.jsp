<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/datatable/jquery.jeditable.js" type="text/javascript"></script>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<script type='text/javascript' src='${sysPath}/js/lib/common/phrase.js'></script>
<script type='text/javascript' src='${sysPath}/js/com/signature/handwritten.js'></script>
<%-- 	<input type="hidden" id="signType" value="${workflowParam.signType}"> --%>
	<input type="hidden" id="signType" value="0">
<%@ include file="/plugin/websign.jsp"%>
  	<input type="hidden" name="scanType" id="scanType" value="${workflowParam.scanType}"/>
  	<input type="hidden" name="dataId" id="dataId" value="${workflowParam.formProperty.dataId}"/>
  	<input type="hidden" name="usePerson" id="usePerson" value="${workflowParam.usePerson}"/>
  	<input type="hidden" name="editField" id="editField"  value="${workflowParam.formProperty.editFields}"/>
  	<input type="hidden" name="readField" id="readField"  value="${workflowParam.formProperty.readFields}"/>
  	<input type="hidden" name="hideField" id="hideField"  value="${workflowParam.formProperty.hideFields}"/>
  	<input type="hidden" name="entrance" id="entrance" value="${workflowParam.entrance}"/>
  	<input type="hidden" id="suggestType" value="${workflowParam.suggestType}">
  	
  	<input type="hidden" name="postscriptDataId" id="postscriptDataId" value="">
  	<input type="hidden" name="signInfoFlag" id="signInfoFlag" value="false">
  	<input type="hidden" name="flowStatus" id="flowStatus" value="${workflowParam.workFlowBean.flowStatus}"/>
  	<input type="hidden" name="signInfoOld" id="signInfoOld" value="${workflowParam.signInfo}">
  	
  	<input workFlowType="true" type="hidden" name="workId" id="workId" value="${workflowParam.workId}"/>
  	<input workFlowType="true" type="hidden" name="curNodeId" id="curNodeId" value="${workflowParam.workFlowBean.curNodeId}">
  	<input workFlowType="true" type="hidden" name="flowId" id="flowId" value="${workflowParam.workFlowBean.processDefinitionKey}">
  	<input workFlowType="true" type="hidden" name="flowName" id="flowName" value="${workflowParam.workFlowBean.flowName}">
  	<input workFlowType="true" type="hidden" name="curNodeName" id="curNodeName" value="${workflowParam.workFlowBean.curNodeName}">
    <input workFlowType="true" type="hidden" name="curTrackId" id="curTrackId" value="${workflowParam.workFlowBean.curTrackId}">
  	<input workFlowType="true" type="hidden" name="formId" id="formId" value="${workflowParam.formProperty.id_}">
    <input workFlowType="true" type="hidden" name="tableName" id="tableName" value="${workflowParam.formProperty.tableName}">
  	<input workFlowType="true" type="hidden" name="workFlowType" id="workFlowType" value="${workflowParam.workFlowType}">
  	<input workFlowType="true" type="hidden" name="confirmType" id="confirmType">
  	<input workFlowType="true" type="hidden" name="confirmRoute" id="confirmRoute">
  	<input workFlowType="true" type="hidden" name="confirmUser" id="confirmUser">
  	
  	<input workFlowType="true" type="hidden" name="signInfo" id="signInfo">
  	<input workFlowType="true" type="hidden" name="message" id="message">
  	<input workFlowType="true" type="hidden" name="suggestId" id="suggestId">
  	<input workFlowType="true" type="hidden" name="signContainerId" id="signContainerId">
  	<input workFlowType="true" type="hidden" name="signId" id="signId">
  	<input workFlowType="true" type="hidden"  id="signIds" name="signIds">
  	<input workFlowType="true" type="hidden" name="postscriptData" id="postscriptData" >
  	<input workFlowType="true" type="hidden" name="postscriptReplyData" id="postscriptReplyData" >
  	<input workFlowType="true" type="hidden" name="supervisionId" id="supervisionId" value="${not empty workflowParam.supervisor?workflowParam.supervisor.id:""}">
  	<input workFlowType="true" type="hidden" name="supervisionUser" id="supervisionUser" >
  	<input workFlowType="true" type="hidden" name="supervisionContent" id="supervisionContent" >
  	<input workFlowType="true" type="hidden" name="supervisorDeleteFlag" id="supervisorDeleteFlag" >