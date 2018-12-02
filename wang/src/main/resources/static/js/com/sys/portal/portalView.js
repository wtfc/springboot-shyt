var portalview = {}; 	

//门户布局获取
portalview.layoutPortal = function(portalId,portalType) {
	/*portalview.clearportlet();
	portalview.clearportlet1();
	portalview.clearportlet2(); */
	jQuery.ajax({
        url: getRootPath()+"/sys/portlet/getLayoutPortals.action?portalId="+portalId+"&portalType="+portalType+"&time="+new Date(),
        type: 'post',
        success: function(data, textStatus, xhr) {
        	portalview.initiallayout(data.layoutType);
        	var dataObj=data.portletList; 
        	var relistsize = data.reListSize;
        	var relist = data.reList; 
        	//var layoutid = data.layoutId;
        	if(relistsize > 0){
        		for(var i=0;i<relist.length;i++){
        			if(relist[i].viewType != 'shortcut'){
        				portalview.layoutPortals(relist[i].id,relist[i].letTitle,relist[i].siteY,relist[i].siteX,relist[i].layoutPackId,relist[i].viewType);
        			} else {
        				portalview.setShortcut(relist[i].id,relist[i].functionId);
        			}
        			if(relist[i].viewType != 'textareaEdit' && relist[i].viewType != 'printEdit'){
        				portalview.setDivPortlets(relist[i].id,relist[i].functionId,relist[i].viewType);
        			}
        		}
        	} else {
        		var coordinateX1=0;
        		var coordinateX2=0;
        		var coordinateX3=0;
        		var coordinateY1=0;
        		var coordinateY2=0;
        		var coordinateY3=0;
        		for(var i=0;i<dataObj.length;i++){ 
        			if(dataObj[i].viewType != 'shortcut'){
        				if((i+1)%3 == 1){
        					if(coordinateX1 == 3){
        						coordinateY1++;
        						coordinateX1=0;
        					}
        					portalview.layoutPortals(dataObj[i].id,dataObj[i].letTitle,coordinateY1,coordinateX1,1,dataObj[i].viewType);
        					coordinateX1++;
        				}else if((i+1)%3 == 2){
        					if(coordinateX2 == 3){
        						coordinateY2++;
        						coordinateX2=0;
        					}
        					portalview.layoutPortals(dataObj[i].id,dataObj[i].letTitle,coordinateY2,coordinateX2,2,dataObj[i].viewType);
        					coordinateX2++;
        				}else if((i+1)%3 == 0){
        					if(coordinateX3 == 3){
        						coordinateY3++;
        						coordinateX3=0;
        					}
        					portalview.layoutPortals(dataObj[i].id,dataObj[i].letTitle,coordinateY3,coordinateX3,3,dataObj[i].viewType);
        					coordinateX3++;
        				}
        			}
        			if(dataObj[i].viewType != 'textareaEdit' && dataObj[i].viewType != 'printEdit'){
        				portalview.setDivPortlets(dataObj[i].id,dataObj[i].functionId,dataObj[i].viewType);
        			}
                }
        	}
        	
        	var t = $("#controlshortcut").html();
            t = t.replace(/[\r\n]/g,"").replace(/[ ]/g,"");
            if(t == ''){
            	$("#shortcutShow").css("display","none");
        	}
            
            portalview.clearPortlet(1);
            portalview.clearPortlet(2);
            portalview.clearPortlet(3);
        },error:function(e){
        	// alert("加载数据错误。");
        	msgBox.tip({content: "加载数据错误", type:'fail'});
        }
    });
};

