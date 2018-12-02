var TemplateJgFilePath = ""; 
var TemplateJg = {
		
		//重复提交标识
		subState : false,
	    addTable:null,
		//分页对象
		 oTable : null,
		 fileName:'',
		 fileSize:'',
		 filePath:'',
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
		               	{ "mData": "title" },
		               	{ "mData": "description" },
		               	{ "mData": "type", "mRender" : function(mData) {
		               		return mData == 0 ? "word" : (mData == 1 ? "word" : "excle");
		               	}
		               	},
//		               	{ "mData": "isUse", "mRender" : function(mData) {
//		               		return mData == 1 ? "启用" : "禁用";
//		               	}
//		               	},
		               	{ "mData": "createUserName"},
		               	{ "mData": function(source) {
		               		return oTableSetButtons(source);
		               	}}
		                ],
		                
	          //组装后台参数
	            oTableFnServerParams : function(aoData){
	            	//排序条件
	            	getTableParameters(TemplateJg.oTable, aoData);
	            	//组装查询条件
	            	$.each($("#searchTemplateJgForm").serializeArray(), function(i, o) {
	            		if(o.value){
	            			aoData.push({ "name": o.name, "value": o.value});
	            		}
	            	});
	            },
		                
		              //分页查询
	            TemplateJgList : function () {
		                	//$('#insideOut-list').fadeIn();
		                	if (TemplateJg.oTable == null) {
		                		TemplateJg.oTable = $('#TemplateJgTable').dataTable( {
		                			iDisplayLength: TemplateJg.pageRows,//每页显示多少条记录
		                			sAjaxSource: getRootPath()+"/doc/templateJg/manageList.action",
		                			fnServerData: oTableRetrieveData,//查询数据回调函数
		                			aoColumns: TemplateJg.oTableAoColumns,//table显示列
		                			fnServerParams: function ( aoData ) {//传参
		                				TemplateJg.oTableFnServerParams(aoData);
		                			},
		                			aaSorting:[],//设置表格默认排序列
		                			//fnDrawCallback: rememberPage,
		                			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
		                			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
		                	        aoColumnDefs: [{bSortable: false, aTargets: [0,1,2,4,5]}]
		                		} );
		                	} else {
		                		TemplateJg.oTable.fnDraw();
		                	}
		                },
		                
		                
		        /*弹出框列表*/
		            	//显示列信息
        		oTableAoFlowColumns : [
        		               	{mData: function(source) {
        		               			return "<input type='checkbox' name='flows' createFlowUserId="+source.createFlowUserId+" flowName="+source.flowName+" flowType="+source.flowType+" value="+ source.flowId + ">";
        		               		}
        		               	},
        		               	{ "mData": "flowType", "mRender" : function(mData) {
        		               			return mData == 0 ? "发文" : "收文";
        		               		}
        		               	},
        		               	{ "mData": "flowName" },
        		               	{ "mData": function(full) {
        		               		return '点击查看流程图';
        		               	}}
        		                ],
        		oTableFnServerFlowParams : function(aoData){
        			            	//排序条件
        			            	getTableParameters(TemplateJg.oTable, aoData);
        			            	//组装查询条件
        			            	/*$.each($("#searchFlowForm").serializeArray(), function(i, o) {
        			            		if(o.value){
        			            			aoData.push({ "name": o.name, "value": o.value});
        			            		}
        			            	});*/
        			            },
		        addExchangeFlowsTable : function () {
            	//$('#insideOut-list').fadeIn();
            	if (TemplateJg.addTable == null) {
            		TemplateJg.addTable = $('#addExchangeFlowsTable').dataTable( {
            			iDisplayLength: 5,//每页显示多少条记录
            			sAjaxSource: getRootPath()+"/doc/exchangeFlow/manageDocFlows.action",
            			fnServerData: oTableRetrieveData,//查询数据回调函数
            			aoColumns: TemplateJg.oTableAoFlowColumns,//table显示列
            			fnServerParams: function ( aoData ) {//传参
            				TemplateJg.oTableFnServerFlowParams(aoData);
            			},
            			aaSorting:[],
            			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
            			aoColumnDefs: [{bSortable: false, aTargets: [0,3]}]
            			
            			
            		} );
            	} else {
            		TemplateJg.oTable.fnDraw();
            	}
            },
            closeAddForm:function(){
            	TemplateJg.clearForm();
            	$('#addTemplateJg').modal('hide');
            	
            },
            editTemplateJgContext:function(){
            	var b = new Base64();
            	//alert(TemplateJg.filePath);
            	//var str = b.encode(TemplateJg.filePath);  
            	TemplateJgFilePath = TemplateJg.filePath;
            	/*if($('input[type="radio"][name="templateJgtype"]:checked').attr('value') == "0") {
            		window.open(getRootPath()+"/doc/templateJg/openTemplateJgPage.action?type=0");
            	}else {
            		window.open(getRootPath()+"/doc/templateJg/openTemplateJgPage.action?type=1");
            	}*/
            	window.open(getRootPath()+"/doc/templateJg/openTemplateJgPage.action");
            	/*
            	 $.ajax({
	    			 type : "POST",
	    			 url : getRootPath()+"/doc/TemplateJg/openTemplateJgPage.action",
	    			 //data : {id: id,isUse:use},
	    			 //dataType : "json",
	    			 success : function(data) {
	    				 if(data == '1') {
	    					 //更新成功
	    					//TemplateJg.exchangeFlowList();
	    				 }
	    			 } ,
	    			 error:function() {
	    				 
	    			 }
	    		 });*/
            	 
            	 
            },
            clearForm:function(){
            	TemplateJg.disableRadio('');
            	opts['echoData'] = [];
            	$("#TemplateJgControlTree").empty();
            	$("#TemplateJgControlTree").deptAndPersonControl(opts);
				  $('#newtitle').val('');
				  $('#oldtitle').val('');
				  $("input[type=radio][name=templateJgtype][value=0]").prop("checked",'checked');
				  document.getElementById('description').value = '';
				  /*$('#description').attr('value',data['description']);*/
				  $('#id').val('');
				  $('#recordid').val('');
				  $('#modifyDate').val('');
				  $('#description').val('');
				  TemplateJg.filePath = '';
				  hideErrorMessage();
            },
            saveTemplateJg:function(){
            	if (TemplateJg.subState)return;
            	if(!TemplateJg.filePath) {
            		//alert(2222);
            	}
            	if($("#nameForm").valid()) {
            		TemplateJg.subState = true;
            		//alert(TemplateJg.filePath);
            		var url = getRootPath()+"/doc/templateJg/save.action?token="+$('#token').val();
            		var obj = {controls:opts['echoData'],type:$('input[type="radio"][name="templateJgtype"]:checked').attr('value'),filepath:$('#recordid').val(),title:$('#newtitle').val(), description:$('#description').val()};
            		if($('#id').val().length > 0) {
            			url = getRootPath()+"/doc/templateJg/updateTemplate.action?modifyDate="+$('#modifyDate').val();
            			obj = {controls:opts['echoData'],id:$('#id').val(),type:$('input[type="radio"][name="templateJgtype"]:checked').attr('value'),title:$('#newtitle').val(), description:$('#description').val(),filepath:$('#recordid').val()};
            		}
            		//$("input[type='radio'][name='type']:checked").attr('index');
            		 //var d = {loginName:'login我Name', users:[{id:'id1',code:'12'},{id:'id2',code:'32'}]};
            		 var p = JSON.stringify(obj,replaceJsonNull);
            	//	 alert(p);
            		jQuery.ajax({
            			url : url,
            			type : 'POST',
            			cache: false,
            		    contentType: "application/json;charset=UTF-8",
            			data : p,
            			success : function(data) {
            				$("#token").val(data.token);
            				TemplateJg.closeAddForm();
            				TemplateJg.subState = false;
            				if(data.success == "true"){
            					msgBox.tip({
            						type:"success",
            						content: $.i18n.prop("JC_SYS_001")
            					});
            				}else {
            					if(data.labelErrorMessage){
            						msgBox.info({
            							content: data.labelErrorMessage
            						});
            					} else{
            						msgBox.info({
            							content: data.errorMessage
            						});
            					}
            				}
            				//getToken();
            				TemplateJg.TemplateJgList();
            			},
            			error : function() {
            				TemplateJg.subState = false;
            				msgBox.tip({
            					content: $.i18n.prop("JC_SYS_002")
            				});
            			}
            		});
            	}
            },
		     showAddDialog:function() {
		    	 TemplateJg.clearForm();
		    	 //$("input[type=radio][name=txzq][index="+remindObj_.cycle+"]").attr("checked",'checked');
		    	 $("#Modal_Titile").html("新增");
		    	 $('#addTemplateJg').modal('show');
		    	 //getToken();
		    	 //TemplateJg.addExchangeFlowsTable();
		     },
		     disableRadio:function(flag){
		    	 $("input[type=radio][name=templateJgtype]").each(function(i, item){
		    		// $(this).css('disabled', 'disabled');
		    		 this.disabled = flag;
		    	 });
		     },
		     get:function(){
		    	 TemplateJg.clearForm();
		    	 var id = arguments[0];
		    	 //$('#id').attr('value',id);
		    	 $.ajax({
	    			 type : "get",
	    			 url : getRootPath()+"/doc/templateJg/get.action",
	    			 data : {id: id},
	    			 dataType : "json",
	    			 cache:false,
	    			 success : function(data) {
	    				 //alert(data.controls[0]['id']);
	    				 if(!data) {
	    					 msgBox.info({
     							content: $.i18n.prop("JC_SYS_054")
     						});
	    					 return;
	    				 }
	    				 opts['echoData'] = [];
	    				 if(data.controls) {
	    					 //opts['echoData'] = data.controls;
	    					 for(var i = 0; i < data.controls.length; i++) {
	    						 opts['echoData'].push({id:data.controls[i]['id'],text:data.controls[i]['text'],type:data.controls[i]['type']});
	    					 }
	    				 }  
	    				  $("#TemplateJgControlTree").empty();
	    				  $("#TemplateJgControlTree").deptAndPersonControl(opts);
	    				  $("#Modal_Titile").html("编辑");
	    				  $('#addTemplateJg').modal('show');
	    				  $('#newtitle').val(data['title']);
	    				  $('#oldtitle').val(data['title']);
	    				  $('#description').val(data['description']);
	    				  //document.getElementById('description').value = data['description'];
	    				  /*$('#description').attr('value',data['description']);*/
	    				  $('#id').val(data['id']);
	    				  $('#modifyDate').val(data['modifyDate']);
	    				  $('#recordid').val(data['filepath']);
	    				  //TemplateJg.filePath = data['filepath'];
	    				  $("input[type=radio][name=templateJgtype][value="+data.type+"]").prop("checked",'checked');
	    				  TemplateJg.disableRadio('disabled');
	    			 } ,
	    			 error:function() {
	    				 
	    			 }
	    		 });
		     },
		   
		     deleteTemplateJg:function(){
			    	var id = arguments[0];
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
				    			 url : getRootPath()+"/doc/templateJg/deleteByIds.action",
				    			 data : {ids: idsArr.join(',')},
				    			 //dataType : "json",
				    			 success : function(data) {
				    				 if(data.success == "true"){
				    					 TemplateJg.TemplateJgList();
			            					msgBox.tip({
			            						type:"success",
			            						content: $.i18n.prop("JC_SYS_005")
			            					});
			            				}else {
			            					if(data.labelErrorMessage){
			            						msgBox.info({
			            							content: data.labelErrorMessage
			            						});
			            					} else{
			            						msgBox.info({
			            							content: data.errorMessage
			            						});
			            					}
			            				}
				    			 } ,
				    			 error:function() {
				    				 
				    			 }
				    		 });
			    			}
			    		});
		    		 
		     },
		     updateUseState:function() {
		    	 var id = arguments[0];
		    	 var use = arguments[1];
		    	 var msg = (use == '1' ? '确定禁用流程吗？':'确定启用流程吗？');
		    		msgBox.confirm({
		    			content: msg,
		    			success: function(){
		    				 $.ajax({
				    			 type : "POST",
				    			 url : getRootPath()+"/doc/TemplateJg/updateUse.action",
				    			 data : {id: id,isUse:use},
				    			 //dataType : "json",
				    			 success : function(data) {
				    				 if(data.success == "true"){
				    					 TemplateJg.TemplateJgList();
			            					msgBox.tip({
			            						type:"success",
			            						content: $.i18n.prop("JC_SYS_003")//'修改成功'//$.i18n.prop("JC_SYS_002")
			            					});
			            				}else {
			            					if(data.labelErrorMessage){
			            						msgBox.info({
			            							content: data.labelErrorMessage
			            						});
			            					} else{
			            						msgBox.info({
			            							content: data.errorMessage
			            						});
			            					}
			            				}
				    			 } ,
				    			 error:function() {
				    				 
				    			 }
				    		 });
		    			}
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
		     		alertx("请选择要删除的记录");
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
		    					TemplateJg.exchangeFlowList();
		    				}
		    			});
		    			}
		    		});
		     	 
		     },
		     saveSelectedFlows:function() {
		    	// TemplateJg.addExchangeFlowsTable();
		    	 var flows = [];
		    	 $('input[type="checkbox"][name="flows"]:checked').each(function(){
		    		 var flow = {};
		    		 flow.flowId = $(this).attr('value');
		    		 flow.flowName = $(this).attr('flowName');
		    		 flow.flowType = $(this).attr('flowType');
		    		 flow.createFlowUserId = $(this).attr('createFlowUserId');
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
            				TemplateJg.subState = false;
            				alertx("保存成功");
            				TemplateJg.exchangeFlowList();
            			},
            			error : function(msg) {
            				TemplateJg.subState = false;
            				alertx(msg);
            			}
            		});
		    	 } else {
		    		 alertx('请选择流程');
		    	 }
		     },
		     queryReset : function(){
		                	$('#searchTemplateJgForm')[0].reset();
		                	//TemplateJg.TemplateJgList();
		                }

};
TemplateJg.pageRows=null;


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
TemplateJg.showInsideOutAddDiv = function (id){
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
TemplateJg.replyTable = null;
//组装后台参数
TemplateJg.replyTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(TemplateJg.replyTable, aoData);
	//组装查询条件
	$.each($("#insideOutReplyForm").serializeArray(), function(i, o) {
		alert(o.id);
		if(o.value != ""){
			aoData.push({ "insideOutId": o.id});
		}
	});
};
//显示回复信息
TemplateJg.replyColumns = [
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
TemplateJg.showInsideOutReplyDiv = function(insideOutId){
	$('#insideOut-reply').modal('show');
	//TemplateJg.replyTable.fnClearTable();
	$("#insideOutReplyForm #id").val(insideOutId);
	//显示回复列信息
	if (TemplateJg.replyTable == null) {
		TemplateJg.replyTable = $('#insideOutReplyTable').dataTable( {
			"iDisplayLength": TemplateJg.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/insideIn/queryReplayList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": TemplateJg.replyColumns,//table显示列
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
		TemplateJg.replyTable.fnDraw();
	}
};

//字典项初始化
TemplateJg.initDic = function(){
	//dic.fillDics("category", "category");
};
function selectControlCallback(data) {
	//alert('33'+data); type = 1 人员，type = 2 组织
	opts['echoData'] = [];
	if(data && data.length > 0) {
		opts['echoData'] = data;
	}
}
var opts = {
		widgetId : "aaa",							//控件ID
		widgetName : "bbb",		
		//回显数据，没有可以不写
		echoData: [],
		callbackFunction: selectControlCallback
	};

//初始化
jQuery(function($){
	
	
	/*$("#showAddDiv").click(TemplateJg.showAddDialog);
	$("#saveSelectedFlows").click(TemplateJg.saveSelectedFlows);
	$("#deleteFlows").click(TemplateJg.deleteFlows);
	$("#queryFlow").click(TemplateJg.exchangeFlowList);
	$("#queryReset").click(TemplateJg.queryReset);
	$("#flowClose").click(TemplateJg.closeAddForm);*/
	TemplateJg.pageRows = TabNub>0 ? TabNub : 10;
	$("#TemplateJgControlTree").deptAndPersonControl(opts);
	$("#showAddDiv_t").click(TemplateJg.showAddDialog);
	$("#showAddDiv").click(TemplateJg.showAddDialog);
	$("#queryFlow").click(TemplateJg.TemplateJgList);
	$("#queryReset").click(TemplateJg.queryReset);
	$("#editTemplateJgContext").click(TemplateJg.editTemplateJgContext);
	$("#saveTemplateJg").click(TemplateJg.saveTemplateJg);
	$("#templateJgClose").click(TemplateJg.closeAddForm);
	$("#deleteTemplateJgs").click(TemplateJg.deleteTemplateJg);
	TemplateJg.TemplateJgList();
	$("#nameForm").validate({
		ignore: ".ignore",
        rules: {
           newtitle: 
		   {
			    required: true,
			    maxlength: 60,
			    fileName:true,
			    remote:{
			        url: getRootPath()+"/doc/TemplateJg/checkTitle.action",
			        type: "get",
			        dataType: 'json',
			        async : false,
			        data: {
			            'title': function(){return $("#nameForm #newtitle").val();},
			            'titleOld': function(){return $("#nameForm #oldtitle").val();}
			        }
			    }
		   },
		   description: 
		   {
			    required: false,
			    maxlength: 100
		   }
	    },
	    messages: {
	    	newtitle: {remote: "模板名称已存在"}
		}
	});
	$("#searchTemplateJgForm").validate({
		ignore: ".ignore",
		rules: {
			title: 
			{
				maxlength: 60,
				fileName:true
			}
		}
	});
	
	//日历控件重新初始化
//	$(".datepicker-input").datepicker();
	//TemplateJg.exchangeFlowList();
	//TemplateJg.initDic();
	
	//TemplateJg = jQuery.validator.format($.trim($("#TemplateJg").val()));
});