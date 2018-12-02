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
		<form class="table-wrap  m-20-auto" id="toaNetworkIpstopForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">IP封停记录表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId"/>
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readonly type = "text" id="companyName" name="companyName" /></a>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">机房</td>							<td>
								<dic:select cssStyle="width:100%;" id="machine" name="machine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">封停IP所在设备</td>							<td>								<input type="text"  style="width:100%;"id="stopEquipment" name="stopEquipment" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">IP地址/位数</td>							<td>								<textarea   style="width:100%;"id="ip" name="ip" ></textarea>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">封停方式</td>							<td>								<select style="width:100%;"id="stopType" name="stopType">
									<option value="">请选择</option>
									<option value="ARP">ARP</option>
									<option value="NULL0">NULL0</option>
								</select>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">封停原因</td>							<td>
								<select style="width:100%;"id="stopReason" name="stopReason">
									<option value="">请选择</option>
									<option value="攻击">攻击</option>
									<option value="网络安全组">网络安全组</option>
								</select>							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">是否请求运营商协助</td>							<td>
								<select style="width:100%;"id="operatorHelp" name="operatorHelp">
									<option value="">请选择</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">运营商</td>							<td>								<input type="text"  style="width:100%;"id="operator" name="operator" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">封停日期</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="stopDate" name="stopDate" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">封停时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="ture" class="datepicker-input" style="width:100%;"id="stopDatetime" name="stopDatetime" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">封停工程师</td>							<td>								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaNetworkIpstopModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="stopEnginer" name="stopEnginer" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">最早可解封时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="ture" class="datepicker-input" style="width:100%;"id="firstDate" name="firstDate" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">解封日期</td>							<td>								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="deblockingDate" name="deblockingDate" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">解封时间</td>							<td>								<input type="text" data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="ture" class="datepicker-input" style="width:100%;"id="deblockingDatetime" name="deblockingDatetime" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">解封工程师</td>							<td>								<input type="text" onFocus="selectControl.singlePerson(this.id, false, 'toaNetworkIpstopModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="deblockingEnginer" name="deblockingEnginer" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<textarea style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaNetworkIpstopModule.saveOrModify(true)">提  交</a>
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
							<td  class=" b-green-dark b-tc">
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
		toaNetworkIpstopModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/network/toaNetworkIpstop/toaNetworkIpstopForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkIpstop/toaNetworkIpstop.validate.js" type="text/javascript"></script>