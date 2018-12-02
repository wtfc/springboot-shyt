<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>

    <!--start 回复 弹出层-->
    <div class="modal fade panel" id="reply" aria-hidden="false">
        <div class="modal-dialog w1100">
            <div class="modal-content">
            <form id="replyForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">回复</h4>
                </div>
                <div class="modal-body">
                    <div class="form-table">
                    <input type="hidden" id="suggestId" name="suggestId"/>
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td class="w115">建议标题</td>
                                    <td id="r_suggestTitle"></td>
                                </tr>
                                <tr>
                                    <td>建议发起人</td>
                                    <td id="r_createUserName"></td>
                                </tr>
                                <tr>
                                    <td>建议类型</td>
                                    <td id="r_suggestTypeName"></td>
                                </tr>
                                <tr>  
                                    <td>发起时间</td>
                                    <td id="r_createDate"></td>
                                </tr>
                                <tr>  
                                    <td>建议接收人</td>
                                    <td id="r_recipientNames">
                                       
                                    </td>
                                </tr>
                                <tr>  
                                    <td>建议内容</td>
                                    <td id="r_suggestContent">
                                       
                                    </td>
                                </tr>
                                <tr>
                                    <td>附件</td>
                                    <td>
                                    <ul id="attachList_r"></ul>
                                    </td>  
                                </tr>
                            </tbody>
                        </table>
                        <div>
	                        <h4 class="modal-heading clearfix">回复内容</h4>
	                        <ul class="dialog m-t" id="repContentList">
	                            
	                        </ul>
                        </div>
                        <section>
                            <textarea rows="3" id="repContent" name="repContent"></textarea>
                            <div>您还可以输入 <span id="count">300</span>字。 </div>
                        </section>
                    </div>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark"  id="repSave" type="button">回 复</button>
                    <button class="btn" id="repClose" type="button">关 闭</button>
                </div>
                </form>
            </div>
        </div>
    </div>
    <!--end 查看 弹出层-->
 
<script src="${sysPath}/js/com/ic/suggest/suggestInteractReplyDiv.js"></script>
<script src='${sysPath}/js/com/ic/suggest/suggestInteract.validate.js'></script>
