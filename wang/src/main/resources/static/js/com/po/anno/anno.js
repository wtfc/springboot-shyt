/**
 * 批注管理js
 */
var anno = {};
anno.pageRows = null;
/**重复提交标识*/
anno.subState = false;

/**清空表单*/
anno.clearForm = function () {
	$('#annoQueryForm')[0].reset();
	hideErrorMessage();
};

/**分页对象*/
anno.oTable = null;
/**显示列信息*/
anno.oTableAoColumns = [
	{mData:"annoName"},//批注文件名称
	{mData: "annoType", mRender : function(mData, type, full){
		return full.annoTypeValue;
		}
	},//批注类别
	{mData: "annoUserId", mRender : function(mData, type, full){
		return full.annoUserIdValue;
		}
	},//批注人
	{mData: "annoUserDepid", mRender : function(mData, type, full){
		return full.annoUserDepidValue;
		}
	},//批注部门
	{mData: "byAnnoUserId", mRender : function(mData, type, full){
		return full.byAnnoUserIdValue;
		}
	},//被批注人
	{mData: "byAnnoUserDepid", mRender : function(mData, type, full){
		return full.byAnnoUserDepidValue;
		}
	},//被批注部门
	{mData: "annoDate"},//批注时间
	//设置权限按钮
	{mData: null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
];

/**组装后台参数*/
anno.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(anno.oTable, aoData);
	//组装查询条件
	$.each($("#annoForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

/**分页查询*/
anno.annoList = function(){
	if(anno.oTable == null){
		anno.oTable = $('#annoTable').dataTable( {
			"iDisplayLength": anno.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/anno/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": anno.oTableAoColumns,//table显示列
			"fnServerParams": function (aoData) {
				anno.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			"aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,7]}]
		});
	}else{
		anno.oTable.fnDraw();
	}
};

/**清空汇总查询列表*/
anno.queryAnnoFormReset = function(){
	//清空表单
	$('#annoForm')[0].reset();
	//清空选择树隐藏域
	selectControl.clearValue("annoUserId-annoUserId");
	selectControl.clearValue("annoUserDepid-annoUserDepid");
	selectControl.clearValue("byAnnoUserId-byAnnoUserId");
	selectControl.clearValue("byAnnoUserDepid-byAnnoUserDepid");
};

/**
 * 查看跳转方法
 */
anno.loadDetailJump = function (id) {
	var ajaxData = {
		id:id,
		time : new Date()
	};
	jQuery.ajax({
		url : getRootPath() + "/po/plan/getAnnoPlan.action",
		type : 'GET',
		data : ajaxData,
		async : false,
		success : function(data) {
			if(data){
//				openform(data.workId,data.piId,"business","view");
				openformNoPerson(data.workId,data.piId,"business","view");
			}
		}
	});
};

/**工作计划跳转*/
anno.loadJump = function (id) {
	loadrightmenu("/po/plan/planForm.action?time=" + new Date(),true);
	anno.getPlan(id);
};

/**工作日志跳转*/
anno.showWorklog = function(id){
	anno.showDetail(id);
};

/**工作日程跳转*/
anno.showDiary = function(id){
	$("#diaryId").val(id);
	anno.getDetailData(id);
};

/**督办协办跳转*/
anno.showTask = function(id){
	loadrightmenu("/po/workTask/queryAnnotationUrl.action?id="+id+"&time=" + new Date(),"督办协办");
};

/**
 * 工作计划数据回显
 */
anno.getPlan = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/plan/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				//将后台绑定的数据回显到相应表单中
				$("#planForm").fill(data);
				//填充工作总结详细数据
				if(data.sumDetails && data.sumDetails.length > 0) {
					for(var i=0;i<data.sumDetails.length;i++){
						var o = data.sumDetails[i];
						$("#preSum_0_"+(i+1)).val(o.content);
						$("#preSum_1_"+(i+1)).val(o.standard);
						$("#preSum_2_"+(i+1)).val(o.directorId);
						$("#preSum_3_"+(i+1)).val(o.startTime);
						$("#preSum_4_"+(i+1)).val(o.endTime);
						$("#preSum_5_"+(i+1)).val(o.compRate);
						$("#preSum_6_"+(i+1)).val(o.description);
					}
				}
				//填充工作计划详细数据
				if(data.planDetails && data.planDetails.length > 0) {
					for(var i=0;i<data.planDetails.length;i++){
						var o = data.planDetails[i];
						$("#prePlan_0_"+(i+31)).val(o.content);
						$("#prePlan_1_"+(i+31)).val(o.standard);
						$("#prePlan_2_"+(i+31)).val(o.directorId);
						$("#prePlan_3_"+(i+31)).val(o.startTime);
						$("#prePlan_4_"+(i+31)).val(o.endTime);
						$("#prePlan_5_"+(i+31)).val(o.compRate);
					}
				}
			}
		}
	});
};

