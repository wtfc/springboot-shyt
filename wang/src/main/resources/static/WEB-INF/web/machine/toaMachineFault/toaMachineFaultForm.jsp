<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header> 
	<section class="panel clearfix" >
		<form class="table-wrap  m-20-auto" id="toaMachineFaultForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">机房故障 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td >工单编号</td>							<td>								<input type="text"  style="width:100%;"id="faultNumber" name="faultNumber" readonly value="${applyNum}"/>							</td>							<td>报障客户</td>							<td>								<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>							</td>						</tr>						<tr>							<td>日期</td>							<td>								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="intDate" name="intDate" />							</td>							<td>故障类型</td>							<td>								<select  style="width:100%;"id="type" name="type">
									<option value="">请选择</option>
									<option value="光缆故障">光缆故障</option>
									<option value="网内攻击">网内攻击</option>
									<option value="设备故障">设备故障</option>
									<option value="运营商">运营商</option>
								</select>
							</td>						</tr>						<tr>							<td>故障时间</td>							<td>								<input type="text"data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="true" class="datepicker-input"  style="width:100%;"id="operateDate" name="operateDate" />							</td>							<td>恢复时间</td>							<td>								<input type="text"data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="true" class="datepicker-input"  style="width:100%;"id="restoreDate" name="restoreDate" />							</td>						</tr>						<tr>							<td>故障原因</td>							<td>								<textarea  style="width:100%;"id="faultReason" name="faultReason" ></textarea>							</td>							<td>故障报告</td>							<td>								<textarea   style="width:100%;"id="faultReport" name="faultReport"></textarea>							</td>						</tr>						<tr>							<td>报障客户</td>							<td>								<input type="text"  style="width:100%;"id="eazyName" name="eazyName" />							</td>							<td>处理过程</td>							<td>								<textarea   style="width:100%;"id="processing" name="processing"></textarea>							</td>						</tr>						<tr>							<td>涉及部门</td>							<td>
								<input style="width:80%" id="department" type="text" readonly name="department" onclick="aaa()"/> 
								<div style="display:none;" id="controlTree" >
								</div>
									<input style="width:20%" class="btn dark button fr" type="button" value="选择" onclick="bb();" />							</td>							<td>网络工程师</td>							<td>								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaMachineFaultModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="network" name="network" />							</td>						</tr>						<tr>							<td>运维工程师</td>							<td>								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaMachineFaultModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="yunwei" name="yunwei" />							</td>							<td>监控工程师</td>							<td>								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaMachineFaultModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="jiankong" name="jiankong" />							</td>						</tr>						<tr>							<td>备注说明</td>							<td colspan="3">								<textarea  style="width:100%;"id="remarkOne" name="remarkOne"></textarea>							</td>						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<shiro:hasPermission name="user:saveMachineFault"><a class="btn dark"  onclick="toaMachineFaultModule.saveOrModify(true)">提  交</a></shiro:hasPermission>
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
		toaMachineFaultModule.get(ids);
	}
});
</script>
</c:if>
<script>
function aaa(){
	var eData = returnValue('department-departments');
	var node="";
	$(eData.split(",")).each(function(i, v){
		 node = v.split(":")[1]+","+node;
	});
	$("#department").val(node);
}
function bb(){
	selectControl.org.open(true,'tree1','myTree1','department-departments','0');
	$("#department").val("");
}
</script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/machine/toaMachineFault/toaMachineFaultForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaMachineFault/toaMachineFault.validate.js" type="text/javascript"></script>