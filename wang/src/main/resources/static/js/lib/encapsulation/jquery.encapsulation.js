
/**
 * @class 二次封装核心类
 * @version 1.0
 * @author lxx
 * @requires jquery 1.8.3
 *
 */
jQuery(document).ready(function($) {
	$.fn.jc_packaging={
		/**
		* 上传组件二次封装类
		* @requires jquery 1.8.3
 		* @requires jquery.uploadify.js
		*/
		upload : function(options){
			/**
			 * @class
			 * @type {Object}
			 */
			var fun={};//upload 返回的对象可以调用相应的方法
			var defaults = {
				uploadId : 'up_file',//上传组件id
				position : 'pop',//上传组件展示方式 pop 代表弹出 show代表页面直接展示
				downloadaction:'resources/download.action', // 下载请求action	
				uploader:'resourceUpload.action',//上传请求action
				deleteaction:'resources/deleteByIds.action',//删除请求action
				swf: './js/lib/uploadify/uploadify.swf',//swf地址
				uploadtype:'',//上传方式 '' 为相对服务器路径 fpt 为相对fpt路径
				uploadshowview_remould : function(){
				var $myModal = $('<div></div>');
				    $myModal.attr({
				    	id: 'myModal',
				    	'class': 'modal hide fade',
				    	tabindex:'-1',
				    	role:'dialog',
				    	'aria-labelledby':'myModalLabel',
				    	'aria-hidden':'true'
				    });
				    var $modal_header = $('<div class="modal-header"></div>');
				    $myModal.append($modal_header);
				    $modal_header.append('<button id="uploadpageClose" type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>');
				    $modal_header.append('<h3 id="myModalLabel"> <input type="file" name="'+opt.uploadId+'" id="'+opt.uploadId+'" /></h3>');
				    $myModal.append('<div id="modal_body" class="modal-body" style="min-height:400px"></div>');
				    var $myModal_footer = $('<div class="modal-footer"></div>');
				    $myModal.append($myModal_footer);
				    $myModal_footer.append('<button id="upload_service" class="btn" >上传</button>');
				    $myModal_footer.append('<button id="cancel_upload" class="btn" >取消上传</button>');
				    $myModal_footer.append('<button id="stop_upload" class="btn" >停止</button>');
				    $myModal_footer.append('<button id="close_upload" class="btn btn-primary">关闭</button>');  
				    return $myModal;
				}
				
			}
			var opt = $.extend(defaults, options);
			
			/**
			* 上传弹出视图
			*/
			 
			/**
			 * 上传弹视图面事件加载
			 * @return {[type]} [description]
			 */
			var eventall = function(){
			    //防止被组件遮挡而无法点击
				var x=$('#'+opt.uploadId).find("object").css("z-index","2000");
				//Pop上传组件事件监听
				
				$('#upload_service').click(function(event) { // 上传
					fun.uploadqueue();
				});
				$('#cancel_upload').click(function(event) { // 删除上传内容
					fun.cancelqueue();
				});
				$('#close_upload').click(function(event) { // 关闭页面
					$("#myModal").modal('hide');
				});
				$('#stop_upload').click(function(event) { //停止
					fun.stopqueue();
				});
			    $('#myModal').on('hide', function () {
			    	$('#cancel_upload').trigger('click');//清空队列
                })
			}
			//创建上传视图单例模式
		    var SingletonUploadPopView = (function () {
			    var instantiated;
			    function init() {
		 			var tempform=opt.uploadshowview_remould;
				    $('body').append(tempform);
				    eventall();
			        return tempform;
			    } 
			    return {
			        getInstance: function () {
			            if (!instantiated) {
			                instantiated = init();
			            }
			            return instantiated;
			        }
			    };
			})();

			//创建下载内容表单的单例模式
		    var SingletonUploadSubmit = (function () {
			    var instantiated;
			    function init() {
		 			var tempform=$('<form data-upload="upload"></form>');
				  	tempform.attr('action',opt.downloadaction);
				    tempform.attr('method','post');
			        return tempform;
			    } 
			    return {
			        getInstance: function () {
			            if (!instantiated) {
			                instantiated = init();
			            }
			            return instantiated;
			        }
			    };
			})();
			//==============对外方法=========
			
			/**
			 * 上传队列
			 *
			 */
			fun.uploadqueue = function(){
				$('#'+opt.uploadId).uploadify('upload', '*');
			} 
			
			/**
			 * 删除队列
			 *
			 */
			fun.cancelqueue = function(){
				$('#'+opt.uploadId).uploadify('cancel', '*');
			}
			/**
			 * 停止上传队列
			 *
			 */
			fun.stopqueue = function(){
				$('#'+opt.uploadId).uploadify('stop');
			}
			/**
			 * 删除已上传的内容
			 * @param  {String} ids     要删除内容的id集合多个用','分割
			 * @param  {String} paths   要删除内容的地址集合多个用','分割
			 * @param  {Object} options ajax提交参数
			 * 
			 */
			fun.deletefile = function(ids,paths,options){
				var defaults = {
	              url: opt.deleteaction,
	              type: 'post',
	              data:{ids:ids,path_s:paths,uploadtype:opt.uploadtype,time:new Date()},
	              async:false,
	              success: function(data, textStatus, xhr) {
	            	  alert("删除成功！");
	            	 
	              },error:function(){
	            	  alert("删除数据错误！");
	              }
	            }
				jQuery.ajax($.extend(defaults,options));
			}
			/**
			 * 下载方法
			 * @param  {Object} jsonpathandname [文件下载路径以及文件名称的对象]	
			 */
			fun.downloadfile =function(jsonpathandname){
			  var tempform =  SingletonUploadSubmit.getInstance();
			  tempform.empty();
			  var input1 = $("<input type='hidden' name='filepathandname' />")
	          input1.attr('value',JSON.stringify(jsonpathandname));
	          var input2 = $("<input type='hidden' name='uploadtype' />")
	          input2.attr('value',opt.uploadtype);
	          tempform.append(input1);
	          tempform.append(input2);
	          $('body').append(tempform);
	          tempform.submit();
			}
			//初始化上传弹出视图
		    if (opt.position =='pop') {
		   	  	SingletonUploadPopView.getInstance();
		    };
			//初始化uploadify组件
			$("#"+opt.uploadId).uploadify(opt);

			return fun;
		}
//----------------------上传组件二次封装结束--------------------------		
	}
	


	
});