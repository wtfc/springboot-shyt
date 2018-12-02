<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

    <div class="modal fade panel" id="myModal-edit" aria-hidden="false">
        <div class="modal-dialog w900">
        	<form class="table-wrap form-table" id="userForm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title" id="actionTitle">编辑</h4>
                </div>
                <div class="modal-body">
                <section class="dis-table">
                    <section class="dis-table-cell w105" >
                        <img src="${sysPath}/images/demoimg/iphoto.jpg" id="userPhoto" height="105">
                        <input type="hidden" id="photo" name="photo" />
                        <div class="form-btn">
                            <button class="btn dark w105" type="button" id="uploadPhoto">上传头像</button>
                        </div>
                    </section>
                    <section class="panel-tab-con dis-table-cell">
                        <!-- tabs -->
                        <section class="tabs-wrap tabs-wrap-in">
                            <!-- 同时添加 tabs-wrap-in 类-->
                            <ul class="nav nav-tabs">
                                <li class="active" id="m1">
                                    <a href="#messages1" data-toggle="tab">基本信息</a>
                                </li>
                                <!-- 根据a标签href属性相对应的id，切换tab-pane -->
                                <li id="m2">
                                    <a href="#messages2" data-toggle="tab">其他信息</a>
                                </li>
                                <li id="m3">
                                    <a href="#messages3" data-toggle="tab">用户角色</a>
                                </li>
                               
                            </ul>
                        </section>
                        <!-- tabs end -->
                        <!-- tabs模块下紧接panel模块 -->
                            <div class="tab-content" style="overflow:hidden;">
                                <div class="tab-pane for fade active in" id="messages1">
                                    <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                    
                                    	<input type="hidden" id="id" name="id" value="0">
                                        <input type="hidden" id="token" name="token" value="${data.token}">
                                        <input type="hidden" id="modifyDate" name="modifyDate">
                                    <div class="table-wrap form-table">
                                        <table class="table table-td-striped">
                                            <tbody>
                                                <tr>
                                                    <td style="width:15%;" class=" b-green-dark b-tc">用户状态</td>
                                                    <td style="width:35%;">
                                                    	<input type="hidden" id="statusOld" name="statusOld" />
                                                        <select name="status" id="status" class="valid">
                                                        	<option value="status_0" selected="">启用</option>
                                                            <option value="status_2">锁定</option>
                                                        </select>
                                                    </td>
                                                    <td style="width:15%;">用户编号</td>
                                                    <td>
                                                        <input type="text" id="code" name="code"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><span class="required">*</span>登录名称</td>
                                                    <td>
                                                        <input type="text" id="loginName" name="loginName"/>
                                                        <input type="hidden" id="loginNameOld" name="loginNameOld"/>
                                                    </td>
                                                    <td><span class="required">*</span>显示名称</td>
                                                    <td>
                                                        <input type="text" id="displayName" name="displayName"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>用户性别</td>
                                                    <td>
                                                    	<dic:select name="sex" id="sex" dictName="sex" headName="-请选择-" headValue="" defaultValue=""/>
                                                        
                                                    </td>
                                                    <td>用户性质</td>
                                                    <td>
                                                        <dic:select name="kind" id="kind" dictName="kind" headName="-请选择-" headValue="" defaultValue=""/>
                                                    </td>
                                                </tr>
												<tr>
													<td>
														<span class="required">*</span>部门
													</td>
													<td>
														<div class="input-group inline-tree">
															<input type="text" id="deptName" name="deptName" readonly /><input
																type="hidden" id="deptId" name="deptId" /><a
																class="btn btn-file input-group-btn" href="#"
																id="showDeptTree" role="button" data-toggle="modal"><i
																class="fa fa-group"></i></a>
														</div>
														<p class="hide" id="deptError" style="color:red;">此信息不能为空</p>
													</td>
													<!--123<td><input id="password" name="password" type="text" data-trigger="manual" data-placement="top" data-animation="false" /></td>-->
													<td>职务</td>
													<td><dic:select name="dutyId" id="dutyId" dictName="dutyId" headName="-请选择-" headValue="" defaultValue="" />
													</td>
												</tr>
												<tr>
                                                    <td>级别</td>
                                                    <td>

                                                        <dic:select name="level" id="level" dictName="level" headName="-请选择-" headValue="" defaultValue=""/>
                                                    </td>
                                                    <td>职称</td>
                                                    <td>

                                                        <dic:select name="jobTitle" id="jobTitle" dictName="jobTitle" headName="-请选择-" headValue="" defaultValue=""/>
                                                       <%--  <dic:checkbox name="room" id="room" dictName="room"   defaultValue="true"/> --%>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><span class="required">*</span>序号</td>
                                                    <td><input type="text" id="orderNo" name = "orderNo"/></td>
                                                    <td>入职时间</td>
                                                    <td><input class="datepicker-input" type="text" id="entryDate" name="entryDate" data-date-format="yyyy-MM-dd" ></td>
                                                </tr>
                                                <tr>
                                                    <td>直接领导</td>
                                                    <td><div id="leaderIdDiv"></div></td>
                                                    <td>部门领导</td>
                                                    <td><div id="deptLeaderDiv"></div></td>
                                                </tr>
                                                <tr>
                                                    <td>分管领导</td>
                                                    <td><div id="chargeLeaderDiv"></div></td>
                                                    <td>政治面貌</td>
                                                    <td>

                                                        <dic:select name="political" id="political" dictName="political" headName="-请选择-" headValue="" defaultValue=""/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>身份证号码</td>
                                                    <td><input type="text" id="cardNo" name = "cardNo"/></td>
                                                    <td>&nbsp;</td>
                                                  <td></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="messages2">
                                    <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                        <div class="table-wrap form-table">
                                            <table class="table table-td-striped">
                                                <tbody>
                                                    <tr>
                                                        <td style="width:18%;">出生日期</td>
                                                        <td style="width:30%;">
                                                            <input id="birthday" name = "birthday" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd">
                                                        </td>
                                                        <td style="width:18%;">民族</td>
                                                        <td>
                                                           
                                                            <dic:select name="ethnic" id="ethnic" dictName="ethnic" headName="-请选择-" headValue="" defaultValue=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>用户手机</td>
                                                        <td>
                                                            <input type="text" id="mobile" name = "mobile"/>
                                                            <input type="hidden" id="mobileOld" name = "mobileOld"/>
                                                        </td>
                                                        <td>集团号码</td>
                                                        <td>
                                                            <input type="text" id="groupTel" name = "groupTel"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                    	<td>用户邮箱</td>
                                                        <td>
                                                            <input type="text" id="email" name = "email"/>
                                                        </td>
                                                        <td>办公地点</td>
                                                        <td>
                                                            <input type="text" id="officeAddress" name="officeAddress"/>
                                                        </td>
                                                        
                                                    </tr>
                                                    <tr>
                                                    	<td>办公电话</td>
                                                        <td>
                                                            <input type="text" id="officeTel" name = "officeTel"/>
                                                        </td>
                                                        <td>入党日期</td>
                                                        <td><input id="entryPoliticalDate" name="entryPoliticalDate" class="datepicker-input" type="text" data-date-format="yyyy-MM-dd"></td>
                                                        
                                                    </tr>
                                                    <tr>
                                                    	<td>婚姻状况</td>
                                                        <td>

                                                             <dic:select name="maritalStatus" id="maritalStatus" dictName="maritalStatus" headName="-请选择-" headValue="" defaultValue=""/>
                                                        </td>
                                                        <td>籍贯</td>
                                                        <td>
                                                            <input type="text" id="hometown" name="hometown"/>
                                                        </td>
                                                        
                                                    </tr>
                                                    <tr>
                                                    	<td>学历</td>
                                                        <td>

                                                            <dic:select name="degree" id="degree" dictName="degree" headName="-请选择-" headValue="" defaultValue=""/>
                                                        </td>
                                                        <td>密码提示问题</td>
                                                        <td>

                                                            <dic:select name="question" id="question" dictName="question" headName="-请选择-" headValue="" defaultValue=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>密码问题答案</td>
                                                        <td><input type="text" id="answer" name = "answer"></td>
                                                        <td>开户银行</td>
                                                        <td><input type="text" id="cardBank" name = "cardBank"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td>开户姓名</td>
                                                        <td><input type="text" id="cardName" name = "cardName"/></td>
                                                         <td>卡号</td>
                                                        <td><input type="text" id="payCardNo" name = "payCardNo"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td>司机</td>
                                                        <td>
                                                            <label class="radio inline">
                                                                <input type="radio" id="isDriver" name = "isDriver" value="1"/>是
                                                            </label>
                                                            <label class="radio inline">
                                                                <input type="radio" id="isDriver" name = "isDriver" checked value="0"/>否
                                                            </label>
                                                        </td>
                                                        <td>是否是领导</td>
                                                        <td>
                                                            <label class="radio inline">
                                                                <input type="radio" id="isLeader" name = "isLeader" value="1" class="leaderCla"/>是
                                                            </label>
                                                            <label class="radio inline">
                                                                <input type="radio" id="isLeader" name = "isLeader" checked value="0" class="leaderCla"/>否
                                                            </label>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>机构管理员</td>
                                                        <td>
                                                            <label class="radio inline">
                                                                <input type="radio" name="isAdmin" value="1" id="showAdminTree_t"/> 
                                                                <a  href="#" role="button" data-toggle="modal" id="showAdminTree">是</a>
                                                            </label>
                                                            <label class="radio inline">
                                                                <input type="radio" name="isAdmin" checked value="0"/>否
                                                            </label>
                                                            <input type="hidden" name="adminSideV" id="adminSideV">
                                                        </td>
                                                        <td><div class="openCaleTd" style="display:none">是否公开日程</div></td>
                                                        <td>
                                                            <div class="openCaleDiv" style="display:none">
                                                            <label class="radio inline">
                                                                <input type="radio" name="openCale" value="1"/>是
                                                            </label>
                                                            <label class="radio inline">
                                                                <input type="radio" name="openCale" checked value="0"/>否
                                                            </label>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                   <!--  <tr>
                                                        <td>是否考勤</td>
                                                        <td>
                                                            <label class="radio inline">
                                                                <input type="radio" name="isCheck" value="1" checked/> 
                                                                是
                                                            </label>
                                                            <label class="radio inline">
                                                                <input type="radio" name="isCheck" value="0"/>否
                                                            </label>
                                                        </td>
                                                        <td></td>
                                                        <td></td>
                                                    </tr> -->
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="m-t m-b">
                                        	<strong class="m-r-sm">是否添加其他部门</strong>
                                            <label class="radio inline m-l m-t-n-xs">
                                                <input type="radio" name="isOtherDept" value="1" class="isOtherCla"/> 
                                                是
                                            </label>
                                            <label class="radio inline m-t-n-xs">
                                                <input type="radio" name="isOtherDept" value="0" class="isOtherCla" checked/>否
                                            </label>
                                        </div>
                                        <div class="form-btn m-b" style="display:none" id="otherDiv">
                                        	
                                            <button class="btn dark" type="button" id="dept-append" >添 加</button><button class="btn" type="button" id="dept-remove" >删 除</button>
                                        </div>
                                        <div class="table-wrap show us-list form-table-h" id="otherContentDIV">
                                            <table class="table table-striped  m-b-md first-td-tc" id="otherDeptTable">
                                                <tbody>
                                                   
                                                </tbody>
                                            </table>
                                        </div>
                                </div>
                                <div class="tab-pane fade" id="messages3">
                                   <div class="ms2side__div">
                                       <div class="ms2side__select">
                                           <div class="ms2side__header">角色选择框</div>
                                           <select title="角色选择框" name="roleList" id="roleList" size="0" multiple="multiple" class="select-list-h">
                                           		<c:forEach items="${data.roleList}" var="role">
                                                	<option value="${role.id}">${role.name}</option>
                                                </c:forEach>
                                           </select>
                                       </div>
                                       <div class="ms2side__options" style="padding-top: 6.5px;">
                                           <p class="AddOne ms2side__hide" title="添加" id="addRole"><span></span></p>
                                           <p class="AddAll ms2side__hide" title="添加所有" id="addAllRole"><span></span></p>
                                           <p class="RemoveOne ms2side__hide" title="删除" id="removeRole"><span></span></p>
                                           <p class="RemoveAll ms2side__hide" title="删除所有" id="removeAllRole"><span></span></p>
                                       </div>
                                       <div class="ms2side__select">
                                           <div class="ms2side__header">已选角色框</div>
                                           <select title="已选角色框" name="chooseList" id="chooseList" size="0" multiple="multiple" class="select-list-h"></select>
                                       </div>
                                   </div>
                                </div>
                                
                            </div>
                    </section>
                 </section>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" id="saveOrModify">保存继续</button><button class="btn" type="button" id="saveAndClose">保存退出</button><button class="btn" type="button" id="userDivClose">关 闭</button>
                </div>
            </div>
            </form>
        </div>
    </div>
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
     <div class="modal fade panel" id="myModal1" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">机构选择</h4>
                </div>
                 <div class="modal-body">
                	<div id="deptFullTreeDiv" class="ztree"></div>
                </div>
                <div class="modal-footer no-all form-btn">
                    <button class="btn dark" type="submit" id="fullTreeSave">确 定</button><button class="btn" type="reset" id="fullTreeClose">取 消</button>
                </div>
            </div>
        </div>
    </div>
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
     <textarea style="display:none" id="template">
        <tr>
        	<td class="w46"><input type="checkbox" name="otherDeptTrId-{0}" id="otherDeptTrId-{0}"/>
            	<input type="hidden" id="otherDeptId-{0}" name="otherDeptId-{0}"/>
            </td>
            <td class="w100"><span class="required">*</span>部门名称</td>
            <td style="w105"><div class="input-group inline-tree"><input type="text" id="otherDeptName-{0}" name="otherDeptName-{0}" class="otherDeptNameCla" readonly/>
            	<a class="btn btn-file input-group-btn" href="#" id="showDeptTree-{0}" role="button" data-toggle="modal" onclick='userEdit.showOtherDept("otherDeptId-{0}","otherDeptName-{0}")'><i class="fa fa-group"></i></a></div></td>
            <td class="w100"><span class="required">*</span>部门职务</td>
            <td class="btn-s-md">
                <select id="otherDeptDuty-{0}" name="otherDeptDuty-{0}" class="otherDeptDutyCla">
                    
                </select>
            </td>
            <td class="w100"><span class="required">*</span>部门序号</td>
            <td class="w84">
                <input type="text" id="otherDeptNo-{0}" name="otherDeptNo-{0}" class="otherDeptNoCla"/>
            </td>
        </tr>                                 
     </textarea>
<script src="${sysPath}/js/com/sys/user/userEdit.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/user.validate.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/userDeptTree.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/user/deptFullTree.js" type="text/javascript"></script>
