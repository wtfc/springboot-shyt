<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<style>
	*{
		margin: 0;
		padding: 0;
	}
	
	.inline.checkbox {
		background-color: #787878;width:4.5%;height:50px;margin-right:1px !important;
	}
	.inline.head {
		background-color: #787878;width:4.5%;height:20px;margin-right:1px;margin-left:9px !important;
	}
	
	html,body{
		height: 100%;
		background: #FFFFFF;
	}

	.main{
		height: 100%;
		width: 100%;
		overflow: auto;
	}

	#zhaowei{
		width: 100%;
		margin-top:30px;
	}

	#zhaowei tbody tr,#zhaowei tbody tr td{
		/* border: 1px black solid; */
		width: auto;
		text-align: center;
	}

	#zhaowei tbody tr td{
		height: 22px;
		line-height:22px;
		min-width: 100px;
		white-space:nowrap;
	}
	
	#zhaowei .explain{
		background: #FFFFDD; 
		height: 10px;
		text-align: left;
		font-weight: bold;
		/*padding-left: 200px;*/
	}

	#zhaowei .mar-left{
		display: inline-block;
		width: 50px;
	}
	
	.wh{
		/* margin:1px; */
		width:100%;
		background-color: #787878;
	}
</style>
<section class="scrollable padder jcGOA-section" id="scrollable">
<section class="tree-fluid m-t-md">
<h1 class="panel-heading clearfix" style="text-align: center;font-size: 25px">兆维机房平面图</h1>
<div >
	<div style="padding-top:30px">
		<button  type="button" class="btn inline head"style="background-color: #CD0000;"></button>公司自用
		<button  type="button" class="btn inline head"style="background-color: #228B22;"></button>整包机柜
		<button  type="button" class="btn inline head" style="background-color: #CDCD00;"></button>散户机柜
		<button  type="button" class="btn inline head"style="background-color: #1C86EE;"></button>预留机柜
		<button  type="button" class="btn inline head"style="background-color: black;"></button>空机柜
		<button  type="button" class="btn inline head"></button>非本公司
	</div>

