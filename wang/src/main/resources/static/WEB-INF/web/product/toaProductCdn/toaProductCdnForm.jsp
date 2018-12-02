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
		<form class="table-wrap  m-20-auto" id="toaProductCdnForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">CDN资源表 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden"   id="customersId" name="customersId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td ><span class="required">*</span>公司名称</td>							<td>
								<a  onclick="toaProductCdnModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="customersName" name="customersName" /></a>							</td>							<td ><span class="required">*</span>分类</td>
							<td>
								<select style="width:100%;"id="extStr1" name="extStr1">
									<option value="">请选择</option>
									<option value="页面">页面</option>
									<option value="下载">下载</option>
									<option value="视频点播">视频点播</option>
									<option value="视频直播">视频直播</option>
								</select>
							</td>							<td >支持人员</td>
							<td>
								<input type="text" onFocus="selectControl.singlePerson(this.id, true, 'toaProductCdnModule.spCall', eval($('#userJson').val()));"  style="width:100%;"id="supporter" name="supporter" />
							</td>
						</tr>						<tr>							<td >用户名</td>							<td>								<input type="text"  style="width:100%;"id="userName" name="userName" />							</td>
							<td >计费方式</td>							<td>								<input type="text"  style="width:100%;"id="chargeMode" name="chargeMode" />							</td>
							<td ><span class="required">*</span>计费时间</td>
							<td>
								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="chargeTime" name="chargeTime" />
							</td>						</tr>
						<tr>
							<td ><span class="required">*</span>计费开始日期</td>
							<td>
								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="startDate" name="startDate" />
							</td>
							<td ><span class="required">*</span>计费终止日期</td>
							<td>
								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="startEnd" name="startEnd" />
							</td>
							<td >单价</td>
							<td>
								<input type="text"  style="width:100%;"id="price" name="price" />
							</td>
						</tr>						<tr>							<td >保底数值</td>							<td>								<input type="text"  style="width:100%;"id="flooredNumber" name="flooredNumber" />							</td>							<td >计费数值</td>							<td>								<input type="text"  style="width:100%;"id="chargeNumber" name="chargeNumber" />							</td>							<td >金额(元)</td>							<td>								<input type="text"  style="width:100%;"id="amount" name="amount" />							</td>						</tr>						<tr>
							<td >加速域名<br>回源IP/回源域名</td>
							<td>
								<textarea  style="width:100%;"id="stick" name="stick" ></textarea>
							</td>							<td >备注</td>							<td colspan="3">								<textarea  style="width:100%;"id="remark" name="remark" ></textarea>							</td>						</tr>						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<shiro:hasPermission name="user:saveProductCdn"><a class="btn dark"  onclick="toaProductCdnModule.saveOrModify(true)">提  交</a></shiro:hasPermission>
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
					            <button class="btn dark" type="button" onclick="toaProductCdnModule.getWorkTask()">查 询</button>
					            <button class="btn" type="button" onclick="toaProductCdnModule.queryReset()">重 置</button>
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
		toaProductCdnModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/product/toaProductCdn/toaProductCdnForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/product/toaProductCdn/toaProductCdn.validate.js" type="text/javascript"></script>