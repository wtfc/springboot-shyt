<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	



%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <base href="<%=basePath%>">
    
    <title>报销费用明细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/bootstrap-combined.min.css"><link rel='stylesheet' type='text/css' href='<%=basePath%>css/validate.css'>
<link rel='stylesheet' type='text/css' href='<%=basePath%>css/flexigrid.css'>


	<script src="<%=basePath%>js/lib/jquery/jquery-1.8.3.js"></script>
<script src="<%=basePath%>js/lib/bootstrap/bootstrap.min.js"></script>
<script src="<%=basePath%>js/lib/common/common.js"></script>
<script src="<%=basePath%>js/com/finance/reimbursementGrade/reimbursementGradeForm.js"></script>
<script src='<%=basePath%>js/lib/jquery/jquery.formFill.js'></script>
<script src='<%=basePath%>js/lib/jquery-validation/jquery.validate.js'></script>
<script src='<%=basePath%>js/lib/jquery-validation/jquery.metadata.js'></script>
<script src='<%=basePath%>js/com/finance/reimbursementGrade/reimbursementGradeForm.validate.js'></script>
<script src='<%=basePath%>js/lib/flexigrid/flexigrid.js'></script>

  </head>
  
  <body>
    <div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div id="form" method="FormMethod">
<div id="reimbursementGradeFormDiv" class="form" tabindex="-1"  aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	   <h3 id="myModalLabel">报销费用明细</h3>
	</div>
	<form class="form-horizontal" id="reimbursementGradeForm">
	<input type="hidden" id="id" name = "id"  >
		  <div class="control-group">
		     <label class="control-label" for="mainType">主表类型</label>
		     <div class="controls ">
		       <input type="text" id="mainType" name = "mainType" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="mainId">主表id</label>
		     <div class="controls ">
		       <input type="text" id="mainId" name = "mainId" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="subject">科目</label>
		     <div class="controls ">
		       <input type="text" id="subject" name = "subject" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="money">金额</label>
		     <div class="controls ">
		       <input type="text" id="money" name = "money" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="date">具体时间</label>
		     <div class="controls ">
		       <input type="text" id="date" name = "date" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="discribe">费用描述</label>
		     <div class="controls ">
		       <input type="text" id="discribe" name = "discribe" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="remark">备注</label>
		     <div class="controls ">
		       <input type="text" id="remark" name = "remark" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="weight">安全系数</label>
		     <div class="controls ">
		       <input type="text" id="weight" name = "weight" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="queue">排序号</label>
		     <div class="controls ">
		       <input type="text" id="queue" name = "queue" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="deleteFlag">删除标记
            0 未删
            1 已删除</label>
		     <div class="controls ">
		       <input type="text" id="deleteFlag" name = "deleteFlag" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="createUser">创建人id</label>
		     <div class="controls ">
		       <input type="text" id="createUser" name = "createUser" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="createUserDept">创建人所在部门id</label>
		     <div class="controls ">
		       <input type="text" id="createUserDept" name = "createUserDept" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="createUserOrg">创建人所在机构id</label>
		     <div class="controls ">
		       <input type="text" id="createUserOrg" name = "createUserOrg" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="createDate">创建时间</label>
		     <div class="controls ">
		       <input type="text" id="createDate" name = "createDate" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="modifyUser">修改人id</label>
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
		     <label class="control-label" for="extStr1">预留</label>
		     <div class="controls ">
		       <input type="text" id="extStr1" name = "extStr1" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extStr2">预留</label>
		     <div class="controls ">
		       <input type="text" id="extStr2" name = "extStr2" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extStr3">预留</label>
		     <div class="controls ">
		       <input type="text" id="extStr3" name = "extStr3" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extStr4">预留</label>
		     <div class="controls ">
		       <input type="text" id="extStr4" name = "extStr4" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="extStr5">预留</label>
		     <div class="controls ">
		       <input type="text" id="extStr5" name = "extStr5" >
		     </div>
		  </div>
	</form> 
	<div class="modal-footer">
	     
	     <button id="ReimbursementGradeClear" class="btn">清除</button>
	    <button id="ReimbursementGradeSubmit" class="btn btn-primary">保存</button>
  </div>
</div>

</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<table id="manageListTable" method="ManageList" style="display: none; "></table>
		</div>
	</div>
</div>
  </body>
</html>
