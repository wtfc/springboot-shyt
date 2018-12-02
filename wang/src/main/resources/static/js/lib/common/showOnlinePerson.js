showOnlinePerson = {};			//显示在线人员对象
showOnlinePerson.index = 0;		//弹出窗口基数
var isShowOnlinePerson = true;	//是否显示在线人员窗口

/**
 * 弹出在线人员
 */
showOnlinePerson.init = function() {
	this.index = parseInt(showOnlinePerson.index) + 1;
	var openPersonDivId   = "openPersonDiv"+ this.index,
	personHtml = ['<div class="modal fade" id="'+openPersonDivId+'" aria-hidden="false">',
	'<div class="modal-dialog w900 modal-tree">',
		'<div class="modal-content">',
			'<div class="modal-header clearfix">',
				'<button type="button" class="close" data-dismiss="modal" onclick="isShowOnlinePerson=true;">×</button>',
				'<h4 class="modal-title fl">在线人员</h4>',
				'<div class="fl btn-group form-btn m-l-lg" data-toggle="buttons">',
				'</div>',
				'<form role="search" class="fr input-append m-b-none m-r-lg">',
					'<span class="add-on"></span> ',
				'</form>',
				'</div>',
				'<div class="modal-body clearfix"></div>',
				'<div class="modal-footer form-btn">',
					//'<button id="close" class="btn" type="button" onClick="showOnlinePerson.closeShowOnlinePerson(\''+openPersonDivId+'\',true);">关 闭</button>',
				'</div>',
			'</div>',
		'</div>',
	'</div>'].join("");
	$(personHtml).empty();
	$(personHtml).appendTo("body");
	$("#"+openPersonDivId).find(".modal-body").empty();
	$.ajax({
		async : false,
		url : getRootPath()+"/department/getDeptAndUserByOnLine.action",
		type : 'post',
		success : function(data) {
			$("#"+openPersonDivId).find(".modal-body").html(showOnlinePerson.showPage(eval(data)[0], openPersonDivId));
		},
		error: function(){
			msgBox.tip({content: "获取在线人员失败", type:'fail'});
		}
	});
	if(isShowOnlinePerson){
		//showOnlinePerson.showOnLine(openPersonDivId);
		$(".tree-list .tree-btn").on('click',function (e) {//人员界面收缩事件
	        e.preventDefault();
	        e.stopPropagation();
	        $(this).find("i").toggleClass("fa-chevron-down").end().closest(".tree-list").next().slideToggle();
	    });
		isShowOnlinePerson = false;
		$("#"+openPersonDivId).modal("show");
	}
}

/**
 * 显示在线人员
 */
showOnlinePerson.showOnLine = function(openPersonDivId){
	$.ajax({
		async: false,
		url : getRootPath()+"/system/getOnlineUsers.action",
		type : 'post',
		success : function(data) {
			$.each(data, function(i, o) {
				$("#"+openPersonDivId+" span:not([class])").each(function(ii, v){
					var t = $(v).text();
					if(t == o.displayName){
						var val = "<font color=\"#60AAE9\">"+t+"</font>";
						$(v).html(val);
					}
				});
			});
		},
		error: function(){
			msgBox.tip({content: "获取在线人员失败", type:'fail'});
		}
	});
}

/**
 * 关闭人员选择树
 */
showOnlinePerson.closeShowOnlinePerson = function(openPersonDivId, isOP){
	$("#"+openPersonDivId).modal("hide");
	if(isOP != null || isOP != undefined)
		isOpenSinglePerson = true;
}

/**
 * 显示人员
 * @param d 				部门人员数据
 * @param openPersonDivId	弹出层DIVID
 */
/*showOnlinePerson.showPage = function(d, openPersonDivId){
	var lv1_dept = '<div>', lv1_user = '<div>';
	if(d != null){
		if(d.user != null && d.user.length > 0){
			lv1_dept += '<label for="" class="fl"><span class="fl w100">'+d.name+'</span></label> ';
			var d_user_len = d.user.length;
			for(var l1=0;l1<d_user_len;l1++){
				var d_user_l1 = d.user[l1];
				lv1_user += 
					'<label for="" class="inline"> <span>'+ 
						'<a href="#" onclick="showOnlinePerson.showPersonInfo('+d_user_l1.id+');">'+d_user_l1.displayName +'</a>'+
					'</span></label>';
			}
		}else{
			lv1_dept += '<label for="" class="fl"><span class="fl w100">'+d.name+'</span></label> ';
			lv1_user += '<label for="" class="fl"></label>';
		}
		lv1_user += '</div>';
		lv1_dept += '<a href="#" class="fr m-r tree-btn"><i class="fa fa-chevron-up"></i></a></div>';
	}else{
		lv1_user = '<div style="text-align:center;">无权限查看</div>';//+$.i18n.prop("JC_SYS_007");
		lv1_dept += '</div>';
	}
	var showlist = $(
		'<section class="w820 fl tree-ul tree-scroll">'+
			'<ul class="tree-horizontal clearfix">'+
				'<li>'+
				'<div class="level1 clearfix tree-list">'+
					lv1_dept +
					lv1_user +
				'</div>'+
				'<ul id="lv"></ul>'+
				'</li>'+
			'</ul>'+
		'</section>'
	);
	if(d != null && d.subDept != null && d.subDept.length > 0)
		showOnlinePerson.recur(d.subDept, showlist.find("#lv"), 2, openPersonDivId);
	return showlist;
}*/

