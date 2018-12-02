<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	



%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <base href="<%=basePath%>">
    
    <title>附件业务关系表</title>
    
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
<script src="<%=basePath%>js/com/sys/attachBusiness/attachBusinessAaa.js"></script>
<script src='<%=basePath%>js/lib/flexigrid/flexigrid.js'></script>
<script src='<%=basePath%>js/lib/jquery/jquery.formFill.js'></script>
<script src='<%=basePath%>js/lib/jquery-validation/jquery.validate.js'></script>
<script src='<%=basePath%>js/lib/jquery-validation/jquery.metadata.js'></script>
<script src='<%=basePath%>js/com/sys/attachBusiness/attachBusinessAaa.validate.js'></script>

  </head>
  
  <body>
    <div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<table id="manageListTable" method="ManageList" style="display: none;"></table>
			<div id="form" method="FormMethod">
<div id="attachBusinessFormDiv" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	   <h3 id="myModalLabel">附件业务关系表</h3>
	</div>
	<form class="form-horizontal" id="attachBusinessForm">
	<input type="hidden" id="id" name = "id"  >
		  <div class="control-group">
		     <label class="control-label" for="attachId">附件ID</label>
		     <div class="controls ">
		       <input type="text" id="attachId" name = "attachId" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="businessId">业务ID</label>
		     <div class="controls ">
		       <input type="text" id="businessId" name = "businessId" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="businessTable">业务表名</label>
		     <div class="controls ">
		       <input type="text" id="businessTable" name = "businessTable" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="businessSource">业务来源  0-OA  1-CRM</label>
		     <div class="controls ">
		       <input type="text" id="businessSource" name = "businessSource" >
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
	     
	    <button id="AttachBusinessSubmit" class="btn btn-primary">保存</button>
  </div>
</div>

</div>
		</div>
	</div>
</div>
  </body>
</html>
