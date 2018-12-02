<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<style type="text/css">
.b1,.b2 {
    font-family: "Microsoft Yahei";
	font-size: 18px;
	color: #ffffff;
	background-color: #60aae9;
	border: 0 none;
	height: 46px;
	width: 140px !important;
	float: left;
}
.b2 {
    float: right;
	background-color: #999999;
} 
.jxo, .jxn {
	font-family: "Microsoft Yahei";
	font-size: 24px;
	color: #60aae9;
	background-color: #ffffff;
	border: 1px solid #cccccc;
	height: 44px;
	width: 52px !important;
}
.jxn {
	color: #666666;
}
.wxo, .wxn {
	font-family: "Microsoft Yahei";
	font-size: 24px;
	color: #60aae9;
	background-color: #ffffff;
	border: 1px solid #cccccc;
	height: 44px;
	width: 52px !important;
}
.wxn {
	color: #666666;
}
.jor {
    font-family: "Microsoft Yahei";
	font-size: 36px;
	color: #e96060;
	height: 206px;
	width: 52px !important;
	background-color: #ebebeb;
	border: 1px solid #B2B2B2;
}
.wor {
    font-family: "Microsoft Yahei";
	font-size: 36px;
	color: #e96060;
	height: 206px;
	width: 52px !important;
	background-color: #ebebeb;
	border: 1px solid #B2B2B2;
}
.calculator{
    width: 332px;
    margin: 0 auto;
    padding: 10px 0;
    border:1px solid #B2D0EA;
 }
.calculator table{
    border-collapse: separate;
    border-spacing: 10px;
    width: 320px;
}
.calculator table.nb{
    padding: 5px;
    border-spacing: 0;
}
.calculator table td{
    padding:0;
}
.calculator table td.text-wrap{
    padding: 20px 10px;
}
.calculator table td.text-wrap a{
	font-family: "Microsoft Yahei";
	font-size: 14px;
	color: #0063a6;
    float: right;
    text-decoration: underline;
}
.calculator-text {
    display: block;
	border-left: 4px solid #60aae9;
	width: 0;
	height: 46px;
	margin-bottom: 10px;
}
.calculator-text input {
	font-family: "Microsoft Yahei";
	font-size: 24px;
	border: 1px solid #cccccc;
	border-left: 0 none;
	padding-left: 10px;
    padding-top:  12px;
	width: 296px;
	height: 46px;
	margin-left: 0;
}
.calculator2 {
	width: 830px;
	margin: 0 auto;
	padding: 10px 0;
}
.calculator2 table td {
	padding: 0;
	height: 45px;
}
.calculator2 table td.func{
    padding:7px;
    background: #e8edf1;
    margin-left: 1px;
    border-left:1px solid #fff;
    box-sizing:border-box;
} 
.calculator2 table td.func input{
    background: #fff;
    width:100%;
    margin:0;  
}
</style>

<script language="javascript">
var endNumber=true
var mem=0
var carry=10
var hexnum="0123456789abcdef"
var angle="d"
var stack=""
var level="0"
var layer=0


//万能数字键

function inputkey(key)
{
	var index=key.charCodeAt(0);
	if ((carry==2 && (index==48 || index==49))
	 || (carry==8 && index>=48 && index<=55)
	 || (carry==10 && (index>=48 && index<=57 || index==46))
	 || (carry==16 && ((index>=48 && index<=57) || (index>=97 && index<=102))))
	if(endNumber)
	{
		endNumber=false
		document.Wcalculate.display.value = key
	}
	else if(document.Wcalculate.display.value == null || document.Wcalculate.display.value == "0")
		document.Wcalculate.display.value = key
	else
		document.Wcalculate.display.value += key
}
//简易数字键

function inputkeyJ(key)
{
	var index=key.charCodeAt(0);
	if ((carry==2 && (index==48 || index==49))
	 || (carry==8 && index>=48 && index<=55)
	 || (carry==10 && (index>=48 && index<=57 || index==46))
	 || (carry==16 && ((index>=48 && index<=57) || (index>=97 && index<=102))))
	if(endNumber)
	{
		endNumber=false
		document.Jcalculate.displayJ.value = key
	}
	else if(document.Jcalculate.displayJ.value == null || document.Jcalculate.displayJ.value == "0")
		document.Jcalculate.displayJ.value = key
	else
		document.Jcalculate.displayJ.value += key
}
//万能计算器除
function changeSign()
{
    if (document.Wcalculate.display.value!="0")
    	if(document.Wcalculate.display.value.substr(0,1) == "-")
        	document.Wcalculate.display.value = document.Wcalculate.display.value.substr(1)
    	else
        	document.Wcalculate.display.value = "-" + document.Wcalculate.display.value
}
//简易计算器除
function changeSignJ()
{
    if (document.Jcalculate.displayJ.value!="0")
    	if(document.Jcalculate.displayJ.value.substr(0,1) == "-")
        	document.Jcalculate.displayJ.value = document.Jcalculate.displayJ.value.substr(1)
    	else
        	document.Jcalculate.displayJ.value = "-" + document.Jcalculate.displayJ.value
}

