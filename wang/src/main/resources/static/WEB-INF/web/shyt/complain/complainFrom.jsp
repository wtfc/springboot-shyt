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
		<form class="table-wrap  m-20-auto" id="complainForm">
			<h3 class=" tc" style="font-size:20px;margin:0;border:0;">客户投诉信息 </h3>
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input  type="hidden"id="customerId" name="customerId" />
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td style="width:10%;" class=" b-green-dark b-tc">
								<span class="required">*</span>公司名称</td>
							<td >
								<a  onclick="complainModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName" /></a>
							</td>
							<td style="width:10%;">
								<span class="required">*</span>被投诉部门</td>
							<td style="width:45%">
								<div class="input-group inline-tree">
									<input type="text" id="partment" style="width:100%;" name="partment" readonly /><input
										type="hidden" id="deptId" name="deptId" /><a
										class="btn btn-file input-group-btn" href="#"
										id="showDeptTree" role="button" data-toggle="modal"><i
										class="fa fa-group"></i></a>
								</div>
								<p class="hide" id="deptError" style="color:red;">此信息不能为空</p>
							</td>
						</tr>
						<tr>
							<td style="width:10%;" class=" b-green-dark b-tc"><span class="required">*</span>投诉日期</td>
							<td >
								<input data-date-format="yyyy-MM-dd HH:mm:ss"data-pick-time="ture" class="datepicker-input" type="text" name = "complainDate" id="complainDate" style="width:100%;"/>
							</td>
							<td style="width:10%;"><span class="required">*</span>投诉类型</td>
							<td >
								<input type="text"  name="complainStatus" id="complainStatus" style="width:100%;"/>
							</td>
						</tr>
						<tr>
							<td><span class="required">*</span>是否解决</td>
							<td>
								<select style="width:100%;" name = "status" id="status">
									<option value="">请选择</option>
									<option value="是">是</option>
									<option value="否">否</option>
							 	</select>
							</td>
							<td>解决方案</td>
							<td>
								<textarea id="program"  style="width:100%;" name = "program"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								备注
							</td>
							<td colspan="3">
								<textarea  style="width:100%;" id="remark" name = "remark"></textarea>
							</td>
						</tr>
						</tbody>
					</table>
				   	<section class="clearfix" id="footer_height">
						<section class="form-btn fl m-l">
							<a class="btn dark" role="button" id="addEatOutregButton" onclick="complainModule.saveOrModify(true)">提  交</a>
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
					            <button class="btn dark" type="button" onclick="complainModule.getWorkTask()">查 询</button>
					            <button class="btn" type="button" onclick="complainModule.queryReset()">重 置</button>
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
<c:if test="${!empty oldId}">
<script >
$(document).ready(function(){
	var ids=(${oldId});
	if(ids!=null&&ids!=""){
	complainModule.get(ids);
	}
});
</script>
</c:if>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/shyt/complain/complainModule.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/userDeptTree.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/shyt/complain/complain.validate.js"type="text/javascript"></script>