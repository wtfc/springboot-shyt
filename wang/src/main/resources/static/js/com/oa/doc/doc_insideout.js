//重复提交标识
subState_ = false;
Date.prototype.Format = function(fmt)   
{ 
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt)){
	  fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  }
  for(var k in o) {
	  if(new RegExp("("+ k +")").test(fmt)) {
		  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  }   
	  
  }
  return fmt;   
}  
var nowDate = new Date();
var nowDateStr = nowDate.Format("yyyy-MM-dd hh:mm:ss");
var html = [];
	html[html.length] = '<form id="insideOutAddForm" name="insideOutAddForm">';
	html[html.length] = '<input type="hidden" id="token" name="token" value="0">';
	html[html.length] = '<input type="hidden" id="title" name="title" />';
	html[html.length] = '<input type="hidden" id="docType" name="docType" />';
	html[html.length] = '<input type="hidden" id="docId" name="docId" />';
	html[html.length] = '<input type="hidden" id="formContent" name="formContent" />';
	html[html.length] = '<input type="hidden" id="content" name="content" />';
	html[html.length] = '<!-- 公文分发>>接收公文>>分发使用 start-->';
	html[html.length] = '<input type="hidden" id="insideInId" name="insideInId" value=""/>';
	html[html.length] = '<input type="hidden" id="modifyDate" name="modifyDate" value=""/>';
	html[html.length] = '<!-- 公文分发>>接收公文>>分发使用 end -->';
	html[html.length] = '<div class="modal fade panel" id="insideOut-add" aria-hidden="false">';
	html[html.length] = '	<div class="modal-dialog w900">';
	html[html.length] = '		<div class="modal-content">';
	html[html.length] = '			<div class="modal-header">';
	html[html.length] = '				<button type="button" class="close" data-dismiss="modal">×</button>';
	html[html.length] = '				<h4 class="modal-title">分发</h4>';
	html[html.length] = '			</div>';
	html[html.length] = '			<div class="modal-body">';
	html[html.length] = '				<div class="table-wrap form-table">';
	html[html.length] = '					<table class="table">';
	html[html.length] = '						<tbody>';
	html[html.length] = '							<tr>';
	html[html.length] = '								<td class="w140"  style="text-align: right !important; font-weight: bold !important; background-color: #f9f8f5;"><span class="required">*</span>接收人</td>';
	html[html.length] = '								<td style="text-align: left;">';
	html[html.length] = '									<input type="hidden" id="receiveUserNames" name="receiveUserNames" />';
	html[html.length] = '									<div id="receiveUserIdsTree"></div>';
	html[html.length] = '								</td>';
	html[html.length] = '							</tr>';
	html[html.length] = '							<tr>';
	html[html.length] = '								<td style="width:20%;text-align: right !important; font-weight: bold !important; background-color: #f9f8f5;">提醒</td>';
	html[html.length] = '	                            <td style="text-align: left;">';
	html[html.length] = '	                                <label class="radio inline"><input type="radio" name="remindMode" value="0" checked/>不提醒</label>';
	html[html.length] = '	                                <label class="radio inline"><input type="radio" name="remindMode" value="2"/>邮件</label>';
	html[html.length] = '	                                <label class="radio inline"><input type="radio" name="remindMode" value="1"/>短信</label>';
	html[html.length] = '	                            </td>';
	html[html.length] = '							</tr>';
	html[html.length] = '							<tr>';
	html[html.length] = '								<td style="width:20%;text-align: right !important; font-weight: bold !important; background-color: #f9f8f5;">有效日期</td>';
	html[html.length] = '	                            <td style="text-align: left;">';
	html[html.length] = '									<label class="checkbox"><input type="checkbox" onclick="showValidSpan(this)" id="forOverCheck" name="forOverCheck" checked/>永久</label>';
	html[html.length] = '									<span id="validDateSpan" style="display:none">有效期至<input type="text" id="validDate" data-start-date="'+nowDate+'" name="validDate"  data-pick-time="true" data-date-format="yyyy-MM-dd HH:mm:ss" class="datepicker-input"/></span>';