/**
 * 递归查询下级菜单
 * @param deptList			部门集合
 * @param parentHtml		上级html
 * @param level				level-css样式
 * @param openPersonDivId	弹出层DIVID
 */
/*showOnlinePerson.recur = function(deptList, parentHtml, level, openPersonDivId){
	var deptList_len = deptList.length;
	for(var i=0; i<deptList_len; i++){
		var deptList_dept = deptList[i];
		if(deptList_dept.subDept) {
			if(deptList_dept.user.length > 0){
				var main_dept = '<div>', main_user = '<div>';
				main_dept += '<label for="" class="radio fl"><span class="fl w100">'+deptList_dept.name+'</span></label> ';
				if(deptList_dept.user != undefined){
					if(deptList_dept.user.length > 0){
						var user_len = deptList_dept.user.length;
						for(var m=0; m<user_len; m++){
							var user_m = deptList_dept.user[m];
							main_user += '<label for="" class="radio inline"><a href="#" onclick="showOnlinePerson.showPersonInfo('+user_m.id+');"><span>'+user_m.displayName+'</span></a></label>';
						}
					}
				}else{
					var subDept_user_len = deptList_dept.subDept[0].user.length;
					for(var m=0; m<subDept_user_len; m++){
						var subDept_user_m = deptList_dept.subDept[0].user[m];
						main_user += '<label for="" class="radio inline"><a href="#" onclick="showOnlinePerson.showPersonInfo('+subDept_user_m.id+');"><span>'+subDept_user_m.displayName +'</span></a></label>';
					}
				}
				main_dept += '<a href="#" class="fr m-r tree-btn"><i class="fa fa-chevron-up"></i></a></div>';
				main_user += '</div>';
				var main_sub = $(
					'<li>'+
						'<div class="level'+level+' clearfix tree-list">'+
							main_dept +
							main_user +
						'</div>'+
						'<ul id="lv'+i+'"></ul>'+
					'</li>'
				);
				$(main_sub).appendTo(parentHtml);
				showOnlinePerson.recur(deptList[i].subDept, $(main_sub).children().last(), level+1, openPersonDivId);
			}else {
				main_dept += '<label for="" class="checkbox fl"><span class="fl w100">'+deptList[i].name+'</span></label> ';
				main_user += '<label for="" class="checkbox inline"></label>';
			}
			else{
				showOnlinePerson.recur(deptList[i].subDept, parentHtml, level+1, openPersonDivId);
			}
		}else {
			if(deptList_dept.user.length > 0){
				var sub_dept = '<div>', sub_user = '<div>';
				sub_dept += '<label for="" class="radio fl"><span class="fl w100">'+deptList_dept.name+'</span></label> ';
				var deptList_user_len = deptList_dept.user.length;
				for(var s=0;s<deptList_user_len;s++){
					var deptList_user_s = deptList_dept.user[s];
					sub_user += '<label for="" class="radio inline"><a href="#" onclick="showOnlinePerson.showPersonInfo('+deptList_user_s.id+');"><span>'+deptList_user_s.displayName +'</span></a></label>';
				}
				sub_user += '</div>';
				sub_dept += '</div>';
				var sub = $(
					'<li>'+
						'<div class="level'+level+' clearfix tree-list">'+
							sub_dept +
							sub_user +
						'</div>'+
					'</li>'
				);
				$(sub).appendTo(parentHtml);
			}else{
				sub_dept += '<label for="" class="checkbox fl"><span class="fl w100">'+deptList[i].name+'</span></label> ';
				sub_user += '<label for="" class="checkbox inline"></label>';
			}
		}
	}
}*/

/**
 * 显示人员
 * @param d 				部门人员数据
 * @param openPersonDivId	弹出层DIVID
 */
