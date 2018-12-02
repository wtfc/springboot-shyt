<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<script src="${sysPath}/js/com/sys/user/deptTree.js" type="text/javascript"></script>
<script type="text/javascript">
//显示没有行数据后面的操作按钮
	function oTableSetButtons( source) {
		var edit = "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"mailbox.get("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a>";
		var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"mailbox.deleteMailbox("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"删除\"/></a>";
		return edit +  del;
};
</script>
<style>
.inputposition{position:relative;z-index:2;}
.emaillist{position:absolute;width:200px;right:0;border:1px solid #dcdcdc;background:#fff;display:none;*top:0;}
.emaillist li{padding:5px;}
.emaillist p{background:#EEEEEE;line-height:20px;}
</style>
<script type="text/javascript">
$(function(){  
	var mailList = new Array('@163.com','@126.com','@qq.com','@hotmail.com');
	$("#mailboxForm #address").bind("keyup",function(){
		var val = $(this).val();
		if(val == '' || val.indexOf("@")>-1){
			$(".emaillist").hide();
			return false;
		}
		$('.emaillist').empty();
		for(var i = 0;i<mailList.length;i++){
		var emailText = $(this).val();
		$('.emaillist').append('<li class=addr>'+emailText+mailList[i]+'</li>');
		}
		$('.emaillist').show();
		$('.emaillist li').click(function(){
			$('#mailboxForm #address').val($(this).text());
			$('.emaillist').hide();
		});	
	});		
});
</script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in">
	<div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
	<a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a>
	<!-- <a class="btn dark fr" href="#myModal-edit" role="button" id="showAddDiv_t" data-toggle="modal">新 增</a> -->
</header>
<!------------------------------------------------------ 显示的块 ---------------------------------------------------->
<!-- ---------------------------------------------------查询条件begin--------------------------------------- -->
<input type="hidden" id="token" name="token" value=${token } />
<section class="panel clearfix search-box search-shrinkage">
 <div class="search-line hide">
	<form  class="table-wrap form-table" id="mailboxListForm">
		<input id="mailboxIds" name="mailboxIds" type="hidden">
			<table class="table table-td-striped">
			<tbody>
				<tr>
					<td class="w140">电子邮件地址</td>
					<td><input type="text" id="address" name="address" /></td>
				</tr>
			</tbody>
		</table>
	<section class="form-btn m-b-lg">
		<button class="btn dark query-jump" type="button" id="querymailbox">查
			询</button>
		<button class="btn" type="reset">重 置</button>
	</section>
</form>
</div>
<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
</section>
<!-- ---------------------------------------------------查询条件end---------------------------------------------------------- -->
<!-- ----------------------------------------------------数据列表begin-------------------------------------------------------- -->
<section class="panel" id="IP-edit">
	<div class="panel-heading clearfix btn-wrap">
		<h2>查询结果</h2>
	</div>
	<div class="table-wrap input-default" id="list">
		<table class="table table-striped tab_height first-td-tc" id="mailboxTable">
			<thead>
				<tr>
					<th class="w46 sorting_disabled"><input type="checkbox" /></th>
					<th>电子邮件地址</th>
					<th>操作</th>
				</tr>
			</thead>
			<!-- 此处必须留有一个空的<tbody>以便js像列表中添加数据 -->
			<tbody>

			</tbody>
		</table>
	</div>
	<section class="bp-inline clearfix" id="footer_height">
		<section class="form-btn fl m-l">
			<a class="btn dark query-jump" href="#" id="showAddDiv" role="button" data-toggle="modal">新 增</a>
			<button class="btn" type="submit" id="deleteMailboxs">批量删除</button>
		</section>
	</section>
</section>
<!-- ----------------------------------------------------数据列表end-------------------------------------------------------- -->

<!-- -----------------------------------------------------------显示的块end---------------------------------------------------------- -->
</section>
<!-- -----------------------------------------------------------弹出层begin---------------------------------------------------------- -->
    <div class="modal fade panel" id="myModal-edit" aria-hidden="false">
        <div class="modal-dialog w900">
        <form class="cmxform" id="mailboxForm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title" id="title"></h4>
                </div>
                <div class="modal-body">
                    <div class="form-table">
                    <input type="hidden" id="id" name="id"> 
					<input type="hidden" id="modifyDate" name="modifyDate">
					<input type="hidden" id="oldName" name="oldName">
                        <table class="table table-td-striped">
                            <tbody>
                                <tr>
                                    <td style="width:30%;"><span class="required">*</span>账户名称</td>
                                    <td><input type="text" id="username" name="username" /></td>
                                </tr>
                                <tr>
                                    <td><span class="required">*</span>登录密码</td>
                                    <td><input type="password" id="mailPassword" name="mailPassword" class="w-p100" /></td>
                                </tr>
                                <tr>  
                                    <td><span class="required">*</span>电子邮件地址</td>
                                    <td>
                                       <div class="inputposition"> <input type="text" id="address" name="address" placeholder="例如123@126.com" >
                                        <!--  <ul class="emaillist">
        								<p></p>
      									</ul> -->
      									 </div>
                                    </td>

                                </tr>
                                <tr>  
                                    <td><span class="required">*</span>接收服务器（IMAP）</td>
                                    <td>
                                        <div class="input-group w-p100">
                                          <input type="text" id="receiveService" name="receiveService" placeholder="例如imap.126.com" >
	                                        <label class="input-group-btn p-l"><input type="checkbox" id="receiveSSL" name="receiveSSL" value="1">&nbsp;&nbsp;SSL安全连接  </label>
                                            <div class="input-group-btn p-l p-r">端口</div>
                                            <input type="text" id="receivePort" name="receivePort" placeholder="143">
                                        	
                                        </div>
                                    </td>
                                </tr>
                                <tr>  
                                    <td><span class="required">*</span>发送服务器（SMTP）</td>
                                    <td>
                                         <div class="input-group w-p100">
                                          <input type="text" id="senderService" name="senderService" placeholder="例如smtp.126.com" >
	                                        <label class="input-group-btn p-l"><input type="checkbox" id="senderSSL" name="senderSSL" value="1">&nbsp;&nbsp;SSL安全连接  </label>
                                            <div class="input-group-btn p-l p-r">端口</div>
                                            <input type="text" id="senderPort" name="senderPort" placeholder="25">
                                        	
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer no-all form-btn">
					<button class="btn dark" type="button" id="savaOrModify">保存继续</button>
					<button class="btn" type="button" id="savaAndClose">保存退出</button>
					<button class="btn" type="button" data-dismiss="modal">关 闭</button>
				</div>
            </div>
            </form>
        </div>
    </div>
    <script src="${sysPath}/js/com/ic/mailbox/mailboxInteract.js" type="text/javascript"></script>
    <script src="${sysPath}/js/com/ic/mailbox/mailboxInteract.validate.js" type="text/javascript"></script>
    <%@ include file="/WEB-INF/web/include/foot.jsp"%>
<!-- -----------------------------------------------------------弹出层end---------------------------------------------------------- -->
