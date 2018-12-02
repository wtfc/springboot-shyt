var queryForTree={};

queryForTree.zNodes = null;
queryForTree.id = null;
queryForTree.name = null;
queryForTree.callback = null;
queryForTree.rootId = null;//第一个
queryForTree.rootName = null;

var tree = null;
/**---------------------------------------------*/
queryForTree.defaultCheckButton=function(){
	$('label').removeClass('active');
	$("#today").toggleClass('active')
};

queryForTree.setting = {
		check : {
			enable : false,
			nocheckInherit : true
		},
		view : {
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback:{
			beforeClick: function(id, node){
				if(node.type!=0){//type 0 人员 1部门
					return false
				}else{
					return true;
				}
			},
			onClick : function (event, id, node) {// id=treeDemo
			    $("#userid").val(node.id);
			    var userid=node.id;
			    //diaryMenuFlag 1日程安排 2下属日程 3共享查询 4领导日程
				if($("#diaryMenuFlag").val()=="2"){
					if($("#diaryPageFlag").val()=="1"){//diaryPageFlag 1日历 2列表
						schedule.destroyFullCalendar();
						schedule.initFullCalendar(node.id);
						queryForTree.defaultCheckButton();
						queryForTree.refreshTree();//刷新树
					}else{
						schedule.getSelectDiary($("#startDateTime").val(),$("#endDateTime").val());//列表页刷新选择日程下拉选
						schedule.clearDetailInfo();//清空详细页各项内容
						schedule.clearModifyInfo();//清空修改页各项内容
						$('#comment').empty();// 清空批注信息
						$('#leaderIdeaTitle').show();// 显示领导批注字样
						schedule.initAnno(0);// 批注信息初始化
						queryForTree.refreshTreeForList();//刷新列表页树
					}
				}
				if($("#diaryMenuFlag").val()=="3"){
					if($("#diaryPageFlag").val()=="1"){//diaryPageFlag 1日历 2列表
						schedule.destroyFullCalendar();
						schedule.initFullCalendar(node.id);
						queryForTree.defaultCheckButton();
						queryForTree.refreshTree();//刷新树
					}else{
						schedule.getSelectDiary($("#startDateTime").val(),$("#endDateTime").val());//列表页刷新选择日程下拉选
						schedule.clearDetailInfo();//清空详细页各项内容
						schedule.clearModifyInfo();//清空修改页各项内容
						$('#comment').empty();// 清空批注信息
						$('#leaderIdeaTitle').show();// 显示领导批注字样
						schedule.initAnno(0);// 批注信息初始化
						queryForTree.refreshTreeForList();//刷新列表页树
					}
				}
				if($("#diaryMenuFlag").val()=="4"){
//					var mandatorId = $("#mandatorId").val();
					if($("#diaryPageFlag").val()=="1"){//diaryPageFlag 1日历 2列表
						diaryLeadCalendar.getDelegation();
						schedule.destroyFullCalendar();
						schedule.initFullCalendar(node.id);
						queryForTree.defaultCheckButton();
						queryForTree.refreshTree();//刷新树
						$("#possessorId").val(node.id);
//						if (userid == mandatorId && userid != 0) {//单委托
						if(schedule.compareMandatorIdUserid(node.id)&& node.id != 0){//比较判断当前传入用户是否是当前登录用户的委托人
							diaryLeadCalendar.initModifyButton();
						}else{
							$("#toModify").hide();
							$("#deleteDiary").hide();
							$("#closeDetail").removeClass("btn");
							$("#closeDetail").addClass("btn dark");
						}
					}else{
						diaryLeadList.getDelegation();
						schedule.getSelectDiary($("#startDateTime").val(),$("#endDateTime").val());//列表页刷新选择日程下拉选
						schedule.detailTable();// 更新到详细界面
						schedule.clearDetailInfo();//清空详细页各项内容
						schedule.clearModifyInfo();//清空修改页各项内容
						$('#comment').empty();// 清空批注信息
						$('#leaderIdeaTitle').show();// 显示领导批注字样
						schedule.initAnno(0);// 批注信息初始化
//						if (userid == mandatorId && userid != 0) {//单委托
						$("#possessorId").val(node.id);
						if(schedule.compareMandatorIdUserid(node.id)&& node.id != 0){//比较判断当前传入用户是否是当前登录用户的委托人
//							diaryLeadList.initModifyButton();
							var str="<a href='#' class='btn dark' id='calendarView'>日历形式展示</a> ";
							$("#buttonSection").html(str);
//							$("#toModify").hide();
//							$("#deleteDiary").hide();
//							$("#calendarView").removeClass("btn");
//							$("#calendarView").addClass("btn dark");
							diaryLeadList.showNewAddButton();
						}else{
							$("#toNewAddButton").html("");
							var str="<a href='#' class='btn dark' id='calendarView'>日历形式展示</a> ";
							$("#buttonSection").html(str);
//							$("#toModify").hide();
//							$("#deleteDiary").hide();
//							$("#calendarView").removeClass("btn");
//							$("#calendarView").addClass("btn dark");
						}
						$("#calendarView").click(function(){diaryLeadList.toCalendarView();});//日历形式展示按键点击事件绑定
						queryForTree.refreshTreeForList();//刷新列表页树
					}
				}
				
				$(".table-td-striped").hide();
				$(".table-td-striped").show();
				if($("#diaryPageFlag").val()=="2"){//diaryPageFlag 1日历 2列表
					$('#leaderIdeaTable').hide();//隐藏批注框
				}
			}
		}
	};

//默认选中节点
queryForTree.defaultCheck = function(treeId,diaryMenuFlag,checkedId){
	if(queryForTree.rootId != null && queryForTree.rootName != null){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var node = treeObj.getNodeByParam("id", queryForTree.rootId);
		treeObj.selectNode(node, true);//选中节点 true 为选中
		//diaryMenuFlag 1日程安排 2下属日程 3共享查询 4领导日程
		if(diaryMenuFlag=="2"){
			if($("#diaryPageFlag").val()=="1"){//diaryPageFlag 1日历 2列表
				if($("#userid").val()!=null&&$("#userid").val()!=""&&$("#userid").val()!="0"){
					schedule.initFullCalendar(checkedId);//加载之前选中节点用户的日程
					var node = treeObj.getNodeByParam("id", checkedId);
					treeObj.selectNode(node, true);//选中节点 true 为选中
					$("#userid").val(checkedId);
				}else{
					$("#userid").val(queryForTree.rootId);
					schedule.initFullCalendar(queryForTree.rootId);//加载默认节点用户的日程
				}
			}else{
				if($("#userid").val()!=null&&$("#userid").val()!=""&&$("#userid").val()!="0"){
					var node = treeObj.getNodeByParam("id", checkedId);
					treeObj.selectNode(node, true);//选中节点 true 为选中
					$("#userid").val(checkedId);
				}else{
					$("#userid").val(queryForTree.rootId);
					$("#userid").val(queryForTree.rootId);
				}
			}
		}
		if(diaryMenuFlag=="3"){
			if($("#diaryPageFlag").val()=="1"){//diaryPageFlag 1日历 2列表
				if($("#userid").val()!=null&&$("#userid").val()!=""&&$("#userid").val()!="0"){
					schedule.initFullCalendar(checkedId);//加载之前选中节点用户的日程
					var node = treeObj.getNodeByParam("id", checkedId);
					treeObj.selectNode(node, true);//选中节点 true 为选中
					$("#userid").val(checkedId);
				}else{
					$("#userid").val(queryForTree.rootId);
					schedule.initFullCalendar(queryForTree.rootId);//加载默认节点用户的日程
				}
			}else{
				if($("#userid").val()!=null&&$("#userid").val()!=""&&$("#userid").val()!="0"){
					var node = treeObj.getNodeByParam("id", checkedId);
					treeObj.selectNode(node, true);//选中节点 true 为选中
					$("#userid").val(checkedId);
				}else{
					$("#userid").val(queryForTree.rootId);
				}
			}
		}
		if(diaryMenuFlag=="4"){
			if($("#diaryPageFlag").val()=="1"){//diaryPageFlag 1日历 2列表
				diaryLeadCalendar.getDelegation();
				if($("#userid").val()!=null&&$("#userid").val()!=""&&$("#userid").val()!="0"){
					schedule.initFullCalendar(checkedId);//加载之前选中节点用户的日程
					var node = treeObj.getNodeByParam("id", checkedId);
					treeObj.selectNode(node, true);//选中节点 true 为选中
					$("#userid").val(checkedId);
					$("#possessorId").val(checkedId);
					if(schedule.compareMandatorIdUserid(checkedId)){//比较判断当前传入用户是否是当前登录用户的委托人
						diaryLeadCalendar.initModifyButton();
					}else{
						$("#toModify").hide();
						$("#deleteDiary").hide();
						$("#closeDetail").removeClass("btn");
						$("#closeDetail").addClass("btn dark");
					}
				}else{
					schedule.initFullCalendar(queryForTree.rootId);//加载默认节点用户的日程
					$("#userid").val(queryForTree.rootId);
					$("#possessorId").val(queryForTree.rootId);
					if(schedule.compareMandatorIdUserid(queryForTree.rootId)){//比较判断当前传入用户是否是当前登录用户的委托人
//						diaryLeadCalendar.initModifyButton();
						var str="<a href='#' class='btn dark' id='calendarView'>日历形式展示</a> ";
						$("#buttonSection").html(str);
					}else{
//						$("#toModify").hide();
//						$("#deleteDiary").hide();
//						$("#closeDetail").removeClass("btn");
//						$("#closeDetail").addClass("btn dark");
						var str="<a href='#' class='btn dark' id='calendarView'>日历形式展示</a> ";
						$("#buttonSection").html(str);
					}
					$("#calendarView").click(function(){diaryLeadList.toCalendarView();});//日历形式展示按键点击事件绑定
				}
			}
//			else{
//				diaryLeadList.getDelegation();
//				if($("#userid").val()!=null&&$("#userid").val()!=""&&$("#userid").val()!="0"){
//					var node = treeObj.getNodeByParam("id", checkedId);
//					treeObj.selectNode(node, true);//选中节点 true 为选中
//					$("#userid").val(checkedId);
//					$("#possessorId").val(checkedId);
//					if(schedule.compareMandatorIdUserid(checkedId)){//比较判断当前传入用户是否是当前登录用户的委托人
//						diaryLeadList.initModifyButton();
//						diaryLeadList.showNewAddButton();
//					}else{
//						$("#toNewAddButton").html("");
//						$("#toModify").hide();
//						$("#deleteDiary").hide();
//						$("#calendarView").removeClass("btn");
//						$("#calendarView").addClass("btn dark");
//					}
//				}else{
//					$("#userid").val(queryForTree.rootId);
//					$("#possessorId").val(queryForTree.rootId);
//					if(schedule.compareMandatorIdUserid(queryForTree.rootId)){//比较判断当前传入用户是否是当前登录用户的委托人
//						diaryLeadList.initModifyButton();
//						diaryLeadList.showNewAddButton();
//					}else{
//						$("#toNewAddButton").html("");
//						$("#toModify").hide();
//						$("#deleteDiary").hide();
//						$("#calendarView").removeClass("btn");
//						$("#calendarView").addClass("btn dark");
//					}
//				}
//			}
		}
	}
};
//刷新树
queryForTree.refreshTree=function(){
	if($("#treeDemo")[0]){
    	var content = $("#content").height();
        var headerHeight_1 = $('#header_1').height() || 0;
        var headerHeight_2 = $("#header_2").height() || 0;
        $(".tree-right").css("padding-left","215px");
		$("#LeftHeight").height(content-80-headerHeight_1-headerHeight_2);
        var lh = $("#LeftHeight").height()  
        if($("#scrollable").scrollTop()>=113){
            $("#LeftHeight").addClass("fixedNav");
            $("#LeftHeight").height(lh + 113)
        }else{
            var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
            $("#LeftHeight").height(lh + a)
            $("#LeftHeight").removeClass("fixedNav");
        }
    }
};
queryForTree.refreshTreeForList=function(){
	if($("#treeDemo")[0]){
    	var content = $("#content").height();
        var headerHeight_1 = $('#header_1').height() || 0;
        var headerHeight_2 = $("#header_2").height() || 0;
        $(".tree-right").css("padding-left","215px");
        $("#LeftHeight_box_rl").height(content-80-headerHeight_1-headerHeight_2);
	    var lh = $("#LeftHeight_box_rl").height();
	    var day_height=$("#datePicker").find("div").height()+32;
	    $("#LeftHeight_rl").height(content-80-headerHeight_1-headerHeight_2-day_height);
	    var lh_day = $("#LeftHeight_rl").height(); 
	    if($("#scrollable").scrollTop()>=113){
	        $("#LeftHeight_box_rl").addClass("fixedNav");
	        $("#LeftHeight_rl").height(lh - day_height + 113);
	    }else{
	        var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
	        		$("#LeftHeight_rl").height(lh_day + a)
	        //$("#LeftHeight").height()
	        $("#LeftHeight_box_rl").removeClass("fixedNav");
	    }
    }
};
/**
 * 初始化方法
 */
jQuery(function($){
	var url ="";
	//diaryMenuFlag 1日程安排 2下属日程 3共享查询 4领导日程
	if($("#diaryMenuFlag").val()=="2"){
		url = getRootPath()+"/po/diary/queryForUnderlingTree.action?time=" + new Date();
	}
	if($("#diaryMenuFlag").val()=="3"){
		url = getRootPath()+"/po/diary/queryForShareTree.action?time=" + new Date();
	}
	if($("#diaryMenuFlag").val()=="4"){
		url = getRootPath()+"/po/diary/queryForLeadTree.action?time=" + new Date();
	}
	$.ajaxSetup({
	    async : false 
	});
	$.getJSON(url, function(data) {
		if(data==null||data==""){
			if($("#diaryMenuFlag").val()=="2"){
				loadrightmenu('/po/diary/diaryNoUnderling.action');
			}
			if($("#diaryMenuFlag").val()=="3"){
				loadrightmenu('/po/diary/diaryNoShare.action');
			}
			if($("#diaryMenuFlag").val()=="4"){
				loadrightmenu('/po/diary/diaryNoLead.action');
			}
		}else{
			if (data) {//type 0 人员 1部门
//				for(var i=0;i<data.length;i++){
//					console.log(data[i].name+" "+data[i].type);
//				}
				queryForTree.zNodes = [];
				var isFrist=false;
				var fristNum;
				$.each(data, function(i, o) {
					if(o.type==0 && isFrist==false){//type 0 人员 1部门
						isFrist=true;
						fristNum=i;
					}
				});
				$.each(data, function(i, o) {
					if(o.type==0 && i == fristNum){//type 0 人员 1部门
						queryForTree.rootId = o.id;
						queryForTree.rootName = o.name;
					}
					queryForTree.zNodes[i] = {
						id : o.id,
						pId : o.parentId,
						name : o.name + "",
						checkState : false,
						createUserDept: o.createUserDept,
						type:o.type
					};
				});
				tree = $.fn.zTree.init($("#treeDemo"), queryForTree.setting, queryForTree.zNodes);
				tree.expandAll(true);
				queryForTree.defaultCheck("treeDemo",$("#diaryMenuFlag").val(),$("#userid").val());
			}
		}
	});
});