showOnlinePerson.showPage = function(d, openPersonDivId){
	var lv1_dept = ['<div>'], lv1_user = ['<div>'];
	if(d != null){
		if(d.user != null && d.user.length > 0){
			lv1_dept.push('<label class="radio fl"><span class="fl w100">'+d.name+'</span></label>');
			var d_user_len = d.user.length;
			for(var l1=0;l1<d_user_len;l1++){
				var d_user_l1 = d.user[l1];
				lv1_user.push('<label class="checkbox inline">');
				lv1_user.push('<span>');
				lv1_user.push('<a href="#" onclick="showOnlinePerson.showPersonInfo('+d_user_l1.id+');"><font color="#60AAE9">'+d_user_l1.displayName +'</font></a>');
				lv1_user.push('</span></label>');
			}
		}else{
			lv1_dept.push('<label class="checkbox fl"><span class="fl w100">'+d.name+'</span></label> ');
			lv1_user.push('<label class="checkbox inline"></label>');
		}
		lv1_user.push('</div>');
		lv1_dept.push('<a href="#" class="fr m-r tree-btn"><i class="fa fa-chevron-up"></i></a></div>');
	}else{
		lv1_dept.push('<label class="fl"><span class="fl w100"></span></label></div> ');
		lv1_user.push('<div style="text-align:center;">'+$.i18n.prop("JC_SYS_007")+'</div>');
	}
	var showlist = $([
		'<section class="w820 fl tree-ul tree-scroll">',
			'<ul class="tree-horizontal clearfix">',
				'<li>',
				'<div class="level1 clearfix tree-list">',
					lv1_dept.join('') ,
					lv1_user.join('') ,
				'</div>',
				'<ul id="lv"></ul>',
				'</li>',
			'</ul>',
		'</section>'
	].join(''));
	if(d != null && d.subDept != null && d.subDept.length > 0)
		showOnlinePerson.recur(d.subDept, showlist.find("#lv"), 2, openPersonDivId);
	return showlist;
}

/**
 * 递归查询下级菜单
 * @param deptList			部门集合
 * @param parentHtml		上级html
 * @param level				level-css样式
 * @param openPersonDivId	弹出层DIVID
 */
showOnlinePerson.recur = function(deptList, parentHtml, level, openPersonDivId){
	var deptList_len = deptList.length;
	for(var i=0; i<deptList_len; i++){
		var deptList_dept = deptList[i];
		if(deptList_dept.subDept) {
			var main_dept = ['<div>'], main_user = ['<div>'];
			if(deptList_dept.user.length > 0){
				main_dept.push('<label class="radio fl"><span class="fl w100">'+deptList_dept.name+'</span></label>');
				if(deptList_dept.user != undefined){
					if(deptList_dept.user.length > 0){
						var user_len = deptList_dept.user.length;
						for(var m=0; m<user_len; m++){
							var user_m = deptList_dept.user[m];
							main_user.push('<label class="radio inline">');
							main_user.push('<a href="#" onclick="showOnlinePerson.showPersonInfo('+user_m.id+');"><span><font color="#60AAE9">'+user_m.displayName +'</font></span></a>');
							main_user.push('</label>');
						}
					}
				}else{
					var subDept_user_len = deptList_dept.subDept[0].user.length;
					for(var m=0; m<subDept_user_len; m++){
						var subDept_user_m = deptList_dept.subDept[0].user[m];
						main_user.push('<label class="radio inline">');
						main_user.push('<a href="#" onclick="showOnlinePerson.showPersonInfo('+subDept_user_m.id+');"><span><font color="#60AAE9">'+subDept_user_m.displayName +'</font></span></a>');
						main_user.push('</label>');
					}
				}
			} else {
				main_dept.push('<label class="radio fl"><span class="fl w100">'+deptList[i].name+'</span></label>');
				main_user.push('<label class="radio inline"></label>');
			}
			main_dept.push('<a href="#" class="fr m-r tree-btn"><i class="fa fa-chevron-up"></i></a></div>');
			main_user.push('</div>');
			var main_sub = $([
				'<li>',
					'<div class="level'+level+' clearfix tree-list">',
						main_dept.join('') ,
						main_user.join('') ,
					'</div>',
					'<ul id="lv'+i+'"></ul>',
				'</li>'
			].join(''));
			$(main_sub).appendTo(parentHtml);
			showOnlinePerson.recur(deptList[i].subDept, $(main_sub).children().last(), level+1, openPersonDivId);
		} else {
			var sub_dept = ['<div>'], sub_user = ['<div>'];
			if(deptList_dept.user.length > 0){
				sub_dept.push('<label class="radio fl"><span class="fl w100">'+deptList_dept.name+'</span></label> ');
				var deptList_user_len = deptList_dept.user.length;
				for(var s=0;s<deptList_user_len;s++){
					var deptList_user_s = deptList_dept.user[s];
					sub_user.push('<label class="radio inline">');
					sub_user.push('<a href="#" onclick="showOnlinePerson.showPersonInfo('+deptList_user_s.id+');"><span><font color="#60AAE9">'+deptList_user_s.displayName +'</font></span></a>');
					sub_user.push('</label>');
				}
			}else{
				sub_dept.push('<label class="radio fl"><span class="fl w100">'+deptList[i].name+'</span></label> ');
				sub_user.push('<label class="radio inline"></label>');
			}
			sub_user.push('</div>');
			sub_dept.push('</div>');
			var sub = $([
				'<li>',
					'<div class="level'+level+' clearfix tree-list">',
						sub_dept.join('') ,
						sub_user.join('') ,
					'</div>',
				'</li>'
			].join(''));
			$(sub).appendTo(parentHtml);
		}
	}
}

