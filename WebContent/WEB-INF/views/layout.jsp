<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
  String path = request.getContextPath();
  String pageTitle = (String) request.getAttribute("pageTitle");
  if (pageTitle == null)
    pageTitle = "";
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>计算机系统结构资源——${pageTitle }</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/ext-all.css" />
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background-color: #F9FAF7;
}
</style>
<script>
var pageTitle = '<%=pageTitle%>';
</script>
<script type="text/javascript" src="<%=path%>/js/lib/ext-all.js"></script>
<script type="text/javascript" src="<%=path%>/js/layout.js"></script>
</head>
<body>
</body>
</html>