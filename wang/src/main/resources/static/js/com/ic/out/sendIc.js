var sendIc={};
//分页处理 start
sendIc.pageRows = null;
//分页对象
sendIc.oTable = null;

//显示列信息
sendIc.oTableAoColumns = [
//不需要排序的列直接用mData function方式
	{mData: "recipient"},
	{mData: "text"},
	{mData: "sendType", mRender : function(mData, type, full){
			return full.sendTypeValue;
		}
	},
	{mData: "sentDate"},
	{mData: "status", mRender : function(mData) {
			var sta = null;
			if(mData == "S"){
				sta = "已发送";
			}else if(mData == "Q"){
				sta = "排队中";
			}else if(mData == "U"){
				sta = "未发送";
			}else{
				sta = "发送失败";
			}
			return sta;
		}
	}
];

//组装后台参数
sendIc.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(sendIc.oTable, aoData);
	//组装查询条件
	var text = $("#sendForm #text").val();
	var sendType = $("#sendForm #sendType").val();
	var status = $("#sendForm #status").val();
	var userId = $("#sendForm #userId-userId").val();
	var outDateStart = $("#sendForm #outDateStart").val();
	var outDateEnd = $("#sendForm #outDateEnd").val();
	var recipient = $("#sendForm #recipient").val();
	var recipientOut = $("#outSearchForNum").val();
	if(text != ""){
		aoData.push({ "name": "text", "value": text});
	}
	if(sendType != null&&sendType!=""){
		aoData.push({ "name": "sendType", "value": sendType});
	}
	if(status != -1){
		aoData.push({ "name": "status", "value": status});
	}
	if(userId != ""){
		aoData.push({ "name": "userId", "value": userId});
	}
	if(recipient != ""){
		aoData.push({ "name": "recipient", "value": recipient});
	}
	if(recipientOut != ""){
		aoData.push({ "name": "recipientOut", "value": recipientOut});
	}
	if(outDateStart != ""){
		aoData.push({ "name": "outDateStart", "value": outDateStart});
	}
	if(outDateEnd != ""){
		aoData.push({ "name": "outDateEnd", "value": outDateEnd});
	}
};

//分页查询用户
sendIc.sendIcList = function () {
	//table对象为null时初始化
	if (sendIc.oTable == null) {
		sendIc.oTable = $('#sendTable').dataTable( {
			iDisplayLength: sendIc.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/out/outList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: sendIc.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				sendIc.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0]}]
		} );
	} else {
		//table不为null时重绘表格
		sendIc.oTable.fnDraw();
	}
};

//字典项初始化
//sendIc.initDic = function(){
//	dic.fillDics("sendType", "sendType",false,2);
//};

//重置方法
sendIc.formReset = function(){
	$('#sendForm')[0].reset();
	$('#outSearchForNum').val("");
	//清空人员选择树
	selectControl.clearValue("userId-userId");
};

//初始化方法按如下结构编写
jQuery(function($){
	//计算分页显示条数
	sendIc.pageRows = TabNub>0 ? TabNub : 10;
    //初始化内容
	$("#querySend").click(sendIc.sendIcList);
	$("#resetSend").click(sendIc.formReset);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	selectControl.init("controlTree","userId-userId", false, true);//单选人员
	//sendIc.initDic();
	sendIc.sendIcList();
	
});

//@ sourceURL=sendIc.js
