function initValid(){
//初始化校验方法
	$("#infoDeliverForm").validate({
		ignore: ".ignore",
        rules: {
        	columnId: 
		   {
			    required: true,
			    
		   },
		   infoTitile: 
		   {
			    required: true,
			    maxlength: 80,
			    specialChar: true
		   },
		   infoSubtitle: 
		   {
			    maxlength: 40
		   },
		   infoSumm: 
		   {
			    maxlength: 1000
		   },
		   author: 
		   {
			    maxlength: 20
		   },
		   authorUnit: 
		   {
			    maxlength: 20
		   },
		   sendTime: 
		   {
			    required: true,
			    
		   },
		   effectiveTime2: 
		   {
			   required: function(element) {
			        return !$('#effectiveTime1').is(':checked');
			      },
			   max: 999999999,
			   digits: true
		   },
		   allowReview: 
		   {
			    required: true,
			    maxlength: 1
		   },
		   allowPrint: 
		   {
			    required: true,
			    maxlength: 1
		   },
		   isTop: 
		   {
			    required: true,
			    maxlength: 1
		   }
	    }
	});
};