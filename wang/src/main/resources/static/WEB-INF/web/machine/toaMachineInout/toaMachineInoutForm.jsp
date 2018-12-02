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
		<form class="table-wrap  m-20-auto" id="toaMachineInoutForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">机房进出 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td>工单编号</td>							<td>								<input type="text"  style="width:100%;"id="equipmentNumber" name="equipmentNumber" readonly value="${applyNum}"/>							</td>							<td>客户名称</td>							<td>								<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>							</td>						</tr>						<tr>							<td>所属机房</td>							<td>								<dic:select cssStyle="width:100%;" id="machina" name="machina" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>							</td>							<td>客户联系人</td>							<td>								<input type="text"  style="width:100%;"id="contact" name="contact" />							</td>						</tr>						<tr>							<td>客户联系方式</td>							<td>								<input type="text"  style="width:100%;"id="tel" name="tel" />							</td>							<td>入室日期</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="true" class="datepicker-input" style="width:100%;"id="intDate" name="intDate" />							</td>						</tr>						<tr>							<td>类型</td>							<td>
								<select style="width:100%;"id="type" name="type">
									<option value="">请选择</option>
									<option value="维护">维护</option>
									<option value="搬入">搬入</option>
									<option value="搬出">搬出</option>
									<option value="迁移">迁移</option>
								</select>							</td>							<td>工单设备数量</td>							<td>								<input type="text"  style="width:100%;"id="countt" name="countt" />							</td>						</tr>						<tr>							<td>设备信息</td>							<td>								<textarea  style="width:100%;"id="deviceInfo" name="deviceInfo" ></textarea>							</td>							<td>入室人员</td>							<td>								<input type="text"  style="width:100%;"id="intPeople" name="intPeople" />							</td>						</tr>						<tr>							<td>是否进场</td>							<td>
								<select style="width:100%;"id="isInput" name="isInput">
									<option value="">请选择</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>							</td>							<td>操作工程师</td>							<td>								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaMachineInoutModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="caozgcs" name="caozgcs" />							</td>						</tr>						<tr>							<td>实际到场设备数量</td>							<td>								<input type="text"  style="width:100%;"id="origin" name="origin" />							</td>							<td>备注</td>							<td>								<textarea   style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<shiro:hasPermission name="user:saveMachineInout"><a class="btn dark"  onclick="toaMachineInoutModule.saveOrModify(true)">提  交</a></shiro:hasPermission>
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
		toaMachineInoutModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/machine/toaMachineInout/toaMachineInoutForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaMachineInout/toaMachineInout.validate.js" type="text/javascript"></script>