/**
 * 显示人员详细信息
 */
showOnlinePerson.showPersonInfo = function(userId){
	var url = getRootPath()+"/system/getUserById.action";
	$.ajax({
		async: false,
		url : url,
		type : 'post',
		data: {
			id: userId
		},
		success : function(data) {
			var dateTime = new Date().getTime(),
			showPersonInfo = "showPersonInfo" + dateTime,
			x = 1000,
		    y = 10,
		    info = ['<div class="modal fade panel" id="'+showPersonInfo+'" aria-hidden="false">'];
			info.push('<div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">×</button><h4 class="modal-title">用户基本信息</h4></div><div class="modal-body dis-table" style="max-height: 661.5999999999999px;"><section class="dis-table-cell" style="width:162px;">');
			if(data.photo != null && data.photo != "")
				info.push('<img src="'+getRootPath()+'/'+data.photo+'" width="147" height="177">');
			else
				info.push('<img src="'+getRootPath()+'/img/none.jpg" width="147" height="177">');
			info.push('</section><section class="panel-tab-con dis-table-cell" style="padding-bottom:20px;"><table class="basicMsg" id="pInfo"><tbody><tr><td class="w115">用户显示名</td>');
			if(data.displayName != null && data.displayName != "")
				info.push('<td>'+data.displayName+'</td>');
			else
				info.push('<td></td>');
			info.push('</tr><tr><td>所在部门</td>');
		    if(data.deptName != null && data.deptName != "")
		    	info.push('<td>'+data.deptName+'</td>');
		    else
		    	info.push('<td></td>');
		    info.push('</tr><tr><td>职务</td>');
		    if(data.dutyIdValue != null && data.dutyIdValue != "")
		    	info.push('<td>'+data.dutyIdValue+'</td>');
		    else
		    	info.push('<td></td>');
		    info.push('</tr><tr><td>级别</td>');
		    if(data.levelValue != null && data.levelValue != "")
		    	info.push('<td>'+data.levelValue+'</td>');
		    else
		    	info.push('<td></td>');
		    info.push('</tr><tr><td>用户性别</td>');
		    if(data.sexValue != null && data.sexValue != "")
		    	info.push('<td>'+data.sexValue+'</td>');
		    else
		    	info.push('<td></td>');
		    info.push('</tr><tr><td>用户手机号码</td>');
		    if(data.mobile != null && data.mobile != "")
		    	info.push('<td>'+data.mobile+'</td>');
		    else
		    	info.push('<td></td>');
		    info.push('</tr><tr><td>用户邮箱</td>');
		    if(data.email != null && data.email != "")
		    	info.push('<td>'+data.email+'</td>');
		    else
		    	info.push('<td></td>');
		    info.push('</tr><tr><td>用户办公电话</td>');
		    if(data.officeTel != null && data.officeTel != "")
		    	info.push('<td>'+data.officeTel+'</td>');
		    else
		    	info.push('<td></td>');
		    info.push('</tr><tr><td>直接领导</td>');
		    if(data.leaderIdValue != null && data.leaderIdValue != "")
		    	info.push('<td>'+data.leaderIdValue+'</td>');
		    else
		    	info.push('<td></td>');
		    info.push('</tr></tbody></table></section></div></div></div></div>');
			$(info.join('')).appendTo("body");		
			$("#"+showPersonInfo).modal("show");
		},
		error: function(){
			msgBox.tip({content: "获取人员详细信息失败", type:'fail'});
		}
	});
}