//万能函数键

function inputfunction(fun,shiftfun)
{
	endNumber=true
	if (document.Wcalculate.shiftf.checked)
		document.Wcalculate.display.value=decto(funWcalculate(shiftfun,(todec(document.Wcalculate.display.value,carry))),carry)
	else
		document.Wcalculate.display.value=decto(funWcalculate(fun,(todec(document.Wcalculate.display.value,carry))),carry)
	document.Wcalculate.shiftf.checked=false
	document.Wcalculate.hypf.checked=false	
	inputshift()
}
//简易函数键
function inputfunctionJ(fun,shiftfun)
{
	endNumber=true
	if (document.Jcalculate.shiftf.checked)
		document.Jcalculate.display.value=decto(funJcalculate(shiftfun,(todec(document.Wcalculate.display.value,carry))),carry)
	else
		document.Jcalculate.display.value=decto(funJcalculate(fun,(todec(document.Wcalculate.display.value,carry))),carry)
	document.Wcalculate.shiftf.checked=false
	document.Wcalculate.hypf.checked=false	
	inputshift()
}
function inputtrig(trig,arctrig,hyp,archyp)
{
	if (document.Wcalculate.hypf.checked)
		inputfunction(hyp,archyp)
	else
		inputfunction(trig,arctrig)
}


//万能运算符

function operation(join,newlevel)
{
	endNumber=true
	var temp=stack.substr(stack.lastIndexOf("(")+1)+document.Wcalculate.display.value
	while (newlevel!=0 && (newlevel<=(level.charAt(level.length-1))))
	{
		temp=parse(temp)
		level=level.slice(0,-1)
	}
	if (temp.match(/^(.*\d[\+\-\*\/\%\^\&\|x])?([+-]?[0-9a-f\.]+)$/))
		document.Wcalculate.display.value=RegExp.$2
	stack=stack.substr(0,stack.lastIndexOf("(")+1)+temp+join
	document.Wcalculate.operator.value=" "+join+" "
	level=level+newlevel
	
}

//简易运算符

function operationJ(join,newlevel)
{
	endNumber=true
	var temp=stack.substr(stack.lastIndexOf("(")+1)+document.Jcalculate.displayJ.value
	while (newlevel!=0 && (newlevel<=(level.charAt(level.length-1))))
	{
		temp=parse(temp)
		level=level.slice(0,-1)
	}
	if (temp.match(/^(.*\d[\+\-\*\/\%\^\&\|x])?([+-]?[0-9a-f\.]+)$/))
		document.Jcalculate.displayJ.value=RegExp.$2
	stack=stack.substr(0,stack.lastIndexOf("(")+1)+temp+join
	document.Jcalculate.operatorJ.value=" "+join+" "
	level=level+newlevel
	
}
//括号

function addbracket()
{
	endNumber=true
	document.Wcalculate.display.value=0
	stack=stack+"("
	document.Wcalculate.operator.value="   "
	level=level+0
	
	layer+=1
	document.Wcalculate.bracket.value="(="+layer
}

function disbracket()
{
	endNumber=true
	var temp=stack.substr(stack.lastIndexOf("(")+1)+document.Wcalculate.display.value
	while ((level.charAt(level.length-1))>0)
	{
		temp=parse(temp)
		level=level.slice(0,-1)
	}
	
	document.Wcalculate.display.value=temp
	stack=stack.substr(0,stack.lastIndexOf("("))
	document.Wcalculate.operator.value="   "
	level=level.slice(0,-1)

	layer-=1
	if (layer>0)
		document.Wcalculate.bracket.value="(="+layer
	else
		document.Wcalculate.bracket.value=""
}
function disbracketJ()
{
	endNumber=true
	var temp=stack.substr(stack.lastIndexOf("(")+1)+document.Jcalculate.displayJ.value
	while ((level.charAt(level.length-1))>0)
	{
		temp=parse(temp)
		level=level.slice(0,-1)
	}
	
	document.Jcalculate.display.value=temp
	stack=stack.substr(0,stack.lastIndexOf("("))
	document.Jcalculate.operatorJ.value=""
	level=level.slice(0,-1)

	layer-=1
	if (layer>0)
		document.Jcalculate.bracketJ.value="(="+layer
	else
		document.Jcalculate.bracketJ.value=""
}

//万能计算器等号

function result()
{
	endNumber=true
	while (layer>0)
		disbracket()
	var temp=stack+document.Wcalculate.display.value
	while ((level.charAt(level.length-1))>0)
	{
		temp=parse(temp)
		level=level.slice(0,-1)
	}

	document.Wcalculate.display.value=temp
	document.Wcalculate.bracket.value=""
	document.Wcalculate.operator.value=""
	stack=""
	level="0"
}
//简易计算器等号

