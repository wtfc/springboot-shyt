<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	



%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <base href="<%=basePath%>">
    
    <title>邮件记录表，记录每条收、发的邮件基本信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/bootstrap-combined.min.css"><link rel='stylesheet' type='text/css' href='<%=basePath%>css/flexigrid.css'>
<link rel='stylesheet' type='text/css' href='<%=basePath%>css/validate.css'>


	<script src="<%=basePath%>js/lib/jquery/jquery-1.8.3.js"></script>
<script src="<%=basePath%>js/lib/bootstrap/bootstrap.min.js"></script>
<script src="<%=basePath%>js/lib/common/common.js"></script>
<script src="<%=basePath%>js/com/ic/mailRecord/mailRecordInteract.js"></script>
<script src='<%=basePath%>js/lib/flexigrid/flexigrid.js'></script>
<script src='<%=basePath%>js/lib/jquery/jquery.formFill.js'></script>
<script src='<%=basePath%>js/lib/jquery-validation/jquery.validate.js'></script>
<script src='<%=basePath%>js/lib/jquery-validation/jquery.metadata.js'></script>
<script src='<%=basePath%>js/com/ic/mailRecord/mailRecordInteract.validate.js'></script>

  </head>
  
  <body>
    <div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<table id="manageListTable" method="ManageList" class="table-condensed" style="display: none;"></table>
			<div id="form" method="FormMethod">
<div id="mailRecordFormDiv" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	   <h3 id="myModalLabel">邮件记录表，记录每条收、发的邮件基本信息</h3>
	</div>
	<form class="form-horizontal" id="mailRecordForm">
	<input type="hidden" id="id" name = "id"  >
		  <div class="control-group">
		     <label class="control-label" for="mailId">邮件记录表主键</label>
		     <div class="controls ">
		       <input type="text" id="mailId" name = "mailId" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="receiveUserId">邮件的接收人，包括正常收件人、抄送接收人、密送接收人</label>
		     <div class="controls ">
		       <input type="text" id="receiveUserId" name = "receiveUserId" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="receiveMail">收件人(内部用户ID，外部邮箱地址)</label>
		     <div class="controls ">
		       <input type="text" id="receiveMail" name = "receiveMail" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="receiveType">接收人类别:0 正常收件人 1 抄送接收人 2 密送接收人</label>
		     <div class="controls ">
		       <input type="text" id="receiveType" name = "receiveType" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="deleteFlag">删除标记 1 删除 0 未删除</label>
		     <div class="controls ">
		       <input type="text" id="deleteFlag" name = "deleteFlag" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="deleteUser">删除人ID</label>
		     <div class="controls ">
		       <input type="text" id="deleteUser" name = "deleteUser" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="deleteDate">删除时间</label>
		     <div class="controls ">
		       <input type="text" id="deleteDate" name = "deleteDate" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="createUser">创建人员ID</label>
		     <div class="controls ">
		       <input type="text" id="createUser" name = "createUser" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="createUserDept">记录所属部门</label>
		     <div class="controls ">
		       <input type="text" id="createUserDept" name = "createUserDept" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="createUserOrg">记录所属机构</label>
		     <div class="controls ">
		       <input type="text" id="createUserOrg" name = "createUserOrg" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="createDate">记录插入数据库的时间</label>
		     <div class="controls ">
		       <input type="text" id="createDate" name = "createDate" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="modifyUser">修改人ID</label>
		     <div class="controls ">
		       <input type="text" id="modifyUser" name = "modifyUser" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="modifyDate">修改时间</label>
		     <div class="controls ">
		       <input type="text" id="modifyDate" name = "modifyDate" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extDate1">预留时间字段1</label>
		     <div class="controls ">
		       <input type="text" id="extDate1" name = "extDate1" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extDate2">预留时间字段2</label>
		     <div class="controls ">
		       <input type="text" id="extDate2" name = "extDate2" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extNum1">预留数字字段1</label>
		     <div class="controls ">
		       <input type="text" id="extNum1" name = "extNum1" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extNum2">预留数字字段2</label>
		     <div class="controls ">
		       <input type="text" id="extNum2" name = "extNum2" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extNum3">预留数字字段3</label>
		     <div class="controls ">
		       <input type="text" id="extNum3" name = "extNum3" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extStr1">预留字符字段1</label>
		     <div class="controls ">
		       <input type="text" id="extStr1" name = "extStr1" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extStr2">预留字符字段2</label>
		     <div class="controls ">
		       <input type="text" id="extStr2" name = "extStr2" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extStr3">预留字符字段3</label>
		     <div class="controls ">
		       <input type="text" id="extStr3" name = "extStr3" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extStr4">预留字符字段4</label>
		     <div class="controls ">
		       <input type="text" id="extStr4" name = "extStr4" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extStr5">预留字符字段5</label>
		     <div class="controls ">
		       <input type="text" id="extStr5" name = "extStr5" >
		     </div>
		  </div>
	</form> 
	<div class="modal-footer">
	     <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	     
	    <button id="MailRecordSubmit" class="btn btn-primary">保存</button>
  </div>
</div>

</div>
		</div>
	</div>
</div>
  </body>
</html>
