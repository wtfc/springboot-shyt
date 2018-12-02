/**
 *  @description 手写相关js
 */

var handWritten = {}
handWritten.signId = "";
/**
 * 初始化函数
 */
handWritten.init = function(){
	var s = ""
	s += "<object id=DWebSignSeal classid='CLSID:77709A87-71F9-41AE-904F-886976F99E3E' style='position:absolute;width:0px;height:0px;left:0px;top:0px;' codebase='"+getRootPath()+"/plugin/WebSign.dll#version=4,5,5,0' >"
	s += "</OBJECT>";
	//document.write(s);
}

handWritten.init();
handWritten.showWritePannel_JG = function(divId) {
	handWritten.WebSign_AddSeal_JG(divId);
}
/**
 * 弹出手写窗口
 */
handWritten.showWritePannel = function(divId){
	var signName = "";
	
	try{ 
		//设置当前印章绑定的表单域
		handWritten.Enc_onclick();
		
		if(document.getElementById("suggestType").value == 1){
			document.all.DWebSignSeal.SetCurrUser("手写人");
			document.all.DWebSignSeal.SetPosition(0,0,divId);
			var strObjectName =$("#signId").val()
			signName = document.all.DWebSignSeal.HandWritePop(0,0,10, handWritten.getWidth(),handWritten.getHeight(),"");
			if(signName.length==0){
				return;
			}
			if(strObjectName!=null&&strObjectName.length>0){
				document.all.DWebSignSeal.DelSeal(strObjectName);
			}
			//锁定位置
			document.DWebSignSeal.LockSealPosition(signName);
			var width = document.DWebSignSeal.GetSealWidth(signName);
			var height = document.DWebSignSeal.GetSealHeight(signName);
			document.DWebSignSeal.MoveSealPosition(signName,20,0,divId);
			//更改div高度
			document.getElementById(divId).style.height = height+80; 
			document.all.DWebSignSeal.ShowWebSeals();
			
			$("#"+divId).find("textarea").css("height",height);
		}else{
			document.all.DWebSignSeal.SetCurrUser("盖章人");
			document.all.DWebSignSeal.RemoteID = "0100018";
			document.all.DWebSignSeal.SetPosition(0,0,divId);
			var strObjectName =$("#signId").val();
			signName = document.all.DWebSignSeal.AddSeal("", "");
			if(signName.length==0){
				return;
			}
			if(strObjectName!=null&&strObjectName.length>0){
				document.all.DWebSignSeal.DelSeal(strObjectName);
			}
			//锁定位置
			document.DWebSignSeal.LockSealPosition(signName);
			var width = document.DWebSignSeal.GetSealWidth(signName);
			var height = document.DWebSignSeal.GetSealHeight(signName);
			document.DWebSignSeal.MoveSealPosition(signName,(document.getElementById(divId).clientWidth - (width + 120)),0,"divId");
			//更改div高度
			document.getElementById(divId).style.height = height+80; 
			document.all.DWebSignSeal.ShowWebSeals();
			
			$("#"+divId).find("textarea").css("height",height);
		}
		if("" == signName){
			 //alert("全屏幕签名失败");
			 return false;
		}
		$("[id^=DDWSign]").css("z-index",1)
		//设置标志位
		$("#signInfoFlag").val("true");
		return signName;
	}catch(e) {
	  alert("控件没有安装，请刷新本页面，控件会自动下载。\r\n或者下载安装程序安装。" +e);
	}
}

/**\
 *
 *金格插件 页面盖章
 */
