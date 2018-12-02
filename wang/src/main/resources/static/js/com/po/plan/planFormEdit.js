/**
 * 工作计划弹出层js
 * @author 刘锡来
 * @version  2014-10-08
 */
var planFormEdit ={};
var planRowDetail = {}, pageCount=10;//查看计划弹出层分页对象
var taskRowDetail = {}, pageCount=10;//查看任务弹出层分页对象
planRowDetail.oPlanTable = null;//计划oTable对象
taskRowDetail.oTaskTable = null;//任务oTable分页对象


/****************************************************************
 * 工作计划查看计划/查看任务 start
 * @author 刘锡来
 * @version  2014-08-12
*****************************************************************/
//导入计划分页查询
planRowDetail.planRowDetailList = function(){
	if (planRowDetail.oPlanTable == null) {
		planRowDetail.oPlanTable = $('#queryPlanTable').dataTable( {
	        "iDisplayLength": planRowDetail.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/plan/manageList.action",//后台分页查询地址url
	        "fnServerData": oTableRetrieveData,//查询数据回调函数
	        "aoColumns": planRowDetail.oTableAoPlanColumns,//table显示列
			"fnServerParams":function(aoData) {
				planRowDetail.oTableFnServerParams(aoData);//传参
			},
	        aaSorting:[],//设置表格默认排序列
	        //默认不排序列
	        "aoColumnDefs": [
	            {"bSortable": false, "aTargets": [0,1,2,3,4]}
	        ]
	    });
	}else{
		//table不为null时重绘表格
		planRowDetail.oPlanTable.fnDraw();
	}
};

//导入任务分页查询
taskRowDetail.taskRowDetailList = function(){
	if(taskRowDetail.oTaskTable == null){
		taskRowDetail.oTaskTable = $('#queryTaskTable').dataTable( {
	        "iDisplayLength": taskRowDetail.pageCount,//每页显示多少条记录
	        "sAjaxSource": getRootPath()+"/po/plan/manageTaskList.action",
	        "fnServerData": oTableRetrieveData,//查询数据回调函数
	        "aoColumns": taskRowDetail.oTableAoTaskColumns,//table显示列
			"fnServerParams":function(aoData) {
				taskRowDetail.oTableTaskFnServerParams(aoData);//传参
			},
	        aaSorting:[],//设置表格默认排序列
	        //默认不排序列
	        "aoColumnDefs":[
	            {"bSortable":false,"aTargets":[0,1,2,3,4,5,6,7]}
	        ]
	    });
	}else{
		taskRowDetail.oTaskTable.fnDraw();//table不为null时重绘表格
	}
};

//计划组装后台参数
planRowDetail.oTableFnServerParams = function(aoData) {
	getTableParameters(planRowDetail.oPlanTable,aoData);
	//组装查询条件
	$.each($("#planBoxForm").serializeArray(), function(i, o) {
		if(o.value !=""){
		   aoData.push({ "name": o.name, "value": o.value});
		}
	});
	aoData.push({ "name": "applyIdsPrimaryKeys", "value": $("#applyId").val()});//当前登录人
	aoData.push({ "name": "flowStatus", "value": "7"});//流程审批通过状态
};

