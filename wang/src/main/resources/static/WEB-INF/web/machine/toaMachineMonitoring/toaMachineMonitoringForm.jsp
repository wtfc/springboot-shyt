<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header> 
	<section class="panel clearfix" >
		<form class="table-wrap  m-20-auto" id="toaMachineMonitoringForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">监控报告通告 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td>工单编号</td>							<td>								<input type="text"  style="width:100%;"id="faultNumber" name="faultNumber" readonly value="${applyNum}"/>							</td>							<td>客户名称</td>							<td>								<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>							</td>						</tr>						<tr>							<td>所属机房</td>							<td>								<dic:select cssStyle="width:100%;" id="machina" name="machina" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>							</td>							<td>开始时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="true" class="datepicker-input" style="width:100%;"id="startDate" name="startDate" />							</td>						</tr>						<tr>							<td>故障类型</td>							<td>								<input type="text"  style="width:100%;"id="type" name="type" />							</td>
							<td>具体类型（2）</td>
							<td>
								<input type="text"  style="width:100%;"id="typeTwo" name="typeTwo" />							</td>						</tr>						<tr>							
							<td>恢复时间</td>
							<td>
								<input type="text" data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="true" class="datepicker-input" style="width:100%;"id="finishDate" name="finishDate" />
							</td>							<td>故障原因</td>							<td>								<textarea  style="width:100%;"id="faultReason" name="faultReason" ></textarea>							</td>						</tr>						<tr>							<td>超流量</td>							<td>								<input type="text"  style="width:100%;"id="overflow" name="overflow" />							</td>							<td>流入流量</td>							<td>								<input type="text"  style="width:100%;"id="inflow" name="inflow" />							</td>						</tr>						<tr>							<td>流出流量</td>							<td>								<input type="text"  style="width:100%;"id="outflow" name="outflow" />							</td>							<td>阀值</td>							<td>								<input type="text"  style="width:100%;"id="fazhi" name="fazhi" />							</td>						</tr>						<tr>							<td>超出阀值</td>							<td>								<input type="text"  style="width:100%;"id="overfazhi" name="overfazhi" />							</td>							<td>备注</td>							<td>								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<shiro:hasPermission name="user:saveMachineMonitoring"><a class="btn dark"  onclick="toaMachineMonitoringModule.saveOrModify(true)">提  交</a></shiro:hasPermission>
						</section>
					</section>
				</form>
			</section>
		</section>
<!--公司名称提取 开始  -->
	<div class="modal  panel" id="new-agency" aria-hidden="false">
        <div class="modal-dialog w820">
            <div class="modal-content">
                <div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">公司名称</h4>
                </div>
                <div class="modal-body">
                <form class="table-wrap  m-20-auto" id="companyForm">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td >
								公司名称
							</td>
							<td>
								<input type = "text" id="companyName1" name="companyName1" />
					            <button class="btn dark" type="button" onclick="companyModule.getWorkTask()">查 询</button>
					            <button class="btn" type="button" onclick="companyModule.queryReset()">重 置</button>
							</td>
						</tr>
						</tbody>
					</table>
				</form>
                    <section class="panel-tab-con">
                        <div class="tab-content">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                <form class="table-wrap">
                                    <table class="table table-striped frist-td-tc" id="issuedTaskTable">
                                        <thead>
                                            <th class="w46">请选择</th>
                                            <th>公司名称</th>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                    </section>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn" type="button" data-dismiss="modal" >关 闭</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 选择公司结束 -->	
<c:if test="${!empty Id}">
<script >
$(document).ready(function(){
	var ids=(${Id});
	if(ids!=null&&ids!=""){
		toaMachineMonitoringModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/machine/toaMachineMonitoring/toaMachineMonitoringForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaMachineMonitoring/toaMachineMonitoring.validate.js" type="text/javascript"></script>