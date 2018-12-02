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
		<form class="table-wrap  m-20-auto" id="customerPeopleForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">客户联系信息 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input  type="hidden"id="customerId" name="customerId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td style="width:10%;" class=" b-green-dark b-tc">
								<span class="required">*</span>公司名称</td>
							<td>
                                     <a  onclick="customerPeopleModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>         
							</td>
							<td style="width:10%;">
								<span class="required">*</span>联系人</td>
							<td style="width:45%">
								<input type="text"  style="width:100%;"id="name" name="name" />
							</td>
						</tr>
						<tr>
							<td style="width:10%;" class=" b-green-dark b-tc">性别</td>
							<td >
								<select name="extStr5" id="extStr5" style="width:100%;">
									<option value="男">男</option>
									<option value="女">女</option>
								</select>
							</td>
							<td style="width:10%;" class=" b-green-dark b-tc"><span class="required">*</span>职务</td>
							<td >
								<input type="text" name = "job" id="job" style="width:100%;"/>
							</td>
						</tr>
						<tr>
							<td style="width:10%;"><span class="required">*</span>联系方式</td>
							<td >
								<input type="text"  name="tel" id="tel" style="width:100%;"/>
							</td>
							<td><span class="required">*</span>身份证号</td>
							<td>
								<input id="idCard" type="text" style="width:100%;" name = "idCard"/>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>邮箱</td>
							<td>
								<input id="email" type="text"  name="email" style="width:100%;"/>
							</td>
							<td>
								微信/QQ
							</td>
							<td>
								<input type="text"  name="weixin" id="weixin" style="width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>
								个人喜好
							</td>
							<td>
								<input type="text" style="width:100%;" id="likee" name = "likee"/>
							</td>
							<td >是否离职</td>
							<td >
								<select id="extStr1" name="extStr1" style="width:100%;">
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>籍贯</td>
							<td>
								<input type="text" style="width:100%;" id="placeOrigin" name ="placeOrigin"/>
							</td>
							<td><span class="required">*</span>是否结婚</td>
							<td>
								<select id="marriagePeople" name="marriagePeople" style="width:100%;">
									<option value="">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>子女情况</td>
							<td>
								<input type="text" style="width:100%;" id="childrenSituation" name ="childrenSituation"/>
							</td>
							<td><span class="required">*</span>部门</td>
							<td>
								<input type="text" style="width:100%;" id="departmentPeople" name ="departmentPeople"/>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>客户入职年份</td>
							<td>
								<input type="text" style="width:100%;" id="customerYear" name ="customerYear"/>
							</td>
							<td><span class="required">*</span>是否有决策采购权</td>
							<td>
								<select id="decisionMaking" name="decisionMaking" style="width:100%;">
									<option value="">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</td>
						</tr>
						<tr>
							<td >离职去向</td>
							<td >
								<input type="text"   id="leaveJob" name="leaveJob" style="width:100%;"/>
							</td>
							<td >备注说明</td>
							<td>
								<textarea   id="remark" name="remark" style="width:100%;"></textarea>
							</td>
						</tr>
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark"  onclick="customerPeopleModule.saveOrModify(true)">提  交</a>
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
                <form class="table-wrap  m-20-auto" id="customerForm">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td  class=" b-green-dark b-tc">
								公司名称
							</td>
							<td>
								<input type = "text" id="companyName1" name="companyName1" />
					            <button class="btn dark" type="button" onclick="customerPeopleModule.getWorkTask()">查 询</button>
					            <button class="btn" type="button" onclick="customerPeopleModule.queryReset()">重 置</button>
							</td>
						</tr>
						</tbody>
					</table>
				</form>
                <div class="modal-body">
                    <section class="panel-tab-con">
                        <div class="tab-content">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                    <table class="table table-striped frist-td-tc" id="issuedTaskTable">
                                        <thead>
                                            <th class="w46">请选择</th>
                                            <th>公司名称</th>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
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
<c:if test="${!empty oldId}">
<script >
$(document).ready(function(){
	var ids=(${oldId});
	if(ids!=null&&ids!=""){
		customerPeopleModule.get(ids);
	}
});
function clearTabale(){
	clearTable();
}
</script>
</c:if>		

<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/shyt/customerPeople/customerPeopleModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/shyt/customerPeople/customerPeople.validate.js"type="text/javascript"></script>