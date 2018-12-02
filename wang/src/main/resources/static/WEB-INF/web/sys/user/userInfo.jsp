<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<script src="${sysPath}/js/com/sys/user/userInfo.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/userInfo.validate.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="navigationMenu">
	<h1></h1>
	<div class="crumbs"></div>
</header>
<section class="panel m-t-md search-box clearfix">
	<h2 class="panel-heading clearfix">个人信息</h2>
	<form class="table-wrap form-table" id="userInfoForm">
    	<input type="hidden" id="id" name="id" value="0">
        <input type="hidden" id="token" name="token" value="0">
        <input type="hidden" id="modifyDate" name="modifyDate">
		<table class="table table-td-striped">
			<tbody>
				<tr>
					<td class="w240">登录名称</td>
					<td>
                    	<input type="text" id="loginName" name="loginName" readonly="readonly" style="width:30%;"/>
					</td>
				</tr>
				<tr>
					<td>显示名称</td>
					<td>
						<input type="text" id="displayName" name="displayName" readonly="readonly" style="width:30%;"/>
					</td>
				</tr>
                <tr>
                	<td>用户头像</td>
                    <td>
                    	   <section class="dis-table-cell w105" >
                                <img src="${sysPath}/images/demoimg/iphoto.jpg" id="userPhoto" height="105" />
                                <input type="hidden" id="photo" name="photo" />
                                <div class="form-btn">
                                    <button class="btn dark w105" type="button" id="uploadPhoto">上传头像</button>
                                </div>
                            </section>
                    </td>
                </tr>
				<tr>
					<td>出生日期</td>
			  <td>
					<input id="birthday" name = "birthday" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" style="width:30%;"/>
				</td>
			  </tr>
				<tr>
					<td>民族</td>
					<td>
						<dic:select name="ethnic" id="ethnic" dictName="ethnic" headName="-请选择-" headValue="" defaultValue="" cssStyle="width:30%;"/>
					</td>
				</tr>
				<tr>
				<td>用户手机</td>
		    	<td>
		    		<input type="text" id="mobile" name = "mobile" style="width:30%;"/>
		    	</td>
		    	</tr>
                <tr>
				<td>集团号码</td>
		    	<td>
		    		<input type="text" id="groupTel" name = "groupTel" style="width:30%;"/>
		    	</td>
		    	</tr>
				<tr>
				<td>用户邮箱</td>
				<td>
					<input type="text" id="email" name = "email" style="width:30%;"/>
				</td>
				</tr>
				<tr>
					<td>办公地点</td>
					<td>
						<input type="text" id="officeAddress" name="officeAddress" style="width:30%;"/></td>
				</tr>
				<tr>
					<td>办公电话</td>
					<td>
						<input type="text" id="officeTel" name = "officeTel" style="width:30%;"/>
					</td>
				</tr>
				<tr>
					<td>入党日期</td>
					<td>
						<input id="entryPoliticalDate" name="entryPoliticalDate" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd" style="width:30%;"></td>
				</tr>
                <tr>
					<td>婚姻状况</td>
					<td>
                     <dic:select name="maritalStatus" id="maritalStatus" dictName="maritalStatus" headName="-请选择-" headValue="" defaultValue="" cssStyle="width:30%;"/>
					</td>
				</tr>
                <tr>
					<td>籍贯</td>
					<td>
                    	<input type="text" id="hometown" name="hometown" style="width:30%;"/>
					</td>
				</tr>
                <tr>
					<td>学历</td>
					<td>
                    	<dic:select name="degree" id="degree" dictName="degree" headName="-请选择-" headValue="" defaultValue="" cssStyle="width:30%;"/>
					</td>
				</tr>
                <tr>
					<td>密码提示问题</td>
					<td>
                    	<dic:select name="question" id="question" dictName="question" headName="-请选择-" headValue="" defaultValue="" cssStyle="width:30%;"/>
					</td>
				</tr>
                <tr>
					<td>密码问题答案</td>
					<td>
                    	<input type="text" id="answer" name = "answer" style="width:30%;">
					</td>
				</tr>
                <tr>
					<td>开户银行</td>
					<td>
                    	<input type="text" id="cardBank" name = "cardBank" style="width:30%;"/>
					</td>
				</tr>
                <tr>
					<td>开户姓名</td>
					<td>
                    	<input type="text" id="cardName" name = "cardName" style="width:30%;"/>
					</td>
				</tr>
                <tr>
					<td>卡号</td>
					<td>
                    	<input type="text" id="payCardNo" name = "payCardNo" style="width:30%;"/>
					</td>
				</tr>
			</tbody>
		</table>
		<section class="form-btn m-b-lg">
			<button class="btn dark ok" type="button" id="saveBtn">保 存</button>
		</section>
	</form>
   </section>
</section>
 <div class="modal fade panel" id="myModal2" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" id="closebtn" data-dismiss="modal">×</button>
                    <h4 class="modal-title">上传头像</h4>
                </div>
                <div class="modal-body"> 
                    <!-- upload_type 1为单传  0为多传-->
                    <input type="hidden" id="upload_type" value="1"> 
                    <%@ include file="/WEB-INF/web/sys/user/userAttach.jsp"%>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button"  data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>