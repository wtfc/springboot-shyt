<%@ page language="java" pageEncoding="UTF-8"%>
</div>
		
		<div class="tab-pane fade in" id="linkman-2">
			<div class="table-wrap">
					<table class="table table-striped" id="uTable">
						<script type="text/javascript">
							var height = ($("#scrollable").height());
						 	if($("#scanType").val() != "create"){
								document.write("<iframe id=frameT frameborder=0 width=100% height='"+height+"' src='"+getRootPath()+"/horizon/workflow/ShowFlowMap.jsp?workid=${workflowParam.workId}&identifier=system' ></iframe>");
						 	}else{
						 		$("#formSheet3").css("display","none");
						 		document.write("<iframe id=frameT frameborder=0 width=100% height='"+height+"' src='"+getRootPath()+"/horizon/workflow/HorizonSummaryInstance.jsp?only=true&flowid=${workflowParam.workFlowBean.processDefinitionKey}&identifier=system' ></iframe>");
						 	}
	                    </script>
					</table>
			</div>
		<section class="pagination m-r fr m-t-none"></section>
		</div>
		<div class="tab-pane fade in" id="linkman-3">
			<div class="table-wrap" >
				<table class="table table-striped" >
						<thead>
							<tr>
								<!-- <th class="w46"><input type="checkbox"/></th> -->
								<th>节点名称</th>
								<th>处理人</th>
								<th>操作时间</th>
								<th>操作</th>
								<th>操作内容</th>
								<th>意见域</th>
							</tr>
						</thead>
						<tbody id="historyDetail">
						</tbody>
					</table>
			</div>
		</div>
	</section>
</section>