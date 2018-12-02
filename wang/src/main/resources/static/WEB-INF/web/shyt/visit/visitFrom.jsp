<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
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
		<form class="table-wrap  m-20-auto" id="visitForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">客户回访信息 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input  type="hidden"id="customerId" name="customerId" />
				<table class="table table-td-striped">
					<tbody>
						<!-- <tr>
							<td style="width:10%;">
								<span class="required">*</span>公司名称</td>
							<td >
								<a onclick="visitModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>
							</td>
							<td style="width:10%;">
								<span class="required">*</span>回访类型</td>
							<td >
								<select id="visitStatus" name="visitStatus" style="width:100%;">
									<option value="">请选择</option>
									<option value="上门">上门</option>
									<option value="非上门">非上门</option>
								</select>
							</td>
							<td style="width:10%;"><span class="required">*</span>回访日期</td>
							<td >
								<input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  type="text" name = "visitDate" id="visitDate" style="width:100%;"/>
							</td>
						</tr>
						<tr>
							<td style="width:10%;">消费费用金额(元)</td>
							<td >
								<input type="text"  name="visitPay" id="visitPay" style="width:100%;"/>
							</td>
							<td style="width:10%;">购买礼品金额(元)</td>
							<td >
								<input type="text"  name="visitPrice" id="visitPrice" style="width:100%;"/>
							</td>
							<td>
								<span><a class="btn dark" type="button"
										href="#file-edit" name="queryattach" id="attachs"
										role="button" data-toggle="modal" onclick="clearTabale()">回访表格</a></span>
							</td>
							<td>
								<ul id="attachList"></ul>
							</td>
						</tr>
						<tr>
							<td>问题是否解决</td>
							<td>
								<select style="width:100%;"id="extStr1" name ="extStr1">
									<option value="是">是</option>
									<option value="否">否</option>
								</select>
							</td>
							<td><span class="required">*</span>回访所涉及部门</td>
							<td style="width:25%">
								<input style="width:80%" id="status" type="text" readonly name ="status" onclick="aaa()"/> 
								<div style="display:none;" id="controlTree" >
								</div>
									<input style="width:20%" class="btn dark button fr" type="button" value="选择" onclick="bb()" />
							</td>
							<td>陪同人员</td>
							<td>
								<input id="visitPeople" type="text" onFocus="selectControl.singlePerson(this.id, true, 'visitModule.spCall', eval($('#userJson').val()));" style="width:100%;" name = "visitPeople"/>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>回访内容</td>
							<td colspan="5">
								<textarea id="visit" onclick="aaa()" style="width:100%;" name = "visit"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<span class="required">*</span>回访反馈
							</td>
							<td colspan="5">
								<textarea  style="width:100%;" id="remark" name = "remark"></textarea>
							</td>
						</tr> -->
						<tr>
							<td colspan="8" style="text-align: left;background-color: green;">
								<span>基本信息</span>
							</td>
						</tr>
					  	<tr>
					  		<td style="width:10%;"><span class="required">*</span>客户公司名称</td>
							<td colspan="3" style="width: 40%;">
								<a onclick="visitModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>
							</td>
							<td style="width:10%;"><span class="required">*</span>客户公司地址</td>
							<td colspan="3" style="width: 40%;">
								<input type="text" id="companyAddress" name="companyAddress" style="width:100%;"/>
							</td>
					  	</tr>
					  	
						  <tr>
						  	<td style="width:10%;"><span class="required">*</span>回访时间</td>
							<td>
								<input data-date-format="yyyy-MM-dd" data-pick-time="false" class="datepicker-input"  type="text" name="visitDate" id="visitDate" style="width:100%;"/>
							</td>
							<td style="width:10%;"><span class="required">*</span>回访方式</td>
							<td>
								<select id="visitMode" name="visitMode">
									<option value="">请选择</option>
									<option value="上门">上门</option>
									<option value="qq">qq</option>
									<option value="电话">电话</option>
									<option value="邮箱">邮箱</option>
								</select>
							</td>
							<td style="width:10%;"><span class="required">*</span>回访人姓名</td>							
						  	<td>
						  		<input type="text" id="returnName" name="returnName" style="width:100%;"/>
						  	</td>
						  	<td style="width:10%;"><span class="required">*</span>回访人部门</td>
						  	<td>
						  		<input type="text" id="returnDept" name="returnDept" style="width:100%;"/>
						  	</td> 
						  </tr>
						  	
						  <tr>
						  	<td style="width:10%;"><span class="required">*</span>回访类型</td>
							<td>
								<select id="visitStatus" name="visitStatus" style="width:100%;">
									<option value="">请选择</option>
									<option value="新签约">新签约</option>
									<option value="目标客户">目标客户</option>
									<option value="客户联系人员变动">客户联系人员变动</option>
									<option value="故障">故障</option>
									<option value="满意度">满意度</option>
									<option value="投诉">投诉</option>
									<option value="日常关系维护">日常关系维护</option>
								</select>
							</td>
						  	<td style="width:10%;"><span class="required">*</span>客户受访人</td>
						  	<td>
						  		<input type="text" id="personName" name="personName" style="width:100%;" />
						  	</td>
						  	<td style="width:10%;"><span class="required">*</span>职位</td>
						  	<td>
						  		<input type="text" id="position" name="position" style="width:100%;"/>
						  	</td>
						  	<td style="width:10%;"><span class="required">*</span>联系方式</td>
						  	<td>
						  		<input type="text" id="contactWay" name="contactWay" style="width:100%;"/>
						  	</td>
						  </tr>
						  <tr>
						  	<td style="width:10%;">协作部门</td>
							<td style="width:25%">
								<input style="width:80%" id="status" type="text" readonly name ="status" onclick="aaa()"/> 
								<div style="display:none;" id="controlTree" ></div>
								<input style="width:20%" class="btn dark button fr" type="button" value="选择" onclick="bb()" />
							</td>
							<td style="width:10%;"><span class="required">*</span>协作人</td>
							<td>
								<input id="visitPeople" type="text" onFocus="selectControl.singlePerson(this.id, true, 'visitModule.spCall', eval($('#userJson').val()));" style="width:100%;" name = "visitPeople"/>
							</td>
							<td style="width:10%;"><span class="required">*</span>回访是否完成</td>
							<td>
								<select id="visitIsReturn" name="visitIsReturn">
									<option value="是">是</option>
									<option value="否">否</option>
								</select>
							</td>
							<td style="width:10%;">费用(元)</td>
							<td>
								<input type="text"  name="visitPay" id="visitPay" style="width:100%;"/>
							</td>
						  </tr>
						  <tr>
						  	<td>
								<span><a class="btn dark" type="button" href="#file-edit" name="queryattach" id="attachs" role="button" data-toggle="modal" onclick="clearTabale()">回访表格</a></span>
							</td>
							<td>
								<ul id="attachList"></ul>
							</td>
						  </tr>
						  
						  <tr>
							<td colspan="8" style="text-align: left;background-color: green;">
								<span>具体跟进项目</span>
							</td>
						  </tr>
						  <tr>
						  	<td colspan="2" style="text-align: left;font-weight:normal;">主要访问/沟通内容</td>
						  	<td colspan="2">回访发现问题</td>
						  	<td colspan="2" style="text-align: left;font-weight:normal;">解决方案</td>
						  	<td colspan="2">客户评价</td>
						  </tr>
						  <tr>
						  	<td colspan="2">
						  		<textarea id="contentMain" name="contentMain"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="visitFind" name="visitFind"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="solutionReplation" name="solutionReplation"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="customerJudge" name="customerJudge"></textarea>
						  	</td>
						  </tr>
						  <tr>
						  	<td colspan="2">
						  		<textarea id="contentAccess" name="contentAccess"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="visitProblem" name="visitProblem"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="solutionDispose" name="solutionDispose"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="customerEvaluation" name="customerEvaluation"></textarea>
						  	</td>
						  </tr>
						  <tr>
						  	<td colspan="2">
						  		<textarea id="contentComment" name="contentComment"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="visitTheProblem" name="visitTheProblem"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="solutionSlove" name="solutionSlove"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="customerReviews" name="customerReviews"></textarea>
						  	</td>
						  </tr>
						  
						   <tr>
							<td colspan="8" style="text-align: left;background-color: green;">
								<span>客户建议或意见</span>
							</td>
						  </tr>
						  <tr>
						  	<td colspan="2" style="text-align: left;font-weight:normal;">内容</td>
						  	<td>涉及部门</td>
						  	<td colspan="2" style="text-align: left;font-weight:normal;">解决方案</td>
						  	<td>是否回复客户</td>
						  	<td colspan="2" style="text-align: left;font-weight:normal;">客户反馈</td>
						  </tr>
						  <tr>
						  	<td colspan="2">
						  		<textarea id="substanceOne" name="substanceOne"></textarea>
						  	</td>
						  	<td>
						  		<textarea id="involvoedBranch" name="involvoedBranch"></textarea> 
						  	</td>
						  	<td colspan="2">
						  		<textarea id="schemePlan" name="schemePlan"></textarea>
						  	</td>
						  	<td>
						  		<!-- <textarea id="replyAnswer" name="replyAnswer"></textarea> -->
						  		<select id="replyAnswer" name="replyAnswer">
						  			<option value="">请选择</option>
						  			<option value="是">是</option>
						  			<option value="否">否</option>
						  		</select>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="customTickling" name="customTickling"></textarea>
						  	</td>
						  </tr>
						  <tr>
						  	<td colspan="2">
						  		<textarea id="substanceTwo" name="substanceTwo"></textarea>
						  	</td>
						  	<td>
						  		<textarea id="involvoedDivision" name="involvoedDivision"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="schemeBlue" name="schemeBlue"></textarea>
						  	</td>
						  	<td>
						  		<!-- <textarea id="replyRestore" name="replyRestore"></textarea> -->
						  		<select id="replyRestore" name="replyRestore">
						  			<option value="">请选择</option>
						  			<option value="是">是</option>
						  			<option value="否">否</option>
						  		</select>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="customFeed" name="customFeed"></textarea>
						  	</td>
						  </tr>
						  <tr>
						  	<td colspan="2">
						  		<textarea id="substanceThree" name="substanceThree"></textarea>
						  	</td>
						  	<td>
						  		<textarea id="involvoedSection" name="involvoedSection"></textarea>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="schemeProject" name="schemeProject"></textarea>
						  	</td>
						  	<td>
						  		<!-- <textarea id="replyReflext" name="replyReflext"></textarea> -->
						  		<select id="replyReflext" name="replyReflext">
						  			<option value="">请选择</option>
						  			<option value="是">是</option>
						  			<option value="否">否</option>
						  		</select>
						  	</td>
						  	<td colspan="2">
						  		<textarea id="customCouple" name="customCouple"></textarea>
						  	</td>
						  </tr>
						   <tr>
							<td colspan="8" style="text-align: left;background-color: green;">
								<span>客户业务动态(客户资源放置比例.竞争对手名称.竞争对手优势.客户最近项目情况.新项目情况等)</span>
							</td>
						  </tr>
						  <tr>
						  	<td colspan="8">
						  		<textarea id="serviceStatus" name="serviceStatus"></textarea>
						  	</td>
						  </tr>
						  <tr>
							<td colspan="8" style="text-align: left;background-color: green;">
								<span>客户意见</span>
							</td>
						  </tr>
						  <tr>
						  	<td colspan="8">
						  		<textarea id="extStr5" name="extStr5"></textarea>
						  	</td>
						  </tr>
						  
						</tbody>
					</table>
					<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<shiro:hasPermission name="visitSave"><a class="btn dark" role="button"  onclick="visitModule.saveOrModify(true)">提  交</a></shiro:hasPermission>
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
					            <button class="btn dark" type="button" onclick="visitModule.getWorkTask()">查 询</button>
					            <button class="btn" type="button" onclick="visitModule.queryReset()">重 置</button>
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
					<input type="hidden" name="businessTable" id="businessTable"  value="toa_shyt_visit"/> 
					<!-- upload_type 1为单传  0为多传-->
	                <input type="hidden" id="upload_type" value="0"> 
	                <input type="hidden" id="isshow" value="0">
	                <input type="hidden" id="upload_div_name" value="file-edit">
					<!-- <input type="hidden" id="upload_close_callback" value="equipmentInOutModule.echoAttachList">  --> 
					<%@ include file="/WEB-INF/web/attach/attachManage.jsp"%>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn dark" type="button"  id="closeModalBtn" onclick="showAttachList(true)" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>
	</div>
	<!--end 上传附件 -->
<c:if test="${!empty oldId}">
<script >
$(document).ready(function(){
	//selectControl.init("controlTree", "status-status", false, false, 0);	//组织多选
	var ids=(${oldId});
	if(ids!=null&&ids!=""){
	visitModule.get(ids);
	}
});
function clearTabale(){
	clearTable();
}
</script>
</c:if>
<script>
function aaa(){
	var eData = returnValue('status-statuss');
	var node="";
	$(eData.split(",")).each(function(i, v){
		 node = v.split(":")[1]+","+node;
	});
	$("#status").val(node);
}
function bb(){
	selectControl.org.open(true,'tree1','myTree1','status-statuss','0');
	$("#status").val("");
}
</script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/shyt/visit/visitModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/shyt/visit/visit.validate.js"type="text/javascript"></script>