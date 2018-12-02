<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<script src="${sysPath}/js/com/signature/handwritten.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/oa/archive/docFormView.js" type="text/javascript"></script>
<style>
	.dongfeng tr td:first-child,
	.dongfeng tr td:first-child+td+td,
	.dongfeng tr td:first-child+td+td+td+td {
		text-align: center !important;
	}
	.memu{width:100%;border:1px solid #fb6b5b;}
	.memu.b-btm-1{border-bottom:1px solid #fb6b5b;}
	.memu th,.memu td{padding:8px;line-height:20px;text-align:left;border-top:1px solid #fb6b5b}
	.memu th{font-weight:bold}
	.memu thead th{vertical-align:bottom}
	.memu caption+thead tr:first-child th,.memu caption+thead tr:first-child td,.memu colgroup+thead tr:first-child th,.memu colgroup+thead tr:first-child td,.memu thead:first-child tr:first-child th,.memu thead:first-child tr:first-child td{border-top:0}
	.memu tbody+tbody{border-top:1px solid #fb6b5b;}
	.memu .table{background-color:#fff}
	.memu th,.memu td{border-left:1px solid #fb6b5b}
	.memu caption+thead tr:first-child th,.memu caption+tbody tr:first-child th,.memu caption+tbody tr:first-child td,.memu colgroup+thead tr:first-child th,.memu colgroup+tbody tr:first-child th,.memu colgroup+tbody tr:first-child td,.memu thead:first-child tr:first-child th,.memu tbody:first-child tr:first-child th,.memu tbody:first-child tr:first-child td{border-top:0}
	.panel .memu thead>tr>th,.panel .memu>tbody>tr>td{height:45px;font-weight: bold;}
	.panel .memu>tbody>tr>td>ul>li>a{font-weight:normal;}
   	.panel-heading,.modal-heading{font-size:18px;margin:0;line-height:18px;border:0 none;padding:24px 20px 15px;}
   	.d-table tr td{
   		text-align:left !important;
   		padding-left:40px !important;
   		font-weight: normal !important;
   	}
   	.d-table tr:first-child td{
   		color: #fb6b5b !important;
   		font-weight: bold !important;
   	}

   	.div-line{
   	   text-align: center;
   	   margin:0 auto;
   	   width:190px;
   	}
   	
   	.div-line div{
   	    width:190px;
   	    color:#fb6b5b;
   		border-bottom: 2px solid #fb6b5b;
   		margin-bottom: 3px;
   		text-align: center;
   	}
   	.div-line div+div{
   		width:190px;
   	   border-bottom: 1px solid #fb6b5b;
   	   margin-bottom: 8px;
   	   text-align: center;
   	}
</style>

<!--start 表格-->
<section class="panel m-t-md clearfix">
	<%@ include file="/plugin/websign.jsp"%>
   	<div class="table-wrap w900 m-20-auto" id="sendSimulationForm">
		${formContent}
    </div>
</section>
<!--end 表格-->
<script type="text/javascript">
/* 	$("#sendSimulationForm").find("[workFlowForm]").each(function(){
		try{
			var item = $(this);
			var itemType = $(this).attr("workFlowForm");
			//对于浏览模式,所有组件都为只读状态
			docFromView.type[itemType].read(item);
		}catch(e) {}
	}); */
	$(document).find("[workFlowForm]").each(function(){
		try{
			var item = $(this);
			var itemType = $(this).attr("workFlowForm");
			//对于浏览模式,所有组件都为只读状态
			docFromView.type[itemType].read(item);
		}catch(e) {}
	});

var subContent = "";
if(Number($("#sendSimulationForm").html().indexOf('id="workId"'))!=-1){
	subContent = $("#sendSimulationForm").html().substr(Number($("#sendSimulationForm").html().indexOf('id="workId"')),$("#sendSimulationForm").html().length);
}else if(Number($("#sendSimulationForm").html().indexOf('id=workId'))!=-1){
	subContent = $("#sendSimulationForm").html().substr(Number($("#sendSimulationForm").html().indexOf('id=workId')),$("#sendSimulationForm").html().length);
}else if(Number($("#sendSimulationForm").html().indexOf("id='workId'"))!=-1){
	subContent = $("#sendSimulationForm").html().substr(Number($("#sendSimulationForm").html().indexOf("id='workId'")),$("#sendSimulationForm").html().length);
}else{
	subContent = $("#sendSimulationForm").html();
}
var workId = subContent.substr(Number(subContent.indexOf("HZ")),32);
if(workId){
	handWritten.getSuggestWrite(workId);
	
	//处理签章失效
	var strObjectName = document.all.DWebSignSeal.FindSeal("",0);
	while(strObjectName){
		document.all.DWebSignSeal.SetDocAutoVerify(strObjectName,0);
		strObjectName = document.all.DWebSignSeal.FindSeal(strObjectName,0);
	};
}
$(document).ready(function (){
	ie8StylePatch();
});
</script>
<!-- IE8水印 -->
