$(document).ready(function(){	
	//初始化校验方法
	$("#planRangeForm").validate({
		ignore: ".ignore",
        rules: {
        	weekRangeId:{
        		required: true
		    },
			monthRangeId:{
				required: true
			},
			yearRangeId:{
				required: true
			}
	    }
	});
});