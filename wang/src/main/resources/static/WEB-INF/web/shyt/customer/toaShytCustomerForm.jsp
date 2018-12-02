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
		<form class="table-wrap  m-20-auto" id="toaShytCustomerForm">
			<h5 class=" tc" style="margin:0;border:0;">客户基本信息 </h5>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="companyId" name="companyId">
				<table class="table table-td-striped">
					<tbody>
						<tr>							<td style="width:10%;"><span class="required">*</span>客户名称</td>							<td>								<a onclick="toaShytCustomerModule.closeWin();" type="button" href="#new-agency"  data-toggle="modal"><input readOnly type="text" style="width:100%;"id="companyName" name="companyName" /></a>							</td>							<td style="width:10%;"><span class="required">*</span>客户类型</td>
							<td>
								<select  style="width:100%;"id="memberType" name="memberType" >
									<option value="">请选择</option>
									<option value="VVIP客户">VVIP客户</option>
									<option value="VIP客户">VIP客户</option>
									<option value="钻石客户">钻石客户</option>
									<option value="白金客户">白金客户</option>
									<option value="普通客户">普通客户</option>
								</select>
							</td>
							<td style="width:10%;"><span class="required">*</span>业务类型</td>
							<td style="width:23%;">
								<dic:checkbox  id="serviceType" name="serviceType" dictName="customerType" />
							</td>
						</tr>
						<tr>
							<td style="width:10%;"><span class="required">*</span>所属行业</td>
							<td>
								<input type="text"  style="width:100%;"id="trade" name="trade" />
							</td>
							<td style="width:10%;"><span class="required">*</span>所属机房</td>							<td style="width:25%;">
								<dic:checkbox  id="machine" name="machine" dictName="room" />							</td>							<td style="width:10%;">通信地址</td>
							<td>
								<input type="text"  style="width:100%;"id="address" name="address" />
							</td>
						</tr>						<tr>							<td style="width:10%;"><span class="required">*</span>签约拓展组</td>							<td>
								<div class="input-group inline-tree">
									<input type="text" id="department" style="width:100%;" name="department" readonly /><input
										type="hidden" id="deptId" name="deptId" /><a
										class="btn btn-file input-group-btn" href="#"
										id="showDeptTree"  data-toggle="modal"><i
										class="fa fa-group"></i></a>
								</div>
								<p class="hide" id="deptError" style="color:red;">此信息不能为空</p>								<!-- <select  style="width:100%;"id="department" name="department" >
									<option value="">请选择</option>
									<option value="拓展一部">拓展一部</option>
									<option value="拓展二部">拓展二部</option>
									<option value="拓展三部">拓展三部</option>
								</select> -->							</td>							<td style="width:10%;"><span class="required">*</span>签约拓展专员</td>							<td>								<input type="text" readonly onFocus="selectControl.singlePerson(this.id, false, 'toaShytCustomerModule.spCall', eval($('#userJson').val()));"  style="width:100%;"id="sale" name="sale" />							</td>							<td style="width:10%;"><span class="required">*</span>维护专员</td>							<td>								<input type="text" readonly onFocus="selectControl.singlePerson(this.id, false, 'toaShytCustomerModule.spCall', eval($('#userJson').val()));" style="width:100%;"id="tradeUser" name="tradeUser" />							</td>						</tr>						<tr>
							<td style="width:10%;">关联客户名称</td>
							<td>
								<a onclick="shytCustomer.closeWin1();" type="button" href="#new-agency1"  data-toggle="modal"><input type="text" readonly style="width:100%;"id="linkUser" name="linkUser" /></a>
							</td>
							<td style="width:10%;">关联客户原因</td>
							<td>
								<input type="text" style="width:100%;" id="extStr3" name="extStr3">
							</td>							<td style="width:10%;"><span class="required">*</span>纳税识别号</td>							<td>								<input type="text"  style="width:100%;"id="taxid" name="taxid" />							</td>						</tr>						<tr>							<td style="width:10%;"><span class="required">*</span>开户银行</td>							<td>								<input type="text"  style="width:100%;"id="bankName" name="bankName" />							</td>							<td style="width:10%;"><span class="required">*</span>银行帐号</td>							<td>								<input type="text"  style="width:100%;"id="bankNo" name="bankNo" />							</td>							<td style="width:10%;"><span class="required">*</span>发票类型</td>							<td>
								<select style="width:100%;"id="ticketFlag" name="ticketFlag">
									<option value="">请选择</option>
									<option value="普票">普票</option>
									<option value="专票">专票</option>
								</select>							</td>						</tr>						<tr>							<td style="width:10%;">超流量取值方式</td>							<td>								<input type="text"  style="width:100%;"id="overflowCategory" name="overflowCategory" />							</td>							<td style="width:10%;">客户选择公司原因</td>							<td>								<input type="text"  style="width:100%;"id="dailiName" name="dailiName" />							</td>
							<td style="width:10%;">公司联系方式</td>
							<td>
								<input type="text"  style="width:100%;"id="extStr2" name="extStr2" />
							</td>						</tr>						<tr>							<td style="width:10%;"><span class="required">*</span>首次入网时间</td>							<td>								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="startIntel" name="startIntel" />							</td>							<td style="width:10%;">终止时间</td>							<td>								<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="endIntel" name="endIntel" />							</td>							<td style="width:10%;">客户评级</td>							<td>								<input type="text"  style="width:100%;"id="rating" name="rating" />							</td>						</tr>
						<tr>
							<td style="width:10%;"><span class="required">*</span>客户接入商</td>
							<td>
								<input type="text" style="width:100%;" id="customerAccess" name="customerAccess">
							</td>
							<td style="width:10%;"><span class="required">*</span>客户官网</td>
							<td>
								<input type="text" style="width:100%;" id="customerWebsite" name="customerWebsite">
							</td>
							<td style="width:10%;"><span class="required">*</span>公共号</td>
							<td>
								<input type="text" style="width:100%;" id="commonNumber" name="commonNumber">
							</td>
						</tr>
						<tr>
							<td style="width:10%;"><span class="required">*</span>注册地址</td>
							<td>
								<input type="text"  style="width:100%;"id="newAddress" name="newAddress" />
							</td>
							<td style="width:10%;"><span class="required">*</span>放置在公司业务</td>
							<td>
								<input type="text" style="width:100%;" id="companyPlaced" name="companyPlaced">
							</td>
							<td style="width:10%;"><span class="required">*</span>客户公司确定采购资源部门</td>
							<td>
								<input type="text" style="width:100%;" id="companyPurchasing" name="companyPurchasing">
							</td>
						</tr>
					</tbody>
					</table>
					<c:if test="${empty Id}">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="add()">增加</button>
					</span>
					<h5 class=" tc" style="margin:0;border:0;">客户联系信息 </h5>
					<table id="customerTable" class="table table-td-striped">
						<tbody>
							<tr>
								<td >
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name" name="name" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job" id="job" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel" id="tel" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard" type="text" style="width:100%;" name = "idCard"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email" type="text"  name="email" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin" id="weixin" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					<div id="customerTable1" style="display:none">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="del1()">删除</button>
					</span>
					<table class="table table-td-striped" >
						<tbody>
							<tr>
								<td>
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name1" name="name1" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job1" id="job1" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel1" id="tel1" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard1" type="text" style="width:100%;" name = "idCard1"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email1" type="text"  name="email1" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin1" id="weixin1" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div id="customerTable2" style="display:none">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="del2()">删除</button>
					</span>
					<table class="table table-td-striped" >
						<tbody>
							<tr>
								<td >
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name2" name="name2" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job2" id="job2" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel2" id="tel2" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard2" type="text" style="width:100%;" name = "idCard2"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email2" type="text"  name="email2" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin2" id="weixin2" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div id="customerTable3" style="display:none">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="del3()">删除</button>
					</span>
					<table class="table table-td-striped" >
						<tbody>
							<tr>
								<td >
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name3" name="name3" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job3" id="job3" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel3" id="tel3" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard3" type="text" style="width:100%;" name = "idCard3"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email3" type="text"  name="email3" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin3" id="weixin3" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div id="customerTable4" style="display:none">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="del4()">删除</button>
					</span>
					<table class="table table-td-striped" >
						<tbody>
							<tr>
								<td >
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name4" name="name4" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job4" id="job4" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel4" id="tel4" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard4" type="text" style="width:100%;" name = "idCard4"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email4" type="text"  name="email4" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin4" id="weixin4" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div id="customerTable5" style="display:none">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="del5()">删除</button>
					</span>
					<table class="table table-td-striped" >
						<tbody>
							<tr>
								<td >
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name5" name="name5" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job5" id="job5" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel5" id="tel5" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard5" type="text" style="width:100%;" name = "idCard5"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email5" type="text"  name="email5" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin5" id="weixin5" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div id="customerTable6" style="display:none">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="del6()">删除</button>
					</span>
					<table class="table table-td-striped" >
						<tbody>
							<tr>
								<td >
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name6" name="name6" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job6" id="job6" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel6" id="tel6" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard6" type="text" style="width:100%;" name = "idCard6"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email6" type="text"  name="email6" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin6" id="weixin6" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div id="customerTable7" style="display:none">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="del7()">删除</button>
					</span>
					<table class="table table-td-striped" >
						<tbody>
							<tr>
								<td >
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name7" name="name7" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job7" id="job7" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel7" id="tel7" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard7" type="text" style="width:100%;" name = "idCard7"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email7" type="text"  name="email7" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin7" id="weixin7" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div id="customerTable8" style="display:none">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="del8()">删除</button>
					</span>
					<table class="table table-td-striped" >
						<tbody>
							<tr>
								<td >
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name8" name="name8" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job8" id="job8" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel8" id="tel8" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard8" type="text" style="width:100%;" name = "idCard8"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email8" type="text"  name="email8" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin8" id="weixin8" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div id="customerTable9" style="display:none">
					<span class="form-btn">
						<button type="button" class="btn  btn-xs m-t-xs fr"
							onclick="del9()">删除</button>
					</span>
					<table class="table table-td-striped" >
						<tbody>
							<tr>
								<td >
									<span class="required">*</span>联系人</td>
								<td >
									<input type="text"  style="width:100%;"id="name9" name="name9" />
								</td>
								<td >职务</td>
								<td >
									<input type="text" name = "job9" id="job9" style="width:100%;"/>
								</td>
								<td ><span class="required">*</span>联系方式</td>
								<td >
									<input type="text"  name="tel9" id="tel9" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input id="idCard9" type="text" style="width:100%;" name = "idCard9"/>
								</td>
								<td>邮箱</td>
								<td>
									<input id="email9" type="text"  name="email9" style="width:100%;"/>
								</td>
								<td>微信/QQ
								</td>
								<td>
									<input type="text"  name="weixin9" id="weixin9" style="width:100%;"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					</c:if>
					<!-- <span class="form-btn">
						<button type="button" class="btn fr"
							onclick="addPrice()">增加</button>
					</span>
					<h5 class=" tc" style="margin:0;border:0;">资源单价 </h5>
					<table class="table">
						<tbody>							<tr>
								<td style="width:120px">机房</td>
								<td >机柜</td>
								<td >服务器</td>
								<td >IP</td>
								<td >端口</td>
								<td >端口带宽G</td>
								<td >保底带宽G</td>
								<td >超流量G</td>
								<td >交换机</td>
								<td >链路</td>
								<td >内存G</td>
								<td >CPU(核)</td>
								<td >硬盘G</td>
								<td >路由器</td>
								<td >操作</td>
							</tr>
							<tr>
								<td>
									<select style="width:100%;"id="room" name="room" >
										<option selected value="">请选择</option>
										<option value="兆维">兆维</option>
										<option value="鲁谷">鲁谷</option>
										<option value="洋桥">洋桥</option>
										<option value="清华园">清华园</option>
										<option value="看丹桥">看丹桥</option>
										<option value="富丰桥">富丰桥</option>
										<option value="沙河">沙河</option>
										<option value="廊坊">廊坊</option>
										<option value="担山屯">担山屯</option>
									</select>
								</td>								<td>									<input type="text"  style="width:100%;"id="machinePrice" name="machinePrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="servicePrice" name="servicePrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="ipPrice" name="ipPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="portPrice" name="portPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="portBandwidthPrice" name="portBandwidthPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="minBandwidthPrice" name="minBandwidthPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="overflowBandwidthPrice" name="overflowBandwidthPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="switchPrice" name="switchPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="odfPrice" name="odfPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="memoryPrice" name="memoryPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="cpuPrice" name="cpuPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="diskPrice" name="diskPrice" />								</td>								<td>									<input type="text"  style="width:100%;"id="routerPrice" name="routerPrice" />								</td>							</tr>
							<tr id="price1" style="display:none">
								<td>
									<select style="width:100%;"id="room1" name="room1" >
										<option selected value="">请选择</option>
										<option value="兆维">兆维</option>
										<option value="鲁谷">鲁谷</option>
										<option value="洋桥">洋桥</option>
										<option value="清华园">清华园</option>
										<option value="看丹桥">看丹桥</option>
										<option value="富丰桥">富丰桥</option>
										<option value="沙河">沙河</option>
										<option value="廊坊">廊坊</option>
										<option value="担山屯">担山屯</option>
									</select>
								</td>
								<td>
									<input type="text"  style="width:100%;"id="machinePrice1" name="machinePrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="servicePrice1" name="servicePrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="ipPrice1" name="ipPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="portPrice1" name="portPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="portBandwidthPrice1" name="portBandwidthPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="minBandwidthPrice1" name="minBandwidthPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="overflowBandwidthPrice1" name="overflowBandwidthPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="switchPrice1" name="switchPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="odfPrice1" name="odfPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="memoryPrice1" name="memoryPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="cpuPrice1" name="cpuPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="diskPrice1" name="diskPrice1" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="routerPrice1" name="routerPrice1" />
								</td>
								<td>
									<button type="button" class="btn fr"onclick="delPrice1()">删除</button>
								</td>
							</tr>
							<tr id="price2" style="display:none">
								<td>
									<select style="width:100%;"id="room2" name="room2" >
										<option selected value="">请选择</option>
										<option value="兆维">兆维</option>
										<option value="鲁谷">鲁谷</option>
										<option value="洋桥">洋桥</option>
										<option value="清华园">清华园</option>
										<option value="看丹桥">看丹桥</option>
										<option value="富丰桥">富丰桥</option>
										<option value="沙河">沙河</option>
										<option value="廊坊">廊坊</option>
										<option value="担山屯">担山屯</option>
									</select>
								</td>
								<td>
									<input type="text"  style="width:100%;"id="machinePrice2" name="machinePrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="servicePrice2" name="servicePrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="ipPrice2" name="ipPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="portPrice2" name="portPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="portBandwidthPrice2" name="portBandwidthPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="minBandwidthPrice2" name="minBandwidthPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="overflowBandwidthPrice2" name="overflowBandwidthPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="switchPrice2" name="switchPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="odfPrice2" name="odfPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="memoryPrice2" name="memoryPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="cpuPrice2" name="cpuPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="diskPrice2" name="diskPrice2" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="routerPrice2" name="routerPrice2" />
								</td>
								<td>
									<button type="button" class="btn fr"onclick="delPrice2()">删除</button>
								</td>
							</tr>
							<tr id="price3" style="display:none">
								<td>
									<select style="width:100%;"id="room3" name="room3" >
										<option selected value="">请选择</option>
										<option value="兆维">兆维</option>
										<option value="鲁谷">鲁谷</option>
										<option value="洋桥">洋桥</option>
										<option value="清华园">清华园</option>
										<option value="看丹桥">看丹桥</option>
										<option value="富丰桥">富丰桥</option>
										<option value="沙河">沙河</option>
										<option value="廊坊">廊坊</option>
										<option value="担山屯">担山屯</option>
									</select>
								</td>
								<td>
									<input type="text"  style="width:100%;"id="machinePrice3" name="machinePrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="servicePrice3" name="servicePrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="ipPrice3" name="ipPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="portPrice3" name="portPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="portBandwidthPrice3" name="portBandwidthPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="minBandwidthPrice3" name="minBandwidthPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="overflowBandwidthPrice3" name="overflowBandwidthPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="switchPrice3" name="switchPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="odfPrice3" name="odfPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="memoryPrice3" name="memoryPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="cpuPrice3" name="cpuPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="diskPrice3" name="diskPrice3" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="routerPrice3" name="routerPrice3" />
								</td>
								<td>
									<button type="button" class="btn fr"onclick="delPrice3()">删除</button>
								</td>
							</tr>
							<tr id="price4" style="display:none">
								<td>
									<select style="width:100%;"id="room4" name="room4" >
										<option selected value="">请选择</option>
										<option value="兆维">兆维</option>
										<option value="鲁谷">鲁谷</option>
										<option value="洋桥">洋桥</option>
										<option value="清华园">清华园</option>
										<option value="看丹桥">看丹桥</option>
										<option value="富丰桥">富丰桥</option>
										<option value="沙河">沙河</option>
										<option value="廊坊">廊坊</option>
										<option value="担山屯">担山屯</option>
									</select>
								</td>
								<td>
									<input type="text"  style="width:100%;"id="machinePrice4" name="machinePrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="servicePrice4" name="servicePrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="ipPrice4" name="ipPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="portPrice4" name="portPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="portBandwidthPrice4" name="portBandwidthPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="minBandwidthPrice4" name="minBandwidthPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="overflowBandwidthPrice4" name="overflowBandwidthPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="switchPrice4" name="switchPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="odfPrice4" name="odfPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="memoryPrice4" name="memoryPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="cpuPrice4" name="cpuPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="diskPrice4" name="diskPrice4" />
								</td>
								<td>
									<input type="text"  style="width:100%;"id="routerPrice4" name="routerPrice4" />
								</td>
								<td>
									<button type="button" class="btn fr"onclick="delPrice4()">删除</button>
								</td>
							</tr>							</tbody>
						</table> -->
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="toaShytCustomerModule.saveOrModify(true)">提  交</a>
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
							<td >
								公司名称
							</td>
							<td>
								<input type = "text" id="companyName1" name="companyName1" />
					            <button class="btn dark" type="button" onclick="toaShytCustomerModule.getWorkTask()">查 询</button>
					            <button class="btn" type="button" onclick="toaShytCustomerModule.queryReset()">重 置</button>
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
    <!--公司名称提取 开始  -->
	<div class="modal  panel" id="new-agency1" aria-hidden="false">
        <div class="modal-dialog w820">
            <div class="modal-content">
                <div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">公司名称</h4>
                </div>
                <div class="modal-body">
                <form class="table-wrap  m-20-auto" id="customerForm1">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td>
								公司名称
							</td>
							<td>
								<input type = "text" id="companyName11" name="companyName11" />
					            <button class="btn dark" type="button" onclick="toaShytCustomerModule.getWorkTask1()">查 询</button>
					            <button class="btn" type="button" onclick="toaShytCustomerModule.queryReset1()">重 置</button>
							</td>
						</tr>
						</tbody>
					</table>
				</form>
                    <section class="panel-tab-con">
                        <div class="tab-content">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                <form class="table-wrap">
                                    <table class="table table-striped frist-td-tc" id="issuedTaskTable1">
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
    <!-- 选择部门 -->
    <div class="modal fade panel" id="myModal" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">机构选择</h4>
                </div>
                 <div class="modal-body">
                	<div id="deptTreeDiv" class="ztree"></div>
                </div>
                <div class="modal-footer no-all form-btn">
                    <button class="btn dark" type="submit" id="treeSave">确 定</button><button class="btn" type="reset" id="treeClose">取 消</button>
                </div>
            </div>
        </div>
    </div>
<c:if test="${!empty Id}">
<script >
$(document).ready(function(){
	var ids=(${Id});
	if(ids!=null&&ids!=""){
		toaShytCustomerModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/shyt/customer/toaShytCustomerForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/userDeptTree.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/shyt/customer/toaShytCustomer.validate.js" type="text/javascript"></script>