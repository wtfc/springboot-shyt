var toaFinanceBillView = {};
toaFinanceBillView.oTable == null;
toaFinanceBillView.getBillViewByMonth = function(data) {

	var chart = echarts.init(document.getElementById('billViewByMonth'));

	var billList = data.billList;
	var billYear = new Object(billList[0].billDate.toString()).substring(0, 4);
	var comeMoney = [];
	var billMoney = [];
	var oweMoney = [];
	var monthArray = [ '01', '02', '03', '04', '05', '06', '07', '08', '09',
			'10', '11', '12' ];

	var monthFlag = true;
	if(billList!=null){
		for (var j = 0; j < monthArray.length; j++) {
			monthFlag = true;
			for (var i = 0; i < billList.length && monthFlag; i++) {
				if (monthArray[j] == new Object(billList[i].billDate.toString())
						.substring(5, 7)) {

					comeMoney.push(billList[i].comeMoneyByMonth);
					billMoney.push(billList[i].billMoneyByMonth);
					oweMoney.push(billList[i].oweMoneyByMonth);
					monthFlag = false;

				} else if (i == billList.length - 1) {
					comeMoney.push(0);
					billMoney.push(0);
					oweMoney.push(0);
					monthFlag = false;
				}
			}

		}
	}
	var itemStyle = {
		normal : {
			barBorderRadius : 5,
			label : {
				show : true,
				position : 'outside'
			}
		},
		emphasis : {
			label : {
				position : 'outside'
			},
			barBorderColor : '#fff',
			barBorderWidth : 1,
			shadowBlur : 10,
			shadowOffsetX : 0,
			shadowOffsetY : 0,
			shadowColor : 'rgba(0,0,0,0.5)'
		}
	};

	chart.setOption({
		backgroundColor : '#eee',
		title : {
			text : billYear + '年度财务统计',
			x : 'center',
			padding : 5
		},
		legend : {// 头部标签颜色、位置设置
			inactiveColor : '#abc',
			borderWidth : 1,
			data : [ {
				name : '实际回款金额'
			}, '应收金额', '/n', '欠费金额' ],
			selected : {
			// 'bar': false
			},
			// orient: 'vertical',
			x : 'right',
			y : 'bottom',
			align : 'left',

			tooltip : {
				show : true
			}
		},
		toolbox : {
			top : 25,
			// right: 20,
			feature : {
				magicType : {
					type : [ 'line', 'bar', 'stack', 'tiled' ]
				},
				dataView : {},
				saveAsImage : {
					pixelRatio : 2
				},
			},

			iconStyle : {
				emphasis : {
					textPosition : 'top'
				// textAlign: 'right'
				}
			}
		},
		tooltip : {},
		xAxis : {
			data : monthArray,
			name : '月份',
			silent : false,
			axisTick : {
				alignWithLabel : true
			},
			// axisTick: {
			// show: false
			// },
			axisLine : {
				onZero : true
			},
			splitLine : {
				show : true
			},
			splitArea : {
				show : true
			}
		},
		yAxis : {
			name : '（元）',
			nameLocation : 'end',
			inverse : false,// 控制y轴方向,false:向上，true:向下
			axisTick : {
				show : false
			},
			splitArea : {
				show : false
			}
		},
		series : [ {
			name : '实际回款金额',
			type : 'bar',
			stack : 'comeMoney',
			itemStyle : itemStyle,
			data : comeMoney
		}, {
			name : '应收金额',
			type : 'bar',
			stack : 'billMoney',
			itemStyle : itemStyle,
			data : billMoney
		}, {
			name : '欠费金额',
			type : 'bar',
			stack : 'oweMoney',
			itemStyle : itemStyle,
			data : oweMoney
		} ]
	});

	chart.on('click', function(params) {
		console.log(params);
	});

	window.onresize = chart.resize;
}