handWritten.WebSign_AddSeal_JG = function(name) {
	SignatureControl.ServiceUrl = $('#mServerUrl').val()+getRootPath()+"/workflow/suggestjg/ExecuteRun.action?workId="+$('#workId').val();
	SignatureControl.UserName="demo";                         //文件版签章用户
	//alert(name);
	//name = name+"text";
	var workId = $('#workId').val();
	SignatureControl.FieldsList = "myBusinessUrl="+workId;
	if(document.getElementById("suggestType").value == 1){
		//手写
		var index = name.lastIndexOf("_");
		var divId = name.substring(0, index) + "Textarea"+name.substring(index+1);
	//	alert(divId);
	// SignatureControl.DivId = divId;                          
		SignatureControl.Position(0,0);          
		SignatureControl.HandPenWidth = 1;                        //设置、读取手写签名的笔宽
		SignatureControl.HandPenColor = 100;                      //设置、读取手写签名笔颜色
	 	SignatureControl.SetPositionRelativeTag(name,1);        //设置签章位置是相对于哪个标记的什么位置
		//SignatureControl.DivId = name;        //设置签章位置是相对于哪个标记的什么位置
	//SignatureControl.PositionByTagType = 1;                 //设置签章所处位置，1表示中间
	SignatureControl.PositionBySignType = 1;                 //设置签章所处位置，1表示中间
		  //DocForm.SignatureControl.UserName="lyj";                        //文件版签章用户
		var result = SignatureControl.RunHandWrite();                          //执行手写签名
		if(result) {
			var sId = SignatureControl.SIGNATUREID;
			var signs = document.getElementsByName("iHtmlSignature");
			var vItem = signs[signs.length - 1];
			var difPosition = vItem.ScalingSign("","","0.7");
			var difWidth = difPosition.substring(0,difPosition.indexOf(";"));
			var difHeight = difPosition.substring(difPosition.indexOf(";") + 1,difPosition.length);  
			vItem.MovePositionByNoSave(difWidth/2,difHeight/2);
			var defaultHeight = document.getElementById(name).style.height;
			if(difHeight < 40) {
				difHeight = 40;
			}
			if(defaultHeight) {
				defaultHeight = +defaultHeight;
				if(defaultHeight > difHeight) {
					difHeight = defaultHeight/2.5;
				}
			}
			document.getElementById(name).style.height = difHeight*2.5; 
			$("#"+name).find("textarea").css("height",difHeight*2.5);
		}
	} else {
		//盖章
		SignatureControl.Position(0,0);          
		//   SignatureControl.DocumentID = $('#workId').val()//签章位置
		SignatureControl.SaveHistory="False";                    //是否自动保存历史记录,true保存  false不保存  默认值false
	    SignatureControl.SetPositionRelativeTag(name,0);         //设置签章位置是相对于哪个标记的什么位置
		SignatureControl.DivId = name;        //设置签章位置是相对于哪个标记的什么位置
		//SignatureControl.PositionByTagType = 2;                 //设置签章所处位置，1表示中间
		//SignatureControl.PositionBySignType = 2;                 //设置签章所处位置，1表示中间
		// SignatureControl.ValidateCertTime = '1';                 //检测数字证书有效性，安装目前下必须有根证书Root.cer和吊销列表Crl.crl
		//SignatureControl.RunHandWrite();  
		// SignatureControl.AutoSave = false;
	    SignatureControl.ShowSignatureWindow = "0";
	    SignatureControl.PassWord = "";
		var i = SignatureControl.RunSignature();  
		SignatureControl.EnableMove = false;
	}
	var signs = document.getElementsByName("iHtmlSignature");
	for(var i = 0; i < signs.length; i++) {
		//var node = signs[i].cloneNode(true);
		$(signs[i]).css('z-index',0);
		$(signs[i]).appendTo("#scrollable");
	}
}
function WebSign_AddSeal(sealName, sealPostion,signData){
	try{	 		
			//是否已经盖章
			var strObjectName ;
			strObjectName = DWebSignSeal.FindSeal("",0);  
			while(strObjectName  != ""){ 
					if(sealName == strObjectName){
					alert("当前页面已经加盖过印章：【"+sealName+"】请核实");
					return false;
				}
				strObjectName = DWebSignSeal.FindSeal(strObjectName,0);
			}
			 
			//设置当前印章绑定的表单域
			Enc_onclick(signData);
			//设置盖章人，可以是OA的用户名
		    document.all.DWebSignSeal.SetCurrUser("盖章人");
			//document.all.DWebSignSeal.SealSetID = "001";
			//公章否？ 1:公章 0：私章 
			//document.all.DWebSignSeal.CurrSealType = 0;
			//网络版的设置服务器路径地址 如果不设置，系统会自动到注册表中读取。
			//document.all.DWebSignSeal.HttpAddress = "127.0.0.1:1127";
			//document.all.DWebSignSeal.HttpAddress = "http://192.168.1.11:80/inc/seal_interface/";
			//网络版的唯一页面ID ，SessionID
			document.all.DWebSignSeal.RemoteID = "0100018";
			//附加信息
			//document.all.DWebSignSeal.AppendTipInfo = "IP:192.168.0.11\r\nMAC:00-00-00-11-02";
			//设置盖章时间，可以有服务器传过来
	 		//document.all.SetCurrTime("2006-02-07 11:11:11");
			//设置当前印章的位置,相对于sealPostion1 (<div id="sealPostion1"> </div> 也可以是 td) 的位置相左偏移50px,向上偏移50px
			//这样就可以很好的固定印章的位置
			//document.all.DWebSignSeal.SetPosition(50,-50,"sealPostion1");
			document.all.DWebSignSeal.SetPosition(0,0,sealPostion);
	    //调用盖章的接口
			if("" == document.all.DWebSignSeal.AddSeal("", sealName)){
				 alert("盖章失败");
				 return false;
			}
	}catch(e) {
	  alert("控件没有安装，请刷新本页面，控件会自动下载。\r\n或者下载安装程序安装。" +e);
	}
}	