/*	html[html.length] = '	                            <table>';
	html[html.length] = '	                            <tr>';
	html[html.length] = '	                            <td>';
	html[html.length] = '	                            </td>';
	html[html.length] = '	                            <td>';
	html[html.length] = '	                            </td>';
	html[html.length] = '	                            </tr>';
	html[html.length] = '	                            </table>';
*/	html[html.length] = '	                            </td>';
	html[html.length] = '							</tr>';
	html[html.length] = '						</tbody>';
	html[html.length] = '					</table>';
	html[html.length] = '				</div>';
	html[html.length] = '			</div>';
	html[html.length] = '			<div class="modal-footer form-btn">';
	html[html.length] = '				<button class="btn dark" type="button" id="saveInsideOut">确 定</button>';
	html[html.length] = '				<button class="btn" type="reset" onclick="closeInsideOut_()">取 消</button>';
	html[html.length] = '			</div>';
	html[html.length] = '		</div>';
	html[html.length] = '	</div>';
	html[html.length] = '</div>';
	html[html.length] = '</form>';
	
function showValidSpan(obj) {
	if(obj.checked) {
		$("#validDateSpan").css("display", "none");
	} else {
		$("#validDateSpan").css("display", "inline-block");
	}
}
function closeInsideOut_() {
	$('#insideOut-add').modal('hide');
}

/*显示分发公文弹出层
 *param objListFun 分发成功后调用刷新列表函数
 *param id 查询公文要分发内容的对象id
 *param docType 0：发文管理>>办结发文 1：收文管理>>办结收文，表单里分发公文 2：公文分发>>分发公文 3：公文分发：接收公文
 *param modifyDate docType==3 时使用 修改原分发公文为已读
 *param insideOutId docType==3 时使用 修改原分发公文为已读（当前分发公文的id）
*/
jQuery.fn.showInsideOutAddDiv_ = function (objListFun,id,docType,modifyDate,insideOutId){
	$("#dataLoad").show();
	if($("#insideOutAddForm").size()==0){
		//初始化表单
		$(this.context.body).append(html.join(' '));
		ie8StylePatch();
		//初始化人员选择树
	    selectControl.init("receiveUserIdsTree","receiveUserIds-receiveUserIds", true, true);
	    //保存方法
	    $("#saveInsideOut").click(function(){validRemind_(objListFun);});
	    //初始化校验方法
		$("#insideOutAddForm").validate({
			ignore: ".ignore",
	        rules: {
	        	receiveUserIds: 
	 		   {
	 			    required: true
	 		   },
	 		  validDate:{
	 			 required:function(element) {
	 				   var isForOver = document.getElementById('forOverCheck').checked;
				    	if(isForOver) {
				    		return false;
				    	} else {
				    		return true;
				    	}
				      }
	 		  }
		    }
		});
	}else{
		$("#saveInsideOut").unbind("click"); 
	    //保存方法
	    $("#saveInsideOut").click(function(){validRemind_(objListFun);});
		//初始化人员选择树
	    selectControl.init("receiveUserIdsTree","receiveUserIds-receiveUserIds", true, true);		
	}
	$(".datepicker-input").datepicker();
	$("#validDateSpan").css("display", "none");
	clearinsideOutAddForm_();
	getToken("insideOutAddForm");
	var params;
	if(docType==3){//接收公文设置已读使用
		$("#insideOutAddForm #insideInId").val(id);
		$("#insideOutAddForm #modifyDate").val(modifyDate);	
		params = {
			time: new Date(),
			id: insideOutId
		};
	}else{
		params = {
			time: new Date(),
			id: id
		};		
	}
	var url;
	if(docType==0){
		url = getRootPath()+"/doc/send/get.action";
	}else if(docType==1){
		url = getRootPath()+"/doc/receive/get.action";
	}else{
		url= getRootPath()+"/doc/insideOut/get.action";
	}

	$.ajax({
		url: url,
      type: 'post',
      data: params,
      success: function(data) {
      	if(data){
      		$("#insideOutAddForm #title").val(data.title);
      		if(docType==2||docType==3){
      			$("#insideOutAddForm #docType").val(data.docType);
      			$("#insideOutAddForm #docId").val(data.docId);
      		}else{//收发文类型 发文管理和收文管理需传公文类型参数
      			$("#insideOutAddForm #docType").val(docType);
      			$("#insideOutAddForm #docId").val(data.id);
      		}
      		$("#insideOutAddForm #formContent").val(data.formContent);
      		$("#insideOutAddForm #content").val(data.content);
      		$('#insideOut-add').modal('show');
      	}else{
    		msgBox.tip({
    			content: $.i18n.prop("JC_SYS_060")
    		});      		
      	}
      	$("#dataLoad").hide();
      },
      error : function() {
		$("#dataLoad").hide();
		msgBox.tip({
			content: $.i18n.prop("JC_SYS_060")
		});
      }
  });
};

