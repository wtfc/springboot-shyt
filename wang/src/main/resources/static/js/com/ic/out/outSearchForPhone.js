var outSearchForPhone = {};
outSearchForPhone.tree = null;
//显示此用户的联系人
outSearchForPhone.showOutSideMain = function(itemId){
	outSearchForPhone.showTree("outSideUserTree",itemId);
};

//构建当前用户外部联系人树
outSearchForPhone.showTree = function(treeId,itemId){
	        var phoneOld = $("#outSearchForNum").val();
			var	setting = {
				check : {
					enable : true,
					chkStyle: "radio",
					radioType: "all"
				},
				view : {
					selectedMulti : false
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				callback : {
					onCheck: function(event, treeId, node){
						$("#userPhoneName").val(node.name);
						$("#userPhoneNum").val(node.phone);
					}
				}
			};
			var url = getRootPath()+"/ic/contacts/getOutSideUserTree.action";
			$.getJSON(url, function(data) {
				if (data) {
					var id ="";
					outSearchForPhone.zNodes = [];
					$.each(data, function(i, o) {
						outSearchForPhone.zNodes[i] = {
							id : o.id,
							pId : o.groupId,
							name : o.userName + "",
							mail : o.mail,
							phone : o.phone,
							nocheck :  o.groupId == 0  ? true:false,
							checked:o.phone == phoneOld ? true:false,
						};
					});
					outSearchForPhone.tree = $.fn.zTree.init($("#"+treeId), setting, outSearchForPhone.zNodes);
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
//                  treeObj.expandAll(true);
					$.each(treeObj.getNodes(),function(i,o){
						if(o.check_Child_State==2){
							outSearchForPhone.tree .expandNode(o,true,false);
						}
					});
					
					$('#outSideUserMainDiv').modal('show');
				}
			});
};

outSearchForPhone.submitOutSideUserForPhone = function(){
	var treeObj = $.fn.zTree.getZTreeObj("outSideUserTree");
	var isChecked = false;
	$.each(treeObj.getNodes(),function(i,o){
		//radio check_Child_State==2表示有子节点被勾选
		if(o.check_Child_State==2){
			isChecked = true;
		}
	});
	if(!isChecked){
		msgBox.info({content:'请选择人员',type:'fail'});
		return;
	}
    $('#outSearchForPhoneName').val($("#userPhoneName").val());
    $('#outSearchForNum').val($("#userPhoneNum").val());
	$('#outSideUserMainDiv').modal('hide');
};

outSearchForPhone.closeOutSideUserForPhone = function(){
	var phoneOld = $("#outSearchForNum").val();
	var userNameOld = $("#outSearchForPhoneName").val();
	if(phoneOld==""||phoneOld==null){
		$("#userPhoneName").val("");
		$("#userPhoneNum").val("");
	}else{
		$("#userPhoneName").val(userNameOld);
		$("#userPhoneNum").val(phoneOld);
	}
	$('#outSideUserMainDiv').modal('hide');
};

//初始化事件
jQuery(function($){
	var outSideUserMainHtml = '<div class="modal fade" id="outSideUserMainDiv" aria-hidden="false">'+
		'<div class="modal-dialog modal-tree w531">'+
	    	'<div class="modal-content">'+
		        '<div class="modal-header clearfix">'+	//onclick="clear(\''+selectPersonId+'\',\''+selectDeptAndPersonId+'\');"
		            '<button type="button" class="close" data-dismiss="modal">×</button>'+
		            '<h4 class="modal-title fl">外部人员选择</h4>'+
		        '</div>'+
		        '<div class="modal-body clearfix">'+
	            	'<div class="w240 fl">'+
		                '<section class="tree-ul">'+
		                    '<div id="outSideUserTree" class="ztree"></div>'+ // 组织树
		                '</section>'+
		            '</div>'+
		        '</div>'+
		        '<div class="modal-footer form-btn">'+
		            '<button id="outSideAreaButton" class="btn dark" type="button">确 定</button>' +
		            '<button id="outSideCloseButton" class="btn" type="button">取 消</button>' +
		        '</div>'+
		    '</div>'+
	    '</div>'+
	'</div>';
	$("#outSearchUserTreeDiv").before(outSideUserMainHtml);
	
	$("[outSideUser='true']").each(function(index,item){
		var itemId = $(item).attr("name");
		var outSideUserSelectHtml = "<div class='select2-wrap input-group  w-p100'>";
		outSideUserSelectHtml += "<div class='select2-container select2-container-multi w-p100' >";
		outSideUserSelectHtml += "<input type='text' id='outSearchForPhoneName' class='email-temp-input' readonly/><input type='hidden' id='outSearchForNum'/>";
		outSideUserSelectHtml += "</div>";
		outSideUserSelectHtml += getPhoneUserButtons(itemId);
		outSideUserSelectHtml += "</div>";
		$(item).append(outSideUserSelectHtml);
		$("#"+itemId+"Div").click(function(){
			outSearchForPhone.showOutSideMain(itemId);
		});
	});
	$("#outSideAreaButton").click(outSearchForPhone.submitOutSideUserForPhone);
	$("#outSideCloseButton").click(outSearchForPhone.closeOutSideUserForPhone);
});