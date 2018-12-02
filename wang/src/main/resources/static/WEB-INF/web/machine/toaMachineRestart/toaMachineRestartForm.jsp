<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header> 
	<section class="panel clearfix" >
		<form class="table-wrap  m-20-auto" id="toaMachineRestartForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">重启操作 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width: 15%;"><span class="required">*</span>工单编号</td>							<td style="width: 35%;">								<input type="text"  style="width:100%;"id="equipmentNumber" name="equipmentNumber" readonly value="${applyNum}"/>							</td>							<td style="width: 15%;"><span class="required">*</span>客户名称</td>							<td style="width: 35%;">								<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName"/></a>							</td>						</tr>
						<tr>
							<td><span class="required">*</span>申请人</td>
							<td>
								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaMachineRestartModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="applicant" name="applicant" />
							</td>
							<td>申请时间</td>
							<td>
								<input readOnly type="text" style="width:100%;"id="billDate" name="billDate"  />
							</td>
						</tr>
						<tr>
							<td>是否前置操作</td>
							<td>
								<select id="chargeExamine" name="chargeExamine" style="width:100%;">
									<option value="">请选择</option>
									<option value="有">有</option>
									<option value="无">无</option>
								</select>
							</td>
							<td>前置操作内容</td>
							<td>
								<input type="text" style="width:100%;" id="chargeInformation" name="chargeInformation">
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align: center;">设备信息</td>
						</tr>
						<tr>
							<td><span class="required">*</span>机房</td>
							<td>
								<dic:select cssStyle="width:100%;" id="machina" name="machina" dictName="room" headName="-请选择-" headValue="" defaultValue="" selectLinkage="" />
							</td>
							<td>机柜</td>
							<td>
								<input type="text"  style="width:100%;"id="cabinet" name="cabinet"/>
							</td>
						</tr>
						<tr>
							<td>IP</td>
							<td>
								<input type="text"  style="width:100%;"id="ip" name="ip"/>
							</td>
							<td>SN</td>
							<td>
								<input type="text" style="width:100%;" id="sn" name="sn">
							</td>
						</tr>
						<tr>
							<td>品牌型号</td>
							<td colspan="3">
								<input type="text" style="width:100%;" id="brand" name="brand">
							</td>
							<!-- <td>型号</td>
							<td>
								<input type="text" style="width:100%;" id="modelNumber" name="modelNumber">
							</td> -->
						</tr>
						<tr>
							<td>备注</td>
							<td colspan="3">
								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align: center;">操作过程</td>
						</tr>
						<tr>
							<td>到场时间</td>
							<td></td>
							<td>是否重复重启（30分钟内多次重启）</td>
							<td></td>
						</tr>
						<tr>
							<td>是否接显示器</td>
							<td></td>
							<td>上传附件</td>
							<td></td>
						</tr>
						<tr>
							<td>是否报错</td>
							<td></td>
							<td>上传附件</td>
							<td></td>
						</tr>
						<tr>
							<td>开始操作时间</td>
							<td></td>
							<td>操作完成时间</td>
							<td></td>
						</tr>
						<tr>
							<td>有无后续操作</td>
							<td></td>
							<td>后续操作类型</td>
							<td></td>
						</tr>
						<tr>
							<td>扫描码</td>
							<td></td>
							<td>协助处理</td>
							<td></td>
						</tr>
						<tr>
							<td>操作工程师(技术处理人)</td>
							<td></td>
							<td>主管评分</td>
							<td></td>
						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaMachineRestartModule.saveOrModify(true)">提  交</a>
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

<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/machine/toaMachineRestart/toaMachineRestartForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/machine/toaMachineRestart/toaMachineRestart.validate.js" type="text/javascript"></script>