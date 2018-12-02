<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
	.select_style 
	{
		width:240px; 
		height:30px; 
		overflow:hidden; 
		border:1px solid #ccc;
		-moz-border-radius: 5px; /* Gecko browsers */
		-webkit-border-radius: 5px; /* Webkit browsers */
		border-radius:5px;
	}
	.select_style select { 
		padding:5px; 
		background:transparent; 
		width:268px; 
		font-size: 14px; 
		border:none; 
		height:30px;	
		-webkit-appearance: none; /*for Webkit browsers*/
	}

</style>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<div class="row-fluid">
				检索：<input type="text" id="treeSearch" value="" class="empty"/><br/>
				</div>
				<div style="float:left;padding-right: 50px;">
					<div id="treeDemo" class="ztree" style="width:180px"></div>
				</div>				
				<div class="row-fluid"  style="width:500px;float:left;">
<!-- 					<div> -->
<!-- 						部门<select name="searchType" id="searchType" style="width:100px" data-type="search"> -->
<!-- 							<option value="">全部</option> -->
<!-- 							<option value="3">架构部</option> -->
<!-- 							<option value="4">CRM</option> -->
<!-- 							<option value="5">客开部</option> -->
<!-- 						</select> -->
<!-- 						级别<select name="searchType1" id="searchType1" style="width:100px" data-type="search"> -->
<!-- 							<option value="">全部</option> -->
<!-- 							<option value="level_0">无</option> -->
<!-- 							<option value="level_1">厅级</option> -->
<!-- 							<option value="level_2">局级</option> -->
<!-- 						</select> -->
<!-- 					</div> -->
					<div>
					    <select  id="searchable" multiple="multiple"  name="searchable[]">
					   
					    </select>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
