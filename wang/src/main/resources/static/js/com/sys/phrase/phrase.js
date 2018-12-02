var phrase = {}, pageCount=10;
  	
//重复提交标识
phrase.subState = false;
//分页处理 start
//分页对象
phrase.oTable = null;

//显示列信息
//常用词
phrase.oTableAoColumns = [
	//不需要排序的列直接用mData function方式
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
	{ "mData": "phraseType", "mRender" : function(mData) {
		return mData == "0" ? "公共" : "个人";
	}},
	{ "mData": "userName" },
	{ "mData": "content" },
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

phrase.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(phrase.oTable, aoData);
};

//分页查询
phrase.phraseList = function () {
	if (phrase.oTable == null) {
		phrase.oTable = $('#phraseTable').dataTable( {
			"iDisplayLength": phrase.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/sys/phrase/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": phrase.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				phrase.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4]}
	        ]
		} );
	} else {
		phrase.oTable.fnDraw();
	}
};
	
//处理缓存问题 清空form表单中值
phrase.clearForm = function(form){	    	  
	   $(':input', form).each(function() { 
	     var type = this.type;
	     var tag = this.tagName.toLowerCase(); // normalize case 
	     if (type == 'text' || type == 'password' || tag == 'textarea') 
	       this.value = ""; 
	   }); 
};

//加载添加DIV
phrase.loadAddHtml = function (){
	if($.trim($("#phraseEdit").html()) == ""){
		$("#phraseEdit").load(getRootPath()+"/sys/phrase/phraseEdit.action",null,phrase.createPhrase);
	}
	else{
		phrase.createPhrase();
	}
};

//新增弹出对话框方法
phrase.createPhrase = function(){	
	hideErrorMessage();
	
	$("#id").attr("value","");
	//$("#token").attr("value","0");
	phrase.clearForm(phraseForm);
	if($("#tempisAdmin").val() == 1)
   		$("#loadtype").html("<input type=\"hidden\" id=\"phraseType\" name=\"phraseType\" value=\"0\">公共");
   	else if($("#tempisAdmin").val() == 0)
   		$("#loadtype").html("<input type=\"hidden\" id=\"phraseType\" name=\"phraseType\" value=\"1\">个人");
	$("#titlephrase").html("新增");
	$("#count").html(30);
	$('#myModal-edit').modal('show');
	ie8StylePatch();
};

//加载修改div
phrase.loadUpdateHtml = function (id){
	if($.trim($("#phraseEdit").html()) == ""){
		$("#phraseEdit").load(getRootPath()+"/sys/phrase/phraseEdit.action",null,function(){phrase.get(id);});
	}
	else{
		phrase.get(id);
	}
};
		 		
//修改弹出对话框方法
phrase.get = function(id){
	  hideErrorMessage();
	  //getToken();
  	  jQuery.ajax({
            url: getRootPath()+"/sys/phrase/get.action?id="+id+"&time="+new Date(),
            type: 'post',
            dataType: 'json',
            success: function(data, textStatus, xhr) {
              phrase.clearForm(phraseForm);
          	  $("#phraseForm").fill(data);
          	  $("#titlephrase").html("编辑");
          	  $("#count").html(30-$("#phraseForm #content").val().length);
          	  if(data.phraseType == 0)
          		$("#loadtype").html("<input type=\"hidden\" id=\"phraseType\" name=\"phraseType\" value=\"0\">公共");
          	  else if(data.phraseType == 1)
          		$("#loadtype").html("<input type=\"hidden\" id=\"phraseType\" name=\"phraseType\" value=\"1\">个人");
          	  $('#myModal-edit').modal('show');
          	  ie8StylePatch();
            },error:function(){
          	  //alert("加载数据错误。");
          	  msgBox.tip({content: "加载数据错误", type:'fail'});
            }
       });
};

//删除常用词
phrase.deletephrase = function (id) {
	var ids = String(id);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		//alertx("请选择要删除的常用词");
		msgBox.info({content: "请选择要删除的常用词", type:'fail'});
		return;
	}
	confirmx($.i18n.prop("JC_SYS_034"),function(){phrase.deleteCallBack(ids);});
};

//删除常用词回调方法
phrase.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/phrase/deleteByIds.action?time=" + new Date(),
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				//alertx("删除成功");
				msgBox.tip({content: "删除成功", type:'success'});
				phrase.phraseList();
			}
		}
	});
};
  
//初始化
jQuery(function($) 
{
	//计算分页显示条数
	phrase.pageCount = TabNub>0 ? TabNub : 10;
	$("#phraseTop").click(phrase.loadAddHtml);
	$("#phraseBottom").click(phrase.loadAddHtml);
	$("#deletephrases").click("click", function(){phrase.deletephrase(0);});
	//初始化列表方法
	phrase.phraseList();
	    		

});	


