<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
<script>
function loadspecialdata(){
	jQuery.ajax({
        url: getRootPath()+"/sys/specialData/getSpecialData.action?columnId=${columnId}&funViewType=${funViewType}&dataRows=${dataRows}&time="+new Date(),
        type: 'post',
        success: function(data, textStatus, xhr) {
        	if('${funViewType}' == 'pviewType_10'){
        		loadspecialother(data.birthdayList,data.festivalList,data.dataRows,"${portletId }${functionId}","dataLoad_special${portletId }${functionId}");
        	} else {
        		$("#dataLoad_special${portletId }${functionId}").fadeOut();
        	}
        },error:function(e){
        	// alert("加载数据错误。");
        	//msgBox.tip({content: "加载数据错误", type:'fail'});
        }
	});
	
	function loadspecialother(birthdayList,festivalList,dataRows,divname,loadingname){
		var festivalcontent = "";
		var content = "";
		var solarorlunarsign = "";
		var phoneurl = "";
		if(festivalList.length > 0){
			for(var i=0;i<festivalList.length;i++){
				if(festivalList[i].solarorlunar == 2)
					solarorlunarsign = "阴历";
				else
					solarorlunarsign = "阳历";
				festivalcontent = festivalcontent + "<p class=\"m-t-sm\" style=\"font-weight: bold;text-align: center;\">"+festivalList[i].infoName+" ["+solarorlunarsign+":"+festivalList[i].specialData+"]&nbsp;</p><p class=\"\">"+festivalList[i].summaryContent+"</p>";
			}
			$("#festival10"+divname).html(festivalcontent);
		}
		
		if(birthdayList.length > 0){
	  		for(var i=0;i<birthdayList.length;i++){
	  	  		if(i == dataRows){
	  	  			break;
	  	  		}
	  	  		if(birthdayList[i].solarorlunar == 2)
					solarorlunarsign = "阴历";
				else
					solarorlunarsign = "阳历";
	  	  		if(birthdayList[i].userPicurl != null 
	  	  				&& birthdayList[i].userPicurl != '')
	  	  			phoneurl = '<img height="60" width="60" src="./../../'+birthdayList[i].userPicurl+'">';
	  	  		else
	  	  			phoneurl = '<img height="60" src="./../../images/demoimg/userPhoto.png">';
	  	  		
	  	  		content = content + "<li>"+
	  	  			"<div class=\"dayCon\">"+
	  	  				"<div class=\"dayImg\" style=\"width:60px\">"+phoneurl+"</div>"+
	  	  				"<div class=\"dayImg website\">"+
	  	  					"<b>"+birthdayList[i].infoName+"</b>"+
	  	  					"<p class=\"\">在今天"+" ["+solarorlunarsign+":"+birthdayList[i].specialData.substring(5,birthdayList[i].specialData.length)+"] 过生日</p>"+
	  	  					"<a class=\"\" type=\"button\" onClick=\"loadrightmenu('/ic/mail/manageSend.action','','/ic/mail/manageSend.action');\">发送祝福</a>"+
	  	  					"</div>"+
	  	  			"</div>"+
	  	  		"</li>";
	  	  	}
	  	  	$("#birthday10"+divname).html("<ul>"+content+"</ul>");
	  	 	runJsForbirthday(divname);
	  		$("#"+loadingname).fadeOut();
	  	} else {
	  		$("#birthday10"+divname).html("<div style='text-align:center;margin-top: 50px;font-size: 18px;font-family: inherit;'>"+"暂无数据"+"</div>");
	  		$("#"+loadingname).fadeOut();
	  	}
	}
	
	function runJsForbirthday(divname){
		$("#birthdays"+divname).slide({
		       mainCell:".birthdaybd ul",
		       pnLoop:true,
		       effect:"topMarquee",
		       interTime:'50',
		       vis:2,
		       mouseOverStop:true,
		       autoPlay:true,
		       trigger:"click"
		});
	}
};
loadspecialdata();
</script>
<input id="morelink_${portletId }" type="hidden" value="${funUrlmore }"/><!-- 更多链接 -->
<input id="morelink_${portletId }_${functionId}" type="hidden" value="${funUrlmore }"/><!-- 更多链接  -->
<c:choose>
	<c:when test="${funViewType == 'pviewType_10' }"><!--其他样式-->
		<div class="index-inform">
			<div id="birthdays${portletId }${functionId}">
				<div class="birthdaybd" id="birthday10${portletId }${functionId}">
				</div>
			</div>
			<div id="festival10${portletId }${functionId}">
			</div>
		</div>
	</c:when>
	<c:when test="${funViewType == 'pviewType_9' }"><!-- 单一大列表 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_3' }"><!-- 图文混合 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_2' }"><!-- 图表混合 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_4' }"><!-- 单一图片 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_5' }"><!-- 多图滚动 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_6' }"><!-- 单一文本 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_7' }"><!-- 快捷方式 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_8' }"><!-- 图表 -->
	</c:when>
	<c:otherwise><!-- 单一小列表 -->
	</c:otherwise>
</c:choose>
<div class="loading" id="dataLoad_special${portletId }${functionId}"></div>