function resultJ()
{
	endNumber=true
	 while (layer>0)
		disbracketJ()
	var temp=stack+document.Jcalculate.displayJ.value
	while ((level.charAt(level.length-1))>0)
	{
		temp=parse(temp)
		level=level.slice(0,-1)
	}

	document.Jcalculate.displayJ.value=temp
	document.Jcalculate.bracketJ.value=""
	document.Jcalculate.operatorJ.value=""
	stack=""
	level="0"
}

//万能修改键

function backspace()
{
	if (!endNumber)
	{
		if(document.Wcalculate.display.value.length>1)
			document.Wcalculate.display.value=document.Wcalculate.display.value.substring(0,document.Wcalculate.display.value.length - 1)
		else
			document.Wcalculate.display.value=0
	}
}
//简易修改键

function backspaceJ()
{
	if (!endNumber)
	{
		if(document.Jcalculate.displayJ.value.length>1)
			document.Jcalculate.displayJ.value=document.Jcalculate.displayJ.value.substring(0,document.Jcalculate.displayJ.value.length - 1)
		else
			document.Jcalculate.displayJ.value=0
	}
}
function clearall()
{
	document.Wcalculate.display.value=0
	endNumber=true
	stack=""
	level="0"
	layer=""
	document.Wcalculate.operator.value=""
	document.Wcalculate.bracket.value=""
}


//万能转换键

function inputChangCarry(newcarry)
{
	endNumber=true
	document.Wcalculate.display.value=(decto(todec(document.Wcalculate.display.value,carry),newcarry))
	carry=newcarry

	document.Wcalculate.sin.disabled=(carry!=10)
	document.Wcalculate.cos.disabled=(carry!=10)
	document.Wcalculate.tan.disabled=(carry!=10)
	document.Wcalculate.bt.disabled=(carry!=10)
	document.Wcalculate.pi.disabled=(carry!=10)
	document.Wcalculate.e.disabled=(carry!=10)
	document.Wcalculate.kp.disabled=(carry!=10)
				
	document.Wcalculate.k2.disabled=(carry<=2)
	document.Wcalculate.k3.disabled=(carry<=2)
	document.Wcalculate.k4.disabled=(carry<=2)
	document.Wcalculate.k5.disabled=(carry<=2)
	document.Wcalculate.k6.disabled=(carry<=2)
	document.Wcalculate.k7.disabled=(carry<=2)
	document.Wcalculate.k8.disabled=(carry<=8)
	document.Wcalculate.k9.disabled=(carry<=8)
	document.Wcalculate.ka.disabled=(carry<=10)
	document.Wcalculate.kb.disabled=(carry<=10)
	document.Wcalculate.kc.disabled=(carry<=10)
	document.Wcalculate.kd.disabled=(carry<=10)
	document.Wcalculate.ke.disabled=(carry<=10)
	document.Wcalculate.kf.disabled=(carry<=10)

	
	
}
//简易转换键

function inputChangCarryJ(newcarry)
{
	endNumber=true
	document.Jcalculate.displayJ.value=(decto(todec(document.Jcalculate.displayJ.value,carry),newcarry))
	carry=newcarry
				
	document.Jcalculate.k2.disabled=(carry<=2)
	document.jcalculate.k3.disabled=(carry<=2)
	document.Jcalculate.k4.disabled=(carry<=2)
	document.Jcalculate.k5.disabled=(carry<=2)
	document.Jcalculate.k6.disabled=(carry<=2)
	document.Jcalculate.k7.disabled=(carry<=2)
	document.Jcalculate.k8.disabled=(carry<=8)
	document.Jcalculate.k9.disabled=(carry<=8)
	document.Jcalculate.ka.disabled=(carry<=10)
	document.Jcalculate.kb.disabled=(carry<=10)
	document.Jcalculate.kc.disabled=(carry<=10)
	document.Jcalculate.kd.disabled=(carry<=10)
	document.Jcalculate.ke.disabled=(carry<=10)
	document.Jcalculate.kf.disabled=(carry<=10)

}

function inputChangAngle(angletype)
{
	endNumber=true
	angle=angletype
	if (angle=="d")
		document.Wcalculate.display.value=radiansToDegress(document.Wcalculate.display.value)
	else
		document.Wcalculate.display.value=degressToRadians(document.Wcalculate.display.value)
	endNumber=true
}

function inputshift()
{
	if (document.Wcalculate.shiftf.checked)
	{
		document.Wcalculate.bt.value="deg "
		document.Wcalculate.ln.value="exp "
		document.Wcalculate.log.value="expd"
		
		if (document.Wcalculate.hypf.checked)
		{
			document.Wcalculate.sin.value="ahs "
			document.Wcalculate.cos.value="ahc "
			document.Wcalculate.tan.value="aht "
		}
		else
		{
			document.Wcalculate.sin.value="asin"
			document.Wcalculate.cos.value="acos"
			document.Wcalculate.tan.value="atan"
		}
		
		document.Wcalculate.sqr.value="x^.5"
		document.Wcalculate.cube.value="x^.3"
		
		document.Wcalculate.floor.value="小数"
	}
	else
	{
		document.Wcalculate.bt.value="d.ms"
		document.Wcalculate.ln.value=" ln "
		document.Wcalculate.log.value="log "

		if (document.Wcalculate.hypf.checked)
		{
			document.Wcalculate.sin.value="hsin"
			document.Wcalculate.cos.value="hcos"
			document.Wcalculate.tan.value="htan"
		}
		else
		{
			document.Wcalculate.sin.value="sin "
			document.Wcalculate.cos.value="cos "
			document.Wcalculate.tan.value="tan "
		}
		
		document.Wcalculate.sqr.value="x^2 "
		document.Wcalculate.cube.value="x^3 "
		
		document.Wcalculate.floor.value="取整"
	}

}
//存储器部分

