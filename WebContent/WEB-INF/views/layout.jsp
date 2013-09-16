<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  String path = request.getContextPath();
  String pageTitle = (String) request.getAttribute("pageTitle");
  if (pageTitle == null) 
    pageTitle = "";
  else
    pageTitle = "——" + pageTitle;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" >
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>计算机系统结构资源——${pageTitle }</title>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/layout.css" />
</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top" id="myheader">
    <div class="container">
      <div class="navbar-header">
        <span class="navbar-brand" id="mybrand">计算机系统结构资源——${pageTitle }</span>
      </div>
      <div class="collapse navbar-collapse navbar-ex1-collapse pull-right">
        <ul class="nav navbar-nav" id="mynavbar">
        <%if(pageTitle.equals("素材库")) {%>
          <li class="active"><a href="<%=path%>/image">素材库</a></li>
        <%} else {%>
          <li><a href="<%=path%>/image">素材库</a></li>
        <%}%>
        <%if(pageTitle.equals("动画库")) {%>
          <li class="active"><a href="<%=path%>/flash">动画库</a></li>
        <%} else {%>
          <li><a href="<%=path%>/flash">动画库</a></li>
        <%}%>
        <%if(pageTitle.equals("专题库")) {%>
          <li class="active"><a href="<%=path%>/subject">专题库</a></li>
        <%} else {%>
          <li><a href="<%=path%>/subject">专题库</a></li>
        <%}%>
        <%if(pageTitle.equals("题库")) {%>
          <li class="active"><a href="<%=path%>/problemset">题库</a></li>
        <%} else {%>
          <li><a href="<%=path%>/problemset">题库</a></li>
        <%}%>
        </ul>
      </div>
    </div>
  </div>
  <div style="height: 100%; clear: both; background-color: #f7f7f7;">
    <div id="main-content" class="center" style="height:100%;">
    </div>
  </div>
  <script>
var root = '<%=path%>/';
var pageTitle = '<%=pageTitle%>';
</script>
<script type="text/javascript" src="<%=path%>/js/lib/ext-all.js"></script>
<!--[if lt IE 9]>
<script src="<%=path%>/js/assets/js/html5shiv.js"></script>
<script src="<%=path%>/js/assets/js/respond.min.js"></script>
<![endif]-->
  <script type="text/javascript" src="<%=path%>/js/assets/js/jquery.js"></script>
  <script type="text/javascript" src="<%=path%>/js/lib/bootstrap.min.js"></script>  
</body>
</html>
