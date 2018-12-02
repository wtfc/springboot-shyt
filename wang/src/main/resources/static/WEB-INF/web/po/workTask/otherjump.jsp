<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--<link rel="stylesheet" type="text/css" href="styles.css">-->
</head>
<body>
	<%
	    String taskName = request.getParameter("taskName");
		String directorId = request.getParameter("directorId");//负责人
		String directorName = request.getParameter("directorName");//负责人姓名
		String content = request.getParameter("content");//主要完成事
		String standard = request.getParameter("standard");// 完成标准
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String jump = request.getParameter("jump");
	%>
	<script type="text/javascript">
	var j_jump = '<%=jump%>';
	var j_taskName = '<%=taskName%>';
	var j_directorId = '<%=directorId%>';
	var j_directorName = '<%=directorName%>';
	var j_content = '<%=content%>';
	var j_standard = '<%=standard%>';
	var j_startTime = '<%=startTime%>';
	var j_endTime = '<%=endTime%>';
		if (j_jump != 'null') {
			if (j_directorId != 'null') {
				//$("#").val(j_taskName);
			}
			if (j_taskName != 'null') {
				$("#taskName").val(j_taskName);
			}
			if (j_content != 'null') {
				$("#taskContent").val(j_content);
			}
			if (j_standard != 'null') {
				$("#standard").val(j_standard);
			}
			if (j_startTime != 'null') {
				$("#startTime").val(j_startTime);
			}
			if (j_endTime != 'null') {
				$("#endTime").val(j_endTime);
			}
		}
	</script>
	<br>
</body>
</html>