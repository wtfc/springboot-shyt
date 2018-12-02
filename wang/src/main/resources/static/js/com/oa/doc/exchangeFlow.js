var exchangeFlow = {
		
		//重复提交标识
		subState : false,
	    addTable:null,
		//分页对象
		 oTable : null,
		//显示列信息
		 /*	{mData: "status", mRender : function(mData, type, full){
		        			return full.statusValue;
		        		}
		        	},*/
		oTableAoColumns : [
		               	{mData: function(source) {
		               			return "<input type='checkbox' name='ids' value="+ source.id + ">";
		               		}
		               	},
		               	{ "mData": "flowType", "mRender" : function(mData) {
		               			return mData == 0 ? "发文" : "收文";
		               		}
		               	},
		               	{ "mData": "flowName" },
//		               	{ "mData": "isUse", "mRender" : function(mData) {
//		               		return mData == 1 ? "启用" : "禁用";
//		               	}
//		               	},
		               	{ "mData": "createFlowUser"},
		               	{ "mData": function(source) {
		               		return oTableSetButtons(source);
		               	}}
		                ],
		                
	          //组装后台参数
	            oTableFnServerParams : function(aoData){
	            	//排序条件
	            	getTableParameters(exchangeFlow.oTable, aoData);
	            	//组装查询条件
	            	$.each($("#searchFlowForm").serializeArray(), function(i, o) {
	            		if(o.value){
	            			aoData.push({ "name": o.name, "value": o.value});
	            		}
	            	});
	            },
		                
		              //分页查询
		       exchangeFlowList : function () {
		                	//$('#insideOut-list').fadeIn();
		                	if (exchangeFlow.oTable == null) {
		                		exchangeFlow.oTable = $('#exchangeFlowTable').dataTable( {
		                			iDisplayLength: exchangeFlow.pageRows,//每页显示多少条记录
		                			sAjaxSource: getRootPath()+"/doc/exchangeFlow/manageList.action",
		                			fnServerData: oTableRetrieveData,//查询数据回调函数
		                			aoColumns: exchangeFlow.oTableAoColumns,//table显示列
		                			fnServerParams: function ( aoData ) {//传参
		                				exchangeFlow.oTableFnServerParams(aoData);
		                			},
		                			aaSorting:[],//设置表格默认排序列
		                			//fnDrawCallback: rememberPage,
		                			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
		                			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
		                	        aoColumnDefs: [{bSortable: false, aTargets: [0,3,4]}]
		                			
		                			
		                		} );
		                	} else {
		                		exchangeFlow.oTable.fnDraw();
		                	}
		                },
		                
		                
		        /*弹出框列表*/
		            	//显示列信息
        		oTableAoFlowColumns : [
        		               	{mData: function(source) {
        		               		     var typeId = source['typeId'];
        		               		     /*var flowType = "0";
        		               		     if("31" == typeId) {
        		               		    	flowType = "1";
        		               		     } else if("29" == typeId) {
        		               		    	 flowType = "0";
        		               		     }*/
        		               		     var flowType = typeId;
        		               			return "<input type='checkbox' name='flows' createUser="+source.createUser+" flowName="+source.flowName+" flowType="+flowType+" value="+ source.flowId + ">";
        		               		}
        		               	},
        		               	{ "mData": "typeName"},
        		               	{ "mData": "flowName"},
        		               	{ "mData": function(full) {
        		               		//var search = "<shiro:hasPermission name='insideIn:search'></shiro:hasPermission>";
        		               		return  "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"workFlow.openProcessDefinitionMap('"+ full.flowId+ "')\" role=\"button\" data-toggle=\"modal\"><i   data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" ></i>查看流程图</a>";
        		               		//return "<a href=\"#\" onclick=\"workFlow.openProcessDefinitionMap('"+ full.flowId+ "')\">点击查看流程图</a>";
        		               	}}
        		                ],
        		oTableFnServerFlowParams : function(aoData){
        			            	//排序条件
        			            	getTableParameters(exchangeFlow.oTable, aoData);
        			            	//组装查询条件
        			            	/*$.each($("#searchFlowForm").serializeArray(), function(i, o) {
        			            		if(o.value){
        			            			aoData.push({ "name": o.name, "value": o.value});
        			            		}
        			            	});*/
        			            },
		        addExchangeFlowsTable : function () {
		        	$.ajaxSetup ({
		    			cache: false //设置成false将不会从浏览器缓存读取信息
		    		});
            	//$('#insideOut-list').fadeIn();
		        	//exchangeFlow.addTable = null;
                if (exchangeFlow.addTable == null) {
            		exchangeFlow.addTable = $('#addExchangeFlowsTable').dataTable( {
            			//iDisplayLength: 5,//每页显示多少条记录
            			bPaginate: false,
            			sAjaxSource: getRootPath()+"/doc/exchangeFlow/manageDocFlows.action",
            			fnServerData: oTableRetrieveData,//查询数据回调函数
            			aoColumns: exchangeFlow.oTableAoFlowColumns,//table显示列
            			fnServerParams: function ( aoData ) {//传参
            				exchangeFlow.oTableFnServerFlowParams(aoData);
            			},
            			aaSorting:[],
            			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
            			aoColumnDefs: [{bSortable: false, aTargets: [0,1,2,3]}]
            			
            		} );
            	} else {
            		exchangeFlow.addTable.fnDraw();
            	}
            },
            closeAddForm:function(){
            	$('#addFlows').modal('hide');
            	
            },
		     showAddDialog:function() {
		    	 $('#addFlows').modal('show');
		    	 //getToken();
		    	 exchangeFlow.addExchangeFlowsTable();
		     },
		     updateUseState:function() {
		    	 var id = arguments[0];
		    	 var use = arguments[1];
		    	 var msg = (use == '1' ? '确定禁用流程吗？':'确定启用流程吗？');
		    	 confirmx(msg,function(){
		    		 $.ajax({
		    			 type : "POST",
		    			 url : getRootPath()+"/doc/exchangeFlow/updateUse.action",
		    			 data : {id: id,isUse:use},
		    			 //dataType : "json",
		    			 success : function(data) {
		    				 if(data == '1') {
		    					 //更新成功
		    					exchangeFlow.exchangeFlowList();
		    				 }
		    			 } ,
		    			 error:function() {
		    				 
		    			 }
		    		 });
			     	});
		    	 
		     },
		     deleteFlows : function (id) {
		    	var idsArr = [];
		    	if('number' == typeof(id)) {
		    		//单个删除
		    		idsArr.push(id);
		    	} else {
		    		//批量删除
		    		$("[name='ids']:checked").each(function() {
		    			idsArr.push($(this).val());
		    		});
		    	}
		     	if (idsArr.length == 0) {
		     		//alertx("请选择要删除的记录");
		     		msgBox.info({
		    			content: $.i18n.prop("JC_SYS_061")
		    		});
		     		return;
		     	}
		     	msgBox.confirm({
		    		content: $.i18n.prop("JC_SYS_034"),
		    		success: function(){
		    			$.ajax({
			     			type : "POST",
			     			url : getRootPath()+"/doc/exchangeFlow/deleteByIds.action",
			     			data : {"ids": idsArr.join(',')},
			     			//dataType : "json",
			     			success : function(data) {
	            				msgBox.tip({
	        						type:"success",
	        						content: $.i18n.prop("JC_SYS_005")
	        					});
			     				exchangeFlow.exchangeFlowList();
			     			}
			     		});
		    			}
		    		});
		     },
		     saveSelectedFlows:function() {
		    	// exchangeFlow.addExchangeFlowsTable();
		    	 var flows = [];
		    	 $('input[type="checkbox"][name="flows"]:checked').each(function(){
		    		 var flow = {};
		    		 flow.remind = r1;
		    		 flow.flowId = $(this).attr('value');
		    		 flow.flowName = $(this).attr('flowName');
		    		 flow.flowType = $(this).attr('flowType');
		    		 flow.createUser = $(this).attr('createUser');
		    		 flows[flows.length] = flow;
		    	 });
		    	 if(flows.length > 0) {
		    		 $('#addFlows').modal('hide');
		    		var p = JSON.stringify(flows,replaceJsonNull);
	    			jQuery.ajax({
            			url : getRootPath()+"/doc/exchangeFlow/addbatch.action?token="+$('#token').val(),
            			type : 'POST',
            			data : p,
            			contentType:"application/json",  
            			success : function(data) {
            				exchangeFlow.subState = false;
            				$("#token").val(data.token);
            				//alertx("保存成功");
            				msgBox.tip({
        						type:"success",
        						content: $.i18n.prop("JC_SYS_001")
        					});
            				exchangeFlow.exchangeFlowList();
            			},
            			error : function(msg) {
            				exchangeFlow.subState = false;
            				msgBox.tip({
        						//type:"success",
        						content: $.i18n.prop("JC_SYS_002")
        					});
            			}
            		});
		    	 } else {
		    		// alertx('请选择流程');
		    		 msgBox.tip({
 						content: $.i18n.prop("JC_WORKFLOW_009")
 					});
		    	 }
		     },
		     queryReset : function(){
		                	$('#searchFlowForm')[0].reset();
		                	//exchangeFlow.exchangeFlowList();
		                }

};
exchangeFlow.pageRows=null;


