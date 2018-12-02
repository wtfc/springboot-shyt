<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
	<c:forEach items="${signList}" var="signVo">
	
	<div class="table-wrap nav-primary nav-tree m-t-md">
		<ul class="nav">
			<li><a href="#"> <i class="fa"></i> <span>${signVo.signTitle}</span>
					<div class="nav-tree-btn-wrap">
						<button class="a-icon i-edit nav-tree-btn" href="#myModal-edit"
							role="button" data-toggle="modal" onclick="mailSign.get(${signVo.id})">
							<i class="fa" data-toggle="tooltip" data-placement="top" title=""
								data-container="body" data-original-title="编辑"></i>
						</button>
						<button class="a-icon i-remove nav-tree-btn" href="#" onclick="mailSign.deleteMailSign(${signVo.id})">
							<i class="fa" data-toggle="tooltip" data-placement="top" title=""
								data-container="body"  data-original-title="删除"></i>
						</button>
					</div>
			</a>
				<ul class="nav lt">
					<li>
						
						<c:choose>
							<c:when test="${empty signVo.signContent}">
								<em><span>该签名尚未设置签名内容</span></em>
							</c:when>
							<c:otherwise>
								${signVo.signContent}
							</c:otherwise>
						</c:choose>
					</li>
				</ul></li>
		</ul>
	</div>
</c:forEach>