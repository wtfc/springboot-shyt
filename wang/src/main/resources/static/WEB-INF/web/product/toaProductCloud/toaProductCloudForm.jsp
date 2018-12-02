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
		<form class="table-wrap  m-20-auto" id="toaProductCloudForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">云资源信息表</h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="customersId" name="customersId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td  class=" b-green-dark b-tc"><span class="required">*</span>公司名称</td>							<td>								<a  onclick="toaProductCloudModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input type="text" readOnly  style="width:100%;"id="customersName" name="customersName" /></a>							</td>							<td  class=" b-green-dark b-tc">支持人员</td>							<td>								<input type="text"   style="width:100%;"id="supporter" name="supporter"  value="杜泽辉，何全"/>							</td>
							<td  class=" b-green-dark b-tc">资源模式</td>
							<td>
								<input type="text"  style="width:100%;"id="resourceType" name="resourceType" />
							</td>						</tr>						<!-- <tr>							<td  class=" b-green-dark b-tc">合作状态</td>							<td>								<input type="text"  style="width:100%;"id="cooperateType" name="cooperateType" />							</td>						</tr> 						<tr>							<td  class=" b-green-dark b-tc">业务类型</td>							<td>								<input type="text"  style="width:100%;"id="businessType" name="businessType" />							</td>						</tr> -->
						<tr>
							<td  class=" b-green-dark b-tc"><span class="required">*</span>计费时间</td>
							<td>
								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="chargeTime" name="chargeTime" />
							</td>
							<td  class=" b-green-dark b-tc"><span class="required">*</span>计费开始时间</td>
							<td>
								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="startDate" name="startDate" />
							</td>
							<td  class=" b-green-dark b-tc"><span class="required">*</span>计费终止时间</td>
							<td>
								<input type="text" data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" style="width:100%;"id="startEnd" name="startEnd" />
							</td>
						</tr>						<tr>
							<td  class=" b-green-dark b-tc"><span class="required">*</span>金额(元)</td>
							<td>
								<input type="text"  style="width:100%;"id="amount" name="amount" />
							</td>							<td  class=" b-green-dark b-tc">CPU</td>							<td>								<input type="text"  style="width:100%;"id="cpu" name="cpu" />							</td>							<td  class=" b-green-dark b-tc">内存</td>							<td>								<input type="text"  style="width:100%;"id="ram" name="ram" />							</td>						</tr>						<tr>							<td  class=" b-green-dark b-tc">容量盘</td>							<td>								<input type="text"  style="width:100%;"id="stick" name="stick" />							</td>							<td  class=" b-green-dark b-tc">性能盘</td>							<td>								<input type="text"  style="width:100%;"id="performance" name="performance" />							</td>							<td  class=" b-green-dark b-tc">云主机快照</td>							<td>								<input type="text"  style="width:100%;"id="cloudPhoto" name="cloudPhoto" />							</td>						</tr>						<tr>							<td  class=" b-green-dark b-tc">云硬盘快照</td>							<td>								<input type="text"  style="width:100%;"id="cloudDive" name="cloudDive" />							</td>							<td  class=" b-green-dark b-tc">公网IP</td>							<td>								<input type="text"  style="width:100%;"id="publicIp" name="publicIp" />							</td>							<td  class=" b-green-dark b-tc">带宽</td>							<td>								<input type="text"  style="width:100%;"id="bandwidth" name="bandwidth" />							</td>						</tr>						<tr>							<td  class=" b-green-dark b-tc">路由器</td>							<td>								<input type="text"  style="width:100%;"id="router" name="router" />							</td>							<td  class=" b-green-dark b-tc">负载均衡</td>							<td>								<input type="text"  style="width:100%;"id="loadBalancer" name="loadBalancer" />							</td>							<td  class=" b-green-dark b-tc">备注</td>							<td >								<textarea  style="width:100%;"id="remark" name="remark"></textarea>							</td>						</tr>					</tbody>
				</table>
				<section class="clearfix" id="footer_height">
					<section class="form-btn fl m-l">
						<shiro:hasPermission name="user:saveProductCloud"><a class="btn dark"  onclick="toaProductCloudModule.saveOrModify(true)">提  交</a></shiro:hasPermission>
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
                <form class="table-wrap  m-20-auto" id="customerForm">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td  class=" b-green-dark b-tc">
								公司名称
							</td>
							<td>
								<input type = "text" id="companyName1" name="companyName1" />
					            <button class="btn dark" type="button" onclick="toaProductCloudModule.getWorkTask()">查 询</button>
					            <button class="btn" type="button" onclick="toaProductCloudModule.queryReset()">重 置</button>
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
		toaProductCloudModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/product/toaProductCloud/toaProductCloudForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/product/toaProductCloud/toaProductCloud.validate.js" type="text/javascript"></script>