<div class="main">
<table class="table table-striped tab_height first-td-tc" id="zhaowei">
<tbody>
	
	<!-- 3层 独享机房 总数：15 -->
	<tr><td colspan="10" class="explain"><span class="mar-left"></span>3层 独享机房 总数：15</td></tr>
	<!-- 排分隔 -->
	<tr>
		<td>6#20</td>
		<td>6#19</td>
		<td>6#18</td>
		<td>6#17</td>
		<td>6#16</td>
		<td>6#15</td>
		<td>6#14</td>
		<td>6#13</td>
		<td>6#12</td>
		<td>6#11</td>
	</tr>
	<tr>
		<td>亿赞普</td>
		<td>合一</td>
		<td>合一</td>
		<td>散户</td>
		<td>天津悦读</td>
		<td>公司核心</td>
		<td>公司核心</td>
		<td>公司核心</td>
		<td>公司核心</td>
		<td>慈恩天下</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>9#7</td>
		<td>9#6</td>
		<td>9#5</td>
		<td>9#4</td>
		<td>9#3</td>
	</tr>
	<tr>
		<td>境界网</td>
		<td>蓝汛</td>
		<td>散户</td>
		<td>散户</td>
		<td>白山云</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>10#8</td>
		<td>10#9</td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>

	<!-- 3层 发展2机房 总数：56 -->
	<tr><td colspan="31" class="explain"><span class="mar-left"></span>3层 发展2机房 总数：56</td></tr>
	<!-- 排分隔 -->
	<tr>
		<td>6#1</td>
		<td>6#2</td>
		<td>6#3</td>
		<td>6#4</td>
		<td>6#5</td>
		<td>6#6</td>
		<td>6#7</td>
		<td>6#8</td>
		<td>6#9</td>
		<td>6#10</td>
		<td>6#11</td>
		<td>6#12</td>
		<td>6#13</td>
		<td>6#14</td>
		<td>6#15</td>
		<td>6#16</td>
		<td>6#17</td>
		<td>6#18</td>
		<td>6#19</td>
		<td>6#20</td>
		<td>6#21</td>
		<td>6#22</td>
		<td>6#23</td>
		<td>6#24</td>
		<td>6#25</td>
		<td>6#26</td>
		<td>6#27</td>
		<td>6#28</td>
		<td>6#29</td>
		<td>6#30</td>
		<td>6#31</td>
	</tr>
	<tr>
		<td>散户</td>
		<td>万邦华唐</td>
		<td>洋浦伟业</td>
		<td>快网</td>
		<td>尚佳崇业</td>
		<td>尚佳崇业</td>
		<td>尚佳崇业</td>
		<td>创想天空</td>
		<td>上海二三四五</td>
		<td>上海二三四五</td>
		<td>学而思教育</td>
		<td>学而思教育</td>
		<td>学而思教育</td>
		<td>阳光飞华</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>散户</td>
		<td>移动微世界</td>
		<td>空</td>
		<td>创想天空</td>
		<td>尚佳崇业</td>
		<td>上海2345</td>
		<td>合一信息</td>
		<td>合一信息</td>
		<td>快网</td>
		<td>无锡汉风网络</td>
		<td>尚佳崇业</td>
		<td>飞流九天</td>
		<td>盛世传越</td>
		<td>散户</td>
		<td>散户</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>5#1</td>
		<td>5#2</td>
		<td>5#3</td>
		<td>5#4</td>
		<td>5#5</td>
		<td>5#6</td>
		<td>5#7</td>
		<td>5#8</td>
		<td>5#9</td>
		<td>5#10</td>
		<td>5#11</td>
		<td>5#12</td>
		<td>5#13</td>
		<td>5#14</td>
		<td>5#15</td>
		<td>5#16</td>
		<td>5#17</td>
		<td>5#18</td>
		<td>5#19</td>
		<td>5#20</td>
		<td>5#21</td>
		<td>5#22</td>
		<td>5#23</td>
		<td>5#24</td>
		<td>5#25</td>
	</tr>
	<tr>
		<td>蓝港在线</td>
		<td>上海彩咖</td>
		<td>蓝港在线</td>
		<td>上海二三四五</td>
		<td>神鹰城迅</td>
		<td>上海二三四五</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>洋浦伟业</td>
		<td>顽石互动</td>
		<td>上海二三四五</td>
		<td>散户</td>
		<td>上海二三四五</td>
		<td>上海二三四五</td>
		<td>上海二三四五</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>空</td>
		<td>蓝港在线</td>
		<td>蓝港在线</td>
		<td>蓝港在线</td>
		<td>蓝港在线</td>
		<td>蓝港在线</td>
		<td>蓝港在线</td>
	</tr>

	<!-- 3层 V13机房 总数：7 -->
	<tr><td colspan="7" class="explain"><span class="mar-left"></span>3层 V13机房 总数：7</td></tr>
	<tr>
		<td>6#1</td>
		<td>6#2</td>
		<td>6#3</td>
		<td>6#4</td>
		<td>6#5</td>
		<td>6#6</td>
		<td>6#7</td>
	</tr>
	<tr>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
	</tr>

	<!-- 3层 V16机房 总数：4 -->
	<tr><td colspan="4" class="explain"><span class="mar-left"></span>3层 V16机房 总数：4</td></tr>
	<tr>
		<td>1#1</td>
		<td>1#2</td>
		<td>1#3</td>
		<td>1#4</td>
	</tr>
	<tr>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
	</tr>

	<!-- 2层 2-6机房 总数：115 -->
	<tr><td colspan="29" class="explain"><span class="mar-left"></span>2层 2-6机房 总数：115</td></tr>
	<!-- 排分隔 -->
	<tr>
		<td>K1接入</td>
		<td>K2</td>
		<td>K3</td>
		<td>K4</td>
		<td>K5</td>
		<td>K6</td>
		<td>K7</td>
		<td>K8</td>
		<td>K9</td>
		<td>K10</td>
		<td>K11</td>
		<td>K12</td>
	</tr>
	<tr>
		<td>公司核心</td>
		<td>快网</td>
		<td>快网</td>
		<td>快网</td>
		<td>快网</td>
		<td>成都西山居世</td>
		<td>快网</td>
		<td>北京韵盛发</td>
		<td>北京淳宇科技</td>
		<td>成都西山居世</td>
		<td>成都西山居世</td>
		<td>成都西山居世</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>K13</td>
		<td>K14</td>
		<td>K15</td>
		<td>K16</td>
		<td>K17</td>
		<td>K18</td>
		<td>K19</td>
		<td>K20</td>
		<td>K21</td>
		<td>K22</td>
		<td>K23</td>
		<td>K24</td>
	</tr>
	<tr>
		<td>成都西山居世</td>
		<td>货车帮</td>
		<td>兰杜互联</td>
		<td>成都西山居世</td>
		<td>亚艺网媒</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>公司核心</td>
		<td>公司核心</td>
		<td>公司核心</td>
		<td>公司核心</td>
		<td>空</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>I20</td>
		<td>I21</td>
		<td>I22</td>
		<td>I23</td>
		<td>I24</td>
		<td>I25</td>
		<td>I26</td>
		<td>I27</td>
		<td>I28</td>	
	</tr>
	<tr>
		<td>普照天星</td>
		<td>新世界</td>
		<td>快网</td>
		<td>盛世慧才</td>
		<td>大公网</td>
		<td>大公网</td>
		<td>大公网</td>
		<td>亿通网联</td>
		<td>万方数据</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>H11</td>
		<td>H12</td>
		<td>H13</td>
		<td>H14</td>
	</tr>
	<tr>
		<td>散户</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>散户</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>E1</td>
		<td>E2</td>
		<td>E3</td>
		<td>E4</td>
		<td>E5</td>
		<td>E6</td>
		<td>E7</td>
		<td>E8</td>
		<td>E9</td>
		<td>E10</td>
		<td>E11</td>
		<td>E12</td>
		<td>E13</td>
		<td>E14</td>
		<td>E15</td>
		<td>E16</td>
		<td>E17</td>
		<td>E18</td>
		<td>E19</td>
		<td>E20</td>
		<td>E21</td>
		<td>E22</td>
		<td>E23</td>
	</tr>
	<tr>
		<td>乐卡包</td>
		<td>远明山水</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>F1</td>
		<td>F2</td>
		<td>F3</td>
		<td>F4</td>
		<td>F5</td>
		<td>F6</td>
		<td>F7</td>
		<td>F8</td>
		<td>F9</td>
		<td>F10</td>
		<td>F11</td>
		<td>F12</td>
		<td>F13</td>
		<td>F14</td>
		<td>F15</td>
		<td>F16</td>
		<td>F17</td>
		<td>F18</td>
		<td>F19</td>
		<td>F20</td>
		<td>F21</td>
		<td>F22</td>
		<td>F23</td>
		<td>F24</td>
		<td>F25</td>
		<td>F26</td>
		<td>F27</td>
		<td>F28</td>
		<td>F29</td>
	</tr>
	<tr>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>中棋惟业</td>
		<td>中棋惟业</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>G1</td>
		<td>G2</td>
		<td>G3</td>
		<td>G4</td>
		<td>G5</td>
		<td>G6</td>
		<td>G7</td>
		<td>G8</td>
		<td>G9</td>
		<td>G10</td>
		<td>G11</td>
		<td>G12</td>
		<td>G13</td>
		<td rowspan="2" style="border: none;"></td>
		<td>G15</td>
		<td>G16</td>
		<td>G17</td>
		<td>G18</td>
		<td>G19</td>
		<td>G20</td>
		<td>G21</td>
		<td>G22</td>
		<td>G23</td>
		<td>G24</td>
		<td>G25</td>
		<td>G26</td>
		<td>G27</td>
		<td>G28</td>
	</tr>
	<tr>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<!-- <td rowspan="2"></td> -->
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>联众互动</td>
	</tr>

	<!-- 2层 2-9机房 总数：25 -->
	<tr><td colspan="9" class="explain"><span class="mar-left"></span>2层 2-9机房 总数：25</td></tr>
	<!-- 排分隔 -->
	<tr>
		<td>Q1</td>
		<td>Q2</td>
		<td>Q3</td>
		<td>Q4</td>
		<td>Q5</td>
		<td>Q6</td>
		<td>Q7</td>
		<td>Q8</td>
	</tr>
	<tr>
		<td>麒麟网</td>
		<td>麒麟网</td>
		<td>麒麟网</td>
		<td>麒麟网</td>
		<td>世纪鑫网</td>
		<td>世纪鑫网</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>R1</td>
		<td>R2</td>
		<td>R3</td>
		<td>R4</td>
		<td>R5</td>
		<td>R6</td>
		<td>R7</td>
		<td>R8</td>
		<td>R9</td>
	</tr>
	<tr>
		<td>佰程</td>
		<td>佰程</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>S1</td>
		<td>S2</td>
		<td>S3</td>
		<td>S4</td>
		<td>S5</td>
		<td>S6</td>
		<td>S7</td>
	</tr>
	<tr>
		<td>厦门美图</td>
		<td>文森特</td>
		<td>麒麟网</td>
		<td>成都西山居世</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>兰杜互联</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>L8</td>
	</tr>
	<tr>
		<td>快网</td>
	</tr>

	<!-- 2层 2-10机房 总数：50 -->
	<tr><td colspan="25" class="explain"><span class="mar-left"></span>2层 2-10机房 总数：50</td></tr>
	<!-- 排分隔 -->
	<tr>
		<td>8#25</td>
		<td>8#24</td>
		<td>8#23</td>
		<td>8#22</td>
		<td>8#21</td>
		<td>8#20</td>
		<td>8#19</td>
		<td>8#18</td>
		<td>8#17</td>
		<td>8#16</td>
		<td>8#15</td>
		<td>8#14</td>
		<td>8#13</td>
		<td>8#12</td>
		<td>8#11</td>
		<td>8#10</td>
		<td>8#9</td>
		<td>8#8</td>
		<td>8#7</td>
		<td>8#6</td>
		<td>8#5</td>
		<td>8#4</td>
		<td>8#3</td>
		<td>8#2</td>
		<td>8#1</td>
	</tr>
	<tr>
		<td>春秋永乐</td>
		<td>春秋永乐</td>
		<td>春秋永乐</td>
		<td>成都西山居世</td>
		<td>成都西山居世</td>
		<td>成都西山居世</td>
		<td>成都西山居世</td>
		<td>成都西山居世</td>
		<td>成都西山居世</td>
		<td>成都西山居世</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>散户</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>云之谷</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
		<td>空</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>9#25</td>
		<td>9#24</td>
		<td>9#23</td>
		<td>9#22</td>
		<td>9#21</td>
		<td>9#20</td>
		<td>9#19</td>
		<td>9#18</td>
		<td>9#17</td>
		<td>9#16</td>
		<td>9#15</td>
		<td>9#14</td>
		<td>9#13</td>
		<td>9#12</td>
		<td>9#11</td>
	</tr>
	<tr>
		<td>新华新闻网</td>
		<td>新华新闻网</td>
		<td>新华新闻网</td>
		<td>新华新闻网</td>
		<td>云之谷</td>
		<td>云之谷</td>
		<td>云之谷</td>
		<td>旅游城市</td>
		<td>云之谷</td>
		<td>蓝汛</td>
		<td>蓝汛</td>
		<td>蓝汛</td>
		<td>蓝汛</td>
		<td>蓝汛</td>
		<td>蓝汛</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>10#10</td>
		<td>10#9</td>
		<td>10#8</td>
		<td>10#7</td>
		<td>10#6</td>
		<td>10#5</td>
		<td>10#4</td>
		<td>10#3</td>
		<td>10#2</td>
		<td>10#1</td>
	</tr>
	<tr>
		<td>国人付</td>
		<td>散户</td>
		<td>快网</td>
		<td>北京北京神鹰城讯科技股份有限公司科技股份有限公司</td>
		<td>萍乡射雕科技</td>
		<td>境界网络</td>
		<td>境界网络</td>
		<td>境界网络</td>
		<td>新东方迅程</td>
		<td>天域星空</td>
	</tr>

	<!-- 1层 1-1机房 总数：67 -->
	<tr><td colspan="32" class="explain"><span class="mar-left"></span>1层 1-1机房 总数：67</td></tr>
	<!-- 排分隔 -->
	<tr>
		<td>11#3</td>
	</tr>
	<tr>
		<td>酷狗</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>6#1</td>
		<td>6#2</td>
		<td>6#3</td>
		<td>6#4</td>
		<td>6#5</td>
		<td>6#6</td>
		<td>6#7</td>
		<td>6#8</td>
		<td>6#9</td>
		<td>6#10</td>
		<td>6#11</td>
		<td>6#12</td>
		<td>6#13</td>
		<td>6#14</td>
		<td>6#15</td>
		<td>6#16</td>
		<td>6#17</td>
		<td>6#18</td>
	</tr>
	<tr>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>蓝汛</td>
		<td>蓝汛</td>
		<td>蓝汛</td>
		<td>原石天达</td>
		<td>惠保科技</td>
		<td>萍乡射雕科技</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>散户</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>5#1</td>
		<td>5#2</td>
		<td>5#3</td>
		<td>5#4</td>
		<td>5#5</td>
		<td>5#6</td>
		<td>5#7</td>
		<td>5#8</td>
		<td>5#9</td>
		<td>5#10</td>
		<td>5#11</td>
		<td>5#12</td>
		<td>5#13</td>
		<td>5#14</td>
		<td>5#15</td>
		<td>5#16</td>
		<td>5#17</td>
		<td>5#18</td>
		<td>5#19</td>
		<td>5#20</td>
		<td>5#21</td>
		<td>5#22</td>
		<td>5#23</td>
		<td>5#24</td>
		<td>5#25</td>
		<td>5#26</td>
		<td>5#27</td>
		<td>5#28</td>
		<td>5#29</td>
		<td>5#30</td>
		<td>5#31</td>
		<td>5#32</td>
	</tr>
	<tr>
		<td>万方数据</td>
		<td>万方数据</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>快网</td>
		<td>腾云世纪</td>
		<td>蓝汛</td>
		<td>寰球时代</td>
		<td>散户</td>
		<td>联众互动</td>
		<td>联众互动</td>
		<td>花果金融</td>
		<td>蓝汛</td>
		<td>蓝汛</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>欢动科技</td>
		<td>公司核心</td>
		<td>快网</td>
		<td>快网</td>
		<td>快网</td>
		<td>快网</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>快网</td>
		<td>快网</td>
		<td>快网</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>4#14</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>酷狗</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>3#1</td>
		<td>3#7</td>
		<td>3#8</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>2#4</td>
		<td>2#2</td>
		<td>2#3</td>
		<td>2#4</td>
		<td>2#5</td>
		<td>2#6</td>
		<td>2#7</td>
		<td>2#9</td>
	</tr>
	<tr>
		<td></td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>寰球时代</td>
		<td>酷狗</td>
	</tr>
	<!-- 排分隔 -->
	<tr>
		<td>1#9</td>
		<td>1#10</td>
		<td>1#11</td>
		<td>1#12</td>
		<td>1#13</td>
	</tr>
	<tr>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>酷狗</td>
		<td>上海星艾</td>
	</tr>

	<!-- 1层 1-3机房 总数：3 -->
	<tr><td colspan="3" class="explain"><span class="mar-left"></span>1层 1-3机房 总数：3</td></tr>
	<tr>
		<td>1#1</td>
		<td>1#2</td>
		<td>1#3</td>
	</tr>
	<tr>
		<td>厦门美图</td>
		<td>厦门美图</td>
		<td>厦门美图</td>
	</tr>
	
</tbody>
</table>
</div>
</div>
</section>
</section>
<div id="formModuleDiv" ></div>
<script >
//设置每行按钮
function add(name,number){
 $("#formModuleDiv").load(getRootPath()+"/machine/view/loadFormView.action?name="+name+"&&number="+number+"",null,function(){
	 viewModule.show();
		});
}
</script>

<%@ include file="/WEB-INF/web/include/foot.jsp"%>