function clearmemory()
{
	mem=0
	document.Wcalculate.memory.value="   "
}

function getmemory()
{
	endNumber=true
	document.Wcalculate.display.value=decto(mem,carry)
}

function putmemory()
{
	endNumber=true
	if (document.Wcalculate.display.value!=0)
	{
		mem=todec(document.Wcalculate.display.value,carry)
		document.Wcalculate.memory.value=" M "
	}
	else
		document.Wcalculate.memory.value="   "
}

function addmemory()
{
	endNumber=true
	mem=parseFloat(mem)+parseFloat(todec(document.Wcalculate.display.value,carry))
	if (mem==0)
		document.Wcalculate.memory.value="   "
	else
		document.Wcalculate.memory.value=" M "
}

function multimemory()
{
	endNumber=true
	mem=parseFloat(mem)*parseFloat(todec(document.Wcalculate.display.value,carry))
	if (mem==0)
		document.Wcalculate.memory.value="   "
	else
		document.Wcalculate.memory.value=" M "
}

//十进制转换

function todec(num,oldcarry)
{
	if (oldcarry==10 || num==0) return(num)
	var neg=(num.charAt(0)=="-")
	if (neg) num=num.substr(1)
	var newnum=0
	for (var index=1;index<=num.length;index++)
		newnum=newnum*oldcarry+hexnum.indexOf(num.charAt(index-1))
	if (neg)
		newnum=-newnum
	return(newnum)
}

function decto(num,newcarry)
{
	var neg=(num<0)
	if (newcarry==10 || num==0) return(num)
	num=""+Math.abs(num)
	var newnum=""
	while (num!=0)
	{
		newnum=hexnum.charAt(num%newcarry)+newnum
		num=Math.floor(num/newcarry)
	}
	if (neg)
		newnum="-"+newnum
	return(newnum)
}

//表达式解析

function parse(string)
{
	if (string.match(/^(.*\d[\+\-\*\/\%\^\&\|x\<])?([+-]?[0-9a-f\.]+)([\+\-\*\/\%\^\&\|x\<])([+-]?[0-9a-f\.]+)$/))
		return(RegExp.$1+cypher(RegExp.$2,RegExp.$3,RegExp.$4))
	else
		return(string)
}

//数学运算和位运算

function cypher(left,join,right)
{
	left=todec(left,carry)
	right=todec(right,carry)
	if (join=="+")
		return(decto(parseFloat(left)+parseFloat(right),carry))
	if (join=="-")
		return(decto(left-right,carry))
	if (join=="*")
		return(decto(left*right,carry))
	if (join=="/" && right!=0)
		return(decto(left/right,carry))
	if (join=="%")
		return(decto(left%right,carry))
	if (join=="&")
		return(decto(left&right,carry))
	if (join=="|")
		return(decto(left|right,carry))
	if (join=="^")
		return(decto(Math.pow(left,right),carry))
	if (join=="x")
		return(decto(left^right,carry))
	if (join=="<")
		return(decto(left<<right,carry))
	msgBox.info({
			type: "fale",
			content: "除数不能为零"
		});
	return(left)
}

//函数计算

