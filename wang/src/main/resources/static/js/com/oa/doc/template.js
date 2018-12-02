var BookMark = {
		
		rows:10,//TabNub > 0 ? TabNub : 10,
		subState:false,
	    addTable:null,
		//分页对象
		 oTable : null,
		  setMark : function (id) {
			     
	             var idsArr = $("[name='ids']:checked");
	             if(idsArr.length == 0) {
	             	//alert("请选择元素");
	             	msgBox.tip({
						//type:"success",
						content: $.i18n.prop("JC_WORKFLOW_008")
					});
	             } else {
	             	var mark = idsArr[0];
/*	             	var name = mark['markName'];
	             	var columnName = mark['markColumn'];*/
	             	var name = mark.getAttribute('markName');
	             	var columnName = mark.getAttribute('markColumn');
	             	var obj = WebOffice.getWeboffice();
	             /*	alert(name);
	             	alert(columnName);
	             	alert(obj);*/
	             	try{
						obj.SetFieldValue(columnName,"["+name+"]"  , "::ADDMARK::");
	             	}catch(e) {
	             		msgBox.tip({
	        				//type:"success",
	        				//结束时间不能小于开始时间
	        				content: $.i18n.prop("JC_SYS_054")
	        			});
	             		//alert("请在模板中选择元素位置");
	             	}
					 
	             //	alert(name);
	             //	alert(columnName);
	             }
	     
	     },
		oTableAoColumns : [
			               	{mData: function(source) {
			               			return "<label class='radio inline'><input type='radio' name='ids' markName="+source.name+" markColumn="+source.columnName+" value="+ source.id + ">" + source.name+"</label>";
			               		}
			               	}/*,
			               	{ "mData": "name" }*//*,
			               	{ "mData": "columnName"}*/
			                ],
			                
      //组装后台参数
        oTableFnServerParams : function(aoData){
        	//排序条件
        	getTableParameters(BookMark.oTable, aoData);
        },
		 bookmarkList : function () {
         	//$('#insideOut-list').fadeIn();
		//	 alert(getRootPath()+"/doc/template/manageBookmarkList.action");
			 //alert(BookMark.oTable);
         	if (BookMark.oTable == null) {
         		BookMark.oTable = $('#bookmarksTable').dataTable( {
         			//iDisplayLength: BookMark.rows,//每页显示多少条记录
         			bPaginate: false,
         			sAjaxSource: getRootPath()+"/doc/template/manageBookmarkList.action",
         			fnServerData: oTableRetrieveData,//查询数据回调函数
         			aoColumns: BookMark.oTableAoColumns,//table显示列
         			fnServerParams: function ( aoData ) {//传参
         				BookMark.oTableFnServerParams(aoData);
         			},
         			aaSorting:[],//设置表格默认排序列
         			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
         	        aoColumnDefs: [{bSortable: false, aTargets: [0]}]
         			
         			
         		} );
         	} else {
         		BookMark.oTable.fnDraw();
         	}
         }
};

 