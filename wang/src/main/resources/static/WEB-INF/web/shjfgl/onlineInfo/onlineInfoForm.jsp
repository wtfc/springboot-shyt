<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<section class="panel m-t-md" id="body">
			<h3 class="tc" style="margin:0;border:0;">森华易腾中层管理人员岗位胜任力调研问卷 </h3>
			<div style="margin-right:20px;margin-left:18px">
			<!-- <h4 >指导语</h4>-->
			<span>亲爱的小伙伴：您好！<br/>为了更加科学有效地评价中层管理人员岗位胜任力及制定未来人才开发战略，人事行政部特开展此次问卷调查。本次调查为匿名形式，您的个人信息及评价结果不会以任何形式展示在系统及报告中，请您根据被评价人的实际情况如实填写，感谢您的支持。</span><br/><h5 style="text-align:right">人事行政部</h5>
			</div>
			<section class="dis-table">
             <section class="panel-tab-con dis-table-cell">
                <div class="tab-pane for fade active in" id="messages1">
                <form class="table-wrap  " id="equipmentInOutForm">
                <input type="hidden" id="id" name="id" value="0"> 
				<input type="hidden" id="token" name="token" value="${data.token}">
				<input type="hidden" id="modifyDate" name="modifyDate">
				<input type="hidden" id="extStr1" name="extStr1" value="${name}">
				<h4>第一部分：岗位胜任能力评价<span style="color:red;font-size:15px">(我们推荐您填写【评价】内容)</span></h4>
				<span ><span style="color:red">评价标准：</span><span style="color:red">5分</span>:非常符合&nbsp&nbsp&nbsp&nbsp<span style="color:red">4分</span>:比较符合&nbsp&nbsp&nbsp&nbsp<span style="color:red">3分</span>:一般符合&nbsp&nbsp&nbsp&nbsp<span style="color:red">2分</span>:不太符合&nbsp&nbsp&nbsp&nbsp<span style="color:red">1分</span>:极不符合</span>
				<table class="table table-striped tab_height" >
					<tbody >
						<tr>
							<td style="text-align:left;">1、较强的专业知识和技能，能够指导我的工作或给予关键意见。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio"  name="num1" value="5"/>5分&nbsp&nbsp
							<input type="radio"  name="num1" value="4"/>4分&nbsp&nbsp
							<input type="radio"  name="num1" value="3"/>3分&nbsp&nbsp
							<input type="radio"  name="num1" value="2"/>2分&nbsp&nbsp
							<input type="radio"  name="num1" value="1"/>1分	
							<br/><br/>评价：<input style="width:500px;"type="text" name="num20" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">2、能够不断强化自身专业知识和技能，加强自我学习。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num2" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num2" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num2" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num2" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num2" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num21" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">3、工作过程中能够公平公正对待人&事，不徇私舞弊，不搞小团体主义。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num3" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num3" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num3" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num3" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num3" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num22" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">4、能够合理有效的分配我的工作任务和时间。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num4" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num4" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num4" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num4" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num4" value="1"/>1分	
							<br/><br/>评价：<input style="width:500px;"type="text" name="num23" />					
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">5、能将复杂任务有条理地分解成若干个可处理的任务。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num5" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num5" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num5" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num5" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num5" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num24" />							
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">6、运用文字或图表等形式清晰、准确地说明自己对工作的构想或设计理念等。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num6" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num6" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num6" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num6" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num6" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num25" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">7、能够在较短的时间内迅速地、果断地解决/完成突发事件或计划外的事件。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num7" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num7" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num7" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num7" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num7" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num26" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">8、在我工作的部门里，所有人对部门的工作目标都十分了解。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num8" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num8" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num8" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num8" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num8" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num27" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">9、在我工作的部门里，我清楚公司的使命和追求目标，感受到公司的文化理念和价值观念。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num9" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num9" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num9" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num9" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num9" value="1"/>1分	
							<br/><br/>评价：<input style="width:500px;"type="text" name="num28" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">10、在我的工作部门里，对公司的政策和决定有很强的信任感。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num10" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num10" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num10" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num10" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num10" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num29" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">11、我的部门中大部分员工都能够在自己的工作岗位上发挥自己的专长。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num11" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num11" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num11" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num11" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num11" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num30" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">12、当处理人/事过程中出现错误或不公平现象时，能够及时发现、改正，并分析错误的根源。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num12" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num12" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num12" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num12" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num12" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num31" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">13、尊重团队成员的意见，并对员工提出的意见或建议，及时给与回应。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num13" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num13" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num13" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num13" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num13" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num32" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">14、对下级取得的进步或业绩给与公开的肯定，以激励员工不断前进。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num14" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num14" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num14" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num14" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num14" value="1"/>1分	
							<br/><br/>评价：<input style="width:500px;"type="text" name="num33" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">15、能够增强部门团队合作氛围，为促进团队合作，付出较多的努力。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num15" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num15" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num15" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num15" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num15" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num34" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">16、敢于承担团队工作中出现的问题，不逃避或推卸责任。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num16" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num16" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num16" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num16" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num16" value="1"/>1分	
							<br/><br/>评价：<input style="width:500px;"type="text" name="num35" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">17、能够正确处理下属间出现的观点分歧或矛盾等情况。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num17" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num17" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num17" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num17" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num17" value="1"/>1分
							<br/><br/>评价：<input style="width:500px;"type="text" name="num36" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">18、跨部门沟通时，能够与其他部门充分的沟通和交流，实现工作目标。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num18" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num18" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num18" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num18" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num18" value="1"/>1分	
							<br/><br/>评价：<input style="width:500px;"type="text" name="num37" />						
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">19、压力面前，能够自我控制，并具备安抚他人情绪的能力。符合程度</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp
							<input type="radio" name="num19" value="5"/>5分&nbsp&nbsp
							<input type="radio" name="num19" value="4"/>4分&nbsp&nbsp
							<input type="radio" name="num19" value="3"/>3分&nbsp&nbsp
							<input type="radio" name="num19" value="2"/>2分&nbsp&nbsp
							<input type="radio" name="num19" value="1"/>1分	
							<br/><br/>评价：<input style="width:500px;"type="text" name="num38" />					
							</td>
						</tr>
						</tbody>
					</table>
					<h4>第二部分：开放性问题</h4>
					<table class="table table-striped tab_height" >
					<tbody >
						<tr>
							<td style="text-align:left;">1、您认为公司管理团队的核心优势是什么？</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp<input type="radio" name="num39" value="A"/>A&nbsp以人为本&nbsp&nbsp
							<input type="radio" name="num39" value="B"/>B&nbsp公平公正&nbsp&nbsp
							<input type="radio" name="num39" value="C"/>C&nbsp效率优先&nbsp&nbsp
							<input type="radio" name="num39" value="D"/>D&nbsp向心力佳
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">2、您认为公司中层管理人员的工作哪些方面问题最多？</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp<input type="radio" name="num40" value="A"/>A&nbsp专业能力欠缺&nbsp&nbsp
							<input type="radio" name="num40" value="B"/>B&nbsp管理能力欠缺&nbsp&nbsp
							<input type="radio" name="num40" value="C"/>C&nbsp缺乏凝聚力&nbsp&nbsp	
							<input type="radio" name="num40" value="D"/>D&nbsp缺乏主动性
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">3、您心中所钦佩敬仰的管理者，他最关键的能力素质有哪些？</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp<input type="radio" name="num41" value="A"/>A&nbsp团队管理能力&nbsp&nbsp
							<input type="radio" name="num41" value="B"/>B&nbsp专业能力&nbsp&nbsp
							<input type="radio" name="num41" value="C"/>C&nbsp执行力&nbsp&nbsp	
							<input type="radio" name="num41" value="D"/>D&nbsp个人魅力
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">4、你认为是否有必要对公司的中层管理人员进行管理知识的培训</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp<input type="radio" name="num42" value="A"/>A&nbsp有&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							<input type="radio" name="num42" value="B"/>B&nbsp没有
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">5、没有人天生是完美的。如果您的上级领导愿意通过努力来提升自己的管理能力和综合素质，作为下属，您会愿意摒弃前嫌来配合他更好的完成工作吗？</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp<input type="radio" name="num43" value="A"/>A&nbsp是&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							<input type="radio" name="num43" value="B"/>B&nbsp否
							</td>
						</tr>
						<tr>
							<td style="text-align:left;">6、除了薪酬之外，您最看重的是：</td>
						</tr>
						<tr>
							<td style="text-align:left;">
							选项：&nbsp&nbsp<input type="radio" name="num44" value="A"/>A&nbsp提高自己能力的机会&nbsp&nbsp
							<input type="radio" name="num44" value="B"/>B&nbsp良好的工作环境&nbsp&nbsp
							<input type="radio" name="num44" value="C"/>C&nbsp和谐的人际关系&nbsp&nbsp
							<input type="radio" name="num44" value="D"/>D&nbsp工作的成就感		
							                              				
							</td>
						</tr>
						</tbody>
					</table>
					<div >
						<button class="btn dark" type="button" onclick=equipmentInOutModule.saveOrModify(true) >提交保存</button>
				</div>
				</form>
				</div>
			</section>
			</section>
	</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/shjfgl/onlineInfo/onlineInfoForm.js" type="text/javascript"></script>
<script src='${sysPath}/js/com/shjfgl/onlineInfo/onlineInfo.validate.js'></script>