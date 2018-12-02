	$(document).ready(function() {
//		if($.cookie("voiceSwitch") == 'true'){
//			$("#voiceSwitch").removeClass("off icon-no-voice").addClass("on icon-voice");
//		}else if($.cookie("voiceSwitch") == 'false'){
//			$("#voiceSwitch").removeClass("on icon-voice").addClass("off icon-no-voice");
//		}else{
//			$.cookie("voiceSwitch",'true');
//			$("#voiceSwitch").removeClass("on icon-voice").addClass("off icon-no-voice");
//		}
//		
    $("#dataLoad").hide(); //页面加载完毕后即将DIV隐藏
    $("#desktop_header li[name='menu1']").click();//默认选中主页
    //在线人数
    getOnlineCount();
    window.setInterval('getOnlineCount()',180000); 
});
	
	//面包屑 menuid菜单id url菜单链接 floors菜单层级 menuname菜单名称
	setMenuId = function(menuid,url,floors,menuname){//赋值菜单id为主页面变量
		$("#loadmenubarid").val(menuid);
		$("#loadmenubarName"+floors).val(menuname);
		$("#loadmenubarFloors").val(floors);
		$("#valPageSaveMneuBar").val(url);
	};
	
	loadleftMenu1 = function(menuid,url,menuname){//加载左侧菜单方法
		
		if(typeof(window.valPageSave) != 'undefined' && window.valPageSave.value != null
        		&& window.valPageSave.value == 'true'){
			var opt = {
    				content:$.i18n.prop("JC_OA_IC_096"),
    				success:function(){
    					if(document.getElementById('mainFrame') != null){
        					var mainWindow = document.getElementById('mainFrame').contentWindow;
        					mainWindow.pageMethon();//名称统一 ，方法内具体实现使用人自行开发
        				}
            			window.valPageSave.value = "false";
            			
            			if(url!=null&&url.length>0){
            				mainFrame.location.href=getRootPath()+"/"+url;
            			}
            			
            			var val = 'menu'+menuid;
            			closejcGOA_menu();
            			$(".menus").each(function(i) {
            	            if($(this).attr("id") == val){
            	            	$(this).css('display','');
            	            } else {
            	            	$(this).css('display','none');
            	            }
            	        });
            			$("#loadmenubarName").val(menuname);
            			$("#loadmenubarFloors").val(0);
            			$("#loadmenubarid").val(menuid);
    				},
    				noback:function(){
    					window.valPageSave.value = "false";
    					if(url!=null&&url.length>0){
            				mainFrame.location.href=getRootPath()+"/"+url;
            			}
    					var val = 'menu'+menuid;
            			closejcGOA_menu();
            			$(".menus").each(function(i) {
            	            if($(this).attr("id") == val){
            	            	$(this).css('display','');
            	            } else {
            	            	$(this).css('display','none');
            	            }
            	        });
            			$("#loadmenubarName").val(menuname);
            			$("#loadmenubarFloors").val(0);
            			$("#loadmenubarid").val(menuid);
    				},
    				buttons:{"离开并存草稿":"yes", "取消":"no","离开":"cancel"}
    		};
    		msgBox.confirm(opt);
		} else {
			
			if(url!=null&&url.length>0){
				mainFrame.location.href=getRootPath()+"/"+url;
			}
			
			var val = 'menu'+menuid;
			closejcGOA_menu();
			$(".menus").each(function(i) {
	            if($(this).attr("id") == val){
	            	$(this).css('display','');
	            } else {
	            	$(this).css('display','none');
	            }
	        });
			$("#loadmenubarName").val(menuname);
			$("#loadmenubarFloors").val(0);
			$("#loadmenubarid").val(menuid);
			$("#desktopBody").hide().show();
		}
	};
	
	reloadTopMenuClass = function(e){
		if($(e).parent().hasClass("clearfix")){
			$("#topnav-other_modules").find("li").removeClass('active');
		}else{
			$("#topnav-other_modules").addClass('active').siblings().removeClass('active');
		}
		$(e).addClass('active').siblings().removeClass('active');
	};

	function closejcGOA_menu(){
        $('.nav-primary a').each(function(){
            var $active = $(this).parent().siblings(".active");
            $active&&$active.find('> a').removeClass('active')&&$active.removeClass('active');
            $(this).next().is("ul")&&$(this).next().hide();
        });
    }
	
	function getOnlineCount(){
		$.ajax({
			type : "GET",
			url : getRootPath()+"/system/getOnlineUserCount.action",
			data : null,
			dataType : "json",
			success : function(data) {
				if(data){
					$("#onlineCount").html(data);
				}
			}
		});
	}
	
	//换肤
	var subState = false;
	function skin(theme){
		if(subState)return;
		subState = true;
    	var url = getRootPath()+"/sys/user/skin.action";
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : {theme:theme},
			success : function(data) {
				subState = false;
				if(data.success == "true"){
					window.location = getRootPath();
				}
			},
			error : function() {
				subState = false;
			}
		});
    }
	
	//生日祝福
	function loadbirthday(){
		var url = getRootPath()+"/sys/specialData/birthdayBlessing.action";
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : null,
			dataType : "json",
			success : function(data) {
				if (data) {
					$("#birthdayDiv").fadeIn(1000);
				}
			}
		});
	};