//分页处理 start
//分页对象




/*
 * 
 * 	//table对象为null时初始化
	if (user.oTable == null) {
		user.oTable = $('#userTable').dataTable( {
			iDisplayLength: user.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/sys/user/manageList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: user.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				user.oTableFnServerParams(aoData);
			},
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,6]}]
		} );
	} else {
		//table不为null时重绘表格
		user.oTable.fnDraw();
	}
 * */



//显示分发公文弹出层
exchangeFlow.showInsideOutAddDiv = function (id){
	$('#insideOutAddForm')[0].reset();
	var url = getRootPath()+"/doc/insideOut/get.action";
	var params = {
		time: new Date(),
		id: id
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        async: false,
        success: function(data, textStatus, xhr) {	
        	$("#insideOutAddForm #title").val(data.title);
        	$("#insideOutAddForm #docType").val(data.docType);
        	$("#insideOutAddForm #docId").val(data.docId);
        	$("#insideOutAddForm #formContent").val(data.formContent);
        	$("#insideOutAddForm #content").val(data.content);
        	$('#insideOut-add').modal('show');
        },error:function(){
        	alertx("获取公文数据失败");
        }
    });
};



//回复分页对象
exchangeFlow.replyTable = null;
//组装后台参数
exchangeFlow.replyTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(exchangeFlow.replyTable, aoData);
	//组装查询条件
	$.each($("#insideOutReplyForm").serializeArray(), function(i, o) {
		alert(o.id);
		if(o.value != ""){
			aoData.push({ "insideOutId": o.id});
		}
	});
};
//显示回复信息
exchangeFlow.replyColumns = [
                             //不需要排序的列直接用mData function方式
                         	{mData: function(source) {
                         			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
                         		}
                         	},
                         	//需要排序列需要指定属性
                         	{mData: "flowType", mRender : function(mData) {
                         			return mData == 0 ? "发文" : "收文";
                         		}
                         	},
                         	{mData: "flowName"},
                         	{mData: "createUser"},
                         	/*{mData: "status", mRender : function(mData, type, full){
                         			return full.statusValue;
                         		}
                         	},*/
                         	//设置权限按钮
                         	{mData: function(source) {
                         		return oTableSetButtons(source);
                         	}}
                             ];
