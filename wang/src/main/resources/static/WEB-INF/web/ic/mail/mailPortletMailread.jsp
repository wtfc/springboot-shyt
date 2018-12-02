<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
function loadmaildata(){
	jQuery.ajax({
        url: getRootPath()+"/ic/mail/getportalMailread.action?foldId=${foldId}&funViewType=${funViewType}&dataRows=${dataRows}&time="+new Date(),
        type: 'post',
        success: function(data, textStatus, xhr) {
        	if('${funViewType}' == 'pviewType_1'){
        		if('3' == '${foldId}')
        			loadmailmixlist3(data.mailList,data.dataRows,"mail1${portletId }${functionId}","dataLoad_mail${portletId }${functionId}","/ic/mail/manage.action/3");
        		else if('1' == '${foldId}')
        			loadmailmixlist1(data.mailList,data.dataRows,"mail1${portletId }${functionId}","dataLoad_mail${portletId }${functionId}","/ic/mail/manage.action/1");
        		else
        			loadmailmixlist1(data.mailList,data.dataRows,"mail1${portletId }${functionId}","dataLoad_mail${portletId }${functionId}","/ic/mail/manageStar.action");
        	} else if('${funViewType}' == 'pviewType_9'){
        		if('3' == '${foldId}')
        			loadmailmaxlist3(data.mailList,data.dataRows,"mail9${portletId }${functionId}","dataLoad_mail${portletId }${functionId}","/ic/mail/manage.action/3");
        		else if('1' == '${foldId}')
        			loadmailmaxlist1(data.mailList,data.dataRows,"mail9${portletId }${functionId}","dataLoad_mail${portletId }${functionId}","/ic/mail/manage.action/1");
        		else
        			loadmailmaxlist1(data.mailList,data.dataRows,"mail9${portletId }${functionId}","dataLoad_mail${portletId }${functionId}","/ic/mail/manageStar.action");
        	} else if('${funViewType}' == 'pviewType_7'){
        		loadmailshortcut(data.mailListSize,"mail7${portletId }${functionId}","dataLoad_mail${portletId }${functionId}");
        	} else {
        		$("#dataLoad_mail${portletId }${functionId}").fadeOut();
        	} 
        },error:function(e){
        	// alert("加载数据错误。");
        	//msgBox.tip({content: "加载数据错误", type:'fail'});
        }
	});
}
function loadmailmixlist1(infoList,dataRows,divname,loadingname,urlbar){
   	var content = "";
  	var title = "";
  	if(infoList.length > 0){
  		for(var i=0;i<infoList.length;i++){
  	   		title = $.escapeHtml(infoList[i].mailTitle);
  	   		if(i == dataRows)
  	   			break;
  	   	//	if(infoList[i].mailboxId==1)
  	   			content = content + "<tr><td style=\"text-align: left;\">"+infoList[i].senderUserName+"</td>";
  	   	//	else
  	   	//		content = content + "<tr><td style=\"text-align: left;\">"+infoList[i].senderMail+"</td>";
  	   		content = content + "<td style=\"display: none;\"></td>"+
  	   			"<td><a href=\"#\" title=\""+title+"\" onclick=\"loadrightmenu('/ic/mail/managePortletView.action?id="+infoList[i].id+"&mrid="+infoList[i].mrid+"','','"+urlbar+"');\">"+
  	   			title+"</a> </td>";
  	   		
  	   		content = content +"<td>"+infoList[i].receiveTime2+"</td><td style=\"display: none;\"></td></tr>";
  	   	}
  	   	$("#"+divname).html(content);
  	   	$("#"+loadingname).fadeOut();
  	} else {
  		$("#"+divname).html("<tr><td colspan='3' style='text-align:center;font-size: 13px;font-family: inherit;'>暂无数据</td></tr>");
  	   	$("#"+loadingname).fadeOut();
  	}
   	
};
function loadmailmaxlist1(infoList,dataRows,divname,loadingname,urlbar){
   	var content = "";
   	var title = "";
   	if(infoList.length > 0){
   		for(var i=0;i<infoList.length;i++){
   	   		title = $.escapeHtml(infoList[i].mailTitle);
   	   		if(i == dataRows)
   	   			break;
   	   		content = content +"<li><i class=\"fa fa-clipboard icon fl\"></i><div class=\"fl index-inform-con\">"+
   	   			"<a href=\"#\" title=\""+title+"\" onclick=\"loadrightmenu('/ic/mail/managePortletView.action?id="+infoList[i].id+"&mrid="+infoList[i].mrid+"','','"+urlbar+"');\">"+
   					"<span>"+title+"</span><!-- 信息标题 -->";
   			//if(infoList[i].mailboxId==1)
   				content = content +"<div class='index-inform-d'>发件人："+infoList[i].senderUserName.substr(0,4)+"<span class='fr'>"+infoList[i].receiveTime2+"</span></div></a></div>";
   			//else
   			//	content = content +"<div class='index-inform-d'>发件人："+infoList[i].senderMail.substr(0,4)+"<span class='fr'>"+infoList[i].receiveTime2+"</span></div></a></div>";
   			content = content + "<!-- 发布时间 --></li>";
   					
   	   	}
   	   	$("#"+divname).html("<ul>"+content+"</ul>");
   	   	$("#"+loadingname).fadeOut();
   	} else {
   		$("#"+divname).html("<div style='text-align:center;margin-top: 50px;font-size: 18px;font-family: inherit;'>"+"暂无数据"+"</div>");
   	   	$("#"+loadingname).fadeOut();
   	}
};
function loadmailmixlist3(infoList,dataRows,divname,loadingname,urlbar){
   	var content = "";
  	var title = "";
  	if(infoList.length > 0){
  		for(var i=0;i<infoList.length;i++){
  	   		title = $.escapeHtml(infoList[i].mailTitle);
  	   		if(i == dataRows)
  	   			break;
  	   	//	if(infoList[i].mailboxId==1)
  	   			content = content + "<tr><td style=\"text-align: left;\">"+infoList[i].receiveUsers+"</td>";
  	   	//	else
  	   	//		content = content + "<tr><td style=\"text-align: left;\">"+infoList[i].senderMail+"</td>";
  	   		content = content + "<td style=\"display: none;\"></td>"+
  	   			"<td><a href=\"#\" title=\""+title+"\" onclick=\"loadrightmenu('/ic/mail/managePortletView.action?id="+infoList[i].id+"&mrid="+infoList[i].mrid+"','','"+urlbar+"');\">"+
  	   			title+"</a> </td>";
  	   		
  	   		content = content +"<td>"+infoList[i].receiveTime2+"</td><td style=\"display: none;\"></td></tr>";
  	   	}
  	   	$("#"+divname).html(content);
  	   	$("#"+loadingname).fadeOut();
  	} else {
  		$("#"+divname).html("<tr><td colspan='3' style='text-align:center;font-size: 13px;font-family: inherit;'>暂无数据</td></tr>");
  	   	$("#"+loadingname).fadeOut();
  	}
   	
};
function loadmailmaxlist3(infoList,dataRows,divname,loadingname,urlbar){
   	var content = "";
   	var title = "";
   	if(infoList.length > 0){
   		for(var i=0;i<infoList.length;i++){
   	   		title = $.escapeHtml(infoList[i].mailTitle);
   	   		if(i == dataRows)
   	   			break;
   	   		content = content +"<li><i class=\"fa fa-clipboard icon fl\"></i><div class=\"fl index-inform-con\">"+
   	   			"<a href=\"#\" title=\""+title+"\" onclick=\"loadrightmenu('/ic/mail/managePortletView.action?id="+infoList[i].id+"&mrid="+infoList[i].mrid+"','','"+urlbar+"');\">"+
   					"<span>"+title+"</span><!-- 信息标题 -->";
   			//if(infoList[i].mailboxId==1)
  	   			content = content +"<div class='index-inform-d'>收件人："+infoList[i].receiveUsers.substr(0,4)+"<span class='fr'>"+infoList[i].receiveTime2+"</span></div></a></div>";
   			//else
   			//	content = content +"<div class='index-inform-d'>发件人："+infoList[i].senderMail.substr(0,4)+"<span class='fr'>"+infoList[i].receiveTime2+"</span></div></a></div>";
   			content = content + "<!-- 发布时间 --></li>";
   					
   	   	}
   	   	$("#"+divname).html("<ul>"+content+"</ul>");
   	   	$("#"+loadingname).fadeOut();
   	} else {
   		$("#"+divname).html("<div style='text-align:center;margin-top: 50px;font-size: 18px;font-family: inherit;'>"+"暂无数据"+"</div>");
   	   	$("#"+loadingname).fadeOut();
   	}
};
function loadmailshortcut(infoListsize,divname,loadingname){
	$("#"+divname).html(infoListsize);
   	$("#"+loadingname).fadeOut();
}
loadmaildata();
</script>
<input id="morelink_${portletId }" type="hidden" value="${funUrlmore }"/><!-- 更多链接 -->
<input id="morelink_${portletId }_${functionId}" type="hidden" value="${funUrlmore }"/><!-- 更多链接  -->
<c:choose>
	<c:when test="${funViewType == 'pviewType_9' }"><!-- 单一大列表 -->
	 <div class="index-inform panel" id="mail9${portletId }${functionId}">
	</div>
	</c:when>
	
	
	<c:when test="${funViewType == 'pviewType_7' }"><!-- 快捷方式 -->
		<i class="fa fa-inform icon fl"><b class="bg-green-dark rounded">&nbsp;</b></i>
         <div class="fl panel-con-wrap inform-con"> <strong id="mail7${portletId }${functionId}"></strong><a href="#" onclick="portalview.loadMore('${portletId }')"><span id="shortcutName">未读邮件</span></a>  </div>
	</c:when>
	
	<c:otherwise><!-- 单一小列表 -->
	 <div class="index-inform">
		<table class="table table-striped table-move first-td-tc over-hide-wrap">
		<thead>
			<tr>
				<th style="display: none;"></th>
				<c:choose>
					<c:when test="${foldId==3 }">
						<th class="">收件人</th>
					</c:when>
					<c:otherwise>
						<th class="">发件人</th>
					</c:otherwise>
				</c:choose>
				<th class="">主题</th>
				<th class="">时间</th>
				<th style="display: none;"></th>
			</tr>
		</thead>
		<tbody id="mail1${portletId }${functionId}">
		</tbody>
		</table>
	</div>
	</c:otherwise>
</c:choose>
<div class="loading" id="dataLoad_mail${portletId }${functionId}"></div>