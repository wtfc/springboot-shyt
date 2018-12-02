<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<div class="modal fade panel" id="myModal-authroize" aria-hidden="false">
        <div class="modal-dialog" style="width:1180px">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">授权</h4>
                </div>
                <div class="modal-body">
                	<section class="tabs-wrap tabs-wrap-in">
                        <!-- 同时添加 tabs-wrap-in 类-->
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#messages1" data-toggle="tab">访问权限</a>
                            </li>
                            <li>
                                <a href="#messages2" data-toggle="tab">数据权限</a>
                            </li>
                            <!-- 根据a标签href属性相对应的id，切换tab-pane -->
                            <li>
                                <a href="#messages3" data-toggle="tab">角色成员</a>
                            </li>
                        </ul>
                    </section>
                	<div class="tab-content">
                	  <div class="tab-pane fade active in w1100" id="messages1">
	                    <form class="cmxform" id="roleMenusForm">
	                    	<input type="hidden" id="id" name="id" value="0">
	                    	<input type="hidden" id="roleId" name="roleId" value="0">
	                        <input type="hidden" id="modifyDate" name="modifyDate">
	                        
	                        <div class="m-t m-b">
		                        <div  style="width:300px;height: 260px" id="menuTreeDiv"  class="ztree scrollable inline"></div>
	                        </div>
	                    </form>
	                </div>
	                <div class="tab-pane fade" id="messages2">
	                	<form class="cmxform" id="roleBlocksForm">
	                	<div class="input-group input-default w-p100">
	                        	<div class="form-control distab fl" style="width:200px"><label class="input-group-btn p-r">数据范围设定</label>
		                        	<select class="select-md" name="permissionType"  id="permissionType" style="width: 100px">
			                        	<option value="1">本人</option>
	                                    <option value="2">所在部门</option>
	                                    <option value="3">所在机构</option>
	                                    <option value="4">自定义</option>
		                        	</select>
	                        	</div>
	                        	<div style="display:none;width: 180px" id="weightLayer1" class="form-control distab fl"  >
	                        	<!-- <label  class="input-group-btn p-r">安全系数</label> -->
		                        	<!-- <select class="select-md" name="weightRule" id="weightRule">
			                        	<option value="-1">小于等于</option>
	                                    <option value="0">等于</option>
	                                    <option value="1">大于等于</option>
		                        	</select>
		                        	 -->
		                        	 <input type="hidden" name="weight" id="weight" value='0' />
		                        	 <input type="hidden" name="weightRule" id="weightRule" value="0" />
	                        	</div>
	                        	<div style="display:none;width: 180px;margin-left: 10px" id="weightLayer2" class="form-control distab fl"  >
	                        		<!--
	                        			<input type="text" name="weight" id="weight" style="width:30px" />
	                        		-->
	                        		<input type="hidden" name="weight" id="weight" value="0" />
	                        	</div>
	                        </div>
	                        <div class="m-t m-b">
		                        <div style="width:300px;height: 260px" id="deptTreeDiv"  class="ztree scrollable none"></div>
	                        </div>
	                        </form>
		            </div>
	                <div class="tab-pane fade w1100" id="messages3">
	                	<div id="userSelectDiv" class="w1100" style="width: 1100px"></div>
		            </div>
	              </div>
	              
                </div>
                <div class="modal-footer no-all form-btn">
                    <button class="btn dark" type="button" id="savaRoleMenu">保  存</button>
                    <button class="btn" type="button" id="closeRoleMenu">关  闭</button>
                </div>
            </div>
        </div>
    </div>

<script type="text/javascript" src="${sysPath}/js/com/sys/role/roleUserSelect.js"></script>
<script type="text/javascript" src="${sysPath}/js/com/sys/role/roleAuthorize.js"></script>
<script type="text/javascript" src="${sysPath}/js/com/sys/role/role.validate.js"></script>