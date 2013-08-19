<%
String path = request.getContextPath();
String basePath = String.format("%s://%s:%d/%s/", request.getScheme(), 
                                request.getServerName(), 
                                request.getServerPort(), path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
  <link rel="stylesheet" type="text/css" href="<%=path %>/css/ext-all.css" />
  <script type="text/javascript" src="<%=path %>/js/lib/ext-all.js"></script>
  <script type="text/javascript" src="<%=path %>/js/courseware/index.js"></script>
</head>
<body>
</body>
</html>