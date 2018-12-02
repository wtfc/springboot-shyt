/**
 * @title GOA V2.0
 * @description 
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 都业广
 * @version  2014-05-22 13:33
 */
$(document).ready(function(){
	
    //初始化校验方法
	$("#timerTaskForm").validate({
		
		//重写错误信息提示的位置
		errorPlacement: function(error, element) {
			error.insertAfter(element.parent()); 
		},
		
		//验证规则(required:必须输入,maxlength:最大长度,digits:正整数)
        rules: {
        	//任务类型
        	groupName: 
        	{
        		required: true,
        		specialChar: true,
			    maxlength: 50
        	},
        	//任务类名称
        	jobClassName: 
        	{
			    required: true,
			    specialChar: true,
			    maxlength: 255
        	},
        	//任务详情
        	description:
        	{
			    required: false,
			    maxlength: 255
        	},
        	//间隔小时数
        	intervalHours:
        	{
        		required: true,
        		digits:true,
        		maxlength: 9
        	},
        	//间隔分钟数
        	intervalMinutes:
        	{
        		required: true,
        		digits: true,
        		maxlength: 9
        	},
        	//循环次数
        	taskCounts:
        	{
        		digits: true,
        		maxlength: 9 
        	},
        	//周期
        	weekly:
        	{
        		required: true
        	},
        	//周期内的时数
        	perHours:
        	{
        		required: true,
        		digits: true,
        		maxlength: 9
        	},
        	//周期内的分钟数
        	perMinutes:
        	{
        		required: true,
        		digits: true,
        		maxlength: 9
        	},
        	//任务开始时间
        	startAt:
        	{
        		required: true
        	}
        }
	});
});