infoColumnSelect = {};

/**初始化所有有栏目*/
infoColumnSelect.initColumn = function(){
	var queryColumnUrl = getRootPath()+"/im/column/initColumn.action";
	$.ajax({ 
		  url: queryColumnUrl, 
		  dataType: 'json', 
		  async:false,
		  data:{'myOrderBy':3},
		  success:function(data) {
				$.each(data.columnList, function(i, o) {
					switch(parseInt(o.columnLevel)){
						case 0: //根栏目
							break;
						case 1: 
							var rootColumn='<li class="active">'+
												'<a href="#" onclick="">'+
													'<i class="fa"></i>'+
													'<span>'+
														o.columnName+
													'</span>'+
													'<div class="nav-tree-btn-wrap" id="button'+o.id+'">'+
														
													'</div>'+
												'</a>'+
												'<ul  id="column-'+o.id+'" class="nav bg">'+
												'</ul>'+
											'</li>';
							$('#firstColumn').append(rootColumn);
							break; 
						default: 
							var defaultColumn =  '<li class="active">'+
													'<a href="#" onclick="">'+
														'<i class="fa"></i>'+
														'<span>'+
															o.columnName+
														'</span>'+
														'<div class="nav-tree-btn-wrap" id="button'+o.id+'">'+
															
														'</div>'+
													'</a>'+
													'<ul  id="column-'+o.id+'" class="nav bg">'+
													'</ul>'+
												'</li>';				
						    $('#column-'+o.columnParentId).append(defaultColumn);
					}
				});
				//删除空的父容器
				$.each($('ul[id^="column-"]'), function(i, o) {
					if($(o).children().size()==0){
							$(o).prev().find('i[class="fa"]').remove();
							$(o).remove();
					}
				});
				
				$('.nav-infomation').hide();
				$('.nav-infomation').show();
			}
	});
};

/**有权限操作的栏目添加name*/
infoColumnSelect.initColumnName = function(){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/im/column/queryColumnTreeByUser.action",
		dataType : "json",
		success : function(data) {
			if (data) {
				if(data.length==0){
					$('#column').empty();
					var returnStr = '<section class="panel m-t-md clearfix" id="column">'+
											'<div class="h500">'+
											'<section class="panel m-t-md" id="prompt_sub">'+
												'<div class="h500">'+
													'<div class="g-z-content img-rounded">'+
														'<img src="./../../images/demoimg/no-staff.png" width="70px;">'+
														'<div class="g-z-text">'+
															'<span class="g-z-span">抱歉！</br>您暂无可操作栏目'+
															'</span><br>'+
														'</div>'+
													'</div>'+
												'</div>'+
											'</section>'+
										'</div>'+
									'</section>';
					$('#infoHeader').after(returnStr);
					return;
				}
				infoColumnSelect.initColumn();
				$.each(data, function(i, o) {
						var buttonStr = '<button class="a-icon i-new i-new1 nav-tree-btn" href="#new-qx" name="allowDeliver" role="button" data-toggle="modal">信息发布</button>';
						$('#button'+o.id).append(buttonStr);
				});
			}
		}
	});
};

infoColumnSelect.columnSkip = function(e){
	var idValue= $(e).parent().attr('id');
	var columnId = idValue.substr(6,idValue.length);
	jQuery.ajax({
		url : getRootPath() + "/im/column/get.action?time="+new Date(),
		type : 'GET',
		data : {id:columnId},
		async : false,
		success : function(data) {
			if(data){
				var flowKey = data.piId;
//				var postData = [];
//				postData.push({'name': 'columnId', 'value': data.id});
//				var jsonStr = JSON.stringify(serializeJson(postData)).replace(/\"/g,"\\\'");
				var url = '/workFlow/processDefinition/toStartProcess.action?processDefinitionKey='+flowKey;
//				loadrightmenu(url,true,{'condition':jsonStr});
				setUrlParameter(data.id);
				loadrightmenu(url);
			}else{
				msgBox.tip({
					content: '栏目已经不存在请刷新重试'
				});
			}
		},
		error : function() {
			msgBox.tip({
				content: '查询失败'
			});
		}
	});
};

/**
 * 初始化方法
 */
jQuery(function($){
	infoColumnSelect.initColumnName();
	$('#column').on('click','button[name="allowDeliver"]',function(){
		infoColumnSelect.columnSkip(this);
    });
	
//	$(document).on('click','#column a',function(e){
//	    var $this=$(e.target),$active;
//	    $this.is('a')||($this=$this.closest('a'));
//	    if($('.nav-vertical').length){return;}
//	    $active=$this.parent().siblings(".active");
//	    $active&&$active.find('> a').toggleClass('active')&&$active.toggleClass('active').find('> ul:visible').slideUp(200);
//	    ($this.hasClass('active')&&$this.next().slideUp(200))||$this.next().slideDown(200);
//	    $this.toggleClass('active').parent().toggleClass('active');
//	    $this.next().is('ul')&&e.preventDefault();
//	    setTimeout(function(){$(document).trigger('updateNav');},300);
//		$('.jcGOA-menu').hide();
//		$('.jcGOA-menu').show();
//	});
});
//@ sourceURL=infoColumnSelect.js
