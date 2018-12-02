/**
 * @title GOA V2.0
 * @description 
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 都业广
 * @version  2014-05-22 13:33
 */

var timerTaskEdit = {};
  	
//重复提交标识
timerTaskEdit.subState = false;


/** 保存方法
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTaskEdit.timerTaskEditSubmit = function(){
	
	//二次提交检测
	if (timerTaskEdit.subState){
	    return;
	}else{
		timerTaskEdit.subState = true;
	}
	
	//验证表单数据
	if($("#timerTaskForm").valid()){
		var url;
	  
		//存在数据id时,对数据进行更新
		if($("#id").val().length > 0){
			
			//更新时的请求地址
			url = getRootPath()+"/sys/job/update.action?time="+new Date();
		
		//追加数据
		}else{
			
			//追加时的请求地址
			url = getRootPath()+"/sys/job/save.action?time="+new Date();
		}
		
		//ajax形式提交
		jQuery.ajax({
			url: url,
			//请求方式参数
			type: 'post',
			//传递表单参数
			data: $("#timerTaskForm").serializeArray(),
			success: function(data, textStatus, xhr) {
				//二次提交位复原
				timerTaskEdit.subState = false;
				//getToken();
				$("#token").val(data.token);
				//后台提交处理数据出错时,errorMessage信息的返回值不为空
				if(data.errorMessage!=null){
					msgBox.tip({content: data.errorMessage, type:'fail'});
				}else{
					
					//提示message 保存成功
					msgBox.tip({content: $.i18n.prop("JC_SYS_001"), type:'success'});
					
					//关闭小窗口
					$('#myModal-edit').modal('hide');
					
					//刷新查询页面的list内容
					timerTask.timerTaskList();
				}
			},error:function(){
				//保存失败时提示信息 保存失败
				msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
			}
		});
	} else {
		
		//验证未通过时需要更改二次提交的标志位
		timerTaskEdit.subState = false;
	}
};

/** 处理缓存问题 清空form表单中值
 * @param form:表单的name
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTaskEdit.clearForm = function(form){	
	
	//清空验证信息
	hideErrorMessage();
	   
	//选择form中所有的input元素
	$(':input', form).each(function() {
		//获得tag类型
	    var type = this.type;
	    //获得tag名称
	    var tag = this.tagName.toLowerCase(); // normalize case 
	    //清空text,password,和textarea的值
	    if (type == 'text' || type == 'password' || tag == 'textarea'){
		       this.value = "";
	    }
	});
	   
	//选择所有的select元素
	var SelectArr = $("select");
	//遍历每个元素
	for (var i = 0; i < SelectArr.length; i++) {
		//选中每个select选项中的第一个
		SelectArr[i].options[0].selected = true; 
	}
	   
	//重置名称为weekly的checkbox元素
	$("input[name='weekly']").each(function(){
		//取消选中
		$(this).attr("checked",false);
	});
	   
	//重置画面外观
	timerTaskEdit.cycleTypeChanged();

};

/** 按下新增按钮时,弹出对话框方法
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTaskEdit.createtimerTaskEdit = function(){
	//修改小画面的title
	$("#subTitle").text("新增");
	
	//getToken();
	
	//清空页面上用来保存id的隐藏元素的值
	$("#id").attr("value","");
	
	//调用清空form方法
	timerTaskEdit.clearForm(timerTaskForm);
	$('#myModal-edit').modal('show');
};
		 		
/** 取得按下修改按钮时,弹出对话框所需要的数据
 * @param id:数据与数据库中对应的id
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTaskEdit.get = function(id){
	
	//修改小画面的title
	$("#subTitle").text("编辑");
	
	//getToken();
	
	//ajax提交内容
	jQuery.ajax({
		//请求地址
		url: getRootPath()+"/sys/job/get.action?id="+id+"&time="+new Date(),
		//请求方式
		type: 'post',
		//请求传递参数的数据格式
		dataType: 'json',
		//成功时的回调
		success: function(data, textStatus, xhr) {
			//清空掉form中的内容
            timerTaskEdit.clearForm(timerTaskForm);
            //添加更新后的数据
          	$("#timerTaskForm").fill(data);
          	
          	//针对radio进行重新设置.普通的重置radio方法在第三次时无法正常设定(JQUERY1.9之后的bug).所以需要这种方式.
          	//radio选中天时的处理
          	if(data.cycleSelect == 'day'){
          		//选中第一项
          		$("input[name=cycleSelect]").get(0).checked = true;
          		
          	//radio选中周时的处理
          	}else if(data.cycleSelect == 'week'){
          		//选中第二项
          		$("input[name=cycleSelect]").get(1).checked = true;
          		
          		//针对checkbox进行重新设置
          		if(data.weekly.length>0){
          			//将后台checkbox的数据进行分割(数据格式:1,2,3)
          			var checkValue = data.weekly.split(",");
          			
          			//遍历数组
              		$.each(checkValue,function(i,n){
              			//重新设置checkbox为选中状态
              			$("input[name='weekly']").get(n-1).checked = true;
              		});
          		}
          		
            //radio选中月时的处理
          	}else if(data.cycleSelect == 'month'){
          		//选中第三项
          		$("input[name=cycleSelect]").get(2).checked = true;
          		
          		//针对按月循环时,画面中的radio重新进行设置
          		if(data.monthly == 'monthlyDay'){
          			$("input[name=monthly]").get(0).checked = true;
          		}else if(data.monthly == 'monthlyLastDay'){
          			$("input[name=monthly]").get(1).checked = true;
          		}else if(data.monthly == 'monthlyDayWeek'){
          			$("input[name=monthly]").get(2).checked = true;
          		}
            //radio选中年时的处理
          	}else if(data.cycleSelect == 'year'){
          		//选中第四项
          		$("input[name=cycleSelect]").get(3).checked = true;
          		
          		//针对按年循环时,画面中的radio重新进行设置
          		if(data.yearly == 'yearlyMD'){
          			$("input[name=yearly]").get(0).checked = true;
          		}else if(data.yearly == 'yearlyMonthL'){
          			$("input[name=yearly]").get(1).checked = true;
          		}else if(data.yearly == 'yearlyLW'){
          			$("input[name=yearly]").get(2).checked = true;
          		}
          	}
          	
          	//根据取得的数据重新设置画面外观
          	timerTaskEdit.cycleTypeChanged();
          	
            },error:function(){
            	//错误信息:加载数据错误
            	msgBox.tip({content: $.i18n.prop("JC_SYS_060"), type:'fail'});
            }
	});
	$('#myModal-edit').modal('show');
};



/** 隐藏全部元素
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTaskEdit.allHide = function(){
	
	//间隔时间
	$("#intervalTime").hide();
	
	//任务次数
	$("#taskCount").hide();
	
	//提醒周期
	$("#cycle").hide();
	
	//时间
	$("#perTime").hide();
	
	//周
	$("#week").hide();
	
	//月
	$("#month").hide();
	
	//年
	$("#year").hide();
	
};

/** 循环周期改变时调用的方法
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTaskEdit.cycleTypeChanged = function(){
	
	//全部控件隐藏
	timerTaskEdit.allHide();
	
	/*
	 * 符合条件的显示
	 */
	//固定间隔
	if($("#cycleType").val() == "1"){
		
		//结束时间
		$("#endTime").show();
		
		//间隔时间
		$("#intervalTime").show();
		
		//任务次数
		$("#taskCount").show();
		
	}
	
	//循环周期
	if($("#cycleType").val() == "2"){
		
		//结束时间
		$("#endTime").show();
		
		//显示周期radio
		$("#cycle").show();
		
		//时间
		$("#perTime").show();
		
		timerTaskEdit.cycleChecked();
	}
	
	//一次性
	if($("#cycleType").val() == "3"){
		
		//结束时间
		$("#endTime").hide();
	}
	
};

