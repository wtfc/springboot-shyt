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
		<form class="table-wrap  m-20-auto" id="toaNetworkPortForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">端口使用情况统计表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">类型</td>							<td>								<input type="text"  style="width:100%;"id="companyType" name="companyType" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">机房</td>							<td>								<dic:select cssStyle="width:100%;" id="machine" name="machine" dictName="room" headName="-请选择-" headValue="" defaultValue=""/>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">设备</td>							<td>								<input type="text"  style="width:100%;"id="equipment" name="equipment" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">板卡</td>							<td>								<input type="text"  style="width:100%;"id="board" name="board" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">端口</td>							<td>								<input type="text"  style="width:100%;"id="ports" name="ports" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">VLAN</td>							<td>								<input type="text"  style="width:100%;"id="vlan" name="vlan" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">带宽</td>							<td>								<input type="text"  style="width:100%;"id="bandwidth" name="bandwidth" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">使用状态</td>							<td>								<input type="text"  style="width:100%;"id="usings" name="usings" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">端口总数</td>							<td>								<input type="text"  style="width:100%;"id="bandwidthNumber" name="bandwidthNumber" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">自用使用个数</td>							<td>								<input type="text"  style="width:100%;"id="myselfNumber" name="myselfNumber" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客户使用个数</td>							<td>								<input type="text"  style="width:100%;"id="companyNumber" name="companyNumber" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">自用使用率</td>							<td>								<input type="text"  style="width:100%;"id="myselfRate" readonly name="myselfRate" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客户使用率</td>							<td>								<input type="text"  style="width:100%;"id="companyRate" readonly name="companyRate" />							</td>						</tr>						<!-- <tr>							<td style="width:10%;" class=" b-green-dark b-tc">自用空闲率</td>							<td>								<input type="text"  style="width:100%;"id="myselfIdleness" name="myselfIdleness" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">客户空闲率</td>							<td>								<input type="text"  style="width:100%;"id="companyIdleness" name="companyIdleness" />							</td>						</tr> -->						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td colspan="3">								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaNetworkPortModule.saveOrModify(true)">提  交</a>
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
		toaNetworkPortModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/network/toaNetworkPort/toaNetworkPortForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkPort/toaNetworkPort.validate.js" type="text/javascript"></script>