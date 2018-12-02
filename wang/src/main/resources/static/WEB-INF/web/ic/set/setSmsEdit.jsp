<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

<div class="modal fade panel" id="news" aria-hidden="false">
    <div class="modal-dialog">
     <input type="hidden" id="token" name="token" value="${token}">
    	<form class="table-wrap form-table" id="setForm">
    		
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal">×</button>
	                <h4 class="modal-title" id="saveOrUpdateName">新增</h4>
	            </div>
	            <div class="modal-body">
	                <div class="form-table">
		                <input type="hidden" id="id" name="id" value="0">
	                    <input type="hidden" id="modifyDate" name="modifyDate">
	                    <input type="hidden" id="statisticsType" name="statisticsType" value="0">
	                    <input type="hidden" id="statisticsRank" name="statisticsRank">
	                    <table class="table table-td-striped">
	                        <tbody>
	                            <tr>
	                                <td class="w140">操作人</td>
	                                <td>
	                                	<input type="hidden" id="createUser" name="createUser"/>
	                                	<input  id="controlUserName" name="controlUserName" value="${userName }" readonly/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td>生效时间</td>
	                                <td><input  id="startDate" name="startDate" value="${effectTime}" readonly/></td>
	                            </tr>
	                            <tr>  
	                                <td>类别</td>
	                                <td>
	                                	<input type="hidden" id="setType" name="setType" value="0"> 
	                                    <label class="radio inline">
	                                        <input type="radio" id="setType0" name="type-1"  value="0" checked>级别
	                                    </label>
	                                    <label class="radio inline m-l-md">
	                                        <input type="radio" id="setType1" name="type-1"  value="1">个人
	                                    </label>
	                                </td>
	
	                            </tr>
	                            <tr>  
	                                <td id="change">级别</td>
	                                <td id="selectOrdiv">
	                                	<div id="controlTree" style="display:none"></div>
	                                    <!-- <select style="display:block" id="rankId" name="rankIdSelect" class="rankIdV">
	                                    </select> -->
	                                    <dic:select name="rankIdSelect" id="rankId" dictName="level" headName="-请选择-" headValue="" defaultValue=""/>
	                                    
	                                </td>
	                            </tr>
	                            <tr>  
	                                <td><span class="required">*</span>短信数量</td>
	                                <td><input type="text" id="maxnum" name="maxnum"></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	            </div>
	            <div class="modal-footer form-btn">
	                <button class="btn dark" type="button" id="savaOrModify">保存继续</button>
					<button class="btn" type="button" id="savaAndClose">保存退出</button>
					<button class="btn" type="button" id="sava">保 存</button>
					<button class="btn" type="button" data-dismiss="modal">关 闭</button>
	            </div>
	        </div>
        </form>
    </div>
</div> 
<script src="${sysPath}/js/com/ic/set/setSmsEdit.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/ic/set/setIc.validate.js" type="text/javascript"></script>