/**
 * 获取意见域对应值
 */
handWritten.aa = function(){
}

/***********************************************
说明：
    Enc_onclick 主要设置绑定的表单域。
    WebSign的SetSignData接口支持两种绑定数据方式：
    1.字符串数据
    2.表单域
    一旦数据发生改变，WebSign会自动校验，并提示修改。
***********************************************/	
handWritten.Enc_onclick = function(tex_name) {		
	try{
		//清空原绑定内容	
		document.all.DWebSignSeal.SetSignData("-");		
		// str为待绑定的字符串数据
		//var str = "";
		 //设置绑定的表单域
		 //来文单位
		 document.all.DWebSignSeal.SetSignData("+LIST:laiwendanwei;");
		 //来文日期
		 document.all.DWebSignSeal.SetSignData("+LIST:laiwenDate;");
		 //事由
		 document.all.DWebSignSeal.SetSignData("+LIST:shiyou;");
		 //时间要求
		 document.all.DWebSignSeal.SetSignData("+LIST:time;");
		  //意见
		 document.all.DWebSignSeal.SetSignData("+LIST:"+tex_name+";");
		 
		/*根据表单域内容自己组织绑定内容,当前例子仅仅做与表单域绑定
		如果绑定字符串数据,需要做如下调用
			document.all.DWebSignSeal.SetSignData("+DATA:"+str);		
		*/
	}catch(e) {
		alert("控件没有安装，请刷新本页面，控件会自动下载。\r\n或者下载安装程序安装。" +e);
	}
}

handWritten.getWidth = function(){
	return document.documentElement.clientWidth *0.8 > 800 ? 800 : document.documentElement.clientWidth *0.8;
}
handWritten.getHeight = function(){
	return document.documentElement.clientHeight*0.8 > 800 ? 800 : document.documentElement.clientHeight *0.8;
}

/**
 * 获得签章接口
 */
handWritten.getSuggestWrite = function(workId){
	var url = getRootPath()+"/workFlow/form/getSuggestWrite.action";
	var ajaxData = {
			workId:workId
	}
	$.ajax({
		url:url,
		data:ajaxData,
		async:false,
		success:function(data){
			 if(!isChrome()&&!isFF()&&document.all.DWebSignSeal!=null){
				 var signInfo = data.signInfo;
				 if(signInfo!=null&&signInfo.length>0){
					 document.all.DWebSignSeal.SetStoreData(signInfo);
					 document.all.DWebSignSeal.ShowWebSeals();
					 $("[workflowSuggest=true]>div").each(function(index,item){
						var objItem = $(this);
						var signId = objItem.attr("signId");
						var px = document.all.DWebSignSeal.GetSealPosX (signId);
						var py = document.all.DWebSignSeal.GetSealPosY (signId)
						var swidth = document.all.DWebSignSeal.GetSealWidth (signId);
						var divWidth = objItem.actual("width");
						if(px+swidth>divWidth){
							document.all.DWebSignSeal.MoveSealPosition(signId,divWidth-swidth,py,objItem.attr("id"));
						}
					 });
					 //document.all.DWebSignSeal.ShowWebSeals();
				 }
			}
		}
	});
}