validRemind_ = function(objListFun){
	if ($("#insideOutAddForm").valid()) {
		var userIds = $('#receiveUserIds-receiveUserIds').val();
		var reminder = $('#insideOutAddForm input[name="remindMode"]:checked').val();
		if(reminder=='1'){//是短信
			jQuery.ajax({
				url : getRootPath()+"/doc/insideOut/validRemind.action?time=" + new Date(),
				type : 'get',
				async: false,
				dataType : "json",
				data : {'receiveUserIds':userIds},
				success : function(data) {
					if(data.success=="success"){
						saveInsideOut_(objListFun);
					}else{
						if(data.success){
							msgBox.confirm({
								content: data.successMessage,
								success: function(){
									saveInsideOut_(objListFun);
								},
								cancel:function(){
								}
							});
							
						}else{
							msgBox.info({
								content: data.errorMessage
							});
						} 
					}
				},
				error : function() {
					msgBox.tip({
						type:"fail",
						content:$.i18n.prop("JC_OA_PO_015"),
						callback:function(){
						}
					});
				}
			});
		}else{//是邮件
			saveInsideOut_(objListFun);
		}
	}else{
		$("#dataLoad").hide();
		subState_ = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//保存公文分发信息
saveInsideOut_ = function(objListFun){
	$("#dataLoad").show();
	if(subState_)return;
	subState_ = true;
	if ($("#insideOutAddForm").valid()) {
		//分发人员名称集合获取
		var receiveUserNamesTemp = returnValue("receiveUserIds-receiveUserIds");
		if(receiveUserNamesTemp != null){
			var receiveUserNames = "";
			var temp = receiveUserNamesTemp.split(",");
			for(var i=0; i<temp.length;i++){
				receiveUserNames += (temp[i].split(":")[1]+",");
			}
			$("#receiveUserNames").val(receiveUserNames.substring(0, receiveUserNames.length-1));
		};
		var url = getRootPath()+"/doc/insideOut/saveOutforIn.action?time=" + new Date();
		var formData = $("#insideOutAddForm").serializeArray();
		var isForOver = document.getElementById('forOverCheck').checked ? "1" : "0";
		formData.push({"name":"forOver", "value":isForOver});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				subState_ = false;
				getToken("insideOutAddForm");
				if(data.success == "true"){
					clearinsideOutAddForm_();
					$('#insideOut-add').modal('hide');
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if(objListFun!=null){//刷新列表
						objListFun();
					}
				} else {
					if(data.labelErrorMessage){
						showErrors("insideOutAddForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
				$("#dataLoad").hide();
			},
			error : function() {
				subState_ = false;
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
				});
				$("#dataLoad").hide();
			}
		});
	}else{
		$("#dataLoad").hide();
		subState_ = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//清空表单
clearinsideOutAddForm_ = function(){
	$('#insideOutAddForm')[0].reset();
	$('#insideOutAddForm').find("input[type=hidden]").val("");
	selectControl.clearValue("receiveUserIds-receiveUserIds");
	hideErrorMessage();
};