//领导批注框清空
anno.clearleaderIdea=function(){
	$("#leaderIdeaContent").val("");
};

//初始化函数
jQuery(function($) {
	//计算分页显示条数
	anno.pageRows = TabNub>0 ? TabNub : 10;
	//绑定查询按钮事件
	$("#queryAnno").click(anno.annoList);
	//绑定重置按钮事件
	$("#resetAnno").click(anno.queryAnnoFormReset);
	//初始化批注人员选择树
	selectControl.init("annoUserTree","annoUserId-annoUserId",false,true);//单选人员
	//初始化批注部门选择树
	selectControl.init("annoDeptTree","annoUserDepid-annoUserDepid",false,false,0);//单选人员
	//初始化被批注人员选择树
	selectControl.init("byAnnoUserTree","byAnnoUserId-byAnnoUserId",false,true);//单选人员
	//初始化被批注部门选择树
	selectControl.init("byAnnoDeptTree","byAnnoUserDepid-byAnnoUserDepid",false,false,0);//单选人员
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//分页查询
	anno.annoList();
	//李洪宇 于2014-7-11 修改 开始
	//绑定批注”回复“
	$('#comment,#comment1').on('click','a[name="reply"]',function(){
		anno.commentReply(this);
	});
	//绑定批注”发送“
	$('#comment').on('click','a[name="send"]',function(){
		anno.commentSend(this);
	});
	//绑定批注”发送“
	$('#comment1').on('click','a[name="send"]',function(){
		anno.commentDiarySend(this);
	});
	//绑定领导批注保存按钮方法
	$("#leaderIdea").click(function(){anno.saveLeaderIdeaForm();});
	$("#clearleaderIdea").click(function(){anno.clearleaderIdea();});//领导批注框清空
	
	//绑定批注内容清除
	$('#worklogLeaderIdeaForm').on('click','#worklogClearleaderIdea',function(){$('#worklogLeaderIdeaContent').text('');});
	
	//绑定领导批注保存按钮方法
	$("#worklogLeaderIdea").click(function(){anno.saveWorklogLeaderIdeaForm();});
	$("#worklogClearleaderIdea").click(function(){
		$("#worklogLeaderIdeaContent").val('');
	});
	//绑定详情页展开收起
    $('a[name="detailShowAndHide"]').on('click',function(){
    	$('a[name="detailShowAndHide"]').toggle();
    	$(this).parent().parent().next().toggle();
    });
    
    /**临时批注校验，统一换成公共的校验*/
    $("#worklogLeaderIdeaForm").validate({
        rules: {
        	content: 
		   {
        		required: true,
			    maxlength: 2000
		   }
	    }
	});
    $("#commentForm").validate({
		rules: {
			replyContent: 
			{
				required: true,
				maxlength: 2000
			}
		}
	});
    $("#leaderIdeaForm").validate({
        rules: {
        	content: 
		   {
        		required: true,
			    maxlength: 2000
		   }
	    }
	});
    $("#commentForm1").validate({
    	rules: {
    		replyContent: 
    		{
    			required: true,
    			maxlength: 2000
    		}
    	}
    });
  //详细页查看提醒设置
	$('#detailRemind').on('click','a[name="detailRemindSet"]',function(){
		anno.showRemindReadonly();
	});
});
