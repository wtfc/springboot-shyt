<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<style type="text/css"> 
	.autoDis{border:1px solid #999;position:absolute;overflow:hidden;} 
	.autoDis p{line-height:25px;cursor:default;padding:0 5px;} 
	.autoDis li{line-height:25px;cursor:default;padding:0 5px;background-color:#fff;} 
	.autoDis .cur{background:#ccc;} 
</style>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<section class="panel m-t-md" id="body">
			<h3 class="tc" style="margin:0;border:0;">森华易腾IDC人员入室审批单 </h3>
			<!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
			<section class="dis-table">
             <section class="panel-tab-con dis-table-cell">
                 <!-- tabs -->
                 <section class="tabs-wrap">
                     <!-- 同时添加 tabs-wrap-in 类-->
                     <ul class="nav nav-tabs">
                         <li class="active" id="m1">
                             <a href="#messages1" data-toggle="tab">工单信息</a>
                         </li>
                         <!-- 根据a标签href属性相对应的id，切换tab-pane -->
                         <li id="m3">
                             <a href="#messages3" data-toggle="tab">工单处理</a>
                         </li>
                         <li id="m4">
                             <a href="#messages4" data-toggle="tab">工单审核</a>
                         </li>
                         <li id="m2">
                             <a href="#messages2" data-toggle="tab">附&nbsp&nbsp&nbsp言</a>
                         </li>
                     </ul>
                 </section>
                <div class="tab-content" style="overflow:hidden;">
                <div class="tab-pane for fade active in" id="messages1">
                <form class="table-wrap  " id="equipmentInOutForm">
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="status" name="status" value="0">
				<input type="hidden" id="extStr1" name="extStr1" value="人员入室">
				<table class="table table-td-striped">
					<tbody>
						<!-- <tr>
							<td style="width:10%;" class=" b-green-dark b-tc"><span
								class="required">*</span>客户编号</td>
							<td ><input readonly="readonly" type="text" style="width:100%;"
								id="" name="" /></td>
							<td style="width:10%;"><span class="required">*</span>工单编号</td>
							<td >
								<input type="text" readonly="readonly" style="width:100%;"
								id="" name="" />
							</td>
							<td style="width:10%;" class=" b-green-dark b-tc"><span
								class="required">*</span>下单时间</td>
							<td>
								<input type="text" readonly="readonly" style="width:100%;"
								id="createDate" name="createDate" />
							</td>
						</tr> -->
						<tr>
							<td style="width:10%;" class=" b-green-dark b-tc"><span
								class="required">*</span>客户名称</td>
							<td>
								<div id="wrap"> 
									<input style="width:100%;" id="autoCom"  type="text" name="companyName" > 
								</div>
							</td>
							<td style="width:10%;"><span class="required">*</span>机房</td>
							<td >
								<select style="width:100%;" id="machina" name = "machina">
								  <option selected value ="">请选择</option>
								  <option value ="鲁谷机房">鲁谷机房</option>
								  <option value="兆维机房">兆维机房</option>
								  <option value="看丹桥机房">看丹桥机房</option>
								  <option value="洋桥机房">洋桥机房</option>
								  <option value="清华园机房">清华园机房</option>
								  <option value="沙河机房">沙河机房</option>
								  <option value="廊坊机房">廊坊机房</option>
								</select>
							</td>
							<td style="width:10%;" class=" b-green-dark b-tc"><span
								class="required">*</span>来源</td>
							<td>
								<select id="origin"style="width:100%;" name = "origin">
								  <option selected value ="">请选择</option>
								  <option value ="客户">客户</option>
								  <option value="销售">销售</option>
								  <option value="技术">技术</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>类型</td>
							<td><select style="width:100%;" id="type" name = "type">
								  <option selected value ="">请选择</option>
								  <option value ="维护">维护</option>
								  <option value="参观">参观</option>
								</select>
							</td>
							<td class=" b-green-dark b-tc">联系人</td>
							<td><input type="text" style="width:100%;" id="contact"
								name="contact" /></td>
							<td>联系方式</td>
							<td><input type="text" id="tel" style="width:100%;" name="tel" /></td>
						</tr>
						<tr>
							<td><span class="required">*</span>入室时间</td>
							<td><input class="datepicker-input"style="width:100%;" type="text" id="intDate" name="intDate" data-pick-time="true" data-date-format="yyyy-MM-dd" /></td>
							<td><span class="required">*</span>下单人</td>
							<td><input type="text" style="width:100%;"  id="noter"
								name="noter" value="${userName}"/></td>
							<td style="width:10%;" class=" b-green-dark b-tc"><span
								class="required">*</span>下单时间</td>
							<td>
								<input type="text" readonly="readonly" style="width:100%;"
								id="createDate" name="createDate" />
							</td>
						</tr>
						<tr>
							<td>入室人员信息</td>
							<td colspan="5"><textarea    id="intPeople"
								name="intPeople" ></textarea></td>
						</tr>
						<tr>
							<td >备注说明</td>
							<td colspan="5"><textarea   id="remarkOne" name="remarkOne" ></textarea>
							</td>
						</tr>
						</tbody>
					</table>
					<div class="modal-footer form-btn">
					<c:if test="${status eq '0'}">  
						<%-- <shiro:hasPermission name="user:add1"> --%>
						<button class="btn dark" type="button" onclick=equipmentInOutModule.saveOrModify(true) >提 交</button>
						<%-- </shiro:hasPermission> --%>
					</c:if>
					<c:if test="${empty id}">  
						<%-- <shiro:hasPermission name="user:add1"> --%>
						<button class="btn dark" type="button" onclick=equipmentInOutModule.saveOrModify(true) >提 交</button>
						<%-- </shiro:hasPermission> --%>
					</c:if>
				</div>
				</form>
				</div>
              <div class="tab-pane fade" id="messages3">
              <form class="table-wrap  " id="equipmentInOutForm2">
              	<input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
                  <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
               <table class="table table-td-striped">
						<tr>
							<td ><span class="required">*</span>是否进场</td>
							<td style="width:30%"><select id="isInput" style="width:100%"name = "isInput">
								  <option selected value="" >请选择</option>
								  <option  value ="是">是</option>
								  <option value ="否">否</option>
								</select>
							</td>
							<td><span class="required">*</span>接待工程师</td>
							<td ><input  type="text"  style="width:60%" id="caozgcs" 
								name="caozgcs" value="${userName}"/></td>
						</tr>
						<tr>
							<!-- <td>附件</td>
		                    <td >
		                   	<span><a class="btn dark" type="button" href="#file-edit"  name="queryattach" id="queryattach"  role="button" data-toggle="modal">附件</a></span>
		                    <ul id="attachList"></ul>
		                    </td> -->
							<td>实施情况</td>
							<td colspan="3">
								 <textarea   id="implemtation" name="implemtation" ></textarea>
							</td>
						</tr>
						<tr>
							<td >备注说明</td>
							<td colspan="3"><textarea   id="remark" name="remark" ></textarea>
							</td>
						</tr>
					</tbody>
					</table>
					<div class="modal-footer form-btn">
					<c:if test="${status eq '1'}">  
						<%-- <shiro:hasPermission name="user:add2"> --%>
						<button class="btn dark" type="button" onclick=equipmentInOutModule.equipmentInOutForm2(false) id="equipmentInOutForm2">提 交</button>
						<%-- </shiro:hasPermission> --%>
					</c:if>
					</div>
	              	</form>
	              	</div>
					 <div class="tab-pane fade" id="messages4">
			              <form class="table-wrap  " id="equipmentInOutForm4">
			              	<input type="hidden" id="id" name="id" value="0"> 
							<input type="hidden" id="token" name="token" value="${data.token}">
							<input type="hidden" id="modifyDate" name="modifyDate">
                  <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
               		<table class="table table-td-striped">
               			<tbody>
               			<tr>
							<td><span class="required">*</span>是否按要求处理</td>
							<td style="width:20%"><select id="isFinish" style="width:100%" name = "isFinish">
								  <option selected value="" >请选择</option>
								  <option value ="1">是</option>
								  <option value="0">否</option>
								</select>
							</td>
							<td><span class="required">*</span>客户满意度</td>
							<td >
								<select style="width:100%" id="search" name="search"> 
								 <option selected value="" >请选择</option>
								 <option  value="非常满意">非常满意</option>
								 <option  value="满意">满意</option>
								 <option  value="一般">一般</option>
								 <option  value="不满意">不满意</option>
								 <option  value="无">无</option>
								</select>
							</td>
							<td><span class="required">*</span>回访人</td>
							<td ><input style="width:60%" type="text" id="rebackName"
								   name="rebackName" value="${userName}"/></td>
						</tr>
						<tr>
							<td>处理时间</td>
							<td colspan="2">
								<select id="extStr2" style="width:100%" name = "extStr2">
								  <option selected value="" >请选择</option>
								  <option value ="合格">合格</option>
								  <option value="不合格">不合格</option>
								</select>
							</td>
							<td>响应时间</td>
							<td colspan="2">
								<select id="extStr3" style="width:100%" name = "extStr3">
								  <option selected value="" >请选择</option>
								  <option value ="合格">合格</option>
								  <option value="不合格">不合格</option>
								</select>
							</td>
						</tr>
						<tr>
							<td >备注</td>
							<td colspan="5"><textarea    id="remarkTwo" name="remarkTwo" ></textarea>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="modal-footer form-btn">
				<c:if test="${status eq '2'}">  
					<%-- <shiro:hasPermission name="user:add3"> --%>
					<button class="btn dark" type="button" onclick=equipmentInOutModule.equipmentInOutForm4(false) id="equipmentInOutForm4">提 交</button>
					<%-- </shiro:hasPermission> --%>
				</c:if>
				</div>
              	</form>
              	</div>
              	<div class="tab-pane fade" id="messages2">
						<form class="table-wrap  " id="equipmentInOutForm3">
							<!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
							<table class="table-wrap form-table">
								<tr>
									<td>
									<c:if test="${status eq '1'}"> 
									<span><a class="btn dark" type="button"
											href="#file-edit" name="queryattach" id="attachs"
											role="button" data-toggle="modal" onclick="clearTabale()">附件</a></span>
									</c:if>
									<c:if test="${empty id}"> 
									<span><a class="btn dark" type="button"
											href="#file-edit" name="queryattach" id="attachs"
											role="button" data-toggle="modal">附件</a></span>
									</c:if>
									</td>
									<td>
										<ul id="attachList"></ul>
									</td>
								</tr>
							</table>
							<c:if test="${!empty status}">
							<div class="table-wrap form-table" id="exchange">
								<c:forEach items="${exchangeList}" var="m">
									<span id="neirong">${m.name }:${m.content}&nbsp&nbsp&nbsp&nbsp${m.startDate}<br/>
									</span>
								</c:forEach>
								<input type="hidden" id="stat" name="stat"value="${status}">
								<input type="hidden" id="token" name="token"value="${data.token}"> 
								<input type="hidden"id="modifyDate" name="modifyDate"> 
								<input type="hidden"id="name" name="name" value="${talkName}" /> 
								<input type="hidden" id="equipmentId" name="equipmentId" value="${idd}"/>
								<input type="hidden" id="extStr1" name="extStr1" value="toa_shjfgl_equipment_inout"/>
								<textarea id="content" name="content" rows="3" cols="3"></textarea>
								<%-- <shiro:hasPermission name="user:add4"> --%>
									<button id="exchangeSubmit" class="btn"
										onclick=equipmentInOutModule.equipmentInOutForm3(false)
										type="button" class="btn">提 交</button>
								<%-- </shiro:hasPermission> --%>
							</div>
							</c:if>
						</form>
					</div>
				</div>
			</section>
			</section>
	</section>
	<!--start 上传附件  -->
	<div class="modal fade panel" id="file-edit" aria-hidden="false">
		<div class="modal-dialog w820">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
					<h4 class="modal-title">添加上传文件</h4>
				</div>
				<div class="modal-body">
				<!--业务关联表的属性值，新增附件时不需要填写或填写空字符串-->
					<!-- islogicDel 1为逻辑删除 0为物理删除-->
					<input type="hidden" id="islogicDel" value="1">
					<input type="hidden" id="iscopy"  value="0">
					<input type="hidden" name="businessId" id="businessId" value="0"/>
					<input type="hidden" name="businessSource" id="businessSource" />
					<input type="hidden" name="businessTable" id="businessTable"  value="toa_shjfgl_equipment_inout"/> 
					<!-- upload_type 1为单传  0为多传-->
	                <input type="hidden" id="upload_type" value="0"> 
	                <input type="hidden" id="isshow" value="0">
	                <input type="hidden" id="upload_div_name" value="file-edit">
					<!-- <input type="hidden" id="upload_close_callback" value="equipmentInOutModule.echoAttachList">  --> 
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button" id="closeModalBtn" onclick="showAttachList(true)"data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
	<!--end 上传附件 -->
</section>
<c:if test="${!empty id}">
<script >
$(document).ready(function(){
	var ids=(${id});
	if(ids!=null&&ids!=""){
	equipmentInOutModule.get(ids);
	}
});
function clearTabale(){
	clearTable();
}
</script>
</c:if>
<c:if test="${empty id}">
<script type="text/javascript"> 
//输入框提示代码
 var customer = ${customerList}.split('|');
	(autoComplete = {
		pop_len : 10,
		pop_cn : 'autoDis',
		hover_cn : 'cur',
		source : customer,
		init : function() {
			this.setDom();
			return this;
		},
		bind : function(x) {
			if (x.getAttribute('type') != 'text' || x.nodeName != 'INPUT')
				return null;
			var self = this;
			x.onkeyup = function(e) {
				e = e || window.event;
				var lis = self.pop.getElementsByTagName('li'), lens = self.pop
						.getElementsByTagName('li').length, n = lens, temp;
				if (e.keyCode == 38) { //键盘up键被按下 
					if (self.pop.style.display != 'none') {
						for (var i = 0; i < lens; i++) { //遍历结果数据，判断是否被选中 
							if (lis[i].className)
								temp = i;
							else
								n--;
						}
						if (n == 0) { //如果没有被选中的li元素，则选中最后一个 
							lis[lens - 1].className = self.hover_cn;
							this.value = lis[lens - 1].innerHTML;
						} else { //如果有被选中的元素，则选择上一个元素并赋值给input 
							if (lis[temp] == lis[0]) { //如果选中的元素是第一个孩子节点则跳到最后一个选中 
								lis[lens - 1].className = self.hover_cn;
								this.value = lis[lens - 1].innerHTML;
								lis[temp].className = '';
							} else {
								lis[temp - 1].className = self.hover_cn;
								this.value = lis[temp - 1].innerHTML;
								lis[temp].className = '';
							}
						}
					} else
						//如果弹出层没有显示则执行插入操作，并显示弹出层 
						self.insert(this);
				} else if (e.keyCode == 40) { //down键被按下，原理同up键 
					if (self.pop.style.display != 'none') {
						for (var i = 0; i < lens; i++) {
							if (lis[i].className)
								temp = i;
							else
								n--;
						}
						if (n == 0) {
							lis[0].className = self.hover_cn;
							this.value = lis[0].innerHTML;
						} else {
							if (lis[temp] == lis[lens - 1]) {
								lis[0].className = self.hover_cn;
								this.value = lis[0].innerHTML;
								lis[temp].className = '';
							} else {
								lis[temp + 1].className = self.hover_cn;
								this.value = lis[temp + 1].innerHTML;
								lis[temp].className = '';
							}
						}
					} else
						self.insert(this);
				} else
					//如果按下的键既不是up又不是down那么直接去匹配数据并插入 
					self.insert(this);
			};
			x.onblur = function() { //这个延迟处理是因为如果失去焦点的时候是点击选中数据的时候会发现先无法触发点击事件 
				setTimeout(function() {
					self.pop.style.display = 'none';
				}, 300);
			};
			return this;
		},
		setDom : function() {
			var self = this;
			var dom = document.createElement('div'), frame = document
					.createElement('iframe'), ul = document.createElement('ul');
			document.body.appendChild(dom);
			with (frame) { //用来在ie6下遮住select元素 
				setAttribute('frameborder', '0');
				setAttribute('scrolling', 'no');
				style.cssText = 'z-index:-1;position:absolute;left:0;top:0;'
			}
			with (dom) { //对弹出层li元素绑定onmouseover，onmouseover 
				className = this.pop_cn;
				appendChild(frame);
				appendChild(ul);
				onmouseover = function(e) { //在li元素还没有加载的时候就绑定这个方法，通过判断target是否是li元素进行处理 
					e = e || window.event;
					var target = e.srcElement || e.target;
					if (target.tagName == 'LI') { //添加样式前先把所有的li样式去掉，这里用的是一种偷懒的方式，没有单独写removeClass方法 
						for (var i = 0, lis = self.pop
								.getElementsByTagName('li'); i < lis.length; i++)
							lis[i].className = '';
						target.className = self.hover_cn; //也没有写addClass方法，直接赋值了 
					}
				};
				onmouseout = function(e) {
					e = e || window.event;
					var target = e.srcElement || e.target;
					if (target.tagName == 'LI')
						target.className = '';
				};
			}
			this.pop = dom;
		},
		insert : function(self) {
			var bak = [], s, li = [], left = 0, top = 0, val = self.value;
			for (var i = 0, leng = this.source.length; i < leng; i++) { //判断input的数据是否与数据源里的数据一致 
				if (!!val && val.length <= this.source[i].length
						&& this.source[i].substr(0, val.length) == val) {
					bak.push(this.source[i]);
				}
			}
			if (bak.length == 0) { //如果没有匹配的数据则隐藏弹出层 
				this.pop.style.display = 'none';
				return false;
			}//这个弹出层定位方法之前也是用循环offsetParent，但发现ie跟ff下差别很大（可能是使用方式不当），所以改用这个getBoundingClientRect 
			left = self.getBoundingClientRect().left
					+ document.documentElement.scrollLeft;
			top = self.getBoundingClientRect().top
					+ document.documentElement.scrollTop + self.offsetHeight;
			with (this.pop) {
				style.cssText = 'width:' + self.offsetWidth + 'px;'
						+ 'position:absolute;left:' + left + 'px;top:' + top
						+ 'px;display:none;';
				getElementsByTagName('iframe')[0].setAttribute('width',
						self.offsetWidth);
				getElementsByTagName('iframe')[0].setAttribute('height',
						self.offsetHeight);
				onclick = function(e) {
					e = e || window.event;
					var target = e.srcElement || e.target;
					if (target.tagName == 'LI')
						self.value = target.innerHTML;
					this.style.display = 'none';
				};
			}
			s = bak.length > this.pop_len ? this.pop_len : bak.length;
			for (var i = 0; i < s; i++)
				li.push('<li>' + bak[i] + '</li>');
			this.pop.getElementsByTagName('ul')[0].innerHTML = li.join('');
			this.pop.style.display = 'block';
		}
	}).init().bind(document.getElementById('autoCom')).bind(
			document.getElementById('autoC'));
</script> 
</c:if>
<script src="${sysPath}/js/com/sys/attach/attachEquipment.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/shjfgl/equipmentInOutPeople/peopleInOut.validate.js"type="text/javascript"></script>
<script src='${sysPath}/js/com/shjfgl/equipmentInOutPeople/module/peopleInOutModule.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>