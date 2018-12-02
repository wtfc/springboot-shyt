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
		<form class="table-wrap  m-20-auto" id="toaNetworkBandwidthForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">在网客户带宽统计表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden"  id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">总带宽(M)</td>							<td>								<input type="text"  style="width:100%;"id="gbps" name="gbps" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">多线(M)</td>							<td>								<input type="text"  style="width:100%;"id="line" name="line" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">网林(M)</td>							<td>								<input type="text"  style="width:100%;"id="wanglin" name="wanglin" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">云众林(M)</td>							<td>								<input type="text"  style="width:100%;"id="yzl" name="yzl" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">廊坊(M)</td>							<td>								<input type="text"  style="width:100%;"id="langfang" name="langfang" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">鲁谷(M)</td>							<td>								<input type="text"  style="width:100%;"id="lugu" name="lugu" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">东四(M)</td>							<td>								<input type="text"  style="width:100%;"id="dongsi" name="dongsi" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">华思(M)</td>							<td>								<input type="text"  style="width:100%;"id="huasi" name="huasi" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">山东(M)</td>							<td>								<input type="text"  style="width:100%;"id="shandong" name="shandong" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">移动(M)</td>							<td>								<input type="text"  style="width:100%;"id="yidong" name="yidong" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">铁通(M)</td>							<td>								<input type="text"  style="width:100%;"id="tietong" name="tietong" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">教育(M)</td>							<td>								<input type="text"  style="width:100%;"id="jiaoyu" name="jiaoyu" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">科技(M)</td>							<td>								<input type="text"  style="width:100%;"id="keji" name="keji" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">CNISP(M)</td>							<td>								<input type="text"  style="width:100%;"id="cnisp" name="cnisp" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">国际(M)</td>							<td>								<input type="text"  style="width:100%;"id="guoji" name="guoji" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">传输</td>							<td>								<input type="text"  style="width:100%;"id="transfer" name="transfer" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">备注</td>							<td>								<textarea style="width:100%;"id="remark" name="remark"></textarea>							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaNetworkBandwidthModule.saveOrModify(true)">提  交</a>
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
		toaNetworkBandwidthModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/network/toaNetworkBandwidth/toaNetworkBandwidthForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkBandwidth/toaNetworkBandwidth.validate.js" type="text/javascript"></script>