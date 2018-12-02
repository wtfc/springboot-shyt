<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<html>
<head>
<title></title>
<style type="text/css">
.tablebq {
	table-layout:fixed;
	width:180;
	border-collapse:collapse;
	width:100%;
	margin: auto;
	background: url(/goa/images/po/img/bq_1.png) bottom center no-repeat;
}
.tablebq td{
	border:0;
	border-collapse:collapse;
}
.bq1 {
	background: url(/goa/images/po/img/bq_2.gif) top center;
	color:#0047ab;
	height:30px;	padding: 0px 8px;

}
.bq2 { color:#ba320d; height:28px;text-align:left;padding-left:10px;font-size:14px;color:#666; background: url(/goa/images/po/img/an_line.gif) bottom center no-repeat;}
.bq3 { line-height:150%;color:#333;font-size:12px;table-layout:fixed; width:180; overflow:hidden; padding:5px 10px 20px 10px;}
.bq3 textarea{color:#333 !important;font-size:12px;}
.apDiv1 {
	width:220px;
	z-index:1;
}
.apDiv2 {
	width:220px;
	z-index:2;
}
.txt_input1 {
	border: 1px ridge #c0c0c0;
	font-family: "宋体";
	font-size: 14px;
	width:200px;

}
</style>
<script type="text/javascript">
//************************************textarea自适应大小***********************************
var autoTextarea = function (elem, extra, maxHeight) {
    extra = extra || 0;
    var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
    isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
            addEvent = function (type, callback) {
                    elem.addEventListener ?
                            elem.addEventListener(type, callback, false) :
                            elem.attachEvent('on' + type, callback);
            },
            getStyle = elem.currentStyle ? function (name) {
                    var val = elem.currentStyle[name];

                    if (name === 'height' && val.search(/px/i) !== 1) {
                            var rect = elem.getBoundingClientRect();
                            return rect.bottom - rect.top -
                                    parseFloat(getStyle('paddingTop')) -
                                    parseFloat(getStyle('paddingBottom')) + 'px';        
                    };

                    return val;
            } : function (name) {
                            return getComputedStyle(elem, null)[name];
            },
            minHeight = parseFloat(getStyle('height'));
            var textHeight = minHeight;
            if(textHeight<200){
            	textHeight=200;
            }

    elem.style.resize = 'none';

    var change = function () {
            var scrollTop, height,
                    padding = 0,
                    style = elem.style;

            if (elem._length === elem.value.length) return;
            elem._length = elem.value.length;

            if (!isFirefox && !isOpera) {
                    padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
            };
            scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

            elem.style.height = textHeight + 'px';
            if (elem.scrollHeight > minHeight) {
                    if (maxHeight && elem.scrollHeight > maxHeight) {
                            height = maxHeight - padding;
                            style.overflowY = 'auto';
                    } else {
                            height = elem.scrollHeight - padding;
                            style.overflowY = 'hidden';
                    };
                    style.height = height + extra + 'px';
                    scrollTop += parseInt(style.height) - elem.currHeight;
                    document.body.scrollTop = scrollTop;
                    document.documentElement.scrollTop = scrollTop;
                    elem.currHeight = parseInt(style.height);
            };
    };

    addEvent('propertychange', change);
    addEvent('input', change);
    addEvent('focus', change);
    change();
};
//************************************textarea自适应大小*************************************
//删除便签
function deleteCarpet(delobj){
	obj=delobj;
	msgBox.confirm({
		content: $.i18n.prop("JC_OA_PO_056"),
		success: function(){
			dodeleteCarpet();
		}
	});
}

//删除便签的回调函数
function dodeleteCarpet(){
	var url = getRootPath()+"/po/personalBox/deleteNote.action";
	var params = {
		ids: obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.pkid
	};
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : params,
		success : function(data) {
			document.getElementById("dom0").removeChild(obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode);
			if($("#dom0").attr("ondblclick")==""){
				$("#dom0").attr("ondblclick","add_div()");
			}
		},
		error : function() {
			msgBox.tip({content: $.i18n.prop("JC_SYS_006"), type:'fail'});
		}
	});
}

//限制text字符长度
function validateMaxLength(parmObject){
    var maxChars = parmObject.getAttribute? parseInt(parmObject.getAttribute("maxlength")) : "";
	if (parmObject.value.length > maxChars)
	parmObject.value = parmObject.value.substring(0,maxChars);
	var curr = maxChars - parmObject.value.length;
	curr = parseInt(curr);
	if(curr<0){
	   curr = 0;	
	}
	var id = parmObject.getAttribute("id");
	id = id.substring(11,id.length);
	document.getElementById("count"+id).innerHTML = curr.toString();
	autoTextarea(parmObject);
}

//为Number增加一个属性,判断当前数据类型是否是数字    
Number.prototype.NaN0=function(){return isNaN(this)?0:this;}
var domid=1;
//全局变量
var iMouseDown=false;
var dragObject=null;
//覆盖关系
var zindex=0;

//获得鼠标的偏移量(对象2-对象1)
function getMouseOffset(target,vent){
	var vent=getEvent();
	var docPos=getPosition(target);
	var mousePos=mouseCoords(vent);
	return {x:mousePos.x-docPos.x,y:mousePos.y-docPos.y};
}

//获得事件发生的实际位置 对象1
function getPosition(e){
	var left=0;
	var top=0;
	//相对位置累加得到实际位置
	while(e.offsetParent){
		left+=e.offsetLeft+(e.currentStyle?(parseInt(e.currentStyle.borderLeftWidth)).NaN0():0);
		top+=e.offsetTop+(e.currentStyle?(parseInt(e.currentStyle.borderTopWidth)).NaN0():0);
		e=e.offsetParent;
	}
	left+=e.offsetLeft+(e.currentStyle?(parseInt(e.currentStyle.borderLeftWidth)).NaN0():0);
	top+=e.offsetTop+(e.currentStyle?(parseInt(e.currentStyle.borderTopWidth)).NaN0():0);
	
	return {x:left,y:top};
}

//获得发生事件鼠标的位置 对象2
function mouseCoords(vent){
	if(vent.pageX||vent.pageY){
		return {x:vent.pageX,y:vent.pageY};
	}
	return {x:vent.clientX+document.body.scrollLeft-document.body.clientLeft,y:vent.clientY+document.body.scrollTop-document.body.clientTop};
}

//定义可以拖拽的对象
function makeDragable(imgjct){
	imgjct.style.zIndex=zindex;
	zindex=zindex+1;
	document.getElementById("dom0").ondblclick="";
	if(!imgjct) return;
	
	//为可拖拽对象定义一个onmousedown事件的方法
	var vent=getEvent();
	imgjct.onmousedown=function(vent){
		dragObject=this;
		mouseOffset=getMouseOffset(this,vent);
		//return false;
	}
}
	
//定义鼠标点下所调用的方法
function mouseDown(vent){
	vent=vent||window.event;
	var target=vent.target||vent.srcElement;
	
	if(target.tagName=="TEXTAREA"){
		iMouseDown=false;
		dragObject=null;
	}

	if(target.onmousedown||target.getAttribute('DragObj')){
		return false;
	}
}

//鼠标抬起后释放对象
function mouseUp(vent){
	//更新数据库中的坐标
	if(dragObject!=null){
		var leftTemp = window.getComputedStyle ?window.getComputedStyle(dragObject, null).left : dragObject.currentStyle.left;
		var topTemp = window.getComputedStyle ?window.getComputedStyle(dragObject, null).top : dragObject.currentStyle.top;
		
		leftTemp=leftTemp.substring(0,leftTemp.length-2);
		topTemp=topTemp.substring(0,topTemp.length-2);
		
		var content = encodeURIComponent(dragObject.oldContent);
		var time = encodeURIComponent(dragObject.createTime);
		var url = getRootPath()+"/po/personalBox/updateNote.action?id="+dragObject.pkid+"&xPosition="+leftTemp+"&yPosition="+topTemp+"&createTime="+time;

		jQuery.ajax({
			url:url,
			type:'POST',
			dataType:"json",
			data : {"content":dragObject.oldContent},
			success:function(data) {
				if(data){
					
				}else{
					msgBox.tip({
						content:$.i18n.prop("JC_SYS_002"),
						type:"fail"
					});
				}
			},
			error:function() {
				msgBox.tip({
					content:$.i18n.prop("JC_SYS_002"),
					type:"fail"
				});
			}
		});
	}
	dragObject = null;
	//onmouseup事件触发时说明鼠标已经松开，所以设置down变量值为false
	iMouseDown = false;
}

//鼠标移动
function mouseMove(vent){
	//移动的最大宽度
	if(document.getElementById("dom0")==null){
		return;
	}
	var maxWidth = parseInt(document.getElementById("dom0").clientWidth);
	var maxHeight = parseInt(document.getElementById("dom0").clientHeight);
	
	vent=vent||window.event;
	var target   = vent.target || vent.srcElement;
	var mousePos = mouseCoords(vent);
	if(dragObject){
		if(dragObject.style){
			//移动便签不可移出边界外
			if(mousePos.x - mouseOffset.x<=0){
				dragObject.style.left = 0+"px";
			}else if(mousePos.x - mouseOffset.x>=maxWidth-220){
				dragObject.style.left = maxWidth-220 +"px";
			}else{
				dragObject.style.left= mousePos.x - mouseOffset.x+"px";
			}

			if((mousePos.y - mouseOffset.y>=30)&&(mousePos.y - mouseOffset.y<maxHeight-120)){
				dragObject.style.top= mousePos.y - mouseOffset.y+"px";
			}else if(mousePos.y - mouseOffset.y>=maxHeight-120){
				dragObject.style.top = maxHeight-120+"px";
			}else{
				dragObject.style.top = 30+"px";
			}
		}
	}
	if(dragObject) return false;
}

document.onmousedown=mouseDown;
document.onmousemove=mouseMove;
document.onmouseup=mouseUp;

function moveImg(){
	var thimg=document.getElementById('thimg');
	makeDragable(thimg);
}

function DivMouseOut(obj){
	document.getElementById("dom0").ondblclick = add_div;
}
//得到event对象
function getEvent(){
 	if(document.all){
 		return window.event;//如果是ie
 	}
 	
 	func=getEvent.caller;
 	while(func!=null){
 		var arg0=func.arguments[0];
 		if(arg0){
 			if((arg0.constructor==Event || arg0.constructor ==MouseEvent)
 					||(typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)){
 				return arg0;
 			}
 		}
 		func=func.caller;
 	}  	
 	return null;
 }
	
//创建一个新的div
function add_div(){
	var vent=getEvent();
	var maxWidth = parseInt(document.getElementById("dom0").clientWidth);
	var maxHeight = parseInt(document.getElementById("dom0").clientHeight);
	var mousePos = mouseCoords(vent);
    var o=document.createElement("div");
    o.id="dom"+domid;
    o.onmouseover=function(){
 	  makeDragable(this);
 	};
    o.onmouseout=function(){
      DivMouseOut(this);
    };
    o.style.position="absolute";
    var leftPos = parseInt(mousePos.x-10)+180;
    if(leftPos>maxWidth){
        o.style.left=parseInt(mousePos.x-10-200)+"px";
    }else{
    	o.style.left=parseInt(mousePos.x-10)+"px";
    }
    var scrollTop=$("#dom0").position().top;//当前div的滚动条距离顶部的位置，此值为负数
    o.style.top=parseInt(mousePos.y-10-scrollTop)+"px";
    o.style.zIndex=zindex;
//    o.setAttribute("insertDiary",false);
    o.pkid="";
    o.createTime="";
	o.oldContent="";
    o.ondblclick=function(){
    	showTextAres(this);
    };
    zindex=zindex+1;
    o.innerHTML='<div id="domShowDiv'+domid+'" style="overflow:auto;display:none;word-wrap:break-word;" class="apDiv1"><table border="0" cellpadding="0" cellspacing="0" class="tablebq"><tr><td align="right" class="bq1"></td></tr><tr><td align="center" class="bq2"></td></tr><tr><td align="left" class="bq3"></td></tr></table></div><div id="domTextAreaDiv'+domid+'" style="display:;" class="apDiv2"><table border="0" cellpadding="0" cellspacing="0" class="tablebq"><tr><td align="right" class="bq1"></td></tr><tr><td align="center" class="bq3"><textarea class="txt_input1" maxlength="2000"  onKeyUp="return validateMaxLength(this);" onKeyDown="return validateMaxLength(this);" id="domTextArea'+domid+'" style="width:180px;height:200px;" onblur=saveDiv(this) rows="10" cols="20"></textarea><div>您还可以输入<span id="count'+domid+'">2000</span>个文字</div></td></tr></table></div>';
    document.getElementById("dom0").appendChild(o);
    document.getElementById("domTextArea"+domid).focus();
    domid++;
}


//div 每行显示字符数
var intLen = 16;
function saveDiv(obj){
	var result = obj.value;
	result = result.replace(/(^\s*)|(\s*$)/g, ""); 
	var idstr = obj.id.substring(11,obj.id.length);
	//如果没有输入内容
	if(result.length==0){//隐藏弹出div
		var oldContentTemp = obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.oldContent;
 		
 		if(oldContentTemp!=null&&oldContentTemp.length!=0){
 			deleteCarpet(obj);
 		}else{
 			document.getElementById("domTextAreaDiv"+idstr).style.display="none";
 		}
		
		
 	
	}else{//输入内容保存
		var pkid = document.getElementById("dom"+idstr).pkid;
		if(pkid == null || pkid.length==0){
			///以下为处理样式获取x、y轴坐标------------------------------------------------
			var tdLeftTemp=document.getElementById("dom"+idstr);
			var leftTemp = window.getComputedStyle ? window.getComputedStyle(tdLeftTemp, null).left : tdLeftTemp.currentStyle.left;
			leftTemp=leftTemp.substring(0,leftTemp.length-2);//去掉px，只保留数字
			var tdtopTemp=document.getElementById("dom"+idstr);
			var topTemp = window.getComputedStyle ? window.getComputedStyle(tdtopTemp, null).top : tdtopTemp.currentStyle.top;
			topTemp=topTemp.substring(0,topTemp.length-2);
			///以上为处理样式------------------------------------------------
			var resultTemp = encodeURIComponent(result);
			var url = getRootPath()+"/po/personalBox/saveNote.action?xPosition="+leftTemp+"&yPosition="+topTemp;
			//保存
			jQuery.ajax({
				url:url,
				type:'POST',
				dataType:"json",
				data : {"content":result},
				success:function(data) {
					if(data){
						document.getElementById("dom"+idstr).pkid=data.note.id;
						document.getElementById("dom"+idstr).createTime=data.note.createDate;
						document.getElementById("dom"+idstr).oldContent=data.note.content;
						document.getElementById("domShowDiv"+idstr).innerHTML="<table border='0' cellpadding='0' cellspacing='0' class='tablebq'><tr><td align='right' class='bq1'><img src='"+getRootPath()+"/images/po/img/an_gb.gif' onclick='deleteCarpet(this)'></td></tr><tr><td align='center' class='bq2'>"+data.note.createDate+"</td></tr><tr><td align='left' class='bq3' style='width:100%;'><xmp>"+result+"</xmp></td></tr></table>";
					}else{
						msgBox.tip({
							content:$.i18n.prop("JC_SYS_002"),
							type:"fail"
						});
					}
					$("#dataLoad").fadeOut();
				},
				error:function() {
					msgBox.tip({
						content:$.i18n.prop("JC_SYS_002"),
						type:"fail"
					});
				}
			});
		}else{//修改
			var oldContent = document.getElementById("dom"+idstr).oldContent;
			if(result==oldContent){
				document.getElementById("domShowDiv"+idstr).innerHTML="<table border='0' cellpadding='0' cellspacing='0' class='tablebq'><tr><td align='right' class='bq1'><img src='"+getRootPath()+"/images/po/img/an_gb.gif' onclick='deleteCarpet(this)'></td></tr><tr><td align='center' class='bq2'>"+document.getElementById("dom"+idstr).createTime+"</td></tr><tr><td align='left' class='bq3' style='width:100%;'><xmp>"+result+"</xmp></td></tr></table>";
			}else{
				var tdLeftTemp=document.getElementById("dom"+idstr);
				var leftTemp = window.getComputedStyle ? window.getComputedStyle(tdLeftTemp, null).left : tdLeftTemp.currentStyle.left;

				var tdtopTemp=document.getElementById("dom"+idstr);
				var topTemp = window.getComputedStyle ? window.getComputedStyle(tdtopTemp, null).top : tdtopTemp.currentStyle.top;
							
				leftTemp=leftTemp.substring(0,leftTemp.length-2);
				topTemp=topTemp.substring(0,topTemp.length-2);
				
				var pkid = document.getElementById("dom"+idstr).pkid;
				var resultTemp = encodeURIComponent(result);
			    var filePath = getRootPath()+"/po/personalBox/updateNote.action?id="+pkid+"&xPosition="+leftTemp+"&yPosition="+topTemp;
			    var strUrl = filePath;
			 	var textDate;
			 	var pkId;
			 	
				jQuery.ajax({
					url:filePath,
					type:'POST',
					dataType:"json",
					data : {"content":result},
					success:function(data) {
						if(data){
							document.getElementById("dom"+idstr).pkid=data.note.id;
							document.getElementById("dom"+idstr).createTime=data.note.modifyDate;
							document.getElementById("dom"+idstr).oldContent=data.note.content;
							document.getElementById("domShowDiv"+idstr).innerHTML="<table border='0' cellpadding='0' cellspacing='0' class='tablebq'><tr><td align='right' class='bq1'><img src='"+getRootPath()+"/images/po/img/an_gb.gif' onclick='deleteCarpet(this)'></td></tr><tr><td align='center' class='bq2'>"+data.note.modifyDate+"</td></tr><tr><td align='left' class='bq3' style='width:100%;'><xmp>"+result+"</xmp></td></tr></table>";
						}else{
							msgBox.tip({
								content:$.i18n.prop("JC_SYS_002"),
								type:"fail"
							});
						}
						$("#dataLoad").fadeOut();
					},
					error:function() {
						msgBox.tip({
							content:$.i18n.prop("JC_SYS_002"),
							type:"fail"
						});
					}
				});
			}
		}
		document.getElementById("domTextAreaDiv"+idstr).style.display="none";
		document.getElementById("domShowDiv"+idstr).style.display="";
	}
}

function showTextAres(obj){
	var idstr = obj.id.substring(3,obj.id.length);
	
	document.getElementById("domTextAreaDiv"+idstr).style.display="";
	document.getElementById("domTextArea"+idstr).focus();
	document.getElementById("domTextArea"+idstr).style.width="180px";
	//document.getElementById("domTextArea"+idstr).style.height="200px";
	document.getElementById("domShowDiv"+idstr).style.display="none";
	autoTextarea(document.getElementById("domTextArea"+idstr));
}
</script>
</head>

<body id="mainBody">
<section class="scrollable padder jcGOA-section" id="scrollable">
    <input type="hidden" id="modifyDate" name="modifyDate">
	<div id="dom0" style="height:400%;width:100%" ondblclick="add_div()">
				<h2 class="panel-heading clearfix">
					<small class="m-l">
						<i class="fa fa-question-sign text-red m-r-xs"></i>双击生成新的便签
					</small>
				</h2>
		<div style="height:100%;width:100%" id="reportMain" class="tab-pane">
			<br>
			<div style="height:100%;width:100%" align="center">
				&nbsp;&nbsp;<font color="#000080" size="4"><b></b></font>
			</div>
		</div>
	</div>
</section>
</body>
</html>
<script type="text/JavaScript">
/**
 * 初始化
 */
jQuery(function($) {
	initFun();
});

function initFun(){
	var url = getRootPath()+"/po/personalBox/noteManagerList.action";//查询便签
	jQuery.ajax({
		url : url,
		type : 'POST',
		dataType : "json",
		success : function(data) {
			if(data.success == "true"){
				var noteList = data.list;
				for (var i =0;i<noteList.length;i++){
					var o=document.createElement("div");
				    o.id="dom"+domid;
				    o.onmouseover=function(){
				    	makeDragable(this);
				    };
				    o.onmouseout=function(){
				    	DivMouseOut(this);
				    };
				    o.style.position="absolute";
				    o.style.left=noteList[i].xPosition+"px";
				    o.style.top=noteList[i].yPosition+"px";
				    o.style.zIndex=zindex;
				    o.createTime=noteList[i].createDate;
//				    o.setAttribute("insertDiary",false);
				    var contentTemp = (noteList[i].content).replace(/<br\/>/g,'\r\n');
				    var contentTempShow = "<xmp>"+contentTemp+"</xmp>";
				    o.oldContent=contentTemp;
				    var num = 2000 - contentTemp.length;
				    if(num<0){
				    	num=0;	
				    }
				    o.pkid=noteList[i].id;
				    o.ondblclick=function(){
				    	showTextAres(this);//便签双击事件
				    };
				    zindex=zindex+1;
				    o.innerHTML="<div id='domShowDiv"+domid+"' class='apDiv1' style='word-wrap: break-word;'>"+
				    			"<table border='0' cellpadding='0' cellspacing='0' class='tablebq'>"+
									"<tr>"+
										"<td align='right' class='bq1'> "+
											"<img src='"+getRootPath()+"/images/po/img/an_gb.gif' onclick='deleteCarpet(this)'>"+
										"</td>"+
									"</tr>"+
									"<tr>"+
										"<td align='center' class='bq2'>"+noteList[i].createDate+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td align='left' class='bq3' style='width:100%;'>"+contentTempShow+"</td>"+
									"</tr>"+
								"</table>"+
								"</div> "+
								"<div id='domTextAreaDiv"+domid+"' class='apDiv2' style='display:none;'>"+
								"<table border='0' cellpadding='0' cellspacing='0' class='tablebq'>"+
									"<tr>"+
										"<td align='right' class='bq1'></td>"+
									"</tr>"+
									"<tr>"+
										"<td align='center' class='bq3'>"+
											"<textarea  class='txt_input1' maxlength='2000'  onKeyUp='return validateMaxLength(this);' onKeyDown='return validateMaxLength(this);'"+ 
												"id='domTextArea"+domid+"' style='width:160px;height=200px;' onblur=saveDiv(this) rows='' cols='' >"+contentTemp+"</textarea>"+
											"<div>您还可以输入 <span id='count"+domid+"'>"+num+"</span>个文字</div>"+
										"</td>"+
									"</tr> "+
								"</table>"+
								"</div>";
				    $("#dom0").append(o);
				    domid++;
				}
			}else{
				
			}
		},
		error : function() {
			msgBox.tip({
				type:"fail",
				content: "保存失败"
			});
		}
	});
}
</script>