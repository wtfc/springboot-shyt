var setting = {};	
	 //保存方法
setting.SettingSubmit = function(){
    	  if($("#settingForm").valid()){
    		  var formData = $("#settingForm").serializeArray();
    		  var addUrl = getRootPath()+"/sys/setting/save.action?time="+new Date();
    		  var updateUrl = getRootPath()+"/sys/setting/update.action?time="+new Date();
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
	            	  $("#token").val(data.token);
	            	  if(data.errorMessage!=null){
	            		  //alert(data.errorMessage);
	            		  msgBox.tip({content: data.errorMessage, type:'fail'});
	            	  }else{
	            		 // alertx("保存成功");
	            		  msgBox.tip({content: $.i18n.prop("JC_SYS_001"), type:'success'});
	            		  setting.updateSetting();
	            	  }
	              },error:function(){
	            	  //alert("保存数据错误。");
	            	  msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
	              }
	            });
    	  }
      };
	  
      //修改对话框方法
 setting.updateSetting = function(){
	    jQuery.ajax({
	       url: getRootPath()+"/sys/setting/getOne.action?time="+new Date(),
	       type: 'post',
	       dataType: 'json',
	       success: function(data, textStatus, xhr) {
	            $("#settingForm").fill(data.settingvos);
	            $("#token").val(data.token);
	       },error:function(){
	          // alert("加载数据错误。");
	           msgBox.tip({content: "加载数据错误", type:'fail'});
	       }
	     });
	   };

	 //初始化
	   jQuery(function($) 
	   {
		   $("#settingbtn").click(setting.SettingSubmit);
		   setting.updateSetting();
	   });
	


