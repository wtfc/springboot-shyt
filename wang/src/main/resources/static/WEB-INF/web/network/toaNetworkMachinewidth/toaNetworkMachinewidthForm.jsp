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
		<form class="table-wrap  m-20-auto" id="toaNetworkMachinewidthForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">在网客户机房带宽统计表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">客户名称</td>							<td>								<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>							</td>							<td style="width:10%;" class=" b-green-dark b-tc">总带宽(M)</td>							<td>								<input type="text"  style="width:100%;"id="gbps" name="gbps" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">兆维(M)（三层环网）</td>							<td>								<input type="text"  style="width:100%;"id="threeZhaowei" name="threeZhaowei" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">洋桥(M)（三层环网）</td>							<td>								<input type="text"  style="width:100%;"id="threeYangqiao" name="threeYangqiao" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">看丹(M)（三层环网）</td>							<td>								<input type="text"  style="width:100%;"id="threeKandan" name="threeKandan" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">清华园(M)（三层环网）</td>							<td>								<input type="text"  style="width:100%;"id="threeQinghuayuan" name="threeQinghuayuan" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">鲁谷(M)（三层环网）</td>							<td>								<input type="text"  style="width:100%;"id="threeLugu" name="threeLugu" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">富丰桥(M)（三层环网）</td>							<td>								<input type="text"  style="width:100%;"id="threeFufengqiao" name="threeFufengqiao" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">沙河(M)（三层环网）</td>							<td>								<input type="text"  style="width:100%;"id="threeShahe" name="threeShahe" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">兆维(M)（二层环网）</td>							<td>								<input type="text"  style="width:100%;"id="twoZhaowei" name="twoZhaowei" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">洋桥(M)（二层环网）</td>							<td>								<input type="text"  style="width:100%;"id="twoYangqiao" name="twoYangqiao" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">看丹(M)（二层环网）</td>							<td>								<input type="text"  style="width:100%;"id="twoKandan" name="twoKandan" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">清华园(M)（二层环网）</td>							<td>								<input type="text"  style="width:100%;"id="twoQinghuayuan" name="twoQinghuayuan" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">鲁谷(M)（二层环网）</td>							<td>								<input type="text"  style="width:100%;"id="twoLugu" name="twoLugu" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">富丰桥(M)（二层环网）</td>							<td>								<input type="text"  style="width:100%;"id="twoFufengqiao" name="twoFufengqiao" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">沙河(M)（二层环网）</td>							<td>								<input type="text"  style="width:100%;"id="twoShahe" name="twoShahe" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">传输(M)（二层环网）</td>							<td>								<input type="text"  style="width:100%;"id="twoChuanshu" name="twoChuanshu" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">廊坊(M)（外省机房）</td>							<td>								<input type="text"  style="width:100%;"id="wsLangfang" name="wsLangfang" />							</td>						</tr>						<tr>							<td style="width:10%;" class=" b-green-dark b-tc">山东(M)（外省机房）</td>							<td>								<input type="text"  style="width:100%;"id="wsShandong" name="wsShandong" />							</td>							<td style="width:10%;" class=" b-green-dark b-tc">传输备注</td>							<td>								<input type="text"  style="width:100%;"id="remark" name="remark" />							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaNetworkMachinewidthModule.saveOrModify(true)">提  交</a>
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
		toaNetworkMachinewidthModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/network/toaNetworkMachinewidth/toaNetworkMachinewidthForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/network/toaNetworkMachinewidth/toaNetworkMachinewidth.validate.js" type="text/javascript"></script>