exchangeFlow.showInsideOutReplyDiv = function(insideOutId){
	$('#insideOut-reply').modal('show');
	//exchangeFlow.replyTable.fnClearTable();
	$("#insideOutReplyForm #id").val(insideOutId);
	//显示回复列信息
	if (exchangeFlow.replyTable == null) {
		exchangeFlow.replyTable = $('#insideOutReplyTable').dataTable( {
			"iDisplayLength": exchangeFlow.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/insideIn/queryReplayList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": exchangeFlow.replyColumns,//table显示列
			//"bStateSave": true,
			//"fnPreDrawCallback": test,
			//传参
			"fnServerParams": function ( aoData ) {
				//console.log(aoData);
				//aoData.push({ "insideOutId": insideOutId});
				aoData.push({ "name":"insideOutId", "value": insideOutId});
			},
			aaSorting:[],
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,4]}]
		} );
	} else {
		taskDataTable.fnClearTable(); 
		exchangeFlow.replyTable.fnDraw();
	}
};

//字典项初始化
exchangeFlow.initDic = function(){
	//dic.fillDics("category", "category");
};
//初始化
jQuery(function($){
	
	exchangeFlow.pageRows = TabNub>0 ? TabNub : 10;
	$("#showAddDiv").click(exchangeFlow.showAddDialog);
	$("#showAddDiv_t").click(exchangeFlow.showAddDialog);
	$("#saveSelectedFlows").click(exchangeFlow.saveSelectedFlows);
	$("#deleteFlows").click(exchangeFlow.deleteFlows);
	$("#queryFlow").click(exchangeFlow.exchangeFlowList);
	$("#queryReset").click(exchangeFlow.queryReset);
	$("#flowClose").click(exchangeFlow.closeAddForm);
	
	
	//日历控件重新初始化
//	$(".datepicker-input").datepicker();
	exchangeFlow.exchangeFlowList();
	//exchangeFlow.initDic();
	
	//template = jQuery.validator.format($.trim($("#template").val()));
	
	$("#searchFlowForm").validate({
		ignore: ".ignore",
        rules: {
        	flowName: 
			{
				maxlength: 100,
				fileName:true
			}
	    }
	});
});