//加载数据替换门户模块信息
portalview.setDivPortlets = function(divid,functionId,viewType){
	if($("#fun_"+divid).length>0){
		jQuery.ajax({
	        url: getRootPath()+"/sys/portalFunction/getFunctionForPortlet.action?layoutStatus=view&tabsize=0.3&functionId="+functionId+"&viewType="+viewType+"&portletId="+divid+"&time="+new Date(),
	        type: 'post',
	        //async:false,
	        success: function(data, textStatus, xhr) {
	        	$("#fun_"+divid).html(data);
	        },error:function(){
	      	 // alert("加载数据错误。");
	      	  msgBox.tip({content: "加载数据错误", type:'fail'});
	        }
	    });
	} else {
		if(viewType == 'shortcut' && $("#div_"+divid).length > 0){
			$("#liout_"+divid).html("<div class=\"fl panel-con-wrap inform-con\"><span>无访问权限</span></div>");
		}
	}
};
//设置快捷方式
portalview.setShortcut = function(portalvoId,functionId){
	$("#controlshortcut").prepend("<li id='liout_"+portalvoId+"'></li>");
};
//触发更多事件
portalview.loadMore = function(portalvoId,functionId){
	var moreurl;
	var menubarurl;
	if(functionId != null && functionId != ''){
		//loadrightmenu($('#morelink_'+portalvoId+'_'+functionId).val());
		//navigationbar(null,$('#morelink_'+portalvoId+'_'+functionId).val());
		moreurl = $('#morelink_'+portalvoId+'_'+functionId).val();
		menubarurl = $('#menubarlink_'+portalvoId+'_'+functionId).val();
	} else {
		//loadrightmenu($('#morelink_'+portalvoId).val());
		//navigationbar(null,$('#morelink_'+portalvoId).val());
		moreurl = $('#morelink_'+portalvoId).val();
		menubarurl = $('#menubarlink_'+portalvoId).val();
	}
	if(typeof(window.parent.loadmenubarurl) != 'undefined' ){
		if(moreurl.indexOf("parameter") != -1)
			window.parent.loadmenubarurl.value = moreurl.split("?")[0];
		else
			window.parent.loadmenubarurl.value = moreurl;
		
		if(menubarurl != undefined)
			window.parent.loadmenubarurl.value = menubarurl;
		
		if(typeof(window.parent.loadmenubarid) != 'undefined' && window.parent.loadmenubarid.value != null)
			window.parent.loadmenubarid.value = 0;
	}
	loadrightmenu(moreurl,'',window.parent.loadmenubarurl.value);
		
};

//设置布局
portalview.layoutPortals = function(portletid,letTitle,x,y,layoutPackId,viewType) {
	$('#portalsLayout'+layoutPackId).portlet('option', 'filterRepeat',true);
	$('#portalsLayout'+layoutPackId).portlet('option', 'add',{
			position: {
				x: x,
				y: y
		    },
		    portlet: {
		        attrs: {
		            id: portletid
		        },
		        beforeRemove: function() {
                    return true;
                },
		        content: {
		            type: 'text',
		            text: function() {
		            	$(".loadfunctionview").each(function() {
		            		$(this).empty();
		            	});
		            	if(viewType == 'textareaEdit' && $("#letTextarea_"+portletid).val() != undefined){
		            		var co=$("#letTextarea_"+portletid).html();
		            		$("#letTextarea_"+portletid).html(co.replace(/(_p;)/g,"&nbsp;"));
		            	}
		            	var htmlvalue = $('#div_'+portletid).html();
		            	$('#div_'+portletid).html("");
                        return htmlvalue;
                    }
		        }
		    }
    });
};

//清除临时门户组件
portalview.clearPortlet = function(layoutPackId){
	$('#portalsLayout'+layoutPackId).portlet('option', 'remove', '#news1');
	$('#portalsLayout'+layoutPackId).portlet('option', 'remove', '#news2');
	$('#portalsLayout'+layoutPackId).portlet('option', 'remove', '#news3');
	$('#portalsLayout'+layoutPackId).portlet('option', 'remove', '#-99');
};

