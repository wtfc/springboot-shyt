<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	



%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <base href="<%=basePath%>">
    
    <title>联系人与分组中间表</title>
    
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
<script src="<%=basePath%>js/com/ic/conGroupR/conGroupRInteract.js"></script>
<script src='<%=basePath%>js/lib/flexigrid/flexigrid.js'></script>
<script src='<%=basePath%>js/lib/jquery/jquery.formFill.js'></script>
<script src='<%=basePath%>js/lib/jquery-validation/jquery.validate.js'></script>
<script src='<%=basePath%>js/lib/jquery-validation/jquery.metadata.js'></script>
<script src='<%=basePath%>js/com/ic/conGroupR/conGroupRInteract.validate.js'></script>

  </head>
  
  <body>
    <div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<table id="manageListTable" method="ManageList" class="table-condensed" style="display: none;"></table>
			<div id="form" method="FormMethod">
<div id="conGroupRFormDiv" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	   <h3 id="myModalLabel">联系人与分组中间表</h3>
	</div>
	<form class="form-horizontal" id="conGroupRForm">
	<input type="hidden" id="id" name = "id"  >
		  <div class="control-group">
		     <label class="control-label" for="contactsId">联系人主键ID</label>
		     <div class="controls ">
		       <input type="text" id="contactsId" name = "contactsId" >
		     </div>
		  </div>
		  <div class="control-group">
		     <label class="control-label" for="contactsGroupId">联系人分组ID</label>
		     <div class="controls ">
		       <input type="text" id="contactsGroupId" name = "contactsGroupId" >
		     </div>
		  </div>
	</form> 
	<div class="modal-footer">
	     <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	     
	    <button id="ConGroupRSubmit" class="btn btn-primary">保存</button>
  </div>
</div>

</div>
		</div>
	</div>
</div>
  </body>
</html>
