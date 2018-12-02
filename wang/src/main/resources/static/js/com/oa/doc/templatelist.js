var templateFilePath = ""; 
var Template = {
		
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
		               		return mData == 0 ? "word" : (mData == 1 ? "单联" : "多联");
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
	            	getTableParameters(Template.oTable, aoData);
	            	//组装查询条件
	            	$.each($("#searchTemplateForm").serializeArray(), function(i, o) {
	            		if(o.value){
	            			aoData.push({ "name": o.name, "value": o.value});
	            		}
	            	});
	            },
		                
		              //分页查询
		       templateList : function () {
		                	//$('#insideOut-list').fadeIn();
		                	if (Template.oTable == null) {
		                		Template.oTable = $('#templateTable').dataTable( {
		                			iDisplayLength: Template.pageRows,//每页显示多少条记录
		                			sAjaxSource: getRootPath()+"/doc/template/manageList.action",
		                			fnServerData: oTableRetrieveData,//查询数据回调函数
		                			aoColumns: Template.oTableAoColumns,//table显示列
		                			fnServerParams: function ( aoData ) {//传参
		                				Template.oTableFnServerParams(aoData);
		                			},
		                			aaSorting:[],//设置表格默认排序列
		                			//fnDrawCallback: rememberPage,
		                			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
		                			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
		                	        aoColumnDefs: [{bSortable: false, aTargets: [0,1,2,4,5]}]
		                		} );
		                	} else {
		                		Template.oTable.fnDraw();
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
        			            	getTableParameters(Template.oTable, aoData);
        			            	//组装查询条件
        			            	/*$.each($("#searchFlowForm").serializeArray(), function(i, o) {
        			            		if(o.value){
        			            			aoData.push({ "name": o.name, "value": o.value});
        			            		}
        			            	});*/
        			            },
		        addExchangeFlowsTable : function () {
            	//$('#insideOut-list').fadeIn();
            	if (Template.addTable == null) {
            		Template.addTable = $('#addExchangeFlowsTable').dataTable( {
            			iDisplayLength: 5,//每页显示多少条记录
            			sAjaxSource: getRootPath()+"/doc/exchangeFlow/manageDocFlows.action",
            			fnServerData: oTableRetrieveData,//查询数据回调函数
            			aoColumns: Template.oTableAoFlowColumns,//table显示列
            			fnServerParams: function ( aoData ) {//传参
            				Template.oTableFnServerFlowParams(aoData);
            			},
            			aaSorting:[],
            			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
            			aoColumnDefs: [{bSortable: false, aTargets: [0,3]}]
            			
            			
            		} );
            	} else {
            		Template.oTable.fnDraw();
            	}
            },
            closeAddForm:function(){
            	Template.clearForm();
            	$('#addTemplate').modal('hide');
            	
            },
            editTemplateContext:function(){
            	var b = new Base64();
            	//alert(Template.filePath);
            	//var str = b.encode(Template.filePath);  
            	templateFilePath = Template.filePath;
            	if($('input[type="radio"][name="templatetype"]:checked').attr('value') == "0") {
            		window.open(getRootPath()+"/doc/template/openTemplatePage.action?type=0");
            	}else {
            		window.open(getRootPath()+"/doc/template/openTemplatePage.action?type=1");
            	}
            	/*
            	 $.ajax({
	    			 type : "POST",
	    			 url : getRootPath()+"/doc/template/openTemplatePage.action",
	    			 //data : {id: id,isUse:use},
	    			 //dataType : "json",
	    			 success : function(data) {
	    				 if(data == '1') {
	    					 //更新成功
	    					//Template.exchangeFlowList();
	    				 }
	    			 } ,
	    			 error:function() {
	    				 
	    			 }
	    		 });*/
            	 
            	 
            },
            clearForm:function(){
            	Template.disableRadio('');
            	opts['echoData'] = [];
            	$("#templateControlTree").empty();
            	$("#templateControlTree").deptAndPersonControl(opts);
				  $('#newtitle').val('');
				  $('#oldtitle').val('');
				  $("input[type=radio][name=templatetype][value=0]").prop("checked",'checked');
				  document.getElementById('description').value = '';
				  /*$('#description').attr('value',data['description']);*/
				  $('#id').val('');
				  $('#modifyDate').val('');
				  $('#description').val('');
				  Template.filePath = '';
				  hideErrorMessage();
            },
            saveTemplate:function(){
            	if (Template.subState)return;
            	if(!Template.filePath) {
            		//alert(2222);
            	}
            	if($("#nameForm").valid()) {
            		Template.subState = true;
            		//alert(Template.filePath);
            		var url = getRootPath()+"/doc/template/save.action?token="+$('#token').val();
            		var obj = {controls:opts['echoData'],type:$('input[type="radio"][name="templatetype"]:checked').attr('value'),filepath:Template.filePath,title:$('#newtitle').val(), description:$('#description').val()};
            		if($('#id').val().length > 0) {
            			url = getRootPath()+"/doc/template/updateTemplate.action?modifyDate="+$('#modifyDate').val();
            			obj = {controls:opts['echoData'],id:$('#id').val(),type:$('input[type="radio"][name="templatetype"]:checked').attr('value'),filepath:Template.filePath,title:$('#newtitle').val(), description:$('#description').val()};
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
            				Template.closeAddForm();
            				Template.subState = false;
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
            				Template.templateList();
            			},
            			error : function() {
            				Template.subState = false;
            				msgBox.tip({
            					content: $.i18n.prop("JC_SYS_002")
            				});
            			}
            		});
            	}
            },
		     showAddDialog:function() {
		    	 Template.clearForm();
		    	 //$("input[type=radio][name=txzq][index="+remindObj_.cycle+"]").attr("checked",'checked');
		    	 $("#Modal_Titile").html("新增");
		    	 $('#addTemplate').modal('show');
		    	 //getToken();
		    	 //Template.addExchangeFlowsTable();
		     },
		     disableRadio:function(flag){
		    	 $("input[type=radio][name=templatetype]").each(function(i, item){
		    		// $(this).css('disabled', 'disabled');
		    		 this.disabled = flag;
		    	 });
		     },
		     get:function(){
		    	 Template.clearForm();
		    	 var id = arguments[0];
		    	 //$('#id').attr('value',id);
		    	 $.ajax({
	    			 type : "get",
	    			 url : getRootPath()+"/doc/template/get.action",
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
	    				  $("#templateControlTree").empty();
	    				  $("#templateControlTree").deptAndPersonControl(opts);
	    				  $("#Modal_Titile").html("编辑");
	    				  $('#addTemplate').modal('show');
	    				  $('#newtitle').val(data['title']);
	    				  $('#oldtitle').val(data['title']);
	    				  $('#description').val(data['description']);
	    				  //document.getElementById('description').value = data['description'];
	    				  /*$('#description').attr('value',data['description']);*/
	    				  $('#id').val(data['id']);
	    				  $('#modifyDate').val(data['modifyDate']);
	    				  Template.filePath = data['filepath'];
	    				  $("input[type=radio][name=templatetype][value="+data.type+"]").prop("checked",'checked');
	    				  Template.disableRadio('disabled');
	    			 } ,
	    			 error:function() {
	    				 
	    			 }
	    		 });
		     },
		   
		     deleteTemplate:function(){
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
				    			 url : getRootPath()+"/doc/template/deleteByIds.action",
				    			 data : {ids: idsArr.join(',')},
				    			 //dataType : "json",
				    			 success : function(data) {
				    				 if(data.success == "true"){
				    					 Template.templateList();
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
				    			 url : getRootPath()+"/doc/template/updateUse.action",
				    			 data : {id: id,isUse:use},
				    			 //dataType : "json",
				    			 success : function(data) {
				    				 if(data.success == "true"){
				    					 Template.templateList();
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
		    					Template.exchangeFlowList();
		    				}
		    			});
		    			}
		    		});
		     	 
		     },
		     saveSelectedFlows:function() {
		    	// Template.addExchangeFlowsTable();
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
            				Template.subState = false;
            				alertx("保存成功");
            				Template.exchangeFlowList();
            			},
            			error : function(msg) {
            				Template.subState = false;
            				alertx(msg);
            			}
            		});
		    	 } else {
		    		 alertx('请选择流程');
		    	 }
		     },
		     queryReset : function(){
		                	$('#searchTemplateForm')[0].reset();
		                	//Template.templateList();
		                }

};
Template.pageRows=null;


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
Template.showInsideOutAddDiv = function (id){
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
Template.replyTable = null;
//组装后台参数
Template.replyTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(Template.replyTable, aoData);
	//组装查询条件
	$.each($("#insideOutReplyForm").serializeArray(), function(i, o) {
		alert(o.id);
		if(o.value != ""){
			aoData.push({ "insideOutId": o.id});
		}
	});
};
//显示回复信息
Template.replyColumns = [
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
Template.showInsideOutReplyDiv = function(insideOutId){
	$('#insideOut-reply').modal('show');
	//Template.replyTable.fnClearTable();
	$("#insideOutReplyForm #id").val(insideOutId);
	//显示回复列信息
	if (Template.replyTable == null) {
		Template.replyTable = $('#insideOutReplyTable').dataTable( {
			"iDisplayLength": Template.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/insideIn/queryReplayList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": Template.replyColumns,//table显示列
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
		Template.replyTable.fnDraw();
	}
};

//字典项初始化
Template.initDic = function(){
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
	
	
	/*$("#showAddDiv").click(Template.showAddDialog);
	$("#saveSelectedFlows").click(Template.saveSelectedFlows);
	$("#deleteFlows").click(Template.deleteFlows);
	$("#queryFlow").click(Template.exchangeFlowList);
	$("#queryReset").click(Template.queryReset);
	$("#flowClose").click(Template.closeAddForm);*/
	Template.pageRows = TabNub>0 ? TabNub : 10;
	$("#templateControlTree").deptAndPersonControl(opts);
	$("#showAddDiv_t").click(Template.showAddDialog);
	$("#showAddDiv").click(Template.showAddDialog);
	$("#queryFlow").click(Template.templateList);
	$("#queryReset").click(Template.queryReset);
	$("#editTemplateContext").click(Template.editTemplateContext);
	$("#saveTemplate").click(Template.saveTemplate);
	$("#templateClose").click(Template.closeAddForm);
	$("#deleteTemplates").click(Template.deleteTemplate);
	Template.templateList();
	$("#nameForm").validate({
		ignore: ".ignore",
        rules: {
           newtitle: 
		   {
			    required: true,
			    maxlength: 60,
			    fileName:true,
			    remote:{
			        url: getRootPath()+"/doc/template/checkTitle.action",
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
	$("#searchTemplateForm").validate({
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
	//Template.exchangeFlowList();
	//Template.initDic();
	
	//template = jQuery.validator.format($.trim($("#template").val()));
});