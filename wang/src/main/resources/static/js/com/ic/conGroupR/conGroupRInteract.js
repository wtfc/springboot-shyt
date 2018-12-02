$(document).ready(function(){
	
	//初始化列表方法
	$('#manageListTable').flexigrid({
		url : getRootPath()+'/conGroupR/manageList.action',
	    dataType : 'json',
	    colModel : [{
			display : 'ID',
			name : 'id',
			align : 'center',
			hide : true
		},{
		    display : '主键ID',
		    name : 'id',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '联系人主键ID',
		    name : 'contactsId',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '联系人分组ID',
		    name : 'contactsGroupId',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }],
	    usepager : true,//是否分页
	    buttons : [ {
            name : '添加',
            bclass : 'add',
            onpress : createConGroupR
            },{
                name : '修改',
                bclass : 'edit',
                onpress : updateConGroupR
            },
            {
                name : '删除',
                bclass : 'delete',
                onpress : deleteConGroupR
            }],
	    title : '联系人与分组中间表',//标题
	    useRp : false,//是否可以动态选择每页显示多少条
	    checkbox:true
    });

    
	//保存方法
	$("#ConGroupRSubmit").on("click",function(){
	    	  if($("#conGroupRForm").valid()){
	    		  var formData = $("#conGroupRForm").serializeArray();
	    		  var addUrl = getRootPath()+"/conGroupR/save.action?time="+new Date();
	    		  var updateUrl = getRootPath()+"/conGroupR/update.action?time="+new Date();
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
		            	  	
		            	  		$('#conGroupRFormDiv').modal('toggle');
		            	  	
		            	  	
		            	  	
		            			$('#manageListTable').flexReload();
		            		
		            	  }
		              },error:function(){
		            	  alert("保存数据错误。");
		              }
		            });
	    	  }
	      });
	
	//清空方法
	$("#ConGroupRClear").on("click",function(){
	    	$('#conGroupRFormDiv').find("input").val("");
	    	$('#conGroupRFormDiv').find("textarea").val("");
	});

	



});
  	

	
	
		//新增弹出对话框方法
		function createConGroupR(){	    	  
	    	  $('#conGroupRFormDiv').modal('toggle');
	    	  $('#conGroupRFormDiv').find("input").val("");
	    	  $('#conGroupRFormDiv').find("textarea").val("");
	      }
	
	  
		  //修改弹出对话框方法
	      function updateConGroupR(){
	    	  if($('.trSelected').length == 0){
	    		  alert('请选择您要修改的记录。');  
	    	  }else if($('.trSelected').length > 1){
	    		  alert('请选择一个修改,不能同时修改多个。');
	    	  }else{
	    		  var id = $('.trSelected').find("td:first").eq(0).text();
		    	  jQuery.ajax({
		              url: getRootPath()+"/conGroupR/get.action?id="+id+"&time="+new Date(),
		              type: 'get',
		              dataType: 'json',
		              async:false,
		              success: function(data, textStatus, xhr) {
		    	    	  $('#conGroupRFormDiv').modal('toggle');
		            	  $("#conGroupRForm").fill(data);
		              },error:function(){
		            	  alert("加载数据错误。");
		              }
		            });
	    	  }
	      }
		
		  
		  //删除方法
	      function deleteConGroupR(grid){
	    	  var chk_value ="";
	    	  $('.trSelected').find("td:first").each(function(){
	    		  chk_value += $(this).text() + ",";
	    	  });
	    	  if(chk_value.length > 0){
	    		  if(window.confirm("您确定要删除选中的数据吗?")){
		    		  chk_value = chk_value.substring(0,chk_value.length-1);
			    	  jQuery.ajax({
			              url: getRootPath()+"/conGroupR/deleteByIds.action",
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
    
	


