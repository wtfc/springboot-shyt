<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script >
//设置每行按钮
 function oTableSetButtones (source) {
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/machine/equipment/equipmentView.action?id="+source.id+"\" onclick=\"\">查看详细</a>";
	buttonStr = read;
	return buttonStr ;
}; 
</script>
<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="viewForm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title" id="actionTitle">机柜编号:${number}</h4>
				</div>
					<section class="dis-table">
						<!-- tabs模块下紧接panel模块 -->
						
							<div class="tab-pane for fade active in" id="messages1">
								<div class="table-wrap">
								<input type="hidden" id="machinaId" name = "machinaId" value="${name}"/>
								<input type="hidden" id="machinaNumber" name = "machinaNumber"value="${number}"/>
									<table class="table table-striped tab_height first-td-tc" id="viewModuleTable">
										<thead>
											<tr>
												<!-- <th class="w46"><input type="checkbox" /></th> -->
												<th>客户名称</th>
												<th>品牌型号</th>
												<th>机柜位置</th>
												<th>上架时间</th>
												<th>管理网IP</th>
											 	<th >操作</th> 
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
				<div class="modal-footer form-btn">
					<button class="btn" type="button" class="close"
						data-dismiss="modal">关 闭</button>
				</div>
					</section>
				</div>
		</form>
	</div>
</div>
<script
	src="${sysPath}/js/com/shjfgl/view/module/equipmentViewModule.js"
	type="text/javascript"></script>