//根据类型初始化门户布局
portalview.initiallayout = function(layouttype){
	$('#loadLayout').html($('#loadLayout'+layouttype).html());
	if(layouttype == 1){
		portalview.clearportlet(1);
		portalview.clearportlet(2);
		portalview.clearportlet(3);
	}
	if(layouttype == 2){
		portalview.clearportlet1(1);
		portalview.clearportlet(2);
		portalview.clearportlet(3);
	}
	if(layouttype == 3){
		portalview.clearportlet(3);
		portalview.clearportlet1(1);
		portalview.clearportlet2(2); 
	}
	if(layouttype == 4){
		portalview.clearportlet1(3);
		portalview.clearportlet1(1);
		portalview.clearportlet1(2); 
	}
	if(layouttype == 5){
		portalview.clearportlet(3);
		portalview.clearportlet(1);
		portalview.clearportlet(2); 
	}
	if(layouttype == 6){
		portalview.clearportlet2(3);
		portalview.clearportlet2(1);
		portalview.clearportlet2(2); 
	}
	if(layouttype == 7){
		portalview.clearportlet3(3);
		portalview.clearportlet(1);
		portalview.clearportlet3(2); 
	}
	if(layouttype == 8){
		portalview.clearportlet2(3);
		portalview.clearportlet2(1);
		portalview.clearportlet2(2); 
	}
	if(layouttype == 9){
		portalview.clearportlet3(3);
		portalview.clearportlet3(1);
		portalview.clearportlet3(2); 
	}
};

//清空门户布局数据
portalview.clearportlet = function(layouttype){//3:3
	$('#portalsLayout'+layouttype).portlet({
        sortable: true,
        singleView: false,
        columns: [
            { 
            	portlets: [{
            		attrs: {
            			id: 'news1'
            		},
            		content: {
            			type: 'text',
            			text: function() {
                            return "";
                        }
            		}
            	}]
            },{ 
            	portlets: [{
            		attrs: {
            			id: 'news2'
            		},
            		content: {
            			type: 'text',
            			text: function() {
            				return "";
                        }
            		}
            	}]
            },{ 
            	portlets: [{
            		attrs: {
            			id: 'news3'
            		},
            		content: {
            			type: 'text',
            			text: function() {
            				return "";
                        }
            		}
            	}]
            }
        ]
	});
};

//清空门户布局数据
portalview.clearportlet1 = function(layouttype){//1:1 初始化
	$('#portalsLayout'+layouttype).portlet({
        sortable: true,
        singleView: false,
        columns: [
            { 
            	portlets: [{
            		attrs: {
            			id: 'news1'
            		},
            		content: {
            			type: 'text',
            			text: function() {
                            return "";
                        }
            		}
            	}]
            },{ 
            	portlets: [{
            		attrs: {
            			id: 'news2'
            		},
            		content: {
            			type: 'text',
            			text: function() {
            				return "";
                        }
            		}
            	}]
            }
        ]
	});
};

//清空门户布局数据
portalview.clearportlet2 = function(layouttype){//2:1 初始化
	$('#portalsLayout'+layouttype).portlet({
        sortable: true,
        singleView: false,
        columns: [
            { 
            	portlets: [{
            		attrs: {
            			id: 'news1'
            		},
            		content: {
            			type: 'text',
            			text: function() {
                            return "";
                        }
            		}
            	}]
            },{ 
            	portlets: [{
            		attrs: {
            			id: 'news2'
            		},
            		content: {
            			type: 'text',
            			text: function() {
            				return "";
                        }
            		}
            	}]
            }
        ]
	});
};
//清空门户布局数据
portalview.clearportlet3 = function(layouttype){//1 初始化
	$('#portalsLayout'+layouttype).portlet({
        sortable: true,
        singleView: false,
        columns: [
            { 
            	portlets: [{
            		attrs: {
            			id: 'news1'
            		},
            		content: {
            			type: 'text',
            			text: function() {
                            return "";
                        }
            		}
            	}]
            }
        ]
	});
};
//开启隐藏门户组件模块
/*portalview.openportlet = function(portletid){
	$('#portalsLayout1').portlet('toggle', portletid, 'show');
	$('#portalsLayout2').portlet('toggle', portletid, 'show');
	$('#portalsLayout3').portlet('toggle', portletid, 'show');
};*/

//关闭门户组件模块
/*portalview.closeportlet = function(portletid){
	$('#portalsLayout1').portlet('toggle', portletid);
	$('#portalsLayout2').portlet('toggle', portletid);
	$('#portalsLayout3').portlet('toggle', portletid);
};*/

//初始化
jQuery(function($){
	portalview.layoutPortal($('#portalId').val(),$('#portalType').val());
});	