function funWcalculate(fun,num)
{
	with(Math)
	{
		if (fun=="pi")
			return(PI)
		if (fun=="e")
			return(E)

		if (fun=="abs")
			return(abs(num))
		if (fun=="ceil")
			return(ceil(num))
		if (fun=="round")
			return(round(num))

		if (fun=="floor")
			return(floor(num))
		if (fun=="deci")
			return(num-floor(num))


		if (fun=="ln" && num>0)
			return(log(num))
		if (fun=="exp")
			return(exp(num))
		if (fun=="log" && num>0)
			return(log(num)*LOG10E)
		if (fun=="expdec")
			return(pow(10,num))

		
		if (fun=="cube")
			return(num*num*num)
		if (fun=="cubt")
			return(pow(num,1/3))
		if (fun=="sqr")
			return(num*num)
		if (fun=="sqrt" && num>=0)
			return(sqrt(num))

		if (fun=="!")
			return(factorial(num))

		if (fun=="recip" && num!=0)
			return(1/num)
		
		if (fun=="dms")
			return(dms(num))
		if (fun=="deg")
			return(deg(num))

		if (fun=="~")
			return(~num)
	
		if (angle=="d")
		{
			if (fun=="sin")
				return(sin(degressToRadians(num)))
			if (fun=="cos")
				return(cos(degressToRadians(num)))
			if (fun=="tan")
				return(tan(degressToRadians(num)))

			if (fun=="arcsin" && abs(num)<=1)
				return(radiansToDegress(asin(num)))
			if (fun=="arccos" && abs(num)<=1)
				return(radiansToDegress(acos(num)))
			if (fun=="arctan")
				return(radiansToDegress(atan(num)))
		}
		else
		{
			if (fun=="sin")
				return(sin(num))
			if (fun=="cos")
				return(cos(num))
			if (fun=="tan")
				return(tan(num))

			if (fun=="arcsin" && abs(num)<=1)
				return(asin(num))
			if (fun=="arccos" && abs(num)<=1)
				return(acos(num))
			if (fun=="arctan")
				return(atan(num))
		}
	
		if (fun=="hypsin")
			return((exp(num)-exp(0-num))*0.5)
		if (fun=="hypcos")
			return((exp(num)+exp(-num))*0.5)
		if (fun=="hyptan")
			return((exp(num)-exp(-num))/(exp(num)+exp(-num)))

		if (fun=="ahypsin" | fun=="hypcos" | fun=="hyptan")
		{
			msgBox.tip({
				type: "fale",
				content: "对不起,公式还没有查到!"
			});
			return(num)
		}
		msgBox.tip({
			type: "fale",
			content: "超出函数定义范围"
		});
		return(num)
	}
}
//简易函数计算
function funJcalculate(fun,num)
{
	with(Math)
	{
		if (fun=="floor")
			return(floor(num))
		if (fun=="deci")
			return(num-floor(num))
		if (fun=="~")
			return(~num)
		msgBox.tip({
			type: "fale",
			content: "超出函数定义范围"
		});	
		return(num)
	}
}

function factorial(n)
{
	n=Math.abs(parseInt(n))
	var fac=1
	for (;n>0;n-=1)
		fac*=n
	return(fac)
}

function dms(n)
{
	var neg=(n<0)
	with(Math)
	{	
		n=abs(n)
		var d=floor(n)
		var m=floor(60*(n-d))
		var s=(n-d)*60-m
	}
	var dms=d+m/100+s*0.006
	if (neg) 
		dms=-dms


	return(dms)
}

function deg(n)
{
	var neg=(n<0)
	with(Math)
	{
		n=abs(n)
		var d=floor(n)
		var m=floor((n-d)*100)
		var s=(n-d)*100-m
	}
	var deg=d+m/60+s/36
	if (neg) 
		deg=-deg
	return(deg)
}

function degressToRadians(degress)
{
	return(degress*Math.PI/180)
}

function radiansToDegress(radians)
{
	return(radians*180/Math.PI)
}

//界面
</script>
<section class="jcGOA-wrap">
<section class="scrollable padder" id="scrollable">
	<header class="con-header pull-in">
		<div class="con-heading fl" id="navigationMenu">
			<h1></h1>
			<div class="crumbs"></div>
		</div>
	</header>
	<section class="tabs-wrap m-t-md">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#linkman-1" data-toggle="tab" data-id="externalContacts">简易计算器</a></li>
			<li><a href="#linkman-2" data-toggle="tab" data-id="internalContacts">万能数学计算器</a></li>
		</ul>
	</section>
	<section class="tab-content">
		<section class="panel tab-content search-shrinkage">
			<!--------------------------------- 简易计算器--------------------------------------------------------------------------->
	<div class="tab-pane active fade in" id="linkman-1">
		<section class="panel clearfix padder-v m-t-md">
			<div class="calculator">
				<form name="Jcalculate">
					<input name="operatorJ" value="" type="hidden">
					<input name="bracketJ" value="" type="hidden">
					<table class="nb">
						<tbody>
							<tr>
								<td class="text-wrap">
									<span class="calculator-text">
										<input type="text" name="displayJ" value="0" readonly="" size="40">
									</span>
								</td>
							</tr>
							<tr>
								<td>
									<table class="calculator-clear">
										<tbody>
											<tr>
												<td>
													<input name="button2" type="button" class="b1" onclick="backspaceJ()" value=" 退格 ">
													<input name="button2" type="button" class="b2" onclick="document.Jcalculate.displayJ.value = 0 "value=" 清屏 ">
												</td>
											</tr>
										</tbody>
									</table>
									<table class="calculator-key">
										<tbody>
											<tr>
												<td>
													<input name="k7" type="button" value=" 7 " onclick="inputkeyJ('7')" class="jxn">
												</td>
												<td>
													<input name="k8" type="button" class="jxn" value=" 8 " onclick="inputkeyJ('8')">
												</td>
												<td>
													<input name="k9" type="button" class="jxn" value=" 9 " onclick="inputkeyJ('9')">
												</td>
												<td>
													<input name="button" type="button" class="jxo" onclick="operationJ('/',6)" value=" ÷ ">
												</td>
												<td rowspan="4">
													<input name="button" type="button" class="jor" onclick="resultJ()" value="＝">
												</td>
											</tr>
											<tr>
												<td>
													<input name="k4" type="button" class="jxn" value=" 4 " onclick="inputkeyJ('4')">
												</td>
												<td>
													<input name="k5" type="button" class="jxn" value=" 5 " onclick="inputkeyJ('5')">
												</td>
												<td>
													<input name="k6" type="button" class="jxn" value=" 6 " onclick="inputkeyJ('6')">
												</td>
												<td>
													<input name="button" type="button" class="jxo" onclick="operationJ('*',6)" value=" × ">
												</td>
											</tr>
											<tr>
												<td>
													<input name="button" type="button" class="jxn" onclick="inputkeyJ('1')" value=" 1 ">
												</td>
												<td>
													<input name="k2" type="button" value=" 2 " onclick="inputkeyJ('2')" class="jxn">
												</td>
												<td>
													<input name="k3" type="button" class="jxn" value=" 3 " onclick="inputkeyJ('3')">
												</td>
												<td>
													<input name="button" type="button" class="jxo" onclick="operationJ('-',5)" value=" － ">
												</td>
											</tr>
											<tr>
												<td>
													<input name="button" type="button" class="jxn" onclick="inputkeyJ('0')" value=" 0 ">
												</td>
												<td>
													<input name="button" type="button" class="jxo" onclick="changeSignJ()" value="+/-">
												</td>
												<td>
													<input name="kp" type="button" value=" · " onclick="inputkeyJ('.')" class="jxo">
												</td>
												<td>
													<input name="button" type="button" class="jxo" onclick="operationJ('+',5)" value=" ＋ ">
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</section>
	</div>
	<!-- -------------------------------------万能计算器------------------------------------------- -->
	<div class="tab-pane fade in" id="linkman-2">
		<section class="panel clearfix padder-v m-t-md">
			<div class="calculator2">
				<form name="Wcalculate">
					<table width="800" cellpadding=2 cellspacing=0 align="center">
						<tr>
							<td align="center">