/** 提醒周期改变时调用的方法
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTaskEdit.cycleChecked = function(){
	
	//全部控件隐藏
	timerTaskEdit.allHide();
	
	//提醒周期
	$("#cycle").show();
	
	var checkedValue = $("input:radio[name='cycleSelect']:checked").val();
	
	//时间
	$("#perTime").show();
	
	if("week" == checkedValue){
		//时间
		$("#week").show();
	}
	
	if("month" == checkedValue){
		//时间
		$("#month").show();
	}
	
	if("year" == checkedValue){
		//时间
		$("#year").show();
	}
};

/** 生成用来选择日和月的select中的值
 * @author 都业广
 * @version  2014-05-22 13:47
 */
timerTaskEdit.generateSelectContent = function(){
	
	//生成31天
	for(var i=1;i<=31;i++){
		$("#monthlyDay").append("<option value='"+i+"'>"+i+"</option>");
		$("#yearlyDay").append("<option value='"+i+"'>"+i+"</option>");
	}
	
	//生成12月
	for(var i=1;i<=12;i++){
		$("#yearlyMonthForDay").append("<option value='"+i+"'>"+i+"</option>");
		$("#yearlyMonthForLast").append("<option value='"+i+"'>"+i+"</option>");
		$("#yearlyMonthForWeek").append("<option value='"+i+"'>"+i+"</option>");
	}
};

/** 页面初始化
 * @author 都业广
 * @version  2014-05-22 13:47
 */
jQuery(function($) 
{
	ie8StylePatch();
	//绑定保存按钮
	$("#timerTaskbtn").click(timerTaskEdit.timerTaskEditSubmit);
	
	//生成用来选择日和月的select中的值
	timerTaskEdit.generateSelectContent();
	
	//绑定循环规则
	$('#cycleType').change(function(){
		//控件隐藏
		timerTaskEdit.cycleTypeChanged();
    });
	
	//radio控件绑定选中事件
	$("input[name='cycleSelect']").bind("click",
			function(){
				timerTaskEdit.cycleChecked();
			});
	
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	//控件隐藏
	timerTaskEdit.cycleTypeChanged();
});	


