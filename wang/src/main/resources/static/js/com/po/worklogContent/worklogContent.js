$(document).ready(function(){
	
	//初始化列表方法
	$('#manageListTable').flexigrid({
		url : getRootPath()+'/po/worklogContent/manageList.action',
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
		    display : '日志ID',
		    name : 'worklogId',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '内容类型(0-今日日志;1-明日提醒)',
		    name : 'contentType',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '排序序号',
		    name : 'sortNum',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '主要完成事项',
		    name : 'content',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '删除标记[0-未删除;1-删除]',
		    name : 'deleteFlag',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '创建人ID',
		    name : 'createUser',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '创建人所在部门ID',
		    name : 'createUserDept',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '创建人所在单位ID',
		    name : 'createUserOrg',
		    width : '10%',
		    sortable : false,
		    align : 'center'
	    }, {
		    display : '创建时间',
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
                name : '修改',
                bclass : 'edit',
                onpress : updateWorklogContent
            },
            {
                name : '删除',
                bclass : 'delete',
                onpress : deleteWorklogContent
            }],
	    title : '',//标题
	    useRp : false,//是否可以动态选择每页显示多少条
	    checkbox:true
    });    
	//保存方法
	$("#WorklogContentSubmit").on("click",function(){
	    	  if($("#worklogContentForm").valid()){
	    		  var formData = $("#worklogContentForm").serializeArray();
	    		  var addUrl = getRootPath()+"/po/worklogContent/save.action?time="+new Date();
	    		  var updateUrl = getRootPath()+"/po/worklogContent/update.action?time="+new Date();
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
		              success: function(data, textStatus, xhr) {	
		            	  if(data.errorMessage!=null){
		            		  alert(data.errorMessage);
		            	  }else{
		            	  	
		            	  	
		            	  		$("a[href='#panel-503905']").click();
		            	  	
		            	  	
		            			$('#manageListTable').flexReload();
		            		
		            	  }
		              },error:function(){
		            	  alert("保存数据错误。");
		              }
		            });
	    	  }
	      });
	
	//清空方法
	$("#WorklogContentClear").on("click",function(){
	    	$('#worklogContentFormDiv').find("input").val("");
	    	$('#worklogContentFormDiv').find("textarea").val("");
	});
});		
			//修改显示方法
		    function updateWorklogContent(){
	    	  if($('.trSelected').length == 0){
	    		  alert('请选择您要修改的记录。');  
	    	  }else if($('.trSelected').length > 1){
	    		  alert('请选择一个修改,不能同时修改多个。');
	    	  }else{
	    		  var id = $('.trSelected').find("td:first").eq(0).text();
	    		  jQuery.ajax({
		              url: getRootPath()+"/po/worklogContent/get.action?id="+id+"&time="+new Date(),
		              type: 'get',
		              dataType: 'json',
		              success: function(data, textStatus, xhr) {
		            	  $("#worklogContentForm").fill(data);
		            	  $("a[href='#panel-53254']").click();
		              },error:function(){
		            	  alert("加载数据错误。");
		              }
		            });
	    	  }
	      }
		  
		  //删除方法
	      function deleteWorklogContent(grid){
	    	  var chk_value ="";
	    	  $('.trSelected').find("td:first").each(function(){
	    		  chk_value += $(this).text() + ",";
	    	  });
	    	  if(chk_value.length > 0){
	    		  if(window.confirm("您确定要删除选中的数据吗?")){
		    		  chk_value = chk_value.substring(0,chk_value.length-1);
			    	  jQuery.ajax({
			              url: getRootPath()+"/po/worklogContent/deleteByIds.action",
			              type: 'post',
			              data:{ids:chk_value,time:new Date()},
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