<style type="text/css">
<!--
.bw, .bw1 {
	font-family: "Microsoft Yahei";
	font-size: 18px;
	color: #666;
	background-color: #fff;
	border:1px solid #ccc;;
	height: 45px;
	width: 88px !important;
	float: left;
    margin: 0 10px 10px 0;
  box-sizing:border-box;
}
.b3 {
	font-size: 12px;
	color: #fff;
	background-color: #999;
	border: none;
	height: 45px;
	width: 80px !important;
	box-sizing: border-box;
}

.b3.dark {
	background: #60aae9;
}

.b3.green {
	background: #8ec165;
}

.b3.green2 {
	background: #00a65a;
}

.b3.yellow {
	background: #ffc333;
}

.b3.blue {
	background: #60aae9;
}

.b3.blue2 {
	background: #00c0ef;
}

.b4 {
	font-size: 16px;
	color: #666;
	background-color: #fff !important;
	height: 45px;
	width: 50px !important;
	border: 1px solid #ccc;
	margin: 0 10px 10px 0;
	box-sizing: border-box;
}
.wxo {
	font-size: 16px;
	color: #666;
	background-color: #fff;
	height: 45px;
	width: 50px;
    margin: 0 10px 10px 0;
	border: 1px solid #ccc;
}
.wxo.colorBlue{
  color:#60aae9;
  font-weight:bold;
}
.wxn {
	font-size: 16px;
	color: #666;
	background-color: #fff;
	height: 45px;
	width: 50px;
	border: 1px solid #ccc;
    margin: 0 10px 10px 0;
}
.wxn.colorBlue{
  color:#60aae9;
  font-weight:bold;
}

.dh {
	font-size: 16px;
	color: #666;
	background-color: #fff !important;
	height: 45px;
	width: 50px !important;
	border: 1px solid #ccc;
	margin: 0 10px 10px 0;
}

.wor {
	font-size: 12px;
	color: #666;
	background-color: #fff;
	height: 45px;
	width: 50px;
	border: 1px solid #ccc;
	margin: 0 10px 10px 0;
}

.wor.colorRed {
	color: #e96060;
	font-size: 16px;
	font-weight: bold;
}

.border {
	border: 1px solid #B1BAC3;
}

.result td {
	height: 45px;
	padding: 0;
}

.result td:first-child {
	padding: 0;
	background: #60aae9;
	color: #fff;
	font-size: 16px;
	width: 72px;
	text-align: center;
	height: 45px;
}

