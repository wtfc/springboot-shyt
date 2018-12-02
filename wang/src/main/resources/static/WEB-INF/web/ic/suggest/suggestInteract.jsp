<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/datepicker/bootstrap-datepicker.js"></script>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script> 

<script type="text/javascript">
//显示没有行数据后面的操作按钮
	function oTableSetButtons( source) {
		//var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"suggest.get("+ source.id+ ","+"'edit'"+")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
		var rep = "<shiro:hasPermission name='suggest:saveRep'><a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"suggest.replyDiv("+ source.id+ ","+"'rep'"+")\" data-toggle=\"modal\">回复</a></shiro:hasPermission>";
		var del = "<shiro:hasPermission name='suggest:delete'><a class=\"a-icon i-remove\" href=\"#\" onclick=\"suggest.deleteSuggest("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"删除\"/></i></a></shiro:hasPermission>";
		if(source.userId == source.createUser){
			return rep + del;
		}else{
			return rep  ;
		}
};
function userOTableSetButtons(source) {
	var download = "<a class=\"btn dark\" href=\""+getRootPath()+"/content/attach/download.action?fileName="+source.fileName+"&resourcesName="+source.resourcesName+"\" >下载</a>";
	return download;
};
</script>

<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in" id="header_1">
	<div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
	 <a class="btn dark fr" href="#" id="showAddDiv_t" role="button"
		data-toggle="modal">发起建议</a>
</header>
	<!-- ------------------------------------查询条件begin-------------------------------- -->
<input type="hidden" id="token" name="token" value=${token } />
<section class="panel clearfix search-box search-shrinkage">
	<div class="search-line hide">
		<form class="table-wrap form-table" id="suggestListForm">
			<input id="suggestIds" name="suggestIds" type="hidden">
			<table class="table table-td-striped">
				<tbody>
					<tr>
						<td class="w140">建议标题</td>
						<td style="width:35%;"><input type="text" id="suggestTitle"
							name="suggestTitle"></td>
						<td class="w140">发起人</td>
						<td>
							<div id="controlTreeToCreateUser" style="display: block;"></div>
						</td>
					</tr>
					<tr>
						<td>建议类型</td>
						<td><select id="suggestTypeId" name="suggestTypeId">

						</select></td>
						<td>回复状态</td>
						<td><label class="checkbox inline"> <input
								type="checkbox" id="repStatus_n" name="repStatus_n" value="0">未回复
						</label> <label class="checkbox inline m-l-md"> <input
								type="checkbox" id="repStatus_y" name="repStatus_y" value="1">已回复
						</label></td>
					</tr>
					<tr>
						<td>处理类型</td>
						<td><label class="checkbox inline"> <input
								type="checkbox" id="disposeType_s" name="disposeType_s"
								value="1">发起
						</label> <label class="checkbox inline m-l-md"> <input
								type="checkbox" id="disposeType_r" name="disposeType_r"
								value="0">接收
						</label></td>
						<td>发起方式</td>
						<td><label class="checkbox inline"> <input
								type="checkbox" id="suggestWay_t" name="suggestWay_t" value="0">实名
						</label> <label class="checkbox inline m-l-md"> <input
								type="checkbox" id="suggestWay_f" name="suggestWay_f" value="1">匿名
						</label></td>

					</tr>
					<tr>
						<td>发起时间</td>
						<td>
							<div class="input-group w-p100">
								<input class="datepicker-input" type="text" id="startCreateDate"
									name="startCreateDate" value="" data-date-format="yyyy-MM-dd"
									data-ref-obj="#endCreateDate lt">
								<div class="input-group-btn w30">-</div>
								<input class="datepicker-input" type="text" id="endCreateDate"
									name="endCreateDate" value="" data-date-format="yyyy-MM-dd"
									data-ref-obj="#startCreateDate gt">
							</div>
						</td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			<section class="form-btn m-b-lg">
				<button class="btn dark query-jump" type="button" id="querysuggest">查 询</button>
				<button class="btn" type="button" id="resetForm">重 置</button>
			</section>
		</form>
	</div>
	<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
</section>
<!-- ----------------------------------查询条件end-------------------------------------------- -->
	<!-- ----------------------------------数据列表begin------------------------------------------ -->
<section class="panel" id="IP-edit">
	<!--start 查询结果-->
	<h2 class="panel-heading clearfix">查询结果</h2>
	<div class="table-wrap" id="list">
		<table class="table table-striped tab_height" id="suggestTable">
			<thead>
				<tr>
					<th style="width:15%;">建议标题</th>
					<th style="width:15%;">建议类型</th>
					<th style="width:10%;">回复状态</th>
					<th style="width:10%;">处理类型</th>
					<th style="width:15%;">发起人</th>
					<th style="width:15%;">发起时间</th>
					<th style="width:20%;">操作</th>
				</tr>
			</thead>
			<!-- 此处必须留有一个空的<tbody>以便js像列表中添加数据 -->
			<tbody>

			</tbody>
		</table>
	</div>
	<section class="bp-inline clearfix" id="footer_height">
		<section class="form-btn fl m-l">
			<shiro:hasPermission name="suggest:save"><a class="btn dark query-jump" id="showAddDiv" role="button"
				data-toggle="modal" >发起建议</a></shiro:hasPermission>
		</section>
	</section>
</section>
<!-- -------------------------数据列表end-------------------------------------------------->
</section>
<!-- ------------------------弹出层 编辑begin-------------------------------------------- -->
   <div id="myModal-editDiv">
      
    </div>
<!-- ------------------------------弹出层end---------------------------------------------->
     <!--start 回复 弹出层-->
    <div id="replyDiv">

    </div>
    <!--end 查看 弹出层-->
   <!---------------------------------------- 附件部分-------------------------------------- --> 
<!--管理附件弹出层 -->
<div class="modal fade panel" id="file-edit" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close"  id="closebtn" data-dismiss="modal" onclick="showAttachList(false)">×</button>
				<h4 class="modal-title">附件</h4>
			</div>
			<div class="modal-body">
			    <!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
				<input type="hidden" name="businessId" id="businessId" value="0"/>
				<input type="hidden" name="businessTable" id="businessTable"  value="TOA_IC_SUGGEST"/> 
				<!-- upload_type 1为单传  0为多传-->
                <input type="hidden" id="upload_type" value="0">
                <!-- islogicDel 1为逻辑删除  0为物理删除-->
                <input type="hidden" id="islogicDel" value="1">
                <!-- isshow 1为查看附件  0为管理附件-->
                <input type="hidden" id="isshow" value="1"> 
                  <!-- iscopy 1为复制附件列表 0为不复制  默认为0-->
                <input type="hidden" id="iscopy" value="0">
                <input type="hidden" id="upload_div_name" value="file-edit">
				<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
			</div>
			<div class="modal-footer form-btn">
			<!--关闭弹出层时 如需清理表格内容可调用clearTable()-->
				<button class="btn dark" type="button" id="clbtn" data-dismiss="modal" onclick="showAttachList(false)">关&nbsp;闭</button>
			</div>
		</div>
	</div>
</div>
<script src="${sysPath}/js/com/ic/suggest/suggestInteract.js"></script>
<script src='${sysPath}/js/com/ic/suggest/suggestInteract.validate.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>