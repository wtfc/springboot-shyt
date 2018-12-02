$(document).ready(function(){
	
	//初始化列表方法
	$('#manageListTable').flexigrid({
		url : getRootPath()+'/mailRecord/manageList.action',
	    dataType : 'json',
	    colModel : [{
			display : 'ID',
			name : 'id',
			align : 'center',
			hide : true
		},{
		    display : '邮件记录表主键',
		    name : 'id',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '邮件记录表主键',
		    name : 'mailId',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '邮件的接收人，包括正常收件人、抄送接收人、密送接收人',
		    name : 'receiveUserId',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '收件人(内部用户ID，外部邮箱地址)',
		    name : 'receiveMail',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '接收人类别:0 正常收件人 1 抄送接收人 2 密送接收人',
		    name : 'receiveType',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '删除标记 1 删除 0 未删除',
		    name : 'deleteFlag',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '删除人ID',
		    name : 'deleteUser',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '删除时间',
		    name : 'deleteDate',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '创建人员ID',
		    name : 'createUser',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '记录所属部门',
		    name : 'createUserDept',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '记录所属机构',
		    name : 'createUserOrg',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '记录插入数据库的时间',
		    name : 'createDate',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '修改人ID',
		    name : 'modifyUser',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '修改时间',
		    name : 'modifyDate',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留时间字段1',
		    name : 'extDate1',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留时间字段2',
		    name : 'extDate2',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留数字字段1',
		    name : 'extNum1',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留数字字段2',
		    name : 'extNum2',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留数字字段3',
		    name : 'extNum3',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留字符字段1',
		    name : 'extStr1',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留字符字段2',
		    name : 'extStr2',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留字符字段3',
		    name : 'extStr3',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留字符字段4',
		    name : 'extStr4',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '预留字符字段5',
		    name : 'extStr5',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }],
	    usepager : true,//是否分页
	    buttons : [ {
            name : '添加',
            bclass : 'add',
            onpress : createMailRecord
            },{
                name : '修改',
                bclass : 'edit',
                onpress : updateMailRecord
            },
            {
                name : '删除',
                bclass : 'delete',
                onpress : deleteMailRecord
            }],
	    title : '邮件记录表，记录每条收、发的邮件基本信息',//标题
	    useRp : false,//是否可以动态选择每页显示多少条
	    checkbox:true
    });

    
	//保存方法
	$("#MailRecordSubmit").on("click",function(){
	    	  if($("#mailRecordForm").valid()){
	    		  var formData = $("#mailRecordForm").serializeArray();
	    		  var addUrl = getRootPath()+"/mailRecord/save.action?time="+new Date();
	    		  var updateUrl = getRootPath()+"/mailRecord/update.action?time="+new Date();
	    		  var url;
	    		  
	    		  if($("#id").val().length > 0){
	    			  url = updateUrl;
	    		  }else{
	    			  url = addUrl;
	    		  }
	    		  jQuery.ajax({
		              url: url,
		              type: 'post',
		              data: formData,
		              async:false,
		              success: function(data, textStatus, xhr) {	
		            	  if(data.errorMessage!=null){
		            		  alert(data.errorMessage);
		            	  }else{
		            	  	
		            	  		$('#mailRecordFormDiv').modal('toggle');
		            	  	
		            	  	
		            	  	
		            			$('#manageListTable').flexReload();
		            		
		            	  }
		              },error:function(){
		            	  alert("保存数据错误。");
		              }
		            });
	    	  }
	      });
	
	//清空方法
	$("#MailRecordClear").on("click",function(){
	    	$('#mailRecordFormDiv').find("input").val("");
	    	$('#mailRecordFormDiv').find("textarea").val("");
	});

	



});
  	

	
	
		//新增弹出对话框方法
		function createMailRecord(){	    	  
	    	  $('#mailRecordFormDiv').modal('toggle');
	    	  $('#mailRecordFormDiv').find("input").val("");
	    	  $('#mailRecordFormDiv').find("textarea").val("");
	      }
	
	  
		  //修改弹出对话框方法
	      function updateMailRecord(){
	    	  if($('.trSelected').length == 0){
	    		  alert('请选择您要修改的记录。');  
	    	  }else if($('.trSelected').length > 1){
	    		  alert('请选择一个修改,不能同时修改多个。');
	    	  }else{
	    		  var id = $('.trSelected').find("td:first").eq(0).text();
		    	  jQuery.ajax({
		              url: getRootPath()+"/mailRecord/get.action?id="+id+"&time="+new Date(),
		              type: 'get',
		              dataType: 'json',
		              async:false,
		              success: function(data, textStatus, xhr) {
		    	    	  $('#mailRecordFormDiv').modal('toggle');
		            	  $("#mailRecordForm").fill(data);
		              },error:function(){
		            	  alert("加载数据错误。");
		              }
		            });
	    	  }
	      }
		
		  
		  //删除方法
	      function deleteMailRecord(grid){
	    	  var chk_value ="";
	    	  $('.trSelected').find("td:first").each(function(){
	    		  chk_value += $(this).text() + ",";
	    	  });
	    	  if(chk_value.length > 0){
	    		  if(window.confirm("您确定要删除选中的数据吗?")){
		    		  chk_value = chk_value.substring(0,chk_value.length-1);
			    	  jQuery.ajax({
			              url: getRootPath()+"/mailRecord/deleteByIds.action",
			              type: 'post',
			              data:{ids:chk_value,time:new Date()},
			              async:false,
			              success: function(data, textStatus, xhr) {
			            	  alert("删除成功。");
			            	  $('#manageListTable').flexReload();
			              },error:function(){
			            	  alert("删除数据错误。");
			              }
			            });
	    		  }
	    	  }else{
	    		  alert("请选择要删除的数据。");
	    	  }
	      }	
    
	