.result td input {
	border: 1px solid #ccc;
	width: 100%;
	height: 45px;
	background: #f6f6f6;
	margin: 0 0 0 1px;
	color: #555;
	font-size: 24px;
	line-height: 35px;
}
-->
</style> 
<br/>
<table width="100%" bgcolor="#FFFFFF" align="center" cellspacing="2">
	<tr>
	<form name=calc>
		<td align="center">
			<table align="center" cellspacing="2">
				<tr>
					<td align="center">
						<table height="45" width="100%" cellspacing="10" class="result">
							<tr>
								<td>结果</td>
								<td align="left" height="45"><input type=text name="display" value="0" readonly size="40"></td>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" style="margin-top:9px;">
							<tr>
								<td bgcolor="e4edf4" align="left">&nbsp;&nbsp;&nbsp;
									<input type=radio name="carry" onClick="inputChangCarry(16)">&nbsp;十六进制
									<input type=radio name="carry" checked onClick="inputChangCarry(10)">&nbsp;十进制
									<input type=radio name="carry" onClick="inputChangCarry(8)">&nbsp;八进制 
									<input type=radio name="carry" onClick="inputChangCarry(2)">&nbsp;二进制
								</td>
								<td bgcolor="e8edf1" align="left" width="34%" style="border-left:1px solid #fff;">&nbsp;&nbsp;&nbsp;
									<input type=radio name="angle" value="d" onClick="inputChangAngle('d')" checked>&nbsp;角度制　　
									<input type=radio name="angle" value="r" onClick="inputChangAngle('r')">&nbsp;弧度制
								</td>
							</tr>
						</table>
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="1">
							<tr style="border-top:1px solid #fff;">
								<td align="center" style="display:block;width:100%;">
									<table width="100%">
										<tr>
											<td bgcolor="e4edf4" width="30%">&nbsp;　
												<input name="shiftf" type="checkbox" onClick="inputshift()">&nbsp;上档功能 　　
												<input name="hypf" type="checkbox" onClick="inputshift()">&nbsp;双曲函数
											</td>
											<td align="center" class="func" bgcolor="e4edf4">
												<input name="bracket" value="" type=text size=3 readonly style="background-color=lightgrey">
											</td>
											<td align="center" class="func" bgcolor="e4edf4">
												<input name="memory" value="" type=text size=3 readonly style="background-color=lightgrey">
											</td>
											<td align="center" class="func" bgcolor="e4edf4">
												<input name="operator" value="" type=text size=3 readonly style="background-color=lightgrey">
											</td>
											<td align="left" width="34%" bgcolor="e4edf4">
												<input name="button2" type="button" class="b3 dark" onclick="backspace()" value=" 退格 ">
												<input name="button2" type="button" class="b3" onClick="document.Wcalculate.display.value = 0 "value=" 清屏 ">
												<input name="button2" type="button" class="b3" onClick="clearall()" value=" 全清" style="margin-left:-1px;">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table width="100%" cellspacing="0" style="margin-top:10px;">
							<tr align="left">
								<td>
									<table cellpadding="1" cellspacing="1">
										<tr align=center>
											<td>
												<input name=pi type="button" class="bw" onClick="inputfunction('pi','pi')" value=" PI ">
											</td>
											<td>
												<input name=e type="button" class="bw" onClick="inputfunction('e','e')" value=" E  ">
											</td>
											<td>
												<input name=bt type="button" class="bw1" onClick="inputfunction('dms','deg')" value="d.ms">
											</td>
										</tr>
										<tr align=center>
											<td>
												<input name="button" type="button" class="bw1" style="color=#ff00ff" onClick="addbracket()" value=" (  ">
											</td>
											<td>
												<input name="button" type="button" class="bw1" onClick="disbracket()" value=" )  ">
											</td>
											<td>
												<input name=ln type="button" class="bw1" onClick="inputfunction('ln','exp')" value=" ln ">
											</td>
										</tr>
										<tr align=center>
											<td>
												<input name=sin type="button" class="bw1" onClick="inputtrig('sin','arcsin','hypsin','ahypsin')" value="sin ">
											</td>
											<td>
												<input name="button" type="button" class="bw1" onClick="operation('^',7)" value="x^y ">
											</td>
											<td>
												<input name=log type="button" class="bw1" onClick="inputfunction('log','expdec')" value="log ">
											</td>
										</tr>
										<tr align=center>
											<td>
												<input name=cos type="button" class="bw1" onClick="inputtrig('cos','arccos','hypcos','ahypcos')" value="cos ">
											</td>
											<td>
												<input name=cube type="button" class="bw1" onClick="inputfunction('cube','cubt')" value="x^3 ">
											</td>
											<td>
												<input name="button" type="button" class="bw1" onClick="inputfunction('!','!')" value=" n! ">
											</td>
										</tr>
										<tr align=center>
											<td>
												<input name=tan type="button" class="bw1" onClick="inputtrig('tan','arctan','hyptan','ahyptan')" value="tan ">
											</td>
											<td>
												<input name=sqr type="button" class="bw1" onClick="inputfunction('sqr','sqrt')" value="x^2 ">
											</td>
											<td>
												<input name="button" type="button" class="bw1" onClick="inputfunction('recip','recip')" value="1/x ">
											</td>
										</tr>
									</table>
								</td>
								<td valign="top"><table cellpadding="0" style="margin-right:10px;">
										<tr>
											<td>
												<input name="button" type="button" class="b3 green" onClick="putmemory()" value=" 储存 ">
											</td>
										</tr>
										<tr>
											<td>
												<input name="button" type="button" class="b3 yellow" onClick="getmemory()" value=" 取存 " style="margin-top:10px;display:block;">
											</td>
										</tr>
										<tr>
											<td>
												<input name="button" type="button" class="b3 blue" onClick="addmemory()" value=" 累存 " style="margin-top:10px;display:block;">
											</td>
										</tr>
										<tr>
											<td>
												<input name="button" type="button" class="b3 blue2" onClick="multimemory()" value=" 积存 " style="margin-top:10px;display:block;">
											</td>
										</tr>
										<tr>
											<td>
												<input name="button" type="button" class="b3 green2" onClick="clearmemory()" value=" 清存 " style="margin-top:10px;display:block;">
											</td>
										</tr>
									</table>
								</td>
								<td align="right" valign="top">
									<table cellpadding="1">
										<tr align=center>
											<td>
												<input name=k7 type="button" value=" 7 " onClick="inputkey('7')" class="wxn">
											</td>
											<td>
												<input name=k8 type="button" class="wxn" value=" 8 " onClick="inputkey('8')">
											</td>
											<td>
												<input name=k9 type="button" class="wxn" value=" 9 " onClick="inputkey('9')">
											</td>
											<td>
												<input name="button" type="button" class="wxo colorBlue" onClick="operation('/',6)" value=" / ">
											</td>
											<td>
												<input name="button" type="button" class="wor" onClick="operation('%',6)" value="取余">
											</td>
											<td>
												<input name="button" type="button" class="wor" onClick="operation('&',3)" value=" 与 " style="margin-right:0;">
											</td>
										</tr>
										<tr align=center>
											<td>
												<input name=k4 type="button" class="wxn" value=" 4 " onClick="inputkey('4')">
											</td>
											<td>
												<input name=k5 type="button" class="wxn" value=" 5 " onClick="inputkey('5')">
											</td>
											<td>
												<input name=k6 type="button" class="wxn"	value=" 6 " onClick="inputkey('6')">
											</td>
											<td>
												<input name="button" type="button"	class="wxo colorBlue" onClick="operation('*',6)" value=" * ">
											</td>
											<td>
												<input name=floor type="button" class="wor" onClick="inputfunction('floor','deci')" value="取整">
											</td>
											<td>
												<input name="button" type="button" class="wor" onClick="operation('|',1)" value=" 或 " style="margin-right:0;">
											</td>
										</tr>
										<tr align=center>
											<td>
												<input name="button" type="button" class="wxn" onClick="inputkey('1')" value=" 1 ">
											</td>
											<td>
												<input name=k2 type="button" value=" 2 " onClick="inputkey('2')" class="wxn">
											</td>
											<td>
												<input name=k3 type="button" class="wxn" value=" 3 " onClick="inputkey('3')">
											</td>
											<td>
												<input name="button" type="button" class="wxo colorBlue" onClick="operation('-',5)" value=" - ">
											</td>
											<td>
												<input name="button" type="button" class="wor" onClick="operation('<',4)" value="左移">
											</td>
											<td>
												<input name="button" type="button"	class="wor" onClick="inputfunction('~','~')" value=" 非 " style="margin-right:0;">
											</td>
										</tr>
										<tr align=center>
											<td>
												<input name="button" type="button" class="wxn" onClick="inputkey('0')" value=" 0 ">
											</td>
											<td>
												<input name="button" type="button"	class="wxn colorBlue" onClick="changeSign()" value="+/-">
											</td>
											<td>
												<input name=kp type="button" value=" . " onClick="inputkey('.')" class="wxn colorBlue">
											</td>
											<td>
												<input name="button" type="button" class="wxo colorBlue" onClick="operation('+',5)" value=" + ">
											</td>
											<td>
												<input name="button" type="button"	class="wor colorRed" onClick="result()" value=" ＝ ">
											</td>
											<td>
												<input name="button" type="button" class="wor" onClick="operation('x',2)" value="异或" style="margin-right:0;">
											</td>
										</tr>
										<tr align=center>
											<td>
												<input name=ka type="button" disabled=true class="b4" onClick="inputkey('a')" value=" A ">
											</td>
											<td>
												<input name=kb type="button" disabled=true class="b4" onClick="inputkey('b')" value=" B ">
											</td>
											<td>
												<input name=kc type="button" disabled=true class="b4" onClick="inputkey('c')" value=" C ">
											</td>
											<td>
												<input name=kd type="button" disabled=true class="b4" onClick="inputkey('d')" value=" D ">
											</td>
											<td>
												<input name=ke type="button" disabled=true class="dh" onClick="inputkey('e')" value=" E ">
											</td>
											<td>
												<input name=kf type="button" disabled=true class="dh" onClick="inputkey('f')" value=" F " style="margin-right:0;">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</form>
</tr>
</table>
<br/>
</td>
</tr>
</table>
</form>
</div>
</section>
</div>
</section>
</section>
</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>