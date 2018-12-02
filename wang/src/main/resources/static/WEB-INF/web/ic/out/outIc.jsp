<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/lib/common/outSideUserForPhone.js" type="text/javascript"></script>
<script type="text/javascript">
//根据权限判断是否显示外部人员选择树
	function getPhoneUserButtons(itemId) {
		var add = "<shiro:hasPermission name='contacts:add'><a class='btn btn-file no-all input-group-btn' id='"+itemId+"Div' role='button' data-toggle='modal'><i class='fa fa-users'></i></a></shiro:hasPermission>";
		return add;
};
function getOutSideUserAdd(phone,nameIndex) {
	var add = "<shiro:hasPermission name='contacts:add'><img style='cursor:hand;vertical-align: middle;margin-top:-1px' src="+getRootPath()+"/img/add.png width=11 height=11 onclick=outSideUser.showOutSideLayer(0,'"+phone+"','"+nameIndex+""+phone+"') ></shiro:hasPermission>";
	return add;
};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
	    <div class="con-heading fl" id="navigationMenu">
	       <h1></h1>
	       <div class="crumbs"></div>
	    </div>
	</header>
	<section class="panel m-t-md">
	    <h2 class="panel-heading clearfix">
	        	发短信
	    </h2>
	    <input type="hidden" id="token" name="token" value="${token}">
	    <form class="table-wrap form-table" id = "outForm">
	        <!--start 发短信-->
	        	<input type="hidden" id="id" name="id" value="0">
	            <input type="hidden" id="modifyDate" name="modifyDate">
	            <table class="table table-td-striped">
	                <tbody>
	                    <tr>
	                        <td class="w140">内部收信人</td>
	                        <td>
	                        	<div id="controlTree"></div>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="w140">外部收信人</td>
	                        <td>
		                        <span outSideUser="true" name="outIcArea" id="outIcAreaValid">
							   </span>
								<input type="hidden" id="outLinkman" name="outLinkman" class="outLinkman">
	                        </td>
	                    </tr>
	                    <tr>
	                        <td><span class="required">*</span>内容</td>
	                        <td><textarea id="text" name="text"></textarea>
	                        <div>您还可以输入<span id="count"></span>字。短信按70个字为一条计费。 </div>
						 </td>
	                    </tr>
	                    <tr>
	                        <td><span class="required">*</span>类型</td>
	                        <td>
	                        <div style="width:24%">
	                        <dic:select name="sendType" id="sendType" dictName="sendType" headName="-请选择-" headValue="" defaultValue=""/>
	                           <!--  <select id="sendType" name="sendType" style="width:24%">
	                            
	                            </select> --></div>
	                        </td>
	                    </tr>
	                   <!--此处为定时发送暂时去掉，如有需要在去掉注释start-->
	                   <%--  <tr>
	                        <td>定时发送</td>
	                        <td>
	                            <div class="input-group">
	                                <div class="input-group-btn p-r">
	                                    <label class="checkbox inline">
	                                        <input type="checkbox" id="s-dsfs">开启
	                                    </label>
	                                </div>
	                                <input type="hidden" name="smmscheduler" id="smmscheduler">
	                                <input class="datepicker-input"  data-pick-time="true" data-date-format="yyyy-MM-dd HH:mm:ss" id="dsfs" name="sentDate" data-start-date="${today}" disabled>
	                            </div>
	                        </td>
	                    </tr> --%>
	                     <!--此处为定时发送暂时去掉，如有需要去掉注释end-->
	                    <tr>
	                        <td>其他选项</td>
	                        <td>
	                            <label class="checkbox inline">
	                                <input type="checkbox" name="addName" id="addName">附带个人姓名
	                            </label>
	                        </td>
	                    </tr>
	                </tbody>
	            </table>
	            <!--end 发短信-->
	             <!--start 按钮-->
	             <section class="form-btn m-b-lg">
	                 <button class="btn dark query-jump" type="button" id="sendMessage">发 送</button>
	                 <!-- <button class="btn" type="button" id="formReset">重 置</button> -->
	             </section>
	             <!--end 按钮-->
	      </form>
	      <div id="outSideUserTreeDiv"></div>
	 </section>
</section>
<%-- <script src="${sysPath}/js/file-input/bootstrap-filestyle.min.js"></script> --%>
<%-- <script type='text/javascript' src='${sysPath}/js/lib/common/common.js'></script> --%> 
<script src="${sysPath}/js/com/ic/out/outIc.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/out/outIc.validate.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/contacts/contactsInteract.validate.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
   