//任务组装后台参数
taskRowDetail.oTableTaskFnServerParams = function(aoData){
	getTableParameters(taskRowDetail.oTaskTable, aoData);
	//组装查询条件
	$.each($("#taskBoxForm").serializeArray(), function(i, o){
		if(o.value !=""){
		   aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//计划显示列信息
planRowDetail.oTableAoPlanColumns = [
     //点击+
 	 {mData: function(source) {
		return "<a class=\"fa fa-plus2\" onClick=\"planRowDetail.planRowDetailShow(this," + source.id + ")\"></a>";
 	 }},
	{mData: "planTypeValue"},//计划类型
	{mData: "planTitle"},//计划标题
	{mData: "planStartTimeValue"},//计划开始时间
	{mData: "planEndTimeValue"}//计划结束时间
];

//计划显示列信息
planRowDetail.oTableAoPlanDetailColumns = [
    {mData:function(source){
//    	  return "<input type=\"checkbox\" name=\"checkPlanIds_" + source.id +"\" value="+source.directorId + ">";
    	//添加planId，导入计划时判断使用--xuweiping
    	return "<input type=\"checkbox\" id="+source.id+'_'+source.planId+" name=\"checkPlanIds_" + source.id +"\" value="+source.directorId + ">";
    }},
	{mData: "content"},//主要完成事项
	{mData: "standard"},//完成标准
	{mData: "directorIdValue"},//负责人
	{mData: "startTime"},//开始时间
	{mData: "endTime"},//结束时间	
	{mData: "compRate"},//完成比例
	{mData: "description"}//完成比例
];

//任务显示列信息
taskRowDetail.oTableAoTaskColumns = [
    //点击+
	{mData: function(source) {
		if(source.taskNowCount >0){//source.totalCount
			if(source.status=='3'){
				return "<a class=\"fa fa-plus2\" onClick=\"taskRowDetail.taskRowDetailShow(this," + source.taskId+",'1')\"></a>";
			}else{
				return "<a class=\"fa fa-plus2\" onClick=\"taskRowDetail.taskRowDetailShow(this,"+ source.id+",'0')\"></a>";
			}
		}else{
			return '';
		}
	}},
    //checkbox框,绑定数据id
    {mData: function(source){return "<input type=\"checkbox\" name=\"checkTaskIds_" + source.id +"\" value="+source.directorId + ">";}},
	{mData: "taskName"},//任务名称
	{mData: "taskTypeName", mRender : function(mData, type, full){//任务类型
		var reVal="无";
		if(mData !=null && mData !=''){
			reVal=mData;
		}
		return reVal;
	}},
	{mData: "content"},//任务内容
	{mData: "standard"},//完成标准
	{mData: "sponsor"},//发起人	
	{mData: "director"},//负责人
	{mData: "startTime"},//计划开始时间
	{mData: "endTime"}//计划结束时间
];

//计划rowDetail的展开和关闭的方法
planRowDetail.planRowDetailShow = function(obj,planId){
	//判断该数据是否存在
	if(!DeleteCascade.syncCheckExist("planAnno",planId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}
	//绑定onClick事件.示例是绑定到每行第一个td上面.
	//取得选中的row  
	var nTr = $(obj).parent().parent("tr")[0];
	//判断如果rowDetail是打开状态,那么则关闭.
    if ( $($(obj).closest("table")).DataTable().fnIsOpen(nTr) ){
    	$($(obj).closest("table")).DataTable().fnClose( nTr );
       	$(obj).removeClass("fa fa-minus");
       	$(obj).addClass("fa fa-plus2");
    }else{
    	//否则,未打开rowDetail时,则追加一个新row
    	//数据加载过程中,显示"数据加载中..."message.对应资源文件中的JC_SYS_068
    	var newRow = $($(obj).closest("table")).DataTable().fnOpen( nTr,$.i18n.prop("JC_SYS_068"),"pd");
    	//调用获得rowDetail数据的方法
    	planRowDetail.getPlanDetailData(planId,newRow,obj);
       	$(obj).removeClass("fa fa-plus2");
       	$(obj).addClass("fa fa-minus");
    }
};

//任务rowDetail的展开和关闭的方法
taskRowDetail.taskRowDetailShow = function(obj,id,qType){
	//绑定onClick事件.示例是绑定到每行第一个td上面.
	//取得选中的row  
	var nTr = $(obj).parent().parent("tr")[0];
	//判断如果rowDetail是打开状态,那么则关闭.
    if ( $($(obj).closest("table")).DataTable().fnIsOpen(nTr) ){
    	$($(obj).closest("table")).DataTable().fnClose( nTr );
       	$(obj).removeClass("fa fa-minus");
       	$(obj).addClass("fa fa-plus2");
    }else{
    	//否则,未打开rowDetail时,则追加一个新row
    	//数据加载过程中,显示"数据加载中..."message.对应资源文件中的JC_SYS_068
    	var newRow = $($(obj).closest("table")).DataTable().fnOpen( nTr,$.i18n.prop("JC_SYS_068"),"pd");
    	//调用获得rowDetail数据的方法
    	taskRowDetail.getTaskDetailData(id,newRow,obj,qType);
       	$(obj).removeClass("fa fa-plus2");
       	$(obj).addClass("fa fa-minus");
    }
};


//调用获得计划rowDetail数据的方法,参数1:id对应每行数据的唯一标示,参数2:新创建的row对象
planRowDetail.getPlanDetailData = function(planId,newRow,obj){
	var urlPath = getRootPath()+"/po/planDetail/manageList.action?planId="+planId+"&time="+new Date();
	//向新创建的row中追加detail数据
	//调用拼装html语句格式的方法
	 $('td',newRow).html(planRowDetail.getPlanDetailHtml(planId));
	 $("#"+planTableId+planId).dataTable( {
        "iDisplayLength": planRowDetail.pageCount,//每页显示多少条记录
        "sAjaxSource": urlPath,
        "fnServerData": oTableRetrieveData,//查询数据回调函数
        "aoColumns": planRowDetail.oTableAoPlanDetailColumns,//table显示列
        "bPaginate":false,
        "bSort":false,
        "fnInitComplete": function(){
            //选中数据然后打开子数据时的判断
            //自身checkbox被选中时,判断选中主数据中的checkbox
//            $(obj).closest("tr").next().find("table").find(":checkbox").prop("checked",
//            $(obj).closest("tr").find(":checkbox").prop("checked"));
        },
        //默认不排序列
        "aoColumnDefs": [
            {"bSortable": false, "aTargets": [0]}
        ]
    });
	planTableId += "_";
};

//调用获得任务rowDetail数据的方法,参数1:id对应每行数据的唯一标示,参数2:新创建的row对象
taskRowDetail.getTaskDetailData = function(id,newRow,obj,qType){
	var urlPath="";
	if(qType=='1'){//查询历史
		urlPath+=getRootPath()+"/po/taskfinish/getChildList.action?id="+id+"&time="+new Date();
	}else{
		urlPath+=getRootPath()+"/po/workTask/getChildListForPlan.action?id="+id+"&time="+new Date();
	}
	//向新创建的row中追加detail数据
	//调用拼装html语句格式的方法
	 $('td',newRow).html(taskRowDetail.getTaskDetailHtml(id));
	 $("#"+taskTableId+id).dataTable( {
        "iDisplayLength": taskRowDetail.pageCount,//每页显示多少条记录
        "sAjaxSource": urlPath,
        "fnServerData": oTableRetrieveData,//查询数据回调函数
        "aoColumns": taskRowDetail.oTableAoTaskColumns,//table显示列
        "bPaginate":false,
        "bSort":false,
        "fnInitComplete": function(){
            //选中数据然后打开子数据时的判断
            //自身checkbox被选中时,判断选中主数据中的checkbox
            //$(obj).closest("tr").next().find("table").find(":checkbox").prop("checked",
            //$(obj).closest("tr").find(":checkbox").prop("checked"));
        },
        //默认不排序列
        "aoColumnDefs": [
            {"bSortable": false, "aTargets": [1]}
        ]
    });
	taskTableId += "_";
};

//计划拼装html的方法,参数1:data对应ajax返回的数据
planRowDetail.getPlanDetailHtml = function(priId){
	var html = "";
	//指定table的格式和css
	html += "<table id=\""+ planTableId + priId +"\" class=\"table table-striped first-td-tc\">";
	html += "<thead>";
	html += "<tr>";
	html += "<th></th>";
	html += "<th style='width:220px;'>主要完成事项</th>";
	html += "<th style='width:220px;'>完成标准</th>";
	html += "<th>负责人</th>";
	html += "<th>开始时间</th>";
	html += "<th>结束时间</th>";
	html += "<th>比例(%)</th>";
	html += "<th style='width:220px;'>未完成原因说明</th>";
	html += "</tr>";
	html += "</thead>";
	html += "<tbody>";
	html += "</tbody>";
	html += "</table>"; 
	return html;
};

//任务拼装html的方法,参数1:data对应ajax返回的数据
taskRowDetail.getTaskDetailHtml = function(priId){
	var html = "";
	//指定table的格式和css
	html += "<table id=\""+ taskTableId + priId +"\" class=\"table table-striped tab_height first-td-tc\">";
	html += "<thead style=\"display:none\"></thead>";
	html += "<tbody>";
	html += "</tbody>";
	// table收尾
	html += "</table>"; 
	return html;
};

taskRowDetail.initCheckBox = function(){
	//动态绑定页面所有checkbox的选中功能
	$(document).on("change", ":checkbox", function() {
		//获得嵌套table的id
		var tId = $(this).closest("table").attr("id");
		//正则表达式判断当前已经打开到了第几层table
		var reg =  new RegExp("_","g");
		var count = tId.match(reg);
		//子数据选中
		$(this).closest("tr").next().find("table").find(":checkbox").prop("checked",$(this).prop("checked"));
		//用来保存临时的checkbox对象
		var obj;
		//当前选中数据拥有的子数据的总条数
		var checkboxNum = $(this).closest("table").find(":checkbox").length;
		//当前选中的子数据的条数
		var checkedLength = $(this).closest("table").find(":checkbox:checked").length;
		//总条数与被选中的数据相等时,证明所有的子数据都被选中了,改变主数据checkbox的状态
		$(this).closest("table").closest("tr").prev("tr").find(":checkbox").prop("checked",checkboxNum == checkedLength);
		//取得当前数据的所属的table元素
		obj = $(this).closest("table");
		//第一个表的处理
        if(count == null){
            //拥有的子数据的总条数
            var checkboxNum = $(obj).find('tbody').find(":checkbox").length;
            //被选中的子数据的条数
            var checkedLength = $(obj).find('tbody').find(":checkbox:checked").length;
            //总条数与被选中的数据相等时,证明所有的子数据都被选中了,改变主数据checkbox的状态
            $(obj).find("thead").find(":checkbox").prop("checked",checkboxNum == checkedLength);
            return false;
        }
		
		//根据打开数据的层数循环判断每一层的主checkbox状态
    	for(var i=0;i<count.length;i++){
    		//取得当前数据的的上一级所属的table元素
    		obj = $(obj).closest("table").closest("tr").prev("tr").find(":checkbox");
    		//拥有的子数据的总条数
			var checkboxNum = $(obj).closest("table").find('tbody').find(":checkbox").length;
			//被选中的子数据的条数
			var checkedLength = $(obj).closest("table").find('tbody').find(":checkbox:checked").length;
			//总条数与被选中的数据相等时,证明所有的子数据都被选中了,改变主数据checkbox的状态
			$(obj).closest("table").closest("tr").prev("tr").find(":checkbox").prop("checked",checkboxNum == checkedLength);
            //数量相等时,选中主数据中的checkbox,否则取消
            $(this).parents('table').find('thead').find(':checkbox').prop('checked',checkboxNum == checkedLength);
    	}
	});
};

//重置查询计划弹出层查询条件
planRowDetail.resetPlanBoxList = function(){
	$('#planBoxForm')[0].reset();//清空表单
	selectControl.clearValue("applyId-applyIdsPrimaryKeys");//清空人员选择树隐藏域
	//selectControl.clearValue("applyDeptid-applyDeptIdsPrimaryKeys");//清空部门选择树隐藏域
};

//重置查询任务弹出层查询条件
taskRowDetail.resetTaskBoxList = function(){
	$('#taskBoxForm')[0].reset();//清空表单
	selectControl.clearValue("sponsorId-sponsorId");
	selectControl.clearValue("directorId-directorId");
};


//计划页面初始化
planRowDetail.planRowDetailInit = function(){
	//计算分页显示条数
	planRowDetail.pageCount = TabNub>0 ? TabNub : 10;
	//初始化列表方法
	planRowDetail.planRowDetailList();
};	


//任务页面初始化
taskRowDetail.taskRowDetailInit = function(){
	//计算分页显示条数
	taskRowDetail.pageCount = TabNub>0 ? TabNub : 10;
	//初始化列表方法
	taskRowDetail.taskRowDetailList();
};

//下发工作任务弹出层隐藏方法
taskRowDetail.hideDiv = function(){
	$("#init").modal("hide");//隐藏下发任务弹出层
	taskRowDetail.clearForm();
	//成功提示
	msgBox.tip({
		type:"success",
		content:$.i18n.prop("JC_SYS_001")
	})
	var url = getRootPath()+"/po/plan/getTokenForTask.action";
	$.ajax({
		type : "Post",
		url : url,
		dataType : "json",
		success : function(data) {
			$("#token").val(data.token);
		}
	});
};
//清空表单
taskRowDetail.clearForm = function () {
	$('#zdsz')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("prticipantsName-prticipantsId");//参与人
	$("#businessId").val("0");//附件清空
	clearTable();
	echoAttachListDul(false,'attachList');
//	showAttachList(false);//附件清空
};

//下发任务弹出层关闭按钮
function closeSendTask(){
	//隐藏错误校验域
	hideErrorMessage();
	//清空附件
	clearTable();
	selectControl.clearValue("prticipantsName-prticipantsId");
	para_week = "";//清空提醒设置
	$("#remindPerName").val("");
	$("#remindContent").val("");
}

//超期提醒设置预览
function showRemindSet(){
	var t=$('input[name="remindType"]:checked').val();
	var s="";
	if(t==1){
		s="短信";
	}else if(t==2){
		s="邮件";
	}else{
		s="不提醒";
	}
	$('#optionsRadios2').val(s);//提醒方式
	var directorId=$('#directorId').val()+":"+$('#directorName').val();
	var prticipantsId=returnValue('prticipantsName-prticipantsId');
	var dire=$('#directorId').val();
	var prti=$('#prticipantsName-prticipantsId').val();
	var perId='';
	if(directorId!=null && prticipantsId==null){
		var director= new Array(); 
		director=directorId.split(":");
		perId+=director[1];
	}
	if(directorId==null && prticipantsId!=null){
		var prticipants= new Array(); 
		prticipants=prticipantsId.split(",");
		var participart='';
		for(var i=0;i<prticipants.length;i++){
			if(i==(prticipants.length-1)){
				participart+=prticipants[i].split(":")[1];
			}else{
				participart+=prticipants[i].split(":")[1]+',';
			}
		}
		perId+=participart;
	}
	if(directorId!=null && prticipantsId!=null){
		var director= new Array(); 
		director=directorId.split(":");
		var prticipants= new Array(); 
		prticipants=prticipantsId.split(",");
		var participart='';
		for(var i=0;i<prticipants.length;i++){
			if(prticipants[i].split(":")[1]!=director[1]){
				participart+=prticipants[i].split(":")[1]+',';
			}
		}
		if(participart !=''){
			perId+=director[1]+','+participart;
		}else{
			perId+=director[1];
		}
		
	}
	var per="";
	if(dire!=null && prti==null){
		per+=dire;
	}
	if(dire==null && prti!=null){
		per+=prti;
	}
	var partis='';
	if(dire!=null && prti!=null){
		var prt= new Array(); 
		prt=prti.split(",");
		for(var i=0;i<prt.length;i++){
			if(i==(prt.length-1)){
				if(dire!=prt[i]){
					partis+=prt[i];
				}
			}else{
				if(dire!=prt[i]){
					partis+=prt[i]+',';
				}
			}
		}
		if(partis!=''){
			per+=dire+','+partis;
		}else{
			per+=dire;
		}
	}
	if(t!=0){
		$("#remindContent").attr("disabled",false);
		if(isSave==0){
			$('#remindContent').val($('#taskName').val());//提醒内容
		}else{
			if($('#remindContent').val()==null || $('#remindContent').val()==''){
				$('#remindContent').val($('#taskName').val());//提醒内容
				isSave=0;
			}
		}
		$('#remindPerId').val(per);
		$('#remindPerName').val(perId);
		$('#remindContentTemp').val($('#remindContent').val());
	}else{
		$('#remindContent').val("");
		$('#remindPerId').val("");
		$('#remindPerName').val("");
		$('#remindContentTemp').val("");
		$("#remindContent").attr("disabled",true);
		isSave=0;
	}
	$('#remindWindow').modal('show');
}

//下发任务 保存时校验上下级关系
function callMothed(){
	//add by xuwp at 2014-11-11 start
	var planId = $("#id").val();//当前计划ID
	if(!DeleteCascade.syncCheckExist("planAnno",planId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}
	//add by xuwp at 2014-11-11 end
	var targetUser = $("#loginUserId").val();//当前登录人ID
	var sourceUser = $("#applyUserId").val();//工作计划申请人ID
	jQuery.ajax({
		url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
		type : 'GET',
		data : {"sourceUser": sourceUser,"targetUser":targetUser},
		success : function(data) {
			if(data.isLeader == "true"||sourceUser == targetUser){
				taskArra.addOrTempTask(taskRowDetail.hideDiv);
			}else{
				msgBox.tip({
					type:"fail",
					content:$.i18n.prop("JC_OA_PO_047")
				});
			}
		}
	});
}

/*****************************************************************
 * 工作计划查看计划/查看任务 end
/************** **************************************************/
$(document).ready(function(){
//	$("#queryPlanBox").click(function(){planRowDetail.planRowDetailList()});//绑定查看计划弹出层查询按钮事件(查看计划弹出层)
//	$("#resetPlanBox").click(function(){planRowDetail.resetPlanBoxList()});//绑定查看计划弹出层重置按钮事件(查看计划弹出层)
//	$("#importPlanBoxBtn").click(function(){planBoxFillData()});//绑定导入计划按钮事件(查看计划)
//	$("#queryTaskBox").click(function(){taskRowDetail.taskRowDetailList()});//绑定查看任务弹出层查询按钮事件(查看任务弹出层)
//	$("#resetTaskBox").click(function(){taskRowDetail.resetTaskBoxList()});//绑定查看任务弹出层重置按钮事件(查看任务弹出层)
//	$("#importTask").click(function(){taskBoxFillData()});//绑定导入任务按钮事件(查看任务弹出层)
//	$("#addTask").unbind();//解除工作任务按钮绑定事件
	$("#addTaskOfPlan").unbind();//解除工作任务按钮绑定事件
//	$("#addTask").click(function(){taskArra.addOrTempTask(hideDiv);});//绑定下发工作任务按钮事件
//	$("#addTask").click(function(){taskArra.addOrTempTaskNew(taskRowDetail.hideDiv,'zdsz');});
//	$("#addTaskOfPlan").click(function(){taskArra.addOrTempTask(taskRowDetail.hideDiv);});//绑定下发工作任务按钮事件
	$("#addTaskOfPlan").click(function(){callMothed();});//绑定下发工作任务按钮事件
//	$("#closeTask").click(function(){closeSendTask()});//绑定下发任务弹出层关闭按钮事件
})