toaFinanceBillView.getBillViewByYear = function(data) {

	var chart = echarts.init(document.getElementById('billViewByYear'));

	var billList = data.billYearList;
	var xAxisData = [];
	var comeMoney = [];
	var billMoney = [];
	var oweMoney = [];
	// 视图赋值
	if(billList!=null){
		for (var i = 0; i < billList.length; i++) {
			xAxisData.push(new Object(billList[i].billDate.toString()).substring(0,
					4));
			comeMoney.push(billList[i].comeMoneyByMonth);
			billMoney.push(billList[i].billMoneyByMonth);
			oweMoney.push(billList[i].oweMoneyByMonth);
		}
	}
	
	var itemStyle = {
		normal : {
			barBorderRadius : 5,
			label : {
				show : true,
				position : 'outside'
			}
		},
		emphasis : {
			label : {
				position : 'outside'
			},
			barBorderColor : '#fff',
			barBorderWidth : 1,
			shadowBlur : 10,
			shadowOffsetX : 0,
			shadowOffsetY : 0,
			shadowColor : 'rgba(0,0,0,0.5)'
		}
	};

	chart.setOption({
		backgroundColor : '#eee',
		title : {
			text : '各年度财务统计',
			x : 'center',// 标题居中
			padding : 5
		},
		legend : {// 头部标签颜色、位置设置
			inactiveColor : '#abc',
			borderWidth : 1,
			data : [ {
				name : '实际回款金额'
			}, '应收金额', '/n', '欠费金额' ],
			selected : {
			// 'bar': false
			},
			// orient: 'vertical',//
			x : 'right',
			y : 'bottom',
			align : 'left',

			tooltip : {
				show : true
			}
		},
		toolbox : {
			top : 25,
			// right: 20,
			feature : {
				magicType : {
					type : [ 'line', 'bar', 'stack', 'tiled' ]
				},
				dataView : {},
				saveAsImage : {
					pixelRatio : 2
				},
			},

			iconStyle : {
				emphasis : {
					textPosition : 'top'
				}
			}
		},
		tooltip : {},
		xAxis : {
			data : xAxisData,
			name : '年份',
			silent : false,
			boundaryGap : true,// false:折线从y轴开始，true:折线不从y轴开始
			axisTick : {
				alignWithLabel : true
			},
			// axisLabel: {
			// show: false
			// },
			// axisTick: {
			// show: false
			// },
			axisLine : {
				onZero : true
			},
			splitLine : {
				show : true
			},
			splitArea : {
				show : true
			}
		},
		yAxis : {
			name : '（元）',
			nameLocation : 'end',
			inverse : false,// 控制y轴方向
			// axisLabel: {//y轴标度是否显示
			// show: false
			// },
			axisTick : {// 刻度线是否显示
				show : false
			},
			// splitLine: {//标度线是否显示
			// show: false
			// },
			splitArea : {
				show : false
			}
		},
		series : [ {
			name : '实际回款金额',
			type : 'bar',
			stack : 'comeMoney',
			itemStyle : itemStyle,
			data : comeMoney
		}, {
			name : '应收金额',
			type : 'bar',
			stack : 'billMoney',
			itemStyle : itemStyle,
			data : billMoney
		}, {
			name : '欠费金额',
			type : 'bar',
			stack : 'oweMoney',
			itemStyle : itemStyle,
			data : oweMoney
		} ]
	});

	chart.on('click', function(params) {
		console.log(params);
	});

	window.onresize = chart.resize;
}

toaFinanceBillView.getBillViewByRoom = function(data) {

	var chart = echarts.init(document.getElementById('billViewByRoom'));

	var billList = data.billYearListByRoom;
	var xAxisData = [];
	var billMoney = [];
	var billMoneySum = 0;
	// 视图赋值
	if(billList!=null){
		for (var i = 0; i < billList.length; i++) {
			billMoneySum+=billList[i].billMoneyByMonth;
		}
		for (var i = 0; i < billList.length; i++) {

			//计算所占百分比
			var percent=Math.round(billList[i].billMoneyByMonth / billMoneySum * 10000) / 100.00 + "%";
			
			xAxisData.push({name:billList[i].roomName+(percent),value:billList[i].billMoneyByMonth});

			billMoney.push({name:billList[i].roomName+(percent),value:billList[i].billMoneyByMonth});
			
		}
	}
	
	var itemStyle = {
			emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
	};

	chart.setOption({
		backgroundColor : '#eee',
		title : {
			text : '本年度各机房应收金额占比统计',
			x : 'left',// 标题居中
			padding : 5
		},
		legend : {// 头部标签颜色、位置设置
			inactiveColor : '#abc',
			borderWidth : 1,
			data : xAxisData,
			x : 'right',
			y : 'bottom',
	        orient: 'vertical',
			selected : {
			// 'bar': false
			},
			align : 'left',
			tooltip : {
				show : true
			}
		},
		toolbox : {
			top : 25,
			// right: 20,
			feature : {
				magicType : {
					//type : [ 'line', 'bar', 'stack', 'tiled' ]
				},
				dataView : {},
				saveAsImage : {
					pixelRatio : 2
				},
			},

			iconStyle : {
				emphasis : {
					textPosition : 'top'
				}
			}
		},
		tooltip : {},
		series : [ {
			name : '应收金额',
			type : 'pie',
			stack : 'billMoney',
			itemStyle : itemStyle,
			data : billMoney
		}]
	});

	chart.on('click', function(params) {
		console.log(params);
	});

	window.onresize = chart.resize;
}

toaFinanceBillView.getMonthSumBill = function() {

	var formData = $("#toaFinanceBillQueryForm").serializeArray();
	$.ajax({
		type : "POST",
		url : getRootPath() + "/finance/toaFinanceBill/getMonthSumBill.action",
		data : formData,
		success : function(data) {
			toaFinanceBillView.getBillViewByMonth(data);
			toaFinanceBillView.getBillViewByYear(data);
			toaFinanceBillView.getBillViewByRoom(data);
		}
	});
}

// 重置按钮功能
toaFinanceBillView.queryReset = function() {
	$('#toaFinanceBillQueryForm')[0].reset();
};

$(document).ready(function() {

	$("#queryBill").click(toaFinanceBillView.getMonthSumBill);
	$("#queryReset").click(toaFinanceBillView.queryReset);
	toaFinanceBillView.getMonthSumBill();
});
